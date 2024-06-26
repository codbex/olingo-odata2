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
package org.apache.olingo.odata2.api.servicedocument;

// TODO: Auto-generated Javadoc
/**
 * A ExtensionAttribute
 * <p>ExtensionAttribute is an attribute of an extension element.
 */
public interface ExtensionAttribute {
  
  /**
   * Get the namespace.
   *
   * @return namespace as String
   */
  public String getNamespace();

  /**
   * Get the prefix of the attribute.
   *
   * @return prefix as String
   */
  public String getPrefix();

  /**
   * Get the local name of the attribute.
   *
   * @return name as String
   */
  public String getName();

  /**
   * Get the text.
   *
   * @return text as String
   */
  public String getText();
}
