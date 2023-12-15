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

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.client.api.edm.EdmDataServices;

// TODO: Auto-generated Javadoc
/**
 * 
 * Interface for metadata deserializer methods.
 *
 */
public interface DeserializerMetadataProviderInterface {


  /**
   * Read (de-serialize) data from metadata <code>inputStream</code> (as {@link InputStream}) and provide Edm as
   * {@link Edm}.
   *
   * @param content the content
   * @param validate has to be true if metadata should be validated
   * @return Edm as {@link Edm}
   * @throws EntityProviderException the entity provider exception
   * @throws EdmException the edm exception
   */
  EdmDataServices readMetadata(InputStream content, boolean validate) 
      throws EntityProviderException, EdmException; //NOPMD  - suppressed

}
