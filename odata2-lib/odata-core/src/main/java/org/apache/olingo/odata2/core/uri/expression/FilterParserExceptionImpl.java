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
import org.apache.olingo.odata2.api.edm.EdmStructuralType;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.uri.expression.BinaryOperator;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionParserException;
import org.apache.olingo.odata2.api.uri.expression.PropertyExpression;

// TODO: Auto-generated Javadoc
/**
 * This class is used to create exceptions of type FilterParserException.
 * Because this class lies inside org.apache.olingo.odata2.core it is possible to define better/more detailed
 * input parameters for inserting into the exception text.<br>
 * The exception {@link ExpressionParserException} does not know the org.apache.olingo.odata2.core content
 * 
 * 
 */
public class FilterParserExceptionImpl extends ExpressionParserException {
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 77L;

  /**
   * Creates the ERRO R I N TOKENIZER.
   *
   * @param exceptionTokenizer the exception tokenizer
   * @param expression the expression
   * @return the expression parser exception
   */
  static public ExpressionParserException createERROR_IN_TOKENIZER(final TokenizerException exceptionTokenizer,
      final String expression) {
    Token token = exceptionTokenizer.getToken();
    MessageReference msgRef = ExpressionParserException.ERROR_IN_TOKENIZER.create();

    msgRef.addContent(token.getUriLiteral());
    msgRef.addContent(Integer.toString(token.getPosition() + 1));
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef, exceptionTokenizer);
  }

  /**
   * Creates the INVALI D TRAILIN G TOKE N DETECTE D AFTE R PARSING.
   *
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   */
  static public ExpressionParserException createINVALID_TRAILING_TOKEN_DETECTED_AFTER_PARSING(final Token token,
      final String expression) {
    MessageReference msgRef = ExpressionParserException.INVALID_TRAILING_TOKEN_DETECTED_AFTER_PARSING.create();

    msgRef.addContent(token.getUriLiteral());
    msgRef.addContent(Integer.toString(token.getPosition() + 1));
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the EXPRESSIO N EXPECTE D AFTE R POS.
   *
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   */
  static public ExpressionParserException
      createEXPRESSION_EXPECTED_AFTER_POS(final Token token, final String expression) {
    MessageReference msgRef = ExpressionParserException.EXPRESSION_EXPECTED_AFTER_POS.create();

    msgRef.addContent(Integer.toString(token.getPosition() + 1));
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the EXPRESSIO N EXPECTE D AFTE R POS.
   *
   * @param position the position
   * @param expression the expression
   * @return the expression parser exception
   */
  static public ExpressionParserException createEXPRESSION_EXPECTED_AFTER_POS(final int position,
      final String expression) {
    MessageReference msgRef = ExpressionParserException.EXPRESSION_EXPECTED_AFTER_POS.create();

    msgRef.addContent(position);
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the COMM A O R EN D EXPECTE D A T POS.
   *
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   */
  static public ExpressionParserException
      createCOMMA_OR_END_EXPECTED_AT_POS(final Token token, final String expression) {
    MessageReference msgRef = ExpressionParserException.COMMA_OR_END_EXPECTED_AT_POS.create();

    msgRef.addContent(Integer.toString(token.getPosition() + 1));
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the EXPRESSIO N EXPECTE D A T POS.
   *
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   */
  static public ExpressionParserException createEXPRESSION_EXPECTED_AT_POS(final Token token, final String expression) {
    MessageReference msgRef = ExpressionParserException.EXPRESSION_EXPECTED_AT_POS.create();

    msgRef.addContent(Integer.toString(token.getPosition() + 1));
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the COMM A O R CLOSIN G PARENTHESI S EXPECTE D AFTE R POS.
   *
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   */
  static public ExpressionParserException createCOMMA_OR_CLOSING_PARENTHESIS_EXPECTED_AFTER_POS(final Token token,
      final String expression) {
    MessageReference msgRef = ExpressionParserException.COMMA_OR_CLOSING_PARENTHESIS_EXPECTED_AFTER_POS.create();

    msgRef.addContent(Integer.toString(token.getPosition() + token.getUriLiteral().length()));
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the METHO D WRON G AR G COUNT.
   *
   * @param methodExpression the method expression
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   */
  public static ExpressionParserException createMETHOD_WRONG_ARG_COUNT(final MethodExpressionImpl methodExpression,
      final Token token, final String expression) {
    MessageReference msgRef = null;
    int minParam = methodExpression.getMethodInfo().getMinParameter();
    int maxParam = methodExpression.getMethodInfo().getMaxParameter();

    if ((minParam == -1) && (maxParam == -1)) {
      // no exception thrown in this case
    } else if ((minParam != -1) && (maxParam == -1)) {
      // Tested with TestParserExceptions.TestPMreadParameters CASE 7-1
      msgRef = ExpressionParserException.METHOD_WRONG_ARG_X_OR_MORE.create();
      msgRef.addContent(methodExpression.getMethod().toUriLiteral());
      msgRef.addContent(token.getPosition() + 1);
      msgRef.addContent(expression);
      msgRef.addContent(minParam);
    } else if ((minParam == -1) && (maxParam != -1)) {
      // Tested with TestParserExceptions.TestPMreadParameters CASE 8-2
      msgRef = ExpressionParserException.METHOD_WRONG_ARG_X_OR_LESS.create();
      msgRef.addContent(methodExpression.getMethod().toUriLiteral());
      msgRef.addContent(token.getPosition() + 1);
      msgRef.addContent(expression);
      msgRef.addContent(maxParam);
    } else if ((minParam != -1) && (maxParam != -1)) {
      if (minParam == maxParam) {
        // Tested with TestParserExceptions.TestPMreadParameters CASE 11-1
        msgRef = ExpressionParserException.METHOD_WRONG_ARG_EXACT.create();
        msgRef.addContent(methodExpression.getMethod().toUriLiteral());
        msgRef.addContent(token.getPosition() + 1);
        msgRef.addContent(expression);
        msgRef.addContent(minParam);
      } else {
        // Tested with TestParserExceptions.TestPMreadParameters CASE 10-1
        msgRef = ExpressionParserException.METHOD_WRONG_ARG_BETWEEN.create();
        msgRef.addContent(methodExpression.getMethod().toUriLiteral());
        msgRef.addContent(token.getPosition() + 1);
        msgRef.addContent(expression);
        msgRef.addContent(minParam);
        msgRef.addContent(maxParam);
      }
    }

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the METHO D WRON G INPU T TYPE.
   *
   * @param methodExpression the method expression
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   */
  public static ExpressionParserException createMETHOD_WRONG_INPUT_TYPE(final MethodExpressionImpl methodExpression,
      final Token token, final String expression) {
    MessageReference msgRef = null;

    // Tested with TestParserExceptions.TestPMreadParameters CASE 7-1
    msgRef = ExpressionParserException.METHOD_WRONG_INPUT_TYPE.create();
    msgRef.addContent(methodExpression.getMethod().toUriLiteral());
    msgRef.addContent(token.getPosition() + 1);
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the LEF T SID E NO T A PROPERTY.
   *
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   * @throws ExpressionParserInternalError the expression parser internal error
   */
  public static ExpressionParserException createLEFT_SIDE_NOT_A_PROPERTY(final Token token, final String expression)
      throws ExpressionParserInternalError {
    MessageReference msgRef = ExpressionParserException.LEFT_SIDE_NOT_A_PROPERTY.create();

    msgRef.addContent(token.getPosition() + 1);
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the LEF T SID E NO T STRUCTURA L TYPE.
   *
   * @param parentType the parent type
   * @param property the property
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   * @throws ExpressionParserInternalError the expression parser internal error
   */
  public static ExpressionParserException createLEFT_SIDE_NOT_STRUCTURAL_TYPE(final EdmType parentType,
      final PropertyExpressionImpl property, final Token token, final String expression)
      throws ExpressionParserInternalError {
    MessageReference msgRef = ExpressionParserException.LEFT_SIDE_NOT_STRUCTURAL_TYPE.create();

    try {
      msgRef.addContent(property.getUriLiteral());
      msgRef.addContent(parentType.getNamespace() + "." + parentType.getName());
      msgRef.addContent(token.getPosition() + 1);
      msgRef.addContent(expression);
    } catch (EdmException e) {
      throw ExpressionParserInternalError.createERROR_ACCESSING_EDM(e);
    }

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the PROPERT Y NAM E NO T FOUN D I N TYPE.
   *
   * @param parentType the parent type
   * @param property the property
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   * @throws ExpressionParserInternalError the expression parser internal error
   */
  public static ExpressionParserException createPROPERTY_NAME_NOT_FOUND_IN_TYPE(final EdmStructuralType parentType,
      final PropertyExpression property, final Token token, final String expression)
      throws ExpressionParserInternalError {
    MessageReference msgRef = ExpressionParserException.PROPERTY_NAME_NOT_FOUND_IN_TYPE.create();

    try {
      msgRef.addContent(property.getUriLiteral());
      msgRef.addContent(parentType.getNamespace() + "." + parentType.getName());
      msgRef.addContent(token.getPosition() + 1);
      msgRef.addContent(expression);
    } catch (EdmException e) {
      throw ExpressionParserInternalError.createERROR_ACCESSING_EDM(e);
    }

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the TOKE N UNDETERMINATE D STRING.
   *
   * @param position the position
   * @param expression the expression
   * @return the expression parser exception
   */
  public static ExpressionParserException
      createTOKEN_UNDETERMINATED_STRING(final int position, final String expression) {
    MessageReference msgRef = ExpressionParserException.TOKEN_UNDETERMINATED_STRING.create();

    msgRef.addContent(position + 1);
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the INVALI D TYPE S FO R BINAR Y OPERATOR.
   *
   * @param op the op
   * @param left the left
   * @param right the right
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   */
  public static ExpressionParserException createINVALID_TYPES_FOR_BINARY_OPERATOR(final BinaryOperator op,
      final EdmType left, final EdmType right, final Token token, final String expression) {
    MessageReference msgRef = ExpressionParserException.INVALID_TYPES_FOR_BINARY_OPERATOR.create();

    msgRef.addContent(op.toUriLiteral());

    try {
      msgRef.addContent(left.getNamespace() + "." + left.getName());
    } catch (EdmException e) {
      msgRef.addContent("");
    }
    try {
      msgRef.addContent(right.getNamespace() + "." + right.getName());
    } catch (EdmException e) {
      msgRef.addContent("");
    }
    msgRef.addContent(token.getPosition() + 1);
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the MISSIN G CLOSIN G PARENTHESIS.
   *
   * @param position the position
   * @param expression the expression
   * @param e the e
   * @return the expression parser exception
   */
  public static ExpressionParserException createMISSING_CLOSING_PARENTHESIS(final int position,
      final String expression, final TokenizerExpectError e) {
    MessageReference msgRef = ExpressionParserException.MISSING_CLOSING_PARENTHESIS.create();

    msgRef.addContent(position + 1);
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef, e);
  }

  /**
   * Creates the INVALI D SOR T ORDER.
   *
   * @param token the token
   * @param expression the expression
   * @return the expression parser exception
   */
  public static ExpressionParserException createINVALID_SORT_ORDER(final Token token, final String expression) {
    MessageReference msgRef = ExpressionParserException.INVALID_SORT_ORDER.create();
    msgRef.addContent(token.getPosition() + 1);
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);
  }

  /**
   * Creates the INVALI D METHO D CALL.
   *
   * @param leftNode the left node
   * @param prevToken the prev token
   * @param expression the expression
   * @return the expression parser exception
   */
  public static ExpressionParserException createINVALID_METHOD_CALL(final CommonExpression leftNode,
      final Token prevToken, final String expression) {
    final MessageReference msgRef = ExpressionParserException.INVALID_METHOD_CALL.create();

    msgRef.addContent(leftNode.getUriLiteral());
    msgRef.addContent(prevToken.getPosition() + 1);
    msgRef.addContent(expression);

    return new ExpressionParserException(msgRef);

  }

  /**
   * Creates the TYP E EXPECTE D AT.
   *
   * @param expectedType the expected type
   * @param actualType the actual type
   * @param position the position
   * @param expression the expression
   * @return the expression parser exception
   */
  public static ExpressionParserException createTYPE_EXPECTED_AT(final EdmType expectedType, final EdmType actualType,
      final int position, final String expression) {
    final MessageReference msgRef = ExpressionParserException.TYPE_EXPECTED_AT.create();

    try {
      msgRef.addContent(expectedType.getNamespace() + '.' + expectedType.getName());
    } catch (EdmException e) {
      msgRef.addContent("");
    }

    msgRef.addContent(position);
    msgRef.addContent(expression);

    if (actualType != null) {
      try {
        msgRef.addContent(actualType.getNamespace() + '.' + actualType.getName());
      } catch (EdmException e) {
        msgRef.addContent("");
      }
    } else {
      msgRef.addContent("null");
    }

    return new ExpressionParserException(msgRef);

  }
}
