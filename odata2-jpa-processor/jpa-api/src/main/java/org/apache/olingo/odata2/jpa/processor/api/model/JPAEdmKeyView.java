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

import org.apache.olingo.odata2.api.edm.provider.Key;

// TODO: Auto-generated Javadoc
/**
 * A view on Java Persistence Entity Key Attributes and EDM Key properties. Java
 * Persistence Key Attributes of type
 * <ol>
 * <li>embedded ID</li>
 * <li>ID</li>
 * </ol>
 * are converted into EDM keys. Embedded IDs are expanded into simple EDM
 * properties.
 * <p>
 * The implementation of the view provides access to EDM key properties for a
 * given JPA EDM entity type. The view acts as a container for consistent EDM
 * key property of an EDM entity type.
 * 
 * 
 * <p>
 *
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmPropertyView
 * @org.apache.olingo.odata2.DoNotImplement 
 */
public interface JPAEdmKeyView extends JPAEdmBaseView {
  /**
   * The method returns an instance of EDM key for the given JPA EDM Entity
   * type.
   * 
   * @return an instance of type {@link org.apache.olingo.odata2.api.edm.provider.Key}
   */
  public Key getEdmKey();
}
