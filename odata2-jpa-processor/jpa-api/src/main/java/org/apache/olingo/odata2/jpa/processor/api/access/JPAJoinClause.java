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

// TODO: Auto-generated Javadoc
/**
 * A container for Java Persistence Join Clause that can be used for building
 * JPQL statements. The container has two main elements <b>
 * <ol>
 * <li>Java Persistence Entity -</li> is the source entity participating in the
 * join. <br>
 * <li>Java Persistence Entity Relationship -</li> is the entity relationship of
 * the source entity participating in the join.
 * </ol>
 * </b>
 * 
 * 
 * 
 */
public class JPAJoinClause {

  /**
   * Enumerated list of possible Joins in JPQL
   * <ol>
   * <li>LEFT - left outer join</li>
   * <li>FETCH - enable fetching of an association as a side effect of the
   * execution of a query</li>
   * <li>INNER - inner join
   * </ol>.
   */
  public enum JOIN {
    
    /** The left. */
    LEFT, 
 /** The fetch. */
 FETCH, 
 /** The inner. */
 INNER
  }

  /** The entity name. */
  private String entityName;
  
  /** The entity alias. */
  private String entityAlias;
  
  /** The entity relation ship. */
  private String entityRelationShip;
  
  /** The entity relation ship alias. */
  private String entityRelationShipAlias;
  
  /** The join type. */
  private JOIN joinType;
  
  /** The join condition. */
  private String joinCondition;

  /**
   * The method returns Java Persistence Entity participating in the join.
   * 
   * @return an entity name
   */
  public String getEntityName() {
    return entityName;
  }

  /**
   * The method returns Java Persistence Entity alias name participating in
   * the join.
   * 
   * @return a entity alias name
   */
  public String getEntityAlias() {
    return entityAlias;
  }

  /**
   * The method returns Java Persistence Entity Relationship name
   * participating in the join.
   * 
   * @return entity alias relationship
   */
  public String getEntityRelationShip() {
    return entityRelationShip;
  }

  /**
   * The method returns Java Persistence Entity Relationship Alias name
   * participating in the join.
   * 
   * @return entity entity relationship alias
   */
  public String getEntityRelationShipAlias() {
    return entityRelationShipAlias;
  }

  /**
   * Constructor for creating elements of JPA Join Clause container.
   * 
   * @param entityName
   * is the name of the JPA entity participating in the join
   * @param entityAlias
   * is the alias for the JPA entity participating in the join
   * @param entityRelationShip
   * is the name of the JPA entity relationship participating in
   * the join
   * @param entityRelationShipAlias
   * is the alias name of the JPA entity relationship participating
   * in the join
   * @param joinCondition
   * is the condition on which the joins should occur
   * @param joinType
   * is the type of join {@link org.apache.olingo.odata2.jpa.processor.api.access.JPAJoinClause.JOIN} to execute
   */
  public JPAJoinClause(final String entityName, final String entityAlias, final String entityRelationShip,
      final String entityRelationShipAlias, final String joinCondition, final JOIN joinType) {

    this.entityName = entityName;
    this.entityAlias = entityAlias;
    this.entityRelationShip = entityRelationShip;
    this.entityRelationShipAlias = entityRelationShipAlias;
    this.joinCondition = joinCondition;
    this.joinType = joinType;
  }

  /**
   * The method returns a join condition that can be used for building JPQL
   * join statements.
   * 
   * @return a join condition
   */
  public String getJoinCondition() {
    return joinCondition;
  }

  /**
   * The method returns the type of {@link org.apache.olingo.odata2.jpa.processor.api.access.JPAJoinClause.JOIN} that
   * can be used for building JPQL join statements.
   * 
   * @return join type
   */
  public JOIN getJoinType() {
    return joinType;
  }

}
