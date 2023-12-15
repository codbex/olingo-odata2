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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.ep.AbstractProviderTest;
import org.apache.olingo.odata2.core.ep.AtomEntityProvider;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.helper.XMLUnitHelper;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlPropertyProducerTest.
 */
public class XmlPropertyProducerTest extends AbstractProviderTest {

  /**
   * Instantiates a new xml property producer test.
   *
   * @param type the type
   */
  public XmlPropertyProducerTest(final StreamWriterImplType type) {
    super(type);
  }

  /**
   * Serialize employee id.
   *
   * @throws Exception the exception
   */
  @Test
  public void serializeEmployeeId() throws Exception {
    AtomEntityProvider s = createAtomEntityProvider();
    EdmTyped edmTyped = MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("EmployeeId");
    EdmProperty edmProperty = (EdmProperty) edmTyped;

    ODataResponse response = s.writeProperty(edmProperty, employeeData.get("EmployeeId"));
    assertNotNull(response);
    assertNotNull(response.getEntity());

    String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:EmployeeId", xml);
    assertXpathEvaluatesTo("1", "/d:EmployeeId/text()", xml);
  }

  /**
   * Serialize room id with facets.
   *
   * @throws Exception the exception
   */
  @Test
  public void serializeRoomIdWithFacets() throws Exception {
    AtomEntityProvider s = createAtomEntityProvider();
    EdmTyped edmTyped = MockFacade.getMockEdm().getEntityType("RefScenario", "Room").getProperty("Id");
    EdmProperty edmProperty = (EdmProperty) edmTyped;

    String id = StringHelper.generateData(1000);
    try {
      ODataResponse response = s.writeProperty(edmProperty, id);
      assertNotNull(response);
    } catch(EntityProviderException e) {
      assertNotNull(e.getCause());
      assertTrue(e.getCause() instanceof EdmSimpleTypeException);
    }
  }

  /**
   * Serialize age.
   *
   * @throws Exception the exception
   */
  @Test
  public void serializeAge() throws Exception {
    AtomEntityProvider s = createAtomEntityProvider();

    EdmTyped edmTyped = MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");
    EdmProperty edmProperty = (EdmProperty) edmTyped;

    ODataResponse response = s.writeProperty(edmProperty, employeeData.get("Age"));
    assertNotNull(response);
    assertNotNull(response.getEntity());

    String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:Age", xml);
    assertXpathEvaluatesTo("52", "/d:Age/text()", xml);
  }

  /**
   * Serialize image url.
   *
   * @throws Exception the exception
   */
  @Test
  public void serializeImageUrl() throws Exception {
    AtomEntityProvider s = createAtomEntityProvider();

    EdmTyped edmTyped = MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("ImageUrl");
    EdmProperty edmProperty = (EdmProperty) edmTyped;

    ODataResponse response = s.writeProperty(edmProperty, employeeData.get("ImageUrl"));
    assertNotNull(response);
    assertNotNull(response.getEntity());

    String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:ImageUrl", xml);
    assertXpathExists("/d:ImageUrl/@m:null", xml);
    assertXpathEvaluatesTo("true", "/d:ImageUrl/@m:null", xml);
  }

  /**
   * Serialize image.
   *
   * @throws Exception the exception
   */
  @Test
  public void serializeImage() throws Exception {
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario2", "Photo").getProperty("Image");
    ODataResponse response = createAtomEntityProvider().writeProperty(property, photoData.get("Image"));
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntityProvider should not set content header", response.getContentHeader());

    final String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);
    assertXpathExists("/d:Image", xml);
    assertXpathExists("/d:Image/@m:MimeType", xml);
    assertXpathEvaluatesTo("image/png", "/d:Image/@m:MimeType", xml);
  }

  /**
   * Serialize binary data.
   *
   * @throws Exception the exception
   */
  @Test
  public void serializeBinaryData() throws Exception {
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario2", "Photo").getProperty("BinaryData");
    ODataResponse response = createAtomEntityProvider().writeProperty(property, photoData.get("BinaryData"));
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntityProvider should not set content header", response.getContentHeader());

    final String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);
    assertXpathExists("/d:BinaryData", xml);
    assertXpathExists("/d:BinaryData/@m:MimeType", xml);
    assertXpathEvaluatesTo("image/jpeg", "/d:BinaryData/@m:MimeType", xml);
  }

  /**
   * Serialize location.
   *
   * @throws Exception the exception
   */
  @Test
  public void serializeLocation() throws Exception {
    AtomEntityProvider s = createAtomEntityProvider();

    EdmEntityType edmEntityType = MockFacade.getMockEdm().getEntityType("RefScenario", "Employee");
    EdmTyped edmTyped = edmEntityType.getProperty("Location");
    EdmProperty edmProperty = (EdmProperty) edmTyped;

    ODataResponse response = s.writeProperty(edmProperty, employeeData.get("Location"));
    assertNotNull(response);
    assertNotNull(response.getEntity());

    String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:Location", xml);
    assertXpathExists("/d:Location/d:City", xml);
    assertXpathExists("/d:Location/d:City/d:PostalCode", xml);
    assertXpathExists("/d:Location/d:City/d:CityName", xml);
    assertXpathExists("/d:Location/d:Country", xml);

    // verify order of tags
    // first outer tags (city/country)
    XMLUnitHelper.verifyTagOrdering(xml, "City", "Country");
    // then inner tags (postalcode/cityname)
    XMLUnitHelper.verifyTagOrdering(xml, "PostalCode", "CityName");

    assertXpathEvaluatesTo("RefScenario.c_Location", "/d:Location/@m:type", xml);

    assertXpathEvaluatesTo("33470", "/d:Location/d:City/d:PostalCode/text()", xml);
    assertXpathEvaluatesTo("Duckburg", "/d:Location/d:City/d:CityName/text()", xml);
    assertXpathEvaluatesTo("Calisota", "/d:Location/d:Country/text()", xml);
  }

}
