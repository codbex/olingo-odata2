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
package org.apache.olingo.odata2.jpa.processor.core.mock.data;

import static org.junit.Assert.fail;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.ep.callback.WriteEntryCallbackContext;
import org.apache.olingo.odata2.api.ep.callback.WriteFeedCallbackContext;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.apache.olingo.odata2.api.uri.NavigationPropertySegment;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import org.easymock.EasyMock;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmMockUtil.
 */
public class EdmMockUtil {

  /**
   * Mock expand select tree node.
   *
   * @return the expand select tree node
   */
  public static ExpandSelectTreeNode mockExpandSelectTreeNode() {
    ExpandSelectTreeNode nextExpandNode = EasyMock.createMock(ExpandSelectTreeNode.class);
    Map<String, ExpandSelectTreeNode> nextLink = null;
    EasyMock.expect(nextExpandNode.getLinks()).andStubReturn(nextLink);
    EasyMock.replay(nextExpandNode);
    ExpandSelectTreeNode expandNode = EasyMock.createMock(ExpandSelectTreeNode.class);
    Map<String, ExpandSelectTreeNode> links = new HashMap<String, ExpandSelectTreeNode>();
    links.put("SalesOrderLineItemDetails", nextExpandNode);
    EasyMock.expect(expandNode.getLinks()).andStubReturn(links);
    List<EdmProperty> edmPropertyList = new ArrayList<EdmProperty>();
    edmPropertyList.add(mockEdmPropertyOfTarget());
    EasyMock.expect(expandNode.getProperties()).andReturn(edmPropertyList).anyTimes();
    EasyMock.replay(expandNode);
    return expandNode;
  }

  /**
   * Mock current expand select tree node.
   *
   * @return the expand select tree node
   */
  public static ExpandSelectTreeNode mockCurrentExpandSelectTreeNode() {
    ExpandSelectTreeNode expandNode = EasyMock.createMock(ExpandSelectTreeNode.class);
    Map<String, ExpandSelectTreeNode> links = new HashMap<String, ExpandSelectTreeNode>();
    EasyMock.expect(expandNode.getLinks()).andStubReturn(links);
    List<EdmProperty> edmPropertyList = new ArrayList<EdmProperty>();
    edmPropertyList.add(mockEdmPropertyOfTarget());
    EasyMock.expect(expandNode.getProperties()).andReturn(edmPropertyList).anyTimes();
    EasyMock.replay(expandNode);
    return expandNode;
  }

  /**
   * Gets the expand list.
   *
   * @return the expand list
   */
  public static List<ArrayList<NavigationPropertySegment>> getExpandList() {
    List<ArrayList<NavigationPropertySegment>> expandList = new ArrayList<ArrayList<NavigationPropertySegment>>();
    ArrayList<NavigationPropertySegment> expands = new ArrayList<NavigationPropertySegment>();
    expands.add(mockNavigationPropertySegment());
    expandList.add(expands);
    return expandList;
  }

  /**
   * Gets the write feed call back context.
   *
   * @return the write feed call back context
   */
  public static WriteFeedCallbackContext getWriteFeedCallBackContext() {
    URI selfLink = null;
    WriteFeedCallbackContext writeContext = new WriteFeedCallbackContext();
    try {
      selfLink = new URI("SalesOrders(2L)/SalesOrderLineItemDetails");
      writeContext.setSelfLink(selfLink);
      writeContext.setCurrentExpandSelectTreeNode(mockCurrentExpandSelectTreeNode());
      writeContext.setNavigationProperty(mockNavigationProperty());
      writeContext.setSourceEntitySet(mockSourceEntitySet());
      writeContext.setEntryData(getFeedData());
    } catch (URISyntaxException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    return writeContext;
  }

  /**
   * Gets the write entry call back context.
   *
   * @return the write entry call back context
   */
  public static WriteEntryCallbackContext getWriteEntryCallBackContext() {
    WriteEntryCallbackContext writeContext = new WriteEntryCallbackContext();
    writeContext.setCurrentExpandSelectTreeNode(mockCurrentExpandSelectTreeNode());
    writeContext.setNavigationProperty(mockNavigationProperty());
    writeContext.setSourceEntitySet(mockSourceEntitySet());
    writeContext.setEntryData(getEntryData());
    return writeContext;
  }

  /**
   * Mock source entity set.
   *
   * @return the edm entity set
   */
  private static EdmEntitySet mockSourceEntitySet() {
    EdmEntitySet entitySet = EasyMock.createMock(EdmEntitySet.class);
    try {
      EasyMock.expect(entitySet.getEntityType()).andStubReturn(mockSourceEdmEntityType());
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(entitySet);
    return entitySet;
  }

  /**
   * Mock source edm entity type.
   *
   * @return the edm entity type
   */
  public static EdmEntityType mockSourceEdmEntityType() {
    EdmEntityType entityType = EasyMock.createMock(EdmEntityType.class);
    EdmMapping mapping = EasyMock.createMock(EdmMapping.class);
    List<String> navigationPropertyNames = new ArrayList<String>();
    List<String> propertyNames = new ArrayList<String>();
    propertyNames.add("id");
    propertyNames.add("description");
    navigationPropertyNames.add("SalesOrderLineItemDetails");
    try {
      EasyMock.expect(mapping.getInternalName()).andStubReturn("SalesOrderHeader");
      EasyMock.replay(mapping);
      EasyMock.expect(entityType.getName()).andStubReturn("SalesOrderHeader");
      EasyMock.expect(entityType.getMapping()).andStubReturn(mapping);
      EasyMock.expect(entityType.getNavigationPropertyNames()).andStubReturn(navigationPropertyNames);
      EasyMock.expect(entityType.getProperty("SalesOrderLineItemDetails")).andStubReturn(mockNavigationProperty());
      EdmProperty property1 = mockEdmPropertyOfSource1();
      EasyMock.expect(entityType.getProperty("id")).andStubReturn(property1);
      EasyMock.expect(entityType.getProperty("description")).andStubReturn(mockEdmPropertyOfSource2());
      EasyMock.expect(entityType.getPropertyNames()).andStubReturn(propertyNames);
      List<EdmProperty> keyProperties = new ArrayList<EdmProperty>();
      keyProperties.add(property1);
      EasyMock.expect(entityType.getKeyProperties()).andStubReturn(keyProperties);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(entityType);
    return entityType;
  }

  /**
   * Mock edm property of source 2.
   *
   * @return the edm typed
   */
  private static EdmTyped mockEdmPropertyOfSource2() {
    EdmProperty edmProperty = EasyMock.createMock(EdmProperty.class);
    EdmType type = EasyMock.createMock(EdmType.class);
    EasyMock.expect(type.getKind()).andStubReturn(EdmTypeKind.SIMPLE);
    EasyMock.replay(type);
    EdmMapping mapping = EasyMock.createMock(EdmMapping.class);
    EasyMock.expect(mapping.getInternalName()).andStubReturn("description");
    EasyMock.replay(mapping);
    try {
      EasyMock.expect(edmProperty.getName()).andStubReturn("description");
      EasyMock.expect(edmProperty.getType()).andStubReturn(type);
      EasyMock.expect(edmProperty.getMapping()).andStubReturn(mapping);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(edmProperty);
    return edmProperty;
  }

  /**
   * Mock edm property of source 1.
   *
   * @return the edm property
   */
  private static EdmProperty mockEdmPropertyOfSource1() {
    EdmProperty edmProperty = EasyMock.createMock(EdmProperty.class);
    EdmType type = EasyMock.createMock(EdmType.class);
    EasyMock.expect(type.getKind()).andStubReturn(EdmTypeKind.SIMPLE);
    EasyMock.replay(type);
    EdmMapping mapping = EasyMock.createMock(EdmMapping.class);
    EasyMock.expect(mapping.getInternalName()).andStubReturn("id");
    EasyMock.replay(mapping);
    try {
      EasyMock.expect(edmProperty.getName()).andStubReturn("id");
      EasyMock.expect(edmProperty.getType()).andStubReturn(type);
      EasyMock.expect(edmProperty.getMapping()).andStubReturn(mapping);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(edmProperty);
    return edmProperty;
  }

  /**
   * Gets the feed data.
   *
   * @return the feed data
   */
  private static Map<String, Object> getFeedData() {
    Map<String, Object> entryData = new HashMap<String, Object>();
    entryData.put("id", 1);
    entryData.put("description", "laptop");
    List<SalesOrderLineItem> salesOrderLineItems = new ArrayList<SalesOrderLineItem>();
    salesOrderLineItems.add(new SalesOrderLineItem(23));
    salesOrderLineItems.add(new SalesOrderLineItem(45));
    entryData.put("SalesOrderLineItemDetails", salesOrderLineItems);
    return entryData;
  }

  /**
   * Gets the entry data.
   *
   * @return the entry data
   */
  private static Map<String, Object> getEntryData() {
    Map<String, Object> entryData = new HashMap<String, Object>();
    entryData.put("id", 1);
    entryData.put("description", "laptop");
    entryData.put("SalesOrderLineItemDetails", new SalesOrderLineItem(23));
    return entryData;
  }

  /**
   * Mock navigation property segment.
   *
   * @param fromRoleAppendix the from role appendix
   * @return the navigation property segment
   */
  public static NavigationPropertySegment mockNavigationPropertySegment(String fromRoleAppendix) {
    NavigationPropertySegment navigationPropSegment = EasyMock.createMock(NavigationPropertySegment.class);
    EasyMock.expect(navigationPropSegment.getNavigationProperty()).andStubReturn(
        mockNavigationProperty(fromRoleAppendix));
    EasyMock.expect(navigationPropSegment.getTargetEntitySet()).andStubReturn(mockTargetEntitySet());
    EasyMock.replay(navigationPropSegment);
    return navigationPropSegment;
  }

  /**
   * Mock navigation property segment.
   *
   * @return the navigation property segment
   */
  public static NavigationPropertySegment mockNavigationPropertySegment() {
    return mockNavigationPropertySegment(null);
  }

  /**
   * Mock third navigation property segment.
   *
   * @return the navigation property segment
   */
  public static NavigationPropertySegment mockThirdNavigationPropertySegment() {
    NavigationPropertySegment navigationPropSegment = EasyMock.createMock(NavigationPropertySegment.class);
    EasyMock.expect(navigationPropSegment.getNavigationProperty()).andStubReturn(mockSecondNavigationProperty());
    EasyMock.expect(navigationPropSegment.getTargetEntitySet()).andStubReturn(mockThirdEntitySet());
    EasyMock.replay(navigationPropSegment);
    return navigationPropSegment;
  }

  /**
   * Mock second navigation property.
   *
   * @return the edm navigation property
   */
  public static EdmNavigationProperty mockSecondNavigationProperty() {
    EdmNavigationProperty navigationProperty = EasyMock.createMock(EdmNavigationProperty.class);
    EdmMapping mapping = EasyMock.createMock(EdmMapping.class);
    EasyMock.expect(mapping.getInternalName()).andStubReturn("materials");
    EasyMock.replay(mapping);
    try {
      EasyMock.expect(navigationProperty.getMultiplicity()).andStubReturn(EdmMultiplicity.ONE);
      EasyMock.expect(navigationProperty.getMapping()).andStubReturn(mapping);
      EasyMock.expect(navigationProperty.getName()).andStubReturn("MaterialDetails");
      EasyMock.expect(navigationProperty.getFromRole()).andStubReturn("SalesOrderLineItem");
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(navigationProperty);
    return navigationProperty;
  }

  /**
   * Mock target entity set.
   *
   * @return the edm entity set
   */
  public static EdmEntitySet mockTargetEntitySet() {
    EdmEntitySet entitySet = EasyMock.createMock(EdmEntitySet.class);
    try {
      EasyMock.expect(entitySet.getEntityType()).andStubReturn(mockTargetEdmEntityType());
      EasyMock.expect(entitySet.getName()).andStubReturn("SalesOrderLineItems");
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(entitySet);
    return entitySet;
  }

  /**
   * Mock third entity set.
   *
   * @return the edm entity set
   */
  public static EdmEntitySet mockThirdEntitySet() {
    EdmEntitySet entitySet = EasyMock.createMock(EdmEntitySet.class);
    try {
      EasyMock.expect(entitySet.getEntityType()).andStubReturn(mockThirdEdmEntityType());
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(entitySet);
    return entitySet;

  }

  /**
   * Mock third edm entity type.
   *
   * @return the edm entity type
   */
  private static EdmEntityType mockThirdEdmEntityType() {
    EdmEntityType entityType = EasyMock.createMock(EdmEntityType.class);
    EdmMapping mapping = EasyMock.createMock(EdmMapping.class);

    List<String> propertyNames = new ArrayList<String>();
    propertyNames.add("price");
    try {
      EasyMock.expect(mapping.getInternalName()).andStubReturn("Material");
      EasyMock.replay(mapping);
      EasyMock.expect(entityType.getName()).andStubReturn("Material");
      EasyMock.expect(entityType.getMapping()).andStubReturn(mapping);
      EdmProperty property = mockEdmPropertyOfTarget();
      EasyMock.expect(entityType.getProperty("price")).andStubReturn(property);
      EasyMock.expect(entityType.getPropertyNames()).andStubReturn(propertyNames);

    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(entityType);
    return entityType;
  }

  /**
   * Mock target edm entity type.
   *
   * @return the edm entity type
   */
  public static EdmEntityType mockTargetEdmEntityType() {
    EdmEntityType entityType = EasyMock.createMock(EdmEntityType.class);
    EdmMapping mapping = EasyMock.createMock(EdmMapping.class);

    List<String> propertyNames = new ArrayList<String>();
    propertyNames.add("price");
    try {
      EasyMock.expect(mapping.getInternalName()).andStubReturn("SalesOrderLineItem");
      EasyMock.replay(mapping);
      EasyMock.expect(entityType.getName()).andStubReturn("SalesOrderLineItem");
      EasyMock.expect(entityType.getMapping()).andStubReturn(mapping);
      EdmProperty property = mockEdmPropertyOfTarget();
      EasyMock.expect(entityType.getProperty("price")).andStubReturn(property);
      EasyMock.expect(entityType.getPropertyNames()).andStubReturn(propertyNames);

      List<EdmProperty> keyProperties = new ArrayList<EdmProperty>();
      keyProperties.add(property);
      EasyMock.expect(entityType.getKeyProperties()).andStubReturn(keyProperties);

    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(entityType);
    return entityType;
  }

  /**
   * Mock edm property of target.
   *
   * @return the edm property
   */
  private static EdmProperty mockEdmPropertyOfTarget() {
    EdmProperty edmProperty = EasyMock.createMock(EdmProperty.class);

    EdmType type = EasyMock.createMock(EdmType.class);
    EasyMock.expect(type.getKind()).andStubReturn(EdmTypeKind.SIMPLE);
    EasyMock.replay(type);
    JPAEdmMappingImpl mapping = EasyMock.createMock(JPAEdmMappingImpl.class);
    EasyMock.expect(mapping.getInternalName()).andStubReturn("price");
    EasyMock.expect(((JPAEdmMappingImpl) mapping).isVirtualAccess()).andStubReturn(false);
    EasyMock.replay(mapping);
    try {
      EasyMock.expect(edmProperty.getName()).andStubReturn("price");
      EasyMock.expect(edmProperty.getType()).andStubReturn(type);
      EasyMock.expect(edmProperty.getMapping()).andStubReturn(mapping);
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(edmProperty);
    return edmProperty;
  }

  /**
   * Mock navigation property.
   *
   * @return the edm navigation property
   */
  public static EdmNavigationProperty mockNavigationProperty() {
    return mockNavigationProperty(null);
  }

  /**
   * Mock navigation property.
   *
   * @param fromRoleAppendix the from role appendix
   * @return the edm navigation property
   */
  public static EdmNavigationProperty mockNavigationProperty(String fromRoleAppendix) {
    EdmNavigationProperty navigationProperty = EasyMock.createMock(EdmNavigationProperty.class);
    EdmMapping mapping = EasyMock.createMock(EdmMapping.class);
    EasyMock.expect(mapping.getInternalName()).andStubReturn("salesOrderLineItems");
    EasyMock.replay(mapping);
    try {
      EasyMock.expect(navigationProperty.getMultiplicity()).andStubReturn(EdmMultiplicity.MANY);
      EasyMock.expect(navigationProperty.getMapping()).andStubReturn(mapping);
      EasyMock.expect(navigationProperty.getName()).andStubReturn("SalesOrderLineItemDetails");
      EasyMock.expect(navigationProperty.getFromRole()).andStubReturn("SalesOrderHeader"
      + (fromRoleAppendix == null? "": fromRoleAppendix));
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(navigationProperty);
    return navigationProperty;
  }

}
