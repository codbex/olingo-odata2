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

import java.util.List;
import org.apache.olingo.odata2.client.api.edm.EdmSchema;
import org.apache.olingo.odata2.client.api.edm.EdmUsing;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmAssociation;
import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;
import org.apache.olingo.odata2.api.edm.EdmAnnotationElement;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent a schema.
 */
public class EdmSchemaImpl implements EdmSchema{

  /** The namespace. */
  private String namespace;
  
  /** The alias. */
  private String alias;
  
  /** The usings. */
  private List<EdmUsing > usings;
  
  /** The entity types. */
  private List<EdmEntityType> entityTypes;
  
  /** The complex types. */
  private List<EdmComplexType> complexTypes;
  
  /** The associations. */
  private List<EdmAssociation> associations;
  
  /** The entity containers. */
  private List<EdmEntityContainer> entityContainers;
  
  /** The annotation attributes. */
  private List<EdmAnnotationAttribute> annotationAttributes;
  
  /** The annotation elements. */
  private List<EdmAnnotationElement> annotationElements;

  /**
   * Sets the namespace for this {@link EdmSchemaImpl}.
   *
   * @param namespace the namespace
   * @return {@link EdmSchemaImpl} for method chaining
   */
  public EdmSchemaImpl setNamespace(final String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Sets the alias for this {@link EdmSchemaImpl}.
   *
   * @param alias the alias
   * @return {@link EdmSchemaImpl} for method chaining
   */
  public EdmSchemaImpl setAlias(final String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Sets the {@link Using} for this {@link EdmSchemaImpl}.
   *
   * @param usings the usings
   * @return {@link EdmSchemaImpl} for method chaining
   */
  public EdmSchemaImpl setUsings(final List<EdmUsing> usings) {
    this.usings = usings;
    return this;
  }

  /**
   * Sets the {@link EntityType}s for this {@link EdmSchemaImpl}.
   *
   * @param entityTypes the entity types
   * @return {@link EdmSchemaImpl} for method chaining
   */
  public EdmSchemaImpl setEntityTypes(final List<EdmEntityType> entityTypes) {
    this.entityTypes = entityTypes;
    return this;
  }

  /**
   * Sets the {@link ComplexType}s for this {@link EdmSchemaImpl}.
   *
   * @param complexTypes the complex types
   * @return {@link EdmSchemaImpl} for method chaining
   */
  public EdmSchemaImpl setComplexTypes(final List<EdmComplexType> complexTypes) {
    this.complexTypes = complexTypes;
    return this;
  }

  /**
   * Sets the {@link Association}s for this {@link EdmSchemaImpl}.
   *
   * @param associations the associations
   * @return {@link EdmSchemaImpl} for method chaining
   */
  public EdmSchemaImpl setAssociations(final List<EdmAssociation> associations) {
    this.associations = associations;
    return this;
  }

  /**
   * Sets the {@link EntityContainer}s for this {@link EdmSchemaImpl}.
   *
   * @param entityContainers the entity containers
   * @return {@link EdmSchemaImpl} for method chaining
   */
  public EdmSchemaImpl setEntityContainers(final List<EdmEntityContainer> entityContainers) {
    this.entityContainers = entityContainers;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationAttribute} for this {@link EdmSchemaImpl}.
   *
   * @param annotationAttributes the annotation attributes
   * @return {@link EdmSchemaImpl} for method chaining
   */
  public EdmSchemaImpl setAnnotationAttributes(final List<EdmAnnotationAttribute> annotationAttributes) {
    this.annotationAttributes = annotationAttributes;
    return this;
  }

  /**
   * Sets the List of {@link AnnotationElement} for this {@link EdmSchemaImpl}.
   *
   * @param annotationElements the annotation elements
   * @return {@link EdmSchemaImpl} for method chaining
   */
  public EdmSchemaImpl setAnnotationElements(final List<EdmAnnotationElement> annotationElements) {
    this.annotationElements = annotationElements;
    return this;
  }

  /**
   * Gets the namespace.
   *
   * @return <b>String</b> namespace of this {@link EdmSchemaImpl}
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Gets the alias.
   *
   * @return <b>String</b> alias of this {@link EdmSchemaImpl}
   */
  public String getAlias() {
    return alias;
  }

  /**
   * Gets the usings.
   *
   * @return List<{@link Using}> of this {@link EdmSchemaImpl}
   */
  public List<EdmUsing> getUsings() {
    return usings;
  }

  /**
   * Gets the entity types.
   *
   * @return List<{@link EntityType}> of this {@link EdmSchemaImpl}
   */
  public List<EdmEntityType> getEntityTypes() {
    return entityTypes;
  }

  /**
   * Gets the complex types.
   *
   * @return List<{@link ComplexType}> of this {@link EdmSchemaImpl}
   */
  public List<EdmComplexType> getComplexTypes() {
    return complexTypes;
  }

  /**
   * Gets the associations.
   *
   * @return List<{@link Association}> of this {@link EdmSchemaImpl}
   */
  public List<EdmAssociation> getAssociations() {
    return associations;
  }

  /**
   * Gets the entity containers.
   *
   * @return List<{@link EntityContainer}> of this {@link EdmSchemaImpl}
   */
  public List<EdmEntityContainer> getEntityContainers() {
    return entityContainers;
  }

  /**
   * Gets the annotation attributes.
   *
   * @return List of {@link AnnotationAttribute} annotation attributes
   */
  public List<EdmAnnotationAttribute> getAnnotationAttributes() {
    return annotationAttributes;
  }

  /**
   * Gets the annotation elements.
   *
   * @return List of {@link AnnotationElement} annotation elements
   */
  public List<EdmAnnotationElement> getAnnotationElements() {
    return annotationElements;
  }
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format(namespace + Edm.DELIMITER  + alias);
  }
}
