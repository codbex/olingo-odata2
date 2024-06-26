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
package org.apache.olingo.odata2.api.uri.expression;

import org.apache.olingo.odata2.api.edm.EdmTyped;

// TODO: Auto-generated Javadoc
/**
 * Represents a property expression in the expression tree
 * <br>
 * <br>
 * <p>A property expression node is inserted in the expression tree for any property.
 * If an EDM is available during parsing the property is automatically verified
 * against the EDM.
 * <br>
 * <br>
 * 
 */
public interface PropertyExpression extends CommonExpression {
  
  /**
   * Gets the property name.
   *
   * @return the property name as used in the EDM
   */
  public String getPropertyName();

  /**
   * Gets the edm property.
   *
   * @return Returns the EDM property matching the property name used in the expression String.
   * This may be an instance of EdmProperty or EdmNavigationProperty
   * @see EdmTyped
   */
  public EdmTyped getEdmProperty();

}
