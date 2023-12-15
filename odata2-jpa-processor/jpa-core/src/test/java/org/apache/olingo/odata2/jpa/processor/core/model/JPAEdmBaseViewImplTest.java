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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import jakarta.persistence.metamodel.Metamodel;

import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAMetaModelMock;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmBaseViewImplTest.
 */
public class JPAEdmBaseViewImplTest extends JPAEdmTestModelView {

  /** The obj JPA edm base view impl test. */
  private JPAEdmBaseViewImplTest objJPAEdmBaseViewImplTest;
  
  /** The obj JPA edm base view impl. */
  private JPAEdmBaseViewImpl objJPAEdmBaseViewImpl;

  /**
   * Sets the up.
   */
  @Before
  public void setUp() {
    objJPAEdmBaseViewImplTest = new JPAEdmBaseViewImplTest();
    objJPAEdmBaseViewImpl = new JPAEdmBaseViewImpl(objJPAEdmBaseViewImplTest) {

      @Override
      public JPAEdmBuilder getBuilder() {
        return null;
      }
    };

    objJPAEdmBaseViewImpl = new JPAEdmBaseViewImpl(getJPAMetaModel(), getpUnitName()) {

      @Override
      public JPAEdmBuilder getBuilder() {
        return null;
      }
    };

  }

  /**
   * Test getp unit name.
   */
  @Test
  public void testGetpUnitName() {
    assertTrue(objJPAEdmBaseViewImpl.getpUnitName().equals("salesorderprocessing"));
  }

  /**
   * Test get JPA meta model.
   */
  @Test
  public void testGetJPAMetaModel() {
    assertNotNull(objJPAEdmBaseViewImpl.getJPAMetaModel());
  }

  /**
   * Test is consistent.
   */
  @Test
  public void testIsConsistent() {
    assertTrue(objJPAEdmBaseViewImpl.isConsistent());
  }

  /**
   * Test clean.
   */
  @Test
  public void testClean() {
    objJPAEdmBaseViewImpl.clean();
    assertFalse(objJPAEdmBaseViewImpl.isConsistent());
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

  /**
   * Gets the JPA meta model.
   *
   * @return the JPA meta model
   */
  @Override
  public Metamodel getJPAMetaModel() {
    return new JPAMetaModelMock();
  }

}
