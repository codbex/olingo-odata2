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
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent function import parameters.
 */
public class FunctionImportParameter {

  /** The name. */
  private String name;
  
  /** The mode. */
  private String mode;
  
  /** The type. */
  private EdmSimpleTypeKind type;
  
  /** The facets. */
  private EdmFacets facets;
  
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
   * @return <b>String</b> name of the parameter
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the mode.
   *
   * @return <b>String</b> mode of this parameter
   */
  public String getMode() {
    return mode;
  }

  /**
   * Gets the type.
   *
   * @return {@link EdmSimpleTypeKind} of this parameter
   */
  public EdmSimpleTypeKind getType() {
    return type;
  }

  /**
   * Gets the facets.
   *
   * @return {@link EdmFacets} of this parameter
   */
  public EdmFacets getFacets() {
    return facets;
  }

  /**
   * Gets the mapping.
   *
   * @return {@link Mapping} of this parameter
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
   * Sets the name of this {@link FunctionImportParameter}.
   *
   * @param name the name
   * @return {@link FunctionImportParameter} for method chaining
   */
  public FunctionImportParameter setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the mode of this {@link FunctionImportParameter}.
   *
   * @param mode the mode
   * @return {@link FunctionImportParameter} for method chaining
   */
  public FunctionImportParameter setMode(final String mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Sets the {@link EdmSimpleTypeKind} of this {@link FunctionImportParameter}.
   *
   * @param type the type
   * @return {@link FunctionImportParameter} for method chaining
   */
  public FunctionImportParameter setType(final EdmSimpleTypeKind type) {
    this.type = type;
    return this;
  }

  /**
   * Sets the {@link EdmFacets} of this {@link FunctionImportParameter}.
   *
   * @param facets the facets
   * @return {@link FunctionImportParameter} for method chaining
   */
  public FunctionImportParameter setFacets(final EdmFacets facets) {
    this.facets = facets;
    return this;
  }

  /**
   * Sets the {@link Mapping} of this {@link FunctionImportParameter}.
   *
   * @param mapping the mapping
   * @return {@link FunctionImportParameter} for method chaining
   */
  public FunctionImportParameter setMapping(final Mapping mapping) {
    this.mapping = mapping;
    return this;
  }

  /**
   * Sets the {@link Documentation} of this {@link FunctionImportParameter}.
   *
   * @param documentation the documentation
   * @return {@link FunctionImportParameter} for method chaining
   */
  public FunctionImportParameter setDocumentation(final Documentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link FunctionImportParameter}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link FunctionImportParameter} for method chaining
   */
  public FunctionImportParameter setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link FunctionImportParameter}.
   *
   * @param annotationElements the annotation elements
   * @return {@link FunctionImportParameter} for method chaining
   */
  public FunctionImportParameter setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
