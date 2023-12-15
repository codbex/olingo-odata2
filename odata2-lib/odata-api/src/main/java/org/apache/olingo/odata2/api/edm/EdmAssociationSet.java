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
 * The Interface EdmAssociationSet.
 *
 * @org.apache.olingo.odata2.DoNotImplement A CSDL AssociationSet element
 * 
 * <p>EdmAssociationSet defines the relationship of two entity sets.
 */
public interface EdmAssociationSet extends EdmNamed, EdmAnnotatable {

  /**
   * Get the association.
   *
   * @return {@link EdmAssociation}
   * @throws EdmException the edm exception
   */
  EdmAssociation getAssociation() throws EdmException;

  /**
   * Get the association set end.
   *
   * @param role the role
   * @return {@link EdmAssociationSetEnd}
   * @throws EdmException the edm exception
   */
  EdmAssociationSetEnd getEnd(String role) throws EdmException;

  /**
   * Get the entity container the association set is located in.
   *
   * @return {@link EdmEntityContainer}
   * @throws EdmException the edm exception
   */
  EdmEntityContainer getEntityContainer() throws EdmException;
}
