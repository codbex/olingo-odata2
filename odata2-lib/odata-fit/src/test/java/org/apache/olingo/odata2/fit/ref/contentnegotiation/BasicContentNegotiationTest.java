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
package org.apache.olingo.odata2.fit.ref.contentnegotiation;

import static org.apache.olingo.odata2.api.commons.HttpContentType.APPLICATION_ATOM_XML;
import static org.apache.olingo.odata2.api.commons.HttpContentType.APPLICATION_ATOM_XML_UTF8;
import static org.apache.olingo.odata2.api.commons.HttpContentType.APPLICATION_JSON;
import static org.apache.olingo.odata2.api.commons.HttpContentType.APPLICATION_JSON_UTF8;
import static org.apache.olingo.odata2.api.commons.HttpContentType.APPLICATION_XML;
import static org.apache.olingo.odata2.api.commons.HttpContentType.APPLICATION_XML_UTF8;
import static org.apache.olingo.odata2.api.commons.HttpContentType.TEXT_PLAIN;
import static org.apache.olingo.odata2.api.commons.HttpContentType.TEXT_PLAIN_UTF8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicContentNegotiationTest.
 */
public class BasicContentNegotiationTest extends AbstractContentNegotiationTest {

  /**
   * Instantiates a new basic content negotiation test.
   *
   * @param servletType the servlet type
   */
  public BasicContentNegotiationTest(final ServletType servletType) {
    super(servletType);
  }

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(BasicContentNegotiationTest.class);

  /**
   * Accept header app atom xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void acceptHeaderAppAtomXml() throws Exception {
    performRequestAndValidateResponseForAcceptHeader("Rooms('1')", APPLICATION_ATOM_XML, APPLICATION_ATOM_XML_UTF8);
  }

  /**
   * Accept header app xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void acceptHeaderAppXml() throws Exception {
    performRequestAndValidateResponseForAcceptHeader("Rooms('1')", APPLICATION_XML, APPLICATION_XML_UTF8);
  }

  /**
   * Accept header app xml charset utf 8.
   *
   * @throws Exception the exception
   */
  @Test
  public void acceptHeaderAppXmlCharsetUtf8() throws Exception {
    performRequestAndValidateResponseForAcceptHeader("Rooms('1')", APPLICATION_XML_UTF8, APPLICATION_XML_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Employees('1')", APPLICATION_XML_UTF8, APPLICATION_XML_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/$count", APPLICATION_XML_UTF8, TEXT_PLAIN_UTF8);
  }

  /**
   * Accept header to content type O data verbose parameter.
   *
   * @throws Exception the exception
   */
  @Test
  public void acceptHeaderToContentTypeODataVerboseParameter() throws Exception {
    final String parameter = ";odata=verbose";
    performRequestAndValidateResponseForAcceptHeader("Employees('1')", APPLICATION_JSON + parameter, APPLICATION_JSON
        + parameter);
    performRequestAndValidateResponseForAcceptHeader("Employees('1')", APPLICATION_JSON_UTF8 + parameter,
        APPLICATION_JSON_UTF8 + parameter);
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/$count", APPLICATION_XML_UTF8 + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/$count", APPLICATION_JSON + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings('1')/$count", APPLICATION_XML_UTF8 + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings('1')/$count", APPLICATION_JSON + parameter,
        TEXT_PLAIN_UTF8);

    performRequestAndValidateResponseForAcceptHeader("Employees/$count", APPLICATION_XML_UTF8 + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Employees/$count", APPLICATION_JSON + parameter, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings/$count", APPLICATION_XML_UTF8 + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings/$count", APPLICATION_JSON + parameter, TEXT_PLAIN_UTF8);
  }

  /**
   * Accept header to content type ignored accept headers.
   *
   * @throws Exception the exception
   */
  @Test
  public void acceptHeaderToContentTypeIgnoredAcceptHeaders() throws Exception {
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/$count", APPLICATION_XML_UTF8, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/$count", APPLICATION_JSON, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings('1')/$count", APPLICATION_XML_UTF8, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings('1')/$count", APPLICATION_JSON, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings('1')/$count", TEXT_PLAIN_UTF8, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings('1')/$count", TEXT_PLAIN, TEXT_PLAIN_UTF8);

    performRequestAndValidateResponseForAcceptHeader("Employees/$count", APPLICATION_XML_UTF8, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Employees/$count", APPLICATION_JSON, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings/$count", APPLICATION_XML_UTF8, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings/$count", APPLICATION_JSON, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings/$count", TEXT_PLAIN_UTF8, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings/$count", TEXT_PLAIN, TEXT_PLAIN_UTF8);

    final String parameter = ";someUnknownParameter=withAValue";
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/$count", APPLICATION_XML_UTF8 + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/$count", APPLICATION_JSON + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings('1')/$count", APPLICATION_XML_UTF8 + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings('1')/$count", APPLICATION_JSON + parameter,
        TEXT_PLAIN_UTF8);

    performRequestAndValidateResponseForAcceptHeader("Employees/$count", APPLICATION_XML_UTF8 + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Employees/$count", APPLICATION_JSON + parameter, TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings/$count", APPLICATION_XML_UTF8 + parameter,
        TEXT_PLAIN_UTF8);
    performRequestAndValidateResponseForAcceptHeader("Buildings/$count", APPLICATION_JSON + parameter, TEXT_PLAIN_UTF8);
  }

  /**
   * Accept header to content type ignored accept headers value.
   *
   * @throws Exception the exception
   */
  @Test
  public void acceptHeaderToContentTypeIgnoredAcceptHeadersValue() throws Exception {
    final String expectedImageContentType = "image/jpeg";
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/$value", APPLICATION_XML_UTF8,
        expectedImageContentType);
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/$value", APPLICATION_JSON,
        expectedImageContentType);

    final String expectedTextContentType = "text/plain;charset=utf-8";
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/Age/$value", APPLICATION_XML_UTF8,
        expectedTextContentType);
    performRequestAndValidateResponseForAcceptHeader("Employees('1')/Age/$value", APPLICATION_JSON,
        expectedTextContentType);
  }

  /**
   * Accept header to content type not acceptable.
   *
   * @throws Exception the exception
   */
  @Test
  public void acceptHeaderToContentTypeNotAcceptable() throws Exception {
    final String parameterUnknown = ";someUnknownParameter=withAValue";
    performRequestAndValidateResponseForNotAcceptable("Employees('1')", APPLICATION_JSON + parameterUnknown);
    performRequestAndValidateResponseForNotAcceptable("Employees('1')", APPLICATION_JSON_UTF8 + parameterUnknown);
    performRequestAndValidateResponseForNotAcceptable("Rooms('1')", APPLICATION_XML + parameterUnknown);
    performRequestAndValidateResponseForNotAcceptable("Employees('1')", APPLICATION_XML_UTF8 + parameterUnknown);
  }

  /**
   * Perform request and validate response for accept header.
   *
   * @param endPointPostfix the end point postfix
   * @param requestAcceptHeader the request accept header
   * @param expectedResponseContentType the expected response content type
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ClientProtocolException the client protocol exception
   */
  private void performRequestAndValidateResponseForAcceptHeader(final String endPointPostfix,
      final String requestAcceptHeader, final String expectedResponseContentType) throws IOException,
      ClientProtocolException {
    HttpGet get = new HttpGet(URI.create(getEndpoint() + endPointPostfix));
    get.setHeader(HttpHeaders.ACCEPT, requestAcceptHeader);
    final HttpResponse response = new DefaultHttpClient().execute(get);

    try {
      assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine().getStatusCode());
      final String contentType = response.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
      assertEquals(ContentType.create(expectedResponseContentType), ContentType.create(contentType));
      assertNotNull(StringHelper.inputStreamToString(response.getEntity().getContent()));
    } catch (AssertionError e) {
      LOG.debug("Response: \n#############\n#\n\n" +
          StringHelper.inputStreamToString(response.getEntity().getContent()) + "\n\n#\n####################");
      throw e;
    }
  }

  /**
   * Perform request and validate response for not acceptable.
   *
   * @param endPointPostfix the end point postfix
   * @param requestAcceptHeader the request accept header
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ClientProtocolException the client protocol exception
   */
  private void performRequestAndValidateResponseForNotAcceptable(final String endPointPostfix,
      final String requestAcceptHeader) throws IOException, ClientProtocolException {
    HttpGet get = new HttpGet(URI.create(getEndpoint() + endPointPostfix));
    get.setHeader(HttpHeaders.ACCEPT, requestAcceptHeader);
    final HttpResponse response = new DefaultHttpClient().execute(get);

    final String contentType = response.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    // assertEquals(expectedResponseContentType, contentType);
    try {

      assertEquals(ContentType.APPLICATION_XML, ContentType.create(contentType));
      assertNotNull(StringHelper.inputStreamToString(response.getEntity().getContent()));
    } catch (AssertionError e) {
      LOG.debug("Response: \n#############\n#\n\n" +
          StringHelper.inputStreamToString(response.getEntity().getContent()) + "\n\n#\n####################");
      throw e;
    }
  }
}
