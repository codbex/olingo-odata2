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
package org.apache.olingo.odata2.api.uri;

import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * URI-parsing exception resulting in a 404 Not Found response.
 * 
 */
public class UriNotMatchingException extends ODataNotFoundException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant MATCHPROBLEM. */
  public static final MessageReference MATCHPROBLEM = createMessageReference(UriNotMatchingException.class,
      "MATCHPROBLEM");
  
  /** The Constant NOTFOUND. */
  public static final MessageReference NOTFOUND = createMessageReference(UriNotMatchingException.class, "NOTFOUND");
  
  /** The Constant CONTAINERNOTFOUND. */
  public static final MessageReference CONTAINERNOTFOUND = createMessageReference(UriNotMatchingException.class,
      "CONTAINERNOTFOUND");
  
  /** The Constant ENTITYNOTFOUND. */
  public static final MessageReference ENTITYNOTFOUND = createMessageReference(UriNotMatchingException.class,
      "ENTITYNOTFOUND");
  
  /** The Constant PROPERTYNOTFOUND. */
  public static final MessageReference PROPERTYNOTFOUND = createMessageReference(UriNotMatchingException.class,
      "PROPERTYNOTFOUND");

  /**
   * Instantiates a new uri not matching exception.
   *
   * @param messageReference the message reference
   */
  public UriNotMatchingException(final MessageReference messageReference) {
    super(messageReference);
  }

  /**
   * Instantiates a new uri not matching exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   */
  public UriNotMatchingException(final MessageReference messageReference, final Throwable cause) {
    super(messageReference, cause);
  }

  /**
   * Instantiates a new uri not matching exception.
   *
   * @param messageReference the message reference
   * @param errorCode the error code
   */
  public UriNotMatchingException(final MessageReference messageReference, final String errorCode) {
    super(messageReference, errorCode);
  }

  /**
   * Instantiates a new uri not matching exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   * @param errorCode the error code
   */
  public UriNotMatchingException(final MessageReference messageReference, final Throwable cause,
      final String errorCode) {
    super(messageReference, cause, errorCode);
  }
}
