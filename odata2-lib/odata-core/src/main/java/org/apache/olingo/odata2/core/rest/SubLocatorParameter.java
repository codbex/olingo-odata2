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
package org.apache.olingo.odata2.core.rest;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Request;

import org.apache.olingo.odata2.api.ODataServiceFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SubLocatorParameter.
 */
public class SubLocatorParameter {

  /** The path segments. */
  private List<jakarta.ws.rs.core.PathSegment> pathSegments;
  
  /** The http headers. */
  private jakarta.ws.rs.core.HttpHeaders httpHeaders;
  
  /** The uri info. */
  private jakarta.ws.rs.core.UriInfo uriInfo;
  
  /** The request. */
  private Request request;
  
  /** The path split. */
  private int pathSplit;
  
  /** The service factory. */
  private ODataServiceFactory serviceFactory;
  
  /** The servlet request. */
  private HttpServletRequest servletRequest;

  /**
   * Gets the service factory.
   *
   * @return the service factory
   */
  public ODataServiceFactory getServiceFactory() {
    return serviceFactory;
  }

  /**
   * Sets the service factory.
   *
   * @param serviceFactory the new service factory
   */
  public void setServiceFactory(final ODataServiceFactory serviceFactory) {
    this.serviceFactory = serviceFactory;
  }

  /**
   * Gets the path segments.
   *
   * @return the path segments
   */
  public List<jakarta.ws.rs.core.PathSegment> getPathSegments() {
    return pathSegments;
  }

  /**
   * Sets the path segments.
   *
   * @param pathSegments the new path segments
   */
  public void setPathSegments(final List<jakarta.ws.rs.core.PathSegment> pathSegments) {
    this.pathSegments = pathSegments;
  }

  /**
   * Gets the http headers.
   *
   * @return the http headers
   */
  public jakarta.ws.rs.core.HttpHeaders getHttpHeaders() {
    return httpHeaders;
  }

  /**
   * Sets the http headers.
   *
   * @param httpHeaders the new http headers
   */
  public void setHttpHeaders(final jakarta.ws.rs.core.HttpHeaders httpHeaders) {
    this.httpHeaders = httpHeaders;
  }

  /**
   * Gets the uri info.
   *
   * @return the uri info
   */
  public jakarta.ws.rs.core.UriInfo getUriInfo() {
    return uriInfo;
  }

  /**
   * Sets the uri info.
   *
   * @param uriInfo the new uri info
   */
  public void setUriInfo(final jakarta.ws.rs.core.UriInfo uriInfo) {
    this.uriInfo = uriInfo;
  }

  /**
   * Gets the request.
   *
   * @return the request
   */
  public Request getRequest() {
    return request;
  }

  /**
   * Sets the request.
   *
   * @param request the new request
   */
  public void setRequest(final Request request) {
    this.request = request;
  }

  /**
   * Gets the path split.
   *
   * @return the path split
   */
  public int getPathSplit() {
    return pathSplit;
  }

  /**
   * Sets the path split.
   *
   * @param pathSplit the new path split
   */
  public void setPathSplit(final int pathSplit) {
    this.pathSplit = pathSplit;
  }

  /**
   * Sets the servlet request.
   *
   * @param servletRequest the new servlet request
   */
  public void setServletRequest(final HttpServletRequest servletRequest) {
    this.servletRequest = servletRequest;
  }

  /**
   * Gets the servlet request.
   *
   * @return the servlet request
   */
  public HttpServletRequest getServletRequest() {
    return servletRequest;
  }
}
