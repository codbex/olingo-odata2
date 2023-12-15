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

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;

// TODO: Auto-generated Javadoc
/**
 * This interface is called if an error occurred and is process inside the exception mapper.
 * 
 * 
 */
public interface ODataErrorCallback extends ODataCallback {
  
  /**
   * This method can be used to handle an error differently than the exception mapper would.
   * <br>Any returned Response will be directly transported to the client.
   * <br>Any thrown {@link ODataApplicationException} will be transformed into the OData error format.
   * <br>Any thrown runtime exception will result in an 500 Internal Server error with the Text:
   * "Exception during error handling occurred!" No OData formatting will be applied.
   * <br>To serialize an error into the OData format the {@link org.apache.olingo.odata2.api.ep.EntityProvider}
   * writeErrorDocument can be used.
   *
   * @param context of this error
   * @return the response which will be propagated to the client
   * @throws ODataApplicationException the o data application exception
   */
  ODataResponse handleError(ODataErrorContext context) throws ODataApplicationException;
}
