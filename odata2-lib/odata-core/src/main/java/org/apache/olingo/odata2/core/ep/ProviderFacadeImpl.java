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
package org.apache.olingo.odata2.core.ep;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.batch.BatchException;
import org.apache.olingo.odata2.api.batch.BatchRequestPart;
import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.client.batch.BatchPart;
import org.apache.olingo.odata2.api.client.batch.BatchSingleResponse;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.provider.DataServices;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.ep.EntityProvider.EntityProviderInterface;
import org.apache.olingo.odata2.api.ep.EntityProviderBatchProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataNotAcceptableException;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.servicedocument.ServiceDocument;
import org.apache.olingo.odata2.core.batch.BatchRequestWriter;
import org.apache.olingo.odata2.core.batch.BatchResponseWriter;
import org.apache.olingo.odata2.core.batch.v2.BatchParser;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.edm.provider.EdmImplProv;
import org.apache.olingo.odata2.core.edm.provider.EdmxProvider;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;

// TODO: Auto-generated Javadoc
/**
 * The Class ProviderFacadeImpl.
 */
public class ProviderFacadeImpl implements EntityProviderInterface {

  /**
   * Creates the.
   *
   * @return the basic entity provider
   * @throws EntityProviderException the entity provider exception
   */
  private static BasicEntityProvider create() throws EntityProviderException {
    return new BasicEntityProvider();
  }

  /**
   * Creates the.
   *
   * @param contentType the content type
   * @return the content type based entity provider
   * @throws EntityProviderException the entity provider exception
   */
  private static ContentTypeBasedEntityProvider create(final String contentType) throws EntityProviderException {
    return create(ContentType.createAsCustom(contentType));
  }

  /**
   * Creates the.
   *
   * @param contentType the content type
   * @return the content type based entity provider
   * @throws EntityProviderException the entity provider exception
   */
  private static ContentTypeBasedEntityProvider create(final ContentType contentType) throws EntityProviderException {
    try {
      switch (contentType.getODataFormat()) {
      case ATOM:
      case XML:
        return new AtomEntityProvider(contentType.getODataFormat());
      case JSON:
        return new JsonEntityProvider();
      default:
        throw new ODataNotAcceptableException(ODataNotAcceptableException.NOT_SUPPORTED_CONTENT_TYPE
            .addContent(contentType));
      }
    } catch (final ODataNotAcceptableException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Write error document.
   *
   * @param context the context
   * @return the o data response
   */
  @Override
  public ODataResponse writeErrorDocument(final ODataErrorContext context) {
    try {
      return create(context.getContentType()).writeErrorDocument(context);
    } catch (EntityProviderException e) {
      throw new ODataRuntimeException(e);
    }
  }

  /**
   * Write service document.
   *
   * @param contentType the content type
   * @param edm the edm
   * @param serviceRoot the service root
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeServiceDocument(final String contentType, final Edm edm, final String serviceRoot)
      throws EntityProviderException {
    return create(contentType).writeServiceDocument(edm, serviceRoot);
  }

  /**
   * Write property value.
   *
   * @param edmProperty the edm property
   * @param value the value
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writePropertyValue(final EdmProperty edmProperty, final Object value)
      throws EntityProviderException {
    return create().writePropertyValue(edmProperty, value);
  }

  /**
   * Write text.
   *
   * @param value the value
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeText(final String value) throws EntityProviderException {
    return create().writeText(value);
  }

  /**
   * Write binary.
   *
   * @param mimeType the mime type
   * @param data the data
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeBinary(final String mimeType, final byte[] data) throws EntityProviderException {
    return create().writeBinary(mimeType, data);
  }

  /**
   * Write feed.
   *
   * @param contentType the content type
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeFeed(final String contentType, final EdmEntitySet entitySet,
      final List<Map<String, Object>> data, final EntityProviderWriteProperties properties)
      throws EntityProviderException {
    return create(contentType).writeFeed(entitySet, data, properties);
  }

  /**
   * Write entry.
   *
   * @param contentType the content type
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeEntry(final String contentType, final EdmEntitySet entitySet,
      final Map<String, Object> data, final EntityProviderWriteProperties properties) throws EntityProviderException {
    return create(contentType).writeEntry(entitySet, data, properties);
  }

  /**
   * Write property.
   *
   * @param contentType the content type
   * @param edmProperty the edm property
   * @param value the value
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeProperty(final String contentType, final EdmProperty edmProperty, final Object value)
      throws EntityProviderException {
    return create(contentType).writeProperty(edmProperty, value);
  }

  /**
   * Write link.
   *
   * @param contentType the content type
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeLink(final String contentType, final EdmEntitySet entitySet,
      final Map<String, Object> data, final EntityProviderWriteProperties properties) throws EntityProviderException {
    return create(contentType).writeLink(entitySet, data, properties);
  }

  /**
   * Write links.
   *
   * @param contentType the content type
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeLinks(final String contentType, final EdmEntitySet entitySet,
      final List<Map<String, Object>> data, final EntityProviderWriteProperties properties)
      throws EntityProviderException {
    return create(contentType).writeLinks(entitySet, data, properties);
  }

  /**
   * Write function import.
   *
   * @param contentType the content type
   * @param functionImport the function import
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeFunctionImport(final String contentType, final EdmFunctionImport functionImport,
      final Object data, final EntityProviderWriteProperties properties) throws EntityProviderException {
    return create(contentType).writeFunctionImport(functionImport, data, properties);
  }

  /**
   * Read error document.
   *
   * @param errorDocument the error document
   * @param contentType the content type
   * @return the o data error context
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataErrorContext readErrorDocument(final InputStream errorDocument, final String contentType)
      throws EntityProviderException {
    return create(contentType).readErrorDocument(errorDocument);
  }

  /**
   * Read feed.
   *
   * @param contentType the content type
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data feed
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataFeed readFeed(final String contentType, final EdmEntitySet entitySet, final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    return create(contentType).readFeed(entitySet, content, properties);
  }

  /**
   * Read delta feed.
   *
   * @param contentType the content type
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data delta feed
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataDeltaFeed readDeltaFeed(final String contentType, final EdmEntitySet entitySet,
      final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    return create(contentType).readDeltaFeed(entitySet, content, properties);
  }

  /**
   * Read entry.
   *
   * @param contentType the content type
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data entry
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataEntry readEntry(final String contentType, final EdmEntitySet entitySet, final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    return create(contentType).readEntry(entitySet, content, properties);
  }

  /**
   * Read property.
   *
   * @param contentType the content type
   * @param edmProperty the edm property
   * @param content the content
   * @param properties the properties
   * @return the map
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public Map<String, Object> readProperty(final String contentType, final EdmProperty edmProperty,
      final InputStream content, final EntityProviderReadProperties properties) throws EntityProviderException {
    return create(contentType).readProperty(edmProperty, content, properties);
  }

  /**
   * Read property value.
   *
   * @param edmProperty the edm property
   * @param content the content
   * @param typeMapping the type mapping
   * @return the object
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public Object readPropertyValue(final EdmProperty edmProperty, final InputStream content, final Class<?> typeMapping)
      throws EntityProviderException {
    return create().readPropertyValue(edmProperty, content, typeMapping);
  }

  /**
   * Read function import.
   *
   * @param contentType the content type
   * @param functionImport the function import
   * @param content the content
   * @param properties the properties
   * @return the object
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public Object readFunctionImport(final String contentType, final EdmFunctionImport functionImport,
      final InputStream content, final EntityProviderReadProperties properties) throws EntityProviderException {
    return create(contentType).readFunctionImport(functionImport, content, properties);
  }

  /**
   * Read links.
   *
   * @param contentType the content type
   * @param entitySet the entity set
   * @param content the content
   * @return the list
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public List<String> readLinks(final String contentType, final EdmEntitySet entitySet, final InputStream content)
      throws EntityProviderException {
    return create(contentType).readLinks(entitySet, content);
  }

  /**
   * Read link.
   *
   * @param contentType the content type
   * @param entitySet the entity set
   * @param content the content
   * @return the string
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public String readLink(final String contentType, final EdmEntitySet entitySet, final InputStream content)
      throws EntityProviderException {
    return create(contentType).readLink(entitySet, content);
  }

  /**
   * Read binary.
   *
   * @param content the content
   * @return the byte[]
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public byte[] readBinary(final InputStream content) throws EntityProviderException {
    return create().readBinary(content);
  }

  /**
   * Write metadata.
   *
   * @param schemas the schemas
   * @param predefinedNamespaces the predefined namespaces
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeMetadata(final List<Schema> schemas, final Map<String, String> predefinedNamespaces)
      throws EntityProviderException {
    return create().writeMetadata(schemas, predefinedNamespaces);
  }

  /**
   * Write metadata.
   *
   * @param seriviceMetadata the serivice metadata
   * @param predefinedNamespaces the predefined namespaces
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeMetadata(final DataServices seriviceMetadata,
      final Map<String, String> predefinedNamespaces) throws EntityProviderException {
    return create().writeMetadata(seriviceMetadata, predefinedNamespaces);
  }

  /**
   * Read metadata.
   *
   * @param inputStream the input stream
   * @param validate the validate
   * @return the edm
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public Edm readMetadata(final InputStream inputStream, final boolean validate) throws EntityProviderException {
    EdmProvider provider = new EdmxProvider().parse(inputStream, validate);
    return new EdmImplProv(provider);
  }

  /**
   * Read service document.
   *
   * @param serviceDocument the service document
   * @param contentType the content type
   * @return the service document
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ServiceDocument readServiceDocument(final InputStream serviceDocument, final String contentType)
      throws EntityProviderException {
    return create(contentType).readServiceDocument(serviceDocument);
  }

  /**
   * Parses the batch request.
   *
   * @param contentType the content type
   * @param content the content
   * @param properties the properties
   * @return the list
   * @throws BatchException the batch exception
   */
  @Override
  public List<BatchRequestPart> parseBatchRequest(final String contentType, final InputStream content,
      final EntityProviderBatchProperties properties) throws BatchException {
    BatchParser batchParser = new BatchParser(contentType, properties, properties.isStrict());
    List<BatchRequestPart> batchParts = batchParser.parseBatchRequest(content);
    return batchParts;
  }

  /**
   * Write batch response.
   *
   * @param batchResponseParts the batch response parts
   * @return the o data response
   * @throws BatchException the batch exception
   */
  @Override
  public ODataResponse writeBatchResponse(final List<BatchResponsePart> batchResponseParts) throws BatchException {
    BatchResponseWriter batchWriter = new BatchResponseWriter();
    return batchWriter.writeResponse(batchResponseParts);
  }

  /**
   * Write batch request.
   *
   * @param batchParts the batch parts
   * @param boundary the boundary
   * @return the input stream
   */
  @Override
  public InputStream writeBatchRequest(final List<BatchPart> batchParts, final String boundary) {
    BatchRequestWriter batchWriter = new BatchRequestWriter();
    return batchWriter.writeBatchRequest(batchParts, boundary);
  }

  /**
   * Parses the batch response.
   *
   * @param contentType the content type
   * @param content the content
   * @return the list
   * @throws BatchException the batch exception
   */
  @Override
  public List<BatchSingleResponse> parseBatchResponse(final String contentType, final InputStream content)
      throws BatchException {
    List<BatchSingleResponse> responses = new BatchParser(contentType, true).parseBatchResponse(content);
    return responses;
  }

}
