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

import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.uri.expression.BinaryExpression;
import org.apache.olingo.odata2.api.uri.expression.BinaryOperator;
import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.LiteralExpression;
import org.apache.olingo.odata2.api.uri.expression.MemberExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodOperator;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderExpression;
import org.apache.olingo.odata2.api.uri.expression.PropertyExpression;
import org.apache.olingo.odata2.api.uri.expression.SortOrder;
import org.apache.olingo.odata2.api.uri.expression.UnaryExpression;
import org.apache.olingo.odata2.api.uri.expression.UnaryOperator;

// TODO: Auto-generated Javadoc
/**
 * The Class VisitorTool.
 */
public class VisitorTool implements ExpressionVisitor {

  /**
   * Visit binary.
   *
   * @param binaryExpression the binary expression
   * @param operator the operator
   * @param leftSide the left side
   * @param rightSide the right side
   * @return the object
   */
  @Override
  public Object visitBinary(final BinaryExpression binaryExpression, final BinaryOperator operator,
      final Object leftSide, final Object rightSide) {
    return "{" + leftSide.toString() + " " + operator.toUriLiteral() + " " + rightSide.toString() + "}";
  }

  /**
   * Visit filter expression.
   *
   * @param filterExpression the filter expression
   * @param expressionString the expression string
   * @param expression the expression
   * @return the object
   */
  @Override
  public Object visitFilterExpression(final FilterExpression filterExpression, final String expressionString,
      final Object expression) {
    return expression;
  }

  /**
   * Visit literal.
   *
   * @param literal the literal
   * @param edmLiteral the edm literal
   * @return the object
   */
  @Override
  public Object visitLiteral(final LiteralExpression literal, final EdmLiteral edmLiteral) {
    return "" + literal.getUriLiteral() + "";
  }

  /**
   * Visit method.
   *
   * @param methodExpression the method expression
   * @param method the method
   * @param retParameters the ret parameters
   * @return the object
   */
  @Override
  public Object visitMethod(final MethodExpression methodExpression, final MethodOperator method,
      final List<Object> retParameters) {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append(method.toUriLiteral());

    sb.append("(");
    for (int i = 0; i < retParameters.size(); i++) {
      if (i != 0) {
        sb.append(",");
      }
      sb.append(retParameters.get(i));
    }

    sb.append(")}");

    return sb.toString();
  }

  /**
   * Visit member.
   *
   * @param memberExpression the member expression
   * @param source the source
   * @param path the path
   * @return the object
   */
  @Override
  public Object visitMember(final MemberExpression memberExpression, final Object source, final Object path) {
    return "{" + source.toString() + "/" + path.toString() + "}";
  }

  /**
   * Visit property.
   *
   * @param literal the literal
   * @param uriLiteral the uri literal
   * @param edmProperty the edm property
   * @return the object
   */
  @Override
  public Object visitProperty(final PropertyExpression literal, final String uriLiteral, final EdmTyped edmProperty) {
    return uriLiteral;
  }

  /**
   * Visit unary.
   *
   * @param unaryExpression the unary expression
   * @param operator the operator
   * @param operand the operand
   * @return the object
   */
  @Override
  public Object visitUnary(final UnaryExpression unaryExpression, final UnaryOperator operator, final Object operand) {
    return "{" + operator.toUriLiteral() + " " + operand.toString() + "}";
  }

  /**
   * Visit order by expression.
   *
   * @param orderByExpression the order by expression
   * @param expressionString the expression string
   * @param orders the orders
   * @return the object
   */
  @Override
  public Object visitOrderByExpression(final OrderByExpression orderByExpression, final String expressionString,
      final List<Object> orders) {
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append("oc");

    sb.append("(");
    for (int i = 0; i < orders.size(); i++) {
      if (i != 0) {
        sb.append(",");
      }
      sb.append(orders.get(i));
    }

    sb.append(")}");

    return sb.toString();
  }

  /**
   * Visit order.
   *
   * @param orderExpression the order expression
   * @param filterResult the filter result
   * @param sortOrder the sort order
   * @return the object
   */
  @Override
  public Object visitOrder(final OrderExpression orderExpression, final Object filterResult,
      final SortOrder sortOrder) {
    return "{o(" + filterResult + ", " + sortOrder.toString() + ")}";
  }

}
