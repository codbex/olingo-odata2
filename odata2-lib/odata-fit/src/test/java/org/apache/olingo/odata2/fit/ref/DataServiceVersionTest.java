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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.olingo.odata2.api.ODataServiceVersion;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.ODataHttpHeaders;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class DataServiceVersionTest.
 */
public class DataServiceVersionTest extends AbstractRefTest {

  /**
   * Instantiates a new data service version test.
   *
   * @param servletType the servlet type
   */
  public DataServiceVersionTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * Check version.
   *
   * @param response the response
   * @param expectedValue the expected value
   * @throws AssertionError the assertion error
   */
  private static void checkVersion(final HttpResponse response, final String expectedValue) throws AssertionError {
    final Header header = response.getFirstHeader(ODataHttpHeaders.DATASERVICEVERSION);
    assertNotNull(header);
    assertEquals(expectedValue, header.getValue());
  }

  /**
   * Test data service version case.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDataServiceVersionCase() throws Exception {
    HttpResponse response = callUri("Employees");
    Header header = response.getFirstHeader("dataserviceversion");
    assertNotNull(header);
    assertEquals(ODataServiceVersion.V20, header.getValue());

    checkVersion(response, ODataServiceVersion.V20);
  }

  /**
   * Test data service version with semicolon.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDataServiceVersionWithSemicolon() throws Exception {
    HttpResponse response = callUri("Employees", ODataHttpHeaders.DATASERVICEVERSION, "2.0;hallo", HttpStatusCodes.OK);
    checkVersion(response, ODataServiceVersion.V20);
  }

  /**
   * Test data service version not set on entity set.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDataServiceVersionNotSetOnEntitySet() throws Exception {
    checkVersion(callUri("Employees"), ODataServiceVersion.V20);
  }

  /**
   * Test data service version set on entity set.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDataServiceVersionSetOnEntitySet() throws Exception {
    HttpResponse response = callUri("Employees", ODataHttpHeaders.DATASERVICEVERSION, "2.0");
    checkVersion(response, ODataServiceVersion.V20);
    getBody(response);

    response = callUri("Employees", ODataHttpHeaders.DATASERVICEVERSION, "1.0");
    checkVersion(response, ODataServiceVersion.V20);
  }

  /**
   * Test data service version set on entity set fail.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDataServiceVersionSetOnEntitySetFail() throws Exception {
    HttpResponse response =
        callUri("Employees", ODataHttpHeaders.DATASERVICEVERSION, "3.0", HttpStatusCodes.BAD_REQUEST);
    checkVersion(response, ODataServiceVersion.V10);
    getBody(response);
    response = callUri("$metadata", ODataHttpHeaders.DATASERVICEVERSION, "3.0", HttpStatusCodes.BAD_REQUEST);
    checkVersion(response, ODataServiceVersion.V10);
    getBody(response);
    response = callUri("Employees", ODataHttpHeaders.DATASERVICEVERSION, "4.0", HttpStatusCodes.BAD_REQUEST);
    checkVersion(response, ODataServiceVersion.V10);
    getBody(response);
    response = callUri("$metadata", ODataHttpHeaders.DATASERVICEVERSION, "somethingwrong", HttpStatusCodes.BAD_REQUEST);
    checkVersion(response, ODataServiceVersion.V10);
    getBody(response);
    response = callUri("$metadata", ODataHttpHeaders.DATASERVICEVERSION, "3.2", HttpStatusCodes.BAD_REQUEST);
    checkVersion(response, ODataServiceVersion.V10);
  }

  /**
   * Test data service version not set on metadata.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDataServiceVersionNotSetOnMetadata() throws Exception {
    checkVersion(callUri("$metadata"), ODataServiceVersion.V20);
  }

  /**
   * Test data service version set on metadata.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDataServiceVersionSetOnMetadata() throws Exception {
    HttpResponse response = callUri("$metadata", ODataHttpHeaders.DATASERVICEVERSION, "1.0");
    checkVersion(response, ODataServiceVersion.V20);
    getBody(response);

    response = callUri("$metadata", ODataHttpHeaders.DATASERVICEVERSION, "2.0");
    checkVersion(response, ODataServiceVersion.V20);
    getBody(response);
  }

  /**
   * Test data service version not set on service document.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDataServiceVersionNotSetOnServiceDocument() throws Exception {
    checkVersion(callUri(""), ODataServiceVersion.V10);
  }

  /**
   * Test data service version set on service document.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDataServiceVersionSetOnServiceDocument() throws Exception {
    HttpResponse response = callUri("", ODataHttpHeaders.DATASERVICEVERSION, "1.0");
    checkVersion(response, ODataServiceVersion.V10);
    getBody(response);

    response = callUri("", ODataHttpHeaders.DATASERVICEVERSION, "2.0");
    checkVersion(response, ODataServiceVersion.V10);
  }
}
