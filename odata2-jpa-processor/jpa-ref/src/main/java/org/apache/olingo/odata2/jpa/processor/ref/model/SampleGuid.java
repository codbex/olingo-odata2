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

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// TODO: Auto-generated Javadoc
/**
 * The Class SampleGuid.
 */
@Entity
@Table(name = "T_SAMPLEGUID")
public class SampleGuid {

  /**
   * Instantiates a new sample guid.
   */
  public SampleGuid() {}
  
  /**
   * Instantiates a new sample guid.
   *
   * @param id the id
   * @param name the name
   */
  public SampleGuid(final int id, final String name) {
    super();
    this.id = id;
    this.name = name;
  }
  
  /** The id. */
  @Column
  private int id;
  
  /** The name. */
  @Column
  private String name;
  
  /** The External recommendation UUID. */
  @Id
  @Convert(converter=org.apache.olingo.odata2.jpa.processor.ref.converter.UUIDConverter.class)
  @GeneratedValue(generator="reco-UUID")
  @Column(name = "ExternalRecommendationUUID")
  private UUID ExternalRecommendationUUID;

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
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
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
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the external recommendation UUID.
   *
   * @return the externalRecommendationUUID
   */
  public UUID getExternalRecommendationUUID() {
    return ExternalRecommendationUUID;
  }

  /**
   * Sets the external recommendation UUID.
   *
   * @param externalRecommendationUUID the externalRecommendationUUID to set
   */
  public void setExternalRecommendationUUID(UUID externalRecommendationUUID) {
    ExternalRecommendationUUID = externalRecommendationUUID;
  }
  
}
