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
package org.apache.olingo.odata2.api.client.batch;

import java.util.Map;

import org.apache.olingo.odata2.api.rt.RuntimeDelegate;

// TODO: Auto-generated Javadoc
/**
 * A BatchChangeSetPart
 * <p> BatchChangeSetPart represents a change request within a Change Set.
 */
public abstract class BatchChangeSetPart {

  /**
   * Gets the headers.
   *
   * @return the headers
   */
  public abstract Map<String, String> getHeaders();

  /**
   * Gets the body.
   *
   * @return the body
   */
  public abstract Object getBody();

  /**
   * Gets the body as bytes.
   *
   * @return the body as bytes
   */
  public abstract byte[] getBodyAsBytes();

  /**
   * Gets the uri.
   *
   * @return the uri
   */
  public abstract String getUri();

  /**
   * Gets the method.
   *
   * @return the method
   */
  public abstract String getMethod();

  /**
   * Gets the content id.
   *
   * @return the content id
   */
  public abstract String getContentId();

  /**
   * Headers.
   *
   * @param headers the headers
   * @return a new builder object
   */
  public static BatchChangeSetPartBuilder headers(final Map<String, String> headers) {
    return newBuilder().headers(headers);
  }

  /**
   * Body.
   *
   * @param body a change request body
   * @return a new builder object
   */
  public static BatchChangeSetPartBuilder body(final String body) {
    return newBuilder().body(body);
  }
  
  /**
   * Body.
   *
   * @param body a change request body
   * @return a new builder object
   */
  public static BatchChangeSetPartBuilder body(final byte[] body) {
    return newBuilder().body(body);
  }

  /**
   * Uri.
   *
   * @param uri should not be null
   * @return a new builder object
   */
  public static BatchChangeSetPartBuilder uri(final String uri) {
    return newBuilder().uri(uri);
  }

  /**
   * Method.
   *
   * @param method MUST be the PUT, POST, MERGE, DELETE or PATCH method
   * @return a new builder object
   */
  public static BatchChangeSetPartBuilder method(final String method) {
    return newBuilder().method(method);
  }

  /**
   * Content id.
   *
   * @param contentId can be used to identify the different request within a the batch
   * @return a new builder object
   */
  public static BatchChangeSetPartBuilder contentId(final String contentId) {
    return newBuilder().contentId(contentId);
  }

  /**
   * New builder.
   *
   * @return returns a new builder object
   */
  public static BatchChangeSetPartBuilder newBuilder() {
    return BatchChangeSetPartBuilder.newInstance();
  }

  /**
   * The Class BatchChangeSetPartBuilder.
   */
  public static abstract class BatchChangeSetPartBuilder {

    /**
     * Instantiates a new batch change set part builder.
     */
    protected BatchChangeSetPartBuilder() {}

    /**
     * New instance.
     *
     * @return the batch change set part builder
     */
    private static BatchChangeSetPartBuilder newInstance() {
      return RuntimeDelegate.createBatchChangeSetPartBuilder();
    }

    /**
     * Builds the.
     *
     * @return the batch change set part
     */
    public abstract BatchChangeSetPart build();

    /**
     * Headers.
     *
     * @param headers the headers
     * @return the batch change set part builder
     */
    public abstract BatchChangeSetPartBuilder headers(Map<String, String> headers);

    /**
     * Body.
     *
     * @param body the body
     * @return the batch change set part builder
     */
    public abstract BatchChangeSetPartBuilder body(String body);
    
    /**
     * Body.
     *
     * @param body the body
     * @return the batch change set part builder
     */
    public abstract BatchChangeSetPartBuilder body(byte[] body);

    /**
     * Uri.
     *
     * @param uri the uri
     * @return the batch change set part builder
     */
    public abstract BatchChangeSetPartBuilder uri(String uri);

    /**
     * Method.
     *
     * @param method the method
     * @return the batch change set part builder
     */
    public abstract BatchChangeSetPartBuilder method(String method);

    /**
     * Content id.
     *
     * @param contentId the content id
     * @return the batch change set part builder
     */
    public abstract BatchChangeSetPartBuilder contentId(String contentId);

  }

}
