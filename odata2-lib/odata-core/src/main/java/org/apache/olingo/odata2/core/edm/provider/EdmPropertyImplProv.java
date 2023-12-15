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

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmCustomizableFeedMappings;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmPropertyImplProv.
 */
public abstract class EdmPropertyImplProv extends EdmElementImplProv implements EdmProperty, EdmAnnotatable {

  /** The property. */
  private Property property;
  
  /** The annotations. */
  private EdmAnnotations annotations;

  /**
   * Instantiates a new edm property impl prov.
   *
   * @param edm the edm
   * @param propertyName the property name
   * @param property the property
   * @throws EdmException the edm exception
   */
  public EdmPropertyImplProv(final EdmImplProv edm, final FullQualifiedName propertyName, final Property property)
      throws EdmException {
    super(edm, property.getName(), propertyName, property.getFacets(), property.getMapping());
    this.property = property;
  }

  /**
   * Gets the customizable feed mappings.
   *
   * @return the customizable feed mappings
   * @throws EdmException the edm exception
   */
  @Override
  public EdmCustomizableFeedMappings getCustomizableFeedMappings() throws EdmException {
    return property.getCustomizableFeedMappings();
  }

  /**
   * Gets the mime type.
   *
   * @return the mime type
   * @throws EdmException the edm exception
   */
  @Override
  public String getMimeType() throws EdmException {
    return property.getMimeType();
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAnnotations getAnnotations() throws EdmException {
    if (annotations == null) {
      annotations = new EdmAnnotationsImplProv(property.getAnnotationAttributes(), property.getAnnotationElements());
    }
    return annotations;
  }
}
