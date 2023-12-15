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

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Material.
 */
@Entity
@Table(name = "T_MATERIAL")
public class Material {

  /**
   * Instantiates a new material.
   */
  public Material() {}

  /**
   * Instantiates a new material.
   *
   * @param materialName the material name
   * @param typeCode the type code
   * @param price the price
   * @param measurementUnit the measurement unit
   */
  public Material(final String materialName, final char[] typeCode, final double price,
      final String measurementUnit) {
    super();
    this.materialName = materialName;
    this.price = price;
    this.measurementUnit = measurementUnit;
  }

  /** The material id. */
  @Id
  @Column(name = "MATERIAL_ID")
  private long materialId;

  /** The material name. */
  @Column(name = "MATERIAL_NAME")
  private String materialName;

  /** The price. */
  @Column(name = "PRICE")
  private Double price;

  /** The measurement unit. */
  @Column(name = "MEASUREMENT_UNIT")
  private String measurementUnit;
  
  /** The material image. */
  @Lob
  @Column(name = "MIMAGE")
  @Convert(converter = org.apache.olingo.odata2.jpa.processor.ref.converter.BlobToByteConverter.class)
  private Blob materialImage;

  /**
   * Gets the material image.
   *
   * @return the material image
   */
  public Blob getMaterialImage() {
    return materialImage;
  }

  /**
   * Sets the material image.
   *
   * @param materialImage the new material image
   */
  public void setMaterialImage(final Blob materialImage) {
    this.materialImage = materialImage;
  }

  /** The stores. */
  @ManyToMany
  private List<Store> stores = new ArrayList<Store>();

  /** The category. */
  @ManyToOne
  @JoinColumns({ @JoinColumn(name = "TYPE_CODE", referencedColumnName = "CODE"),
      @JoinColumn(name = "CAT_ID", referencedColumnName = "ID") })
  private Category category;

  /**
   * Gets the material id.
   *
   * @return the material id
   */
  public long getMaterialId() {
    return materialId;
  }

  /**
   * Sets the material id.
   *
   * @param materialId the new material id
   */
  public void setMaterialId(final long materialId) {
    this.materialId = materialId;
  }

  /**
   * Gets the category.
   *
   * @return the category
   */
  public Category getCategory() {
    return category;
  }

  /**
   * Sets the category.
   *
   * @param category the new category
   */
  public void setCategory(final Category category) {
    this.category = category;
  }

  /**
   * Gets the material name.
   *
   * @return the material name
   */
  public String getMaterialName() {
    return materialName;
  }

  /**
   * Sets the material name.
   *
   * @param materialName the new material name
   */
  public void setMaterialName(final String materialName) {
    this.materialName = materialName;
  }

  /**
   * Gets the price.
   *
   * @return the price
   */
  public Double getPrice() {
    return price;
  }

  /**
   * Sets the price.
   *
   * @param price the new price
   */
  public void setPrice(final Double price) {
    this.price = price;
  }

  /**
   * Gets the measurement unit.
   *
   * @return the measurement unit
   */
  public String getMeasurementUnit() {
    return measurementUnit;
  }

  /**
   * Sets the measurement unit.
   *
   * @param measurementUnit the new measurement unit
   */
  public void setMeasurementUnit(final String measurementUnit) {
    this.measurementUnit = measurementUnit;
  }

  /**
   * Gets the stores.
   *
   * @return the stores
   */
  public List<Store> getStores() {
    return stores;
  }

  /**
   * Sets the stores.
   *
   * @param stores the new stores
   */
  public void setStores(final List<Store> stores) {
    this.stores = stores;
    Iterator<Store> itr = stores.iterator();
    while (itr.hasNext()) {
      itr.next().getMaterials().add(this);
    }
  }
}
