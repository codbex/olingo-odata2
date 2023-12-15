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

import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataMessageException;

// TODO: Auto-generated Javadoc
/**
 * This exception is thrown if a token should be read
 * from the top of the {@link TokenList} which does not match an
 * expected token. The cause for using this exception <b>MUST</b> indicate an internal error
 * in the {@link Tokenizer} or inside the {@link FilterParserImpl}.
 * <br><br>
 * <b>This exception in not in the public API</b>, but may be added as cause for
 * the {@link ExpressionParserInternalError} exception.
 * 
 */
public class TokenizerExpectError extends ODataMessageException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The Constant parseStringpoken. */
  public static final int parseStringpoken = 1;

  /** The Constant NO_TOKEN_AVAILABLE. */
  // Invalid token detected at position &POSITION&
  public static final MessageReference NO_TOKEN_AVAILABLE = createMessageReference(TokenizerExpectError.class,
      "NO_TOKEN_AVAILABLE");
  
  /** The Constant INVALID_TOKEN_AT. */
  public static final MessageReference INVALID_TOKEN_AT = createMessageReference(TokenizerExpectError.class,
      "INVALID_TOKEN_AT");
  
  /** The Constant INVALID_TOKENKIND_AT. */
  public static final MessageReference INVALID_TOKENKIND_AT = createMessageReference(TokenizerExpectError.class,
      "INVALID_TOKENKIND_AT");

  /** The token. */
  private String token;
  
  /** The previous. */
  private Exception previous;
  
  /** The position. */
  private int position;

  /**
   * Gets the token.
   *
   * @return the token
   */
  public String getToken() {
    return token;
  }

  /**
   * Sets the token.
   *
   * @param token the new token
   */
  public void setToken(final String token) {
    this.token = token;
  }

  /**
   * Gets the previous.
   *
   * @return the previous
   */
  public Exception getPrevious() {
    return previous;
  }

  /**
   * Sets the previous.
   *
   * @param previous the new previous
   */
  public void setPrevious(final Exception previous) {
    this.previous = previous;
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
   * Sets the position.
   *
   * @param position the new position
   */
  public void setPosition(final int position) {
    this.position = position;
  }

  /**
   * Instantiates a new tokenizer expect error.
   *
   * @param messageReference the message reference
   */
  public TokenizerExpectError(final MessageReference messageReference) {
    super(messageReference);
  }

  /**
   * Creates the INVALI D TOKE N AT.
   *
   * @param expectedToken the expected token
   * @param actualToken the actual token
   * @return the tokenizer expect error
   */
  public static TokenizerExpectError createINVALID_TOKEN_AT(final String expectedToken, final Token actualToken) {
    MessageReference msgRef = TokenizerExpectError.INVALID_TOKEN_AT.create();

    msgRef.addContent(expectedToken);
    msgRef.addContent(actualToken.getUriLiteral());
    msgRef.addContent(actualToken.getPosition());

    return new TokenizerExpectError(msgRef);
  }

  /**
   * Creates the INVALI D TOKENKIN D AT.
   *
   * @param expectedTokenKind the expected token kind
   * @param actualToken the actual token
   * @return the tokenizer expect error
   */
  public static TokenizerExpectError createINVALID_TOKENKIND_AT(final TokenKind expectedTokenKind,
      final Token actualToken) {
    MessageReference msgRef = TokenizerExpectError.INVALID_TOKEN_AT.create();

    msgRef.addContent(expectedTokenKind.toString());
    msgRef.addContent(actualToken.getKind().toString());
    msgRef.addContent(actualToken.getUriLiteral());
    msgRef.addContent(actualToken.getPosition());

    return new TokenizerExpectError(msgRef);
  }

  /**
   * Creates the N O TOKE N AVAILABLE.
   *
   * @param expectedToken the expected token
   * @return the tokenizer expect error
   */
  public static TokenizerExpectError createNO_TOKEN_AVAILABLE(final String expectedToken) {
    MessageReference msgRef = TokenizerExpectError.INVALID_TOKEN_AT.create();

    msgRef.addContent(expectedToken);

    return new TokenizerExpectError(msgRef);
  }

}
