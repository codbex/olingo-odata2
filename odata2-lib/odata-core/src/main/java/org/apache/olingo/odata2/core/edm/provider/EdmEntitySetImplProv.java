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
import org.apache.olingo.odata2.api.edm.EdmAssociationSet;
import org.apache.olingo.odata2.api.edm.EdmAssociationSetEnd;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmEntitySetImplProv.
 */
public class EdmEntitySetImplProv extends EdmNamedImplProv implements EdmEntitySet, EdmAnnotatable {

  /** The entity set. */
  private EntitySet entitySet;
  
  /** The edm entity container. */
  private EdmEntityContainer edmEntityContainer;
  
  /** The edm entity type. */
  private EdmEntityType edmEntityType;
  
  /** The annotations. */
  private EdmAnnotationsImplProv annotations;

  /**
   * Instantiates a new edm entity set impl prov.
   *
   * @param edm the edm
   * @param entitySet the entity set
   * @param edmEntityContainer the edm entity container
   * @throws EdmException the edm exception
   */
  public EdmEntitySetImplProv(final EdmImplProv edm, final EntitySet entitySet,
      final EdmEntityContainer edmEntityContainer) throws EdmException {
    super(edm, entitySet.getName());
    this.entitySet = entitySet;
    this.edmEntityContainer = edmEntityContainer;
  }

  /**
   * Gets the entity type.
   *
   * @return the entity type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityType getEntityType() throws EdmException {
    if (edmEntityType == null) {
      FullQualifiedName fqName = entitySet.getEntityType();
      edmEntityType = edm.getEntityType(fqName.getNamespace(), fqName.getName());
      if (edmEntityType == null) {
        throw new EdmException(EdmException.COMMON);
      }
    }
    return edmEntityType;
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
        edmEntityContainer.getAssociationSet(edmEntityContainer.getEntitySet(entitySet.getName()), navigationProperty);
    EdmAssociationSetEnd toEnd = associationSet.getEnd(navigationProperty.getToRole());
    if (toEnd == null) {
      throw new EdmException(EdmException.COMMON);
    }
    EdmEntitySet targetEntitySet = toEnd.getEntitySet();
    if (targetEntitySet == null) {
      throw new EdmException(EdmException.COMMON);
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
    if (annotations == null) {
      annotations = new EdmAnnotationsImplProv(entitySet.getAnnotationAttributes(), entitySet.getAnnotationElements());
    }
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
    return entitySet.getMapping();
  }
}
