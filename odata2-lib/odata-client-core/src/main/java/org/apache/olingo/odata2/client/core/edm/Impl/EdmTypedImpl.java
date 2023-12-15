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

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.core.edm.EdmSimpleTypeFacadeImpl;

// TODO: Auto-generated Javadoc
/**
 *  Objects of this class represent type of the entity.
 */
public class EdmTypedImpl extends EdmNamedImpl implements EdmTyped {

  /** The edm type. */
  protected EdmType edmType;
  
  /** The type name. */
  protected FullQualifiedName typeName;
  
  /** The multiplicity. */
  protected EdmMultiplicity multiplicity;

  /**
   * Sets the edm type.
   *
   * @param edmType the new edm type
   */
  public void setEdmType(EdmType edmType) {
    this.edmType = edmType;
  }

  /**
   * Sets the type name.
   *
   * @param typeName the type name
   * @return the edm typed impl
   */
  public EdmTypedImpl setTypeName(FullQualifiedName typeName) {
    this.typeName = typeName;
    return this;
  }

  /**
   * Sets the multiplicity.
   *
   * @param multiplicity the multiplicity
   * @return the edm typed impl
   */
  public EdmTypedImpl setMultiplicity(EdmMultiplicity multiplicity) {
    this.multiplicity = multiplicity;
    return this;
  }

  /**
   * Gets the type.
   *
   * @return the type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmType getType() throws EdmException {
    if (edmType == null) {
      final String namespace = typeName.getNamespace();
      if (EdmSimpleType.EDM_NAMESPACE.equals(typeName.getNamespace())) {
        edmType = EdmSimpleTypeFacadeImpl.getEdmSimpleType(EdmSimpleTypeKind.valueOf(typeName.getName()));
      } else {
        edmType = edm.getComplexType(namespace, typeName.getName());
      }
      if (edmType == null) {
        edmType = edm.getEntityType(namespace, typeName.getName());
      }

      if (edmType == null) {
        throw new EdmException(EdmException.TYPEPROBLEM);
      }

    }
    return edmType;
  }

  /**
   * Gets the multiplicity.
   *
   * @return the multiplicity
   * @throws EdmException the edm exception
   */
  @Override
  public EdmMultiplicity getMultiplicity() throws EdmException {
    return multiplicity;
  }
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return typeName.getNamespace() + Edm.DELIMITER + typeName.getName();
  }
}
