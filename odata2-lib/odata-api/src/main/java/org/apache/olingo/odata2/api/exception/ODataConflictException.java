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
 * Exceptions of this class will result in a HTTP status 409 Conflict.
 */
public class ODataConflictException extends ODataHttpException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant COMMON. */
  public static final MessageReference COMMON = createMessageReference(ODataConflictException.class, "COMMON");

  /**
   * Instantiates a new o data conflict exception.
   *
   * @param context the context
   */
  public ODataConflictException(final MessageReference context) {
    super(context, HttpStatusCodes.CONFLICT);
  }

  /**
   * Instantiates a new o data conflict exception.
   *
   * @param context the context
   * @param cause the cause
   */
  public ODataConflictException(final MessageReference context, final Throwable cause) {
    super(context, cause, HttpStatusCodes.CONFLICT);
  }

  /**
   * Instantiates a new o data conflict exception.
   *
   * @param context the context
   * @param errorCode the error code
   */
  public ODataConflictException(final MessageReference context, final String errorCode) {
    super(context, HttpStatusCodes.CONFLICT, errorCode);
  }

  /**
   * Instantiates a new o data conflict exception.
   *
   * @param context the context
   * @param cause the cause
   * @param errorCode the error code
   */
  public ODataConflictException(final MessageReference context, final Throwable cause, final String errorCode) {
    super(context, cause, HttpStatusCodes.CONFLICT, errorCode);
  }

}
