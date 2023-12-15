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

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

import jakarta.persistence.JoinColumns;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAJavaMemberMock.
 */
public class JPAJavaMemberMock implements Member, AnnotatedElement, Annotation {

  /**
   * Gets the declaring class.
   *
   * @return the declaring class
   */
  @Override
  public Class<?> getDeclaringClass() {
    return null;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return null;
  }

  /**
   * Gets the modifiers.
   *
   * @return the modifiers
   */
  @Override
  public int getModifiers() {
    return 0;
  }

  /**
   * Checks if is synthetic.
   *
   * @return true, if is synthetic
   */
  @Override
  public boolean isSynthetic() {
    return false;
  }

  /**
   * Checks if is annotation present.
   *
   * @param annotationClass the annotation class
   * @return true, if is annotation present
   */
  @Override
  public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass) {
    return false;
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   */
  @Override
  public Annotation[] getAnnotations() {
    return null;
  }

  /**
   * Gets the declared annotations.
   *
   * @return the declared annotations
   */
  @Override
  public Annotation[] getDeclaredAnnotations() {
    return null;
  }

  /**
   * Annotation type.
   *
   * @return the class<? extends annotation>
   */
  @Override
  public Class<? extends Annotation> annotationType() {
    return JoinColumns.class;
  }

  /**
   * Gets the annotation.
   *
   * @param <T> the generic type
   * @param annotationClass the annotation class
   * @return the annotation
   */
  @Override
  public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
    return null;
  }

}
