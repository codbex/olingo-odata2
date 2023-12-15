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

import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.MapAttribute;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAManagedTypeMock.
 *
 * @param <X> the generic type
 */
public class JPAManagedTypeMock<X> implements ManagedType<X> {

  /**
   * Gets the java type.
   *
   * @return the java type
   */
  @Override
  public Class<X> getJavaType() {
    return null;
  }

  /**
   * Gets the persistence type.
   *
   * @return the persistence type
   */
  @Override
  public jakarta.persistence.metamodel.Type.PersistenceType getPersistenceType() {
    return null;
  }

  /**
   * Gets the attribute.
   *
   * @param arg0 the arg 0
   * @return the attribute
   */
  @Override
  public Attribute<? super X, ?> getAttribute(final String arg0) {
    return null;
  }

  /**
   * Gets the attributes.
   *
   * @return the attributes
   */
  @Override
  public Set<Attribute<? super X, ?>> getAttributes() {
    return null;
  }

  /**
   * Gets the collection.
   *
   * @param arg0 the arg 0
   * @return the collection
   */
  @Override
  public CollectionAttribute<? super X, ?> getCollection(final String arg0) {
    return null;
  }

  /**
   * Gets the collection.
   *
   * @param <E> the element type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @return the collection
   */
  @Override
  public <E> CollectionAttribute<? super X, E> getCollection(final String arg0, final Class<E> arg1) {
    return null;
  }

  /**
   * Gets the declared attribute.
   *
   * @param arg0 the arg 0
   * @return the declared attribute
   */
  @Override
  public Attribute<X, ?> getDeclaredAttribute(final String arg0) {
    return null;
  }

  /**
   * Gets the declared attributes.
   *
   * @return the declared attributes
   */
  @Override
  public Set<Attribute<X, ?>> getDeclaredAttributes() {
    return null;
  }

  /**
   * Gets the declared collection.
   *
   * @param arg0 the arg 0
   * @return the declared collection
   */
  @Override
  public CollectionAttribute<X, ?> getDeclaredCollection(final String arg0) {
    return null;
  }

  /**
   * Gets the declared collection.
   *
   * @param <E> the element type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @return the declared collection
   */
  @Override
  public <E> CollectionAttribute<X, E> getDeclaredCollection(final String arg0, final Class<E> arg1) {
    return null;
  }

  /**
   * Gets the declared list.
   *
   * @param arg0 the arg 0
   * @return the declared list
   */
  @Override
  public ListAttribute<X, ?> getDeclaredList(final String arg0) {
    return null;
  }

  /**
   * Gets the declared list.
   *
   * @param <E> the element type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @return the declared list
   */
  @Override
  public <E> ListAttribute<X, E> getDeclaredList(final String arg0, final Class<E> arg1) {
    return null;
  }

  /**
   * Gets the declared map.
   *
   * @param arg0 the arg 0
   * @return the declared map
   */
  @Override
  public MapAttribute<X, ?, ?> getDeclaredMap(final String arg0) {
    return null;
  }

  /**
   * Gets the declared map.
   *
   * @param <K> the key type
   * @param <V> the value type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @param arg2 the arg 2
   * @return the declared map
   */
  @Override
  public <K, V> MapAttribute<X, K, V> getDeclaredMap(final String arg0, final Class<K> arg1, final Class<V> arg2) {
    return null;
  }

  /**
   * Gets the declared plural attributes.
   *
   * @return the declared plural attributes
   */
  @Override
  public Set<PluralAttribute<X, ?, ?>> getDeclaredPluralAttributes() {
    return null;
  }

  /**
   * Gets the declared set.
   *
   * @param arg0 the arg 0
   * @return the declared set
   */
  @Override
  public SetAttribute<X, ?> getDeclaredSet(final String arg0) {
    return null;
  }

  /**
   * Gets the declared set.
   *
   * @param <E> the element type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @return the declared set
   */
  @Override
  public <E> SetAttribute<X, E> getDeclaredSet(final String arg0, final Class<E> arg1) {
    return null;
  }

  /**
   * Gets the declared singular attribute.
   *
   * @param arg0 the arg 0
   * @return the declared singular attribute
   */
  @Override
  public SingularAttribute<X, ?> getDeclaredSingularAttribute(final String arg0) {
    return null;
  }

  /**
   * Gets the declared singular attribute.
   *
   * @param <Y> the generic type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @return the declared singular attribute
   */
  @Override
  public <Y> SingularAttribute<X, Y> getDeclaredSingularAttribute(final String arg0, final Class<Y> arg1) {
    return null;
  }

  /**
   * Gets the declared singular attributes.
   *
   * @return the declared singular attributes
   */
  @Override
  public Set<SingularAttribute<X, ?>> getDeclaredSingularAttributes() {
    return null;
  }

  /**
   * Gets the list.
   *
   * @param arg0 the arg 0
   * @return the list
   */
  @Override
  public ListAttribute<? super X, ?> getList(final String arg0) {
    return null;
  }

  /**
   * Gets the list.
   *
   * @param <E> the element type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @return the list
   */
  @Override
  public <E> ListAttribute<? super X, E> getList(final String arg0, final Class<E> arg1) {
    return null;
  }

  /**
   * Gets the map.
   *
   * @param arg0 the arg 0
   * @return the map
   */
  @Override
  public MapAttribute<? super X, ?, ?> getMap(final String arg0) {
    return null;
  }

  /**
   * Gets the map.
   *
   * @param <K> the key type
   * @param <V> the value type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @param arg2 the arg 2
   * @return the map
   */
  @Override
  public <K, V> MapAttribute<? super X, K, V> getMap(final String arg0, final Class<K> arg1, final Class<V> arg2) {
    return null;
  }

  /**
   * Gets the plural attributes.
   *
   * @return the plural attributes
   */
  @Override
  public Set<PluralAttribute<? super X, ?, ?>> getPluralAttributes() {
    return null;
  }

  /**
   * Gets the set.
   *
   * @param arg0 the arg 0
   * @return the sets the
   */
  @Override
  public SetAttribute<? super X, ?> getSet(final String arg0) {
    return null;
  }

  /**
   * Gets the set.
   *
   * @param <E> the element type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @return the sets the
   */
  @Override
  public <E> SetAttribute<? super X, E> getSet(final String arg0, final Class<E> arg1) {
    return null;
  }

  /**
   * Gets the singular attribute.
   *
   * @param arg0 the arg 0
   * @return the singular attribute
   */
  @Override
  public SingularAttribute<? super X, ?> getSingularAttribute(final String arg0) {
    return null;
  }

  /**
   * Gets the singular attribute.
   *
   * @param <Y> the generic type
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @return the singular attribute
   */
  @Override
  public <Y> SingularAttribute<? super X, Y> getSingularAttribute(final String arg0, final Class<Y> arg1) {
    return null;
  }

  /**
   * Gets the singular attributes.
   *
   * @return the singular attributes
   */
  @Override
  public Set<SingularAttribute<? super X, ?>> getSingularAttributes() {
    return null;
  }

}
