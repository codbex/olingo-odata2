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
package org.apache.olingo.odata2.jpa.processor.core.factory;

import java.util.Locale;

import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPADefaultProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAResponseBuilder;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmMappingModelAccess;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAMethodContext.JPAMethodContextBuilder;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAProcessor;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAMessageService;
import org.apache.olingo.odata2.jpa.processor.api.factory.JPAAccessFactory;
import org.apache.olingo.odata2.jpa.processor.api.factory.JPQLBuilderFactory;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAAccessFactory;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement.JPQLStatementBuilder;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmModelView;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAResponseBuilderDefault;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAFunctionContext;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAProcessorImpl;
import org.apache.olingo.odata2.jpa.processor.core.access.model.JPAEdmMappingModelService;
import org.apache.olingo.odata2.jpa.processor.core.edm.ODataJPAEdmProvider;
import org.apache.olingo.odata2.jpa.processor.core.exception.ODataJPAMessageServiceDefault;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLJoinSelectContext;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLJoinSelectSingleContext;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLJoinSelectSingleStatementBuilder;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLJoinStatementBuilder;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLSelectContext;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLSelectSingleContext;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLSelectSingleStatementBuilder;
import org.apache.olingo.odata2.jpa.processor.core.jpql.JPQLSelectStatementBuilder;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataJPAFactoryImpl.
 */
public class ODataJPAFactoryImpl extends ODataJPAFactory {

  /**
   * Gets the JPQL builder factory.
   *
   * @return the JPQL builder factory
   */
  @Override
  public JPQLBuilderFactory getJPQLBuilderFactory() {
    return JPQLBuilderFactoryImpl.create();
  };

  /**
   * Gets the JPA access factory.
   *
   * @return the JPA access factory
   */
  @Override
  public JPAAccessFactory getJPAAccessFactory() {
    return JPAAccessFactoryImpl.create();
  };

  /**
   * Gets the o data JPA access factory.
   *
   * @return the o data JPA access factory
   */
  @Override
  public ODataJPAAccessFactory getODataJPAAccessFactory() {
    return ODataJPAAccessFactoryImpl.create();
  };

  /**
   * The Class JPQLBuilderFactoryImpl.
   */
  private static class JPQLBuilderFactoryImpl implements JPQLBuilderFactory {

    /** The factory. */
    private static JPQLBuilderFactoryImpl factory = null;

    /**
     * Instantiates a new JPQL builder factory impl.
     */
    private JPQLBuilderFactoryImpl() {}

    /**
     * Gets the statement builder.
     *
     * @param context the context
     * @return the statement builder
     */
    @Override
    public JPQLStatementBuilder getStatementBuilder(final JPQLContextView context) {
      JPQLStatementBuilder builder = null;
      switch (context.getType()) {
      case SELECT:
      case SELECT_COUNT: // for $count, Same as select
        builder = new JPQLSelectStatementBuilder(context);
        break;
      case SELECT_SINGLE:
        builder = new JPQLSelectSingleStatementBuilder(context);
        break;
      case JOIN:
      case JOIN_COUNT: // for $count, Same as join
        builder = new JPQLJoinStatementBuilder(context);
        break;
      case JOIN_SINGLE:
        builder = new JPQLJoinSelectSingleStatementBuilder(context);
        break;
      default:
        break;
      }

      return builder;
    }

    /**
     * Gets the context builder.
     *
     * @param contextType the context type
     * @return the context builder
     */
    @Override
    public JPQLContextBuilder getContextBuilder(final JPQLContextType contextType) {
      JPQLContextBuilder contextBuilder = null;

      switch (contextType) {
      case SELECT:
        JPQLSelectContext selectContext = new JPQLSelectContext(false);
        contextBuilder = selectContext.new JPQLSelectContextBuilder();
        break;
      case SELECT_SINGLE:
        JPQLSelectSingleContext singleSelectContext = new JPQLSelectSingleContext();
        contextBuilder = singleSelectContext.new JPQLSelectSingleContextBuilder();
        break;
      case JOIN:
        JPQLJoinSelectContext joinContext = new JPQLJoinSelectContext(false);
        contextBuilder = joinContext.new JPQLJoinContextBuilder();
        break;
      case JOIN_SINGLE:
        JPQLJoinSelectSingleContext joinSingleContext = new JPQLJoinSelectSingleContext();
        contextBuilder = joinSingleContext.new JPQLJoinSelectSingleContextBuilder();
        break;
      case SELECT_COUNT:
        JPQLSelectContext selectCountContext = new JPQLSelectContext(true);
        contextBuilder = selectCountContext.new JPQLSelectContextBuilder();
        break;
      case JOIN_COUNT:
        JPQLJoinSelectContext joinCountContext = new JPQLJoinSelectContext(true);
        contextBuilder = joinCountContext.new JPQLJoinContextBuilder();
        break;
      default:
        break;
      }

      return contextBuilder;
    }

    /**
     * Creates the.
     *
     * @return the JPQL builder factory
     */
    private static JPQLBuilderFactory create() {
      if (factory == null) {
        return new JPQLBuilderFactoryImpl();
      } else {
        return factory;
      }
    }

    /**
     * Gets the JPA method context builder.
     *
     * @param contextType the context type
     * @return the JPA method context builder
     */
    @Override
    public JPAMethodContextBuilder getJPAMethodContextBuilder(final JPQLContextType contextType) {

      JPAMethodContextBuilder contextBuilder = null;
      switch (contextType) {
      case FUNCTION:
        JPAFunctionContext methodConext = new JPAFunctionContext();
        contextBuilder = methodConext.new JPAFunctionContextBuilder();

        break;
      default:
        break;
      }
      return contextBuilder;
    }

  }

  /**
   * The Class ODataJPAAccessFactoryImpl.
   */
  private static class ODataJPAAccessFactoryImpl implements ODataJPAAccessFactory {

    /** The factory. */
    private static ODataJPAAccessFactoryImpl factory = null;

    /**
     * Instantiates a new o data JPA access factory impl.
     */
    private ODataJPAAccessFactoryImpl() {}

    /**
     * Creates the O data processor.
     *
     * @param oDataJPAContext the o data JPA context
     * @return the o data single processor
     */
    @Override
    public ODataSingleProcessor createODataProcessor(final ODataJPAContext oDataJPAContext) {
      return new ODataJPADefaultProcessor(oDataJPAContext) { };
    }

    /**
     * Creates the JPA edm provider.
     *
     * @param oDataJPAContext the o data JPA context
     * @return the edm provider
     */
    @Override
    public EdmProvider createJPAEdmProvider(final ODataJPAContext oDataJPAContext) {
      return new ODataJPAEdmProvider(oDataJPAContext);
    }

    /**
     * Creates the O data JPA context.
     *
     * @return the o data JPA context
     */
    @Override
    public ODataJPAContext createODataJPAContext() {
      return new ODataJPAContextImpl();
    }

    /**
     * Creates the.
     *
     * @return the o data JPA access factory impl
     */
    private static ODataJPAAccessFactoryImpl create() {
      if (factory == null) {
        return new ODataJPAAccessFactoryImpl();
      } else {
        return factory;
      }
    }

    /**
     * Gets the o data JPA message service.
     *
     * @param locale the locale
     * @return the o data JPA message service
     */
    @Override
    public ODataJPAMessageService getODataJPAMessageService(final Locale locale) {
      return ODataJPAMessageServiceDefault.getInstance(locale);
    }

    /**
     * Gets the o data JPA response builder.
     *
     * @param oDataJPAContext the o data JPA context
     * @return the o data JPA response builder
     */
    @Override
    public ODataJPAResponseBuilder getODataJPAResponseBuilder(final ODataJPAContext oDataJPAContext) {
      return new ODataJPAResponseBuilderDefault(oDataJPAContext);
    }

  }

  /**
   * The Class JPAAccessFactoryImpl.
   */
  private static class JPAAccessFactoryImpl implements JPAAccessFactory {

    /** The factory. */
    private static JPAAccessFactoryImpl factory = null;

    /**
     * Instantiates a new JPA access factory impl.
     */
    private JPAAccessFactoryImpl() {}

    /**
     * Gets the JPA edm model view.
     *
     * @param oDataJPAContext the o data JPA context
     * @return the JPA edm model view
     */
    @Override
    public JPAEdmModelView getJPAEdmModelView(final ODataJPAContext oDataJPAContext) {
      JPAEdmModelView view = null;

      view = new JPAEdmModel(oDataJPAContext);
      return view;
    }

    /**
     * Gets the JPA processor.
     *
     * @param oDataJPAContext the o data JPA context
     * @return the JPA processor
     */
    @Override
    public JPAProcessor getJPAProcessor(final ODataJPAContext oDataJPAContext) {
      JPAProcessor jpaProcessor = new JPAProcessorImpl(oDataJPAContext);

      return jpaProcessor;
    }

    /**
     * Creates the.
     *
     * @return the JPA access factory impl
     */
    private static JPAAccessFactoryImpl create() {
      if (factory == null) {
        return new JPAAccessFactoryImpl();
      } else {
        return factory;
      }
    }

    /**
     * Gets the JPA edm mapping model access.
     *
     * @param oDataJPAContext the o data JPA context
     * @return the JPA edm mapping model access
     */
    @Override
    public JPAEdmMappingModelAccess getJPAEdmMappingModelAccess(final ODataJPAContext oDataJPAContext) {
      JPAEdmMappingModelAccess mappingModelAccess = new JPAEdmMappingModelService(oDataJPAContext);

      return mappingModelAccess;
    }

    /**
     * Gets the JPA edm mapping instance.
     *
     * @return the JPA edm mapping instance
     */
    @Override
    public JPAEdmMapping getJPAEdmMappingInstance() {
      return new JPAEdmMappingImpl();
    }

  }
}
