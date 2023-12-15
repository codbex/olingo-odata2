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

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent an association end.
 */
public class AssociationEnd {

  /** The type. */
  private FullQualifiedName type;
  
  /** The role. */
  private String role;
  
  /** The multiplicity. */
  private EdmMultiplicity multiplicity;
  
  /** The on delete. */
  private OnDelete onDelete;
  
  /** The documentation. */
  private Documentation documentation;
  
  /** The annotation attributes. */
  private List<AnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<AnnotationElement> annotationElements;

  /**
   * Gets the type.
   *
   * @return {@link FullQualifiedName} full qualified name (namespace and name)
   */
  public FullQualifiedName getType() {
    return type;
  }

  /**
   * Gets the role.
   *
   * @return <b>String</b> role
   */
  public String getRole() {
    return role;
  }

  /**
   * Gets the multiplicity.
   *
   * @return {@link EdmMultiplicity} multiplicity of this end
   */
  public EdmMultiplicity getMultiplicity() {
    return multiplicity;
  }

  /**
   * Gets the on delete.
   *
   * @return {@link OnDelete} on delete
   */
  public OnDelete getOnDelete() {
    return onDelete;
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
   * Sets the {@link FullQualifiedName} for this {@link AssociationEnd}.
   *
   * @param type the type
   * @return {@link AssociationEnd} for method chaining
   */
  public AssociationEnd setType(final FullQualifiedName type) {
    this.type = type;
    return this;
  }

  /**
   * Sets the role for this {@link AssociationEnd}.
   *
   * @param role the role
   * @return {@link AssociationEnd} for method chaining
   */
  public AssociationEnd setRole(final String role) {
    this.role = role;
    return this;
  }

  /**
   * Sets the {@link EdmMultiplicity} for this {@link AssociationEnd}.
   *
   * @param multiplicity the multiplicity
   * @return {@link AssociationEnd} for method chaining
   */
  public AssociationEnd setMultiplicity(final EdmMultiplicity multiplicity) {
    this.multiplicity = multiplicity;
    return this;
  }

  /**
   * Sets {@link OnDelete} for this {@link AssociationEnd}.
   *
   * @param onDelete the on delete
   * @return {@link AssociationEnd} for method chaining
   */
  public AssociationEnd setOnDelete(final OnDelete onDelete) {
    this.onDelete = onDelete;
    return this;
  }

  /**
   * Sets the {@link Documentation} for this {@link AssociationEnd}.
   *
   * @param documentation the documentation
   * @return {@link AssociationEnd} for method chaining
   */
  public AssociationEnd setDocumentation(final Documentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link AssociationEnd}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link AssociationEnd} for method chaining
   */
  public AssociationEnd setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link AssociationEnd}.
   *
   * @param annotationElements the annotation elements
   * @return {@link AssociationEnd} for method chaining
   */
  public AssociationEnd setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
