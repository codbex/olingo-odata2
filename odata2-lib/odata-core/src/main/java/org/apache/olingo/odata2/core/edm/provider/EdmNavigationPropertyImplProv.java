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
import org.apache.olingo.odata2.api.edm.EdmAssociation;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmNavigationPropertyImplProv.
 */
public class EdmNavigationPropertyImplProv extends EdmTypedImplProv implements EdmNavigationProperty, EdmAnnotatable {

  /** The navigation property. */
  private NavigationProperty navigationProperty;
  
  /** The annotations. */
  private EdmAnnotations annotations;

  /**
   * Instantiates a new edm navigation property impl prov.
   *
   * @param edm the edm
   * @param property the property
   * @throws EdmException the edm exception
   */
  public EdmNavigationPropertyImplProv(final EdmImplProv edm, final NavigationProperty property) throws EdmException {
    super(edm, property.getName(), null, null);
    navigationProperty = property;
  }

  /**
   * Gets the type.
   *
   * @return the type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmType getType() throws EdmException {
    return getRelationship().getEnd(navigationProperty.getToRole()).getEntityType();
  }

  /**
   * Gets the multiplicity.
   *
   * @return the multiplicity
   * @throws EdmException the edm exception
   */
  @Override
  public EdmMultiplicity getMultiplicity() throws EdmException {
    return ((EdmAssociationImplProv) getRelationship()).getEndMultiplicity(navigationProperty.getToRole());
  }

  /**
   * Gets the relationship.
   *
   * @return the relationship
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAssociation getRelationship() throws EdmException {
    final FullQualifiedName relationship = navigationProperty.getRelationship();
    return edm.getAssociation(relationship.getNamespace(), relationship.getName());
  }

  /**
   * Gets the from role.
   *
   * @return the from role
   * @throws EdmException the edm exception
   */
  @Override
  public String getFromRole() throws EdmException {
    return navigationProperty.getFromRole();
  }

  /**
   * Gets the to role.
   *
   * @return the to role
   * @throws EdmException the edm exception
   */
  @Override
  public String getToRole() throws EdmException {
    return navigationProperty.getToRole();
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
      annotations = new EdmAnnotationsImplProv(navigationProperty.getAnnotationAttributes(),
          navigationProperty.getAnnotationElements());
    }
    return annotations;
  }

  /**
   * Gets the mapping.
   *
   * @return the mapping
   * @throws EdmException the edm exception
   */
  @Override
  public EdmMapping getMapping() throws EdmException {
    return navigationProperty.getMapping();
  }

}
