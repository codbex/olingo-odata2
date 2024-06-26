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
package org.apache.olingo.odata2.api.processor;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.uri.PathInfo;

// TODO: Auto-generated Javadoc
/**
 * Error context information bean. Usually created and in error situations.
 * @see org.apache.olingo.odata2.api.ep.EntityProvider EntityProvider
 * @see ODataErrorCallback
 * 
 */
public class ODataErrorContext {

  /** The content type. */
  // General purpose attributes
  private String contentType;
  
  /** The http status. */
  private HttpStatusCodes httpStatus;
  
  /** The exception. */
  private Exception exception;
  
  /** The request headers. */
  private Map<String, List<String>> requestHeaders;
  
  /** The path info. */
  private PathInfo pathInfo;
  
  /** The request uri. */
  private URI requestUri;

  /** The error code. */
  // Standard OData Error Response
  private String errorCode;
  
  /** The locale. */
  private Locale locale;
  
  /** The message. */
  private String message;
  
  /** The inner error. */
  private String innerError;

  /** The severity. */
  // Extended OData Error Response
  private String severity;
  
  /** The target. */
  private String target;
  
  /** The error details. */
  private Collection<ODataErrorContext> errorDetails = new ArrayList<ODataErrorContext>();

  /**
   * create a new context object.
   */
  public ODataErrorContext() {
    requestHeaders = new HashMap<String, List<String>>();
  }

  /**
   * Returns the causing exception.
   * @return exception object
   */
  public Exception getException() {
    return exception;
  }

  /**
   * Set the causing exception.
   * @param exception exception object
   */
  public void setException(final Exception exception) {
    this.exception = exception;
  }

  /**
   * Get the content type which should be used to serialize an error response.
   * @return a content type
   */
  public String getContentType() {
    return contentType;
  }

  /**
   * Set content type which should be used to serialize an error response.
   * @param contentType a content type
   */
  public void setContentType(final String contentType) {
    this.contentType = contentType;
  }

  /**
   * Get the status code which will be returned in error response.
   * @return http status code
   */
  public HttpStatusCodes getHttpStatus() {
    return httpStatus;
  }

  /**
   * Set http status code that should be returned in error response.
   * @param status http status code
   */
  public void setHttpStatus(final HttpStatusCodes status) {
    httpStatus = status;
  }

  /**
   * Return OData error code that is returned in error response.
   * @return an application defined error code
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * Set OData error code that should be returned in error response.
   * @param errorCode an application defined error code
   */
  public void setErrorCode(final String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * Return OData severity that is returned in error response.
   * @return an application defined severity
   */
  public String getSeverity() {
	return severity;
}
  
  /**
   * Set OData severity that should be returned in error response.
   * @param severity an application defined severity
   */
  public void setSeverity(String severity) {
    this.severity = severity;
  }

/**
   * Return a translated error message.
   * @return translated message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Set a translated message.
   * @param message translated message
   */
  public void setMessage(final String message) {
    this.message = message;
  }

  /**
   * Return the locale of the translated message.
   * @return a locale
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * Set the locale for a translated message.
   * @param locale a locale
   */
  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  /**
   * Put http headers to be returned in error response.
   * @param key header name
   * @param value list of header values
   */
  public void putRequestHeader(final String key, final List<String> value) {
    requestHeaders.put(key, value);
  }

  /**
   * Return a map of http headers to be returned in error response.
   * @return a map of http headers
   */
  public Map<String, List<String>> getRequestHeaders() {
    return Collections.unmodifiableMap(requestHeaders);
  }

  /**
   * Get the list of values for a request header.
   * @param name header name
   * @return list of values
   */
  public List<String> getRequestHeader(final String name) {
    return requestHeaders.get(name);
  }

  /**
   * Set the request uri to be used in a error response.
   * @param requestUri a uri object
   */
  public void setRequestUri(final URI requestUri) {
    this.requestUri = requestUri;
  }

  /**
   * Get the request uri to be used in an error response. Might be null in case the URI was the cause of the exception.
   * @return a uri object
   */
  public URI getRequestUri() {
    return requestUri;
  }

  /**
   * Get a string for a OData inner error to be returned in error response.
   * @return a inner error message
   */
  public String getInnerError() {
    return innerError;
  }

  /**
   * Set a string for a OData inner error to be returned in error response.
   * @param innerError a inner error message
   */
  public void setInnerError(final String innerError) {
    this.innerError = innerError;
  }

  /**
   * Get a collection of detailed errors for a OData inner error to be returned in error response.
   * @return a inner error message
   */
  public Collection<ODataErrorContext> getErrorDetails() {
    return errorDetails;
  }

  /**
   * Get a target of detailed error for a OData inner error to be returned in error response.
   * @return a target
   */
  public String getTarget() {
    return target;
 }

  /**
   * Set a target for a OData detailed error to be returned in error response.
   * @param target a target
   */
  public void setTarget(String target) {
    this.target = target;
  }

/**
   * Set a collection of detailed errors for a OData inner error to be returned in error response.
   * If set, will overwrite the <code>innerError</code> element.
   * @param errorDetails a inner error message
   */
  public void setErrorDetails(Collection<ODataErrorContext> errorDetails) {
    this.errorDetails = errorDetails;
  }

  /**
   * Get {@link PathInfo} object.
   * May be <code>NULL</code> if no path info was created/set till error occurred (but may be over written by
   * application).
   * 
   * @return {@link PathInfo} or <code>NULL</code>.
   */
  public PathInfo getPathInfo() {
    return pathInfo;
  }

  /**
   * Set {@link PathInfo} object.
   * 
   * @param pathInfo path info
   */
  public void setPathInfo(final PathInfo pathInfo) {
    this.pathInfo = pathInfo;
  }
}
