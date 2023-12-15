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
 * Represents a order expression in the expression tree
 * <br>
 * <br>
 * <p>A order expression node is inserted in the expression tree for any valid
 * OData order. For example for "$orderby=age desc, name asc" two order expression node
 * will be inserted into the expression tree
 * <br>
 * <br>
 * 
 */
public interface OrderExpression extends CommonExpression {

  /**
   * Gets the sort order.
   *
   * @return Returns the sort order (ascending or descending) of the order expression
   */
  SortOrder getSortOrder();

  /**
   * Gets the expression.
   *
   * @return Returns the expression node which defines the data used to order the output
   * send back to the client. In the simplest case this would be a {@link PropertyExpression}.
   * @see CommonExpression
   */
  CommonExpression getExpression();

}
