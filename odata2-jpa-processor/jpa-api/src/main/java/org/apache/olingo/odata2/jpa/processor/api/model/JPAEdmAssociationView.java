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

import org.apache.olingo.odata2.api.edm.provider.Association;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * A View on Java Persistence Entity Relationship and Entity Data Model
 * Association.
 * </p>
 * <p>
 * The implementation of the view provides access to EDM Association created
 * from Java Persistence Entity Relationships. The implementation acts as a
 * container for list of association that are consistent.
 * 
 * An Association is said to be consistent only
 * <ol>
 * <li>If both the Ends of Association are consistent</li>
 * <li>If referential constraint exists for the Association then it should be
 * consistent</li>
 * </ol>
 * </p>
 * 
 * 
 * <p>
 *
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationSetView
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmReferentialConstraintView
 * @org.apache.olingo.odata2.DoNotImplement 
 */
public interface JPAEdmAssociationView extends JPAEdmBaseView {

  /**
   * The method returns an association which is currently being processed.
   * 
   * @return an {@link org.apache.olingo.odata2.api.edm.provider.Association}
   */
  public Association getEdmAssociation();

  /**
   * The method returns a consistent list of associations. An association is
   * set to be consistent only if all its mandatory properties can be
   * completely built from a Java Persistence Relationship.
   * 
   * @return a consistent list of {@link org.apache.olingo.odata2.api.edm.provider.Association}
   * 
   */
  public List<Association> getConsistentEdmAssociationList();

  /**
   * The method adds {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView} to its container
   *
   * @param associationView of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView}
   * @param associationEndView the association end view
   */
  public void addJPAEdmAssociationView(JPAEdmAssociationView associationView,
      JPAEdmAssociationEndView associationEndView);

  /**
   * The method searches for an Association in its container against the
   * search parameter <b>view</b> of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView}
   * .
   * 
   * The Association in the container <b>view</b> is searched against the
   * consistent list of Association stored in this container.
   * 
   * @param view
   * of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView}
   * @return {@link org.apache.olingo.odata2.api.edm.provider.Association} if found
   * in the container
   */
  public Association searchAssociation(JPAEdmAssociationEndView view);

  /**
   * The method adds the referential constraint view to its container.
   * <p>
   * <b>Note: </b>The referential constraint view is added only if it exists.
   * </p>
   * 
   * @param refView
   * of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmReferentialConstraintView}
   */
  public void addJPAEdmRefConstraintView(JPAEdmReferentialConstraintView refView);

  /**
   * The method returns the referential constraint view that is currently
   * being processed.
   * 
   * @return an instance of type
   * {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmReferentialConstraintView}
   */
  public JPAEdmReferentialConstraintView getJPAEdmReferentialConstraintView();

  /**
   * The method searches for the number of associations with similar endpoints in its container against the
   * search parameter <b>view</b> of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView}
   * .
   * 
   * The Association in the container <b>view</b> is searched against the
   * consistent list of Association stored in this container.
   * 
   * @param view
   * of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView}
   * @return {@link org.apache.olingo.odata2.api.edm.provider.Association} if found
   * in the container
   */
  int getNumberOfAssociationsWithSimilarEndPoints(JPAEdmAssociationEndView view);

}
