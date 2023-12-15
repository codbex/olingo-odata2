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
package org.apache.olingo.odata2.api.edm.provider;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent the data service. They contain all schemas of the EDM as well as the
 * dataServiceVersion
 * 
 */
public class DataServices {

  /** The schemas. */
  private List<Schema> schemas;
  
  /** The data service version. */
  private String dataServiceVersion;
  
  /** The custom edmx version. */
  private String customEdmxVersion;
  
  /** The annotation elements. */
  private List<AnnotationElement> annotationElements;

  /**
   * Sets the schemas for this {@link DataServices}.
   *
   * @param schemas the schemas
   * @return {@link DataServices} for method chaining
   */
  public DataServices setSchemas(final List<Schema> schemas) {
    this.schemas = schemas;
    return this;
  }

  /**
   * Sets the data service version for this {@link DataServices}.
   *
   * @param dataServiceVersion the data service version
   * @return {@link DataServices} for method chaining
   */
  public DataServices setDataServiceVersion(final String dataServiceVersion) {
    this.dataServiceVersion = dataServiceVersion;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link DataServices}.
   *
   * @param annotationElements the annotation elements
   * @return {@link ComplexType} for method chaining
   */
  public DataServices setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }

  /**
   * Sets a custom edmx version which is used in the metadata document.
   *
   * @param customEdmxVersion the custom edmx version
   * @return {@link ComplexType} for method chaining
   */
  public DataServices setCustomEdmxVersion(String customEdmxVersion) {
    this.customEdmxVersion = customEdmxVersion;
    return this;
  }

  /**
   * Gets the annotation elements.
   *
   * @return collection of {@link AnnotationElement} annotation elements
   */
  public List<AnnotationElement> getAnnotationElements() {
    return annotationElements;
  }

  /**
   * Gets the schemas.
   *
   * @return List<{@link Schema}>
   */
  public List<Schema> getSchemas() {
    return schemas;
  }

  /**
   * Gets the data service version.
   *
   * @return <b>String</b> data service version
   */
  public String getDataServiceVersion() {
    return dataServiceVersion;
  }

  /**
   * Gets the custom edmx version.
   *
   * @return <b>String</b> custom edmx version
   */
  public String getCustomEdmxVersion() {
    return customEdmxVersion;
  }
}
