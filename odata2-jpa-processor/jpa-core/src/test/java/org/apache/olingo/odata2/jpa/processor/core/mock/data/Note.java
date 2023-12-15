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
package org.apache.olingo.odata2.jpa.processor.core.mock.data;

// TODO: Auto-generated Javadoc
/**
 * The Class Note.
 */
public class Note {
  
  /** The id. */
  private String id;
  
  /** The sales order header. */
  private SalesOrderHeader salesOrderHeader;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(String id) {
    this.id = id;
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
  public void setSalesOrderHeader(SalesOrderHeader salesOrderHeader) {
    this.salesOrderHeader = salesOrderHeader;
  }
}
