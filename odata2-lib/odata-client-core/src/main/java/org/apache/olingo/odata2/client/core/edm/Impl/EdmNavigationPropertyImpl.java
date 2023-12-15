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
package org.apache.olingo.odata2.client.core.edm.Impl;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmAssociation;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.client.api.edm.EdmDocumentation;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent EdmNavigationProperty.
 */
public class EdmNavigationPropertyImpl extends EdmTypedImpl implements EdmNavigationProperty, EdmAnnotatable {

  /** The annotations. */
  private EdmAnnotations annotations;
  
  /** The relationship. */
  private FullQualifiedName relationship;
  
  /** The from role. */
  private String fromRole;
  
  /** The to role. */
  private String toRole;
  
  /** The documentation. */
  private EdmDocumentation documentation;
  
  /** The mapping. */
  private Mapping mapping;

  /**
   * Gets the documentation.
   *
   * @return the documentation
   */
  public EdmDocumentation getDocumentation() {
    return documentation;
  }

  /**
   * Sets the documentation.
   *
   * @param documentation the new documentation
   */
  public void setDocumentation(EdmDocumentation documentation) {
    this.documentation = documentation;
  }

  /**
   * Sets the mapping.
   *
   * @param mapping the new mapping
   */
  public void setMapping(Mapping mapping) {
    this.mapping = mapping;
  }


  /**
   * Sets the annotations.
   *
   * @param annotations the new annotations
   */
  public void setAnnotations(EdmAnnotations annotations) {
    this.annotations = annotations;
  }

  /**
   * Sets the from role.
   *
   * @param fromRole the new from role
   */
  public void setFromRole(String fromRole) {
    this.fromRole = fromRole;
  }

  /**
   * Sets the to role.
   *
   * @param toRole the new to role
   */
  public void setToRole(String toRole) {
    this.toRole = toRole;
  }

  /**
   * Gets the type.
   *
   * @return the type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmType getType() throws EdmException {
    return edmType;
  }

  /**
   * Gets the multiplicity.
   *
   * @return the multiplicity
   * @throws EdmException the edm exception
   */
  @Override
  public EdmMultiplicity getMultiplicity() throws EdmException {
    return multiplicity;
  }

  /**
   * Gets the relationship.
   *
   * @return the relationship
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAssociation getRelationship() throws EdmException {
    return edm.getAssociation(relationship.getNamespace(), relationship.getName());
  }
  
  /**
   * Gets the relationship name.
   *
   * @return the relationship name
   * @throws EdmException the edm exception
   */
  public FullQualifiedName getRelationshipName() throws EdmException {
    return relationship;
  }
  
  /**
   * Sets the relationship name.
   *
   * @param relationship the new relationship name
   */
  public void setRelationshipName( FullQualifiedName relationship){
    this.relationship = relationship;
  }

  /**
   * Gets the from role.
   *
   * @return the from role
   * @throws EdmException the edm exception
   */
  @Override
  public String getFromRole() throws EdmException {
    return fromRole;
  }

  /**
   * Gets the to role.
   *
   * @return the to role
   * @throws EdmException the edm exception
   */
  @Override
  public String getToRole() throws EdmException {
    return toRole;
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAnnotations getAnnotations() throws EdmException {
    return annotations;
  }

  /**
   * Gets the mapping.
   *
   * @return the mapping
   * @throws EdmException the edm exception
   */
  @Override
  public EdmMapping getMapping() throws EdmException {
    return mapping;
  }
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
      return String.format(name);
  }

}
