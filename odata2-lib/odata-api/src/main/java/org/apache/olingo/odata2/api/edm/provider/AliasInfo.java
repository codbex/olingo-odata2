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
package org.apache.olingo.odata2.api.edm.provider;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class hold an alias and the associated namespace.
 */
public class AliasInfo {

  /** The alias. */
  private String alias;
  
  /** The namespace. */
  private String namespace;

  /**
   * Gets the alias.
   *
   * @return the alias
   */
  public String getAlias() {
    return alias;
  }

  /**
   * Sets the alias.
   *
   * @param alias the alias
   * @return {@link AliasInfo} for method chaining
   */
  public AliasInfo setAlias(final String alias) {
    this.alias = alias;
    return this;
  }

  /**
   * Gets the namespace.
   *
   * @return the namespace which is associated with this alias
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Sets the namespace.
   *
   * @param namespace the namespace
   * @return {@link AliasInfo} for method chaining
   */
  public AliasInfo setNamespace(final String namespace) {
    this.namespace = namespace;
    return this;
  }

}
