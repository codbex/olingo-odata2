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

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathNotExists;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.rt.RuntimeDelegate;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.core.ODataPathSegmentImpl;
import org.apache.olingo.odata2.core.ep.AbstractProviderTest;
import org.apache.olingo.odata2.core.ep.AtomEntityProvider;
import org.apache.olingo.odata2.core.uri.ExpandSelectTreeCreator;
import org.apache.olingo.odata2.core.uri.UriParserImpl;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.mock.EdmTestProvider;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.junit.Test;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlSelectProducerTest.
 */
public class XmlSelectProducerTest extends AbstractProviderTest {

  /**
   * Instantiates a new xml select producer test.
   *
   * @param type the type
   */
  public XmlSelectProducerTest(final StreamWriterImplType type) {
    super(type);
  }

  /** The Constant T. */
  private static final boolean T = true;
  
  /** The Constant F. */
  private static final boolean F = false;

  /**
   * All properties no select.
   *
   * @throws Exception the exception
   */
  @Test
  public void allPropertiesNoSelect() throws Exception {
    AtomEntityProvider provider = createAtomEntityProvider();
    ODataResponse response =
        provider.writeEntry(MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees"),
            employeeData, DEFAULT_PROPERTIES);

    String xmlString = verifyResponse(response);

    verifyNavigationProperties(xmlString, T, T, T);
    assertXpathExists("/a:entry/m:properties", xmlString);
    verifyKeyProperties(xmlString, T, T, T, T);
    verifySingleProperties(xmlString, T, T, T, T);
    verifyComplexProperties(xmlString, T);
  }

  /**
   * All properties select star.
   *
   * @throws Exception the exception
   */
  @Test
  public void allPropertiesSelectStar() throws Exception {
    ExpandSelectTreeNode selectTree = getSelectExpandTree("*", null);

    EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(BASE_URI).expandSelectTree(selectTree).build();
    AtomEntityProvider provider = createAtomEntityProvider();
    ODataResponse response =
        provider.writeEntry(MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees"),
            employeeData, properties);

    String xmlString = verifyResponse(response);

    verifyNavigationProperties(xmlString, T, T, T);
    assertXpathExists("/a:entry/m:properties", xmlString);
    verifyKeyProperties(xmlString, T, T, T, T);
    verifySingleProperties(xmlString, T, T, T, T);
    verifyComplexProperties(xmlString, T);
  }

  /**
   * Select employee id.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectEmployeeId() throws Exception {
    ExpandSelectTreeNode selectTree = getSelectExpandTree("EmployeeId", null);

    EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(BASE_URI).expandSelectTree(selectTree).build();
    AtomEntityProvider provider = createAtomEntityProvider();
    ODataResponse response =
        provider.writeEntry(MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees"),
            employeeData, properties);

    String xmlString = verifyResponse(response);

    verifyNavigationProperties(xmlString, F, F, F);
    assertXpathExists("/a:entry/m:properties", xmlString);
    verifyKeyProperties(xmlString, T, F, F, F);
    verifySingleProperties(xmlString, F, F, F, F);
    verifyComplexProperties(xmlString, F);
  }

  /**
   * Select navigation properties.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectNavigationProperties() throws Exception {
    ExpandSelectTreeNode selectTree = getSelectExpandTree("ne_Team, ne_Manager", null);

    EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(BASE_URI).expandSelectTree(selectTree).build();
    AtomEntityProvider provider = createAtomEntityProvider();
    ODataResponse response =
        provider.writeEntry(MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees"),
            employeeData, properties);

    String xmlString = verifyResponse(response);

    verifyNavigationProperties(xmlString, T, F, T);
    assertXpathNotExists("/a:entry/m:properties", xmlString);
    verifyKeyProperties(xmlString, F, F, F, F);
    verifySingleProperties(xmlString, F, F, F, F);
    verifyComplexProperties(xmlString, F);
  }

  /**
   * Select complex properties.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectComplexProperties() throws Exception {
    ExpandSelectTreeNode selectTree = getSelectExpandTree("Location", null);

    EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(BASE_URI).expandSelectTree(selectTree).build();
    AtomEntityProvider provider = createAtomEntityProvider();
    ODataResponse response =
        provider.writeEntry(MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees"),
            employeeData, properties);

    String xmlString = verifyResponse(response);

    verifyNavigationProperties(xmlString, F, F, F);
    assertXpathExists("/a:entry/m:properties", xmlString);
    verifyKeyProperties(xmlString, F, F, F, F);
    verifySingleProperties(xmlString, F, F, F, F);
    verifyComplexProperties(xmlString, T);
  }

  /**
   * Select complex and navigation properties.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectComplexAndNavigationProperties() throws Exception {
    ExpandSelectTreeNode selectTree = getSelectExpandTree("Location, ne_Room", null);

    EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(BASE_URI).expandSelectTree(selectTree).build();
    AtomEntityProvider provider = createAtomEntityProvider();
    ODataResponse response =
        provider.writeEntry(MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees"),
            employeeData, properties);

    String xmlString = verifyResponse(response);

    verifyNavigationProperties(xmlString, F, T, F);
    assertXpathExists("/a:entry/m:properties", xmlString);
    verifyKeyProperties(xmlString, F, F, F, F);
    verifySingleProperties(xmlString, F, F, F, F);
    verifyComplexProperties(xmlString, T);
  }

  /**
   * Select complex and navigation and key properties.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectComplexAndNavigationAndKeyProperties() throws Exception {
    ExpandSelectTreeNode selectTree = getSelectExpandTree("Location, ne_Room, EmployeeId, TeamId", null);

    EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(BASE_URI).expandSelectTree(selectTree).build();
    AtomEntityProvider provider = createAtomEntityProvider();
    ODataResponse response =
        provider.writeEntry(MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees"),
            employeeData, properties);

    String xmlString = verifyResponse(response);

    verifyNavigationProperties(xmlString, F, T, F);
    assertXpathExists("/a:entry/m:properties", xmlString);
    verifyKeyProperties(xmlString, T, F, F, T);
    verifySingleProperties(xmlString, F, F, F, F);
    verifyComplexProperties(xmlString, T);
  }

  /**
   * Select employee id employee name image url.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectEmployeeIdEmployeeNameImageUrl() throws Exception {
    ExpandSelectTreeNode selectTree = getSelectExpandTree("EmployeeId, EmployeeName, ImageUrl", null);

    EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(BASE_URI).expandSelectTree(selectTree).build();
    AtomEntityProvider provider = createAtomEntityProvider();
    ODataResponse response =
        provider.writeEntry(MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees"),
            employeeData, properties);

    String xmlString = verifyResponse(response);

    verifyNavigationProperties(xmlString, F, F, F);
    assertXpathExists("/a:entry/m:properties", xmlString);
    verifyKeyProperties(xmlString, T, F, F, F);
    verifySingleProperties(xmlString, T, F, F, T);
    verifyComplexProperties(xmlString, F);
  }

  /**
   * Select age.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectAge() throws Exception {
    ExpandSelectTreeNode selectTree = getSelectExpandTree("Age", null);

    EntityProviderWriteProperties properties =
        EntityProviderWriteProperties.serviceRoot(BASE_URI).expandSelectTree(selectTree).build();
    AtomEntityProvider provider = createAtomEntityProvider();
    ODataResponse response =
        provider.writeEntry(MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees"),
            employeeData, properties);

    String xmlString = verifyResponse(response);

    verifyNavigationProperties(xmlString, F, F, F);
    assertXpathExists("/a:entry/m:properties", xmlString);
    verifyKeyProperties(xmlString, F, F, F, F);
    verifySingleProperties(xmlString, F, T, F, F);
    verifyComplexProperties(xmlString, F);
  }

  /**
   * Verify complex properties.
   *
   * @param xmlString the xml string
   * @param location the location
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SAXException the SAX exception
   * @throws XpathException the xpath exception
   */
  private void verifyComplexProperties(final String xmlString, final boolean location) throws IOException,
      SAXException, XpathException {
    if (location) {
      assertXpathExists("/a:entry/m:properties/d:Location", xmlString);
    } else {
      assertXpathNotExists("/a:entry/m:properties/d:Location", xmlString);
    }
  }

  /**
   * Verify single properties.
   *
   * @param xmlString the xml string
   * @param employeeName the employee name
   * @param age the age
   * @param entryDate the entry date
   * @param imageUrl the image url
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SAXException the SAX exception
   * @throws XpathException the xpath exception
   */
  private void verifySingleProperties(final String xmlString, final boolean employeeName, final boolean age,
      final boolean entryDate, final boolean imageUrl) throws IOException, SAXException, XpathException {
    if (employeeName) {
      assertXpathExists("/a:entry/m:properties/d:EmployeeName", xmlString);
    } else {
      assertXpathNotExists("/a:entry/m:properties/d:EmployeeName", xmlString);
    }
    if (age) {
      assertXpathExists("/a:entry/m:properties/d:Age", xmlString);
    } else {
      assertXpathNotExists("/a:entry/m:properties/d:Age", xmlString);
    }
    if (entryDate) {
      assertXpathExists("/a:entry/m:properties/d:EntryDate", xmlString);
    } else {
      assertXpathNotExists("/a:entry/m:properties/d:EntryDate", xmlString);
    }
    if (imageUrl) {
      assertXpathExists("/a:entry/m:properties/d:ImageUrl", xmlString);
    } else {
      assertXpathNotExists("/a:entry/m:properties/d:ImageUrl", xmlString);
    }
  }

  /**
   * Verify key properties.
   *
   * @param xmlString the xml string
   * @param employeeId the employee id
   * @param managerId the manager id
   * @param roomId the room id
   * @param teamId the team id
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SAXException the SAX exception
   * @throws XpathException the xpath exception
   */
  private void verifyKeyProperties(final String xmlString, final boolean employeeId, final boolean managerId,
      final boolean roomId, final boolean teamId) throws IOException, SAXException, XpathException {
    if (employeeId) {
      assertXpathExists("/a:entry/m:properties/d:EmployeeId", xmlString);
    } else {
      assertXpathNotExists("/a:entry/m:properties/d:EmployeeId", xmlString);
    }
    if (managerId) {
      assertXpathExists("/a:entry/m:properties/d:ManagerId", xmlString);
    } else {
      assertXpathNotExists("/a:entry/m:properties/d:ManagerId", xmlString);
    }
    if (roomId) {
      assertXpathExists("/a:entry/m:properties/d:RoomId", xmlString);
    } else {
      assertXpathNotExists("/a:entry/m:properties/d:RoomId", xmlString);
    }
    if (teamId) {
      assertXpathExists("/a:entry/m:properties/d:TeamId", xmlString);
    } else {
      assertXpathNotExists("/a:entry/m:properties/d:TeamId", xmlString);
    }
  }

  /**
   * Verify navigation properties.
   *
   * @param xmlString the xml string
   * @param neManager the ne manager
   * @param neRoom the ne room
   * @param neTeam the ne team
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws SAXException the SAX exception
   * @throws XpathException the xpath exception
   */
  private void verifyNavigationProperties(final String xmlString, final boolean neManager, final boolean neRoom,
      final boolean neTeam) throws IOException, SAXException, XpathException {
    if (neManager) {
      assertXpathExists("/a:entry/a:link[@href=\"Employees('1')/ne_Manager\" and @title='ne_Manager']", xmlString);
    } else {
      assertXpathNotExists("/a:entry/a:link[@href=\"Employees('1')/ne_Manager\" and @title='ne_Manager']", xmlString);
    }
    if (neRoom) {
      assertXpathExists("/a:entry/a:link[@href=\"Employees('1')/ne_Room\" and @title='ne_Room']", xmlString);
    } else {
      assertXpathNotExists("/a:entry/a:link[@href=\"Employees('1')/ne_Room\" and @title='ne_Room']", xmlString);
    }
    if (neTeam) {
      assertXpathExists("/a:entry/a:link[@href=\"Employees('1')/ne_Team\" and @title='ne_Team']", xmlString);
    } else {
      assertXpathNotExists("/a:entry/a:link[@href=\"Employees('1')/ne_Team\" and @title='ne_Team']", xmlString);
    }
  }

  /**
   * Verify response.
   *
   * @param response the response
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String verifyResponse(final ODataResponse response) throws IOException {
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntityProvider should not set content header", response.getContentHeader());
    String xmlString = StringHelper.inputStreamToString((InputStream) response.getEntity());
    return xmlString;
  }

  /**
   * Gets the select expand tree.
   *
   * @param selectString the select string
   * @param expandString the expand string
   * @return the select expand tree
   * @throws Exception the exception
   */
  private ExpandSelectTreeNode getSelectExpandTree(final String selectString, final String expandString)
      throws Exception {

    Edm edm = RuntimeDelegate.createEdm(new EdmTestProvider());
    UriParserImpl uriParser = new UriParserImpl(edm);

    List<PathSegment> pathSegments = new ArrayList<PathSegment>();
    pathSegments.add(new ODataPathSegmentImpl("Employees('1')", null));

    Map<String, String> queryParameters = new HashMap<String, String>();
    if (selectString != null) {
      queryParameters.put("$select", selectString);
    }
    if (expandString != null) {
      queryParameters.put("$expand", expandString);
    }
    UriInfo uriInfo = uriParser.parse(pathSegments, queryParameters);

    ExpandSelectTreeCreator expandSelectTreeCreator =
        new ExpandSelectTreeCreator(uriInfo.getSelect(), uriInfo.getExpand());
    ExpandSelectTreeNode expandSelectTree = expandSelectTreeCreator.create();
    assertNotNull(expandSelectTree);
    return expandSelectTree;
  }

}
