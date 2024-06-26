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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.apache.http.HttpResponse;
import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * Tests employing the reference scenario changing entities in XML format.
 * 
 */
public class EntryXmlChangeTest extends AbstractRefXmlTest {

  /**
   * Instantiates a new entry xml change test.
   *
   * @param servletType the servlet type
   */
  public EntryXmlChangeTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * Creates the.
   *
   * @throws Exception the exception
   */
  @Test
  public void create() throws Exception {
    // Create an entry for a type that has no media resource.
    String requestBody = getBody(callUri("Teams('1')"))
        .replace("'1'", "'9'")
        .replace("Id>1", "Id>9")
        .replace("Team 1", "Team X")
        .replaceAll("<link.+?/>", "");
    HttpResponse response =
        postUri("Teams()", requestBody, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Teams('4')", response.getFirstHeader(HttpHeaders.LOCATION).getValue());
    assertNull(response.getFirstHeader(HttpHeaders.ETAG));
    assertXpathEvaluatesTo("Team X", "/atom:entry/atom:content/m:properties/d:Name", getBody(response));

    // Create an entry for a type that has no media resource.
    // Add navigation to Employee('4') and Employee('5').
    requestBody = "<entry xmlns=\"" + Edm.NAMESPACE_ATOM_2005 + "\"" + "\n"
        + "       xmlns:d=\"" + Edm.NAMESPACE_D_2007_08 + "\"" + "\n"
        + "       xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">" + "\n"
        + "  <author><name>no author</name></author>" + "\n"
        + "  <content type=\"application/xml\">" + "\n"
        + "    <m:properties>" + "\n"
        + "      <d:Id>109</d:Id>" + "\n"
        + "      <d:Name/>" + "\n"
        + "      <d:Seats>4</d:Seats>" + "\n"
        + "      <d:Version>2</d:Version>" + "\n"
        + "    </m:properties>" + "\n"
        + "  </content>" + "\n"
        + "  <id>Rooms('104')</id>" + "\n"
        + "  <title>Room 104</title>" + "\n"
        + "  <updated>2011-08-10T12:00:23Z</updated>" + "\n"
        + "  <link href=\"Employees('4')\"" + "\n"
        + "        rel=\"" + Edm.NAMESPACE_REL_2007_08 + "nr_Employees\"" + "\n"
        + "        type=\"" + HttpContentType.APPLICATION_ATOM_XML_FEED_UTF8 + "\"/>" + "\n"
        + "  <link href=\"Employees('5')\"" + "\n"
        + "        rel=\"" + Edm.NAMESPACE_REL_2007_08 + "nr_Employees\"" + "\n"
        + "        type=\"" + HttpContentType.APPLICATION_ATOM_XML_FEED_UTF8 + "\"/>" + "\n"
        + "</entry>";
    response = postUri("Rooms", requestBody, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Rooms('104')", response.getFirstHeader(HttpHeaders.LOCATION).getValue());
    checkEtag(response, "W/\"2\"");
    assertXpathEvaluatesTo("4", "/atom:entry/atom:content/m:properties/d:Seats", getBody(response));
    checkUri("Rooms('104')/nr_Employees('4')");
    checkUri("Rooms('104')/nr_Employees('5')");
  }

  /**
   * Creates the with accept header entry.
   *
   * @throws Exception the exception
   */
  @Test
  public void createWithAcceptHeaderEntry() throws Exception {
    // Create an entry for a type that has no media resource.
    String requestBody = getBody(callUri("Teams('1')"))
        .replace("'1'", "'9'")
        .replace("Id>1", "Id>9")
        .replace("Team 1", "Team X")
        .replaceAll("<link.+?/>", "");
    String requestContentType = HttpContentType.APPLICATION_ATOM_XML_ENTRY;
    HttpStatusCodes expectedStatusCode = HttpStatusCodes.CREATED;
    String headerName = "Accept";
    String headerValue = "application/atom+xml;type=entry";
    HttpResponse response =
        callUri(ODataHttpMethod.POST, "Teams()", headerName, headerValue, requestBody, requestContentType,
            expectedStatusCode);

    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Teams('4')", response.getFirstHeader(HttpHeaders.LOCATION).getValue());
    assertNull(response.getFirstHeader(HttpHeaders.ETAG));
    assertXpathEvaluatesTo("Team X", "/atom:entry/atom:content/m:properties/d:Name", getBody(response));

    // Create an entry for a type that has no media resource.
    // Add navigation to Employee('4') and Employee('5').
    requestBody = "<entry xmlns=\"" + Edm.NAMESPACE_ATOM_2005 + "\"" + "\n"
        + "       xmlns:d=\"" + Edm.NAMESPACE_D_2007_08 + "\"" + "\n"
        + "       xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">" + "\n"
        + "  <author><name>no author</name></author>" + "\n"
        + "  <content type=\"application/xml\">" + "\n"
        + "    <m:properties>" + "\n"
        + "      <d:Id>109</d:Id>" + "\n"
        + "      <d:Name/>" + "\n"
        + "      <d:Seats>4</d:Seats>" + "\n"
        + "      <d:Version>2</d:Version>" + "\n"
        + "    </m:properties>" + "\n"
        + "  </content>" + "\n"
        + "  <id>Rooms('104')</id>" + "\n"
        + "  <title>Room 104</title>" + "\n"
        + "  <updated>2011-08-10T12:00:23Z</updated>" + "\n"
        + "  <link href=\"Employees('4')\"" + "\n"
        + "        rel=\"" + Edm.NAMESPACE_REL_2007_08 + "nr_Employees\"" + "\n"
        + "        type=\"" + HttpContentType.APPLICATION_ATOM_XML_FEED_UTF8 + "\"/>" + "\n"
        + "  <link href=\"Employees('5')\"" + "\n"
        + "        rel=\"" + Edm.NAMESPACE_REL_2007_08 + "nr_Employees\"" + "\n"
        + "        type=\"" + HttpContentType.APPLICATION_ATOM_XML_FEED_UTF8 + "\"/>" + "\n"
        + "</entry>";
    response = postUri("Rooms", requestBody, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Rooms('104')", response.getFirstHeader(HttpHeaders.LOCATION).getValue());
    checkEtag(response, "W/\"2\"");
    assertXpathEvaluatesTo("4", "/atom:entry/atom:content/m:properties/d:Seats", getBody(response));
    checkUri("Rooms('104')/nr_Employees('4')");
    checkUri("Rooms('104')/nr_Employees('5')");
  }

  /**
   * Creates the with large property.
   *
   * @throws Exception the exception
   */
  @Test
  public void createWithLargeProperty() throws Exception {
    final String largeTeamName = StringHelper.generateData(888888);
    // Create an entry for a type that has no media resource.
    final String requestBody = getBody(callUri("Teams('1')"))
        .replace("'1'", "'9'")
        .replace("Id>1", "Id>9")
        .replace("Team 1", largeTeamName)
        .replaceAll("<link.+?/>", "");

    HttpResponse response =
        postUri("Teams()", requestBody, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Teams('4')", response.getFirstHeader(HttpHeaders.LOCATION).getValue());
    assertNull(response.getFirstHeader(HttpHeaders.ETAG));
    assertXpathEvaluatesTo(largeTeamName, "/atom:entry/atom:content/m:properties/d:Name", getBody(response));
  }

  /**
   * Creates the minimal.
   *
   * @throws Exception the exception
   */
  @Test
  public void createMinimal() throws Exception {
    final String requestBody = "<entry xmlns=\"" + Edm.NAMESPACE_ATOM_2005 + "\"" + "\n"
        + "       xmlns:d=\"" + Edm.NAMESPACE_D_2007_08 + "\"" + "\n"
        + "       xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">" + "\n"
        + "  <content><m:properties><d:Id>99</d:Id></m:properties></content>" + "\n"
        + "</entry>";

    final HttpResponse response =
        postUri("Teams()", requestBody, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Teams('4')", response.getFirstHeader(HttpHeaders.LOCATION).getValue());
  }

  /**
   * Creates the invalid xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void createInvalidXml() throws Exception {
    getBody(callUri("Employees('7')", HttpStatusCodes.NOT_FOUND));

    final String updateBody = "<invalidXml></invalid>";
    final HttpResponse postResult =
        postUri("Employees", updateBody, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.CREATED);
    checkMediaType(postResult, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertXpathEvaluatesTo("7", "/atom:entry/m:properties/d:EmployeeId", getBody(postResult));

    final String requestBodyAfter = getBody(callUri("Employees('7')"));
    assertXpathEvaluatesTo("7", "/atom:entry/m:properties/d:EmployeeId", requestBodyAfter);
  }

  /**
   * Creates the media resource.
   *
   * @throws Exception the exception
   */
  @Test
  public void createMediaResource() throws Exception {
    HttpResponse response = postUri("Employees()", "plain text", HttpContentType.TEXT_PLAIN, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Employees('7')", response.getFirstHeader(HttpHeaders.LOCATION).getValue());
    assertNull(response.getFirstHeader(HttpHeaders.ETAG));
    assertXpathEvaluatesTo("7", "/atom:entry/m:properties/d:EmployeeId", getBody(response));
    response = callUri("Employees('7')/$value");
    checkMediaType(response, HttpContentType.TEXT_PLAIN);
    assertEquals("plain text", getBody(response));

    response = postUri("Container2.Photos", "dummy", HttpContentType.TEXT_PLAIN, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Container2.Photos(Id=5,Type='application%2Foctet-stream')", response.getFirstHeader(
        HttpHeaders.LOCATION).getValue());
    checkEtag(response, "W/\"5\"");
    assertXpathEvaluatesTo("Photo 5", "/atom:entry/m:properties/d:Name", getBody(response));
    response = callUri("Container2.Photos(Id=5,Type='application%2Foctet-stream')/$value");
    checkMediaType(response, HttpContentType.TEXT_PLAIN);
    assertEquals("dummy", getBody(response));
  }

  /**
   * Creates the media resource with navigation.
   *
   * @throws Exception the exception
   */
  @Test
  public void createMediaResourceWithNavigation() throws Exception {
    HttpResponse response =
        postUri("Teams('1')/nt_Employees", "X", HttpContentType.TEXT_PLAIN, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Employees('7')", response.getFirstHeader(HttpHeaders.LOCATION).getValue());
    assertXpathEvaluatesTo("7", "/atom:entry/m:properties/d:EmployeeId", getBody(response));

    response = callUri("Employees('7')/$value");
    checkMediaType(response, HttpContentType.TEXT_PLAIN);
    assertEquals("X", getBody(response));
  }

  /**
   * Creates the entry with inline feed.
   *
   * @throws Exception the exception
   */
  @Test
  public void createEntryWithInlineFeed() throws Exception {
    final String buildingWithRooms = "<atom:entry xml:base=\"" + getEndpoint() + "\""
        + " xmlns:atom=\"" + Edm.NAMESPACE_ATOM_2005 + "\""
        + " xmlns:d=\"" + Edm.NAMESPACE_D_2007_08 + "\""
        + " xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
        + "<atom:content type=\"application/xml\">"
        + "  <m:properties><d:Id>1</d:Id><d:Name>Building 1</d:Name></m:properties>"
        + "</atom:content>"
        + "<atom:id>Buildings('1')</atom:id>"
        + "<atom:link href=\"Buildings('1')/nb_Rooms\" rel=\"" + Edm.NAMESPACE_REL_2007_08 + "nb_Rooms\""
        + " type=\"application/atom+xml;type=feed\" title=\"included Rooms\">"
        + "  <m:inline>"
        + "    <atom:feed>"
        + "      <atom:author><atom:name/></atom:author>"
        + "      <atom:id>Rooms</atom:id>"
        + "      <atom:entry>"
        + "        <atom:content type=\"application/xml\">"
        + "          <m:properties>"
        + "            <d:Id>1</d:Id><d:Name>Room 1</d:Name><d:Seats>1</d:Seats><d:Version>1</d:Version>"
        + "          </m:properties>"
        + "        </atom:content>"
        + "        <atom:id>Rooms('1')</atom:id>"
        + "      </atom:entry>"
        + "      <atom:entry>"
        + "        <atom:content type=\"application/xml\">"
        + "          <m:properties>"
        + "            <d:Id>2</d:Id><d:Name>Room 2</d:Name><d:Seats>5</d:Seats><d:Version>2</d:Version>"
        + "          </m:properties>"
        + "        </atom:content>"
        + "        <atom:id>Rooms('2')</atom:id>"
        + "      </atom:entry>"
        + "    </atom:feed>"
        + "  </m:inline>"
        + "</atom:link>"
        + "<atom:title type=\"text\">Buildings('1')</atom:title>"
        + "<atom:updated>2012-02-29T11:59:59Z</atom:updated>"
        + "</atom:entry>";

    HttpResponse response =
        postUri("Buildings", buildingWithRooms, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.CREATED);
    checkMediaType(response, HttpContentType.APPLICATION_ATOM_XML_UTF8 + ";type=entry");
    assertEquals(getEndpoint() + "Buildings('4')", response.getFirstHeader(HttpHeaders.LOCATION).getValue());
    final String body = getBody(response);
    assertXpathEvaluatesTo("4", "/atom:entry/atom:content/m:properties/d:Id", body);
    assertXpathEvaluatesTo("105", "/atom:entry/atom:link[@rel='" + Edm.NAMESPACE_REL_2007_08
        + "nb_Rooms']/m:inline/atom:feed/atom:entry[2]/atom:content/m:properties/d:Id", body);
    checkUri("Buildings('4')");
    checkUri("Rooms('104')");
    checkUri("Buildings('4')/nb_Rooms('104')");
    checkUri("Rooms('104')/nr_Building");
    checkUri("Rooms('105')");
    checkUri("Buildings('4')/nb_Rooms('105')");
    checkUri("Rooms('105')/nr_Building");
    assertEquals("5", getBody(callUri("Buildings('4')/nb_Rooms('105')/Seats/$value")));
  }

  /**
   * Update.
   *
   * @throws Exception the exception
   */
  @Test
  public void update() throws Exception {
    final String requestBody = getBody(callUri("Employees('2')"))
        .replace("'2'", "'9'")
        .replace("EmployeeId>2", "EmployeeId>9")
        .replace(EMPLOYEE_2_NAME, "Mister X")
        .replace("<d:Age>" + EMPLOYEE_2_AGE + "</d:Age>", "")
        .replace(">2003-07-01T00:00:00", " m:null='true'>")
        .replaceAll("<link.+?/>", "");
    final HttpResponse response =
        callUri(ODataHttpMethod.PUT, "Employees('1')", null, null, requestBody,
            HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.NO_CONTENT);
    assertFalse(response.containsHeader(HttpHeaders.LOCATION));
    final String body = getBody(callUri("Employees('1')"));
    assertXpathEvaluatesTo("Mister X", "/atom:entry/m:properties/d:EmployeeName", body);
    assertXpathEvaluatesTo("0", "/atom:entry/m:properties/d:Age", body);
    assertXpathEvaluatesTo("true", "/atom:entry/m:properties/d:EntryDate/@m:null", body);
  }

  /**
   * Update incomplete.
   *
   * @throws Exception the exception
   */
  @Test
  public void updateIncomplete() throws Exception {
    final String requestBody = "<entry xmlns=\"" + Edm.NAMESPACE_ATOM_2005 + "\"" + "\n"
        + "       xmlns:d=\"" + Edm.NAMESPACE_D_2007_08 + "\"" + "\n"
        + "       xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">" + "\n"
        + "  <m:properties><d:EmployeeName>Mister X</d:EmployeeName></m:properties>" + "\n"
        + "</entry>";
    putUri("Employees('1')", requestBody, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.NO_CONTENT);
    final String body = getBody(callUri("Employees('1')"));
    assertXpathEvaluatesTo("Mister X", "/atom:entry/m:properties/d:EmployeeName", body);
    assertXpathEvaluatesTo("0", "/atom:entry/m:properties/d:Age", body);
    assertXpathEvaluatesTo("true", "/atom:entry/m:properties/d:EntryDate/@m:null", body);

    final String requestBody2 = requestBody.replace("<d:EmployeeName>Mister X</d:EmployeeName>",
        "<d:Location><d:Country>Allemagne</d:Country></d:Location>");
    putUri("Employees('1')", requestBody2, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.NO_CONTENT);
    final String body2 = getBody(callUri("Employees('1')"));
    assertXpathEvaluatesTo("Allemagne", "/atom:entry/m:properties/d:Location/d:Country", body2);
    assertXpathEvaluatesTo("true", "/atom:entry/m:properties/d:Location/d:City/d:CityName/@m:null", body2);
    assertXpathEvaluatesTo("true", "/atom:entry/m:properties/d:EmployeeName/@m:null", body2);
  }

  /**
   * Update unknown property.
   *
   * @throws Exception the exception
   */
  @Test
  public void updateUnknownProperty() throws Exception {
    final String requestBody = getBody(callUri("Employees('2')"))
        .replace("<d:Age>" + EMPLOYEE_2_AGE + "</d:Age>",
            "<d:Age>33</d:Age><d:SomeUnknownTag>SomeUnknownValue</d:SomeUnknownTag>");

    putUri("Employees('2')", requestBody, HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.BAD_REQUEST);
    // check nothing has changed
    assertXpathEvaluatesTo(EMPLOYEE_2_AGE, "/atom:entry/m:properties/d:Age", getBody(callUri("Employees('2')")));
  }

  /**
   * Update invalid xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void updateInvalidXml() throws Exception {
    final String requestBodyBefore = getBody(callUri("Employees('2')"));

    putUri("Employees('2')", "<invalidXml></invalid>", HttpContentType.APPLICATION_ATOM_XML_ENTRY,
        HttpStatusCodes.BAD_REQUEST);

    assertEquals(requestBodyBefore, getBody(callUri("Employees('2')")));
  }

  /**
   * Patch and merge.
   *
   * @throws Exception the exception
   */
  @Test
  public void patchAndMerge() throws Exception {
    String requestBody = "<entry xmlns=\"" + Edm.NAMESPACE_ATOM_2005 + "\"" + "\n"
        + "       xmlns:d=\"" + Edm.NAMESPACE_D_2007_08 + "\"" + "\n"
        + "       xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">" + "\n"
        + "  <content/>" + "\n"
        + "  <m:properties>" + "\n"
        + "    <d:Location>" + "\n"
        + "      <d:City>" + "\n"
        + "        <d:PostalCode>69124</d:PostalCode>" + "\n"
        + "        <d:CityName>" + CITY_1_NAME + "</d:CityName>" + "\n"
        + "      </d:City>" + "\n"
        + "      <d:Country>Germany</d:Country>" + "\n"
        + "    </d:Location>" + "\n"
        + "    <d:EntryDate m:null=\"true\"/>" + "\n"
        + "  </m:properties>" + "\n"
        + "</entry>";
    callUri(ODataHttpMethod.PATCH, "Employees('2')", null, null, requestBody,
        HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.NO_CONTENT);
    final String body = getBody(callUri("Employees('2')"));
    assertXpathEvaluatesTo(CITY_1_NAME, "/atom:entry/m:properties/d:Location/d:City/d:CityName", body);
    assertXpathEvaluatesTo(EMPLOYEE_2_AGE, "/atom:entry/m:properties/d:Age", body);

    requestBody = "<entry xmlns=\"" + Edm.NAMESPACE_ATOM_2005 + "\">" + "\n"
        + "  <content xmlns:d=\"" + Edm.NAMESPACE_D_2007_08 + "\"" + "\n"
        + "           xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\"" + "\n"
        + "           type=\"" + HttpContentType.APPLICATION_XML_UTF8 + "\">" + "\n"
        + "    <m:properties><d:Name>Room X</d:Name></m:properties>" + "\n"
        + "  </content>" + "\n"
        + "</entry>";
    HttpResponse response =
        callUri(ODataHttpMethod.MERGE, "Rooms('3')", HttpHeaders.IF_MATCH, "W/\"3\"", requestBody,
            HttpContentType.APPLICATION_ATOM_XML_ENTRY, HttpStatusCodes.NO_CONTENT);
    checkEtag(response, "W/\"3\"");
    assertXpathEvaluatesTo("Room X", "/atom:entry/atom:content/m:properties/d:Name", getBody(callUri("Rooms('3')")));
  }

  /**
   * Delete.
   *
   * @throws Exception the exception
   */
  @Test
  public void delete() throws Exception {
    final String uri = "Employees('2')";
    deleteUriOk(uri);

    final String requestBody = getBody(callUri(uri, HttpStatusCodes.NOT_FOUND));
    assertXpathExists("/m:error", requestBody);
    assertXpathEvaluatesTo("Requested entity could not be found.", "/m:error/m:message", requestBody);
  }
}
