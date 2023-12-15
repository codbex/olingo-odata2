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
package org.apache.olingo.odata2.jpa.processor.api.model;

import java.util.HashMap;

import org.apache.olingo.odata2.api.edm.provider.Schema;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * A view on Java Persistence Model and Entity Data Model Schema. Each java
 * persistence unit corresponds to a one EDM schema.
 * </p>
 * <p>
 * The implementation of the view provides access to EDM schema created from
 * Java Persistence unit. The implementation acts as a container for schema. The
 * schema is consistent only if following elements are consistent
 * <ol>
 * <li>{@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView}</li>
 * <li> {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityContainerView}</li>
 * <li>{@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmComplexTypeView}</li>
 * </ol>
 * </p>
 * 
 * 
 * <p>
 *
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityContainerView
 * @see org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmComplexTypeView
 * @org.apache.olingo.odata2.DoNotImplement 
 */
public interface JPAEdmSchemaView extends JPAEdmBaseView {
  /**
   * The method returns the EDM schema present in the container.
   * 
   * @return an instance EDM schema of type {@link org.apache.olingo.odata2.api.edm.provider.Schema}
   */
  public Schema getEdmSchema();

  /**
   * The method returns JPA EDM container view. The JPA EDM container view can
   * be used to access EDM Entity Container elements.
   * 
   * @return an instance of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityContainerView}
   */
  public JPAEdmEntityContainerView getJPAEdmEntityContainerView();

  /**
   * The method returns JPA EDM complex view. The JPA EDM complex view can be
   * used to access EDM complex types and JPA Embeddable Types.
   * 
   * @return an instance of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmComplexTypeView}
   */
  public JPAEdmComplexTypeView getJPAEdmComplexTypeView();

  /**
   * The method returns JPA EDM association view. The JPA EDM association view
   * can be used to access EDM associations and JPA Relationships.
   * 
   * @return an instance of type {@link org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView}
   */
  public JPAEdmAssociationView getJPAEdmAssociationView();

  /**
   * The method registers custom operations that shall be represented as Edm
   * Function Imports. Custom operations are created using Edm Annotation
   * {@link org.apache.olingo.odata2.api.annotation.edm.FunctionImport}.
   * <p>
   * Custom Operations can be part of JPA Entity or can be created in a class
   * other than JPA Entity. Such custom operations can be registered using
   * this method.
   * <p>
   * The method is a callback.
   * 
   * @param customClass
   * is the class that contains custom operations
   * @param methodNames
   * is the name of the method that needs to be transformed into
   * Function Imports. It is an optional parameter. If null is
   * passed then all annotated methods are transformed into
   * Function Imports.
   * 
   */
  public void registerOperations(Class<?> customClass, String methodNames[]);

  /**
   * The method returns an Hash Map containing the registered custom
   * operations.
   * 
   * @return a HashMap of Class and the methods in the class
   */
  public HashMap<Class<?>, String[]> getRegisteredOperations();

}
