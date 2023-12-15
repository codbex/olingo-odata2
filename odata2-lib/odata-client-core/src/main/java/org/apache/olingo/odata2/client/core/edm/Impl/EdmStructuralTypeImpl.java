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

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmStructuralType;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTyped;

// TODO: Auto-generated Javadoc
/**
 *  Objects of this class represent structural type of the entity.
 */
public abstract class EdmStructuralTypeImpl extends EdmNamedImpl implements EdmStructuralType, EdmAnnotatable {

  /** The edm base type. */
  protected EdmStructuralType edmBaseType;
  
  /** The structural type. */
  protected EdmComplexType structuralType;
  
  /** The edm type kind. */
  private EdmTypeKind edmTypeKind;
  
  /** The namespace. */
  protected String namespace;
  
  /** The properties. */
  private List<EdmProperty> properties;
  
  /** The edm property names. */
  private List<String> edmPropertyNames;
  
  /** The annotations. */
  private EdmAnnotations annotations;


  /**
   * Gets the edm base type.
   *
   * @return the edm base type
   */
  public EdmStructuralType getEdmBaseType() {
    return edmBaseType;
  }

  /**
   * Sets the edm base type.
   *
   * @param edmBaseType the new edm base type
   */
  public void setEdmBaseType(EdmStructuralType edmBaseType) {
    this.edmBaseType = edmBaseType;
  }

  /**
   * Gets the structural type.
   *
   * @return the structural type
   */
  public EdmComplexType getStructuralType() {
    return structuralType;
  }

  /**
   * Sets the structural type.
   *
   * @param structuralType the new structural type
   */
  public void setStructuralType(EdmComplexType structuralType) {
    this.structuralType = structuralType;
  }

  /**
   * Gets the edm type kind.
   *
   * @return the edm type kind
   */
  public EdmTypeKind getEdmTypeKind() {
    return edmTypeKind;
  }

  /**
   * Sets the edm type kind.
   *
   * @param edmTypeKind the new edm type kind
   */
  public void setEdmTypeKind(EdmTypeKind edmTypeKind) {
    this.edmTypeKind = edmTypeKind;
  }

  /**
   * Sets the edm property names.
   *
   * @param edmPropertyNames the new edm property names
   */
  public void setEdmPropertyNames(List<String> edmPropertyNames) {
    this.edmPropertyNames = edmPropertyNames;
  }

  /**
   * Sets the namespace.
   *
   * @param namespace the new namespace
   */
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  /**
   * Sets the annotations.
   *
   * @param annotations the new annotations
   */
  public void setAnnotations(EdmAnnotations annotations) {
    this.annotations = annotations;
  }


  /**
   * Gets the properties.
   *
   * @return the properties
   */
  public List<EdmProperty> getProperties() {
    return properties;
  }

  /**
   * Sets the properties.
   *
   * @param properties the new properties
   */
  public void setProperties(List<EdmProperty> properties) {
    this.properties = properties;
  }

  /**
   * Gets the namespace.
   *
   * @return the namespace
   * @throws EdmException the edm exception
   */
  @Override
  public String getNamespace() throws EdmException {
    return namespace;
  }

  /**
   * Gets the property.
   *
   * @param name the name
   * @return the property
   * @throws EdmException the edm exception
   */
  @Override
  public EdmTyped getProperty(final String name) throws EdmException {
    EdmTyped property = getPropertyInternal(name);
    if (property == null && edmBaseType != null) {
      property = edmBaseType.getProperty(name);
    }
    return property;
  }

  /**
   * Gets the property names.
   *
   * @return the property names
   * @throws EdmException the edm exception
   */
  @Override
  public List<String> getPropertyNames() throws EdmException {
    return edmPropertyNames;
  }

  /**
   * Gets the base type.
   *
   * @return the base type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmStructuralType getBaseType() throws EdmException {
    return edmBaseType;
  }

  /**
   * Gets the kind.
   *
   * @return the kind
   */
  @Override
  public EdmTypeKind getKind() {
    return edmTypeKind;
  }

  /**
   * Gets the mapping.
   *
   * @return the mapping
   * @throws EdmException the edm exception
   */
  @Override
  public EdmMapping getMapping() throws EdmException {
    return structuralType.getMapping();
  }

  /**
   * Gets the property internal.
   *
   * @param name the name
   * @return the property internal
   * @throws EdmException the edm exception
   */
  protected EdmTyped getPropertyInternal(final String name) throws EdmException {
    EdmTyped edmProperty = null;
    for (EdmProperty property : properties) {
      if (property.getName().equals(name)) {
        return property;
      } 
    }
    if (edmBaseType!=null) {
      edmProperty = edmBaseType.getProperty(name);
    }
    return edmProperty;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    try {
      return namespace + Edm.DELIMITER + getName();
    } catch (final EdmException e) {
      return null; //NOSONAR
    }
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAnnotations getAnnotations() throws EdmException {
    return annotations;
  }
  
}
