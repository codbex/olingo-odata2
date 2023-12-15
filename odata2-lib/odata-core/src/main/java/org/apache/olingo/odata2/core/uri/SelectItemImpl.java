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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.uri.NavigationPropertySegment;
import org.apache.olingo.odata2.api.uri.SelectItem;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectItemImpl.
 */
public class SelectItemImpl implements SelectItem {

  /** The navigation property segments. */
  private List<NavigationPropertySegment> navigationPropertySegments = Collections.emptyList();
  
  /** The property. */
  private EdmProperty property;
  
  /** The star. */
  private boolean star;

  /**
   * Checks if is star.
   *
   * @return true, if is star
   */
  @Override
  public boolean isStar() {
    return star;
  }

  /**
   * Sets the star.
   *
   * @param star the new star
   */
  public void setStar(final boolean star) {
    this.star = star;
  }

  /**
   * Gets the property.
   *
   * @return the property
   */
  @Override
  public EdmProperty getProperty() {
    return property;
  }

  /**
   * Sets the property.
   *
   * @param property the new property
   */
  public void setProperty(final EdmProperty property) {
    this.property = property;
  }

  /**
   * Adds the navigation property segment.
   *
   * @param segment the segment
   */
  public void addNavigationPropertySegment(final NavigationPropertySegment segment) {
    if (navigationPropertySegments.equals(Collections.EMPTY_LIST)) {
      navigationPropertySegments = new ArrayList<NavigationPropertySegment>();
    }

    navigationPropertySegments.add(segment);
  }

  /**
   * Gets the navigation property segments.
   *
   * @return the navigation property segments
   */
  @Override
  public List<NavigationPropertySegment> getNavigationPropertySegments() {
    return navigationPropertySegments;
  }

}
