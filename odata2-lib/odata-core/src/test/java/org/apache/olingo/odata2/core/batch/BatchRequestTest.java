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
package org.apache.olingo.odata2.core.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.batch.BatchException;
import org.apache.olingo.odata2.api.batch.BatchRequestPart;
import org.apache.olingo.odata2.api.client.batch.BatchChangeSet;
import org.apache.olingo.odata2.api.client.batch.BatchChangeSetPart;
import org.apache.olingo.odata2.api.client.batch.BatchPart;
import org.apache.olingo.odata2.api.client.batch.BatchQueryPart;
import org.apache.olingo.odata2.api.ep.EntityProviderBatchProperties;
import org.apache.olingo.odata2.core.PathInfoImpl;
import org.apache.olingo.odata2.core.batch.v2.BatchParser;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * Test creation of a batch request with BatchRequestWriter and
 * then parsing this request again with BatchRequestParser.
 */
public class BatchRequestTest {

  /** The Constant POST. */
  private static final String POST = "POST";
  
  /** The Constant GET. */
  private static final String GET = "GET";
  
  /** The Constant PUT. */
  private static final String PUT = "PUT";
  
  /** The Constant BOUNDARY. */
  private static final String BOUNDARY = "batch_123";
  
  /** The Constant SERVICE_ROOT. */
  private static final String SERVICE_ROOT = "http://localhost/odata/";

  /** The parse properties. */
  private EntityProviderBatchProperties parseProperties;

  /**
   * Instantiates a new batch request test.
   *
   * @throws URISyntaxException the URI syntax exception
   */
  public BatchRequestTest() throws URISyntaxException {
    PathInfoImpl pathInfo = new PathInfoImpl();
    pathInfo.setServiceRoot(new URI(SERVICE_ROOT));
    parseProperties = EntityProviderBatchProperties.init().pathInfo(pathInfo).build();
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
   * Test batch query part.
   *
   * @throws BatchException the batch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testBatchQueryPart() throws BatchException, IOException {
    List<BatchPart> batch = new ArrayList<BatchPart>();
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("Accept", "application/json");
    BatchPart request = BatchQueryPart.method(GET).uri("Employees").headers(headers).build();
    batch.add(request);

    BatchRequestWriter writer = new BatchRequestWriter();
    InputStream batchRequest = writer.writeBatchRequest(batch, BOUNDARY);
    assertNotNull(batchRequest);

    StringHelper.Stream batchRequestStream = StringHelper.toStream(batchRequest);
    String requestBody = batchRequestStream.asString();
    checkMimeHeaders(requestBody);

    assertTrue(requestBody.contains("--batch_"));
    assertTrue(requestBody.contains("GET Employees HTTP/1.1"));
    checkHeaders(headers, requestBody);

    String contentType = "multipart/mixed; boundary=" + BOUNDARY;
    BatchParser parser = new BatchParser(contentType, parseProperties, true);
    List<BatchRequestPart> parseResult = parser.parseBatchRequest(batchRequestStream.asStream());
    assertEquals(1, parseResult.size());
  }

  /**
   * Test batch change set.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws BatchException the batch exception
   */
  @Test
  public void testBatchChangeSet() throws IOException, BatchException {
    List<BatchPart> batch = new ArrayList<BatchPart>();
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("content-type", "application/json");
    BatchChangeSetPart request = BatchChangeSetPart.method(PUT)
        .uri("Employees('2')")
        .body("{\"Возраст\":40}")
        .headers(headers)
        .contentId("111")
        .build();
    BatchChangeSet changeSet = BatchChangeSet.newBuilder().build();
    changeSet.add(request);
    batch.add(changeSet);

    BatchRequestWriter writer = new BatchRequestWriter();
    InputStream batchRequest = writer.writeBatchRequest(batch, BOUNDARY);
    assertNotNull(batchRequest);

    StringHelper.Stream batchRequestStream = StringHelper.toStream(batchRequest);
    String requestBody = batchRequestStream.asString();
    checkMimeHeaders(requestBody);
    checkHeaders(headers, requestBody);

    assertTrue(requestBody.contains("--batch_"));
    assertTrue(requestBody.contains("--changeset_"));
    assertTrue(requestBody.contains("PUT Employees('2') HTTP/1.1"));
    assertTrue(requestBody.contains("{\"Возраст\":40}"));
    assertEquals(15, batchRequestStream.linesCount());

    String contentType = "multipart/mixed; boundary=" + BOUNDARY;
    BatchParser parser = new BatchParser(contentType, parseProperties, true);
    List<BatchRequestPart> parseResult = parser.parseBatchRequest(batchRequestStream.asStream());
    assertEquals(1, parseResult.size());
  }

  /**
   * Test batch change set iso.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws BatchException the batch exception
   */
  @Test
  public void testBatchChangeSetIso() throws IOException, BatchException {
    List<BatchPart> batch = new ArrayList<BatchPart>();
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("Content-type", "application/json; charset=iso-8859-1");
    BatchChangeSetPart request = BatchChangeSetPart.method(PUT)
        .uri("Employees('2')")
        .body("{\"Name\":Wälter}")
        .headers(headers)
        .contentId("111")
        .build();
    BatchChangeSet changeSet = BatchChangeSet.newBuilder().build();
    changeSet.add(request);
    batch.add(changeSet);

    BatchRequestWriter writer = new BatchRequestWriter();
    InputStream batchRequest = writer.writeBatchRequest(batch, BOUNDARY);
    assertNotNull(batchRequest);

    StringHelper.Stream batchRequestStream = StringHelper.toStream(batchRequest);
    String requestBody = batchRequestStream.asString("iso-8859-1");
    checkMimeHeaders(requestBody);
    checkHeaders(headers, requestBody);

    assertTrue(requestBody.contains("--batch_"));
    assertTrue(requestBody.contains("--changeset_"));
    assertTrue(requestBody.contains("PUT Employees('2') HTTP/1.1"));
    assertTrue(requestBody.contains("Content-Length: 15"));
    assertTrue(requestBody.contains("{\"Name\":Wälter}"));
    assertEquals(15, batchRequestStream.linesCount());

    String contentType = "multipart/mixed; boundary=" + BOUNDARY;
    BatchParser parser = new BatchParser(contentType, parseProperties, true);
    List<BatchRequestPart> parseResult = parser.parseBatchRequest(batchRequestStream.asStream());
    assertEquals(1, parseResult.size());
  }


  /**
   * Test batch with get and post.
   *
   * @throws BatchException the batch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testBatchWithGetAndPost() throws BatchException, IOException {
    List<BatchPart> batch = new ArrayList<BatchPart>();
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("Accept", "application/json");
    BatchPart request = BatchQueryPart.method(GET).uri("Employees").headers(headers).contentId("000").build();
    batch.add(request);

    Map<String, String> changeSetHeaders = new HashMap<String, String>();
    changeSetHeaders.put("content-type", "application/json");
    String body = "/9j/4AAQSkZJRgABAQEBLAEsAAD/4RM0RXhpZgAATU0AKgAAAAgABwESAAMAAAABAAEA";
    BatchChangeSetPart changeRequest = BatchChangeSetPart.method(POST)
        .uri("Employees")
        .body(body)
        .headers(changeSetHeaders)
        .contentId("111")
        .build();
    BatchChangeSet changeSet = BatchChangeSet.newBuilder().build();
    changeSet.add(changeRequest);
    batch.add(changeSet);
    BatchRequestWriter writer = new BatchRequestWriter();
    InputStream batchRequest = writer.writeBatchRequest(batch, BOUNDARY);
    assertNotNull(batchRequest);
    StringHelper.Stream batchRequestStream = StringHelper.toStream(batchRequest);
    String requestBody = batchRequestStream.asString();
    checkMimeHeaders(requestBody);

    checkHeaders(headers, requestBody);
    checkHeaders(changeSetHeaders, requestBody);
    assertTrue(requestBody.contains("GET Employees HTTP/1.1"));
    assertTrue(requestBody.contains("POST Employees HTTP/1.1"));
    assertTrue(requestBody.contains(body));
    assertEquals(24, batchRequestStream.linesCount());

    String contentType = "multipart/mixed; boundary=" + BOUNDARY;
    BatchParser parser = new BatchParser(contentType, parseProperties, true);
    List<BatchRequestPart> parseResult = parser.parseBatchRequest(batchRequestStream.asStream());
    assertEquals(2, parseResult.size());
  }
  
  /**
   * Test change set with content id referencing.
   *
   * @throws BatchException the batch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testChangeSetWithContentIdReferencing() throws BatchException, IOException {
    List<BatchPart> batch = new ArrayList<BatchPart>();

    Map<String, String> changeSetHeaders = new HashMap<String, String>();
    changeSetHeaders.put("content-type", "application/json");
    String body = "/9j/4AAQSkZJRgABAQEBLAEsAAD/4RM0RXhpZgAATU0AKgAAAAgABwESAAMAAAABAAEA";
    BatchChangeSetPart changeRequest = BatchChangeSetPart.method(POST)
        .uri("Employees('2')")
        .body(body)
        .headers(changeSetHeaders)
        .contentId("1")
        .build();
    BatchChangeSet changeSet = BatchChangeSet.newBuilder().build();
    changeSet.add(changeRequest);

    changeSetHeaders = new HashMap<String, String>();
    changeSetHeaders.put("content-type", "application/json;odata=verbose");
    BatchChangeSetPart changeRequest2 = BatchChangeSetPart.method(PUT)
        .uri("$/ManagerId")
        .body("{\"ManagerId\":1}")
        .headers(changeSetHeaders)
        .contentId("2")
        .build();
    changeSet.add(changeRequest2);
    batch.add(changeSet);

    BatchRequestWriter writer = new BatchRequestWriter();
    InputStream batchRequest = writer.writeBatchRequest(batch, BOUNDARY);
    assertNotNull(batchRequest);

    StringHelper.Stream batchRequestStream = StringHelper.toStream(batchRequest);
    String requestBody = batchRequestStream.asString();
    checkMimeHeaders(requestBody);

    assertTrue(requestBody.contains("POST Employees('2') HTTP/1.1"));
    assertTrue(requestBody.contains("PUT $/ManagerId HTTP/1.1"));
    assertTrue(requestBody.contains(BatchHelper.HTTP_CONTENT_ID + ": 1"));
    assertTrue(requestBody.contains(BatchHelper.HTTP_CONTENT_ID + ": 2"));
    assertTrue(requestBody.contains(body));

    String contentType = "multipart/mixed; boundary=" + BOUNDARY;
    BatchParser parser = new BatchParser(contentType, parseProperties, true);
    List<BatchRequestPart> parseResult = parser.parseBatchRequest(batchRequestStream.asStream());
    assertEquals(1, parseResult.size());
  }

  /**
   * Test batch with two change sets.
   *
   * @throws BatchException the batch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testBatchWithTwoChangeSets() throws BatchException, IOException {
    List<BatchPart> batch = new ArrayList<BatchPart>();

    Map<String, String> changeSetHeaders = new HashMap<String, String>();
    changeSetHeaders.put("content-type", "application/json");
    changeSetHeaders.put("content-Id", "111");
    String body = "/9j/4AAQSkZJRgABAQEBLAEsAAD/4RM0RXhpZgAATU0AKgAAAAgABwESAAMAAAABAAEA";
    BatchChangeSetPart changeRequest = BatchChangeSetPart.method(POST)
        .uri("Employees")
        .contentId("111request")
        .body(body)
        .headers(changeSetHeaders)
        .build();
    BatchChangeSet changeSet = BatchChangeSet.newBuilder().build();
    changeSet.add(changeRequest);
    batch.add(changeSet);

    Map<String, String> changeSetHeaders2 = new HashMap<String, String>();
    changeSetHeaders2.put("content-type", "application/json;odata=verbose");
    changeSetHeaders2.put("content-Id", "222");
    BatchChangeSetPart changeRequest2 = BatchChangeSetPart.method(PUT)
        .uri("Employees('2')/ManagerId")
        .contentId("222request")
        .body("{\"ManagerId\":1}")
        .headers(changeSetHeaders2)
        .build();
    BatchChangeSet changeSet2 = BatchChangeSet.newBuilder().build();
    changeSet2.add(changeRequest2);
    batch.add(changeSet2);

    BatchRequestWriter writer = new BatchRequestWriter();
    InputStream batchRequest = writer.writeBatchRequest(batch, BOUNDARY);
    assertNotNull(batchRequest);

    StringHelper.Stream batchRequestStream = StringHelper.toStream(batchRequest);
    String requestBody = batchRequestStream.asString();
    checkMimeHeaders(requestBody);

    assertTrue(requestBody.contains("POST Employees HTTP/1.1"));
    assertTrue(requestBody.contains("PUT Employees('2')/ManagerId HTTP/1.1"));

    assertTrue(requestBody.contains(body));

    String contentType = "multipart/mixed; boundary=" + BOUNDARY;
    BatchParser parser = new BatchParser(contentType, parseProperties, true);
    List<BatchRequestPart> parseResult = parser.parseBatchRequest(batchRequestStream.asStream());
    assertEquals(2, parseResult.size());
  }

  /**
   * Check headers.
   *
   * @param headers the headers
   * @param requestBody the request body
   */
  private void checkHeaders(final Map<String, String> headers, final String requestBody) {
    for (Map.Entry<String, String> header : headers.entrySet()) {
      assertTrue(requestBody.contains(header.getKey() + ": " + header.getValue()));
    }
  }

  /**
   * Test batch query part with invalid method.
   *
   * @throws BatchException the batch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBatchQueryPartWithInvalidMethod() throws BatchException, IOException {
    BatchQueryPart.method(PUT).uri("Employees").build();

  }

  /**
   * Test batch change set part with invalid method.
   *
   * @throws BatchException the batch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testBatchChangeSetPartWithInvalidMethod() throws BatchException, IOException {
    BatchChangeSetPart.method(GET).uri("Employees('2')").build();

  }  
  
  /**
   * Test batch change set raw bytes.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws BatchException the batch exception
   */
  @Test
  public void testBatchChangeSetRawBytes() throws IOException, BatchException {
    List<BatchPart> batch = new ArrayList<BatchPart>();
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("content-type", "application/octect-stream");
    byte[] data = getRawBytes();
    BatchChangeSetPart request = BatchChangeSetPart.method(PUT)
        .uri("Employees('2')/$value")
        .body(data)
        .headers(headers)
        .build();
    BatchChangeSet changeSet = BatchChangeSet.newBuilder().build();
    changeSet.add(request);
    batch.add(changeSet);

    BatchRequestWriter writer = new BatchRequestWriter();
    InputStream batchRequest = writer.writeBatchRequest(batch, BOUNDARY);
    assertNotNull(batchRequest);

    StringHelper.Stream batchRequestStream = StringHelper.toStream(batchRequest);
    String requestBody = batchRequestStream.asString("ISO-8859-1");
    checkMimeHeaders(requestBody);
    checkHeaders(headers, requestBody);

    assertTrue(requestBody.contains("--batch_"));
    assertTrue(requestBody.contains("--changeset_"));
    assertTrue(requestBody.contains("PUT Employees('2')/$value HTTP/1.1"));

    String contentType = "multipart/mixed; boundary=" + BOUNDARY;
    BatchParser parser = new BatchParser(contentType, parseProperties, true);
    List<BatchRequestPart> parseResult = parser.parseBatchRequest(batchRequestStream.asStream());
    assertEquals(1, parseResult.size());
    InputStream in = parseResult.get(0).getRequests().get(0).getBody();
    StringHelper.Stream parsedReqStream = StringHelper.toStream(in);
    String parsedReqData = parsedReqStream.asString("ISO-8859-1");
    assertArrayEquals(data, parsedReqData.getBytes("ISO-8859-1"));
  }

  /**
   * Gets the raw bytes.
   *
   * @return the raw bytes
   */
  private byte[] getRawBytes() {
    byte[] data = new byte[Byte.MAX_VALUE - Byte.MIN_VALUE + 1];
    // binary content, not a valid UTF-8 representation of a string
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
      data[i - Byte.MIN_VALUE] = (byte) i;
    }
    return data;
  }
}
