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

import org.apache.olingo.odata2.api.servicedocument.Collection;
import org.apache.olingo.odata2.api.servicedocument.CommonAttributes;
import org.apache.olingo.odata2.api.servicedocument.ExtensionElement;
import org.apache.olingo.odata2.api.servicedocument.Title;
import org.apache.olingo.odata2.api.servicedocument.Workspace;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkspaceImpl.
 */
public class WorkspaceImpl implements Workspace {
  
  /** The title. */
  private Title title;
  
  /** The collections. */
  private List<Collection> collections;
  
  /** The attributes. */
  private CommonAttributes attributes;
  
  /** The extension elements. */
  private List<ExtensionElement> extensionElements;

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
   * Gets the collections.
   *
   * @return the collections
   */
  @Override
  public List<Collection> getCollections() {
    return collections;
  }

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
   * @return the workspace impl
   */
  public WorkspaceImpl setTitle(final Title title) {
    this.title = title;
    return this;
  }

  /**
   * Sets the collections.
   *
   * @param collections the collections
   * @return the workspace impl
   */
  public WorkspaceImpl setCollections(final List<Collection> collections) {
    this.collections = collections;
    return this;
  }

  /**
   * Sets the attributes.
   *
   * @param attributes the attributes
   * @return the workspace impl
   */
  public WorkspaceImpl setAttributes(final CommonAttributes attributes) {
    this.attributes = attributes;
    return this;
  }

  /**
   * Sets the extesion elements.
   *
   * @param elements the elements
   * @return the workspace impl
   */
  public WorkspaceImpl setExtesionElements(final List<ExtensionElement> elements) {
    extensionElements = elements;
    return this;
  }
}
