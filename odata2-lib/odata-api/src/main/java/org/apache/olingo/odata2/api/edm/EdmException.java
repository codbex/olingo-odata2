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
import org.apache.olingo.odata2.api.exception.ODataMessageException;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmException.
 *
 * @org.apache.olingo.odata2.DoNotImplement An exception for problems regarding the Entity Data Model.
 */
public class EdmException extends ODataMessageException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant COMMON. */
  public static final MessageReference COMMON = createMessageReference(EdmException.class, "COMMON");
  
  /** The Constant PROVIDERPROBLEM. */
  public static final MessageReference PROVIDERPROBLEM = createMessageReference(EdmException.class, "PROVIDERPROBLEM");
  
  /** The Constant PROPERTYNOTFOUND. */
  public static final MessageReference PROPERTYNOTFOUND =
      createMessageReference(EdmException.class, "PROPERTYNOTFOUND");
  
  /** The Constant NAVIGATIONPROPERTYNOTFOUND. */
  public static final MessageReference NAVIGATIONPROPERTYNOTFOUND =
      createMessageReference(EdmException.class, "NAVIGATIONPROPERTYNOTFOUND");
  
  /** The Constant MUSTBENAVIGATIONPROPERTY. */
  public static final MessageReference MUSTBENAVIGATIONPROPERTY =
      createMessageReference(EdmException.class, "MUSTBENAVIGATIONPROPERTY");
  
  /** The Constant MUSTBEPROPERTY. */
  public static final MessageReference MUSTBEPROPERTY = createMessageReference(EdmException.class, "MUSTBEPROPERTY");
  
  /** The Constant NAMINGERROR. */
  public static final MessageReference NAMINGERROR =
      createMessageReference(EdmException.class, "NAMINGERROR");
  
  /** The Constant TYPEPROBLEM. */
  public static final MessageReference TYPEPROBLEM = createMessageReference(EdmException.class, "TYPEPROBLEM");
  
  /** The Constant ENTITYTYPEPROBLEM. */
  public static final MessageReference ENTITYTYPEPROBLEM = 
      createMessageReference(EdmException.class, "ENTITYTYPEPROBLEM");
  
  /** The Constant ASSOCIATIONNOTFOUND. */
  public static final MessageReference ASSOCIATIONNOTFOUND =
      createMessageReference(EdmException.class, "ASSOCIATIONNOTFOUND");
  
  /**
   * Instantiates a new edm exception.
   *
   * @param messageReference the message reference
   */
  public EdmException(final MessageReference messageReference) {
    super(messageReference);
  }

  /**
   * Instantiates a new edm exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   */
  public EdmException(final MessageReference messageReference, final Throwable cause) {
    super(messageReference, cause);
  }

  /**
   * Instantiates a new edm exception.
   *
   * @param messageReference the message reference
   * @param errorCode the error code
   */
  public EdmException(final MessageReference messageReference, final String errorCode) {
    super(messageReference, errorCode);
  }

  /**
   * Instantiates a new edm exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   * @param errorCode the error code
   */
  public EdmException(final MessageReference messageReference, final Throwable cause, final String errorCode) {
    super(messageReference, cause, errorCode);
  }

}
