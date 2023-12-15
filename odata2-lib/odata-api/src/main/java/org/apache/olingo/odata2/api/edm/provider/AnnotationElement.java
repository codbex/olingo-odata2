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

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent an annotation element.
 * 
 */
public class AnnotationElement {

  /** The namespace. */
  private String namespace;
  
  /** The prefix. */
  private String prefix;
  
  /** The name. */
  private String name;
  
  /** The text. */
  private String text;
  
  /** The child elements. */
  private List<AnnotationElement> childElements;
  
  /** The attributes. */
  private List<AnnotationAttribute> attributes;

  /**
   * Gets the namespace.
   *
   * @return the namespace
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Gets the prefix.
   *
   * @return the prefix
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * Gets the child elements.
   *
   * @return the child elements
   */
  public List<AnnotationElement> getChildElements() {
    return childElements;
  }

  /**
   * Gets the attributes.
   *
   * @return the attributes
   */
  public List<AnnotationAttribute> getAttributes() {
    return attributes;
  }

  /**
   * Sets the attributes for this {@link AnnotationElement}.
   *
   * @param attributes the attributes
   * @return {@link AnnotationElement} for method chaining
   */
  public AnnotationElement setAttributes(final List<AnnotationAttribute> attributes) {
    this.attributes = attributes;
    return this;
  }

  /**
   * Sets the child elements for this {@link AnnotationElement}.
   * Does not set child elements and characterData for one element.
   *
   * @param childElements the child elements
   * @return {@link AnnotationElement} for method chaining
   */
  public AnnotationElement setChildElements(final List<AnnotationElement> childElements) {
    this.childElements = childElements;
    return this;
  }

  /**
   * Sets the namespace for this {@link AnnotationElement}.
   *
   * @param namespace the namespace
   * @return {@link AnnotationElement} for method chaining
   */
  public AnnotationElement setNamespace(final String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Sets the prefix for this {@link AnnotationElement}.
   *
   * @param prefix the prefix
   * @return {@link AnnotationElement} for method chaining
   */
  public AnnotationElement setPrefix(final String prefix) {
    this.prefix = prefix;
    return this;
  }

  /**
   * Sets the name for this {@link AnnotationElement}.
   *
   * @param name the name
   * @return {@link AnnotationElement} for method chaining
   */
  public AnnotationElement setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the text for this {@link AnnotationElement} which will be displayed inside the tags.
   * Must NOT be set if child elements are set!
   *
   * @param text the text
   * @return {@link AnnotationElement} for method chaining
   */
  public AnnotationElement setText(final String text) {
    this.text = text;
    return this;
  }

}
