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
 * Objects of this class represent documentation.
 */
public class Documentation {

  /** The summary. */
  private String summary;
  
  /** The long description. */
  private String longDescription;
  
  /** The annotation attributes. */
  private List<AnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<AnnotationElement> annotationElements;

  /**
   * Gets the summary.
   *
   * @return <b>String</b> summary
   */
  public String getSummary() {
    return summary;
  }

  /**
   * Gets the long description.
   *
   * @return <b>String</b> the long description
   */
  public String getLongDescription() {
    return longDescription;
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
   * Gets the annotation elements.
   *
   * @return collection of {@link AnnotationElement} annotation elements
   */
  public List<AnnotationElement> getAnnotationElements() {
    return annotationElements;
  }

  /**
   * Sets the summary for this {@link Documentation}.
   *
   * @param summary the summary
   * @return {@link Documentation} for method chaining
   */
  public Documentation setSummary(final String summary) {
    this.summary = summary;
    return this;
  }

  /**
   * Sets the long description for this {@link Documentation}.
   *
   * @param longDescription the long description
   * @return {@link Documentation} for method chaining
   */
  public Documentation setLongDescription(final String longDescription) {
    this.longDescription = longDescription;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link Documentation}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link Documentation} for method chaining
   */
  public Documentation setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link Documentation}.
   *
   * @param annotationElements the annotation elements
   * @return {@link Documentation} for method chaining
   */
  public Documentation setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
