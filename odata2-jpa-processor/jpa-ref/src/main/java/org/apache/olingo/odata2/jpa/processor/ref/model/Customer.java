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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Customer.
 */
@Entity
@Table(name = "T_CUSTOMER")
@EntityListeners(org.apache.olingo.odata2.jpa.processor.ref.listeners.CustomerQueryExtension.class)
public class Customer extends CustomerBase {

  /** The id. */
  @Id
  @Column(name = "ID", nullable = false, length = 20)
  private Long id;

  /** The parent. */
  @ManyToOne(optional = true)
  @JoinColumn(name = "PARENT_ID", referencedColumnName = "ID" , nullable = true)
  private Customer parent;

  /** The name. */
  @Column(name = "NAME")
  private String name;

  /** The importance. */
  @Enumerated(EnumType.STRING)
  @Column(name = "IMPORTANCE")
  private Importance importance;

  /** The address. */
  @Embedded
  private Address address;

  /** The created at. */
  @Column(name = "CREATED_AT")
  private Timestamp createdAt;

  /** The orders. */
  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
  private List<SalesOrderHeader> orders = new ArrayList<SalesOrderHeader>();

  /**
   * Gets the orders.
   *
   * @return the orders
   */
  public List<SalesOrderHeader> getOrders() {
    return orders;
  }

  /**
   * Sets the orders.
   *
   * @param orders the new orders
   */
  public void setOrders(List<SalesOrderHeader> orders) {
    this.orders = orders;
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Gets the parent.
   *
   * @return the parent
   */
  public Customer getParent() {
    return parent;
  }

  /**
   * Sets the parent.
   *
   * @param parent the new parent
   */
  public void setParent(Customer parent) {
    this.parent = parent;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Gets the importance.
   *
   * @return the importance
   */
  public Importance getImportance() {
    return importance;
  }

  /**
   * Sets the importance.
   *
   * @param importance the new importance
   */
  public void setImportance(Importance importance) {
    this.importance = importance;
  }

  /**
   * Gets the address.
   *
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Sets the address.
   *
   * @param address the new address
   */
  public void setAddress(final Address address) {
    this.address = address;
  }

  /**
   * Gets the created at.
   *
   * @return the created at
   */
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the created at.
   *
   * @param createdAt the new created at
   */
  public void setCreatedAt(final Timestamp createdAt) {
    this.createdAt = createdAt;
  }

}
