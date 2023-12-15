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
package org.apache.olingo.odata2.fit.mapping;

import java.util.Arrays;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Key;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.api.exception.ODataException;

// TODO: Auto-generated Javadoc
/**
 * The Class MapProvider.
 */
public class MapProvider extends EdmProvider {

  /** The Constant NAMESPACE. */
  private static final String NAMESPACE = "map";
  
  /** The p1. */
  private static int P1 = 0;
  
  /** The p2. */
  private static int P2 = 1;
  
  /** The p3. */
  private static int P3 = 2;
  
  /** The entitytype. */
  private static int ENTITYTYPE = 3;
  
  /** The entityset. */
  private static int ENTITYSET = 4;
  
  /** The mapping container. */
  private static String MAPPING_CONTAINER = "mappingContainer";

  /** The edm. */
  private static int EDM = 0;
  
  /** The backend. */
  private static int BACKEND = 1;

  /** The mapping. */
  private final String[][] mapping = {
      { "p1", "P01" },
      { "p2", "P02" },
      { "p3", "P03" },
      { "mapping", "MAPPING" },
      { "mappings", "MAPPINGS" }
  };

  /**
   * Instantiates a new map provider.
   */
  public MapProvider() {
    buildSchema();
  }

  /** The schemas. */
  private List<Schema> schemas;
  
  /** The property ref. */
  private PropertyRef propertyRef;
  
  /** The key. */
  private Key key;
  
  /** The property 1. */
  private Property property1;
  
  /** The property 2. */
  private Property property2;
  
  /** The property 3. */
  private Property property3;
  
  /** The entity type. */
  private EntityType entityType;
  
  /** The entity set. */
  private EntitySet entitySet;
  
  /** The entity container. */
  private EntityContainer entityContainer;
  
  /** The schema. */
  private Schema schema;

  /**
   * Gets the schemas.
   *
   * @return the schemas
   * @throws ODataException the o data exception
   */
  @Override
  public List<Schema> getSchemas() throws ODataException {
    return schemas;
  }

  /**
   * Builds the schema.
   */
  private void buildSchema() {
    propertyRef = new PropertyRef();
    propertyRef.setName("p1");

    key = new Key();
    key.setKeys(Arrays.asList(propertyRef));

    property1 =
        new SimpleProperty().setName(mapping[P1][EDM]).setType(EdmSimpleTypeKind.String).setMapping(
            new Mapping().setObject(mapping[P1][BACKEND]));
    property2 =
        new SimpleProperty().setName(mapping[P2][EDM]).setType(EdmSimpleTypeKind.String).setMapping(
            new Mapping().setObject(mapping[P2][BACKEND]));
    property3 =
        new SimpleProperty().setName(mapping[P3][EDM]).setType(EdmSimpleTypeKind.String).setMapping(
            new Mapping().setObject(mapping[P3][BACKEND]));

    entityType = new EntityType();
    entityType.setName(mapping[ENTITYTYPE][EDM]);
    entityType.setKey(key);
    entityType.setProperties(Arrays.asList(property1, property2, property3));
    entityType.setMapping(new Mapping().setObject(mapping[ENTITYTYPE][BACKEND]));

    entitySet = new EntitySet();
    entitySet.setName(mapping[ENTITYSET][EDM]);
    entitySet.setEntityType(new FullQualifiedName(NAMESPACE, mapping[ENTITYTYPE][EDM]));
    entitySet.setMapping(new Mapping().setObject(mapping[ENTITYSET][BACKEND]));

    entityContainer = new EntityContainer();
    entityContainer.setDefaultEntityContainer(true);
    entityContainer.setName(MAPPING_CONTAINER);
    entityContainer.setEntitySets(Arrays.asList(entitySet));

    schema = new Schema();
    schema.setNamespace("mapping");
    schema.setAlias(NAMESPACE);
    schema.setEntityContainers(Arrays.asList(entityContainer));
    schema.setEntityTypes(Arrays.asList(entityType));

    schemas = Arrays.asList(schema);
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
    if (!NAMESPACE.equals(edmFQName.getNamespace()) || !mapping[ENTITYTYPE][EDM].equals(edmFQName.getName())) {
      throw new ODataException("not found: " + edmFQName);
    }

    return entityType;
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
    if (!MAPPING_CONTAINER.equals(entityContainer) || !mapping[ENTITYSET][EDM].equals(name)) {
      throw new ODataException("not found: " + entityContainer + ", " + name);
    }

    return entitySet;
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
    EntityContainerInfo entityContainerInfo = null;

    if (MAPPING_CONTAINER.equals(name) || (name == null)) {
      entityContainerInfo = new EntityContainerInfo().setName(MAPPING_CONTAINER).setDefaultEntityContainer(true);
    }

    return entityContainerInfo;
  }

}
