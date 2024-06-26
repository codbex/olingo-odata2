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

import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * A view on Java Persistence Entity Relationship and Entity Data Model
 * Association End.
 * </p>
 * <p>
 * The implementation of the view provides access to EDM Association Ends
 * created from Java Persistence Entity Relationships. The implementation acts
 * as a container for Association Ends.
 * </p>
 *
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView
 * @org.apache.olingo.odata2.DoNotImplement 
 */
public interface JPAEdmAssociationEndView extends JPAEdmBaseView {

  /**
   * The method gets the one of the association ends present in the container.
   * 
   * @return one of the {@link org.apache.olingo.odata2.api.edm.provider.AssociationEnd} for an
   * {@link org.apache.olingo.odata2.api.edm.provider.Association}
   */
  AssociationEnd getEdmAssociationEnd2();

  /**
   * The method gets the other association end present in the container.
   * 
   * @return one of the {@link org.apache.olingo.odata2.api.edm.provider.AssociationEnd} for an
   * {@link org.apache.olingo.odata2.api.edm.provider.Association}
   */
  AssociationEnd getEdmAssociationEnd1();

  /**
   * The method compares two ends {<b>end1, end2</b>} of an
   * {@link org.apache.olingo.odata2.api.edm.provider.AssociationEnd} against its
   * two ends.
   * 
   * The Method compares the following properties in each end for equality <i>
   * <ul>
   * <li>{@link org.apache.olingo.odata2.api.edm.FullQualifiedName} of End Type</li>
   * <li>{@link org.apache.olingo.odata2.api.edm.EdmMultiplicity} of End</li>
   * </ul>
   * </i>
   * 
   * @param end1
   * one end of type {@link org.apache.olingo.odata2.api.edm.provider.AssociationEnd} of
   * an {@link org.apache.olingo.odata2.api.edm.provider.Association}
   * @param end2
   * other end of type {@link org.apache.olingo.odata2.api.edm.provider.AssociationEnd} of
   * an {@link org.apache.olingo.odata2.api.edm.provider.Association} <p>
   * @return <ul>
   * <li><i>true</i> - Only if the properties of <b>end1</b> matches
   * with all the properties of any one end and only if the properties
   * of <b>end2</b> matches with all the properties of the remaining
   * end</li> <li><i>false</i> - Otherwise</li>
   * </ul>
   */
  boolean compare(AssociationEnd end1, AssociationEnd end2);

  /**
   * Gets the join column names.
   *
   * @return the join column names
   */
  String[] getJoinColumnNames();

  /**
   * Gets the join column reference column names.
   *
   * @return the join column reference column names
   */
  String[] getJoinColumnReferenceColumnNames();

  /**
   * Gets the mapped by name.
   *
   * @return the mapped by name
   */
  String getMappedByName();

  /**
   * Gets the owning property name.
   *
   * @return the owning property name
   */
  String getOwningPropertyName();

}
