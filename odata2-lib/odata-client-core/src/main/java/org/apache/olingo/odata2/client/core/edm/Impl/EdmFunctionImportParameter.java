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

import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.client.api.edm.EdmDocumentation;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent function import parameters.
 */
public class EdmFunctionImportParameter {

  /** The name. */
  private String name;
  
  /** The mode. */
  private String mode;
  
  /** The type. */
  private EdmSimpleTypeKind type;
  
  /** The facets. */
  private EdmFacets facets;
  
  /** The mapping. */
  private EdmMapping mapping;
  
  /** The documentation. */
  private EdmDocumentation documentation;
  
  /** The annotations. */
  private EdmAnnotations annotations;

  /**
   * Gets the name.
   *
   * @return <b>String</b> name of the parameter
   */
  public String getName() {
    return name;
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
   * @return {@link EdmMappingImpl} of this parameter
   */
  public EdmMapping getMapping() {
    return mapping;
  }

  /**
   * Sets the name of this {@link EdmFunctionImportParameter}.
   *
   * @param name the name
   * @return {@link EdmFunctionImportParameter} for method chaining
   */
  public EdmFunctionImportParameter setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the mode of this {@link EdmFunctionImportParameter}.
   *
   * @param mode the mode
   * @return {@link EdmFunctionImportParameter} for method chaining
   */
  public EdmFunctionImportParameter setMode(final String mode) {
    this.mode = mode;
    return this;
  }

  /**
   * Sets the {@link EdmSimpleTypeKind} of this {@link EdmFunctionImportParameter}.
   *
   * @param type the type
   * @return {@link EdmFunctionImportParameter} for method chaining
   */
  public EdmFunctionImportParameter setType(final EdmSimpleTypeKind type) {
    this.type = type;
    return this;
  }

  /**
   * Sets the {@link EdmFacets} of this {@link EdmFunctionImportParameter}.
   *
   * @param facets the facets
   * @return {@link EdmFunctionImportParameter} for method chaining
   */
  public EdmFunctionImportParameter setFacets(final EdmFacets facets) {
    this.facets = facets;
    return this;
  }

  /**
   * Sets the {@link EdmMappingImpl} of this {@link EdmFunctionImportParameter}.
   *
   * @param mapping the mapping
   * @return {@link EdmFunctionImportParameter} for method chaining
   */
  public EdmFunctionImportParameter setMapping(final EdmMapping mapping) {
    this.mapping = mapping;
    return this;
  }

  /**
   * Sets the {@link Documentation} of this {@link EdmFunctionImportParameter}.
   *
   * @param documentation the documentation
   * @return {@link EdmFunctionImportParameter} for method chaining
   */
  public EdmFunctionImportParameter setDocumentation(final EdmDocumentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the annotations.
   *
   * @param annotations the new annotations
   */
  public void setAnnotations(EdmAnnotationsImpl annotations) {
    this.annotations = annotations;
  }
  
  /**
   * Gets the annotations.
   *
   * @return the annotations
   */
  public EdmAnnotations getAnnotations() {
    return this.annotations;
  }  
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format(name);
  }
}
