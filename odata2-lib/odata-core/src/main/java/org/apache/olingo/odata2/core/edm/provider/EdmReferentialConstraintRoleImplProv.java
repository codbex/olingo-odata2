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

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmReferentialConstraintRole;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.ReferentialConstraintRole;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmReferentialConstraintRoleImplProv.
 */
public class EdmReferentialConstraintRoleImplProv implements EdmReferentialConstraintRole, EdmAnnotatable {
  
  /** The role. */
  private ReferentialConstraintRole role;
  
  /** The ref names. */
  private List<String> refNames;
  
  /** The annotations. */
  private EdmAnnotations annotations;

  /**
   * Instantiates a new edm referential constraint role impl prov.
   *
   * @param role the role
   * @throws EdmException the edm exception
   */
  public EdmReferentialConstraintRoleImplProv(final ReferentialConstraintRole role) throws EdmException {
    this.role = role;
  }

  /**
   * Gets the role.
   *
   * @return the role
   */
  @Override
  public String getRole() {
    return role.getRole();
  }

  /**
   * Gets the property ref names.
   *
   * @return the property ref names
   */
  @Override
  public List<String> getPropertyRefNames() {
    if (refNames == null) {
      refNames = new ArrayList<String>();
      for (PropertyRef ref : role.getPropertyRefs()) {
        refNames.add(ref.getName());
      }
    }
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
    if (annotations == null) {
      annotations = new EdmAnnotationsImplProv(role.getAnnotationAttributes(), role.getAnnotationElements());
    }
    return annotations;
  }
}
