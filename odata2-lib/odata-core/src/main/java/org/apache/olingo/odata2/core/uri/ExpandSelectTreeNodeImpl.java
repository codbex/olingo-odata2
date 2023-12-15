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

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpandSelectTreeNodeImpl.
 */
public class ExpandSelectTreeNodeImpl extends ExpandSelectTreeNode {

  /**
   * The Enum AllKinds.
   */
  public enum AllKinds {
    
    /** The implicitlytrue. */
    IMPLICITLYTRUE(true), 
 /** The explicitlytrue. */
 EXPLICITLYTRUE(true), 
 /** The false. */
 FALSE(false);

    /** The boolean representation. */
    private boolean booleanRepresentation;

    /**
     * Instantiates a new all kinds.
     *
     * @param booleanRepresentation the boolean representation
     */
    private AllKinds(final boolean booleanRepresentation) {
      this.booleanRepresentation = booleanRepresentation;
    }

    /**
     * Gets the boolean.
     *
     * @return the boolean
     */
    public boolean getBoolean() {
      return booleanRepresentation;
    }
  }

  /** The is all. */
  private AllKinds isAll = AllKinds.IMPLICITLYTRUE;
  
  /** The is explicitly selected. */
  private boolean isExplicitlySelected = false;
  
  /** The is expanded. */
  private boolean isExpanded = false;
  
  /** The properties. */
  private final List<EdmProperty> properties = new ArrayList<EdmProperty>();
  
  /** The links. */
  private final Map<String, ExpandSelectTreeNodeImpl> links = new HashMap<String, ExpandSelectTreeNodeImpl>();
  
  /** The expandselect tree nodes. */
  private final List<ExpandSelectTreeNode> expandselectTreeNodes = new ArrayList<ExpandSelectTreeNode>();
  
  /**
   * Checks if is all.
   *
   * @return true, if is all
   */
  @Override
  public boolean isAll() {
    return isAll.getBoolean();
  }

  /**
   * Gets the properties.
   *
   * @return the properties
   */
  @Override
  public List<EdmProperty> getProperties() {
    return properties;
  }

  /**
   * Gets the links.
   *
   * @return the links
   */
  @SuppressWarnings("unchecked")
  @Override
  public Map<String, ExpandSelectTreeNode> getLinks() {
    return (Map<String, ExpandSelectTreeNode>) ((Map<String, ? extends ExpandSelectTreeNode>) Collections
        .unmodifiableMap(links));
  }
  
  /**
   * Gets the expanded list.
   *
   * @return the expanded list
   */
  @Override
  public List<ExpandSelectTreeNode> getExpandedList() {
    return expandselectTreeNodes;
  }

  /**
   * Put link.
   *
   * @param name the name
   * @param node the node
   */
  public void putLink(final String name, final ExpandSelectTreeNodeImpl node) {
    links.put(name, node);
  }

  /**
   * Removes the link.
   *
   * @param name the name
   */
  public void removeLink(final String name) {
    links.remove(name);
  }

  /**
   * Checks if is explicitly selected.
   *
   * @return true, if is explicitly selected
   */
  public boolean isExplicitlySelected() {
    return isExplicitlySelected;
  }

  /**
   * Sets the explicitly selected.
   */
  public void setExplicitlySelected() {
    isExplicitlySelected = true;
    setAllExplicitly();
  }

  /**
   * Checks if is expanded.
   *
   * @return true, if is expanded
   */
  public boolean isExpanded() {
    return isExpanded;
  }

  /**
   * Sets the expanded.
   */
  public void setExpanded() {
    isExpanded = true;
  }

  /**
   * Adds the property.
   *
   * @param property the property
   */
  public void addProperty(final EdmProperty property) {
    if (property != null && isAll != AllKinds.EXPLICITLYTRUE && !properties.contains(property)) {
      properties.add(property);
      isAll = AllKinds.FALSE;
    }
  }

  /**
   * Sets the all explicitly.
   */
  public void setAllExplicitly() {
    properties.clear();
    isAll = AllKinds.EXPLICITLYTRUE;
  }

  /**
   * Gets the all kind.
   *
   * @return the all kind
   */
  public AllKinds getAllKind() {
    return isAll;
  }

  /**
   * Sets the all kind false.
   */
  public void setAllKindFalse() {
    isAll = AllKinds.FALSE;
  }

  /**
   * To json string.
   *
   * @return the string
   */
  public String toJsonString() {
    try {
      StringWriter writer = new StringWriter();
      JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
      jsonStreamWriter.beginObject()
          .name("all").unquotedValue(Boolean.toString(isAll())).separator()
          .name("properties")
          .beginArray();
      boolean first = true;
      for (EdmProperty property : properties) {
        if (first) {
          first = false;
        } else {
          jsonStreamWriter.separator();
        }
        jsonStreamWriter.stringValueRaw(property.getName());
      }
      jsonStreamWriter.endArray().separator()
          .name("links")
          .beginArray();
      first = true;
      for (Map.Entry<String, ExpandSelectTreeNodeImpl> entry : links.entrySet()) {
        if (first) {
          first = false;
        } else {
          jsonStreamWriter.separator();
        }
        final String nodeString = entry.getValue() == null ? null : entry.getValue().toJsonString();
        jsonStreamWriter.beginObject()
            .name(entry.getKey()).unquotedValue(nodeString)
            .endObject();
      }
      jsonStreamWriter.endArray()
          .endObject();
      writer.flush();
      return writer.toString();
    } catch (final IOException e) {
      throw new ODataRuntimeException("IOException: ", e);
    } catch (final EdmException e) {
      throw new ODataRuntimeException("EdmException: ", e);
    }
  }

  /**
   * The Class ExpandSelectTreeNodeBuilderImpl.
   */
  public class ExpandSelectTreeNodeBuilderImpl extends ExpandSelectTreeNodeBuilder {

    /** The entity set. */
    private EdmEntitySet entitySet;
    
    /** The selected property names. */
    private List<String> selectedPropertyNames;
    
    /** The selected navigation property names. */
    private List<String> selectedNavigationPropertyNames;
    
    /** The custom expanded navigation properties. */
    private Map<String, ExpandSelectTreeNode> customExpandedNavigationProperties;
    
    /** The expanded navigation property names. */
    private List<String> expandedNavigationPropertyNames;

    /**
     * Entity set.
     *
     * @param entitySet the entity set
     * @return the expand select tree node builder
     */
    @Override
    public ExpandSelectTreeNodeBuilder entitySet(final EdmEntitySet entitySet) {
      this.entitySet = entitySet;
      return this;
    }

    /**
     * Builds the.
     *
     * @return the expand select tree node
     * @throws EdmException the edm exception
     */
    @Override
    public ExpandSelectTreeNode build() throws EdmException {
      EdmEntityType entityType = entitySet.getEntityType();
      if (selectedPropertyNames != null) {
        handleProperties(entityType);
      }

      if (selectedNavigationPropertyNames != null) {
        setAllKindFalse();
        handleLinks(entityType, selectedNavigationPropertyNames, null);
      }

      if (expandedNavigationPropertyNames != null) {
        ExpandSelectTreeNodeImpl subNode = new ExpandSelectTreeNodeImpl();
        subNode.setExplicitlySelected();
        handleLinks(entityType, expandedNavigationPropertyNames, subNode);
      }

      if (customExpandedNavigationProperties != null) {
        handleCustomLinks(entityType);
      }

      return ExpandSelectTreeNodeImpl.this;
    }

    /**
     * Handle custom links.
     *
     * @param entityType the entity type
     * @throws EdmException the edm exception
     */
    private void handleCustomLinks(final EdmEntityType entityType) throws EdmException {
      for (Map.Entry<String, ExpandSelectTreeNode> entry : customExpandedNavigationProperties.entrySet()) {
        EdmTyped navigationProperty = entityType.getProperty(entry.getKey());
        if (navigationProperty == null) {
          throw new EdmException(EdmException.NAVIGATIONPROPERTYNOTFOUND.addContent(entry.getKey()));
        }
        if (!(navigationProperty instanceof EdmNavigationProperty)) {
          throw new EdmException(EdmException.MUSTBENAVIGATIONPROPERTY.addContent(entry.getKey()));
        }
        putLink(entry.getKey(), (ExpandSelectTreeNodeImpl) entry.getValue());
      }
    }

    /**
     * Handle links.
     *
     * @param entityType the entity type
     * @param names the names
     * @param subNode the sub node
     * @throws EdmException the edm exception
     */
    private void handleLinks(final EdmEntityType entityType, final List<String> names,
        final ExpandSelectTreeNodeImpl subNode) throws EdmException {
      for (String navigationPropertyName : names) {
        EdmTyped navigationProperty = entityType.getProperty(navigationPropertyName);
        if (navigationProperty == null) {
          throw new EdmException(EdmException.NAVIGATIONPROPERTYNOTFOUND.addContent(navigationPropertyName));
        } else if (!(navigationProperty instanceof EdmNavigationProperty)) {
          throw new EdmException(EdmException.MUSTBENAVIGATIONPROPERTY.addContent(navigationPropertyName));
        }
        putLink(navigationPropertyName, subNode);
      }
    }

    /**
     * Handle properties.
     *
     * @param entityType the entity type
     * @throws EdmException the edm exception
     */
    private void handleProperties(final EdmEntityType entityType) throws EdmException {
      for (String propertyName : selectedPropertyNames) {
        EdmTyped property = entityType.getProperty(propertyName);
        if (property == null) {
          throw new EdmException(EdmException.PROPERTYNOTFOUND.addContent(propertyName));
        } else if (!(property instanceof EdmProperty)) {
          throw new EdmException(EdmException.MUSTBEPROPERTY.addContent(propertyName));
        }
        addProperty((EdmProperty) property);
      }
    }

    /**
     * Selected properties.
     *
     * @param selectedPropertyNames the selected property names
     * @return the expand select tree node builder
     */
    @Override
    public ExpandSelectTreeNodeBuilder selectedProperties(final List<String> selectedPropertyNames) {
      this.selectedPropertyNames = selectedPropertyNames;
      return this;
    }

    /**
     * Selected links.
     *
     * @param selectedNavigationPropertyNames the selected navigation property names
     * @return the expand select tree node builder
     */
    @Override
    public ExpandSelectTreeNodeBuilder selectedLinks(final List<String> selectedNavigationPropertyNames) {
      this.selectedNavigationPropertyNames = selectedNavigationPropertyNames;
      return this;
    }

    /**
     * Custom expanded link.
     *
     * @param navigationPropertyName the navigation property name
     * @param expandNode the expand node
     * @return the expand select tree node builder
     */
    @Override
    public ExpandSelectTreeNodeBuilder
        customExpandedLink(final String navigationPropertyName, final ExpandSelectTreeNode expandNode) {
      if (expandNode == null) {
        throw new ODataRuntimeException("ExpandNode must not be null");
      }
      if (customExpandedNavigationProperties == null) {
        customExpandedNavigationProperties = new HashMap<String, ExpandSelectTreeNode>();
      }
      customExpandedNavigationProperties.put(navigationPropertyName, expandNode);
      return this;
    }

    /**
     * Expanded links.
     *
     * @param navigationPropertyNames the navigation property names
     * @return the expand select tree node builder
     */
    @Override
    public ExpandSelectTreeNodeBuilder expandedLinks(final List<String> navigationPropertyNames) {
      expandedNavigationPropertyNames = navigationPropertyNames;
      return this;
    }

  }
}
