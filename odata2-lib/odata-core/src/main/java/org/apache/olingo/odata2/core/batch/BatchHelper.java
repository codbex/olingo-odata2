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

import org.apache.olingo.odata2.api.client.batch.BatchChangeSetPart;
import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchHelper.
 */
public class BatchHelper {

  /** The Constant BINARY_ENCODING. */
  public static final String BINARY_ENCODING = "binary";
  
  /** The Constant UTF8_ENCODING. */
  public static final String UTF8_ENCODING = "UTF-8";
  
  /** The Constant ISO_ENCODING. */
  public static final String ISO_ENCODING = "ISO-8859-1";
  
  /** The default encoding. */
  private static String DEFAULT_ENCODING = "ISO-8859-1";
  
  /** The Constant HTTP_CONTENT_TRANSFER_ENCODING. */
  public static final String HTTP_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
  
  /** The Constant HTTP_CONTENT_ID. */
  public static final String HTTP_CONTENT_ID = "Content-Id";
  
  /** The Constant MIME_HEADER_CONTENT_ID. */
  public static final String MIME_HEADER_CONTENT_ID = "MimeHeader-ContentId";
  
  /** The Constant REQUEST_HEADER_CONTENT_ID. */
  public static final String REQUEST_HEADER_CONTENT_ID = "RequestHeader-ContentId";

  /** The default charset. */
  protected static Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_ENCODING);

  /**
   * Generate boundary.
   *
   * @param value the value
   * @return the string
   */
  protected static String generateBoundary(final String value) {
    return value + "_" + UUID.randomUUID().toString();
  }

  /**
   * Gets the bytes.
   *
   * @param body the body
   * @return the bytes
   */
  protected static byte[] getBytes(final String body) {
    try {
      return body.getBytes(DEFAULT_ENCODING);
    } catch (UnsupportedEncodingException e) {
      throw new ODataRuntimeException(e);
    }
  }

  /**
   * Extract charset.
   *
   * @param headers the headers
   * @return the charset
   */
  public static Charset extractCharset(Map<String, String> headers) {
    String contentType = null;
    for (Map.Entry<String, String> s : headers.entrySet()) {
      if(s.getKey().equalsIgnoreCase(HttpHeaders.CONTENT_TYPE)) {
        contentType = s.getValue();
        break;
      }
    }

    return getCharset(contentType);
  }

  /**
   * Extract charset.
   *
   * @param contentType the content type
   * @return the charset
   */
  public static Charset extractCharset(ContentType contentType) {
    if (contentType != null) {
      final String charsetValue = contentType.getParameters().get(ContentType.PARAMETER_CHARSET);
      if (charsetValue == null) {
        if (contentType.isCompatible(ContentType.APPLICATION_JSON) || contentType.getSubtype().contains("xml")) {
          setDefaultValues(UTF8_ENCODING);
          return Charset.forName(UTF8_ENCODING);
        }
      } else {
        setDefaultValues(charsetValue);
        return Charset.forName(charsetValue);
      }
    }
    setDefaultValues(ISO_ENCODING);
    return Charset.forName(ISO_ENCODING);
  }

  /**
   * Gets the charset.
   *
   * @param contentType the content type
   * @return the charset
   */
  private static Charset getCharset(String contentType) {
    ContentType ct = ContentType.parse(contentType);
    if(ct != null) {
      String charsetString = ct.getParameters().get(ContentType.PARAMETER_CHARSET);
      if (charsetString != null && Charset.isSupported(charsetString)) {
        setDefaultValues(charsetString);
        return Charset.forName(charsetString);
      } else {
        if (ct.isCompatible(ContentType.APPLICATION_JSON) || ct.getSubtype().contains("xml")) {
          setDefaultValues(UTF8_ENCODING);
          return Charset.forName(UTF8_ENCODING);
        }
      }
    }
    setDefaultValues(ISO_ENCODING);
    return Charset.forName(ISO_ENCODING);
  }
  
  /**
   * Sets the default values.
   *
   * @param contentType the new default values
   */
  private static void setDefaultValues(String contentType) {
    DEFAULT_CHARSET = Charset.forName(contentType);
    DEFAULT_ENCODING = contentType;
    
  }

  /**
   * Builder class to create the body and the header.
   */
  static class BodyBuilder {
    
    /** The Constant DEFAULT_SIZE. */
    public static final int DEFAULT_SIZE = 8192;
    
    /** The charset iso 8859 1. */
    private final Charset CHARSET_ISO_8859_1 = Charset.forName("iso-8859-1");
    
    /** The buffer. */
    private ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_SIZE);
    
    /** The is closed. */
    private boolean isClosed = false;

    /**
     * Gets the content.
     *
     * @return the content
     */
    public byte[] getContent() {
      isClosed = true;
      byte[] tmp = new byte[buffer.position()];
      buffer.flip();
      buffer.get(tmp, 0, buffer.limit());
      return tmp;
    }

    /**
     * Gets the content as stream.
     *
     * @return the content as stream
     */
    public InputStream getContentAsStream() {
      return new ByteArrayInputStream(getContent());
    }

    /**
     * Gets the content as string.
     *
     * @param charset the charset
     * @return the content as string
     */
    public String getContentAsString(Charset charset) {
      return new String(getContent(), charset);
    }

    /**
     * Gets the length.
     *
     * @return the length
     */
    public int getLength() {
      return (buffer.limit() > buffer.position() ? buffer.limit(): buffer.position());
    }

    /**
     * Append.
     *
     * @param string the string
     * @return the body builder
     */
    public BodyBuilder append(String string) {
      byte [] b = string.getBytes(DEFAULT_CHARSET);
      put(b);
      return this;
    }

    /**
     * Put.
     *
     * @param b the b
     */
    private void put(byte[] b) {
      if(isClosed) {
        throw new RuntimeException("BodyBuilder is closed.");
      }
      if(buffer.remaining() < b.length) {
        buffer.flip();
        int newSize = (buffer.limit() * 2) + b.length;
        ByteBuffer tmp = ByteBuffer.allocate(newSize);
        tmp.put(buffer);
        buffer = tmp;
      }
      buffer.put(b);
    }

    /**
     * Append.
     *
     * @param statusCode the status code
     * @return the body builder
     */
    public BodyBuilder append(int statusCode) {
      return append(String.valueOf(statusCode));
    }

    /**
     * Append.
     *
     * @param body the body
     * @return the body builder
     */
    public BodyBuilder append(Body body) {
      put(body.getContent());
      return this;
    }

    /**
     * To string.
     *
     * @return the string
     */
    public String toString() {
      return new String(buffer.array(), 0, buffer.position());
    }
    
    /**
     * Fetch the calibrated length in case of binary data. 
     * Since after applying the charset the content length changes.
     * If the previously generated length is sent back then the batch response 
     * body is seen truncated
     *
     * @param batchResponseBody the batch response body
     * @return the int
     */
    public int calculateLength(Object batchResponseBody) {
      if (batchResponseBody != null) {
        if (batchResponseBody instanceof String) {
          if (DEFAULT_ENCODING.equalsIgnoreCase(ISO_ENCODING)) {
            try {
              return ((String) batchResponseBody).getBytes(UTF8_ENCODING).length;
            } catch (UnsupportedEncodingException e) {
              throw new ODataRuntimeException(e);
            }
          } else {
            return getLength();
          }
        } else {
          return getLength();
        }
      }
      return getLength();
    }
  }

  /**
   * Body part which is read and stored as bytes (no charset conversion).
   */
  static class Body {
    
    /** The Constant BUFFER_SIZE. */
    private static final int BUFFER_SIZE = 8192;
    
    /** The Constant EMPTY_BYTES. */
    public static final byte[] EMPTY_BYTES = new byte[0];
    
    /** The content. */
    private final byte[] content;

    /**
     * Instantiates a new body.
     *
     * @param response the response
     */
    public Body(BatchChangeSetPart response) {
      this.content = getBody(response);
    }

    /**
     * Instantiates a new body.
     *
     * @param response the response
     */
    public Body(ODataResponse response) {
      this.content = getBody(response);
    }

    /**
     * Instantiates a new body.
     */
    public Body() {
      this.content = EMPTY_BYTES;
      setDefaultValues(ISO_ENCODING);
    }

    /**
     * Gets the length.
     *
     * @return the length
     */
    public int getLength() {
      return content.length;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public byte[] getContent() {
      return content;
    }

    /**
     * Checks if is empty.
     *
     * @return true, if is empty
     */
    public boolean isEmpty() {
      return content.length == 0;
    }

    /**
     * Gets the body.
     *
     * @param response the response
     * @return the body
     */
    private byte[] getBody(final BatchChangeSetPart response) {
      if (response == null || response.getBodyAsBytes() == null) {
        return EMPTY_BYTES;
      }

      return response.getBodyAsBytes();
    }

    /**
     * Gets the body.
     *
     * @param response the response
     * @return the body
     */
    private byte[] getBody(final ODataResponse response) {
      if (response == null) {
        return EMPTY_BYTES;
      }
      Object entity = response.getEntity();
      if(entity == null) {
        return EMPTY_BYTES;
      } else if(entity instanceof InputStream) {
		  ReadableByteChannel ic = null;
		  WritableByteChannel oc = null;
        try {
          extractCharset(ContentType.parse(response.getHeader("Content-Type")));
          ByteArrayOutputStream output = new ByteArrayOutputStream();
          ByteBuffer inBuffer = ByteBuffer.allocate(BUFFER_SIZE);
          ic = Channels.newChannel((InputStream) entity);
          oc = Channels.newChannel(output);
          while (ic.read(inBuffer) > 0) {
            inBuffer.flip();
            oc.write(inBuffer);
            inBuffer.rewind();
          }
          return output.toByteArray();
        } catch (IOException e) {
          throw new ODataRuntimeException("Error on reading request content");
        } finally {
          try {
			  if (ic != null) {
				ic.close();  
			  }
          } catch (IOException e) {
            throw new ODataRuntimeException("Error closing the Readable Byte Channel", e);
          }
          try {
			  if (oc != null) {
				oc.close();  
			  }
          } catch (IOException e) {
            throw new ODataRuntimeException("Error closing the Writable Byte Channel", e);
          }
        }
      } else if (entity instanceof byte[]) {
        setDefaultValues(ISO_ENCODING);
        return (byte[]) entity;
      } else if(entity instanceof String) {
        setDefaultValues(UTF8_ENCODING);
        return ((String) entity).getBytes(DEFAULT_CHARSET);
      } else {
        throw new ODataRuntimeException("Error on reading request content for entity type:" + entity.getClass());
      }
    }
  }

}
