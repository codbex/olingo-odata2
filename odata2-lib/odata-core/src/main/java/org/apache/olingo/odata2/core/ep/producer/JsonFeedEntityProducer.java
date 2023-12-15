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
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.callback.TombstoneCallback;
import org.apache.olingo.odata2.api.ep.callback.TombstoneCallbackResult;
import org.apache.olingo.odata2.core.ep.EntityProviderProducerException;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.util.FormatJson;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;

// TODO: Auto-generated Javadoc
/**
 * Producer for writing an entity collection (a feed) in JSON.
 * 
 */
public class JsonFeedEntityProducer {

  /** The properties. */
  private final EntityProviderWriteProperties properties;

  /**
   * Instantiates a new json feed entity producer.
   *
   * @param properties the properties
   * @throws EntityProviderException the entity provider exception
   */
  public JsonFeedEntityProducer(final EntityProviderWriteProperties properties) throws EntityProviderException {
    this.properties = properties == null ? EntityProviderWriteProperties.serviceRoot(null).build() : properties;
  }

  /**
   * Append as object.
   *
   * @param writer the writer
   * @param entityInfo the entity info
   * @param data the data
   * @param isRootElement the is root element
   * @throws EntityProviderException the entity provider exception
   */
  public void appendAsObject(final Writer writer, final EntityInfoAggregator entityInfo,
                             final List<Map<String, Object>> data,
                             final boolean isRootElement) throws EntityProviderException {
    JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);

    TombstoneCallback callback = getTombstoneCallback();

    try {
      jsonStreamWriter.beginObject();

      if (isRootElement) {
        jsonStreamWriter.name(FormatJson.D)
            .beginObject();
      }

      if (properties.getInlineCountType() == InlineCount.ALLPAGES) {
        final int inlineCount = properties.getInlineCount() == null ? 0 : properties.getInlineCount();
        jsonStreamWriter.namedStringValueRaw(FormatJson.COUNT, String.valueOf(inlineCount)).separator();
      }

      jsonStreamWriter.name(FormatJson.RESULTS)
          .beginArray();

      appendEntries(writer, entityInfo, data, jsonStreamWriter);

      if (callback != null) {
        appendDeletedEntries(writer, entityInfo, data, callback);
      }

      jsonStreamWriter.endArray();

      appendNextLink(jsonStreamWriter);
      appendDeltaLink(callback, jsonStreamWriter);

      if (isRootElement) {
        jsonStreamWriter.endObject();
      }

      jsonStreamWriter.endObject();
    } catch (final IOException e) {
      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  }

  /**
   * Append as array.
   *
   * @param writer the writer
   * @param entityInfo the entity info
   * @param data the data
   * @throws EntityProviderException the entity provider exception
   */
  public void appendAsArray(final Writer writer, final EntityInfoAggregator entityInfo,
                            final List<Map<String, Object>> data) throws EntityProviderException {
    JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
    try {
      jsonStreamWriter.beginArray();
      appendEntries(writer, entityInfo, data, jsonStreamWriter);
      jsonStreamWriter.endArray();
    } catch (final IOException e) {
      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  }

  /**
   * Append deleted entries.
   *
   * @param writer the writer
   * @param entityInfo the entity info
   * @param data the data
   * @param callback the callback
   * @throws EntityProviderException the entity provider exception
   */
  private void appendDeletedEntries(final Writer writer, final EntityInfoAggregator entityInfo,
      final List<Map<String, Object>> data, TombstoneCallback callback) throws EntityProviderException {
    JsonDeletedEntryEntityProducer deletedEntryProducer = new JsonDeletedEntryEntityProducer(properties);
    TombstoneCallbackResult callbackResult = callback.getTombstoneCallbackResult();
    List<Map<String, Object>> deletedEntries = callbackResult.getDeletedEntriesData();
    if (deletedEntries != null) {
      deletedEntryProducer.append(writer, entityInfo, deletedEntries, data.isEmpty());
    }
  }

  /**
   * Append entries.
   *
   * @param writer the writer
   * @param entityInfo the entity info
   * @param data the data
   * @param jsonStreamWriter the json stream writer
   * @throws EntityProviderException the entity provider exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void appendEntries(final Writer writer, final EntityInfoAggregator entityInfo,
      final List<Map<String, Object>> data, JsonStreamWriter jsonStreamWriter) throws EntityProviderException,
      IOException {
    JsonEntryEntityProducer entryProducer = new JsonEntryEntityProducer(properties);
    boolean first = true;
    for (final Map<String, Object> entryData : data) {
      if (first) {
        first = false;
      } else {
        jsonStreamWriter.separator();
      }
      entryProducer.append(writer, entityInfo, entryData, false);
    }
  }

  /**
   * Gets the tombstone callback.
   *
   * @return the tombstone callback
   */
  private TombstoneCallback getTombstoneCallback() {
    if (properties.getCallbacks() != null
        && properties.getCallbacks().containsKey(TombstoneCallback.CALLBACK_KEY_TOMBSTONE)) {
      TombstoneCallback callback =
          (TombstoneCallback) properties.getCallbacks().get(TombstoneCallback.CALLBACK_KEY_TOMBSTONE);
      return callback;
    } else {
      return null;
    }
  }

  /**
   * Append next link.
   *
   * @param jsonStreamWriter the json stream writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void appendNextLink(final JsonStreamWriter jsonStreamWriter) throws IOException {
    // Write "next" link.
    // To be compatible with other implementations out there, the link is
    // written directly after "__next" and not as "{"uri":"next link"}",
    // deviating from the OData 2.0 specification.
    if (properties.getNextLink() != null) {
      jsonStreamWriter.separator()
          .namedStringValue(FormatJson.NEXT, properties.getNextLink());
    }
  }

  /**
   * Append delta link.
   *
   * @param callback the callback
   * @param jsonStreamWriter the json stream writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void appendDeltaLink(final TombstoneCallback callback, final JsonStreamWriter jsonStreamWriter)
      throws IOException {
    if (callback != null) {
      TombstoneCallbackResult callbackResult = callback.getTombstoneCallbackResult();

      String deltaLink = callbackResult.getDeltaLink();
      if (deltaLink != null) {
        jsonStreamWriter.separator().namedStringValue(FormatJson.DELTA, deltaLink);
      }
    }
  }
}
