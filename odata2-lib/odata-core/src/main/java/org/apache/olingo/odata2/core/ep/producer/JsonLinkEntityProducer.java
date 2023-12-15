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
import java.util.Map;

import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.core.ep.EntityProviderProducerException;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.util.FormatJson;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;

// TODO: Auto-generated Javadoc
/**
 * Producer for writing a link in JSON.
 * 
 */
public class JsonLinkEntityProducer {

  /** The properties. */
  private final EntityProviderWriteProperties properties;

  /**
   * Instantiates a new json link entity producer.
   *
   * @param properties the properties
   * @throws EntityProviderException the entity provider exception
   */
  public JsonLinkEntityProducer(final EntityProviderWriteProperties properties) throws EntityProviderException {
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
  public void append(final Writer writer, final EntityInfoAggregator entityInfo, final Map<String, Object> data)
      throws EntityProviderException {
    JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);

    final String uri = (properties.getServiceRoot() == null ? "" : properties.getServiceRoot().toASCIIString())
        + AtomEntryEntityProducer.createSelfLink(entityInfo, data, null);
    try {
      if (!properties.isOmitJsonWrapper()) {
        jsonStreamWriter.beginObject()
            .name(FormatJson.D);
      }
      appendUri(jsonStreamWriter, uri);
      if (!properties.isOmitJsonWrapper()) {
        jsonStreamWriter.endObject();
      }
    } catch (final IOException e) {
      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  }

  /**
   * Append uri.
   *
   * @param jsonStreamWriter the json stream writer
   * @param uri the uri
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected static void appendUri(final JsonStreamWriter jsonStreamWriter, final String uri) throws IOException {
    jsonStreamWriter.beginObject()
        .namedStringValue(FormatJson.URI, uri)
        .endObject();
  }
}
