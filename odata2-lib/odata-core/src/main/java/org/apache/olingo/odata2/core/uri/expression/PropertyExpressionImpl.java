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

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.uri.expression.CommonExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionKind;
import org.apache.olingo.odata2.api.uri.expression.ExpressionVisitor;
import org.apache.olingo.odata2.api.uri.expression.PropertyExpression;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyExpressionImpl.
 */
public class PropertyExpressionImpl implements PropertyExpression {
  
  /** The uri literal. */
  private String uriLiteral;
  
  /** The edm type. */
  private EdmType edmType;
  
  /** The edm property. */
  private EdmTyped edmProperty;
  
  /** The edm literal. */
  private EdmLiteral edmLiteral;

  /**
   * Instantiates a new property expression impl.
   *
   * @param uriLiteral the uri literal
   * @param edmLiteral the edm literal
   */
  public PropertyExpressionImpl(final String uriLiteral, final EdmLiteral edmLiteral) {
    this.uriLiteral = uriLiteral;

    this.edmLiteral = edmLiteral;
    if (edmLiteral != null) {
      edmType = edmLiteral.getType();
    }
  }

  /**
   * Sets the edm property.
   *
   * @param edmProperty the edm property
   * @return the common expression
   */
  public CommonExpression setEdmProperty(final EdmTyped edmProperty) {
    // used EdmTyped because it may be a EdmProperty or a EdmNavigationProperty
    this.edmProperty = edmProperty;
    return this;
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
   * Gets the property name.
   *
   * @return the property name
   */
  @Override
  public String getPropertyName() {
    if (edmProperty == null) {
      return "";
    }

    try {
      return edmProperty.getName();
    } catch (EdmException e) {
      return "";
    }
  }

  /**
   * Gets the edm literal.
   *
   * @return the edm literal
   */
  public EdmLiteral getEdmLiteral() {
    return edmLiteral;
  }

  /**
   * Gets the edm property.
   *
   * @return the edm property
   */
  @Override
  public EdmTyped getEdmProperty() {
    return edmProperty;
  }

  /**
   * Gets the kind.
   *
   * @return the kind
   */
  @Override
  public ExpressionKind getKind() {
    return ExpressionKind.PROPERTY;
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
   * Gets the edm type.
   *
   * @return the edm type
   */
  @Override
  public EdmType getEdmType() {
    return edmType;
  }

  /**
   * Accept.
   *
   * @param visitor the visitor
   * @return the object
   */
  @Override
  public Object accept(final ExpressionVisitor visitor) {
    Object ret = visitor.visitProperty(this, uriLiteral, edmProperty);
    return ret;
  }

}
