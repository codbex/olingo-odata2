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
package org.apache.olingo.odata2.testutil.mock;

import static org.junit.Assert.fail;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;

// TODO: Auto-generated Javadoc
/**
 * Helper for the entity data model used as technical reference scenario.
 * 
 */
public class TecEdmInfo {
  
  /** The edm. */
  private final Edm edm;

  /**
   * Instantiates a new tec edm info.
   *
   * @param edm the edm
   */
  public TecEdmInfo(final Edm edm) {
    this.edm = edm;
  }

  /**
   * Gets the type et all types.
   *
   * @return the type et all types
   */
  public EdmEntityType getTypeEtAllTypes() {
    return getEntityType(TechnicalScenarioEdmProvider.ET_ALL_TYPES);
  }

  /**
   * Gets the type et key type integer.
   *
   * @return the type et key type integer
   */
  public EdmEntityType getTypeEtKeyTypeInteger() {
    return getEntityType(TechnicalScenarioEdmProvider.ET_KEY_IS_INTEGER);
  }

  /**
   * Gets the type et key type string.
   *
   * @return the type et key type string
   */
  public EdmEntityType getTypeEtKeyTypeString() {
    return getEntityType(TechnicalScenarioEdmProvider.ET_KEY_IS_STRING);
  }

  /**
   * Gets the entity type.
   *
   * @param name the name
   * @return the entity type
   */
  private EdmEntityType getEntityType(final FullQualifiedName name) {
    try {
      return edm.getEntityType(name.getNamespace(), name.getName());
    } catch (final EdmException e) {
      fail("Error in test setup: " + e.getLocalizedMessage());
      return null;
    }
  }
}
