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

import java.util.List;

import org.apache.olingo.odata2.api.batch.BatchException;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchQueryOperation.
 */
public class BatchQueryOperation implements BatchPart {

  /** The is strict. */
  protected final boolean isStrict;
  
  /** The http status line. */
  protected Line httpStatusLine;
  
  /** The headers. */
  protected Header headers;
  
  /** The body. */
  protected List<Line> body;
  
  /** The body size. */
  protected int bodySize;
  
  /** The message. */
  protected List<Line> message;

  /**
   * Instantiates a new batch query operation.
   *
   * @param message the message
   * @param isStrict the is strict
   */
  public BatchQueryOperation(final List<Line> message, final boolean isStrict) {
    this.isStrict = isStrict;
    this.message = message;
  }

  /**
   * Parses the.
   *
   * @return the batch query operation
   * @throws BatchException the batch exception
   */
  public BatchQueryOperation parse() throws BatchException {
    httpStatusLine = consumeHttpStatusLine(message);
    headers = BatchParserCommon.consumeHeaders(message);
    BatchParserCommon.consumeBlankLine(message, isStrict);
    body = message;

    return this;
  }

  /**
   * Consume http status line.
   *
   * @param message the message
   * @return the line
   * @throws BatchException the batch exception
   */
  protected Line consumeHttpStatusLine(final List<Line> message) throws BatchException {
    if (!message.isEmpty() && !"".equals(message.get(0).toString().trim())) {
      final Line method = message.get(0);
      message.remove(0);

      return method;
    } else {
      final int line = (!message.isEmpty()) ? message.get(0).getLineNumber() : 0;
      throw new BatchException(BatchException.MISSING_METHOD.addContent(line));
    }
  }

  /**
   * Gets the http status line.
   *
   * @return the http status line
   */
  public Line getHttpStatusLine() {
    return httpStatusLine;
  }

  /**
   * Gets the body.
   *
   * @return the body
   */
  public List<Line> getBody() {
    return body;
  }

  /**
   * Gets the body size.
   *
   * @return the body size
   */
  public int getBodySize() {
    return bodySize;
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
}
