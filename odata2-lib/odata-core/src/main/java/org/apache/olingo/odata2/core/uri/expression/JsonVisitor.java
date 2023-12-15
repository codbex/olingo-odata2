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

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.uri.expression.BinaryExpression;
import org.apache.olingo.odata2.api.uri.expression.BinaryOperator;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
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
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonVisitor.
 */
public class JsonVisitor implements ExpressionVisitor {

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
    try {
      StringWriter writer = new StringWriter();
      JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
      jsonStreamWriter.beginObject().namedStringValueRaw("nodeType", binaryExpression.getKind().toString()).separator()
          .namedStringValue("operator", operator.toUriLiteral()).separator().namedStringValueRaw("type",
              getType(binaryExpression)).separator().name("left").unquotedValue(leftSide.toString()).separator().name(
              "right").unquotedValue(rightSide.toString()).endObject();
      writer.flush();
      return writer.toString();
    } catch (final IOException e) {
      return null;
    }
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
    try {
      StringWriter writer = new StringWriter();
      JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
      jsonStreamWriter.beginObject().namedStringValueRaw("nodeType", "order collection").separator().name("orders")
          .beginArray();
      boolean first = true;
      for (final Object order : orders) {
        if (first) {
          first = false;
        } else {
          jsonStreamWriter.separator();
        }
        jsonStreamWriter.unquotedValue(order.toString());
      }
      jsonStreamWriter.endArray().endObject();
      writer.flush();
      return writer.toString();
    } catch (final IOException e) {
      return null;
    }
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
    try {
      StringWriter writer = new StringWriter();
      JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
      jsonStreamWriter.beginObject().namedStringValueRaw("nodeType", orderExpression.getKind().toString()).separator()
          .namedStringValueRaw("sortorder", sortOrder.toString()).separator().name("expression").unquotedValue(
              filterResult.toString()).endObject();
      writer.flush();
      return writer.toString();
    } catch (final IOException e) {
      return null;
    }
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
    try {
      StringWriter writer = new StringWriter();
      JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
      jsonStreamWriter.beginObject().namedStringValueRaw("nodeType", literal.getKind().toString()).separator()
          .namedStringValueRaw("type", getType(literal)).separator().namedStringValue("value", edmLiteral.getLiteral())
          .endObject();
      writer.flush();
      return writer.toString();
    } catch (final IOException e) {
      return null;
    }
  }

  /**
   * Visit method.
   *
   * @param methodExpression the method expression
   * @param method the method
   * @param parameters the parameters
   * @return the object
   */
  @Override
  public Object visitMethod(final MethodExpression methodExpression, final MethodOperator method,
      final List<Object> parameters) {
    try {
      StringWriter writer = new StringWriter();
      JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
      jsonStreamWriter.beginObject().namedStringValueRaw("nodeType", methodExpression.getKind().toString()).separator()
          .namedStringValueRaw("operator", method.toUriLiteral()).separator().namedStringValueRaw("type",
              getType(methodExpression)).separator().name("parameters").beginArray();
      boolean first = true;
      for (Object parameter : parameters) {
        if (first) {
          first = false;
        } else {
          jsonStreamWriter.separator();
        }
        jsonStreamWriter.unquotedValue(parameter.toString());
      }
      jsonStreamWriter.endArray().endObject();
      writer.flush();
      return writer.toString();
    } catch (final IOException e) {
      return null;
    }
  }

  /**
   * Visit member.
   *
   * @param memberExpression the member expression
   * @param path the path
   * @param property the property
   * @return the object
   */
  @Override
  public Object visitMember(final MemberExpression memberExpression, final Object path, final Object property) {
    try {
      StringWriter writer = new StringWriter();
      JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
      jsonStreamWriter.beginObject().namedStringValueRaw("nodeType", memberExpression.getKind().toString()).separator()
          .namedStringValueRaw("type", getType(memberExpression)).separator().name("source").unquotedValue(
              path.toString()).separator().name("path").unquotedValue(property.toString()).endObject();
      writer.flush();
      return writer.toString();
    } catch (final IOException e) {
      return null;
    }
  }

  /**
   * Visit property.
   *
   * @param propertyExpression the property expression
   * @param uriLiteral the uri literal
   * @param edmProperty the edm property
   * @return the object
   */
  @Override
  public Object visitProperty(final PropertyExpression propertyExpression, final String uriLiteral,
      final EdmTyped edmProperty) {
    try {
      StringWriter writer = new StringWriter();
      JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
      jsonStreamWriter.beginObject().namedStringValueRaw("nodeType", propertyExpression.getKind().toString())
          .separator().namedStringValue("name", uriLiteral).separator().namedStringValueRaw("type",
              getType(propertyExpression)).endObject();
      writer.flush();
      return writer.toString();
    } catch (final IOException e) {
      return null;
    }
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
    try {
      StringWriter writer = new StringWriter();
      JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
      jsonStreamWriter.beginObject().namedStringValueRaw("nodeType", unaryExpression.getKind().toString()).separator()
          .namedStringValueRaw("operator", operator.toUriLiteral()).separator().namedStringValueRaw("type",
              getType(unaryExpression)).separator().name("operand").unquotedValue(operand.toString()).endObject();
      writer.flush();
      return writer.toString();
    } catch (final IOException e) {
      return null;
    }
  }

  /**
   * Gets the type.
   *
   * @param expression the expression
   * @return the type
   */
  private static String getType(final CommonExpression expression) {
    try {
      final EdmType type = expression.getEdmType();
      return type == null ? null : type.getNamespace() + Edm.DELIMITER + type.getName();
    } catch (final EdmException e) {
      return "EdmException occurred: " + e.getMessage();
    }
  }
}
