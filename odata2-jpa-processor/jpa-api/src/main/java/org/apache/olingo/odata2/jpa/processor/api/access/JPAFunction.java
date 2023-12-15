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
package org.apache.olingo.odata2.jpa.processor.api.access;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

// TODO: Auto-generated Javadoc
/**
 * A container for JPA Functions. A JPA function can be
 * <ol>
 * <li>Property Access method</li>
 * <li>Custom Operation (Annotated with EDM Annotation FunctionImport)</li>
 * </ol>
 * 
 * 
 * 
 */
public class JPAFunction {

  /** The function. */
  private Method function;
  
  /** The parameter types. */
  private Class<?>[] parameterTypes;
  
  /** The return type. */
  private Type returnType;
  
  /** The args. */
  private Object[] args;

  /**
   * Instantiates a new JPA function.
   *
   * @param function the function
   * @param parameterTypes the parameter types
   * @param returnType the return type
   * @param args the args
   */
  public JPAFunction(final Method function, final Class<?>[] parameterTypes, final Type returnType,
      final Object[] args) {
    this.function = function;
    this.parameterTypes = parameterTypes;
    this.returnType = returnType;
    this.args = args;
  }

  /**
   * The method returns the Java method.
   * 
   * @return an instance of {@link java.lang.reflect.Method}
   */
  public Method getFunction() {
    return function;
  }

  /**
   * The method returns the parameter types for the Java method.
   * 
   * @return an array of type {@link java.lang.Class}
   */
  public Class<?>[] getParameterTypes() {
    return parameterTypes;
  }

  /**
   * The method returns the return type for the Java method.
   * 
   * @return an instance of {@link java.lang.reflect.Type}
   */
  public Type getReturnType() {
    return returnType;
  }

  /**
   * The method returns an array of arguments for invoking the Java method.
   * 
   * @return an array of Objects
   */
  public Object[] getArguments() {
    return args;
  }

}
