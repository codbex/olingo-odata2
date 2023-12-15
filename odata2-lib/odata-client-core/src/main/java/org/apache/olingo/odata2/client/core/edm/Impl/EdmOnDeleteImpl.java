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

import org.apache.olingo.odata2.api.edm.EdmAction;
import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;
import org.apache.olingo.odata2.api.edm.EdmAnnotationElement;
import org.apache.olingo.odata2.client.api.edm.EdmDocumentation;
import org.apache.olingo.odata2.client.core.edm.EdmOnDelete;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent an OnDelete Action.
 */
public class EdmOnDeleteImpl implements EdmOnDelete{

  /** The action. */
  private EdmAction action;
  
  /** The documentation. */
  private EdmDocumentation documentation;
  
  /** The annotation attributes. */
  private List<EdmAnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<EdmAnnotationElement> annotationElements;

  /**
   * Gets the action.
   *
   * @return {@link EdmAction} action
   */
  public EdmAction getAction() {
    return action;
  }

  /**
   * Gets the documentation.
   *
   * @return {@link Documentation} documentation
   */
  public EdmDocumentation getDocumentation() {
    return documentation;
  }

  /**
   * Gets the annotation attributes.
   *
   * @return List of {@link AnnotationAttribute} annotation attributes
   */
  public List<EdmAnnotationAttribute> getAnnotationAttributes() {
    return annotationAttributes;
  }

  /**
   * Gets the annotation elements.
   *
   * @return List of {@link AnnotationElement} annotation elements
   */
  public List<EdmAnnotationElement> getAnnotationElements() {
    return annotationElements;
  }

  /**
   * Sets the {@link EdmAction} for this {@link EdmOnDeleteImpl}.
   *
   * @param action the action
   * @return {@link EdmOnDeleteImpl} for method chaining
   */
  public EdmOnDeleteImpl setAction(final EdmAction action) {
    this.action = action;
    return this;
  }

  /**
   * Sets the {@link Documentation} for this {@link EdmOnDeleteImpl}.
   *
   * @param documentation the documentation
   * @return {@link EdmOnDeleteImpl} for method chaining
   */
  public EdmOnDeleteImpl setDocumentation(final EdmDocumentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationAttribute} for this {@link EdmOnDeleteImpl}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link EdmOnDeleteImpl} for method chaining
   */
  public EdmOnDeleteImpl setAnnotationAttributes(final List<EdmAnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationElement} for this {@link EdmOnDeleteImpl}.
   *
   * @param annotationElements the annotation elements
   * @return {@link EdmOnDeleteImpl} for method chaining
   */
  public EdmOnDeleteImpl setAnnotationElements(final List<EdmAnnotationElement> annotationElements) {
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
      return String.format(action.name());
  }
}
