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
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmModelView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmModel.
 */
public class JPAEdmModel extends JPAEdmBaseViewImpl implements JPAEdmModelView {

  /** The schema view. */
  protected JPAEdmSchemaView schemaView;

  /**
   * Instantiates a new JPA edm model.
   *
   * @param metaModel the meta model
   * @param pUnitName the unit name
   */
  public JPAEdmModel(final Metamodel metaModel, final String pUnitName) {
    super(metaModel, pUnitName);
  }

  /**
   * Instantiates a new JPA edm model.
   *
   * @param ctx the ctx
   */
  public JPAEdmModel(final ODataJPAContext ctx) {
    super(ctx);
  }

  /**
   * Gets the edm schema view.
   *
   * @return the edm schema view
   */
  @Override
  public JPAEdmSchemaView getEdmSchemaView() {
    return schemaView;
  }

  /**
   * Gets the builder.
   *
   * @return the builder
   */
  @Override
  public JPAEdmBuilder getBuilder() {
    if (builder == null) {
      builder = new JPAEdmModelBuilder();
    }

    return builder;
  }

  /**
   * The Class JPAEdmModelBuilder.
   */
  private class JPAEdmModelBuilder implements JPAEdmBuilder {

    /**
     * Builds the.
     *
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    @Override
    public void build() throws ODataJPAModelException, ODataJPARuntimeException {
      schemaView = new JPAEdmSchema(JPAEdmModel.this);
      schemaView.getBuilder().build();
    }

  }
}
