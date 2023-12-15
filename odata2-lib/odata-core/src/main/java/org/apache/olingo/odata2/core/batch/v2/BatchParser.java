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

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.olingo.odata2.api.batch.BatchException;
import org.apache.olingo.odata2.api.batch.BatchParserResult;
import org.apache.olingo.odata2.api.batch.BatchRequestPart;
import org.apache.olingo.odata2.api.client.batch.BatchSingleResponse;
import org.apache.olingo.odata2.api.ep.EntityProviderBatchProperties;
import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchParser.
 */
public class BatchParser {

  /** The batch request path info. */
  private final PathInfo batchRequestPathInfo;
  
  /** The content type mime. */
  private final String contentTypeMime;
  
  /** The is strict. */
  private final boolean isStrict;

  /**
   * Instantiates a new batch parser.
   *
   * @param contentType the content type
   * @param isStrict the is strict
   */
  public BatchParser(final String contentType, final boolean isStrict) {
    this(contentType, null, isStrict);
  }

  /**
   * Instantiates a new batch parser.
   *
   * @param contentType the content type
   * @param properties the properties
   * @param isStrict the is strict
   */
  public BatchParser(final String contentType, final EntityProviderBatchProperties properties, final boolean isStrict) {
    contentTypeMime = contentType;
    batchRequestPathInfo = (properties != null) ? properties.getPathInfo() : null;
    this.isStrict = isStrict;
  }

  /**
   * Parses the batch response.
   *
   * @param in the in
   * @return the list
   * @throws BatchException the batch exception
   */
  @SuppressWarnings("unchecked")
  public List<BatchSingleResponse> parseBatchResponse(final InputStream in) throws BatchException {
    return (List<BatchSingleResponse>) parse(in, new BatchResponseTransformator());
  }

  /**
   * Parses the batch request.
   *
   * @param in the in
   * @return the list
   * @throws BatchException the batch exception
   */
  @SuppressWarnings("unchecked")
  public List<BatchRequestPart> parseBatchRequest(final InputStream in) throws BatchException {
    return (List<BatchRequestPart>) parse(in, new BatchRequestTransformator());
  }

  /**
   * Parses the.
   *
   * @param in the in
   * @param transformator the transformator
   * @return the list<? extends batch parser result>
   * @throws BatchException the batch exception
   */
  private List<? extends BatchParserResult> parse(final InputStream in, final BatchTransformator transformator)
      throws BatchException {
    try {
      return parseBatch(in, transformator);
    } catch (IOException e) {
      throw new ODataRuntimeException(e);
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        throw new ODataRuntimeException(e);
      }
    }
  }

  /**
   * Parses the batch.
   *
   * @param in the in
   * @param transformator the transformator
   * @return the list
   * @throws BatchException the batch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private List<BatchParserResult> parseBatch(final InputStream in,
      final BatchTransformator transformator) throws BatchException, IOException {

    final String baseUri = getBaseUri();
    final String boundary = BatchParserCommon.getBoundary(contentTypeMime, 1);
    final List<BatchParserResult> resultList = new LinkedList<BatchParserResult>();
    final List<List<Line>> bodyPartStrings = splitBodyParts(in, boundary);

    for (List<Line> bodyPartString : bodyPartStrings) {
      BatchBodyPart bodyPart = new BatchBodyPart(bodyPartString, boundary, isStrict).parse();
      resultList.addAll(transformator.transform(bodyPart, batchRequestPathInfo, baseUri));
    }

    return resultList;
  }
  
  /**
   * Split body parts.
   *
   * @param in the in
   * @param boundary the boundary
   * @return the list
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws BatchException the batch exception
   */
  private List<List<Line>> splitBodyParts(final InputStream in, final String boundary)
      throws IOException, BatchException {

    final BatchLineReader reader = new BatchLineReader(in);
    final List<Line> message = reader.toLineList();
    reader.close();

    return BatchParserCommon.splitMessageByBoundary(message, boundary);
  }


  /**
   * Gets the base uri.
   *
   * @return the base uri
   * @throws BatchException the batch exception
   */
  private String getBaseUri() throws BatchException {
    String baseUri = "";

    //The service root already contains any additional path parameters
    if (batchRequestPathInfo != null && batchRequestPathInfo.getServiceRoot() != null) {
      final String uri = batchRequestPathInfo.getServiceRoot().toASCIIString();
      baseUri = removeLastSlash(uri);
    }

    return baseUri;
  }

  /**
   * Removes the last slash.
   *
   * @param baseUri the base uri
   * @return the string
   */
  private String removeLastSlash(String baseUri) {
    if (baseUri.lastIndexOf('/') == baseUri.length() - 1) {
      baseUri = baseUri.substring(0, baseUri.length() - 1);
    }

    return baseUri;
  }
}
