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

// TODO: Auto-generated Javadoc
/**
 * Base exception for all <code>OData</code>-related exceptions.
 * 
 */
public class ODataException extends Exception {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new o data exception.
   */
  public ODataException() {
    super();
  }

  /**
   * Instantiates a new o data exception.
   *
   * @param msg the msg
   */
  public ODataException(final String msg) {
    super(msg);
  }

  /**
   * Instantiates a new o data exception.
   *
   * @param msg the msg
   * @param e the e
   */
  public ODataException(final String msg, final Throwable e) {
    super(msg, e);
  }

  /**
   * Instantiates a new o data exception.
   *
   * @param e the e
   */
  public ODataException(final Throwable e) {
    super(e);
  }

  /**
   * Checks whether this exception is an or was caused by an {@link ODataHttpException} exception.
   * 
   * @return <code>true</code> if this is an or was caused by an {@link ODataHttpException}, otherwise
   * <code>false</code>
   */
  public boolean isCausedByHttpException() {
    return getHttpExceptionCause() != null;
  }

  /**
   * Search for and return first (from top) {@link ODataHttpException} in the cause hierarchy.
   * If there is no {@link ODataHttpException} in the cause hierarchy, <code>NULL</code> is returned.
   * 
   * @return the first found {@link ODataHttpException} in the cause exception hierarchy
   * or <code>NULL</code> if no {@link ODataHttpException} is found in the cause hierarchy
   */
  public ODataHttpException getHttpExceptionCause() {
    return getSpecificCause(ODataHttpException.class);
  }

  /**
   * Checks whether this exception is an or was caused by an {@link ODataApplicationException} exception.
   * 
   * @return <code>true</code> if this is an or was caused by an {@link ODataApplicationException}, otherwise
   * <code>false</code>
   */
  public boolean isCausedByApplicationException() {
    return getApplicationExceptionCause() != null;
  }

  /**
   * Checks whether this exception is an or was caused by an {@link ODataMessageException} exception.
   * 
   * @return <code>true</code> if this is an or was caused by an {@link ODataMessageException}, otherwise
   * <code>false</code>
   */
  public boolean isCausedByMessageException() {
    return getMessageExceptionCause() != null;
  }

  /**
   * Search for and return first (from top) {@link ODataMessageException} in the cause hierarchy.
   * If there is no {@link ODataMessageException} in the cause hierarchy <code>NULL</code> is returned.
   * 
   * @return the first found {@link ODataMessageException} in the cause exception hierarchy
   * or <code>NULL</code> if no {@link ODataMessageException} is found in the cause hierarchy
   */
  public ODataMessageException getMessageExceptionCause() {
    return getSpecificCause(ODataMessageException.class);
  }

  /**
   * Search for and return first (from top) {@link ODataApplicationException} in the cause hierarchy.
   * If there is no {@link ODataApplicationException} in the cause hierarchy <code>NULL</code> is returned.
   * 
   * @return the first found {@link ODataApplicationException} in the cause exception hierarchy
   * or <code>NULL</code> if no {@link ODataApplicationException} is found in the cause hierarchy
   */
  public ODataApplicationException getApplicationExceptionCause() {
    return getSpecificCause(ODataApplicationException.class);
  }

  /**
   * Gets the specific cause.
   *
   * @param <T> the generic type
   * @param causeClass the cause class
   * @return the specific cause
   */
  private <T extends ODataException> T getSpecificCause(final Class<T> causeClass) {
    Throwable cause = this;
    while (cause != null) {
      if (causeClass.isInstance(cause)) {
        return causeClass.cast(cause);
      } else {
        cause = cause.getCause();
      }
    }

    return null;
  }
}
