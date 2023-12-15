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

import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent an annotation attribute.
 */
public class AnnotationAttribute implements EdmAnnotationAttribute {

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
   * Sets the namespace for this {@link AnnotationAttribute}.
   *
   * @param namespace the namespace
   * @return {@link AnnotationAttribute} for method chaining
   */
  public AnnotationAttribute setNamespace(final String namespace) {
    this.namespace = namespace;
    return this;
  }

  /**
   * Sets the prefix for this {@link AnnotationAttribute}.
   *
   * @param prefix the prefix
   * @return {@link AnnotationAttribute} for method chaining
   */
  public AnnotationAttribute setPrefix(final String prefix) {
    this.prefix = prefix;
    return this;
  }

  /**
   * Sets the name for this {@link AnnotationAttribute}.
   *
   * @param name the name
   * @return {@link AnnotationAttribute} for method chaining
   */
  public AnnotationAttribute setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the text for this {@link AnnotationAttribute}.
   *
   * @param text the text
   * @return {@link AnnotationAttribute} for method chaining
   */
  public AnnotationAttribute setText(final String text) {
    this.text = text;
    return this;
  }

}
