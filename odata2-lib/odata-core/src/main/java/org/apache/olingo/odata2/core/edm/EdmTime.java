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

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;

// TODO: Auto-generated Javadoc
/**
 * <p>Implementation of the EDM simple type Time.</p>
 * <p>Arguably, this type is intended to represent a time of day, not an instance in time.
 * The time value is interpreted and formatted as local time.</p>
 * <p>Formatting simply ignores the year, month, and day parts of time instances.
 * Parsing returns a Calendar object where all unused fields have been cleared.</p>
 * 
 */
public class EdmTime extends AbstractSimpleType {
  
  /** The Constant PATTERN. */
  private static final Pattern PATTERN = Pattern.compile(
      "P(?:(\\p{Digit}{1,2})Y)?(?:(\\p{Digit}{1,2})M)?(?:(\\p{Digit}{1,2})D)?"
      + "T(?:(\\p{Digit}{1,2})H)?(?:(\\p{Digit}{1,4})M)?(?:(\\p{Digit}{1,5})(?:\\.(\\p{Digit}+?)0*)?S)?");
  
  /** The Constant instance. */
  private static final EdmTime instance = new EdmTime();
  
  /** The Constant TIME_ZONE_GMT. */
  private static final TimeZone TIME_ZONE_GMT = TimeZone.getTimeZone("GMT");

  /**
   * Gets the single instance of EdmTime.
   *
   * @return single instance of EdmTime
   */
  public static EdmTime getInstance() {
    return instance;
  }

  /**
   * Gets the default type.
   *
   * @return the default type
   */
  @Override
  public Class<?> getDefaultType() {
    return Calendar.class;
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

    //OLINGO-883 prefix is case insensitve so we need to check with lower case if we want to use startsWith()
    if (literalKind == EdmLiteralKind.URI
        && (value.length() <= 6 || !value.toLowerCase().startsWith("time'") || !value.endsWith("'"))) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value));
    }

    final Matcher matcher = PATTERN.matcher(
        literalKind == EdmLiteralKind.URI ? value.substring(5, value.length() - 1) : value);
    if (!matcher.matches()
        || (matcher.group(1) == null && matcher.group(2) == null && matcher.group(3) == null 
        && matcher.group(4) == null && matcher.group(5) == null && matcher.group(6) == null)) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value));
    }

    Calendar dateTimeValue = Calendar.getInstance();
    dateTimeValue.clear();

    if (matcher.group(1) != null) {
      dateTimeValue.set(Calendar.YEAR, Integer.parseInt(matcher.group(1)));
    }
    if (matcher.group(2) != null) {
      dateTimeValue.set(Calendar.MONTH, Integer.parseInt(matcher.group(2)));
    }
    if (matcher.group(3) != null) {
      dateTimeValue.set(Calendar.DAY_OF_YEAR, Integer.parseInt(matcher.group(3)));
    }
    dateTimeValue.set(Calendar.HOUR_OF_DAY,
        matcher.group(4) == null ? 0 : Integer.parseInt(matcher.group(4)));
    dateTimeValue.set(Calendar.MINUTE,
        matcher.group(5) == null ? 0 : Integer.parseInt(matcher.group(5)));
    dateTimeValue.set(Calendar.SECOND,
        matcher.group(6) == null ? 0 : Integer.parseInt(matcher.group(6)));

    int nanoSeconds = 0;
    if (matcher.group(7) != null) {
      final String decimals = matcher.group(7);
      if (facets == null || facets.getPrecision() == null || facets.getPrecision() >= decimals.length()) {
        nanoSeconds = Integer.parseInt(decimals + "000000000".substring(decimals.length()));
        if (!(returnType.isAssignableFrom(Timestamp.class))) {
          if (nanoSeconds % (1000 * 1000) == 0) {
            dateTimeValue.set(Calendar.MILLISECOND, nanoSeconds / (1000 * 1000));
          } else {
            throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_ILLEGAL_CONTENT.addContent(value));
          }
        }
      } else {
        throw new EdmSimpleTypeException(EdmSimpleTypeException.LITERAL_FACETS_NOT_MATCHED.addContent(value, facets));
      }
    }

    if (returnType.isAssignableFrom(Calendar.class)) {
      return returnType.cast(dateTimeValue);
    } else if (returnType.isAssignableFrom(Long.class)) {
      return returnType.cast(dateTimeValue.getTimeInMillis());
    } else if (returnType.isAssignableFrom(Date.class)) {
      return returnType.cast(dateTimeValue.getTime());
    } else if (returnType.isAssignableFrom(Time.class)) {
      return returnType.cast(new Time(dateTimeValue.getTimeInMillis()));
    } else if (returnType.isAssignableFrom(Timestamp.class)) {
      Timestamp timestamp = new Timestamp(dateTimeValue.getTimeInMillis());
      timestamp.setNanos(nanoSeconds);
      return returnType.cast(timestamp);
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
    Calendar dateTimeValue;
    if (value instanceof Date) {
      dateTimeValue = Calendar.getInstance();
      dateTimeValue.clear();
      dateTimeValue.setTime((Date) value);
    } else if (value instanceof Calendar) {
      dateTimeValue = (Calendar) ((Calendar) value).clone();
    } else if (value instanceof Long) {
      dateTimeValue = Calendar.getInstance(TIME_ZONE_GMT);
      dateTimeValue.clear();
      dateTimeValue.setTimeInMillis((Long) value);
    } else {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_TYPE_NOT_SUPPORTED.addContent(value.getClass()));
    }

    StringBuilder result = new StringBuilder(21); // 21 characters are enough for nanosecond precision.
    result.append('P');
    result.append('T');
    result.append(dateTimeValue.get(Calendar.HOUR_OF_DAY));
    result.append('H');
    result.append(dateTimeValue.get(Calendar.MINUTE));
    result.append('M');
    result.append(dateTimeValue.get(Calendar.SECOND));

    final int fractionalSecs = value instanceof Timestamp ?
        ((Timestamp) value).getNanos() :
        dateTimeValue.get(Calendar.MILLISECOND);
    try {
      EdmDateTime.appendFractionalSeconds(result, fractionalSecs, value instanceof Timestamp, facets);
    } catch (final IllegalArgumentException e) {
      throw new EdmSimpleTypeException(EdmSimpleTypeException.VALUE_FACETS_NOT_MATCHED.addContent(value, facets), e);
    }

    result.append('S');

    return result.toString();
  }

  /**
   * To uri literal.
   *
   * @param literal the literal
   * @return the string
   */
  @Override
  public String toUriLiteral(final String literal) {
    return "time'" + literal + "'";
  }
}
