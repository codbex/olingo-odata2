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
package org.apache.olingo.odata2.core.servicedocument;

import org.apache.olingo.odata2.api.servicedocument.Accept;
import org.apache.olingo.odata2.api.servicedocument.CommonAttributes;

// TODO: Auto-generated Javadoc
/**
 * The Class AcceptImpl.
 */
public class AcceptImpl implements Accept {
  
  /** The value. */
  private String value;
  
  /** The common attributes. */
  private CommonAttributes commonAttributes;

  /**
   * Gets the value.
   *
   * @return the value
   */
  @Override
  public String getValue() {
    return value;
  }

  /**
   * Gets the common attributes.
   *
   * @return the common attributes
   */
  @Override
  public CommonAttributes getCommonAttributes() {
    return commonAttributes;
  }

  /**
   * Sets the text.
   *
   * @param text the text
   * @return the accept impl
   */
  public AcceptImpl setText(final String text) {
    value = text;
    return this;
  }

  /**
   * Sets the common attributes.
   *
   * @param commonAttributes the common attributes
   * @return the accept impl
   */
  public AcceptImpl setCommonAttributes(final CommonAttributes commonAttributes) {
    this.commonAttributes = commonAttributes;
    return this;
  }
}
