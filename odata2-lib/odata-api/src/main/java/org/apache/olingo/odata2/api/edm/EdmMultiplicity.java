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
package org.apache.olingo.odata2.api.edm;

// TODO: Auto-generated Javadoc
/**
 * The Enum EdmMultiplicity.
 *
 * @org.apache.olingo.odata2.DoNotImplement <p>EdmMultiplicity indicates the number of entity type instances
 * an association end can relate to:
 * <dl>
 * <dt>0..1</dt><dd>one or none</dd>
 * <dt> 1</dt><dd>exactly one</dd>
 * <dt> *</dt><dd>many</dd>
 * </dl></p>
 */
public enum EdmMultiplicity {

  /** The zero to one. */
  ZERO_TO_ONE("0..1"), /** The many. */
 MANY("*"), /** The one. */
 ONE("1");

  /** The literal. */
  private final String literal;

  /**
   * Instantiates a new edm multiplicity.
   *
   * @param literal the literal
   */
  private EdmMultiplicity(final String literal) {
    this.literal = literal;
  }

  /**
   * Gets the multiplicity for a given name.
   *
   * @param literal the literal
   * @return {@link EdmMultiplicity}
   */
  public static EdmMultiplicity fromLiteral(final String literal) {
    for (final EdmMultiplicity edmMultiplicity : EdmMultiplicity.values()) {
      if (edmMultiplicity.toString().equals(literal)) {
        return edmMultiplicity;
      }
    }
    throw new IllegalArgumentException("Invalid literal " + literal);
  }

  /**
   * Returns the OData literal form of this multiplicity.
   * @return the OData literal form of this multiplicity
   */
  @Override
  public String toString() {
    return literal;
  }
}
