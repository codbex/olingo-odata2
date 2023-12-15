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
 * Objects of this class represent an entity container.
 */
public class EntityContainerInfo {

  /** The name. */
  private String name;
  
  /** The extendz. */
  private String extendz;
  
  /** The is default entity container. */
  private boolean isDefaultEntityContainer;
  
  /** The annotation attributes. */
  private List<AnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<AnnotationElement> annotationElements;

  /**
   * Gets the name.
   *
   * @return <b>String</b> name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the extendz.
   *
   * @return <b>String</b> name of the container which is extended by this container
   */
  public String getExtendz() {
    return extendz;
  }

  /**
   * Checks if is default entity container.
   *
   * @return <b>boolean</b> if this container is the default container
   */
  public boolean isDefaultEntityContainer() {
    return isDefaultEntityContainer;
  }

  /**
   * Sets the name of this {@link EntityContainerInfo}.
   *
   * @param name the name
   * @return {@link EntityContainerInfo} for method chaining
   */
  public EntityContainerInfo setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the entity container which is the parent of this {@link EntityContainerInfo}.
   *
   * @param extendz the extendz
   * @return {@link EntityContainerInfo} for method chaining
   */
  public EntityContainerInfo setExtendz(final String extendz) {
    this.extendz = extendz;
    return this;
  }

  /**
   * Sets if this is the default {@link EntityContainerInfo}.
   *
   * @param isDefaultEntityContainer the is default entity container
   * @return {@link EntityContainerInfo} for method chaining
   */
  public EntityContainerInfo setDefaultEntityContainer(final boolean isDefaultEntityContainer) {
    this.isDefaultEntityContainer = isDefaultEntityContainer;
    return this;
  }

  /**
   * Gets the annotation attributes.
   *
   * @return collection of {@link AnnotationAttribute} annotation attributes
   */
  public List<AnnotationAttribute> getAnnotationAttributes() {
    return annotationAttributes;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link EntityContainer}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link EntityContainer} for method chaining
   */
  public EntityContainerInfo setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
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
   * Sets the collection of {@link AnnotationElement} for this {@link EntityContainer}.
   *
   * @param annotationElements the annotation elements
   * @return {@link EntityContainer} for method chaining
   */
  public EntityContainerInfo setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }

}
