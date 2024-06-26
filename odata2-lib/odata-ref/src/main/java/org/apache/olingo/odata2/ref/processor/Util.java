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
package org.apache.olingo.odata2.ref.processor;

// TODO: Auto-generated Javadoc
/**
 * The Class Util.
 */
public class Util {

  /** The Constant instance. */
  private static final Util instance = new Util();

  /** The binary content. */
  private byte[] binaryContent = null;

  /**
   * Gets the single instance of Util.
   *
   * @return single instance of Util
   */
  public static Util getInstance() {
    return instance;
  }

  /**
   * Gets the binary content.
   *
   * @return the binaryContent
   */
  public byte[] getBinaryContent() {
    return binaryContent;
  }

  /**
   * Sets the binary content.
   *
   * @param binaryContent the binaryContent to set
   */
  public void setBinaryContent(byte[] binaryContent) {
    this.binaryContent = binaryContent;
  }
}