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
package org.apache.olingo.odata2.annotation.processor.core.datasource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.olingo.odata2.annotation.processor.core.util.AnnotationHelper;
import org.apache.olingo.odata2.annotation.processor.core.util.AnnotationRuntimeException;
import org.apache.olingo.odata2.annotation.processor.core.util.ClassHelper;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;

// TODO: Auto-generated Javadoc
/**
 * The Class DataStore.
 *
 * @param <T> the generic type
 */
public class DataStore<T> {

  /** The Constant ANNOTATION_HELPER. */
  private static final AnnotationHelper ANNOTATION_HELPER = new AnnotationHelper();
  
  /** The data store. */
  private final Map<KeyElement, T> dataStore;
  
  /** The data type class. */
  private final Class<T> dataTypeClass;
  
  /** The key access. */
  private final KeyAccess keyAccess;

  /**
   * The Class InMemoryDataStore.
   */
  private static class InMemoryDataStore {
    
    /** The Constant c2ds. */
    private static final Map<Class<?>, DataStore<?>> c2ds = new HashMap<Class<?>, DataStore<?>>();

    /**
     * Gets the single instance of InMemoryDataStore.
     *
     * @param clz the clz
     * @param createNewInstance the create new instance
     * @return single instance of InMemoryDataStore
     * @throws DataStoreException the data store exception
     */
    @SuppressWarnings("unchecked")
    static synchronized DataStore<?> getInstance(final Class<?> clz, final boolean createNewInstance)
        throws DataStoreException {
      DataStore<?> ds = c2ds.get(clz);
      if (createNewInstance || ds == null) {
        ds = new DataStore<Object>((Class<Object>) clz);
        c2ds.put(clz, ds);
      }
      return ds;
    }
  }

  /**
   * Creates the in memory.
   *
   * @param <T> the generic type
   * @param clazz the clazz
   * @return the data store
   * @throws DataStoreException the data store exception
   */
  @SuppressWarnings("unchecked")
  public static <T> DataStore<T> createInMemory(final Class<T> clazz) throws DataStoreException {
    return (DataStore<T>) InMemoryDataStore.getInstance(clazz, true);
  }

  /**
   * Creates the in memory.
   *
   * @param <T> the generic type
   * @param clazz the clazz
   * @param keepExisting the keep existing
   * @return the data store
   * @throws DataStoreException the data store exception
   */
  @SuppressWarnings("unchecked")
  public static <T> DataStore<T> createInMemory(final Class<T> clazz, final boolean keepExisting)
      throws DataStoreException {
    return (DataStore<T>) InMemoryDataStore.getInstance(clazz, !keepExisting);
  }

  /**
   * Instantiates a new data store.
   *
   * @param wrapStore the wrap store
   * @param clz the clz
   * @throws DataStoreException the data store exception
   */
  private DataStore(final Map<KeyElement, T> wrapStore, final Class<T> clz) throws DataStoreException {
    dataStore = Collections.synchronizedMap(wrapStore);
    dataTypeClass = clz;
    keyAccess = new KeyAccess(clz);
  }

  /**
   * Instantiates a new data store.
   *
   * @param clz the clz
   * @throws DataStoreException the data store exception
   */
  private DataStore(final Class<T> clz) throws DataStoreException {
    this(new HashMap<KeyElement, T>(), clz);
  }

  /**
   * Gets the data type class.
   *
   * @return the data type class
   */
  public Class<T> getDataTypeClass() {
    return dataTypeClass;
  }

  /**
   * Gets the entity type name.
   *
   * @return the entity type name
   */
  public String getEntityTypeName() {
    return ANNOTATION_HELPER.extractEntityTypeName(dataTypeClass);
  }

  /**
   * Creates the instance.
   *
   * @return the t
   */
  public T createInstance() {
    try {
      return dataTypeClass.newInstance();
    } catch (InstantiationException e) {
      throw new AnnotationRuntimeException("Unable to create instance of class '" + dataTypeClass + "'.", e);
    } catch (IllegalAccessException e) {
      throw new AnnotationRuntimeException("Unable to create instance of class '" + dataTypeClass + "'.", e);
    }
  }

  /**
   * Read.
   *
   * @param obj the obj
   * @return the t
   */
  public T read(final T obj) {
    KeyElement objKeys = getKeys(obj);
    return dataStore.get(objKeys);
  }

  /**
   * Read.
   *
   * @return the collection
   */
  public Collection<T> read() {
    return Collections.unmodifiableCollection(dataStore.values());
  }

  /**
   * Creates the.
   *
   * @param object the object
   * @return the t
   * @throws DataStoreException the data store exception
   */
  public T create(final T object) throws DataStoreException {
    KeyElement keyElement = getKeys(object);
    return create(object, keyElement);
  }

  /**
   * Store an entity, preserving any existing keys if possible. If the combination of
   * existing and generated keys would produce a duplicate entry, replace all keys.
   *
   * @param object the object
   * @param keyElement the key element
   * @return the t
   * @throws DataStoreException the data store exception
   */
  private T create(final T object, final KeyElement keyElement) throws DataStoreException {
    synchronized (dataStore) {
      final boolean replaceKeys = dataStore.containsKey(keyElement);
      if (keyElement.keyValuesMissing() || replaceKeys) {
        KeyElement newKey = createSetAndGetKeys(object, replaceKeys);
        return this.create(object, newKey);
      }
      dataStore.put(keyElement, object);
    }
    return object;
  }

  /**
   * Update.
   *
   * @param object the object
   * @return the t
   */
  public T update(final T object) {
    KeyElement keyElement = getKeys(object);
    synchronized (dataStore) {
      dataStore.remove(keyElement);
      dataStore.put(keyElement, object);
    }
    return object;
  }

  /**
   * Delete.
   *
   * @param object the object
   * @return the t
   */
  public T delete(final T object) {
    KeyElement keyElement = getKeys(object);
    synchronized (dataStore) {
      return dataStore.remove(keyElement);
    }
  }

  /**
   * Are the key values equal for both instances.
   * If all compared key values are <code>null</code> this also means equal.
   * 
   * @param first first instance to check for key equal
   * @param second second instance to check for key equal
   * @return <code>true</code> if object instance have equal keys set.
   */
  public boolean isKeyEqual(final T first, final T second) {
    KeyElement firstKeys = getKeys(first);
    KeyElement secondKeys = getKeys(second);

    return firstKeys.equals(secondKeys);
  }

  /**
   * Are the key values equal for both instances.
   * If all compared key values are <code>null</code> this also means equal.
   * Before object (keys) are compared it is validated that both object instance are NOT null
   * and that both are from the same class as this {@link DataStore} (see {@link #dataTypeClass}).
   * For the equal check on {@link #dataTypeClass} instances without validation see {@link #isKeyEqual(Object, Object)}.
   *
   * @param first first instance to check for key equal
   * @param second second instance to check for key equal
   * @return <code>true</code> if object instance have equal keys set.
   * @throws DataStoreException the data store exception
   */
  @SuppressWarnings("unchecked")
  public boolean isKeyEqualChecked(final Object first, final Object second) throws DataStoreException {
    if (first == null || second == null) {
      throw new DataStoreException("Tried to compare null values which is not allowed.");
    } else if (first.getClass() != dataTypeClass) {
      throw new DataStoreException("First value is no instance from required class '" + dataTypeClass + "'.");
    } else if (second.getClass() != dataTypeClass) {
      throw new DataStoreException("Second value is no instance from required class '" + dataTypeClass + "'.");
    }

    return isKeyEqual((T) first, (T) second);
  }

  /**
   * The Class KeyElement.
   */
  private class KeyElement {
    
    /** The cached hash code. */
    private int cachedHashCode = 42;
    
    /** The key values. */
    private final List<Object> keyValues;

    /**
     * Instantiates a new key element.
     *
     * @param size the size
     */
    public KeyElement(final int size) {
      keyValues = new ArrayList<Object>(size);
    }

    /**
     * Adds the value.
     *
     * @param keyValue the key value
     */
    private void addValue(final Object keyValue) {
      keyValues.add(keyValue);
      cachedHashCode = 89 * cachedHashCode + (keyValue != null ? keyValue.hashCode() : 0);
    }

    /**
     * Key values missing.
     *
     * @return true, if successful
     */
    boolean keyValuesMissing() {
      return keyValues.contains(null);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
      return cachedHashCode;
    }

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(final Object obj) {
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      @SuppressWarnings("unchecked")
      final KeyElement other = (KeyElement) obj;
      if (this.keyValues != other.keyValues && (this.keyValues == null || !this.keyValues.equals(other.keyValues))) {
        return false;
      }
      return true;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
      return "KeyElement{" + "cachedHashCode=" + cachedHashCode + ", keyValues=" + keyValues + '}';
    }
  }

  /**
   * The Class KeyAccess.
   */
  private class KeyAccess {
    
    /** The key fields. */
    final List<Field> keyFields;
    
    /** The id counter. */
    final AtomicInteger idCounter = new AtomicInteger(1);

    /**
     * Instantiates a new key access.
     *
     * @param clazz the clazz
     * @throws DataStoreException the data store exception
     */
    KeyAccess(final Class<?> clazz) throws DataStoreException {
      keyFields = ANNOTATION_HELPER.getAnnotatedFields(clazz, EdmKey.class);
      if (keyFields.isEmpty()) {
        throw new DataStoreException("No EdmKey annotated fields found for class " + clazz);
      }
    }

    /**
     * Gets the key values.
     *
     * @param object the object
     * @return the key values
     */
    KeyElement getKeyValues(final T object) {
      KeyElement keyElement = new KeyElement(keyFields.size());
      for (Field field : keyFields) {
        Object keyValue = ClassHelper.getFieldValue(object, field);
        keyElement.addValue(keyValue);
      }

      return keyElement;
    }

    /**
     * Creates the set and get keys.
     *
     * @param object the object
     * @param replaceKeys the replace keys
     * @return the key element
     * @throws DataStoreException the data store exception
     */
    KeyElement createSetAndGetKeys(final T object, boolean replaceKeys) throws DataStoreException {
      KeyElement keyElement = new KeyElement(keyFields.size());
      for (Field field : keyFields) {
        Object key = ClassHelper.getFieldValue(object, field);
        if (key == null || replaceKeys) {
          key = createKey(field);
          ClassHelper.setFieldValue(object, field, key);
        }
        keyElement.addValue(key);
      }

      return keyElement;
    }

    /**
     * Creates the key.
     *
     * @param field the field
     * @return the object
     */
    private Object createKey(final Field field) {
      Class<?> type = field.getType();

      if (type == String.class) {
        return String.valueOf(idCounter.getAndIncrement());
      } else if (type == Integer.class || type == int.class) {
        return Integer.valueOf(idCounter.getAndIncrement());
      } else if (type == Long.class || type == long.class) {
        return Long.valueOf(idCounter.getAndIncrement());
      } else if (type == UUID.class) {
        return UUID.randomUUID();
      }

      throw new UnsupportedOperationException("Automated key generation for type '" + type
          + "' is not supported (caused on field '" + field + "').");
    }
  }

  /**
   * Gets the keys.
   *
   * @param object the object
   * @return the keys
   */
  private KeyElement getKeys(final T object) {
    return keyAccess.getKeyValues(object);
  }

  /**
   * Creates the set and get keys.
   *
   * @param object the object
   * @param replaceKeys the replace keys
   * @return the key element
   * @throws DataStoreException the data store exception
   */
  private KeyElement createSetAndGetKeys(final T object, boolean replaceKeys) throws DataStoreException {
    return keyAccess.createSetAndGetKeys(object, replaceKeys);
  }

  /**
   * The Class DataStoreException.
   */
  public static class DataStoreException extends ODataApplicationException {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 42L;

    /**
     * Instantiates a new data store exception.
     *
     * @param message the message
     */
    public DataStoreException(final String message) {
      this(message, null);
    }

    /**
     * Instantiates a new data store exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public DataStoreException(final String message, final Throwable cause) {
      super(message, Locale.ENGLISH, cause);
    }
  }
}
