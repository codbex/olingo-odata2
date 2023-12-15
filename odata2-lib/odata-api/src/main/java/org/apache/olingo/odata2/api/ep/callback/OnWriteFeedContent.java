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
package org.apache.olingo.odata2.api.ep.callback;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;

// TODO: Auto-generated Javadoc
/**
 * Callback interface for the $expand query option.
 * <p>If an expand clause for a navigation property which points to a feed is found this callback will be called.
 * <br>Pointing to an feed means the navigation property has a multiplicity of 0..* or 1..*.
 * 
 * 
 * 
 */
public interface OnWriteFeedContent extends ODataCallback {

  /**
   * Retrieves the data for a feed. See {@link WriteFeedCallbackContext} for details on the context and
   * {@link WriteFeedCallbackResult} for details on the result of this method.
   *
   * @param context of this entry
   * @return result - must not be null.
   * @throws ODataApplicationException the o data application exception
   */
  WriteFeedCallbackResult retrieveFeedResult(WriteFeedCallbackContext context) throws ODataApplicationException;

}
