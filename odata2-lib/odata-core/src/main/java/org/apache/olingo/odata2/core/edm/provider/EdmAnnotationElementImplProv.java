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
package org.apache.olingo.odata2.core.edm.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;
import org.apache.olingo.odata2.api.edm.EdmAnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmAnnotationElementImplProv.
 */
public class EdmAnnotationElementImplProv implements EdmAnnotationElement {

  /** The element. */
  private AnnotationElement element;
  
  /** The child elements. */
  ArrayList<EdmAnnotationElement> childElements;
  
  /** The attributes. */
  List<EdmAnnotationAttribute> attributes;

  /**
   * Instantiates a new edm annotation element impl prov.
   *
   * @param element the element
   */
  public EdmAnnotationElementImplProv(final AnnotationElement element) {
    this.element = element;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return element.getName();
  }

  /**
   * Gets the namespace.
   *
   * @return the namespace
   */
  @Override
  public String getNamespace() {
    return element.getNamespace();
  }

  /**
   * Gets the prefix.
   *
   * @return the prefix
   */
  @Override
  public String getPrefix() {
    return element.getPrefix();
  }

  /**
   * Gets the text.
   *
   * @return the text
   */
  @Override
  public String getText() {
    return element.getText();
  }

  /**
   * Gets the child elements.
   *
   * @return the child elements
   */
  @Override
  public List<EdmAnnotationElement> getChildElements() {
    if (childElements == null && element.getChildElements() != null) {
      childElements = new ArrayList<EdmAnnotationElement>();
      for (AnnotationElement childElement : element.getChildElements()) {
        childElements.add(new EdmAnnotationElementImplProv(childElement));
      }
    }
    return childElements;
  }

  /**
   * Gets the attributes.
   *
   * @return the attributes
   */
  @Override
  public List<EdmAnnotationAttribute> getAttributes() {
    if (attributes == null && element.getAttributes() != null) {
      attributes = new ArrayList<EdmAnnotationAttribute>();
      attributes.addAll(element.getAttributes());
    }
    return attributes;
  }
}
