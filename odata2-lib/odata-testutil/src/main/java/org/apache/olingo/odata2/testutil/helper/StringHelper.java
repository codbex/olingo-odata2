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
package org.apache.olingo.odata2.testutil.helper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.olingo.odata2.testutil.TestUtilRuntimeException;

// TODO: Auto-generated Javadoc
/**
 * The Class StringHelper.
 */
public class StringHelper {

  /**
   * The Class Stream.
   */
  public static class Stream {
    
    /** The data. */
    private final byte[] data;

    /**
     * Instantiates a new stream.
     *
     * @param data the data
     */
    private Stream(final byte[] data) {
      this.data = data;
    }

    /**
     * Instantiates a new stream.
     *
     * @param content the content
     * @param charset the charset
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public Stream(final String content, final String charset) throws UnsupportedEncodingException {
      this(content.getBytes(charset));
    }

    /**
     * As stream.
     *
     * @return the input stream
     */
    public InputStream asStream() {
      return new ByteArrayInputStream(data);
    }

    /**
     * As array.
     *
     * @return the byte[]
     */
    public byte[] asArray() {
      return data;
    }

    /**
     * As string.
     *
     * @return the string
     */
    public String asString() {
      return asString("UTF-8");
    }

    /**
     * As string.
     *
     * @param charsetName the charset name
     * @return the string
     */
    public String asString(final String charsetName) {
      return new String(data, Charset.forName(charsetName));
    }

    /**
     * Prints the.
     *
     * @param out the out
     * @return the stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Stream print(final OutputStream out) throws IOException {
      out.write(data);
      return this;
    }

    /**
     * Prints the.
     *
     * @return the stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Stream print() throws IOException {
      return print(System.out);
    }

    /**
     * As string with line separation.
     *
     * @param separator the separator
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String asStringWithLineSeparation(String separator) throws IOException {
      BufferedReader br = new BufferedReader(new StringReader(asString()));
      StringBuilder sb = new StringBuilder(br.readLine());
      String line = br.readLine();
      while (line != null) {
        sb.append(separator).append(line);
        line = br.readLine();
      }
      return sb.toString();
    }

    /**
     * As stream with line separation.
     *
     * @param separator the separator
     * @return the input stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public InputStream asStreamWithLineSeparation(String separator) throws IOException {
      String asString = asStringWithLineSeparation(separator);
      return new ByteArrayInputStream(asString.getBytes("UTF-8"));
    }

    /**
     * Number of lines separated by line breaks (<code>CRLF</code>).
     * A content string like <code>text\r\nmoreText</code> will result in
     * a line count of <code>2</code>.
     * 
     * @return lines count
     */
    public int linesCount() {
      return StringHelper.countLines(asString(), "\r\n");
    }
  }

  /**
   * To stream.
   *
   * @param stream the stream
   * @return the stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Stream toStream(final InputStream stream) throws IOException {
    byte[] result = new byte[0];
    byte[] tmp = new byte[8192];
    int readCount = stream.read(tmp);
    while (readCount >= 0) {
      byte[] innerTmp = new byte[result.length + readCount];
      System.arraycopy(result, 0, innerTmp, 0, result.length);
      System.arraycopy(tmp, 0, innerTmp, result.length, readCount);
      result = innerTmp;
      readCount = stream.read(tmp);
    }
    stream.close();
    return new Stream(result);
  }

  /**
   * To stream.
   *
   * @param content the content
   * @return the stream
   */
  public static Stream toStream(final String content) {
    return toStream(content, "utf-8");
  }

  /**
   * To stream.
   *
   * @param content the content
   * @param charset the charset
   * @return the stream
   */
  public static Stream toStream(final String content, final String charset) {
    try {
      return new Stream(content, charset);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("UTF-8 should be supported on each system.");
    }
  }

  /**
   * Input stream to string.
   *
   * @param in the in
   * @param preserveLineBreaks the preserve line breaks
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String inputStreamToString(final InputStream in, final boolean preserveLineBreaks) throws IOException {
    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
    final StringBuilder stringBuilder = new StringBuilder();
    String line = null;

    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line);
      if (preserveLineBreaks) {
        stringBuilder.append("\n");
      }
    }

    bufferedReader.close();

    final String result = stringBuilder.toString();

    return result;
  }

  /**
   * Input stream to string CRLF line breaks.
   *
   * @param in the in
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String inputStreamToStringCRLFLineBreaks(final InputStream in) throws IOException {
    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
    final StringBuilder stringBuilder = new StringBuilder();
    String line = null;

    while ((line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line);
      stringBuilder.append("\r\n");
    }

    bufferedReader.close();

    final String result = stringBuilder.toString();

    return result;
  }

  /**
   * Count lines.
   *
   * @param content the content
   * @return the int
   */
  public static int countLines(final String content) {
    return countLines(content, "\r\n");
  }

  /**
   * Count lines.
   *
   * @param content the content
   * @param lineBreak the line break
   * @return the int
   */
  public static int countLines(final String content, final String lineBreak) {
    if (content == null) {
      return -1;
    }

    int lastPos = content.indexOf(lineBreak);
    int count = 1;

    while (lastPos >= 0) {
      lastPos = content.indexOf(lineBreak, lastPos + 1);
      count++;
    }
    return count;
  }

  /**
   * Input stream to string.
   *
   * @param in the in
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String inputStreamToString(final InputStream in) throws IOException {
    return inputStreamToString(in, false);
  }

  /**
   * Http entity to string.
   *
   * @param entity the entity
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws IllegalStateException the illegal state exception
   */
  public static String httpEntityToString(final HttpEntity entity) throws IOException, IllegalStateException {
    return inputStreamToString(entity.getContent());
  }

  /**
   * Encapsulate given content in an {@link InputStream} with charset <code>UTF-8</code>.
   * 
   * @param content to encapsulate content
   * @return content as stream
   */
  public static InputStream encapsulate(final String content) {
    try {
      return encapsulate(content, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      // we know that UTF-8 is supported
      throw new TestUtilRuntimeException("UTF-8 MUST be supported.", e);
    }
  }

  /**
   * Encapsulate given content in an {@link InputStream} with given charset.
   * 
   * @param content to encapsulate content
   * @param charset to be used charset
   * @return content as stream
   * @throws UnsupportedEncodingException if charset is not supported
   */
  public static InputStream encapsulate(final String content, final String charset)
      throws UnsupportedEncodingException {
    return new ByteArrayInputStream(content.getBytes(charset));
  }

  /**
   * Generate a string with given length containing random upper case characters ([A-Z]).
   * 
   * @param len length of to generated string
   * @return random upper case characters ([A-Z]).
   */
  public static InputStream generateDataStream(final int len) {
    return encapsulate(generateData(len));
  }

  /**
   * Generates a string with given length containing random upper case characters ([A-Z]).
   * @param len length of the generated string
   * @return random upper case characters ([A-Z])
   */
  public static String generateData(final int len) {
    Random random = new Random();
    StringBuilder b = new StringBuilder(len);
    for (int j = 0; j < len; j++) {
      final char c = (char) ('A' + random.nextInt('Z' - 'A' + 1));
      b.append(c);
    }
    return b.toString();
  }

}
