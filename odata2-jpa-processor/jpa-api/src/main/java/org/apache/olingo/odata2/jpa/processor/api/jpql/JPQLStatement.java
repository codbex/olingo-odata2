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

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.factory.ODataJPAFactory;

// TODO: Auto-generated Javadoc
/**
 * The class represents a Java Persistence Query Language (JPQL) Statement.
 * The JPQL statement is built using a builder namely
 * {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement.JPQLStatementBuilder} . Based upon the JPQL
 * Context types ( {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType} different
 * kinds of JPQL statements are built.
 * The JPQL statements thus generated can be executed using JPA Query APIs to fetch JPA entities.
 * 
 * 
 * @see org.apache.olingo.odata2.jpa.processor.api.factory.JPQLBuilderFactory
 * @see org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView
 */
public class JPQLStatement {

  /** The statement. */
  protected String statement;

  /**
   * The method is used for creating an instance of JPQL Statement Builder for
   * building JPQL statements. The JPQL Statement builder is created based
   * upon the JPQL Context.
   *
   * @param context a non null value of {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextView} . The context is
   * expected to be set to be built with no
   * errors.
   * @return an instance of JPQL statement builder
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public static JPQLStatementBuilder createBuilder(final JPQLContextView context) throws ODataJPARuntimeException {
    return JPQLStatementBuilder.create(context);
  }

  /**
   * Instantiates a new JPQL statement.
   *
   * @param statement the statement
   */
  private JPQLStatement(final String statement) {
    this.statement = statement;
  }

  /**
   * The method provides a String representation of JPQLStatement.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return statement;
  }

  /**
   * The abstract class is extended by specific JPQL statement builders for
   * building JPQL statements like
   * <ol>
   * <li>Select statements</li>
   * <li>Select single statements</li>
   * <li>Select statements with Join</li>
   * <li>Insert/Modify/Delete statements</li>
   * </ol>
   * 
   * A default statement builder for building each kind of JPQL statements is
   * provided by the library.
   * 
   * 
   * 
   */
  public static abstract class JPQLStatementBuilder {

    /**
     * Instantiates a new JPQL statement builder.
     */
    protected JPQLStatementBuilder() {}

    /**
     * Creates the.
     *
     * @param context the context
     * @return the JPQL statement builder
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    private static final JPQLStatementBuilder create(final JPQLContextView context) throws ODataJPARuntimeException {
      return ODataJPAFactory.createFactory().getJPQLBuilderFactory().getStatementBuilder(context);
    }

    /**
     * Creates the statement.
     *
     * @param statement the statement
     * @return the JPQL statement
     */
    protected final JPQLStatement createStatement(final String statement) {
      return new JPQLStatement(statement);
    }

    /**
     * The abstract method is implemented by specific statement builder for
     * building JPQL Statement.
     * 
     * @return an instance of {@link org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement}
     * @throws ODataJPARuntimeException
     * in case there are errors building the statements
     */
    public abstract JPQLStatement build() throws ODataJPARuntimeException;

  }

  /**
   * The Class Operator.
   */
  public static final class Operator {
    
    /** The Constant EQ. */
    public static final String EQ = "=";
    
    /** The Constant NE. */
    public static final String NE = "<>";
    
    /** The Constant LT. */
    public static final String LT = "<";
    
    /** The Constant LE. */
    public static final String LE = "<=";
    
    /** The Constant GT. */
    public static final String GT = ">";
    
    /** The Constant GE. */
    public static final String GE = ">=";
    
    /** The Constant AND. */
    public static final String AND = "AND";
    
    /** The Constant NOT. */
    public static final String NOT = "NOT";
    
    /** The Constant OR. */
    public static final String OR = "OR";
    
    /** The Constant LIKE. */
    public static final String LIKE = "LIKE";

  }

  /**
   * The Class KEYWORD.
   */
  public static final class KEYWORD {
    
    /** The Constant SELECT. */
    public static final String SELECT = "SELECT";
    
    /** The Constant SELECT_DISTINCT. */
    public static final String SELECT_DISTINCT = "SELECT DISTINCT";
    
    /** The Constant FROM. */
    public static final String FROM = "FROM";
    
    /** The Constant WHERE. */
    public static final String WHERE = "WHERE";
    
    /** The Constant LEFT_OUTER_JOIN. */
    public static final String LEFT_OUTER_JOIN = "LEFT OUTER JOIN";
    
    /** The Constant OUTER. */
    public static final String OUTER = "OUTER";
    
    /** The Constant JOIN. */
    public static final String JOIN = "JOIN";
    
    /** The Constant ORDERBY. */
    public static final String ORDERBY = "ORDER BY";
    
    /** The Constant COUNT. */
    public static final String COUNT = "COUNT";
    
    /** The Constant OFFSET. */
    public static final String OFFSET = ".000";
    
    /** The Constant TIMESTAMP. */
    public static final String TIMESTAMP = "ts";

  }

  /**
   * The Class DELIMITER.
   */
  public static final class DELIMITER {
    
    /** The Constant SPACE. */
    public static final char SPACE = ' ';
    
    /** The Constant COMMA. */
    public static final char COMMA = ',';
    
    /** The Constant PERIOD. */
    public static final char PERIOD = '.';
    
    /** The Constant PARENTHESIS_LEFT. */
    public static final char PARENTHESIS_LEFT = '(';
    
    /** The Constant PARENTHESIS_RIGHT. */
    public static final char PARENTHESIS_RIGHT = ')';
    
    /** The Constant COLON. */
    public static final char COLON = ':';
    
    /** The Constant HYPHEN. */
    public static final char HYPHEN = '-';
    
    /** The Constant LEFT_BRACE. */
    public static final char LEFT_BRACE = '{';
    
    /** The Constant RIGHT_BRACE. */
    public static final char RIGHT_BRACE = '}';
    
    /** The Constant LONG. */
    public static final char LONG = 'L';
  }

}
