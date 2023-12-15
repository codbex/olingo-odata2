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
import org.apache.olingo.odata2.api.exception.ODataBadRequestException;

// TODO: Auto-generated Javadoc
/**
 * Exception for violation of the OData URI construction rules,
 * resulting in a 400 Bad Request response.
 * 
 */
public class UriSyntaxException extends ODataBadRequestException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant URISYNTAX. */
  public static final MessageReference URISYNTAX = createMessageReference(UriSyntaxException.class, "URISYNTAX");
  
  /** The Constant ENTITYSETINSTEADOFENTITY. */
  public static final MessageReference ENTITYSETINSTEADOFENTITY = createMessageReference(UriSyntaxException.class,
      "ENTITYSETINSTEADOFENTITY");
  
  /** The Constant NOTEXT. */
  public static final MessageReference NOTEXT = createMessageReference(UriSyntaxException.class, "NOTEXT");
  
  /** The Constant NOMEDIARESOURCE. */
  public static final MessageReference NOMEDIARESOURCE = createMessageReference(UriSyntaxException.class,
      "NOMEDIARESOURCE");
  
  /** The Constant NONAVIGATIONPROPERTY. */
  public static final MessageReference NONAVIGATIONPROPERTY = createMessageReference(UriSyntaxException.class,
      "NONAVIGATIONPROPERTY");
  
  /** The Constant MISSINGPARAMETER. */
  public static final MessageReference MISSINGPARAMETER = createMessageReference(UriSyntaxException.class,
      "MISSINGPARAMETER");
  
  /** The Constant MISSINGKEYPREDICATENAME. */
  public static final MessageReference MISSINGKEYPREDICATENAME = createMessageReference(UriSyntaxException.class,
      "MISSINGKEYPREDICATENAME");
  
  /** The Constant DUPLICATEKEYNAMES. */
  public static final MessageReference DUPLICATEKEYNAMES = createMessageReference(UriSyntaxException.class,
      "DUPLICATEKEYNAMES");
  
  /** The Constant DUPLICATESYSTEMQUERYPARAMETES. */
  public static final MessageReference DUPLICATESYSTEMQUERYPARAMETES = createMessageReference(UriSyntaxException.class,
      "DUPLICATESYSTEMQUERYPARAMETES");
  
  /** The Constant EMPTYSEGMENT. */
  public static final MessageReference EMPTYSEGMENT = createMessageReference(UriSyntaxException.class, "EMPTYSEGMENT");
  
  /** The Constant MUSTNOTBELASTSEGMENT. */
  public static final MessageReference MUSTNOTBELASTSEGMENT = createMessageReference(UriSyntaxException.class,
      "MUSTNOTBELASTSEGMENT");
  
  /** The Constant MUSTBELASTSEGMENT. */
  public static final MessageReference MUSTBELASTSEGMENT = createMessageReference(UriSyntaxException.class,
      "MUSTBELASTSEGMENT");
  
  /** The Constant INVALIDSEGMENT. */
  public static final MessageReference INVALIDSEGMENT = createMessageReference(UriSyntaxException.class,
      "INVALIDSEGMENT");
  
  /** The Constant INVALIDVALUE. */
  public static final MessageReference INVALIDVALUE = createMessageReference(UriSyntaxException.class, "INVALIDVALUE");
  
  /** The Constant INVALIDNULLVALUE. */
  public static final MessageReference INVALIDNULLVALUE = createMessageReference(UriSyntaxException.class,
      "INVALIDNULLVALUE");
  
  /** The Constant INVALIDNEGATIVEVALUE. */
  public static final MessageReference INVALIDNEGATIVEVALUE = createMessageReference(UriSyntaxException.class,
      "INVALIDNEGATIVEVALUE");
  
  /** The Constant INVALIDRETURNTYPE. */
  public static final MessageReference INVALIDRETURNTYPE = createMessageReference(UriSyntaxException.class,
      "INVALIDRETURNTYPE");
  
  /** The Constant INVALIDPROPERTYTYPE. */
  public static final MessageReference INVALIDPROPERTYTYPE = createMessageReference(UriSyntaxException.class,
      "INVALIDPROPERTYTYPE");
  
  /** The Constant INVALIDKEYPREDICATE. */
  public static final MessageReference INVALIDKEYPREDICATE = createMessageReference(UriSyntaxException.class,
      "INVALIDKEYPREDICATE");
  
  /** The Constant INVALIDSYSTEMQUERYOPTION. */
  public static final MessageReference INVALIDSYSTEMQUERYOPTION = createMessageReference(UriSyntaxException.class,
      "INVALIDSYSTEMQUERYOPTION");
  
  /** The Constant INVALIDFILTEREXPRESSION. */
  public static final MessageReference INVALIDFILTEREXPRESSION = createMessageReference(UriSyntaxException.class,
      "INVALIDFILTEREXPRESSION");
  
  /** The Constant INVALIDORDERBYEXPRESSION. */
  public static final MessageReference INVALIDORDERBYEXPRESSION = createMessageReference(UriSyntaxException.class,
      "INVALIDORDERBYEXPRESSION");
  
  /** The Constant LITERALFORMAT. */
  public static final MessageReference LITERALFORMAT =
      createMessageReference(UriSyntaxException.class, "LITERALFORMAT");
  
  /** The Constant UNKNOWNLITERAL. */
  public static final MessageReference UNKNOWNLITERAL = createMessageReference(UriSyntaxException.class,
      "UNKNOWNLITERAL");
  
  /** The Constant INCOMPATIBLELITERAL. */
  public static final MessageReference INCOMPATIBLELITERAL = createMessageReference(UriSyntaxException.class,
      "INCOMPATIBLELITERAL");
  
  /** The Constant INCOMPATIBLESYSTEMQUERYOPTION. */
  public static final MessageReference INCOMPATIBLESYSTEMQUERYOPTION = createMessageReference(UriSyntaxException.class,
      "INCOMPATIBLESYSTEMQUERYOPTION");

  /**
   * Instantiates a new uri syntax exception.
   *
   * @param MessageReference the message reference
   */
  public UriSyntaxException(final MessageReference MessageReference) {
    super(MessageReference);
  }

  /**
   * Instantiates a new uri syntax exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   */
  public UriSyntaxException(final MessageReference messageReference, final Throwable cause) {
    super(messageReference, cause);
  }

  /**
   * Instantiates a new uri syntax exception.
   *
   * @param MessageReference the message reference
   * @param errorCode the error code
   */
  public UriSyntaxException(final MessageReference MessageReference, final String errorCode) {
    super(MessageReference, errorCode);
  }

  /**
   * Instantiates a new uri syntax exception.
   *
   * @param messageReference the message reference
   * @param cause the cause
   * @param errorCode the error code
   */
  public UriSyntaxException(final MessageReference messageReference, final Throwable cause, final String errorCode) {
    super(messageReference, cause, errorCode);
  }
}
