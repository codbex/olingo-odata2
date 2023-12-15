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
package org.apache.olingo.odata2.fit.mapping;

import static junit.framework.Assert.assertEquals;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.testutil.fit.AbstractFitTest;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.apache.olingo.odata2.testutil.server.TestServer;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class MappingTest.
 */
public class MappingTest extends AbstractFitTest {

  /**
   * Instantiates a new mapping test.
   *
   * @param servletType the servlet type
   */
  public MappingTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(final String[] args) {
    final TestServer server = new TestServer(ServletType.JAXRS_SERVLET);
    try {
      server.startServer(MapFactory.class);
      System.out.println("Press any key to exit");
      new BufferedReader(new InputStreamReader(System.in)).readLine();
    } catch (final IOException e) {
      e.printStackTrace(System.err);
    } finally {
      server.stopServer();
    }
  }

  /**
   * Before.
   */
  @Override
  @Before
  public void before() {
    super.before();
    Map<String, String> prefixMap = new HashMap<String, String>();
    prefixMap.put("a", Edm.NAMESPACE_ATOM_2005);
    prefixMap.put("d", Edm.NAMESPACE_D_2007_08);
    prefixMap.put("m", Edm.NAMESPACE_M_2007_08);
    XMLUnit.setXpathNamespaceContext(new SimpleNamespaceContext(prefixMap));
  }

  /**
   * Creates the service.
   *
   * @return the o data service
   * @throws ODataException the o data exception
   */
  @Override
  protected ODataService createService() throws ODataException {
    return MapFactory.create();
  }

  /**
   * Test service document.
   *
   * @throws Exception the exception
   */
  @Test
  public void testServiceDocument() throws Exception {
    final HttpGet get = new HttpGet(URI.create(getEndpoint().toString() + "/"));
    final HttpResponse response = getHttpClient().execute(get);
    assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine().getStatusCode());
  }

  /**
   * Test metadata.
   *
   * @throws Exception the exception
   */
  @Test
  public void testMetadata() throws Exception {
    final HttpGet get = new HttpGet(URI.create(getEndpoint().toString() + "/$metadata"));
    final HttpResponse response = getHttpClient().execute(get);
    assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine().getStatusCode());
  }

  /**
   * Test entity set.
   *
   * @throws Exception the exception
   */
  @Test
  public void testEntitySet() throws Exception {
    final HttpGet get = new HttpGet(URI.create(getEndpoint().toString() + "/mappings"));
    final HttpResponse response = getHttpClient().execute(get);
    assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine().getStatusCode());

    final String payload = StringHelper.inputStreamToString(response.getEntity().getContent());
    for (int i = 1; i <= MapProcessor.RECORD_COUNT; i++) {
      assertXpathEvaluatesTo("V01." + i, "/a:feed/a:entry[" + i + "]/a:content/m:properties/d:p1", payload);
      assertXpathEvaluatesTo("V02." + i, "/a:feed/a:entry[" + i + "]/a:content/m:properties/d:p2", payload);
      assertXpathEvaluatesTo("V03." + i, "/a:feed/a:entry[" + i + "]/a:content/m:properties/d:p3", payload);
    }
  }

  /**
   * Test entity.
   *
   * @throws Exception the exception
   */
  @Test
  public void testEntity() throws Exception {
    final HttpGet get = new HttpGet(URI.create(getEndpoint().toString() + "/mappings('V01.7')"));
    final HttpResponse response = getHttpClient().execute(get);
    assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine().getStatusCode());

    final String payload = StringHelper.inputStreamToString(response.getEntity().getContent());
    assertXpathEvaluatesTo("V01.7", "/a:entry/a:content/m:properties/d:p1", payload);
    assertXpathEvaluatesTo("V02.7", "/a:entry/a:content/m:properties/d:p2", payload);
    assertXpathEvaluatesTo("V03.7", "/a:entry/a:content/m:properties/d:p3", payload);
  }

  /**
   * Test property value.
   *
   * @throws Exception the exception
   */
  @Test
  public void testPropertyValue() throws Exception {
    final HttpGet get = new HttpGet(URI.create(getEndpoint().toString() + "/mappings('V01.7')/p2/$value"));
    final HttpResponse response = getHttpClient().execute(get);
    assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine().getStatusCode());

    final String payload = StringHelper.inputStreamToString(response.getEntity().getContent());
    assertEquals("V02.7", payload);
  }
}
