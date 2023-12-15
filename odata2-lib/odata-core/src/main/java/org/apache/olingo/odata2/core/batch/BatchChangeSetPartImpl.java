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

import org.apache.olingo.odata2.api.client.batch.BatchChangeSetPart;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchChangeSetPartImpl.
 */
public class BatchChangeSetPartImpl extends BatchChangeSetPart {
  
  /** The method. */
  private String method;
  
  /** The headers. */
  private Map<String, String> headers = new HashMap<String, String>();
  
  /** The body. */
  private Object body;
  
  /** The uri. */
  private String uri;
  
  /** The cnt id. */
  private String cntId;
  
  /** The Constant CHANGE_METHODS. */
  private static final String CHANGE_METHODS = "(PUT|POST|DELETE|MERGE|PATCH)";

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
   * Gets the body.
   *
   * @return the body
   */
  @Override
  public String getBody() {
    return body.toString();
  }

  /**
   * Gets the body as bytes.
   *
   * @return the body as bytes
   */
  @Override
  public byte[] getBodyAsBytes() {
    if(body == null) {
      return new byte[0];
    }
    Charset charset = getCharset();
    if (body instanceof byte[]) {
      return (byte[]) body; //NOSONAR
    } else {
      return body.toString().getBytes(charset);
    }
  }

  /**
   * Gets the charset.
   *
   * @return the charset
   */
  private Charset getCharset() {
    return BatchHelper.extractCharset(this.headers);
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
    return cntId;
  }

  /**
   * The Class BatchChangeSetRequestBuilderImpl.
   */
  public class BatchChangeSetRequestBuilderImpl extends BatchChangeSetPartBuilder {
    
    /** The method. */
    private String method;
    
    /** The headers. */
    private Map<String, String> headers = new HashMap<String, String>();
    
    /** The body. */
    private Object body;
    
    /** The uri. */
    private String uri;
    
    /** The content id. */
    private String contentId;

    /**
     * Builds the.
     *
     * @return the batch change set part
     */
    @Override
    public BatchChangeSetPart build() {
      if (method == null || uri == null) {
        throw new IllegalArgumentException();
      }
      BatchChangeSetPartImpl.this.method = method;
      BatchChangeSetPartImpl.this.headers = headers;
      BatchChangeSetPartImpl.this.body = body;
      BatchChangeSetPartImpl.this.uri = uri;
      BatchChangeSetPartImpl.this.cntId = contentId;
      return BatchChangeSetPartImpl.this;
    }

    /**
     * Headers.
     *
     * @param headers the headers
     * @return the batch change set part builder
     */
    @Override
    public BatchChangeSetPartBuilder headers(final Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    /**
     * Body.
     *
     * @param body the body
     * @return the batch change set part builder
     */
    @Override
    public BatchChangeSetPartBuilder body(final String body) {
      this.body = body;
      return this;
    }
    
    /**
     * Body.
     *
     * @param body the body
     * @return the batch change set part builder
     */
    @Override
    public BatchChangeSetPartBuilder body(byte[] body) {
      this.body = body;
      return this;
    }

    /**
     * Uri.
     *
     * @param uri the uri
     * @return the batch change set part builder
     */
    @Override
    public BatchChangeSetPartBuilder uri(final String uri) {
      this.uri = uri;
      return this;
    }

    /**
     * Method.
     *
     * @param method the method
     * @return the batch change set part builder
     */
    @Override
    public BatchChangeSetPartBuilder method(final String method) {
      if (method != null && method.matches(CHANGE_METHODS)) {
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
     * @return the batch change set part builder
     */
    @Override
    public BatchChangeSetPartBuilder contentId(final String contentId) {
      this.contentId = contentId;
      return this;
    }

  }

}
