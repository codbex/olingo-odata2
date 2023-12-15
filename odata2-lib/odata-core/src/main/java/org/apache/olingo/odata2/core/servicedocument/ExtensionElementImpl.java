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

import java.util.List;

import org.apache.olingo.odata2.api.servicedocument.ExtensionAttribute;
import org.apache.olingo.odata2.api.servicedocument.ExtensionElement;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtensionElementImpl.
 */
public class ExtensionElementImpl implements ExtensionElement {
  
  /** The namespace. */
  private String namespace;
  
  /** The prefix. */
  private String prefix;
  
  /** The name. */
  private String name;
  
  /** The text. */
  private String text;
  
  /** The any elements. */
  private List<ExtensionElement> anyElements;
  
  /** The attributes. */
  private List<ExtensionAttribute> attributes;

  /**
   * Gets the namespace.
   *
   * @return the namespace
   */
  @Override
  public String getNamespace() {
    return namespace;
  }

  /**
   * Gets the prefix.
   *
   * @return the prefix
   */
  @Override
  public String getPrefix() {
    return prefix;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  @Override
  public String getText() {
    return text;
  }

  /**
   * Gets the elements.
   *
   * @return the elements
   */
  @Override
  public List<ExtensionElement> getElements() {
    return anyElements;
  }

  /**
   * Gets the attributes.
   *
   * @return the attributes
   */
  @Override
  public List<ExtensionAttribute> getAttributes() {
    return attributes;
  }

  /**
   * Sets the namespace.
   *
   * @param namespace the namespace
   * @return the extension element impl
   */
  public ExtensionElementImpl setNamespace(final String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Sets the prefix.
   *
   * @param prefix the prefix
   * @return the extension element impl
   */
  public ExtensionElementImpl setPrefix(final String prefix) {
    this.prefix = prefix;
    return this;
  }

  /**
   * Sets the name.
   *
   * @param name the name
   * @return the extension element impl
   */
  public ExtensionElementImpl setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the text.
   *
   * @param text the text
   * @return the extension element impl
   */
  public ExtensionElementImpl setText(final String text) {
    this.text = text;
    return this;

  }

  /**
   * Sets the elements.
   *
   * @param anyElements the any elements
   * @return the extension element impl
   */
  public ExtensionElementImpl setElements(final List<ExtensionElement> anyElements) {
    this.anyElements = anyElements;
    return this;
  }

  /**
   * Sets the attributes.
   *
   * @param attributes the attributes
   * @return the extension element impl
   */
  public ExtensionElementImpl setAttributes(final List<ExtensionAttribute> attributes) {
    this.attributes = attributes;
    return this;
  }

}
