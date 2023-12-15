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
package org.apache.olingo.odata2.jpa.processor.api.access;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The interface provides view on JPA Method Context. JPA Method context can be
 * used to access custom operations or JPA Entity property access methods.
 * 
 * 
 * 
 */
public interface JPAMethodContextView {
  /**
   * The method returns an instance of Object on which the methods/custom
   * operations can be executed.
   * 
   * @return instance of enclosing object for the method
   */
  public Object getEnclosingObject();

  /**
   * The method returns list of JPA functions that can be executed on the
   * enclosing object.
   * 
   * @return an instance of list of JPA Function
   */
  public List<JPAFunction> getJPAFunctionList();
}
