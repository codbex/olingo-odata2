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
package org.apache.olingo.odata2.api.uri.info;

import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.NavigationSegment;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;

// TODO: Auto-generated Javadoc
/**
 * Access to the parts of the request URI that are relevant for PUT, PATCH, or MERGE requests.
 * @org.apache.olingo.odata2.DoNotImplement
 * 
 */
public interface PutMergePatchUriInfo {
  /**
   * Gets the target entity container.
   * @return {@link EdmEntityContainer} the target entity container
   */
  public EdmEntityContainer getEntityContainer();

  /**
   * Gets the start entity set - identical to the target entity set if no navigation
   * has been used.
   * @return {@link EdmEntitySet}
   */
  public EdmEntitySet getStartEntitySet();

  /**
   * Gets the target entity set after navigation.
   * @return {@link EdmEntitySet} target entity set
   */
  public EdmEntitySet getTargetEntitySet();

  /**
   * Gets the function import.
   * @return {@link EdmFunctionImport} the function import
   */
  public EdmFunctionImport getFunctionImport();

  /**
   * Gets the target type of the request: an entity type, a simple type, or a complex type.
   * @return {@link EdmType} the target type
   */
  public EdmType getTargetType();

  /**
   * Gets the key predicates used to select a single entity out of the start entity set,
   * or an empty list if not used.
   * @return List of {@link KeyPredicate}
   * @see #getStartEntitySet()
   */
  public List<KeyPredicate> getKeyPredicates();

  /**
   * Gets the key predicates used to select a single entity out of the target entity set,
   * or an empty list if not used - identical to the key predicates from the last entry
   * retrieved from {@link #getNavigationSegments()} or, if no navigation has been used,
   * to the result of {@link #getKeyPredicates()}.
   * @return List of {@link KeyPredicate}
   * @see #getTargetEntitySet()
   */
  public List<KeyPredicate> getTargetKeyPredicates();

  /**
   * Gets the navigation segments, or an empty list if no navigation has been used.
   * @return List of {@link NavigationSegment}
   */
  public List<NavigationSegment> getNavigationSegments();

  /**
   * Gets the path used to select a (simple or complex) property of an entity,
   * or an empty list if no property is accessed.
   * @return List of {@link EdmProperty}
   */
  public List<EdmProperty> getPropertyPath();

  /**
   * Gets the value of the $filter system query option as root object of the
   * expression tree built during URI parsing.
   * @return the filter expression or null
   */
  public FilterExpression getFilter();

  /**
   * Gets the parameters of a function import as Map from parameter names to
   * their corresponding typed values, or an empty list if no function import
   * is used or no parameters are given in the URI.
   * @return Map of {@literal <String,} {@link EdmLiteral}{@literal >} function import parameters
   */
  public Map<String, EdmLiteral> getFunctionImportParameters();

  /**
   * Gets the custom query options as Map from option names to their
   * corresponding String values, or an empty list if no custom query options
   * are given in the URI.
   * @return Map of {@literal <String, String>} custom query options
   */
  public Map<String, String> getCustomQueryOptions();
}
