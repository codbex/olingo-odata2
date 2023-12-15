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
 * Objects of this class represent an association set end.
 */
public class AssociationSetEnd {

  /** The role. */
  private String role;
  
  /** The entity set. */
  private String entitySet;
  
  /** The documentation. */
  private Documentation documentation;
  
  /** The annotation attributes. */
  private List<AnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<AnnotationElement> annotationElements;

  /**
   * Gets the role.
   *
   * @return <b>String</b> role
   */
  public String getRole() {
    return role;
  }

  /**
   * Gets the entity set.
   *
   * @return <b>String</b> name of the target entity set
   */
  public String getEntitySet() {
    return entitySet;
  }

  /**
   * Gets the documentation.
   *
   * @return {@link Documentation} documentation
   */
  public Documentation getDocumentation() {
    return documentation;
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
   * Sets the role of this {@link AssociationSetEnd}.
   *
   * @param role the role
   * @return {@link AssociationSetEnd} for method chaining
   */
  public AssociationSetEnd setRole(final String role) {
    this.role = role;
    return this;
  }

  /**
   * Sets the target entity set of this {@link AssociationSetEnd}.
   *
   * @param entitySet the entity set
   * @return {@link AssociationSetEnd} for method chaining
   */
  public AssociationSetEnd setEntitySet(final String entitySet) {
    this.entitySet = entitySet;
    return this;
  }

  /**
   * Sets the {@link Documentation} of this {@link AssociationSetEnd}.
   *
   * @param documentation the documentation
   * @return {@link AssociationSetEnd} for method chaining
   */
  public AssociationSetEnd setDocumentation(final Documentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link AssociationSetEnd}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link AssociationSetEnd} for method chaining
   */
  public AssociationSetEnd setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link AssociationSetEnd}.
   *
   * @param annotationElements the annotation elements
   * @return {@link AssociationSetEnd} for method chaining
   */
  public AssociationSetEnd setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
