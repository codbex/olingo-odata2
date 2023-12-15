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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.SelectItem;
import org.apache.olingo.odata2.api.uri.expression.ExpressionKind;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderExpression;
import org.apache.olingo.odata2.api.uri.expression.PropertyExpression;
import org.apache.olingo.odata2.api.uri.expression.SortOrder;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLSelectContext.JPQLSelectContextBuilder;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLSelectContextImplTest.
 */
public class JPQLSelectContextImplTest {

  /** The entity type name. */
  private static String entityTypeName = "MockEntity";
  
  /** The fields. */
  private static String[] fields = { "Field1", "Field2" };
  
  /** The key predicates. */
  private static List<KeyPredicate> keyPredicates;
  
  /** The order type. */
  private static SortOrder[] orderType = { SortOrder.asc, SortOrder.desc };

  /** The builder. */
  private static JPQLSelectContextBuilder builder;
  
  /** The select context. */
  private static JPQLSelectContext selectContext;

  /**
   * Setup.
   */
  @BeforeClass
  public static void setup() {

  }

  /**
   * Builds the select context.
   *
   * @param orderByIsNull the order by is null
   * @param selectFieldsIsNull the select fields is null
   * @param filterIsNull the filter is null
   * @param isTopNull the is top null
   * @param isSkipNull the is skip null
   */
  private void buildSelectContext(final boolean orderByIsNull, final boolean selectFieldsIsNull,
      final boolean filterIsNull, final boolean isTopNull, final boolean isSkipNull) {
    builder = null;
    selectContext = null;
    keyPredicates = new ArrayList<KeyPredicate>();
    GetEntitySetUriInfo resultsView = EasyMock.createMock(GetEntitySetUriInfo.class);

    EdmEntitySet entitySet = EasyMock.createMock(EdmEntitySet.class);
    EdmEntityType entityType = EasyMock.createMock(EdmEntityType.class);
    KeyPredicate keyPredicate = EasyMock.createMock(KeyPredicate.class);
    EdmProperty kpProperty = EasyMock.createMock(EdmProperty.class);
    EdmType edmType1 = EasyMock.createMock(EdmType.class);
    EdmMapping mapping = EasyMock.createMock(EdmMapping.class);
    EasyMock.expect(keyPredicate.getLiteral()).andStubReturn("1");
    try {
      EasyMock.expect(mapping.getInternalName()).andStubReturn("Field1");
      EasyMock.expect(kpProperty.getMapping()).andStubReturn(mapping);
      EasyMock.expect(kpProperty.getType()).andStubReturn(edmType1);

    } catch (EdmException e2) {
      fail("this should not happen");
    }
    EasyMock.expect(keyPredicate.getProperty()).andStubReturn(kpProperty);
    EasyMock.replay(mapping, edmType1, kpProperty, keyPredicate);
    keyPredicates.add(keyPredicate);
    int i = 0;
    List<OrderExpression> orderList = new ArrayList<OrderExpression>(2);
    do {

      EdmType edmType = EasyMock.createMock(EdmType.class);
      try {
        EasyMock.expect(edmType.getName()).andStubReturn(fields[i]);
        EasyMock.replay(edmType);
      } catch (EdmException e2) {
        fail("Exception not Expected");
      }

      PropertyExpression commonExpression = EasyMock.createMock(PropertyExpression.class);
      EasyMock.expect(commonExpression.getKind()).andReturn(ExpressionKind.PROPERTY).anyTimes();
      EasyMock.expect(commonExpression.getEdmType()).andStubReturn(edmType);

      EdmProperty edmTyped = EasyMock.createMock(EdmProperty.class);
      EdmMapping edmMapping = EasyMock.createMock(EdmMapping.class);
      EasyMock.expect(edmMapping.getInternalName()).andStubReturn(fields[i]);
      try {
        EasyMock.expect(edmTyped.getMapping()).andStubReturn(edmMapping);
      } catch (EdmException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      EasyMock.expect(commonExpression.getEdmProperty()).andStubReturn(edmTyped);
      OrderExpression order = EasyMock.createMock(OrderExpression.class);
      EasyMock.expect(order.getExpression()).andStubReturn(commonExpression);
      EasyMock.expect(order.getSortOrder()).andStubReturn(orderType[i]);
      EasyMock.replay(edmMapping, edmTyped, commonExpression);
      EasyMock.replay(order);

      orderList.add(order);

    } while (++i < 2);

    OrderByExpression orderBy = EasyMock.createMock(OrderByExpression.class);
    EasyMock.expect(orderBy.getOrders()).andStubReturn(orderList);
    EasyMock.replay(orderBy);

    try {
      i = 0;
      List<SelectItem> selectItemList = new ArrayList<SelectItem>(2);
      do {
        EdmMapping edmMapping = EasyMock.createMock(EdmMapping.class);
        EasyMock.expect(edmMapping.getInternalName()).andStubReturn(fields[i]);
        EdmProperty edmProperty = EasyMock.createMock(EdmProperty.class);
        EasyMock.expect(edmProperty.getMapping()).andStubReturn(edmMapping);
        EasyMock.replay(edmMapping, edmProperty);

        SelectItem selectItem = EasyMock.createMock(SelectItem.class);
        EasyMock.expect(selectItem.getProperty()).andStubReturn(edmProperty);
        EasyMock.replay(selectItem);

        selectItemList.add(selectItem);

      } while (++i < 2);
      EasyMock.expect(entityType.getMapping()).andStubReturn(null);
      EasyMock.expect(entityType.getName()).andStubReturn(entityTypeName);
      EasyMock.expect(entityType.getKeyProperties()).andStubReturn(getLocalKeyProperties());

      EasyMock.replay(entityType);
      EasyMock.expect(entitySet.getEntityType()).andStubReturn(entityType);
      EasyMock.replay(entitySet);

      EasyMock.expect(resultsView.getTargetEntitySet()).andStubReturn(entitySet);
      if (orderByIsNull) {
        EasyMock.expect(resultsView.getOrderBy()).andStubReturn(null);
      } else {
        EasyMock.expect(resultsView.getOrderBy()).andStubReturn(orderBy);
      }
      if (selectFieldsIsNull) {
        EasyMock.expect(resultsView.getSelect()).andStubReturn(null);
      } else {
        EasyMock.expect(resultsView.getSelect()).andStubReturn(selectItemList);
      }
      // FilterExpression filterExpression =
      // EasyMock.createMock(FilterExpression.class);
      // EasyMock.expect(filterExpression.g)
      if (filterIsNull) {
        EasyMock.expect(resultsView.getFilter()).andStubReturn(null);
      } else {
        EasyMock.expect(resultsView.getFilter()).andStubReturn(
            getFilterExpressionMockedObj(ExpressionKind.PROPERTY, "SalesOrder"));
      }
      if (isTopNull) {
        EasyMock.expect(resultsView.getTop()).andStubReturn(null);
      } else {
        EasyMock.expect(resultsView.getTop()).andStubReturn(10);
      }
      if (isSkipNull) {
        EasyMock.expect(resultsView.getSkip()).andStubReturn(null);
      } else {
        EasyMock.expect(resultsView.getSkip()).andStubReturn(0);
      }
      EasyMock.expect(resultsView.getKeyPredicates()).andStubReturn(keyPredicates);
      EasyMock.replay(resultsView);

    } catch (EdmException e1) {
      fail("Exception not Expected");
    }
    try {
      builder = (JPQLSelectContextBuilder) JPQLContext.createBuilder(JPQLContextType.SELECT, resultsView);

      selectContext = (JPQLSelectContext) builder.build();
    } catch (ODataJPAModelException e) {
      fail("Exception not Expected");
    } catch (ODataJPARuntimeException e) {
      fail("Runtime Exception thrown");
    }
  }

  /**
   * Gets the local key properties.
   *
   * @return the local key properties
   */
  private List<EdmProperty> getLocalKeyProperties() {
    List<EdmProperty> propertyList = new ArrayList<EdmProperty>();
    EdmProperty edmProperty = EasyMock.createMock(EdmProperty.class);
    try {
      EasyMock.expect(edmProperty.getName()).andStubReturn("Field1");
      EasyMock.expect(edmProperty.getMapping()).andStubReturn(null);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(edmProperty);
    propertyList.add(edmProperty);
    return propertyList;
  }

  /**
   * Test entity name throwing exception.
   */
  @Test
  public void testEntityNameThrowingException() {
    // buildSelectContext(false, false, false);
    GetEntitySetUriInfo resultsView = EasyMock.createMock(GetEntitySetUriInfo.class);

    EdmEntitySet entitySet = EasyMock.createMock(EdmEntitySet.class);
    EdmEntityType entityType = EasyMock.createMock(EdmEntityType.class);

    try {
      EasyMock.expect(entityType.getName()).andStubThrow(new EdmException(null));
      EasyMock.expect(entitySet.getEntityType()).andStubThrow(new EdmException(null));
    } catch (EdmException e1) {
      // throw new ODataException();
    }

    EasyMock.replay(entityType);
    EasyMock.replay(entitySet);

    EasyMock.expect(resultsView.getTargetEntitySet()).andStubReturn(entitySet);
    EasyMock.expect(resultsView.getOrderBy()).andStubReturn(null);
    EasyMock.expect(resultsView.getSelect()).andStubReturn(null);
    EasyMock.expect(resultsView.getFilter()).andStubReturn(null);
    EasyMock.replay(resultsView);
    try {
      JPQLSelectContextBuilder builder1 =
          (JPQLSelectContextBuilder) JPQLContext.createBuilder(JPQLContextType.SELECT, resultsView);

      builder1.build();
      fail("Should not come here");
    } catch (ODataJPAModelException e) {
      fail();
    } catch (ODataJPARuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test select fields as null.
   */
  @Test
  public void testSelectFieldsAsNull() {
    buildSelectContext(false, true, true, true, true);

    try {
      selectContext = (JPQLSelectContext) builder.build();
      assertEquals("E2", selectContext.getSelectExpression());
    } catch (ODataJPAModelException e) {
      fail();
    } catch (ODataJPARuntimeException e) {
      fail();
    }
  }

  /**
   * Test get order by collection.
   */
  @Test
  public void testGetOrderByCollection() {
    buildSelectContext(false, false, true, true, true);
    assertEquals("E1.Field1 , E1.Field2 DESC", selectContext.getOrderByCollection());
  }

  /**
   * Test get where expression.
   */
  @Test
  public void testGetWhereExpression() {
    buildSelectContext(false, false, true, true, true);
    // fail("Not yet implemented");
  }

  /**
   * Test get JPA entity name.
   */
  @Test
  public void testGetJPAEntityName() {
    buildSelectContext(false, false, true, true, true);
    assertEquals(JPQLSelectContextImplTest.entityTypeName, selectContext.getJPAEntityName());
  }

  /**
   * Test get type.
   */
  @Test
  public void testGetType() {
    buildSelectContext(false, false, true, true, true);
    assertEquals(JPQLContextType.SELECT, selectContext.getType());
  }

  /**
   * Test create builder.
   */
  @Test
  public void testCreateBuilder() {
    buildSelectContext(false, false, true, true, true);
    assertEquals(JPQLSelectContextBuilder.class.toString(), builder.getClass().toString());
  }

  /**
   * Test entity set as null.
   */
  @Test
  public void testEntitySetAsNull() {
    buildSelectContext(false, false, true, true, true);
    try {
      JPQLSelectContextBuilder builder =
          (JPQLSelectContextBuilder) JPQLContext.createBuilder(JPQLContextType.SELECT, null);

      JPQLSelectContext selectContext1 = (JPQLSelectContext) builder.build();
      assertNull(selectContext1.getJPAEntityAlias());
      assertNull(selectContext1.getJPAEntityName());
      assertNull(selectContext1.getOrderByCollection());
      assertNull(selectContext1.getSelectExpression());
      assertNull(selectContext1.getType());
      assertNull(selectContext1.getWhereExpression());
    } catch (ODataJPAModelException e) {
      fail("Exception not Expected");
    } catch (ODataJPARuntimeException e) {
      fail("Runtime Exception thrown");
    }
  }

  /**
   * Test ordering with skip.
   */
  @Test
  public void testOrderingWithSkip() {
    buildSelectContext(true, false, true, true, false);
    assertEquals("E1.Field1", selectContext.getOrderByCollection());

  }

  /**
   * Test ordering with top.
   */
  @Test
  public void testOrderingWithTop() {
    buildSelectContext(true, false, true, false, true);
    assertEquals("E1.Field1", selectContext.getOrderByCollection());
  }

  /**
   * Test order by top skip as null.
   */
  @Test
  public void testOrderByTopSkipAsNull() {
    buildSelectContext(true, true, true, true, true);
    assertNull(selectContext.getOrderByCollection());
  }

  /**
   * Test filter.
   */
  @Test
  public void testFilter() {
    buildSelectContext(true, false, false, false, false);
    assertEquals("E1.field", selectContext.whereCondition);

  }

  /**
   * Gets the filter expression mocked obj.
   *
   * @param leftOperandExpKind the left operand exp kind
   * @param propertyName the property name
   * @return the filter expression mocked obj
   * @throws EdmException the edm exception
   */
  private FilterExpression getFilterExpressionMockedObj(final ExpressionKind leftOperandExpKind,
      final String propertyName) throws EdmException {
    FilterExpression filterExpression = EasyMock.createMock(FilterExpression.class);
    EasyMock.expect(filterExpression.getKind()).andStubReturn(ExpressionKind.FILTER);
    EasyMock.expect(filterExpression.getExpression()).andStubReturn(
        getPropertyExpressionMockedObj(leftOperandExpKind, propertyName));
    EasyMock.replay(filterExpression);
    return filterExpression;
  }

  /**
   * Gets the property expression mocked obj.
   *
   * @param expKind the exp kind
   * @param propertyName the property name
   * @return the property expression mocked obj
   */
  private PropertyExpression getPropertyExpressionMockedObj(final ExpressionKind expKind, final String propertyName) {
    PropertyExpression leftOperandPropertyExpresion = EasyMock.createMock(PropertyExpression.class);
    EasyMock.expect(leftOperandPropertyExpresion.getKind()).andReturn(ExpressionKind.PROPERTY);
    EasyMock.expect(leftOperandPropertyExpresion.getPropertyName()).andReturn(propertyName);
    EdmProperty edmtTyped = EasyMock.createMock(EdmProperty.class);
    EdmMapping mapping = EasyMock.createMock(EdmMapping.class);
    EasyMock.expect(mapping.getInternalName()).andStubReturn("field");
    try {
      EasyMock.expect(edmtTyped.getMapping()).andStubReturn(mapping);
    } catch (EdmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    EasyMock.expect(leftOperandPropertyExpresion.getEdmProperty()).andReturn(edmtTyped);
    EasyMock.replay(mapping, edmtTyped, leftOperandPropertyExpresion);
    return leftOperandPropertyExpresion;
  }

}
