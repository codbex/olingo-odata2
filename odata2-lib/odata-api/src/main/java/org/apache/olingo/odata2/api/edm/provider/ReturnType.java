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

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;

// TODO: Auto-generated Javadoc
/**
 * Objects of this Class represent a return type of a function import.
 */
public class ReturnType {

  /** The type name. */
  private FullQualifiedName typeName;
  
  /** The multiplicity. */
  private EdmMultiplicity multiplicity;

  /**
   * Gets the type name.
   *
   * @return {@link FullQualifiedName} type of this {@link ReturnType}
   */
  public FullQualifiedName getTypeName() {
    return typeName;
  }

  /**
   * Gets the multiplicity.
   *
   * @return {@link EdmMultiplicity} of this {@link ReturnType}
   */
  public EdmMultiplicity getMultiplicity() {
    return multiplicity;
  }

  /**
   * Sets the type of this {@link ReturnType} via the types {@link FullQualifiedName}.
   *
   * @param qualifiedName the qualified name
   * @return {@link ReturnType} for method chaining
   */
  public ReturnType setTypeName(final FullQualifiedName qualifiedName) {
    typeName = qualifiedName;
    return this;
  }

  /**
   * Sets the {@link EdmMultiplicity} of this {@link ReturnType}.
   *
   * @param multiplicity the multiplicity
   * @return {@link ReturnType} for method chaining
   */
  public ReturnType setMultiplicity(final EdmMultiplicity multiplicity) {
    this.multiplicity = multiplicity;
    return this;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    if (EdmMultiplicity.MANY == multiplicity) {
      return "Collection(" + typeName + ")";
    } else {
      return typeName.toString();
    }
  }

}
