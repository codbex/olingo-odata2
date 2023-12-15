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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.EdmAssociation;
import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmServiceMetadata;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.client.api.edm.ClientEdm;
import org.apache.olingo.odata2.client.api.edm.EdmSchema;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent Edm.
 */
public class EdmImpl implements ClientEdm {

  /** The edm entity containers. */
  protected Map<FullQualifiedName, EdmEntityContainer> edmEntityContainers;
  
  /** The edm entity types. */
  protected Map<FullQualifiedName, EdmEntityType> edmEntityTypes;
  
  /** The edm complex types. */
  private Map<FullQualifiedName, EdmComplexType> edmComplexTypes;
  
  /** The edm associations. */
  private Map<FullQualifiedName, EdmAssociation> edmAssociations;
  
  /** The alias to namespace info. */
  private Map<String, String> aliasToNamespaceInfo;
  
  /** The edm entity sets. */
  private List<EdmEntitySet> edmEntitySets;
  
  /** The edm function imports. */
  private List<EdmFunctionImport> edmFunctionImports;
  
  /** The edm schema. */
  private List<EdmSchema> edmSchema;
  
  /** The default edm entity container. */
  private EdmEntityContainer defaultEdmEntityContainer;

  /**
   * Instantiates a new edm impl.
   */
  public EdmImpl() {
    edmEntityContainers = new HashMap<FullQualifiedName, EdmEntityContainer>();
    edmEntityTypes = new HashMap<FullQualifiedName, EdmEntityType>();
    edmComplexTypes = new HashMap<FullQualifiedName, EdmComplexType>();
    edmAssociations = new HashMap<FullQualifiedName, EdmAssociation>();
    aliasToNamespaceInfo = new HashMap<String, String>();
  }

  /**
   * Gets the entity container.
   *
   * @param name the name
   * @return the entity container
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityContainer getEntityContainer(final String name)
      throws EdmException {
    for (Entry<FullQualifiedName, EdmEntityContainer> entry : edmEntityContainers.entrySet()) {
      if (entry.getKey().getName().equals(name)) {
        return entry.getValue();
      }
    }
    return null;
  }

  /**
   * Sets the edm schemas.
   *
   * @param edmSchema the edm schema
   * @return the edm impl
   */
  public EdmImpl setEdmSchemas(List<EdmSchema> edmSchema) {
    this.edmSchema = edmSchema;
    return this;
  }

  /**
   * Gets the entity type.
   *
   * @param namespaceOrAlias the namespace or alias
   * @param name the name
   * @return the entity type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityType getEntityType(final String namespaceOrAlias,
      final String name) throws EdmException {
    FullQualifiedName fqName = getNamespaceForAlias(namespaceOrAlias, name);
    return edmEntityTypes.get(fqName);
  }

  /**
   * Gets the namespace for alias.
   *
   * @param namespaceOrAlias the namespace or alias
   * @param name the name
   * @return the namespace for alias
   * @throws EdmException the edm exception
   */
  private FullQualifiedName getNamespaceForAlias(final String namespaceOrAlias, String name)
      throws EdmException {
    String namespace = aliasToNamespaceInfo.get(namespaceOrAlias);
    if (namespace != null) {
      //Namespace to alias mapping found
      return new FullQualifiedName(namespace, name);
    } else {
      //No mapping found. Parameter must be the namespace
      return new FullQualifiedName(namespaceOrAlias, name);
    }
  }

  /**
   * Gets the complex type.
   *
   * @param namespaceOrAlias the namespace or alias
   * @param name the name
   * @return the complex type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmComplexType getComplexType(final String namespaceOrAlias,
      final String name) throws EdmException {
    FullQualifiedName fqName = getNamespaceForAlias(namespaceOrAlias, name);
    return edmComplexTypes.get(fqName);
  }

  /**
   * Gets the association.
   *
   * @param namespaceOrAlias the namespace or alias
   * @param name the name
   * @return the association
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAssociation getAssociation(final String namespaceOrAlias,
      final String name) throws EdmException {
    FullQualifiedName fqName = getNamespaceForAlias(namespaceOrAlias, name);
    return edmAssociations.get(fqName);
  }

  /**
   * Sets the default entity container.
   *
   * @param defaultEdmEntityContainer the default edm entity container
   * @return the edm impl
   * @throws EdmException the edm exception
   */
  public EdmImpl setDefaultEntityContainer(EdmEntityContainer defaultEdmEntityContainer) throws EdmException {
    this.defaultEdmEntityContainer = defaultEdmEntityContainer;
    return this;
  }

  /**
   * Gets the default entity container.
   *
   * @return the default entity container
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityContainer getDefaultEntityContainer() throws EdmException {
    return defaultEdmEntityContainer;
  }

  /**
   * Gets the entity sets.
   *
   * @return the entity sets
   * @throws EdmException the edm exception
   */
  @Override
  public List<EdmEntitySet> getEntitySets() throws EdmException {
    return edmEntitySets;
  }

  /**
   * Gets the function imports.
   *
   * @return the function imports
   * @throws EdmException the edm exception
   */
  @Override
  public List<EdmFunctionImport> getFunctionImports() throws EdmException {
    return edmFunctionImports;
  }

  /**
   * Gets the service metadata.
   *
   * @return the service metadata
   */
  @Override
  public EdmServiceMetadata getServiceMetadata() {
    return null;
  }

  /**
   * Gets the edm entity containers.
   *
   * @return the edm entity containers
   */
  public Map<FullQualifiedName, EdmEntityContainer> getEdmEntityContainers() {
    return edmEntityContainers;
  }

  /**
   * Sets the edm entity containers.
   *
   * @param edmEntityContainers the edm entity containers
   * @return the edm impl
   */
  public EdmImpl setEdmEntityContainers(Map<FullQualifiedName, EdmEntityContainer> edmEntityContainers) {
    this.edmEntityContainers = edmEntityContainers;
    return this;
  }

  /**
   * Gets the edm entity types.
   *
   * @return the edm entity types
   */
  public Map<FullQualifiedName, EdmEntityType> getEdmEntityTypes() {
    return edmEntityTypes;
  }

  /**
   * Sets the edm entity types.
   *
   * @param edmEntityTypes the edm entity types
   * @return the edm impl
   */
  public EdmImpl setEdmEntityTypes(Map<FullQualifiedName, EdmEntityType> edmEntityTypes) {
    this.edmEntityTypes = edmEntityTypes;
    return this;
  }

  /**
   * Gets the edm complex types.
   *
   * @return the edm complex types
   */
  public Map<FullQualifiedName, EdmComplexType> getEdmComplexTypes() {
    return edmComplexTypes;
  }

  /**
   * Sets the edm complex types.
   *
   * @param edmComplexTypes the edm complex types
   * @return the edm impl
   */
  public EdmImpl setEdmComplexTypes(Map<FullQualifiedName, EdmComplexType> edmComplexTypes) {
    this.edmComplexTypes = edmComplexTypes;
    return this;
  }

  /**
   * Gets the edm associations.
   *
   * @return the edm associations
   */
  public Map<FullQualifiedName, EdmAssociation> getEdmAssociations() {
    return edmAssociations;
  }

  /**
   * Sets the edm associations.
   *
   * @param edmAssociations the edm associations
   * @return the edm impl
   */
  public EdmImpl setEdmAssociations(Map<FullQualifiedName, EdmAssociation> edmAssociations) {
    this.edmAssociations = edmAssociations;
    return this;
  }

  /**
   * Gets the alias to namespace info.
   *
   * @return the alias to namespace info
   */
  public Map<String, String> getAliasToNamespaceInfo() {
    return aliasToNamespaceInfo;
  }

  /**
   * Sets the alias to namespace info.
   *
   * @param aliasToNamespaceInfo the alias to namespace info
   * @return the edm impl
   */
  public EdmImpl setAliasToNamespaceInfo(Map<String, String> aliasToNamespaceInfo) {
    this.aliasToNamespaceInfo = aliasToNamespaceInfo;
    return this;
  }

  /**
   * Gets the edm entity sets.
   *
   * @return the edm entity sets
   */
  public List<EdmEntitySet> getEdmEntitySets() {
    return edmEntitySets;
  }

  /**
   * Sets the edm entity sets.
   *
   * @param edmEntitySets the edm entity sets
   * @return the edm impl
   */
  public EdmImpl setEdmEntitySets(List<EdmEntitySet> edmEntitySets) {
    this.edmEntitySets = edmEntitySets;
    return this;
  }

  /**
   * Gets the edm function imports.
   *
   * @return the edm function imports
   */
  public List<EdmFunctionImport> getEdmFunctionImports() {
    return edmFunctionImports;
  }

  /**
   * Sets the edm function imports.
   *
   * @param edmFunctionImports the edm function imports
   * @return the edm impl
   */
  public EdmImpl setEdmFunctionImports(List<EdmFunctionImport> edmFunctionImports) {
    this.edmFunctionImports = edmFunctionImports;
    return this;
  }

  /**
   * Gets the schemas.
   *
   * @return the schemas
   */
  @Override
  public List<EdmSchema> getSchemas() {
    return edmSchema;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return String.format("EdmImpl");
  }
}
