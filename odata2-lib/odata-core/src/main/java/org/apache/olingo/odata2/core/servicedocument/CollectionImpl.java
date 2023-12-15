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

import org.apache.olingo.odata2.api.servicedocument.Accept;
import org.apache.olingo.odata2.api.servicedocument.Categories;
import org.apache.olingo.odata2.api.servicedocument.Collection;
import org.apache.olingo.odata2.api.servicedocument.CommonAttributes;
import org.apache.olingo.odata2.api.servicedocument.ExtensionElement;
import org.apache.olingo.odata2.api.servicedocument.Title;

// TODO: Auto-generated Javadoc
/**
 * The Class CollectionImpl.
 */
public class CollectionImpl implements Collection {
  
  /** The title. */
  private Title title;
  
  /** The href. */
  private String href;
  
  /** The accept elements. */
  private List<Accept> acceptElements;
  
  /** The categories. */
  private List<Categories> categories;
  
  /** The attributes. */
  private CommonAttributes attributes;
  
  /** The extension elements. */
  private List<ExtensionElement> extensionElements;

  /**
   * Gets the common attributes.
   *
   * @return the common attributes
   */
  @Override
  public CommonAttributes getCommonAttributes() {
    return attributes;
  }

  /**
   * Gets the title.
   *
   * @return the title
   */
  @Override
  public Title getTitle() {
    return title;
  }

  /**
   * Gets the href.
   *
   * @return the href
   */
  @Override
  public String getHref() {
    return href;
  }

  /**
   * Gets the accept elements.
   *
   * @return the accept elements
   */
  @Override
  public List<Accept> getAcceptElements() {
    return acceptElements;
  }

  /**
   * Gets the categories.
   *
   * @return the categories
   */
  @Override
  public List<Categories> getCategories() {
    return categories;
  }

  /**
   * Gets the extesion elements.
   *
   * @return the extesion elements
   */
  @Override
  public List<ExtensionElement> getExtesionElements() {
    return extensionElements;
  }

  /**
   * Sets the title.
   *
   * @param title the title
   * @return the collection impl
   */
  public CollectionImpl setTitle(final Title title) {
    this.title = title;
    return this;
  }

  /**
   * Sets the href.
   *
   * @param href the href
   * @return the collection impl
   */
  public CollectionImpl setHref(final String href) {
    this.href = href;
    return this;
  }

  /**
   * Sets the accept elements.
   *
   * @param acceptElements the accept elements
   * @return the collection impl
   */
  public CollectionImpl setAcceptElements(final List<Accept> acceptElements) {
    this.acceptElements = acceptElements;
    return this;
  }

  /**
   * Sets the categories.
   *
   * @param categories the categories
   * @return the collection impl
   */
  public CollectionImpl setCategories(final List<Categories> categories) {
    this.categories = categories;
    return this;
  }

  /**
   * Sets the common attributes.
   *
   * @param attributes the attributes
   * @return the collection impl
   */
  public CollectionImpl setCommonAttributes(final CommonAttributes attributes) {
    this.attributes = attributes;
    return this;
  }

  /**
   * Sets the extesion elements.
   *
   * @param elements the elements
   * @return the collection impl
   */
  public CollectionImpl setExtesionElements(final List<ExtensionElement> elements) {
    extensionElements = elements;
    return this;
  }

}
