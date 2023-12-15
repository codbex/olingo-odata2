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
 * Objects of this class represent a complex type.
 */
public class ComplexType {

  /** The name. */
  private String name;
  
  /** The base type. */
  private FullQualifiedName baseType;
  
  /** The is abstract. */
  private boolean isAbstract;
  
  /** The properties. */
  private List<Property> properties;
  
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
   * @return <b>String</b> name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the base type.
   *
   * @return {@link FullQualifiedName} of the base type of this type (namespace and name)
   */
  public FullQualifiedName getBaseType() {
    return baseType;
  }

  /**
   * Checks if is abstract.
   *
   * @return <b>boolean</b> if this type is abstract
   */
  public boolean isAbstract() {
    return isAbstract;
  }

  /**
   * Gets the properties.
   *
   * @return List<{@link Property}> of all properties for this type
   */
  public List<Property> getProperties() {
    return properties;
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
   * Sets the name.
   *
   * @param name the name
   * @return {@link ComplexType} for method chaining
   */
  public ComplexType setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the {@link FullQualifiedName} of the base type.
   *
   * @param baseType the base type
   * @return {@link ComplexType} for method chaining
   */
  public ComplexType setBaseType(final FullQualifiedName baseType) {
    this.baseType = baseType;
    return this;
  }

  /**
   * Sets if it is abstract.
   *
   * @param isAbstract the is abstract
   * @return {@link ComplexType} for method chaining
   */
  public ComplexType setAbstract(final boolean isAbstract) {
    this.isAbstract = isAbstract;
    return this;
  }

  /**
   * Sets the {@link Property}s.
   *
   * @param properties the properties
   * @return {@link ComplexType} for method chaining
   */
  public ComplexType setProperties(final List<Property> properties) {
    this.properties = properties;
    return this;
  }

  /**
   * Sets the {@link Mapping}.
   *
   * @param mapping the mapping
   * @return {@link ComplexType} for method chaining
   */
  public ComplexType setMapping(final Mapping mapping) {
    this.mapping = mapping;
    return this;
  }

  /**
   * Sets the {@link Documentation}.
   *
   * @param documentation the documentation
   * @return {@link ComplexType} for method chaining
   */
  public ComplexType setDocumentation(final Documentation documentation) {
    this.documentation = documentation;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationAttribute} for this {@link ComplexType}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link ComplexType} for method chaining
   */
  public ComplexType setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the collection of {@link AnnotationElement} for this {@link ComplexType}.
   *
   * @param annotationElements the annotation elements
   * @return {@link ComplexType} for method chaining
   */
  public ComplexType setAnnotationElements(final List<AnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }
}
