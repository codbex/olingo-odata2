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
package org.apache.olingo.odata2.client.core.uri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmParameter;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.client.api.uri.QueryOption;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmUriBuilderTest.
 */
public class EdmUriBuilderTest {
  
  /** The Constant SERVICE_ROOT_URI. */
  protected static final String SERVICE_ROOT_URI = "http://host:80/service/";
  
  /** The Constant SERVICE_ROOT_URI_1. */
  protected static final String SERVICE_ROOT_URI_1 = "http://host:80/service";
  
  /** The edm. */
  private Edm edm;

  /**
   * Gets the edm.
   *
   * @return the edm
   * @throws ODataException the o data exception
   */
  @Before
  public void getEdm() throws ODataException {
    edm = MockFacade.getMockEdm();
  }
  
  /**
   * Test uri simple ES.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriSimpleES() throws EdmException {
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(edm.getDefaultEntityContainer().getEntitySet("Employees")).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees", uri.toASCIIString());
  }
  
  /**
   * Test simple ES with count uri 1.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testSimpleESWithCountUri1() throws EdmException {
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(edm.getDefaultEntityContainer().getEntitySet("Employees")).
    appendCountSegment().
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees/$count", uri.toASCIIString());
  }
  
  /**
   * Neg test with count and format.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void negTestWithCountAndFormat() throws EdmException {
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(edm.getDefaultEntityContainer().getEntitySet("Employees")).
    appendCountSegment().
    format("application/json").
    build();
  }
  
  /**
   * Test simple ES with count uri 2.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testSimpleESWithCountUri2() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendCountSegment().
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees/$count", uri.toASCIIString());
  }
  
  /**
   * Test simple ES with count and filter.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testSimpleESWithCountAndFilter() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendCountSegment().
    addQueryOption(QueryOption.FILTER, "TeamId eq '1'").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees/"
        + "$count?$filter=TeamId%20eq%20'1'", uri.toASCIIString());
  }
  
  /**
   * Neg test uri with count segment 1.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void negTestUriWithCountSegment1() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet employeeEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendKeySegment((EdmProperty)employeeEntitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendCountSegment().
    build();
  }
  
  /**
   * Neg test uri with count segment 2.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void negTestUriWithCountSegment2() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet employeeEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendKeySegment((EdmProperty)employeeEntitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)employeeEntitySet.getEntityType().getProperty("ne_Team")).
    appendCountSegment().
    build();
  }
  
  /**
   * Test metadata uri.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testMetadataUri() throws EdmException {
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendMetadataSegment().
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/$metadata", uri.toASCIIString());
  }
  
  /**
   * Test simple ES with key uri.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testSimpleESWithKeyUri() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty) entitySet.getEntityType().getProperty("EmployeeId"), "1").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees('1')", uri.toASCIIString());
  }
  
  /**
   * Test composite keys uri.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testCompositeKeysUri() throws EdmException {
    EdmEntitySet entitySet = edm.getEntityContainer("Container2").getEntitySet("Photos");
    Map<EdmProperty, Object> keyMap = new LinkedHashMap<EdmProperty,Object>();
    keyMap.put((EdmProperty) entitySet.getEntityType().getProperty("Id"), 4);
    keyMap.put((EdmProperty) entitySet.getEntityType().getProperty("Type"), "foo");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment(keyMap).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Photos(Id=4,Type='foo')", uri.toASCIIString());
  }
  
  /**
   * Test filter uri.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testFilterUri() throws EdmException {
    EdmEntitySet entitySet = edm.getEntityContainer("Container2").getEntitySet("Photos");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    filter("Name eq 'Photo 1'").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Photos?$filter=Name%20eq%20'Photo%201'", uri.toASCIIString());
  }
  
  /**
   * Test top uri 1.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testTopUri1() throws EdmException {
    EdmEntitySet entitySet = edm.getEntityContainer("Container2").getEntitySet("Photos");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    top(2).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Photos?$top=2", uri.toASCIIString());
  }
  
  /**
   * Test top uri 2.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testTopUri2() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    addQueryOption(QueryOption.FILTER, "TeamId eq '1'").
    top(2).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees"
        + "?$filter=TeamId%20eq%20'1'&$top=2", uri.toASCIIString());
  }
  
  /**
   * Neg test query option.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void negTestQueryOption() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet employeeEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendKeySegment((EdmProperty)employeeEntitySet.getEntityType().getProperty("EmployeeId"), "1").
    addQueryOption(QueryOption.FILTER, "TeamId eq '1'").
    top(2).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees"
        + "?$filter=TeamId%20eq%20'1'&$top=2", uri.toASCIIString());
  }
  
  /**
   * Test skip uri.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testSkipUri() throws EdmException {
    EdmEntitySet entitySet = edm.getEntityContainer("Container2").getEntitySet("Photos");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    skip(2).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Photos?$skip=2", uri.toASCIIString());
  }
  
  /**
   * Test uri with query options.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithQueryOptions() throws EdmException {
    EdmEntitySet entitySet = edm.getEntityContainer("Container2").getEntitySet("Photos");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    filter("Name eq 'Photo 1'").
    top(2).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Photos?$filter=Name%20eq%20'Photo%201'&$top=2", uri.toASCIIString());
  }
   
  /**
   * Test uri with navigation segment 1.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithNavigationSegment1() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("ne_Team")).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees('1')/ne_Team", uri.toASCIIString());
  }
  
  /**
   * Test uri with navigation segment 2.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithNavigationSegment2() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet employeeEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendKeySegment((EdmProperty)employeeEntitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)employeeEntitySet.getEntityType().getProperty("ne_Team")).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees('1')/ne_Team", uri.toASCIIString());
  }
  
  /**
   * Test uri with navigation segment 3.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithNavigationSegment3() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    EdmEntitySet teamEntitySet = edm.getDefaultEntityContainer().getEntitySet("Teams");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("ne_Team")).
    appendNavigationSegment((EdmNavigationProperty)teamEntitySet.getEntityType().getProperty("nt_Employees")).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees('1')/ne_Team/nt_Employees", uri.toASCIIString());
  }
  
  /**
   * Neg test 1 uri with navigation segment.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void negTest1UriWithNavigationSegment() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet employeeEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendNavigationSegment((EdmNavigationProperty)employeeEntitySet.getEntityType().getProperty("ne_Team")).
    build();
  }
  
  /**
   * Neg test 2 uri with navigation segment.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void negTest2UriWithNavigationSegment() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("ne_Team")).
    build();
  }
  
  /**
   * Test uri with simple property segment.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithSimplePropertySegment() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendPropertySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeName"), "EmployeeName").
    appendValueSegment().
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees('1')/EmployeeName/$value", uri.toASCIIString());
  }
  
  /**
   * Test uri with complex property segment.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithComplexPropertySegment() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendPropertySegment((EdmProperty)entitySet.getEntityType().getProperty("Location"), "Location").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees('1')/Location", uri.toASCIIString());
  }
  
  /**
   * Test uri with complex property segment 1.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithComplexPropertySegment1() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    EdmComplexType complexType = edm.getComplexType("RefScenario", "c_Location");
    EdmProperty property = (EdmProperty) complexType.getProperty("City");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendPropertySegment((EdmProperty)entitySet.getEntityType().getProperty("Location"), "Location").
    appendPropertySegment(property, "City").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees('1')/Location/City", uri.toASCIIString());
  }
  
  /**
   * Test uri with complex property segment with value segment.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected = RuntimeException.class)
  public void testUriWithComplexPropertySegmentWithValueSegment() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    EdmComplexType complexType = edm.getComplexType("RefScenario", "c_Location");
    EdmProperty property = (EdmProperty) complexType.getProperty("City");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendPropertySegment((EdmProperty)entitySet.getEntityType().getProperty("Location"), "Location").
    appendPropertySegment(property, "City").
    appendValueSegment().
    build();
  }
  
  /**
   * Neg test uri with format.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected = RuntimeException.class)
  public void negTestUriWithFormat() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    EdmComplexType complexType = edm.getComplexType("RefScenario", "c_Location");
    EdmProperty property = (EdmProperty) complexType.getProperty("City");
    EdmComplexType complexType1 = (EdmComplexType) property.getType();
    EdmProperty property1 = (EdmProperty) complexType1.getProperty("CityName");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendPropertySegment((EdmProperty)entitySet.getEntityType().getProperty("Location"), "Location").
    appendPropertySegment(property, "City").
    appendPropertySegment(property1, "CityName").
    appendValueSegment().
    format("application/json").
    build();
  }
  
  /**
   * Wrong ES in uri.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void wrongESInUri() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employee");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    build();
  }
  
  /**
   * Duplicate key property in uri.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected = RuntimeException.class)
  public void duplicateKeyPropertyInUri() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    build();
  }
  
  /**
   * Duplicate key for nav property in uri.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void duplicateKeyForNavPropertyInUri() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet empEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendKeySegment((EdmProperty)empEntitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendKeySegment((EdmProperty)empEntitySet.getEntityType().getProperty("EmployeeId"), "1").
    build();
  }
  
  /**
   * Test navigation to many in uri.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testNavigationToManyInUri() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet empEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    Map<EdmProperty, Object> keyMap = new HashMap<EdmProperty, Object>();
    keyMap.put((EdmProperty)empEntitySet.getEntityType().getProperty("EmployeeId"), "1");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendKeySegment(keyMap).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees(EmployeeId='1')", uri.toASCIIString());
  }
  
  /**
   * Test uri with orderby.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithOrderby() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    orderBy("EmployeeId").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees?$orderby=EmployeeId", uri.toASCIIString());
  }
  
  /**
   * Neg test uri with orderby.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void negTestUriWithOrderby() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    orderBy("EmployeeId").
    build();
  }
  
  /**
   * Test uri with orderby and format.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithOrderbyAndFormat() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    orderBy("EmployeeId").
    format("application/json").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees"
        + "?$orderby=EmployeeId&$format=application%2Fjson", uri.toASCIIString());
  }
  
  /**
   * Test uri with orderby with null value.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithOrderbyWithNullValue() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    try {
      new EdmURIBuilderImpl(SERVICE_ROOT_URI).
      appendEntitySetSegment(entitySet).
      appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
      appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
      orderBy("EmployeeName").
      build();
    } catch(EdmException e) {
      assertEquals("Property not defined.", e.getMessage());
    }
  }
  
  /**
   * Test uri with select.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithSelect() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    select("EmployeeId", "EmployeeName", "RoomId", "TeamId").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees"
        + "?$select=EmployeeId%2CEmployeeName%2CRoomId%2CTeamId", uri.toASCIIString());
  }
  
  /**
   * Test uri with select and filter.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithSelectAndFilter() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    filter("EmployeeId eq 1").
    select("EmployeeId", "EmployeeName", "RoomId", "TeamId").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees?$filter="
        + "EmployeeId%20eq%201&$select=EmployeeId%2CEmployeeName%2CRoomId%2CTeamId", uri.toASCIIString());
  }
  
  /**
   * Test uri with select and count.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void testUriWithSelectAndCount() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendCountSegment().
    select("EmployeeId", "EmployeeName", "RoomId", "TeamId").
    build();
  }
  
  /**
   * Test uri with select on entity.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithSelectOnEntity() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    select("EmployeeId", "EmployeeName", "RoomId", "TeamId").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')"
        + "?$select=EmployeeId%2CEmployeeName%2CRoomId%2CTeamId", uri.toASCIIString());
  }
  
  /**
   * Test uri with select on entity with top.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void testUriWithSelectOnEntityWithTop() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    select("EmployeeId", "EmployeeName", "RoomId", "TeamId").
    top(2).
    build();
  }
  
  /**
   * Test uri with expand.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithExpand() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    expand("nm_Employees").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')?$expand=nm_Employees", uri.toASCIIString());
  }
  
  /**
   * Test uri with expand and filter.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithExpandAndFilter() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    expand("nm_Employees").
    filter("EmployeeName eq 'Walter Winter'").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers?$expand=nm_Employees&$filter="
        + "EmployeeName%20eq%20'Walter%20Winter'", uri.toASCIIString());
  }
  
  /**
   * Test uri with expand and count.
   *
   * @throws EdmException the edm exception
   */
  @Test(expected=RuntimeException.class)
  public void testUriWithExpandAndCount() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendCountSegment().
    expand("nm_Employees").
    build();
  }
  
  /**
   * Test uri with filter and expand.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithFilterAndExpand() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    filter("EmployeeName eq 'Walter Winter'").
    expand("nm_Employees").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers?$filter=EmployeeName%20eq%20'Walter%20Winter'"
        + "&$expand=nm_Employees", uri.toASCIIString());
  }
  
  /**
   * Test uri with custom query option.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithCustomQueryOption() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    top(2).
    addCustomQueryOption("x", "y").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees?$top=2&x=y", uri.toASCIIString());
  }
  
  /**
   * Test uri with custom query option with format.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithCustomQueryOptionWithFormat() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    top(2).
    addCustomQueryOption("x", "y").
    format("application/json").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees?$top=2&$format=application%2Fjson&x=y", uri.toASCIIString());
  }
  
  /**
   * Test uri with filters.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithFilters() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    filter("EmployeeId ge '1' and EmployeeId le '10'").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers?$filter=EmployeeId%20ge%20'1'%20"
        + "and%20EmployeeId%20le%20'10'", uri.toASCIIString());
  }
  
  /**
   * Test uri with duplicate expands.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithDuplicateExpands() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    expand("nm_Employees").
    expand("nm_Employees").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers?$expand=nm_Employees%2Cnm_Employees", uri.toASCIIString());
  }
  
  /**
   * Test uri with two custom query options.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithTwoCustomQueryOptions() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    top(2).
    addCustomQueryOption("x", "y").
    addCustomQueryOption("z", "y").
    format("application/json").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees?$top=2&$"
        + "format=application%2Fjson&x=y&z=y", uri.toASCIIString());
  }
  
  /**
   * Test uri with only custom query option.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithOnlyCustomQueryOption() throws EdmException {
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI_1).
    addCustomQueryOption("x", "y").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service?x=y", uri.toASCIIString());
  }
  
  /**
   * Test uri with duplicate orderby.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithDuplicateOrderby() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    orderBy("EmployeeId").
    orderBy("EmployeeId").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers?$orderby=EmployeeId%2CEmployeeId", uri.toASCIIString());
  }
  
  /**
   * Test uri with two orderby.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithTwoOrderby() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    orderBy("EmployeeId").
    orderBy("EmployeeName desc").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees?$orderby=EmployeeId%2CEmployeeName%20desc", uri.toASCIIString());
  }
  
  /**
   * Test uri with navigation to many with key with simple property.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithNavigationToManyWithKeyWithSimpleProperty() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet empEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendKeySegment((EdmProperty)empEntitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendPropertySegment((EdmProperty)empEntitySet.getEntityType().getProperty("EmployeeName"), "EmployeeName").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees('1')/EmployeeName", uri.toASCIIString());
  }
  
  /**
   * Neg test uri with navigation to many with simple property.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void negTestUriWithNavigationToManyWithSimpleProperty() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet empEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    try {
      new EdmURIBuilderImpl(SERVICE_ROOT_URI).
      appendEntitySetSegment(entitySet).
      appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
      appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
      appendPropertySegment((EdmProperty)empEntitySet.getEntityType().getProperty("EmployeeName"), "EmployeeName").
      build();
    } catch (Exception e) {
      assertEquals("Can't specify a property at this position", e.getMessage());
    }
  }
  
  /**
   * Test simple ES with encoded key uri.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testSimpleESWithEncodedKeyUri() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty) entitySet.getEntityType().getProperty("EmployeeId"), "abc/def").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Employees('abc%2Fdef')", uri.toASCIIString());
  }
  
  /**
   * Test composite keys encoded uri.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testCompositeKeysEncodedUri() throws EdmException {
    EdmEntitySet entitySet = edm.getEntityContainer("Container2").getEntitySet("Photos");
    Map<EdmProperty, Object> keyMap = new LinkedHashMap<EdmProperty,Object>();
    keyMap.put((EdmProperty) entitySet.getEntityType().getProperty("Id"), 4);
    keyMap.put((EdmProperty) entitySet.getEntityType().getProperty("Type"), "foo,foo;");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment(keyMap).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Photos(Id=4,Type='foo%2Cfoo%3B')", uri.toASCIIString());
  }
  
  /**
   * Test uri with navigation to many with key encoded.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithNavigationToManyWithKeyEncoded() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    EdmEntitySet empEntitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1()*;").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    appendKeySegment((EdmProperty)empEntitySet.getEntityType().getProperty("EmployeeId"), "@#$%").
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1%28%29%2A%3B')/nm_Employees('%40%23%24%25')", uri.toASCIIString());
  }
  
  /**
   * Test function import with empty params.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testFunctionImportWithEmptyParams() throws EdmException {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("AllLocations");
    Map<EdmParameter, Object> functionImportParams = new HashMap<EdmParameter, Object>();
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).appendFunctionImportParameters(functionImportParams).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/AllLocations", uri.toASCIIString());
  }
  
  /**
   * Test function import with null params.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testFunctionImportWithNullParams() throws EdmException {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("AllLocations");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).appendFunctionImportParameters(null).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/AllLocations", uri.toASCIIString());
  }
  
  /**
   * Test function import with params.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithParams() throws Exception {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("EmployeeSearch");
    Map<EdmParameter, Object> functionImportParams = new HashMap<EdmParameter, Object>();
    EdmParameter param = edm.getDefaultEntityContainer().getFunctionImport("EmployeeSearch").getParameter("q");
    functionImportParams.put(param, "Emp1");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).appendFunctionImportParameters(functionImportParams).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/EmployeeSearch?q='Emp1'", uri.toASCIIString());
  }
  
  /**
   * Test function import with path segments and params.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithPathSegmentsAndParams() throws Exception {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("EmployeeSearch");
    Map<EdmParameter, Object> functionImportParams = new HashMap<EdmParameter, Object>();
    EdmParameter param = edm.getDefaultEntityContainer().getFunctionImport("EmployeeSearch").getParameter("q");
    functionImportParams.put(param, "Emp1");
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    EdmProperty property = (EdmProperty) entitySet.getEntityType().getProperty("Location");
    
    try {
      new EdmURIBuilderImpl(SERVICE_ROOT_URI).
          appendFunctionImportSegment(functionImport).appendPropertySegment(property, "Location").
          appendFunctionImportParameters(functionImportParams).build();
    } catch (RuntimeException e) {
      assertEquals("Can't specify a property at this position", e.getMessage());
    }
  }
  
  /**
   * Test uri with function import with key encoded segment.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testUriWithFunctionImportWithKeyEncodedSegment() throws EdmException {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("EmployeeSearch");
    Map<EdmParameter, Object> functionImportParams = new HashMap<EdmParameter, Object>();
    EdmParameter param = edm.getDefaultEntityContainer().getFunctionImport("EmployeeSearch").getParameter("q");
    functionImportParams.put(param, "Emp1");
    Map<EdmProperty, Object> keySegParams = new HashMap<EdmProperty, Object>();
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    keySegParams.put((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1()*;");
    
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).
    appendKeySegment(keySegParams).
    appendFunctionImportParameters(functionImportParams).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/EmployeeSearch(EmployeeId='1%28%29%2A%3B')?q='Emp1'", uri.toASCIIString());
  }
  
  /**
   * Test function import with params with null facets.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithParamsWithNullFacets() throws Exception {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("FINullableParameter");
    Map<EdmParameter, Object> functionImportParams = new HashMap<EdmParameter, Object>();
    EdmParameter param = edm.getDefaultEntityContainer().getFunctionImport("FINullableParameter").getParameter("Id");
    functionImportParams.put(param, "1");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).appendFunctionImportParameters(functionImportParams).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/FINullableParameter?Id='1'", uri.toASCIIString());
  }
  
  /**
   * Test function import with params with false null facets.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithParamsWithFalseNullFacets() throws Exception {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("ManagerPhoto");
    Map<EdmParameter, Object> functionImportParams = new HashMap<EdmParameter, Object>();
    EdmParameter param = edm.getDefaultEntityContainer().getFunctionImport("FINullableParameter").getParameter("Id");
    functionImportParams.put(param, "1");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).appendFunctionImportParameters(functionImportParams).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/ManagerPhoto?Id='1'", uri.toASCIIString());
  }
  
  /**
   * Test function import with multiple params.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithMultipleParams() throws Exception {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch");
    Map<EdmParameter, Object> functionImportParams = new LinkedHashMap<EdmParameter, Object>();
    EdmParameter param1 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("q");
    functionImportParams.put(param1, "1");
    EdmParameter param2 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("r");
    functionImportParams.put(param2, 1);
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).appendFunctionImportParameters(functionImportParams).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/BuildingSearch?q='1'&r=1", uri.toASCIIString());
  }
  
  /**
   * Test function import with multiple params with more segments.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithMultipleParamsWithMoreSegments() throws Exception {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch");
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Buildings");
    EdmProperty property = (EdmProperty) entitySet.getEntityType().getProperty("Id");
    EdmNavigationProperty navProperty = (EdmNavigationProperty) entitySet.getEntityType().getProperty("nb_Rooms");
    Map<EdmParameter, Object> functionImportParams = new LinkedHashMap<EdmParameter, Object>();
    EdmParameter param1 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("q");
    functionImportParams.put(param1, "1");
    EdmParameter param2 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("r");
    functionImportParams.put(param2, 1);
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).appendKeySegment(property, "1").
        appendNavigationSegment(navProperty).
        appendFunctionImportParameters(functionImportParams).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/BuildingSearch('1')/nb_Rooms?q='1'&r=1", uri.toASCIIString());
  }
  
  /**
   * Test function import with multiple params with count.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithMultipleParamsWithCount() throws Exception {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch");
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Buildings");
    EdmProperty property = (EdmProperty) entitySet.getEntityType().getProperty("Id");
    EdmNavigationProperty navProperty = (EdmNavigationProperty) entitySet.getEntityType().getProperty("nb_Rooms");
    Map<EdmParameter, Object> functionImportParams = new LinkedHashMap<EdmParameter, Object>();
    EdmParameter param1 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("q");
    functionImportParams.put(param1, "1");
    EdmParameter param2 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("r");
    functionImportParams.put(param2, 1);
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).appendKeySegment(property, "1").
        appendNavigationSegment(navProperty).
        appendCountSegment().
        appendFunctionImportParameters(functionImportParams).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/BuildingSearch('1')/nb_Rooms/$count?q='1'&r=1", uri.toASCIIString());
  }
  
  /**
   * Test function import with count.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithCount() throws Exception {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch");
    Map<EdmParameter, Object> functionImportParams = new LinkedHashMap<EdmParameter, Object>();
    EdmParameter param1 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("q");
    functionImportParams.put(param1, "1");
    EdmParameter param2 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("r");
    functionImportParams.put(param2, 1);
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).
        appendCountSegment().
        appendFunctionImportParameters(functionImportParams).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/BuildingSearch/$count?q='1'&r=1", uri.toASCIIString());
  }
  
  /**
   * Test function import with multiple params with property segment.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithMultipleParamsWithPropertySegment() throws Exception {
    EdmFunctionImport functionImport = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch");
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Buildings");
    EdmProperty property = (EdmProperty) entitySet.getEntityType().getProperty("Id");
    EdmNavigationProperty navProperty = (EdmNavigationProperty) entitySet.getEntityType().getProperty("nb_Rooms");
    EdmEntitySet entitySet1 = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    EdmProperty property1 = (EdmProperty) entitySet1.getEntityType().getProperty("Id");
    EdmProperty property2 = (EdmProperty) entitySet1.getEntityType().getProperty("Name");
    Map<EdmParameter, Object> functionImportParams = new LinkedHashMap<EdmParameter, Object>();
    EdmParameter param1 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("q");
    functionImportParams.put(param1, "1");
    EdmParameter param2 = edm.getDefaultEntityContainer().getFunctionImport("BuildingSearch").getParameter("r");
    functionImportParams.put(param2, 1);
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).appendKeySegment(property, "1").
        appendNavigationSegment(navProperty).appendKeySegment(property1, "1").
        appendPropertySegment(property2, "Name").
        appendFunctionImportParameters(functionImportParams).build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/BuildingSearch('1')/nb_Rooms('1')/Name?q='1'&r=1", uri.toASCIIString());
  }
  
  /**
   * Test function import with key segment.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImportWithKeySegment() throws Exception {
    EdmFunctionImport functionImport = edm.getEntityContainer("Container2").getFunctionImport("PhotoSearch");
    EdmEntitySet entitySet = edm.getEntityContainer("Container2").getEntitySet("Photos");
    EdmProperty property = (EdmProperty) entitySet.getEntityType().getProperty("Id");
    EdmProperty property1 = (EdmProperty) entitySet.getEntityType().getProperty("Type");
    Map<EdmProperty, Object> keySegments = new LinkedHashMap<EdmProperty, Object>();
    keySegments.put(property, "1");
    keySegments.put(property1, "Internal");
    Map<EdmParameter, Object> functionImportParams = new LinkedHashMap<EdmParameter, Object>();
    EdmParameter param1 = edm.getEntityContainer("Container2").getFunctionImport("PhotoSearch").getParameter("Id");
    functionImportParams.put(param1, 1);
    EdmParameter param2 = edm.getEntityContainer("Container2").getFunctionImport("PhotoSearch").getParameter("Type");
    functionImportParams.put(param2, "Internal");
    try {
    new EdmURIBuilderImpl(SERVICE_ROOT_URI).
        appendFunctionImportSegment(functionImport).
        appendKeySegment(keySegments).
        appendFunctionImportParameters(functionImportParams).build();
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "Can't specify a key at this position");
    }
  }
  
  /**
   * Test custom query with system query.
   *
   * @throws Exception the exception
   */
  @Test
  public void testCustomQueryWithSystemQuery() throws Exception {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).appendEntitySetSegment(entitySet).
        addCustomQueryOption("x", "y").filter("EmployeeName eq 'Walter Winter'").build();
    assertEquals("http://host:80/service/Managers?$filter="
        + "EmployeeName%20eq%20'Walter%20Winter'&x=y", uri.toASCIIString());
  }
  
  /**
   * Test service document.
   *
   * @throws Exception the exception
   */
  @Test
  public void testServiceDocument() throws Exception {
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).build();
    assertEquals("http://host:80/service/", uri.toASCIIString());
  }
  
  /**
   * Test service document 1.
   *
   * @throws Exception the exception
   */
  @Test
  public void testServiceDocument1() throws Exception {
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI_1).build();
    assertEquals("http://host:80/service/", uri.toASCIIString());
  }
  
  /**
   * Adds the same filter option twice.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void addSameFilterOptionTwice() throws EdmException {
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Managers");
    URI uri = new EdmURIBuilderImpl(SERVICE_ROOT_URI).
    appendEntitySetSegment(entitySet).
    appendKeySegment((EdmProperty)entitySet.getEntityType().getProperty("EmployeeId"), "1").
    appendNavigationSegment((EdmNavigationProperty)entitySet.getEntityType().getProperty("nm_Employees")).
    addQueryOption(QueryOption.FILTER, "TeamId eq '1'").
    addQueryOption(QueryOption.FILTER, "TeamId eq '1'").
    top(2).
    build();
    assertNotNull(uri);
    assertEquals("http://host:80/service/Managers('1')/nm_Employees"
        + "?$filter=TeamId%20eq%20'1'%2CTeamId%20eq%20'1'&$top=2", uri.toASCIIString());
  }
}