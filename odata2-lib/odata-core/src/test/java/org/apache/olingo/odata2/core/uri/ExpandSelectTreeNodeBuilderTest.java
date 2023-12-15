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
package org.apache.olingo.odata2.core.uri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpandSelectTreeNodeBuilderTest.
 */
public class ExpandSelectTreeNodeBuilderTest extends BaseTest {

  /** The edm. */
  Edm edm;

  /**
   * Setup edm.
   *
   * @throws Exception the exception
   */
  @Before
  public void setupEdm() throws Exception {
    edm = MockFacade.getMockEdm();
  }

  /**
   * Initial build with only entity set.
   *
   * @throws Exception the exception
   */
  @Test
  public void initialBuildWithOnlyEntitySet() throws Exception {
    ExpandSelectTreeNode node = ExpandSelectTreeNode.entitySet(mock(EdmEntitySet.class)).build();
    assertNotNull(node);
    assertTrue(node.isAll());
    assertTrue(node.getProperties().isEmpty());
    assertTrue(node.getLinks().isEmpty());
  }

  /**
   * Builds the with right selected properties only.
   *
   * @throws Exception the exception
   */
  @Test
  public void buildWithRightSelectedPropertiesOnly() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> selectedPropertyNames = new ArrayList<String>();
    selectedPropertyNames.add("Id");
    ExpandSelectTreeNode node =
        ExpandSelectTreeNode.entitySet(roomsSet).selectedProperties(selectedPropertyNames).build();
    assertNotNull(node);
    assertFalse(node.isAll());
    assertFalse(node.getProperties().isEmpty());
    assertTrue(node.getLinks().isEmpty());

    assertEquals(1, node.getProperties().size());
    assertEquals("Id", node.getProperties().get(0).getName());
  }

  /**
   * Builds the with right selected navigation properties only.
   *
   * @throws Exception the exception
   */
  @Test
  public void buildWithRightSelectedNavigationPropertiesOnly() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> selectedNavigationPropertyNames = new ArrayList<String>();
    selectedNavigationPropertyNames.add("nr_Employees");
    ExpandSelectTreeNode node =
        ExpandSelectTreeNode.entitySet(roomsSet).selectedLinks(selectedNavigationPropertyNames).build();
    assertNotNull(node);
    assertFalse(node.isAll());
    assertTrue(node.getProperties().isEmpty());
    assertFalse(node.getLinks().isEmpty());

    assertEquals(1, node.getLinks().size());
    assertTrue(node.getLinks().containsKey("nr_Employees"));
    assertNull(node.getLinks().get("nr_Employees"));
  }

  /**
   * Builds the with right expanded navigation properties only.
   *
   * @throws Exception the exception
   */
  @Test
  public void buildWithRightExpandedNavigationPropertiesOnly() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    String navigationPropertyName = "nr_Employees";
    List<String> navigationPropertyNames = new ArrayList<String>();
    navigationPropertyNames.add(navigationPropertyName);

    ExpandSelectTreeNode node =
        ExpandSelectTreeNode.entitySet(roomsSet).expandedLinks(navigationPropertyNames).build();
    assertExpandedNode(node);

    assertLinksWithOneNavigationProperty(navigationPropertyName, node);
  }

  /**
   * Builds the with right custom expanded navigation property only.
   *
   * @throws Exception the exception
   */
  @Test
  public void buildWithRightCustomExpandedNavigationPropertyOnly() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    ExpandSelectTreeNode expandNode = ExpandSelectTreeNode.entitySet(mock(EdmEntitySet.class)).build();
    String navigationPropertyName = "nr_Employees";

    ExpandSelectTreeNode node =
        ExpandSelectTreeNode.entitySet(roomsSet).customExpandedLink(navigationPropertyName, expandNode).build();
    assertExpandedNode(node);

    assertLinksWithOneNavigationProperty(navigationPropertyName, node);
  }

  /**
   * Expanded and custom expanded nav prop custom must win.
   *
   * @throws Exception the exception
   */
  @Test
  public void expandedAndCustomExpandedNavPropCustomMustWin() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    ExpandSelectTreeNode expandNode = ExpandSelectTreeNode.entitySet(mock(EdmEntitySet.class)).build();
    String navigationPropertyName = "nr_Employees";
    List<String> navigationPropertyNames = new ArrayList<String>();
    navigationPropertyNames.add(navigationPropertyName);

    ExpandSelectTreeNode node = ExpandSelectTreeNode.entitySet(roomsSet)
        .customExpandedLink(navigationPropertyName, expandNode).expandedLinks(navigationPropertyNames).build();
    assertExpandedNode(node);

    assertLinksWithOneNavigationProperty(navigationPropertyName, node);
    assertEquals(expandNode, node.getLinks().get(navigationPropertyName));
  }

  /**
   * Selected properties and expanded navigation properties.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectedPropertiesAndExpandedNavigationProperties() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> selectedPropertyNames = new ArrayList<String>();
    selectedPropertyNames.add("Id");
    List<String> navigationPropertyNames = new ArrayList<String>();
    navigationPropertyNames.add("nr_Employees");
    ExpandSelectTreeNode node = ExpandSelectTreeNode.entitySet(roomsSet).selectedProperties(selectedPropertyNames)
        .expandedLinks(navigationPropertyNames).build();
    assertNotNull(node);
    assertFalse(node.isAll());
    assertFalse(node.getProperties().isEmpty());
    assertFalse(node.getLinks().isEmpty());

    assertEquals(1, node.getProperties().size());
    assertEquals("Id", node.getProperties().get(0).getName());

    assertLinksWithOneNavigationProperty("nr_Employees", node);
  }

  /**
   * Selected nav props and expanded nav props expanded must not be null.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectedNavPropsAndExpandedNavPropsExpandedMustNotBeNull() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    String navigationPropertyName = "nr_Employees";
    List<String> navigationPropertyNames = new ArrayList<String>();
    navigationPropertyNames.add(navigationPropertyName);

    ExpandSelectTreeNode node = ExpandSelectTreeNode.entitySet(roomsSet).selectedLinks(navigationPropertyNames)
        .expandedLinks(navigationPropertyNames).build();
    assertNotNull(node);
    assertFalse(node.isAll());
    assertTrue(node.getProperties().isEmpty());
    assertFalse(node.getLinks().isEmpty());

    assertLinksWithOneNavigationProperty(navigationPropertyName, node);
  }

  /**
   * Selected expanded and custom expanded.
   *
   * @throws Exception the exception
   */
  @Test
  public void selectedExpandedAndCustomExpanded() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> selectedPropertyNames = new ArrayList<String>();
    selectedPropertyNames.add("Id");
    List<String> navigationPropertyNames = new ArrayList<String>();
    navigationPropertyNames.add("nr_Employees");
    ExpandSelectTreeNode expandNode = ExpandSelectTreeNode.entitySet(mock(EdmEntitySet.class)).build();

    ExpandSelectTreeNode node =
        ExpandSelectTreeNode.entitySet(roomsSet).selectedProperties(selectedPropertyNames).expandedLinks(
            navigationPropertyNames).customExpandedLink("nr_Building", expandNode).build();

    assertNotNull(node);
    assertFalse(node.isAll());
    assertFalse(node.getProperties().isEmpty());
    assertFalse(node.getLinks().isEmpty());

    assertEquals(1, node.getProperties().size());
    assertEquals("Id", node.getProperties().get(0).getName());

    assertEquals(2, node.getLinks().size());
    assertTrue(node.getLinks().containsKey("nr_Employees"));
    assertNotNull(node.getLinks().get("nr_Employees"));

    assertTrue(node.getLinks().containsKey("nr_Building"));
    assertNotNull(node.getLinks().get("nr_Building"));
    assertEquals(expandNode, node.getLinks().get("nr_Building"));
  }

  /**
   * Builds the with wrong selected properties only.
   *
   * @throws Exception the exception
   */
  @Test(expected = EdmException.class)
  public void buildWithWrongSelectedPropertiesOnly() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> selectedPropertyNames = new ArrayList<String>();
    selectedPropertyNames.add("WrongProperty");
    ExpandSelectTreeNode.entitySet(roomsSet).selectedProperties(selectedPropertyNames).build();
  }

  /**
   * Builds the with wrong selected navigation properties only.
   *
   * @throws Exception the exception
   */
  @Test(expected = EdmException.class)
  public void buildWithWrongSelectedNavigationPropertiesOnly() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> selectedNavigationPropertyNames = new ArrayList<String>();
    selectedNavigationPropertyNames.add("WrongProperty");
    ExpandSelectTreeNode.entitySet(roomsSet).selectedLinks(selectedNavigationPropertyNames).build();
  }

  /**
   * Property in navigation properties list.
   *
   * @throws Exception the exception
   */
  @Test(expected = EdmException.class)
  public void propertyInNavigationPropertiesList() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> selectedNavigationPropertyNames = new ArrayList<String>();
    selectedNavigationPropertyNames.add("Id");
    ExpandSelectTreeNode.entitySet(roomsSet).selectedLinks(selectedNavigationPropertyNames).build();
  }

  /**
   * Navigation property in properties list.
   *
   * @throws Exception the exception
   */
  @Test(expected = EdmException.class)
  public void navigationPropertyInPropertiesList() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> selectedPropertyNames = new ArrayList<String>();
    selectedPropertyNames.add("nr_Building");
    ExpandSelectTreeNode.entitySet(roomsSet).selectedProperties(selectedPropertyNames).build();
  }

  /**
   * Property in expanded navigation property list.
   *
   * @throws Exception the exception
   */
  @Test(expected = EdmException.class)
  public void propertyInExpandedNavigationPropertyList() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> expandedNavigationPropertyNames = new ArrayList<String>();
    expandedNavigationPropertyNames.add("Id");
    ExpandSelectTreeNode.entitySet(roomsSet).expandedLinks(expandedNavigationPropertyNames).build();
  }

  /**
   * Property in custom expanded navigation property list.
   *
   * @throws Exception the exception
   */
  @Test(expected = EdmException.class)
  public void propertyInCustomExpandedNavigationPropertyList() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    ExpandSelectTreeNode.entitySet(roomsSet).customExpandedLink("Id", mock(ExpandSelectTreeNode.class)).build();
  }

  /**
   * Null node in custom expanded property.
   *
   * @throws Exception the exception
   */
  @Test(expected = ODataRuntimeException.class)
  public void nullNodeInCustomExpandedProperty() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    ExpandSelectTreeNode.entitySet(roomsSet).customExpandedLink("nr_Building", null).build();
  }

  /**
   * Wrong navigation property in custom expand.
   *
   * @throws Exception the exception
   */
  @Test(expected = EdmException.class)
  public void wrongNavigationPropertyInCustomExpand() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    ExpandSelectTreeNode.entitySet(roomsSet).customExpandedLink("Wrong", mock(ExpandSelectTreeNode.class)).build();
  }

  /**
   * Wrong navigation property in expanded list.
   *
   * @throws Exception the exception
   */
  @Test(expected = EdmException.class)
  public void wrongNavigationPropertyInExpandedList() throws Exception {
    EdmEntitySet roomsSet = edm.getDefaultEntityContainer().getEntitySet("Rooms");
    List<String> expandedNavigationPropertyNames = new ArrayList<String>();
    expandedNavigationPropertyNames.add("Wrong");
    ExpandSelectTreeNode.entitySet(roomsSet).expandedLinks(expandedNavigationPropertyNames).build();
  }

  /**
   * Assert links with one navigation property.
   *
   * @param navigationPropertyName the navigation property name
   * @param node the node
   */
  private void
      assertLinksWithOneNavigationProperty(final String navigationPropertyName, final ExpandSelectTreeNode node) {
    assertEquals(1, node.getLinks().size());
    assertTrue(node.getLinks().containsKey(navigationPropertyName));
    assertNotNull(node.getLinks().get(navigationPropertyName));
  }

  /**
   * Assert expanded node.
   *
   * @param node the node
   */
  private void assertExpandedNode(final ExpandSelectTreeNode node) {
    assertNotNull(node);
    assertTrue(node.isAll());
    assertTrue(node.getProperties().isEmpty());
    assertFalse(node.getLinks().isEmpty());
  }

}
