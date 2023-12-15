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
package org.apache.olingo.odata2.core.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.olingo.odata2.api.batch.BatchException;
import org.apache.olingo.odata2.api.exception.MessageReference;

// TODO: Auto-generated Javadoc
/**
 * The Class AcceptParser.
 */
public class AcceptParser {

  /** The Constant BAD_REQUEST. */
  private static final String BAD_REQUEST = "400";
  
  /** The Constant ALL. */
  private static final String ALL = "*";
  
  /** The Constant REG_EX_QUALITY_FACTOR. */
  private static final String REG_EX_QUALITY_FACTOR = "q=((?:1\\.0{0,3})|(?:0\\.[0-9]{0,2}[1-9]))";
  
  /** The Constant REG_EX_OPTIONAL_WHITESPACE. */
  private static final String REG_EX_OPTIONAL_WHITESPACE = "\\s?";
  
  /** The Constant REG_EX_ACCEPT. */
  private static final Pattern REG_EX_ACCEPT = Pattern.compile("([a-z\\*]+/[a-z0-9\\+\\*\\-=;\\s]+)");
  
  /** The Constant REG_EX_ACCEPT_WITH_Q_FACTOR. */
  private static final Pattern REG_EX_ACCEPT_WITH_Q_FACTOR = Pattern.compile(REG_EX_ACCEPT + "(?:;"
      + REG_EX_OPTIONAL_WHITESPACE + REG_EX_QUALITY_FACTOR + ")?");
  
  /** The Constant REG_EX_ACCEPT_LANGUAGES. */
  private static final Pattern REG_EX_ACCEPT_LANGUAGES = Pattern
      .compile("((?:(?:[a-zA-Z]{1,8})(?:-[a-zA-Z0-9]{1,8}){0,})|(?:\\*))");
  
  /** The Constant REG_EX_ACCEPT_LANGUAGES_WITH_Q_FACTOR. */
  private static final Pattern REG_EX_ACCEPT_LANGUAGES_WITH_Q_FACTOR = Pattern.compile(REG_EX_ACCEPT_LANGUAGES + "(?:;"
      + REG_EX_OPTIONAL_WHITESPACE + REG_EX_QUALITY_FACTOR + ")?");

  /** The Constant QUALITY_PARAM_FACTOR. */
  private static final double QUALITY_PARAM_FACTOR = 0.001;

  /** The accept header values. */
  private List<String> acceptHeaderValues = new ArrayList<String>();
  
  /** The accept language header values. */
  private List<String> acceptLanguageHeaderValues = new ArrayList<String>();

  /**
   * Parses the accept headers.
   *
   * @return the list
   * @throws BatchException the batch exception
   */
  public List<String> parseAcceptHeaders() throws BatchException {
    return parseQualifiedHeader(acceptHeaderValues,
        REG_EX_ACCEPT_WITH_Q_FACTOR,
        BatchException.INVALID_ACCEPT_HEADER);
  }

  /**
   * Parses the acceptable languages.
   *
   * @return the list
   * @throws BatchException the batch exception
   */
  public List<String> parseAcceptableLanguages() throws BatchException {
    return parseQualifiedHeader(acceptLanguageHeaderValues,
        REG_EX_ACCEPT_LANGUAGES_WITH_Q_FACTOR,
        BatchException.INVALID_ACCEPT_LANGUAGE_HEADER);
  }

  /**
   * Parses the qualified header.
   *
   * @param headerValues the header values
   * @param regEx the reg ex
   * @param exectionMessage the exection message
   * @return the list
   * @throws BatchException the batch exception
   */
  private List<String> parseQualifiedHeader(List<String> headerValues, Pattern regEx, MessageReference exectionMessage)
      throws BatchException {
    final TreeSet<Accept> acceptTree = new TreeSet<AcceptParser.Accept>();
    final List<String> acceptHeaders = new ArrayList<String>();

    for (final String headerValue : headerValues) {
      final String[] acceptParts = headerValue.split(",");

      for (final String part : acceptParts) {
        final Matcher matcher = regEx.matcher(part.trim());

        if (matcher.matches() && matcher.groupCount() == 2) {
          final Accept acceptHeader = getQualifiedHeader(matcher);
          acceptTree.add(acceptHeader);
        } else {
          throw new BatchException(exectionMessage.addContent(part), BAD_REQUEST);
        }
      }
    }

    for (Accept accept : acceptTree) {
      if (!acceptHeaders.contains(accept.getValue())) {
        acceptHeaders.add(accept.getValue());
      }
    }
    return acceptHeaders;
  }

  /**
   * Gets the qualified header.
   *
   * @param matcher the matcher
   * @return the qualified header
   */
  private Accept getQualifiedHeader(final Matcher matcher) {
    final String acceptHeaderValue = matcher.group(1);
    double qualityFactor = matcher.group(2) != null ? Double.parseDouble(matcher.group(2)) : 1d;
    qualityFactor = getQualityFactor(acceptHeaderValue, qualityFactor);

    return new Accept().setQuality(qualityFactor).setValue(acceptHeaderValue);
  }
  
  /**
   * Gets the quality factor.
   *
   * @param acceptHeaderValue the accept header value
   * @param qualityFactor the quality factor
   * @return the quality factor
   */
  private double getQualityFactor(final String acceptHeaderValue, double qualityFactor) {
    int paramNumber = 0;
    double typeFactor = 0.0;
    double subtypeFactor = 0.0;
    String[] mediaRange = acceptHeaderValue.split("(?=[^;]+);");
    String[] mediaTypes = mediaRange[0].split("/");

    if (mediaTypes.length == 2) {
      String type = mediaTypes[0];
      String subtype = mediaTypes[1];
      if (!ALL.equals(type)) {
        typeFactor = 0.001;
      }
      if (!ALL.equals(subtype)) {
        subtypeFactor = 0.001;
      }
    }
    if (mediaRange.length == 2) {
      String[] parameters = mediaRange[1].split(";\\s?");
      paramNumber = parameters.length;
    }

    qualityFactor = qualityFactor + paramNumber * QUALITY_PARAM_FACTOR + typeFactor + subtypeFactor;
    return qualityFactor;
  }
  
  /**
   * Adds the accept header value.
   *
   * @param headerValue the header value
   */
  public void addAcceptHeaderValue(final String headerValue) {
    acceptHeaderValues.add(headerValue);
  }

  /**
   * Adds the accept language header value.
   *
   * @param headerValue the header value
   */
  public void addAcceptLanguageHeaderValue(final String headerValue) {
    acceptLanguageHeaderValues.add(headerValue);
  }

  /**
   * The Class Accept.
   */
  private static class Accept implements Comparable<Accept> {
    
    /** The quality. */
    private double quality;
    
    /** The value. */
    private String value;

    /**
     * Gets the value.
     *
     * @return the value
     */
    public String getValue() {
      return value;
    }

    /**
     * Sets the value.
     *
     * @param value the value
     * @return the accept
     */
    public Accept setValue(final String value) {
      this.value = value;
      return this;
    }

    /**
     * Sets the quality.
     *
     * @param quality the quality
     * @return the accept
     */
    public Accept setQuality(final double quality) {
      this.quality = quality;
      return this;
    }

    /**
     * Compare to.
     *
     * @param o the o
     * @return the int
     */
    @Override
    public int compareTo(Accept o) {
      if (quality <= o.quality) {
        return 1;
      } else {
        return -1;
      }
    }
  }
}
