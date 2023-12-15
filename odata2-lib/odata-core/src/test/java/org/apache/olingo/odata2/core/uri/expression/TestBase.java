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

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.rt.RuntimeDelegate;
import org.apache.olingo.odata2.testutil.mock.TecEdmInfo;
import org.apache.olingo.odata2.testutil.mock.TechnicalScenarioEdmProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TestBase.
 */
public class TestBase {

  /** The edm info. */
  protected TecEdmInfo edmInfo = null;

  /**
   * Instantiates a new test base.
   */
  public TestBase() {
    final Edm edm = RuntimeDelegate.createEdm(new TechnicalScenarioEdmProvider());
    edmInfo = new TecEdmInfo(edm);
  }

  /**
   * Gets the PTF.
   *
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTF(final String expression) {
    return new ParserTool(expression, false, true, false, null);
  }

  /**
   * Gets the PT F only binary.
   *
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTF_onlyBinary(final String expression) {
    return new ParserTool(expression, false, true, true, null);
  }

  /**
   * Gets the PTFE.
   *
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTFE(final String expression) {
    return new ParserTool(expression, false, true, false, null);
  }

  /**
   * Gets the PTF.
   *
   * @param resourceEntityType the resource entity type
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTF(final EdmEntityType resourceEntityType, final String expression) {
    return new ParserTool(expression, false, true, false, resourceEntityType);
  }

  /**
   * Gets the PTO.
   *
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTO(final String expression) {
    return new ParserTool(expression, true, true, false, null);
  }

  /**
   * Gets the PTO.
   *
   * @param resourceEntityType the resource entity type
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTO(final EdmEntityType resourceEntityType, final String expression) {
    return new ParserTool(expression, true, true, false, resourceEntityType);
  }

  /**
   * Gets the PT F no TEST.
   *
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTF_noTEST(final String expression) {
    return new ParserTool(expression, false, false, false, null);
  }

  /**
   * Gets the PT F no TEST.
   *
   * @param resourceEntityType the resource entity type
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTF_noTEST(final EdmEntityType resourceEntityType, final String expression) {
    return new ParserTool(expression, false, false, true, resourceEntityType);
  }

  /**
   * Gets the PT O no TEST.
   *
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTO_noTEST(final String expression) {
    return new ParserTool(expression, true, false, true, null);
  }

  /**
   * Gets the PT O no TEST.
   *
   * @param resourceEntityType the resource entity type
   * @param expression the expression
   * @return the parser tool
   */
  static public ParserTool GetPTO_noTEST(final EdmEntityType resourceEntityType, final String expression) {
    return new ParserTool(expression, true, false, true, resourceEntityType);
  }

}
