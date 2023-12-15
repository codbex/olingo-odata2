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
package org.apache.olingo.odata2.api.processor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.rt.RuntimeDelegate;

// TODO: Auto-generated Javadoc
/**
 * <p>An <code>ODataResponse</code> is usually created by an {@link ODataProcessor} during request handling.</p>
 * <p>The handler can use a serializer to create an
 * OData body (== response entity) and can set various response headers.
 * A response can be created using the builder pattern:
 * <pre> {@code
 * ODataResponse response = ODataResponse.entity("hello world").setStatus(HttpStatusCodes.OK).build();
 * } </pre>
 * 
 */
public abstract class ODataResponse {

  /** The Constant DEFAULT_CHARSET. */
  private static final Charset DEFAULT_CHARSET = Charset.forName("utf-8");
  
  /** The Constant CHARSET_MATCHER_PATTERN. */
  public static final Pattern CHARSET_MATCHER_PATTERN = Pattern.compile("(charset=[\\w-]*)", Pattern.CASE_INSENSITIVE);

  /**
   * Do not subclass ODataResponse!.
   */
  protected ODataResponse() {}

  /**
   * Gets the status.
   *
   * @return HTTP status code of this response
   */
  public abstract HttpStatusCodes getStatus();

  /**
   * Gets the entity.
   *
   * @return a response entity which becomes the body part of a response message
   */
  public abstract Object getEntity();

  /**
   * Gets the entity as stream.
   *
   * @return a response entity as inputStream which becomes the body part of a response message
   * @throws ODataException throws ODataException in case of entity is not a stream (internal ClassCastException)
   */
  public InputStream getEntityAsStream() throws ODataException {
    Object obj = getEntity();
    if(obj instanceof InputStream) {
      return (InputStream) obj;
    } else if(obj instanceof byte[]) {
      return new ByteArrayInputStream((byte[]) obj);
    } else if(obj instanceof String) {
      return getInputStream((String) obj);
    }
    throw new ODataException("Entity is not an instance of an InputStream (entity class: " +
        (obj == null ? "NULL": obj.getClass()) + ")");
  }

  /**
   * Gets the input stream.
   *
   * @param stringEntity the string entity
   * @return the input stream
   * @throws ODataException the o data exception
   */
  private InputStream getInputStream(String stringEntity) throws ODataException {
    try {
      String contentHeader = getContentHeader();
      Charset charset = DEFAULT_CHARSET;
      if(contentHeader != null) {
        Matcher matcher = CHARSET_MATCHER_PATTERN.matcher(contentHeader);
        if(matcher.find()) {
          charset = Charset.forName(matcher.group(0).split("=")[1]);
        }
      }
      return new ByteArrayInputStream(stringEntity.getBytes(charset));
    } catch (Exception e) {
      throw new ODataException("Unexpected exception for wrapping of String entity into InputStream.");
    }
  }

  /**
   * Close the underlying entity input stream (if such a stream is available) and release all with this repsonse
   * associated resources.
   * 
   * @throws IOException if something goes wrong during close of {@link ODataResponse}
   */
  public abstract void close() throws IOException;

  /**
   * Gets the header.
   *
   * @param name HTTP response header name
   * @return a header value or null if not set
   */
  public abstract String getHeader(String name);

  /**
   * Gets the content header.
   *
   * @return Content-Type header value or null if not set
   */
  public abstract String getContentHeader();

  /**
   * Gets the id literal.
   *
   * @return Location header value or null if not set
   */
  public abstract String getIdLiteral();

  /**
   * Gets the e tag.
   *
   * @return ETag header value or null if not available
   */
  public abstract String getETag();

  /**
   * Gets the header names.
   *
   * @return a set of all available header names
   */
  public abstract Set<String> getHeaderNames();

  /**
   * Case insensitive check if the header is available in this ODataResponse.
   *
   * @param header header name
   * @return true/false
   */
  public abstract boolean containsHeader(String header);

  /**
   * Status.
   *
   * @param status HTTP status code
   * @return a builder object
   */
  public static ODataResponseBuilder status(final HttpStatusCodes status) {
    return newBuilder().status(status);
  }

  /**
   * From response.
   *
   * @param response the response
   * @return a new builder object
   */
  public static ODataResponseBuilder fromResponse(final ODataResponse response) {
    return newBuilder().fromResponse(response);
  }

  /**
   * Entity.
   *
   * @param entity the entity
   * @return a builder object
   */
  public static ODataResponseBuilder entity(final Object entity) {
    return newBuilder().entity(entity);
  }

  /**
   * Header.
   *
   * @param name HTTP header name
   * @param value associated value
   * @return a builder object
   */
  public static ODataResponseBuilder header(final String name, final String value) {
    return newBuilder().header(name, value);
  }

  /**
   * Content header.
   *
   * @param value content header value
   * @return a builder object
   */
  public static ODataResponseBuilder contentHeader(final String value) {
    return newBuilder().contentHeader(value);
  }

  /**
   * New builder.
   *
   * @return returns a new builder object
   */
  public static ODataResponseBuilder newBuilder() {
    return ODataResponseBuilder.newInstance();
  }

  /**
   * Implementation of the builder pattern to create instances of this type of object.
   * 
   */
  public static abstract class ODataResponseBuilder {

    /**
     * Instantiates a new o data response builder.
     */
    protected ODataResponseBuilder() {}

    /**
     * New instance.
     *
     * @return the o data response builder
     */
    private static ODataResponseBuilder newInstance() {
      return RuntimeDelegate.createODataResponseBuilder();
    }

    /**
     * Builds the.
     *
     * @return the o data response
     */
    public abstract ODataResponse build();

    /**
     * Status.
     *
     * @param status the status
     * @return the o data response builder
     */
    public abstract ODataResponseBuilder status(HttpStatusCodes status);

    /**
     * Entity.
     *
     * @param entity the entity
     * @return the o data response builder
     */
    public abstract ODataResponseBuilder entity(Object entity);

    /**
     * Header.
     *
     * @param name the name
     * @param value the value
     * @return the o data response builder
     */
    public abstract ODataResponseBuilder header(String name, String value);

    /**
     * Id literal.
     *
     * @param idLiteral the id literal
     * @return the o data response builder
     */
    public abstract ODataResponseBuilder idLiteral(String idLiteral);

    /**
     * E tag.
     *
     * @param eTag the e tag
     * @return the o data response builder
     */
    public abstract ODataResponseBuilder eTag(String eTag);

    /**
     * Content header.
     *
     * @param contentHeader the content header
     * @return the o data response builder
     */
    public abstract ODataResponseBuilder contentHeader(String contentHeader);

    /**
     * From response.
     *
     * @param response the response
     * @return the o data response builder
     */
    protected abstract ODataResponseBuilder fromResponse(ODataResponse response);
  }
}
