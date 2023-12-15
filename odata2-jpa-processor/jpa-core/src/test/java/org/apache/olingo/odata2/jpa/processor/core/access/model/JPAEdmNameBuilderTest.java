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
package org.apache.olingo.odata2.jpa.processor.core.access.model;

import static org.junit.Assert.assertEquals;

import org.apache.olingo.odata2.api.edm.provider.ComplexProperty;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmComplexPropertyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityTypeView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmPropertyView;
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAContextImpl;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAAttributeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEntityTypeMock;
import org.easymock.EasyMock;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmNameBuilderTest.
 */
public class JPAEdmNameBuilderTest {

  /**
   * Test build JPA edm property view.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Test
  public void testBuildJPAEdmPropertyView() {

    SimpleProperty simpleProperty = new SimpleProperty();

    // Mocking EDMProperty
    JPAEdmPropertyView propertyView = EasyMock.createMock(JPAEdmPropertyView.class);
    JPAEdmEntityTypeView entityTypeView = EasyMock.createMock(JPAEdmEntityTypeView.class);

    EasyMock.expect(propertyView.getJPAAttribute()).andStubReturn(new JPAAttribute());
    EasyMock.expect(propertyView.getJPAEdmEntityTypeView()).andStubReturn(entityTypeView);
    EasyMock.expect(propertyView.getJPAEdmMappingModelAccess()).andStubReturn(null);
    EasyMock.expect(propertyView.getEdmSimpleProperty()).andStubReturn(simpleProperty);
    EasyMock.replay(propertyView);

    JPAEdmNameBuilder.build(propertyView, false, false, false);
    assertEquals("Id", simpleProperty.getName());
  }

  /**
   * Test build JPA edm property view with no default naming.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Test
  public void testBuildJPAEdmPropertyViewWithNoDefaultNaming() {

    SimpleProperty simpleProperty = new SimpleProperty();

    // Mocking EDMProperty
    JPAEdmPropertyView propertyView = EasyMock.createMock(JPAEdmPropertyView.class);
    JPAEdmEntityTypeView entityTypeView = EasyMock.createMock(JPAEdmEntityTypeView.class);

    EasyMock.expect(propertyView.getJPAAttribute()).andStubReturn(new JPAAttribute());
    EasyMock.expect(propertyView.getJPAEdmEntityTypeView()).andStubReturn(entityTypeView);
    EasyMock.expect(propertyView.getJPAEdmMappingModelAccess()).andStubReturn(null);
    EasyMock.expect(propertyView.getEdmSimpleProperty()).andStubReturn(simpleProperty);
    EasyMock.replay(propertyView);

    JPAEdmNameBuilder.build(propertyView, false, true, false);
    assertEquals("id", simpleProperty.getName());
  }

  /**
   * Test build JPA edm complex property view.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Test
  public void testBuildJPAEdmComplexPropertyView() {
    JPAEdmComplexPropertyView complexPropertyView = EasyMock.createMock(JPAEdmComplexPropertyView.class);
    ComplexProperty complexProperty = new ComplexProperty();
    EasyMock.expect(complexPropertyView.getEdmComplexProperty()).andStubReturn(complexProperty);
    ODataJPAContextImpl oDataJPAContext = new ODataJPAContextImpl();
    JPAEdmMappingModelService mappingModelService = new JPAEdmMappingModelService(oDataJPAContext);
    EasyMock.expect(complexPropertyView.getJPAEdmMappingModelAccess()).andStubReturn(mappingModelService);

    // Mocking EDMProperty
    JPAEdmPropertyView propertyView = EasyMock.createMock(JPAEdmPropertyView.class);
    JPAEdmEntityTypeView entityTypeView = EasyMock.createMock(JPAEdmEntityTypeView.class);
    EasyMock.expect(entityTypeView.getJPAEntityType()).andStubReturn(new JPAEntityType());
    EasyMock.replay(entityTypeView);
    EasyMock.expect(propertyView.getJPAAttribute()).andStubReturn(new JPAAttribute());
    EasyMock.expect(propertyView.getJPAEdmEntityTypeView()).andStubReturn(entityTypeView);
    EasyMock.replay(complexPropertyView);
    EasyMock.replay(propertyView);

    JPAEdmNameBuilder.build(complexPropertyView, propertyView, false);
    assertEquals("Id", complexPropertyView.getEdmComplexProperty().getName());

  }

  /**
   * Test build JPA edm complex property view with no default naming.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Test
  public void testBuildJPAEdmComplexPropertyViewWithNoDefaultNaming() {
    JPAEdmComplexPropertyView complexPropertyView = EasyMock.createMock(JPAEdmComplexPropertyView.class);
    ComplexProperty complexProperty = new ComplexProperty();
    EasyMock.expect(complexPropertyView.getEdmComplexProperty()).andStubReturn(complexProperty);
    ODataJPAContextImpl oDataJPAContext = new ODataJPAContextImpl();
    JPAEdmMappingModelService mappingModelService = new JPAEdmMappingModelService(oDataJPAContext);
    EasyMock.expect(complexPropertyView.getJPAEdmMappingModelAccess()).andStubReturn(mappingModelService);

    // Mocking EDMProperty
    JPAEdmPropertyView propertyView = EasyMock.createMock(JPAEdmPropertyView.class);
    JPAEdmEntityTypeView entityTypeView = EasyMock.createMock(JPAEdmEntityTypeView.class);
    EasyMock.expect(entityTypeView.getJPAEntityType()).andStubReturn(new JPAEntityType());
    EasyMock.replay(entityTypeView);
    EasyMock.expect(propertyView.getJPAAttribute()).andStubReturn(new JPAAttribute());
    EasyMock.expect(propertyView.getJPAEdmEntityTypeView()).andStubReturn(entityTypeView);
    EasyMock.replay(complexPropertyView);
    EasyMock.replay(propertyView);

    JPAEdmNameBuilder.build(complexPropertyView, propertyView, true);
    assertEquals("id", complexPropertyView.getEdmComplexProperty().getName());

  }

  /**
   * The Class JPAAttribute.
   *
   * @param <Object> the generic type
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  class JPAAttribute<Object, String> extends JPAAttributeMock<Object, java.lang.String> {

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public java.lang.String getName() {
      return "id";
    }

    /**
     * Gets the java type.
     *
     * @return the java type
     */
    @Override
    public Class<java.lang.String> getJavaType() {
      return java.lang.String.class;
    }

  }

  /**
   * The Class JPAEntityType.
   *
   * @param <Object> the generic type
   */
  @SuppressWarnings("hiding")
  class JPAEntityType<Object> extends JPAEntityTypeMock<Object> {

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public java.lang.String getName() {
      return "SalesOrderHeader";
    }

  }

}
