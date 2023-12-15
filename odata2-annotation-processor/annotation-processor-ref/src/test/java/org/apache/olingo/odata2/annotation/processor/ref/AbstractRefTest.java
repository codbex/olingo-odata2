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
package org.apache.olingo.odata2.annotation.processor.ref;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.olingo.odata2.annotation.processor.api.AnnotationServiceFactory;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.testutil.fit.AbstractFitTest;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Ignore;

// TODO: Auto-generated Javadoc
/**
 * Abstract base class for tests employing the reference scenario.
 * 
 */
@Ignore("no test methods")
public class AbstractRefTest extends AbstractFitTest {

  /**
   * Instantiates a new abstract ref test.
   *
   * @param servletType the servlet type
   */
  public AbstractRefTest(final ServletType servletType) {
    super(servletType);
  }

  /** The Constant IMAGE_JPEG. */
  protected static final String IMAGE_JPEG = "image/jpeg";
  
  /** The Constant IMAGE_GIF. */
  protected static final String IMAGE_GIF = "image/gif";

  /** The Constant MODEL_PACKAGE. */
  final static String MODEL_PACKAGE = "org.apache.olingo.odata2.annotation.processor.ref.model";

  /**
   * Creates the service.
   *
   * @return the o data service
   * @throws ODataException the o data exception
   */
  @Override
  protected ODataService createService() throws ODataException {
    return AnnotationServiceFactory.createAnnotationService(MODEL_PACKAGE);
  }

  /**
   * Call uri.
   *
   * @param httpMethod the http method
   * @param uri the uri
   * @param additionalHeader the additional header
   * @param additionalHeaderValue the additional header value
   * @param requestBody the request body
   * @param requestContentType the request content type
   * @param expectedStatusCode the expected status code
   * @return the http response
   * @throws Exception the exception
   */
  protected HttpResponse callUri(
      final ODataHttpMethod httpMethod, final String uri,
      final String additionalHeader, final String additionalHeaderValue,
      final String requestBody, final String requestContentType,
      final HttpStatusCodes expectedStatusCode) throws Exception {

    HttpRequestBase request =
        httpMethod == ODataHttpMethod.GET ? new HttpGet() :
            httpMethod == ODataHttpMethod.DELETE ? new HttpDelete() :
                httpMethod == ODataHttpMethod.POST ? new HttpPost() :
                    httpMethod == ODataHttpMethod.PUT ? new HttpPut() : new HttpPatch();
    request.setURI(URI.create(getEndpoint() + uri));
    if (additionalHeader != null) {
      request.addHeader(additionalHeader, additionalHeaderValue);
    }
    if (requestBody != null) {
      ((HttpEntityEnclosingRequest) request).setEntity(new StringEntity(requestBody));
      request.setHeader(HttpHeaders.CONTENT_TYPE, requestContentType);
    }

    final HttpResponse response = getHttpClient().execute(request);

    assertNotNull(response);
    assertEquals(expectedStatusCode.getStatusCode(), response.getStatusLine().getStatusCode());

    if (expectedStatusCode == HttpStatusCodes.OK) {
      assertNotNull(response.getEntity());
      assertNotNull(response.getEntity().getContent());
    } else if (expectedStatusCode == HttpStatusCodes.CREATED) {
      assertNotNull(response.getEntity());
      assertNotNull(response.getEntity().getContent());
      assertNotNull(response.getFirstHeader(HttpHeaders.LOCATION));
    } else if (expectedStatusCode == HttpStatusCodes.NO_CONTENT) {
      assertTrue(response.getEntity() == null || response.getEntity().getContent() == null);
    }

    return response;
  }

  /**
   * Call uri.
   *
   * @param uri the uri
   * @param additionalHeader the additional header
   * @param additionalHeaderValue the additional header value
   * @param expectedStatusCode the expected status code
   * @return the http response
   * @throws Exception the exception
   */
  protected HttpResponse callUri(final String uri, final String additionalHeader, final String additionalHeaderValue,
      final HttpStatusCodes expectedStatusCode) throws Exception {
    return callUri(ODataHttpMethod.GET, uri, additionalHeader, additionalHeaderValue, null, null, expectedStatusCode);
  }

  /**
   * Call uri.
   *
   * @param uri the uri
   * @param additionalHeader the additional header
   * @param additionalHeaderValue the additional header value
   * @return the http response
   * @throws Exception the exception
   */
  protected HttpResponse callUri(final String uri, final String additionalHeader, final String additionalHeaderValue)
      throws Exception {
    return callUri(ODataHttpMethod.GET, uri, additionalHeader, additionalHeaderValue, null, null, HttpStatusCodes.OK);
  }

  /**
   * Call uri.
   *
   * @param uri the uri
   * @param expectedStatusCode the expected status code
   * @return the http response
   * @throws Exception the exception
   */
  protected HttpResponse callUri(final String uri, final HttpStatusCodes expectedStatusCode) throws Exception {
    return callUri(uri, null, null, expectedStatusCode);
  }

  /**
   * Call uri.
   *
   * @param uri the uri
   * @return the http response
   * @throws Exception the exception
   */
  protected HttpResponse callUri(final String uri) throws Exception {
    return callUri(uri, HttpStatusCodes.OK);
  }

  /**
   * Check uri.
   *
   * @param uri the uri
   * @throws Exception the exception
   */
  protected void checkUri(final String uri) throws Exception {
    assertNotNull(getBody(callUri(uri)));
  }

  /**
   * Bad request.
   *
   * @param uri the uri
   * @throws Exception the exception
   */
  protected void badRequest(final String uri) throws Exception {
    final HttpResponse response = callUri(uri, HttpStatusCodes.BAD_REQUEST);
    assertNotNull(getBody(response));
  }

  /**
   * Not found.
   *
   * @param uri the uri
   * @throws Exception the exception
   */
  protected void notFound(final String uri) throws Exception {
    final HttpResponse response = callUri(uri, HttpStatusCodes.NOT_FOUND);
    assertNotNull(getBody(response));
  }

  /**
   * Delete uri.
   *
   * @param uri the uri
   * @param expectedStatusCode the expected status code
   * @throws Exception the exception
   * @throws AssertionError the assertion error
   */
  protected void deleteUri(final String uri, final HttpStatusCodes expectedStatusCode)
      throws Exception, AssertionError {
    final HttpResponse response = callUri(ODataHttpMethod.DELETE, uri, null, null, null, null, expectedStatusCode);
    if (expectedStatusCode != HttpStatusCodes.NO_CONTENT) {
      response.getEntity().getContent().close();
    }
  }

  /**
   * Delete uri ok.
   *
   * @param uri the uri
   * @throws Exception the exception
   */
  protected void deleteUriOk(final String uri) throws Exception {
    deleteUri(uri, HttpStatusCodes.NO_CONTENT);
  }

  /**
   * Post uri.
   *
   * @param uri the uri
   * @param requestBody the request body
   * @param requestContentType the request content type
   * @param expectedStatusCode the expected status code
   * @return the http response
   * @throws Exception the exception
   */
  protected HttpResponse postUri(final String uri, final String requestBody, final String requestContentType,
      final HttpStatusCodes expectedStatusCode) throws Exception {
    return callUri(ODataHttpMethod.POST, uri, null, null, requestBody, requestContentType, expectedStatusCode);
  }

  /**
   * Post uri.
   *
   * @param uri the uri
   * @param requestBody the request body
   * @param requestContentType the request content type
   * @param additionalHeader the additional header
   * @param additionalHeaderValue the additional header value
   * @param expectedStatusCode the expected status code
   * @return the http response
   * @throws Exception the exception
   */
  protected HttpResponse postUri(final String uri, final String requestBody, final String requestContentType,
      final String additionalHeader, final String additionalHeaderValue, final HttpStatusCodes expectedStatusCode)
      throws Exception {
    return callUri(ODataHttpMethod.POST, uri, additionalHeader, additionalHeaderValue, requestBody, requestContentType,
        expectedStatusCode);
  }

  /**
   * Put uri.
   *
   * @param uri the uri
   * @param requestBody the request body
   * @param requestContentType the request content type
   * @param expectedStatusCode the expected status code
   * @throws Exception the exception
   */
  protected void putUri(final String uri,
      final String requestBody, final String requestContentType,
      final HttpStatusCodes expectedStatusCode) throws Exception {
    final HttpResponse response =
        callUri(ODataHttpMethod.PUT, uri, null, null, requestBody, requestContentType, expectedStatusCode);
    if (expectedStatusCode != HttpStatusCodes.NO_CONTENT) {
      response.getEntity().getContent().close();
    }
  }

  /**
   * Put uri.
   *
   * @param uri the uri
   * @param acceptHeader the accept header
   * @param requestBody the request body
   * @param requestContentType the request content type
   * @param expectedStatusCode the expected status code
   * @throws Exception the exception
   */
  protected void putUri(final String uri, final String acceptHeader,
      final String requestBody, final String requestContentType,
      final HttpStatusCodes expectedStatusCode) throws Exception {
    final HttpResponse response =
        callUri(ODataHttpMethod.PUT, uri,
            org.apache.olingo.odata2.api.commons.HttpHeaders.ACCEPT, acceptHeader, requestBody, requestContentType,
            expectedStatusCode);
    if (expectedStatusCode != HttpStatusCodes.NO_CONTENT) {
      response.getEntity().getContent().close();
    }
  }

  /**
   * Gets the body.
   *
   * @param response the response
   * @return the body
   * @throws Exception the exception
   */
  protected String getBody(final HttpResponse response) throws Exception {
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNotNull(response.getEntity().getContent());
    return StringHelper.inputStreamToString(response.getEntity().getContent());
  }

  /**
   * Check media type.
   *
   * @param response the response
   * @param expectedMediaType the expected media type
   */
  protected void checkMediaType(final HttpResponse response, final String expectedMediaType) {
    assertEquals(expectedMediaType, response.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue());
  }

  /**
   * Check etag.
   *
   * @param response the response
   * @param expectedEtag the expected etag
   */
  protected void checkEtag(final HttpResponse response, final String expectedEtag) {
    assertNotNull(response.getFirstHeader(HttpHeaders.ETAG));
    final String entityTag = response.getFirstHeader(HttpHeaders.ETAG).getValue();
    assertNotNull(entityTag);
    assertEquals(expectedEtag, entityTag);
  }
}
