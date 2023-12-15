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
 * Objects of this class represent an entity type.
 */
public class EntityType extends ComplexType {

  /** The has stream. */
  private boolean hasStream;
  
  /** The customizable feed mappings. */
  private CustomizableFeedMappings customizableFeedMappings;
  
  /** The key. */
  private Key key;
  
  /** The navigation properties. */
  private List<NavigationProperty> navigationProperties;

  /**
   * Checks if is checks for stream.
   *
   * @return <b>boolean</b> if this EntityType is a media resource
   */
  public boolean isHasStream() {
    return hasStream;
  }

  /**
   * Gets the customizable feed mappings.
   *
   * @return {@link CustomizableFeedMappings} of this entity type
   */
  public CustomizableFeedMappings getCustomizableFeedMappings() {
    return customizableFeedMappings;
  }

  /**
   * Gets the key.
   *
   * @return {@link Key} of this entity type
   */
  public Key getKey() {
    return key;
  }

  /**
   * Gets the navigation properties.
   *
   * @return List<{@link NavigationProperty}> of this entity type
   */
  public List<NavigationProperty> getNavigationProperties() {
    return navigationProperties;
  }

  /**
   * Sets if this {@link EntityType} is a media resource.
   *
   * @param hasStream the has stream
   * @return {@link EntityType} for method chaining,
   */
  public EntityType setHasStream(final boolean hasStream) {
    this.hasStream = hasStream;
    return this;
  }

  /**
   * Sets the {@link CustomizableFeedMappings} for this {@link EntityType}.
   *
   * @param customizableFeedMappings the customizable feed mappings
   * @return {@link EntityType} for method chaining
   */
  public EntityType setCustomizableFeedMappings(final CustomizableFeedMappings customizableFeedMappings) {
    this.customizableFeedMappings = customizableFeedMappings;
    return this;
  }

  /**
   * Sets the {@link Key} for this {@link EntityType}.
   *
   * @param key the key
   * @return {@link EntityType} for method chaining
   */
  public EntityType setKey(final Key key) {
    this.key = key;
    return this;
  }

  /**
   * Sets the {@link NavigationProperty}s for this {@link EntityType}.
   *
   * @param navigationProperties the navigation properties
   * @return {@link EntityType} for method chaining
   */
  public EntityType setNavigationProperties(final List<NavigationProperty> navigationProperties) {
    this.navigationProperties = navigationProperties;
    return this;
  }

  /**
   * Sets the name.
   *
   * @param name the name
   * @return {@link EntityType} for method chaining
   */
  @Override
  public EntityType setName(final String name) {
    super.setName(name);
    return this;
  }

  /**
   * Sets the base type.
   *
   * @param baseType the base type
   * @return {@link EntityType} for method chaining
   */
  @Override
  public EntityType setBaseType(final FullQualifiedName baseType) {
    super.setBaseType(baseType);
    return this;
  }

  /**
   * Sets the abstract.
   *
   * @param isAbstract the is abstract
   * @return {@link EntityType} for method chaining
   */
  @Override
  public EntityType setAbstract(final boolean isAbstract) {
    super.setAbstract(isAbstract);
    return this;
  }

  /**
   * Sets the properties.
   *
   * @param properties the properties
   * @return {@link EntityType} for method chaining
   */
  @Override
  public EntityType setProperties(final List<Property> properties) {
    super.setProperties(properties);
    return this;
  }

  /**
   * Sets the mapping.
   *
   * @param mapping the mapping
   * @return {@link EntityType} for method chaining
   */
  @Override
  public EntityType setMapping(final Mapping mapping) {
    super.setMapping(mapping);
    return this;
  }

  /**
   * Sets the documentation.
   *
   * @param documentation the documentation
   * @return {@link EntityType} for method chaining
   */
  @Override
  public EntityType setDocumentation(final Documentation documentation) {
    super.setDocumentation(documentation);
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link EntityType}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link EntityType} for method chaining
   */
  @Override
  public EntityType setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    super.setAnnotationAttributes(annotationAttributes);
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link EntityType}.
   *
   * @param annotationElements the annotation elements
   * @return {@link EntityType} for method chaining
   */
  @Override
  public EntityType setAnnotationElements(final List<AnnotationElement> annotationElements) {
    super.setAnnotationElements(annotationElements);
    return this;
  }
}
