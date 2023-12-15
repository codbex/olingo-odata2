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

// TODO: Auto-generated Javadoc
/**
 * The Interface EdmCustomizableFeedMappings.
 *
 * @org.apache.olingo.odata2.DoNotImplement Customizable Feed property mappings for the AtomPub Format as defined in the OData specification.
 */
public interface EdmCustomizableFeedMappings {

  /**
   * Get the information if the property should be kept in the content.
   *
   * @return <code>true</code> if the property must be kept in the content
   */
  public Boolean isFcKeepInContent();

  /**
   * Get the content kind.
   *
   * @return {@link EdmContentKind}
   */
  public EdmContentKind getFcContentKind();

  /**
   * Get the XML namespace prefix.
   *
   * @return String
   */
  public String getFcNsPrefix();

  /**
   * Get the XML namespace URI.
   *
   * @return String
   */
  public String getFcNsUri();

  /**
   * Get the source path.
   *
   * @return String
   */
  public String getFcSourcePath();

  /**
   * Get the target path.
   *
   * @return String
   */
  public String getFcTargetPath();
}
