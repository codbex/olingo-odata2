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

// TODO: Auto-generated Javadoc
/**
 * A Category element
 * <p>Category element.
 */
public interface Category {
  
  /**
   * Get the scheme.
   *
   * @return scheme as String
   */
  public String getScheme();

  /**
   * Get the term.
   *
   * @return term as String
   */
  public String getTerm();

  /**
   * Get common attributes.
   *
   * @return {@link CommonAttributes}
   */
  public CommonAttributes getCommonAttributes();

  /**
   * Get the label.
   *
   * @return label as String
   */
  public String getLabel();
}
