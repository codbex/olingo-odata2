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

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface JPAPaging.
 */
public interface JPAPaging {
  
  /**
   * Gets the page size.
   *
   * @return the page size
   */
  public int getPageSize();

  /**
   * Gets the paged entities.
   *
   * @return the paged entities
   */
  public List<Object> getPagedEntities();

  /**
   * Gets the next page.
   *
   * @return the next page
   */
  public int getNextPage();

  /**
   * Gets the start page.
   *
   * @return the start page
   */
  public int getStartPage();

}
