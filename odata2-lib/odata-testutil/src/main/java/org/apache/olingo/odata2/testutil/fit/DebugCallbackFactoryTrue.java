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
package org.apache.olingo.odata2.testutil.fit;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataDebugCallback;

// TODO: Auto-generated Javadoc
/**
 * The Class DebugCallbackFactoryTrue.
 */
public class DebugCallbackFactoryTrue extends FitStaticServiceFactory {

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
    if (callbackInterface.isAssignableFrom(FitErrorCallback.class)) {
      return (T) new FitErrorCallback();
    } else if (callbackInterface.isAssignableFrom(ODataDebugCallback.class)) {
      return (T) new ODataDebugCallback() {
        @Override
        public boolean isDebugEnabled() {
          return true;
        }
      };
    }
    return null;
  }
}
