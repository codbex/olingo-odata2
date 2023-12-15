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

// TODO: Auto-generated Javadoc
/**
 * The Interface EdmEntitySet.
 *
 * @org.apache.olingo.odata2.DoNotImplement A CSDL EntitySet element
 * <p>EdmEntitySet is the container for entity type instances as described in the OData protocol.
 */
public interface EdmEntitySet extends EdmMappable, EdmNamed, EdmAnnotatable {

  /**
   * Get the entity type.
   *
   * @return {@link EdmEntityType}
   * @throws EdmException the edm exception
   */
  EdmEntityType getEntityType() throws EdmException;

  /**
   * Get the related entity set by providing the navigation property.
   *
   * @param navigationProperty of type {@link EdmNavigationProperty}
   * @return {@link EdmEntitySet}
   * @throws EdmException the edm exception
   */
  EdmEntitySet getRelatedEntitySet(EdmNavigationProperty navigationProperty) throws EdmException;

  /**
   * Get the entity container the entity set is contained in.
   *
   * @return {@link EdmEntityContainer}
   * @throws EdmException the edm exception
   */
  EdmEntityContainer getEntityContainer() throws EdmException;
}
