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
package org.apache.olingo.odata2.api.annotation.edm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * <p>Annotation for definition of an method as an {@link EdmFunctionImport} call/endpoint.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EdmFunctionImport {

  /**
   * <p>Annotation for definition of the return type of an {@link EdmFunctionImport} method.</p>
   */
  @interface ReturnType {
    /**
     * Concrete return types as specified in OData.
     */
    enum Type {
      
      /** The simple. */
      SIMPLE, 
 /** The entity. */
 ENTITY, 
 /** The complex. */
 COMPLEX
    }

    /**
     * Define the return type for the function import.
     * 
     * @return return type for the function import
     */
    Type type();

    /**
     * Define if the return type for the function import is a collection (entity set) or
     * an single entity (entity).
     * 
     * @return <code>true</code> if a collection is returned,
     * otherwise <code>false</code> if a single entity is returned.
     */
    boolean isCollection() default false;
  }

  /**
   * Concrete HttpMethods for a function import as specified in OData.
   */
  enum HttpMethod {
    
    /** The post. */
    POST, 
 /** The put. */
 PUT, 
 /** The get. */
 GET, 
 /** The merge. */
 MERGE, 
 /** The delete. */
 DELETE, 
 /** The patch. */
 PATCH
  };

  /**
   * Define the name for the function import.
   * If not set a default value has to be generated by the EDM provider.
   * 
   * @return name for the function import
   */
  String name() default "";

  /**
   * Define the name for the according entity set of the function import.
   * If not set a default value has to be generated by the EDM provider.
   * 
   * @return name for the according entity set of the function import
   */
  String entitySet() default "";

  /**
   * Define the return type of this function import.
   *
   * @return return type of this function import
   */
  ReturnType returnType();

  /**
   * Define the http method for which this function import is used
   * If not set the default http method <code>GET</code> is used.
   * 
   * @return http method for which this function import is used
   */
  HttpMethod httpMethod() default HttpMethod.GET;

  /**
   * Defines additional documentation for this function import.
   * 
   * @return additional documentation for this function import.
   */
  EdmDocumentation documentation() default @EdmDocumentation;
}