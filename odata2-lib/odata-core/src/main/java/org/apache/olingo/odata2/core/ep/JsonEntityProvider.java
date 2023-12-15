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

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.odata2.api.ODataServiceVersion;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.commons.ODataHttpHeaders;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataResponse.ODataResponseBuilder;
import org.apache.olingo.odata2.api.servicedocument.ServiceDocument;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.aggregator.EntityPropertyInfo;
import org.apache.olingo.odata2.core.ep.consumer.JsonEntityConsumer;
import org.apache.olingo.odata2.core.ep.consumer.JsonErrorDocumentConsumer;
import org.apache.olingo.odata2.core.ep.consumer.JsonServiceDocumentConsumer;
import org.apache.olingo.odata2.core.ep.producer.JsonCollectionEntityProducer;
import org.apache.olingo.odata2.core.ep.producer.JsonEntryEntityProducer;
import org.apache.olingo.odata2.core.ep.producer.JsonErrorDocumentProducer;
import org.apache.olingo.odata2.core.ep.producer.JsonFeedEntityProducer;
import org.apache.olingo.odata2.core.ep.producer.JsonLinkEntityProducer;
import org.apache.olingo.odata2.core.ep.producer.JsonLinksEntityProducer;
import org.apache.olingo.odata2.core.ep.producer.JsonPropertyEntityProducer;
import org.apache.olingo.odata2.core.ep.producer.JsonServiceDocumentProducer;
import org.apache.olingo.odata2.core.ep.util.CircleStreamBuffer;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonEntityProvider.
 */
public class JsonEntityProvider implements ContentTypeBasedEntityProvider {

  /** The Constant DEFAULT_CHARSET. */
  private static final String DEFAULT_CHARSET = "UTF-8";

  /**
   * <p>Serializes an error message according to the OData standard.</p>
   * <p>In case an error occurs, it is logged.
   * An exception is not thrown because this method is used in exception handling.</p>
   * @param status the {@link HttpStatusCodes status code} associated with this error
   * @param errorCode a String that serves as a substatus to the HTTP response code
   * @param message a human-readable message describing the error
   * @param locale the {@link Locale} that should be used to format the error message
   * @param innerError the inner error for this message; if it is null or an empty String
   * no inner error tag is shown inside the response structure
   * @return an {@link ODataResponse} containing the serialized error message
   */
  @Override
  public ODataResponse writeErrorDocument(final HttpStatusCodes status, final String errorCode, final String message,
      final Locale locale, final String innerError) {
      ODataErrorContext context = new ODataErrorContext();
      context.setHttpStatus(status);
      context.setErrorCode(errorCode);
      context.setMessage(message);
      context.setLocale(locale);
      context.setInnerError(innerError);

      return writeErrorDocument(context);
  }

  /**
   * <p>Serializes an error message according to the OData standard.</p>
   * <p>In case an error occurs, it is logged.
   * An exception is not thrown because this method is used in exception handling.</p>
   * @param context the {@link ODataErrorContext} associated with this error
   * @return an {@link ODataResponse} containing the serialized error message
   */
  @Override
  public ODataResponse writeErrorDocument(ODataErrorContext context) {
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), DEFAULT_CHARSET));
      new JsonErrorDocumentProducer().writeErrorDocument(writer, context);
      writer.flush();
      buffer.closeWrite();

      return ODataResponse.status(context.getHttpStatus())
          .entity(buffer.getInputStream())
          .header(ODataHttpHeaders.DATASERVICEVERSION, ODataServiceVersion.V10)
          .build();
    } catch (Exception e) {
      buffer.close();
      throw new ODataRuntimeException(e);
    }
  }

  /**
   * Writes service document based on given {@link Edm} and <code>service root</code>.
   *
   * @param edm the Entity Data Model
   * @param serviceRoot the root URI of the service
   * @return resulting {@link ODataResponse} with written service document
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeServiceDocument(final Edm edm, final String serviceRoot) throws EntityProviderException {
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), DEFAULT_CHARSET));
      JsonServiceDocumentProducer.writeServiceDocument(writer, edm);
      writer.flush();
      buffer.closeWrite();

      return ODataResponse.entity(buffer.getInputStream())
          .header(ODataHttpHeaders.DATASERVICEVERSION, ODataServiceVersion.V10)
          .build();
    } catch (Exception e) {
      buffer.close();
      throw new EntityProviderProducerException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  }

  /**
   * Write entry.
   *
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeEntry(final EdmEntitySet entitySet, final Map<String, Object> data,
      final EntityProviderWriteProperties properties) throws EntityProviderException {
    final EntityInfoAggregator entityInfo = EntityInfoAggregator.create(entitySet, properties.getExpandSelectTree());
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), DEFAULT_CHARSET));
      JsonEntryEntityProducer producer = new JsonEntryEntityProducer(properties);
      producer.append(writer, entityInfo, data, true);
      writer.flush();
      buffer.closeWrite();

      return ODataResponse.entity(buffer.getInputStream())
          .eTag(producer.getETag())
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
   * Write property.
   *
   * @param edmProperty the edm property
   * @param value the value
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeProperty(final EdmProperty edmProperty, final Object value) throws EntityProviderException {
    return writeSingleTypedElement(EntityInfoAggregator.create(edmProperty), value);
  }

  /**
   * Write single typed element.
   *
   * @param propertyInfo the property info
   * @param value the value
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  private ODataResponse writeSingleTypedElement(final EntityPropertyInfo propertyInfo, final Object value)
      throws EntityProviderException {
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      OutputStream outStream = buffer.getOutputStream();
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream, DEFAULT_CHARSET));
      new JsonPropertyEntityProducer().append(writer, propertyInfo, value);
      writer.flush();
      buffer.closeWrite();

      return ODataResponse.entity(buffer.getInputStream())
          .header(ODataHttpHeaders.DATASERVICEVERSION, ODataServiceVersion.V10)
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
   * Write feed.
   *
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeFeed(final EdmEntitySet entitySet, final List<Map<String, Object>> data,
      final EntityProviderWriteProperties properties) throws EntityProviderException {
    final EntityInfoAggregator entityInfo = EntityInfoAggregator.create(entitySet, properties.getExpandSelectTree());
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), DEFAULT_CHARSET));
      new JsonFeedEntityProducer(properties).appendAsObject(writer, entityInfo, data, true);
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
   * Write link.
   *
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeLink(final EdmEntitySet entitySet, final Map<String, Object> data,
      final EntityProviderWriteProperties properties) throws EntityProviderException {
    final EntityInfoAggregator entityInfo = EntityInfoAggregator.create(entitySet, properties.getExpandSelectTree());
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), DEFAULT_CHARSET));
      new JsonLinkEntityProducer(properties).append(writer, entityInfo, data);
      writer.flush();
      buffer.closeWrite();

      return ODataResponse.entity(buffer.getInputStream())
          .header(ODataHttpHeaders.DATASERVICEVERSION, ODataServiceVersion.V10)
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
   * Write links.
   *
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataResponse writeLinks(final EdmEntitySet entitySet, final List<Map<String, Object>> data,
      final EntityProviderWriteProperties properties) throws EntityProviderException {
    final EntityInfoAggregator entityInfo = EntityInfoAggregator.create(entitySet, properties.getExpandSelectTree());
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), DEFAULT_CHARSET));
      new JsonLinksEntityProducer(properties).append(writer, entityInfo, data);
      writer.flush();
      buffer.closeWrite();

      ODataResponseBuilder response = ODataResponse.entity(buffer.getInputStream());
      if (properties.getInlineCountType() != InlineCount.ALLPAGES) {
        response = response.header(ODataHttpHeaders.DATASERVICEVERSION, ODataServiceVersion.V10);
      }
      return response.build();
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
   * Write collection.
   *
   * @param propertyInfo the property info
   * @param data the data
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  private ODataResponse writeCollection(final EntityPropertyInfo propertyInfo, final List<?> data)
      throws EntityProviderException {
    CircleStreamBuffer buffer = new CircleStreamBuffer();

    try {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(buffer.getOutputStream(), DEFAULT_CHARSET));
      new JsonCollectionEntityProducer().append(writer, propertyInfo, data);
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
   * Write function import.
   *
   * @param functionImport the function import
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  @SuppressWarnings("unchecked")
  public ODataResponse writeFunctionImport(final EdmFunctionImport functionImport, final Object data,
      final EntityProviderWriteProperties properties) throws EntityProviderException {
    try {
      if(functionImport.getReturnType() !=null){
        final boolean isCollection = functionImport.getReturnType().getMultiplicity() == EdmMultiplicity.MANY;
        if (functionImport.getReturnType().getType().getKind() == EdmTypeKind.ENTITY) {
          if (isCollection) {
               List<Map<String, Object>> dataList = (List<Map<String, Object>>) data;
               return writeFeed(functionImport.getEntitySet(), dataList, properties);
          } else {
               return writeEntry(functionImport.getEntitySet(), (Map<String, Object>)data, properties);
          }
        }

        final EntityPropertyInfo info = EntityInfoAggregator.create(functionImport);
        if (isCollection) {
          return writeCollection(info, (List<?>) data);
        } else {
          return writeSingleTypedElement(info, data);
        }
      }else{
        return ODataResponse.newBuilder().status(HttpStatusCodes.ACCEPTED).build();
      }
    } catch (final EdmException e) {
      throw new EntityProviderProducerException(e.getMessageReference(), e);
    }
  }

  /**
   * Read feed.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data feed
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataFeed readFeed(final EdmEntitySet entitySet, final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    return new JsonEntityConsumer().readFeed(entitySet, content, properties);
  }

  /**
   * Read entry.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data entry
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataEntry readEntry(final EdmEntitySet entitySet, final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    return new JsonEntityConsumer().readEntry(entitySet, content, properties);
  }

  /**
   * Read property.
   *
   * @param edmProperty the edm property
   * @param content the content
   * @param properties the properties
   * @return the map
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public Map<String, Object> readProperty(final EdmProperty edmProperty, final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    return new JsonEntityConsumer().readProperty(edmProperty, content, properties);
  }

  /**
   * Read function import.
   *
   * @param functionImport the function import
   * @param content the content
   * @param properties the properties
   * @return the object
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public Object readFunctionImport(final EdmFunctionImport functionImport, final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    try {
      if (functionImport.getReturnType().getType().getKind() == EdmTypeKind.ENTITY) {
        return functionImport.getReturnType().getMultiplicity() == EdmMultiplicity.MANY
            ? new JsonEntityConsumer().readFeed(functionImport.getEntitySet(), content, properties)
            : new JsonEntityConsumer().readEntry(functionImport.getEntitySet(), content, properties);
      } else {
        final EntityPropertyInfo info = EntityInfoAggregator.create(functionImport);
        return functionImport.getReturnType().getMultiplicity() == EdmMultiplicity.MANY ?
            new JsonEntityConsumer().readCollection(info, content, properties) :
            new JsonEntityConsumer().readProperty(info, content, properties).get(info.getName());
      }
    } catch (final EdmException e) {
      throw new EntityProviderException(e.getMessageReference(), e);
    }
  }

  /**
   * Read link.
   *
   * @param entitySet the entity set
   * @param content the content
   * @return the string
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public String readLink(final EdmEntitySet entitySet, final InputStream content) throws EntityProviderException {
    return new JsonEntityConsumer().readLink(entitySet, content);
  }

  /**
   * Read links.
   *
   * @param entitySet the entity set
   * @param content the content
   * @return the list
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public List<String> readLinks(final EdmEntitySet entitySet, final InputStream content)
      throws EntityProviderException {
    return new JsonEntityConsumer().readLinks(entitySet, content);
  }

  /**
   * Read service document.
   *
   * @param serviceDocument the service document
   * @return the service document
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ServiceDocument readServiceDocument(final InputStream serviceDocument) throws EntityProviderException {
    return new JsonServiceDocumentConsumer().parseJson(serviceDocument);
  }

  /**
   * Read delta feed.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data delta feed
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataDeltaFeed readDeltaFeed(final EdmEntitySet entitySet, final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    return new JsonEntityConsumer().readDeltaFeed(entitySet, content, properties);
  }

  /**
   * Read error document.
   *
   * @param errorDocument the error document
   * @return the o data error context
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ODataErrorContext readErrorDocument(final InputStream errorDocument) throws EntityProviderException {
    return new JsonErrorDocumentConsumer().readError(errorDocument);
  }
}
