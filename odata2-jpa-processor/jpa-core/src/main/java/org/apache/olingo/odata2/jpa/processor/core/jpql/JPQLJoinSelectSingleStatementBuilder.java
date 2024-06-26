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

import java.util.List;

import org.apache.olingo.odata2.jpa.processor.api.access.JPAJoinClause;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLJoinSelectSingleContextView;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement.JPQLStatementBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLJoinSelectSingleStatementBuilder.
 */
public class JPQLJoinSelectSingleStatementBuilder extends JPQLStatementBuilder {

  /** The jpql statement. */
  JPQLStatement jpqlStatement;
  
  /** The context. */
  private JPQLJoinSelectSingleContextView context;

  /**
   * Instantiates a new JPQL join select single statement builder.
   *
   * @param context the context
   */
  public JPQLJoinSelectSingleStatementBuilder(final JPQLContextView context) {
    this.context = (JPQLJoinSelectSingleContextView) context;
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
    StringBuilder joinWhereCondition = null;

    jpqlQuery.append(JPQLStatement.KEYWORD.SELECT).append(JPQLStatement.DELIMITER.SPACE);
    jpqlQuery.append(context.getSelectExpression()).append(JPQLStatement.DELIMITER.SPACE);
    jpqlQuery.append(JPQLStatement.KEYWORD.FROM).append(JPQLStatement.DELIMITER.SPACE);

    if (context.getJPAJoinClauses() != null && !context.getJPAJoinClauses().isEmpty()) {
      List<JPAJoinClause> joinClauseList = context.getJPAJoinClauses();
      JPAJoinClause joinClause = joinClauseList.get(0);
      String joinCondition = joinClause.getJoinCondition();
      joinWhereCondition = new StringBuilder();
      if (joinCondition != null) {
        joinWhereCondition.append(joinCondition);
      }
      String relationShipAlias = null;
      joinClause = joinClauseList.get(1);
      jpqlQuery.append(joinClause.getEntityName()).append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(joinClause.getEntityAlias());

      int i = 1;
      int limit = joinClauseList.size();
      relationShipAlias = joinClause.getEntityAlias();
      while (i < limit) {
        jpqlQuery.append(JPQLStatement.DELIMITER.SPACE);
        jpqlQuery.append(JPQLStatement.KEYWORD.JOIN).append(JPQLStatement.DELIMITER.SPACE);

        joinClause = joinClauseList.get(i);
        jpqlQuery.append(relationShipAlias).append(JPQLStatement.DELIMITER.PERIOD);
        jpqlQuery.append(joinClause.getEntityRelationShip()).append(JPQLStatement.DELIMITER.SPACE);
        jpqlQuery.append(joinClause.getEntityRelationShipAlias());

        relationShipAlias = joinClause.getEntityRelationShipAlias();
        i++;

        joinCondition = joinClause.getJoinCondition();
        if (joinCondition != null) {
          joinWhereCondition.append(JPQLStatement.DELIMITER.SPACE + JPQLStatement.Operator.AND
              + JPQLStatement.DELIMITER.SPACE);

          joinWhereCondition.append(joinCondition);
        }

      }
    } else {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.JOIN_CLAUSE_EXPECTED, null);
    }

    if (joinWhereCondition.length() > 0) {
      jpqlQuery.append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(JPQLStatement.KEYWORD.WHERE).append(JPQLStatement.DELIMITER.SPACE);
      jpqlQuery.append(joinWhereCondition.toString());
    }

    return jpqlQuery.toString();

  }

}
