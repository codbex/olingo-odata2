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
import static org.junit.Assert.assertFalse;

import org.apache.http.HttpResponse;
import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * Tests employing the reference scenario reading links in XML format.
 */
public final class LinksXmlReadOnlyTest extends AbstractRefXmlTest {

  /**
   * Instantiates a new links xml read only test.
   *
   * @param servletType the servlet type
   */
  public LinksXmlReadOnlyTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * Single link.
   *
   * @throws Exception the exception
   */
  @Test
  public void singleLink() throws Exception {
    HttpResponse response = callUri("Employees('6')/$links/ne_Room");
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    assertXpathEvaluatesTo(getEndpoint() + "Rooms('2')", "/d:uri", getBody(response));

    response = callUri("Managers('3')/$links/nm_Employees('5')");
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    assertXpathEvaluatesTo(getEndpoint() + "Employees('5')", "/d:uri", getBody(response));

    response = callUri("Teams('3')/nt_Employees('6')/$links/ne_Room");
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    assertXpathEvaluatesTo(getEndpoint() + "Rooms('2')", "/d:uri", getBody(response));

    response = callUri("Employees('6')/ne_Manager/$links/nm_Employees('3')");
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    assertXpathEvaluatesTo(getEndpoint() + "Employees('3')", "/d:uri", getBody(response));

    badRequest("Employees('6')/$links/");
    badRequest("Employees('6')/ne_Manager/$links");
  }

  /**
   * Links.
   *
   * @throws Exception the exception
   */
  @Test
  public void links() throws Exception {
    HttpResponse response = callUri("Managers('3')/$links/nm_Employees()");
    checkMediaType(response, HttpContentType.APPLICATION_XML_UTF8);
    final String body = getBody(response);
    assertXpathEvaluatesTo("2", "count(/d:links/d:uri)", body);
    assertXpathExists("/d:links[d:uri = \"" + getEndpoint() + "Employees('4')" + "\"]", body);
    assertXpathExists("/d:links[d:uri = \"" + getEndpoint() + "Employees('5')" + "\"]", body);
    assertFalse(body.contains("'6'"));
  }
}
