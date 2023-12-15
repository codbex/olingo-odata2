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
package org.apache.olingo.odata2.api.edm;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Interface EdmFunctionImport.
 *
 * @org.apache.olingo.odata2.DoNotImplement A CSDL FunctionImport element
 * 
 * EdmFunctionImport can be used model functions which have input parameters, an associated HTTP Method
 * and a return type which can be of different kinds:
 * 
 * <li>{@link EdmSimpleType} or a collection of simple types
 * <li>{@link EdmEntityType} or a collection of entity types
 * <li>{@link EdmEntitySet}
 */
public interface EdmFunctionImport extends EdmMappable, EdmNamed, EdmAnnotatable {

  /**
   * Get the parameter by name.
   *
   * @param name the name
   * @return {@link EdmParameter}
   * @throws EdmException the edm exception
   */
  EdmParameter getParameter(String name) throws EdmException;

  /**
   * Get all parameter names.
   *
   * @return collection of parameter names of type Collection<String>
   * @throws EdmException the edm exception
   */
  Collection<String> getParameterNames() throws EdmException;

  /**
   * Get the edm entity set.
   *
   * @return {@link EdmEntitySet}
   * @throws EdmException the edm exception
   */
  EdmEntitySet getEntitySet() throws EdmException;

  /**
   * Get the HTTP Method.
   *
   * @return HTTP Method as String
   * @throws EdmException the edm exception
   */
  String getHttpMethod() throws EdmException;

  /**
   * Gets the return type.
   *
   * @return {@link EdmTyped}
   * @throws EdmException the edm exception
   */
  EdmTyped getReturnType() throws EdmException;

  /**
   * Get the entity container the function import is contained in.
   *
   * @return {@link EdmEntityContainer}
   * @throws EdmException the edm exception
   */
  EdmEntityContainer getEntityContainer() throws EdmException;
}
