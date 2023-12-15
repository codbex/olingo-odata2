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

import java.math.BigInteger;

import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;

// TODO: Auto-generated Javadoc
/**
 * Implementation of the EDM simple type Int64.
 * 
 */
public class EdmInt64 extends AbstractSimpleType {

  /** The Constant instance. */
  private static final EdmInt64 instance = new EdmInt64();

  /**
   * Gets the single instance of EdmInt64.
   *
   * @return single instance of EdmInt64
   */
  public static EdmInt64 getInstance() {
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
    return simpleType instanceof Bit
        || simpleType instanceof Uint7
        || simpleType instanceof EdmByte
        || simpleType instanceof EdmSByte
        || simpleType instanceof EdmInt16
        || simpleType instanceof EdmInt32
        || simpleType instanceof EdmInt64;
  }

  /**
   * Gets the default type.
   *
   * @return the default type
   */
  @Override
  public Class<?> getDefaultType() {
    return Long.class;
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
    Long valueLong;
    try {
      if (literalKind == EdmLiteralKind.URI) {
        if (value.endsWith("L") || value.endsWith("l")) {
          valueLong = Long.parseLong(value.substring(0, value.length() - 1));
        } else {
          throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value));
        }
      } else {
        valueLong = Long.parseLong(value);
      }
    } catch (final NumberFormatException e) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value), e);
    }

    if (returnType.isAssignableFrom(Long.class)) {
      return returnType.cast(valueLong);
    } else if (returnType.isAssignableFrom(Byte.class)) {
      if (valueLong >= Byte.MIN_VALUE && valueLong <= Byte.MAX_VALUE) {
        return returnType.cast(valueLong.byteValue());
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_UNCONVERTIBLE_TO_VALUE_TYPE.addContent(value,
            returnType));
      }
    } else if (returnType.isAssignableFrom(Short.class)) {
      if (valueLong >= Short.MIN_VALUE && valueLong <= Short.MAX_VALUE) {
        return returnType.cast(valueLong.shortValue());
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_UNCONVERTIBLE_TO_VALUE_TYPE.addContent(value,
            returnType));
      }
    } else if (returnType.isAssignableFrom(Integer.class)) {
      if (valueLong >= Integer.MIN_VALUE && valueLong <= Integer.MAX_VALUE) {
        return returnType.cast(valueLong.intValue());
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_UNCONVERTIBLE_TO_VALUE_TYPE.addContent(value,
            returnType));
      }
    } else if (returnType.isAssignableFrom(BigInteger.class)) {
      return returnType.cast(BigInteger.valueOf(valueLong));
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
    if (value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long) {
      return value.toString();
    } else if (value instanceof BigInteger) {
      if (((BigInteger) value).bitLength() < Long.SIZE) {
        return value.toString();
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_ILLEGAL_CONTENT.addContent(value));
      }
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
    return literal + "L";
  }
}
