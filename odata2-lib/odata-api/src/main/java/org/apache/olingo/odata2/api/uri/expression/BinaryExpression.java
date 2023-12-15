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
package org.apache.olingo.odata2.api.uri.expression;

// TODO: Auto-generated Javadoc
/**
 * Represents a binary expression node in the expression tree returned by the methods
 * <br>
 * <br>
 * A binary expression node is inserted in the expression tree for any valid
 * ODATA binary operator in {@link BinaryOperator} (e.g. for "and", "div", "eg", ... )
 * <br>
 * 
 */
public interface BinaryExpression extends CommonExpression {
  
  /**
   * Gets the operator.
   *
   * @return Operator object that represents the used operator
   * @see BinaryOperator
   */
  public BinaryOperator getOperator();

  /**
   * Gets the left operand.
   *
   * @return Expression sub tree of the left operand
   */
  public CommonExpression getLeftOperand();

  /**
   * Gets the right operand.
   *
   * @return Expression sub tree of the right operand
   */
  public CommonExpression getRightOperand();
}
