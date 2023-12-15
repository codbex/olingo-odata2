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

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.core.edm.EdmSimpleTypeFacadeImpl;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent EdmSimpleProperty.
 */
public class EdmSimplePropertyImpl extends EdmPropertyImpl {

  /** The simple type. */
  private EdmSimpleTypeKind simpleType;

  /**
   * Gets the type.
   *
   * @return the type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmType getType() throws EdmException {
    if (edmType == null) {
      edmType = EdmSimpleTypeFacadeImpl.getEdmSimpleType(getSimpleType());
      if (edmType == null) {
        throw new EdmException(EdmException.TYPEPROBLEM);
      }
    }
    return edmType;
  }

  /**
   * Gets the simple type.
   *
   * @return the simple type
   */
  public EdmSimpleTypeKind getSimpleType() {
    return simpleType;
  }

  /**
   * Sets the simple type.
   *
   * @param simpleType the new simple type
   */
  public void setSimpleType(EdmSimpleTypeKind simpleType) {
    this.simpleType = simpleType;
  }

  /**
   * Checks if is simple.
   *
   * @return true, if is simple
   */
  @Override
  public boolean isSimple() {
    return true;
  }
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format(name);
  }
}
