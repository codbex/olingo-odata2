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
package org.apache.olingo.odata2.testutil.fit;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.testutil.TestUtilRuntimeException;
import org.apache.olingo.odata2.testutil.server.ServerRuntimeException;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.apache.olingo.odata2.testutil.server.TestServer;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractFitTest.
 */
@RunWith(Parameterized.class)
public abstract class AbstractFitTest extends BaseTest {

  /** The server. */
  private final TestServer server;

  /** The service. */
  private ODataService service;

  /** The http client. */
  private final HttpClient httpClient = new DefaultHttpClient();

  /**
   * Instantiates a new abstract fit test.
   *
   * @param servletType the servlet type
   */
  public AbstractFitTest(final ServletType servletType) {
    server = new TestServer(this.getClass().getSimpleName(), servletType);
  }

  /**
   * Data.
   *
   * @return the list
   */
  @Parameterized.Parameters
  public static List<Object[]> data() {
    // If desired this can be made dependent on runtime variables
    Object[][] a;
    a = new Object[2][1];
    a[0][0] = ServletType.JAXRS_SERVLET;
    a[1][0] = ServletType.ODATA_SERVLET;

    return Arrays.asList(a);
  }

  // public AbstractFitTest() {
  // }

  /**
   * Gets the endpoint.
   *
   * @return the endpoint
   */
  protected URI getEndpoint() {
    return server.getEndpoint();
  }

  /**
   * Gets the http client.
   *
   * @return the http client
   */
  protected HttpClient getHttpClient() {
    return httpClient;
  }

  /**
   * Gets the service.
   *
   * @return the service
   */
  protected ODataService getService() {
    return service;
  }

  /**
   * Start custom server.
   *
   * @param factoryClass the factory class
   */
  protected void startCustomServer(Class<? extends FitStaticServiceFactory> factoryClass){
    try {
      service = createService();
      server.startServer(service, factoryClass);
    } catch (final ODataException e) {
      throw new TestUtilRuntimeException(e);
    }
  }
  
  /**
   * Stop custom server.
   */
  protected void stopCustomServer(){
    try {
      server.stopServer();
    } catch (final ServerRuntimeException e) {
      throw new TestUtilRuntimeException(e);
    }
  }
  
  /**
   * Creates the service.
   *
   * @return the o data service
   * @throws ODataException the o data exception
   */
  protected abstract ODataService createService() throws ODataException;

  /**
   * Before.
   */
  @Before
  public void before() {
    try {
      service = createService();
      server.startServer(service);
    } catch (final ODataException e) {
      throw new TestUtilRuntimeException(e);
    }
  }

  /**
   * After.
   */
  @After
  public void after() {
    try {
      server.stopServer();
    } catch (final ServerRuntimeException e) {
      throw new TestUtilRuntimeException(e);
    }
  }
}
