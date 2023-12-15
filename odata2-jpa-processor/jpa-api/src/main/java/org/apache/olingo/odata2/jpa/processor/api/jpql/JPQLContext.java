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
package org.apache.olingo.odata2.jpa.processor.api.jpql;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory;

// TODO: Auto-generated Javadoc
/**
 * The abstract class is a compilation of objects required for building
 * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement}. Extend this
 * class to implement specific implementations of JPQL context types (Select,
 * Join). A JPQL Context is constructed from an OData
 * request. Depending on OData CRUD operation performed on an Entity, a
 * corresponding JPQL context object is built. The JPQL context object thus
 * built can be used for constructing JPQL statements. <br>
 * A default implementation is provided by the library.
 * 
 * 
 * @see org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement
 * @see org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType
 * @see org.apache.olingo.odata2.jpa.processor.api.factory.JPQLBuilderFactory
 * 
 */
public abstract class JPQLContext implements JPQLContextView {

  /** An alias for Java Persistence Entity. */
  protected String jpaEntityAlias;
  
  /** Java Persistence Entity name. */
  protected String jpaEntityName;
  /**
   * The type of JPQL context. Based on the type JPQL statements can be built.
   */
  protected JPQLContextType type;
  
  /** The paging requested. */
  protected boolean pagingRequested = false;
  
  /** The Constant jpqlContext. */
  protected static final ThreadLocal<JPQLContext> jpqlContext = new ThreadLocal<JPQLContext>();

  /**
   * sets JPA Entity Name into the context.
   *
   * @param jpaEntityName is the name of JPA Entity
   */
  protected final void setJPAEntityName(final String jpaEntityName) {
    this.jpaEntityName = jpaEntityName;
  }

  /**
   * sets JPA Entity alias name into the context.
   *
   * @param jpaEntityAlias is the JPA entity alias name
   */
  protected final void setJPAEntityAlias(final String jpaEntityAlias) {
    this.jpaEntityAlias = jpaEntityAlias;
  }

  /**
   * gets the JPA entity alias name set into the context.
   *
   * @return the JPA entity alias
   */
  @Override
  public final String getJPAEntityAlias() {
    return jpaEntityAlias;
  }

  /**
   * sets the JPQL context type into the context.
   *
   * @param type is JPQLContextType
   */
  protected final void setType(final JPQLContextType type) {
    this.type = type;
  }
  
  /**
   * gets the JPA entity name set into the context.
   *
   * @return the JPA entity name
   */
  @Override
  public final String getJPAEntityName() {
    return jpaEntityName;
  }

  /**
   * gets the JPQL context type set into the context.
   *
   * @return the type
   */
  @Override
  public final JPQLContextType getType() {
    return type;
  }

  /**
   * Checks if is paging requested.
   *
   * @param pagingRequested the paging requested
   */
  protected void isPagingRequested(final boolean pagingRequested) {
    this.pagingRequested = pagingRequested;
  }
  
  /**
   * the method returns an instance of type
   * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder} based on the
   * JPQLContextType. The context builder can be used for
   * building different JPQL contexts.
   *
   * @param contextType is the JPQLContextType
   * @param resultsView is the OData request view
   * @return an instance of type {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder}
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public final static JPQLContextBuilder createBuilder(final JPQLContextType contextType, final Object resultsView)
      throws ODataJPARuntimeException {
    return JPQLContextBuilder.create(contextType, resultsView, false);
  }

  /**
   * the method returns an instance of type
   * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder} based on the
   * JPQLContextType. The context builder can be used for
   * building different JPQL contexts.
   *
   * @param contextType is the JPQLContextType
   * @param resultsView is the OData request view
   * @param withPaging indicates whether to build the context with paging
   * @return an instance of type {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder}
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public final static JPQLContextBuilder createBuilder(final JPQLContextType contextType, final Object resultsView,
      final boolean withPaging)
      throws ODataJPARuntimeException {
    return JPQLContextBuilder.create(contextType, resultsView, withPaging);
  }
  
  /**
   * Sets the JPQL context.
   *
   * @param context the new JPQL context
   */
  protected static void setJPQLContext(JPQLContext context) {
    jpqlContext.set(context);
  }
  
  /**
   * Gets the JPQL context.
   *
   * @return the JPQL context
   */
  public final static JPQLContext getJPQLContext() {
    return jpqlContext.get();
  }
  
  /**
   * Removes the JPQL context.
   */
  public final static void removeJPQLContext() {
    jpqlContext.remove();
  }
  /**
   * The abstract class is extended by specific JPQLContext builder for
   * building JPQLContexts.
   * 
   * 
   * 
   */
  public static abstract class JPQLContextBuilder {
    
    /** The Constant ALIAS. */
    private static final String ALIAS = "E";

    /**
     * alias counter is an integer counter that is incremented by "1" for
     * every new alias name generation. The value of counter is used in the
     * generation of JPA entity alias names.
     */
    protected int aliasCounter = 0;

    /** The with paging. */
    protected boolean withPaging = false;

    /**
     * Instantiates a new JPQL context builder.
     */
    protected JPQLContextBuilder() {}

    /**
     * the method instantiates an instance of type JPQLContextBuilder.
     *
     * @param contextType indicates the type of JPQLContextBuilder to instantiate.
     * @param resultsView is the OData request view
     * @param withPaging the with paging
     * @return an instance of type
     * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder}
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    private static JPQLContextBuilder create(final JPQLContextType contextType, final Object resultsView,
        final boolean withPaging)
        throws ODataJPARuntimeException {
      JPQLContextBuilder contextBuilder =
          ODataJPAFactory.createFactory().getJPQLBuilderFactory().getContextBuilder(contextType);
      if (contextBuilder == null) {
        throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ERROR_JPQLCTXBLDR_CREATE, null);
      }
      contextBuilder.setResultsView(resultsView);
      contextBuilder.withPaging = withPaging;
      return contextBuilder;
    }

    /**
     * The abstract method is implemented by specific JPQL context builders
     * to build JPQL Contexts. The build method makes use of information set
     * into the context to built JPQL Context Types.
     *
     * @return an instance of {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext}
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    public abstract JPQLContext build() throws ODataJPAModelException, ODataJPARuntimeException;

    /**
     * The abstract method is implemented by specific JPQL context builder.
     * The method sets the OData request view into the JPQL context.
     * 
     * @param resultsView
     * is an instance representing OData request.
     */
    protected abstract void setResultsView(Object resultsView);

    /**
     * The method resets the alias counter value to "0".
     */
    protected void resetAliasCounter() {
      aliasCounter = 0;
    }

    /**
     * The method returns a system generated alias name starting with prefix
     * "E" and ending with suffix "aliasCounter".
     * 
     * @return a String representing JPA entity alias name
     */
    protected String generateJPAEntityAlias() {
      return new String(ALIAS + ++aliasCounter);
    }
  }
}
