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
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class Category.
 */
@Entity
@Table(name = "T_CATEGORY")
@IdClass(value = CategoryKey.class)
public class Category {

  /** The code. */
  @Id
  @Column(name = "CODE")
  private char code[] = new char[2];

  /** The id. */
  @Id
  private long id;

  /** The description. */
  @Column(name = "DESC")
  private String description;

  /** The materials. */
  @OneToMany(mappedBy = "category")
  private List<Material> materials = new ArrayList<Material>();

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
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final long id) {
    this.id = id;
  }

  /**
   * Gets the code.
   *
   * @return the code
   */
  public char[] getCode() {
    return code;
  }

  /**
   * Sets the code.
   *
   * @param code the new code
   */
  public void setCode(final char[] code) {
    this.code = code;
  }

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

}
