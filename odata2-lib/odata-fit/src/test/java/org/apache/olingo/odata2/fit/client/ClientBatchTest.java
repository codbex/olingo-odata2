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
package org.apache.olingo.odata2.fit.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.olingo.odata2.api.client.batch.BatchChangeSet;
import org.apache.olingo.odata2.api.client.batch.BatchChangeSetPart;
import org.apache.olingo.odata2.api.client.batch.BatchPart;
import org.apache.olingo.odata2.api.client.batch.BatchQueryPart;
import org.apache.olingo.odata2.api.client.batch.BatchSingleResponse;
import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.fit.ref.AbstractRefTest;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ClientBatchTest.
 */
public class ClientBatchTest extends AbstractRefTest {
  
  /**
   * Instantiates a new client batch test.
   *
   * @param servletType the servlet type
   */
  public ClientBatchTest(final ServletType servletType) {
    super(servletType);
  }

  /** The Constant BOUNDARY. */
  private static final String BOUNDARY = "batch_123";

  /**
   * Simple batch.
   *
   * @throws Exception the exception
   */
  @Test
  public void simpleBatch() throws Exception {
    List<BatchPart> batch = new ArrayList<BatchPart>();
    BatchPart request = BatchQueryPart.method(ODataHttpMethod.GET.name()).uri("$metadata").build();
    batch.add(request);

    InputStream body = EntityProvider.writeBatchRequest(batch, BOUNDARY);
    String batchRequestBody = StringHelper.inputStreamToStringCRLFLineBreaks(body);
    checkMimeHeaders(batchRequestBody);
    checkBoundaryDelimiters(batchRequestBody);
    assertTrue(batchRequestBody.contains("GET $metadata HTTP/1.1"));

    HttpResponse batchResponse = execute(batchRequestBody);
    InputStream responseBody = batchResponse.getEntity().getContent();
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    for (BatchSingleResponse response : responses) {
      assertEquals("200", response.getStatusCode());
      assertEquals("OK", response.getStatusInfo());
      assertTrue(response.getBody().contains("<edmx:Edmx"));
      assertEquals("application/xml;charset=utf-8", response.getHeader(HttpHeaders.CONTENT_TYPE));
      assertNotNull(response.getHeader(HttpHeaders.CONTENT_LENGTH));
    }
  }

  /**
   * Simple batch with absolute uri.
   *
   * @throws Exception the exception
   */
  @Test
  public void simpleBatchWithAbsoluteUri() throws Exception {
    final String batchRequestBody = StringHelper.inputStreamToStringCRLFLineBreaks(
        EntityProvider.writeBatchRequest(
            Collections.<BatchPart> singletonList(
                BatchQueryPart
                    .method(ODataHttpMethod.GET.name())
                    .uri(getEndpoint().getPath() + "Employees('2')/EmployeeName/$value")
                    .build()),
            BOUNDARY));
    final HttpResponse batchResponse = execute(batchRequestBody);
    final List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(
        batchResponse.getEntity().getContent(),
        batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue());
    assertEquals(1, responses.size());
    final BatchSingleResponse response = responses.get(0);
    assertEquals(Integer.toString(HttpStatusCodes.OK.getStatusCode()), response.getStatusCode());
    assertEquals(HttpStatusCodes.OK.getInfo(), response.getStatusInfo());
    assertEquals(EMPLOYEE_2_NAME, response.getBody());
    assertEquals(HttpContentType.TEXT_PLAIN_UTF8, response.getHeader(HttpHeaders.CONTENT_TYPE));
    assertNotNull(response.getHeader(HttpHeaders.CONTENT_LENGTH));
  }

  /**
   * Change set batch.
   *
   * @throws Exception the exception
   */
  @Test
  public void changeSetBatch() throws Exception {
    List<BatchPart> batch = new ArrayList<BatchPart>();

    BatchChangeSet changeSet = BatchChangeSet.newBuilder().build();
    Map<String, String> changeSetHeaders = new HashMap<String, String>();
    changeSetHeaders.put(HttpHeaders.CONTENT_TYPE, HttpContentType.APPLICATION_JSON_VERBOSE);

    BatchChangeSetPart changeRequest = BatchChangeSetPart.method(ODataHttpMethod.PUT.name())
        .uri("Employees('2')/EmployeeName")
        .body("{\"EmployeeName\":\"Frederic Fall MODIFIED\"}")
        .headers(changeSetHeaders)
        .build();
    changeSet.add(changeRequest);
    batch.add(changeSet);

    BatchPart request = BatchQueryPart.method(ODataHttpMethod.GET.name())
        .uri("Employees('2')/EmployeeName/$value")
        .build();
    batch.add(request);

    InputStream body = EntityProvider.writeBatchRequest(batch, BOUNDARY);
    String bodyAsString = StringHelper.inputStreamToStringCRLFLineBreaks(body);
    checkMimeHeaders(bodyAsString);
    checkBoundaryDelimiters(bodyAsString);

    assertTrue(bodyAsString.contains("PUT Employees('2')/EmployeeName HTTP/1.1"));
    assertTrue(bodyAsString.contains("GET Employees('2')/EmployeeName/$value HTTP/1.1"));
    assertTrue(bodyAsString.contains("Content-Type: application/json;odata=verbose"));

    HttpResponse batchResponse = execute(bodyAsString);
    InputStream responseBody = batchResponse.getEntity().getContent();
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    for (BatchSingleResponse response : responses) {
      if ("204".equals(response.getStatusCode())) {
        assertEquals("No Content", response.getStatusInfo());
      } else if ("200".equals(response.getStatusCode())) {
        assertEquals("OK", response.getStatusInfo());
        assertTrue(response.getBody().contains("Frederic Fall MODIFIED"));
      } else {
        fail();
      }
    }
  }

  /**
   * Change set batch umlauts.
   *
   * @throws Exception the exception
   */
  @Test
  public void changeSetBatchUmlauts() throws Exception {
    List<BatchPart> batch = new ArrayList<BatchPart>();

    BatchChangeSet changeSet = BatchChangeSet.newBuilder().build();
    Map<String, String> changeSetHeaders = new HashMap<String, String>();
    changeSetHeaders.put(HttpHeaders.CONTENT_TYPE, HttpContentType.APPLICATION_JSON_VERBOSE);

    BatchChangeSetPart changeRequest = BatchChangeSetPart.method(ODataHttpMethod.PUT.name())
        .uri("Employees('2')/EmployeeName")
        .body("{\"EmployeeName\":\"Frederic üäö Fall\"}")
        .headers(changeSetHeaders)
        .build();
    changeSet.add(changeRequest);
    batch.add(changeSet);

    BatchPart request = BatchQueryPart.method(ODataHttpMethod.GET.name())
        .uri("Employees('2')/EmployeeName/$value")
        .build();
    batch.add(request);

    InputStream body = EntityProvider.writeBatchRequest(batch, BOUNDARY);
    StringHelper.Stream bodyStream = StringHelper.toStream(body);
    String bodyAsString = bodyStream.asStringWithLineSeparation("\r\n");
    checkMimeHeaders(bodyAsString);
    checkBoundaryDelimiters(bodyAsString);

    assertTrue(bodyAsString.contains("PUT Employees('2')/EmployeeName HTTP/1.1"));
    assertTrue(bodyAsString.contains("GET Employees('2')/EmployeeName/$value HTTP/1.1"));
    assertTrue(bodyAsString.contains("Content-Type: application/json;odata=verbose"));

    HttpResponse batchResponse = execute(bodyStream.asStreamWithLineSeparation("\r\n"));
    InputStream responseBody = batchResponse.getEntity().getContent();
    //
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    for (BatchSingleResponse response : responses) {
      if ("204".equals(response.getStatusCode())) {
        assertEquals("No Content", response.getStatusInfo());
      } else if ("200".equals(response.getStatusCode())) {
        assertEquals("OK", response.getStatusInfo());
        assertTrue(response.getBody().contains("Frederic üäö Fall"));
      } else {
        fail();
      }
    }
  }


  /**
   * Content id referencing.
   *
   * @throws Exception the exception
   */
  @Test
  public void contentIdReferencing() throws Exception {
    List<BatchPart> batch = new ArrayList<BatchPart>();
    BatchChangeSet changeSet = BatchChangeSet.newBuilder().build();
    Map<String, String> changeSetHeaders = new HashMap<String, String>();
    changeSetHeaders.put(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
    changeSetHeaders.put(HttpHeaders.ACCEPT,
        "application/atom+xml;q=0.8, application/json;odata=verbose;q=0.5, */*;q=0.1");
    BatchChangeSetPart changeRequest = BatchChangeSetPart.method(ODataHttpMethod.POST.name())
        .uri("Employees")
        .contentId("1")
        .body("gAAAAgABwESAAMAAAABAAEA")
        .headers(changeSetHeaders)
        .build();
    changeSet.add(changeRequest);

    changeSetHeaders = new HashMap<String, String>();
    changeSetHeaders.put(HttpHeaders.CONTENT_TYPE, HttpContentType.APPLICATION_JSON_VERBOSE);
    BatchChangeSetPart changeRequest2 = BatchChangeSetPart.method(ODataHttpMethod.PUT.name())
        .uri("$1/EmployeeName")
        .contentId("2")
        .body("{\"EmployeeName\":\"Frederic Fall MODIFIED\"}")
        .headers(changeSetHeaders)
        .build();
    changeSet.add(changeRequest2);
    batch.add(changeSet);

    Map<String, String> getRequestHeaders = new HashMap<String, String>();
    getRequestHeaders.put("content-id", "3");
    BatchPart request = BatchQueryPart.method(ODataHttpMethod.GET.name())
        .uri("Employees('7')/EmployeeName")
        .contentId("3")
        .headers(getRequestHeaders).build();
    batch.add(request);

    InputStream body = EntityProvider.writeBatchRequest(batch, BOUNDARY);
    String bodyAsString = StringHelper.inputStreamToStringCRLFLineBreaks(body);
    checkMimeHeaders(bodyAsString);
    checkBoundaryDelimiters(bodyAsString);
    assertTrue(bodyAsString.contains("POST Employees HTTP/1.1"));
    assertTrue(bodyAsString.contains("PUT $1/EmployeeName"));
    assertTrue(bodyAsString.contains("GET Employees('7')/EmployeeName HTTP/1.1"));

    HttpResponse batchResponse = execute(bodyAsString);
    InputStream responseBody = batchResponse.getEntity().getContent();
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    for (BatchSingleResponse response : responses) {
      if ("1".equals(response.getContentId())) {
        assertEquals("201", response.getStatusCode());
        assertEquals("Created", response.getStatusInfo());
      } else if ("2".equals(response.getContentId())) {
        assertEquals("204", response.getStatusCode());
        assertEquals("No Content", response.getStatusInfo());
      } else if ("3".equals(response.getContentId())) {
        assertEquals("200", response.getStatusCode());
        assertEquals("OK", response.getStatusInfo());
      } else {
        fail();
      }
    }
  }

  /**
   * Error batch.
   *
   * @throws Exception the exception
   */
  @Test
  public void errorBatch() throws Exception {
    List<BatchPart> batch = new ArrayList<BatchPart>();
    BatchPart request = BatchQueryPart.method(ODataHttpMethod.GET.name())
        .uri("nonsense")
        .build();
    batch.add(request);

    InputStream body = EntityProvider.writeBatchRequest(batch, BOUNDARY);
    String bodyAsString = StringHelper.inputStreamToStringCRLFLineBreaks(body);
    checkMimeHeaders(bodyAsString);
    checkBoundaryDelimiters(bodyAsString);

    assertTrue(bodyAsString.contains("GET nonsense HTTP/1.1"));
    HttpResponse batchResponse = execute(bodyAsString);
    InputStream responseBody = batchResponse.getEntity().getContent();
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    for (BatchSingleResponse response : responses) {
      assertEquals("404", response.getStatusCode());
      assertEquals("Not Found", response.getStatusInfo());
    }
  }

  /**
   * Execute.
   *
   * @param entity the entity
   * @return the http response
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private HttpResponse execute(final HttpEntity entity) throws IOException {
    final HttpPost post = new HttpPost(URI.create(getEndpoint().toString() + "$batch"));

    post.setHeader(HttpHeaders.CONTENT_TYPE, "multipart/mixed;boundary=" + BOUNDARY);
    post.setEntity(entity);
    HttpResponse response = getHttpClient().execute(post);

    assertNotNull(response);
    assertEquals(HttpStatusCodes.ACCEPTED.getStatusCode(), response.getStatusLine().getStatusCode());

    return response;
  }

  /**
   * Execute.
   *
   * @param body the body
   * @return the http response
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private HttpResponse execute(final String body) throws IOException {
    return execute(new StringEntity(body));
  }

  /**
   * Execute.
   *
   * @param body the body
   * @return the http response
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private HttpResponse execute(final InputStream body) throws IOException {
    return execute(new InputStreamEntity(body, -1));
  }

  /**
   * Check mime headers.
   *
   * @param requestBody the request body
   */
  private void checkMimeHeaders(final String requestBody) {
    assertTrue(requestBody.contains("Content-Type: application/http"));
    assertTrue(requestBody.contains("Content-Transfer-Encoding: binary"));
  }

  /**
   * Check boundary delimiters.
   *
   * @param requestBody the request body
   */
  private void checkBoundaryDelimiters(final String requestBody) {
    assertTrue(requestBody.contains("--" + BOUNDARY));
    assertTrue(requestBody.contains("--" + BOUNDARY + "--"));
  }
  
  /**
   * Test content format error batch.
   *
   * @throws Exception the exception
   */
  @Test
  public void testContentFormatErrorBatch() throws Exception {
    List<BatchPart> batch = new ArrayList<BatchPart>();
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("DataServiceVersion", "2.0");
    headers.put("MaxDataServiceVersion", "3.0");
    headers.put("Accept", "application/json;odata=verbose");
    BatchPart request = BatchQueryPart.method(ODataHttpMethod.GET.name())
        .uri("nonsense")
        .headers(headers)
        .build();
    batch.add(request);

    InputStream body = EntityProvider.writeBatchRequest(batch, BOUNDARY);
    String bodyAsString = StringHelper.inputStreamToStringCRLFLineBreaks(body);
    checkMimeHeaders(bodyAsString);
    checkBoundaryDelimiters(bodyAsString);

    assertTrue(bodyAsString.contains("GET nonsense HTTP/1.1"));
    HttpResponse batchResponse = execute(bodyAsString);
    InputStream responseBody = batchResponse.getEntity().getContent();
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    for (BatchSingleResponse response : responses) {
      assertEquals("404", response.getStatusCode());
      assertEquals("Not Found", response.getStatusInfo());
      assertEquals("application/json", response.getHeaders().get("Content-Type"));
      assertEquals("{\"error\":{\"code\":null,\"message\":{\"lang\":\"en\",\"value\":"
          + "\"Could not find an entity set or function import for 'nonsense'.\"}}}", response.getBody());
    }
  }
  
  /**
   * Test content ID referencing.
   *
   * @throws Exception the exception
   */
  @Test
  public void testContentIDReferencing() throws Exception {
    final HttpPost post = new HttpPost(URI.create(getEndpoint().toString() + "$batch"));
    post.setHeader("Content-Type", "multipart/mixed;boundary=" + BOUNDARY);

    String body = StringHelper.inputStreamToStringCRLFLineBreaks(
        this.getClass().getResourceAsStream("/basicBatchWithContentIdReferencing.batch"));
    HttpEntity entity = new StringEntity(body);
    post.setEntity(entity);
    HttpResponse batchResponse = getHttpClient().execute(post);

    assertNotNull(batchResponse);
    assertEquals(202, batchResponse.getStatusLine().getStatusCode());
    
    InputStream responseBody = batchResponse.getEntity().getContent();
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    assertEquals("201", responses.get(0).getStatusCode());
    assertEquals("Created", responses.get(0).getStatusInfo());
    assertEquals("201", responses.get(1).getStatusCode());
    assertEquals("Created", responses.get(1).getStatusInfo());
    assertEquals("200", responses.get(2).getStatusCode());
    assertEquals("OK", responses.get(2).getStatusInfo());
  }
  
  /**
   * Test content ID referencingfail.
   *
   * @throws Exception the exception
   */
  @Test
  public void testContentIDReferencingfail() throws Exception {
    final HttpPost post = new HttpPost(URI.create(getEndpoint().toString() + "$batch"));
    post.setHeader("Content-Type", "multipart/mixed;boundary=" + BOUNDARY);

    String body = StringHelper.inputStreamToStringCRLFLineBreaks(
        this.getClass().getResourceAsStream("/basicBatchWithContentIdReferencingFail.batch"));
    HttpEntity entity = new StringEntity(body);
    post.setEntity(entity);
    HttpResponse batchResponse = getHttpClient().execute(post);

    assertNotNull(batchResponse);
    assertEquals(202, batchResponse.getStatusLine().getStatusCode());
    
    InputStream responseBody = batchResponse.getEntity().getContent();
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    assertEquals("400", responses.get(0).getStatusCode());
    assertEquals("Bad Request", responses.get(0).getStatusInfo());
    assertEquals("201", responses.get(1).getStatusCode());
    assertEquals("Created", responses.get(1).getStatusInfo());
    assertEquals("404", responses.get(2).getStatusCode());
    assertEquals("Not Found", responses.get(2).getStatusInfo());
    assertEquals("200", responses.get(3).getStatusCode());
    assertEquals("OK", responses.get(3).getStatusInfo());
  }
  
  /**
   * Test content ID referencing with nav.
   *
   * @throws Exception the exception
   */
  @Test
  public void testContentIDReferencingWithNav() throws Exception {
    final HttpPost post = new HttpPost(URI.create(getEndpoint().toString() + "$batch"));
    post.setHeader("Content-Type", "multipart/mixed;boundary=" + BOUNDARY);

    String body = StringHelper.inputStreamToStringCRLFLineBreaks(
        this.getClass().getResourceAsStream("/basicBatchWithContentIdWithNav.batch"));
    HttpEntity entity = new StringEntity(body);
    post.setEntity(entity);
    HttpResponse batchResponse = getHttpClient().execute(post);

    assertNotNull(batchResponse);
    assertEquals(202, batchResponse.getStatusLine().getStatusCode());
    
    InputStream responseBody = batchResponse.getEntity().getContent();
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    assertEquals("201", responses.get(0).getStatusCode());
    assertEquals("Created", responses.get(0).getStatusInfo());
    assertEquals("200", responses.get(1).getStatusCode());
    assertEquals("OK", responses.get(1).getStatusInfo());
    assertTrue(responses.get(1).getBody().contains("Building 100"));
  }
  
  /**
   * Test content ID referencing with nav fail.
   *
   * @throws Exception the exception
   */
  @Test
  public void testContentIDReferencingWithNavFail() throws Exception {
    final HttpPost post = new HttpPost(URI.create(getEndpoint().toString() + "$batch"));
    post.setHeader("Content-Type", "multipart/mixed;boundary=" + BOUNDARY);

    String body = StringHelper.inputStreamToStringCRLFLineBreaks(
        this.getClass().getResourceAsStream("/basicBatchWithContentIdWithNavFail.batch"));
    HttpEntity entity = new StringEntity(body);
    post.setEntity(entity);
    HttpResponse batchResponse = getHttpClient().execute(post);

    assertNotNull(batchResponse);
    assertEquals(202, batchResponse.getStatusLine().getStatusCode());
    
    InputStream responseBody = batchResponse.getEntity().getContent();
    String contentType = batchResponse.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
    List<BatchSingleResponse> responses = EntityProvider.parseBatchResponse(responseBody, contentType);
    assertEquals("400", responses.get(0).getStatusCode());
    assertEquals("Bad Request", responses.get(0).getStatusInfo());
    assertTrue(responses.get(0).getBody().contains("The request body is malformed."));
    assertEquals("404", responses.get(1).getStatusCode());
    assertEquals("Not Found", responses.get(1).getStatusInfo());
    assertTrue(responses.get(1).getBody().contains(
        "Could not find an entity set or function import for '$2'."));
  }
}
