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

import org.apache.olingo.odata2.api.edm.EdmFacets;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent a property of an entity type.
 */
public abstract class Property {

  /** The name. */
  private String name;
  
  /** The facets. */
  private EdmFacets facets;
  
  /** The customizable feed mappings. */
  private CustomizableFeedMappings customizableFeedMappings;
  
  /** The mime type. */
  private String mimeType;
  
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
   * @return <b>String</b> name of this property
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the facets.
   *
   * @return {@link EdmFacets} of this property
   */
  public EdmFacets getFacets() {
    return facets;
  }

  /**
   * Gets the customizable feed mappings.
   *
   * @return {@link CustomizableFeedMappings} of this property
   */
  public CustomizableFeedMappings getCustomizableFeedMappings() {
    return customizableFeedMappings;
  }

  /**
   * Gets the mime type.
   *
   * @return <b>String</b> mime type of this property
   */
  public String getMimeType() {
    return mimeType;
  }

  /**
   * Gets the mapping.
   *
   * @return {@link Mapping} of this property
   */
  public Mapping getMapping() {
    return mapping;
  }

  /**
   * Gets the documentation.
   *
   * @return {@link Documentation} of this property
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
   * Sets the name for this {@link Property}.
   *
   * @param name the name
   * @return {@link Property} for method chaining
   */
  public Property setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the {@link Facets} for this {@link Property}.
   *
   * @param facets the facets
   * @return {@link Property} for method chaining
   */
  public Property setFacets(final EdmFacets facets) {
    this.facets = facets;
    return this;
  }

  /**
   * Sets the {@link CustomizableFeedMappings} for this {@link Property}.
   *
   * @param customizableFeedMappings the customizable feed mappings
   * @return {@link Property} for method chaining
   */
  public Property setCustomizableFeedMappings(final CustomizableFeedMappings customizableFeedMappings) {
    this.customizableFeedMappings = customizableFeedMappings;
    return this;
  }

  /**
   * Sets the mime type for this {@link Property}.
   *
   * @param mimeType the mime type
   * @return {@link Property} for method chaining
   */
  public Property setMimeType(final String mimeType) {
    this.mimeType = mimeType;
    return this;
  }

  /**
   * Sets the {@link Mapping} for this {@link Property}.
   *
   * @param mapping the mapping
   * @return {@link Property} for method chaining
   */
  public Property setMapping(final Mapping mapping) {
    this.mapping = mapping;
    return this;
  }

  /**
   * Sets the {@link Documentation} for this {@link Property}.
   *
   * @param documentation the documentation
   * @return {@link Property} for method chaining
   */
  public Property setDocumentation(final Documentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationAttribute} for this {@link Property}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link Property} for method chaining
   */
  public Property setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationElement} for this {@link Property}.
   *
   * @param annotationElements the annotation elements
   * @return {@link Property} for method chaining
   */
  public Property setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
