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
package org.apache.olingo.odata2.api.servicedocument;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * A Categories element
 * <p>Categories element provides a list of the categories that can be applied to the members of a Collection
 * <p>If the "href" attribute is provided, the Categories element MUST be empty and MUST NOT have the "fixed" or
 * "scheme" attributes.
 */

public interface Categories {

  /**
   * Get the IRI reference identifying a Category Document.
   *
   * @return href as String
   */
  public String getHref();

  /**
   * Get the attribute fixed that indicates whether the list of categories is a fixed or an open set.
   *
   * @return {@link Fixed}
   */
  public Fixed getFixed();

  /**
   * Get the scheme.
   *
   * @return scheme as String
   */
  public String getScheme();

  /**
   * Get the list of categories.
   *
   * @return list of {@link Category}
   */
  public List<Category> getCategoryList();

}
