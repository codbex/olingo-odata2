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

import org.apache.olingo.odata2.api.edm.provider.ReferentialConstraint;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * A view on Java Persistence Entity Join Columns and Entity Data Model
 * Referential Constraint. Each java persistence entity with properties
 * annotated with Join Columns are transformed into Referential constraints.
 * </p>
 * <p>
 * The implementation of the view provides access to EDM referential constraint
 * created from Java Persistence Entity Join Columns. The implementation acts as
 * a container for EDM referential constraint. A referential constraint is said
 * to be consistent only if referential constraint role is consistent.
 * </p>
 * 
 * <br>
 *
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmReferentialConstraintRoleView
 * @org.apache.olingo.odata2.DoNotImplement <br>
 */
public interface JPAEdmReferentialConstraintView extends JPAEdmBaseView {

  /**
   * The method returns EDM referential constraint created from Java
   * persistence Entity Join Columns.
   * 
   * @return an instance of type {@link org.apache.olingo.odata2.api.edm.provider.ReferentialConstraint}
   */
  public ReferentialConstraint getEdmReferentialConstraint();

  /**
   * The method returns if a valid referential constraint exists for a given
   * EDM association. If there exists a JPA entity relationship with join
   * column having a valid "Name" and "ReferenceColumnName", that can be
   * mapped to EDM properties in dependent and source EDM entities
   * respectively then a valid EDM referential constraint exists.
   * 
   * @return true if there exists a valid referential constraint else false.
   */
  public boolean isExists();

  /**
   * The method returns the name of EDM Association.
   * 
   * @return name of an EDM association
   */
  public String getEdmRelationShipName();
}
