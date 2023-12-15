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
package org.apache.olingo.odata2.core.edm.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmComplexTypeImplProvTest.
 */
public class EdmComplexTypeImplProvTest extends BaseTest {
  
  /** The edm complex type. */
  private static EdmComplexTypeImplProv edmComplexType;
  
  /** The edm complex type with base type. */
  private static EdmComplexTypeImplProv edmComplexTypeWithBaseType;
  
  /** The edm provider. */
  private static EdmProvider edmProvider;

  /**
   * Gets the edm entity container impl.
   *
   * @return the edm entity container impl
   * @throws Exception the exception
   */
  @BeforeClass
  public static void getEdmEntityContainerImpl() throws Exception {

    edmProvider = mock(EdmProvider.class);
    EdmImplProv edmImplProv = new EdmImplProv(edmProvider);

    ComplexType fooComplexType = new ComplexType().setName("fooComplexType");

    List<Property> keyPropertysFoo = new ArrayList<Property>();
    keyPropertysFoo.add(new SimpleProperty().setName("Name").setType(EdmSimpleTypeKind.String));
    keyPropertysFoo.add(new SimpleProperty().setName("Address").setType(EdmSimpleTypeKind.String));
    fooComplexType.setProperties(keyPropertysFoo);

    edmComplexType = new EdmComplexTypeImplProv(edmImplProv, fooComplexType, "namespace");

    FullQualifiedName barBaseTypeName = new FullQualifiedName("namespace", "barBase");
    ComplexType barBase = new ComplexType().setName("barBase");
    when(edmProvider.getComplexType(barBaseTypeName)).thenReturn(barBase);

    List<Property> propertysBarBase = new ArrayList<Property>();
    propertysBarBase.add(new SimpleProperty().setName("Name").setType(EdmSimpleTypeKind.String));
    propertysBarBase.add(new SimpleProperty().setName("Address").setType(EdmSimpleTypeKind.String));
    barBase.setProperties(propertysBarBase);

    ComplexType barComplexType = new ComplexType().setName("barComplexType").setBaseType(barBaseTypeName);
    edmComplexTypeWithBaseType = new EdmComplexTypeImplProv(edmImplProv, barComplexType, "namespace");

  }

  /**
   * Gets the properties names.
   *
   * @return the properties names
   * @throws Exception the exception
   */
  @Test
  public void getPropertiesNames() throws Exception {
    List<String> properties = edmComplexType.getPropertyNames();
    assertNotNull(properties);
    assertTrue(properties.contains("Name"));
    assertTrue(properties.contains("Address"));
  }

  /**
   * Gets the properties with base type.
   *
   * @return the properties with base type
   * @throws Exception the exception
   */
  @Test
  public void getPropertiesWithBaseType() throws Exception {
    List<String> properties = edmComplexTypeWithBaseType.getPropertyNames();
    assertNotNull(properties);
    assertTrue(properties.contains("Name"));
    assertTrue(properties.contains("Address"));
  }

  /**
   * Gets the base type.
   *
   * @return the base type
   * @throws Exception the exception
   */
  @Test
  public void getBaseType() throws Exception {
    EdmComplexType baseType = edmComplexTypeWithBaseType.getBaseType();
    assertNotNull(baseType);
    assertEquals("barBase", baseType.getName());
    assertEquals("namespace", baseType.getNamespace());
  }

  /**
   * Gets the property.
   *
   * @return the property
   * @throws Exception the exception
   */
  @Test
  public void getProperty() throws Exception {
    EdmTyped property = edmComplexType.getProperty("Name");
    assertNotNull(property);
    assertEquals("Name", property.getName());
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws Exception the exception
   */
  @Test
  public void getAnnotations() throws Exception {
    EdmAnnotatable annotatable = edmComplexType;
    EdmAnnotations annotations = annotatable.getAnnotations();
    assertNull(annotations.getAnnotationAttributes());
    assertNull(annotations.getAnnotationElements());
  }
}
