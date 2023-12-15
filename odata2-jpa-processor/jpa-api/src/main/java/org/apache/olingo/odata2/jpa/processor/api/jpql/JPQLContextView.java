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
package org.apache.olingo.odata2.jpa.processor.api.jpql;

// TODO: Auto-generated Javadoc
/**
 * The interface provides a view on JPQL Context. The view can be used to access
 * different JPQL context type implementations.
 * 
 * 
 * @see org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType
 * @see org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType
 */
public interface JPQLContextView {
  /**
   * The method returns a JPA entity name for which the JPQL context is
   * relevant.
   * 
   * @return JPA entity name
   */
  public String getJPAEntityName();

  /**
   * The method returns a JPA entity alias name for which the JPQL context is
   * relevant.
   * 
   * @return JPA entity alias name
   */

  public String getJPAEntityAlias();

  /**
   * The method returns a JPQL context type.
   *
   * @return an instance of type {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType}
   */
  public JPQLContextType getType();
  
  /**
   * Set the JPQL Statement to the context.
   *
   * @param jpqlStatement the new JPQL statement
   */
  public void setJPQLStatement(String jpqlStatement);
  
  /**
   * Return the JPQL Statement set in context.
   *
   * @return String JPQLStatement
   */
  public String getJPQLStatement();
}
