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
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmReferentialConstraint;
import org.apache.olingo.odata2.api.edm.EdmReferentialConstraintRole;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent EdmReferentialConstraint.
 */
public class EdmReferentialConstraintImpl implements EdmReferentialConstraint, EdmAnnotatable {
  
  /** The annotations. */
  private EdmAnnotations annotations;
  
  /** The referential constraint dependent role. */
  private EdmReferentialConstraintRole referentialConstraintDependentRole;
  
  /** The referential constraint principal role. */
  private EdmReferentialConstraintRole referentialConstraintPrincipalRole;


  /**
   * Gets the principal.
   *
   * @return the principal
   * @throws EdmException the edm exception
   */
  @Override
  public EdmReferentialConstraintRole getPrincipal() throws EdmException {
    return referentialConstraintPrincipalRole;
  }

  /**
   * Gets the dependent.
   *
   * @return the dependent
   * @throws EdmException the edm exception
   */
  @Override
  public EdmReferentialConstraintRole getDependent() throws EdmException {
    return referentialConstraintDependentRole;
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
   * Sets the principal.
   *
   * @param referentialConstraintPrincipalRole the new principal
   */
  public void setPrincipal(EdmReferentialConstraintRole referentialConstraintPrincipalRole) {
    this.referentialConstraintPrincipalRole = referentialConstraintPrincipalRole;
    
  }

  /**
   * Sets the dependent.
   *
   * @param referentialConstraintDependentRole the new dependent
   */
  public void setDependent(EdmReferentialConstraintRole referentialConstraintDependentRole) {
    this.referentialConstraintDependentRole = referentialConstraintDependentRole;
    
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
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format("Dependent Role: " + referentialConstraintDependentRole.getRole() + 
          "PrincipalRole: "+ referentialConstraintPrincipalRole);
  }
}
