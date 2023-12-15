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

import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.uri.expression.UnaryOperator;

// TODO: Auto-generated Javadoc
/**
 * Describes a unary operator which is allowed in OData expressions.
 */
class InfoUnaryOperator {
  
  /** The operator. */
  UnaryOperator operator;
  
  /** The category. */
  private String category;
  
  /** The syntax. */
  private String syntax;
  
  /** The combination. */
  ParameterSetCombination combination;

  /**
   * Instantiates a new info unary operator.
   *
   * @param operator the operator
   * @param category the category
   * @param combination the combination
   */
  public InfoUnaryOperator(final UnaryOperator operator, final String category,
      final ParameterSetCombination combination) {
    this.operator = operator;
    this.category = category;
    syntax = operator.toUriLiteral();
    this.combination = combination;
  }

  /**
   * Gets the category.
   *
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Gets the syntax.
   *
   * @return the syntax
   */
  public String getSyntax() {
    return syntax;
  }

  /**
   * Gets the operator.
   *
   * @return the operator
   */
  public UnaryOperator getOperator() {
    return operator;
  }

  /**
   * Validate parameter set.
   *
   * @param actualParameterTypes the actual parameter types
   * @return the parameter set
   * @throws ExpressionParserInternalError the expression parser internal error
   */
  public ParameterSet validateParameterSet(final List<EdmType> actualParameterTypes)
      throws ExpressionParserInternalError {
    return combination.validate(actualParameterTypes);
  }

}
