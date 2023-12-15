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

import java.util.Collections;
import java.util.List;

import org.apache.olingo.odata2.api.servicedocument.CommonAttributes;
import org.apache.olingo.odata2.api.servicedocument.ExtensionAttribute;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonAttributesImpl.
 */
public class CommonAttributesImpl implements CommonAttributes {
  
  /** The base. */
  private String base;
  
  /** The lang. */
  private String lang;
  
  /** The attributes. */
  private List<ExtensionAttribute> attributes;

  /**
   * Gets the base.
   *
   * @return the base
   */
  @Override
  public String getBase() {
    return base;
  }

  /**
   * Gets the lang.
   *
   * @return the lang
   */
  @Override
  public String getLang() {
    return lang;
  }

  /**
   * Gets the attributes.
   *
   * @return the attributes
   */
  @Override
  public List<ExtensionAttribute> getAttributes() {
    return Collections.unmodifiableList(attributes);
  }

  /**
   * Sets the base.
   *
   * @param base the base
   * @return the common attributes impl
   */
  public CommonAttributesImpl setBase(final String base) {
    this.base = base;
    return this;
  }

  /**
   * Sets the lang.
   *
   * @param lang the lang
   * @return the common attributes impl
   */
  public CommonAttributesImpl setLang(final String lang) {
    this.lang = lang;
    return this;
  }

  /**
   * Sets the attributes.
   *
   * @param attributes the attributes
   * @return the common attributes impl
   */
  public CommonAttributesImpl setAttributes(final List<ExtensionAttribute> attributes) {
    this.attributes = attributes;
    return this;
  }

}
