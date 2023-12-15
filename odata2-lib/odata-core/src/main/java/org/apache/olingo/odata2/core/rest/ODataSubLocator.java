/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.apache.olingo.odata2.core.rest;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataInternalServerErrorException;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.ODataContextImpl;
import org.apache.olingo.odata2.core.ODataRequestHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.Response;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataSubLocator.
 */
public final class ODataSubLocator {

    /** The service factory. */
    private ODataServiceFactory serviceFactory;
    
    /** The request. */
    private ODataRequest request;

    /** The http request. */
    private HttpServletRequest httpRequest;

    /**
     * Handle get.
     *
     * @return the response
     * @throws ODataException the o data exception
     */
    @GET
    public Response handleGet() throws ODataException {
        return handle(ODataHttpMethod.GET);
    }

    /**
     * Handle put.
     *
     * @return the response
     * @throws ODataException the o data exception
     */
    @PUT
    public Response handlePut() throws ODataException {
        return handle(ODataHttpMethod.PUT);
    }

    /**
     * Handle patch.
     *
     * @return the response
     * @throws ODataException the o data exception
     */
    @PATCH
    public Response handlePatch() throws ODataException {
        return handle(ODataHttpMethod.PATCH);
    }

    /**
     * Handle merge.
     *
     * @return the response
     * @throws ODataException the o data exception
     */
    @MERGE
    public Response handleMerge() throws ODataException {
        return handle(ODataHttpMethod.MERGE);
    }

    /**
     * Handle delete.
     *
     * @return the response
     * @throws ODataException the o data exception
     */
    @DELETE
    public Response handleDelete() throws ODataException {
        return handle(ODataHttpMethod.DELETE);
    }

    /**
     * Handle post.
     *
     * @param xHttpMethod the x http method
     * @param xHttpMethodOverride the x http method override
     * @return the response
     * @throws ODataException the o data exception
     */
    @POST
    public Response handlePost(@HeaderParam("X-HTTP-Method") final String xHttpMethod,
            @HeaderParam("X-HTTP-Method-Override") String xHttpMethodOverride) throws ODataException {
        if (xHttpMethod == null && xHttpMethodOverride != null) {
            return handleMethodOverride(xHttpMethodOverride);
        }

        if (xHttpMethod == null) {
            return handle(ODataHttpMethod.POST);
        }

        /* tunneling */
        if ("MERGE".equals(xHttpMethod)) {
            return handle(ODataHttpMethod.MERGE);
        }
        if ("PATCH".equals(xHttpMethod)) {
            return handle(ODataHttpMethod.PATCH);
        }
        if (HttpMethod.DELETE.equals(xHttpMethod)) {
            return handle(ODataHttpMethod.DELETE);
        }
        if (HttpMethod.PUT.equals(xHttpMethod)) {
            return handle(ODataHttpMethod.PUT);
        }
        if (HttpMethod.GET.equals(xHttpMethod)) {
            return handle(ODataHttpMethod.GET);
        }
        if (HttpMethod.POST.equals(xHttpMethod)) {
            return handle(ODataHttpMethod.POST);
        }
        if (HttpMethod.HEAD.equals(xHttpMethod)) {
            return handleHead();
        }
        if (HttpMethod.OPTIONS.equals(xHttpMethod)) {
            return handleOptions();
        }
        return returnNotImplementedResponse(ODataNotImplementedException.TUNNELING);
    }

    /**
     * Handle method override.
     *
     * @param xHttpMethodOverride the x http method override
     * @return the response
     * @throws ODataException the o data exception
     */
    private Response handleMethodOverride(String xHttpMethodOverride) throws ODataException {
        switch (xHttpMethodOverride.toUpperCase()) {
            case HttpMethod.OPTIONS:
                return handleOptions();
            case HttpMethod.GET:
                return handleGet();
            case HttpMethod.DELETE:
                return handleDelete();
            case HttpMethod.HEAD:
                return handleHead();
            case "MERGE":
                return handleMerge();
            case HttpMethod.PATCH:
                return handlePatch();
            case HttpMethod.POST:
                return handle(ODataHttpMethod.POST);
            case HttpMethod.PUT:
                return handlePut();
            default:
                return returnNotImplementedResponse(ODataNotImplementedException.COMMON);
        }
    }

    /**
     * Return not implemented response.
     *
     * @param messageReference the message reference
     * @return the response
     */
    private Response returnNotImplementedResponse(final MessageReference messageReference) {
        // RFC 2616, 5.1.1: "An origin server SHOULD return the status code [...]
        // 501 (Not Implemented) if the method is unrecognized [...] by the origin server."
        return returnException(new ODataNotImplementedException(messageReference));
    }

    /**
     * Return exception.
     *
     * @param messageException the message exception
     * @return the response
     */
    private Response returnException(final ODataMessageException messageException) {
        ODataContextImpl context = new ODataContextImpl(request, serviceFactory);
        context.setRequest(request);
        context.setAcceptableLanguages(request.getAcceptableLanguages());
        context.setPathInfo(request.getPathInfo());
        context.setServiceFactory(serviceFactory);
        context.setParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT, httpRequest);
        ODataExceptionWrapper exceptionWrapper =
                new ODataExceptionWrapper(context, request.getQueryParameters(), request.getAcceptHeaders());
        ODataResponse response = exceptionWrapper.wrapInExceptionResponse(messageException);
        return RestUtil.convertResponse(response);
    }

    /**
     * Return no service response.
     *
     * @param messageReference the message reference
     * @return the response
     */
    private Response returnNoServiceResponse(MessageReference messageReference) {
        return returnException(new ODataInternalServerErrorException(messageReference));
    }

    /**
     * Handle options.
     *
     * @return the response
     */
    @OPTIONS
    public Response handleOptions() {
        // RFC 2616, 5.1.1: "An origin server SHOULD return the status code [...]
        // 501 (Not Implemented) if the method is unrecognized or not implemented
        // by the origin server."
        return returnNotImplementedResponse(ODataNotImplementedException.COMMON);
    }

    /**
     * Handle head.
     *
     * @return the response
     * @throws ODataException the o data exception
     */
    @HEAD
    public Response handleHead() throws ODataException {
        return handleGet();
    }

    /**
     * Handle.
     *
     * @param method the method
     * @return the response
     * @throws ODataException the o data exception
     */
    private Response handle(final ODataHttpMethod method) throws ODataException {
        request = ODataRequest.fromRequest(request)
                              .method(method)
                              .build();

        ODataContextImpl context = new ODataContextImpl(request, serviceFactory);
        context.setParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT, httpRequest);

        ODataService service = serviceFactory.createService(context);
        if (service == null) {
            return returnNoServiceResponse(ODataInternalServerErrorException.NOSERVICE);
        }
        service.getProcessor()
               .setContext(context);
        context.setService(service);

        ODataRequestHandler requestHandler = new ODataRequestHandler(serviceFactory, service, context);

        final ODataResponse odataResponse = requestHandler.handle(request);
        return RestUtil.convertResponse(odataResponse);
    }



    /**
     * Creates the.
     *
     * @param param the param
     * @return the o data sub locator
     * @throws ODataException the o data exception
     */
    public static ODataSubLocator create(final SubLocatorParameter param) throws ODataException {
        ODataSubLocator subLocator = new ODataSubLocator();

        subLocator.serviceFactory = param.getServiceFactory();
        subLocator.request = ODataRequest.acceptableLanguages(param.getHttpHeaders()
                                                                   .getAcceptableLanguages())
                                         .httpMethod(param.getServletRequest()
                                                          .getMethod())
                                         .acceptHeaders(RestUtil.extractAcceptHeaders(param))
                                         .body(RestUtil.contentAsStream(RestUtil.extractRequestContent(param)))
                                         .pathInfo(RestUtil.buildODataPathInfo(param))
                                         .allQueryParameters(param.getUriInfo()
                                                                  .getQueryParameters())
                                         .requestHeaders(param.getHttpHeaders()
                                                              .getRequestHeaders())
                                         .contentType(RestUtil.extractRequestContentType(param)
                                                              .toContentTypeString())
                                         .build();

        subLocator.httpRequest = param.getServletRequest();

        return subLocator;
    }

    /**
     * Instantiates a new o data sub locator.
     */
    private ODataSubLocator() {}
}
