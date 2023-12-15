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
package org.apache.olingo.odata2.jpa.processor.core.model;

import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATombstoneEntityListener;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmMappingImpl.
 */
public class JPAEdmMappingImpl extends Mapping implements JPAEdmMapping {

  /** The column name. */
  private String columnName = null;
  
  /** The type. */
  private Class<?> type = null;
  
  /** The entity listener. */
  private Class<? extends ODataJPATombstoneEntityListener> entityListener = null;
  
  /** The is virtual access. */
  private boolean isVirtualAccess;

  /**
   * Sets the JPA column name.
   *
   * @param name the new JPA column name
   */
  @Override
  public void setJPAColumnName(final String name) {
    columnName = name;

  }

  /**
   * Gets the JPA column name.
   *
   * @return the JPA column name
   */
  @Override
  public String getJPAColumnName() {
    return columnName;
  }

  /**
   * Sets the JPA type.
   *
   * @param type the new JPA type
   */
  @Override
  public void setJPAType(final Class<?> type) {
    this.type = type;

  }

  /**
   * Gets the JPA type.
   *
   * @return the JPA type
   */
  @Override
  public Class<?> getJPAType() {
    return type;
  }

  /**
   * Sets the o data JPA tombstone entity listener.
   *
   * @param entityListener the new o data JPA tombstone entity listener
   */
  @Override
  public void setODataJPATombstoneEntityListener(
      final Class<? extends ODataJPATombstoneEntityListener> entityListener) {
    this.entityListener = entityListener;
  }

  /**
   * Gets the O data JPA tombstone entity listener.
   *
   * @return the o data JPA tombstone entity listener
   */
  @Override
  public Class<? extends ODataJPATombstoneEntityListener> getODataJPATombstoneEntityListener() {
    return entityListener;
  }

  /**
   * Checks if is virtual access.
   *
   * @return true, if is virtual access
   */
  @Override
  public boolean isVirtualAccess() {
	  return isVirtualAccess;
  }

  /**
   * Sets the virtual access.
   *
   * @param virtualAccess the new virtual access
   */
  @Override
  public void setVirtualAccess(boolean virtualAccess) {
	  this.isVirtualAccess=virtualAccess;
  }
}
