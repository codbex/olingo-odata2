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
package org.apache.olingo.odata2.core.edm.provider;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmReferentialConstraint;
import org.apache.olingo.odata2.api.edm.EdmReferentialConstraintRole;
import org.apache.olingo.odata2.api.edm.provider.ReferentialConstraint;
import org.apache.olingo.odata2.api.edm.provider.ReferentialConstraintRole;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmReferentialConstraintImplProv.
 */
public class EdmReferentialConstraintImplProv implements EdmReferentialConstraint, EdmAnnotatable {
  
  /** The referential constraint. */
  private ReferentialConstraint referentialConstraint;
  
  /** The annotations. */
  private EdmAnnotations annotations;

  /**
   * Instantiates a new edm referential constraint impl prov.
   *
   * @param referentialConstraint the referential constraint
   * @throws EdmException the edm exception
   */
  public EdmReferentialConstraintImplProv(final ReferentialConstraint referentialConstraint) throws EdmException {
    this.referentialConstraint = referentialConstraint;
  }

  /**
   * Gets the principal.
   *
   * @return the principal
   * @throws EdmException the edm exception
   */
  @Override
  public EdmReferentialConstraintRole getPrincipal() throws EdmException {
    ReferentialConstraintRole principal = referentialConstraint.getPrincipal();
    return new EdmReferentialConstraintRoleImplProv(principal);
  }

  /**
   * Gets the dependent.
   *
   * @return the dependent
   * @throws EdmException the edm exception
   */
  @Override
  public EdmReferentialConstraintRole getDependent() throws EdmException {
    ReferentialConstraintRole dependent = referentialConstraint.getDependent();
    return new EdmReferentialConstraintRoleImplProv(dependent);
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAnnotations getAnnotations() throws EdmException {
    if (annotations == null) {
      annotations =
          new EdmAnnotationsImplProv(referentialConstraint.getAnnotationAttributes(), referentialConstraint
              .getAnnotationElements());
    }
    return annotations;
  }

}
