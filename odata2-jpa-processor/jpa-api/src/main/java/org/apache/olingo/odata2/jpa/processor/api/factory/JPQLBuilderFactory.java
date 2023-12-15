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
package org.apache.olingo.odata2.jpa.processor.api.factory;

import org.apache.olingo.odata2.jpa.processor.api.access.JPAMethodContext.JPAMethodContextBuilder;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement.JPQLStatementBuilder;

// TODO: Auto-generated Javadoc
/**
 * Factory interface for creating following instances
 * 
 * <p>
 * <ul>
 * <li>JPQL statement builders of type
 * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement.JPQLStatementBuilder} </li>
 * <li>JPQL context builder of type
 * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder} </li>
 * </ul>
 * </p>
 * 
 * 
 * @see org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory
 */
public interface JPQLBuilderFactory {
  /**
   * The method returns JPQL statement builder for building JPQL statements.
   * 
   * @param context
   * is {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext} that determines the type of JPQL statement
   * builder. The
   * parameter cannot be null.
   * @return an instance of JPQLStatementBuilder
   */
  public JPQLStatementBuilder getStatementBuilder(JPQLContextView context);

  /**
   * The method returns a JPQL context builder for building JPQL Context
   * object.
   * 
   * @param contextType
   * is {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType} that determines the type of JPQL context
   * builder. The
   * parameter cannot be null.
   * @return an instance of JPQLContextBuilder
   */
  public JPQLContextBuilder getContextBuilder(JPQLContextType contextType);

  /**
   * The method returns a JPA method context builder for building JPA Method
   * context object.
   * 
   * @param contextType
   * is {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType} that determines the type of JPQL context
   * builder. The
   * parameter cannot be null.
   * @return an instance of JPAMethodContextBuilder
   */
  public JPAMethodContextBuilder getJPAMethodContextBuilder(JPQLContextType contextType);
}
