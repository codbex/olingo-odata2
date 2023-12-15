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
package org.apache.olingo.odata2.client.api.ep;

import java.io.InputStream;
import java.util.List;

import org.apache.olingo.odata2.api.client.batch.BatchPart;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.processor.ODataResponse;

// TODO: Auto-generated Javadoc
/**
 * Interface for all none basic (content type <b>dependent</b>) serializer methods.
 * 
 * 
 */
public interface ContentTypeBasedSerializer {

  /**
   * Returns an OData Response for a given entry.
   *
   * @param entitySet the entity set
   * @param data the data
   * @return ODataResponse
   * @throws EntityProviderException the entity provider exception
   */
  ODataResponse writeEntry(EdmEntitySet entitySet, Entity data)
      throws EntityProviderException;
  
  /**
   * Returns an OData Response for a given feed.
   *
   * @param entitySet the entity set
   * @param data the data
   * @return ODataResponse
   * @throws EntityProviderException the entity provider exception
   */
  ODataResponse writeFeed(EdmEntitySet entitySet, EntityCollection data)
      throws EntityProviderException;
  
  /**
   * Reads Batch Request Parts and returns an input Stream.
   *
   * @param batchParts the batch parts
   * @param boundary the boundary
   * @return InputStream
   */
  InputStream readBatchRequest(final List<BatchPart> batchParts, final String boundary);
}
