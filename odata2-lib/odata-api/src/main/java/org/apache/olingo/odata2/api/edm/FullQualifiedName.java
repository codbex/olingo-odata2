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
package org.apache.olingo.odata2.api.edm;

// TODO: Auto-generated Javadoc
/**
 * The Class FullQualifiedName.
 *
 * @org.apache.olingo.odata2.DoNotImplement A full qualified name of any element in the EDM consists of a name and a namespace.
 */
public class FullQualifiedName {

  /** The namespace. */
  private final String namespace;
  
  /** The name. */
  private final String name;

  /**
   * Instantiates a new full qualified name.
   *
   * @param namespace the namespace
   * @param name the name
   */
  public FullQualifiedName(final String namespace, final String name) {
    this.namespace = namespace;
    this.name = name;
  }

  /**
   * Gets the namespace.
   *
   * @return namespace
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Gets the name.
   *
   * @return name
   */
  public String getName() {
    return name;
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  /**
   * Equals.
   *
   * @param obj the obj
   * @return true, if successful
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if ((obj == null) || !(obj instanceof FullQualifiedName)) {
      return false;
    }
    final FullQualifiedName other = (FullQualifiedName) obj;
    return namespace.equals(other.getNamespace()) && name.equals(other.getName());
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return namespace + Edm.DELIMITER + name;
  }
}
