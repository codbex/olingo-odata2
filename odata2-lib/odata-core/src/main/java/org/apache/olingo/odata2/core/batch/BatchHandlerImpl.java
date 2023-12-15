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
package org.apache.olingo.odata2.core.batch;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.batch.BatchHandler;
import org.apache.olingo.odata2.api.batch.BatchRequestPart;
import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.core.ODataContextImpl;
import org.apache.olingo.odata2.core.ODataPathSegmentImpl;
import org.apache.olingo.odata2.core.ODataRequestHandler;
import org.apache.olingo.odata2.core.PathInfoImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchHandlerImpl.
 */
public class BatchHandlerImpl implements BatchHandler {
  
  /** The Constant BAD_REQUEST. */
  private static final int BAD_REQUEST = 400;
  
  /** The factory. */
  private ODataServiceFactory factory;
  
  /** The service. */
  private ODataService service;
  
  /** The content id map. */
  private Map<String, String> contentIdMap;
  
  /** The Constant BATCH_ODATA_REQUEST_HEADERS. */
  private static final String BATCH_ODATA_REQUEST_HEADERS = "batchODataRequestHeaders";

  /**
   * Instantiates a new batch handler impl.
   *
   * @param factory the factory
   * @param service the service
   */
  public BatchHandlerImpl(final ODataServiceFactory factory, final ODataService service) {
    this.factory = factory;
    this.service = service;
    contentIdMap = new HashMap<String, String>();
  }

  /**
   * Handle batch part.
   *
   * @param batchPart the batch part
   * @return the batch response part
   * @throws ODataException the o data exception
   */
  @Override
  public BatchResponsePart handleBatchPart(final BatchRequestPart batchPart) throws ODataException {
    if (batchPart.isChangeSet()) {
      List<ODataRequest> changeSetRequests = batchPart.getRequests();
      return service.getBatchProcessor().executeChangeSet(this, changeSetRequests);
    } else {
      if (batchPart.getRequests().size() != 1) {
        throw new ODataException("Query Operation should contain one request");
      }
      ODataRequest request = batchPart.getRequests().get(0);
      String mimeHeaderContentId =
          request.getRequestHeaderValue(BatchHelper.MIME_HEADER_CONTENT_ID.toLowerCase(Locale.ENGLISH));
      String requestHeaderContentId =
          request.getRequestHeaderValue(BatchHelper.REQUEST_HEADER_CONTENT_ID.toLowerCase(Locale.ENGLISH));

      List<PathSegment> odataSegments = request.getPathInfo().getODataSegments();
      if (!odataSegments.isEmpty() && odataSegments.get(0).getPath().matches("\\$.*")) {
        request = modifyRequest(request, odataSegments);
      }
      ODataRequestHandler handler = createHandler(request);
      ODataResponse response = setContentIdHeader(request, handler.handle(request), 
          mimeHeaderContentId, requestHeaderContentId);
      List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
      responses.add(response);
      return BatchResponsePart.responses(responses).changeSet(false).build();
    }
  }

  /**
   * Handle request.
   *
   * @param suppliedRequest the supplied request
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse handleRequest(final ODataRequest suppliedRequest) throws ODataException {
    ODataRequest request;
    String mimeHeaderContentId =
        suppliedRequest.getRequestHeaderValue(BatchHelper.MIME_HEADER_CONTENT_ID.toLowerCase(Locale.ENGLISH));
    String requestHeaderContentId =
        suppliedRequest.getRequestHeaderValue(BatchHelper.REQUEST_HEADER_CONTENT_ID.toLowerCase(Locale.ENGLISH));

    List<PathSegment> odataSegments = suppliedRequest.getPathInfo().getODataSegments();
    if (!odataSegments.isEmpty() && odataSegments.get(0).getPath().matches("\\$.*")) {
      request = modifyRequest(suppliedRequest, odataSegments);
    } else {
      request = suppliedRequest;
    }
    ODataRequestHandler handler = createHandler(request);
    ODataResponse response = handler.handle(request);
    if (response.getStatus().getStatusCode() < BAD_REQUEST) {
      response = setContentIdHeader(request, response, mimeHeaderContentId, requestHeaderContentId);
    }
    if (request.getMethod().equals(ODataHttpMethod.POST)) {
      String baseUri = getBaseUri(request);
      if (mimeHeaderContentId != null) {
        fillContentIdMap(response, mimeHeaderContentId, baseUri);
      } else if (requestHeaderContentId != null) {
        fillContentIdMap(response, requestHeaderContentId, baseUri);
      }
    }
    return response;
  }

  /**
   * Fill content id map.
   *
   * @param response the response
   * @param contentId the content id
   * @param baseUri the base uri
   */
  private void fillContentIdMap(final ODataResponse response, final String contentId, final String baseUri) {
    String location = response.getHeader(HttpHeaders.LOCATION);
    if (location != null) {
      String relLocation = location.replace(baseUri + "/", "");
      contentIdMap.put("$" + contentId, relLocation);
    }
  }

  /**
   * Modify request.
   *
   * @param request the request
   * @param odataSegments the odata segments
   * @return the o data request
   * @throws ODataException the o data exception
   */
  private ODataRequest modifyRequest(final ODataRequest request, final List<PathSegment> odataSegments)
      throws ODataException {
    String contentId = contentIdMap.get(odataSegments.get(0).getPath());
    if (contentId == null) {
      // invalid content ID. But throwing an exception here is wrong so we use the base request and fail later
      return request;
    }
    PathInfoImpl pathInfo = new PathInfoImpl();
    try {
      List<PathSegment> modifiedODataSegments = new ArrayList<PathSegment>();
      String[] segments = contentId.split("/");
      for (String segment : segments) {
        modifiedODataSegments.add(new ODataPathSegmentImpl(segment, null));
      }
      String newRequestUri = getBaseUri(request);
      newRequestUri += "/" + contentId;
      for (int i = 1; i < odataSegments.size(); i++) {
        newRequestUri += "/" + odataSegments.get(i).getPath();
        modifiedODataSegments.add(odataSegments.get(i));
      }
      for (Map.Entry<String, String> entry : request.getQueryParameters().entrySet()) {
        newRequestUri += "/" + entry;
      }

      pathInfo.setServiceRoot(request.getPathInfo().getServiceRoot());
      pathInfo.setPrecedingPathSegment(request.getPathInfo().getPrecedingSegments());
      pathInfo.setRequestUri(new URI(newRequestUri));
      pathInfo.setODataPathSegment(modifiedODataSegments);
    } catch (URISyntaxException e) {
      throw new ODataException(e);
    }
    ODataRequest modifiedRequest = ODataRequest.fromRequest(request).pathInfo(pathInfo).build();
    return modifiedRequest;
  }

  /**
   * Sets the content id header.
   *
   * @param request the request
   * @param response the response
   * @param mimeHeaderContentId the mime header content id
   * @param requestHeaderContentId the request header content id
   * @return the o data response
   */
  private ODataResponse setContentIdHeader(ODataRequest request, final ODataResponse response, 
      final String mimeHeaderContentId, final String requestHeaderContentId) {
    ODataResponse modifiedResponse;
    if (requestHeaderContentId != null && mimeHeaderContentId != null) {
      String baseUri = getBaseUri(request);
      fillContentIdMap(response, requestHeaderContentId, baseUri);
      fillContentIdMap(response, mimeHeaderContentId, baseUri);
      modifiedResponse =
          ODataResponse.fromResponse(response).header(BatchHelper.REQUEST_HEADER_CONTENT_ID, requestHeaderContentId)
              .header(BatchHelper.MIME_HEADER_CONTENT_ID, mimeHeaderContentId).build();
    } else if (requestHeaderContentId != null) {
      modifiedResponse =
          ODataResponse.fromResponse(response).header(BatchHelper.REQUEST_HEADER_CONTENT_ID, requestHeaderContentId)
              .build();
    } else if (mimeHeaderContentId != null) {
      modifiedResponse =
          ODataResponse.fromResponse(response).header(BatchHelper.MIME_HEADER_CONTENT_ID, mimeHeaderContentId).build();
    } else {
      return response;
    }
    return modifiedResponse;
  }

  /**
   * Gets the base uri.
   *
   * @param request the request
   * @return the base uri
   */
  private String getBaseUri(final ODataRequest request) {
    // The service root already contains any additional path parameters
    String baseUri = request.getPathInfo().getServiceRoot().toASCIIString();
    if (baseUri.endsWith("/")) {
      baseUri = baseUri.substring(0, baseUri.length() - 1);
    }
    return baseUri;
  }

  /**
   * Creates the handler.
   *
   * @param request the request
   * @return the o data request handler
   * @throws ODataException the o data exception
   */
  private ODataRequestHandler createHandler(final ODataRequest request) throws ODataException {
    ODataContextImpl context = new ODataContextImpl(request, factory);
    ODataContext parentContext = service.getProcessor().getContext();
    context.setBatchParentContext(parentContext);
    context.setService(service);
    if (parentContext != null && parentContext.getParameter(BATCH_ODATA_REQUEST_HEADERS) != null) {
      context.setParameter(BATCH_ODATA_REQUEST_HEADERS, parentContext.getParameter(BATCH_ODATA_REQUEST_HEADERS));
    } else if (parentContext != null && parentContext.getRequestHeaders() != null) {
      context.setParameter(BATCH_ODATA_REQUEST_HEADERS, parentContext.getRequestHeaders());
    }
    service.getProcessor().setContext(context);
    return new ODataRequestHandler(factory, service, context);
  }

}
