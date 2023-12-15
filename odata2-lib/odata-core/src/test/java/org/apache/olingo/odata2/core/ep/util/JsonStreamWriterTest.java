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
package org.apache.olingo.odata2.core.ep.util;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonStreamWriterTest.
 */
public class JsonStreamWriterTest extends BaseTest {

  /**
   * Basic.
   *
   * @throws Exception the exception
   */
  @Test
  public void basic() throws Exception {
    StringWriter writer = new StringWriter();
    JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
    jsonStreamWriter.beginArray()
        .beginObject()
        .name("value").stringValue("value").separator()
        .name("boolean").unquotedValue(FormatJson.FALSE).separator()
        .name("booleanTrue").unquotedValue(FormatJson.TRUE).separator()
        .name("number").unquotedValue("42.42").separator()
        .namedStringValue("string", "value").separator()
        .namedStringValueRaw("string raw", "1")
        .endObject()
        .endArray();
    writer.flush();
    assertEquals("[{\"value\":\"value\",\"boolean\":false,\"booleanTrue\":true,"
        + "\"number\":42.42,\"string\":\"value\",\"string raw\":\"1\"}]",
        writer.toString());
  }

  /**
   * Null values.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullValues() throws Exception {
    StringWriter writer = new StringWriter();
    JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
    jsonStreamWriter.beginObject()
        .name("number").unquotedValue(null).separator()
        .namedStringValue("string", null).separator().namedStringValueRaw("raw", null);
    jsonStreamWriter.endObject();
    writer.flush();
    assertEquals("{\"number\":null,\"string\":null,\"raw\":null}", writer.toString());
  }

  /**
   * Escape.
   *
   * @throws Exception the exception
   */
  @Test
  public void escape() throws Exception {
    final String outsideBMP = String.valueOf(Character.toChars(0x1F603));
    StringWriter writer = new StringWriter();
    JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
    jsonStreamWriter.beginObject()
        .namedStringValue("normal", "abc / ? \u007F € \uFDFC").separator()
        .namedStringValue("outsideBMP", outsideBMP).separator()
        .namedStringValue("control", "\b\t\n\f\r\u0001\u000B\u0011\u001F " + "€ \uFDFC " + outsideBMP).separator()
        .namedStringValue("escaped", "\"\\")
        .endObject();
    writer.flush();
    assertEquals("{\"normal\":\"abc / ? \u007F € \uFDFC\","
        + "\"outsideBMP\":\"\uD83D\uDE03\","
        + "\"control\":\"\\b\\t\\n\\f\\r\\u0001\\u000B\\u0011\\u001F € \uFDFC \uD83D\uDE03\","
        + "\"escaped\":\"\\\"\\\\\"}",
        writer.toString());
  }
}
