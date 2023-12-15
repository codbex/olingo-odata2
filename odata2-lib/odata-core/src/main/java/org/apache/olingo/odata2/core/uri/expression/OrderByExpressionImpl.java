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

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExceptionVisitExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionKind;
import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderExpression;

// TODO: Auto-generated Javadoc
/**
 * The Class OrderByExpressionImpl.
 */
public class OrderByExpressionImpl implements OrderByExpression {
  
  /** The orderby string. */
  private String orderbyString;

  /** The orders. */
  List<OrderExpression> orders;

  /**
   * Instantiates a new order by expression impl.
   *
   * @param orderbyString the orderby string
   */
  public OrderByExpressionImpl(final String orderbyString) {
    this.orderbyString = orderbyString;
    orders = new ArrayList<OrderExpression>();
  }

  /**
   * Gets the expression string.
   *
   * @return the expression string
   */
  @Override
  public String getExpressionString() {
    return orderbyString;
  }

  /**
   * Gets the orders.
   *
   * @return the orders
   */
  @Override
  public List<OrderExpression> getOrders() {
    return orders;
  }

  /**
   * Gets the orders count.
   *
   * @return the orders count
   */
  @Override
  public int getOrdersCount() {
    return orders.size();
  }

  /**
   * Adds the order.
   *
   * @param orderNode the order node
   */
  public void addOrder(final OrderExpression orderNode) {
    orders.add(orderNode);
  }

  /**
   * Gets the kind.
   *
   * @return the kind
   */
  @Override
  public ExpressionKind getKind() {
    return ExpressionKind.ORDERBY;
  }

  /**
   * Gets the edm type.
   *
   * @return the edm type
   */
  @Override
  public EdmType getEdmType() {
    return null;
  }

  /**
   * Sets the edm type.
   *
   * @param edmType the edm type
   * @return the common expression
   */
  @Override
  public CommonExpression setEdmType(final EdmType edmType) {
    return this;
  }

  /**
   * Gets the uri literal.
   *
   * @return the uri literal
   */
  @Override
  public String getUriLiteral() {
    return orderbyString;
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
    ArrayList<Object> retParameters = new ArrayList<Object>();
    for (OrderExpression order : orders) {
      Object retParameter = order.accept(visitor);
      retParameters.add(retParameter);
    }

    Object ret = visitor.visitOrderByExpression(this, orderbyString, retParameters);
    return ret;
  }

}
