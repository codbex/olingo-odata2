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

import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.core.rest.app.AbstractODataApplication;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataApplication_.
 */
public class ODataApplication_ extends AbstractODataApplication {

  /**
   * Gets the service factory class.
   *
   * @return the service factory class
   */
  @Override
  public Class<? extends ODataServiceFactory> getServiceFactoryClass() {
    return ODataApplicationFactory.class;
  }

}
