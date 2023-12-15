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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotAcceptableException;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.core.uri.UriType;
import org.junit.Test;
import org.mockito.Mockito;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentNegotiatorTest.
 */
public class ContentNegotiatorTest {

  /**
   * Negotiate content type.
   *
   * @param contentTypes the content types
   * @param supportedTypes the supported types
   * @param expected the expected
   * @throws ODataException the o data exception
   */
  private void negotiateContentType(final List<ContentType> contentTypes, final List<ContentType> supportedTypes,
      final String expected) throws ODataException {
    final ContentType contentType = new ContentNegotiator().contentNegotiation(contentTypes, supportedTypes);
    assertEquals(expected, contentType.toContentTypeString());
  }

  /**
   * Default content type for empty list.
   *
   * @throws ODataException the o data exception
   */
  @Test
  public void defaultContentTypeForEmptyList() throws ODataException {
    List<ContentType> contentTypes = Arrays.asList();
    List<ContentType> supportedTypes = Arrays.asList(ContentType.APPLICATION_ATOM_XML, ContentType.APPLICATION_XML);

    negotiateContentType(contentTypes, supportedTypes, "application/atom+xml");
  }

  /**
   * Invalid content negotiator creation.
   *
   * @throws ODataException the o data exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidContentNegotiatorCreation() throws ODataException {
    final ContentType contentType = new ContentNegotiator().doContentNegotiation(null, null, null);
    assertNull(contentType);
  }

  /**
   * Invalid content negotiator creation null request.
   *
   * @throws ODataException the o data exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidContentNegotiatorCreationNullRequest() throws ODataException {
    UriInfoImpl uriInfo = Mockito.mock(UriInfoImpl.class);
    final ContentType contentType =
        new ContentNegotiator().doContentNegotiation(null, uriInfo, new ArrayList<String>());
    assertNull(contentType);
  }

  /**
   * Invalid content negotiator creation null uri.
   *
   * @throws ODataException the o data exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidContentNegotiatorCreationNullUri() throws ODataException {
    ODataRequest request = Mockito.mock(ODataRequest.class);
    final ContentType contentType =
        new ContentNegotiator().doContentNegotiation(request, null, new ArrayList<String>());
    assertNull(contentType);
  }

  /**
   * Invalid content negotiator creation null supported.
   *
   * @throws ODataException the o data exception
   */
  @Test(expected = IllegalArgumentException.class)
  public void invalidContentNegotiatorCreationNullSupported() throws ODataException {
    ODataRequest request = Mockito.mock(ODataRequest.class);
    UriInfoImpl uriInfo = Mockito.mock(UriInfoImpl.class);
    final ContentType contentType = new ContentNegotiator().doContentNegotiation(request, uriInfo, null);
    assertNull(contentType);
  }

  /**
   * Content negotiation empty request.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationEmptyRequest() throws Exception {
    negotiateContentType(
        contentTypes(),
        contentTypes("sup/111", "sup/222"),
        "sup/111");
  }

  /**
   * Content negotiation concrete request.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationConcreteRequest() throws Exception {
    negotiateContentType(
        contentTypes("sup/222"),
        contentTypes("sup/111", "sup/222"),
        "sup/222");
  }

  /**
   * Content negotiation not supported.
   *
   * @throws Exception the exception
   */
  @Test(expected = ODataNotAcceptableException.class)
  public void contentNegotiationNotSupported() throws Exception {
    negotiateContentType(contentTypes("image/gif"), contentTypes("sup/111", "sup/222"), null);
  }

  /**
   * Content negotiation supported wildcard.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationSupportedWildcard() throws Exception {
    negotiateContentType(
        contentTypes("image/gif"),
        contentTypes("sup/111", "sup/222", "*/*"),
        "image/gif");
  }

  /**
   * Content negotiation supported sub wildcard.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationSupportedSubWildcard() throws Exception {
    negotiateContentType(
        contentTypes("image/gif"),
        contentTypes("sup/111", "sup/222", "image/*"),
        "image/gif");
  }

  /**
   * Content negotiation request wildcard.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationRequestWildcard() throws Exception {
    negotiateContentType(
        contentTypes("*/*"),
        contentTypes("sup/111", "sup/222"),
        "sup/111");
  }

  /**
   * Content negotiation request sub wildcard.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationRequestSubWildcard() throws Exception {
    negotiateContentType(
        contentTypes("sup/*", "*/*"),
        contentTypes("bla/111", "sup/222"),
        "sup/222");
  }

  /**
   * Content negotiation request subtype wildcard.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationRequestSubtypeWildcard() throws Exception {
    negotiateContentType(
        contentTypes("sup2/*"),
        contentTypes("sup1/111", "sup2/222", "sup2/333"),
        "sup2/222");
  }

  /**
   * Content negotiation request response wildcard.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationRequestResponseWildcard() throws Exception {
    negotiateContentType(contentTypes("*/*"), contentTypes("*/*"), "*/*");
  }

  /**
   * Content negotiation many requests.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationManyRequests() throws Exception {
    negotiateContentType(
        contentTypes("bla/111", "bla/blub", "sub2/222"),
        contentTypes("sub1/666", "sub2/222", "sub3/333"),
        "sub2/222");
  }

  /**
   * Content negotiation charset not supported.
   *
   * @throws Exception the exception
   */
  @Test(expected = ODataNotAcceptableException.class)
  public void contentNegotiationCharsetNotSupported() throws Exception {
    negotiateContentType(
        contentTypes("text/plain;charset=iso-8859-1"),
        contentTypes("sup/111", "sup/222"),
        "sup/222");
  }

  /**
   * Content negotiation with O data verbose.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationWithODataVerbose() throws Exception {
    negotiateContentType(
        contentTypes("text/plain;q=0.5", "application/json;odata=verbose;q=0.2", "*/*"),
        contentTypes("application/json;charset=utf-8", "sup/222"),
        "application/json;charset=utf-8");
  }

  /**
   * Content negotiation default charset.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationDefaultCharset() throws Exception {
    negotiateContentTypeCharset("application/xml", "application/xml;charset=utf-8", false);
  }

  /**
   * Content negotiation default charset as dollar format.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationDefaultCharsetAsDollarFormat() throws Exception {
    negotiateContentTypeCharset("application/xml", "application/xml;charset=utf-8", true);
  }

  /**
   * Content negotiation supported charset.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationSupportedCharset() throws Exception {
    negotiateContentTypeCharset("application/xml; charset=utf-8", "application/xml;charset=utf-8", false);
  }

  /**
   * Content negotiation supported charset as dollar format.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentNegotiationSupportedCharsetAsDollarFormat() throws Exception {
    negotiateContentTypeCharset("application/xml; charset=utf-8", "application/xml;charset=utf-8", true);
  }

  /**
   * Negotiate content type charset.
   *
   * @param requestType the request type
   * @param supportedType the supported type
   * @param asFormat the as format
   * @throws ODataException the o data exception
   */
  private void
      negotiateContentTypeCharset(final String requestType, final String supportedType, final boolean asFormat)
          throws ODataException {
    UriInfoImpl uriInfo = Mockito.mock(UriInfoImpl.class);
    Mockito.when(uriInfo.getUriType()).thenReturn(UriType.URI1);
    if (asFormat) {
      Mockito.when(uriInfo.getFormat()).thenReturn(requestType);
    }

    List<String> acceptedContentTypes = Arrays.asList(requestType);
    List<String> supportedContentTypes = Arrays.asList(supportedType);

    ODataRequest request = Mockito.mock(ODataRequest.class);
    Mockito.when(request.getMethod()).thenReturn(ODataHttpMethod.GET);
    Mockito.when(request.getRequestHeaderValue(HttpHeaders.ACCEPT)).thenReturn(requestType);
    Mockito.when(request.getAcceptHeaders()).thenReturn(acceptedContentTypes);

    // perform
    ContentNegotiator negotiator = new ContentNegotiator();
    String negotiatedContentType =
        negotiator.doContentNegotiation(request, uriInfo, supportedContentTypes).toContentTypeString();

    // verify
    assertEquals(supportedType, negotiatedContentType);
  }

  /**
   * Content types.
   *
   * @param contentType the content type
   * @return the list
   */
  private List<ContentType> contentTypes(final String... contentType) {
    List<ContentType> ctList = new ArrayList<ContentType>();
    for (String ct : contentType) {
      ctList.add(ContentType.create(ct));
    }
    return ctList;
  }
}
