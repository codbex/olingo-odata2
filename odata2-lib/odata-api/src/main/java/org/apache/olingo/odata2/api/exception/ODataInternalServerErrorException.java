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
 * The Class ODataInternalServerErrorException.
 */
public class ODataInternalServerErrorException extends ODataHttpException {
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant NOSERVICE. */
  public static final MessageReference NOSERVICE = createMessageReference(ODataInternalServerErrorException.class,
      "NOSERVICE");

  /**
   * Instantiates a new o data internal server error exception.
   *
   * @param messageReference the message reference
   */
  public ODataInternalServerErrorException(final MessageReference messageReference) {
    super(messageReference, HttpStatusCodes.INTERNAL_SERVER_ERROR);
  }

  /**
   * Instantiates a new o data internal server error exception.
   *
   * @param messageReference the message reference
   * @param errorCode the error code
   */
  public ODataInternalServerErrorException(final MessageReference messageReference, final String errorCode) {
    super(messageReference, HttpStatusCodes.INTERNAL_SERVER_ERROR, errorCode);
  }

  /**
   * Instantiates a new o data internal server error exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   */
  public ODataInternalServerErrorException(final MessageReference messageReference, final Throwable cause) {
    super(messageReference, cause, HttpStatusCodes.INTERNAL_SERVER_ERROR);
  }

  /**
   * Instantiates a new o data internal server error exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   * @param errorCode the error code
   */
  public ODataInternalServerErrorException(final MessageReference messageReference, final Throwable cause,
      final String errorCode) {
    super(messageReference, cause, HttpStatusCodes.INTERNAL_SERVER_ERROR, errorCode);
  }

}
