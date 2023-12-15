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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.api.uri.NavigationSegment;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAJoinClause;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLJoinSelectSingleContextView;
import org.apache.olingo.odata2.jpa.processor.core.ODataExpressionParser;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLJoinSelectSingleContext.
 */
public class JPQLJoinSelectSingleContext extends JPQLSelectSingleContext implements JPQLJoinSelectSingleContextView {

  /** The jpa join clauses. */
  private List<JPAJoinClause> jpaJoinClauses = null;

  /**
   * Sets the JPA join clause.
   *
   * @param jpaJoinClauses the new JPA join clause
   */
  protected void setJPAJoinClause(final List<JPAJoinClause> jpaJoinClauses) {
    this.jpaJoinClauses = jpaJoinClauses;
  }

  /**
   * The Class JPQLJoinSelectSingleContextBuilder.
   */
  public class JPQLJoinSelectSingleContextBuilder extends JPQLSelectSingleContextBuilder {

    /** The relation ship alias counter. */
    protected int relationShipAliasCounter = 0;

    /**
     * Builds the.
     *
     * @return the JPQL context
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    @Override
    public JPQLContext build() throws ODataJPAModelException, ODataJPARuntimeException {
      try {
        setType(JPQLContextType.JOIN_SINGLE);
        setJPAJoinClause(generateJoinClauses());

        if (!jpaJoinClauses.isEmpty()) {
          JPAJoinClause joinClause = jpaJoinClauses.get(jpaJoinClauses.size() - 1);
          setJPAEntityName(joinClause.getEntityName());
          setJPAEntityAlias(joinClause.getEntityRelationShipAlias());
        }

        setKeyPredicates(entityView.getKeyPredicates());

        setSelectExpression(generateSelectExpression());
        
        setJPQLContext(JPQLJoinSelectSingleContext.this);
        
      } catch (EdmException e) {
        throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL, e);
      }

      return JPQLJoinSelectSingleContext.this;
    }

    /**
     * Generate join clauses.
     *
     * @return the list
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     * @throws EdmException the edm exception
     */
    protected List<JPAJoinClause> generateJoinClauses() throws ODataJPARuntimeException, EdmException {

      List<JPAJoinClause> jpaOuterJoinClauses = new ArrayList<JPAJoinClause>();
      JPAJoinClause jpaOuterJoinClause = null;
      String joinCondition = null;
      Map<String, Map<Integer, Object>> parameterizedExpressionMap = 
          new HashMap<String, Map<Integer,Object>>();
      String entityAlias = generateJPAEntityAlias();
      joinCondition = ODataExpressionParser.parseKeyPredicates(entityView.getKeyPredicates(), entityAlias);

      if (joinCondition != null) { 
        parameterizedExpressionMap.put(joinCondition, 
            ODataExpressionParser.getPositionalParametersThreadLocal());
      }
      
      EdmEntityType entityType = entityView.getStartEntitySet().getEntityType();
      Mapping mapping = (Mapping) entityType.getMapping();
      String entityTypeName = null;
      if (mapping != null) {
        entityTypeName = mapping.getInternalName();
      } else {
        entityTypeName = entityType.getName();
      }

      jpaOuterJoinClause =
          new JPAJoinClause(entityTypeName, entityAlias, null, null, joinCondition, JPAJoinClause.JOIN.INNER);

      jpaOuterJoinClauses.add(jpaOuterJoinClause);

      for (NavigationSegment navigationSegment : entityView.getNavigationSegments()) {

        EdmNavigationProperty navigationProperty = navigationSegment.getNavigationProperty();

        String relationShipAlias = generateRelationShipAlias();

        joinCondition =
            ODataExpressionParser.parseKeyPredicates(navigationSegment.getKeyPredicates(), relationShipAlias);

        if (joinCondition != null) { 
          parameterizedExpressionMap.put(joinCondition, 
              ODataExpressionParser.getPositionalParametersThreadLocal());
        }
        
        jpaOuterJoinClause =
            new JPAJoinClause(getFromEntityName(navigationProperty), entityAlias,
                getRelationShipName(navigationProperty), relationShipAlias, joinCondition, JPAJoinClause.JOIN.INNER);

        jpaOuterJoinClauses.add(jpaOuterJoinClause);

      }
      setParameterizedQueryMap(parameterizedExpressionMap);
      return jpaOuterJoinClauses;
    }

    /**
     * Gets the from entity name.
     *
     * @param navigationProperty the navigation property
     * @return the from entity name
     * @throws EdmException the edm exception
     */
    private String getFromEntityName(final EdmNavigationProperty navigationProperty) throws EdmException {

      String fromRole = navigationProperty.getFromRole();

      EdmEntityType fromEntityType = navigationProperty.getRelationship().getEnd(fromRole).getEntityType();

      EdmMapping mapping = fromEntityType.getMapping();

      String entityName = null;
      if (mapping != null) {
        entityName = mapping.getInternalName();
      } else {
        entityName = fromEntityType.getName();
      }

      return entityName;

    }

    /**
     * Gets the relation ship name.
     *
     * @param navigationProperty the navigation property
     * @return the relation ship name
     * @throws EdmException the edm exception
     */
    private String getRelationShipName(final EdmNavigationProperty navigationProperty) throws EdmException {

      EdmMapping mapping = navigationProperty.getMapping();

      String relationShipName = null;
      if (mapping != null) {
        relationShipName = mapping.getInternalName();
      } else {
        relationShipName = navigationProperty.getName();
      }

      return relationShipName;
    }

    /**
     * Generate relation ship alias.
     *
     * @return the string
     */
    private String generateRelationShipAlias() {
      return new String("R" + ++relationShipAliasCounter);
    }
  }

  /**
   * Gets the JPA join clauses.
   *
   * @return the JPA join clauses
   */
  @Override
  public List<JPAJoinClause> getJPAJoinClauses() {
    return jpaJoinClauses;
  }

}
