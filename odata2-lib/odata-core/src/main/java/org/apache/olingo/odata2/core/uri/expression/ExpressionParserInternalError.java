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

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;

// TODO: Auto-generated Javadoc
/**
 * Internal error in the expression parser.
 * 
 */
public class ExpressionParserInternalError extends ODataMessageException {

  /** The Constant serialVersionUID. */
  static final long serialVersionUID = 77L;
  
  /** The Constant ERROR_PARSING_METHOD. */
  public static final MessageReference ERROR_PARSING_METHOD = createMessageReference(
      ExpressionParserInternalError.class, "ERROR_PARSING_METHOD");
  
  /** The Constant ERROR_PARSING_PARENTHESIS. */
  public static final MessageReference ERROR_PARSING_PARENTHESIS = createMessageReference(
      ExpressionParserInternalError.class, "ERROR_PARSING_PARENTHESIS");
  
  /** The Constant ERROR_ACCESSING_EDM. */
  public static final MessageReference ERROR_ACCESSING_EDM = createMessageReference(
      ExpressionParserInternalError.class, "ERROR_ACCESSING_EDM");
  
  /** The Constant INVALID_TYPE_COUNT. */
  public static final MessageReference INVALID_TYPE_COUNT = createMessageReference(ExpressionParserInternalError.class,
      "INVALID_TYPE_COUNT");;
  
  /** The Constant INVALID_TOKEN_AT. */
  public static final MessageReference INVALID_TOKEN_AT = createMessageReference(ExpressionParserInternalError.class,
      "INVALID_TOKEN_AT");
  
  /** The Constant INVALID_TOKENKIND_AT. */
  public static final MessageReference INVALID_TOKENKIND_AT = createMessageReference(
      ExpressionParserInternalError.class, "INVALID_TOKENKIND_AT");

  /** The parenthesis expression. */
  CommonExpression parenthesisExpression = null;

  /**
   * Instantiates a new expression parser internal error.
   *
   * @param messageReference the message reference
   */
  public ExpressionParserInternalError(final MessageReference messageReference) {
    super(messageReference);
  }

  /**
   * Instantiates a new expression parser internal error.
   *
   * @param messageReference the message reference
   * @param cause the cause
   */
  public ExpressionParserInternalError(final MessageReference messageReference, final Throwable cause) {
    super(messageReference, cause);
  }

  /**
   * Instantiates a new expression parser internal error.
   *
   * @param messageReference the message reference
   * @param cause the cause
   */
  public ExpressionParserInternalError(final MessageReference messageReference, final TokenizerExpectError cause) {
    super(messageReference, cause);
  }

  /**
   * Instantiates a new expression parser internal error.
   *
   * @param messageReference the message reference
   * @param cause the cause
   */
  public ExpressionParserInternalError(final MessageReference messageReference, final EdmException cause) {
    super(messageReference, cause);
  }

  /**
   * Sets the expression.
   *
   * @param parenthesisExpression the parenthesis expression
   * @return the expression parser internal error
   */
  public ExpressionParserInternalError setExpression(final CommonExpression parenthesisExpression) {
    this.parenthesisExpression = parenthesisExpression;
    return this;
  }

  /**
   * Creates the ERRO R PARSIN G METHOD.
   *
   * @param cause the cause
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createERROR_PARSING_METHOD(final TokenizerExpectError cause) {
    return new ExpressionParserInternalError(ERROR_PARSING_METHOD, cause);
  }

  /**
   * Creates the ERRO R PARSIN G PARENTHESIS.
   *
   * @param cause the cause
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createERROR_PARSING_PARENTHESIS(final TokenizerExpectError cause) {
    return new ExpressionParserInternalError(ERROR_PARSING_PARENTHESIS, cause);
  }

  /**
   * Creates the ERRO R PARSIN G PARENTHESIS.
   *
   * @param parenthesisExpression the parenthesis expression
   * @param cause the cause
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createERROR_PARSING_PARENTHESIS(
      final CommonExpression parenthesisExpression, final TokenizerExpectError cause) {
    return new ExpressionParserInternalError(ERROR_PARSING_PARENTHESIS, cause).setExpression(parenthesisExpression);
  }

  /**
   * Creates the ERRO R ACCESSIN G EDM.
   *
   * @param cause the cause
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createERROR_ACCESSING_EDM(final EdmException cause) {
    return new ExpressionParserInternalError(ERROR_ACCESSING_EDM, cause);
  }

  /**
   * Creates the COMMON.
   *
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createCOMMON() {
    return new ExpressionParserInternalError(COMMON);
  }

  /**
   * Creates the COMMON.
   *
   * @param e the e
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createCOMMON(final Throwable e) {
    return new ExpressionParserInternalError(COMMON, e);
  }

  /**
   * Creates the INVALI D TYP E COUNT.
   *
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createINVALID_TYPE_COUNT() {
    return new ExpressionParserInternalError(INVALID_TYPE_COUNT);
  }

  /**
   * Creates the ERRO R ACCESSIN G EDM.
   *
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createERROR_ACCESSING_EDM() {
    return new ExpressionParserInternalError(ERROR_ACCESSING_EDM);
  }

  /**
   * Creates the INVALI D TOKE N AT.
   *
   * @param expectedToken the expected token
   * @param actualToken the actual token
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError
      createINVALID_TOKEN_AT(final String expectedToken, final Token actualToken) {
    MessageReference msgRef = ExpressionParserInternalError.INVALID_TOKEN_AT.create();

    msgRef.addContent(expectedToken);
    msgRef.addContent(actualToken.getUriLiteral());
    msgRef.addContent(actualToken.getPosition());

    return new ExpressionParserInternalError(msgRef);
  }

  /**
   * Creates the INVALI D TOKENKIN D AT.
   *
   * @param expectedTokenKind the expected token kind
   * @param actualToken the actual token
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createINVALID_TOKENKIND_AT(final TokenKind expectedTokenKind,
      final Token actualToken) {
    MessageReference msgRef = ExpressionParserInternalError.INVALID_TOKEN_AT.create();

    msgRef.addContent(expectedTokenKind.toString());
    msgRef.addContent(actualToken.getKind().toString());
    msgRef.addContent(actualToken.getUriLiteral());
    msgRef.addContent(actualToken.getPosition());

    return new ExpressionParserInternalError(msgRef);
  }

  /**
   * Creates the N O TOKE N AVAILABLE.
   *
   * @param expectedToken the expected token
   * @return the expression parser internal error
   */
  public static ExpressionParserInternalError createNO_TOKEN_AVAILABLE(final String expectedToken) {
    MessageReference msgRef = ExpressionParserInternalError.INVALID_TOKEN_AT.create();

    msgRef.addContent(expectedToken);

    return new ExpressionParserInternalError(msgRef);
  }

}
