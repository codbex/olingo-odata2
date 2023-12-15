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
package org.apache.olingo.odata2.jpa.processor.api.model;

import java.util.List;

import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;

// TODO: Auto-generated Javadoc
/**
 * A view on Java persistence entity relationship and EDM navigation property.
 * Java persistence entity relationships annotated as
 * <ol>
 * <li>Many To Many</li>
 * <li>One To Many</li>
 * <li>One To One</li>
 * <li>Many To One</li>
 * </ol>
 * are transformed into navigation properties.
 * <p>
 * The implementation of the view provides access to EDM navigation properties
 * for a given JPA EDM entity type. The view acts as a container for consistent
 * list of EDM navigation properties of an EDM entity type. EDM navigation
 * property is consistent only if there exists a consistent EDM association.
 *
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView
 * @org.apache.olingo.odata2.DoNotImplement 
 */
public interface JPAEdmNavigationPropertyView extends JPAEdmBaseView {
  /**
   * The method adds a navigation property view to its container.
   * 
   * @param view
   * is an instance of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmNavigationPropertyView}
   */
  void addJPAEdmNavigationPropertyView(JPAEdmNavigationPropertyView view);

  /**
   * The method returns a consistent list of EDM navigation property. A
   * navigation property is consistent only if all its mandatory properties
   * can be built with no errors from Java persistence entity relationship.
   * 
   * @return a list of consistent EDM navigation property for the Entity
   */
  List<NavigationProperty> getConsistentEdmNavigationProperties();

  /**
   * The method returns the navigation property that is currently being
   * processed.
   * 
   * @return an instance of type {@link org.apache.olingo.odata2.api.edm.provider.NavigationProperty}
   */
  NavigationProperty getEdmNavigationProperty();

}
