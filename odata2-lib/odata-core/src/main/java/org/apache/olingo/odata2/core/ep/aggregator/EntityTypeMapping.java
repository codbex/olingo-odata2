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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.olingo.odata2.api.ep.EntityProviderException;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityTypeMapping.
 */
public class EntityTypeMapping {
  
  /** The Constant ENTITY_TYPE_MAPPING. */
  private static final EntityTypeMapping ENTITY_TYPE_MAPPING = new EntityTypeMapping();
  
  /** The property name. */
  final String propertyName;
  
  /** The mapping. */
  final Class<?> mapping;
  
  /** The mappings. */
  final List<EntityTypeMapping> mappings;

  /**
   * Instantiates a new entity type mapping.
   */
  private EntityTypeMapping() {
    this(null, Object.class);
  }

  /**
   * Instantiates a new entity type mapping.
   *
   * @param name the name
   * @param mappingClass the mapping class
   */
  private EntityTypeMapping(final String name, final Class<?> mappingClass) {
    propertyName = name;
    mapping = mappingClass;
    mappings = Collections.emptyList();
  }

  /**
   * Instantiates a new entity type mapping.
   *
   * @param name the name
   * @param typeMappings the type mappings
   */
  private EntityTypeMapping(final String name, final List<EntityTypeMapping> typeMappings) {
    propertyName = name;
    mapping = EntityTypeMapping.class;
    List<EntityTypeMapping> tmp = new ArrayList<EntityTypeMapping>();
    for (EntityTypeMapping typeMapping : typeMappings) {
      tmp.add(typeMapping);
    }
    mappings = Collections.unmodifiableList(tmp);
  }

  /**
   * Creates the.
   *
   * @param mappings the mappings
   * @return the entity type mapping
   * @throws EntityProviderException the entity provider exception
   */
  public static EntityTypeMapping create(final Map<String, Object> mappings) throws EntityProviderException {
    return create(null, mappings);
  }

  /**
   * Creates the.
   *
   * @param name the name
   * @param mappings the mappings
   * @return the entity type mapping
   * @throws EntityProviderException the entity provider exception
   */
  @SuppressWarnings("unchecked")
  public static EntityTypeMapping create(final String name, final Map<String, Object> mappings)
      throws EntityProviderException {
    if (mappings == null) {
      return ENTITY_TYPE_MAPPING;
    }
    List<EntityTypeMapping> typeMappings = new ArrayList<EntityTypeMapping>();
    Set<Entry<String, Object>> entries = mappings.entrySet();
    for (Entry<String, Object> entry : entries) {
      EntityTypeMapping typeMapping;
      Object value = entry.getValue();
      if (value instanceof Map) {
        typeMapping = create(entry.getKey(), (Map<String, Object>) value);
      } else if (value instanceof Class) {
        typeMapping = new EntityTypeMapping(entry.getKey(), (Class<?>) value);
      } else {
        throw new EntityProviderException(EntityProviderException.INVALID_MAPPING.addContent(entry.getKey()));
      }
      typeMappings.add(typeMapping);
    }

    return new EntityTypeMapping(name, typeMappings);
  }

  /**
   * Checks if is complex.
   *
   * @return true, if is complex
   */
  boolean isComplex() {
    return mappings != null && mapping == EntityTypeMapping.class;
  }

  /**
   * If this {@link EntityTypeMapping} is complex the mapping for the property
   * with the given <code>name</code> is returned; otherwise an empty {@link EntityTypeMapping} is returned.
   *
   * @param name the name
   * @return the mapping for this entity type
   */
  public EntityTypeMapping getEntityTypeMapping(final String name) {
    if (isComplex()) {
      for (EntityTypeMapping mappingValue : mappings) {
        if (mappingValue.propertyName.equals(name)) {
          return mappingValue;
        }
      }
    }
    return ENTITY_TYPE_MAPPING;
  }

  /**
   * If this {@link EntityTypeMapping} is complex the mapping {@link Class} for the property
   * with the given <code>name</code> is returned; otherwise <code>NULL</code> is returned.
   *
   * @param name the name
   * @return mapping {@link Class} for the property with given <code>name</code> or <code>NULL</code>.
   */
  public Class<?> getMappingClass(final String name) {
    if (isComplex()) {
      for (EntityTypeMapping mappingValue : mappings) {
        if (mappingValue.propertyName.equals(name)) {
          return mappingValue.mapping;
        }
      }
    }
    return null;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    if (isComplex()) {
      return "{'" + propertyName + "'->" + mappings.toString() + "}";
    }
    return "{'" + propertyName + "' as " + (mapping == null ? "NULL" : mapping.getSimpleName()) + "}";
  }

}
