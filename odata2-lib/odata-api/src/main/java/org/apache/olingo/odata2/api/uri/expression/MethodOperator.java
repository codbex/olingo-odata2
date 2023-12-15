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
 * Enumerations for all supported methods of the ODATA expression parser
 * for ODATA version 2.0 (with some restrictions).
 * 
 */
public enum MethodOperator {
  
  /** The endswith. */
  ENDSWITH("endswith"), 
 /** The indexof. */
 INDEXOF("indexof"), 
 /** The startswith. */
 STARTSWITH("startswith"), 
 /** The tolower. */
 TOLOWER("tolower"), 
 /** The toupper. */
 TOUPPER("toupper"), 
 /** The trim. */
 TRIM(
      "trim"), 
 /** The substring. */
 SUBSTRING("substring"), 
 /** The substringof. */
 SUBSTRINGOF("substringof"), 
 /** The concat. */
 CONCAT("concat"), 
 /** The length. */
 LENGTH("length"), 
 /** The year. */
 YEAR("year"),
  
  /** The month. */
  MONTH("month"), 
 /** The day. */
 DAY("day"), 
 /** The hour. */
 HOUR("hour"), 
 /** The minute. */
 MINUTE("minute"), 
 /** The second. */
 SECOND("second"), 
 /** The round. */
 ROUND("round"), 
 /** The floor. */
 FLOOR("floor"),
  
  /** The ceiling. */
  CEILING("ceiling"), 
 /** The replace. */
 REPLACE("replace");

  /** The syntax. */
  private String syntax;
  
  /** The string respresentation. */
  private String stringRespresentation;

  /**
   * Instantiates a new method operator.
   *
   * @param syntax the syntax
   */
  private MethodOperator(final String syntax) {
    this.syntax = syntax;
    stringRespresentation = syntax;
  }

  /**
   * To string.
   *
   * @return Operators name for usage in in text
   */
  @Override
  public String toString() {
    return stringRespresentation;
  }

  /**
   * To uri literal.
   *
   * @return URI literal of the unary operator as used in the URL.
   */
  public String toUriLiteral() {
    return syntax;
  }

}
