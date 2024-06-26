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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.apache.olingo.odata2.api.uri.NavigationPropertySegment;
import org.apache.olingo.odata2.api.uri.SelectItem;
import org.apache.olingo.odata2.core.uri.ExpandSelectTreeNodeImpl.AllKinds;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpandSelectTreeCreator.
 */
public class ExpandSelectTreeCreator {

  /** The initial select. */
  private List<SelectItem> initialSelect;
  
  /** The initial expand. */
  private List<ArrayList<NavigationPropertySegment>> initialExpand;

  /**
   * Instantiates a new expand select tree creator.
   *
   * @param select the select
   * @param expand the expand
   */
  public ExpandSelectTreeCreator(final List<SelectItem> select,
      final List<ArrayList<NavigationPropertySegment>> expand) {
    if (select != null) {
      initialSelect = select;
    } else {
      initialSelect = Collections.emptyList();
    }

    if (expand != null) {
      initialExpand = expand;
    } else {
      initialExpand = Collections.emptyList();
    }
  }

  /**
   * Creates the.
   *
   * @return the expand select tree node impl
   * @throws EdmException the edm exception
   */
  public ExpandSelectTreeNodeImpl create() throws EdmException {

    // Initial node
    ExpandSelectTreeNodeImpl root = new ExpandSelectTreeNodeImpl();
    if (!initialSelect.isEmpty()) {
      // Create a full expand tree
      createSelectTree(root);
    } else {
      // If no select is given the root node is explicitly selected for all expand clauses
      root.setExplicitlySelected();
    }
    // Merge in the expand tree
    mergeExpandTree(root);

    // consolidate the tree
    consolidate(root);
    return root;
  }

  /**
   * Consolidate.
   *
   * @param node the node
   */
  private void consolidate(final ExpandSelectTreeNodeImpl node) {
    switch (node.getAllKind()) {
    case EXPLICITLYTRUE:
    case IMPLICITLYTRUE:
      consolidateTrueNode(node);
      break;
    case FALSE:
      consolidateFalseNode(node);
    }

  }

  /**
   * Consolidate false node.
   *
   * @param node the node
   */
  private void consolidateFalseNode(final ExpandSelectTreeNodeImpl node) {
    Iterator<Map.Entry<String, ExpandSelectTreeNode>> iterator = node.getLinks().entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry<String, ExpandSelectTreeNode> entry = iterator.next();
      ExpandSelectTreeNodeImpl subNode = (ExpandSelectTreeNodeImpl) entry.getValue();
      if (!subNode.isExpanded()) {
        node.putLink(entry.getKey(), null);
      } else {
        consolidate(subNode);
      }
    }
  }

  /**
   * Consolidate true node.
   *
   * @param node the node
   */
  private void consolidateTrueNode(final ExpandSelectTreeNodeImpl node) {
    Map<String, ExpandSelectTreeNode> links = node.getLinks();
    Set<Entry<String, ExpandSelectTreeNode>> linkEntries = links.entrySet();
    List<String> toRemove = new ArrayList<String>();

    for (Entry<String, ExpandSelectTreeNode> entry : linkEntries) {
      ExpandSelectTreeNodeImpl subNode = (ExpandSelectTreeNodeImpl) entry.getValue();
      if (subNode.isExpanded() && node.isExplicitlySelected()) {
        subNode.setExplicitlySelected();
        consolidate(subNode);
      } else if (subNode.isExpanded()) {
        consolidate(subNode);
      } else {
        toRemove.add(entry.getKey());
      }
    }

    //
    for (String key : toRemove) {
      node.removeLink(key);
    }
  }

  /**
   * Creates the select tree.
   *
   * @param root the root
   * @throws EdmException the edm exception
   */
  private void createSelectTree(final ExpandSelectTreeNodeImpl root) throws EdmException {
    for (SelectItem item : initialSelect) {
      ExpandSelectTreeNodeImpl actualNode = root;

      for (NavigationPropertySegment navSegement : item.getNavigationPropertySegments()) {
        actualNode = addSelectNode(actualNode, navSegement.getNavigationProperty().getName());
      }

      if (item.getProperty() != null) {
        actualNode.addProperty(item.getProperty());
      } else if (item.isStar()) {
        actualNode.setAllExplicitly();
      } else {
        // The actual node is a navigation property and has no property or star so it is explicitly selected
        actualNode.setExplicitlySelected();
      }
    }
  }

  /**
   * Adds the select node.
   *
   * @param actualNode the actual node
   * @param navigationPropertyName the navigation property name
   * @return the expand select tree node impl
   */
  private ExpandSelectTreeNodeImpl addSelectNode(final ExpandSelectTreeNodeImpl actualNode,
      final String navigationPropertyName) {
    Map<String, ExpandSelectTreeNode> links = actualNode.getLinks();
    if (!links.containsKey(navigationPropertyName)) {
      ExpandSelectTreeNodeImpl subNode = new ExpandSelectTreeNodeImpl();
      actualNode.putLink(navigationPropertyName, subNode);
      if (actualNode.isExplicitlySelected()) {
        // if a node was explicitly selected all sub nodes are explicitly selected
        subNode.setExplicitlySelected();
      } else {
        if (actualNode.getAllKind() == AllKinds.IMPLICITLYTRUE) {
          actualNode.setAllKindFalse();
        }
      }
      return subNode;
    } else {
      return (ExpandSelectTreeNodeImpl) links.get(navigationPropertyName);
    }
  }

  /**
   * Merge expand tree.
   *
   * @param root the root
   * @throws EdmException the edm exception
   */
  private void mergeExpandTree(final ExpandSelectTreeNodeImpl root) throws EdmException {
    for (ArrayList<NavigationPropertySegment> singleExpand : initialExpand) {
      ExpandSelectTreeNodeImpl actualNode = root;
      for (NavigationPropertySegment navSegment : singleExpand) {
        actualNode = addExpandNode(actualNode, navSegment.getNavigationProperty().getName());
        if (actualNode == null) {
          break;
        }
      }
    }

  }

  /**
   * Adds the expand node.
   *
   * @param actualNode the actual node
   * @param navigationPropertyName the navigation property name
   * @return the expand select tree node impl
   */
  private ExpandSelectTreeNodeImpl addExpandNode(final ExpandSelectTreeNodeImpl actualNode,
      final String navigationPropertyName) {
    Map<String, ExpandSelectTreeNode> links = actualNode.getLinks();
    if (!links.containsKey(navigationPropertyName)) {
      if (actualNode.isExplicitlySelected() || (actualNode.isExplicitlySelected() && actualNode.isExpanded())) {
        ExpandSelectTreeNodeImpl subNode = new ExpandSelectTreeNodeImpl();
        subNode.setExpanded();
        subNode.setExplicitlySelected();
        actualNode.putLink(navigationPropertyName, subNode);
        return subNode;
      } else {
        return null;
      }
    } else {
      ExpandSelectTreeNodeImpl subNode = (ExpandSelectTreeNodeImpl) links.get(navigationPropertyName);
      subNode.setExpanded();
      return subNode;
    }
  }

}
