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

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;

// TODO: Auto-generated Javadoc
/**
 * Implementation of the EDM simple type Binary.
 * 
 */
public class EdmBinary extends AbstractSimpleType {

  /** The Constant instance. */
  private static final EdmBinary instance = new EdmBinary();

  /**
   * Gets the single instance of EdmBinary.
   *
   * @return single instance of EdmBinary
   */
  public static EdmBinary getInstance() {
    return instance;
  }

  /**
   * Gets the default type.
   *
   * @return the default type
   */
  @Override
  public Class<?> getDefaultType() {
    return byte[].class;
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
    if (value == null) {
      return facets == null || facets.isNullable() == null || facets.isNullable();
    }

    if (literalKind == null) {
      return false;
    }

    return validateLiteral(value, literalKind) && validateMaxLength(value, literalKind, facets);
  }

  /**
   * Validate literal.
   *
   * @param value the value
   * @param literalKind the literal kind
   * @return true, if successful
   */
  private static boolean validateLiteral(final String value, final EdmLiteralKind literalKind) {
    return literalKind == EdmLiteralKind.URI ?
        value.matches("(?:X|binary)'(?:\\p{XDigit}{2})*'") : Base64.isBase64(value);
  }

  /**
   * Validate max length.
   *
   * @param value the value
   * @param literalKind the literal kind
   * @param facets the facets
   * @return true, if successful
   */
  private static boolean
      validateMaxLength(final String value, final EdmLiteralKind literalKind, final EdmFacets facets) {
    return facets == null || facets.getMaxLength() == null ? true :
        literalKind == EdmLiteralKind.URI ?
            // In URI representation, each byte is represented as two hexadecimal digits;
            // additionally, we have to account for the prefix and the surrounding "'"s.
            facets.getMaxLength() >= (value.length() - (value.startsWith("X") ? 3 : 8)) / 2
            :
            // In default representation, every three bytes are represented as four base-64 characters.
            // Additionally, there could be up to two padding "=" characters if the number of bytes is
            // not a multiple of three, and there could be line feeds, possibly with carriage returns.
            facets.getMaxLength() * 4L >= (value.length() - crlfLength(value)) * 3L
                - (value.contains("==") ? 2 : value.contains("=") ? 1 : 0) * 4L;
  }

  /**
   * Crlf length.
   *
   * @param value the value
   * @return the int
   */
  private static int crlfLength(final String value) {
    int result = 0;
    int index = 0;
    while ((index = value.indexOf('\n', index)) >= 0) {
      result += index > 0 && value.charAt(index - 1) == '\r' ? 2 : 1;
      index++;
    }
    return result;
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
    if (!validateLiteral(value, literalKind)) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value));
    }
    if (!validateMaxLength(value, literalKind, facets)) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_FACETS_NOT_MATCHED.addContent(value, facets));
    }

    byte[] result;
    if (literalKind == EdmLiteralKind.URI) {
      try {
        result = Hex.decodeHex(value.substring(value.startsWith("X") ? 2 : 7, value.length() - 1).toCharArray());
      } catch (final DecoderException e) {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value), e);
      }
    } else {
      result = Base64.decodeBase64(value);
    }

    if (returnType.isAssignableFrom(byte[].class)) {
      return returnType.cast(result);
    } else if (returnType.isAssignableFrom(Byte[].class)) {
      Byte[] byteArray = new Byte[result.length];
      for (int i = 0; i < result.length; i++) {
        byteArray[i] = result[i];
      }
      return returnType.cast(byteArray);
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
    byte[] byteArrayValue;
    if (value instanceof byte[]) {
      byteArrayValue = (byte[]) value;
    } else if (value instanceof Byte[]) {
      final int length = ((Byte[]) value).length;
      byteArrayValue = new byte[length];
      for (int i = 0; i < length; i++) {
        byteArrayValue[i] = ((Byte[]) value)[i].byteValue();
      }
    } else {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(value.getClass()));
    }

    if (facets != null && facets.getMaxLength() != null && byteArrayValue.length > facets.getMaxLength()) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_FACETS_NOT_MATCHED.addContent(value, facets));
    }

    return Base64.encodeBase64String(byteArrayValue);
  }

  /**
   * To uri literal.
   *
   * @param literal the literal
   * @return the string
   * @throws EdmSimpleTypeException the edm simple type exception
   */
  @Override
  public String toUriLiteral(final String literal) throws EdmSimpleTypeException {
    return "binary'" + String.valueOf(Hex.encodeHex(Base64.decodeBase64(literal), false)) + "'";
  }
}
