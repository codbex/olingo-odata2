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
 * Objects of this class represent a function import.
 */
public class FunctionImport {

  /** The name. */
  private String name;
  
  /** The return type. */
  private ReturnType returnType;
  
  /** The entity set. */
  private String entitySet;
  
  /** The http method. */
  private String httpMethod;
  
  /** The parameters. */
  private List<FunctionImportParameter> parameters;
  
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
   * @return <b>String</b> name of this function import
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the return type.
   *
   * @return {@link ReturnType} of this function import
   */
  public ReturnType getReturnType() {
    return returnType;
  }

  /**
   * Gets the entity set.
   *
   * @return <b>String</b> name of the entity set
   */
  public String getEntitySet() {
    return entitySet;
  }

  /**
   * Gets the http method.
   *
   * @return <b>String</b> name of the used HTTP method
   */
  public String getHttpMethod() {
    return httpMethod;
  }

  /**
   * Gets the parameters.
   *
   * @return List<{@link FunctionImportParameter}>s of this function import
   */
  public List<FunctionImportParameter> getParameters() {
    return parameters;
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
   * Sets the name of this {@link FunctionImport}.
   *
   * @param name the name
   * @return {@link FunctionImport} for method chaining
   */
  public FunctionImport setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the {@link ReturnType} of this {@link FunctionImport}.
   *
   * @param returnType the return type
   * @return {@link FunctionImport} for method chaining
   */
  public FunctionImport setReturnType(final ReturnType returnType) {
    this.returnType = returnType;
    return this;
  }

  /**
   * Sets the {@link EntitySet} of this {@link FunctionImport}.
   *
   * @param entitySet the entity set
   * @return {@link FunctionImport} for method chaining
   */
  public FunctionImport setEntitySet(final String entitySet) {
    this.entitySet = entitySet;
    return this;
  }

  /**
   * Sets the HTTP method of this {@link FunctionImport}.
   *
   * @param httpMethod the http method
   * @return {@link FunctionImport} for method chaining
   */
  public FunctionImport setHttpMethod(final String httpMethod) {
    this.httpMethod = httpMethod;
    return this;
  }

  /**
   * Sets the {@link FunctionImportParameter}s of this {@link FunctionImport}.
   *
   * @param parameters the parameters
   * @return {@link FunctionImport} for method chaining
   */
  public FunctionImport setParameters(final List<FunctionImportParameter> parameters) {
    this.parameters = parameters;
    return this;
  }

  /**
   * Sets the {@link Mapping}.
   *
   * @param mapping the mapping
   * @return {@link FunctionImport} for method chaining
   */
  public FunctionImport setMapping(final Mapping mapping) {
    this.mapping = mapping;
    return this;
  }

  /**
   * Sets the {@link Documentation}.
   *
   * @param documentation the documentation
   * @return {@link FunctionImport} for method chaining
   */
  public FunctionImport setDocumentation(final Documentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link FunctionImport}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link FunctionImport} for method chaining
   */
  public FunctionImport setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link FunctionImport}.
   *
   * @param annotationElements the annotation elements
   * @return {@link FunctionImport} for method chaining
   */
  public FunctionImport setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
