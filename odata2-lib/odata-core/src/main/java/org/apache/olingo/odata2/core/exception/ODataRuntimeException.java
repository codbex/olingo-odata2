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
package org.apache.olingo.odata2.core.exception;

// TODO: Auto-generated Javadoc
/**
 * Common un-checked exception for the <code>OData</code> library and
 * base exception for all <code>OData</code>-related exceptions
 * caused by programming errors and/or unexpected behavior within the code.
 * 
 */
public final class ODataRuntimeException extends RuntimeException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new o data runtime exception.
   */
  public ODataRuntimeException() {
    super();
  }

  /**
   * Instantiates a new o data runtime exception.
   *
   * @param throwable the throwable
   */
  public ODataRuntimeException(final Throwable throwable) {
    super(throwable);
  }

  /**
   * Instantiates a new o data runtime exception.
   *
   * @param message the message
   */
  public ODataRuntimeException(final String message) {
    super(message);
  }

  /**
   * Instantiates a new o data runtime exception.
   *
   * @param message the message
   * @param throwable the throwable
   */
  public ODataRuntimeException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

}
