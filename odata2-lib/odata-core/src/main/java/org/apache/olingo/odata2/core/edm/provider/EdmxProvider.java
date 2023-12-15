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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamReader;

import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.AliasInfo;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.DataServices;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.FunctionImport;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.core.commons.XmlHelper;
import org.apache.olingo.odata2.core.ep.consumer.XmlMetadataConsumer;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmxProvider.
 */
public class EdmxProvider extends EdmProvider {
  
  /** The data services. */
  private DataServices dataServices;

  /**
   * Parses the.
   *
   * @param in the in
   * @param validate the validate
   * @return the edmx provider
   * @throws EntityProviderException the entity provider exception
   */
  public EdmxProvider parse(final InputStream in, final boolean validate) throws EntityProviderException {
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader streamReader = XmlHelper.createStreamReader(in);
    dataServices = parser.readMetadata(streamReader, validate);
    return this;
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
    if (name != null) {
      for (Schema schema : dataServices.getSchemas()) {
        for (EntityContainer container : schema.getEntityContainers()) {
          if (container.getName().equals(name)) {
            return container;
          }
        }
      }
    } else {
      for (Schema schema : dataServices.getSchemas()) {
        for (EntityContainer container : schema.getEntityContainers()) {
          if (container.isDefaultEntityContainer()) {
            return container;
          }
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
    for (Schema schema : dataServices.getSchemas()) {
      if (schema.getNamespace().equals(edmFQName.getNamespace())) {
        for (EntityType entityType : schema.getEntityTypes()) {
          if (entityType.getName().equals(edmFQName.getName())) {
            return entityType;
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
    for (Schema schema : dataServices.getSchemas()) {
      if (schema.getNamespace().equals(edmFQName.getNamespace())) {
        for (ComplexType complexType : schema.getComplexTypes()) {
          if (complexType.getName().equals(edmFQName.getName())) {
            return complexType;
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
    for (Schema schema : dataServices.getSchemas()) {
      if (schema.getNamespace().equals(edmFQName.getNamespace())) {
        for (Association association : schema.getAssociations()) {
          if (association.getName().equals(edmFQName.getName())) {
            return association;
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
    for (Schema schema : dataServices.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        if (container.getName().equals(entityContainer)) {
          for (EntitySet entitySet : container.getEntitySets()) {
            if (entitySet.getName().equals(name)) {
              return entitySet;
            }
          }
        }
      }
    }
    return null;
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
    for (Schema schema : dataServices.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        if (container.getName().equals(entityContainer)) {
          for (AssociationSet associationSet : container.getAssociationSets()) {
            if (associationSet.getAssociation().equals(association)
                && ((associationSet.getEnd1().getEntitySet().equals(sourceEntitySetName) && associationSet.getEnd1()
                    .getRole().equals(sourceEntitySetRole))
                || (associationSet.getEnd2().getEntitySet().equals(sourceEntitySetName) && associationSet.getEnd2()
                    .getRole().equals(sourceEntitySetRole)))) {
              return associationSet;
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
    for (Schema schema : dataServices.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        if (container.getName().equals(entityContainer)) {
          for (FunctionImport function : container.getFunctionImports()) {
            if (function.getName().equals(name)) {
              return function;
            }
          }
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
    return dataServices.getSchemas();
  }

  /**
   * Gets the alias infos.
   *
   * @return the alias infos
   */
  @Override
  public List<AliasInfo> getAliasInfos() {
    List<AliasInfo> aliasInfos = new ArrayList<AliasInfo>();
    for (Schema schema : dataServices.getSchemas()) {
      if (schema.getAlias() != null) {
        aliasInfos.add(new AliasInfo().setAlias(schema.getAlias()).setNamespace(schema.getNamespace()));
      }
    }

    return aliasInfos;
  }
}
