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
package org.apache.olingo.odata2.jpa.processor.core.model;

import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import jakarta.persistence.EntityListeners;

import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATombstoneEntityListener;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmMappingModelAccess;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityTypeView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmKeyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmNavigationPropertyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmPropertyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import org.apache.olingo.odata2.jpa.processor.core.access.model.JPAEdmNameBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmEntityType.
 */
public class JPAEdmEntityType extends JPAEdmBaseViewImpl implements JPAEdmEntityTypeView {

  /** The schema view. */
  private JPAEdmSchemaView schemaView = null;
  
  /** The current edm entity type. */
  private EntityType currentEdmEntityType = null;
  
  /** The current JPA entity type. */
  private jakarta.persistence.metamodel.EntityType<?> currentJPAEntityType = null;
  
  /** The consistent entity types. */
  private EntityTypeList<EntityType> consistentEntityTypes = null;

  /** The consistent entity type map. */
  private HashMap<String, EntityType> consistentEntityTypeMap;

  /**
   * Instantiates a new JPA edm entity type.
   *
   * @param view the view
   */
  public JPAEdmEntityType(final JPAEdmSchemaView view) {
    super(view);
    schemaView = view;
    consistentEntityTypeMap = new HashMap<String, EntityType>();
  }

  /**
   * Gets the builder.
   *
   * @return the builder
   */
  @Override
  public JPAEdmBuilder getBuilder() {
    if (builder == null) {
      builder = new JPAEdmEntityTypeBuilder();
    }

    return builder;
  }

  /**
   * Gets the edm entity type.
   *
   * @return the edm entity type
   */
  @Override
  public EntityType getEdmEntityType() {
    return currentEdmEntityType;
  }

  /**
   * Gets the JPA entity type.
   *
   * @return the JPA entity type
   */
  @Override
  public jakarta.persistence.metamodel.EntityType<?> getJPAEntityType() {
    return currentJPAEntityType;
  }

  /**
   * Gets the consistent edm entity types.
   *
   * @return the consistent edm entity types
   */
  @Override
  public List<EntityType> getConsistentEdmEntityTypes() {
    return consistentEntityTypes;
  }

  /**
   * Search edm entity type.
   *
   * @param jpaEntityTypeName the jpa entity type name
   * @return the entity type
   */
  @Override
  public EntityType searchEdmEntityType(final String jpaEntityTypeName) {
    return consistentEntityTypeMap.get(jpaEntityTypeName);
  }

  /**
   * The Class JPAEdmEntityTypeBuilder.
   */
  private class JPAEdmEntityTypeBuilder implements JPAEdmBuilder {

    /**
     * Builds the.
     *
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public void build() throws ODataJPAModelException, ODataJPARuntimeException {

      Collection<jakarta.persistence.metamodel.EntityType<?>> jpaEntityTypes = metaModel.getEntities();

      if (jpaEntityTypes == null || jpaEntityTypes.isEmpty() == true) {
        return;
      } else if (consistentEntityTypes == null) {
        consistentEntityTypes = new EntityTypeList<EntityType>();

      }

      jpaEntityTypes = sortJPAEntityTypes(jpaEntityTypes);
      for (jakarta.persistence.metamodel.EntityType<?> jpaEntityType : jpaEntityTypes) {
        currentEdmEntityType = new EntityType();
        currentJPAEntityType = jpaEntityType;

        // Check for need to Exclude
        if (isExcluded(JPAEdmEntityType.this)) {
          continue;
        }

        JPAEdmNameBuilder.build(JPAEdmEntityType.this);
        JPAEdmMapping jpaEdmMapping = (JPAEdmMapping) currentEdmEntityType.getMapping();
        EntityListeners entityListners = currentJPAEntityType.getJavaType().getAnnotation(EntityListeners.class);
        if (entityListners != null) {
          for (Class<EntityListeners> entityListner : entityListners.value()) {
            if (ODataJPATombstoneEntityListener.class.isAssignableFrom(entityListner)) {
              jpaEdmMapping
                  .setODataJPATombstoneEntityListener((Class<? extends ODataJPATombstoneEntityListener>)
                  (Object) entityListner);
              break;
            }
          }
        }
        JPAEdmPropertyView propertyView = new JPAEdmProperty(schemaView);
        propertyView.getBuilder().build();

        currentEdmEntityType.setProperties(propertyView.getEdmPropertyList());
        if (propertyView.getJPAEdmNavigationPropertyView() != null) {
          JPAEdmNavigationPropertyView navPropView = propertyView.getJPAEdmNavigationPropertyView();
          if (navPropView.getConsistentEdmNavigationProperties() != null
              && !navPropView.getConsistentEdmNavigationProperties().isEmpty()) {
            currentEdmEntityType.setNavigationProperties(navPropView.getConsistentEdmNavigationProperties());
          }
        }
        JPAEdmKeyView keyView = propertyView.getJPAEdmKeyView();
        currentEdmEntityType.setKey(keyView.getEdmKey());

        consistentEntityTypes.add(currentEdmEntityType);
        consistentEntityTypeMap.put(currentJPAEntityType.getName(), currentEdmEntityType);
      }

    }

    /**
     * Sort JPA entity types.
     *
     * @param entities the entities
     * @return the list
     */
    private List<jakarta.persistence.metamodel.EntityType<?>> sortJPAEntityTypes(
      final Collection<jakarta.persistence.metamodel.EntityType<?>> entities) {

      List<jakarta.persistence.metamodel.EntityType<?>> entityTypeList =
        new ArrayList<jakarta.persistence.metamodel.EntityType<?>>(entities.size());

        Iterator<jakarta.persistence.metamodel.EntityType<?>> itr;
       jakarta.persistence.metamodel.EntityType<?> smallestJpaEntity;
       jakarta.persistence.metamodel.EntityType<?> currentJpaEntity;
        while (!entities.isEmpty()) {
          itr = entities.iterator();
          smallestJpaEntity = itr.next();
          while (itr.hasNext()) {
            currentJpaEntity = itr.next();
            if (smallestJpaEntity.getName().compareTo(currentJpaEntity.getName()) > 0) {
              smallestJpaEntity = currentJpaEntity;
            }
          }
          entityTypeList.add(smallestJpaEntity);
          entities.remove(smallestJpaEntity);
        }

      return entityTypeList;
    }

    /**
     * Checks if is excluded.
     *
     * @param jpaEdmEntityType the jpa edm entity type
     * @return true, if is excluded
     */
    private boolean isExcluded(final JPAEdmEntityType jpaEdmEntityType) {
      JPAEdmMappingModelAccess mappingModelAccess = jpaEdmEntityType.getJPAEdmMappingModelAccess();
      if (mappingModelAccess != null && mappingModelAccess.isMappingModelExists()
          && mappingModelAccess.checkExclusionOfJPAEntityType(jpaEdmEntityType.getJPAEntityType().getName())) {
        return true;
      }
      return false;
    }

  }

  /**
   * The Class EntityTypeList.
   *
   * @param <X> the generic type
   */
  private class EntityTypeList<X> extends ArrayList<EntityType> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 719079109608251592L;

    /**
     * Iterator.
     *
     * @return the iterator
     */
    @Override
    public Iterator<EntityType> iterator() {
      return new EntityTypeListIterator<X>(size());
    }

  }

  /**
   * The Class EntityTypeListIterator.
   *
   * @param <E> the element type
   */
  private class EntityTypeListIterator<E> implements ListIterator<EntityType> {

    /** The size. */
    private int size = 0;
    
    /** The pos. */
    private int pos = 0;

    /**
     * Instantiates a new entity type list iterator.
     *
     * @param listSize the list size
     */
    public EntityTypeListIterator(final int listSize) {
      this.size = listSize;
    }

    /**
     * Adds the.
     *
     * @param e the e
     */
    @Override
    public void add(final EntityType e) {
      consistentEntityTypes.add(e);
      size++;
    }

    /**
     * Checks for next.
     *
     * @return true, if successful
     */
    @Override
    public boolean hasNext() {
      if (pos < size) {
        return true;
      }

      return false;
    }

    /**
     * Checks for previous.
     *
     * @return true, if successful
     */
    @Override
    public boolean hasPrevious() {
      if (pos > 0) {
        return true;
      }
      return false;
    }

    /**
     * Next.
     *
     * @return the entity type
     */
    @Override
    public EntityType next() {
      if (pos < size) {
        currentEdmEntityType = consistentEntityTypes.get(pos++);
        return currentEdmEntityType;
      }

      return null;
    }

    /**
     * Next index.
     *
     * @return the int
     */
    @Override
    public int nextIndex() {
      return pos;
    }

    /**
     * Previous.
     *
     * @return the entity type
     */
    @Override
    public EntityType previous() {
      if (pos > 0 && pos < size) {
        currentEdmEntityType = consistentEntityTypes.get(--pos);
        return currentEdmEntityType;
      }
      return null;
    }

    /**
     * Previous index.
     *
     * @return the int
     */
    @Override
    public int previousIndex() {
      if (pos > 0) {
        return pos - 1;
      }

      return 0;
    }

    /**
     * Removes the.
     */
    @Override
    public void remove() {
      consistentEntityTypes.remove(pos);
    }

    /**
     * Sets the.
     *
     * @param e the e
     */
    @Override
    public void set(final EntityType e) {
      consistentEntityTypes.set(pos, e);
    }

  }
}
