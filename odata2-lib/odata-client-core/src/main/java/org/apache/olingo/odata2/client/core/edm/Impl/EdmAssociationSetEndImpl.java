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
import org.apache.olingo.odata2.api.edm.EdmAssociationSetEnd;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent AssociationSetEnd.
 */
public class EdmAssociationSetEndImpl implements EdmAssociationSetEnd, EdmAnnotatable {

  /** The entity set. */
  private EdmEntitySet entitySet;
  
  /** The role. */
  private String role;
  
  /** The end. */
  private EdmAssociationSetEnd end;
  
  /** The annotations. */
  private EdmAnnotations annotations;
  
  /** The entity set name. */
  private String entitySetName;

  /**
   * Gets the entity set.
   *
   * @return the entity set
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntitySet getEntitySet() throws EdmException {
    return entitySet;
  }

  /**
   * Gets the end.
   *
   * @return the end
   */
  public EdmAssociationSetEnd getEnd() {
    return end;
  }

  /**
   * Sets the end.
   *
   * @param end the new end
   */
  public void setEnd(EdmAssociationSetEnd end) {
    this.end = end;
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
   * Sets the annotations.
   *
   * @param annotations the new annotations
   */
  public void setAnnotations(EdmAnnotations annotations) {
    this.annotations = annotations;
  }

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
   * Sets the entity set name.
   *
   * @param entitySetName the new entity set name
   */
  public void setEntitySetName(String entitySetName) {
    this.entitySetName = entitySetName;
  }

  /**
   * Gets the entity set name.
   *
   * @return the entitySetName
   */
  public String getEntitySetName() {
    return entitySetName;
  }

  /**
   * Sets the entity set.
   *
   * @param entitySet the entitySet to set
   */
  public void setEntitySet(EdmEntitySet entitySet) {
    this.entitySet = entitySet;
  }
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format(end.toString());
  }
}
