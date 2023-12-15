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
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExceptionVisitExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionKind;
import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;
import org.apache.olingo.odata2.api.uri.expression.UnaryExpression;
import org.apache.olingo.odata2.api.uri.expression.UnaryOperator;

// TODO: Auto-generated Javadoc
/**
 * The Class UnaryExpressionImpl.
 */
public class UnaryExpressionImpl implements UnaryExpression {
  
  /** The operator info. */
  private InfoUnaryOperator operatorInfo = null;
  
  /** The operand. */
  private CommonExpression operand = null;
  
  /** The edm type. */
  private EdmType edmType = null;

  /**
   * Instantiates a new unary expression impl.
   *
   * @param operatorInfo the operator info
   * @param operand the operand
   */
  public UnaryExpressionImpl(final InfoUnaryOperator operatorInfo, final CommonExpression operand) {
    this.operatorInfo = operatorInfo;
    this.operand = operand;
  }

  /**
   * Gets the kind.
   *
   * @return the kind
   */
  @Override
  public ExpressionKind getKind() {
    return ExpressionKind.UNARY;
  }

  /**
   * Gets the operator.
   *
   * @return the operator
   */
  @Override
  public UnaryOperator getOperator() {
    return operatorInfo.operator;
  }

  /**
   * Gets the operand.
   *
   * @return the operand
   */
  @Override
  public CommonExpression getOperand() {
    return operand;
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
    Object retOperand = operand.accept(visitor);

    return visitor.visitUnary(this, operatorInfo.getOperator(), retOperand);
  }

}
