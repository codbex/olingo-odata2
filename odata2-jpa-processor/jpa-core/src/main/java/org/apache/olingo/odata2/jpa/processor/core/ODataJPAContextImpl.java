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
package org.apache.olingo.odata2.jpa.processor.core;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataProcessor;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATransaction;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAPaging;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataJPAContextImpl.
 */
public class ODataJPAContextImpl implements ODataJPAContext {

  /** The p unit name. */
  private String pUnitName;
  
  /** The emf. */
  private EntityManagerFactory emf;
  
  /** The Constant emThreadLocal. */
  private static final ThreadLocal<EntityManager> emThreadLocal = new ThreadLocal<EntityManager>();
  
  /** The odata context. */
  private ODataContext odataContext;
  
  /** The processor. */
  private ODataProcessor processor;
  
  /** The edm provider. */
  private EdmProvider edmProvider;
  
  /** The jpa edm mapping model name. */
  private String jpaEdmMappingModelName;
  
  /** The jpa edm extension. */
  private JPAEdmExtension jpaEdmExtension;
  
  /** The page size. */
  private int pageSize = 0;
  
  /** The jpa paging. */
  private JPAPaging jpaPaging;
  
  /** The Constant oDataContextThreadLocal. */
  private static final ThreadLocal<ODataContext> oDataContextThreadLocal = new ThreadLocal<ODataContext>();
  
  /** The default naming. */
  private boolean defaultNaming = true;
  
  /** The transaction. */
  private ODataJPATransaction transaction = null;
  
  /** The container managed. */
  private boolean containerManaged = false;

  /**
   * Gets the persistence unit name.
   *
   * @return the persistence unit name
   */
  @Override
  public String getPersistenceUnitName() {
    return pUnitName;
  }

  /**
   * Sets the persistence unit name.
   *
   * @param pUnitName the new persistence unit name
   */
  @Override
  public void setPersistenceUnitName(final String pUnitName) {
    this.pUnitName = pUnitName;
  }

  /**
   * Gets the entity manager factory.
   *
   * @return the entity manager factory
   */
  @Override
  public EntityManagerFactory getEntityManagerFactory() {
    return emf;
  }

  /**
   * Sets the entity manager factory.
   *
   * @param emf the new entity manager factory
   */
  @Override
  public void setEntityManagerFactory(final EntityManagerFactory emf) {
    this.emf = emf;
  }

  /**
   * Sets the o data context.
   *
   * @param ctx the new o data context
   */
  @Override
  public void setODataContext(final ODataContext ctx) {
    odataContext = ctx;
    setContextInThreadLocal(odataContext);
  }

  /**
   * Gets the o data context.
   *
   * @return the o data context
   */
  @Override
  public ODataContext getODataContext() {
    return odataContext;
  }

  /**
   * Sets the o data processor.
   *
   * @param processor the new o data processor
   */
  @Override
  public void setODataProcessor(final ODataProcessor processor) {
    this.processor = processor;
  }

  /**
   * Gets the o data processor.
   *
   * @return the o data processor
   */
  @Override
  public ODataProcessor getODataProcessor() {
    return processor;
  }

  /**
   * Sets the edm provider.
   *
   * @param edmProvider the new edm provider
   */
  @Override
  public void setEdmProvider(final EdmProvider edmProvider) {
    this.edmProvider = edmProvider;
  }

  /**
   * Gets the edm provider.
   *
   * @return the edm provider
   */
  @Override
  public EdmProvider getEdmProvider() {
    return edmProvider;
  }

  /**
   * Sets the JPA edm mapping model.
   *
   * @param name the new JPA edm mapping model
   */
  @Override
  public void setJPAEdmMappingModel(final String name) {
    jpaEdmMappingModelName = name;

  }

  /**
   * Gets the JPA edm mapping model.
   *
   * @return the JPA edm mapping model
   */
  @Override
  public String getJPAEdmMappingModel() {
    return jpaEdmMappingModelName;
  }

  /**
   * Sets the context in thread local.
   *
   * @param ctx the new context in thread local
   */
  public static void setContextInThreadLocal(final ODataContext ctx) {
    oDataContextThreadLocal.set(ctx);
  }

  /**
   * Unset context in thread local.
   */
  public static void unsetContextInThreadLocal() {
    oDataContextThreadLocal.remove();
  }

  /**
   * Gets the context in thread local.
   *
   * @return the context in thread local
   */
  public static ODataContext getContextInThreadLocal() {
    return (ODataContext) oDataContextThreadLocal.get();
  }

  /**
   * Gets the entity manager.
   *
   * @return the entity manager
   */
  @Override
  public EntityManager getEntityManager() {
    EntityManager em = emThreadLocal.get();
    if (em == null || !em.isOpen()) {
        em = emf.createEntityManager();
        setEntityManager(em);
    }
    return em;
  }

  /**
   * Sets the JPA edm extension.
   *
   * @param jpaEdmExtension the new JPA edm extension
   */
  @Override
  public void setJPAEdmExtension(final JPAEdmExtension jpaEdmExtension) {
    this.jpaEdmExtension = jpaEdmExtension;
  }

  /**
   * Gets the JPA edm extension.
   *
   * @return the JPA edm extension
   */
  @Override
  public JPAEdmExtension getJPAEdmExtension() {
    return jpaEdmExtension;
  }

  /**
   * Sets the default naming.
   *
   * @param defaultNaming the new default naming
   */
  @Override
  public void setDefaultNaming(final boolean defaultNaming) {
    this.defaultNaming = defaultNaming;
  }

  /**
   * Gets the default naming.
   *
   * @return the default naming
   */
  @Override
  public boolean getDefaultNaming() {
    return defaultNaming;
  }

  /**
   * Gets the page size.
   *
   * @return the page size
   */
  @Override
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Sets the page size.
   *
   * @param size the new page size
   */
  @Override
  public void setPageSize(final int size) {
    pageSize = size;
  }

  /**
   * Sets the paging.
   *
   * @param paging the new paging
   */
  @Override
  public void setPaging(final JPAPaging paging) {
    jpaPaging = paging;
  }

  /**
   * Gets the paging.
   *
   * @return the paging
   */
  @Override
  public JPAPaging getPaging() {
    return jpaPaging;
  }

  /**
   * Gets the o data JPA transaction.
   *
   * @return the o data JPA transaction
   */
  @Override
  public ODataJPATransaction getODataJPATransaction() {
    if (transaction == null) {
      transaction = odataContext.getServiceFactory().getCallback(ODataJPATransaction.class);
      // fallback to default implementations
      if (transaction == null) {
        if(isContainerManaged()) {
          transaction = new ODataJPATransactionContainerManaged();
        } else {
          // Fallback to RESOURCE_LOCAL based transaction
          transaction = new ODataJPATransactionLocalDefault(getEntityManager());
        }
      }
    }
    return transaction;
  }

  /**
   * Checks if is container managed.
   *
   * @return true, if is container managed
   */
  @Override
  public boolean isContainerManaged() {
    return this.containerManaged;
  }

  /**
   * Sets the container managed.
   *
   * @param containerManaged the new container managed
   */
  @Override
  public void setContainerManaged(boolean containerManaged) {
    this.containerManaged = containerManaged;
  }

  /**
   * Sets the entity manager.
   *
   * @param em the new entity manager
   */
  @Override
  public void setEntityManager(EntityManager em) {
    emThreadLocal.set(em);
  }
}
