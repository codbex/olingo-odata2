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
package org.apache.olingo.odata2.api.uri.expression;

// TODO: Auto-generated Javadoc
/**
 * Enumerations for supported binary operators of the ODATA expression parser
 * for ODATA version 2.0 (with some restrictions)
 * 
 */
public enum BinaryOperator {
  
  /** The and. */
  AND("and"), 
 /** The or. */
 OR("or"), 
 /** The eq. */
 EQ("eq"), 
 /** The ne. */
 NE("ne"), 
 /** The lt. */
 LT("lt"), 
 /** The le. */
 LE("le"), 
 /** The gt. */
 GT("gt"), 
 /** The ge. */
 GE("ge"), 
 /** The add. */
 ADD("add"), 
 /** The sub. */
 SUB("sub"),
  
  /** The mul. */
  MUL("mul"), 
 /** The div. */
 DIV("div"), 
 /** The modulo. */
 MODULO("mod"),

  /**
   * Property access operator. E.g. $filter=address/city eq "Sydney"
   */
  PROPERTY_ACCESS("/", "property access");

  /** The uri syntax. */
  private String uriSyntax;
  
  /** The string respresentation. */
  private String stringRespresentation;

  /**
   * Instantiates a new binary operator.
   *
   * @param uriSyntax the uri syntax
   */
  private BinaryOperator(final String uriSyntax) {
    this.uriSyntax = uriSyntax;
    stringRespresentation = uriSyntax;
  }

  /**
   * Instantiates a new binary operator.
   *
   * @param syntax the syntax
   * @param stringRespresentation the string respresentation
   */
  private BinaryOperator(final String syntax, final String stringRespresentation) {
    uriSyntax = syntax;
    this.stringRespresentation = stringRespresentation;
  }

  /**
   * To string.
   *
   * @return Operator name for usage in text
   */
  @Override
  public String toString() {
    return stringRespresentation;
  }

  /**
   * To uri literal.
   *
   * @return URI literal of the binary operator as used in the URL.
   */
  public String toUriLiteral() {
    return uriSyntax;
  }
}
