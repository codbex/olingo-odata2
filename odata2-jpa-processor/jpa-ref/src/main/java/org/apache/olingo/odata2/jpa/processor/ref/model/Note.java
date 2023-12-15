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

import java.sql.Clob;
import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

// TODO: Auto-generated Javadoc
/**
 * The Class Note.
 */
@Entity
@IdClass(value = NoteKey.class)
@Table(name = "T_NOTE")
public class Note {

  /**
   * Instantiates a new note.
   */
  public Note() {}

  /**
   * Instantiates a new note.
   *
   * @param creationTime the creation time
   * @param creationDate the creation date
   * @param createdBy the created by
   */
  public Note(final Calendar creationTime, final Calendar creationDate, final String createdBy) {
    super();
    this.creationTime = creationTime;
    this.creationDate = creationDate;
    this.createdBy = createdBy;

  }

  /** The creation time. */
  @Id
  @Temporal(TemporalType.TIME)
  private Calendar creationTime;

  /** The creation date. */
  @Id
  @Temporal(TemporalType.DATE)
  private Calendar creationDate;

  /** The created by. */
  @Id
  private String createdBy;

  /** The text. */
  @Column
  @Lob
  @Convert(converter = org.apache.olingo.odata2.jpa.processor.ref.converter.ClobToStringConverter.class)
  private Clob text;

  /** The so id. */
  @Column(name = "SO_ID")
  private long soId;

  /** The sales order header. */
  @JoinColumn(name = "SO_ID", referencedColumnName = "SO_ID", insertable = false, updatable = false)
  @ManyToOne
  private SalesOrderHeader salesOrderHeader;

  /**
   * Gets the creation time.
   *
   * @return the creation time
   */
  public Calendar getCreationTime() {
    return creationTime;
  }

  /**
   * Sets the creation time.
   *
   * @param creationTime the new creation time
   */
  public void setCreationTime(final Calendar creationTime) {
    this.creationTime = creationTime;
  }

  /**
   * Gets the creation date.
   *
   * @return the creation date
   */
  public Calendar getCreationDate() {
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
   * Gets the created by.
   *
   * @return the created by
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Sets the created by.
   *
   * @param createdBy the new created by
   */
  public void setCreatedBy(final String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  public Clob getText() {
    return text;
  }

  /**
   * Sets the text.
   *
   * @param text the new text
   */
  public void setText(final Clob text) {
    this.text = text;
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
    this.salesOrderHeader.getNotes().add(this);
  }
}
