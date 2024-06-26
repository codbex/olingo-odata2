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
package org.apache.olingo.odata2.core.batch.v2;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.olingo.odata2.core.batch.v2.BatchLineReader;
import org.apache.olingo.odata2.core.batch.v2.Line;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchLineReaderTest.
 */
public class BatchLineReaderTest {

  /** The Constant TEXT_COMBINED. */
  private static final String TEXT_COMBINED = "Test\r" +
      "Test2\r\n" +
      "Test3\n" +
      "Test4\r" +
      "\r" +
      "\r\n" +
      "\r\n" +
      "Test5\n" +
      "Test6\r\n" +
      "Test7\n" +
      "\n";

  /** The Constant TEXT_EMPTY. */
  private static final String TEXT_EMPTY = "";

  /**
   * Test simple text.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testSimpleText() throws IOException {
    final String TEXT = "Test";
    BatchLineReader reader = create(TEXT);

    assertEquals(TEXT, reader.readLine());
    assertNull(reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Test no text.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testNoText() throws IOException {
    final String TEXT = "";
    BatchLineReader reader = create(TEXT);

    assertNull(reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Test no bytes.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testNoBytes() throws IOException {
    BatchLineReader reader =
        new BatchLineReader(new ByteArrayInputStream(new byte[0]));

    assertNull(reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Test CRLF.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testCRLF() throws IOException {
    final String TEXT = "Test\r\n" +
        "Test2";

    BatchLineReader reader = create(TEXT);

    assertEquals("Test\r\n", reader.readLine());
    assertEquals("Test2", reader.readLine());
    assertNull(reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Test LF.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testLF() throws IOException {
    final String TEXT = "Test\n" +
        "Test2";

    BatchLineReader reader = create(TEXT);

    assertEquals("Test\n", reader.readLine());
    assertEquals("Test2", reader.readLine());
    assertNull(reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Test for special case (described in https://issues.apache.org/jira/browse/OLINGO-1053 )
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testSpecialCRLF() throws IOException {
    String line1 = StringHelper.generateData(8191);
    String line2 = StringHelper.generateData(8192);
    final String content = line1 + "\r\n" + line2 + "\n" + "test";

    BatchLineReader reader = create(content);

    assertEquals(line1 + "\r\n", reader.readLine());
    assertEquals(line2 + "\n", reader.readLine());
    assertEquals("test", reader.readLine());
    reader.close();
  }

  /**
   * Test CR.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testCR() throws IOException {
    final String TEXT = "Test\r" +
        "Test2";

    BatchLineReader reader = create(TEXT);

    assertEquals("Test\r", reader.readLine());
    assertEquals("Test2", reader.readLine());
    assertNull(reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Test combined.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testCombined() throws IOException {
    BatchLineReader reader = create(TEXT_COMBINED);

    assertEquals("Test\r", reader.readLine());
    assertEquals("Test2\r\n", reader.readLine());
    assertEquals("Test3\n", reader.readLine());
    assertEquals("Test4\r", reader.readLine());
    assertEquals("\r", reader.readLine());
    assertEquals("\r\n", reader.readLine());
    assertEquals("\r\n", reader.readLine());
    assertEquals("Test5\n", reader.readLine());
    assertEquals("Test6\r\n", reader.readLine());
    assertEquals("Test7\n", reader.readLine());
    assertEquals("\n", reader.readLine());
    assertNull(reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Test combined buffer size two.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testCombinedBufferSizeTwo() throws IOException {
    BatchLineReader reader = create(TEXT_COMBINED, 2);

    assertEquals("Test\r", reader.readLine());
    assertEquals("Test2\r\n", reader.readLine());
    assertEquals("Test3\n", reader.readLine());
    assertEquals("Test4\r", reader.readLine());
    assertEquals("\r", reader.readLine());
    assertEquals("\r\n", reader.readLine());
    assertEquals("\r\n", reader.readLine());
    assertEquals("Test5\n", reader.readLine());
    assertEquals("Test6\r\n", reader.readLine());
    assertEquals("Test7\n", reader.readLine());
    assertEquals("\n", reader.readLine());
    assertNull(reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Test combined buffer size one.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testCombinedBufferSizeOne() throws IOException {
    final String TEXT = "Test\r" +
        "Test2\r\n" +
        "Test3\n" +
        "Test4\r" +
        "\r" +
        "\r\n" +
        "\r\n" +
        "Test5\n" +
        "Test6\r\n" +
        "Test7\n" +
        "\r\n";

    BatchLineReader reader = create(TEXT, 1);

    assertEquals("Test\r", reader.readLine());
    assertEquals("Test2\r\n", reader.readLine());
    assertEquals("Test3\n", reader.readLine());
    assertEquals("Test4\r", reader.readLine());
    assertEquals("\r", reader.readLine());
    assertEquals("\r\n", reader.readLine());
    assertEquals("\r\n", reader.readLine());
    assertEquals("Test5\n", reader.readLine());
    assertEquals("Test6\r\n", reader.readLine());
    assertEquals("Test7\n", reader.readLine());
    assertEquals("\r\n", reader.readLine());
    assertNull(reader.readLine());
    assertNull(reader.readLine());

    reader.close();
  }

  /**
   * Test double LF.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testDoubleLF() throws IOException {
    final String TEXT = "Test\r" +
        "\r";

    BatchLineReader reader = create(TEXT, 1);

    assertEquals("Test\r", reader.readLine());
    assertEquals("\r", reader.readLine());
    reader.close();
  }

  /**
   * Special characters.
   *
   * @throws Exception the exception
   */
  @Test
  public void specialCharacters() throws Exception {
    final String text = "\r\n"
        + "Content-Type: text/plain; charset=UTF-8\r\n"
        + "\r\n"
        + "ä€\r\n"
        + "\uFDFC\r\n"  // RIAL SIGN
        // Unicode characters outside the Basic Multilingual Plane are stored
        // in a Java String in two surrogate characters.
        + String.valueOf(Character.toChars(0x1F603));
    BatchLineReader reader = create(text);
    reader.readLine();
    reader.readLine();
    reader.readLine();
    assertEquals("ä€\r\n", reader.readLine());
    assertEquals("\uFDFC\r\n", reader.readLine());
    assertEquals(String.valueOf(Character.toChars(0x1F603)), reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Special characters in json with newline.
   *
   * @throws Exception the exception
   */
  @Test
  public void specialCharactersInJsonWithNewline() throws Exception {
    final String text = "\n"
        + "Content-Type: application/json\n"
        + "\n"
        + "{\"text\": \"ä€ß\"}\n";
    BatchLineReader reader = create(text);
    reader.readLine();
    reader.readLine();
    reader.readLine();
    assertEquals("{\"text\": \"ä€ß\"}\n", reader.readLine());
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Test fail buffer size zero.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailBufferSizeZero() throws IOException {
    BatchLineReader reader = create(TEXT_EMPTY, 0);
    reader.close();
  }

  /**
   * Test input stream is null.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test(expected = NullPointerException.class)
  public void testInputStreamIsNull() throws IOException {
    // Same behaviour like BufferedReader
    BatchLineReader reader = new BatchLineReader(null);
    reader.close();
  }

  /**
   * Test fail buffer size negative.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testFailBufferSizeNegative() throws IOException {
    BatchLineReader reader = create(TEXT_EMPTY, -1);
    reader.close();
  }

  /**
   * Test to list.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  public void testToList() throws IOException {
    BatchLineReader reader = create(TEXT_COMBINED);
    List<Line> stringList = reader.toLineList();

    assertEquals(11, stringList.size());
    assertEquals("Test\r", stringList.get(0).toString());
    assertEquals("Test2\r\n", stringList.get(1).toString());
    assertEquals("Test3\n", stringList.get(2).toString());
    assertEquals("Test4\r", stringList.get(3).toString());
    assertEquals("\r", stringList.get(4).toString());
    assertEquals("\r\n", stringList.get(5).toString());
    assertEquals("\r\n", stringList.get(6).toString());
    assertEquals("Test5\n", stringList.get(7).toString());
    assertEquals("Test6\r\n", stringList.get(8).toString());
    assertEquals("Test7\n", stringList.get(9).toString());
    assertEquals("\n", stringList.get(10).toString());
    reader.close();
  }

  /**
   * Creates the.
   *
   * @param inputString the input string
   * @return the batch line reader
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  private BatchLineReader create(final String inputString) throws UnsupportedEncodingException {
    return new BatchLineReader(new ByteArrayInputStream(inputString.getBytes("UTF-8")));
  }

  /**
   * Creates the.
   *
   * @param inputString the input string
   * @param bufferSize the buffer size
   * @return the batch line reader
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  private BatchLineReader create(final String inputString, int bufferSize)
      throws UnsupportedEncodingException {
    return new BatchLineReader(new ByteArrayInputStream(inputString.getBytes("UTF-8")), bufferSize);
  }

  /**
   * Raw bytes.
   *
   * @throws Exception the exception
   */
  @Test
  public void rawBytes() throws Exception {
    byte[] content = new byte[Byte.MAX_VALUE - Byte.MIN_VALUE + 1];
    // binary content, not a valid UTF-8 representation of a string
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
      content[i - Byte.MIN_VALUE] = (byte) i;
    }
    BatchLineReader reader = new BatchLineReader(new ByteArrayInputStream(content));
    final String contentString = reader.readLine()  // initial part up to '\n'
        + reader.readLine()  // second part from '\n' to '\r'
        + reader.readLine();  // the rest
    assertArrayEquals(content, contentString.getBytes(Charset.forName("ISO-8859-1")));
    assertNull(reader.readLine());
    reader.close();
  }

  /**
   * Image test.
   *
   * @throws Exception the exception
   */
  @Test
  public void imageTest() throws Exception {
    byte[] data = getImageData("/Employee_1.png");
    BatchLineReader reader = new BatchLineReader(new ByteArrayInputStream(data));
    final List<Line> contentString = reader.toLineList();
    String finalContent = "";
    for (Line content : contentString) {
      finalContent += content.toString();
    }

    assertArrayEquals(data, finalContent.getBytes(Charset.forName("ISO-8859-1")));
    reader.close();
  }

  /**
   * Gets the image data.
   *
   * @param imageUrl the image url
   * @return the image data
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private byte[] getImageData(String imageUrl) throws IOException {
    byte[] data = null;
    try {
      InputStream in = this.getClass().getResourceAsStream(imageUrl);
      ByteArrayOutputStream stream = new ByteArrayOutputStream();
      int b = 0;
      while ((b = in.read()) != -1) {
        stream.write(b);
      }

      data = stream.toByteArray();
    } catch (IOException e) {
      throw e;
    }
    return data;
  }
}
