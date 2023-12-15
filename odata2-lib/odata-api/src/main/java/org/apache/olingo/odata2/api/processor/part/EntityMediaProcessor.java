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
package org.apache.olingo.odata2.api.processor.part;

import java.io.InputStream;

import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataProcessor;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMediaResourceUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;

// TODO: Auto-generated Javadoc
/**
 * Execute an OData entity media request.
 */
public interface EntityMediaProcessor extends ODataProcessor {

  /**
   * Reads the media resource of an entity.
   *
   * @param uriInfo information about the request URI
   * @param contentType the content type of the response
   * @return an {@link ODataResponse} object
   * @throws ODataException the o data exception
   */
  ODataResponse readEntityMedia(GetMediaResourceUriInfo uriInfo, String contentType) throws ODataException;

  /**
   * Updates the media resource of an entity.
   *
   * @param uriInfo information about the request URI
   * @param content the content of the request
   * @param requestContentType the content type of the request body
   * @param contentType the content type of the response
   * @return an {@link ODataResponse} object
   * @throws ODataException the o data exception
   */
  ODataResponse updateEntityMedia(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType,
      String contentType) throws ODataException;

  /**
   * Deletes the media resource of an entity.
   *
   * @param uriInfo information about the request URI
   * @param contentType the content type of the response
   * @return an {@link ODataResponse} object
   * @throws ODataException the o data exception
   */
  ODataResponse deleteEntityMedia(DeleteUriInfo uriInfo, String contentType) throws ODataException;
}
