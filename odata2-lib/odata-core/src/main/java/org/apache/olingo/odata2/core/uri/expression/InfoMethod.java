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
import org.apache.olingo.odata2.api.uri.expression.MethodOperator;

// TODO: Auto-generated Javadoc
/**
 * Describes a method expression which is allowed in OData expressions.
 */
class InfoMethod {

  /** The method. */
  private MethodOperator method;
  
  /** The syntax. */
  private String syntax;
  
  /** The min parameter. */
  private int minParameter;
  
  /** The max parameter. */
  private int maxParameter;
  
  /** The combination. */
  ParameterSetCombination combination;

  /**
   * Instantiates a new info method.
   *
   * @param method the method
   * @param combination the combination
   */
  public InfoMethod(final MethodOperator method, final ParameterSetCombination combination) {
    this.method = method;
    syntax = method.toUriLiteral();
    minParameter = 1;
    maxParameter = 1;
    this.combination = combination;
  }

  /**
   * Instantiates a new info method.
   *
   * @param method the method
   * @param minParameters the min parameters
   * @param maxParameters the max parameters
   * @param combination the combination
   */
  public InfoMethod(final MethodOperator method, final int minParameters, final int maxParameters,
      final ParameterSetCombination combination) {
    this.method = method;
    syntax = method.toUriLiteral();
    minParameter = minParameters;
    maxParameter = maxParameters;
    this.combination = combination;
  }

  /**
   * Instantiates a new info method.
   *
   * @param method the method
   * @param string the string
   * @param minParameters the min parameters
   * @param maxParameters the max parameters
   * @param combination the combination
   */
  public InfoMethod(final MethodOperator method, final String string, final int minParameters, final int maxParameters,
      final ParameterSetCombination combination) {
    this.method = method;
    syntax = string;
    minParameter = minParameters;
    maxParameter = maxParameters;
    this.combination = combination;
  }

  /**
   * Gets the method.
   *
   * @return the method
   */
  public MethodOperator getMethod() {
    return method;
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
   * Gets the min parameter.
   *
   * @return the min parameter
   */
  public int getMinParameter() {
    return minParameter;
  }

  /**
   * Gets the max parameter.
   *
   * @return the max parameter
   */
  public int getMaxParameter() {
    return maxParameter;
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

  /**
   * Returns the EdmType of the returned value of a Method
   * If a method may have different return types (depending on the input type) null will be returned.
   *
   * @return the return type
   */
  public EdmType getReturnType() {
    return combination.getReturnType();

  }
}
