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
 * Enumerations for supported unary operators of the OData expression parser
 * for OData version 2.0
 * 
 */
public enum UnaryOperator {
  
  /** The minus. */
  MINUS("-", "negation"), 
 /** The not. */
 NOT("not");

  /** The syntax. */
  private String syntax;
  
  /** The string respresentation. */
  private String stringRespresentation;

  /**
   * Instantiates a new unary operator.
   *
   * @param syntax the syntax
   */
  private UnaryOperator(final String syntax) {
    this.syntax = syntax;
    stringRespresentation = syntax;
  }

  /**
   * Instantiates a new unary operator.
   *
   * @param syntax the syntax
   * @param stringRespresentation the string respresentation
   */
  private UnaryOperator(final String syntax, final String stringRespresentation) {
    this.syntax = syntax;
    this.stringRespresentation = stringRespresentation;
  }

  /**
   * To string.
   *
   * @return Methods name for usage in in text
   */
  @Override
  public String toString() {
    return stringRespresentation;
  }

  /**
   * To uri literal.
   *
   * @return Syntax of the unary operator as used in the URL.
   */
  public String toUriLiteral() {
    return syntax;
  }
}
