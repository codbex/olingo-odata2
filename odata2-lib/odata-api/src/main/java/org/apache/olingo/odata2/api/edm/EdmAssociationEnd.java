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
package org.apache.olingo.odata2.api.edm;

// TODO: Auto-generated Javadoc
/**
 * The Interface EdmAssociationEnd.
 *
 * @org.apache.olingo.odata2.DoNotImplement A CSDL AssociationEnd element
 * 
 * <p>EdmAssociationEnd defines one side of the relationship of two entity types.
 */
public interface EdmAssociationEnd extends EdmAnnotatable {

  /**
   * Gets the role.
   *
   * @return the role of this {@link EdmAssociationEnd} as a String.
   */
  String getRole();

  /**
   * Gets the entity type.
   *
   * @return {@link EdmEntityType} this association end points to.
   * @throws EdmException the edm exception
   */
  EdmEntityType getEntityType() throws EdmException;

  /**
   * See {@link EdmMultiplicity} for more information about possible multiplicities.
   * @return {@link EdmMultiplicity}
   */
  EdmMultiplicity getMultiplicity();
}
