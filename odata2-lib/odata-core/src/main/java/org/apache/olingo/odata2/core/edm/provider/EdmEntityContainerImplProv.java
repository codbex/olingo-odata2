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
package org.apache.olingo.odata2.core.edm.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmAssociation;
import org.apache.olingo.odata2.api.edm.EdmAssociationSet;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.FunctionImport;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.exception.ODataException;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmEntityContainerImplProv.
 */
public class EdmEntityContainerImplProv implements EdmEntityContainer, EdmAnnotatable {

  /** The edm. */
  private EdmImplProv edm;
  
  /** The entity container hierachy. */
  private List<EntityContainer> entityContainerHierachy;
  
  /** The entity container info. */
  private EntityContainerInfo entityContainerInfo;
  
  /** The edm entity sets. */
  private Map<String, EdmEntitySet> edmEntitySets;
  
  /** The edm association sets. */
  private Map<String, EdmAssociationSet> edmAssociationSets;
  
  /** The edm function imports. */
  private Map<String, EdmFunctionImport> edmFunctionImports;
  
  /** The edm extended entity container. */
  private EdmEntityContainer edmExtendedEntityContainer;
  
  /** The is default container. */
  private boolean isDefaultContainer;
  
  /** The annotations. */
  private EdmAnnotations annotations;
  
  /** The namespace. */
  private String namespace;
  
  /**
   * Instantiates a new edm entity container impl prov.
   *
   * @param edm the edm
   * @param entityContainerInfo the entity container info
   * @throws EdmException the edm exception
   */
  public EdmEntityContainerImplProv(final EdmImplProv edm, final EntityContainerInfo entityContainerInfo)
      throws EdmException {
    this.edm = edm;
    this.entityContainerInfo = entityContainerInfo;
    edmEntitySets = new HashMap<String, EdmEntitySet>();
    edmAssociationSets = new HashMap<String, EdmAssociationSet>();
    edmFunctionImports = new HashMap<String, EdmFunctionImport>();
    isDefaultContainer = entityContainerInfo.isDefaultEntityContainer();

    if (entityContainerInfo.getExtendz() != null) {
      edmExtendedEntityContainer = edm.getEntityContainer(entityContainerInfo.getExtendz());
      if (edmExtendedEntityContainer == null) {
        throw new EdmException(EdmException.COMMON);
      }
    }
  }

  /**
   * Gets the name.
   *
   * @return the name
   * @throws EdmException the edm exception
   */
  @Override
  public String getName() throws EdmException {
    return entityContainerInfo.getName();
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
    EdmEntitySet edmEntitySet = edmEntitySets.get(name);
    if (edmEntitySet != null) {
      return edmEntitySet;
    }

    EntitySet entitySet;
    try {
      entitySet = edm.edmProvider.getEntitySet(entityContainerInfo.getName(), name);
    } catch (ODataException e) {
      throw new EdmException(EdmException.PROVIDERPROBLEM, e);
    }

    if (entitySet != null) {
      edmEntitySet = createEntitySet(entitySet);
    } else if (edmExtendedEntityContainer != null) {
      edmEntitySet = edmExtendedEntityContainer.getEntitySet(name);
      if (edmEntitySet != null) {
        edmEntitySets.put(name, edmEntitySet);
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
    EdmFunctionImport edmFunctionImport = edmFunctionImports.get(name);
    if (edmFunctionImport != null) {
      return edmFunctionImport;
    }

    FunctionImport functionImport;
    try {
      functionImport = edm.edmProvider.getFunctionImport(entityContainerInfo.getName(), name);
    } catch (ODataException e) {
      throw new EdmException(EdmException.PROVIDERPROBLEM, e);
    }

    if (functionImport != null) {
      edmFunctionImport = createFunctionImport(functionImport);
      edmFunctionImports.put(name, edmFunctionImport);
    } else if (edmExtendedEntityContainer != null) {
      edmFunctionImport = edmExtendedEntityContainer.getFunctionImport(name);
      if (edmFunctionImport != null) {
        edmFunctionImports.put(name, edmFunctionImport);
      }
    }

    return edmFunctionImport;
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

    EdmAssociationSet edmAssociationSet = edmAssociationSets.get(key);
    if (edmAssociationSet != null) {
      return edmAssociationSet;
    }

    AssociationSet associationSet;
    FullQualifiedName associationFQName =
        new FullQualifiedName(edmAssociation.getNamespace(), edmAssociation.getName());
    try {
      associationSet =
          edm.edmProvider.getAssociationSet(entityContainerInfo.getName(), associationFQName, entitySetName,
              entitySetFromRole);
    } catch (ODataException e) {
      throw new EdmException(EdmException.PROVIDERPROBLEM, e);
    }

    if (associationSet != null) {
      edmAssociationSet = createAssociationSet(associationSet);
      edmAssociationSets.put(key, edmAssociationSet);
      return edmAssociationSet;
    } else if (edmExtendedEntityContainer != null) {
      edmAssociationSet = edmExtendedEntityContainer.getAssociationSet(sourceEntitySet, navigationProperty);
      edmAssociationSets.put(key, edmAssociationSet);
      return edmAssociationSet;
    } else {
      throw new EdmException(EdmException.COMMON);
    }
  }

  /**
   * Create an {@link EdmEntitySet} based on given {@link EntitySet} and put it into the cache (see
   * {@link #edmEntitySets}).
   *
   * @param entitySet based on which the {@link EdmEntitySet} is created
   * @return the created and cached {@link EdmEntitySet}
   * @throws EdmException the edm exception
   */
  private EdmEntitySet createEntitySet(final EntitySet entitySet) throws EdmException {
    EdmEntitySet edmEntitySet = new EdmEntitySetImplProv(edm, entitySet, this);
    edmEntitySets.put(entitySet.getName(), edmEntitySet);
    return edmEntitySet;
  }

  /**
   * Creates the function import.
   *
   * @param functionImport the function import
   * @return the edm function import
   * @throws EdmException the edm exception
   */
  private EdmFunctionImport createFunctionImport(final FunctionImport functionImport) throws EdmException {
    return new EdmFunctionImportImplProv(edm, functionImport, this);
  }

  /**
   * Creates the association set.
   *
   * @param associationSet the association set
   * @return the edm association set
   * @throws EdmException the edm exception
   */
  private EdmAssociationSet createAssociationSet(final AssociationSet associationSet) throws EdmException {
    return new EdmAssociationSetImplProv(edm, associationSet, this);
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
    if (annotations == null) {
      annotations = new EdmAnnotationsImplProv(entityContainerInfo.getAnnotationAttributes(),
          entityContainerInfo.getAnnotationElements());
    }
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
    try {
      List<EdmEntitySet> edmEntitySetsList = new ArrayList<EdmEntitySet>();
      List<EntityContainer> entityContainerHierachyList = getEntityContainerHierachy();
      for (EntityContainer entityContainer : entityContainerHierachyList) {
        List<EntitySet> entitySets = entityContainer.getEntitySets();
        for (EntitySet entitySet : entitySets) {
          EdmEntitySet ees = createEntitySet(entitySet);
          edmEntitySetsList.add(ees);
        }
      }
      return edmEntitySetsList;
    } catch (ODataException e) {
      throw new EdmException(EdmException.PROVIDERPROBLEM, e);
    }
  }

  /**
   * Gets the association sets.
   *
   * @return the association sets
   * @throws EdmException the edm exception
   */
  @Override
  public List<EdmAssociationSet> getAssociationSets() throws EdmException {
    try {
      List<EntityContainer> containers = getEntityContainerHierachy();
      List<EdmAssociationSet> edmAssociationSetsList = new ArrayList<EdmAssociationSet>();
      for (EntityContainer entityContainer : containers) {
        List<AssociationSet> associationSets = entityContainer.getAssociationSets();
        for (AssociationSet associationSet : associationSets) {
          EdmAssociationSet eas = createAssociationSet(associationSet);
          edmAssociationSetsList.add(eas);
        }
      }

      return edmAssociationSetsList;
    } catch (ODataException e) {
      throw new EdmException(EdmException.PROVIDERPROBLEM, e);
    }
  }

  /**
   * Gets the entity container map.
   *
   * @return the entity container map
   * @throws ODataException the o data exception
   */
  private Map<String, EntityContainer> getEntityContainerMap() throws ODataException {
    Map<String, EntityContainer> name2Container = new HashMap<String, EntityContainer>();
    List<Schema> schemas = edm.edmProvider.getSchemas();
    for (Schema schema : schemas) {
      List<EntityContainer> containers = schema.getEntityContainers();
      for (EntityContainer container : containers) {
        name2Container.put(container.getName(), container);
      }
    }
    return name2Container;
  }

  /**
   * Gets the entity container hierachy.
   *
   * @return the entity container hierachy
   * @throws ODataException the o data exception
   */
  private List<EntityContainer> getEntityContainerHierachy() throws ODataException {
    if (entityContainerHierachy != null) {
      return entityContainerHierachy;
    }

    entityContainerHierachy = new ArrayList<EntityContainer>();
    Map<String, EntityContainer> name2Container = getEntityContainerMap();
    String currentName = getName();
    while (currentName != null) {
      EntityContainer currentContainer = name2Container.get(currentName);
      entityContainerHierachy.add(currentContainer);
      currentName = currentContainer.getExtendz();
    }

    if (entityContainerHierachy.isEmpty()) {
      throw new EdmException(EdmException.PROVIDERPROBLEM, "No container at all found.");
    }
    return entityContainerHierachy;
  }
  

  /**
   * Gets the namespace.
   *
   * @return the namespace
   * @throws EdmException the edm exception
   */
  @Override
  public String getNamespace()  throws EdmException{
    try {
      if (namespace == null) {
        List<Schema> schemas;
        schemas = edm.edmProvider.getSchemas();
        for (Schema schema : schemas) {
          List<EntityContainer> containers = schema.getEntityContainers();
          for (EntityContainer container : containers) {
            if (container.getName().equals(entityContainerInfo.getName())) {
              this.namespace = schema.getNamespace();
              break;
            }
          }
          if (namespace != null) {
            break;
          }
        }
      }
    } catch (ODataException e) {
      throw new EdmException(EdmException.PROVIDERPROBLEM, e);
    }
    return namespace;
  }
}
