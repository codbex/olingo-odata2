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

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.uri.NavigationPropertySegment;

// TODO: Auto-generated Javadoc
/**
 * The Class NavigationPropertySegmentImpl.
 */
public class NavigationPropertySegmentImpl implements NavigationPropertySegment {

  /** The navigation property. */
  private EdmNavigationProperty navigationProperty;
  
  /** The target entity set. */
  private EdmEntitySet targetEntitySet;

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
   * @param navigationProperty the new navigation property
   */
  public void setNavigationProperty(final EdmNavigationProperty navigationProperty) {
    this.navigationProperty = navigationProperty;
  }

  /**
   * Gets the target entity set.
   *
   * @return the target entity set
   */
  @Override
  public EdmEntitySet getTargetEntitySet() {
    return targetEntitySet;
  }

  /**
   * Sets the target entity set.
   *
   * @param targetEntitySet the new target entity set
   */
  public void setTargetEntitySet(final EdmEntitySet targetEntitySet) {
    this.targetEntitySet = targetEntitySet;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Navigation Property: " + navigationProperty + ", Target Entity Set: " + targetEntitySet;
  }
}
