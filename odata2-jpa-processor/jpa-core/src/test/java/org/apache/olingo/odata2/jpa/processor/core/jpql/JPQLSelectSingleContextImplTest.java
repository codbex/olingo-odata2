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

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.SelectItem;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLSelectSingleContext.JPQLSelectSingleContextBuilder;
import org.easymock.EasyMock;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLSelectSingleContextImplTest.
 */
public class JPQLSelectSingleContextImplTest {

  /** The entity type name. */
  private static String entityTypeName = "MockEntity";
  
  /** The fields. */
  private static String[] fields = { "Field1", "Field2" };
  
  /** The key predicates. */
  private static List<KeyPredicate> keyPredicates;

  /** The builder. */
  private static JPQLSelectSingleContextBuilder builder;
  
  /** The select context. */
  private static JPQLSelectSingleContext selectContext;

  /**
   * Builds the context builder.
   *
   * @param isSelectNull the is select null
   */
  private void buildContextBuilder(final boolean isSelectNull) {
    builder = null;
    selectContext = null;
    keyPredicates = new ArrayList<KeyPredicate>();
    GetEntityUriInfo resultsView = EasyMock.createMock(GetEntityUriInfo.class);

    EdmEntitySet entitySet = EasyMock.createMock(EdmEntitySet.class);
    EdmEntityType entityType = EasyMock.createMock(EdmEntityType.class);

    KeyPredicate keyPredicate = EasyMock.createMock(KeyPredicate.class);
    EdmProperty kpProperty = EasyMock.createMock(EdmProperty.class);
    EdmType edmType = EasyMock.createMock(EdmType.class);
    EasyMock.expect(keyPredicate.getLiteral()).andStubReturn("1");
    try {
      EasyMock.expect(kpProperty.getName()).andStubReturn("Field1");
      EasyMock.expect(kpProperty.getType()).andStubReturn(edmType);

    } catch (EdmException e2) {
      fail("this should not happen");
    }
    EasyMock.expect(keyPredicate.getProperty()).andStubReturn(kpProperty);
    EasyMock.replay(edmType, kpProperty, keyPredicate);
    keyPredicates.add(keyPredicate);
    int i = 0;
    try {

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
      EasyMock.replay(entityType);
      EasyMock.expect(entitySet.getEntityType()).andStubReturn(entityType);
      EasyMock.replay(entitySet);

      EasyMock.expect(resultsView.getTargetEntitySet()).andStubReturn(entitySet);
      if (isSelectNull) {
        selectItemList = null;
      }
      EasyMock.expect(resultsView.getSelect()).andStubReturn(selectItemList);
      ArrayList<KeyPredicate> arrayList = new ArrayList<KeyPredicate>();
      arrayList.add(keyPredicate);
      EasyMock.expect(resultsView.getKeyPredicates()).andStubReturn(arrayList);
      EasyMock.replay(resultsView);

    } catch (EdmException e1) {
      fail("Exception not Expected");
    }
    try {
      builder = (JPQLSelectSingleContextBuilder) JPQLContext.createBuilder(JPQLContextType.SELECT_SINGLE, resultsView);

      selectContext = (JPQLSelectSingleContext) builder.build();
    } catch (ODataJPAModelException e) {
      fail("Exception not Expected");
    } catch (ODataJPARuntimeException e) {
      fail("Runtime Exception thrown");
    }
  }

  /**
   * Test entity name throwing exception.
   */
  @Test
  public void testEntityNameThrowingException() {
    // buildSelectContext(false, false, false);
    GetEntityUriInfo resultsView = EasyMock.createMock(GetEntityUriInfo.class);

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
    EasyMock.expect(resultsView.getSelect()).andStubReturn(null);
    EasyMock.expect(resultsView.getFilter()).andStubReturn(null);
    EasyMock.replay(resultsView);
    try {
      JPQLSelectSingleContextBuilder builder1 =
          (JPQLSelectSingleContextBuilder) JPQLContext.createBuilder(JPQLContextType.SELECT_SINGLE, resultsView);
      builder1.build();
      fail("Should not come here");
    } catch (ODataJPAModelException e) {
      fail();
    } catch (ODataJPARuntimeException e) {
      assertTrue(true);
    }
  }

  /**
   * Test slected fields as null.
   */
  @Test
  public void testSlectedFieldsAsNull() {
    buildContextBuilder(true);
    try {
      selectContext = (JPQLSelectSingleContext) builder.build();
      assertEquals("E2", selectContext.getSelectExpression());
    } catch (ODataJPAModelException e) {
      fail();
    } catch (ODataJPARuntimeException e) {
      fail();
    }
  }

  /**
   * Gets the key predicates.
   *
   * @return the key predicates
   */
  @Test
  public void getKeyPredicates() {
    buildContextBuilder(false);
    assertEquals(keyPredicates.size(), selectContext.getKeyPredicates().size());
    assertEquals(keyPredicates, selectContext.getKeyPredicates());
  }

  /**
   * Test get JPA entity name.
   */
  @Test
  public void testGetJPAEntityName() {
    buildContextBuilder(false);
    assertEquals(JPQLSelectSingleContextImplTest.entityTypeName, selectContext.getJPAEntityName());
  }

  /**
   * Test get type.
   */
  @Test
  public void testGetType() {
    buildContextBuilder(false);
    assertEquals(JPQLContextType.SELECT_SINGLE, selectContext.getType());
  }

  /**
   * Test create builder.
   */
  @Test
  public void testCreateBuilder() {
    buildContextBuilder(false);
    assertEquals(JPQLSelectSingleContextBuilder.class.toString(), builder.getClass().toString());
  }

}
