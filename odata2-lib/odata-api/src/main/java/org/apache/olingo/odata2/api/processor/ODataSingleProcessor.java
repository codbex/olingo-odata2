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
package org.apache.olingo.odata2.api.processor;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.odata2.api.ODataServiceVersion;
import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.ODataHttpHeaders;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmServiceMetadata;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.apache.olingo.odata2.api.processor.feature.CustomContentType;
import org.apache.olingo.odata2.api.processor.part.BatchProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityComplexPropertyProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityLinkProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityLinksProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityMediaProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityProcessor;
import org.apache.olingo.odata2.api.processor.part.EntitySetProcessor;
import org.apache.olingo.odata2.api.processor.part.EntitySimplePropertyProcessor;
import org.apache.olingo.odata2.api.processor.part.EntitySimplePropertyValueProcessor;
import org.apache.olingo.odata2.api.processor.part.FunctionImportProcessor;
import org.apache.olingo.odata2.api.processor.part.FunctionImportValueProcessor;
import org.apache.olingo.odata2.api.processor.part.MetadataProcessor;
import org.apache.olingo.odata2.api.processor.part.ServiceDocumentProcessor;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetComplexPropertyUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMediaResourceUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMetadataUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetServiceDocumentUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetSimplePropertyUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;

// TODO: Auto-generated Javadoc
/**
 * <p>A default {@link ODataProcessor} that implements all processor features in a single class.</p>
 * <p>It is recommended to derive from this class and it is required by the
 * {@link org.apache.olingo.odata2.api.ODataServiceFactory} to build an
 * {@link org.apache.olingo.odata2.api.ODataService}.</p>
 * <p>This abstract class provides a default behavior, returning the correct response
 * for requests for the service or the metadata document, respectively, and throwing an
 * {@link ODataNotImplementedException} for all other requests.
 * Sub classes have to override only methods they want to support.</p>
 * 
 * 
 */
public abstract class ODataSingleProcessor implements MetadataProcessor, ServiceDocumentProcessor, EntityProcessor,
    EntitySetProcessor, EntityComplexPropertyProcessor, EntityLinkProcessor, EntityLinksProcessor,
    EntityMediaProcessor, EntitySimplePropertyProcessor, EntitySimplePropertyValueProcessor, FunctionImportProcessor,
    FunctionImportValueProcessor, BatchProcessor, CustomContentType {

  /**
   * A request context object usually injected by the OData library.
   */
  private ODataContext context;

  /**
   * Sets the context.
   *
   * @param context the new context
   * @see ODataProcessor
   */
  @Override
  public void setContext(final ODataContext context) {
    this.context = context;
  }

  /**
   * Gets the context.
   *
   * @return the context
   * @see ODataProcessor
   */
  @Override
  public ODataContext getContext() {
    return context;
  }

  /**
   * Execute batch.
   *
   * @param handler the handler
   * @param contentType the content type
   * @param content the content
   * @return the o data response
   * @throws ODataException the o data exception
   * @see BatchProcessor
   */
  @Override
  public ODataResponse executeBatch(final BatchHandler handler, final String contentType, final InputStream content)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Execute change set.
   *
   * @param handler the handler
   * @param requests the requests
   * @return the batch response part
   * @throws ODataException the o data exception
   * @see BatchProcessor
   */
  @Override
  public BatchResponsePart executeChangeSet(final BatchHandler handler, final List<ODataRequest> requests)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Execute function import.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see FunctionImportProcessor
   */
  @Override
  public ODataResponse executeFunctionImport(final GetFunctionImportUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Execute function import value.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see FunctionImportValueProcessor
   */
  @Override
  public ODataResponse executeFunctionImportValue(final GetFunctionImportUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Read entity simple property value.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntitySimplePropertyValueProcessor
   */
  @Override
  public ODataResponse readEntitySimplePropertyValue(final GetSimplePropertyUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Update entity simple property value.
   *
   * @param uriInfo the uri info
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntitySimplePropertyValueProcessor
   */
  @Override
  public ODataResponse updateEntitySimplePropertyValue(final PutMergePatchUriInfo uriInfo, final InputStream content,
      final String requestContentType, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Delete entity simple property value.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntitySimplePropertyValueProcessor
   */
  @Override
  public ODataResponse deleteEntitySimplePropertyValue(final DeleteUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Read entity simple property.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntitySimplePropertyProcessor
   */
  @Override
  public ODataResponse readEntitySimpleProperty(final GetSimplePropertyUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Update entity simple property.
   *
   * @param uriInfo the uri info
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntitySimplePropertyProcessor
   */
  @Override
  public ODataResponse updateEntitySimpleProperty(final PutMergePatchUriInfo uriInfo, final InputStream content,
      final String requestContentType, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Read entity media.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityMediaProcessor
   */
  @Override
  public ODataResponse readEntityMedia(final GetMediaResourceUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Update entity media.
   *
   * @param uriInfo the uri info
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityMediaProcessor
   */
  @Override
  public ODataResponse updateEntityMedia(final PutMergePatchUriInfo uriInfo, final InputStream content,
      final String requestContentType, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Delete entity media.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityMediaProcessor
   */
  @Override
  public ODataResponse deleteEntityMedia(final DeleteUriInfo uriInfo, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Read entity links.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityLinksProcessor
   */
  @Override
  public ODataResponse readEntityLinks(final GetEntitySetLinksUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Count entity links.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityLinksProcessor
   */
  @Override
  public ODataResponse countEntityLinks(final GetEntitySetLinksCountUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Creates the entity link.
   *
   * @param uriInfo the uri info
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityLinkProcessor
   */
  @Override
  public ODataResponse createEntityLink(final PostUriInfo uriInfo, final InputStream content,
      final String requestContentType, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Read entity link.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityLinkProcessor
   */
  @Override
  public ODataResponse readEntityLink(final GetEntityLinkUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Exists entity link.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityLinkProcessor
   */
  @Override
  public ODataResponse existsEntityLink(final GetEntityLinkCountUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Update entity link.
   *
   * @param uriInfo the uri info
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityLinkProcessor
   */
  @Override
  public ODataResponse updateEntityLink(final PutMergePatchUriInfo uriInfo, final InputStream content,
      final String requestContentType, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Delete entity link.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityLinkProcessor
   */
  @Override
  public ODataResponse deleteEntityLink(final DeleteUriInfo uriInfo, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Read entity complex property.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityComplexPropertyProcessor
   */
  @Override
  public ODataResponse readEntityComplexProperty(final GetComplexPropertyUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Update entity complex property.
   *
   * @param uriInfo the uri info
   * @param content the content
   * @param requestContentType the request content type
   * @param merge the merge
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityComplexPropertyProcessor
   */
  @Override
  public ODataResponse updateEntityComplexProperty(final PutMergePatchUriInfo uriInfo, final InputStream content,
      final String requestContentType, final boolean merge, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Read entity set.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntitySetProcessor
   */
  @Override
  public ODataResponse readEntitySet(final GetEntitySetUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Count entity set.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntitySetProcessor
   */
  @Override
  public ODataResponse countEntitySet(final GetEntitySetCountUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Creates the entity.
   *
   * @param uriInfo the uri info
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntitySetProcessor
   */
  @Override
  public ODataResponse createEntity(final PostUriInfo uriInfo, final InputStream content,
      final String requestContentType, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Read entity.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityProcessor
   */
  @Override
  public ODataResponse readEntity(final GetEntityUriInfo uriInfo, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Exists entity.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityProcessor
   */
  @Override
  public ODataResponse existsEntity(final GetEntityCountUriInfo uriInfo, final String contentType)
      throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Update entity.
   *
   * @param uriInfo the uri info
   * @param content the content
   * @param requestContentType the request content type
   * @param merge the merge
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityProcessor
   */
  @Override
  public ODataResponse updateEntity(final PutMergePatchUriInfo uriInfo, final InputStream content,
      final String requestContentType, final boolean merge, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Delete entity.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see EntityProcessor
   */
  @Override
  public ODataResponse deleteEntity(final DeleteUriInfo uriInfo, final String contentType) throws ODataException {
    throw new ODataNotImplementedException();
  }

  /**
   * Read service document.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see ServiceDocumentProcessor
   */
  @Override
  public ODataResponse readServiceDocument(final GetServiceDocumentUriInfo uriInfo, final String contentType)
      throws ODataException {
    final Edm edm = getContext().getService().getEntityDataModel();

    //Service Document has version 1.0 specifically
    if ("HEAD".equals(getContext().getHttpMethod())) {
      return ODataResponse.header(ODataHttpHeaders.DATASERVICEVERSION, ODataServiceVersion.V10).build();
    } else {
      final String serviceRoot = getContext().getPathInfo().getServiceRoot().toASCIIString();
      final ODataResponse response = EntityProvider.writeServiceDocument(contentType, edm, serviceRoot);
      return ODataResponse.fromResponse(response)
          .header(ODataHttpHeaders.DATASERVICEVERSION, ODataServiceVersion.V10).build();
    }
  }

  /**
   * Read metadata.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   * @see MetadataProcessor
   */
  @Override
  public ODataResponse readMetadata(final GetMetadataUriInfo uriInfo, final String contentType) throws ODataException {
    final EdmServiceMetadata edmServiceMetadata = getContext().getService().getEntityDataModel().getServiceMetadata();

    return ODataResponse.status(HttpStatusCodes.OK)
        .header(ODataHttpHeaders.DATASERVICEVERSION, edmServiceMetadata.getDataServiceVersion())
        .entity(edmServiceMetadata.getMetadata()).build();
  }

  /**
   * Gets the custom content types.
   *
   * @param processorFeature the processor feature
   * @return the custom content types
   * @throws ODataException the o data exception
   * @see CustomContentType
   */
  @Override
  public List<String> getCustomContentTypes(final Class<? extends ODataProcessor> processorFeature)
      throws ODataException {
    return Collections.emptyList();
  }
}
