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
package org.apache.olingo.odata2.jpa.processor.api.model;

import java.util.List;

import org.apache.olingo.odata2.api.edm.provider.FunctionImport;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * A view on EDM Function Imports. EDM function imports are derived from Java
 * class methods annotated with EDM Annotations.
 * </p>
 * <p>
 * The implementation of the view provides access to EDM Function Import created
 * from Java class methods. The implementation act as a container for list of
 * function imports that are consistent.
 * </p>
 * 
 * 
 * <p>
 * @org.apache.olingo.odata2.DoNotImplement
 * 
 */
public interface JPAEdmFunctionImportView extends JPAEdmBaseView {

  /**
   * The method returns a list of consistent Function Imports. A function
   * import is said to be consistent only if it adheres to the rules defined
   * in CSDL.
   * 
   * @return a list of type {@link org.apache.olingo.odata2.api.edm.provider.FunctionImport}
   */
  List<FunctionImport> getConsistentFunctionImportList();
}
