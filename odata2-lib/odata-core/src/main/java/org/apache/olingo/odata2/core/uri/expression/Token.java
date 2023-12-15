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
package org.apache.olingo.odata2.core.uri.expression;

/* 1 */

import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmType;

// TODO: Auto-generated Javadoc
/**
 * The Class Token.
 */
public class Token {

  /** The kind. */
  private TokenKind kind;
  
  /** The position. */
  private int position;
  
  /** The uri literal. */
  private String uriLiteral;
  
  /** The java literal. */
  private EdmLiteral javaLiteral;

  /**
   * Instantiates a new token.
   *
   * @param kind the kind
   * @param position the position
   * @param uriLiteral the uri literal
   * @param javaLiteral the java literal
   */
  public Token(final TokenKind kind, final int position, final String uriLiteral, final EdmLiteral javaLiteral) {
    this.kind = kind;
    this.position = position;
    this.uriLiteral = uriLiteral;
    this.javaLiteral = javaLiteral;
  }

  /**
   * Instantiates a new token.
   *
   * @param kind the kind
   * @param position the position
   * @param uriLiteral the uri literal
   */
  public Token(final TokenKind kind, final int position, final String uriLiteral) {
    this.kind = kind;
    this.position = position;
    this.uriLiteral = uriLiteral;
    javaLiteral = null;
  }

  /**
   * Gets the kind.
   *
   * @return the kind
   */
  public TokenKind getKind() {
    return kind;
  }

  /**
   * Gets the position.
   *
   * @return the position
   */
  public int getPosition() {
    return position;
  }

  /**
   * Gets the edm type.
   *
   * @return the edm type
   */
  public EdmType getEdmType() {
    if (javaLiteral == null) {
      return null;
    }
    return javaLiteral.getType();
  }

  /**
   * Gets the uri literal.
   *
   * @return the uri literal
   */
  public String getUriLiteral() {
    return uriLiteral;
  }

  /**
   * Gets the java literal.
   *
   * @return the java literal
   */
  public EdmLiteral getJavaLiteral() {
    return javaLiteral;
  }
}
