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
package org.apache.olingo.odata2.api.ep.callback;

import java.util.Map;

import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;

// TODO: Auto-generated Javadoc
/**
 * Result of a callback. It contains the data of the entry which is to be expanded as well as the properties of this
 * entry.
 * 
 */
public class WriteEntryCallbackResult {

  /** The inline properties. */
  EntityProviderWriteProperties inlineProperties;
  
  /** The one entry data. */
  Map<String, Object> oneEntryData;

  /**
   * Gets the inline properties.
   *
   * @return the inline properties
   */
  public EntityProviderWriteProperties getInlineProperties() {
    return inlineProperties;
  }

  /**
   * Sets the inline properties for this entry.
   *
   * @param inlineProperties the new inline properties
   */
  public void setInlineProperties(final EntityProviderWriteProperties inlineProperties) {
    this.inlineProperties = inlineProperties;
  }

  /**
   * Gets the entry data.
   *
   * @return the data for the entry as a map
   */
  public Map<String, Object> getEntryData() {
    return oneEntryData;
  }

  /**
   * Sets the entry data.
   *
   * @param data for the entry as a map
   */
  public void setEntryData(final Map<String, Object> data) {
    oneEntryData = data;
  }
}
