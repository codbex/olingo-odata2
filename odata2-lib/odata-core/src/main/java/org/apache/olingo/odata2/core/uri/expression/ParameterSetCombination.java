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

// TODO: Auto-generated Javadoc
/**
 * The Interface ParameterSetCombination.
 */
public interface ParameterSetCombination {

  /**
   * Adds the.
   *
   * @param parameterSet the parameter set
   */
  void add(ParameterSet parameterSet);

  /**
   * Validate.
   *
   * @param actualParameterTypes the actual parameter types
   * @return the parameter set
   * @throws ExpressionParserInternalError the expression parser internal error
   */
  ParameterSet validate(List<EdmType> actualParameterTypes) throws ExpressionParserInternalError;

  /**
   * Adds the first.
   *
   * @param parameterSet the parameter set
   */
  void addFirst(ParameterSet parameterSet);

  /**
   * Gets the return type.
   *
   * @return the return type
   */
  EdmType getReturnType();

  /**
   * The Class PSCflex.
   */
  public static class PSCflex implements ParameterSetCombination {
    
    /** The combinations. */
    private List<ParameterSet> combinations = new ArrayList<ParameterSet>();

    /**
     * Adds the.
     *
     * @param parameterSet the parameter set
     */
    @Override
    public void add(final ParameterSet parameterSet) {
      combinations.add(parameterSet);
    }

    /**
     * Gets the return type.
     *
     * @return the return type
     */
    @Override
    public EdmType getReturnType() {
      int parameterCount = combinations.size();
      if (parameterCount == 0) {
        return null;
      }

      if (parameterCount == 1) {
        return combinations.get(0).getReturnType();
      }

      // There are more than 1 possible return type, check if they are equal, if not return null.
      EdmType returnType = combinations.get(0).getReturnType();
      for (int i = 1; i < parameterCount; i++) {
        if (returnType != combinations.get(i)) {
          return null;
        }
      }

      return returnType;

    }

    /**
     * Adds the first.
     *
     * @param parameterSet the parameter set
     */
    @Override
    public void addFirst(final ParameterSet parameterSet) {
      List<ParameterSet> oldCombinations = combinations;
      combinations = new ArrayList<ParameterSet>();
      combinations.add(parameterSet);
      for (ParameterSet parameterSet1 : oldCombinations) {
        combinations.add(parameterSet1);
      }

    }

    /**
     * Validate.
     *
     * @param actualParameterTypes the actual parameter types
     * @return the parameter set
     * @throws ExpressionParserInternalError the expression parser internal error
     */
    @Override
    public ParameterSet validate(final List<EdmType> actualParameterTypes) throws ExpressionParserInternalError {
      if (combinations.isEmpty()) {
        return new ParameterSet(null, null);
      }

      // first check for exact parameter combination
      for (ParameterSet parameterSet : combinations) {
        boolean s = parameterSet.equals(actualParameterTypes, false);
        if (s) {
          return parameterSet;
        }
      }

      // second check for parameter combination with promotion
      for (ParameterSet parameterSet : combinations) {
        boolean s = parameterSet.equals(actualParameterTypes, true);
        if (s) {
          return parameterSet;
        }
      }
      return null;
    }

  }

  /**
   * The Class PSCReturnTypeEqLastParameter.
   */
  public static class PSCReturnTypeEqLastParameter implements ParameterSetCombination {

    /**
     * Adds the.
     *
     * @param parameterSet the parameter set
     */
    @Override
    public void add(final ParameterSet parameterSet) {
      throw new IllegalStateException();
    }

    /**
     * Adds the first.
     *
     * @param parameterSet the parameter set
     */
    @Override
    public void addFirst(final ParameterSet parameterSet) {
      throw new IllegalStateException();
    }

    /**
     * Validate.
     *
     * @param actualParameterTypes the actual parameter types
     * @return the parameter set
     * @throws ExpressionParserInternalError the expression parser internal error
     */
    @Override
    public ParameterSet validate(final List<EdmType> actualParameterTypes) throws ExpressionParserInternalError {
      EdmType xxx = actualParameterTypes.get(actualParameterTypes.size() - 1);
      return new ParameterSet(xxx, null);
    }

    /**
     * Gets the return type.
     *
     * @return the return type
     */
    @Override
    public EdmType getReturnType() {
      // If the return type is always the type of the last parameter of the actual parameters ( e.g. when using the
      // method operator)
      // then the return type can not predicted.
      return null;
    }
  }

}
