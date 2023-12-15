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
package org.apache.olingo.odata2.api.commons;

// TODO: Auto-generated Javadoc
/**
 * Constants for <code>Http Content Type</code> definitions as specified in
 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html">RFC 2616 Section 14</a>.
 * 
 */
public interface HttpContentType {

  /** The Constant APPLICATION_XML. */
  public static final String APPLICATION_XML = "application/xml";
  
  /** The Constant APPLICATION_XML_UTF8. */
  public static final String APPLICATION_XML_UTF8 = APPLICATION_XML + ";charset=utf-8";

  /** The Constant APPLICATION_ATOM_XML. */
  public static final String APPLICATION_ATOM_XML = "application/atom+xml";
  
  /** The Constant APPLICATION_ATOM_XML_UTF8. */
  public static final String APPLICATION_ATOM_XML_UTF8 = APPLICATION_ATOM_XML + ";charset=utf-8";
  
  /** The Constant APPLICATION_ATOM_XML_ENTRY. */
  public static final String APPLICATION_ATOM_XML_ENTRY = APPLICATION_ATOM_XML + ";type=entry";
  
  /** The Constant APPLICATION_ATOM_XML_ENTRY_UTF8. */
  public static final String APPLICATION_ATOM_XML_ENTRY_UTF8 = APPLICATION_ATOM_XML_ENTRY + ";charset=utf-8";
  
  /** The Constant APPLICATION_ATOM_XML_FEED. */
  public static final String APPLICATION_ATOM_XML_FEED = APPLICATION_ATOM_XML + ";type=feed";
  
  /** The Constant APPLICATION_ATOM_XML_FEED_UTF8. */
  public static final String APPLICATION_ATOM_XML_FEED_UTF8 = APPLICATION_ATOM_XML_FEED + ";charset=utf-8";
  
  /** The Constant APPLICATION_ATOM_SVC. */
  public static final String APPLICATION_ATOM_SVC = "application/atomsvc+xml";
  
  /** The Constant APPLICATION_ATOM_SVC_UTF8. */
  public static final String APPLICATION_ATOM_SVC_UTF8 = APPLICATION_ATOM_SVC + ";charset=utf-8";

  /** The Constant APPLICATION_JSON. */
  public static final String APPLICATION_JSON = "application/json";
  
  /** The Constant APPLICATION_JSON_VERBOSE. */
  public static final String APPLICATION_JSON_VERBOSE = APPLICATION_JSON + ";odata=verbose";
  
  /** The Constant APPLICATION_JSON_UTF8. */
  public static final String APPLICATION_JSON_UTF8 = APPLICATION_JSON + ";charset=utf-8";
  
  /** The Constant APPLICATION_JSON_UTF8_VERBOSE. */
  public static final String APPLICATION_JSON_UTF8_VERBOSE = APPLICATION_JSON_UTF8 + ";odata=verbose";

  /** The Constant TEXT_PLAIN. */
  public static final String TEXT_PLAIN = "text/plain";
  
  /** The Constant TEXT_PLAIN_UTF8. */
  public static final String TEXT_PLAIN_UTF8 = TEXT_PLAIN + ";charset=utf-8";

  /** The Constant TEXT_HTML. */
  public static final String TEXT_HTML = "text/html";

  /** The Constant APPLICATION_OCTET_STREAM. */
  public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

  /** The Constant APPLICATION_HTTP. */
  public static final String APPLICATION_HTTP = "application/http";

  /** The Constant MULTIPART_MIXED. */
  public static final String MULTIPART_MIXED = "multipart/mixed";

  /** The Constant WILDCARD. */
  public static final String WILDCARD = "*/*";
}
