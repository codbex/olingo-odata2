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

import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent ComplexType.
 */
public class EdmComplexTypeImpl extends EdmStructuralTypeImpl implements EdmComplexType{

  /** The is abstract. */
  private boolean isAbstract;

  /** The base type. */
  private FullQualifiedName baseType;
  
  /** The mapping. */
  private EdmMapping mapping;
  
  /**
   * Gets the mapping.
   *
   * @return the mapping
   */
  public EdmMapping getMapping() {
    return mapping;
  }
  
  /**
   * Sets the mapping.
   *
   * @param mapping the new mapping
   */
  public void setMapping(EdmMapping mapping) {
    this.mapping = mapping;
  }
  
  /**
   * Sets the abstract.
   *
   * @param isAbstract the new abstract
   */
  public void setAbstract(boolean isAbstract) {
    this.isAbstract = isAbstract;
  }
  
  /**
   * Gets the base type.
   *
   * @return the base type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmComplexType getBaseType() throws EdmException {
    if(edmBaseType!=null){
      return (EdmComplexTypeImpl) edmBaseType;
    } else {
      return null;
    }
  }
  
  /**
   * Gets the edm base type name.
   *
   * @return the edm base type name
   */
  public FullQualifiedName getEdmBaseTypeName() {
    return baseType;
  }
  
  /**
   * Sets the base type name.
   *
   * @param baseType the new base type name
   */
  public void setBaseTypeName(FullQualifiedName baseType) {
    this.baseType = baseType;
  }
  
  /**
   * Checks if is abstract.
   *
   * @return true, if is abstract
   */
  public boolean isAbstract() {
    return isAbstract;
  }
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return name;
  }
  
  /**
   * Gets the kind.
   *
   * @return the kind
   */
  @Override
  public EdmTypeKind getKind() {
    return EdmTypeKind.COMPLEX;
  }
}
