/*
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
 */
package org.apache.olingo.odata2.client.api.ep;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Data representation for a single entity.
 */
public class EntityCollection {

  /** The entities. */
  private final List<Entity> entities = new ArrayList<Entity>();
  
  /** The collection properties. */
  private EntityCollectionSerializerProperties collectionProperties;
  
  /** The global entity properties. */
  private EntitySerializerProperties globalEntityProperties;
 
  /**
   * Gets the global entity properties.
   *
   * @return the globalWriteProperties
   */
  public EntitySerializerProperties getGlobalEntityProperties() {
    return globalEntityProperties;
  }

  /**
   * Sets the global entity properties.
   *
   * @param globalEntityProperties the new global entity properties
   */
  public void setGlobalEntityProperties(EntitySerializerProperties globalEntityProperties) {
    this.globalEntityProperties = globalEntityProperties;
  }

  /**
   * Gets the entities.
   *
   * @return List of Entities
   */
  public List<Entity> getEntities() {
    return entities;
  }
  
  /**
   * Add an Entity to collection of entities.
   *
   * @param entity the entity
   */
  public void addEntity(Entity entity) {
    entities.add(entity);
  }

  /**
   * Return collection write properties.
   *
   * @return EntityCollectionSerializerProperties
   */
  public EntityCollectionSerializerProperties getCollectionProperties() {
    return collectionProperties;
  }

  /**
   * Set collection write properties.
   *
   * @param collectionProperties the new collection properties
   */
  public void setCollectionProperties(EntityCollectionSerializerProperties collectionProperties) {
    this.collectionProperties = collectionProperties;
  }

}
