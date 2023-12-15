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
 * Objects of this class represent an association set.
 */
public class AssociationSet {

  /** The name. */
  private String name;
  
  /** The association. */
  private FullQualifiedName association;
  
  /** The end 1. */
  private AssociationSetEnd end1;
  
  /** The end 2. */
  private AssociationSetEnd end2;
  
  /** The documentation. */
  private Documentation documentation;
  
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
   * Gets the association.
   *
   * @return {@link FullQualifiedName} Association of this {@link AssociationSet} (namespace and name)
   */
  public FullQualifiedName getAssociation() {
    return association;
  }

  /**
   * Gets the end 1.
   *
   * @return {@link AssociationEnd} end1
   */
  public AssociationSetEnd getEnd1() {
    return end1;
  }

  /**
   * Gets the end 2.
   *
   * @return {@link AssociationEnd} end2
   */
  public AssociationSetEnd getEnd2() {
    return end2;
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
   * Sets the name for this {@link AssociationSet}.
   *
   * @param name the name
   * @return {@link AssociationSet} for method chaining
   */
  public AssociationSet setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the {@link FullQualifiedName} association for this {@link AssociationSet}.
   *
   * @param association the association
   * @return {@link AssociationSet} for method chaining
   */
  public AssociationSet setAssociation(final FullQualifiedName association) {
    this.association = association;
    return this;
  }

  /**
   * Sets the first {@link AssociationSetEnd} for this {@link AssociationSet}.
   *
   * @param end1 the end 1
   * @return {@link AssociationSet} for method chaining
   */
  public AssociationSet setEnd1(final AssociationSetEnd end1) {
    this.end1 = end1;
    return this;
  }

  /**
   * Sets the second {@link AssociationSetEnd} for this {@link AssociationSet}.
   *
   * @param end2 the end 2
   * @return {@link AssociationSet} for method chaining
   */
  public AssociationSet setEnd2(final AssociationSetEnd end2) {
    this.end2 = end2;
    return this;
  }

  /**
   * Sets the {@link Documentation} for this {@link AssociationSet}.
   *
   * @param documentation the documentation
   * @return {@link AssociationSet} for method chaining
   */
  public AssociationSet setDocumentation(final Documentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link AssociationSet}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link AssociationSet} for method chaining
   */
  public AssociationSet setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link AssociationSet}.
   *
   * @param annotationElements the annotation elements
   * @return {@link AssociationSet} for method chaining
   */
  public AssociationSet setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
