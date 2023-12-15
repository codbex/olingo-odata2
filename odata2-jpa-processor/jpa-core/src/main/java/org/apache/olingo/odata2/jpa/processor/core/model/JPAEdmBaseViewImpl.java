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

import jakarta.persistence.metamodel.Metamodel;

import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmMappingModelAccess;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmBaseView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmBaseViewImpl.
 */
public abstract class JPAEdmBaseViewImpl implements JPAEdmBaseView {

  /** The skip default naming. */
  protected boolean skipDefaultNaming = false;
  
  /** The p unit name. */
  protected String pUnitName = null;
  
  /** The meta model. */
  protected Metamodel metaModel = null;
  
  /** The is consistent. */
  protected boolean isConsistent = true;
  
  /** The builder. */
  protected JPAEdmBuilder builder = null;
  
  /** The jpa edm extension. */
  protected JPAEdmExtension jpaEdmExtension = null;
  
  /** The jpa edm mapping model access. */
  private JPAEdmMappingModelAccess jpaEdmMappingModelAccess = null;

  /**
   * Instantiates a new JPA edm base view impl.
   *
   * @param view the view
   */
  public JPAEdmBaseViewImpl(final JPAEdmBaseView view) {
    pUnitName = view.getpUnitName();
    metaModel = view.getJPAMetaModel();
    jpaEdmMappingModelAccess = view.getJPAEdmMappingModelAccess();
    jpaEdmExtension = view.getJPAEdmExtension();
    skipDefaultNaming = view.isDefaultNamingSkipped();
  }

  /**
   * Instantiates a new JPA edm base view impl.
   *
   * @param context the context
   */
  public JPAEdmBaseViewImpl(final ODataJPAContext context) {
    pUnitName = context.getPersistenceUnitName();
    metaModel = context.getEntityManager().getMetamodel();
    jpaEdmMappingModelAccess =
        ODataJPAFactory.createFactory().getJPAAccessFactory().getJPAEdmMappingModelAccess(context);
    jpaEdmExtension = context.getJPAEdmExtension();
    jpaEdmMappingModelAccess.loadMappingModel();
    skipDefaultNaming = !context.getDefaultNaming();
  }

  /**
   * Instantiates a new JPA edm base view impl.
   *
   * @param metaModel the meta model
   * @param pUnitName the unit name
   */
  public JPAEdmBaseViewImpl(final Metamodel metaModel, final String pUnitName) {
    this.metaModel = metaModel;
    this.pUnitName = pUnitName;
  }

  /**
   * Gets the p unit name.
   *
   * @return the p unit name
   */
  @Override
  public String getpUnitName() {
    return pUnitName;
  }

  /**
   * Gets the JPA meta model.
   *
   * @return the JPA meta model
   */
  @Override
  public Metamodel getJPAMetaModel() {
    return metaModel;
  }

  /**
   * Checks if is consistent.
   *
   * @return true, if is consistent
   */
  @Override
  public boolean isConsistent() {
    return isConsistent;
  }

  /**
   * Clean.
   */
  @Override
  public void clean() {
    pUnitName = null;
    metaModel = null;
    isConsistent = false;
  }

  /**
   * Gets the JPA edm mapping model access.
   *
   * @return the JPA edm mapping model access
   */
  @Override
  public JPAEdmMappingModelAccess getJPAEdmMappingModelAccess() {
    return jpaEdmMappingModelAccess;

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
   * Checks if is default naming skipped.
   *
   * @return true, if is default naming skipped
   */
  @Override
  public boolean isDefaultNamingSkipped() {
    return skipDefaultNaming;
  }

}
