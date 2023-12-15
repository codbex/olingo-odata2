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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.uri.expression.MethodOperator;

// TODO: Auto-generated Javadoc
/**
 * The Class FilterParserImplTool.
 */
public class FilterParserImplTool extends FilterParserImpl {

  /**
   * Instantiates a new filter parser impl tool.
   *
   * @param resourceEntityType the resource entity type
   */
  public FilterParserImplTool(final EdmEntityType resourceEntityType) {
    super(resourceEntityType);
  }

  /**
   * Adds the testfunctions.
   */
  public void addTestfunctions() {
    Map<String, InfoMethod> lAvailableMethods = new HashMap<String, InfoMethod>(availableMethods);
    ParameterSetCombination combination = null;
    // create type helpers

    // TESTING
    combination = new ParameterSetCombination.PSCflex();
    lAvailableMethods.put("testingMINMAX1",
        new InfoMethod(MethodOperator.CONCAT, "testingMINMAX1", -1, -1, combination));

    // TESTING
    combination = new ParameterSetCombination.PSCflex();
    lAvailableMethods
        .put("testingMINMAX2", new InfoMethod(MethodOperator.CONCAT, "testingMINMAX2", 0, -1, combination));

    // TESTING
    combination = new ParameterSetCombination.PSCflex();
    lAvailableMethods
        .put("testingMINMAX3", new InfoMethod(MethodOperator.CONCAT, "testingMINMAX3", 2, -1, combination));

    // TESTING
    combination = new ParameterSetCombination.PSCflex();
    lAvailableMethods
        .put("testingMINMAX4", new InfoMethod(MethodOperator.CONCAT, "testingMINMAX4", -1, 0, combination));

    // TESTING
    combination = new ParameterSetCombination.PSCflex();
    lAvailableMethods
        .put("testingMINMAX5", new InfoMethod(MethodOperator.CONCAT, "testingMINMAX5", -1, 2, combination));

    // TESTING
    combination = new ParameterSetCombination.PSCflex();
    lAvailableMethods.put("testingMINMAX6", new InfoMethod(MethodOperator.CONCAT, "testingMINMAX6", 1, 2, combination));

    // TESTING
    combination = new ParameterSetCombination.PSCflex();
    lAvailableMethods.put("testingMINMAX7", new InfoMethod(MethodOperator.CONCAT, "testingMINMAX7", 1, 1, combination));

    availableMethods = Collections.unmodifiableMap(lAvailableMethods);

  }

}
