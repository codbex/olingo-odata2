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
import org.apache.olingo.odata2.api.edm.EdmParameter;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.core.edm.EdmSimpleTypeFacadeImpl;

// TODO: Auto-generated Javadoc
/**
 *  Objects of this class represent EdmParameter.
 */
public class EdmParameterImpl extends EdmElementImpl implements EdmParameter, EdmAnnotatable {

  /** The parameter. */
  private EdmFunctionImportParameter parameter;
  
  /** The annotations. */
  private EdmAnnotations annotations;

  
  /**
   * Gets the type.
   *
   * @return the type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmType getType() throws EdmException {
    if (edmType == null) {
      edmType = EdmSimpleTypeFacadeImpl.getEdmSimpleType(parameter.getType());
      if (edmType == null) {
        throw new EdmException(EdmException.TYPEPROBLEM);
      }
    }
    return edmType;
  }

  /**
   * Sets the parameter.
   *
   * @param parameter the parameter to set
   */
  public void setParameter(EdmFunctionImportParameter parameter) {
    this.parameter = parameter;
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
   * Sets the annotations.
   *
   * @param annotations the annotations to set
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
      return String.format(name);
  }
}
