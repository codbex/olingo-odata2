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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.core.processor.ODataSingleProcessorService;
import org.apache.olingo.odata2.testutil.fit.AbstractFitTest;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractBasicTest.
 */
public abstract class AbstractBasicTest extends AbstractFitTest {

  /**
   * Instantiates a new abstract basic test.
   *
   * @param servletType the servlet type
   */
  public AbstractBasicTest(final ServletType servletType) {
    super(servletType);
  }

  /** The context. */
  private ODataContext context;

  /**
   * Creates the service.
   *
   * @return the o data service
   * @throws ODataException the o data exception
   */
  @Override
  protected ODataService createService() throws ODataException {
    final EdmProvider provider = createEdmProvider();
    final ODataSingleProcessor processor = createProcessor();

    // science fiction (return context after setContext)
    // see http://www.planetgeek.ch/2010/07/20/mockito-answer-vs-return/

    doAnswer(new Answer<Object>() {
      @Override
      public Object answer(final InvocationOnMock invocation) throws Throwable {
        context = (ODataContext) invocation.getArguments()[0];
        return null;
      }
    }).when(processor).setContext(any(ODataContext.class));

    when(processor.getContext()).thenAnswer(new Answer<ODataContext>() {
      @Override
      public ODataContext answer(final InvocationOnMock invocation) throws Throwable {
        return context;
      }
    });

    return new ODataSingleProcessorService(provider, processor) {};
  }

  /**
   * Creates the edm provider.
   *
   * @return the edm provider
   */
  protected EdmProvider createEdmProvider() {
    return mock(EdmProvider.class);
  }

  /**
   * Creates the processor.
   *
   * @return the o data single processor
   * @throws ODataException the o data exception
   */
  protected abstract ODataSingleProcessor createProcessor() throws ODataException;

  /**
   * Execute get request.
   *
   * @param request the request
   * @return the http response
   * @throws ClientProtocolException the client protocol exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected HttpResponse executeGetRequest(final String request) throws ClientProtocolException, IOException {
    final HttpGet get = new HttpGet(URI.create(getEndpoint().toString() + request));
    return getHttpClient().execute(get);
  }
}
