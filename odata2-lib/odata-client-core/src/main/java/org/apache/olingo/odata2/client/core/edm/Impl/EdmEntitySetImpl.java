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
import org.apache.olingo.odata2.api.edm.EdmAssociationSet;
import org.apache.olingo.odata2.api.edm.EdmAssociationSetEnd;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.client.api.edm.EdmDocumentation;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent EdmEntitySet.
 */
public class EdmEntitySetImpl extends EdmNamedImpl implements EdmEntitySet, EdmAnnotatable {

  /** The edm entity container. */
  private EdmEntityContainer edmEntityContainer;
  
  /** The edm entity type. */
  private EdmEntityType edmEntityType;  
  
  /** The annotations. */
  private EdmAnnotationsImpl annotations;
  
  /** The entity type name. */
  private FullQualifiedName entityTypeName;
  
  /** The mapping. */
  private EdmMapping mapping;
  
  /** The documentation. */
  private EdmDocumentation documentation;  

 
  /**
   * Gets the entity type name.
   *
   * @return the entity type name
   */
  public FullQualifiedName getEntityTypeName() {
    return entityTypeName;
  }

  /**
   * Sets the entity type name.
   *
   * @param entityTypeName the new entity type name
   */
  public void setEntityTypeName(FullQualifiedName entityTypeName) {
    this.entityTypeName = entityTypeName;
  }

  /**
   * Gets the entity type.
   *
   * @return the entity type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityType getEntityType() throws EdmException {
    return edmEntityType;
  }
  
  /**
   * Gets the edm entity container.
   *
   * @return the edm entity container
   */
  public EdmEntityContainer getEdmEntityContainer() {
    return edmEntityContainer;
  }

  /**
   * Sets the edm entity container.
   *
   * @param edmEntityContainer the new edm entity container
   */
  public void setEdmEntityContainer(EdmEntityContainer edmEntityContainer) {
    this.edmEntityContainer = edmEntityContainer;
  }

  /**
   * Gets the edm entity type.
   *
   * @return the edm entity type
   */
  public EdmEntityType getEdmEntityType() {
    return edmEntityType;
  }

  /**
   * Sets the edm entity type.
   *
   * @param edmEntityType the new edm entity type
   */
  public void setEdmEntityType(EdmEntityType edmEntityType) {
    this.edmEntityType = edmEntityType;
  }

  /**
   * Sets the annotations.
   *
   * @param annotations the new annotations
   */
  public void setAnnotations(EdmAnnotationsImpl annotations) {
    this.annotations = annotations;
  }

  /**
   * Gets the documentation.
   *
   * @return the documentation
   */
  public EdmDocumentation getDocumentation() {
    return documentation;
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
   * Sets the mapping.
   *
   * @param mapping the new mapping
   */
  public void setMapping(EdmMapping mapping) {
    this.mapping = mapping;
  }

  /**
   * Gets the related entity set.
   *
   * @param navigationProperty the navigation property
   * @return the related entity set
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntitySet getRelatedEntitySet(final EdmNavigationProperty navigationProperty) throws EdmException {
    EdmAssociationSet associationSet =
        edmEntityContainer.getAssociationSet(edmEntityContainer.getEntitySet(name), navigationProperty);
    if(associationSet == null){
      return null;
    }
    EdmAssociationSetEnd toEnd = associationSet.getEnd(navigationProperty.getToRole());
    if (toEnd == null) {
      throw new EdmException(EdmException.NAVIGATIONPROPERTYNOTFOUND,navigationProperty.getName());
    }
    EdmEntitySet targetEntitySet = toEnd.getEntitySet();
    if (targetEntitySet == null) {
      throw new EdmException(EdmException.NAVIGATIONPROPERTYNOTFOUND,navigationProperty.getName());
    }
    return targetEntitySet;
  }

  /**
   * Gets the entity container.
   *
   * @return the entity container
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityContainer getEntityContainer() throws EdmException {
    return edmEntityContainer;
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
   * Gets the mapping.
   *
   * @return the mapping
   * @throws EdmException the edm exception
   */
  @Override
  public EdmMapping getMapping() throws EdmException {
    return mapping;
  }

  /**
   * Sets the edm entity type name.
   *
   * @param fqName the new edm entity type name
   */
  public void setEdmEntityTypeName(FullQualifiedName fqName) {
    this.entityTypeName = fqName;    
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
