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

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.rt.RuntimeDelegate;
import org.apache.olingo.odata2.api.uri.PathInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataRequest.
 */
public abstract class ODataRequest {

  /**
   * Gets the request header value.
   *
   * @param name the name
   * @return the request header value
   */
  public abstract String getRequestHeaderValue(String name);

  /**
   * Gets the request headers.
   *
   * @return the request headers
   */
  public abstract Map<String, List<String>> getRequestHeaders();

  /**
   * Gets the body.
   *
   * @return the body
   */
  public abstract InputStream getBody();

  /**
   * Gets the path info.
   *
   * @return the path info
   */
  public abstract PathInfo getPathInfo();

  /**
   * Gets the method.
   *
   * @return the method
   */
  public abstract ODataHttpMethod getMethod();

  /**
   * Gets the http method.
   *
   * @return the http method
   */
  public abstract String getHttpMethod();

  /**
   * Gets the acceptable languages.
   *
   * @return the acceptable languages
   */
  public abstract List<Locale> getAcceptableLanguages();

  /**
   * Gets the content type.
   *
   * @return the content type
   */
  public abstract String getContentType();

  /**
   * Gets the accept headers.
   *
   * @return the accept headers
   */
  public abstract List<String> getAcceptHeaders();

  /**
   * Gets the query parameters.
   *
   * @return the query parameters
   */
  public abstract Map<String, String> getQueryParameters();
  
  /**
   * Gets the all query parameters.
   *
   * @return the all query parameters
   */
  public abstract Map<String, List<String>> getAllQueryParameters();
  
  /**
   * Request headers.
   *
   * @param headers the headers
   * @return the o data request builder
   */
  public static ODataRequestBuilder requestHeaders(final Map<String, List<String>> headers) {
    return newBuilder().requestHeaders(headers);
  }

  /**
   * Body.
   *
   * @param body the body
   * @return the o data request builder
   */
  public static ODataRequestBuilder body(final InputStream body) {
    return newBuilder().body(body);
  }

  /**
   * Path info.
   *
   * @param pathInfo the path info
   * @return the o data request builder
   */
  public static ODataRequestBuilder pathInfo(final PathInfo pathInfo) {
    return newBuilder().pathInfo(pathInfo);
  }

  /**
   * Method.
   *
   * @param method the method
   * @return the o data request builder
   */
  public static ODataRequestBuilder method(final ODataHttpMethod method) {
    return newBuilder().method(method);
  }

  /**
   * Acceptable languages.
   *
   * @param acceptableLanguages the acceptable languages
   * @return the o data request builder
   */
  public static ODataRequestBuilder acceptableLanguages(final List<Locale> acceptableLanguages) {
    return newBuilder().acceptableLanguages(acceptableLanguages);
  }

  /**
   * Content type.
   *
   * @param contentType the content type
   * @return the o data request builder
   */
  public static ODataRequestBuilder contentType(final String contentType) {
    return newBuilder().contentType(contentType);
  }

  /**
   * Accept headers.
   *
   * @param acceptHeaders the accept headers
   * @return the o data request builder
   */
  public static ODataRequestBuilder acceptHeaders(final List<String> acceptHeaders) {
    return newBuilder().acceptHeaders(acceptHeaders);
  }

  /**
   * Query parameters.
   *
   * @param queryParameters the query parameters
   * @return the o data request builder
   */
  public static ODataRequestBuilder queryParameters(final Map<String, String> queryParameters) {
    return newBuilder().queryParameters(queryParameters);
  }
  
  /**
   * All query parameters.
   *
   * @param allQueryParameters the all query parameters
   * @return the o data request builder
   */
  public static ODataRequestBuilder allQueryParameters(final Map<String, List<String>> allQueryParameters) {
    return newBuilder().allQueryParameters(allQueryParameters);
  }
  
  /**
   * From request.
   *
   * @param request the request
   * @return the o data request builder
   */
  public static ODataRequestBuilder fromRequest(final ODataRequest request) {
    return newBuilder().fromRequest(request);
  }

  /**
   * New builder.
   *
   * @return returns a new builder object
   */
  public static ODataRequestBuilder newBuilder() {
    return ODataRequestBuilder.newInstance();
  }

  /**
   * The Class ODataRequestBuilder.
   */
  public static abstract class ODataRequestBuilder {

    /**
     * Instantiates a new o data request builder.
     */
    protected ODataRequestBuilder() {}

    /**
     * New instance.
     *
     * @return the o data request builder
     */
    private static ODataRequestBuilder newInstance() {
      return RuntimeDelegate.createODataRequestBuilder();
    }

    /**
     * Builds the.
     *
     * @return the o data request
     */
    public abstract ODataRequest build();

    /**
     * Request headers.
     *
     * @param headers the headers
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder requestHeaders(Map<String, List<String>> headers);

    /**
     * Http method.
     *
     * @param httpMethod the http method
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder httpMethod(String httpMethod);

    /**
     * Body.
     *
     * @param body the body
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder body(InputStream body);

    /**
     * Path info.
     *
     * @param pathInfo the path info
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder pathInfo(PathInfo pathInfo);

    /**
     * Method.
     *
     * @param method the method
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder method(ODataHttpMethod method);

    /**
     * Acceptable languages.
     *
     * @param acceptableLanguages the acceptable languages
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder acceptableLanguages(List<Locale> acceptableLanguages);

    /**
     * Content type.
     *
     * @param contentType the content type
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder contentType(String contentType);

    /**
     * Accept headers.
     *
     * @param acceptHeaders the accept headers
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder acceptHeaders(List<String> acceptHeaders);

    /**
     * Query parameters.
     *
     * @param queryParameters the query parameters
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder queryParameters(Map<String, String> queryParameters);
    
    /**
     * All query parameters.
     *
     * @param queryParameters the query parameters
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder allQueryParameters(Map<String, List<String>> queryParameters);
    
    /**
     * From request.
     *
     * @param request the request
     * @return the o data request builder
     */
    public abstract ODataRequestBuilder fromRequest(ODataRequest request);

  }

}
