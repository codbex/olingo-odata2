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
package org.apache.olingo.odata2.api.exception;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;

// TODO: Auto-generated Javadoc
/**
 * {@link ODataMessageException} with a HTTP status code.
 * 
 */
public abstract class ODataHttpException extends ODataMessageException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The http status. */
  protected final HttpStatusCodes httpStatus;

  /** The Constant COMMON. */
  public static final MessageReference COMMON = createMessageReference(ODataHttpException.class, "COMMON");

  /**
   * Instantiates a new o data http exception.
   *
   * @param messageReference the message reference
   * @param httpStatus the http status
   */
  public ODataHttpException(final MessageReference messageReference, final HttpStatusCodes httpStatus) {
    this(messageReference, null, httpStatus);
  }

  /**
   * Instantiates a new o data http exception.
   *
   * @param messageReference the message reference
   * @param httpStatus the http status
   * @param errorCode the error code
   */
  public ODataHttpException(final MessageReference messageReference, final HttpStatusCodes httpStatus,
      final String errorCode) {
    this(messageReference, null, httpStatus, errorCode);
  }

  /**
   * Instantiates a new o data http exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   * @param httpStatus the http status
   */
  public ODataHttpException(final MessageReference messageReference, final Throwable cause,
      final HttpStatusCodes httpStatus) {
    this(messageReference, cause, httpStatus, null);
  }

  /**
   * Instantiates a new o data http exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   * @param httpStatus the http status
   * @param errorCode the error code
   */
  public ODataHttpException(final MessageReference messageReference, final Throwable cause,
      final HttpStatusCodes httpStatus, final String errorCode) {
    super(messageReference, cause, errorCode);
    this.httpStatus = httpStatus;
  }

  /**
   * Gets the http status.
   *
   * @return the http status
   */
  public HttpStatusCodes getHttpStatus() {
    return httpStatus;
  }
}
