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

import org.apache.olingo.odata2.api.uri.expression.ExceptionVisitExpression;
import org.apache.olingo.odata2.api.uri.expression.ExpressionParserException;
import org.apache.olingo.odata2.testutil.helper.ODataMessageTextVerifier;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class TestExceptionTexts.
 */
public class TestExceptionTexts extends TestBase {
  
  /**
   * Test filter parser exception messages.
   */
  @Test
  public void TestFilterParserExceptionMessages() {
    ODataMessageTextVerifier.TestClass(ExpressionParserException.class);
  }

  /**
   * Test filter parser internal error messages.
   */
  @Test
  public void TestFilterParserInternalErrorMessages() {
    ODataMessageTextVerifier.TestClass(ExpressionParserInternalError.class);
  }

  /**
   * Test exception visit expression messages.
   */
  @Test
  public void TestExceptionVisitExpressionMessages() {
    ODataMessageTextVerifier.TestClass(ExceptionVisitExpression.class);
  }

  /**
   * Test exception tokenizer expect messages.
   */
  @Test
  public void TestExceptionTokenizerExpectMessages() {
    ODataMessageTextVerifier.TestClass(TokenizerExpectError.class);
  }

  /**
   * Test exception tokenizer messages.
   */
  @Test
  public void TestExceptionTokenizerMessages() {
    ODataMessageTextVerifier.TestClass(TokenizerException.class);
  }
}
