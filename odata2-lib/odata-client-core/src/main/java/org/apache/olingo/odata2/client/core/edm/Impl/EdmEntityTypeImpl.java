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
import org.apache.olingo.odata2.api.edm.EdmCustomizableFeedMappings;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent EdmEntityType.
 */
public class EdmEntityTypeImpl extends EdmStructuralTypeImpl implements EdmEntityType {

  /** The edm key properties. */
  private List<EdmProperty> edmKeyProperties;
  
  /** The edm key property names. */
  private List<String> edmKeyPropertyNames;
  
  /** The navigation properties. */
  private List<EdmNavigationProperty> navigationProperties;
  
  /** The edm navigation property names. */
  private List<String> edmNavigationPropertyNames;
  
  /** The has stream. */
  private boolean hasStream;
  
  /** The is abstract. */
  private boolean isAbstract;
  
  /** The base type. */
  private FullQualifiedName baseType;
  
  /** The customizable feed mappings. */
  private EdmCustomizableFeedMappings customizableFeedMappings;

  /**
   * Sets the base type.
   *
   * @param baseType the new base type
   */
  public void setBaseType(FullQualifiedName baseType) {
    this.baseType = baseType;
  }

  /**
   * Sets the abstract.
   *
   * @param isAbstract the new abstract
   */
  public void setAbstract(boolean isAbstract) {
    this.isAbstract = isAbstract;
  }

   /**
    * Sets the edm key properties.
    *
    * @param edmKeyProperties the new edm key properties
    */
   public void setEdmKeyProperties(List<EdmProperty> edmKeyProperties) {
    this.edmKeyProperties = edmKeyProperties;
  }

  /**
   * Sets the navigation properties.
   *
   * @param navigationProperties the new navigation properties
   */
  public void setNavigationProperties(List<EdmNavigationProperty> navigationProperties) {
    this.navigationProperties = navigationProperties;
  }

  /**
   * Sets the edm navigation property names.
   *
   * @param edmNavigationPropertyNames the new edm navigation property names
   */
  public void setEdmNavigationPropertyNames(List<String> edmNavigationPropertyNames) {
    this.edmNavigationPropertyNames = edmNavigationPropertyNames;
  }

  /**
   * Sets the checks for stream.
   *
   * @param hasStream the new checks for stream
   */
  public void setHasStream(boolean hasStream) {
    this.hasStream = hasStream;
  }

  /**
   * Gets the key property names.
   *
   * @return the key property names
   * @throws EdmException the edm exception
   */
  @Override
  public List<String> getKeyPropertyNames() throws EdmException {
    return edmKeyPropertyNames;
  }

  /**
   * Sets the edm key property names.
   *
   * @param edmKeyPropertyNames the new edm key property names
   */
  public void setEdmKeyPropertyNames(List<String> edmKeyPropertyNames) {
    this.edmKeyPropertyNames = edmKeyPropertyNames;
  }

  /**
   * Gets the key properties.
   *
   * @return the key properties
   * @throws EdmException the edm exception
   */
  @Override
  public List<EdmProperty> getKeyProperties() throws EdmException {
    return edmKeyProperties;
  }

  /**
   * Checks for stream.
   *
   * @return true, if successful
   * @throws EdmException the edm exception
   */
  @Override
  public boolean hasStream() throws EdmException {
    return hasStream;
  }

  /**
   * Gets the customizable feed mappings.
   *
   * @return the customizable feed mappings
   * @throws EdmException the edm exception
   */
  @Override
  public EdmCustomizableFeedMappings getCustomizableFeedMappings() throws EdmException {
    return customizableFeedMappings;
  }

  /**
   * Gets the navigation property names.
   *
   * @return the navigation property names
   * @throws EdmException the edm exception
   */
  @Override
  public List<String> getNavigationPropertyNames() throws EdmException {
    return edmNavigationPropertyNames;
  }

  /**
   * Gets the base type.
   *
   * @return the base type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityType getBaseType() throws EdmException {
    return (EdmEntityType) edmBaseType;
  }

  /**
   * Gets the base type name.
   *
   * @return the base type name
   * @throws EdmException the edm exception
   */
  public FullQualifiedName getBaseTypeName() throws EdmException {
    return baseType;
  }

  /**
   * Gets the property internal.
   *
   * @param name the name
   * @return the property internal
   * @throws EdmException the edm exception
   */
  @Override
  protected EdmTyped getPropertyInternal(final String name) throws EdmException {
    EdmTyped edmProperty = super.getPropertyInternal(name);

    if (edmProperty != null) {
      return edmProperty;
    }
    for (EdmNavigationProperty navigations : navigationProperties) {
      if (navigations.getName().equals(name)) {
        return navigations;
      }
    }
    return edmProperty;
  }

  /**
   * Sets the customizable feed mappings.
   *
   * @param edmCustomizableFeedMappings the new customizable feed mappings
   */
  public void setCustomizableFeedMappings(EdmCustomizableFeedMappings edmCustomizableFeedMappings) {
    this.customizableFeedMappings = edmCustomizableFeedMappings;
  }
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format(namespace+ Edm.DELIMITER +name);
  }

}
