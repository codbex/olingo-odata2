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
package org.apache.olingo.odata2.core;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.api.uri.PathSegment;

// TODO: Auto-generated Javadoc
/**
 * The Class PathInfoImpl.
 */
public class PathInfoImpl implements PathInfo {

  /** The preceding path segment. */
  private List<PathSegment> precedingPathSegment = Collections.emptyList();
  
  /** The odata path segment. */
  private List<PathSegment> odataPathSegment = Collections.emptyList();
  
  /** The service root. */
  private URI serviceRoot;
  
  /** The request uri. */
  private URI requestUri;

  /**
   * Sets the o data path segment.
   *
   * @param odataPathSegment the new o data path segment
   */
  public void setODataPathSegment(final List<PathSegment> odataPathSegment) {
    this.odataPathSegment = odataPathSegment;
  }

  /**
   * Sets the preceding path segment.
   *
   * @param precedingPathSegment the new preceding path segment
   */
  public void setPrecedingPathSegment(final List<PathSegment> precedingPathSegment) {
    this.precedingPathSegment = precedingPathSegment;
  }

  /**
   * Sets the service root.
   *
   * @param uri the new service root
   */
  public void setServiceRoot(final URI uri) {
    serviceRoot = uri;
  }

  /**
   * Gets the preceding segments.
   *
   * @return the preceding segments
   */
  @Override
  public List<PathSegment> getPrecedingSegments() {
    return Collections.unmodifiableList(precedingPathSegment);
  }

  /**
   * Gets the o data segments.
   *
   * @return the o data segments
   */
  @Override
  public List<PathSegment> getODataSegments() {
    return Collections.unmodifiableList(odataPathSegment);
  }

  /**
   * Gets the service root.
   *
   * @return the service root
   */
  @Override
  public URI getServiceRoot() {
    return serviceRoot;
  }

  /**
   * Gets the request uri.
   *
   * @return the request uri
   */
  @Override
  public URI getRequestUri() {
    return requestUri;
  }

  /**
   * Sets the request uri.
   *
   * @param requestUri the new request uri
   */
  public void setRequestUri(final URI requestUri) {
    this.requestUri = requestUri;
  }
}
