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
package org.apache.olingo.odata2.fit.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetSimplePropertyUriInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class MapProcessor.
 */
public class MapProcessor extends ODataSingleProcessor {

  /** The Constant RECORD_COUNT. */
  public static final int RECORD_COUNT = 10;
  
  /** The records. */
  private final ArrayList<HashMap<String, String>> records = new ArrayList<HashMap<String, String>>();

  {
    HashMap<String, String> record;

    for (int i = 1; i <= RECORD_COUNT; i++) {
      record = new HashMap<String, String>();
      record.put("P01", "V01." + i);
      record.put("P02", "V02." + i);
      record.put("P03", "V03." + i);
      records.add(record);
    }
  }

  /**
   * Index of.
   *
   * @param key the key
   * @param value the value
   * @return the int
   */
  private int indexOf(final String key, final String value) {
    for (int i = 0; i < RECORD_COUNT; i++) {
      if (records.get(i).containsKey(key) && records.get(i).containsValue(value)) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Read entity set.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse readEntitySet(final GetEntitySetUriInfo uriInfo, final String contentType)
      throws ODataException {
    final EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build();

    final List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();

    for (final HashMap<String, String> record : records) {
      final HashMap<String, Object> data = new HashMap<String, Object>();

      for (final String pName : uriInfo.getTargetEntitySet().getEntityType().getPropertyNames()) {
        final EdmProperty property = (EdmProperty) uriInfo.getTargetEntitySet().getEntityType().getProperty(pName);
        final String mappedPropertyName = (String) property.getMapping().getObject();
        data.put(pName, record.get(mappedPropertyName));
      }

      values.add(data);
    }

    final ODataResponse response =
        EntityProvider.writeFeed(contentType, uriInfo.getTargetEntitySet(), values, properties);

    return response;
  }

  /**
   * Read entity.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse readEntity(final GetEntityUriInfo uriInfo, final String contentType) throws ODataException {
    final EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(getContext().getPathInfo().getServiceRoot()).build();

    // query
    final String mappedKeyName =
        (String) uriInfo.getTargetEntitySet().getEntityType().getKeyProperties().get(0).getMapping().getObject();
    final String keyValue = uriInfo.getKeyPredicates().get(0).getLiteral();
    final int index = indexOf(mappedKeyName, keyValue);
    if ((index < 0) || (index > records.size())) {
      throw new ODataNotFoundException(ODataNotFoundException.ENTITY.addContent(keyValue));
    }
    final HashMap<String, String> record = records.get(index);

    final HashMap<String, Object> data = new HashMap<String, Object>();
    for (final String pName : uriInfo.getTargetEntitySet().getEntityType().getPropertyNames()) {
      final EdmProperty property = (EdmProperty) uriInfo.getTargetEntitySet().getEntityType().getProperty(pName);
      final String mappedPropertyName = (String) property.getMapping().getObject();
      data.put(pName, record.get(mappedPropertyName));
    }

    final ODataResponse response =
        EntityProvider.writeEntry(contentType, uriInfo.getTargetEntitySet(), data, properties);
    return response;
  }

  /**
   * Read entity simple property value.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse readEntitySimplePropertyValue(final GetSimplePropertyUriInfo uriInfo, final String contentType)
      throws ODataException {
    final List<EdmProperty> propertyPath = uriInfo.getPropertyPath();
    final EdmProperty property = propertyPath.get(propertyPath.size() - 1);

    final String mappedKeyName =
        (String) uriInfo.getTargetEntitySet().getEntityType().getKeyProperties().get(0).getMapping().getObject();
    final String keyValue = uriInfo.getKeyPredicates().get(0).getLiteral();

    final int index = indexOf(mappedKeyName, keyValue);
    if ((index < 0) || (index > records.size())) {
      throw new ODataNotFoundException(ODataNotFoundException.ENTITY.addContent(keyValue));
    }
    final HashMap<String, String> record = records.get(index);

    final String mappedPropertyName = (String) property.getMapping().getObject();
    final Object value = record.get(mappedPropertyName);

    final ODataResponse response = EntityProvider.writePropertyValue(property, value);
    return response;
  }

}
