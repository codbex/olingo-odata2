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
package org.apache.olingo.odata2.api.edm;

import org.apache.olingo.odata2.api.exception.MessageReference;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmLiteralException.
 *
 * @org.apache.olingo.odata2.DoNotImplement Exception for violation of the OData URI construction rules, resulting in a 400 Bad Request response
 */
public class EdmLiteralException extends EdmException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant NOTEXT. */
  public static final MessageReference NOTEXT = createMessageReference(EdmLiteralException.class, "NOTEXT");
  
  /** The Constant LITERALFORMAT. */
  public static final MessageReference LITERALFORMAT = createMessageReference(EdmLiteralException.class,
      "LITERALFORMAT");
  
  /** The Constant UNKNOWNLITERAL. */
  public static final MessageReference UNKNOWNLITERAL = createMessageReference(EdmLiteralException.class,
      "UNKNOWNLITERAL");

  /**
   * Instantiates a new edm literal exception.
   *
   * @param MessageReference the message reference
   */
  public EdmLiteralException(final MessageReference MessageReference) {
    super(MessageReference);
  }

  /**
   * Instantiates a new edm literal exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   */
  public EdmLiteralException(final MessageReference messageReference, final Throwable cause) {
    super(messageReference, cause);
  }

  /**
   * Instantiates a new edm literal exception.
   *
   * @param MessageReference the message reference
   * @param errorCode the error code
   */
  public EdmLiteralException(final MessageReference MessageReference, final String errorCode) {
    super(MessageReference, errorCode);
  }

  /**
   * Instantiates a new edm literal exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   * @param errorCode the error code
   */
  public EdmLiteralException(final MessageReference messageReference, final Throwable cause, final String errorCode) {
    super(messageReference, cause, errorCode);
  }
}
