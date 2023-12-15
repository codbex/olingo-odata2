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
import org.apache.olingo.odata2.api.uri.expression.MethodExpression;
import org.apache.olingo.odata2.api.uri.expression.MethodOperator;

// TODO: Auto-generated Javadoc
/**
 * The Class MethodExpressionImpl.
 */
public class MethodExpressionImpl implements MethodExpression {

  /** The info method. */
  private InfoMethod infoMethod;
  
  /** The return type. */
  private EdmType returnType;
  
  /** The actual parameters. */
  private List<CommonExpression> actualParameters;

  /**
   * Instantiates a new method expression impl.
   *
   * @param infoMethod the info method
   */
  public MethodExpressionImpl(final InfoMethod infoMethod) {
    this.infoMethod = infoMethod;
    returnType = infoMethod.getReturnType();
    actualParameters = new ArrayList<CommonExpression>();
  }

  /**
   * Gets the edm type.
   *
   * @return the edm type
   */
  @Override
  public EdmType getEdmType() {
    return returnType;
  }

  /**
   * Sets the edm type.
   *
   * @param edmType the edm type
   * @return the common expression
   */
  @Override
  public CommonExpression setEdmType(final EdmType edmType) {
    returnType = edmType;
    return this;
  }

  /**
   * Gets the method.
   *
   * @return the method
   */
  @Override
  public MethodOperator getMethod() {
    return infoMethod.getMethod();
  }

  /**
   * Gets the method info.
   *
   * @return the method info
   */
  public InfoMethod getMethodInfo() {
    return infoMethod;
  }

  /**
   * Gets the parameters.
   *
   * @return the parameters
   */
  @Override
  public List<CommonExpression> getParameters() {
    return actualParameters;
  }

  /**
   * Gets the parameter count.
   *
   * @return the parameter count
   */
  @Override
  public int getParameterCount() {
    return actualParameters.size();
  }

  /**
   * Append parameter.
   *
   * @param expression the expression
   * @return A self reference for method chaining"
   */
  public MethodExpressionImpl appendParameter(final CommonExpression expression) {
    actualParameters.add(expression);
    return this;
  }

  /**
   * Gets the kind.
   *
   * @return the kind
   */
  @Override
  public ExpressionKind getKind() {
    return ExpressionKind.METHOD;
  }

  /**
   * Gets the uri literal.
   *
   * @return the uri literal
   */
  @Override
  public String getUriLiteral() {
    return infoMethod.getSyntax();
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
    for (CommonExpression parameter : actualParameters) {
      Object retParameter = parameter.accept(visitor);
      retParameters.add(retParameter);
    }

    Object ret = visitor.visitMethod(this, getMethod(), retParameters);
    return ret;
  }

}
