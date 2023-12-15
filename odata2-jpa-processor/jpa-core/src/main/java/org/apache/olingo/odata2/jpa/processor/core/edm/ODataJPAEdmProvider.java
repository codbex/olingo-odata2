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
package org.apache.olingo.odata2.jpa.processor.core.edm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.AssociationSetEnd;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.FunctionImport;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmModelView;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataJPAEdmProvider.
 */
public class ODataJPAEdmProvider extends EdmProvider {

  /** The o data JPA context. */
  private ODataJPAContext oDataJPAContext;
  
  /** The jpa edm model. */
  private JPAEdmModelView jpaEdmModel;

  /** The schemas. */
  private List<Schema> schemas;
  
  /** The entity types. */
  private HashMap<String, EntityType> entityTypes;
  
  /** The entity container infos. */
  private HashMap<String, EntityContainerInfo> entityContainerInfos;
  
  /** The complex types. */
  private HashMap<String, ComplexType> complexTypes;
  
  /** The associations. */
  private HashMap<String, Association> associations;
  
  /** The function imports. */
  private HashMap<String, FunctionImport> functionImports;

  /**
   * Instantiates a new o data JPA edm provider.
   */
  public ODataJPAEdmProvider() {
    entityTypes = new LinkedHashMap<String, EntityType>();
    entityContainerInfos = new LinkedHashMap<String, EntityContainerInfo>();
    complexTypes = new LinkedHashMap<String, ComplexType>();
    associations = new LinkedHashMap<String, Association>();
    functionImports = new LinkedHashMap<String, FunctionImport>();
  }

  /**
   * Instantiates a new o data JPA edm provider.
   *
   * @param oDataJPAContext the o data JPA context
   */
  public ODataJPAEdmProvider(final ODataJPAContext oDataJPAContext) {
    if (oDataJPAContext == null) {
      throw new IllegalArgumentException(ODataJPAException.ODATA_JPACTX_NULL);
    }
    entityTypes = new LinkedHashMap<String, EntityType>();
    entityContainerInfos = new LinkedHashMap<String, EntityContainerInfo>();
    complexTypes = new LinkedHashMap<String, ComplexType>();
    associations = new LinkedHashMap<String, Association>();
    functionImports = new LinkedHashMap<String, FunctionImport>();
    jpaEdmModel = ODataJPAFactory.createFactory().getJPAAccessFactory().getJPAEdmModelView(oDataJPAContext);
  }

  /**
   * Gets the o data JPA context.
   *
   * @return the o data JPA context
   */
  public ODataJPAContext getODataJPAContext() {
    return oDataJPAContext;
  }

  /**
   * Sets the o data JPA context.
   *
   * @param jpaContext the new o data JPA context
   */
  public void setODataJPAContext(final ODataJPAContext jpaContext) {
    oDataJPAContext = jpaContext;
  }

  /**
   * Gets the entity container info.
   *
   * @param name the name
   * @return the entity container info
   * @throws ODataException the o data exception
   */
  @Override
  public EntityContainerInfo getEntityContainerInfo(final String name) throws ODataException {

    if (entityContainerInfos.containsKey(name)) {
      return entityContainerInfos.get(name);
    } else {

      if (schemas == null) {
        getSchemas();
      }
      List<EntityContainer> containerList = schemas.get(0).getEntityContainers();
      if (containerList == null) {
        return null;
      }
      for (EntityContainer container : containerList) {
        if (name == null && container.isDefaultEntityContainer()) {
          entityContainerInfos.put(name, container);
          return container;
        } else if (name != null && name.equals(container.getName())) {
          return container;
        }
      }
    }
    return null;
  }

  /**
   * Gets the entity type.
   *
   * @param edmFQName the edm FQ name
   * @return the entity type
   * @throws ODataException the o data exception
   */
  @Override
  public EntityType getEntityType(final FullQualifiedName edmFQName) throws ODataException {

    String strEdmFQName = null;

    if (edmFQName != null) {
      strEdmFQName = edmFQName.toString();
      if (entityTypes.containsKey(strEdmFQName)) {
        return entityTypes.get(strEdmFQName);
      } else if (schemas == null) {
        getSchemas();
      }

      String entityTypeNamespace = edmFQName.getNamespace();
      String entityTypeName = edmFQName.getName();

      for (Schema schema : schemas) {
        String schemaNamespace = schema.getNamespace();
        if (schemaNamespace.equals(entityTypeNamespace)) {
          if (schema.getEntityTypes() == null) {
            return null;
          }
          for (EntityType et : schema.getEntityTypes()) {
            if (et.getName().equals(entityTypeName)) {
              entityTypes.put(strEdmFQName, et);
              return et;
            }
          }
        }
      }
    }

    return null;
  }

  /**
   * Gets the complex type.
   *
   * @param edmFQName the edm FQ name
   * @return the complex type
   * @throws ODataException the o data exception
   */
  @Override
  public ComplexType getComplexType(final FullQualifiedName edmFQName) throws ODataException {

    if (edmFQName != null) {
      if (complexTypes.containsKey(edmFQName.toString())) {
        return complexTypes.get(edmFQName.toString());
      } else if (schemas == null) {
        getSchemas();
      }

      for (Schema schema : schemas) {
        if (schema.getNamespace().equals(edmFQName.getNamespace())) {
          if (schema.getComplexTypes() == null) {
            return null;
          }
          for (ComplexType ct : schema.getComplexTypes()) {
            if (ct.getName().equals(edmFQName.getName())) {
              complexTypes.put(edmFQName.toString(), ct);
              return ct;
            }
          }
        }
      }
    }

    return null;
  }

  /**
   * Gets the association.
   *
   * @param edmFQName the edm FQ name
   * @return the association
   * @throws ODataException the o data exception
   */
  @Override
  public Association getAssociation(final FullQualifiedName edmFQName) throws ODataException {
    if (edmFQName != null) {
      if (associations.containsKey(edmFQName.toString())) {
        return associations.get(edmFQName.toString());
      } else if (schemas == null) {
        getSchemas();
      }

      for (Schema schema : schemas) {
        if (schema.getNamespace().equals(edmFQName.getNamespace())) {
          if (schema.getAssociations() == null) {
            return null;
          }
          for (Association association : schema.getAssociations()) {
            if (association.getName().equals(edmFQName.getName())) {
              associations.put(edmFQName.toString(), association);
              return association;
            }
          }
        }
      }

    }
    return null;
  }

  /**
   * Gets the entity set.
   *
   * @param entityContainer the entity container
   * @param name the name
   * @return the entity set
   * @throws ODataException the o data exception
   */
  @Override
  public EntitySet getEntitySet(final String entityContainer, final String name) throws ODataException {

    EntitySet returnedSet = null;
    EntityContainer container = null;
    if (!entityContainerInfos.containsKey(entityContainer)) {
      container = (EntityContainer) getEntityContainerInfo(entityContainer);
    } else {
      container = (EntityContainer) entityContainerInfos.get(entityContainer);
    }

    if (container != null && name != null) {
      for (EntitySet es : container.getEntitySets()) {
        if (name.equals(es.getName())) {
          returnedSet = es;
          break;
        }
      }
    }

    return returnedSet;
  }

  /**
   * Gets the association set.
   *
   * @param entityContainer the entity container
   * @param association the association
   * @param sourceEntitySetName the source entity set name
   * @param sourceEntitySetRole the source entity set role
   * @return the association set
   * @throws ODataException the o data exception
   */
  @Override
  public AssociationSet getAssociationSet(final String entityContainer, final FullQualifiedName association,
      final String sourceEntitySetName, final String sourceEntitySetRole) throws ODataException {

    EntityContainer container = null;
    if (!entityContainerInfos.containsKey(entityContainer)) {
      container = (EntityContainer) getEntityContainerInfo(entityContainer);
    } else {
      container = (EntityContainer) entityContainerInfos.get(entityContainer);
    }

    if (container != null && association != null && container.getAssociationSets() != null) {
      for (AssociationSet as : container.getAssociationSets()) {
        if (association.equals(as.getAssociation())) {
          AssociationSetEnd end = as.getEnd1();
          if (sourceEntitySetName.equals(end.getEntitySet()) && sourceEntitySetRole.equals(end.getRole())) {
            return as;
          } else {
            end = as.getEnd2();
            if (sourceEntitySetName.equals(end.getEntitySet()) && sourceEntitySetRole.equals(end.getRole())) {
              return as;
            }
          }
        }
      }
    }
    return null;
  }

  /**
   * Gets the function import.
   *
   * @param entityContainer the entity container
   * @param name the name
   * @return the function import
   * @throws ODataException the o data exception
   */
  @Override
  public FunctionImport getFunctionImport(final String entityContainer, final String name) throws ODataException {

    if (functionImports.containsKey(name)) {
      return functionImports.get(name);
    }

    EntityContainer container = null;
    if (!entityContainerInfos.containsKey(entityContainer)) {
      container = (EntityContainer) getEntityContainerInfo(entityContainer);
    } else {
      container = (EntityContainer) entityContainerInfos.get(entityContainer);
    }

    if (container != null && name != null) {
      if (container.getFunctionImports() == null) {
        return null;
      }
      for (FunctionImport fi : container.getFunctionImports()) {
        if (name.equals(fi.getName())) {
          functionImports.put(name, fi);
          return fi;
        }
      }
    }
    return null;
  }

  /**
   * Gets the schemas.
   *
   * @return the schemas
   * @throws ODataException the o data exception
   */
  @Override
  public List<Schema> getSchemas() throws ODataException {
    if (schemas == null && jpaEdmModel != null) {
      jpaEdmModel.getBuilder().build();
      schemas = new ArrayList<Schema>();
      schemas.add(jpaEdmModel.getEdmSchemaView().getEdmSchema());
    }
    if (jpaEdmModel == null) {

      throw ODataJPAModelException.throwException(ODataJPAModelException.BUILDER_NULL, null);
    }

    return schemas;

  }

}
