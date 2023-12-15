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
package org.apache.olingo.odata2.jpa.processor.core.access.data;

import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.FlushModeType;
import jakarta.persistence.LockModeType;
import jakarta.persistence.Parameter;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
import jakarta.persistence.metamodel.Metamodel;

import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.edm.EdmConcurrencyMode;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.NavigationSegment;
import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAQueryExtensionEntityListener;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATombstoneEntityListener;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATransaction;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAPaging;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.data.SalesOrderHeader;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAProcessorImplTest.
 */
public class JPAProcessorImplTest {

  // -------------------------------- Common Start ------------------------------------common in
  /** The Constant STR_LOCAL_URI. */
  // ODataJPADefaultProcessorTest as well
  private static final String STR_LOCAL_URI = "http://localhost:8080/org.apache.olingo.odata2.processor.ref.web/";
  
  /** The Constant SALESORDERPROCESSING_CONTAINER. */
  private static final String SALESORDERPROCESSING_CONTAINER = "salesorderprocessingContainer";
  
  /** The Constant SO_ID. */
  private static final String SO_ID = "SoId";
  
  /** The Constant SALES_ORDER. */
  private static final String SALES_ORDER = "SalesOrder";
  
  /** The Constant SALES_ORDER_HEADERS. */
  private static final String SALES_ORDER_HEADERS = "SalesOrderHeaders";
  // -------------------------------- Common End ------------------------------------

  /** The obj JPA processor impl. */
  JPAProcessorImpl objJPAProcessorImpl;

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    objJPAProcessorImpl = new JPAProcessorImpl(getLocalmockODataJPAContext());
  }

  /**
   * Test process get entity set count uri info.
   */
  @Test
  public void testProcessGetEntitySetCountUriInfo() {
    try {
      Assert.assertEquals(11, objJPAProcessorImpl.process(getEntitySetCountUriInfo()));
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Test process get entity count uri info.
   */
  @Test
  public void testProcessGetEntityCountUriInfo() {
    try {
      Assert.assertEquals(11, objJPAProcessorImpl.process(getEntityCountUriInfo()));
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Test process get entity set uri info.
   */
  @Test
  public void testProcessGetEntitySetUriInfo() {
    try {
      Assert.assertNotNull(objJPAProcessorImpl.process(getEntitySetUriInfo()));
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }
  
  /**
   * Test process get entity set uri info with listener.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testProcessGetEntitySetUriInfoWithListener() throws EdmException {
    try {
      Assert.assertNotNull(objJPAProcessorImpl.process((GetEntitySetUriInfo)mockURIInfoWithTopSkipInlineListener()));
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }
  
  /**
   * Test process get entity link uri info.
   */
  @Test
  public void testProcessGetEntityLinkUriInfo() {
    try {
      Assert.assertNotNull(objJPAProcessorImpl.process(getEntityLinkUriInfo()));
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }
  
  /**
   * Test process get entity set links uri info.
   */
  @Test
  public void testProcessGetEntitySetLinksUriInfo() {
    try {
      Assert.assertNotNull(objJPAProcessorImpl.process(getEntitySetLinksUriInfo()));
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }
  
  /**
   * Test process function import uri info.
   *
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Test(expected = ODataJPARuntimeException.class)
  public void testProcessFunctionImportUriInfo() throws ODataJPARuntimeException {
    try {
     objJPAProcessorImpl.process(getFunctionImportUriInfo());
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } 
  }

  /**
   * Test process delete uri info.
   */
  @Test
  public void testProcessDeleteUriInfo() {
    try {
      Assert.assertNotNull(objJPAProcessorImpl.process(getDeletetUriInfo(), "application/xml"));
      Assert.assertEquals(new Address(), objJPAProcessorImpl.process(getDeletetUriInfo(), "application/xml"));
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Test process delete uri info negative.
   */
  @Test
  public void testProcessDeleteUriInfoNegative() {
    try {
      Assert.assertNotNull(objJPAProcessorImpl.process(getDeletetUriInfo(), "application/xml"));
      Assert.assertNotSame(new Object(), objJPAProcessorImpl.process(getDeletetUriInfo(), "application/xml"));
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  // ---------------------------- Common Code Start ---------------- TODO - common in ODataJPADefaultProcessorTest as
  // well

  /**
   * Gets the deletet uri info.
   *
   * @return the deletet uri info
   */
  private DeleteUriInfo getDeletetUriInfo() {
    UriInfo objUriInfo = EasyMock.createMock(UriInfo.class);
    EasyMock.expect(objUriInfo.getStartEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getTargetEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getSelect()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getOrderBy()).andStubReturn(getOrderByExpression());
    EasyMock.expect(objUriInfo.getTop()).andStubReturn(getTop());
    EasyMock.expect(objUriInfo.getSkip()).andStubReturn(getSkip());
    EasyMock.expect(objUriInfo.getInlineCount()).andStubReturn(getInlineCount());
    EasyMock.expect(objUriInfo.getFilter()).andStubReturn(getFilter());
    EasyMock.expect(objUriInfo.getKeyPredicates()).andStubReturn(getKeyPredicates());
    EasyMock.expect(objUriInfo.isLinks()).andStubReturn(false);
    EasyMock.expect(objUriInfo.getNavigationSegments()).andStubReturn(new ArrayList<NavigationSegment>());
    EasyMock.replay(objUriInfo);
    return objUriInfo;
  }

  /**
   * Gets the key predicates.
   *
   * @return the key predicates
   */
  private List<KeyPredicate> getKeyPredicates() {
    List<KeyPredicate> keyPredicates = new ArrayList<KeyPredicate>();
    return keyPredicates;
  }

  /**
   * Gets the entity set count uri info.
   *
   * @return the entity set count uri info
   */
  private GetEntitySetCountUriInfo getEntitySetCountUriInfo() {
    return getLocalUriInfo();
  }

  /**
   * Gets the entity count uri info.
   *
   * @return the entity count uri info
   */
  private GetEntityCountUriInfo getEntityCountUriInfo() {
    return getLocalUriInfo();
  }
  
  /**
   * Gets the function import uri info.
   *
   * @return the function import uri info
   */
  private GetFunctionImportUriInfo getFunctionImportUriInfo() {

    GetFunctionImportUriInfo objUriInfo = EasyMock.createMock(UriInfo.class);
    EasyMock.expect(objUriInfo.getFunctionImport()).andStubReturn(getLocalEdmFunctionImport());
    EasyMock.replay(objUriInfo);
    return objUriInfo;
  }

  /**
   * Gets the entity set uri info.
   *
   * @return the entity set uri info
   */
  private GetEntitySetUriInfo getEntitySetUriInfo() {

    UriInfo objUriInfo = EasyMock.createMock(UriInfo.class);
    EasyMock.expect(objUriInfo.getStartEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getTargetEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getSelect()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getOrderBy()).andStubReturn(getOrderByExpression());
    EasyMock.expect(objUriInfo.getTop()).andStubReturn(getTop());
    EasyMock.expect(objUriInfo.getSkip()).andStubReturn(getSkip());
    EasyMock.expect(objUriInfo.getSkipToken()).andReturn("5");
    EasyMock.expect(objUriInfo.getInlineCount()).andStubReturn(getInlineCount());
    EasyMock.expect(objUriInfo.getFilter()).andStubReturn(getFilter());
    EasyMock.expect(objUriInfo.getFunctionImport()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getCustomQueryOptions()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getNavigationSegments()).andStubReturn(new ArrayList<NavigationSegment>());
    EasyMock.replay(objUriInfo);
    return objUriInfo;
  }
  
  /**
   * Gets the entity link uri info.
   *
   * @return the entity link uri info
   */
  private GetEntityLinkUriInfo getEntityLinkUriInfo () {

    UriInfo objUriInfo = EasyMock.createMock(UriInfo.class);
    EasyMock.expect(objUriInfo.getStartEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getTargetEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getSelect()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getOrderBy()).andStubReturn(getOrderByExpression());
    EasyMock.expect(objUriInfo.getTop()).andStubReturn(getTop());
    EasyMock.expect(objUriInfo.getSkip()).andStubReturn(getSkip());
    EasyMock.expect(objUriInfo.getKeyPredicates()).andReturn(getKeyPredicates());
    EasyMock.expect(objUriInfo.getInlineCount()).andStubReturn(getInlineCount());
    EasyMock.expect(objUriInfo.getFilter()).andStubReturn(getFilter());
    EasyMock.expect(objUriInfo.getFunctionImport()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getCustomQueryOptions()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getNavigationSegments()).andStubReturn(new ArrayList<NavigationSegment>());
    EasyMock.replay(objUriInfo);
    return objUriInfo;
  }
  
  /**
   * Gets the entity set links uri info.
   *
   * @return the entity set links uri info
   */
  private GetEntitySetLinksUriInfo getEntitySetLinksUriInfo () {

    UriInfoImpl objUriInfo = EasyMock.createMock(UriInfoImpl.class);
    EasyMock.expect(objUriInfo.getStartEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getTargetEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getSelect()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getOrderBy()).andStubReturn(getOrderByExpression());
    EasyMock.expect(objUriInfo.getTop()).andStubReturn(2);
    EasyMock.expect(objUriInfo.getSkip()).andStubReturn(1);
    EasyMock.expect(objUriInfo.getSkipToken()).andReturn("5");
    EasyMock.expect(objUriInfo.getInlineCount()).andStubReturn(InlineCount.ALLPAGES);
    EasyMock.expect(objUriInfo.getFilter()).andStubReturn(getFilter());
    EasyMock.expect(objUriInfo.getFunctionImport()).andStubReturn(null);
    EasyMock.expect(objUriInfo.isCount()).andStubReturn(false);
    EasyMock.expect(objUriInfo.getCustomQueryOptions()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getNavigationSegments()).andStubReturn(new ArrayList<NavigationSegment>());
    objUriInfo.setCount(true);
    EasyMock.expectLastCall().times(1);
    objUriInfo.setCount(false);
    EasyMock.expectLastCall().times(1);
    Map<String, String> data = new HashMap<String, String>();
    data.put("count", "11");
    objUriInfo.setCustomQueryOptions(data );
    EasyMock.expectLastCall().times(1);
    EasyMock.expect(objUriInfo.getCustomQueryOptions()).andStubReturn(data);
    EasyMock.replay(objUriInfo);
    return objUriInfo;
  }

  /**
   * Gets the local uri info.
   *
   * @return the local uri info
   */
  private UriInfo getLocalUriInfo() {
    UriInfo objUriInfo = EasyMock.createMock(UriInfo.class);
    EasyMock.expect(objUriInfo.getStartEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getNavigationSegments()).andStubReturn(new ArrayList<NavigationSegment>());
    EasyMock.expect(objUriInfo.getTargetEntitySet()).andStubReturn(getLocalEdmEntitySet());
    EasyMock.expect(objUriInfo.getSelect()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getOrderBy()).andStubReturn(getOrderByExpression());
    EasyMock.expect(objUriInfo.getTop()).andStubReturn(getTop());
    EasyMock.expect(objUriInfo.getSkip()).andStubReturn(getSkip());
    EasyMock.expect(objUriInfo.getInlineCount()).andStubReturn(getInlineCount());
    EasyMock.expect(objUriInfo.getFilter()).andStubReturn(getFilter());
    EasyMock.replay(objUriInfo);
    return objUriInfo;
  }
  
  /**
   * Gets the local edm function import.
   *
   * @return the local edm function import
   */
  private EdmFunctionImport getLocalEdmFunctionImport() {
    EdmFunctionImport edmFunction = EasyMock.createMock(EdmFunctionImport.class);
    try {
      EasyMock.expect(edmFunction.getName()).andStubReturn(SALES_ORDER_HEADERS);
      EasyMock.expect(edmFunction.getEntityContainer()).andStubReturn(getLocalEdmEntityContainer());
      EasyMock.expect(edmFunction.getReturnType()).andStubReturn(null);
      EasyMock.expect(edmFunction.getHttpMethod()).andStubReturn("GET");
      EasyMock.expect(edmFunction.getParameterNames()).andStubReturn(new ArrayList<String>());
      JPAEdmMappingImpl mockedEdmMapping = EasyMock.createMock(JPAEdmMappingImpl.class);
    //  ((Mapping) mockedEdmMapping).setInternalName(SALES_ORDER_HEADERS);
      EasyMock.expect(edmFunction.getMapping()).andStubReturn(mockedEdmMapping);
      EasyMock.expect(mockedEdmMapping.getInternalName()).andStubReturn(SALES_ORDER_HEADERS);
      EasyMock.<Class<?>> expect(mockedEdmMapping.getJPAType()).andReturn(SalesOrderHeader.class);
      EasyMock.replay(mockedEdmMapping);
      EasyMock.replay(edmFunction);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    return edmFunction;
  }

  /**
   * Gets the local edm entity set.
   *
   * @return the local edm entity set
   */
  private EdmEntitySet getLocalEdmEntitySet() {
    EdmEntitySet edmEntitySet = EasyMock.createMock(EdmEntitySet.class);
    try {
      EasyMock.expect(edmEntitySet.getName()).andStubReturn(SALES_ORDER_HEADERS);
      EasyMock.expect(edmEntitySet.getEntityContainer()).andStubReturn(getLocalEdmEntityContainer());
      EasyMock.expect(edmEntitySet.getEntityType()).andStubReturn(getLocalEdmEntityType());
      EasyMock.replay(edmEntitySet);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    return edmEntitySet;
  }

  /**
   * Gets the local edm entity type.
   *
   * @return the local edm entity type
   */
  private EdmEntityType getLocalEdmEntityType() {
    EdmEntityType edmEntityType = EasyMock.createMock(EdmEntityType.class);
    try {
      EasyMock.expect(edmEntityType.getKeyProperties()).andStubReturn(new ArrayList<EdmProperty>());
      EasyMock.expect(edmEntityType.getPropertyNames()).andStubReturn(getLocalPropertyNames());
      EasyMock.expect(edmEntityType.getProperty(SO_ID)).andStubReturn(getEdmTypedMockedObj(SALES_ORDER));
      EasyMock.expect(edmEntityType.getKind()).andStubReturn(EdmTypeKind.SIMPLE);
      EasyMock.expect(edmEntityType.getNamespace()).andStubReturn(SALES_ORDER_HEADERS);
      EasyMock.expect(edmEntityType.getName()).andStubReturn(SALES_ORDER_HEADERS);
      EasyMock.expect(edmEntityType.hasStream()).andStubReturn(false);
      EasyMock.expect(edmEntityType.getNavigationPropertyNames()).andStubReturn(new ArrayList<String>());
      EasyMock.expect(edmEntityType.getKeyPropertyNames()).andStubReturn(new ArrayList<String>());
      EasyMock.expect(edmEntityType.getMapping()).andStubReturn(getEdmMappingMockedObj(SALES_ORDER));// ID vs Salesorder
                                                                                                     // ID
      EasyMock.replay(edmEntityType);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    return edmEntityType;
  }

  /**
   * Gets the inline count.
   *
   * @return the inline count
   */
  private InlineCount getInlineCount() {
    return InlineCount.NONE;
  }

  /**
   * Gets the filter.
   *
   * @return the filter
   */
  private FilterExpression getFilter() {
    return null;
  }

  /**
   * Gets the skip.
   *
   * @return the skip
   */
  private Integer getSkip() {
    return null;
  }

  /**
   * Gets the top.
   *
   * @return the top
   */
  private Integer getTop() {
    return null;
  }

  /**
   * Gets the order by expression.
   *
   * @return the order by expression
   */
  private OrderByExpression getOrderByExpression() {
    return null;
  }

  /**
   * Gets the localmock O data JPA context.
   *
   * @return the localmock O data JPA context
   */
  private ODataJPAContext getLocalmockODataJPAContext() {
    ODataJPAContext odataJPAContext = EasyMock.createMock(ODataJPAContext.class);
    EasyMock.expect(odataJPAContext.getPersistenceUnitName()).andStubReturn("salesorderprocessing");
    EasyMock.expect(odataJPAContext.getEntityManagerFactory()).andStubReturn(mockEntityManagerFactory());
    EasyMock.expect(odataJPAContext.getODataJPATransaction()).andStubReturn(getLocalJpaTransaction());
    EasyMock.expect(odataJPAContext.getODataContext()).andStubReturn(getLocalODataContext());
    EasyMock.expect(odataJPAContext.getEntityManager()).andStubReturn(getLocalEntityManager());
    EasyMock.expect(odataJPAContext.getPageSize()).andReturn(10).anyTimes();
    odataJPAContext.setPaging(EasyMock.isA(JPAPaging.class));
    EasyMock.expectLastCall();
    EasyMock.replay(odataJPAContext);
    return odataJPAContext;
  }

  /**
   * Gets the local jpa transaction.
   *
   * @return the local jpa transaction
   */
  private ODataJPATransaction getLocalJpaTransaction() {
    ODataJPATransaction tx = EasyMock.createMock(ODataJPATransaction.class);
    EasyMock.expect(tx.isActive()).andReturn(false);
    tx.begin(); // testing void method
    tx.commit();// testing void method
    EasyMock.expect(tx.isActive()).andReturn(false);
    tx.begin(); // testing void method
    tx.commit();// testing void method
    EasyMock.replay(tx);
    return tx;
  }

  /**
   * Mock entity manager factory.
   *
   * @return the entity manager factory
   */
  private EntityManagerFactory mockEntityManagerFactory() {
    EntityManagerFactory emf = EasyMock.createMock(EntityManagerFactory.class);
    EasyMock.expect(emf.getMetamodel()).andStubReturn(mockMetaModel());
    EasyMock.expect(emf.createEntityManager()).andStubReturn(getLocalEntityManager());
    EasyMock.replay(emf);
    return emf;
  }

  /**
   * Gets the local entity manager.
   *
   * @return the local entity manager
   */
  public EntityManager getLocalEntityManager() {
    EntityManager em = EasyMock.createMock(EntityManager.class);
    EasyMock.expect(em.createQuery("SELECT E1 FROM SalesOrderHeaders E1")).andStubReturn(getQuery());
    EasyMock.expect(em.createQuery("SELECT COUNT ( E1 ) FROM SalesOrderHeaders E1")).andStubReturn(
        getQueryForSelectCount());
    EasyMock.expect(em.getTransaction()).andStubReturn(getLocalTransaction()); // For Delete
    EasyMock.expect(em.isOpen()).andReturn(false);
    em.flush();
    em.flush();
    Address obj = new Address();
    em.remove(obj);// testing void method
    em.remove(obj);// testing void method
    EasyMock.replay(em);
    return em;
  }

  /**
   * Gets the local transaction.
   *
   * @return the local transaction
   */
  private EntityTransaction getLocalTransaction() {
    EntityTransaction entityTransaction = EasyMock.createMock(EntityTransaction.class);
    entityTransaction.begin(); // testing void method
    entityTransaction.begin(); // testing void method
    entityTransaction.commit();// testing void method
    entityTransaction.commit();// testing void method
    EasyMock.expect(entityTransaction.isActive()).andReturn(false).anyTimes();
    EasyMock.replay(entityTransaction);
    return entityTransaction;
  }

  /**
   * Gets the query.
   *
   * @return the query
   */
  private Query getQuery() {
    return new Query() {

      private int maxResults;
      private int firstResult;

      @Override
      public Query setFirstResult(final int arg0) {
        firstResult = arg0;
        return this;
      }

      @Override
      public Query setMaxResults(final int arg0) {
        maxResults = arg0;
        return this;
      }

      @Override
      public int getMaxResults() {
        return maxResults;
      }

      @Override
      public int getFirstResult() {
        return firstResult;
      }

      @SuppressWarnings("unchecked")
      @Override
      public List<Object> getResultList() {
        return (List<Object>) getResultListL();
      }

      @Override
      public <T> T unwrap(final Class<T> arg0) {
        return null;
      }

      @Override
      public Query setParameter(final int arg0, final Date arg1, final TemporalType arg2) {
        return null;
      }

      @Override
      public Query setParameter(final int arg0, final Calendar arg1, final TemporalType arg2) {
        return null;
      }

      @Override
      public Query setParameter(final String arg0, final Date arg1, final TemporalType arg2) {
        return null;
      }

      @Override
      public Query setParameter(final String arg0, final Calendar arg1, final TemporalType arg2) {
        return null;
      }

      @Override
      public Query setParameter(final Parameter<Date> arg0, final Date arg1, final TemporalType arg2) {
        return null;
      }

      @Override
      public Query setParameter(final Parameter<Calendar> arg0, final Calendar arg1, final TemporalType arg2) {
        return null;
      }

      @Override
      public Query setParameter(final int arg0, final Object arg1) {
        return null;
      }

      @Override
      public Query setParameter(final String arg0, final Object arg1) {
        return null;
      }

      @Override
      public <T> Query setParameter(final Parameter<T> arg0, final T arg1) {
        return null;
      }

      @Override
      public Query setLockMode(final LockModeType arg0) {
        return null;
      }

      @Override
      public Query setHint(final String arg0, final Object arg1) {
        return null;
      }

      @Override
      public Query setFlushMode(final FlushModeType arg0) {
        return null;
      }

      @Override
      public boolean isBound(final Parameter<?> arg0) {
        return false;
      }

      @Override
      public Object getSingleResult() {
        return null;
      }

      @Override
      public Set<Parameter<?>> getParameters() {
        return null;
      }

      @Override
      public Object getParameterValue(final int arg0) {
        return null;
      }

      @Override
      public Object getParameterValue(final String arg0) {
        return null;
      }

      @Override
      public <T> T getParameterValue(final Parameter<T> arg0) {
        return null;
      }

      @Override
      public <T> Parameter<T> getParameter(final int arg0, final Class<T> arg1) {
        return null;
      }

      @Override
      public <T> Parameter<T> getParameter(final String arg0, final Class<T> arg1) {
        return null;
      }

      @Override
      public Parameter<?> getParameter(final int arg0) {
        return null;
      }

      @Override
      public Parameter<?> getParameter(final String arg0) {
        return null;
      }

      @Override
      public LockModeType getLockMode() {
        return null;
      }

      @Override
      public Map<String, Object> getHints() {
        return null;
      }

      @Override
      public FlushModeType getFlushMode() {
        return null;
      }

      @Override
      public int executeUpdate() {
        return 0;
      }
    };
  }

  /**
   * Gets the query for select count.
   *
   * @return the query for select count
   */
  private Query getQueryForSelectCount() {
    Query query = EasyMock.createMock(Query.class);
    EasyMock.expect(query.getResultList()).andStubReturn(getResultListForSelectCount());
    EasyMock.replay(query);
    return query;
  }

  /**
   * Gets the result list L.
   *
   * @return the result list L
   */
  private List<?> getResultListL() {
    List<Object> list = new ArrayList<Object>();
    list.add(new Address());
    return list;
  }

  /**
   * Gets the result list for select count.
   *
   * @return the result list for select count
   */
  private List<?> getResultListForSelectCount() {
    List<Object> list = new ArrayList<Object>();
    list.add(new Long(11));
    return list;
  }

  /**
   * The Class Address.
   */
  private class Address {
    
    /** The so id. */
    private String soId = "12";

    /**
     * Gets the so id.
     *
     * @return the so id
     */
    public String getSoId() {
      return soId;
    }

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(final Object obj) {
      boolean isEqual = false;
      if (obj instanceof Address) {
        isEqual = getSoId().equalsIgnoreCase(((Address) obj).getSoId());//
      }
      return isEqual;
    }
  }

  /**
   * Mock meta model.
   *
   * @return the metamodel
   */
  private Metamodel mockMetaModel() {
    Metamodel metaModel = EasyMock.createMock(Metamodel.class);
    EasyMock.replay(metaModel);
    return metaModel;
  }

  /**
   * Gets the local edm entity container.
   *
   * @return the local edm entity container
   */
  private EdmEntityContainer getLocalEdmEntityContainer() {
    EdmEntityContainer edmEntityContainer = EasyMock.createMock(EdmEntityContainer.class);
    EasyMock.expect(edmEntityContainer.isDefaultEntityContainer()).andStubReturn(true);
    try {
      EasyMock.expect(edmEntityContainer.getName()).andStubReturn(SALESORDERPROCESSING_CONTAINER);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    EasyMock.replay(edmEntityContainer);
    return edmEntityContainer;
  }

  /**
   * Gets the edm typed mocked obj.
   *
   * @param propertyName the property name
   * @return the edm typed mocked obj
   */
  private EdmTyped getEdmTypedMockedObj(final String propertyName) {
    EdmProperty mockedEdmProperty = EasyMock.createMock(EdmProperty.class);
    try {
      EasyMock.expect(mockedEdmProperty.getMapping()).andStubReturn(getEdmMappingMockedObj(propertyName));
      EdmType edmType = EasyMock.createMock(EdmType.class);
      EasyMock.expect(edmType.getKind()).andStubReturn(EdmTypeKind.SIMPLE);
      EasyMock.replay(edmType);
      EasyMock.expect(mockedEdmProperty.getName()).andStubReturn("identifier");
      EasyMock.expect(mockedEdmProperty.getType()).andStubReturn(edmType);
      EasyMock.expect(mockedEdmProperty.getFacets()).andStubReturn(getEdmFacetsMockedObj());

      EasyMock.replay(mockedEdmProperty);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    return mockedEdmProperty;
  }

  /**
   * Gets the edm facets mocked obj.
   *
   * @return the edm facets mocked obj
   */
  private EdmFacets getEdmFacetsMockedObj() {
    EdmFacets facets = EasyMock.createMock(EdmFacets.class);
    EasyMock.expect(facets.getConcurrencyMode()).andStubReturn(EdmConcurrencyMode.Fixed);

    EasyMock.replay(facets);
    return facets;
  }

  /**
   * Gets the edm mapping mocked obj.
   *
   * @param propertyName the property name
   * @return the edm mapping mocked obj
   */
  private EdmMapping getEdmMappingMockedObj(final String propertyName) {
    EdmMapping mockedEdmMapping = new JPAEdmMappingImpl();
    if (propertyName.equalsIgnoreCase(SALES_ORDER)) {
      ((Mapping) mockedEdmMapping).setInternalName(SALES_ORDER_HEADERS);
    } else {
      ((Mapping) mockedEdmMapping).setInternalName(propertyName);
    }

    return mockedEdmMapping;
  }

  /**
   * Gets the local property names.
   *
   * @return the local property names
   */
  private List<String> getLocalPropertyNames() {
    List<String> list = new ArrayList<String>();
    list.add(SO_ID);
    return list;
  }

  /**
   * Gets the local O data context.
   *
   * @return the local O data context
   */
  private ODataContext getLocalODataContext() {
    ODataContext objODataContext = EasyMock.createMock(ODataContext.class);
    try {
      EasyMock.expect(objODataContext.getPathInfo()).andStubReturn(getLocalPathInfo());
    } catch (ODataException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(objODataContext);
    return objODataContext;
  }

  /**
   * Gets the local path info.
   *
   * @return the local path info
   */
  private PathInfo getLocalPathInfo() {
    PathInfo pathInfo = EasyMock.createMock(PathInfo.class);
    EasyMock.expect(pathInfo.getServiceRoot()).andStubReturn(getLocalURI());
    EasyMock.replay(pathInfo);
    return pathInfo;
  }

  /**
   * Gets the local URI.
   *
   * @return the local URI
   */
  private URI getLocalURI() {
    URI uri = null;
    try {
      uri = new URI(STR_LOCAL_URI);
    } catch (URISyntaxException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    return uri;
  }

  /**
   * Mock URI info with top skip inline listener.
   *
   * @return the uri info
   * @throws EdmException the edm exception
   */
  // -------------------------------- Common End ------------------------------------
  private UriInfo mockURIInfoWithTopSkipInlineListener() throws EdmException {

    UriInfoImpl objUriInfo = EasyMock.createMock(UriInfoImpl.class);
    EdmEntityType edmEntityType = EasyMock.createMock(EdmEntityType.class);
    EasyMock.expect(edmEntityType.getMapping()).andStubReturn((EdmMapping) mockEdmMapping());
    EdmEntitySet edmEntitySet = EasyMock.createMock(EdmEntitySet.class);
    EasyMock.expect(edmEntitySet.getEntityType()).andStubReturn(edmEntityType);
    EasyMock.expect(edmEntityType.getKeyProperties()).andStubReturn(new ArrayList<EdmProperty>());
    EasyMock.expect(objUriInfo.getStartEntitySet()).andStubReturn(edmEntitySet);
    EasyMock.expect(objUriInfo.getTargetEntitySet()).andStubReturn(edmEntitySet);
    EasyMock.expect(objUriInfo.getSelect()).andStubReturn(null);
    EasyMock.expect(objUriInfo.getOrderBy()).andStubReturn(getOrderByExpression());
    EasyMock.expect(objUriInfo.getTop()).andStubReturn(1);
    EasyMock.expect(objUriInfo.getSkip()).andStubReturn(1);
    EasyMock.expect(objUriInfo.getSkipToken()).andReturn("5");
    EasyMock.expect(objUriInfo.getFilter()).andStubReturn(getFilter());
    EasyMock.expect(objUriInfo.getFunctionImport()).andStubReturn(null);
    Map<String, String> delta = new HashMap<String, String>();
    delta.put("!deltatoken", "!deltatoken");
    EasyMock.expect(objUriInfo.getCustomQueryOptions()).andStubReturn(delta );
    EasyMock.expect(objUriInfo.isCount()).andReturn(false);
    EasyMock.expect(objUriInfo.getNavigationSegments()).andStubReturn(new ArrayList<NavigationSegment>());
    EasyMock.expect(objUriInfo.getInlineCount()).andStubReturn(InlineCount.ALLPAGES);
    objUriInfo.setCount(true);
    EasyMock.expectLastCall().times(1);
    objUriInfo.setCount(false);
    EasyMock.expectLastCall().times(1);
    Map<String, String> data = new HashMap<String, String>();
    data.put("count", "11");
    objUriInfo.setCustomQueryOptions(data );
    EasyMock.expectLastCall().times(1);
    EasyMock.replay(edmEntityType, edmEntitySet, objUriInfo);
    return objUriInfo;

  }
  
  /**
   * Mock edm mapping.
   *
   * @return the JPA edm mapping
   */
  private JPAEdmMapping mockEdmMapping() {
    JPATombstoneExtensionMock tombstone = new JPATombstoneExtensionMock();
    tombstone.handleDelta(new String("delta"));
    JPAEdmMappingImpl mockedEdmMapping = new JPAEdmMappingImpl();
    mockedEdmMapping.setODataJPATombstoneEntityListener(JPAQueryExtensionMock.class);
    mockedEdmMapping.setODataJPATombstoneEntityListener(JPATombstoneExtensionMock.class);
    mockedEdmMapping.setInternalName(SALES_ORDER_HEADERS);
    return mockedEdmMapping;
  }
  
  /**
   * The Class JPATombstoneExtensionMock.
   */
  public static final class JPATombstoneExtensionMock extends ODataJPATombstoneEntityListener {
    
    /**
     * Handle delta.
     *
     * @param entity the entity
     */
    public void handleDelta(final Object entity) {
      addToDelta(entity, SALES_ORDER_HEADERS);
    }

    /**
     * Gets the query.
     *
     * @param resultsView the results view
     * @param em the em
     * @return the query
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    @Override
    public Query getQuery(GetEntitySetUriInfo resultsView, EntityManager em) throws ODataJPARuntimeException {
      return null;
    }

    /**
     * Generate delta token.
     *
     * @param deltas the deltas
     * @param query the query
     * @return the string
     */
    @Override
    public String generateDeltaToken(List<Object> deltas, Query query) {
      return null;
    }
    
  }
  
  /**
   * The Class JPAQueryExtensionMock.
   */
  public static final class JPAQueryExtensionMock extends ODataJPAQueryExtensionEntityListener {
    
    /** The query. */
    Query query = EasyMock.createMock(Query.class);

    /**
     * Gets the query.
     *
     * @param uriInfo the uri info
     * @param em the em
     * @return the query
     */
    @Override
    public Query getQuery(GetEntityUriInfo uriInfo, EntityManager em) {
      return query;
    }

    /**
     * Gets the query.
     *
     * @param uriInfo the uri info
     * @param em the em
     * @return the query
     */
    @Override
    public Query getQuery(GetEntitySetUriInfo uriInfo, EntityManager em) {
      return null;
    }

    /**
     * Gets the query.
     *
     * @param uriInfo the uri info
     * @param em the em
     * @return the query
     */
    @Override
    public Query getQuery(GetEntitySetCountUriInfo uriInfo, EntityManager em) {
      return query;
    }

    /**
     * Gets the query.
     *
     * @param uriInfo the uri info
     * @param em the em
     * @return the query
     */
    @Override
    public Query getQuery(DeleteUriInfo uriInfo, EntityManager em) {
      return query;
    }

    /**
     * Gets the query.
     *
     * @param uriInfo the uri info
     * @param em the em
     * @return the query
     */
    @Override
    public Query getQuery(GetEntityCountUriInfo uriInfo, EntityManager em) {
      return query;
    }

    /**
     * Gets the query.
     *
     * @param uriInfo the uri info
     * @param em the em
     * @return the query
     */
    @Override
    public Query getQuery(PutMergePatchUriInfo uriInfo, EntityManager em) {
      return query;
    }

    /**
     * Checks if is tombstone supported.
     *
     * @return true, if is tombstone supported
     */
    @Override
    public boolean isTombstoneSupported() {
      return true;
    }
  }
}
