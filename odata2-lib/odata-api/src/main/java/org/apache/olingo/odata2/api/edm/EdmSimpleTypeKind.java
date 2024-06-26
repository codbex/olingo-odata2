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

import org.apache.olingo.odata2.api.rt.RuntimeDelegate;

// TODO: Auto-generated Javadoc
/**
 * The Enum EdmSimpleTypeKind.
 *
 * @org.apache.olingo.odata2.DoNotImplement EdmSimpleTypeKind holds all EdmSimpleTypes defined as primitive type in the Entity Data Model (EDM).
 */
public enum EdmSimpleTypeKind {

  /** The Binary. */
  Binary, /** The Boolean. */
 Boolean, /** The Byte. */
 Byte, /** The Date time. */
 DateTime, /** The Date time offset. */
 DateTimeOffset, /** The Decimal. */
 Decimal, /** The Double. */
 Double, /** The Guid. */
 Guid, /** The Int 16. */
 Int16, /** The Int 32. */
 Int32, /** The Int 64. */
 Int64, /** The S byte. */
 SByte, /** The Single. */
 Single, /** The String. */
 String,
  
  /** The Time. */
  Time, 
 /** The Null. */
 Null;

  /**
   * Returns the {@link FullQualifiedName} for this SimpleTypeKind.
   * @return {@link FullQualifiedName}
   */
  public FullQualifiedName getFullQualifiedName() {
    return new FullQualifiedName(EdmSimpleType.EDM_NAMESPACE, toString());
  }

  /**
   * Returns an instance for this {@link EdmSimpleTypeKind} in the form of {@link EdmSimpleType}.
   * @return {@link EdmSimpleType} instance
   */
  public EdmSimpleType getEdmSimpleTypeInstance() {
    return SimpleTypeFacadeHolder.instance.getEdmSimpleTypeInstance(this);
  }

  /**
   * <p>Parses a URI literal and determines its EDM simple type on the way.</p>
   * <p>If the literal is <code>null</code> or consists of the literal string
   * "null", the EDM simple type <code>Null</code> is returned.</p>
   * <p>The URI literal syntax of EDM simple types allows two ways of determining
   * the type:
   * <ul>
   * <li>The literal has an explicit type indicator (prefix or suffix).</li>
   * <li>The value is of a type compatible to all other possible types, e.g., "256"
   * could be of type <code>Int16</code> or <code>Int32</code> but all possible
   * values of <code>Int16</code> are also legal values of <code>Int32</code>
   * so callers could promote it to <code>Int32</code> in all cases where they
   * deem it necessary.<br/>
   * For a given literal, always the narrowest possible type is chosen.</li>
   * </ul></p>
   * <p>There are two cases where it is not possible to choose unambiguously
   * a compatible type:
   * <ul>
   * <li><code>0</code> or <code>1</code> could be a number but also a boolean value;
   * therefore, the internal (system) type <code>Bit</code> is used for these values.</li>
   * <li>Integer values between <code>0</code> and <code>127</code> (inclusive) could
   * be of type <code>SByte</code> or <code>Byte</code> both of which are not compatible
   * to the other; therefore, the internal (system) type <code>Uint7</code> is used
   * for these values.</li>
   * </ul></p>
   * @param uriLiteral the literal
   * @return an instance of {@link EdmLiteral}, containing the literal
   * in default String representation and the EDM simple type
   * @throws EdmLiteralException if the literal is malformed
   */
  public static EdmLiteral parseUriLiteral(final String uriLiteral) throws EdmLiteralException {
    return SimpleTypeFacadeHolder.instance.parseUriLiteral(uriLiteral);
  }

  /**
   * Cached access to {@link EdmSimpleTypeFacade} which is used i.a. for {@link EdmSimpleType} instance creation
   * or parsing of {@link EdmLiteral}s.
   */
  private static class SimpleTypeFacadeHolder {
    
    /** The Constant instance. */
    static final EdmSimpleTypeFacade instance = RuntimeDelegate.getSimpleTypeFacade();
    
    /**
     * Instantiates a new simple type facade holder.
     */
    private SimpleTypeFacadeHolder() {
      
    }
  }
}
