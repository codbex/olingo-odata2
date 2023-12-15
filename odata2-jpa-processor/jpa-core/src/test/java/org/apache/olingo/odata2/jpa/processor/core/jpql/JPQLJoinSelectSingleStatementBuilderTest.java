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
package org.apache.olingo.odata2.jpa.processor.core.jpql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAJoinClause;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLJoinSelectSingleContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLJoinSelectSingleStatementBuilderTest.
 */
public class JPQLJoinSelectSingleStatementBuilderTest {
  
  /** The context. */
  JPQLJoinSelectSingleContextView context = null;

  /**
   * Sets the up before class.
   *
   * @throws Exception the exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  /**
   * Tear down after class.
   *
   * @throws Exception the exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  /**
   * Sets the up.
   *
   * @param joinClauseList the new up
   * @throws Exception the exception
   */
  public void setUp(final List<JPAJoinClause> joinClauseList) throws Exception {
    context = EasyMock.createMock(JPQLJoinSelectSingleContextView.class);
    EasyMock.expect(context.getJPAEntityAlias()).andStubReturn("gt1");
    EasyMock.expect(context.getJPAEntityName()).andStubReturn("SOHeader");
    EasyMock.expect(context.getType()).andStubReturn(JPQLContextType.SELECT);
    EasyMock.expect(context.getKeyPredicates()).andStubReturn(createKeyPredicates());
    EasyMock.expect(context.getSelectExpression()).andStubReturn("gt1");
    EasyMock.expect(context.getJPAJoinClauses()).andStubReturn(joinClauseList);
    context.setJPQLStatement("SELECT gt1 FROM SOHeader soh JOIN "
        + "soh.soItem soi JOIN soi.material mat WHERE soh.soId = 1 AND " +
            "soi.shId = soh.soId AND mat.id = 'abc'");
    EasyMock.replay(context);
  }

  /**
   * Gets the join clause list.
   *
   * @return the join clause list
   */
  private List<JPAJoinClause> getJoinClauseList() {
    List<JPAJoinClause> joinClauseList = new ArrayList<JPAJoinClause>();
    JPAJoinClause jpaOuterJoinClause =
        new JPAJoinClause("SOHeader", "soh", null, null, "soh.soId = 1", JPAJoinClause.JOIN.LEFT);
    joinClauseList.add(jpaOuterJoinClause);
    jpaOuterJoinClause =
        new JPAJoinClause("SOHeader", "soh", "soItem", "soi", "soi.shId = soh.soId", JPAJoinClause.JOIN.LEFT);
    joinClauseList.add(jpaOuterJoinClause);
    jpaOuterJoinClause =
        new JPAJoinClause("SOItem", "si", "material", "mat", "mat.id = 'abc'", JPAJoinClause.JOIN.LEFT);
    joinClauseList.add(jpaOuterJoinClause);
    return joinClauseList;
  }

  /**
   * Tear down.
   *
   * @throws Exception the exception
   */
  @After
  public void tearDown() throws Exception {}

  /**
   * Test build.
   *
   * @throws Exception the exception
   */
  @Test
  public void testBuild() throws Exception {
    setUp(getJoinClauseList());
    JPQLJoinSelectSingleStatementBuilder jpqlJoinSelectsingleStatementBuilder =
        new JPQLJoinSelectSingleStatementBuilder(context);
    try {
      JPQLStatement jpqlStatement = jpqlJoinSelectsingleStatementBuilder.build();
      assertEquals(
          "SELECT gt1 FROM SOHeader soh JOIN soh.soItem soi JOIN soi.material mat WHERE soh.soId = 1 AND " +
              "soi.shId = soh.soId AND mat.id = 'abc'",
              jpqlStatement.toString());
    } catch (ODataJPARuntimeException e) {
      fail("Should not have come here");
    }

  }

  /**
   * Creates the key predicates.
   *
   * @return the list
   * @throws EdmException the edm exception
   */
  private List<KeyPredicate> createKeyPredicates() throws EdmException {
    KeyPredicate keyPredicate = EasyMock.createMock(KeyPredicate.class);
    EasyMock.expect(keyPredicate.getLiteral()).andStubReturn("1");
    EdmProperty edmProperty = EasyMock.createMock(EdmProperty.class);
    EdmMapping edmMapping = EasyMock.createMock(EdmMapping.class);
    EasyMock.expect(edmMapping.getInternalName()).andStubReturn("soid");
    EasyMock.expect(edmProperty.getMapping()).andStubReturn(edmMapping);
    EdmSimpleType edmType = EasyMock.createMock(EdmSimpleType.class);
    EasyMock.expect(edmProperty.getType()).andStubReturn(edmType);
    EasyMock.expect(keyPredicate.getProperty()).andStubReturn(edmProperty);

    EasyMock.replay(edmType, edmMapping, edmProperty, keyPredicate);
    List<KeyPredicate> keyPredicates = new ArrayList<KeyPredicate>();
    keyPredicates.add(keyPredicate);
    return keyPredicates;
  }

  /**
   * Test join clause as null.
   *
   * @throws Exception the exception
   */
  @Test
  public void testJoinClauseAsNull() throws Exception {
    setUp(null);
    JPQLJoinSelectSingleStatementBuilder jpqlJoinSelectsingleStatementBuilder =
        new JPQLJoinSelectSingleStatementBuilder(context);
    try {
      jpqlJoinSelectsingleStatementBuilder.build();
      fail("Should not have come here");
    } catch (ODataJPARuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test join clause list as empty.
   *
   * @throws Exception the exception
   */
  @Test
  public void testJoinClauseListAsEmpty() throws Exception {
    List<JPAJoinClause> joinClauseList = new ArrayList<JPAJoinClause>();
    setUp(joinClauseList);
    JPQLJoinSelectSingleStatementBuilder jpqlJoinSelectsingleStatementBuilder =
        new JPQLJoinSelectSingleStatementBuilder(context);
    try {
      jpqlJoinSelectsingleStatementBuilder.build();
      fail("Should not have come here");
    } catch (ODataJPARuntimeException e) {
      assertTrue(true);
    }
  }

}
