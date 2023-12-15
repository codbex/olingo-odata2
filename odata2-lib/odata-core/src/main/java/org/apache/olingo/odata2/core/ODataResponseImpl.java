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

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.processor.ODataResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataResponseImpl.
 */
public class ODataResponseImpl extends ODataResponse {

  /** The status. */
  private HttpStatusCodes status;
  
  /** The entity. */
  private Object entity;
  
  /** The headers. */
  private HashMap<String, String> headers;

  /**
   * Gets the status.
   *
   * @return the status
   */
  @Override
  public HttpStatusCodes getStatus() {
    return status;
  }

  /**
   * Gets the entity.
   *
   * @return the entity
   */
  @Override
  public Object getEntity() {
    // TODO: check type of entity??
    // current default (defined by test cases is String)
    return entity;
  }

  /**
   * Close.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void close() throws IOException {
    if (entity != null && entity instanceof Closeable) {
      Closeable closeableEntity = (Closeable) entity;
      closeableEntity.close();
    }
  }

  /**
   * Gets the header.
   *
   * @param name the name
   * @return the header
   */
  @Override
  public String getHeader(final String name) {
    return headers.get(name);
  }

  /**
   * Gets the header names.
   *
   * @return the header names
   */
  @Override
  public Set<String> getHeaderNames() {
    return headers.keySet();
  }

  /**
   * Gets the id literal.
   *
   * @return the id literal
   */
  @Override
  public String getIdLiteral() {
    return headers.get(HttpHeaders.LOCATION);
  }

  /**
   * Gets the e tag.
   *
   * @return the e tag
   */
  @Override
  public String getETag() {
    return headers.get(HttpHeaders.ETAG);
  }

  /**
   * Gets the content header.
   *
   * @return the content header
   */
  @Override
  public String getContentHeader() {
    return headers.get(HttpHeaders.CONTENT_TYPE);
  }

  /**
   * Contains header.
   *
   * @param header the header
   * @return true, if successful
   */
  @Override
  public boolean containsHeader(final String header) {
    boolean contains = false;
    for (String containedHeader : headers.keySet()) {
      if (containedHeader.equalsIgnoreCase(header)) {
        contains = true;
        break;
      }
    }
    return contains;
  }

  /**
   * The Class ODataResponseBuilderImpl.
   */
  public class ODataResponseBuilderImpl extends ODataResponseBuilder {
    
    /** The status. */
    private HttpStatusCodes status;
    
    /** The entity. */
    private Object entity;
    
    /** The headers. */
    private HashMap<String, String> headers = new HashMap<String, String>();

    /**
     * Builds the.
     *
     * @return the o data response
     */
    @Override
    public ODataResponse build() {
      ODataResponseImpl.this.status = status;
      ODataResponseImpl.this.entity = entity;
      ODataResponseImpl.this.headers = headers;

      return ODataResponseImpl.this;
    }

    /**
     * Status.
     *
     * @param status the status
     * @return the o data response builder
     */
    @Override
    public ODataResponseBuilder status(final HttpStatusCodes status) {
      this.status = status;
      return this;
    }

    /**
     * Entity.
     *
     * @param entity the entity
     * @return the o data response builder
     */
    @Override
    public ODataResponseBuilder entity(final Object entity) {
      this.entity = entity;
      return this;
    }

    /**
     * Header.
     *
     * @param name the name
     * @param value the value
     * @return the o data response builder
     */
    @Override
    public ODataResponseBuilder header(final String name, final String value) {
      if (value == null) {
        headers.remove(name);
      } else {
        headers.put(name, value);
      }

      return this;
    }

    /**
     * Id literal.
     *
     * @param idLiteral the id literal
     * @return the o data response builder
     */
    @Override
    public ODataResponseBuilder idLiteral(final String idLiteral) {
      return header(HttpHeaders.LOCATION, idLiteral);
    }

    /**
     * E tag.
     *
     * @param eTag the e tag
     * @return the o data response builder
     */
    @Override
    public ODataResponseBuilder eTag(final String eTag) {
      return header(HttpHeaders.ETAG, eTag);
    }

    /**
     * Content header.
     *
     * @param value the value
     * @return the o data response builder
     */
    @Override
    public ODataResponseBuilder contentHeader(final String value) {
      return header(HttpHeaders.CONTENT_TYPE, value);
    }

    /**
     * From response.
     *
     * @param response the response
     * @return the o data response builder
     */
    @Override
    protected ODataResponseBuilder fromResponse(final ODataResponse response) {
      status = response.getStatus();
      entity = response.getEntity();

      headers = new HashMap<String, String>();
      for (String key : response.getHeaderNames()) {
        headers.put(key, response.getHeader(key));
      }

      return this;
    }

  }
}
