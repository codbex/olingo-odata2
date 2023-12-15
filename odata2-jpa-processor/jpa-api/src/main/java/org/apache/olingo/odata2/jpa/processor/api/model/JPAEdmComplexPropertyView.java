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

import org.apache.olingo.odata2.api.edm.provider.ComplexProperty;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * A view on properties of Java Persistence embeddable type and EDM complex
 * type. Properties of JPA embeddable types are converted into EDM properties of
 * EDM complex type.
 * </p>
 * <p>
 * The implementation of the view provides access to properties of EDM complex
 * type created for a given JPA EDM complex type. The implementation acts as a
 * container for the properties of EDM complex type.
 * </p>
 *
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmComplexTypeView
 * @org.apache.olingo.odata2.DoNotImplement 
 */
public interface JPAEdmComplexPropertyView extends JPAEdmBaseView {
  /**
   * The method returns a complex property for a complex type.
   * 
   * @return an instance of {@link org.apache.olingo.odata2.api.edm.provider.ComplexProperty}
   */
  ComplexProperty getEdmComplexProperty();
}
