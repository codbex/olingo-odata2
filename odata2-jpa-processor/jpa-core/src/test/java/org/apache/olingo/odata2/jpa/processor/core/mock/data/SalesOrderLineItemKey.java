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
 * The Class SalesOrderLineItemKey.
 */
public class SalesOrderLineItemKey {

  /** The so id. */
  private int soId;
  
  /** The li id. */
  private int liId;

  /**
   * Instantiates a new sales order line item key.
   */
  public SalesOrderLineItemKey() {

  }

  /**
   * Instantiates a new sales order line item key.
   *
   * @param soId the so id
   * @param liId the li id
   */
  public SalesOrderLineItemKey(final int soId, final int liId) {
    super();
    this.soId = soId;
    this.liId = liId;
  }

  /**
   * Gets the so id.
   *
   * @return the so id
   */
  public int getSoId() {
    return soId;
  }

  /**
   * Sets the so id.
   *
   * @param soId the new so id
   */
  public void setSoId(final int soId) {
    this.soId = soId;
  }

  /**
   * Gets the li id.
   *
   * @return the li id
   */
  public int getLiId() {
    return liId;
  }

  /**
   * Sets the li id.
   *
   * @param liId the new li id
   */
  public void setLiId(final int liId) {
    this.liId = liId;
  }

}
