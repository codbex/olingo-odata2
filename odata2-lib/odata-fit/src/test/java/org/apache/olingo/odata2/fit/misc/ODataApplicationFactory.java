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
package org.apache.olingo.odata2.fit.misc;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.ref.processor.ScenarioServiceFactory;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating ODataApplication objects.
 */
public class ODataApplicationFactory extends ODataServiceFactory {

  /**
   * Creates a new ODataApplication object.
   *
   * @param ctx the ctx
   * @return the o data service
   * @throws ODataException the o data exception
   */
  @Override
  public ODataService createService(final ODataContext ctx) throws ODataException {
    return new ScenarioServiceFactory().createService(ctx);
  }

  /**
   * Gets the callback.
   *
   * @param <T> the generic type
   * @param callbackInterface the callback interface
   * @return the callback
   */
  @Override
  public <T extends ODataCallback> T getCallback(final Class<T> callbackInterface) {
    return new ScenarioServiceFactory().getCallback(callbackInterface);
  }

}
