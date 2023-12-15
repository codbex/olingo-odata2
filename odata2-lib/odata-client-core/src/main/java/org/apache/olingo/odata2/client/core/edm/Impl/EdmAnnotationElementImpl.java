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

import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;
import org.apache.olingo.odata2.api.edm.EdmAnnotationElement;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent AnnotationElement.
 */
public class EdmAnnotationElementImpl implements EdmAnnotationElement {

  /** The child elements. */
  List<EdmAnnotationElement> childElements;
  
  /** The attributes. */
  List<EdmAnnotationAttribute> attributes; 
  
  /** The namespace. */
  private String namespace;
  
  /** The prefix. */
  private String prefix;
  
  /** The name. */
  private String name;
  
  /** The text. */
  private String text;

  /**
   * Sets the namespace.
   *
   * @param namespace the new namespace
   */
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }
  
  /**
   * Sets the prefix.
   *
   * @param prefix the new prefix
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
  
  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Sets the text.
   *
   * @param text the new text
   */
  public void setText(String text) {
    this.text = text;
  }
 
  /**
   * Sets the child elements.
   *
   * @param childElements the new child elements
   */
  public void setChildElements(List<EdmAnnotationElement> childElements) {
    this.childElements = childElements;
  }
  
  /**
   * Sets the attributes.
   *
   * @param attributes the new attributes
   */
  public void setAttributes(List<EdmAnnotationAttribute> attributes) {
    this.attributes = attributes;
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
   * Gets the text.
   *
   * @return the text
   */
  @Override
  public String getText() {
    return text;
  }

  /**
   * Gets the child elements.
   *
   * @return the child elements
   */
  @Override
  public List<EdmAnnotationElement> getChildElements() {
    return childElements;
  }

  /**
   * Gets the attributes.
   *
   * @return the attributes
   */
  @Override
  public List<EdmAnnotationAttribute> getAttributes() {
    return attributes;
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
