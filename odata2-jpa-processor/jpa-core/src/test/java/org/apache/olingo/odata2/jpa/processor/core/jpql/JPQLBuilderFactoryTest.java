/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.apache.olingo.odata2.jpa.processor.core.jpql;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.NavigationSegment;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.factory.JPAAccessFactory;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAAccessFactory;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement.JPQLStatementBuilder;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAProcessorImplTest;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.factory.ODataJPAFactoryImpl;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLSelectContext.JPQLSelectContextBuilder;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLSelectSingleContext.JPQLSelectSingleContextBuilder;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;
import jakarta.persistence.Cache;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.persistence.Query;
import jakarta.persistence.SynchronizationType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.metamodel.Metamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLBuilderFactoryTest.
 */
public class JPQLBuilderFactoryTest {

    /**
     * Test get statement builder factoryfor select.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetStatementBuilderFactoryforSelect() throws ODataException {

        GetEntitySetUriInfo getEntitySetView = getUriInfo();

        // Build JPQL Context
        JPQLContext selectContext = JPQLContext.createBuilder(JPQLContextType.SELECT, getEntitySetView)
                                               .build();
        JPQLStatementBuilder statementBuilder = new ODataJPAFactoryImpl().getJPQLBuilderFactory()
                                                                         .getStatementBuilder(selectContext);

        assertTrue(statementBuilder instanceof JPQLSelectStatementBuilder);

    }

    /**
     * Test get statement builder factoryfor select single.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetStatementBuilderFactoryforSelectSingle() throws ODataException {

        GetEntityUriInfo getEntityView = getEntityUriInfo();

        // Build JPQL Context
        JPQLContext selectContext = JPQLContext.createBuilder(JPQLContextType.SELECT_SINGLE, getEntityView)
                                               .build();
        JPQLStatementBuilder statementBuilder = new ODataJPAFactoryImpl().getJPQLBuilderFactory()
                                                                         .getStatementBuilder(selectContext);

        assertTrue(statementBuilder instanceof JPQLSelectSingleStatementBuilder);

    }

    /**
     * Test get statement builder factoryfor join select.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetStatementBuilderFactoryforJoinSelect() throws ODataException {

        GetEntitySetUriInfo getEntitySetView = getUriInfo();

        // Build JPQL Context
        JPQLContext selectContext = JPQLContext.createBuilder(JPQLContextType.JOIN, getEntitySetView)
                                               .build();
        JPQLStatementBuilder statementBuilder = new ODataJPAFactoryImpl().getJPQLBuilderFactory()
                                                                         .getStatementBuilder(selectContext);

        assertTrue(statementBuilder instanceof JPQLJoinStatementBuilder);

    }

    /**
     * Test get statement builder factoryfor join select single.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetStatementBuilderFactoryforJoinSelectSingle() throws ODataException {

        GetEntityUriInfo getEntityView = getEntityUriInfo();

        // Build JPQL Context
        JPQLContext selectContext = JPQLContext.createBuilder(JPQLContextType.JOIN_SINGLE, getEntityView)
                                               .build();
        JPQLStatementBuilder statementBuilder = new ODataJPAFactoryImpl().getJPQLBuilderFactory()
                                                                         .getStatementBuilder(selectContext);

        assertTrue(statementBuilder instanceof JPQLJoinSelectSingleStatementBuilder);

    }

    /**
     * Test get context builderfor delete.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetContextBuilderforDelete() throws ODataException {

        // Build JPQL ContextBuilder
        JPQLContextBuilder contextBuilder = new ODataJPAFactoryImpl().getJPQLBuilderFactory()
                                                                     .getContextBuilder(JPQLContextType.DELETE);

        assertNull(contextBuilder);

    }

    /**
     * Test get context builderfor select.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetContextBuilderforSelect() throws ODataException {

        // Build JPQL ContextBuilder
        JPQLContextBuilder contextBuilder = new ODataJPAFactoryImpl().getJPQLBuilderFactory()
                                                                     .getContextBuilder(JPQLContextType.SELECT);

        assertNotNull(contextBuilder);
        assertTrue(contextBuilder instanceof JPQLSelectContextBuilder);

    }

    /**
     * Test get context builderfor select single.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetContextBuilderforSelectSingle() throws ODataException {

        // Build JPQL ContextBuilder
        JPQLContextBuilder contextBuilder = new ODataJPAFactoryImpl().getJPQLBuilderFactory()
                                                                     .getContextBuilder(JPQLContextType.SELECT_SINGLE);

        assertNotNull(contextBuilder);
        assertTrue(contextBuilder instanceof JPQLSelectSingleContextBuilder);

    }

    /**
     * Gets the uri info.
     *
     * @return the uri info
     * @throws EdmException the edm exception
     */
    private GetEntitySetUriInfo getUriInfo() throws EdmException {
        GetEntitySetUriInfo getEntitySetView = EasyMock.createMock(GetEntitySetUriInfo.class);
        EdmEntitySet edmEntitySet = EasyMock.createMock(EdmEntitySet.class);
        EdmEntityType edmEntityType = EasyMock.createMock(EdmEntityType.class);
        EasyMock.expect(edmEntityType.getMapping())
                .andStubReturn(null);
        EasyMock.expect(edmEntityType.getName())
                .andStubReturn("SOItem");
        EasyMock.replay(edmEntityType);
        OrderByExpression orderByExpression = EasyMock.createMock(OrderByExpression.class);
        EasyMock.expect(getEntitySetView.getTargetEntitySet())
                .andStubReturn(edmEntitySet);
        EdmEntitySet startEdmEntitySet = EasyMock.createMock(EdmEntitySet.class);
        EdmEntityType startEdmEntityType = EasyMock.createMock(EdmEntityType.class);
        EasyMock.expect(startEdmEntityType.getMapping())
                .andStubReturn(null);
        EasyMock.expect(startEdmEntityType.getName())
                .andStubReturn("SOHeader");
        EasyMock.expect(startEdmEntitySet.getEntityType())
                .andStubReturn(startEdmEntityType);
        EasyMock.expect(getEntitySetView.getStartEntitySet())
                .andStubReturn(startEdmEntitySet);
        EasyMock.replay(startEdmEntityType, startEdmEntitySet);
        EasyMock.expect(getEntitySetView.getOrderBy())
                .andStubReturn(orderByExpression);
        EasyMock.expect(getEntitySetView.getSelect())
                .andStubReturn(null);
        EasyMock.expect(getEntitySetView.getFilter())
                .andStubReturn(null);
        List<NavigationSegment> navigationSegments = new ArrayList<NavigationSegment>();
        EasyMock.expect(getEntitySetView.getNavigationSegments())
                .andStubReturn(navigationSegments);
        KeyPredicate keyPredicate = EasyMock.createMock(KeyPredicate.class);
        EdmProperty kpProperty = EasyMock.createMock(EdmProperty.class);
        EdmSimpleType edmType = EdmSimpleTypeKind.Int32.getEdmSimpleTypeInstance();
        JPAEdmMappingImpl edmMapping = EasyMock.createMock(JPAEdmMappingImpl.class);
        EasyMock.expect(edmMapping.getInternalName())
                .andStubReturn("Field1");
        EasyMock.expect(keyPredicate.getLiteral())
                .andStubReturn("1");
        EasyMock.expect(edmMapping.getJPAType())
                .andStubReturn(null);
        try {
            EasyMock.expect(kpProperty.getName())
                    .andStubReturn("Field1");
            EasyMock.expect(kpProperty.getType())
                    .andStubReturn(edmType);

            EasyMock.expect(kpProperty.getMapping())
                    .andStubReturn(edmMapping);

        } catch (EdmException e2) {
            fail("this should not happen");
        }
        EasyMock.expect(keyPredicate.getProperty())
                .andStubReturn(kpProperty);
        EasyMock.replay(edmMapping, kpProperty, keyPredicate);
        List<KeyPredicate> keyPredicates = new ArrayList<KeyPredicate>();
        keyPredicates.add(keyPredicate);
        EasyMock.expect(getEntitySetView.getKeyPredicates())
                .andStubReturn(keyPredicates);
        EasyMock.replay(getEntitySetView);
        EasyMock.expect(edmEntitySet.getEntityType())
                .andStubReturn(edmEntityType);
        EasyMock.replay(edmEntitySet);
        return getEntitySetView;
    }

    /**
     * Gets the entity uri info.
     *
     * @return the entity uri info
     * @throws EdmException the edm exception
     */
    private GetEntityUriInfo getEntityUriInfo() throws EdmException {
        GetEntityUriInfo getEntityView = EasyMock.createMock(GetEntityUriInfo.class);
        EdmEntitySet edmEntitySet = EasyMock.createMock(EdmEntitySet.class);
        EdmEntityType edmEntityType = EasyMock.createMock(EdmEntityType.class);
        EasyMock.expect(edmEntityType.getKeyProperties())
                .andStubReturn(new ArrayList<EdmProperty>());
        EasyMock.expect(edmEntityType.getMapping())
                .andStubReturn(null);
        EasyMock.expect(edmEntityType.getName())
                .andStubReturn("");
        EasyMock.expect(edmEntitySet.getEntityType())
                .andStubReturn(edmEntityType);
        EasyMock.expect(getEntityView.getSelect())
                .andStubReturn(null);
        EasyMock.expect(getEntityView.getTargetEntitySet())
                .andStubReturn(edmEntitySet);
        EdmEntitySet startEdmEntitySet = EasyMock.createMock(EdmEntitySet.class);
        EdmEntityType startEdmEntityType = EasyMock.createMock(EdmEntityType.class);
        EasyMock.expect(startEdmEntityType.getMapping())
                .andStubReturn(null);
        EasyMock.expect(startEdmEntityType.getName())
                .andStubReturn("SOHeader");
        EasyMock.expect(startEdmEntitySet.getEntityType())
                .andStubReturn(startEdmEntityType);
        EasyMock.expect(getEntityView.getStartEntitySet())
                .andStubReturn(startEdmEntitySet);
        EasyMock.replay(startEdmEntityType, startEdmEntitySet);
        EasyMock.replay(edmEntityType, edmEntitySet);
        EasyMock.expect(getEntityView.getKeyPredicates())
                .andStubReturn(new ArrayList<KeyPredicate>());
        List<NavigationSegment> navigationSegments = new ArrayList<NavigationSegment>();
        EasyMock.expect(getEntityView.getNavigationSegments())
                .andStubReturn(navigationSegments);
        EasyMock.replay(getEntityView);
        return getEntityView;
    }

    /**
     * Test JPA access factory.
     */
    @Test
    public void testJPAAccessFactory() {
        ODataJPAFactoryImpl oDataJPAFactoryImpl = new ODataJPAFactoryImpl();
        JPAAccessFactory jpaAccessFactory = oDataJPAFactoryImpl.getJPAAccessFactory();
        ODataJPAContextImpl oDataJPAContextImpl = new ODataJPAContextImpl();
        try {
            oDataJPAContextImpl.setEntityManager(new JPAProcessorImplTest().getLocalEntityManager());
        } catch (SecurityException | IllegalArgumentException e) {
            fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
        }
        final EntityManager em = EasyMock.createMock(EntityManager.class);
        Metamodel metamodel = EasyMock.createMock(Metamodel.class);
        EasyMock.expect(em.getMetamodel())
                .andReturn(metamodel)
                .anyTimes();
        EasyMock.expect(em.isOpen())
                .andReturn(true)
                .anyTimes();
        EasyMock.replay(em);

        oDataJPAContextImpl.setEntityManagerFactory(new TestEntityManagerFactory(em));
        oDataJPAContextImpl.setPersistenceUnitName("pUnit");

        assertNotNull(jpaAccessFactory.getJPAProcessor(oDataJPAContextImpl));
        assertNotNull(jpaAccessFactory.getJPAEdmModelView(oDataJPAContextImpl));
    }


    /**
     * Test JPA access factory entity manager only.
     */
    @Ignore("easymock doesn't respect the times section")
    @Test
    public void testJPAAccessFactoryEntityManagerOnly() {
        ODataJPAFactoryImpl oDataJPAFactoryImpl = new ODataJPAFactoryImpl();
        JPAAccessFactory jpaAccessFactory = oDataJPAFactoryImpl.getJPAAccessFactory();
        ODataJPAContextImpl oDataJPAContextImpl = new ODataJPAContextImpl();

        final EntityManager em = EasyMock.createMock(EntityManager.class);
        Metamodel metamodel = EasyMock.createMock(Metamodel.class);
        EasyMock.expect(em.getMetamodel())
                .andReturn(metamodel)
                .anyTimes();
        EasyMock.expect(em.isOpen())
                .andReturn(true)
                .anyTimes();
        EasyMock.replay(em);

        oDataJPAContextImpl.setEntityManager(em);

        assertNotNull(jpaAccessFactory.getJPAProcessor(oDataJPAContextImpl));
        assertNotNull(jpaAccessFactory.getJPAEdmModelView(oDataJPAContextImpl));
    }

    /**
     * Test odata jpa access factory.
     */
    @Ignore("easymock doesn't respect the times section")
    @Test
    public void testOdataJpaAccessFactory() {

        ODataJPAFactoryImpl oDataJPAFactoryImpl = new ODataJPAFactoryImpl();
        ODataJPAAccessFactory jpaAccessFactory = oDataJPAFactoryImpl.getODataJPAAccessFactory();
        ODataJPAContextImpl oDataJPAContextImpl = new ODataJPAContextImpl();

        final EntityManager em = EasyMock.createMock(EntityManager.class);
        Metamodel metamodel = EasyMock.createMock(Metamodel.class);
        EasyMock.expect(em.getMetamodel())
                .andReturn(metamodel)
                .anyTimes();
        EasyMock.expect(em.isOpen())
                .andReturn(true)
                .anyTimes();
        EasyMock.replay(em);

        oDataJPAContextImpl.setEntityManagerFactory(new TestEntityManagerFactory(em));
        oDataJPAContextImpl.setPersistenceUnitName("pUnit");

        assertNotNull(jpaAccessFactory.getODataJPAMessageService(new Locale("en")));
        assertNotNull(jpaAccessFactory.createODataJPAContext());
        assertNotNull(jpaAccessFactory.createJPAEdmProvider(oDataJPAContextImpl));
        assertNotNull(jpaAccessFactory.createODataProcessor(oDataJPAContextImpl));
    }

    /**
     * Test odata jpa access factory entity manager only.
     */
    @Test
    public void testOdataJpaAccessFactoryEntityManagerOnly() {
        ODataJPAFactoryImpl oDataJPAFactoryImpl = new ODataJPAFactoryImpl();
        ODataJPAAccessFactory jpaAccessFactory = oDataJPAFactoryImpl.getODataJPAAccessFactory();
        ODataJPAContextImpl oDataJPAContextImpl = new ODataJPAContextImpl();

        EntityManager em = EasyMock.createMock(EntityManager.class);
        Metamodel metamodel = EasyMock.createMock(Metamodel.class);
        EasyMock.expect(em.getMetamodel())
                .andReturn(metamodel)
                .anyTimes();
        EasyMock.expect(em.isOpen())
                .andReturn(true)
                .anyTimes();
        EasyMock.replay(em);

        oDataJPAContextImpl.setEntityManager(em);
        oDataJPAContextImpl.setPersistenceUnitName("pUnit");

        assertNotNull(jpaAccessFactory.getODataJPAMessageService(new Locale("en")));
        assertNotNull(jpaAccessFactory.createODataJPAContext());
        assertNotNull(jpaAccessFactory.createJPAEdmProvider(oDataJPAContextImpl));
        assertNotNull(jpaAccessFactory.createODataProcessor(oDataJPAContextImpl));
    }

    /**
     * A factory for creating TestEntityManager objects.
     */
    private static class TestEntityManagerFactory implements EntityManagerFactory {

        /** The em. */
        private final EntityManager em;

        /**
         * Instantiates a new test entity manager factory.
         *
         * @param entityManager the entity manager
         */
        public TestEntityManagerFactory(EntityManager entityManager) {
            em = entityManager;
        }

        /**
         * Checks if is open.
         *
         * @return true, if is open
         */
        @Override
        public boolean isOpen() {
            return false;
        }

        /**
         * Gets the properties.
         *
         * @return the properties
         */
        @Override
        public Map<String, Object> getProperties() {
            return null;
        }

        /**
         * Gets the persistence unit util.
         *
         * @return the persistence unit util
         */
        @Override
        public PersistenceUnitUtil getPersistenceUnitUtil() {
            return null;
        }

        /**
         * Adds the named query.
         *
         * @param s the s
         * @param query the query
         */
        @Override
        public void addNamedQuery(String s, Query query) {

        }

        /**
         * Unwrap.
         *
         * @param <T> the generic type
         * @param aClass the a class
         * @return the t
         */
        @Override
        public <T> T unwrap(Class<T> aClass) {
            return null;
        }

        /**
         * Adds the named entity graph.
         *
         * @param <T> the generic type
         * @param s the s
         * @param entityGraph the entity graph
         */
        @Override
        public <T> void addNamedEntityGraph(String s, EntityGraph<T> entityGraph) {

        }

        /**
         * Gets the metamodel.
         *
         * @return the metamodel
         */
        @Override
        public Metamodel getMetamodel() {
            return null;
        }

        /**
         * Gets the criteria builder.
         *
         * @return the criteria builder
         */
        @Override
        public CriteriaBuilder getCriteriaBuilder() {
            return null;
        }

        /**
         * Gets the cache.
         *
         * @return the cache
         */
        @Override
        public Cache getCache() {
            return null;
        }

        /**
         * Creates a new TestEntityManager object.
         *
         * @param arg0 the arg 0
         * @return the entity manager
         */
        @SuppressWarnings("rawtypes")
        @Override
        public EntityManager createEntityManager(final Map arg0) {
            return em;
        }

        /**
         * Creates a new TestEntityManager object.
         *
         * @param synchronizationType the synchronization type
         * @return the entity manager
         */
        @Override
        public EntityManager createEntityManager(SynchronizationType synchronizationType) {
            return null;
        }

        /**
         * Creates a new TestEntityManager object.
         *
         * @param synchronizationType the synchronization type
         * @param map the map
         * @return the entity manager
         */
        @Override
        public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
            return null;
        }

        /**
         * Creates a new TestEntityManager object.
         *
         * @return the entity manager
         */
        @Override
        public EntityManager createEntityManager() {
            return em;
        }

        /**
         * Close.
         */
        @Override
        public void close() {}
    }

}
