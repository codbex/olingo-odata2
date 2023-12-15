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
package org.apache.olingo.odata2.core.edm;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;

// TODO: Auto-generated Javadoc
/**
 * Implementation of the internal simple type Bit.
 * 
 */
public class Bit extends AbstractSimpleType {

  /** The Constant instance. */
  private static final Bit instance = new Bit();

  /**
   * Gets the single instance of Bit.
   *
   * @return single instance of Bit
   */
  public static Bit getInstance() {
    return instance;
  }

  /**
   * Gets the namespace.
   *
   * @return the namespace
   * @throws EdmException the edm exception
   */
  @Override
  public String getNamespace() throws EdmException {
    return SYSTEM_NAMESPACE;
  }

  /**
   * Gets the default type.
   *
   * @return the default type
   */
  @Override
  public Class<?> getDefaultType() {
    return Byte.class;
  }

  /**
   * Internal value of string.
   *
   * @param <T> the generic type
   * @param value the value
   * @param literalKind the literal kind
   * @param facets the facets
   * @param returnType the return type
   * @return the t
   * @throws EdmSimpleTypeException the edm simple type exception
   */
  @Override
  protected <T> T internalValueOfString(final String value, final EdmLiteralKind literalKind, final EdmFacets facets,
      final Class<T> returnType) throws EdmSimpleTypeException {
    return EdmSByte.getInstance().internalValueOfString(value, literalKind, facets, returnType);
  }

  /**
   * Internal value to string.
   *
   * @param <T> the generic type
   * @param value the value
   * @param literalKind the literal kind
   * @param facets the facets
   * @return the string
   * @throws EdmSimpleTypeException the edm simple type exception
   */
  @Override
  protected <T> String internalValueToString(final T value, final EdmLiteralKind literalKind, final EdmFacets facets)
      throws EdmSimpleTypeException {
    return EdmSByte.getInstance().internalValueToString(value, literalKind, facets);
  }
}
