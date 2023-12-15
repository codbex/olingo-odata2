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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.olingo.odata2.api.client.batch.BatchQueryPart;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchQueryPartImpl.
 */
public class BatchQueryPartImpl extends BatchQueryPart {
  
  /** The method. */
  private String method;
  
  /** The headers. */
  private Map<String, String> headers = new HashMap<String, String>();
  
  /** The uri. */
  private String uri;
  
  /** The content id. */
  private String contentId;
  
  /** The Constant GET. */
  private static final String GET = "GET";

  /**
   * Gets the headers.
   *
   * @return the headers
   */
  @Override
  public Map<String, String> getHeaders() {
    return Collections.unmodifiableMap(headers);
  }

  /**
   * Gets the method.
   *
   * @return the method
   */
  @Override
  public String getMethod() {
    return method;
  }

  /**
   * Gets the uri.
   *
   * @return the uri
   */
  @Override
  public String getUri() {
    return uri;
  }

  /**
   * Gets the content id.
   *
   * @return the content id
   */
  @Override
  public String getContentId() {
    return contentId;
  }

  /**
   * The Class BatchQueryRequestBuilderImpl.
   */
  public class BatchQueryRequestBuilderImpl extends BatchQueryPartBuilder {
    
    /** The method. */
    private String method;
    
    /** The headers. */
    private Map<String, String> headers = new HashMap<String, String>();
    
    /** The uri. */
    private String uri;
    
    /** The content id. */
    private String contentId;

    /**
     * Builds the.
     *
     * @return the batch query part
     */
    @Override
    public BatchQueryPart build() {
      if (method == null || uri == null) {
        throw new IllegalArgumentException();
      }
      BatchQueryPartImpl.this.method = method;
      BatchQueryPartImpl.this.headers = headers;
      BatchQueryPartImpl.this.uri = uri;
      BatchQueryPartImpl.this.contentId = contentId;
      return BatchQueryPartImpl.this;
    }

    /**
     * Headers.
     *
     * @param headers the headers
     * @return the batch query part builder
     */
    @Override
    public BatchQueryPartBuilder headers(final Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    /**
     * Uri.
     *
     * @param uri the uri
     * @return the batch query part builder
     */
    @Override
    public BatchQueryPartBuilder uri(final String uri) {
      this.uri = uri;
      return this;
    }

    /**
     * Method.
     *
     * @param method the method
     * @return the batch query part builder
     */
    @Override
    public BatchQueryPartBuilder method(final String method) {
      if (method != null && method.matches(GET)) {
        this.method = method;
      } else {
        throw new IllegalArgumentException();
      }
      return this;
    }

    /**
     * Content id.
     *
     * @param contentId the content id
     * @return the batch query part builder
     */
    @Override
    public BatchQueryPartBuilder contentId(final String contentId) {
      this.contentId = contentId;
      return this;
    }

  }

}
