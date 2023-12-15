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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.AssociationSetEnd;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityContainerView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntitySetView;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmAssociationSetTest.
 */
public class JPAEdmAssociationSetTest extends JPAEdmTestModelView {

  /** The obj JPA edm association set test. */
  private JPAEdmAssociationSetTest objJPAEdmAssociationSetTest;
  
  /** The obj JPA edm association set. */
  private JPAEdmAssociationSet objJPAEdmAssociationSet;

  /**
   * Sets the up.
   */
  @Before
  public void setUp() {
    objJPAEdmAssociationSetTest = new JPAEdmAssociationSetTest();
    objJPAEdmAssociationSet = new JPAEdmAssociationSet(objJPAEdmAssociationSetTest);
    try {
      objJPAEdmAssociationSet.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

  }

  /**
   * Test get builder.
   */
  @Test
  public void testGetBuilder() {
    assertNotNull(objJPAEdmAssociationSet.getBuilder());
  }

  /**
   * Test get consistent edm association set list.
   */
  @Test
  public void testGetConsistentEdmAssociationSetList() {
    assertNotNull(objJPAEdmAssociationSet.getConsistentEdmAssociationSetList());
  }

  /**
   * Test get edm association set.
   */
  @Test
  public void testGetEdmAssociationSet() {
    assertNotNull(objJPAEdmAssociationSet.getEdmAssociationSet());
  }

  /**
   * Test get edm association.
   */
  @Test
  public void testGetEdmAssociation() {
    assertNotNull(objJPAEdmAssociationSet.getEdmAssociation());
  }

  /**
   * Test is consistent.
   */
  @Test
  public void testIsConsistent() {
    assertTrue(objJPAEdmAssociationSet.isConsistent());
  }

  /**
   * Test get builder idempotent.
   */
  @Test
  public void testGetBuilderIdempotent() {
    JPAEdmBuilder builder1 = objJPAEdmAssociationSet.getBuilder();
    JPAEdmBuilder builder2 = objJPAEdmAssociationSet.getBuilder();

    assertEquals(builder1.hashCode(), builder2.hashCode());
  }

  /**
   * Gets the JPA edm entity container view.
   *
   * @return the JPA edm entity container view
   */
  @Override
  public JPAEdmEntityContainerView getJPAEdmEntityContainerView() {
    return this;
  }

  /**
   * Gets the JPA edm entity set view.
   *
   * @return the JPA edm entity set view
   */
  @Override
  public JPAEdmEntitySetView getJPAEdmEntitySetView() {
    return this;
  }

  /**
   * Gets the JPA edm association view.
   *
   * @return the JPA edm association view
   */
  @Override
  public JPAEdmAssociationView getJPAEdmAssociationView() {
    return this;
  }

  /**
   * Gets the edm association set.
   *
   * @return the edm association set
   */
  @Override
  public AssociationSet getEdmAssociationSet() {
    AssociationSet associationSet = new AssociationSet();
    associationSet.setEnd1(new AssociationSetEnd());
    associationSet.setEnd2(new AssociationSetEnd());

    return associationSet;
  }

  /**
   * Gets the consistent edm association list.
   *
   * @return the consistent edm association list
   */
  @Override
  public List<Association> getConsistentEdmAssociationList() {
    return getEdmAssociationListLocal();
  }

  /**
   * Gets the consistent edm association set list.
   *
   * @return the consistent edm association set list
   */
  @Override
  public List<AssociationSet> getConsistentEdmAssociationSetList() {

    List<AssociationSet> associationSetList = new ArrayList<AssociationSet>();
    associationSetList.add(getEdmAssociationSet());
    associationSetList.add(getEdmAssociationSet());

    return associationSetList;
  }

  /**
   * Gets the consistent edm entity set list.
   *
   * @return the consistent edm entity set list
   */
  @Override
  public List<EntitySet> getConsistentEdmEntitySetList() {
    return getEntitySetListLocal();
  }

  /**
   * Checks if is consistent.
   *
   * @return true, if is consistent
   */
  @Override
  public boolean isConsistent() {
    return true;
  }

  /**
   * Gets the edm schema.
   *
   * @return the edm schema
   */
  @Override
  public Schema getEdmSchema() {
    Schema schema = new Schema();
    schema.setNamespace("salesordereprocessing");
    return schema;
  }

  /**
   * Gets the entity set list local.
   *
   * @return the entity set list local
   */
  private List<EntitySet> getEntitySetListLocal() {
    List<EntitySet> entitySetList = new ArrayList<EntitySet>();

    EntitySet entitySet = new EntitySet();
    entitySet.setName("SalesOrderHeader");
    entitySet.setEntityType(new FullQualifiedName("salesorderprocessing", "SOID"));

    EntitySet entitySet2 = new EntitySet();
    entitySet2.setName("SalesOrderItem");
    entitySet2.setEntityType(new FullQualifiedName("salesorderprocessing", "SOID"));

    entitySetList.add(entitySet);
    entitySetList.add(entitySet2);
    return entitySetList;
  }

  /**
   * Gets the edm association list local.
   *
   * @return the edm association list local
   */
  private List<Association> getEdmAssociationListLocal() {
    List<Association> associationList = new ArrayList<Association>();

    Association association = new Association();
    association.setName("Assoc_SalesOrderHeader_SalesOrderItem");
    association.setEnd1(new AssociationEnd().setType(new FullQualifiedName("salesorderprocessing", "String")).setRole(
        "SalesOrderHeader"));
    association.setEnd2(new AssociationEnd().setType(new FullQualifiedName("salesorderprocessing", "SalesOrderItem"))
        .setRole("SalesOrderItem"));

    associationList.add(association);
    return associationList;
  }

}
