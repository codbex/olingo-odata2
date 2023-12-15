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
package org.apache.olingo.odata2.api.ep.callback;

import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;

// TODO: Auto-generated Javadoc
/**
 * A {@link ReadResult} represents an inlined navigation property which points to an entry or feed.
 * The {@link ReadResult} contains the {@link EntityProviderReadProperties} which were used for read,
 * the <code>navigationPropertyName</code>, the read/de-serialized inlined entity and information whether the inlined
 * content
 * is a <code>feed</code> (multiplicity of <code>1..m</code>) or a single <code>entry</code> (multiplicity of
 * <code>0..1</code> or <code>1..1</code>).
 * If inlined navigation property is <code>nullable</code> the {@link ReadResult} has the
 * <code>navigationPropertyName</code> and a <code>NULL</code> entry set.
 * 
 * 
 */
public abstract class ReadResult {

  /** The read properties. */
  protected final EntityProviderReadProperties readProperties;
  
  /** The navigation property. */
  protected final EdmNavigationProperty navigationProperty;

  /**
   * Constructor.
   * Parameters <b>MUST NOT BE NULL</b>.
   * 
   * @param readProperties read properties which are used to read enclosing parent entity
   * @param navigationProperty emd navigation property information of found inline navigation property
   */
  public ReadResult(final EntityProviderReadProperties readProperties, final EdmNavigationProperty navigationProperty) {
    this.readProperties = readProperties;
    this.navigationProperty = navigationProperty;
  }

  /**
   * Gets the read properties.
   *
   * @return read properties which were used to read enclosing parent entity
   */
  public EntityProviderReadProperties getReadProperties() {
    return readProperties;
  }

  /**
   * Gets the navigation property.
   *
   * @return emd navigation property information of found inline navigation property
   */
  public EdmNavigationProperty getNavigationProperty() {
    return navigationProperty;
  }

  /**
   * Common access method to read result.
   * 
   * @return an {@link org.apache.olingo.odata2.api.ep.entry.ODataEntry ODataEntry} for the case of an single read entry
   * or a list of {@link org.apache.olingo.odata2.api.ep.entry.ODataEntry ODataEntry} in the case of an read feed.
   */
  public abstract Object getResult();

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return this.getClass().getSimpleName() + " [readProperties=" + readProperties + ", navigationProperty="
        + navigationProperty + "]";
  }

  /**
   * Return whether this entry is a <code>feed</code> (multiplicity of <code>1..m</code>)
   * or a single <code>entry</code> (multiplicity of <code>0..1</code> or <code>1..1</code>).
   * 
   * @return <code>true</code> for a feed and <code>false</code> for an entry
   */
  public abstract boolean isFeed();
}
