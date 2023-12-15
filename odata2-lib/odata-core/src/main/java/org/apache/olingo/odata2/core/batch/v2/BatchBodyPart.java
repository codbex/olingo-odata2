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

import java.util.LinkedList;
import java.util.List;

import org.apache.olingo.odata2.api.batch.BatchException;
import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.core.batch.v2.Header.HeaderField;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchBodyPart.
 */
public class BatchBodyPart implements BatchPart {
  
  /** The boundary. */
  final private String boundary;
  
  /** The is strict. */
  final private boolean isStrict;
  
  /** The remaining message. */
  final List<Line> remainingMessage = new LinkedList<Line>();

  /** The headers. */
  private Header headers;
  
  /** The is change set. */
  private boolean isChangeSet;
  
  /** The requests. */
  private List<BatchQueryOperation> requests;

  /**
   * Instantiates a new batch body part.
   *
   * @param bodyPartMessage the body part message
   * @param boundary the boundary
   * @param isStrict the is strict
   * @throws BatchException the batch exception
   */
  public BatchBodyPart(final List<Line> bodyPartMessage, final String boundary, final boolean isStrict)
      throws BatchException {
    this.boundary = boundary;
    this.isStrict = isStrict;

    remainingMessage.addAll(bodyPartMessage);
  }

  /**
   * Parses the.
   *
   * @return the batch body part
   * @throws BatchException the batch exception
   */
  public BatchBodyPart parse() throws BatchException {
    headers = BatchParserCommon.consumeHeaders(remainingMessage);
    BatchParserCommon.consumeBlankLine(remainingMessage, isStrict);
    isChangeSet = isChangeSet(headers);
    requests = consumeRequest(remainingMessage);

    return this;
  }

  /**
   * Checks if is change set.
   *
   * @param headers the headers
   * @return true, if is change set
   * @throws BatchException the batch exception
   */
  private boolean isChangeSet(final Header headers) throws BatchException {
    final List<String> contentTypes = headers.getHeaders(HttpHeaders.CONTENT_TYPE);
    boolean isChgSet = false;

    if (contentTypes.isEmpty()) {
      throw new BatchException(BatchException.MISSING_CONTENT_TYPE.addContent(headers.getLineNumber()));
    }

    for (String contentType : contentTypes) {
      if (isContentTypeMultiPartMixed(contentType)) {
        isChgSet = true;
      }
    }

    return isChgSet;
  }

  /**
   * Checks if is content type multi part mixed.
   *
   * @param contentType the content type
   * @return true, if is content type multi part mixed
   */
  private boolean isContentTypeMultiPartMixed(final String contentType) {
    return BatchParserCommon.PATTERN_MULTIPART_MIXED.matcher(contentType).matches();
  }

  /**
   * Consume request.
   *
   * @param remainingMessage the remaining message
   * @return the list
   * @throws BatchException the batch exception
   */
  private List<BatchQueryOperation> consumeRequest(final List<Line> remainingMessage) throws BatchException {
    if (isChangeSet) {
      return consumeChangeSet(remainingMessage);
    } else {
      return consumeQueryOperation(remainingMessage);
    }
  }

  /**
   * Consume change set.
   *
   * @param remainingMessage the remaining message
   * @return the list
   * @throws BatchException the batch exception
   */
  private List<BatchQueryOperation> consumeChangeSet(final List<Line> remainingMessage)
      throws BatchException {
    final List<List<Line>> changeRequests = splitChangeSet(remainingMessage);
    final List<BatchQueryOperation> requestList = new LinkedList<BatchQueryOperation>();

    for (List<Line> changeRequest : changeRequests) {
      requestList.add(new BatchChangeSetPart(changeRequest, isStrict).parse());
    }

    return requestList;
  }

  /**
   * Split change set.
   *
   * @param remainingMessage the remaining message
   * @return the list
   * @throws BatchException the batch exception
   */
  private List<List<Line>> splitChangeSet(final List<Line> remainingMessage)
      throws BatchException {

    final HeaderField contentTypeField = headers.getHeaderField(HttpHeaders.CONTENT_TYPE);
    final String changeSetBoundary =
        BatchParserCommon.getBoundary(contentTypeField.getValueNotNull(), contentTypeField.getLineNumber());
    validateChangeSetBoundary(changeSetBoundary, headers);

    return BatchParserCommon.splitMessageByBoundary(remainingMessage, changeSetBoundary);
  }

  /**
   * Consume query operation.
   *
   * @param remainingMessage the remaining message
   * @return the list
   * @throws BatchException the batch exception
   */
  private List<BatchQueryOperation> consumeQueryOperation(final List<Line> remainingMessage)
      throws BatchException {
    final List<BatchQueryOperation> requestList = new LinkedList<BatchQueryOperation>();
    requestList.add(new BatchQueryOperation(remainingMessage, isStrict).parse());

    return requestList;
  }

  /**
   * Validate change set boundary.
   *
   * @param changeSetBoundary the change set boundary
   * @param header the header
   * @throws BatchException the batch exception
   */
  private void validateChangeSetBoundary(final String changeSetBoundary, final Header header) throws BatchException {
    if (changeSetBoundary.equals(boundary)) {
      throw new BatchException(BatchException.INVALID_BOUNDARY.addContent(header.getHeaderField(
          HttpHeaders.CONTENT_TYPE).getLineNumber()));
    }
  }

  /**
   * Gets the headers.
   *
   * @return the headers
   */
  @Override
  public Header getHeaders() {
    return headers;
  }

  /**
   * Checks if is strict.
   *
   * @return true, if is strict
   */
  @Override
  public boolean isStrict() {
    return isStrict;
  }

  /**
   * Checks if is change set.
   *
   * @return true, if is change set
   */
  public boolean isChangeSet() {
    return isChangeSet;
  }

  /**
   * Gets the requests.
   *
   * @return the requests
   */
  public List<BatchQueryOperation> getRequests() {
    return requests;
  }
}
