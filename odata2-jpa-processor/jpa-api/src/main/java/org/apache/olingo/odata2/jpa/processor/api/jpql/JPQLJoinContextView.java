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

import java.util.List;

import org.apache.olingo.odata2.jpa.processor.api.access.JPAJoinClause;

// TODO: Auto-generated Javadoc
/**
 * The interface provide a view on JPQL Join context.The interface provides
 * methods for accessing the Join Clause which can be part of JPQL Select
 * statement. The interface extends the JPQL Select Context to add JQPL Join
 * clauses to the Select statement. The JPQL Join context view is built from
 * OData read entity set with navigation request.
 * 
 * 
 * @see org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLSelectContextView
 * 
 */
public interface JPQLJoinContextView extends JPQLSelectContextView {
  /**
   * The method returns a list of JPA Join Clauses. The returned list of
   * values can be used for building JPQL Statements with Join clauses.
   * 
   * @return a list of {@link org.apache.olingo.odata2.jpa.processor.api.access.JPAJoinClause}
   */
  public List<JPAJoinClause> getJPAJoinClauses();
}
