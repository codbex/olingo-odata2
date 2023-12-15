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

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationSetView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityContainerView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntitySetView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import org.apache.olingo.odata2.jpa.processor.core.access.model.JPAEdmNameBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmEntityContainer.
 */
public class JPAEdmEntityContainer extends JPAEdmBaseViewImpl implements JPAEdmEntityContainerView {

  /** The entity set view. */
  private JPAEdmEntitySetView entitySetView;
  
  /** The schema view. */
  private JPAEdmSchemaView schemaView;
  
  /** The association set view. */
  private JPAEdmAssociationSetView associationSetView;

  /** The current entity container. */
  private EntityContainer currentEntityContainer;
  
  /** The consistent entity container list. */
  private List<EntityContainer> consistentEntityContainerList;

  /**
   * Instantiates a new JPA edm entity container.
   *
   * @param view the view
   */
  public JPAEdmEntityContainer(final JPAEdmSchemaView view) {
    super(view);
    schemaView = view;
  }

  /**
   * Gets the builder.
   *
   * @return the builder
   */
  @Override
  public JPAEdmBuilder getBuilder() {
    if (builder == null) {
      builder = new JPAEdmEntityContainerBuilder();
    }

    return builder;
  }

  /**
   * Gets the edm entity container.
   *
   * @return the edm entity container
   */
  @Override
  public EntityContainer getEdmEntityContainer() {
    return currentEntityContainer;
  }

  /**
   * Gets the consistent edm entity container list.
   *
   * @return the consistent edm entity container list
   */
  @Override
  public List<EntityContainer> getConsistentEdmEntityContainerList() {
    return consistentEntityContainerList;
  }

  /**
   * Gets the JPA edm entity set view.
   *
   * @return the JPA edm entity set view
   */
  @Override
  public JPAEdmEntitySetView getJPAEdmEntitySetView() {
    return entitySetView;
  }

  /**
   * Gets the edm association set view.
   *
   * @return the edm association set view
   */
  @Override
  public JPAEdmAssociationSetView getEdmAssociationSetView() {
    return associationSetView;
  }

  /**
   * Clean.
   */
  @Override
  public void clean() {
    super.clean();
    entitySetView = null;
    associationSetView = null;
    currentEntityContainer = null;
    consistentEntityContainerList = null;
  }

  /**
   * The Class JPAEdmEntityContainerBuilder.
   */
  private class JPAEdmEntityContainerBuilder implements JPAEdmBuilder {
    
    /**
     * Builds the.
     *
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    /*
     * 
     * Each call to build method creates a new Entity Container and builds
     * the entity container with Association Sets and Entity Sets. The newly
     * created and built entity container is added to the exiting Entity
     * Container List.
     * 
     * ************************************************************ Build
     * EDM Entity Container - STEPS
     * ************************************************************ 1)
     * Instantiate New EDM Entity Container 2) Build Name for EDM Entity
     * Container 2) Create Entity Container List (if does not exists) 3)
     * Build EDM Entity Set 4) Add EDM Entity Set to EDM Entity Container 6)
     * Build EDM Association Set 7) Add EDM Association Set to EDM Entity
     * Container 8) Add EDM Entity Container to the Container List
     * ************************************************************ Build
     * EDM Entity Container - STEPS
     * ************************************************************
     */
    @Override
    public void build() throws ODataJPAModelException, ODataJPARuntimeException {

      currentEntityContainer = new EntityContainer();

      if (consistentEntityContainerList == null) {
        currentEntityContainer.setDefaultEntityContainer(true);
        consistentEntityContainerList = new ArrayList<EntityContainer>();
      }

      entitySetView = new JPAEdmEntitySet(schemaView);
      entitySetView.getBuilder().build();
      if (entitySetView.isConsistent()) {
        currentEntityContainer.setEntitySets(entitySetView.getConsistentEdmEntitySetList());
      } else {
        isConsistent = false;
        return;
      }

      if (!schemaView.getJPAEdmAssociationView().isConsistent()) {
        schemaView.getJPAEdmAssociationView().getBuilder().build();
      }

      associationSetView = new JPAEdmAssociationSet(schemaView);
      associationSetView.getBuilder().build();
      if (associationSetView.isConsistent()) {
        currentEntityContainer.setAssociationSets(associationSetView.getConsistentEdmAssociationSetList());
      } else {
        isConsistent = false;
        return;
      }

      JPAEdmNameBuilder.build(JPAEdmEntityContainer.this);
      consistentEntityContainerList.add(currentEntityContainer);
      isConsistent = true;

    }

  }
}
