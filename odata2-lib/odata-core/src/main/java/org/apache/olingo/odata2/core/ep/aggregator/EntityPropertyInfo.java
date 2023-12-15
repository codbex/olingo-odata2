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
package org.apache.olingo.odata2.core.ep.aggregator;

import org.apache.olingo.odata2.api.edm.EdmCustomizableFeedMappings;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmType;

// TODO: Auto-generated Javadoc
/**
 * Collects informations about a property of an entity.
 * 
 */
public class EntityPropertyInfo {
  
  /** The name. */
  private final String name;
  
  /** The type. */
  private final EdmType type;
  
  /** The facets. */
  private final EdmFacets facets;
  
  /** The custom mapping. */
  private final EdmCustomizableFeedMappings customMapping;
  
  /** The mime type. */
  private final String mimeType;
  
  /** The mapping. */
  private final EdmMapping mapping;

  /**
   * Instantiates a new entity property info.
   *
   * @param name the name
   * @param type the type
   * @param facets the facets
   * @param customizableFeedMapping the customizable feed mapping
   * @param mimeType the mime type
   * @param mapping the mapping
   */
  EntityPropertyInfo(final String name, final EdmType type, final EdmFacets facets,
      final EdmCustomizableFeedMappings customizableFeedMapping, final String mimeType, final EdmMapping mapping) {
    this.name = name;
    this.type = type;
    this.facets = facets;
    customMapping = customizableFeedMapping;
    this.mimeType = mimeType;
    this.mapping = mapping;
  }

  /**
   * Creates the.
   *
   * @param property the property
   * @return the entity property info
   * @throws EdmException the edm exception
   */
  static EntityPropertyInfo create(final EdmProperty property) throws EdmException {
    return new EntityPropertyInfo(
        property.getName(),
        property.getType(),
        property.getFacets(),
        property.getCustomizableFeedMappings(),
        property.getMimeType(),
        property.getMapping());
  }

  /**
   * Checks if is mandatory.
   *
   * @return true, if is mandatory
   */
  public boolean isMandatory() {
    return !(facets == null || facets.isNullable() == null || facets.isNullable());
  }

  /**
   * Checks if is complex.
   *
   * @return true, if is complex
   */
  public boolean isComplex() {
    return false;
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
   * Gets the type.
   *
   * @return the type
   */
  public EdmType getType() {
    return type;
  }

  /**
   * Gets the facets.
   *
   * @return the facets
   */
  public EdmFacets getFacets() {
    return facets;
  }

  /**
   * Gets the custom mapping.
   *
   * @return the custom mapping
   */
  public EdmCustomizableFeedMappings getCustomMapping() {
    return customMapping;
  }

  /**
   * Gets the mime type.
   *
   * @return the mime type
   */
  public String getMimeType() {
    return mimeType;
  }

  /**
   * Gets the mapping.
   *
   * @return the mapping
   */
  public EdmMapping getMapping() {
    return mapping;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return name;
  }
}
