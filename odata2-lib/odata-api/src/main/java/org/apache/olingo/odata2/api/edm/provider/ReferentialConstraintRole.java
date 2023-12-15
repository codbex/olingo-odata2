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
 * Objects of this Class represent a referential constraint role.
 */
public class ReferentialConstraintRole {

  /** The role. */
  private String role;
  
  /** The property refs. */
  private List<PropertyRef> propertyRefs;
  
  /** The annotation attributes. */
  private List<AnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<AnnotationElement> annotationElements;

  /**
   * Gets the role.
   *
   * @return <b>String</b> role of this {@link ReferentialConstraintRole}
   */
  public String getRole() {
    return role;
  }

  /**
   * Gets the property refs.
   *
   * @return List<{@link PropertyRef}> for this {@link ReferentialConstraintRole}
   */
  public List<PropertyRef> getPropertyRefs() {
    return propertyRefs;
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
   * Sets the role of this {@link ReferentialConstraintRole}.
   *
   * @param role the role
   * @return {@link ReferentialConstraintRole} for method chaining
   */
  public ReferentialConstraintRole setRole(final String role) {
    this.role = role;
    return this;
  }

  /**
   * Sets the {@link PropertyRef}s of this {@link ReferentialConstraintRole}.
   *
   * @param propertyRef the property ref
   * @return {@link ReferentialConstraintRole} for method chaining
   */
  public ReferentialConstraintRole setPropertyRefs(final List<PropertyRef> propertyRef) {
    propertyRefs = propertyRef;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationAttribute} for this {@link ReferentialConstraintRole}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link ReferentialConstraintRole} for method chaining
   */
  public ReferentialConstraintRole setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationElement} for this {@link ReferentialConstraintRole}.
   *
   * @param annotationElements the annotation elements
   * @return {@link ReferentialConstraintRole} for method chaining
   */
  public ReferentialConstraintRole setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
