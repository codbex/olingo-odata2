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
package org.apache.olingo.odata2.jpa.processor.core.jpql;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement.JPQLStatementBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLSelectStatementBuilder.
 */
public class JPQLSelectStatementBuilder extends JPQLStatementBuilder {

  /** The jpql statement. */
  JPQLStatement jpqlStatement;
  
  /** The context. */
  private JPQLSelectContextView context;

  /**
   * Instantiates a new JPQL select statement builder.
   *
   * @param context the context
   */
  public JPQLSelectStatementBuilder(final JPQLContextView context) {
    this.context = (JPQLSelectContextView) context;
  }

  /**
   * Builds the.
   *
   * @return the JPQL statement
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public JPQLStatement build() throws ODataJPARuntimeException {
    jpqlStatement = createStatement(createJPQLQuery());
    this.context.setJPQLStatement(jpqlStatement.toString());
    return jpqlStatement;

  }

  /**
   * Creates the JPQL query.
   *
   * @return the string
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  private String createJPQLQuery() throws ODataJPARuntimeException {

    StringBuilder jpqlQuery = new StringBuilder();
    String tableAlias = context.getJPAEntityAlias();
    String fromClause = context.getJPAEntityName() + JPQLStatement.DELIMITER.SPACE + tableAlias;

    jpqlQuery.append(JPQLStatement.KEYWORD.SELECT).append(JPQLStatement.DELIMITER.SPACE);
    if (context.getType().equals(JPQLContextType.SELECT_COUNT)) { // $COUNT
      jpqlQuery.append(JPQLStatement.KEYWORD.COUNT).append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(JPQLStatement.DELIMITER.PARENTHESIS_LEFT).append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(context.getSelectExpression()).append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(JPQLStatement.DELIMITER.PARENTHESIS_RIGHT).append(JPQLStatement.DELIMITER.SPACE);
    } else {// Normal
      jpqlQuery.append(context.getSelectExpression()).append(JPQLStatement.DELIMITER.SPACE);
    }

    jpqlQuery.append(JPQLStatement.KEYWORD.FROM).append(JPQLStatement.DELIMITER.SPACE);
    jpqlQuery.append(fromClause);

    if (context.getWhereExpression() != null) {
      jpqlQuery.append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(JPQLStatement.KEYWORD.WHERE).append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(context.getWhereExpression());
    }

    if (context.getOrderByCollection() != null && context.getOrderByCollection().length() > 0) {

      StringBuilder orderByBuilder = new StringBuilder();
      orderByBuilder.append(context.getOrderByCollection());
      jpqlQuery.append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(JPQLStatement.KEYWORD.ORDERBY).append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(orderByBuilder);
    }

    return jpqlQuery.toString();

  }

}
