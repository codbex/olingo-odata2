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
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectSingleContextView;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLSelectSingleContext.
 */
public class JPQLSelectSingleContext extends JPQLContext implements JPQLSelectSingleContextView {

  /** The select expression. */
  private String selectExpression;
  
  /** The key predicates. */
  private List<KeyPredicate> keyPredicates;
  
  /** The parameterized query map. */
  protected Map<String, Map<Integer, Object>> parameterizedQueryMap;
  
  /** The jpql statement. */
  protected String jpqlStatement;

  /**
   * Sets the key predicates.
   *
   * @param keyPredicates the new key predicates
   */
  protected void setKeyPredicates(final List<KeyPredicate> keyPredicates) {
    this.keyPredicates = keyPredicates;
  }

  /**
   * Gets the key predicates.
   *
   * @return the key predicates
   */
  @Override
  public List<KeyPredicate> getKeyPredicates() {
    return keyPredicates;
  }

  /**
   * Sets the select expression.
   *
   * @param selectExpression the new select expression
   */
  protected final void setSelectExpression(final String selectExpression) {
    this.selectExpression = selectExpression;
  }

  /**
   * Sets the parameterized query map.
   *
   * @param parameterizedQueryMap the parameterized query map
   */
  protected final void setParameterizedQueryMap(
      final Map<String, Map<Integer, Object>> parameterizedQueryMap) {
    this.parameterizedQueryMap = parameterizedQueryMap;
  }
  
  /**
   * Gets the select expression.
   *
   * @return the select expression
   */
  @Override
  public String getSelectExpression() {
    return selectExpression;
  }

  /**
   * Gets the parameterized query map.
   *
   * @return the parameterized query map
   */
  @Override
  public Map<String, Map<Integer, Object>> getParameterizedQueryMap() {
    return parameterizedQueryMap;
  }
  
  /**
   * The Class JPQLSelectSingleContextBuilder.
   */
  public class JPQLSelectSingleContextBuilder extends
  org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder {

    /** The entity view. */
    protected GetEntityUriInfo entityView;

    /**
     * Builds the.
     *
     * @return the JPQL context
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    @Override
    public JPQLContext build() throws ODataJPAModelException, ODataJPARuntimeException {
      if (entityView != null) {

        try {

          setType(JPQLContextType.SELECT_SINGLE);

          EdmEntityType entityType = entityView.getTargetEntitySet().getEntityType();
          EdmMapping mapping = entityType.getMapping();
          if (mapping != null) {
            setJPAEntityName(mapping.getInternalName());
          } else {
            setJPAEntityName(entityType.getName());
          }

          setJPAEntityAlias(generateJPAEntityAlias());

          setKeyPredicates(entityView.getKeyPredicates());

          setSelectExpression(generateSelectExpression());
          
          setJPQLContext(JPQLSelectSingleContext.this);
          
        } catch (EdmException e) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
        }

      }

      return JPQLSelectSingleContext.this;

    }

    /**
     * Sets the results view.
     *
     * @param resultsView the new results view
     */
    @Override
    protected void setResultsView(final Object resultsView) {
      if (resultsView instanceof GetEntityUriInfo) {
        entityView = (GetEntityUriInfo) resultsView;
      }

    }

    /**
     * Generate select expression.
     *
     * @return the string
     * @throws EdmException the edm exception
     */
    /*
     * Generate Select Clause
     */
    protected String generateSelectExpression() throws EdmException {
      return getJPAEntityAlias();
    }
  }

  /**
   * Sets the JPQL statement.
   *
   * @param jpqlStatement the new JPQL statement
   */
  @Override
  public void setJPQLStatement(String jpqlStatement) {
    this.jpqlStatement = jpqlStatement;
  }

  /**
   * Gets the JPQL statement.
   *
   * @return the JPQL statement
   */
  @Override
  public String getJPQLStatement() {
    return jpqlStatement;
  }
}
