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
package org.apache.olingo.odata2.jpa.processor.ref.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

// TODO: Auto-generated Javadoc
/**
 * The Class SalesOrderItem.
 */
@Entity
@Table(name = "T_SALESORDERITEM")
@EntityListeners(org.apache.olingo.odata2.jpa.processor.ref.listeners.SalesOrderItemTombstoneListener.class)
public class SalesOrderItem {

  /**
   * Instantiates a new sales order item.
   */
  public SalesOrderItem() {}

  /**
   * Instantiates a new sales order item.
   *
   * @param quantity the quantity
   * @param amount the amount
   * @param discount the discount
   * @param material the material
   */
  public SalesOrderItem(final int quantity, final double amount,
      final double discount, final Material material) {
    super();
    this.quantity = quantity;
    this.amount = amount;
    this.discount = discount;
    this.material = material;
  }

  /** The sales order item key. */
  @EmbeddedId
  private SalesOrderItemKey salesOrderItemKey;

  /** The quantity. */
  @Column
  private int quantity;

  /** The amount. */
  @Column
  private double amount;

  /** The discount. */
  @Column
  private double discount;

  /** The net amount. */
  @Transient
  private double netAmount;

  /** The delivered. */
  @Column
  private boolean delivered;

  /**
   * Checks if is delivered.
   *
   * @return true, if is delivered
   */
  public boolean isDelivered() {
    return delivered;
  }

  /**
   * Sets the delivered.
   *
   * @param deliveryStatus the new delivered
   */
  public void setDelivered(final boolean deliveryStatus) {
    delivered = deliveryStatus;
  }

  /** The material. */
  @JoinColumn(name = "Material_Id")
  @ManyToOne
  private Material material;

  /** The sales order header. */
  @JoinColumn(name = "Sales_Order_Id", referencedColumnName = "SO_ID", insertable = false, updatable = false)
  @ManyToOne
  private SalesOrderHeader salesOrderHeader;

  /**
   * Gets the sales order item key.
   *
   * @return the sales order item key
   */
  public SalesOrderItemKey getSalesOrderItemKey() {
    return salesOrderItemKey;
  }

  /**
   * Sets the sales order item key.
   *
   * @param salesOrderItemKey the new sales order item key
   */
  public void setSalesOrderItemKey(final SalesOrderItemKey salesOrderItemKey) {
    this.salesOrderItemKey = salesOrderItemKey;
  }

  /**
   * Gets the quantity.
   *
   * @return the quantity
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity.
   *
   * @param quantity the new quantity
   */
  public void setQuantity(final int quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets the amount.
   *
   * @return the amount
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Sets the amount.
   *
   * @param amount the new amount
   */
  public void setAmount(final double amount) {
    this.amount = amount;
  }

  /**
   * Gets the discount.
   *
   * @return the discount
   */
  public double getDiscount() {
    return discount;
  }

  /**
   * Sets the discount.
   *
   * @param discount the new discount
   */
  public void setDiscount(final double discount) {
    this.discount = discount;
  }

  /**
   * Gets the net amount.
   *
   * @return the net amount
   */
  public double getNetAmount() {
    return netAmount;
  }

  /**
   * Sets the net amount.
   *
   * @param netAmount the new net amount
   */
  public void setNetAmount(final double netAmount) {
    this.netAmount = netAmount;
  }

  /**
   * Gets the material.
   *
   * @return the material
   */
  public Material getMaterial() {
    return material;
  }

  /**
   * Sets the material.
   *
   * @param material the new material
   */
  public void setMaterial(final Material material) {
    this.material = material;
  }

  /**
   * Gets the sales order header.
   *
   * @return the sales order header
   */
  public SalesOrderHeader getSalesOrderHeader() {
    return salesOrderHeader;
  }

  /**
   * Sets the sales order header.
   *
   * @param salesOrderHeader the new sales order header
   */
  public void setSalesOrderHeader(final SalesOrderHeader salesOrderHeader) {
    this.salesOrderHeader = salesOrderHeader;
  }
}
