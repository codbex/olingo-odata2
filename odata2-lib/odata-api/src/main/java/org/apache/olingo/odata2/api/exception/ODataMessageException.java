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

import org.apache.olingo.odata2.api.rt.RuntimeDelegate;

// TODO: Auto-generated Javadoc
/**
 * <p>DO NOT EXTEND THIS EXCEPTION</p>
 * APPLICATION DEVELOPERS: please use {@link ODataApplicationException} o throw custom exceptions.
 * <p>Base exception class for all exceptions in the <code>OData</code> library.
 * This class extends {@link ODataException} with a message that will be displayed
 * to a possible client and therefore needs support for internationalization.
 * <br>To support internationalization and translation of messages, this class
 * and its sub classes contain a {@link MessageReference} object which can be
 * mapped to a related key and message text in the resource bundles.
 * 
 */
public abstract class ODataMessageException extends ODataException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 42L;

  /**  Message reference for exception which is used for internationalization. */
  protected final MessageReference messageReference;
  
  /**  OData error code. */
  protected final String errorCode;

  /**  Reference to common message for a {@link ODataMessageException}. */
  public static final MessageReference COMMON = createMessageReference(ODataMessageException.class, "COMMON");

  /**
   * Creates {@link ODataMessageException} with given {@link MessageReference}.
   * @param messageReference references the message text (and additional values)
   * of this {@link ODataMessageException}
   */
  public ODataMessageException(final MessageReference messageReference) {
    this(messageReference, null, null);
  }

  /**
   * Creates {@link ODataMessageException} with given {@link MessageReference} and cause {@link Throwable} which caused
   * this exception.
   * @param messageReference references the message text (and additional values)
   * of this {@link ODataMessageException}
   * @param cause exception which caused this exception
   */
  public ODataMessageException(final MessageReference messageReference, final Throwable cause) {
    this(messageReference, cause, null);
  }

  /**
   * Creates {@link ODataMessageException} with given {@link MessageReference},
   * cause {@link Throwable} and error code.
   * @param messageReference references the message text (and additional values)
   * of this {@link ODataMessageException}
   * @param cause exception which caused this exception
   * @param errorCode a String with a unique code identifying this exception
   */
  public ODataMessageException(final MessageReference messageReference, final Throwable cause, final String errorCode) {
    super(cause);
    this.messageReference = messageReference;
    this.errorCode = errorCode;
  }

  /**
   * Creates {@link ODataMessageException} with given {@link MessageReference} and error code.
   * @param messageReference references the message text (and additional values)
   * of this {@link ODataMessageException}
   * @param errorCode a String with a unique code identifying this exception
   */
  public ODataMessageException(final MessageReference messageReference, final String errorCode) {
    this(messageReference, null, errorCode);
  }

  /**
   * Creates {@link MessageReference} objects more conveniently.
   * @param clazz exception class for message reference
   * @param messageReferenceKey unique (in exception class) key for message reference
   * @return created message-reference instance
   */
  protected static final MessageReference createMessageReference(final Class<? extends ODataMessageException> clazz,
      final String messageReferenceKey) {
    return MessageReference.create(clazz, messageReferenceKey);
  }

  /**
   * Gets the related {@link MessageReference}.
   * @return the message reference
   */
  public MessageReference getMessageReference() {
    return messageReference;
  }

  /**
   * Gets the error code for this {@link ODataMessageException}.
   * Default is <code>null</code>.
   * @return the error code
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {
    if (messageReference == null) {
      return "No message reference given. Inherit message is = '" + super.getMessage() + "'";
    }

    String message = RuntimeDelegate.extractExceptionMessage(this);
    if (message == null) {
      return "Message Reference key = '" + messageReference.getKey() +
          "' and inherit message = '" + super.getMessage() + "'";
    }
    return message;
  }
}
