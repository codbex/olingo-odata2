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

import java.util.UUID;

import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;

// TODO: Auto-generated Javadoc
/**
 * Implementation of the EDM simple type Guid.
 * 
 */
public class EdmGuid extends AbstractSimpleType {

  /** The Constant PATTERN. */
  private static final String PATTERN = "\\p{XDigit}{8}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{4}-\\p{XDigit}{12}";
  
  /** The Constant instance. */
  private static final EdmGuid instance = new EdmGuid();

  /**
   * Gets the single instance of EdmGuid.
   *
   * @return single instance of EdmGuid
   */
  public static EdmGuid getInstance() {
    return instance;
  }

  /**
   * Gets the default type.
   *
   * @return the default type
   */
  @Override
  public Class<?> getDefaultType() {
    return UUID.class;
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
        validateLiteralInternal(value, literalKind);
  }

  /**
   * Validate literal internal.
   *
   * @param value the value
   * @param literalKind the literal kind
   * @return true, if successful
   */
  private boolean validateLiteralInternal(String value, EdmLiteralKind literalKind) {
    String cleanValue = null;
    if (literalKind == EdmLiteralKind.URI && value.toLowerCase().startsWith("guid'") && value.endsWith("'")) {
      cleanValue = value.substring(5, value.length() - 1);
    } else {
      cleanValue = value;
    }
    return validateLiteral(cleanValue);
  }

  /**
   * Validate literal.
   *
   * @param value the value
   * @return true, if successful
   */
  private boolean validateLiteral(final String value) {
    return value.matches(PATTERN);
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
    UUID result;
    String cleanValue = null;
    if (literalKind == EdmLiteralKind.URI && value.toLowerCase().startsWith("guid'") && value.endsWith("'")) {
      cleanValue = value.substring(5, value.length() - 1);
    } else {
      cleanValue = value;
    }
    if (validateLiteral(cleanValue)) {
      result = UUID.fromString(cleanValue);
    } else {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value));
    }

    if (returnType.isAssignableFrom(UUID.class)) {
      return returnType.cast(result);
    } else {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(returnType));
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
    if (value instanceof UUID) {
      return ((UUID) value).toString();
    } else {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(value.getClass()));
    }
  }

  /**
   * To uri literal.
   *
   * @param literal the literal
   * @return the string
   */
  @Override
  public String toUriLiteral(final String literal) {
    return "guid'" + literal + "'";
  }
}
