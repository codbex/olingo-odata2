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
package org.apache.olingo.odata2.jpa.processor.core.mock.model;

import java.lang.reflect.Member;
import java.util.ArrayList;

import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAPluralAttributeMock.
 */
public class JPAPluralAttributeMock implements PluralAttribute<Object, ArrayList<String>, String> {

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the persistent attribute type.
   *
   * @return the persistent attribute type
   */
  @Override
  public jakarta.persistence.metamodel.Attribute.PersistentAttributeType getPersistentAttributeType() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the declaring type.
   *
   * @return the declaring type
   */
  @Override
  public ManagedType<Object> getDeclaringType() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the java type.
   *
   * @return the java type
   */
  @Override
  public Class<ArrayList<String>> getJavaType() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the java member.
   *
   * @return the java member
   */
  @Override
  public Member getJavaMember() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Checks if is association.
   *
   * @return true, if is association
   */
  @Override
  public boolean isAssociation() {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Checks if is collection.
   *
   * @return true, if is collection
   */
  @Override
  public boolean isCollection() {
    return false;
  }

  /**
   * Gets the bindable type.
   *
   * @return the bindable type
   */
  @Override
  public jakarta.persistence.metamodel.Bindable.BindableType getBindableType() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the bindable java type.
   *
   * @return the bindable java type
   */
  @Override
  public Class<String> getBindableJavaType() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the collection type.
   *
   * @return the collection type
   */
  @Override
  public jakarta.persistence.metamodel.PluralAttribute.CollectionType getCollectionType() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the element type.
   *
   * @return the element type
   */
  @Override
  public Type<String> getElementType() {
    // TODO Auto-generated method stub
    return null;
  }

}