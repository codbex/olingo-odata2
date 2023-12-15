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
import org.apache.olingo.odata2.api.edm.EdmAssociationEnd;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmAssociationEndImplProv.
 */
public class EdmAssociationEndImplProv implements EdmAssociationEnd, EdmAnnotatable {

  /** The edm. */
  private EdmImplProv edm;
  
  /** The association end. */
  private AssociationEnd associationEnd;
  
  /** The annotations. */
  private EdmAnnotations annotations;

  /**
   * Instantiates a new edm association end impl prov.
   *
   * @param edm the edm
   * @param associationEnd the association end
   * @throws EdmException the edm exception
   */
  public EdmAssociationEndImplProv(final EdmImplProv edm, final AssociationEnd associationEnd) throws EdmException {
    this.edm = edm;
    this.associationEnd = associationEnd;
  }

  /**
   * Gets the role.
   *
   * @return the role
   */
  @Override
  public String getRole() {
    return associationEnd.getRole();
  }

  /**
   * Gets the entity type.
   *
   * @return the entity type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityType getEntityType() throws EdmException {
    final FullQualifiedName type = associationEnd.getType();
    EdmEntityType entityType = edm.getEntityType(type.getNamespace(), type.getName());
    if (entityType == null) {
      throw new EdmException(EdmException.COMMON);
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
    return associationEnd.getMultiplicity();
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
          new EdmAnnotationsImplProv(associationEnd.getAnnotationAttributes(), associationEnd.getAnnotationElements());
    }
    return annotations;
  }
}
