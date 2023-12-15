/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.olingo.odata2.jpa.processor.ref.extension;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.HttpMethod;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType.Type;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImportParameter;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.jpa.processor.ref.model.Address;
import org.apache.olingo.odata2.jpa.processor.ref.model.SalesOrderHeader;
import org.apache.olingo.odata2.jpa.processor.ref.model.SalesOrderItem;

// TODO: Auto-generated Javadoc
/**
 * The Class SalesOrderHeaderProcessor.
 */
public class SalesOrderHeaderProcessor {

  /** The em. */
  private EntityManager em;

  /**
   * Instantiates a new sales order header processor.
   */
  public SalesOrderHeaderProcessor() {
    em = Persistence.createEntityManagerFactory("salesorderprocessing")
        .createEntityManager();
  }

  /**
   * Find all sales orders.
   *
   * @param status the status
   * @return the list
   */
  @SuppressWarnings("unchecked")
  @EdmFunctionImport(name = "FindAllSalesOrders", entitySet = "SalesOrders", returnType = @ReturnType(
      type = Type.ENTITY, isCollection = true))
  public List<SalesOrderHeader> findAllSalesOrders(
      @EdmFunctionImportParameter(name = "DeliveryStatusCode",
          facets = @EdmFacets(maxLength = 2)) final String status) {

    Query q = em
        .createQuery("SELECT E1 from SalesOrderHeader E1 WHERE E1.deliveryStatus = '"
            + status + "'");
    List<SalesOrderHeader> soList = (List<SalesOrderHeader>) q
        .getResultList();
    return soList;
  }

  /**
   * Check ATP.
   *
   * @param soID the so ID
   * @param lineItemID the line item ID
   * @return true, if successful
   */
  @EdmFunctionImport(name = "CheckATP", returnType = @ReturnType(type = Type.SIMPLE, isCollection = false),
      httpMethod = HttpMethod.GET)
  public boolean checkATP(
      @EdmFunctionImportParameter(name = "SoID", facets = @EdmFacets(nullable = false)) final Long soID,
      @EdmFunctionImportParameter(name = "LiId", facets = @EdmFacets(nullable = false)) final Long lineItemID) {
    if (soID == 2L) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Calculate net amount.
   *
   * @param soID the so ID
   * @return the sales order header
   * @throws ODataException the o data exception
   */
  @EdmFunctionImport(returnType = @ReturnType(type = Type.ENTITY, isCollection = false), entitySet = "SalesOrders")
  public SalesOrderHeader calculateNetAmount(
      @EdmFunctionImportParameter(name = "SoID", facets = @EdmFacets(nullable = false)) final Long soID)
      throws ODataException {

    if (soID <= 0L) {
      throw new ODataException("Invalid SoID");
    }

    Query q = em
        .createQuery("SELECT E1 from SalesOrderHeader E1 WHERE E1.soId = "
            + soID + "l");
    if (q.getResultList().isEmpty()) {
      return null;
    }
    SalesOrderHeader so = (SalesOrderHeader) q.getResultList().get(0);
    double amount = 0;
    for (SalesOrderItem soi : so.getSalesOrderItem()) {
      amount = amount
          + (soi.getAmount() * soi.getDiscount() * soi.getQuantity());
    }
    so.setNetAmount(amount);
    return so;
  }

  /**
   * Gets the address.
   *
   * @param soID the so ID
   * @return the address
   */
  @SuppressWarnings("unchecked")
  @EdmFunctionImport(returnType = @ReturnType(type = Type.COMPLEX))
  public Address getAddress(
      @EdmFunctionImportParameter(name = "SoID", facets = @EdmFacets(nullable = false)) final Long soID) {
    Query q = em
        .createQuery("SELECT E1 from SalesOrderHeader E1 WHERE E1.soId = "
            + soID + "l");
    List<SalesOrderHeader> soList = (List<SalesOrderHeader>) q
        .getResultList();
    if (!soList.isEmpty()) {
      return soList.get(0).getCustomer().getAddress();
    } else {
      return null;
    }
  }

  /**
   * Order value.
   *
   * @param soID the so ID
   * @return the order value
   */
  @EdmFunctionImport(returnType = @ReturnType(type = Type.COMPLEX))
  public OrderValue orderValue(
      @EdmFunctionImportParameter(name = "SoId", facets = @EdmFacets(nullable = false)) final Long soID) {
    Query q = em
        .createQuery("SELECT E1 from SalesOrderHeader E1 WHERE E1.soId = "
            + soID + "l");
    if (q.getResultList().isEmpty()) {
      return null;
    }
    SalesOrderHeader so = (SalesOrderHeader) q.getResultList().get(0);
    double amount = 0;
    for (SalesOrderItem soi : so.getSalesOrderItem()) {
      amount = amount
          + (soi.getAmount() * soi.getDiscount() * soi.getQuantity());
    }
    OrderValue orderValue = new OrderValue();
    orderValue.setAmount(amount);
    orderValue.setCurrency(so.getCurrencyCode());
    return orderValue;
  }

}
