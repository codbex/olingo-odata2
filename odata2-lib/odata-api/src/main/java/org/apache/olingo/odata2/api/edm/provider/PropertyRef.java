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
 * Objects of this class represent a reference to a property via its name.
 */
public class PropertyRef {

  /** The name. */
  private String name;
  
  /** The annotation attributes. */
  private List<AnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<AnnotationElement> annotationElements;

  /**
   * Gets the name.
   *
   * @return <b>String</b> name of the {@link Property} this {@link PropertyRef} is referencing to
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the annotation attributes.
   *
   * @return List of {@link AnnotationAttribute} annotation attributes
   */
  public List<AnnotationAttribute> getAnnotationAttributes() {
    return annotationAttributes;
  }

  /**
   * Gets the annotation elements.
   *
   * @return List of {@link AnnotationElement} annotation elements
   */
  public List<AnnotationElement> getAnnotationElements() {
    return annotationElements;
  }

  /**
   * Sets the name of the {@link Property} this {@link PropertyRef} is pointing to.
   *
   * @param name the name
   * @return {@link PropertyRef} for method chaining
   */
  public PropertyRef setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationAttribute} for this {@link PropertyRef}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link PropertyRef} for method chaining
   */
  public PropertyRef setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationElement} for this {@link PropertyRef}.
   *
   * @param annotationElements the annotation elements
   * @return {@link PropertyRef} for method chaining
   */
  public PropertyRef setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
