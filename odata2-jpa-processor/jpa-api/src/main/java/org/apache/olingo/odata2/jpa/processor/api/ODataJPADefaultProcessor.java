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
package org.apache.olingo.odata2.jpa.processor.api;

import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.batch.BatchRequestPart;
import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderBatchProperties;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataJPADefaultProcessor.
 */
public abstract class ODataJPADefaultProcessor extends ODataJPAProcessor {

  /**
   * Instantiates a new o data JPA default processor.
   *
   * @param oDataJPAContext the o data JPA context
   */
  public ODataJPADefaultProcessor(final ODataJPAContext oDataJPAContext) {
    super(oDataJPAContext);
  }

  /**
   * Read entity set.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse readEntitySet(final GetEntitySetUriInfo uriParserResultView, final String contentType)
      throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      List<Object> jpaEntities = jpaProcessor.process(uriParserResultView);
      oDataResponse =
          responseBuilder.build(uriParserResultView, jpaEntities, contentType);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Read entity.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse readEntity(final GetEntityUriInfo uriParserResultView, final String contentType)
      throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      Object jpaEntity = jpaProcessor.process(uriParserResultView);
      oDataResponse =
          responseBuilder.build(uriParserResultView, jpaEntity, contentType);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Count entity set.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse countEntitySet(final GetEntitySetCountUriInfo uriParserResultView, final String contentType)
      throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      long jpaEntityCount = jpaProcessor.process(uriParserResultView);
      oDataResponse = responseBuilder.build(jpaEntityCount);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Exists entity.
   *
   * @param uriInfo the uri info
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse existsEntity(final GetEntityCountUriInfo uriInfo, final String contentType)
      throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      long jpaEntityCount = jpaProcessor.process(uriInfo);
      oDataResponse = responseBuilder.build(jpaEntityCount);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Creates the entity.
   *
   * @param uriParserResultView the uri parser result view
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse createEntity(final PostUriInfo uriParserResultView, final InputStream content,
      final String requestContentType, final String contentType) throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      Object createdJpaEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
      oDataResponse =
          responseBuilder.build(uriParserResultView, createdJpaEntity, contentType);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Update entity.
   *
   * @param uriParserResultView the uri parser result view
   * @param content the content
   * @param requestContentType the request content type
   * @param merge the merge
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse updateEntity(final PutMergePatchUriInfo uriParserResultView, final InputStream content,
      final String requestContentType, final boolean merge, final String contentType) throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      Object jpaEntity = jpaProcessor.process(uriParserResultView, content, requestContentType);
      oDataResponse = responseBuilder.build(uriParserResultView, jpaEntity);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Delete entity.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse deleteEntity(final DeleteUriInfo uriParserResultView, final String contentType)
      throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      Object deletedObj = jpaProcessor.process(uriParserResultView, contentType);
      oDataResponse = responseBuilder.build(uriParserResultView, deletedObj);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Execute function import.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse executeFunctionImport(final GetFunctionImportUriInfo uriParserResultView,
      final String contentType) throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      List<Object> resultEntity = jpaProcessor.process(uriParserResultView);
      oDataResponse =
          responseBuilder.build(uriParserResultView, resultEntity, contentType);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Execute function import value.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse executeFunctionImportValue(final GetFunctionImportUriInfo uriParserResultView,
      final String contentType) throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      List<Object> result = jpaProcessor.process(uriParserResultView);
      oDataResponse =
          responseBuilder.build(uriParserResultView, result.get(0));
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Read entity link.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse readEntityLink(final GetEntityLinkUriInfo uriParserResultView, final String contentType)
      throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      Object jpaEntity = jpaProcessor.process(uriParserResultView);
      oDataResponse =
          responseBuilder.build(uriParserResultView, jpaEntity, contentType);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Read entity links.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse readEntityLinks(final GetEntitySetLinksUriInfo uriParserResultView, final String contentType)
      throws ODataException {
    ODataResponse oDataResponse = null;
    try {
      oDataJPAContext.setODataContext(getContext());
      List<Object> jpaEntity = jpaProcessor.process(uriParserResultView);
      oDataResponse =
          responseBuilder.build(uriParserResultView, jpaEntity, contentType);
    } finally {
      close();
    }
    return oDataResponse;
  }

  /**
   * Creates the entity link.
   *
   * @param uriParserResultView the uri parser result view
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse createEntityLink(final PostUriInfo uriParserResultView, final InputStream content,
      final String requestContentType, final String contentType) throws ODataException {
    try {
      oDataJPAContext.setODataContext(getContext());
      jpaProcessor.process(uriParserResultView, content, requestContentType, contentType);
      return ODataResponse.newBuilder().build();
    } finally {
      close();
    }
  }

  /**
   * Update entity link.
   *
   * @param uriParserResultView the uri parser result view
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse updateEntityLink(final PutMergePatchUriInfo uriParserResultView, final InputStream content,
      final String requestContentType, final String contentType) throws ODataException {
    try {
      oDataJPAContext.setODataContext(getContext());
      jpaProcessor.process(uriParserResultView, content, requestContentType, contentType);
      return ODataResponse.newBuilder().build();
    } finally {
      close();
    }
  }

  /**
   * Delete entity link.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse deleteEntityLink(final DeleteUriInfo uriParserResultView, final String contentType)
      throws ODataException {
    try {
      oDataJPAContext.setODataContext(getContext());
      jpaProcessor.process(uriParserResultView, contentType);
      return ODataResponse.newBuilder().build();
    } finally {
      close();
    }
  }

  /**
   * Execute batch.
   *
   * @param handler the handler
   * @param contentType the content type
   * @param content the content
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse executeBatch(final BatchHandler handler, final String contentType, final InputStream content)
      throws ODataException {
    try {
      oDataJPAContext.setODataContext(getContext());

      ODataResponse batchResponse;
      List<BatchResponsePart> batchResponseParts = new ArrayList<BatchResponsePart>();
      PathInfo pathInfo = getContext().getPathInfo();
      EntityProviderBatchProperties batchProperties = EntityProviderBatchProperties.init().pathInfo(pathInfo).build();
      List<BatchRequestPart> batchParts = EntityProvider.parseBatchRequest(contentType, content, batchProperties);

      for (BatchRequestPart batchPart : batchParts) {
        batchResponseParts.add(handler.handleBatchPart(batchPart));
      }
      batchResponse = EntityProvider.writeBatchResponse(batchResponseParts);
      return batchResponse;
    } finally {
      close(true);
    }
  }

  /**
   * Execute change set.
   *
   * @param handler the handler
   * @param requests the requests
   * @return the batch response part
   * @throws ODataException the o data exception
   */
  @Override
  public BatchResponsePart executeChangeSet(final BatchHandler handler, final List<ODataRequest> requests)
      throws ODataException {
    List<ODataResponse> responses = new ArrayList<ODataResponse>();
    try {
      oDataJPAContext.getODataJPATransaction().begin();

      for (ODataRequest request : requests) {
        oDataJPAContext.setODataContext(getContext());
        ODataResponse response = handler.handleRequest(request);
        if (response.getStatus().getStatusCode() >= HttpStatusCodes.BAD_REQUEST.getStatusCode()) {
          // Rollback
          oDataJPAContext.getODataJPATransaction().rollback();
          List<ODataResponse> errorResponses = new ArrayList<ODataResponse>(1);
          errorResponses.add(response);
          return BatchResponsePart.responses(errorResponses).changeSet(false).build();
        }
        responses.add(response);
      }
      oDataJPAContext.getODataJPATransaction().commit();

      return BatchResponsePart.responses(responses).changeSet(true).build();
    } catch (Exception e) {
      throw new ODataException("Error on processing request content:" + e.getMessage(), e);
    } finally {
      close(true);
    }
  }
}