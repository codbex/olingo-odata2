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

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// TODO: Auto-generated Javadoc
/**
 * The Class SalesOrderItemKey.
 */
@Embeddable
public class SalesOrderItemKey implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new sales order item key.
   */
  public SalesOrderItemKey() {}

  /**
   * Instantiates a new sales order item key.
   *
   * @param liId the li id
   */
  public SalesOrderItemKey(final long liId) {
    super();
    this.liId = liId;
  }

  /** The so id. */
  @Column(name = "Sales_Order_Id", nullable = false)
  private long soId;

  /** The li id. */
  @Column(name = "Sales_Order_Item_Id", unique = true)
  private long liId;

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (liId ^ (liId >>> 32));
    result = prime * result + (int) (soId ^ (soId >>> 32));
    return result;
  }

  /**
   * Equals.
   *
   * @param obj the obj
   * @return true, if successful
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SalesOrderItemKey other = (SalesOrderItemKey) obj;
    if (liId != other.liId) {
      return false;
    }
    if (soId != other.soId) {
      return false;
    }
    return true;
  }

  /**
   * Gets the so id.
   *
   * @return the so id
   */
  public long getSoId() {
    return soId;
  }

  /**
   * Sets the so id.
   *
   * @param soId the new so id
   */
  public void setSoId(final long soId) {
    this.soId = soId;
  }

  /**
   * Gets the li id.
   *
   * @return the li id
   */
  public long getLiId() {
    return liId;
  }

  /**
   * Sets the li id.
   *
   * @param liId the new li id
   */
  public void setLiId(final long liId) {
    this.liId = liId;
  }
}
