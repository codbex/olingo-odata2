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

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.InputStream;
import java.util.Arrays;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.ep.AbstractProviderTest;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlFunctionImportTest.
 */
public class XmlFunctionImportTest extends AbstractProviderTest {

  /**
   * Instantiates a new xml function import test.
   *
   * @param type the type
   */
  public XmlFunctionImportTest(final StreamWriterImplType type) {
    super(type);
  }

  /**
   * Single simple type.
   *
   * @throws Exception the exception
   */
  @Test
  public void singleSimpleType() throws Exception {
    final EdmFunctionImport functionImport =
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("MaximalAge");

    final ODataResponse response =
        createAtomEntityProvider().writeFunctionImport(functionImport, employeeData.get("Age"), DEFAULT_PROPERTIES);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntitypProvider must not set content header", response.getContentHeader());

    final String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:MaximalAge", xml);
    assertXpathEvaluatesTo("52", "/d:MaximalAge/text()", xml);
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

    final ODataResponse response =
        createAtomEntityProvider()
            .writeFunctionImport(functionImport, employeeData.get("Location"), DEFAULT_PROPERTIES);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntitypProvider must not set content header", response.getContentHeader());

    final String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:MostCommonLocation", xml);
    assertXpathEvaluatesTo("RefScenario.c_Location", "/d:MostCommonLocation/@m:type", xml);
    assertXpathEvaluatesTo("Duckburg", "/d:MostCommonLocation/d:City/d:CityName/text()", xml);
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
        createAtomEntityProvider()
            .writeFunctionImport(functionImport, Arrays.asList("1", "2", "3"), DEFAULT_PROPERTIES);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntitypProvider must not set content header", response.getContentHeader());

    final String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:AllUsedRoomIds", xml);
    assertXpathEvaluatesTo("1", "/d:AllUsedRoomIds/d:element/text()", xml);
    assertXpathEvaluatesTo("2", "/d:AllUsedRoomIds/d:element[2]/text()", xml);
    assertXpathEvaluatesTo("3", "/d:AllUsedRoomIds/d:element[3]/text()", xml);
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

    final ODataResponse response =
        createAtomEntityProvider().writeFunctionImport(functionImport, Arrays.asList(employeeData.get("Location")),
            DEFAULT_PROPERTIES);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntitypProvider must not set content header", response.getContentHeader());

    final String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:AllLocations", xml);
    assertXpathExists("/d:AllLocations/d:element", xml);
    assertXpathEvaluatesTo("RefScenario.c_Location", "/d:AllLocations/d:element/@m:type", xml);
    assertXpathEvaluatesTo("Duckburg", "/d:AllLocations/d:element/d:City/d:CityName/text()", xml);
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

    final ODataResponse response =
        createAtomEntityProvider().writeFunctionImport(functionImport, employeeData, DEFAULT_PROPERTIES);
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntityProvider should not set content header", response.getContentHeader());

    final String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/a:entry", xml);
    assertXpathExists("/a:entry/a:link[@href=\"Employees('1')/$value\"]", xml);
    assertXpathEvaluatesTo("Duckburg", "/a:entry/m:properties/d:Location/d:City/d:CityName/text()", xml);
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

    final ODataResponse response =
        createAtomEntityProvider().writeFunctionImport(functionImport, employeeData, DEFAULT_PROPERTIES);
    assertNotNull(response);
    assertNull(response.getEntity());
    assertNull(response.getContentHeader());
    assertEquals(HttpStatusCodes.ACCEPTED, response.getStatus());
   
  }
}
