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
package org.apache.olingo.odata2.api.ep;

import org.apache.olingo.odata2.api.uri.PathInfo;

// TODO: Auto-generated Javadoc
/**
 * The {@link EntityProviderBatchProperties} contains necessary informations to parse a Batch Request body.
 * 
 * 
 */
public class EntityProviderBatchProperties {
  
  /** PathInfo contains service root and preceding segments which should be used for URI parsing of a single request. */
  private PathInfo pathInfo;

  /**
   * Defines whether to use strict batch parsing.
   */
  private boolean isStrict = true;

  /**
   * Inits the.
   *
   * @return the entity provider batch properties builder
   */
  public static EntityProviderBatchPropertiesBuilder init() {
    return new EntityProviderBatchPropertiesBuilder();
  }

  /**
   * Gets the path info.
   *
   * @return the path info
   */
  public PathInfo getPathInfo() {
    return pathInfo;
  }

  /**
   * Checks if is strict.
   *
   * @return true, if is strict
   */
  public boolean isStrict() {
    return isStrict;
  }

  /**
   * The Class EntityProviderBatchPropertiesBuilder.
   */
  public static class EntityProviderBatchPropertiesBuilder {
    
    /** The properties. */
    private final EntityProviderBatchProperties properties = new EntityProviderBatchProperties();

    /**
     * Instantiates a new entity provider batch properties builder.
     */
    public EntityProviderBatchPropertiesBuilder() {}

    /**
     * Instantiates a new entity provider batch properties builder.
     *
     * @param propertiesFrom the properties from
     */
    public EntityProviderBatchPropertiesBuilder(final EntityProviderBatchProperties propertiesFrom) {
      properties.pathInfo = propertiesFrom.pathInfo;
    }

    /**
     * Path info.
     *
     * @param pathInfo the path info
     * @return the entity provider batch properties builder
     */
    public EntityProviderBatchPropertiesBuilder pathInfo(final PathInfo pathInfo) {
      properties.pathInfo = pathInfo;
      return this;
    }

    /**
     * Sets the strict.
     *
     * @param isStrict the is strict
     * @return the entity provider batch properties builder
     */
    public EntityProviderBatchPropertiesBuilder setStrict(final boolean isStrict) {
      properties.isStrict = isStrict;
      return this;
    }

    /**
     * Builds the.
     *
     * @return the entity provider batch properties
     */
    public EntityProviderBatchProperties build() {
      return properties;
    }
  }

}
