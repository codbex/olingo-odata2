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

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class SalesOrderHeader.
 */
public class SalesOrderHeader {

  /** The id. */
  private int id;
  
  /** The description. */
  private String description;

  /**
   * Instantiates a new sales order header.
   */
  public SalesOrderHeader() {}

  /**
   * Instantiates a new sales order header.
   *
   * @param id the id
   * @param description the description
   */
  public SalesOrderHeader(final int id, final String description) {
    super();
    this.id = id;
    this.description = description;
  }

  /** The sales order line items. */
  private List<SalesOrderLineItem> salesOrderLineItems = new ArrayList<SalesOrderLineItem>();
  
  /** The notes. */
  private List<Note> notes = new ArrayList<Note>();

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(final String description) {
    this.description = description;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final int id) {
    this.id = id;
  }

  /**
   * Gets the sales order line items.
   *
   * @return the sales order line items
   */
  public List<SalesOrderLineItem> getSalesOrderLineItems() {
    return salesOrderLineItems;
  }

  /**
   * Sets the sales order line items.
   *
   * @param salesOrderLineItems the new sales order line items
   */
  public void setSalesOrderLineItems(final List<SalesOrderLineItem> salesOrderLineItems) {
    this.salesOrderLineItems = salesOrderLineItems;
  }

  /**
   * Gets the notes details.
   *
   * @return the notes details
   */
  public List<Note> getNotesDetails() {
    return notes;
  }

  /**
   * Sets the notes details.
   *
   * @param notes the new notes details
   */
  public void setNotesDetails(List<Note> notes) {
    this.notes = notes;
  }

}
