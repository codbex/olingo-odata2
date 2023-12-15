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
package org.apache.olingo.odata2.jpa.processor.core.mock.model;

import java.util.Set;

import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.Metamodel;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAMetaModelMock.
 */
public class JPAMetaModelMock implements Metamodel {

  /**
   * Embeddable.
   *
   * @param <X> the generic type
   * @param arg0 the arg 0
   * @return the embeddable type
   */
  @Override
  public <X> EmbeddableType<X> embeddable(final Class<X> arg0) {
    return null;
  }

  /**
   * Entity.
   *
   * @param <X> the generic type
   * @param arg0 the arg 0
   * @return the entity type
   */
  @Override
  public <X> EntityType<X> entity(final Class<X> arg0) {
    return null;
  }

  /**
   * Gets the embeddables.
   *
   * @return the embeddables
   */
  @Override
  public Set<EmbeddableType<?>> getEmbeddables() {
    return null;
  }

  /**
   * Gets the entities.
   *
   * @return the entities
   */
  @Override
  public Set<EntityType<?>> getEntities() {
    return null;
  }

  /**
   * Gets the managed types.
   *
   * @return the managed types
   */
  @Override
  public Set<ManagedType<?>> getManagedTypes() {
    return null;
  }

  /**
   * Managed type.
   *
   * @param <X> the generic type
   * @param arg0 the arg 0
   * @return the managed type
   */
  @Override
  public <X> ManagedType<X> managedType(final Class<X> arg0) {
    return null;
  }

}
