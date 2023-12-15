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

import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent a simple property.
 * 
 */
public class SimpleProperty extends Property {

  /** The type. */
  private EdmSimpleTypeKind type;

  /**
   * Gets the type.
   *
   * @return {@link EdmSimpleTypeKind} of this property
   */
  public EdmSimpleTypeKind getType() {
    return type;
  }

  /**
   * Sets the {@link EdmSimpleTypeKind} for this {@link Property}.
   *
   * @param type the type
   * @return {@link Property} for method chaining
   */
  public SimpleProperty setType(final EdmSimpleTypeKind type) {
    this.type = type;
    return this;
  }

  /**
   * Sets the name.
   *
   * @param name the name
   * @return the simple property
   */
  @Override
  public SimpleProperty setName(final String name) {
    super.setName(name);
    return this;
  }

  /**
   * Sets the facets.
   *
   * @param facets the facets
   * @return the simple property
   */
  @Override
  public SimpleProperty setFacets(final EdmFacets facets) {
    super.setFacets(facets);
    return this;
  }

  /**
   * Sets the customizable feed mappings.
   *
   * @param customizableFeedMappings the customizable feed mappings
   * @return the simple property
   */
  @Override
  public SimpleProperty setCustomizableFeedMappings(final CustomizableFeedMappings customizableFeedMappings) {
    super.setCustomizableFeedMappings(customizableFeedMappings);
    return this;
  }

  /**
   * Sets the mime type.
   *
   * @param mimeType the mime type
   * @return the simple property
   */
  @Override
  public SimpleProperty setMimeType(final String mimeType) {
    super.setMimeType(mimeType);
    return this;
  }

  /**
   * Sets the mapping.
   *
   * @param mapping the mapping
   * @return the simple property
   */
  @Override
  public SimpleProperty setMapping(final Mapping mapping) {
    super.setMapping(mapping);
    return this;
  }

  /**
   * Sets the documentation.
   *
   * @param documentation the documentation
   * @return the simple property
   */
  @Override
  public SimpleProperty setDocumentation(final Documentation documentation) {
    super.setDocumentation(documentation);
    return this;
  }

  /**
   * Sets the annotation attributes.
   *
   * @param annotationAttributes the annotation attributes
   * @return the simple property
   */
  @Override
  public SimpleProperty setAnnotationAttributes(final List<AnnotationAttribute> annotationAttributes) {
    super.setAnnotationAttributes(annotationAttributes);
    return this;
  }

  /**
   * Sets the annotation elements.
   *
   * @param annotationElements the annotation elements
   * @return the simple property
   */
  @Override
  public SimpleProperty setAnnotationElements(final List<AnnotationElement> annotationElements) {
    super.setAnnotationElements(annotationElements);
    return this;
  }
}
