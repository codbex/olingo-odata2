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
package org.apache.olingo.odata2.core.debug;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.codec.binary.Base64;
import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class DebugInfoBodyTest.
 */
public class DebugInfoBodyTest {

  /** The Constant STRING_CONTENT. */
  private static final String STRING_CONTENT = "StringContent";
  
  /** The Constant STRING_CONTENT_JSON. */
  private static final String STRING_CONTENT_JSON = "\"" + STRING_CONTENT + "\"";

  /**
   * Json string content.
   *
   * @throws Exception the exception
   */
  @Test
  public void jsonStringContent() throws Exception {
    ODataResponse response = mock(ODataResponse.class);
    when(response.getEntity()).thenReturn(STRING_CONTENT);
    when(response.getContentHeader()).thenReturn(HttpContentType.APPLICATION_OCTET_STREAM);
    assertEquals(STRING_CONTENT_JSON, appendJson(response));

    when(response.getContentHeader()).thenReturn("image/png");
    assertEquals(STRING_CONTENT_JSON, appendJson(response));
  }

  /**
   * Json input stream content.
   *
   * @throws Exception the exception
   */
  @Test
  public void jsonInputStreamContent() throws Exception {
    ODataResponse response = mock(ODataResponse.class);
    ByteArrayInputStream in = new ByteArrayInputStream(STRING_CONTENT.getBytes());
    when(response.getEntity()).thenReturn(in);
    when(response.getContentHeader()).thenReturn(HttpContentType.TEXT_PLAIN);
    assertEquals(STRING_CONTENT_JSON, appendJson(response));

    in = new ByteArrayInputStream(STRING_CONTENT.getBytes("UTF-8"));
    when(response.getEntity()).thenReturn(in);
    when(response.getContentHeader()).thenReturn("image/png");
    assertEquals("\"" + Base64.encodeBase64String(STRING_CONTENT.getBytes("UTF-8")) + "\"",
        appendJson(response));
  }

  /**
   * Json unsupported content.
   *
   * @throws Exception the exception
   */
  @Test(expected = ClassCastException.class)
  public void jsonUnsupportedContent() throws Exception {
    ODataResponse response = mock(ODataResponse.class);
    when(response.getEntity()).thenReturn(new Object());
    when(response.getContentHeader()).thenReturn(HttpContentType.APPLICATION_OCTET_STREAM);

    appendJson(response);
  }

  /**
   * Append json.
   *
   * @param response the response
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String appendJson(final ODataResponse response) throws IOException {
    Writer writer = new StringWriter();
    DebugInfoBody body = new DebugInfoBody(response, null);
    body.appendJson(new JsonStreamWriter(writer));
    return writer.toString();
  }
}
