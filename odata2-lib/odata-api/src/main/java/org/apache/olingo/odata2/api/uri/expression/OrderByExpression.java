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

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Represents a $orderby expression
 * Used to define the <b>root</b> expression node in an $filter expression tree.
 * 
 * 
 */
public interface OrderByExpression extends CommonExpression {
  
  /**
   * Gets the expression string.
   *
   * @return Returns the $filter expression string used to build the expression tree
   */
  String getExpressionString();

  /**
   * Gets the orders.
   *
   * @return Returns a ordered list of order expressions contained in the $orderby expression string
   * <p>
   * <b>For example</b>: The orderby expression build from "$orderby=name asc, age desc"
   * would contain to order expression.
   */
  public List<OrderExpression> getOrders();

  /**
   * Gets the orders count.
   *
   * @return Returns the count of order expressions contained in the $orderby expression string
   */
  public int getOrdersCount();

}
