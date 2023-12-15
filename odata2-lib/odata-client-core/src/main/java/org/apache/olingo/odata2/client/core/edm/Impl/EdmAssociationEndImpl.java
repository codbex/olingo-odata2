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
package org.apache.olingo.odata2.client.core.edm.Impl;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.client.core.edm.EdmMetadataAssociationEnd;
import org.apache.olingo.odata2.client.core.edm.EdmOnDelete;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent AssociationEnds.
 */
public class EdmAssociationEndImpl implements EdmMetadataAssociationEnd, EdmAnnotatable {

  /** The edm. */
  private EdmImpl edm;
  
  /** The annotations. */
  private EdmAnnotations annotations;
  
  /** The role. */
  private String role;
  
  /** The edm multiplicity. */
  private EdmMultiplicity edmMultiplicity;
  
  /** The association end type name. */
  private FullQualifiedName associationEndTypeName;
  
  /** The on delete. */
  private EdmOnDelete onDelete;

  /**
   * Gets the role.
   *
   * @return the role
   */
  @Override
  public String getRole() {
    return role;
  }

  /**
   * Sets the edm.
   *
   * @param edm the edm to set
   */
  public void setEdm(EdmImpl edm) {
    this.edm = edm;
  }


  /**
   * Gets the entity type.
   *
   * @return the entity type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityType getEntityType() throws EdmException {
    EdmEntityType entityType = edm.getEntityType(
        associationEndTypeName.getNamespace(), 
       associationEndTypeName.getName());
    if (entityType == null) {
      throw new EdmException(EdmException.ENTITYTYPEPROBLEM);
    }
    return entityType;
  }

  /**
   * Gets the multiplicity.
   *
   * @return the multiplicity
   */
  @Override
  public EdmMultiplicity getMultiplicity() {
    return edmMultiplicity;
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAnnotations getAnnotations() throws EdmException {
    return annotations;
  }

  /**
   * Sets the role.
   *
   * @param role the new role
   */
  public void setRole(String role) {
    this.role = role;
  }

  /**
   * Sets the multiplicity.
   *
   * @param edmMultiplicity the new multiplicity
   */
  public void setMultiplicity(EdmMultiplicity edmMultiplicity) {
    this.edmMultiplicity = edmMultiplicity;
  }
  
  /**
   * Sets the type.
   *
   * @param associationEndTypeName the new type
   */
  public void setType(FullQualifiedName associationEndTypeName) {
    this.associationEndTypeName = associationEndTypeName;
  }
  
  /**
   * Sets the annotations.
   *
   * @param annotations the new annotations
   */
  public void setAnnotations(EdmAnnotations annotations) {
    this.annotations = annotations;
  }

  /**
   * Sets the on delete.
   *
   * @param onDelete the new on delete
   */
  public void setOnDelete(EdmOnDelete onDelete) {
    this.onDelete = onDelete;
  }

  /**
   * Gets the on delete.
   *
   * @return the on delete
   */
  @Override
  public EdmOnDelete getOnDelete() {
    return onDelete;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format(annotations.toString());
  }
}
