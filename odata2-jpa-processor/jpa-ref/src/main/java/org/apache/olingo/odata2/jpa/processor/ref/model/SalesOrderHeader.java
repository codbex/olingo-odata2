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
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

// TODO: Auto-generated Javadoc
/**
 * The Class SalesOrderHeader.
 */
@Entity
@Table(name = "T_SALESORDERHEADER")
@EntityListeners(org.apache.olingo.odata2.jpa.processor.ref.listeners.SalesOrderTombstoneListener.class)
public class SalesOrderHeader {

  /**
   * Instantiates a new sales order header.
   */
  public SalesOrderHeader() {}

  /**
   * Instantiates a new sales order header.
   *
   * @param creationDate the creation date
   * @param currencyCode the currency code
   * @param netAmount the net amount
   * @param deliveryStatus the delivery status
   * @param shortText the short text
   * @param longText the long text
   */
  public SalesOrderHeader(final Calendar creationDate, final String currencyCode, final double netAmount,
      final String deliveryStatus, final char[] shortText, final Character[] longText) {
    super();
    this.creationDate = creationDate;
    this.currencyCode = currencyCode;
    this.deliveryStatus = deliveryStatus;
    this.shortText = shortText;
    this.longText = longText;
  }

  /** The so id. */
  @Id
  @Column(name = "SO_ID")
  private long soId;

  /** The creation date. */
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar creationDate;

  /** The status. */
  @Column
  private Character status;

  /** The short text. */
  @Column(name = "SHORT_TEXT", length = 20)
  private char[] shortText;

  /** The long text. */
  @Column(name = "LONG_TEXT", length = 40)
  private Character[] longText;

  /** The currency code. */
  @Column(name = "CURRENCY_CODE", length = 3)
  private String currencyCode;

  /** The delivery status. */
  @Column(name = "DELIVERY_STATUS", length = 2)
  private String deliveryStatus;

  /** The gross amount. */
  @Column(precision = 5)
  private double grossAmount;

  /** The net amount. */
  @Column(precision = 8)
  private double netAmount;

  /** The sales order item. */
  @OneToMany(mappedBy = "salesOrderHeader", cascade = CascadeType.ALL)
  private Set<SalesOrderItem> salesOrderItem = new HashSet<SalesOrderItem>();

  /** The notes. */
  @OneToMany(mappedBy = "salesOrderHeader")
  private List<Note> notes = new ArrayList<Note>();

  /** The customer. */
  @ManyToOne
  @JoinColumn
  private Customer customer;

  /**
   * Gets the customer.
   *
   * @return the customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * Sets the customer.
   *
   * @param customer the new customer
   */
  public void setCustomer(final Customer customer) {
    this.customer = customer;
  }

  /**
   * Gets the status.
   *
   * @return the status
   */
  public Character getStatus() {
    return status;
  }

  /**
   * Sets the status.
   *
   * @param status the new status
   */
  public void setStatus(final Character status) {
    this.status = status;
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
   * Gets the creation date.
   *
   * @return the creation date
   */
  public Calendar getCreationDate() {
    if (creationDate == null) {
      return null;
    }

    return creationDate;
  }

  /**
   * Sets the creation date.
   *
   * @param creationDate the new creation date
   */
  public void setCreationDate(final Calendar creationDate) {

    this.creationDate = creationDate;
  }

  /**
   * Gets the currency code.
   *
   * @return the currency code
   */
  public String getCurrencyCode() {
    return currencyCode;
  }

  /**
   * Sets the currency code.
   *
   * @param currencyCode the new currency code
   */
  public void setCurrencyCode(final String currencyCode) {
    this.currencyCode = currencyCode;
  }

  /**
   * Gets the delivery status.
   *
   * @return the delivery status
   */
  public String getDeliveryStatus() {
    return deliveryStatus;
  }

  /**
   * Sets the delivery status.
   *
   * @param deliveryStatus the new delivery status
   */
  public void setDeliveryStatus(final String deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
  }

  /**
   * Gets the gross amount.
   *
   * @return the gross amount
   */
  public double getGrossAmount() {
    return grossAmount;
  }

  /**
   * Sets the gross amount.
   *
   * @param grossAmount the new gross amount
   */
  public void setGrossAmount(final double grossAmount) {
    this.grossAmount = grossAmount;
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
   * Gets the sales order item.
   *
   * @return the sales order item
   */
  public Set<SalesOrderItem> getSalesOrderItem() {
    return salesOrderItem;
  }

  /**
   * Sets the sales order item.
   *
   * @param salesOrderItem the new sales order item
   */
  public void setSalesOrderItem(final Set<SalesOrderItem> salesOrderItem) {
    this.salesOrderItem = salesOrderItem;
  }

  /**
   * Gets the notes.
   *
   * @return the notes
   */
  public List<Note> getNotes() {
    return notes;
  }

  /**
   * Sets the notes.
   *
   * @param notes the new notes
   */
  public void setNotes(final List<Note> notes) {
    this.notes = notes;
  }

  /**
   * Gets the short text.
   *
   * @return the short text
   */
  public char[] getShortText() {
    return shortText;
  }

  /**
   * Sets the short text.
   *
   * @param shortText the new short text
   */
  public void setShortText(final char[] shortText) {
    this.shortText = shortText;
  }

  /**
   * Gets the long text.
   *
   * @return the long text
   */
  public Character[] getLongText() {
    return longText;
  }

  /**
   * Sets the long text.
   *
   * @param longText the new long text
   */
  public void setLongText(final Character[] longText) {
    this.longText = longText;
  }

  /**
   * Default values.
   */
  @PostPersist
  public void defaultValues() {
    if (creationDate == null) {
      setCreationDate(creationDate);
    }
  }

}
