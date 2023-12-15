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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmAssociation;
import org.apache.olingo.odata2.api.edm.EdmAssociationSet;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.client.api.edm.ClientEdm;
import org.apache.olingo.odata2.client.api.edm.EdmDocumentation;

// TODO: Auto-generated Javadoc
/**
 *  Objects of this class represent EdmEntityContainer.
 */
public class EdmEntityContainerImpl implements EdmEntityContainer, EdmAnnotatable {

  /** The edm. */
  private EdmImpl edm;
  
  /** The entity container hierachy. */
  private List<EdmEntityContainer> entityContainerHierachy;
  
  /** The edm entity sets. */
  private List<EdmEntitySet> edmEntitySets;
  
  /** The edm association set map. */
  private Map<String, EdmAssociationSet> edmAssociationSetMap;
  
  /** The edm association sets. */
  private List<EdmAssociationSet> edmAssociationSets;
  
  /** The edm function imports. */
  private List<EdmFunctionImport> edmFunctionImports;
  
  /** The edm extended entity container. */
  private EdmEntityContainer edmExtendedEntityContainer;
  
  /** The is default container. */
  private boolean isDefaultContainer;
  
  /** The annotations. */
  private EdmAnnotations annotations;
  
  /** The documentation. */
  private EdmDocumentation documentation;
  
  /** The name. */
  private String name;
  
  /** The extendz. */
  private String extendz;
  
  /** The namespace. */
  private String namespace;

  /**
   * Gets the edm association set map.
   *
   * @return the edm association set map
   */
  public Map<String, EdmAssociationSet> getEdmAssociationSetMap() {
    return edmAssociationSetMap;
  }

  /**
   * Sets the edm association set map.
   *
   * @param associationSetMap the association set map
   */
  public void setEdmAssociationSetMap(Map<String, EdmAssociationSet> associationSetMap) {
    this.edmAssociationSetMap = associationSetMap;
  }

  /**
   * Gets the documentation.
   *
   * @return the documentation
   */
  public EdmDocumentation getDocumentation() {
    return documentation;
  }

  /**
   * Sets the documentation.
   *
   * @param documentation the new documentation
   */
  public void setDocumentation(EdmDocumentation documentation) {
    this.documentation = documentation;
  }

  /**
   * Sets the edm.
   *
   * @param edm the new edm
   */
  public void setEdm(EdmImpl edm) {
    this.edm = edm;
  }

  /**
   * Sets the namespace.
   *
   * @param namespace the new namespace
   * @throws EdmException the edm exception
   */
  public void setNamespace(String namespace) throws EdmException {
    this.namespace = namespace;
  }
  
  /**
   * Gets the edm.
   *
   * @return the edm
   */
  public ClientEdm getEdm() {
    return edm;
  }

  /**
   * Gets the edm extended entity container.
   *
   * @return the edm extended entity container
   */
  public EdmEntityContainer getEdmExtendedEntityContainer() {
    return edmExtendedEntityContainer;
  }

  /**
   * Sets the edm extended entity container.
   *
   * @param edmExtendedEntityContainer the new edm extended entity container
   */
  public void setEdmExtendedEntityContainer(EdmEntityContainer edmExtendedEntityContainer) {
    this.edmExtendedEntityContainer = edmExtendedEntityContainer;
  }

  /**
   * Sets the entity container hierachy.
   *
   * @param entityContainerHierachy the new entity container hierachy
   */
  public void setEntityContainerHierachy(List<EdmEntityContainer> entityContainerHierachy) {
    this.entityContainerHierachy = entityContainerHierachy;
  }

  /**
   * Gets the extendz.
   *
   * @return the extendz
   */
  public String getExtendz() {
    return extendz;
  }

  /**
   * Sets the extendz.
   *
   * @param extendz the new extendz
   */
  public void setExtendz(String extendz) {
    this.extendz = extendz;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
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
   * Instantiates a new edm entity container impl.
   *
   * @param edm the edm
   * @throws EdmException the edm exception
   */
  public EdmEntityContainerImpl(final EdmImpl edm)
      throws EdmException {
    this.edm = edm;
    edmEntitySets = new ArrayList<EdmEntitySet>();
    edmAssociationSets = new ArrayList<EdmAssociationSet>();
    edmFunctionImports = new ArrayList<EdmFunctionImport>();
  }

  /**
   * Checks if is default container.
   *
   * @return true, if is default container
   */
  public boolean isDefaultContainer() {
    return isDefaultContainer;
  }

  /**
   * Sets the default container.
   *
   * @param isDefaultContainer the new default container
   */
  public void setDefaultContainer(boolean isDefaultContainer) {
    this.isDefaultContainer = isDefaultContainer;
  }
  
  /**
   * Gets the name.
   *
   * @return the name
   * @throws EdmException the edm exception
   */
  @Override
  public String getName() throws EdmException {
    return name;
  }

  /**
   * Gets the entity set.
   *
   * @param name the name
   * @return the entity set
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntitySet getEntitySet(final String name) throws EdmException {
    EdmEntitySet edmEntitySet = null;
      for(EdmEntitySet entity:edmEntitySets){
        if(entity.getName().equals(name)){
          edmEntitySet = entity;
          break;
      }
    }
    return edmEntitySet;
   
  }

  /**
   * Gets the function import.
   *
   * @param name the name
   * @return the function import
   * @throws EdmException the edm exception
   */
  @Override
  public EdmFunctionImport getFunctionImport(final String name) throws EdmException {
    for (EdmFunctionImport edmFunctionImport : edmFunctionImports) {
      if (edmFunctionImport.getName().equalsIgnoreCase(name)) {
        return edmFunctionImport;
      }
    }
    return null;
  }

  /**
   * Gets the association set.
   *
   * @param sourceEntitySet the source entity set
   * @param navigationProperty the navigation property
   * @return the association set
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAssociationSet getAssociationSet(final EdmEntitySet sourceEntitySet,
      final EdmNavigationProperty navigationProperty) throws EdmException {
    EdmAssociation edmAssociation = navigationProperty.getRelationship();
    String association = edmAssociation.getNamespace() + "." + edmAssociation.getName();
    String entitySetName = sourceEntitySet.getName();
    String entitySetFromRole = navigationProperty.getFromRole();

    String key = entitySetName + ">>" + association + ">>" + entitySetFromRole;

    for (Entry<String, EdmAssociationSet> edmAssociationSet : edmAssociationSetMap.entrySet()) {
      if (edmAssociationSet.getKey().equalsIgnoreCase(key)) {
        return edmAssociationSet.getValue();
      }
    }
   return null;

  }

  /**
   * Checks if is default entity container.
   *
   * @return true, if is default entity container
   */
  @Override
  public boolean isDefaultEntityContainer() {
    return isDefaultContainer;
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
   * Gets the association sets.
   *
   * @return the association sets
   * @throws EdmException the edm exception
   */
  @Override
  public List<EdmAssociationSet> getAssociationSets() throws EdmException {
    return edmAssociationSets;
  }

  /**
   * Gets the edm entity sets.
   *
   * @return the edm entity sets
   */
  public  List<EdmEntitySet> getEdmEntitySets() {
    return edmEntitySets;
  }

  /**
   * Sets the edm entity sets.
   *
   * @param edmEntitySets the edm entity sets
   * @return the edm entity container impl
   */
  public EdmEntityContainerImpl setEdmEntitySets( List<EdmEntitySet> edmEntitySets) {
    this.edmEntitySets = edmEntitySets;
    return this;
  }

  /**
   * Gets the edm association sets.
   *
   * @return the edm association sets
   */
  public List<EdmAssociationSet> getEdmAssociationSets() {
    return edmAssociationSets;
  }

  /**
   * Sets the edm association sets.
   *
   * @param edmAssociationSets the edm association sets
   * @return the edm entity container impl
   */
  public EdmEntityContainerImpl setEdmAssociationSets(List<EdmAssociationSet> edmAssociationSets) {
    this.edmAssociationSets = edmAssociationSets;
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
   * @return the edm entity container impl
   */
  public EdmEntityContainerImpl setEdmFunctionImports(List<EdmFunctionImport> edmFunctionImports) {
    this.edmFunctionImports = edmFunctionImports;
    return this;
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
}
