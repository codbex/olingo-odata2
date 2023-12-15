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

import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmReferentialConstraintRole;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent EdmReferentialConstraintRole.
 */
public class EdmReferentialConstraintRoleImpl implements EdmReferentialConstraintRole, EdmAnnotatable {
  
  /** The ref names. */
  protected List<String> refNames;
  
  /** The role. */
  private EdmReferentialConstraintRole role;
  
  /** The annotations. */
  private EdmAnnotations annotations;
  
  /** The role name. */
  private String roleName;
  
  /** The property. */
  private List<EdmPropertyImpl> property;


  /**
   * Gets the ref names.
   *
   * @return the ref names
   */
  public List<String> getRefNames() {
    return refNames;
  }

  /**
   * Sets the ref names.
   *
   * @param refNames the new ref names
   */
  public void setRefNames(List<String> refNames) {
    this.refNames = refNames;
  }

  /**
   * Gets the role name.
   *
   * @return the role name
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * Gets the role.
   *
   * @return the role
   */
  @Override
  public String getRole() {
    return roleName;
  }

  /**
   * Gets the property.
   *
   * @return the propertyRefs
   */
  public List<EdmPropertyImpl> getProperty() {
    return property;
  }

  /**
   * Gets the property ref names.
   *
   * @return the property ref names
   */
  @Override
  public List<String> getPropertyRefNames() {
    return refNames;
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
   * @param role the role to set
   */
  public void setRole(EdmReferentialConstraintRole role) {
    this.role = role;
  }

  /**
   * Sets the role name.
   *
   * @param roleName the new role name
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
    
  }

  /**
   * Sets the property.
   *
   * @param property the new property
   */
  public void setProperty(List<EdmPropertyImpl> property) {
    this.property = property;
    
  }

  /**
   * Sets the annotations.
   *
   * @param annotations the new annotations
   */
  public void setAnnotations(EdmAnnotations annotations) {
   this.annotations = annotations;
    
  }  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return String.format(role.getRole());
}
}
