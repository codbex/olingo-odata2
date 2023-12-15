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

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * Tests employing the reference scenario reading the service document in XML format.
 */
public class ServiceXmlTest extends AbstractRefXmlTest {

  /**
   * Instantiates a new service xml test.
   *
   * @param servletType the servlet type
   */
  public ServiceXmlTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * Service document.
   *
   * @throws Exception the exception
   */
  @Test
  public void serviceDocument() throws Exception {
    final HttpResponse response = callUri("/");
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_SVC_UTF8);
    final String body = getBody(response);
    assertXpathEvaluatesTo("Employees", "/app:service/app:workspace/app:collection[1]/@href", body);
    assertXpathEvaluatesTo("Teams", "/app:service/app:workspace/app:collection[2]/@href", body);
    assertXpathEvaluatesTo("Rooms", "/app:service/app:workspace/app:collection[3]/@href", body);
    assertXpathEvaluatesTo("Managers", "/app:service/app:workspace/app:collection[4]/@href", body);
    assertXpathEvaluatesTo("Buildings", "/app:service/app:workspace/app:collection[5]/@href", body);
    assertXpathEvaluatesTo("Container2.Photos", "/app:service/app:workspace/app:collection[6]/@href", body);

    notFound("invalid.svc");
  }

  /**
   * Service document default.
   *
   * @throws Exception the exception
   */
  @Test
  public void serviceDocumentDefault() throws Exception {
    final HttpResponse response = callUri("");
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_SVC_UTF8);
    validateXmlServiceDocument(getBody(response));
  }

  /**
   * Service document dollar format atom.
   *
   * @throws Exception the exception
   */
  @Test
  public void serviceDocumentDollarFormatAtom() throws Exception {
    HttpResponse response = callUri("?$format=atom");
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_SVC_UTF8);
    validateXmlServiceDocument(getBody(response));
  }

  /**
   * Service document dollar format xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void serviceDocumentDollarFormatXml() throws Exception {
    HttpResponse response = callUri("?$format=xml");
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    validateXmlServiceDocument(getBody(response));
  }

  /**
   * Service document accept header atom.
   *
   * @throws Exception the exception
   */
  @Test
  public void serviceDocumentAcceptHeaderAtom() throws Exception {
    final HttpResponse response =
        callUri("", HttpHeaders.ACCEPT, HttpContentType.APPLICATION_ATOM_XML, HttpStatusCodes.NOT_ACCEPTABLE);
    checkMediaType(response, HttpContentType.APPLICATION_XML);
    validateXmlError(getBody(response));
  }

  /**
   * Service document accept header utf 8 atom.
   *
   * @throws Exception the exception
   */
  @Test
  public void serviceDocumentAcceptHeaderUtf8Atom() throws Exception {
    final HttpResponse response =
        callUri("", HttpHeaders.ACCEPT, HttpContentType.APPLICATION_ATOM_XML_UTF8, HttpStatusCodes.NOT_ACCEPTABLE);
    checkMediaType(response, HttpContentType.APPLICATION_XML);
    validateXmlError(getBody(response));
  }

  /**
   * Service document accept header xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void serviceDocumentAcceptHeaderXml() throws Exception {
    HttpResponse response = callUri("", HttpHeaders.ACCEPT, HttpContentType.APPLICATION_XML);
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    validateXmlServiceDocument(getBody(response));
  }

  /**
   * Service document accept header utf 8 xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void serviceDocumentAcceptHeaderUtf8Xml() throws Exception {
    HttpResponse response = callUri("", HttpHeaders.ACCEPT, HttpContentType.APPLICATION_XML_UTF8);
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    validateXmlServiceDocument(getBody(response));
  }

  /**
   * Validate xml service document.
   *
   * @param payload the payload
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SAXException the SAX exception
   * @throws XpathException the xpath exception
   */
  private void validateXmlServiceDocument(final String payload) throws IOException, SAXException, XpathException {
    assertXpathExists("/app:service", payload);
    assertXpathExists("/app:service/app:workspace", payload);
    assertXpathExists("/app:service/app:workspace/atom:title", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Employees\"]", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Employees\"]/atom:title", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Teams\"]", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Teams\"]/atom:title", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Rooms\"]", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Rooms\"]/atom:title", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Managers\"]", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Managers\"]/atom:title", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Buildings\"]", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Buildings\"]/atom:title", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Container2.Photos\"]", payload);
    assertXpathExists("/app:service/app:workspace/app:collection[@href=\"Container2.Photos\"]/atom:title", payload);
  }
}
