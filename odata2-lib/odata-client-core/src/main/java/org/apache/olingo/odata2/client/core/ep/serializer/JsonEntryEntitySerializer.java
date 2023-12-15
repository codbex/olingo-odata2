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
package org.apache.olingo.odata2.client.core.ep.serializer;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.client.api.ep.Entity;
import org.apache.olingo.odata2.client.api.ep.EntityCollection;
import org.apache.olingo.odata2.client.api.ep.EntityCollectionSerializerProperties;
import org.apache.olingo.odata2.client.api.ep.EntitySerializerProperties;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.ep.EntityProviderProducerException;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.util.FormatJson;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;

// TODO: Auto-generated Javadoc
/**
 * Producer for writing an entity in JSON, also usable for function imports
 * returning a single instance of an entity type.
 * 
 */
public class JsonEntryEntitySerializer {

  /** The properties. */
  private final EntitySerializerProperties properties;
  
  /** The location. */
  private String location;
  
  /** The idlocation. */
  private String idlocation;
  
  /** The json stream writer. */
  private JsonStreamWriter jsonStreamWriter;
  
  /** The Constant VALUE. */
  private static final String VALUE = "/$value";

  /**
   * Instantiates a new json entry entity serializer.
   *
   * @param properties the properties
   */
  public JsonEntryEntitySerializer(final EntitySerializerProperties properties) {
    this.properties = properties == null ? EntitySerializerProperties.serviceRoot(null).build() : properties;
  }

  /**
   * This serializes the json payload entry.
   *
   * @param writer the writer
   * @param entityInfo the entity info
   * @param data the data
   * @throws EntityProviderException the entity provider exception
   */
  public void append(final Writer writer, final EntityInfoAggregator entityInfo, final Entity data) 
      throws EntityProviderException {
   if (data == null) {
     throw new EntityProviderException(EntityProviderException.NULL_VALUE);
   }
    final EdmEntityType type = entityInfo.getEntityType();

    try {
      jsonStreamWriter = new JsonStreamWriter(writer);
     
      jsonStreamWriter.beginObject();

      boolean containsMetadata = false;
      if (properties.isIncludeMetadata()) {
        writeMetadata(entityInfo, data.getProperties(), type);
        containsMetadata = true;
      } 
      writeProperties(entityInfo, data.getProperties(), type, containsMetadata);

      writeNavigationProperties(writer, entityInfo, data.getNavigations(), type, data.getProperties().isEmpty());
      jsonStreamWriter.endObject();
      
      writer.flush();

    } catch (final IOException e) {
      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    } catch (final EdmException e) {
      throw new EntityProviderProducerException(e.getMessageReference(), e);
    }
  }

  /**
   * Write navigation properties.
   *
   * @param writer the writer
   * @param entityInfo the entity info
   * @param data the data
   * @param type the type
   * @param emptyData the empty data
   * @throws EdmException the edm exception
   * @throws EntityProviderException the entity provider exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void writeNavigationProperties(final Writer writer, final EntityInfoAggregator entityInfo,
      final Map<String, Object> data,
      final EdmEntityType type, boolean emptyData) throws EdmException, EntityProviderException, IOException {
    for (final String navigationPropertyName : type.getNavigationPropertyNames()) {
      if (data.containsKey(navigationPropertyName)) {
        if (data.get(navigationPropertyName) == null) {
          throw new EntityProviderException(EntityProviderException.NULL_VALUE);
        }
        if (data.get(navigationPropertyName) instanceof Entity || 
            data.get(navigationPropertyName) instanceof EntityCollection) {
          if( !emptyData){
            jsonStreamWriter.separator();
          }
          emptyData=false;
          jsonStreamWriter.name(navigationPropertyName);
          writeExpandedNavigationProperty(writer, entityInfo, data, type, navigationPropertyName);
        } else if (data.get(navigationPropertyName) instanceof Map<?,?>){
          writeNavigationLinks(entityInfo, data);
        } else {
          throw new EntityProviderException(EntityProviderException.INCORRECT_NAVIGATION_TYPE);
        }
      }
    }
  }

  /**
   * Write expanded navigation property.
   *
   * @param writer the writer
   * @param entityInfo the entity info
   * @param data the data
   * @param type the type
   * @param navigationPropertyName the navigation property name
   * @throws EdmException the edm exception
   * @throws EntityProviderException the entity provider exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void writeExpandedNavigationProperty(final Writer writer, final EntityInfoAggregator entityInfo,
      final Map<String, Object> data,
      final EdmEntityType type, final String navigationPropertyName) throws EdmException, EntityProviderException,
      IOException {
    
    final EdmNavigationProperty navigationProperty = (EdmNavigationProperty) type.getProperty(navigationPropertyName);
    final boolean isFeed = navigationProperty.getMultiplicity() == EdmMultiplicity.MANY;
    final EdmEntitySet entitySet = entityInfo.getEntitySet();
    final EdmEntitySet inlineEntitySet = entitySet.getRelatedEntitySet(navigationProperty);

    if (isFeed) {
      EntityCollection inlineData = (EntityCollection) data.get(navigationProperty.getName());
      
      if(inlineData == null){
        throw new EntityProviderException(EntityProviderException.NULL_VALUE);
      }
      final EntityCollectionSerializerProperties inlineProperties = inlineData.getCollectionProperties() == null ? 
          EntityCollectionSerializerProperties.
          serviceRoot(properties.getServiceRoot()).build() : 
            inlineData.getCollectionProperties();
      JsonFeedEntitySerializer jsonFeedEntityProducer = new JsonFeedEntitySerializer(inlineProperties);
      final EntityInfoAggregator inlineEntityInfo =
          EntityInfoAggregator.create(inlineEntitySet, null);
      jsonFeedEntityProducer.appendAsArray(writer, inlineEntityInfo, inlineData);
    } else {
      Entity inlineData = (Entity) data.get(navigationProperty.getName());
    //This statement is used for the client use case. Flag should never be set on server side
      if(inlineData == null){
        throw new EntityProviderException(EntityProviderException.NULL_VALUE);
      }
      if (inlineData != null && inlineData.getProperties() != null) {
        final EntitySerializerProperties inlineProperties = inlineData.getWriteProperties() == null ?
            EntitySerializerProperties.
            serviceRoot(properties.getServiceRoot()).build() : inlineData.getWriteProperties();
        final EntityInfoAggregator inlineEntityInfo =
            EntityInfoAggregator.create(inlineEntitySet, null);
        new JsonEntryEntitySerializer(inlineProperties).append(writer, inlineEntityInfo, inlineData);
      } else {
        jsonStreamWriter.beginObject();
        jsonStreamWriter.endObject();
      }
    }
   }

  /**
   * Write properties.
   *
   * @param entityInfo the entity info
   * @param data the data
   * @param type the type
   * @param containsMetadata the contains metadata
   * @throws EdmException the edm exception
   * @throws EntityProviderException the entity provider exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void writeProperties(final EntityInfoAggregator entityInfo, final Map<String, Object> data,
      final EdmEntityType type, boolean containsMetadata) throws EdmException, EntityProviderException, IOException {
    // if the payload contains metadata we must not omit the first comm as it separates the _metadata object form the
    // properties
    boolean omitComma = !containsMetadata;

    List<String> propertyNames = type.getPropertyNames();
    for (final String propertyName : propertyNames) {
      if (data.containsKey(propertyName)) {
        omitComma = appendPropertyNameValue(entityInfo, data, omitComma, propertyName);
      } 
    }
  }

  /**
   * Append property name value.
   *
   * @param entityInfo the entity info
   * @param data the data
   * @param omitComma the omit comma
   * @param propertyName the property name
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EdmException the edm exception
   * @throws EntityProviderException the entity provider exception
   */
  private boolean appendPropertyNameValue(final EntityInfoAggregator entityInfo, final Map<String, Object> data,
      boolean omitComma, String propertyName) throws IOException, EdmException, EntityProviderException {
    if (omitComma) {
      omitComma = false; //NOSONAR
    } else {
      jsonStreamWriter.separator();
    }
    jsonStreamWriter.name(propertyName);
 
    JsonPropertyEntitySerializer.appendPropertyValue(jsonStreamWriter,
        entityInfo.getPropertyInfo(propertyName),
        data.get(propertyName),
        properties.isValidatingFacets());
    return omitComma;
  }
  
  /**
   * Write metadata.
   *
   * @param entityInfo the entity info
   * @param data the data
   * @param type the type
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EntityProviderException the entity provider exception
   * @throws EdmException the edm exception
   */
  private void writeMetadata(final EntityInfoAggregator entityInfo, final Map<String, Object> data,
      final EdmEntityType type) throws IOException, EntityProviderException, EdmException {
    if (properties.getServiceRoot() == null) {
      location = "";
      idlocation = "";
    } else {
      location = properties.getServiceRoot().toASCIIString() +
          AtomEntryEntitySerializer.createSelfLink(entityInfo, data, null, properties.isKeyAutoGenerated(), false);
      idlocation = properties.getServiceRoot().toASCIIString() +
          AtomEntryEntitySerializer.createSelfLink(entityInfo, data, null, properties.isKeyAutoGenerated(), true);
    }

    jsonStreamWriter.name(FormatJson.METADATA);
    jsonStreamWriter.beginObject();
    jsonStreamWriter.namedStringValue(FormatJson.ID, idlocation);
    jsonStreamWriter.separator();
    jsonStreamWriter.namedStringValue(FormatJson.URI, location);
    jsonStreamWriter.separator();
    jsonStreamWriter.namedStringValueRaw(FormatJson.TYPE, type.getNamespace() + Edm.DELIMITER + type.getName());
    
    if (type.hasStream()) {
      jsonStreamWriter.separator();

      EdmMapping entityTypeMapping = entityInfo.getEntityType().getMapping();
      String mediaResourceMimeType = null;
      String mediaSrc = null;

      if (entityTypeMapping != null) {
        String mediaResourceSourceKey = entityTypeMapping.getMediaResourceSourceKey();
        if (mediaResourceSourceKey != null) {
          mediaSrc = (String) data.get(mediaResourceSourceKey);
        }
        if (mediaSrc == null) {
          mediaSrc = location + VALUE;
        }
        String mediaResourceMimeTypeKey = entityTypeMapping.getMediaResourceMimeTypeKey();
        if (mediaResourceMimeTypeKey != null) {
          mediaResourceMimeType = (String) data.get(mediaResourceMimeTypeKey);
        }
        if (mediaResourceMimeType == null) {
          mediaResourceMimeType = ContentType.APPLICATION_OCTET_STREAM.toString();
        }
      } else {
        mediaSrc = location + VALUE;
        mediaResourceMimeType = ContentType.APPLICATION_OCTET_STREAM.toString();
      }

      jsonStreamWriter.namedStringValueRaw(FormatJson.CONTENT_TYPE, mediaResourceMimeType);
      jsonStreamWriter.separator();

      jsonStreamWriter.namedStringValue(FormatJson.MEDIA_SRC, mediaSrc);
      jsonStreamWriter.separator();
      jsonStreamWriter.namedStringValue(FormatJson.EDIT_MEDIA, location + VALUE);
    }
    jsonStreamWriter.endObject();
  }

  /**
   * Creates the custom target link.
   *
   * @param entityInfo the entity info
   * @param navigationPropertyName the navigation property name
   * @param key the key
   * @return the string
   * @throws EntityProviderException the entity provider exception
   * @throws EdmException the edm exception
   */
  private String createCustomTargetLink(final EntityInfoAggregator entityInfo, final String navigationPropertyName,
      final Map<String, Object> key) throws EntityProviderException, EdmException {
    String target;
    final EntityInfoAggregator targetEntityInfo = EntityInfoAggregator.create(
        entityInfo.getEntitySet().getRelatedEntitySet(
            (EdmNavigationProperty) entityInfo.getEntityType().getProperty(navigationPropertyName)));
    target = (properties.getServiceRoot() == null ? "" : properties.getServiceRoot().toASCIIString())
        + AtomEntryEntitySerializer.createSelfLink(targetEntityInfo, key, null, properties.isKeyAutoGenerated(), false);
    return target;
  }

  /**
   * Write navigation links.
   *
   * @param entityInfo the entity info
   * @param navigationLinks the navigation links
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EntityProviderException the entity provider exception
   * @throws EdmException the edm exception
   */
  private void writeNavigationLinks(final EntityInfoAggregator entityInfo,
      Map<String, Object> navigationLinks) throws IOException, 
  EntityProviderException, EdmException {
    if (navigationLinks != null && !navigationLinks.isEmpty()) {
      for (Entry<String, Object> entry : navigationLinks.entrySet()) {
        String target = null;
        if (entry.getValue() instanceof HashMap<?, ?>) {
          @SuppressWarnings("unchecked")
          Map<String, Object> navigationKeyMap = (Map<String, Object>) entry.getValue();
          if (navigationKeyMap != null && !navigationKeyMap.isEmpty()) { //NOSONAR
            target = createCustomTargetLink(entityInfo, entry.getKey(), navigationKeyMap);
            jsonStreamWriter.separator();
            jsonStreamWriter.name(entry.getKey());
            jsonStreamWriter.beginObject()
                .name(FormatJson.DEFERRED);
            JsonLinkEntitySerializer.appendUri(jsonStreamWriter, target);
            jsonStreamWriter.endObject();
          }
        }
      }
    }
  }

  /**
   * Gets the location.
   *
   * @return the location
   */
  public String getLocation() {
    return location;
  }
}
