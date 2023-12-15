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
import org.apache.olingo.odata2.api.uri.expression.MemberExpression;

// TODO: Auto-generated Javadoc
/**
 * The Class MemberExpressionImpl.
 */
public class MemberExpressionImpl implements BinaryExpression, MemberExpression {
  
  /** The path. */
  CommonExpression path;
  
  /** The property. */
  CommonExpression property;
  
  /** The edm type. */
  EdmType edmType;

  /**
   * Instantiates a new member expression impl.
   *
   * @param path the path
   * @param property the property
   */
  public MemberExpressionImpl(final CommonExpression path, final CommonExpression property) {
    this.path = path;
    this.property = property;
    edmType = property.getEdmType();
  }

  /**
   * Gets the path.
   *
   * @return the path
   */
  @Override
  public CommonExpression getPath() {
    return path;
  }

  /**
   * Gets the property.
   *
   * @return the property
   */
  @Override
  public CommonExpression getProperty() {
    return property;
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
   * Gets the operator.
   *
   * @return the operator
   */
  @Override
  public BinaryOperator getOperator() {
    return BinaryOperator.PROPERTY_ACCESS;
  }

  /**
   * Gets the kind.
   *
   * @return the kind
   */
  @Override
  public ExpressionKind getKind() {
    return ExpressionKind.MEMBER;
  }

  /**
   * Gets the uri literal.
   *
   * @return the uri literal
   */
  @Override
  public String getUriLiteral() {
    return BinaryOperator.PROPERTY_ACCESS.toUriLiteral();
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
    Object retSource = path.accept(visitor);
    Object retPath = property.accept(visitor);

    Object ret = visitor.visitMember(this, retSource, retPath);
    return ret;
  }

  /**
   * Gets the left operand.
   *
   * @return the left operand
   */
  @Override
  public CommonExpression getLeftOperand() {
    return path;
  }

  /**
   * Gets the right operand.
   *
   * @return the right operand
   */
  @Override
  public CommonExpression getRightOperand() {
    return property;
  }

}
