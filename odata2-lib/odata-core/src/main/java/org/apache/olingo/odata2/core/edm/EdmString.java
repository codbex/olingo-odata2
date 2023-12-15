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

import java.util.regex.Pattern;

import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;

// TODO: Auto-generated Javadoc
/**
 * Implementation of the EDM simple type String.
 * 
 */
public class EdmString extends AbstractSimpleType {

  /** The Constant PATTERN_ASCII. */
  private static final Pattern PATTERN_ASCII = Pattern.compile("\\p{ASCII}*");
  
  /** The Constant instance. */
  private static final EdmString instance = new EdmString();

  /**
   * Gets the single instance of EdmString.
   *
   * @return single instance of EdmString
   */
  public static EdmString getInstance() {
    return instance;
  }

  /**
   * Gets the default type.
   *
   * @return the default type
   */
  @Override
  public Class<?> getDefaultType() {
    return String.class;
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
    String result;
    if (literalKind == EdmLiteralKind.URI) {
      if (value.length() >= 2 && value.startsWith("'") && value.endsWith("'")) {
        result = (value.substring(1, value.length() - 1)).replace("''", "'");
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value));
      }
    } else {
      result = value;
    }

    if (facets != null
        && (facets.isUnicode() != null && !facets.isUnicode() && !PATTERN_ASCII.matcher(result).matches()
        || facets.getMaxLength() != null && facets.getMaxLength()!=0 && facets.getMaxLength() < result.length())) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_FACETS_NOT_MATCHED.addContent(value, facets));
    }

    if (returnType.isAssignableFrom(String.class)) {
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
    final String result = value instanceof String ? (String) value : String.valueOf(value);

    if (facets != null
        && (facets.isUnicode() != null && !facets.isUnicode() && !PATTERN_ASCII.matcher(result).matches()
        || facets.getMaxLength() != null && facets.getMaxLength() < result.length())) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_FACETS_NOT_MATCHED.addContent(value, facets));
    }

    return result;
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
    final int length = literal.length();

    StringBuilder uriLiteral = new StringBuilder(length + 2);
    uriLiteral.append('\'');
    for (int i = 0; i < length; i++) {
      final char c = literal.charAt(i);
      if (c == '\'') {
        uriLiteral.append(c);
      }
      uriLiteral.append(c);
    }
    uriLiteral.append('\'');
    return uriLiteral.toString();
  }
}
