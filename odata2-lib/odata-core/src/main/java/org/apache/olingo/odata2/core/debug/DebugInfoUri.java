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
package org.apache.olingo.odata2.core.debug;

import java.io.IOException;
import java.io.Writer;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.expression.BinaryExpression;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExceptionVisitExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionKind;
import org.apache.olingo.odata2.api.uri.expression.ExpressionParserException;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.MemberExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderExpression;
import org.apache.olingo.odata2.api.uri.expression.UnaryExpression;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;
import org.apache.olingo.odata2.core.uri.ExpandSelectTreeCreator;
import org.apache.olingo.odata2.core.uri.ExpandSelectTreeNodeImpl;
import org.apache.olingo.odata2.core.uri.expression.JsonVisitor;

// TODO: Auto-generated Javadoc
/**
 * URI parser debug information.
 */
public class DebugInfoUri implements DebugInfo {

  /** The uri info. */
  private final UriInfo uriInfo;
  
  /** The filter. */
  private final FilterExpression filter;
  
  /** The order by. */
  private final OrderByExpression orderBy;
  
  /** The expand select tree. */
  private final ExpandSelectTreeNodeImpl expandSelectTree;
  
  /** The exception. */
  private final ExpressionParserException exception;

  /**
   * Instantiates a new debug info uri.
   *
   * @param uriInfo the uri info
   * @param exception the exception
   */
  public DebugInfoUri(final UriInfo uriInfo, final ExpressionParserException exception) {
    this.uriInfo = uriInfo;
    filter = uriInfo == null ? null : uriInfo.getFilter();
    orderBy = uriInfo == null ? null : uriInfo.getOrderBy();
    expandSelectTree = uriInfo == null ? null : getExpandSelect();
    this.exception = exception;
  }

  /**
   * Gets the expand select.
   *
   * @return the expand select
   */
  private ExpandSelectTreeNodeImpl getExpandSelect() {
    try {
      return uriInfo.getExpand().isEmpty() && uriInfo.getSelect().isEmpty() ? null :
          new ExpandSelectTreeCreator(uriInfo.getSelect(), uriInfo.getExpand()).create();
    } catch (final EdmException e) {
      return null;
    }
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return "URI";
  }

  /**
   * Append json.
   *
   * @param jsonStreamWriter the json stream writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void appendJson(final JsonStreamWriter jsonStreamWriter) throws IOException {
    jsonStreamWriter.beginObject();

    if (exception != null && exception.getFilterTree() != null) {
      jsonStreamWriter.name("error")
          .beginObject()
          .namedStringValue("expression", exception.getFilterTree().getUriLiteral())
          .endObject();
      if (filter != null || orderBy != null || expandSelectTree != null) {
        jsonStreamWriter.separator();
      }
    }

    if (filter != null) {
      String filterString;
      try {
        filterString = (String) filter.accept(new JsonVisitor());
      } catch (final ExceptionVisitExpression e) {
        filterString = null;
      } catch (final ODataApplicationException e) {
        filterString = null;
      }
      jsonStreamWriter.name("filter").unquotedValue(filterString);
      if (orderBy != null || expandSelectTree != null) {
        jsonStreamWriter.separator();
      }
    }

    if (orderBy != null) {
      String orderByString;
      try {
        orderByString = (String) orderBy.accept(new JsonVisitor());
      } catch (final ExceptionVisitExpression e) {
        orderByString = null;
      } catch (final ODataApplicationException e) {
        orderByString = null;
      }
      jsonStreamWriter.name("orderby").unquotedValue(orderByString);
      if (expandSelectTree != null) {
        jsonStreamWriter.separator();
      }
    }

    if (expandSelectTree != null) {
      jsonStreamWriter.name("expandSelect").unquotedValue(expandSelectTree.toJsonString());
    }

    jsonStreamWriter.endObject();
  }

  /**
   * Append html.
   *
   * @param writer the writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void appendHtml(final Writer writer) throws IOException {
    if (exception != null && exception.getFilterTree() != null) {
      writer.append("<h2>Expression Information</h2>\n")
          .append("<pre class=\"code\">").append(exception.getFilterTree().getUriLiteral())
          .append("</pre>\n");
      // TODO: filter error position, filter tokens, filter tree
    }
    if (filter != null) {
      writer.append("<h2>Filter</h2>\n")
          .append("<ul class=\"expr\"><li>");
      appendExpression(filter.getExpression(), writer);
      writer.append("</li></ul>\n");
    }
    if (orderBy != null) {
      writer.append("<h2>Orderby</h2>\n")
          .append(orderBy.getOrdersCount() == 1 ? "<ul" : "<ol").append(" class=\"expr\">\n");
      for (final OrderExpression order : orderBy.getOrders()) {
        writer.append("<li>");
        appendExpression(order.getExpression(), writer);
        final ExpressionKind kind = order.getExpression().getKind();
        if (kind == ExpressionKind.PROPERTY || kind == ExpressionKind.LITERAL) {
          writer.append("<br />");
        }
        writer.append("<span class=\"order\">")
            .append(order.getSortOrder().toString())
            .append("</span></li>\n");
      }
      writer.append(orderBy.getOrdersCount() == 1 ? "</ul" : "</ol").append(">\n");
    }
    if (expandSelectTree != null) {
      writer.append("<h2>Expand/Select</h2>\n");
      appendExpandSelect(expandSelectTree, writer);
    }
  }

  /**
   * Append expression.
   *
   * @param expression the expression
   * @param writer the writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void appendExpression(final CommonExpression expression, final Writer writer) throws IOException {
    final ExpressionKind kind = expression.getKind();
    writer.append("<span class=\"kind\">")
        .append(kind.toString())
        .append("</span> <span class=\"literal\">")
        .append(kind == ExpressionKind.MEMBER ? ((MemberExpression) expression).getProperty().getUriLiteral() :
            expression.getUriLiteral())
        .append("</span>, type <span class=\"type\">")
        .append(expression.getEdmType().toString())
        .append("</span>");
    if (kind == ExpressionKind.UNARY) {
      writer.append("<ul class=\"expr\"><li>");
      appendExpression(((UnaryExpression) expression).getOperand(), writer);
      writer.append("</li></ul>");
    } else if (kind == ExpressionKind.BINARY) {
      writer.append("<ol class=\"expr\"><li>");
      appendExpression(((BinaryExpression) expression).getLeftOperand(), writer);
      writer.append("</li><li>");
      appendExpression(((BinaryExpression) expression).getRightOperand(), writer);
      writer.append("</li></ol>");
    } else if (kind == ExpressionKind.METHOD) {
      final MethodExpression methodExpression = (MethodExpression) expression;
      if (methodExpression.getParameterCount() > 0) {
        writer.append("<ol class=\"expr\">");
        for (final CommonExpression parameter : methodExpression.getParameters()) {
          writer.append("<li>");
          appendExpression(parameter, writer);
          writer.append("</li>");
        }
        writer.append("</ol>");
      }
    } else if (kind == ExpressionKind.MEMBER) {
      writer.append("<ul class=\"expr\"><li>");
      appendExpression(((MemberExpression) expression).getPath(), writer);
      writer.append("</li></ul>");
    }
  }

  /**
   * Append expand select.
   *
   * @param expandSelect the expand select
   * @param writer the writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void appendExpandSelect(final ExpandSelectTreeNode expandSelect, final Writer writer) throws IOException {
    writer.append("<ul class=\"expand\">\n")
        .append("<li>");
    if (expandSelect.isAll()) {
      writer.append("all properties");
    } else {
      for (final EdmProperty property : expandSelect.getProperties()) {
        try {
          writer.append("property <span class=\"prop\">")
              .append(property.getName())
              .append("</span><br />");
        } catch (final EdmException e) {}
      }
    }
    writer.append("</li>\n");
    if (!expandSelect.getLinks().isEmpty()) {
      for (final String name : expandSelect.getLinks().keySet()) {
        writer.append("<li>link <span class=\"link\">").append(name).append("</span>");
        final ExpandSelectTreeNode link = expandSelect.getLinks().get(name);
        if (link != null) {
          writer.append('\n');
          appendExpandSelect(link, writer);
        }
        writer.append("</li>\n");
      }
    }
    writer.append("</ul>\n");
  }
}
