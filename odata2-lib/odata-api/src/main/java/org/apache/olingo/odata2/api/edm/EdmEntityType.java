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

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * <p>A CSDL EntityType element.</p>
 * <p>EdmEntityType holds a set of related information like {@link EdmSimpleType} properties and {@link EdmComplexType}
 * properties and in addition to a {@link EdmComplexType complex type} it provides information about key properties,
 * customizable feed mappings and {@link EdmNavigationProperty navigation properties}.
 * 
 * @org.apache.olingo.odata2.DoNotImplement
 */
public interface EdmEntityType extends EdmStructuralType {

  /**
   * Gets all key property names.
   *
   * @return collection of key property names of type List<String>
   * @throws EdmException the edm exception
   */
  List<String> getKeyPropertyNames() throws EdmException;

  /**
   * Get all key properties as list of {@link EdmProperty}.
   *
   * @return collection of key properties of type List<EdmProperty>
   * @throws EdmException the edm exception
   */
  List<EdmProperty> getKeyProperties() throws EdmException;

  /**
   * Indicates if the entity type is treated as Media Link Entry
   * with associated Media Resource.
   *
   * @return <code>true</code> if the entity type is a Media Link Entry
   * @throws EdmException the edm exception
   */
  boolean hasStream() throws EdmException;

  /**
   * Gets the base type.
   *
   * @return the base type
   * @throws EdmException the edm exception
   */
  @Override
  EdmEntityType getBaseType() throws EdmException;

  /**
   * Gets the Customizable Feed Mappings of the entity type.
   *
   * @return {@link EdmCustomizableFeedMappings}
   * @throws EdmException the edm exception
   */
  EdmCustomizableFeedMappings getCustomizableFeedMappings() throws EdmException;

  /**
   * Gets all navigation property names.
   *
   * @return collection of navigation properties of type List<String>
   * @throws EdmException the edm exception
   */
  List<String> getNavigationPropertyNames() throws EdmException;
}
