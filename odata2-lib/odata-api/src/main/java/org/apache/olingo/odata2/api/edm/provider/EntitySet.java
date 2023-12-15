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
 * Objects of this class represent an entity set.
 */
public class EntitySet {

  /** The name. */
  private String name;
  
  /** The entity type. */
  private FullQualifiedName entityType;
  
  /** The mapping. */
  private Mapping mapping;
  
  /** The documentation. */
  private Documentation documentation;
  
  /** The annotation attributes. */
  private List<AnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<AnnotationElement> annotationElements;

  /**
   * Gets the name.
   *
   * @return <b>String> name of this entity set
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the entity type.
   *
   * @return {@link FullQualifiedName} of the entity type of this entity set
   */
  public FullQualifiedName getEntityType() {
    return entityType;
  }

  /**
   * Gets the mapping.
   *
   * @return {@link Mapping} for this type
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
   * Sets the name of this {@link EntitySet}.
   *
   * @param name the name
   * @return {@link EntitySet} for method chaining
   */
  public EntitySet setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the {@link FullQualifiedName} of the {@link EntityType} of this {@link EntitySet}.
   *
   * @param entityType the entity type
   * @return {@link EntitySet} for method chaining
   */
  public EntitySet setEntityType(final FullQualifiedName entityType) {
    this.entityType = entityType;
    return this;
  }

  /**
   * Sets the {@link Mapping}.
   *
   * @param mapping the mapping
   * @return {@link EntitySet} for method chaining
   */
  public EntitySet setMapping(final Mapping mapping) {
    this.mapping = mapping;
    return this;
  }

  /**
   * Sets the {@link Documentation}.
   *
   * @param documentation the documentation
   * @return {@link EntitySet} for method chaining
   */
  public EntitySet setDocumentation(final Documentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link EntitySet}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link EntitySet} for method chaining
   */
  public EntitySet setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link EntitySet}.
   *
   * @param annotationElements the annotation elements
   * @return {@link EntitySet} for method chaining
   */
  public EntitySet setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
