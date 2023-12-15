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

import org.apache.olingo.odata2.api.servicedocument.Category;
import org.apache.olingo.odata2.api.servicedocument.CommonAttributes;

// TODO: Auto-generated Javadoc
/**
 * The Class CategoryImpl.
 */
public class CategoryImpl implements Category {
  
  /** The scheme. */
  private String scheme;
  
  /** The term. */
  private String term;
  
  /** The common attributes. */
  private CommonAttributes commonAttributes;
  
  /** The label. */
  private String label;

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
   * Gets the term.
   *
   * @return the term
   */
  @Override
  public String getTerm() {
    return term;
  }

  /**
   * Gets the common attributes.
   *
   * @return the common attributes
   */
  @Override
  public CommonAttributes getCommonAttributes() {
    return commonAttributes;
  }

  /**
   * Gets the label.
   *
   * @return the label
   */
  @Override
  public String getLabel() {
    return label;
  }

  /**
   * Sets the scheme.
   *
   * @param scheme the scheme
   * @return the category impl
   */
  public CategoryImpl setScheme(final String scheme) {
    this.scheme = scheme;
    return this;
  }

  /**
   * Sets the term.
   *
   * @param term the term
   * @return the category impl
   */
  public CategoryImpl setTerm(final String term) {
    this.term = term;
    return this;
  }

  /**
   * Sets the common attributes.
   *
   * @param commonAttribute the common attribute
   * @return the category impl
   */
  public CategoryImpl setCommonAttributes(final CommonAttributes commonAttribute) {
    commonAttributes = commonAttribute;
    return this;
  }

  /**
   * Sets the label.
   *
   * @param label the label
   * @return the category impl
   */
  public CategoryImpl setLabel(final String label) {
    this.label = label;
    return this;
  }
}
