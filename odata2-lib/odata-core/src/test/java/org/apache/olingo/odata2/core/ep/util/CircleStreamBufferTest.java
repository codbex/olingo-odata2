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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class CircleStreamBufferTest.
 */
public class CircleStreamBufferTest extends BaseTest {

  /** The Constant LOG_ON. */
  private static final boolean LOG_ON = false;
  
  /** The Constant DEFAULT_CHARSET. */
  private static final Charset DEFAULT_CHARSET = Charset.forName("utf-8");

  /**
   * Instantiates a new circle stream buffer test.
   */
  public CircleStreamBufferTest() {}

  /**
   * Sets the up.
   */
  @Before
  public void setUp() {}

  /**
   * Test simple write read sign by sign.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteReadSignBySign() throws Exception {
    CircleStreamBuffer csb = new CircleStreamBuffer();

    OutputStream write = csb.getOutputStream();
    byte[] writeData = "Test".getBytes("UTF-8");
    for (byte element : writeData) {
      write.write(element);
    }

    InputStream inStream = csb.getInputStream();
    byte[] buffer = new byte[4];
    for (int i = 0; i < buffer.length; i++) {
      buffer[i] = (byte) inStream.read();
    }

    String result = new String(buffer);
    log("Test result = [" + result + "]");

    assertEquals("Test", result);
  }

  /**
   * Test simple write read sign by sign more then buffer size.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteReadSignBySignMoreThenBufferSize() throws Exception {
    CircleStreamBuffer csb = new CircleStreamBuffer(128);

    OutputStream write = csb.getOutputStream();
    int signs = 1024;
    String testData = createTestString(signs);
    byte[] writeData = testData.getBytes("UTF-8");
    for (byte element : writeData) {
      write.write(element);
    }

    InputStream inStream = csb.getInputStream();
    byte[] buffer = new byte[signs];
    for (int i = 0; i < buffer.length; i++) {
      buffer[i] = (byte) inStream.read();
    }

    String result = new String(buffer);
    log("Test result = [" + result + "]");

    assertEquals(testData, result);
  }

  /**
   * Test simple write read once.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteReadOnce() throws Exception {
    CircleStreamBuffer csb = new CircleStreamBuffer();

    OutputStream write = csb.getOutputStream();
    write.write("Test".getBytes(), 0, 4);

    InputStream inStream = csb.getInputStream();
    byte[] buffer = new byte[4];
    int count = inStream.read(buffer);

    String result = new String(buffer);
    log("Test result = [" + result + "]");

    assertEquals(4, count);
    assertEquals("Test", result);
  }

  /**
   * Test simple write read twice.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteReadTwice() throws Exception {
    CircleStreamBuffer csb = new CircleStreamBuffer();

    OutputStream outStream = csb.getOutputStream();
    InputStream inStream = csb.getInputStream();

    // first writeInternal/read cycle
    outStream.write("Test_1".getBytes());
    String firstResult = readFrom(inStream);

    log("Test result = [" + firstResult + "]");
    assertEquals("Test_1", firstResult);

    // second writeInternal/read cycle
    outStream.write("Test_2".getBytes());
    String secondResult = readFrom(inStream);

    log("Test result = [" + secondResult + "]");
    assertEquals("Test_2", secondResult);
  }

  /**
   * Test simple write read once 8 k.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteReadOnce8k() throws Exception {
    CircleStreamBuffer csb = new CircleStreamBuffer();

    OutputStream outStream = csb.getOutputStream();
    InputStream inStream = csb.getInputStream();
    final int signs = 8192;

    String testData = createTestString(signs);
    outStream.write(testData.getBytes());
    String result = readFrom(inStream);

    log("Test result = [" + result + "]");
    assertEquals(signs, result.length());
    assertEquals(testData, result);
  }

  /**
   * Test simple write exact once more then buffer size.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteExactOnceMoreThenBufferSize() throws Exception {
    int bufferSize = 4096;
    CircleStreamBuffer csb = new CircleStreamBuffer(bufferSize);

    OutputStream outStream = csb.getOutputStream();
    InputStream inStream = csb.getInputStream();
    final int signs = bufferSize + 1;

    String testData = createTestString(signs);
    outStream.write(testData.getBytes());
    String result = readFrom(inStream, bufferSize * 2);

    log("Test result = [" + result + "]");
    assertEquals(signs, result.length());
    assertEquals(testData, result);
  }

  /**
   * Test simple write read once more then buffer size.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteReadOnceMoreThenBufferSize() throws Exception {
    int bufferSize = 4096;
    CircleStreamBuffer csb = new CircleStreamBuffer(bufferSize);

    OutputStream outStream = csb.getOutputStream();
    InputStream inStream = csb.getInputStream();

    int signs = (1 + bufferSize) * 3;
    String testData = createTestString(signs);
    outStream.write(testData.getBytes());
    String result = readFrom(inStream);
    // log("Test result = [" + result + "]");

    assertEquals(signs, result.length());
    assertEquals(testData, result);
  }

  /**
   * Test simple write more then default buffer size.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteMoreThenDefaultBufferSize() throws Exception {
    CircleStreamBuffer csb = new CircleStreamBuffer();

    OutputStream outStream = csb.getOutputStream();
    InputStream inStream = csb.getInputStream();
    final int signs = 70110;

    String testData = createTestString(signs);
    outStream.write(testData.getBytes(DEFAULT_CHARSET));
    String result = readFrom(inStream);

    assertEquals(signs, result.length());
    assertEquals(testData, result);
  }

  /**
   * Test simple write more then buffer size.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteMoreThenBufferSize() throws Exception {
    int bufferSize = 4096;
    CircleStreamBuffer csb = new CircleStreamBuffer(bufferSize);

    OutputStream outStream = csb.getOutputStream();
    InputStream inStream = csb.getInputStream();
    final int signs = bufferSize * 10;

    String testData = createTestString(signs);
    outStream.write(testData.getBytes(DEFAULT_CHARSET));
    String result = readFrom(inStream, bufferSize * 2);

    assertEquals(signs, result.length());
    assertEquals(testData, result);
  }

  /**
   * Test simple write more then buffer size and umlauts.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteMoreThenBufferSizeAndUmlauts() throws Exception {
    int bufferSize = 4096;
    CircleStreamBuffer csb = new CircleStreamBuffer(bufferSize);

    OutputStream outStream = csb.getOutputStream();
    InputStream inStream = csb.getInputStream();
    final int signs = bufferSize * 10;

    String testData = createTestString(signs);
    testData = "äüöÄÜÖ" + testData + "äüöÄÜÖ";
    outStream.write(testData.getBytes(DEFAULT_CHARSET));
    String result = readFrom(inStream);

    assertEquals(testData.length(), result.length());
    assertEquals(testData, result);
  }

  /**
   * Test simple write more then buffer size and umlauts iso.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteMoreThenBufferSizeAndUmlautsIso() throws Exception {
    int bufferSize = 4096;
    CircleStreamBuffer csb = new CircleStreamBuffer(bufferSize);

    OutputStream outStream = csb.getOutputStream();
    InputStream inStream = csb.getInputStream();
    final int signs = bufferSize * 10;

    String testData = createTestString(signs);
    testData = "äüöÄÜÖ" + testData + "äüöÄÜÖ";
    outStream.write(testData.getBytes("iso-8859-1"));
    String result = readFrom(inStream, "iso-8859-1");

    assertEquals(testData.length(), result.length());
    assertEquals(testData, result);
  }

  /**
   * Test simple write A lot more then buffer size.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSimpleWriteALotMoreThenBufferSize() throws Exception {
    int bufferSize = 4096;
    CircleStreamBuffer csb = new CircleStreamBuffer(bufferSize);

    OutputStream outStream = csb.getOutputStream();
    InputStream inStream = csb.getInputStream();
    final int signs = bufferSize * 100;

    String testData = createTestString(signs);
    outStream.write(testData.getBytes(DEFAULT_CHARSET));
    String result = readFrom(inStream, bufferSize * 2);

    assertEquals(signs, result.length());
    assertEquals(testData, result);
  }


  /**
   * Test close input stream.
   *
   * @throws Exception the exception
   */
  @Test(expected = IOException.class)
  public void testCloseInputStream() throws Exception {
    CircleStreamBuffer csb = new CircleStreamBuffer();

    OutputStream write = csb.getOutputStream();
    write.write("Test".getBytes(), 0, 4);

    InputStream inStream = csb.getInputStream();
    inStream.close();
    byte[] buffer = new byte[4];
    int count = inStream.read(buffer);
    assertEquals(4, count);
  }

  /**
   * Test close output stream.
   *
   * @throws Exception the exception
   */
  @Test(expected = IOException.class)
  public void testCloseOutputStream() throws Exception {
    CircleStreamBuffer csb = new CircleStreamBuffer();

    OutputStream write = csb.getOutputStream();
    write.close();
    write.write("Test".getBytes(), 0, 4);
  }

  // ###################################################
  // #
  // # Below here are test helper methods
  // #
  // ###################################################

  /**
   * Read from.
   *
   * @param stream the stream
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String readFrom(final InputStream stream) throws IOException {
    return readFrom(stream, 128);
  }

  /**
   * Read from.
   *
   * @param stream the stream
   * @param bufferSize the buffer size
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String readFrom(final InputStream stream, final int bufferSize) throws IOException {
    return readFrom(stream, DEFAULT_CHARSET, bufferSize);
  }

  /**
   * Read from.
   *
   * @param stream the stream
   * @param charset the charset
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String readFrom(final InputStream stream, final String charset) throws IOException {
    return readFrom(stream, Charset.forName(charset), 128);
  }

  /**
   * Read from.
   *
   * @param stream the stream
   * @param charset the charset
   * @param bufferSize the buffer size
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String readFrom(final InputStream stream, final Charset charset, final int bufferSize) throws IOException {
    StringBuilder b = new StringBuilder();
    int count;
    byte[] buffer = new byte[bufferSize];
    while ((count = stream.read(buffer)) >= 0) {
      b.append(new String(buffer, 0, count, charset));
    }
    return b.toString();
  }

  /**
   * Creates the test string.
   *
   * @param signs the signs
   * @return the string
   */
  private String createTestString(final int signs) {
    StringBuilder b = new StringBuilder();

    for (int i = 0; i < signs; i++) {
      int sign = (int) (65 + (Math.random() * 25));
      b.append((char) sign);
    }

    return b.toString();
  }

  /**
   * Log.
   *
   * @param toLog the to log
   */
  private void log(final String toLog) {
    if (LOG_ON) {
      System.out.println(toLog);
    }
  }
}
