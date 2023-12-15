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

import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.uri.expression.BinaryExpression;
import org.apache.olingo.odata2.api.uri.expression.BinaryOperator;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExceptionVisitExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionKind;
import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;

// TODO: Auto-generated Javadoc
/**
 * The Class BinaryExpressionImpl.
 */
public class BinaryExpressionImpl implements BinaryExpression {
  
  /** The operator info. */
  final protected InfoBinaryOperator operatorInfo;
  
  /** The left side. */
  final protected CommonExpression leftSide;
  
  /** The right side. */
  final protected CommonExpression rightSide;
  
  /** The token. */
  final protected Token token;
  
  /** The edm type. */
  protected EdmType edmType;

  /**
   * Instantiates a new binary expression impl.
   *
   * @param operatorInfo the operator info
   * @param leftSide the left side
   * @param rightSide the right side
   * @param token the token
   */
  public BinaryExpressionImpl(final InfoBinaryOperator operatorInfo, final CommonExpression leftSide,
      final CommonExpression rightSide, final Token token) {
    this.operatorInfo = operatorInfo;
    this.leftSide = leftSide;
    this.rightSide = rightSide;
    this.token = token;
    edmType = null;
  }

  /**
   * Gets the operator.
   *
   * @return the operator
   */
  @Override
  public BinaryOperator getOperator() {
    return operatorInfo.getOperator();
  }

  /**
   * Gets the left operand.
   *
   * @return the left operand
   */
  @Override
  public CommonExpression getLeftOperand() {
    return leftSide;
  }

  /**
   * Gets the right operand.
   *
   * @return the right operand
   */
  @Override
  public CommonExpression getRightOperand() {
    return rightSide;
  }

  /**
   * Gets the edm type.
   *
   * @return the edm type
   */
  @Override
  public EdmType getEdmType() {
    return edmType;
  }

  /**
   * Sets the edm type.
   *
   * @param edmType the edm type
   * @return the common expression
   */
  @Override
  public CommonExpression setEdmType(final EdmType edmType) {
    this.edmType = edmType;
    return this;
  }

  /**
   * Gets the kind.
   *
   * @return the kind
   */
  @Override
  public ExpressionKind getKind() {
    return ExpressionKind.BINARY;
  }

  /**
   * Gets the uri literal.
   *
   * @return the uri literal
   */
  @Override
  public String getUriLiteral() {
    return operatorInfo.getSyntax();
  }

  /**
   * Accept.
   *
   * @param visitor the visitor
   * @return the object
   * @throws ExceptionVisitExpression the exception visit expression
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public Object accept(final ExpressionVisitor visitor) throws ExceptionVisitExpression, ODataApplicationException {
    Object retLeftSide = leftSide.accept(visitor);
    Object retRightSide = rightSide.accept(visitor);

    return visitor.visitBinary(this, operatorInfo.getOperator(), retLeftSide, retRightSide);
  }

  /**
   * Gets the token.
   *
   * @return the token
   */
  public Token getToken() {
    return token;
  }

}
