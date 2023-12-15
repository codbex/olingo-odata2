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
package org.apache.olingo.odata2.client.api.edm;

import java.util.Collection;

import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;
import org.apache.olingo.odata2.api.edm.EdmAnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;

// TODO: Auto-generated Javadoc
/**
 *  Impl objects of this class create a client EdmUsing.
 */
public interface EdmUsing {

  /**
   * Gets the namespace.
   *
   * @return <b>String</b> namespace
   */
  public String getNamespace() ;

  /**
   * Gets the alias.
   *
   * @return <b>String</b> alias
   */
  public String getAlias() ;

  /**
   * Gets the documentation.
   *
   * @return {@link EdmDocumentation} documentation
   */
  public EdmDocumentation getDocumentation() ;

  /**
   * Gets the annotation attributes.
   *
   * @return collection of {@link AnnotationAttribute} annotation attributes
   */
  public Collection<EdmAnnotationAttribute> getAnnotationAttributes() ;

  /**
   * Gets the annotation elements.
   *
   * @return collection of {@link AnnotationElement} annotation elements
   */
  public Collection<EdmAnnotationElement> getAnnotationElements() ;
}
