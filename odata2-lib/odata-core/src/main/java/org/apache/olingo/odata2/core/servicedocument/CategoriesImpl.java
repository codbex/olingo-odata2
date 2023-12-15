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
package org.apache.olingo.odata2.core.servicedocument;

import java.util.List;

import org.apache.olingo.odata2.api.servicedocument.Categories;
import org.apache.olingo.odata2.api.servicedocument.Category;
import org.apache.olingo.odata2.api.servicedocument.Fixed;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoriesImpl.
 */
public class CategoriesImpl implements Categories {
  
  /** The href. */
  private String href;
  
  /** The fixed. */
  private Fixed fixed;
  
  /** The scheme. */
  private String scheme;
  
  /** The category list. */
  private List<Category> categoryList;

  /**
   * Gets the href.
   *
   * @return the href
   */
  @Override
  public String getHref() {
    return href;
  }

  /**
   * Gets the fixed.
   *
   * @return the fixed
   */
  @Override
  public Fixed getFixed() {
    return fixed;
  }

  /**
   * Gets the scheme.
   *
   * @return the scheme
   */
  @Override
  public String getScheme() {
    return scheme;
  }

  /**
   * Gets the category list.
   *
   * @return the category list
   */
  @Override
  public List<Category> getCategoryList() {
    return categoryList;
  }

  /**
   * Sets the href.
   *
   * @param href the href
   * @return the categories impl
   */
  public CategoriesImpl setHref(final String href) {
    this.href = href;
    return this;
  }

  /**
   * Sets the fixed.
   *
   * @param fixed the fixed
   * @return the categories impl
   */
  public CategoriesImpl setFixed(final Fixed fixed) {
    this.fixed = fixed;
    return this;
  }

  /**
   * Sets the scheme.
   *
   * @param scheme the scheme
   * @return the categories impl
   */
  public CategoriesImpl setScheme(final String scheme) {
    this.scheme = scheme;
    return this;
  }

  /**
   * Sets the category list.
   *
   * @param categoryList the category list
   * @return the categories impl
   */
  public CategoriesImpl setCategoryList(final List<Category> categoryList) {
    this.categoryList = categoryList;
    return this;
  }

}
