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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.ODataJPAContextMock;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmModel;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataJPAEdmProviderNegativeTest.
 */
public class ODataJPAEdmProviderNegativeTest {

  /** The edm provider. */
  private static ODataJPAEdmProvider edmProvider;

  /**
   * Setup.
   */
  @BeforeClass
  public static void setup() {

    edmProvider = new ODataJPAEdmProvider();
    try {
      Class<? extends ODataJPAEdmProvider> clazz = edmProvider.getClass();
      Field field = clazz.getDeclaredField("schemas");
      field.setAccessible(true);
      List<Schema> schemas = new ArrayList<Schema>();
      schemas.add(new Schema().setNamespace("salesorderprocessing")); // Empty Schema
      field.set(edmProvider, schemas);
      field = clazz.getDeclaredField("oDataJPAContext");
      field.setAccessible(true);
      field.set(edmProvider, ODataJPAContextMock.mockODataJPAContext());
      field = clazz.getDeclaredField("jpaEdmModel");
      field.setAccessible(true);
      field.set(edmProvider, new JPAEdmModel(null, null));
    } catch (IllegalArgumentException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (IllegalAccessException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (NoSuchFieldException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (SecurityException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

  }

  /**
   * Test null get entity container info.
   */
  @Test
  public void testNullGetEntityContainerInfo() {
    EntityContainerInfo entityContainer = null;
    try {
      entityContainer = edmProvider.getEntityContainerInfo("salesorderprocessingContainer");
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    assertNull(entityContainer);
  }

  /**
   * Test null get entity type.
   */
  @Test
  public void testNullGetEntityType() {
    FullQualifiedName entityTypeName = new FullQualifiedName("salesorderprocessing", "SalesOrderHeader");
    try {
      assertNull(edmProvider.getEntityType(entityTypeName));
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Test null get complex type.
   */
  @Test
  public void testNullGetComplexType() {
    FullQualifiedName complexTypeName = new FullQualifiedName("salesorderprocessing", "Address");
    try {
      assertNull(edmProvider.getComplexType(complexTypeName));
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Test get association full qualified name.
   */
  @Test
  public void testGetAssociationFullQualifiedName() {
    Association association = null;
    try {
      association =
          edmProvider.getAssociation(new FullQualifiedName("salesorderprocessing", "SalesOrderHeader_SalesOrderItem"));
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    assertNull(association);
  }

  /**
   * Test get entity set.
   */
  @Test
  public void testGetEntitySet() {
    try {
      assertNull(edmProvider.getEntitySet("salesorderprocessingContainer", "SalesOrderHeaders"));
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Test get association set.
   */
  @Test
  public void testGetAssociationSet() {
    try {
      assertNull(edmProvider.getAssociationSet("salesorderprocessingContainer", new FullQualifiedName(
          "salesorderprocessing", "SalesOrderHeader_SalesOrderItem"), "SalesOrderHeaders", "SalesOrderHeader"));
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

  }

  /**
   * Test null get function import.
   */
  @Test
  public void testNullGetFunctionImport() {

    try {
      assertNull(edmProvider.getFunctionImport("salesorderprocessingContainer", "SalesOrder_FunctionImport1"));
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

  }

  /**
   * Test null get function import 2.
   */
  @Test
  public void testNullGetFunctionImport2() {

    try {
      ODataJPAEdmProvider provider = new ODataJPAEdmProvider();
      try {
        Class<? extends ODataJPAEdmProvider> clazz = provider.getClass();
        Field field = clazz.getDeclaredField("schemas");
        field.setAccessible(true);
        List<Schema> schemas = new ArrayList<Schema>();
        Schema schema = new Schema().setNamespace("salesorderprocessing");
        EntityContainer container = new EntityContainer().setName("salesorderprocessingContainer");
        List<EntityContainer> containerList = new ArrayList<EntityContainer>();
        containerList.add(container); // Empty Container
        schema.setEntityContainers(containerList);
        schemas.add(schema); // Empty Schema
        field.set(provider, schemas);
      } catch (IllegalArgumentException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (IllegalAccessException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (NoSuchFieldException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (SecurityException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }

      assertNull(provider.getFunctionImport("salesorderprocessingContainer", "SalesOrder_FunctionImport1"));
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

  }

  /**
   * Test get schemas.
   */
  @Test
  public void testGetSchemas() {
    try {
      assertNotNull(edmProvider.getSchemas());
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

}
