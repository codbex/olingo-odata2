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
package org.apache.olingo.odata2.ref.processor;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataDebugCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.ref.edm.ScenarioEdmProvider;
import org.apache.olingo.odata2.ref.model.DataContainer;

// TODO: Auto-generated Javadoc
/**
 * Service Factory for the reference scenario.
 */
public class ScenarioServiceFactory extends ODataServiceFactory {

  /**
   * Creates a new ScenarioService object.
   *
   * @param context the context
   * @return the o data service
   * @throws ODataException the o data exception
   */
  @Override
  public ODataService createService(final ODataContext context) throws ODataException {
    DataContainer dataContainer = new DataContainer();
    dataContainer.reset();

    return createODataSingleProcessorService(
        new ScenarioEdmProvider(),
        new ListsProcessor(new ScenarioDataSource(dataContainer)));
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
    return (T) (callbackInterface.isAssignableFrom(ScenarioErrorCallback.class) ?
        new ScenarioErrorCallback() : callbackInterface.isAssignableFrom(ODataDebugCallback.class) ?
            new ScenarioDebugCallback() : super.getCallback(callbackInterface));
  }

  /**
   * The Class ScenarioDebugCallback.
   */
  private final class ScenarioDebugCallback implements ODataDebugCallback {
    
    /**
     * Checks if is debug enabled.
     *
     * @return true, if is debug enabled
     */
    @Override
    public boolean isDebugEnabled() {
      return true;
    }
  }
}
