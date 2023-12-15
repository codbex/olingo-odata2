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
package org.apache.olingo.odata2.core.edm.provider;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.provider.ComplexProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmComplexPropertyImplProv.
 */
public class EdmComplexPropertyImplProv extends EdmPropertyImplProv {

  /** The property. */
  private ComplexProperty property;

  /**
   * Instantiates a new edm complex property impl prov.
   *
   * @param edm the edm
   * @param property the property
   * @throws EdmException the edm exception
   */
  public EdmComplexPropertyImplProv(final EdmImplProv edm, final ComplexProperty property) throws EdmException {
    super(edm, property.getType(), property);
    this.property = property;
  }

  /**
   * Gets the type.
   *
   * @return the type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmType getType() throws EdmException {
    if (edmType == null) {
      edmType = edm.getComplexType(property.getType().getNamespace(), property.getType().getName());
    }
    if (edmType == null) {
      throw new EdmException(EdmException.PROVIDERPROBLEM);
    }
    return edmType;
  }

  /**
   * Checks if is simple.
   *
   * @return true, if is simple
   */
  @Override
  public boolean isSimple() {
    return false;
  }
}
