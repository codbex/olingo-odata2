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
package org.apache.olingo.odata2.core.ep.producer;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.callback.OnWriteEntryContent;
import org.apache.olingo.odata2.api.ep.callback.OnWriteFeedContent;
import org.apache.olingo.odata2.api.ep.callback.WriteCallbackContext;
import org.apache.olingo.odata2.api.ep.callback.WriteEntryCallbackContext;
import org.apache.olingo.odata2.api.ep.callback.WriteEntryCallbackResult;
import org.apache.olingo.odata2.api.ep.callback.WriteFeedCallbackContext;
import org.apache.olingo.odata2.api.ep.callback.WriteFeedCallbackResult;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.commons.Encoder;
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
public class JsonEntryEntityProducer {

  /** The properties. */
  private final EntityProviderWriteProperties properties;
  
  /** The e tag. */
  private String eTag;
  
  /** The location. */
  private String location;
  
  /** The json stream writer. */
  private JsonStreamWriter jsonStreamWriter;

  /**
   * Instantiates a new json entry entity producer.
   *
   * @param properties the properties
   * @throws EntityProviderException the entity provider exception
   */
  public JsonEntryEntityProducer(final EntityProviderWriteProperties properties) throws EntityProviderException {
    this.properties = properties == null ? EntityProviderWriteProperties.serviceRoot(null).build() : properties;
  }

  /**
   * Append.
   *
   * @param writer the writer
   * @param entityInfo the entity info
   * @param data the data
   * @param isRootElement the is root element
   * @throws EntityProviderException the entity provider exception
   */
  public void append(final Writer writer, final EntityInfoAggregator entityInfo, final Map<String, Object> data,
      final boolean isRootElement) throws EntityProviderException {
    final EdmEntityType type = entityInfo.getEntityType();

    try {
      jsonStreamWriter = new JsonStreamWriter(writer);
      if (isRootElement && !properties.isOmitJsonWrapper()) {
        jsonStreamWriter.beginObject().name(FormatJson.D);
      }

      jsonStreamWriter.beginObject();

      boolean containsMetadata = false;
      if (!properties.isContentOnly() || (properties.isContentOnly() && properties.isIncludeMetadataInContentOnly())) {
        writeMetadata(entityInfo, data, type);
        containsMetadata = true;
      }

      writeProperties(entityInfo, data, type, containsMetadata);

      if (!properties.isContentOnly()) {
        writeNavigationProperties(writer, entityInfo, data, type);
      } else {
        writeAdditonalLinksInContentOnlyCase(entityInfo);
      }

      jsonStreamWriter.endObject();

      if (isRootElement && !properties.isOmitJsonWrapper()) {
        jsonStreamWriter.endObject();
      }

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
   * @throws EdmException the edm exception
   * @throws EntityProviderException the entity provider exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void writeNavigationProperties(final Writer writer, final EntityInfoAggregator entityInfo,
      final Map<String, Object> data,
      final EdmEntityType type) throws EdmException, EntityProviderException, IOException {
    for (final String navigationPropertyName : type.getNavigationPropertyNames()) {
      if (entityInfo.getSelectedNavigationPropertyNames().contains(navigationPropertyName)) {
        jsonStreamWriter.separator();
        jsonStreamWriter.name(navigationPropertyName);
        if (entityInfo.getExpandedNavigationPropertyNames().contains(navigationPropertyName)) {

        String navigationPropertyNameFqn = concat(entityInfo.getEntityType().getName(), navigationPropertyName);
        Map<String, ODataCallback> callbacks = properties.getCallbacks();
        if (callbacks != null && (callbacks.containsKey(navigationPropertyName) || 
                callbacks.containsKey(navigationPropertyNameFqn))) {
            writeExpandedNavigationProperty(writer, entityInfo, data, type, navigationPropertyName);
          } else {
            writeDeferredUri(entityInfo, navigationPropertyName);
          }
        } else {
          writeDeferredUri(entityInfo, navigationPropertyName);
        }
      }
    }
  }

  /**
   * Concat.
   *
   * @param s1 the s 1
   * @param s2 the s 2
   * @return the string
   */
  private String concat(String s1, String s2) {
    return s1.concat(".").concat(s2);
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

    WriteCallbackContext context = isFeed ? new WriteFeedCallbackContext() : new WriteEntryCallbackContext();
    context.setSourceEntitySet(entitySet);
    context.setNavigationProperty(navigationProperty);
    context.setEntryData(data);
    context.setCurrentWriteProperties(properties);
    context.setCurrentExpandSelectTreeNode(properties.getExpandSelectTree().getLinks().get(
        navigationPropertyName));

    Map<String, ODataCallback> callbacks = properties.getCallbacks();
    String navigationPropertyNameFqn = concat(entityInfo.getEntityType().getName(), navigationPropertyName);
    ODataCallback callback = callbacks.get(navigationPropertyName);
    callback = callback == null ? callbacks.get(navigationPropertyNameFqn) : callback;
    if (callback == null) {
      throw new EntityProviderProducerException(EntityProviderException.EXPANDNOTSUPPORTED);
    }
    try {
      if (isFeed) {
        final WriteFeedCallbackResult result =
            ((OnWriteFeedContent) callback).retrieveFeedResult((WriteFeedCallbackContext) context);
        List<Map<String, Object>> inlineData = result.getFeedData();
        if (inlineData == null) {
          inlineData = new ArrayList<Map<String, Object>>();
        }
        
        //This statement is used for the client use case. Flag should never be set on server side
        if(properties.isOmitInlineForNullData() && inlineData.isEmpty()){
          writeDeferredUri(entityInfo, navigationPropertyName);
          return;
        }
        
        final EntityProviderWriteProperties inlineProperties = result.getInlineProperties();
        final EntityInfoAggregator inlineEntityInfo =
            EntityInfoAggregator.create(inlineEntitySet, inlineProperties.getExpandSelectTree());

        JsonFeedEntityProducer jsonFeedEntityProducer = new JsonFeedEntityProducer(inlineProperties);
        if (properties.isResponsePayload()) {
          jsonFeedEntityProducer.appendAsObject(writer, inlineEntityInfo, inlineData, false);
        } else {
          jsonFeedEntityProducer.appendAsArray(writer, inlineEntityInfo, inlineData);
        }

      } else {
        final WriteEntryCallbackResult result =
            ((OnWriteEntryContent) callback).retrieveEntryResult((WriteEntryCallbackContext) context);
        Map<String, Object> inlineData = result.getEntryData();
        
        //This statement is used for the client use case. Flag should never be set on server side
        if(properties.isOmitInlineForNullData() && (inlineData == null || inlineData.isEmpty())){
          writeDeferredUri(entityInfo, navigationPropertyName);
          return;
        }
        
        if (inlineData != null && !inlineData.isEmpty()) {
          final EntityProviderWriteProperties inlineProperties = result.getInlineProperties();
          final EntityInfoAggregator inlineEntityInfo =
              EntityInfoAggregator.create(inlineEntitySet, inlineProperties.getExpandSelectTree());
          new JsonEntryEntityProducer(inlineProperties).append(writer, inlineEntityInfo, inlineData, false);
        } else {
          jsonStreamWriter.unquotedValue("null");
        }
      }
    } catch (final ODataApplicationException e) {
      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
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
      if (properties.isDataBasedPropertySerialization() && ((Map<?,?>)data).containsKey(propertyName)) {
        omitComma = appendPropertyNameValue(entityInfo, data, omitComma, propertyName);
      } else if (!properties.isDataBasedPropertySerialization() && entityInfo.getSelectedPropertyNames()
          .contains(propertyName)) {
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
      omitComma = false;
    } else {
      jsonStreamWriter.separator();
    }
    jsonStreamWriter.name(propertyName);
 
    JsonPropertyEntityProducer.appendPropertyValue(jsonStreamWriter,
        entityInfo.getPropertyInfo(propertyName),
        data.get(propertyName),
        properties.isValidatingFacets(), properties.isDataBasedPropertySerialization());
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
    } else {
      location = properties.getServiceRoot().toASCIIString() +
          AtomEntryEntityProducer.createSelfLink(entityInfo, data, null);
    }

    jsonStreamWriter.name(FormatJson.METADATA);
    jsonStreamWriter.beginObject();
    jsonStreamWriter.namedStringValue(FormatJson.ID, location);
    jsonStreamWriter.separator();
    jsonStreamWriter.namedStringValue(FormatJson.URI, location);
    jsonStreamWriter.separator();
    jsonStreamWriter.namedStringValueRaw(FormatJson.TYPE, type.getNamespace() + Edm.DELIMITER + type.getName());
    if (!properties.isOmitETag()) {
      eTag = AtomEntryEntityProducer.createETag(entityInfo, data);
      if (eTag != null) {
        jsonStreamWriter.separator();
        jsonStreamWriter.namedStringValue(FormatJson.ETAG, eTag);
      }
    }
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
          mediaSrc = location + "/$value";
        }
        String mediaResourceMimeTypeKey = entityTypeMapping.getMediaResourceMimeTypeKey();
        if (mediaResourceMimeTypeKey != null) {
          mediaResourceMimeType = (String) data.get(mediaResourceMimeTypeKey);
        }
        if (mediaResourceMimeType == null) {
          mediaResourceMimeType = ContentType.APPLICATION_OCTET_STREAM.toString();
        }
      } else {
        mediaSrc = location + "/$value";
        mediaResourceMimeType = ContentType.APPLICATION_OCTET_STREAM.toString();
      }

      jsonStreamWriter.namedStringValueRaw(FormatJson.CONTENT_TYPE, mediaResourceMimeType);
      jsonStreamWriter.separator();

      jsonStreamWriter.namedStringValue(FormatJson.MEDIA_SRC, mediaSrc);
      jsonStreamWriter.separator();
      jsonStreamWriter.namedStringValue(FormatJson.EDIT_MEDIA, location + "/$value");
    }
    jsonStreamWriter.endObject();
  }

  /**
   * Write deferred uri.
   *
   * @param entityInfo the entity info
   * @param navigationPropertyName the navigation property name
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EntityProviderException the entity provider exception
   * @throws EdmException the edm exception
   */
  private void writeDeferredUri(final EntityInfoAggregator entityInfo, final String navigationPropertyName)
      throws IOException, EntityProviderException, EdmException {
    jsonStreamWriter.beginObject()
        .name(FormatJson.DEFERRED);
    String target = null;
    final Map<String, Map<String, Object>> links = properties.getAdditionalLinks();
    final Map<String, Object> key = links == null ? null : links.get(navigationPropertyName);
    if (key == null || key.isEmpty()) {
      target = location + "/" + Encoder.encode(navigationPropertyName);
    } else {
      target = createCustomTargetLink(entityInfo, navigationPropertyName, key);
    }
    JsonLinkEntityProducer.appendUri(jsonStreamWriter, target);
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
        + AtomEntryEntityProducer.createSelfLink(targetEntityInfo, key, null);
    return target;
  }

  /**
   * Write additonal links in content only case.
   *
   * @param entityInfo the entity info
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EntityProviderException the entity provider exception
   * @throws EdmException the edm exception
   */
  private void writeAdditonalLinksInContentOnlyCase(final EntityInfoAggregator entityInfo)
      throws IOException, EntityProviderException, EdmException {
    final Map<String, Map<String, Object>> links = properties.getAdditionalLinks();
    if (links != null && !links.isEmpty()) {
      for (Entry<String, Map<String, Object>> entry : links.entrySet()) {
        Map<String, Object> navigationKeyMap = entry.getValue();
        if (navigationKeyMap != null && !navigationKeyMap.isEmpty()) {
          String target = createCustomTargetLink(entityInfo, entry.getKey(), navigationKeyMap);
          jsonStreamWriter.separator();
          jsonStreamWriter.name(entry.getKey());
          jsonStreamWriter.beginObject()
              .name(FormatJson.DEFERRED);
          JsonLinkEntityProducer.appendUri(jsonStreamWriter, target);
          jsonStreamWriter.endObject();
        }
      }
    }
  }

  /**
   * Gets the e tag.
   *
   * @return the e tag
   */
  public String getETag() {
    return eTag;
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
