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
package org.apache.olingo.odata2.api.edm.provider;

import java.io.InputStream;

import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.rt.RuntimeDelegate;

// TODO: Auto-generated Javadoc
/**
 * EDM Provider Factory which can be used to create an edm provider (e.g. from a metadata document)
 * 
 * 
 */
public class EdmProviderFactory {

  /**
   * Instantiates a new edm provider factory.
   */
  private EdmProviderFactory() {
    
  }
  
  /**
   * Creates and returns an edm provider.
   *
   * @param metadataXml a metadata xml input stream (means the metadata document)
   * @param validate true if semantic checks for metadata document input stream shall be done
   * @return an instance of EdmProvider
   * @throws EntityProviderException the entity provider exception
   */
  public static EdmProvider getEdmProvider(final InputStream metadataXml, final boolean validate)
      throws EntityProviderException {
    return RuntimeDelegate.createEdmProvider(metadataXml, validate);
  }
}
