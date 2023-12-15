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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectContextView;
import org.apache.olingo.odata2.jpa.processor.core.ODataExpressionParser;

// TODO: Auto-generated Javadoc
/**
 * The Class JPQLSelectContext.
 */
public class JPQLSelectContext extends JPQLContext implements JPQLSelectContextView {

  /** The select expression. */
  protected String selectExpression;
  
  /** The order by collection. */
  protected String orderByCollection;
  
  /** The where condition. */
  protected String whereCondition;
  
  /** The parameterized query map. */
  protected Map<String, Map<Integer, Object>> parameterizedQueryMap;
  
  /** The jpql statement. */
  protected String jpqlStatement;

  /** The is count only. */
  protected boolean isCountOnly = false;// Support for $count

  /**
   * Instantiates a new JPQL select context.
   *
   * @param isCountOnly the is count only
   */
  public JPQLSelectContext(final boolean isCountOnly) {
    this.isCountOnly = isCountOnly;
  }

  /**
   * Sets the order by collection.
   *
   * @param orderByCollection the new order by collection
   */
  protected final void setOrderByCollection(final String orderByCollection) {
    this.orderByCollection = orderByCollection;
  }

  /**
   * Sets the where expression.
   *
   * @param filterExpression the new where expression
   */
  protected final void setWhereExpression(final String filterExpression) {
    whereCondition = filterExpression;
  }
  
  /**
   * Sets the parameterized query map.
   *
   * @param parameterizedQueryMap the parameterized query map
   */
  protected final void setParameterizedQueryMap(
      final Map<String, Map<Integer, Object>> parameterizedQueryMap) {
    if (null == this.parameterizedQueryMap || this.parameterizedQueryMap.isEmpty()) {
      this.parameterizedQueryMap = parameterizedQueryMap;
    } else {
      this.parameterizedQueryMap.putAll(parameterizedQueryMap);
    }
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
   * Gets the select expression.
   *
   * @return the select expression
   */
  @Override
  public String getSelectExpression() {
    return selectExpression;
  }

  /**
   * Gets the order by collection.
   *
   * @return the order by collection
   */
  @Override
  public String getOrderByCollection() {
    return orderByCollection;
  }

  /**
   * Gets the where expression.
   *
   * @return the where expression
   */
  @Override
  public String getWhereExpression() {
    return whereCondition;
  }

  /**
   * The Class JPQLSelectContextBuilder.
   */
  public class JPQLSelectContextBuilder extends
  org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContext.JPQLContextBuilder {

    /** The entity set view. */
    protected GetEntitySetUriInfo entitySetView;

    /**
     * Builds the.
     *
     * @return the JPQL context
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    @Override
    public JPQLContext build() throws ODataJPAModelException, ODataJPARuntimeException {
      if (entitySetView != null) {

        try {

          if (isCountOnly) {
            setType(JPQLContextType.SELECT_COUNT);
          } else {
            setType(JPQLContextType.SELECT);
          }

          if (withPaging) {
            isPagingRequested(withPaging);
          }

          EdmEntityType entityType = entitySetView.getTargetEntitySet().getEntityType();
          EdmMapping mapping = entityType.getMapping();
          if (mapping != null) {
            setJPAEntityName(mapping.getInternalName());
          } else {
            setJPAEntityName(entityType.getName());
          }

          setJPAEntityAlias(generateJPAEntityAlias());

          setOrderByCollection(generateOrderByFileds());

          setSelectExpression(generateSelectExpression());

          setWhereExpression(generateWhereExpression());
          
          setJPQLContext(JPQLSelectContext.this);
          
        } catch (ODataException e) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
        }
      }

      return JPQLSelectContext.this;

    }

    /**
     * Sets the results view.
     *
     * @param resultsView the new results view
     */
    @Override
    protected void setResultsView(final Object resultsView) {
      if (resultsView instanceof GetEntitySetUriInfo) {
        entitySetView = (GetEntitySetUriInfo) resultsView;
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

    /**
     * Generate order by fileds.
     *
     * @return the string
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     * @throws EdmException the edm exception
     */
    /*
     * Generate Order By Clause Fields
     */
    protected String generateOrderByFileds() throws ODataJPARuntimeException, EdmException {

      if (entitySetView.getOrderBy() != null  && !isCountOnly) {

        return ODataExpressionParser.parseToJPAOrderByExpression(entitySetView.getOrderBy(), getJPAEntityAlias());

      } else if ((entitySetView.getTop() != null || entitySetView.getSkip() != null ||
          pagingRequested) && !isCountOnly) {

        return ODataExpressionParser.parseKeyPropertiesToJPAOrderByExpression(entitySetView.getTargetEntitySet()
            .getEntityType().getKeyProperties(), getJPAEntityAlias());
      } else {
        return null;
      }

    }

    /**
     * Generate where expression.
     *
     * @return the string
     * @throws ODataException the o data exception
     */
    /*
     * Generate Where Clause Expression
     */
    protected String generateWhereExpression() throws ODataException {
      if (entitySetView.getFilter() != null) {
        String whereExpression = null;
        if (null != parameterizedQueryMap && !parameterizedQueryMap.isEmpty()) {
          int index = 1;
          int previousIndex = 1;
          for (Entry<String, Map<Integer, Object>> parameter : parameterizedQueryMap.entrySet()) {
            index = getIndexValue(parameter.getValue());
            if (index > previousIndex) {
              previousIndex = index;
            }
          }
          whereExpression = ODataExpressionParser.parseToJPAWhereExpression(
              entitySetView.getFilter(), getJPAEntityAlias(), 
              previousIndex, new ConcurrentHashMap<Integer, Object>(), null);
        } else {
          whereExpression = ODataExpressionParser.parseToJPAWhereExpression(
              entitySetView.getFilter(), getJPAEntityAlias());
        }
        Map<String, Map<Integer, Object>> parameterizedExpressionMap = 
            new HashMap<String, Map<Integer,Object>>();
        parameterizedExpressionMap.put(whereExpression, 
            ODataExpressionParser.getPositionalParametersThreadLocal());
        setParameterizedQueryMap(parameterizedExpressionMap);
        return whereExpression;
      }
      return null;
    }
  }
  
  /**
   * Gets the index value.
   *
   * @param map the map
   * @return the index value
   */
  private int getIndexValue(Map<Integer, Object> map) {
    int index = 1;
    if (map != null) {
      for (Entry<Integer, Object> entry : map.entrySet()) {
        index = entry.getKey();
      }
      return index + 1;
    } else {
      return index;
    }
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