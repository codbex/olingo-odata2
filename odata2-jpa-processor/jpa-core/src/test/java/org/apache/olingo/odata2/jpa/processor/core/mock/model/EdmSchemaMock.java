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
package org.apache.olingo.odata2.jpa.processor.core.mock.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.AssociationSetEnd;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.FunctionImport;
import org.apache.olingo.odata2.api.edm.provider.Key;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmSchemaMock.
 */
public class EdmSchemaMock {

  /** The Constant ASSOCIATION_ROLE_NAME_ONE. */
  private static final String ASSOCIATION_ROLE_NAME_ONE = "SalesOrderHeader";
  
  /** The Constant ASSOCIATION_NAME. */
  private static final String ASSOCIATION_NAME = "SalesOrderHeader_SalesOrderItem";
  
  /** The Constant ASSOCIATION_SET_NAME. */
  private static final String ASSOCIATION_SET_NAME = "SalesOrderHeader_SalesOrderItemSet";
  
  /** The Constant ASSOCIATION_ROLE_NAME_TWO. */
  private static final String ASSOCIATION_ROLE_NAME_TWO = "SalesOrderItem";
  
  /** The Constant NAMESPACE. */
  private static final String NAMESPACE = "salesorderprocessing";
  
  /** The Constant ENTITY_CONTAINER_NAME. */
  private static final String ENTITY_CONTAINER_NAME = "salesorderprocessingContainer";
  
  /** The Constant ENTITY_NAME_ONE. */
  private static final String ENTITY_NAME_ONE = "SalesOrderHeader";
  
  /** The Constant ENTITY_NAME_TWO. */
  private static final String ENTITY_NAME_TWO = "SalesOrderItem";
  
  /** The Constant ENTITY_SET_NAME_ONE. */
  private static final String ENTITY_SET_NAME_ONE = "SalesOrderHeaders";
  
  /** The Constant FUNCTION_IMPORT_NAME_ONE. */
  private static final String FUNCTION_IMPORT_NAME_ONE = "SalesOrder_FunctionImport1";
  
  /** The Constant FUNCTION_IMPORT_NAME_TWO. */
  private static final String FUNCTION_IMPORT_NAME_TWO = "SalesOrder_FunctionImport2";
  
  /** The Constant ENTITY_SET_NAME_TWO. */
  private static final String ENTITY_SET_NAME_TWO = "SalesOrderItems";
  
  /** The Constant COMPLEX_TYPE_NAME_ONE. */
  private static final String COMPLEX_TYPE_NAME_ONE = "Address";
  
  /** The Constant COMPLEX_TYPE_NAME_TWO. */
  private static final String COMPLEX_TYPE_NAME_TWO = "SalesOrderItemKey";

  /**
   * Creates the mock edm schema.
   *
   * @return the schema
   */
  public static Schema createMockEdmSchema() {
    Schema schema = new Schema();
    schema.setNamespace(NAMESPACE);
    schema.setComplexTypes(createComplexTypes());
    schema.setEntityContainers(createEntityContainer());
    schema.setEntityTypes(createEntityTypes());
    schema.setAssociations(createAssociations());
    return schema;
  }

  /**
   * Creates the entity container.
   *
   * @return the list
   */
  private static List<EntityContainer> createEntityContainer() {
    List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
    EntityContainer entityContainer = new EntityContainer();
    entityContainer.setDefaultEntityContainer(true);
    entityContainer.setName(ENTITY_CONTAINER_NAME);
    entityContainer.setEntitySets(createEntitySets());
    entityContainer.setAssociationSets(createAssociationSets());
    entityContainer.setFunctionImports(createFunctionImports());
    entityContainers.add(entityContainer);
    return entityContainers;
  }

  /**
   * Creates the association sets.
   *
   * @return the list
   */
  private static List<AssociationSet> createAssociationSets() {
    List<AssociationSet> associationSets = new ArrayList<AssociationSet>();
    AssociationSet associationSet = new AssociationSet();
    associationSet.setName(ASSOCIATION_SET_NAME);
    associationSet.setAssociation(new FullQualifiedName(NAMESPACE, ASSOCIATION_NAME));
    associationSet
        .setEnd1(new AssociationSetEnd().setEntitySet(ENTITY_SET_NAME_ONE).setRole(ASSOCIATION_ROLE_NAME_ONE));
    associationSet
        .setEnd2(new AssociationSetEnd().setEntitySet(ENTITY_SET_NAME_TWO).setRole(ASSOCIATION_ROLE_NAME_TWO));
    associationSets.add(associationSet);
    return associationSets;
  }

  /**
   * Creates the entity sets.
   *
   * @return the list
   */
  private static List<EntitySet> createEntitySets() {
    List<EntitySet> entitySets = new ArrayList<EntitySet>();
    EntitySet entitySet = new EntitySet();
    entitySet.setName(ENTITY_SET_NAME_ONE);
    entitySet.setEntityType(new FullQualifiedName(NAMESPACE, ENTITY_NAME_ONE));
    entitySets.add(entitySet);
    entitySet = new EntitySet();
    entitySet.setName(ENTITY_SET_NAME_TWO);
    entitySet.setEntityType(new FullQualifiedName(NAMESPACE, ENTITY_NAME_TWO));
    entitySets.add(entitySet);
    return entitySets;
  }

  /**
   * Creates the function imports.
   *
   * @return the list
   */
  private static List<FunctionImport> createFunctionImports() {
    List<FunctionImport> functionImports = new ArrayList<FunctionImport>();
    FunctionImport functionImport = new FunctionImport();
    functionImport.setName(FUNCTION_IMPORT_NAME_ONE);
    functionImports.add(functionImport);
    functionImport = new FunctionImport();
    functionImport.setName(FUNCTION_IMPORT_NAME_TWO);
    functionImports.add(functionImport);
    return functionImports;
  }

  /**
   * Creates the associations.
   *
   * @return the list
   */
  private static List<Association> createAssociations() {
    List<Association> associations = new ArrayList<Association>();
    Association association = new Association();
    association.setName(ASSOCIATION_NAME);
    association.setEnd1(new AssociationEnd().setMultiplicity(EdmMultiplicity.ONE).setRole(ASSOCIATION_ROLE_NAME_ONE)
        .setType(new FullQualifiedName(NAMESPACE, ENTITY_NAME_ONE)));
    association.setEnd2(new AssociationEnd().setMultiplicity(EdmMultiplicity.MANY).setRole(ASSOCIATION_ROLE_NAME_TWO)
        .setType(new FullQualifiedName(NAMESPACE, ENTITY_NAME_TWO)));
    associations.add(association);
    return associations;
  }

  /**
   * Creates the entity types.
   *
   * @return the list
   */
  private static List<EntityType> createEntityTypes() {
    List<EntityType> entityTypes = new ArrayList<EntityType>();
    EntityType entityType = new EntityType();
    entityType.setName(ENTITY_NAME_ONE);
    String[] keyNamesOne = { "SoId" };
    entityType.setKey(createKey(keyNamesOne));
    entityTypes.add(entityType);

    entityType = new EntityType();
    entityType.setName(ENTITY_NAME_TWO);
    String[] keyNamesTwo = { "SoId", "LiId" };
    entityType.setKey(createKey(keyNamesTwo));
    entityTypes.add(entityType);
    return entityTypes;

  }

  /**
   * Creates the key.
   *
   * @param keyNames the key names
   * @return the key
   */
  private static Key createKey(final String[] keyNames) {
    Key key = new Key();
    List<PropertyRef> keys = new ArrayList<PropertyRef>();
    for (String keyName : keyNames) {
      keys.add(new PropertyRef().setName(keyName));
    }
    key.setKeys(keys);
    return null;
  }

  /**
   * Creates the complex types.
   *
   * @return the list
   */
  private static List<ComplexType> createComplexTypes() {
    List<ComplexType> complexTypes = new ArrayList<ComplexType>();
    ComplexType complexTypeOne = new ComplexType();
    complexTypeOne.setName(COMPLEX_TYPE_NAME_ONE);
    complexTypeOne.setProperties(createComplexTypePropertiesOne());
    complexTypes.add(complexTypeOne);
    ComplexType complexTypeTwo = new ComplexType();
    complexTypeTwo.setName(COMPLEX_TYPE_NAME_TWO);
    complexTypeTwo.setProperties(createComplexTypePropertiesTwo());
    complexTypes.add(complexTypeTwo);
    return complexTypes;
  }

  /**
   * Creates the complex type properties two.
   *
   * @return the list
   */
  private static List<Property> createComplexTypePropertiesTwo() {
    List<Property> properties = new ArrayList<Property>();
    SimpleProperty property = new SimpleProperty();
    property.setName("SoId");
    property.setType(EdmSimpleTypeKind.Int64);
    JPAEdmMapping mapping = new JPAEdmMappingImpl();
    mapping.setJPAColumnName("Sales_Order_Id");
    ((Mapping) mapping).setInternalName("SalesOrderItemKey.SoId");
    property.setMapping((Mapping) mapping);
    properties.add(property);
    property = new SimpleProperty();
    property.setName("LiId");
    property.setType(EdmSimpleTypeKind.Int64);
    mapping = new JPAEdmMappingImpl();
    mapping.setJPAColumnName("Sales_Order_Item_Id");
    property.setMapping((Mapping) mapping);
    properties.add(property);
    return properties;

  }

  /**
   * Creates the complex type properties one.
   *
   * @return the list
   */
  private static List<Property> createComplexTypePropertiesOne() {
    List<Property> properties = new ArrayList<Property>();
    SimpleProperty property = new SimpleProperty();
    property.setName("StreetName");
    property.setType(EdmSimpleTypeKind.String);
    JPAEdmMapping mapping = new JPAEdmMappingImpl();
    mapping.setJPAColumnName("STREET_NAME");
    property.setMapping((Mapping) mapping);
    properties.add(property);
    property = new SimpleProperty();
    property.setName("City");
    property.setType(EdmSimpleTypeKind.String);
    mapping = new JPAEdmMappingImpl();
    mapping.setJPAColumnName("CITY");
    property.setMapping((Mapping) mapping);
    properties.add(property);
    return properties;
  }

}
