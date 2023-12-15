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

import org.apache.olingo.odata2.api.batch.BatchResponsePart;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.batch.v2.BatchLineReader;
import org.apache.olingo.odata2.core.batch.v2.Line;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchResponseWriterTest.
 */
public class BatchResponseWriterTest {

  /** The Constant CRLF. */
  private static final String CRLF = "\r\n";

  /**
   * Test batch response.
   *
   * @throws ODataException the o data exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testBatchResponse() throws ODataException, IOException {
    List<BatchResponsePart> parts = new ArrayList<BatchResponsePart>();
    ODataResponse response = ODataResponse.entity("Walter Winter")
        .status(HttpStatusCodes.OK)
        .contentHeader("application/json")
        .build();
    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(BatchResponsePart.responses(responses).changeSet(false).build());

    ODataResponse changeSetResponse = ODataResponse.status(HttpStatusCodes.NO_CONTENT).build();
    responses = new ArrayList<ODataResponse>(1);
    responses.add(changeSetResponse);
    parts.add(BatchResponsePart.responses(responses).changeSet(true).build());

    BatchResponseWriter writer = new BatchResponseWriter();
    ODataResponse batchResponse = writer.writeResponse(parts);

    assertEquals(202, batchResponse.getStatus().getStatusCode());
    assertNotNull(batchResponse.getEntity());

    BatchLineReader reader =
        new BatchLineReader(batchResponse.getEntityAsStream());
    List<Line> lines = reader.toLineList();
    reader.close();
    int index = 0;

    assertTrue(lines.get(index++).toString().startsWith("--batch"));
    assertEquals("Content-Type: application/http" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("HTTP/1.1 200 OK" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Type: application/json" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Length: 13" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("Walter Winter" + CRLF, lines.get(index++).toString());
    
    assertTrue(lines.get(index++).toString().startsWith("--batch"));
    assertTrue(lines.get(index++).toString().startsWith("Content-Type: multipart/mixed; boundary=changeset_"));
    assertEquals(CRLF, lines.get(index++).toString());
    assertTrue(lines.get(index++).toString().startsWith("--changeset"));
    assertEquals("Content-Type: application/http" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("HTTP/1.1 204 No Content" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertTrue(lines.get(index++).toString().startsWith("--changeset"));
    assertTrue(lines.get(index).toString().startsWith("--batch"));
  }

  /**
   * Test response.
   *
   * @throws Exception the exception
   */
  @Test
  public void testResponse() throws Exception {
    List<BatchResponsePart> parts = new ArrayList<BatchResponsePart>();
    ODataResponse response =
        ODataResponse.entity("Walter Winter").status(HttpStatusCodes.OK).contentHeader("application/json").build();
    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(BatchResponsePart.responses(responses).changeSet(false).build());
    BatchResponseWriter writer = new BatchResponseWriter();
    ODataResponse batchResponse = writer.writeResponse(parts);

    assertEquals(202, batchResponse.getStatus().getStatusCode());
    assertNotNull(batchResponse.getEntity());
//    String body = (String) batchResponse.getEntity();
    
    BatchLineReader reader =
        new BatchLineReader(batchResponse.getEntityAsStream());
    List<Line> lines = reader.toLineList();
    reader.close();
    int index = 0;
    
    assertTrue(lines.get(index++).toString().startsWith("--batch"));
    assertEquals("Content-Type: application/http" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("HTTP/1.1 200 OK" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Type: application/json" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Length: 13" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("Walter Winter" + CRLF, lines.get(index++).toString());
    assertTrue(lines.get(index).toString().startsWith("--batch"));
  }

  /**
   * Test change set response.
   *
   * @throws Exception the exception
   */
  @Test
  public void testChangeSetResponse() throws Exception {
    List<BatchResponsePart> parts = new ArrayList<BatchResponsePart>();
    ODataResponse changeSetResponse = ODataResponse.status(HttpStatusCodes.NO_CONTENT).build();
    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(changeSetResponse);
    parts.add(BatchResponsePart.responses(responses).changeSet(true).build());

    BatchResponseWriter writer = new BatchResponseWriter();
    ODataResponse batchResponse = writer.writeResponse(parts);

    assertEquals(202, batchResponse.getStatus().getStatusCode());
    assertNotNull(batchResponse.getEntity());

    BatchLineReader reader =
        new BatchLineReader(batchResponse.getEntityAsStream());
    List<Line> lines = reader.toLineList();
    reader.close();
    int index = 0;
    
    assertTrue(lines.get(index++).toString().startsWith("--batch"));
    assertTrue(lines.get(index++).toString().startsWith("Content-Type: multipart/mixed; boundary=changeset_"));
    assertEquals(CRLF, lines.get(index++).toString());
    assertTrue(lines.get(index++).toString().startsWith("--changeset"));
    assertEquals("Content-Type: application/http" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("HTTP/1.1 204 No Content" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertTrue(lines.get(index++).toString().startsWith("--changeset"));
    assertTrue(lines.get(index).toString().startsWith("--batch"));
  }

  /**
   * Test content id echoing.
   *
   * @throws Exception the exception
   */
  @Test
  public void testContentIdEchoing() throws Exception {
    List<BatchResponsePart> parts = new ArrayList<BatchResponsePart>();
    ODataResponse response = ODataResponse.entity("Walter Winter")
        .status(HttpStatusCodes.OK)
        .contentHeader("application/json")
        .header(BatchHelper.MIME_HEADER_CONTENT_ID, "mimeHeaderContentId123")
        .header(BatchHelper.REQUEST_HEADER_CONTENT_ID, "requestHeaderContentId123")
        .build();
    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(BatchResponsePart.responses(responses).changeSet(false).build());
    BatchResponseWriter writer = new BatchResponseWriter();
    ODataResponse batchResponse = writer.writeResponse(parts);

    assertEquals(202, batchResponse.getStatus().getStatusCode());
    assertNotNull(batchResponse.getEntity());

    BatchLineReader reader =
        new BatchLineReader(batchResponse.getEntityAsStream());
    List<Line> lines = reader.toLineList();
    reader.close();
    int index = 0;

    assertTrue(lines.get(index++).toString().startsWith("--batch"));
    assertEquals("Content-Type: application/http" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Id: mimeHeaderContentId123" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("HTTP/1.1 200 OK" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Id: requestHeaderContentId123" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Type: application/json" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Length: 13" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("Walter Winter" + CRLF, lines.get(index++).toString());
    assertTrue(lines.get(index).toString().startsWith("--batch"));
  }

  /**
   * Test response iso.
   *
   * @throws Exception the exception
   */
  @Test
  public void testResponseIso() throws Exception {
    List<BatchResponsePart> parts = new ArrayList<BatchResponsePart>();
    StringHelper.Stream stream = StringHelper.toStream("Wälter Winter", "iso-8859-1");
    ODataResponse response =
        ODataResponse.entity(stream.asStream())
            .contentHeader("application/json; charset=iso-8859-1")
            .status(HttpStatusCodes.OK)
            .build();
    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(BatchResponsePart.responses(responses).changeSet(false).build());
    BatchResponseWriter writer = new BatchResponseWriter(true);
    ODataResponse batchResponse = writer.writeResponse(parts);

    assertEquals(202, batchResponse.getStatus().getStatusCode());
    assertNotNull(batchResponse.getEntity());

    BatchLineReader reader =
        new BatchLineReader(batchResponse.getEntityAsStream());
    List<Line> lines = reader.toLineList();
    reader.close();
    int index = 0;

    assertTrue(lines.get(index++).toString().startsWith("--batch"));
    assertEquals("Content-Type: application/http" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("HTTP/1.1 200 OK" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Type: application/json; charset=iso-8859-1" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Length: 13" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("Wälter Winter" + CRLF, lines.get(index++).toString());
    assertTrue(lines.get(index).toString().startsWith("--batch"));
  }

  /**
   * Test response utf.
   *
   * @throws Exception the exception
   */
  @Test
  public void testResponseUtf() throws Exception {
    List<BatchResponsePart> parts = new ArrayList<BatchResponsePart>();
    String charset = "utf-8";
    StringHelper.Stream stream = StringHelper.toStream("Wälter Winter", charset);
    ODataResponse response =
        ODataResponse.entity(stream.asString(charset))
            .contentHeader("application/json; charset=" + charset)
            .status(HttpStatusCodes.OK)
            .build();
    List<ODataResponse> responses = new ArrayList<ODataResponse>(1);
    responses.add(response);
    parts.add(BatchResponsePart.responses(responses).changeSet(false).build());
    BatchResponseWriter writer = new BatchResponseWriter();
    ODataResponse batchResponse = writer.writeResponse(parts);

    assertEquals(202, batchResponse.getStatus().getStatusCode());
    assertNotNull(batchResponse.getEntity());
//    String body = (String) batchResponse.getEntity();

    BatchLineReader reader =
        new BatchLineReader(batchResponse.getEntityAsStream());
    List<Line> lines = reader.toLineList();
    reader.close();
    int index = 0;

    assertTrue(lines.get(index++).toString().startsWith("--batch"));
    assertEquals("Content-Type: application/http" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Transfer-Encoding: binary" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("HTTP/1.1 200 OK" + CRLF, lines.get(index++).toString());
    assertEquals("Content-Type: application/json; charset=" + charset + CRLF, lines.get(index++).toString());
    assertEquals("Content-Length: 14" + CRLF, lines.get(index++).toString());
    assertEquals(CRLF, lines.get(index++).toString());
    assertEquals("Wälter Winter" + CRLF, lines.get(index++).toString());
    assertTrue(lines.get(index).toString().startsWith("--batch"));
  }
}
