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
package org.apache.olingo.odata2.core.rest;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataServiceFactoryWithCallbackImpl.
 */
public class ODataServiceFactoryWithCallbackImpl extends ODataServiceFactory {

  /**
   * Instantiates a new o data service factory with callback impl.
   */
  public ODataServiceFactoryWithCallbackImpl() {
    super();
  }

  /**
   * Creates the service.
   *
   * @param ctx the ctx
   * @return the o data service
   * @throws ODataException the o data exception
   */
  @Override
  public ODataService createService(final ODataContext ctx) throws ODataException {
    return null;
  }

  /**
   * Gets the callback.
   *
   * @param <T> the generic type
   * @param callbackInterface the callback interface
   * @return the callback
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T extends ODataCallback> T getCallback(final Class<T> callbackInterface) {
    T callback = null;

    if (callbackInterface == ODataErrorCallback.class) {
      callback = (T) new ODataErrorHandlerCallbackImpl();
    }

    return callback;
  }

}
