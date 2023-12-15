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
/**
 *
 */
package org.apache.olingo.odata2.jpa.processor.core.jpql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.uri.SelectItem;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLSelectStatementBuilderTest.
 */
public class JPQLSelectStatementBuilderTest {

  /**
   * The jpql select statement builder.
   *
   */
  private JPQLSelectStatementBuilder jpqlSelectStatementBuilder;

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {

  }

  /**
   * Creates the select context.
   *
   * @param orderByExpression the order by expression
   * @param filterExpression the filter expression
   * @return the JPQL select context
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws EdmException the edm exception
   */
  private JPQLSelectContext createSelectContext(final OrderByExpression orderByExpression,
      final FilterExpression filterExpression) throws ODataJPARuntimeException, EdmException {
    // Object Instantiation

    JPQLSelectContext jpqlSelectContextImpl = null;
    GetEntitySetUriInfo getEntitySetView = EasyMock.createMock(GetEntitySetUriInfo.class);

    EdmEntitySet edmEntitySet = EasyMock.createMock(EdmEntitySet.class);
    EdmEntityType edmEntityType = EasyMock.createMock(EdmEntityType.class);
    List<SelectItem> selectItemList = null;

    // Setting up the expected value

    EasyMock.expect(getEntitySetView.getTargetEntitySet()).andStubReturn(edmEntitySet);
    EasyMock.expect(getEntitySetView.getOrderBy()).andStubReturn(orderByExpression);
    EasyMock.expect(getEntitySetView.getSelect()).andStubReturn(selectItemList);
    EasyMock.expect(getEntitySetView.getFilter()).andStubReturn(filterExpression);
    EasyMock.replay(getEntitySetView);
    EasyMock.expect(edmEntitySet.getEntityType()).andStubReturn(edmEntityType);
    EasyMock.replay(edmEntitySet);
    EasyMock.expect(edmEntityType.getMapping()).andStubReturn(null);
    EasyMock.expect(edmEntityType.getName()).andStubReturn("SalesOrderHeader");
    EasyMock.replay(edmEntityType);

    JPQLContextBuilder contextBuilder1 = JPQLContext.createBuilder(JPQLContextType.SELECT, getEntitySetView);
    try {
      jpqlSelectContextImpl = (JPQLSelectContext) contextBuilder1.build();
    } catch (ODataJPAModelException e) {
      fail("Model Exception thrown");
    }

    return jpqlSelectContextImpl;
  }

  /**
   * Test method for {@link org.apache.olingo.odata2.processor.jpa.jpql.JPQLSelectStatementBuilder#build)}.
   *
   * @throws EdmException the edm exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */

  @Test
  public void testBuildSimpleQuery() throws EdmException, ODataJPARuntimeException {
    OrderByExpression orderByExpression = EasyMock.createMock(OrderByExpression.class);
    JPQLSelectContext jpqlSelectContextImpl = createSelectContext(orderByExpression, null);
    jpqlSelectStatementBuilder = new JPQLSelectStatementBuilder(jpqlSelectContextImpl);

    assertEquals("SELECT E1 FROM SalesOrderHeader E1", jpqlSelectStatementBuilder.build().toString());
  }

  /**
   * Test build query with order by.
   *
   * @throws EdmException the edm exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Test
  public void testBuildQueryWithOrderBy() throws EdmException, ODataJPARuntimeException {
    OrderByExpression orderByExpression = EasyMock.createMock(OrderByExpression.class);

    JPQLSelectContext jpqlSelectContextImpl = createSelectContext(orderByExpression, null);
    jpqlSelectContextImpl.setOrderByCollection("E1.soID ASC , E1.buyerId DESC");
    jpqlSelectStatementBuilder = new JPQLSelectStatementBuilder(jpqlSelectContextImpl);

    assertEquals("SELECT E1 FROM SalesOrderHeader E1 ORDER BY E1.soID ASC , E1.buyerId DESC",
        jpqlSelectStatementBuilder.build().toString());
  }

  /**
   * Test build query with filter.
   *
   * @throws EdmException the edm exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Test
  public void testBuildQueryWithFilter() throws EdmException, ODataJPARuntimeException {
    OrderByExpression orderByExpression = EasyMock.createMock(OrderByExpression.class);
    FilterExpression filterExpression = null;// getFilterExpressionMockedObj();
    JPQLSelectContext jpqlSelectContextImpl = createSelectContext(orderByExpression, filterExpression);
    jpqlSelectContextImpl.setWhereExpression("E1.soID >= 1234");

    jpqlSelectStatementBuilder = new JPQLSelectStatementBuilder(jpqlSelectContextImpl);

    assertEquals("SELECT E1 FROM SalesOrderHeader E1 WHERE E1.soID >= 1234", jpqlSelectStatementBuilder.build()
        .toString());
  }

}
