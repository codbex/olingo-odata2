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
import org.apache.olingo.odata2.core.ep.EntityProviderProducerException;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.util.FormatJson;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;

// TODO: Auto-generated Javadoc
/**
 * Producer for writing a link collection in JSON.
 * 
 */
public class JsonLinksEntityProducer {

  /** The properties. */
  private final EntityProviderWriteProperties properties;

  /**
   * Instantiates a new json links entity producer.
   *
   * @param properties the properties
   * @throws EntityProviderException the entity provider exception
   */
  public JsonLinksEntityProducer(final EntityProviderWriteProperties properties) throws EntityProviderException {
    this.properties = properties == null ? EntityProviderWriteProperties.serviceRoot(null).build() : properties;
  }

  /**
   * Append.
   *
   * @param writer the writer
   * @param entityInfo the entity info
   * @param data the data
   * @throws EntityProviderException the entity provider exception
   */
  public void append(final Writer writer, final EntityInfoAggregator entityInfo, final List<Map<String, Object>> data)
      throws EntityProviderException {
    JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);

    try {
      if (!properties.isOmitJsonWrapper()) {
        jsonStreamWriter.beginObject()
            .name(FormatJson.D);
      }

      if (properties.getInlineCountType() == InlineCount.ALLPAGES) {
        final int inlineCount = properties.getInlineCount() == null ? 0 : properties.getInlineCount();
        jsonStreamWriter.beginObject()
            .namedStringValueRaw(FormatJson.COUNT, String.valueOf(inlineCount)).separator()
            .name(FormatJson.RESULTS);
      }

      jsonStreamWriter.beginArray();
      final String serviceRoot = properties.getServiceRoot().toASCIIString();
      boolean first = true;
      for (final Map<String, Object> entryData : data) {
        if (first) {
          first = false;
        } else {
          jsonStreamWriter.separator();
        }
        JsonLinkEntityProducer.appendUri(jsonStreamWriter,
            (serviceRoot == null ? "" : serviceRoot)
                + AtomEntryEntityProducer.createSelfLink(entityInfo, entryData, null));
      }
      jsonStreamWriter.endArray();

      if (properties.getInlineCountType() == InlineCount.ALLPAGES) {
        jsonStreamWriter.endObject();
      }
      if (!properties.isOmitJsonWrapper()) {
        jsonStreamWriter.endObject();
      }
    } catch (final IOException e) {
      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  }
}
