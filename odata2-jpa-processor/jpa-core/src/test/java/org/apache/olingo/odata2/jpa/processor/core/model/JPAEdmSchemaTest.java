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
package org.apache.olingo.odata2.jpa.processor.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import jakarta.persistence.metamodel.Metamodel;

import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAMetaModelMock;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmSchemaTest.
 */
public class JPAEdmSchemaTest extends JPAEdmTestModelView {
  
  /** The obj JPA edm schema test. */
  private JPAEdmSchemaTest objJPAEdmSchemaTest;
  
  /** The obj JPA edm schema. */
  private JPAEdmSchema objJPAEdmSchema;

  /**
   * Sets the up.
   */
  @Before
  public void setUp() {
    objJPAEdmSchemaTest = new JPAEdmSchemaTest();
    objJPAEdmSchema = new JPAEdmSchema(objJPAEdmSchemaTest);
    // building schema is not required as downstream structure already tested

  }

  /**
   * Test clean.
   */
  @Test
  public void testClean() {
    assertTrue(objJPAEdmSchema.isConsistent());
    objJPAEdmSchema.clean();
    assertFalse(objJPAEdmSchema.isConsistent());
  }

  /**
   * Test get edm schema.
   */
  @Test
  public void testGetEdmSchema() {
    assertNull(objJPAEdmSchema.getEdmSchema());
  }

  /**
   * Test get JPA edm entity container view.
   */
  @Test
  public void testGetJPAEdmEntityContainerView() {
    assertNull(objJPAEdmSchema.getJPAEdmEntityContainerView());
  }

  /**
   * Test get JPA edm complex type view.
   */
  @Test
  public void testGetJPAEdmComplexTypeView() {
    assertNull(objJPAEdmSchema.getJPAEdmComplexTypeView());
  }

  /**
   * Test get builder.
   */
  @Test
  public void testGetBuilder() {
    assertNotNull(objJPAEdmSchema.getBuilder());
  }

  /**
   * Test get builder idempotent.
   */
  @Test
  public void testGetBuilderIdempotent() {
    JPAEdmBuilder builder1 = objJPAEdmSchema.getBuilder();
    JPAEdmBuilder builder2 = objJPAEdmSchema.getBuilder();

    assertEquals(builder1.hashCode(), builder2.hashCode());
  }

  /**
   * Test get JPA edm association view.
   */
  @Test
  public void testGetJPAEdmAssociationView() {
    assertNull(objJPAEdmSchema.getJPAEdmAssociationView());
  }

  /**
   * Test is consistent.
   */
  @Test
  public void testIsConsistent() {
    assertTrue(objJPAEdmSchema.isConsistent());
    objJPAEdmSchema.clean();
    assertFalse(objJPAEdmSchema.isConsistent());
  }

  /**
   * Gets the JPA meta model.
   *
   * @return the JPA meta model
   */
  @Override
  public Metamodel getJPAMetaModel() {
    return new JPAMetaModelMock();
  }

  /**
   * Gets the p unit name.
   *
   * @return the p unit name
   */
  @Override
  public String getpUnitName() {
    return "salesorderprocessing";
  }

}
