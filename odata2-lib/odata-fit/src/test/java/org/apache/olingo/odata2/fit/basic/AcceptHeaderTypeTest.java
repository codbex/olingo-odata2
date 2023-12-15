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
package org.apache.olingo.odata2.fit.basic;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMetadataUriInfo;
import org.apache.olingo.odata2.testutil.mock.EdmTestProvider;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class AcceptHeaderTypeTest.
 */
public class AcceptHeaderTypeTest extends AbstractBasicTest {

  /**
   * Instantiates a new accept header type test.
   *
   * @param servletType the servlet type
   */
  public AcceptHeaderTypeTest(final ServletType servletType) {
    super(servletType);
  }

  /** The processor. */
  private ODataSingleProcessor processor = mock(ODataSingleProcessor.class);

  /**
   * Creates the processor.
   *
   * @return the o data single processor
   * @throws ODataException the o data exception
   */
  @Override
  protected ODataSingleProcessor createProcessor() throws ODataException {
    String contentType = "application/atom+xml";
    ODataResponse response =
        ODataResponse.status(HttpStatusCodes.OK).contentHeader(contentType).entity("Test passed.").build();
    when(processor.readEntity(any(GetEntityUriInfo.class), any(String.class))).thenReturn(response);
    when(processor.readEntitySet(any(GetEntitySetUriInfo.class), any(String.class))).thenReturn(response);

    return processor;
  }

  /**
   * Creates the edm provider.
   *
   * @return the edm provider
   */
  @Override
  protected EdmProvider createEdmProvider() {
    return new EdmTestProvider();
  }

  /**
   * Test get request.
   *
   * @param uriExtension the uri extension
   * @param acceptHeader the accept header
   * @param expectedStatus the expected status
   * @param expectedContentType the expected content type
   * @return the http response
   * @throws ClientProtocolException the client protocol exception
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ODataException the o data exception
   */
  private HttpResponse testGetRequest(final String uriExtension, final String acceptHeader,
      final HttpStatusCodes expectedStatus, final String expectedContentType)
      throws ClientProtocolException, IOException, ODataException {
    // prepare
    ODataResponse expectedResponse = ODataResponse.contentHeader(expectedContentType).entity("Test passed.").build();
    when(processor.readMetadata(any(GetMetadataUriInfo.class), any(String.class))).thenReturn(expectedResponse);
    when(processor.readEntity(any(GetEntityUriInfo.class), any(String.class))).thenReturn(expectedResponse);
    when(processor.readEntitySet(any(GetEntitySetUriInfo.class), any(String.class))).thenReturn(expectedResponse);

    HttpGet getRequest = new HttpGet(URI.create(getEndpoint().toString() + uriExtension));
    getRequest.setHeader(HttpHeaders.ACCEPT, acceptHeader);

    // execute
    HttpResponse response = getHttpClient().execute(getRequest);

    // validate
    assertEquals(expectedStatus.getStatusCode(), response.getStatusLine().getStatusCode());
    Header[] contentTypeHeaders = response.getHeaders("Content-Type");
    assertEquals("Found more then one content type header in response.", 1, contentTypeHeaders.length);
    assertEquals("Received content type does not match expected.", expectedContentType, contentTypeHeaders[0]
        .getValue());
    assertEquals("Received status code does not match expected.", expectedStatus.getStatusCode(), response
        .getStatusLine().getStatusCode());
    //
    return response;
  }

  /**
   * Content type application atom xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeApplicationAtomXml() throws Exception {
    HttpGet getRequest = new HttpGet(URI.create(getEndpoint().toString() + "Employees('1')"));
    getRequest.setHeader(HttpHeaders.ACCEPT, "application/atom+xml");
    final HttpResponse response = getHttpClient().execute(getRequest);
    assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine().getStatusCode());
  }

  /**
   * Content type application xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeApplicationXml() throws Exception {
    testGetRequest("Employees('1')", "application/xml", HttpStatusCodes.OK, "application/atom+xml");
  }

  /**
   * Content type application json.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeApplicationJson() throws Exception {
    testGetRequest("Rooms('1')", "application/json", HttpStatusCodes.OK, "application/json");
  }

  /**
   * Content type application xml on set.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeApplicationXmlOnSet() throws Exception {
    testGetRequest("Employees", "application/xml", HttpStatusCodes.OK, "application/atom+xml");
  }

  /**
   * Content type application jsonon set.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeApplicationJsononSet() throws Exception {
    testGetRequest("Rooms", "application/json", HttpStatusCodes.OK, "application/json");
  }

  /**
   * Content type application xml on metadata.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeApplicationXmlOnMetadata() throws Exception {
    testGetRequest("$metadata", "application/xml", HttpStatusCodes.OK, "application/xml");
  }

  /**
   * Content type application xml on metadata multi accept.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentTypeApplicationXmlOnMetadataMultiAccept() throws Exception {
    testGetRequest("$metadata", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", HttpStatusCodes.OK,
        "application/xml");
  }

  /**
   * Illegal Q parameter in content type.
   *
   * @throws Exception the exception
   */
  @Test
  public void illegalQParameterInContentType() throws Exception {
    testGetRequest("Rooms('1')", "application/json;q=2", HttpStatusCodes.BAD_REQUEST, "application/xml");
  }

  /**
   * Illegal space in content type.
   *
   * @throws Exception the exception
   */
  @Test
  public void illegalSpaceInContentType() throws Exception {
    testGetRequest("Rooms('1')", "application    /json", HttpStatusCodes.BAD_REQUEST, "application/xml");
  }

  /**
   * Illegal space in content sub type.
   *
   * @throws Exception the exception
   */
  @Test
  public void illegalSpaceInContentSubType() throws Exception {
    testGetRequest("Employees('1')", "application/    xml", HttpStatusCodes.BAD_REQUEST, "application/xml");
  }

  /**
   * Illegal spaces in accept header.
   *
   * @throws Exception the exception
   */
  @Test
  public void illegalSpacesInAcceptHeader() throws Exception {
    testGetRequest("Employees('1')", "    applic  ation/    xml   ", HttpStatusCodes.BAD_REQUEST, "application/xml");
  }

  /**
   * Illegal spaces in accept header on set.
   *
   * @throws Exception the exception
   */
  @Test
  public void illegalSpacesInAcceptHeaderOnSet() throws Exception {
    testGetRequest("Employees", "    applic  ation/    xml   ", HttpStatusCodes.BAD_REQUEST, "application/xml");
  }

  /**
   * Illegal spaces in accept header parameter on set.
   *
   * @throws Exception the exception
   */
  @Test
  public void illegalSpacesInAcceptHeaderParameterOnSet() throws Exception {
    testGetRequest("Employees", "application/xml; param= alskdf", HttpStatusCodes.BAD_REQUEST, "application/xml");
  }

  /**
   * Illegal lws in accept header parameter.
   *
   * @throws Exception the exception
   */
  @Test
  public void illegalLwsInAcceptHeaderParameter() throws Exception {
    testGetRequest("Employees('1')", "application/xml; param=\talskdf;", HttpStatusCodes.BAD_REQUEST,
        "application/xml");
  }

  /**
   * Illegal spaces between accept header parameter on set.
   *
   * @throws Exception the exception
   */
  @Test
  public void illegalSpacesBetweenAcceptHeaderParameterOnSet() throws Exception {
    testGetRequest("Employees", "application/xml; another=  test ; param=alskdf", HttpStatusCodes.BAD_REQUEST,
        "application/xml");
  }

  /**
   * Legal spaces between accept header parameter on set.
   *
   * @throws Exception the exception
   */
  @Test
  public void legalSpacesBetweenAcceptHeaderParameterOnSet() throws Exception {
    testGetRequest("Employees", "application/xml; another=test   ;   param=alskdf", HttpStatusCodes.NOT_ACCEPTABLE,
        "application/xml");
  }
}
