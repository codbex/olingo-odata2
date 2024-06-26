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

import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmType;

// TODO: Auto-generated Javadoc
/**
 * The Class RefBase.
 */
@EdmEntityType(name = "Base", namespace = ModelSharedConstants.NAMESPACE_1)
public abstract class RefBase {
  
  /** The name. */
  @EdmProperty(name = "Name")
  protected String name;
  
  /** The id. */
  @EdmProperty(name = "Id", type = EdmType.STRING, facets = @EdmFacets(nullable = false))
  @EdmKey
  protected int id;

  /**
   * Instantiates a new ref base.
   *
   * @param id the id
   * @param name the name
   */
  public RefBase(final int id, final String name) {
    this.name = name;
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
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return Integer.toString(id);
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final int id) {
    this.id = id;
  }
}
