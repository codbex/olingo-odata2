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
package org.apache.olingo.odata2.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.olingo.odata2.api.exception.ODataBadRequestException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotAcceptableException;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.core.uri.UriType;

// TODO: Auto-generated Javadoc
/**
 * Handles content negotiation with handling of OData special cases.
 */
public class ContentNegotiator {
  
  /** The Constant URI_INFO_FORMAT_JSON. */
  private static final String URI_INFO_FORMAT_JSON = "json";
  
  /** The Constant URI_INFO_FORMAT_ATOM. */
  private static final String URI_INFO_FORMAT_ATOM = "atom";
  
  /** The Constant URI_INFO_FORMAT_XML. */
  private static final String URI_INFO_FORMAT_XML = "xml";
  
  /** The Constant DEFAULT_CHARSET. */
  static final String DEFAULT_CHARSET = "utf-8";

  /**
   * Do the content negotiation for <code>accept header value</code> based on
   * requested content type (in HTTP accept header from {@link ODataRequest})
   * in combination with uri information from {@link org.apache.olingo.odata2.api.uri.UriInfo} and from given supported
   * content types (via
   * <code>supportedContentTypes</code>).
   *
   * @param odataRequest the odata request
   * @param uriInfo specific uri information
   * @param supportedContentTypes list of supported content types
   * @return best fitting content type or <code>NULL</code> if content type is not set and for given
   * {@link org.apache.olingo.odata2.api.uri.UriInfo} is
   * ignored
   * @throws ODataException if no supported content type was found
   * @throws IllegalArgumentException if one of the input parameter is <code>NULL</code>
   */
  public ContentType doContentNegotiation(final ODataRequest odataRequest, final UriInfoImpl uriInfo,
      final List<String> supportedContentTypes) throws ODataException {
    validateNotNull(odataRequest, uriInfo, supportedContentTypes);

    if (uriInfo.isCount()) {
      return ContentType.TEXT_PLAIN_CS_UTF_8;
    } else if (uriInfo.isValue()) {
      if (uriInfo.getUriType() == UriType.URI5 || uriInfo.getUriType() == UriType.URI4) {
        return ContentType.TEXT_PLAIN_CS_UTF_8;
      }
      return doContentNegotiationForAcceptHeader(Arrays.asList("*/*"), ContentType.create(supportedContentTypes));
    }

    if (uriInfo.getFormat() == null) {
      return doContentNegotiationForAcceptHeader(odataRequest.getAcceptHeaders(), ContentType
          .create(supportedContentTypes));
    } else {
      return doContentNegotiationForFormat(uriInfo, ContentType.createAsCustom(supportedContentTypes));
    }
  }

  /**
   * Validate not null.
   *
   * @param odataRequest the odata request
   * @param uriInfo the uri info
   * @param supportedContentTypes the supported content types
   */
  private void validateNotNull(final ODataRequest odataRequest, final UriInfoImpl uriInfo,
      final List<String> supportedContentTypes) {
    if (uriInfo == null) {
      throw new IllegalArgumentException("Parameter uriInfo MUST NOT be null.");
    }
    if (odataRequest == null) {
      throw new IllegalArgumentException("Parameter odataRequest MUST NOT be null.");
    }
    if (supportedContentTypes == null) {
      throw new IllegalArgumentException("Parameter supportedContentTypes MUST NOT be null.");
    }
  }

  /**
   * Do content negotiation for format.
   *
   * @param uriInfo the uri info
   * @param supportedContentTypes the supported content types
   * @return the content type
   * @throws ODataException the o data exception
   */
  private ContentType doContentNegotiationForFormat(final UriInfoImpl uriInfo,
      final List<ContentType> supportedContentTypes) throws ODataException {
    validateFormatQuery(uriInfo);
    ContentType formatContentType = mapFormat(uriInfo);
    formatContentType = ensureCharset(formatContentType);

    for (final ContentType contentType : supportedContentTypes) {
      if (contentType.equals(formatContentType)) {
        return formatContentType;
      }
    }

    throw new ODataNotAcceptableException(ODataNotAcceptableException.NOT_SUPPORTED_CONTENT_TYPE.addContent(uriInfo
        .getFormat()));
  }

  /**
   * Validates that <code>dollar format query/syntax</code> is correct for further processing.
   * If some validation error occurs an exception is thrown.
   *
   * @param uriInfo the uri info
   * @throws ODataBadRequestException the o data bad request exception
   */
  private void validateFormatQuery(final UriInfoImpl uriInfo) throws ODataBadRequestException {
    if (uriInfo.isValue()) {
      throw new ODataBadRequestException(ODataBadRequestException.INVALID_SYNTAX);
    }
  }

  /**
   * Map format.
   *
   * @param uriInfo the uri info
   * @return the content type
   */
  private ContentType mapFormat(final UriInfoImpl uriInfo) {
    final String format = uriInfo.getFormat();
    if (URI_INFO_FORMAT_XML.equals(format)) {
      return ContentType.APPLICATION_XML;
    } else if (URI_INFO_FORMAT_ATOM.equals(format)) {
      if (uriInfo.getUriType() == UriType.URI0) {
        // special handling for serviceDocument uris (UriType.URI0)
        return ContentType.APPLICATION_ATOM_SVC;
      } else if (uriInfo.getUriType() == UriType.URI1) {
        return ContentType.APPLICATION_ATOM_XML_FEED;
      } else if (uriInfo.getUriType() == UriType.URI2 || uriInfo.getUriType() == UriType.URI10) {
        return ContentType.APPLICATION_ATOM_XML_ENTRY;
      }
    } else if (URI_INFO_FORMAT_JSON.equals(format)) {
      return ContentType.APPLICATION_JSON;
    }

    return ContentType.createAsCustom(format);
  }

  /**
   * Do content negotiation for accept header.
   *
   * @param acceptHeaderContentTypes the accept header content types
   * @param supportedContentTypes the supported content types
   * @return the content type
   * @throws ODataException the o data exception
   */
  private ContentType doContentNegotiationForAcceptHeader(final List<String> acceptHeaderContentTypes,
      final List<ContentType> supportedContentTypes) throws ODataException {
    return contentNegotiation(extractAcceptHeaders(acceptHeaderContentTypes), supportedContentTypes);
  }

  /**
   * Extract accept headers.
   *
   * @param acceptHeaderValues the accept header values
   * @return the list
   * @throws ODataBadRequestException the o data bad request exception
   */
  private List<ContentType> extractAcceptHeaders(final List<String> acceptHeaderValues)
      throws ODataBadRequestException {
    final List<ContentType> mediaTypes = new ArrayList<ContentType>();
    if (acceptHeaderValues != null) {
      for (final String mediaType : acceptHeaderValues) {
        try {
          mediaTypes.add(ContentType.create(mediaType.toString()));
        } catch (IllegalArgumentException e) {
          throw new ODataBadRequestException(ODataBadRequestException.INVALID_HEADER.addContent("Accept")
              .addContent(mediaType.toString()), e);
        }
      }
    }

    return mediaTypes;
  }

  /**
   * Content negotiation.
   *
   * @param acceptedContentTypes the accepted content types
   * @param supportedContentTypes the supported content types
   * @return the content type
   * @throws ODataException the o data exception
   */
  ContentType contentNegotiation(final List<ContentType> acceptedContentTypes,
      final List<ContentType> supportedContentTypes) throws ODataException {
    final Set<ContentType> setSupported = new HashSet<ContentType>(supportedContentTypes);

    if (acceptedContentTypes.isEmpty()) {
      if (!setSupported.isEmpty()) {
        return supportedContentTypes.get(0);
      }
    } else {
      for (ContentType contentType : acceptedContentTypes) {
        contentType = ensureCharset(contentType);
        final ContentType match = contentType.match(supportedContentTypes);
        if (match != null) {
          return match;
        }
      }
    }

    throw new ODataNotAcceptableException(ODataNotAcceptableException.NOT_SUPPORTED_ACCEPT_HEADER
        .addContent(acceptedContentTypes.toString()));
  }

  /**
   * Ensure charset.
   *
   * @param contentType the content type
   * @return the content type
   */
  private ContentType ensureCharset(final ContentType contentType) {
    if (ContentType.APPLICATION_ATOM_XML.isCompatible(contentType)
        || ContentType.APPLICATION_ATOM_SVC.isCompatible(contentType)
        || ContentType.APPLICATION_XML.isCompatible(contentType)) {
      return contentType.receiveWithCharsetParameter(DEFAULT_CHARSET);
    }
    return contentType;
  }
}
