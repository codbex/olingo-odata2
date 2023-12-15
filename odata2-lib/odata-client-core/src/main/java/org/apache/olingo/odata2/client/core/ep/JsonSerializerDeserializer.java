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
package org.apache.olingo.odata2.client.core.ep;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.olingo.odata2.api.batch.BatchException;
import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.client.batch.BatchPart;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.client.api.ep.ContentTypeBasedDeserializer;
import org.apache.olingo.odata2.client.api.ep.ContentTypeBasedSerializer;
import org.apache.olingo.odata2.client.api.ep.Entity;
import org.apache.olingo.odata2.client.api.ep.EntityCollection;
import org.apache.olingo.odata2.client.api.ep.EntityCollectionSerializerProperties;
import org.apache.olingo.odata2.client.api.ep.EntitySerializerProperties;
import org.apache.olingo.odata2.client.api.ep.EntityStream;
import org.apache.olingo.odata2.client.core.ep.deserializer.JsonEntityDeserializer;
import org.apache.olingo.odata2.client.core.ep.serializer.JsonEntryEntitySerializer;
import org.apache.olingo.odata2.client.core.ep.serializer.JsonFeedEntitySerializer;
import org.apache.olingo.odata2.core.batch.BatchRequestWriter;
import org.apache.olingo.odata2.core.batch.BatchResponseWriter;
import org.apache.olingo.odata2.core.ep.EntityProviderProducerException;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.aggregator.EntityPropertyInfo;
import org.apache.olingo.odata2.core.ep.consumer.JsonErrorDocumentConsumer;
import org.apache.olingo.odata2.core.ep.util.CircleStreamBuffer;

// TODO: Auto-generated Javadoc
/**
 *  This class includes methods to serialize deserialize JSON Content type.
 */
public class JsonSerializerDeserializer implements ContentTypeBasedSerializer, ContentTypeBasedDeserializer {

  /** The Constant DEFAULT_CHARSET. */
  private static final String DEFAULT_CHARSET = "UTF-8";

  /**
   * Write entry.
   *
   * @param entitySet the entity set
   * @param data the data
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeEntry(EdmEntitySet entitySet, Entity data) 
      throws EntityProviderException {

    final EntitySerializerProperties properties = data == null ? 
        EntitySerializerProperties.serviceRoot(null).build() : data.getWriteProperties() == null ? 
            EntitySerializerProperties.serviceRoot(null).build() : data.getWriteProperties();
    final EntityInfoAggregator entityInfo = EntityInfoAggregator.create(entitySet, null);
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), DEFAULT_CHARSET));
      JsonEntryEntitySerializer producer = new JsonEntryEntitySerializer(properties);
      producer.append(writer, entityInfo, data);
      writer.flush();
      buffer.closeWrite();

      return ODataResponse.entity(buffer.getInputStream())
          .idLiteral(producer.getLocation())
          .build();
    } catch (EntityProviderException e) {
      buffer.close();
      throw e;
    } catch (Exception e) {
      buffer.close();
      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  
  }

  /**
   * Read feed.
   *
   * @param entitySet the entity set
   * @param content the content
   * @return the o data feed
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataFeed readFeed(EdmEntitySet entitySet, EntityStream content) 
      throws EntityProviderException {
    return new JsonEntityDeserializer().readFeed(entitySet, content);
  }

  /**
   * Read entry.
   *
   * @param entitySet the entity set
   * @param content the content
   * @return the o data entry
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataEntry readEntry(EdmEntitySet entitySet, EntityStream content) 
      throws EntityProviderException {
    return new JsonEntityDeserializer().readEntry(entitySet, content);
  
  }

  /**
   * Read error document.
   *
   * @param errorDocument the error document
   * @return the o data error context
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataErrorContext readErrorDocument(InputStream errorDocument) throws EntityProviderException {
    return new JsonErrorDocumentConsumer().readError(errorDocument);
  }

  /**
   * Write feed.
   *
   * @param entitySet the entity set
   * @param data the data
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeFeed(EdmEntitySet entitySet, EntityCollection data) throws EntityProviderException {
    final EntityCollectionSerializerProperties properties = data == null ? 
        EntityCollectionSerializerProperties.serviceRoot(null).build() : data.getCollectionProperties() == null ? 
            EntityCollectionSerializerProperties.serviceRoot(null).build() : data.getCollectionProperties();
    final EntityInfoAggregator entityInfo = EntityInfoAggregator.create(entitySet, null);
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), DEFAULT_CHARSET));
      new JsonFeedEntitySerializer(properties).appendAsObject(writer, entityInfo, data);
      writer.flush();
      buffer.closeWrite();

      return ODataResponse.entity(buffer.getInputStream()).build();
    } catch (EntityProviderException e) {
      buffer.close();
      throw e;
    } catch (Exception e) {
      buffer.close();
      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  }

  /**
   * Write batch response.
   *
   * @param batchResponseParts the batch response parts
   * @return the o data response
   * @throws BatchException the batch exception
   */
  @Override
  public ODataResponse writeBatchResponse(List<BatchResponsePart> batchResponseParts) throws BatchException {
    BatchResponseWriter batchWriter = new BatchResponseWriter();
    return batchWriter.writeResponse(batchResponseParts);
  }

  /**
   * Read batch request.
   *
   * @param batchParts the batch parts
   * @param boundary the boundary
   * @return the input stream
   */
  @Override
  public InputStream readBatchRequest(List<BatchPart> batchParts,
      String boundary) {
    BatchRequestWriter batchWriter = new BatchRequestWriter();
    return batchWriter.writeBatchRequest(batchParts, boundary);
  }

  /**
   * Read function import.
   *
   * @param functionImport the function import
   * @param content the content
   * @return the object
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public Object readFunctionImport(EdmFunctionImport functionImport, EntityStream content)
      throws EntityProviderException {
    try {
      if (functionImport.getReturnType().getType().getKind() == EdmTypeKind.ENTITY) {
        return functionImport.getReturnType().getMultiplicity() == EdmMultiplicity.MANY
            ? new JsonEntityDeserializer().readFeed(functionImport.getEntitySet(), content)
            : new JsonEntityDeserializer().readEntry(functionImport.getEntitySet(), content);
      } else {
        final EntityPropertyInfo info = EntityInfoAggregator.create(functionImport);
        return functionImport.getReturnType().getMultiplicity() == EdmMultiplicity.MANY ?
            new JsonEntityDeserializer().readCollection(info, content) :
            new JsonEntityDeserializer().readProperty(info, content).get(info.getName());
      }
    } catch (final EdmException e) {
      throw new EntityProviderException(e.getMessageReference(), e);
    }
  }
}
