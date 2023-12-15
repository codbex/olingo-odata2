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
package org.apache.olingo.odata2.client.core.edm.Impl;

import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;
import org.apache.olingo.odata2.api.edm.EdmAnnotationElement;
import org.apache.olingo.odata2.client.api.edm.EdmDocumentation;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent documentation.
 */
public class EdmDocumentationImpl implements EdmDocumentation{

  /** The summary. */
  private String summary;
  
  /** The long description. */
  private String longDescription;
  
  /** The annotation attributes. */
  private List<EdmAnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<EdmAnnotationElement> annotationElements;

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
   * @return collection of {@link EdmAnnotationAttributeImpl} annotation attributes
   */
  public List<EdmAnnotationAttribute> getAnnotationAttributes() {
    return annotationAttributes;
  }

  /**
   * Gets the annotation elements.
   *
   * @return collection of {@link AnnotationElement} annotation elements
   */
  public List<EdmAnnotationElement> getAnnotationElements() {
    return annotationElements;
  }

  /**
   * Sets the summary for this {@link EdmDocumentationImpl}.
   *
   * @param summary the summary
   * @return {@link EdmDocumentationImpl} for method chaining
   */
  public EdmDocumentationImpl setSummary(final String summary) {
    this.summary = summary;
    return this;
  }

  /**
   * Sets the long description for this {@link EdmDocumentationImpl}.
   *
   * @param longDescription the long description
   * @return {@link EdmDocumentationImpl} for method chaining
   */
  public EdmDocumentationImpl setLongDescription(final String longDescription) {
    this.longDescription = longDescription;
    return this;
  }

  /**
   * Sets the collection of {@link EdmAnnotationAttributeImpl} for this {@link EdmDocumentationImpl}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link EdmDocumentationImpl} for method chaining
   */
  public EdmDocumentationImpl setAnnotationAttributes(final List<EdmAnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link EdmDocumentationImpl}.
   *
   * @param annotationElements the annotation elements
   * @return {@link EdmDocumentationImpl} for method chaining
   */
  public EdmDocumentationImpl setAnnotationElements(final List<EdmAnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format(summary);
  }
}
