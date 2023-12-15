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
 * HTTP status codes as defined in RFC2616-sec10
 * and additional status codes as defined in RFC6585.
 */
public enum HttpStatusCodes {

  /** The ok. */
  OK(200, "OK"), /** The created. */
 CREATED(201, "Created"), /** The accepted. */
 ACCEPTED(202, "Accepted"), /** The no content. */
 NO_CONTENT(204, "No Content"), /** The reset content. */
 RESET_CONTENT(205,
      "Reset Content"), 
 /** The partial content. */
 PARTIAL_CONTENT(206, "Partial Content"),

  /** The moved permanently. */
  MOVED_PERMANENTLY(301, "Moved Permanently"), /** The found. */
 FOUND(302, "Found"), /** The see other. */
 SEE_OTHER(303, "See Other"), /** The not modified. */
 NOT_MODIFIED(304,
      "Not Modified"), 
 /** The use proxy. */
 USE_PROXY(305, "Use Proxy"), 
 /** The temporary redirect. */
 TEMPORARY_REDIRECT(307, "Temporary Redirect"),

  /** The bad request. */
  BAD_REQUEST(400, "Bad Request"), /** The unauthorized. */
 UNAUTHORIZED(401, "Unauthorized"), /** The payment required. */
 PAYMENT_REQUIRED(402, "Payment Required"),
  
  /** The forbidden. */
  FORBIDDEN(
      403, "Forbidden"), 
 /** The not found. */
 NOT_FOUND(404, "Not Found"), 
 /** The method not allowed. */
 METHOD_NOT_ALLOWED(405, "Method Not Allowed"), 
 /** The not acceptable. */
 NOT_ACCEPTABLE(
      406, "Not Acceptable"), 
 /** The proxy authentication required. */
 PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"), 
 /** The request timeout. */
 REQUEST_TIMEOUT(408,
      "Request Timeout"), 
 /** The conflict. */
 CONFLICT(409, "Conflict"), 
 /** The gone. */
 GONE(410, "Gone"), 
 /** The length required. */
 LENGTH_REQUIRED(411, "Length Required"),
  
  /** The precondition failed. */
  PRECONDITION_FAILED(412, "Precondition Failed"), 
 /** The request entity too large. */
 REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
  
  /** The request uri too long. */
  REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"), 
 /** The unsupported media type. */
 UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
  
  /** The requested range not satisfiable. */
  REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
  
  /** The expectation failed. */
  EXPECTATION_FAILED(417, "Expectation Failed"),  
  /** The misdirected request. */
  MISDIRECTED_REQUEST(421, "Misdirected Request"),
  
  /** The unprocessable entity. */
  UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"), 
 /** The locked. */
 LOCKED(423, "Locked"),
  
  /** The failed dependency. */
  FAILED_DEPENDENCY(424, "Failed Dependency"),
  
  /** The precondition required. */
  PRECONDITION_REQUIRED(428, "Precondition Required"),
/** The too many requests. */
TOO_MANY_REQUESTS(429, "Too Many Requests"),
  
  /** The method failed. */
  METHOD_FAILED(420, "Method Failed"),
  
  /** The request header field too large. */
  REQUEST_HEADER_FIELD_TOO_LARGE(431, "Request Header Fields Too Large"),
  
  /** The unavailable for legal reasons. */
  UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),

  /** The internal server error. */
  INTERNAL_SERVER_ERROR(500, "Internal Server Error"), /** The not implemented. */
 NOT_IMPLEMENTED(501, "Not Implemented"), /** The bad gateway. */
 BAD_GATEWAY(502,
      "Bad Gateway"), 
 /** The service unavailable. */
 SERVICE_UNAVAILABLE(503, "Service Unavailable"), 
 /** The gateway timeout. */
 GATEWAY_TIMEOUT(504, "Gateway Timeout"),
  
  /** The http version not supported. */
  HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");

  /** The code. */
  private final int code;
  
  /** The info. */
  private final String info;

  /**
   * Instantiates a new http status codes.
   *
   * @param statusCode the status code
   * @param info the info
   */
  HttpStatusCodes(final int statusCode, final String info) {
    code = statusCode;
    this.info = info;
  }

  /**
   * Convert a numerical status code into the corresponding status enum object.
   * 
   * @param statusCode the numerical status code
   * @return the matching status enum object or null if no matching enum is defined
   */
  public static HttpStatusCodes fromStatusCode(final int statusCode) {
    for (final HttpStatusCodes s : HttpStatusCodes.values()) {
      if (s.code == statusCode) {
        return s;
      }
    }
    return null;
  }

  /**
   * Get the associated status code.
   * 
   * @return the status code.
   */
  public int getStatusCode() {
    return code;
  }

  /**
   * Get the status code info.
   * 
   * @return the status code info
   */
  public String getInfo() {
    return toString();
  }

  /**
   * Get the status code info.
   * 
   * @return the status code info
   */
  @Override
  public String toString() {
    return info;
  }

}
