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
package org.apache.olingo.odata2.fit.ref;

import static org.junit.Assert.assertTrue;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentNegotiationTest.
 */
public class ContentNegotiationTest extends AbstractRefTest {

  /**
   * Instantiates a new content negotiation test.
   *
   * @param servletType the servlet type
   */
  public ContentNegotiationTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * Format overwrite accept header.
   *
   * @throws Exception the exception
   */
  @Test
  public void formatOverwriteAcceptHeader() throws Exception {
    final HttpResponse response = callUri("?$format=xml", HttpHeaders.ACCEPT, IMAGE_GIF, HttpStatusCodes.OK);
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
  }

  /**
   * Format xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void formatXml() throws Exception {
    final HttpResponse response = callUri("?$format=xml");
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
  }

  /**
   * Format json.
   *
   * @throws Exception the exception
   */
  @Test
  public void formatJson() throws Exception {
    final HttpResponse response = callUri("?$format=json");
    checkMediaType(response, HttpContentType.APPLICATION_JSON);
  }

  /**
   * Format atom.
   *
   * @throws Exception the exception
   */
  @Test
  public void formatAtom() throws Exception {
    final HttpResponse response = callUri("Rooms('1')?$format=atom");
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
  }

  /**
   * Format not supported.
   *
   * @throws Exception the exception
   */
  @Test
  public void formatNotSupported() throws Exception {
    callUri("?$format=XXXML", HttpStatusCodes.NOT_ACCEPTABLE);
  }

  /**
   * Content type metadata.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeMetadata() throws Exception {
    final HttpResponse response = callUri("$metadata");
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
  }

  /**
   * Content type metadata not accepted.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeMetadataNotAccepted() throws Exception {
    callUri("$metadata", HttpHeaders.ACCEPT, IMAGE_GIF, HttpStatusCodes.NOT_ACCEPTABLE);
  }

  /**
   * Browser accept header.
   *
   * @throws Exception the exception
   */
  @Test
  public void browserAcceptHeader() throws Exception {
    final HttpResponse response = callUri("$metadata",
        HttpHeaders.ACCEPT, "text/html, application/xhtml+xml, application/xml;q=0.9, */*;q=0.8",
        HttpStatusCodes.OK);
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
  }

  /**
   * Browser issue 95.
   *
   * @throws Exception the exception
   */
  @Test
  public void browserIssue95() throws Exception {
    final HttpResponse response = callUri("Employees",
        HttpHeaders.ACCEPT, "application/atomsvc+xml;q=0.9, application/json;q=0.8, */*;q=0.1",
        HttpStatusCodes.OK);
    checkMediaType(response, HttpContentType.APPLICATION_JSON);
  }

  /**
   * Content type service document wo accept header.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeServiceDocumentWoAcceptHeader() throws Exception {
    final HttpResponse response = callUri("");
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_SVC_UTF8);
    assertTrue(getBody(response).length() > 100);
  }

  /**
   * Content type service document atom xml not accept.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeServiceDocumentAtomXmlNotAccept() throws Exception {
    final HttpResponse response =
        callUri("", HttpHeaders.ACCEPT, HttpContentType.APPLICATION_ATOM_XML, HttpStatusCodes.NOT_ACCEPTABLE);
    checkMediaType(response, HttpContentType.APPLICATION_XML);
    String body = getBody(response);
    assertTrue(body.length() > 100);
    assertTrue(body.contains("error"));
  }

  /**
   * Content type service document xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeServiceDocumentXml() throws Exception {
    final HttpResponse response = callUri("", HttpHeaders.ACCEPT, HttpContentType.APPLICATION_XML, HttpStatusCodes.OK);
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    assertTrue(getBody(response).length() > 100);
  }

  /**
   * Content type service document atom svc xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeServiceDocumentAtomSvcXml() throws Exception {
    final HttpResponse response =
        callUri("", HttpHeaders.ACCEPT, HttpContentType.APPLICATION_ATOM_SVC, HttpStatusCodes.OK);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_SVC_UTF8);
    assertTrue(getBody(response).length() > 100);
  }

  /**
   * Content type service document accept headers.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeServiceDocumentAcceptHeaders() throws Exception {
    final HttpResponse response = callUri("",
        HttpHeaders.ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        HttpStatusCodes.OK);
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    assertTrue(getBody(response).length() > 100);
  }

  /**
   * Request content type different.
   *
   * @throws Exception the exception
   */
  @Test
  public void requestContentTypeDifferent() throws Exception {
    String body = getBody(callUri("Rooms('1')"));
    final HttpResponse response = postUri("Rooms", body,
        HttpContentType.APPLICATION_ATOM_XML, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertTrue(getBody(response).length() > 100);
  }

  /**
   * Post with unsupported content type feed.
   *
   * @throws Exception the exception
   */
  @Test
  public void postWithUnsupportedContentTypeFeed() throws Exception {
    String body = getBody(callUri("Rooms('1')"));
    final HttpResponse response = postUri("Rooms", body,
        HttpContentType.APPLICATION_ATOM_XML_FEED_UTF8, HttpStatusCodes.UNSUPPORTED_MEDIA_TYPE);
    checkMediaType(response, HttpContentType.APPLICATION_XML);
    assertTrue(getBody(response).length() > 100);
  }
}
