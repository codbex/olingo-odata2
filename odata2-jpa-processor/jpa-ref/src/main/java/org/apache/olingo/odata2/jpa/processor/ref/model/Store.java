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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Store.
 */
@Entity
@Table(name = "T_STORE")
public class Store {

  /**
   * Instantiates a new store.
   */
  public Store() {}

  /**
   * Instantiates a new store.
   *
   * @param storeName the store name
   * @param storeAddress the store address
   */
  public Store(final String storeName, final Address storeAddress) {
    super();
    this.storeName = storeName;
    this.storeAddress = storeAddress;
  }

  /** The store id. */
  @Id
  @Column(name = "STORE_ID")
  private long storeId;

  /** The store name. */
  @Column(name = "STORE_NAME", unique = true)
  private String storeName;

  /** The store address. */
  @Embedded
  private Address storeAddress;

  /** The materials. */
  @ManyToMany(mappedBy = "stores")
  private List<Material> materials = new ArrayList<Material>();

  /**
   * Gets the store id.
   *
   * @return the store id
   */
  public long getStoreId() {
    return storeId;
  }

  /**
   * Sets the store id.
   *
   * @param storeId the new store id
   */
  public void setStoreId(final long storeId) {
    this.storeId = storeId;
  }

  /**
   * Gets the store name.
   *
   * @return the store name
   */
  public String getStoreName() {
    return storeName;
  }

  /**
   * Sets the store name.
   *
   * @param storeName the new store name
   */
  public void setStoreName(final String storeName) {
    this.storeName = storeName;
  }

  /**
   * Gets the store address.
   *
   * @return the store address
   */
  public Address getStoreAddress() {
    return storeAddress;
  }

  /**
   * Sets the store address.
   *
   * @param storeAddress the new store address
   */
  public void setStoreAddress(final Address storeAddress) {
    this.storeAddress = storeAddress;
  }

  /**
   * Gets the materials.
   *
   * @return the materials
   */
  public List<Material> getMaterials() {
    return materials;
  }

  /**
   * Sets the materials.
   *
   * @param materials the new materials
   */
  public void setMaterials(final List<Material> materials) {
    this.materials = materials;
    Iterator<Material> itr = materials.iterator();
    while (itr.hasNext()) {
      itr.next().getStores().add(this);
    }
  }
}