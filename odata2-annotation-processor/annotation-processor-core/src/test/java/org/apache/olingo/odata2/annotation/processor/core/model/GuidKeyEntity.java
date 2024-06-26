/*
 * Copyright 2013 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.olingo.odata2.annotation.processor.core.model;

import java.util.UUID;

import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmType;

// TODO: Auto-generated Javadoc
/**
 * The Class GuidKeyEntity.
 */
@EdmEntityType(name = "GuidKeyEntity", namespace = ModelSharedConstants.NAMESPACE_1)
@EdmEntitySet(name = GuidKeyEntity.GUID_KEY_ENTITIES)
public class GuidKeyEntity {

  /** The Constant GUID_KEY_ENTITIES. */
  public static final String GUID_KEY_ENTITIES = "GuidKeyEntities";
  
  /** The id. */
  @EdmProperty(name = "Id", type = EdmType.GUID, facets = @EdmFacets(nullable = false))
  @EdmKey
  protected UUID id;

  /** The name. */
  @EdmProperty(name = "Name")
  protected String name;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public UUID getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(UUID id) {
    this.id = id;
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
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

}
