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
package org.apache.olingo.odata2.core.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * Internally used {@link ContentType} for OData library.
 * 
 * For more details on format and content of a {@link ContentType} see
 * <code>Media Type</code> format as defined in <code>RFC 2616 chapter 3.7
 * (http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html)</code>.
 * <pre>
 * <code>
 * media-type = type "/" subtype *( ";" parameter )
 * type = token
 * subtype = token
 * </code>
 * </pre>
 * 
 * Especially for <code>Accept</code> Header as defined in
 * <code>RFC 2616 chapter 14.1 (http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html)</code>:
 * <pre>
 * <code>
 * Accept = "Accept" ":"
 * #( media-range [ accept-params ] )
 * media-range = ( "* /*"
 * | ( type "/" "*" )
 * | ( type "/" subtype )
 * ) *( ";" parameter )
 * accept-params = ";" "q" "=" qvalue *( accept-extension )
 * accept-extension = ";" token [ "=" ( token | quoted-string ) ]
 * </code>
 * </pre>
 * 
 * Especially for <code>Content-Type</code> Header as defined in
 * <code>RFC 2616 chapter 14.7 (http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html)</code>:
 * <pre>
 * <code>
 * Content-Type = "Content-Type" ":" media-type
 * </code>
 * </pre>
 * 
 * Once created a {@link ContentType} is <b>IMMUTABLE</b>.
 * 
 * 
 */
public class ContentType {

  /**
   * The Enum ODataFormat.
   */
  public enum ODataFormat {
    
    /** The atom. */
    ATOM, 
 /** The xml. */
 XML, 
 /** The json. */
 JSON, 
 /** The mime. */
 MIME, 
 /** The custom. */
 CUSTOM
  }

  /** The Constant KNOWN_MIME_TYPES. */
  private static final Set<String> KNOWN_MIME_TYPES = new HashSet<String>();
  static {
    KNOWN_MIME_TYPES.add("audio");
    KNOWN_MIME_TYPES.add("image");
    KNOWN_MIME_TYPES.add("video");
    KNOWN_MIME_TYPES.add("multipart");
    KNOWN_MIME_TYPES.add("text");
  }

  /** The Constant Q_PARAMETER_COMPARATOR. */
  private static final Comparator<String> Q_PARAMETER_COMPARATOR = new Comparator<String>() {
    @Override
    public int compare(final String o1, final String o2) {
      Float f1 = parseQParameterValue(o1);
      Float f2 = parseQParameterValue(o2);
      return f2.compareTo(f1);
    }
  };

  /** The Constant WHITESPACE_CHAR. */
  private static final char WHITESPACE_CHAR = ' ';
  
  /** The Constant PARAMETER_SEPARATOR. */
  private static final String PARAMETER_SEPARATOR = ";";
  
  /** The Constant PARAMETER_KEY_VALUE_SEPARATOR. */
  private static final String PARAMETER_KEY_VALUE_SEPARATOR = "=";
  
  /** The Constant TYPE_SUBTYPE_SEPARATOR. */
  private static final String TYPE_SUBTYPE_SEPARATOR = "/";
  
  /** The Constant MEDIA_TYPE_WILDCARD. */
  private static final String MEDIA_TYPE_WILDCARD = "*";
  
  /** The Constant VERBOSE. */
  private static final String VERBOSE = "verbose";

  /** The Constant PARAMETER_CHARSET. */
  public static final String PARAMETER_CHARSET = "charset";
  
  /** The Constant PARAMETER_ODATA. */
  public static final String PARAMETER_ODATA = "odata";
  
  /** The Constant PARAMETER_Q. */
  public static final String PARAMETER_Q = "q";
  
  /** The Constant PARAMETER_TYPE. */
  public static final String PARAMETER_TYPE = "type";
  
  /** The Constant CHARSET_UTF_8. */
  public static final String CHARSET_UTF_8 = "utf-8";

  /** The Constant Q_PARAMETER_VALUE_PATTERN. */
  private static final Pattern Q_PARAMETER_VALUE_PATTERN = Pattern.compile("1|0|1\\.0{1,3}|0\\.\\d{1,3}");

  /** The Constant WILDCARD. */
  public static final ContentType WILDCARD = new ContentType(MEDIA_TYPE_WILDCARD, MEDIA_TYPE_WILDCARD);

  /** The Constant APPLICATION_XML. */
  public static final ContentType APPLICATION_XML = new ContentType("application", "xml", ODataFormat.XML);
  
  /** The Constant APPLICATION_XML_CS_UTF_8. */
  public static final ContentType APPLICATION_XML_CS_UTF_8 = ContentType.create(APPLICATION_XML, PARAMETER_CHARSET,
      CHARSET_UTF_8);
  
  /** The Constant APPLICATION_ATOM_XML. */
  public static final ContentType APPLICATION_ATOM_XML = new ContentType("application", "atom+xml", ODataFormat.ATOM);
  
  /** The Constant APPLICATION_ATOM_XML_CS_UTF_8. */
  public static final ContentType APPLICATION_ATOM_XML_CS_UTF_8 = ContentType.create(APPLICATION_ATOM_XML,
      PARAMETER_CHARSET, CHARSET_UTF_8);
  
  /** The Constant APPLICATION_ATOM_XML_ENTRY. */
  public static final ContentType APPLICATION_ATOM_XML_ENTRY = new ContentType("application", "atom+xml",
      ODataFormat.ATOM, parameterMap(PARAMETER_TYPE, "entry"));
  
  /** The Constant APPLICATION_ATOM_XML_ENTRY_CS_UTF_8. */
  public static final ContentType APPLICATION_ATOM_XML_ENTRY_CS_UTF_8 = ContentType.create(APPLICATION_ATOM_XML_ENTRY,
      PARAMETER_CHARSET, CHARSET_UTF_8);
  
  /** The Constant APPLICATION_ATOM_XML_FEED. */
  public static final ContentType APPLICATION_ATOM_XML_FEED = new ContentType("application", "atom+xml",
      ODataFormat.ATOM, parameterMap(PARAMETER_TYPE, "feed"));
  
  /** The Constant APPLICATION_ATOM_XML_FEED_CS_UTF_8. */
  public static final ContentType APPLICATION_ATOM_XML_FEED_CS_UTF_8 = ContentType.create(APPLICATION_ATOM_XML_FEED,
      PARAMETER_CHARSET, CHARSET_UTF_8);
  
  /** The Constant APPLICATION_ATOM_SVC. */
  public static final ContentType APPLICATION_ATOM_SVC =
      new ContentType("application", "atomsvc+xml", ODataFormat.ATOM);
  
  /** The Constant APPLICATION_ATOM_SVC_CS_UTF_8. */
  public static final ContentType APPLICATION_ATOM_SVC_CS_UTF_8 = ContentType.create(APPLICATION_ATOM_SVC,
      PARAMETER_CHARSET, CHARSET_UTF_8);
  
  /** The Constant APPLICATION_JSON. */
  public static final ContentType APPLICATION_JSON = new ContentType("application", "json", ODataFormat.JSON);
  
  /** The Constant APPLICATION_JSON_ODATA_VERBOSE. */
  public static final ContentType APPLICATION_JSON_ODATA_VERBOSE = ContentType.create(APPLICATION_JSON,
      PARAMETER_ODATA, VERBOSE);
  
  /** The Constant APPLICATION_JSON_CS_UTF_8. */
  public static final ContentType APPLICATION_JSON_CS_UTF_8 = ContentType.create(APPLICATION_JSON, PARAMETER_CHARSET,
      CHARSET_UTF_8);
  
  /** The Constant APPLICATION_OCTET_STREAM. */
  public static final ContentType APPLICATION_OCTET_STREAM = new ContentType("application", "octet-stream");
  
  /** The Constant TEXT_PLAIN. */
  public static final ContentType TEXT_PLAIN = new ContentType("text", "plain");
  
  /** The Constant TEXT_PLAIN_CS_UTF_8. */
  public static final ContentType TEXT_PLAIN_CS_UTF_8 = ContentType
      .create(TEXT_PLAIN, PARAMETER_CHARSET, CHARSET_UTF_8);
  
  /** The Constant MULTIPART_MIXED. */
  public static final ContentType MULTIPART_MIXED = new ContentType("multipart", "mixed");

  /** The type. */
  private final String type;
  
  /** The subtype. */
  private final String subtype;
  
  /** The parameters. */
  private final Map<String, String> parameters;
  
  /** The odata format. */
  private final ODataFormat odataFormat;

  /**
   * Instantiates a new content type.
   *
   * @param type the type
   */
  private ContentType(final String type) {
    if (type == null) {
      throw new IllegalArgumentException("Type parameter MUST NOT be null.");
    }
    odataFormat = ODataFormat.CUSTOM;
    this.type = validateType(type);
    subtype = null;
    parameters = Collections.emptyMap();
  }

  /**
   * Instantiates a new content type.
   *
   * @param type the type
   * @param subtype the subtype
   */
  private ContentType(final String type, final String subtype) {
    this(type, subtype, ODataFormat.CUSTOM, null);
  }

  /**
   * Instantiates a new content type.
   *
   * @param type the type
   * @param subtype the subtype
   * @param odataFormat the odata format
   */
  private ContentType(final String type, final String subtype, final ODataFormat odataFormat) {
    this(type, subtype, odataFormat, null);
  }

  /**
   * Instantiates a new content type.
   *
   * @param type the type
   * @param subtype the subtype
   * @param odataFormat the odata format
   * @param parameters the parameters
   */
  private ContentType(final String type, final String subtype, final ODataFormat odataFormat,
      final Map<String, String> parameters) {
    if ((type == null || MEDIA_TYPE_WILDCARD.equals(type)) && !MEDIA_TYPE_WILDCARD.equals(subtype)) {
      throw new IllegalArgumentException("Illegal combination of WILDCARD type with NONE WILDCARD subtype.");
    }
    this.odataFormat = odataFormat;
    this.type = validateType(type);
    this.subtype = validateType(subtype);

    if (parameters == null) {
      this.parameters = Collections.emptyMap();
    } else {
      this.parameters = new TreeMap<String, String>(new Comparator<String>() {
        @Override
        public int compare(final String o1, final String o2) {
          return o1.compareToIgnoreCase(o2);
        }
      });
      this.parameters.putAll(parameters);
      this.parameters.remove(PARAMETER_Q);
    }
  }

  /**
   * Validate type.
   *
   * @param type the type
   * @return the string
   */
  private String validateType(final String type) {
    if (type == null || type.isEmpty()) {
      return MEDIA_TYPE_WILDCARD;
    }
    int len = type.length();
    for (int i = 0; i < len; i++) {
      if (type.charAt(i) == WHITESPACE_CHAR) {
        throw new IllegalArgumentException("Illegal whitespace found for type '" + type + "'.");
      }
    }
    return type;
  }

  /**
   * Validates if given <code>format</code> is parseable and can be used as input for {@link #create(String)} method.
   * @param format to be validated string
   * @return <code>true</code> if format is parseable otherwise <code>false</code>
   */
  public static boolean isParseable(final String format) {
    try {
      return ContentType.create(format) != null;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Validates if given <code>format</code> is parseable and can be used as input for {@link #create(String)} method.
   * @param format to be validated string
   * @return <code>true</code> if format is parseable otherwise <code>false</code>
   */
  public static boolean isParseableAsCustom(final String format) {
    try {
      return ContentType.create(format) != null;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Creates a content type from type and subtype.
   *
   * @param type the type
   * @param subtype the subtype
   * @return a new <code>ContentType</code> object
   */
  public static ContentType create(final String type, final String subtype) {
    return new ContentType(type, subtype, mapToODataFormat(type, subtype), null);
  }

  /**
   * Creates the.
   *
   * @param type the type
   * @param subtype the subtype
   * @param parameters the parameters
   * @return a new <code>ContentType</code> object
   */
  public static ContentType create(final String type, final String subtype, final Map<String, String> parameters) {
    return new ContentType(type, subtype, mapToODataFormat(type, subtype), parameters);
  }

  /**
   * Creates the.
   *
   * @param contentType the content type
   * @param parameterKey the parameter key
   * @param parameterValue the parameter value
   * @return a new <code>ContentType</code> object
   */
  public static ContentType
      create(final ContentType contentType, final String parameterKey, final String parameterValue) {
    ContentType ct =
        new ContentType(contentType.type, contentType.subtype, contentType.odataFormat, contentType.parameters);
    ct.parameters.put(parameterKey, parameterValue);
    return ct;
  }

  /**
   * Create a {@link ContentType} based on given input string (<code>format</code>).
   * 
   * Supported format is <code>Media Type</code> format as defined in <code>RFC 2616 chapter 3.7</code>.
   * This format is used as
   * <code>HTTP Accept HEADER</code> format as defined in <code>RFC 2616 chapter 14.1</code>
   * and
   * <code>HTTP Content-Type HEADER</code> format as defined in <code>RFC 2616 chapter 14.17</code>
   * 
   * @param format a string in format as defined in <code>RFC 2616 section 3.7</code>
   * @return a new <code>ContentType</code> object
   * @throws IllegalArgumentException if input string is not parseable
   */
  public static ContentType create(final String format) {
    if (format == null) {
      throw new IllegalArgumentException("Parameter format MUST NOT be NULL.");
    }

    // split 'types' and 'parameters'
    String[] typesAndParameters = format.split(PARAMETER_SEPARATOR, 2);
    String types = typesAndParameters[0];
    String parameters = (typesAndParameters.length > 1 ? typesAndParameters[1] : null);
    //
    Map<String, String> parametersMap = parseParameters(parameters);
    //
    if (types.contains(TYPE_SUBTYPE_SEPARATOR)) {
      String[] tokens = types.split(TYPE_SUBTYPE_SEPARATOR);
      if (tokens.length == 2) {
        if (tokens[0] == null || tokens[0].isEmpty()) {
          throw new IllegalArgumentException("No type found in format '" + format + "'.");
        } else if (tokens[1] == null || tokens[1].isEmpty()) {
          throw new IllegalArgumentException("No subtype found in format '" + format + "'.");
        } else {
          return create(tokens[0], tokens[1], parametersMap);
        }
      } else {
        throw new IllegalArgumentException("Too many '" + TYPE_SUBTYPE_SEPARATOR + "' in format '" + format + "'.");
      }
    } else if (MEDIA_TYPE_WILDCARD.equals(types)) {
      return ContentType.WILDCARD;
    } else {
      throw new IllegalArgumentException("No separator '" + TYPE_SUBTYPE_SEPARATOR + "' was found in format '" + format
          + "'.");
    }
  }

  /**
   * Create a {@link ContentType} based on given input string (<code>format</code>).
   * 
   * Supported format is <code>Media Type</code> format as defined in <code>RFC 2616 chapter 3.7</code>.
   * and {@link ContentType} with {@link ODataFormat#CUSTOM}.
   * 
   * The <code>Media Type</code> format can be used as
   * <code>HTTP Accept HEADER</code> format as defined in <code>RFC 2616 chapter 14.1</code>
   * and
   * <code>HTTP Content-Type HEADER</code> format as defined in <code>RFC 2616 chapter 14.17</code>.
   * The {@link ContentType} with {@link ODataFormat#CUSTOM} can only be used as <code>$format</code> system query
   * option
   * (as defined
   * http://www.odata.org/documentation/odata-v2-documentation/uri-conventions/#47_Format_System_Query_Option_format).
   * 
   * @param format a string in format as defined in <code>RFC 2616 section 3.7</code>
   * @return a new <code>ContentType</code> object
   * @throws IllegalArgumentException if input string is not parseable
   */
  public static ContentType createAsCustom(final String format) {
    ContentType parsedContentType = parse(format);
    if (parsedContentType == null) {
      return new ContentType(format);
    }
    return parsedContentType;
  }

  /**
   * Create a list of {@link ContentType} based on given input strings (<code>contentTypes</code>).
   * 
   * Supported format is <code>Media Type</code> format as defined in <code>RFC 2616 chapter 3.7</code>.
   * This format is used as
   * <code>HTTP Accept HEADER</code> format as defined in <code>RFC 2616 chapter 14.1</code>
   * and
   * <code>HTTP Content-Type HEADER</code> format as defined in <code>RFC 2616 chapter 14.17</code>.
   * <p>
   * If one of the given strings can not be parsed an exception is thrown (hence no list is returned with the parseable
   * strings).
   * </p>
   * 
   * @param contentTypeStrings a list of strings in format as defined in <code>RFC 2616 section 3.7</code>
   * @return a list of new <code>ContentType</code> object
   * @throws IllegalArgumentException if one of the given input string is not parseable this exceptions is thrown
   */
  public static List<ContentType> create(final List<String> contentTypeStrings) {
    List<ContentType> contentTypes = new ArrayList<ContentType>(contentTypeStrings.size());
    for (String contentTypeString : contentTypeStrings) {
      contentTypes.add(create(contentTypeString));
    }
    return contentTypes;
  }

  /**
   * Create a list of {@link ContentType} based on given input strings (<code>contentTypes</code>).
   * 
   * Supported format is <code>Media Type</code> format as defined in <code>RFC 2616 chapter 3.7</code>.
   * and {@link ContentType} with {@link ODataFormat#CUSTOM}.
   * 
   * The <code>Media Type</code> format can be used as
   * <code>HTTP Accept HEADER</code> format as defined in <code>RFC 2616 chapter 14.1</code>
   * and
   * <code>HTTP Content-Type HEADER</code> format as defined in <code>RFC 2616 chapter 14.17</code>.
   * The {@link ContentType} with {@link ODataFormat#CUSTOM} can only be used as <code>$format</code> system query
   * option
   * (as defined
   * http://www.odata.org/documentation/odata-v2-documentation/uri-conventions/#47_Format_System_Query_Option_format).
   * 
   * @param contentTypeStrings a list of strings in format as defined in <code>RFC 2616 section 3.7</code> or
   * as defined
   * http://www.odata.org/documentation/odata-v2-documentation/uri-conventions/#47_Format_System_Query_Option_format
   * @return a list of new <code>ContentType</code> object
   * @throws IllegalArgumentException if one of the given input string is not parseable this exceptions is thrown
   */
  public static List<ContentType> createAsCustom(final List<String> contentTypeStrings) {
    List<ContentType> contentTypes = new ArrayList<ContentType>(contentTypeStrings.size());
    for (String contentTypeString : contentTypeStrings) {
      contentTypes.add(createAsCustom(contentTypeString));
    }
    return contentTypes;
  }

  /**
   * Parses the given input string (<code>format</code>) and returns created {@link ContentType} if input was valid or
   * return <code>NULL</code> if
   * input was not parseable.
   * 
   * For the definition of the supported format see {@link #create(String)}.
   * 
   * @param format a string in format as defined in <code>RFC 2616 section 3.7</code>
   * @return a new <code>ContentType</code> object
   */
  public static ContentType parse(final String format) {
    try {
      return ContentType.create(format);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * Sort given list (which must contains content type formated string) for their {@value #PARAMETER_Q} value
   * as defined in <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1">RFC 2616 section 4.1</a> and
   * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec3.html#sec3.9">RFC 2616 Section 3.9</a>.
   * 
   * <b>Attention:</b> For invalid values a {@value #PARAMETER_Q} value from <code>-1</code> is used for sorting.
   * 
   * @param toSort list which is sorted and hence re-arranged
   */
  public static void sortForQParameter(final List<String> toSort) {
    Collections.sort(toSort, ContentType.Q_PARAMETER_COMPARATOR);
  }

  /**
   * Map combination of type/subtype to corresponding {@link ODataFormat}.
   * 
   * @param type type which is used for mapping
   * @param subtype subtype which is used for mapping
   * @return corresponding {@link ODataFormat} or {@link ODataFormat#CUSTOM} if none specific format is available.
   */
  private static ODataFormat mapToODataFormat(final String type, final String subtype) {
    ODataFormat odataFormat = ODataFormat.CUSTOM;
    if (type.contains("application")) {
      if (subtype.contains("atom")) {
        odataFormat = ODataFormat.ATOM;
      } else if (subtype.contains("xml")) {
        odataFormat = ODataFormat.XML;
      } else if (subtype.contains("json")) {
        odataFormat = ODataFormat.JSON;
      }
    } else if (KNOWN_MIME_TYPES.contains(type)) {
      odataFormat = ODataFormat.MIME;
    }
    return odataFormat;
  }

  /**
   * Maps content of array into map.
   * Therefore it must be an combination of <code>key</code> followed by the <code>value</code> in the array.
   * 
   * @param content content which is added to {@link Map}.
   * @return a new <code>ContentType</code> object
   */
  private static Map<String, String> parameterMap(final String... content) {
    Map<String, String> map = new HashMap<String, String>();
    for (int i = 0; i < content.length - 1; i += 2) {
      String key = content[i];
      String value = content[i + 1];
      map.put(key, value);
    }
    return map;
  }

  /**
   * Valid input are <code>;</code> separated <code>key=value</code> pairs
   * without spaces between key and value.
   * <b>Attention:</b> <code>q</code> parameter is validated but not added to result map
   * 
   * <p>
   * See RFC 2616:
   * The type, subtype, and parameter attribute names are case-insensitive.
   * Parameter values might or might not be case-sensitive, depending on the
   * semantics of the parameter name. <b>Linear white space (LWS) MUST NOT be used
   * between the type and subtype, nor between an attribute and its value</b>.
   * </p>
   *
   * @param parameters the parameters
   * @return Map with keys mapped to values
   */
  private static Map<String, String> parseParameters(final String parameters) {
    Map<String, String> parameterMap = new HashMap<String, String>();
    if (parameters != null) {
      String[] splittedParameters = parameters.split(PARAMETER_SEPARATOR);
      for (String parameter : splittedParameters) {
        String[] keyValue = parameter.split(PARAMETER_KEY_VALUE_SEPARATOR);
        String key = keyValue[0].trim().toLowerCase(Locale.ENGLISH);
        String value = keyValue.length > 1 ? keyValue[1] : null;
        if (value != null && isLws(value.charAt(0))) {
          throw new IllegalArgumentException("Value of parameter '" + key + "' starts with a LWS ('" + parameters
              + "').");
        }
        if (PARAMETER_Q.equals(key.toLowerCase(Locale.US))) {
          // q parameter is only validated but not added
          if (!Q_PARAMETER_VALUE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Value of 'q' parameter is not valid (q='" + value + "').");
          }
        } else {
          parameterMap.put(key, value);
        }
      }
    }
    return parameterMap;
  }

  /**
   * Parse value of {@value #PARAMETER_Q} <code>parameter</code> out of content type/parameters.
   * If no {@value #PARAMETER_Q} <code>parameter</code> is in <code>content type/parameters</code> parameter found
   * <code>1</code> is returned.
   * If {@value #PARAMETER_Q} <code>parameter</code> is invalid <code>-1</code> is returned.
   * 
   * @param contentType parameter which is parsed for {@value #PARAMETER_Q} <code>parameter</code> value
   * @return value of {@value #PARAMETER_Q} <code>parameter</code> or <code>1</code> or <code>-1</code>
   */
  private static Float parseQParameterValue(final String contentType) {
    if (contentType != null) {
      String[] splittedParameters = contentType.split(PARAMETER_SEPARATOR);
      for (String parameter : splittedParameters) {
        String[] keyValue = parameter.split(PARAMETER_KEY_VALUE_SEPARATOR);
        String key = keyValue[0].trim().toLowerCase(Locale.ENGLISH);
        if (PARAMETER_Q.equalsIgnoreCase(key)) {
          String value = keyValue.length > 1 ? keyValue[1] : null;
          if (Q_PARAMETER_VALUE_PATTERN.matcher(value).matches()) {
            return Float.valueOf(value);
          }
          return Float.valueOf(-1);
        }
      }
    }
    return Float.valueOf(1);
  }

  /**
   * Check if parameter with key value is a allowed parameter.
   *
   * @param key the key
   * @return true, if is parameter allowed
   */
  private static boolean isParameterAllowed(final String key) {
    return key != null && !PARAMETER_Q.equals(key.toLowerCase(Locale.US));
  }

  /**
   * Validate if given character is a linear whitepace (includes <code>horizontal-tab, linefeed, carriage return and
   * space</code>).
   * 
   * @param character to be checked
   * @return <code>true</code> if character is a LWS, otherwise <code>false</code>.
   */
  private static boolean isLws(final char character) {
    switch (character) {
    case 9: // HT = <US-ASCII HT, horizontal-tab (9)>
    case 10: // LF = <US-ASCII LF, linefeed (10)>
    case 13: // CR = <US-ASCII CR, carriage return (13)>
    case 32: // SP = <US-ASCII SP, space (32)>
      return true;
    default:
      return false;
    }
  }

  /**
   * Ensure that charset parameter ({@link #PARAMETER_CHARSET}) is set on returned content type
   * if this {@link ContentType} is a <code>odata text related</code> content type (@see
   * {@link #isContentTypeODataTextRelated()}).
   * If <code>this</code> {@link ContentType} has no charset parameter set a new {@link ContentType} with given
   * <code>defaultCharset</code> is created.
   * Otherwise if charset parameter is already set nothing is done.
   *
   * @param defaultCharset the default charset
   * @return ContentType
   */
  public ContentType receiveWithCharsetParameter(final String defaultCharset) {
    if (isContentTypeODataTextRelated()) {
      if (!parameters.containsKey(ContentType.PARAMETER_CHARSET)) {
        return ContentType.create(this, ContentType.PARAMETER_CHARSET, defaultCharset);
      }
    }
    return this;
  }

  /**
   * Checks if is content type O data text related.
   *
   * @return <code>true</code> if this {@link ContentType} is text related (in the view of OData)
   */
  public boolean isContentTypeODataTextRelated() {
    return (ContentType.TEXT_PLAIN.equals(this)
        || (getODataFormat() == ODataFormat.XML)
        || (getODataFormat() == ODataFormat.ATOM)
        || (getODataFormat() == ODataFormat.JSON));
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Gets the subtype.
   *
   * @return the subtype
   */
  public String getSubtype() {
    return subtype;
  }

  /**
   * Gets the parameters.
   *
   * @return parameters of this {@link ContentType} as unmodifiable map.
   */
  public Map<String, String> getParameters() {
    return Collections.unmodifiableMap(parameters);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return 1;
  }

  /**
   * {@link ContentType}s are equal
   * <ul>
   * <li>if <code>type</code>, <code>subtype</code> and all <code>parameters</code> have the same value.</li>
   * <li>if <code>type</code> and/or <code>subtype</code> is set to "*" (in such a case the <code>parameters</code> are
   * ignored).</li>
   * </ul>
   *
   * @param obj the obj
   * @return <code>true</code> if both instances are equal (see definition above), otherwise <code>false</code>.
   */
  @Override
  public boolean equals(final Object obj) {
    // NULL validation is done in method 'isEqualWithoutParameters(obj)'
    Boolean compatible = isEqualWithoutParameters(obj);

    if (compatible == null) {
      ContentType other = (ContentType) obj;

      // parameter checks
      if (parameters == null) {
        if (other.parameters != null) {
          return false;
        }
      } else if (parameters.size() == other.parameters.size()) {
        Iterator<Entry<String, String>> entries = parameters.entrySet().iterator();
        Iterator<Entry<String, String>> otherEntries = other.parameters.entrySet().iterator();
        while (entries.hasNext()) {
          Entry<String, String> e = entries.next();
          Entry<String, String> oe = otherEntries.next();

          if (!areEqual(e.getKey(), oe.getKey())) {
            return false;
          }
          if (!areEqual(e.getValue(), oe.getValue())) {
            return false;
          }
        }
      } else {
        return false;
      }
      return true;
    } else {
      // all tests run
      return compatible.booleanValue();
    }
  }

  /**
   * {@link ContentType}s are <b>compatible</b>
   * <ul>
   * <li>if <code>type</code>, <code>subtype</code> have the same value.</li>
   * <li>if <code>type</code> and/or <code>subtype</code> is set to "*"</li>
   * </ul>
   * The set <code>parameters</code> are <b>always</b> ignored (for compare with parameters see {@link #equals(Object)}
   * ).
   *
   * @param obj the obj
   * @return <code>true</code> if both instances are equal (see definition above), otherwise <code>false</code>.
   */
  public boolean isCompatible(final ContentType obj) {
    Boolean compatible = isEqualWithoutParameters(obj);
    if (compatible == null) {
      return true;
    }
    return compatible.booleanValue();
  }

  /**
   * Check equal without parameters.
   * It is possible that no decision about <code>equal/none equal</code> can be determined a <code>NULL</code> is
   * returned.
   * 
   * @param obj to checked object
   * @return <code>true</code> if both instances are equal (see definition above), otherwise <code>false</code>
   * or <code>NULL</code> if no decision about <code>equal/none equal</code> could be determined.
   */
  private Boolean isEqualWithoutParameters(final Object obj) {
    // basic checks
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    ContentType other = (ContentType) obj;

    // subtype checks
    if (subtype == null) {
      if (other.subtype != null) {
        return false;
      }
    } else if (!subtype.equals(other.subtype)) {
      if (other.subtype == null) {
        return false;
      } else if (!subtype.equals(MEDIA_TYPE_WILDCARD) && !other.subtype.equals(MEDIA_TYPE_WILDCARD)) {
        return false;
      }
    }

    // type checks
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      if (!type.equals(MEDIA_TYPE_WILDCARD) && !other.type.equals(MEDIA_TYPE_WILDCARD)) {
        return false;
      }
    }

    // if wildcards are set, content types are defined as 'equal'
    if (countWildcards() > 0 || other.countWildcards() > 0) {
      return true;
    }

    return null;
  }

  /**
   * Check whether both string are equal ignoring the case of the strings.
   * 
   * @param first first string
   * @param second second string
   * @return <code>true</code> if both strings are equal (by ignoring the case), otherwise <code>false</code> is
   * returned
   */
  private static boolean areEqual(final String first, final String second) {
    if (first == null) {
      if (second != null) {
        return false;
      }
    } else if (!first.equalsIgnoreCase(second)) {
      return false;
    }
    return true;
  }

  /**
   * Get {@link ContentType} as string as defined in RFC 2616 (http://www.ietf.org/rfc/rfc2616.txt - chapter 14.17:
   * Content-Type)
   * 
   * @return string representation of <code>ContentType</code> object
   */
  public String toContentTypeString() {
    StringBuilder sb = new StringBuilder();

    if (odataFormat == ODataFormat.CUSTOM && subtype == null) {
      sb.append(type);
    } else {
      sb.append(type).append(TYPE_SUBTYPE_SEPARATOR).append(subtype);
    }
    
    for (Entry<String, String> parameter : parameters.entrySet()) {
      if (isParameterAllowed(parameter.getKey())) {
        String value = parameter.getValue();
        sb.append(";").append(parameter.getKey()).append("=").append(value);
      }
    }
    
    return sb.toString();
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return toContentTypeString();
  }

  /**
   * Gets the o data format.
   *
   * @return the o data format
   */
  public ODataFormat getODataFormat() {
    return odataFormat;
  }

  /**
   * Find best match between this {@link ContentType} and the {@link ContentType} in the list.
   * If a match (this {@link ContentType} is equal to a {@link ContentType} in list) is found either this or the
   * {@link ContentType} from the list is returned based on which {@link ContentType} has less "**" characters set
   * (checked with {@link #compareWildcardCounts(ContentType)}.
   * If no match (none {@link ContentType} in list is equal to this {@link ContentType}) is found <code>NULL</code> is
   * returned.
   * 
   * @param toMatchContentTypes list of {@link ContentType}s which are matches against this {@link ContentType}
   * @return best matched content type in list or <code>NULL</code> if none content type match to this content type
   * instance
   */
  public ContentType match(final List<ContentType> toMatchContentTypes) {
    for (ContentType supportedContentType : toMatchContentTypes) {
      if (equals(supportedContentType)) {
        if (compareWildcardCounts(supportedContentType) < 0) {
          return this;
        } else {
          return supportedContentType;
        }
      }
    }
    return null;
  }

  /**
   * Find best match between this {@link ContentType} and the {@link ContentType} in the list ignoring all set
   * parameters.
   * If a match (this {@link ContentType} is equal to a {@link ContentType} in list) is found either this or the
   * {@link ContentType} from the list is returned based on which {@link ContentType} has less "**" characters set
   * (checked with {@link #compareWildcardCounts(ContentType)}.
   * If no match (none {@link ContentType} in list is equal to this {@link ContentType}) is found <code>NULL</code> is
   * returned.
   * 
   * @param toMatchContentTypes list of {@link ContentType}s which are matches against this {@link ContentType}
   * @return best matched content type in list or <code>NULL</code> if none content type match to this content type
   * instance
   */
  public ContentType matchCompatible(final List<ContentType> toMatchContentTypes) {
    for (ContentType supportedContentType : toMatchContentTypes) {
      if (isCompatible(supportedContentType)) {
        if (compareWildcardCounts(supportedContentType) < 0) {
          return this;
        } else {
          return supportedContentType;
        }
      }
    }
    return null;
  }

  /**
   * Check if a valid compatible match for this {@link ContentType} exists in given list.
   * Compatible in this case means that <b>all set parameters are ignored</b>.
   * For more detail what a valid match is see {@link #matchCompatible(List)}.
   * 
   * @param toMatchContentTypes list of {@link ContentType}s which are matches against this {@link ContentType}
   * @return <code>true</code> if a compatible content type was found in given list
   * or <code>false</code> if none compatible content type match was found
   */
  public boolean hasCompatible(final List<ContentType> toMatchContentTypes) {
    return matchCompatible(toMatchContentTypes) != null;
  }

  /**
   * Check if a valid match for this {@link ContentType} exists in given list.
   * For more detail what a valid match is see {@link #match(List)}.
   * 
   * @param toMatchContentTypes list of {@link ContentType}s which are matches against this {@link ContentType}
   * @return <code>true</code> if a matching content type was found in given list
   * or <code>false</code> if none matching content type match was found
   */
  public boolean hasMatch(final List<ContentType> toMatchContentTypes) {
    return match(toMatchContentTypes) != null;
  }

  /**
   * Compare wildcards counts/weights of both {@link ContentType}.
   * 
   * The smaller {@link ContentType} has lesser weighted wildcards then the bigger {@link ContentType}.
   * As result this method returns this object weighted wildcards minus the given parameter object weighted wildcards.
   * 
   * A type wildcard is weighted with <code>2</code> and a subtype wildcard is weighted with <code>1</code>.
   * 
   * @param otherContentType {@link ContentType} to be compared to
   * @return this object weighted wildcards minus the given parameter object weighted wildcards.
   */
  public int compareWildcardCounts(final ContentType otherContentType) {
    return countWildcards() - otherContentType.countWildcards();
  }

  /**
   * Count wildcards.
   *
   * @return the int
   */
  private int countWildcards() {
    int count = 0;
    if (MEDIA_TYPE_WILDCARD.equals(type)) {
      count += 2;
    }
    if (MEDIA_TYPE_WILDCARD.equals(subtype)) {
      count++;
    }
    return count;
  }

  /**
   * Checks for wildcard.
   *
   * @return <code>true</code> if <code>type</code> or <code>subtype</code> of this instance is a "*".
   */
  public boolean hasWildcard() {
    return (MEDIA_TYPE_WILDCARD.equals(type) || MEDIA_TYPE_WILDCARD.equals(subtype));
  }

  /**
   * Checks if is wildcard.
   *
   * @return <code>true</code> if both <code>type</code> and <code>subtype</code> of this instance are a "*".
   */
  public boolean isWildcard() {
    return (MEDIA_TYPE_WILDCARD.equals(type) && MEDIA_TYPE_WILDCARD.equals(subtype));
  }

  /**
   * Convert.
   *
   * @param types the types
   * @return the list
   */
  public static List<ContentType> convert(final List<String> types) {
    List<ContentType> results = new ArrayList<ContentType>();
    for (String contentType : types) {
      results.add(ContentType.create(contentType));
    }
    return results;
  }

  /**
   * Check if a valid match for given content type formated string (<code>toMatch</code>) exists in given list.
   * Therefore the given content type formated string (<code>toMatch</code>) is converted into a {@link ContentType}
   * with a simple {@link #create(String)} call (during which an exception can occur).
   * 
   * For more detail in general see {@link #hasMatch(List)} and for what a valid match is see {@link #match(List)}.
   * 
   * @param toMatch content type formated string (<code>toMatch</code>) for which is checked if a match exists in given
   * list
   * @param matchExamples list of {@link ContentType}s which are matches against content type formated string
   * (<code>toMatch</code>)
   * @return <code>true</code> if a matching content type was found in given list
   * or <code>false</code> if none matching content type match was found
   */
  public static boolean match(final String toMatch, final ContentType... matchExamples) {
    ContentType toMatchContentType = ContentType.create(toMatch);

    return toMatchContentType.hasMatch(Arrays.asList(matchExamples));
  }
}
