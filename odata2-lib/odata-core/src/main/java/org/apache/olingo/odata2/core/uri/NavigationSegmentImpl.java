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
package org.apache.olingo.odata2.core.uri;

import java.util.Collections;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.NavigationSegment;

// TODO: Auto-generated Javadoc
/**
 * The Class NavigationSegmentImpl.
 */
public class NavigationSegmentImpl implements NavigationSegment {

  /** The navigation property. */
  private EdmNavigationProperty navigationProperty;
  
  /** The target entity set. */
  private EdmEntitySet targetEntitySet;
  
  /** The key predicates. */
  private List<KeyPredicate> keyPredicates = Collections.emptyList();

  /**
   * Gets the key predicates.
   *
   * @return the key predicates
   */
  @Override
  public List<KeyPredicate> getKeyPredicates() {
    return keyPredicates;
  }

  /**
   * Sets the key predicates.
   *
   * @param keyPredicates the new key predicates
   */
  public void setKeyPredicates(final List<KeyPredicate> keyPredicates) {
    this.keyPredicates = keyPredicates;
  }

  /**
   * Gets the navigation property.
   *
   * @return the navigation property
   */
  @Override
  public EdmNavigationProperty getNavigationProperty() {
    return navigationProperty;
  }

  /**
   * Sets the navigation property.
   *
   * @param edmNavigationProperty the new navigation property
   */
  public void setNavigationProperty(final EdmNavigationProperty edmNavigationProperty) {
    navigationProperty = edmNavigationProperty;
  }

  /**
   * Gets the entity set.
   *
   * @return the entity set
   */
  @Override
  public EdmEntitySet getEntitySet() {
    return targetEntitySet;
  }

  /**
   * Sets the entity set.
   *
   * @param edmEntitySet the new entity set
   */
  public void setEntitySet(final EdmEntitySet edmEntitySet) {
    targetEntitySet = edmEntitySet;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Navigation Property: " + navigationProperty + ", "
        + "Target Entity Set: " + targetEntitySet + ", "
        + "Key Predicates: " + keyPredicates;
  }

}
