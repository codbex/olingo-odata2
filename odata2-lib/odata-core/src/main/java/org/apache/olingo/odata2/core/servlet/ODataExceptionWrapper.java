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
package org.apache.olingo.odata2.core.servlet;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.batch.BatchException;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataHttpException;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.ep.EntityProviderProducerException;
import org.apache.olingo.odata2.core.ep.ProviderFacadeImpl;
import org.apache.olingo.odata2.core.exception.MessageService;
import org.apache.olingo.odata2.core.exception.MessageService.Message;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataExceptionWrapper.
 */
public class ODataExceptionWrapper {

  /** The Constant DOLLAR_FORMAT. */
  private static final String DOLLAR_FORMAT = "$format";
  
  /** The Constant DOLLAR_FORMAT_JSON. */
  private static final String DOLLAR_FORMAT_JSON = "json";
  
  /** The Constant DEFAULT_RESPONSE_LOCALE. */
  private static final Locale DEFAULT_RESPONSE_LOCALE = Locale.ENGLISH;
  
  /** The error context. */
  private final ODataErrorContext errorContext = new ODataErrorContext();
  
  /** The content type. */
  private final String contentType;
  
  /** The message locale. */
  private final Locale messageLocale;
  
  /** The http request headers. */
  private final Map<String, List<String>> httpRequestHeaders;
  
  /** The callback. */
  private final ODataErrorCallback callback;
  
  /** The request uri. */
  private URI requestUri;

  /**
   * Instantiates a new o data exception wrapper.
   *
   * @param req the req
   * @param serviceFactory the service factory
   */
  public ODataExceptionWrapper(final HttpServletRequest req, ODataServiceFactory serviceFactory) {
    try {
      requestUri = new URI(req.getRequestURI());
    } catch (URISyntaxException e) {
      requestUri = null;
    }
    httpRequestHeaders = RestUtil.extractHeaders(req);
    Map<String, String> queryParameters;
    try {
      queryParameters = RestUtil.extractQueryParameters(req.getQueryString());
    } catch (Exception e) {
      queryParameters = new HashMap<String, String>();
    }
    List<Locale> acceptableLanguages = RestUtil.extractAcceptableLanguage(req.getHeader("Accept-Language"));
    List<String> acceptHeaders = RestUtil.extractAcceptHeaders(req.getHeader("Accept"));
    contentType = getContentType(queryParameters, acceptHeaders).toContentTypeString();
    messageLocale = MessageService.getSupportedLocale(getLanguages(acceptableLanguages), DEFAULT_RESPONSE_LOCALE);
    callback = serviceFactory.getCallback(ODataErrorCallback.class);
  }

  /**
   * Wrap in exception response.
   *
   * @param exception the exception
   * @return the o data response
   */
  public ODataResponse wrapInExceptionResponse(final Exception exception) {
    try {
      final Exception toHandleException = extractException(exception);
      fillErrorContext(toHandleException);
      if (toHandleException instanceof ODataApplicationException) {
        enhanceContextWithApplicationException((ODataApplicationException) toHandleException);
      } else if (toHandleException instanceof ODataMessageException) {
        enhanceContextWithMessageException((ODataMessageException) toHandleException);
      }

      ODataResponse oDataResponse;
      if (callback != null) {
        oDataResponse = handleErrorCallback(callback);
      } else {
        oDataResponse = EntityProvider.writeErrorDocument(errorContext);
      }
      if (!oDataResponse.containsHeader(org.apache.olingo.odata2.api.commons.HttpHeaders.CONTENT_TYPE)) {
        oDataResponse = ODataResponse.fromResponse(oDataResponse).contentHeader(contentType).build();
      }
      return oDataResponse;
    } catch (Exception e) {
      ODataResponse response = ODataResponse.entity("Exception during error handling occured!")
          .contentHeader(ContentType.TEXT_PLAIN.toContentTypeString())
          .status(HttpStatusCodes.INTERNAL_SERVER_ERROR).build();
      return response;
    }
  }

  /**
   * Handle error callback.
   *
   * @param callback the callback
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  private ODataResponse handleErrorCallback(final ODataErrorCallback callback) throws EntityProviderException {
    ODataResponse oDataResponse;
    try {
      oDataResponse = callback.handleError(errorContext);
    } catch (ODataApplicationException e) {
      fillErrorContext(e);
      enhanceContextWithApplicationException(e);
      oDataResponse = new ProviderFacadeImpl().writeErrorDocument(errorContext);
    }
    return oDataResponse;
  }

  /**
   * Extract exception.
   *
   * @param exception the exception
   * @return the exception
   */
  private Exception extractException(final Exception exception) {
    if (exception instanceof ODataException) {
      ODataException odataException = (ODataException) exception;
      if (odataException.isCausedByApplicationException()) {
        return odataException.getApplicationExceptionCause();
      } else if (odataException.isCausedByHttpException()) {
        return odataException.getHttpExceptionCause();
      } else if (odataException.isCausedByMessageException()) {
        return odataException.getMessageExceptionCause();
      }
    }
    return exception;
  }

  /**
   * Enhance context with application exception.
   *
   * @param toHandleException the to handle exception
   */
  private void enhanceContextWithApplicationException(final ODataApplicationException toHandleException) {
    errorContext.setHttpStatus(toHandleException.getHttpStatus());
    errorContext.setErrorCode(toHandleException.getCode());
  }

  /**
   * Enhance context with message exception.
   *
   * @param toHandleException the to handle exception
   */
  private void enhanceContextWithMessageException(final ODataMessageException toHandleException) {
    errorContext.setErrorCode(toHandleException.getErrorCode());
    MessageReference messageReference = toHandleException.getMessageReference();
    Message localizedMessage = messageReference == null ? null : extractEntity(messageReference);
    if (localizedMessage != null) {
      errorContext.setMessage(localizedMessage.getText());
      errorContext.setLocale(localizedMessage.getLocale());
    }
    if (toHandleException instanceof ODataHttpException) {
      errorContext.setHttpStatus(((ODataHttpException) toHandleException).getHttpStatus());
    } else if (toHandleException instanceof EntityProviderException) {
      if(toHandleException instanceof EntityProviderProducerException){
        /*
         * As per OLINGO-763 serializer exceptions are produced by the server and must therefore result 
         * in a 500 internal server error
         */
        errorContext.setHttpStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
      }else{
        errorContext.setHttpStatus(HttpStatusCodes.BAD_REQUEST);
      }
    } else if (toHandleException instanceof BatchException) {
      errorContext.setHttpStatus(HttpStatusCodes.BAD_REQUEST);
    }

  }

  /**
   * Extract entity.
   *
   * @param context the context
   * @return the message
   */
  private Message extractEntity(final MessageReference context) {
    return MessageService.getMessage(messageLocale, context);
  }

  /**
   * Gets the languages.
   *
   * @param acceptableLanguages the acceptable languages
   * @return the languages
   */
  private List<Locale> getLanguages(final List<Locale> acceptableLanguages) {
    if (acceptableLanguages.isEmpty()) {
      return Arrays.asList(DEFAULT_RESPONSE_LOCALE);
    }
    return acceptableLanguages;

  }

  /**
   * Gets the content type.
   *
   * @param queryParameters the query parameters
   * @param acceptHeaders the accept headers
   * @return the content type
   */
  private ContentType getContentType(final Map<String, String> queryParameters, final List<String> acceptHeaders) {
    ContentType cntType = getContentTypeByUriInfo(queryParameters);
    if (cntType == null) {
      cntType = getContentTypeByAcceptHeader(acceptHeaders);
    }
    return cntType;
  }

  /**
   * Gets the content type by uri info.
   *
   * @param queryParameters the query parameters
   * @return the content type by uri info
   */
  private ContentType getContentTypeByUriInfo(final Map<String, String> queryParameters) {
    ContentType cntType = null;
    if (queryParameters != null) {
      if (queryParameters.containsKey(DOLLAR_FORMAT)) {
        String contentTypeString = queryParameters.get(DOLLAR_FORMAT);
        if (DOLLAR_FORMAT_JSON.equals(contentTypeString)) {
          cntType = ContentType.APPLICATION_JSON;
        } else {
          // Any format mentioned in the $format parameter other than json results in an application/xml content type
          // for error messages due to the OData V2 Specification.
          cntType = ContentType.APPLICATION_XML;
        }
      }
    }
    return cntType;
  }

  /**
   * Gets the content type by accept header.
   *
   * @param acceptHeaders the accept headers
   * @return the content type by accept header
   */
  private ContentType getContentTypeByAcceptHeader(final List<String> acceptHeaders) {
    for (String type : acceptHeaders) {
      if (ContentType.isParseable(type)) {
        ContentType convertedContentType = ContentType.create(type);
        if (convertedContentType.isWildcard()
            || ContentType.APPLICATION_XML.equals(convertedContentType)
            || ContentType.APPLICATION_XML_CS_UTF_8.equals(convertedContentType)
            || ContentType.APPLICATION_ATOM_XML.equals(convertedContentType)
            || ContentType.APPLICATION_ATOM_XML_CS_UTF_8.equals(convertedContentType)) {
          return ContentType.APPLICATION_XML;
        } else if (ContentType.APPLICATION_JSON.equals(convertedContentType)
            || ContentType.APPLICATION_JSON_CS_UTF_8.equals(convertedContentType)) {
          return ContentType.APPLICATION_JSON;
        }
      }
    }
    return ContentType.APPLICATION_XML;
  }

  /**
   * Fill error context.
   *
   * @param exception the exception
   */
  private void fillErrorContext(final Exception exception) {
    errorContext.setContentType(contentType);
    errorContext.setException(exception);
    errorContext.setHttpStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
    errorContext.setErrorCode(null);
    errorContext.setMessage(exception.getMessage());
    errorContext.setLocale(DEFAULT_RESPONSE_LOCALE);
    errorContext.setRequestUri(requestUri);

    if (httpRequestHeaders != null) {
      for (Entry<String, List<String>> entry : httpRequestHeaders.entrySet()) {
        errorContext.putRequestHeader(entry.getKey(), entry.getValue());
      }
    }
  }
}
