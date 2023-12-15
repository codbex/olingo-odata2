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
package org.apache.olingo.odata2.core.edm;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;

// TODO: Auto-generated Javadoc
/**
 * Abstract implementation of the EDM simple-type interface.
 * 
 */
public abstract class AbstractSimpleType implements EdmSimpleType {

  /**
   * Equals.
   *
   * @param obj the obj
   * @return true, if successful
   */
  @Override
  public boolean equals(final Object obj) {
    return this == obj || (obj != null && getClass() == obj.getClass());
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  /**
   * Gets the namespace.
   *
   * @return the namespace
   * @throws EdmException the edm exception
   */
  @Override
  public String getNamespace() throws EdmException {
    return EDM_NAMESPACE;
  }

  /**
   * Gets the kind.
   *
   * @return the kind
   */
  @Override
  public EdmTypeKind getKind() {
    return EdmTypeKind.SIMPLE;
  }

  /**
   * Gets the name.
   *
   * @return the name
   * @throws EdmException the edm exception
   */
  @Override
  public String getName() throws EdmException {
    final String name = getClass().getSimpleName();
    return name.startsWith(EDM_NAMESPACE) ? name.substring(3) : name;
  }

  /**
   * Checks if is compatible.
   *
   * @param simpleType the simple type
   * @return true, if is compatible
   */
  @Override
  public boolean isCompatible(final EdmSimpleType simpleType) {
    return equals(simpleType);
  }

  /**
   * Validate.
   *
   * @param value the value
   * @param literalKind the literal kind
   * @param facets the facets
   * @return true, if successful
   */
  @Override
  public boolean validate(final String value, final EdmLiteralKind literalKind, final EdmFacets facets) {
    try {
      valueOfString(value, literalKind, facets, getDefaultType());
      return true;
    } catch (final EdmSimpleTypeException e) {
      return false;
    }
  }

  /**
   * Value of string.
   *
   * @param <T> the generic type
   * @param value the value
   * @param literalKind the literal kind
   * @param facets the facets
   * @param returnType the return type
   * @return the t
   * @throws EdmSimpleTypeException the edm simple type exception
   */
  @Override
  public final <T> T valueOfString(final String value, final EdmLiteralKind literalKind, final EdmFacets facets,
      final Class<T> returnType) throws EdmSimpleTypeException {
    if (value == null) {
      if (facets == null || facets.isNullable() == null || facets.isNullable()) {
        return null;
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_NULL_NOT_ALLOWED);
      }
    }

    if (literalKind == null) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_KIND_MISSING);
    }

    return internalValueOfString(value, literalKind, facets, returnType);
  }

  /**
   * Internal value of string.
   *
   * @param <T> the generic type
   * @param value the value
   * @param literalKind the literal kind
   * @param facets the facets
   * @param returnType the return type
   * @return the t
   * @throws EdmSimpleTypeException the edm simple type exception
   */
  protected abstract <T> T internalValueOfString(String value, EdmLiteralKind literalKind, EdmFacets facets,
      Class<T> returnType) throws EdmSimpleTypeException;

  /**
   * Value to string.
   *
   * @param value the value
   * @param literalKind the literal kind
   * @param facets the facets
   * @return the string
   * @throws EdmSimpleTypeException the edm simple type exception
   */
  @Override
  public final String valueToString(final Object value, final EdmLiteralKind literalKind, final EdmFacets facets)
      throws EdmSimpleTypeException {
    if (value == null) {
      if (facets == null || facets.isNullable() == null || facets.isNullable()) {
        return null;
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_NULL_NOT_ALLOWED);
      }
    }

    if (literalKind == null) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_KIND_MISSING);
    }

    final String result = internalValueToString(value, literalKind, facets);
    return literalKind == EdmLiteralKind.URI ? toUriLiteral(result) : result;
  }

  /**
   * Internal value to string.
   *
   * @param <T> the generic type
   * @param value the value
   * @param literalKind the literal kind
   * @param facets the facets
   * @return the string
   * @throws EdmSimpleTypeException the edm simple type exception
   */
  protected abstract <T> String internalValueToString(T value, EdmLiteralKind literalKind, EdmFacets facets)
      throws EdmSimpleTypeException;

  /**
   * To uri literal.
   *
   * @param literal the literal
   * @return the string
   * @throws EdmSimpleTypeException the edm simple type exception
   */
  @Override
  public String toUriLiteral(final String literal) throws EdmSimpleTypeException {
    return literal;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    try {
      return getNamespace() + Edm.DELIMITER + getName();
    } catch (final EdmException e) {
      return super.toString();
    }
  }
}
