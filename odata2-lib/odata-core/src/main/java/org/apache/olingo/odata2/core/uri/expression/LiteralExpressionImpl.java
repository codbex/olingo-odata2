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

import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionKind;
import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;
import org.apache.olingo.odata2.api.uri.expression.LiteralExpression;

// TODO: Auto-generated Javadoc
/**
 * The Class LiteralExpressionImpl.
 */
public class LiteralExpressionImpl implements LiteralExpression {

  /** The edm type. */
  private EdmType edmType;
  
  /** The edm literal. */
  private EdmLiteral edmLiteral;
  
  /** The uri literal. */
  private String uriLiteral;

  /**
   * Instantiates a new literal expression impl.
   *
   * @param uriLiteral the uri literal
   * @param javaLiteral the java literal
   */
  public LiteralExpressionImpl(final String uriLiteral, final EdmLiteral javaLiteral) {
    this.uriLiteral = uriLiteral;
    edmLiteral = javaLiteral;
    edmType = edmLiteral.getType();
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
    return ExpressionKind.LITERAL;
  }

  /**
   * Gets the uri literal.
   *
   * @return the uri literal
   */
  @Override
  public String getUriLiteral() {
    return uriLiteral;
  }

  /**
   * Accept.
   *
   * @param visitor the visitor
   * @return the object
   */
  @Override
  public Object accept(final ExpressionVisitor visitor) {
    Object ret = visitor.visitLiteral(this, edmLiteral);
    return ret;
  }

}
