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
package org.apache.olingo.odata2.core.servicedocument;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmEntitySetInfo;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.servicedocument.AtomInfo;
import org.apache.olingo.odata2.api.servicedocument.ServiceDocument;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceDocumentImpl.
 */
public class ServiceDocumentImpl implements ServiceDocument {
  
  /** The atom info. */
  private AtomInfo atomInfo;
  
  /** The entity sets. */
  private List<EdmEntitySetInfo> entitySets = new ArrayList<EdmEntitySetInfo>();

  /**
   * Sets the entity sets info.
   *
   * @param entitySets the entity sets
   * @return the service document impl
   */
  public ServiceDocumentImpl setEntitySetsInfo(final List<EdmEntitySetInfo> entitySets) {
    this.entitySets = entitySets;
    return this;
  }

  /**
   * Gets the entity sets info.
   *
   * @return the entity sets info
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public List<EdmEntitySetInfo> getEntitySetsInfo() throws EntityProviderException {
    return entitySets;
  }

  /**
   * Gets the atom info.
   *
   * @return the atom info
   */
  @Override
  public AtomInfo getAtomInfo() {
    return atomInfo;
  }

  /**
   * Sets the atom info.
   *
   * @param atomInfo the new atom info
   */
  public void setAtomInfo(final AtomInfo atomInfo) {
    this.atomInfo = atomInfo;
  }

}
