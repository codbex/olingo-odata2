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

import org.apache.olingo.odata2.api.edm.FullQualifiedName;

// TODO: Auto-generated Javadoc
/**
 * Objects of this Class represent a navigation property.
 */
public class NavigationProperty {

  /** The name. */
  private String name;
  
  /** The relationship. */
  private FullQualifiedName relationship;
  
  /** The from role. */
  private String fromRole;
  
  /** The to role. */
  private String toRole;
  
  /** The documentation. */
  private Documentation documentation;
  
  /** The mapping. */
  private Mapping mapping;
  
  /** The annotation attributes. */
  private List<AnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<AnnotationElement> annotationElements;

  /**
   * Gets the name.
   *
   * @return <b>String</b> name of this navigation property
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the relationship.
   *
   * @return {@link FullQualifiedName} of the relationship
   */
  public FullQualifiedName getRelationship() {
    return relationship;
  }

  /**
   * Gets the from role.
   *
   * @return <b>String</b> name of the role this navigation is comming from
   */
  public String getFromRole() {
    return fromRole;
  }

  /**
   * Gets the to role.
   *
   * @return <b>String</b> name of the role this navigation is going to
   */
  public String getToRole() {
    return toRole;
  }

  /**
   * Gets the mapping.
   *
   * @return {@link Mapping} of this navigation property
   */
  public Mapping getMapping() {
    return mapping;
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
   * Sets the name of this {@link NavigationProperty}.
   *
   * @param name the name
   * @return {@link NavigationProperty} for method chaining
   */
  public NavigationProperty setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the {@link FullQualifiedName} for the relationship of this {@link NavigationProperty}.
   *
   * @param relationship the relationship
   * @return {@link NavigationProperty} for method chaining
   */
  public NavigationProperty setRelationship(final FullQualifiedName relationship) {
    this.relationship = relationship;
    return this;
  }

  /**
   * Sets the role this {@link NavigationProperty} is comming from.
   *
   * @param fromRole the from role
   * @return {@link NavigationProperty} for method chaining
   */
  public NavigationProperty setFromRole(final String fromRole) {
    this.fromRole = fromRole;
    return this;
  }

  /**
   * Sets the role this {@link NavigationProperty} is going to.
   *
   * @param toRole the to role
   * @return {@link NavigationProperty} for method chaining
   */
  public NavigationProperty setToRole(final String toRole) {
    this.toRole = toRole;
    return this;
  }

  /**
   * Sets the {@link Mapping} for this {@link NavigationProperty}.
   *
   * @param mapping the mapping
   * @return {@link NavigationProperty} for method chaining
   */
  public NavigationProperty setMapping(final Mapping mapping) {
    this.mapping = mapping;
    return this;
  }

  /**
   * Sets the {@link Documentation} for this {@link NavigationProperty}.
   *
   * @param documentation the documentation
   * @return {@link NavigationProperty} for method chaining
   */
  public NavigationProperty setDocumentation(final Documentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationAttribute} for this {@link NavigationProperty}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link NavigationProperty} for method chaining
   */
  public NavigationProperty setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationElement} for this {@link NavigationProperty}.
   *
   * @param annotationElements the annotation elements
   * @return {@link NavigationProperty} for method chaining
   */
  public NavigationProperty setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
