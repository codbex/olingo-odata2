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

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmCustomizableFeedMappings;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.client.api.edm.EdmDocumentation;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent EdmProperty.
 */
public abstract class EdmPropertyImpl extends EdmElementImpl implements EdmProperty, EdmAnnotatable {

  /** The facets. */
  private EdmFacets facets;
  
  /** The customizable feed mappings. */
  private EdmCustomizableFeedMappings customizableFeedMappings;
  
  /** The mime type. */
  private String mimeType;
  
  /** The mapping. */
  private Mapping mapping;
  
  /** The documentation. */
  private EdmDocumentation documentation;
  
  /** The annotations. */
  private EdmAnnotations annotations;

  /**
   * Gets the facets.
   *
   * @return the facets
   */
  public EdmFacets getFacets() {
    return facets;
  }

  /**
   * Sets the facets.
   *
   * @param facets the new facets
   */
  @Override
  public void setFacets(EdmFacets facets) {
    this.facets = facets;
  }
  
  /**
   * Sets the mapping.
   *
   * @param mapping the new mapping
   */
  public void setMapping(Mapping mapping) {
    this.mapping = mapping;
  }

  /**
   * Sets the documentation.
   *
   * @param documentation the new documentation
   */
  public void setDocumentation(EdmDocumentation documentation) {
    this.documentation = documentation;
  }

  /**
   * Sets the customizable feed mappings.
   *
   * @param customizableFeedMappings the new customizable feed mappings
   */
  public void setCustomizableFeedMappings(EdmCustomizableFeedMappings customizableFeedMappings) {
    this.customizableFeedMappings = customizableFeedMappings;
  }

  /**
   * Sets the mime type.
   *
   * @param mimeType the new mime type
   */
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * Gets the customizable feed mappings.
   *
   * @return the customizable feed mappings
   * @throws EdmException the edm exception
   */
  @Override
  public EdmCustomizableFeedMappings getCustomizableFeedMappings() throws EdmException {
    return customizableFeedMappings;
  }


  /**
   * Sets the annotations.
   *
   * @param annotations the new annotations
   */
  public void setAnnotations(EdmAnnotations annotations) {
    this.annotations = annotations;
  }

  /**
   * Gets the mime type.
   *
   * @return the mime type
   * @throws EdmException the edm exception
   */
  @Override
  public String getMimeType() throws EdmException {
    return mimeType;
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAnnotations getAnnotations() throws EdmException {
    return annotations;
  }
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format(name);
  }
}
