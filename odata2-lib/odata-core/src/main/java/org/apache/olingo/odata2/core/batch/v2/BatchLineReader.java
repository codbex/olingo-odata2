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

import org.apache.olingo.odata2.core.commons.ContentType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchLineReader.
 */
public class BatchLineReader {
  
  /** The Constant CR. */
  private static final byte CR = '\r';
  
  /** The Constant LF. */
  private static final byte LF = '\n';
  
  /** The Constant EOF. */
  private static final int EOF = -1;
  
  /** The Constant BUFFER_SIZE. */
  private static final int BUFFER_SIZE = 8192;
  
  /** The Constant DEFAULT_CHARSET. */
  private static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
  
  /** The Constant UTF8_CHARSET. */
  private static final String UTF8_CHARSET = "UTF-8";
  
  /** The Constant CONTENT_TYPE. */
  private static final String CONTENT_TYPE = "content-type";
  
  /** The Constant XML_SUBTYPE. */
  private static final String XML_SUBTYPE = "xml";
  
  /** The Constant BOUNDARY. */
  public static final String BOUNDARY = "boundary";
  
  /** The Constant DOUBLE_DASH. */
  public static final String DOUBLE_DASH = "--";
  
  /** The Constant CRLF. */
  public static final String CRLF = "\r\n";
  
  /** The Constant LFS. */
  public static final String LFS = "\n";
  
  /** The current charset. */
  private Charset currentCharset = DEFAULT_CHARSET;
  
  /** The current boundary. */
  private String currentBoundary = null;
  
  /** The read state. */
  private ReadState readState = new ReadState();
  
  /** The reader. */
  private InputStream reader;
  
  /** The buffer. */
  private byte[] buffer;
  
  /** The offset. */
  private int offset = 0;
  
  /** The limit. */
  private int limit = 0;

  /**
   * Instantiates a new batch line reader.
   *
   * @param reader the reader
   */
  public BatchLineReader(final InputStream reader) {
    this(reader, BUFFER_SIZE);
  }

  /**
   * Instantiates a new batch line reader.
   *
   * @param reader the reader
   * @param bufferSize the buffer size
   */
  public BatchLineReader(final InputStream reader, final int bufferSize) {
    if (bufferSize <= 0) {
      throw new IllegalArgumentException("Buffer size must be greater than zero.");
    }

    this.reader = reader;
    buffer = new byte[bufferSize];
  }

  /**
   * Close.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void close() throws IOException {
    reader.close();
  }

  /**
   * To list.
   *
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public List<String> toList() throws IOException {
    final List<String> result = new ArrayList<String>();
    String currentLine = readLine();
    if(currentLine != null) {
      currentBoundary = currentLine.trim();
      result.add(currentLine);

      while ((currentLine = readLine()) != null) {
        result.add(currentLine);
      }
    }
    return result;
  }

  /**
   * To line list.
   *
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public List<Line> toLineList() throws IOException {
    final List<Line> result = new ArrayList<Line>();
    String currentLine = readLine();
    if(currentLine != null) {
      currentBoundary = currentLine.trim();
      int counter = 1;
      result.add(new Line(currentLine, counter++));

      while ((currentLine = readLine()) != null) {
        result.add(new Line(currentLine, counter++));
      }
    }

    return result;
  }

  /**
   * Update current charset.
   *
   * @param currentLine the current line
   */
  private void updateCurrentCharset(String currentLine) {
    if(currentLine != null) {
      if(isContentTypeHeaderLine(currentLine)) {
        int cutOff = currentLine.endsWith(CRLF) ? 2 : currentLine.endsWith(LFS) ? 1 : 0;
        currentLine = currentLine.substring(13, currentLine.length() - cutOff).trim();
        ContentType ct = ContentType.parse(currentLine);
        if (ct != null) {
          String charsetString = ct.getParameters().get(ContentType.PARAMETER_CHARSET);
          if (charsetString != null) {
            currentCharset = Charset.forName(charsetString);
          } else {
            if (ct.isCompatible(ContentType.APPLICATION_JSON) || ct.getSubtype().contains(XML_SUBTYPE)) {
              currentCharset = Charset.forName(UTF8_CHARSET);
            } else {
              currentCharset = DEFAULT_CHARSET;
            }
          }
          // boundary
          String boundary = ct.getParameters().get(BOUNDARY);
          if (boundary != null) {
            currentBoundary = DOUBLE_DASH + boundary;
          }
        }
      } else if(CRLF.equals(currentLine) || LFS.equals(currentLine)) {
        readState.foundLinebreak();
      } else if(isBoundary(currentLine)) {
        readState.foundBoundary();
      }
    }
  }

  /**
   * Checks if is content type header line.
   *
   * @param currentLine the current line
   * @return true, if is content type header line
   */
  private boolean isContentTypeHeaderLine(String currentLine) {
    return currentLine.toLowerCase(Locale.ENGLISH).startsWith(CONTENT_TYPE);
  }

  /**
   * Checks if is boundary.
   *
   * @param currentLine the current line
   * @return true, if is boundary
   */
  private boolean isBoundary(String currentLine) {
    if((currentBoundary + CRLF).equals(currentLine) || (currentBoundary + LFS).equals(currentLine)) {
      return true;
    } else if((currentBoundary + DOUBLE_DASH + CRLF).equals(currentLine) 
        || (currentBoundary + DOUBLE_DASH + LFS).equals(currentLine)) {
      return true;
    }
    return false;
  }

  /**
   * Read line.
   *
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  String readLine() throws IOException {
    if (limit == EOF) {
      return null;
    }

    ByteBuffer buf = ByteBuffer.allocate(BUFFER_SIZE);
    boolean foundLineEnd = false; // EOF will be considered as line ending

    while (!foundLineEnd) {
      // Is buffer refill required?
      if (limit == offset) {
        if (fillBuffer() == EOF) {
          foundLineEnd = true;
        }
      }

      if (!foundLineEnd) {
        byte currentChar = this.buffer[offset++];
        buf = grantBuffer(buf);
        buf.put(currentChar);

        if (currentChar == LF) {
          foundLineEnd = true;
        } else if (currentChar == CR) {
          foundLineEnd = true;

          // Check next byte. Consume \n if available
          // Is buffer refill required?
          if (limit == offset) {
            fillBuffer();
          }

          // Check if there is at least one character
          if (limit != EOF && this.buffer[offset] == LF) {
            buf = grantBuffer(buf);
            buf.put(LF);
            offset++;
          }
        }
      }
    }

    if(buf.position() == 0) {
      return null;
    } else {
      String currentLine;
      if(readState.isReadBody()) {
        currentLine = new String(buf.array(), 0, buf.position(), getCurrentCharset());
      } else {
        currentLine = new String(buf.array(), 0, buf.position(), DEFAULT_CHARSET);
      }
      updateCurrentCharset(currentLine);
      return currentLine;
    }
  }

  /**
   * Grant buffer.
   *
   * @param buffer the buffer
   * @return the byte buffer
   */
  private ByteBuffer grantBuffer(ByteBuffer buffer) {
    if(!buffer.hasRemaining()) {
      buffer.flip();
      ByteBuffer tmp = ByteBuffer.allocate(buffer.limit() *2);
      tmp.put(buffer);
      buffer = tmp;
    }
    return buffer;
  }

  /**
   * Fill buffer.
   *
   * @return the int
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private int fillBuffer() throws IOException {
    limit = reader.read(buffer, 0, buffer.length);
    offset = 0;

    return limit;
  }

  /**
   * Gets the current charset.
   *
   * @return the current charset
   */
  private Charset getCurrentCharset() {
    return currentCharset;
  }

  /**
   * Read state indicator (whether currently the <code>body</code> or <code>header</code> part is read).
   */
  private class ReadState {
    
    /** The state. */
    private int state = 0;

    /**
     * Found linebreak.
     */
    public void foundLinebreak() {
      state++;
    }
    
    /**
     * Found boundary.
     */
    public void foundBoundary() {
      state = 0;
    }
    
    /**
     * Checks if is read body.
     *
     * @return true, if is read body
     */
    public boolean isReadBody() {
      return state >= 2;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
      return String.valueOf(state);
    }
  }
}
