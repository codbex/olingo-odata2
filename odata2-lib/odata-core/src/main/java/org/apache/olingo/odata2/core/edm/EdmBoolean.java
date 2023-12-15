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

import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;

// TODO: Auto-generated Javadoc
/**
 * Implementation of the EDM simple type Boolean.
 * 
 */
public class EdmBoolean extends AbstractSimpleType {

  /** The Constant instance. */
  private static final EdmBoolean instance = new EdmBoolean();

  /**
   * Gets the single instance of EdmBoolean.
   *
   * @return single instance of EdmBoolean
   */
  public static EdmBoolean getInstance() {
    return instance;
  }

  /**
   * Checks if is compatible.
   *
   * @param simpleType the simple type
   * @return true, if is compatible
   */
  @Override
  public boolean isCompatible(final EdmSimpleType simpleType) {
    return simpleType instanceof Bit || simpleType instanceof EdmBoolean;
  }

  /**
   * Gets the default type.
   *
   * @return the default type
   */
  @Override
  public Class<?> getDefaultType() {
    return Boolean.class;
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
    return value == null ?
        facets == null || facets.isNullable() == null || facets.isNullable() :
        validateLiteral(value);
  }

  /**
   * Validate literal.
   *
   * @param value the value
   * @return true, if successful
   */
  private static boolean validateLiteral(final String value) {
    return "true".equals(value) || "1".equals(value)
        || "false".equals(value) || "0".equals(value);
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
  @Override
  protected <T> T internalValueOfString(final String value, final EdmLiteralKind literalKind, final EdmFacets facets,
      final Class<T> returnType) throws EdmSimpleTypeException {
    if (validateLiteral(value)) {
      if (returnType.isAssignableFrom(Boolean.class)) {
        return returnType.cast(Boolean.valueOf("true".equals(value) || "1".equals(value)));
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(returnType));
      }
    } else {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value));
    }
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
  @Override
  protected <T> String internalValueToString(final T value, final EdmLiteralKind literalKind, final EdmFacets facets)
      throws EdmSimpleTypeException {
    if (value instanceof Boolean) {
      return Boolean.toString((Boolean) value);
    } else {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(value.getClass()));
    }
  }
}
