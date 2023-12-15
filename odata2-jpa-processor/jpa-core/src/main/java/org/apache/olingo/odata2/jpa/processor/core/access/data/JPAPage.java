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
package org.apache.olingo.odata2.jpa.processor.core.access.data;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Query;

import org.apache.olingo.odata2.jpa.processor.api.access.JPAPaging;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAPage.
 */
public class JPAPage implements JPAPaging {

  /** The page size. */
  private int pageSize;
  
  /** The start page. */
  private int startPage;
  
  /** The next page. */
  private int nextPage;
  
  /** The paged entries. */
  private List<Object> pagedEntries;

  /**
   * Instantiates a new JPA page.
   *
   * @param startPage the start page
   * @param nextPage the next page
   * @param pagedEntities the paged entities
   * @param pageSize the page size
   */
  protected JPAPage(final int startPage, final int nextPage, final List<Object> pagedEntities, final int pageSize) {
    this.pageSize = pageSize;
    this.startPage = startPage;
    this.nextPage = nextPage;
    pagedEntries = pagedEntities;
  }

  /**
   * Gets the page size.
   *
   * @return the page size
   */
  @Override
  public int getPageSize() {
    return pageSize;
  }

  /**
   * Gets the paged entities.
   *
   * @return the paged entities
   */
  @Override
  public List<Object> getPagedEntities() {
    return pagedEntries;
  }

  /**
   * Gets the next page.
   *
   * @return the next page
   */
  @Override
  public int getNextPage() {
    return nextPage;
  }

  /**
   * Gets the start page.
   *
   * @return the start page
   */
  @Override
  public int getStartPage() {
    return startPage;
  }

  /**
   * The Class JPAPageBuilder.
   */
  public static class JPAPageBuilder {

    /** The page size. */
    private int pageSize;
    
    /** The start page. */
    private int startPage;
    
    /** The next page. */
    private int nextPage;
    
    /** The top. */
    private int top = -1;
    
    /** The skip. */
    private int skip;
    
    /** The skip token. */
    private int skipToken;
    
    /** The query. */
    private Query query;
    
    /** The entities. */
    private List<Object> entities;
    
    /** The paged entities. */
    private List<Object> pagedEntities;

    /**
     * The Class TopSkip.
     */
    private static class TopSkip {
      
      /** The top. */
      private Integer top;
      
      /** The skip. */
      private Integer skip;
    }

    /**
     * Instantiates a new JPA page builder.
     */
    public JPAPageBuilder() {}

    /**
     * Page size.
     *
     * @param pageSize the page size
     * @return the JPA page builder
     */
    public JPAPageBuilder pageSize(final int pageSize) {
      this.pageSize = pageSize;
      return this;
    }

    /**
     * Query.
     *
     * @param query the query
     * @return the JPA page builder
     */
    public JPAPageBuilder query(final Query query) {
      this.query = query;
      return this;
    }

    /**
     * Builds the.
     *
     * @return the JPA page
     */
    public JPAPage build() {
      if (entities != null) {
        return buildFromEntities();
      } else {
        return buildFromQuery();
      }
    }

    /**
     * Builds the from entities.
     *
     * @return the JPA page
     */
    private JPAPage buildFromEntities() {
      TopSkip topSkip = formulateTopSkip();
      pagedEntities = new ArrayList<Object>();
      Integer top = topSkip.top;
      Integer skip = topSkip.skip;
      if (skip == null || topSkip.skip <= 0) {
        skip = 1;
      }
      if(top == null || topSkip.top <= 0){
        top = 0;
      }
      for (int i = skip - 1, j = 0; (j < top && i < entities.size()); j++) {
        pagedEntities.add(entities.get(i++));
      }
      formulateNextPage();
      return new JPAPage(startPage, nextPage, pagedEntities, pageSize);
    }

    /**
     * Builds the from query.
     *
     * @return the JPA page
     */
    @SuppressWarnings("unchecked")
    private JPAPage buildFromQuery() {
      TopSkip topSkip = formulateTopSkip();
      if(topSkip.skip != null){
        query.setFirstResult(topSkip.skip);
      }
      if(topSkip.top != null){
        query.setMaxResults(topSkip.top);
      }
      pagedEntities = query.getResultList();
      formulateNextPage();
      return new JPAPage(startPage, nextPage, pagedEntities, pageSize);
    }

    /**
     * Formulate top skip.
     *
     * @return the top skip
     */
    private TopSkip formulateTopSkip() {
      TopSkip topSkip = new TopSkip();
      int size = 0;
      if (pageSize <= 0) {
        if (skip > 0) {
          topSkip.skip = skip;
        }
        if (top > 0) {
          topSkip.top = top;
        }
      } else {
        if (skip >= pageSize) { // No Records to fetch
          startPage = skipToken;
          nextPage = 0;
        } else {
          // Max Results
          size = top + skip;
          if (size > pageSize) {
            if (skip == 0) {
              topSkip.top = pageSize;
            } else {
              topSkip.top = pageSize - skip;
            }
          } else {
            if (top > 0) {
              topSkip.top = top;
            } else {
              topSkip.top = pageSize;
            }
          }

          startPage = skipToken;
          if (skip > 0) {
            topSkip.skip = startPage + skip;
          } else {
            topSkip.skip = startPage;
          }
        }
      }
      return topSkip;
    }

    /**
     * Formulate next page.
     */
    private void formulateNextPage() {
      if (pagedEntities.isEmpty()) {
        nextPage = 0;
      } else if (pagedEntities.size() < pageSize) {
        nextPage = 0;
      } else {
        nextPage = startPage + pageSize;
      }
    }

    /**
     * Skip.
     *
     * @param skip the skip
     * @return the JPA page builder
     */
    public JPAPageBuilder skip(final int skip) {
      this.skip = skip;
      if (skip < 0) {
        this.skip = 0;
      } else {
        this.skip = skip;
      }
      return this;
    }

    /**
     * Skip token.
     *
     * @param skipToken the skip token
     * @return the JPA page builder
     * @throws NumberFormatException the number format exception
     */
    public JPAPageBuilder skipToken(final String skipToken) throws NumberFormatException {
      if (skipToken == null) {
        this.skipToken = 0;
      } else {
        this.skipToken = new Integer(skipToken).intValue();
        if (this.skipToken < 0) {
          this.skipToken = 0;
        }
      }

      return this;
    }

    /**
     * Top.
     *
     * @param top the top
     * @return the JPA page builder
     */
    public JPAPageBuilder top(final int top) {
      if (top < 0) {
        this.top = 0;
      } else {
        this.top = top;
      }
      return this;
    }

    /**
     * Entities.
     *
     * @param result the result
     * @return the JPA page builder
     */
    public JPAPageBuilder entities(final List<Object> result) {
      entities = result;
      return this;
    }
  }
}
