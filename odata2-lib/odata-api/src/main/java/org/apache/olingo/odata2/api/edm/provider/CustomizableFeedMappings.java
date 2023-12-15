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
package org.apache.olingo.odata2.api.edm.provider;

import org.apache.olingo.odata2.api.edm.EdmContentKind;
import org.apache.olingo.odata2.api.edm.EdmCustomizableFeedMappings;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent customizable feed mappings.
 * 
 */
public class CustomizableFeedMappings implements EdmCustomizableFeedMappings {

  /** The fc keep in content. */
  private Boolean fcKeepInContent;
  
  /** The fc content kind. */
  private EdmContentKind fcContentKind;
  
  /** The fc ns prefix. */
  private String fcNsPrefix;
  
  /** The fc ns uri. */
  private String fcNsUri;
  
  /** The fc source path. */
  private String fcSourcePath;
  
  /** The fc target path. */
  private String fcTargetPath;

  /**
   * Checks if is fc keep in content.
   *
   * @return the boolean
   */
  @Override
  public Boolean isFcKeepInContent() {
    return fcKeepInContent;
  }

  /**
   * Gets the fc content kind.
   *
   * @return the fc content kind
   */
  @Override
  public EdmContentKind getFcContentKind() {
    return fcContentKind;
  }

  /**
   * Gets the fc ns prefix.
   *
   * @return the fc ns prefix
   */
  @Override
  public String getFcNsPrefix() {
    return fcNsPrefix;
  }

  /**
   * Gets the fc ns uri.
   *
   * @return the fc ns uri
   */
  @Override
  public String getFcNsUri() {
    return fcNsUri;
  }

  /**
   * Gets the fc source path.
   *
   * @return the fc source path
   */
  @Override
  public String getFcSourcePath() {
    return fcSourcePath;
  }

  /**
   * Gets the fc target path.
   *
   * @return the fc target path
   */
  @Override
  public String getFcTargetPath() {
    return fcTargetPath;
  }

  /**
   * Gets the fc keep in content.
   *
   * @return <b>boolean</b>
   */
  public Boolean getFcKeepInContent() {
    return fcKeepInContent;
  }

  /**
   * Sets if this is kept in content.
   *
   * @param fcKeepInContent the fc keep in content
   * @return {@link CustomizableFeedMappings} for method chaining
   */
  public CustomizableFeedMappings setFcKeepInContent(final Boolean fcKeepInContent) {
    this.fcKeepInContent = fcKeepInContent;
    return this;
  }

  /**
   * Sets the {@link EdmContentKind}.
   *
   * @param fcContentKind the fc content kind
   * @return {@link CustomizableFeedMappings} for method chaining
   */
  public CustomizableFeedMappings setFcContentKind(final EdmContentKind fcContentKind) {
    this.fcContentKind = fcContentKind;
    return this;
  }

  /**
   * Sets the prefix.
   *
   * @param fcNsPrefix the fc ns prefix
   * @return {@link CustomizableFeedMappings} for method chaining
   */
  public CustomizableFeedMappings setFcNsPrefix(final String fcNsPrefix) {
    this.fcNsPrefix = fcNsPrefix;
    return this;
  }

  /**
   * Sets the Uri.
   *
   * @param fcNsUri the fc ns uri
   * @return {@link CustomizableFeedMappings} for method chaining
   */
  public CustomizableFeedMappings setFcNsUri(final String fcNsUri) {
    this.fcNsUri = fcNsUri;
    return this;
  }

  /**
   * Sets the source path.
   *
   * @param fcSourcePath the fc source path
   * @return {@link CustomizableFeedMappings} for method chaining
   */
  public CustomizableFeedMappings setFcSourcePath(final String fcSourcePath) {
    this.fcSourcePath = fcSourcePath;
    return this;
  }

  /**
   * <p>Sets the target path.</p>
   * <p>For standard Atom elements, constants are available in {@link org.apache.olingo.odata2.api.edm.EdmTargetPath
   * EdmTargetPath}.</p>
   *
   * @param fcTargetPath the fc target path
   * @return {@link CustomizableFeedMappings} for method chaining
   */
  public CustomizableFeedMappings setFcTargetPath(final String fcTargetPath) {
    this.fcTargetPath = fcTargetPath;
    return this;
  }
}
