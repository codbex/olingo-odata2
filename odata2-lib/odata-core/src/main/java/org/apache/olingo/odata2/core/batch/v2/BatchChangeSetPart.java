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
 * The Class BatchChangeSetPart.
 */
public class BatchChangeSetPart extends BatchQueryOperation {
  
  /** The request. */
  private BatchQueryOperation request;

  /**
   * Instantiates a new batch change set part.
   *
   * @param message the message
   * @param isStrict the is strict
   * @throws BatchException the batch exception
   */
  public BatchChangeSetPart(final List<Line> message, final boolean isStrict) throws BatchException {
    super(message, isStrict);
  }

  /**
   * Parses the.
   *
   * @return the batch change set part
   * @throws BatchException the batch exception
   */
  @Override
  public BatchChangeSetPart parse() throws BatchException {
    headers = BatchParserCommon.consumeHeaders(message);
    BatchParserCommon.consumeBlankLine(message, isStrict);

    request = new BatchQueryOperation(message, isStrict).parse();

    return this;
  }

  /**
   * Gets the request.
   *
   * @return the request
   */
  public BatchQueryOperation getRequest() {
    return request;
  }

  /**
   * Gets the body.
   *
   * @return the body
   */
  @Override
  public List<Line> getBody() {
    return request.getBody();
  }

  /**
   * Gets the http status line.
   *
   * @return the http status line
   */
  @Override
  public Line getHttpStatusLine() {
    return request.getHttpStatusLine();
  }
}
