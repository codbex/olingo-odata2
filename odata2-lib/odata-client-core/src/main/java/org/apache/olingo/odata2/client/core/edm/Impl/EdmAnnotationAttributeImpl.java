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
package org.apache.olingo.odata2.client.core.edm.Impl;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent an annotation attribute.
 */
public class EdmAnnotationAttributeImpl implements EdmAnnotationAttribute {

  /** The namespace. */
  private String namespace;
  
  /** The prefix. */
  private String prefix;
  
  /** The name. */
  private String name;
  
  /** The text. */
  private String text;

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
   * Sets the namespace for this {@link EdmAnnotationAttributeImpl}.
   *
   * @param namespace the namespace
   * @return {@link EdmAnnotationAttributeImpl} for method chaining
   */
  public EdmAnnotationAttributeImpl setNamespace(final String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Sets the prefix for this {@link EdmAnnotationAttributeImpl}.
   *
   * @param prefix the prefix
   * @return {@link EdmAnnotationAttributeImpl} for method chaining
   */
  public EdmAnnotationAttributeImpl setPrefix(final String prefix) {
    this.prefix = prefix;
    return this;
  }

  /**
   * Sets the name for this {@link EdmAnnotationAttributeImpl}.
   *
   * @param name the name
   * @return {@link EdmAnnotationAttributeImpl} for method chaining
   */
  public EdmAnnotationAttributeImpl setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the text for this {@link EdmAnnotationAttributeImpl}.
   *
   * @param text the text
   * @return {@link EdmAnnotationAttributeImpl} for method chaining
   */
  public EdmAnnotationAttributeImpl setText(final String text) {
    this.text = text;
    return this;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return namespace + Edm.DELIMITER + name;
  }
}
