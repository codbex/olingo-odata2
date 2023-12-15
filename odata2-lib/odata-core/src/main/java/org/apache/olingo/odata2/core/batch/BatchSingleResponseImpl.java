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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.olingo.odata2.api.client.batch.BatchSingleResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchSingleResponseImpl.
 */
public class BatchSingleResponseImpl implements BatchSingleResponse {

  /** The status code. */
  private String statusCode;
  
  /** The status info. */
  private String statusInfo;
  
  /** The body. */
  private String body;
  
  /** The headers. */
  private Map<String, String> headers = new HashMap<String, String>();
  
  /** The content id. */
  private String contentId;

  /**
   * Gets the status code.
   *
   * @return the status code
   */
  @Override
  public String getStatusCode() {
    return statusCode;
  }

  /**
   * Gets the status info.
   *
   * @return the status info
   */
  @Override
  public String getStatusInfo() {
    return statusInfo;
  }

  /**
   * Gets the body.
   *
   * @return the body
   */
  @Override
  public String getBody() {
    return body;
  }

  /**
   * Gets the headers.
   *
   * @return the headers
   */
  @Override
  public Map<String, String> getHeaders() {
    return headers;
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
   * Sets the status code.
   *
   * @param statusCode the new status code
   */
  public void setStatusCode(final String statusCode) {
    this.statusCode = statusCode;
  }

  /**
   * Sets the status info.
   *
   * @param statusInfo the new status info
   */
  public void setStatusInfo(final String statusInfo) {
    this.statusInfo = statusInfo;
  }

  /**
   * Sets the body.
   *
   * @param body the new body
   */
  public void setBody(final String body) {
    this.body = body;
  }

  /**
   * Sets the headers.
   *
   * @param headers the headers
   */
  public void setHeaders(final Map<String, String> headers) {
    this.headers = headers;
  }

  /**
   * Sets the content id.
   *
   * @param contentId the new content id
   */
  public void setContentId(final String contentId) {
    this.contentId = contentId;
  }

}
