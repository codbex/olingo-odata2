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
package org.apache.olingo.odata2.api.batch;

import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.api.processor.ODataResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface BatchHandler.
 */
public interface BatchHandler {
  
  /**
   * <p>Handles the {@link BatchRequestPart} in a way that it results in a corresponding {@link BatchResponsePart}.</p>
   *
   * @param batchRequestPart the incoming MIME part
   * @return the corresponding result
   * @throws ODataException the o data exception
   */
  public BatchResponsePart handleBatchPart(BatchRequestPart batchRequestPart) throws ODataException;

  /**
   * <p>Delegates a handling of the request {@link ODataRequest} to the request handler and provides ODataResponse
   * {@link ODataResponse}.</p>
   *
   * @param request the incoming request
   * @return the corresponding result
   * @throws ODataException the o data exception
   */
  public ODataResponse handleRequest(ODataRequest request) throws ODataException;
}
