/**
 * *****************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ***************************************************************************** 
 */
package org.apache.olingo.odata2.annotation.processor.core.datasource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.annotation.processor.core.datasource.DataStore.DataStoreException;
import org.apache.olingo.odata2.annotation.processor.core.util.AnnotationHelper;
import org.apache.olingo.odata2.annotation.processor.core.util.AnnotationHelper.AnnotatedNavInfo;
import org.apache.olingo.odata2.annotation.processor.core.util.AnnotationHelper.ODataAnnotationException;
import org.apache.olingo.odata2.annotation.processor.core.util.AnnotationRuntimeException;
import org.apache.olingo.odata2.annotation.processor.core.util.ClassHelper;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceContent;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceMimeType;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;

// TODO: Auto-generated Javadoc
/**
 * The Class AnnotationInMemoryDs.
 */
public class AnnotationInMemoryDs implements DataSource {

  /** The Constant ANNOTATION_HELPER. */
  private static final AnnotationHelper ANNOTATION_HELPER = new AnnotationHelper();
  
  /** The data stores. */
  private final Map<String, DataStore<Object>> dataStores = new HashMap<String, DataStore<Object>>();
  
  /** The persist in memory. */
  private final boolean persistInMemory;

  /**
   * Instantiates a new annotation in memory ds.
   *
   * @param annotatedClasses the annotated classes
   * @throws ODataException the o data exception
   */
  public AnnotationInMemoryDs(final Collection<Class<?>> annotatedClasses) throws ODataException {
    this(annotatedClasses, true);
  }

  /**
   * Instantiates a new annotation in memory ds.
   *
   * @param annotatedClasses the annotated classes
   * @param persistInMemory the persist in memory
   * @throws ODataException the o data exception
   */
  public AnnotationInMemoryDs(final Collection<Class<?>> annotatedClasses, final boolean persistInMemory)
      throws ODataException {
    this.persistInMemory = persistInMemory;
    init(annotatedClasses);
  }

  /**
   * Instantiates a new annotation in memory ds.
   *
   * @param packageToScan the package to scan
   * @throws ODataException the o data exception
   */
  public AnnotationInMemoryDs(final String packageToScan) throws ODataException {
    this(packageToScan, true);
  }

  /**
   * Instantiates a new annotation in memory ds.
   *
   * @param packageToScan the package to scan
   * @param persistInMemory the persist in memory
   * @throws ODataException the o data exception
   */
  public AnnotationInMemoryDs(final String packageToScan, final boolean persistInMemory) throws ODataException {
    this.persistInMemory = persistInMemory;
    List<Class<?>> foundClasses = ClassHelper.loadClasses(packageToScan, new ClassHelper.ClassValidator() {
      @Override
      public boolean isClassValid(final Class<?> c) {
        return null != c.getAnnotation(org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet.class);
      }
    });

    init(foundClasses);
  }

  /**
   * Inits the.
   *
   * @param annotatedClasses the annotated classes
   * @throws ODataException the o data exception
   */
  @SuppressWarnings("unchecked")
  private void init(final Collection<Class<?>> annotatedClasses) throws ODataException {
    try {
      for (Class<?> clz : annotatedClasses) {
        String entitySetName = ANNOTATION_HELPER.extractEntitySetName(clz);
        if (entitySetName != null) {
          DataStore<Object> dhs = (DataStore<Object>) DataStore.createInMemory(clz, persistInMemory);
          dataStores.put(entitySetName, dhs);
        } else if (!ANNOTATION_HELPER.isEdmAnnotated(clz)) {
          throw new ODataException("Found not annotated class during DataStore initilization of type: "
              + clz.getName());
        }
      }
    } catch (DataStore.DataStoreException e) {
      throw new ODataException("Error in DataStore initilization with message: " + e.getMessage(), e);
    }
  }

  /**
   * Gets the data store.
   *
   * @param <T> the generic type
   * @param clazz the clazz
   * @return the data store
   */
  @SuppressWarnings("unchecked")
  public <T> DataStore<T> getDataStore(final Class<T> clazz) {
    String entitySetName = ANNOTATION_HELPER.extractEntitySetName(clazz);
    return (DataStore<T>) dataStores.get(entitySetName);
  }

  /**
   * Read data.
   *
   * @param entitySet the entity set
   * @return the list
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public List<?> readData(final EdmEntitySet entitySet) throws ODataNotImplementedException,
      ODataNotFoundException, EdmException, ODataApplicationException {

    DataStore<Object> holder = getDataStore(entitySet);
    if (holder != null) {
      return new ArrayList<Object>(holder.read());
    }

    throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
  }

  /**
   * Read data.
   *
   * @param entitySet the entity set
   * @param keys the keys
   * @return the object
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public Object readData(final EdmEntitySet entitySet, final Map<String, Object> keys)
      throws ODataNotFoundException, EdmException, ODataApplicationException {

    DataStore<Object> store = getDataStore(entitySet);
    if (store != null) {
      Object keyInstance = store.createInstance();
      ANNOTATION_HELPER.setKeyFields(keyInstance, keys);

      Object result = store.read(keyInstance);
      if (result != null) {
        return result;
      }
    }

    throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
  }

  /**
   * Read data.
   *
   * @param function the function
   * @param parameters the parameters
   * @param keys the keys
   * @return the object
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public Object readData(final EdmFunctionImport function, final Map<String, Object> parameters,
      final Map<String, Object> keys)
      throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
    throw new ODataNotImplementedException(ODataNotImplementedException.COMMON);
  }

  /**
   * Read related data.
   *
   * @param sourceEntitySet the source entity set
   * @param sourceData the source data
   * @param targetEntitySet the target entity set
   * @param targetKeys the target keys
   * @return the object
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public Object readRelatedData(final EdmEntitySet sourceEntitySet, final Object sourceData,
      final EdmEntitySet targetEntitySet,
      final Map<String, Object> targetKeys)
      throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {

    DataStore<?> sourceStore = dataStores.get(sourceEntitySet.getName());
    DataStore<?> targetStore = dataStores.get(targetEntitySet.getName());

    AnnotatedNavInfo navInfo = ANNOTATION_HELPER.getCommonNavigationInfo(
        sourceStore.getDataTypeClass(), targetStore.getDataTypeClass());
    final Field sourceField;
    if(navInfo.isBiDirectional()) {
      sourceField = navInfo.getToField();
    } else {
      sourceField = navInfo.getFromField();
    }
    if (sourceField == null) {
      throw new AnnotationRuntimeException("Missing source field for related data (sourceStore='" + sourceStore
          + "', targetStore='" + targetStore + "').");
    }

    List<Object> resultData = readResultData(targetStore, sourceData, sourceField);
    return extractResultData(targetStore, targetKeys, navInfo, resultData);
  }

  /**
   * Read the result data from the target store based on <code>sourceData</code> and <code>sourceField</code>.
   *
   * @param targetStore the target store
   * @param sourceData the source data
   * @param sourceField the source field
   * @return the list
   * @throws DataStoreException the data store exception
   */
  private List<Object> readResultData(final DataStore<?> targetStore, final Object sourceData, final Field sourceField)
      throws DataStoreException {
    Object navigationInstance = getValue(sourceField, sourceData);
    if (navigationInstance == null) {
      return Collections.emptyList();
    }

    List<Object> resultData = new ArrayList<Object>();
    for (Object targetInstance : targetStore.read()) {
      if (navigationInstance instanceof Collection) {
        for (Object object : (Collection<?>) navigationInstance) {
          if (targetStore.isKeyEqualChecked(targetInstance, object)) {
            resultData.add(targetInstance);
          }
        }
      } else if (targetStore.isKeyEqualChecked(targetInstance, navigationInstance)) {
        resultData.add(targetInstance);
      }
    }
    return resultData;
  }

  /**
   * Extract the <code>result data</code> from the <code>resultData</code> list based on
   * <code>navigation information</code> and <code>targetKeys</code>.
   *
   * @param targetStore the target store
   * @param targetKeys the target keys
   * @param navInfo the nav info
   * @param resultData the result data
   * @return the object
   * @throws DataStoreException the data store exception
   */
  private Object extractResultData(final DataStore<?> targetStore, final Map<String, Object> targetKeys,
      final AnnotatedNavInfo navInfo, final List<Object> resultData) throws DataStoreException {
    if (navInfo.getToMultiplicity() == EdmMultiplicity.MANY) {
      if (targetKeys.isEmpty()) {
        return resultData;
      } else {
        Object keyInstance = targetStore.createInstance();
        ANNOTATION_HELPER.setKeyFields(keyInstance, targetKeys);
        for (Object result : resultData) {
          if (targetStore.isKeyEqualChecked(result, keyInstance)) {
            return result;
          }
        }
        return null;
      }
    } else {
      if (resultData.isEmpty()) {
        return null;
      }
      return resultData.get(0);
    }
  }

  /**
   * Read binary data.
   *
   * @param entitySet the entity set
   * @param mediaLinkEntryData the media link entry data
   * @return the binary data
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public BinaryData readBinaryData(final EdmEntitySet entitySet, final Object mediaLinkEntryData)
      throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {

    Object data = ANNOTATION_HELPER.getValueForField(mediaLinkEntryData, EdmMediaResourceContent.class);
    Object mimeType = ANNOTATION_HELPER.getValueForField(mediaLinkEntryData, EdmMediaResourceMimeType.class);

    if (data == null && mimeType == null) {
      DataStore<Object> dataStore = getDataStore(entitySet);
      Object readEntry = dataStore.read(mediaLinkEntryData);
      if (readEntry != null) {
        data = ANNOTATION_HELPER.getValueForField(readEntry, EdmMediaResourceContent.class);
        mimeType = ANNOTATION_HELPER.getValueForField(readEntry, EdmMediaResourceMimeType.class);
      }
    }

    return new BinaryData((byte[]) data, String.valueOf(mimeType));
  }

  /**
   * New data object.
   *
   * @param entitySet the entity set
   * @return the object
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public Object newDataObject(final EdmEntitySet entitySet)
      throws ODataNotImplementedException, EdmException, ODataApplicationException {

    DataStore<Object> dataStore = getDataStore(entitySet);
    if (dataStore != null) {
      return dataStore.createInstance();
    }

    throw new AnnotationRuntimeException("No DataStore found for entitySet with name: " + entitySet.getName());
  }

  /**
   * Write binary data.
   *
   * @param entitySet the entity set
   * @param mediaEntityInstance the media entity instance
   * @param binaryData the binary data
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public void writeBinaryData(final EdmEntitySet entitySet, final Object mediaEntityInstance,
      final BinaryData binaryData)
      throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {

    try {
      DataStore<Object> dataStore = getDataStore(entitySet);
      Object readEntry = dataStore.read(mediaEntityInstance);
      if (readEntry == null) {
        throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
      } else {
        ANNOTATION_HELPER.setValueForAnnotatedField(
            mediaEntityInstance, EdmMediaResourceContent.class, binaryData.getData());
        ANNOTATION_HELPER.setValueForAnnotatedField(
            mediaEntityInstance, EdmMediaResourceMimeType.class, binaryData.getMimeType());
      }
    } catch (ODataAnnotationException e) {
      throw new AnnotationRuntimeException("Invalid media resource annotation at entity set '" + entitySet.getName()
          + "' with message '" + e.getMessage() + "'.", e);
    }
  }

  /**
   * <p>Updates a single data object identified by the specified entity set and key fields of
   * the data object.</p>
   *
   * @param entitySet the {@link EdmEntitySet} the object must correspond to
   * @param data the data object of the new entity
   * @return updated data object instance
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  public Object updateData(final EdmEntitySet entitySet, final Object data)
      throws ODataNotImplementedException, EdmException, ODataApplicationException {

    DataStore<Object> dataStore = getDataStore(entitySet);
    return dataStore.update(data);
  }

  /**
   * Delete data.
   *
   * @param entitySet the entity set
   * @param keys the keys
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public void deleteData(final EdmEntitySet entitySet, final Map<String, Object> keys)
      throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
    DataStore<Object> dataStore = getDataStore(entitySet);
    Object keyInstance = dataStore.createInstance();
    ANNOTATION_HELPER.setKeyFields(keyInstance, keys);
    dataStore.delete(keyInstance);
  }

  /**
   * Creates the data.
   *
   * @param entitySet the entity set
   * @param data the data
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public void createData(final EdmEntitySet entitySet, final Object data)
      throws ODataNotImplementedException, EdmException, ODataApplicationException {

    DataStore<Object> dataStore = getDataStore(entitySet);
    dataStore.create(data);
  }

  /**
   * Delete relation.
   *
   * @param sourceEntitySet the source entity set
   * @param sourceData the source data
   * @param targetEntitySet the target entity set
   * @param targetKeys the target keys
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public void deleteRelation(final EdmEntitySet sourceEntitySet, final Object sourceData,
      final EdmEntitySet targetEntitySet,
      final Map<String, Object> targetKeys)
      throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
    throw new ODataNotImplementedException(ODataNotImplementedException.COMMON);
  }

  /**
   * Write relation.
   *
   * @param sourceEntitySet the source entity set
   * @param sourceEntity the source entity
   * @param targetEntitySet the target entity set
   * @param targetEntityValues the target entity values
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public void writeRelation(final EdmEntitySet sourceEntitySet, final Object sourceEntity,
      final EdmEntitySet targetEntitySet,
      final Map<String, Object> targetEntityValues)
      throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException {
    // get common data
    DataStore<Object> sourceStore = dataStores.get(sourceEntitySet.getName());
    DataStore<Object> targetStore = dataStores.get(targetEntitySet.getName());

    AnnotatedNavInfo commonNavInfo = ANNOTATION_HELPER.getCommonNavigationInfo(
        sourceStore.getDataTypeClass(), targetStore.getDataTypeClass());

    // get and validate source fields
    Field sourceField = commonNavInfo.getFromField();
    if (sourceField == null) {
      throw new AnnotationRuntimeException("Missing source field for related data (sourceStore='" + sourceStore
          + "', targetStore='" + targetStore + "').");
    }

    // get related target entity
    Object targetEntity = targetStore.createInstance();
    ANNOTATION_HELPER.setKeyFields(targetEntity, targetEntityValues);
    targetEntity = targetStore.read(targetEntity);

    // set at source
    setValueAtNavigationField(sourceEntity, sourceField, targetEntity);
    // set at target
    Field targetField = commonNavInfo.getToField();
    if (targetField != null) {
      setValueAtNavigationField(targetEntity, targetField, sourceEntity);
    }
  }

  /**
   * Set (Multiplicity != *) or add (Multiplicity == *) <code>value</code> at <code>field</code>
   * of <code>instance</code>.
   *
   * @param instance the instance
   * @param field the field
   * @param value the value
   * @throws EdmException the edm exception
   */
  private void setValueAtNavigationField(final Object instance, final Field field, final Object value)
      throws EdmException {
    Class<?> fieldTypeClass = field.getType();
    if (Collection.class.isAssignableFrom(fieldTypeClass)) {
      @SuppressWarnings("unchecked")
      Collection<Object> collection = (Collection<Object>) ANNOTATION_HELPER.getValueForField(
          instance, field.getName(), EdmNavigationProperty.class);
      if (collection == null) {
        collection = new ArrayList<Object>();
        setValue(instance, field, collection);
      }
      collection.add(value);
    } else if (fieldTypeClass.isArray()) {
      throw new AnnotationRuntimeException("Write relations for internal used arrays is not supported.");
    } else {
      setValue(instance, field, value);
    }
  }

  /**
   * Returns corresponding DataStore for EdmEntitySet or if no data store is registered an
   * AnnotationRuntimeException is thrown.
   * Never returns NULL.
   *
   * @param entitySet for which the corresponding DataStore is returned
   * @return a DataStore object
   * @throws EdmException the edm exception
   * @throws AnnotationRuntimeException if no DataStore is found
   */
  private DataStore<Object> getDataStore(final EdmEntitySet entitySet) throws EdmException {
    final String name = entitySet.getName();
    DataStore<Object> dataStore = dataStores.get(name);
    if (dataStore == null) {
      throw new AnnotationRuntimeException("No DataStore found for entity set '" + entitySet + "'.");
    }
    return dataStore;
  }

  /**
   * Gets the value.
   *
   * @param field the field
   * @param instance the instance
   * @return the value
   */
  private Object getValue(final Field field, final Object instance) {
    try {
      boolean access = field.isAccessible();
      field.setAccessible(true);
      Object value = field.get(instance);
      field.setAccessible(access);
      return value;
    } catch (IllegalArgumentException e) {
      throw new AnnotationRuntimeException("Error for getting value of field '"
          + field + "' at instance '" + instance + "'.", e);
    } catch (IllegalAccessException e) {
      throw new AnnotationRuntimeException("Error for getting value of field '"
          + field + "' at instance '" + instance + "'.", e);
    }
  }

  /**
   * Sets the value.
   *
   * @param instance the instance
   * @param field the field
   * @param value the value
   */
  private void setValue(final Object instance, final Field field, final Object value) {
    try {
      boolean access = field.isAccessible();
      field.setAccessible(true);
      field.set(instance, value);
      field.setAccessible(access);
    } catch (IllegalArgumentException e) {
      throw new AnnotationRuntimeException("Error for setting value of field: '"
          + field + "' at instance: '" + instance + "'.", e);
    } catch (IllegalAccessException e) {
      throw new AnnotationRuntimeException("Error for setting value of field: '"
          + field + "' at instance: '" + instance + "'.", e);
    }
  }
}
