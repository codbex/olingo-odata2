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

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.testutil.fit.AbstractFitTest;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class NullServiceTest.
 */
public class NullServiceTest extends AbstractFitTest {

  /**
   * Instantiates a new null service test.
   *
   * @param servletType the servlet type
   */
  public NullServiceTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * Creates the service.
   *
   * @return the o data service
   * @throws ODataException the o data exception
   */
  @Override
  protected ODataService createService() throws ODataException {
    return null;
  }

  /**
   * Null service must result in O data response.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullServiceMustResultInODataResponse() throws Exception {
    disableLogging();
    final HttpResponse response = executeGetRequest("$metadata");
    assertEquals(HttpStatusCodes.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatusLine().getStatusCode());

    
    ODataErrorContext error = EntityProvider.readErrorDocument(response.getEntity().getContent(), "application/xml");
    assertEquals("Service unavailable.", error.getMessage());
  }
  
  /**
   * Execute get request.
   *
   * @param request the request
   * @return the http response
   * @throws ClientProtocolException the client protocol exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private HttpResponse executeGetRequest(final String request) throws ClientProtocolException, IOException {
    final HttpGet get = new HttpGet(URI.create(getEndpoint().toString() + request));
    return getHttpClient().execute(get);
  }

}
