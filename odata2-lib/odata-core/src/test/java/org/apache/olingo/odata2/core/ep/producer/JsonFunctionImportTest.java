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
package org.apache.olingo.odata2.core.ep.producer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.ep.JsonEntityProvider;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonFunctionImportTest.
 */
public class JsonFunctionImportTest extends BaseTest {

  /**
   * Single simple type.
   *
   * @throws Exception the exception
   */
  @Test
  public void singleSimpleType() throws Exception {
    final EdmFunctionImport functionImport =
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("MaximalAge");

    final ODataResponse response = new JsonEntityProvider().writeFunctionImport(functionImport, 42, null);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntitypProvider must not set content header", response.getContentHeader());

    final String json = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(json);
    assertEquals("{\"d\":{\"MaximalAge\":42}}", json);
  }

  /**
   * Single complex type.
   *
   * @throws Exception the exception
   */
  @Test
  public void singleComplexType() throws Exception {
    final EdmFunctionImport functionImport =
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("MostCommonLocation");
    Map<String, Object> cityData = new HashMap<String, Object>();
    cityData.put("PostalCode", "8392");
    cityData.put("CityName", "Å");
    Map<String, Object> locationData = new HashMap<String, Object>();
    locationData.put("City", cityData);
    locationData.put("Country", "NO");

    final ODataResponse response = new JsonEntityProvider().writeFunctionImport(functionImport, locationData, null);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntitypProvider must not set content header", response.getContentHeader());

    final String json = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(json);
    assertEquals("{\"d\":{\"MostCommonLocation\":{"
        + "\"__metadata\":{\"type\":\"RefScenario.c_Location\"},"
        + "\"City\":{\"__metadata\":{\"type\":\"RefScenario.c_City\"},\"PostalCode\":\"8392\","
        + "\"CityName\":\"Å\"},\"Country\":\"NO\"}}}",
        json);
  }

  /**
   * Collection of simple types.
   *
   * @throws Exception the exception
   */
  @Test
  public void collectionOfSimpleTypes() throws Exception {
    final EdmFunctionImport functionImport =
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds");

    final ODataResponse response =
        new JsonEntityProvider().writeFunctionImport(functionImport, Arrays.asList("1", "2", "3"), null);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntitypProvider must not set content header", response.getContentHeader());

    final String json = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(json);
    assertEquals("{\"d\":{\"__metadata\":{\"type\":\"Collection(Edm.String)\"},"
        + "\"results\":[\"1\",\"2\",\"3\"]}}",
        json);
  }

  /**
   * Collection of complex types.
   *
   * @throws Exception the exception
   */
  @Test
  public void collectionOfComplexTypes() throws Exception {
    final EdmFunctionImport functionImport =
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllLocations");
    Map<String, Object> locationData = new HashMap<String, Object>();
    locationData.put("Country", "NO");
    List<Map<String, Object>> locations = new ArrayList<Map<String, Object>>();
    locations.add(locationData);

    final ODataResponse response = new JsonEntityProvider().writeFunctionImport(functionImport, locations, null);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntitypProvider must not set content header", response.getContentHeader());

    final String json = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(json);
    assertEquals("{\"d\":{\"__metadata\":{\"type\":\"Collection(RefScenario.c_Location)\"},"
        + "\"results\":[{\"__metadata\":{\"type\":\"RefScenario.c_Location\"},"
        + "\"City\":{\"__metadata\":{\"type\":\"RefScenario.c_City\"},"
        + "\"PostalCode\":null,\"CityName\":null},\"Country\":\"NO\"}]}}",
        json);
  }

  /**
   * Single entity type.
   *
   * @throws Exception the exception
   */
  @Test
  public void singleEntityType() throws Exception {
    final EdmFunctionImport functionImport =
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("OldestEmployee");
    final String uri = "http://host:80/service/";
    final EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(URI.create(uri)).build();
    Map<String, Object> employeeData = new HashMap<String, Object>();
    employeeData.put("EmployeeId", "1");
    employeeData.put("getImageType", "image/jpeg");

    final ODataResponse response =
        new JsonEntityProvider().writeFunctionImport(functionImport, employeeData, properties);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntitypProvider must not set content header", response.getContentHeader());

    final String json = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(json);
    assertEquals("{\"d\":{\"__metadata\":{"
        + "\"id\":\"" + uri + "Employees('1')\","
        + "\"uri\":\"" + uri + "Employees('1')\","
        + "\"type\":\"RefScenario.Employee\",\"content_type\":\"image/jpeg\","
        + "\"media_src\":\"" + uri + "Employees('1')/$value\","
        + "\"edit_media\":\"" + uri + "Employees('1')/$value\"},"
        + "\"EmployeeId\":\"1\",\"EmployeeName\":null,"
        + "\"ManagerId\":null,\"RoomId\":null,\"TeamId\":null,"
        + "\"Location\":{\"__metadata\":{\"type\":\"RefScenario.c_Location\"},"
        + "\"City\":{\"__metadata\":{\"type\":\"RefScenario.c_City\"},"
        + "\"PostalCode\":null,\"CityName\":null},\"Country\":null},\"Age\":null,"
        + "\"EntryDate\":null,\"ImageUrl\":null,"
        + "\"ne_Manager\":{\"__deferred\":{\"uri\":\"" + uri + "Employees('1')/ne_Manager\"}},"
        + "\"ne_Team\":{\"__deferred\":{\"uri\":\"" + uri + "Employees('1')/ne_Team\"}},"
        + "\"ne_Room\":{\"__deferred\":{\"uri\":\"" + uri + "Employees('1')/ne_Room\"}}}}",
        json);
  }
  
  
  /**
   * No return type action.
   *
   * @throws Exception the exception
   */
  @Test
  public void noReturnTypeAction() throws Exception {
    final EdmFunctionImport functionImport =
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AddEmployee");
    final String uri = "http://host:80/service/";
    final EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(URI.create(uri)).build();
    Map<String, Object> employeeData = new HashMap<String, Object>();
    employeeData.put("EmployeeId", "1");
    employeeData.put("getImageType", "image/jpeg");
    final ODataResponse response =
        new JsonEntityProvider().writeFunctionImport(functionImport, employeeData, properties);
    assertNotNull(response);
    assertNull(response.getEntity());
    assertNull(response.getContentHeader());
    assertEquals(HttpStatusCodes.ACCEPTED, response.getStatus());
   
  }
}
