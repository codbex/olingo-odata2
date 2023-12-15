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
package org.apache.olingo.odata2.api.servicedocument;

import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmEntitySetInfo;
import org.apache.olingo.odata2.api.ep.EntityProviderException;

// TODO: Auto-generated Javadoc
/**
 * A Service document
 * <p>Service document lists all EntitySets.
 */
public interface ServiceDocument {
  
  /**
   * Get the list of the EntitySets.
   *
   * @return a list of {@link EdmEntitySetInfo}
   * @throws EntityProviderException the entity provider exception
   */
  public List<EdmEntitySetInfo> getEntitySetsInfo() throws EntityProviderException;

  /**
   * Get additional information if the service document is in atom format.
   *
   * @return {@link AtomInfo} or null
   */
  public AtomInfo getAtomInfo();
}
