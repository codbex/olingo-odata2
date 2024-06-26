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
package org.apache.olingo.odata2.testutil;

// TODO: Auto-generated Javadoc
/**
 * This class is a helper to throw RuntimeExceptions in test util methods.
 */
public class TestUtilRuntimeException extends RuntimeException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new test util runtime exception.
   *
   * @param e the e
   */
  public TestUtilRuntimeException(final Throwable e) {
    super(e);
  }

  /**
   * Instantiates a new test util runtime exception.
   *
   * @param msg the msg
   */
  public TestUtilRuntimeException(final String msg) {
    super(msg);
  }

  /**
   * Instantiates a new test util runtime exception.
   *
   * @param msg the msg
   * @param e the e
   */
  public TestUtilRuntimeException(final String msg, final Throwable e) {
    super(msg, e);
  }

}
