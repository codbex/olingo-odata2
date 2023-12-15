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
package org.apache.olingo.odata2.core.ep.entry;

import java.util.Map;

import org.apache.olingo.odata2.api.ep.entry.EntryMetadata;
import org.apache.olingo.odata2.api.ep.entry.MediaMetadata;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.apache.olingo.odata2.core.uri.ExpandSelectTreeNodeImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataEntryImpl.
 */
public class ODataEntryImpl implements ODataEntry {

  /** The data. */
  private final Map<String, Object> data;
  
  /** The entry metadata. */
  private final EntryMetadata entryMetadata;
  
  /** The media metadata. */
  private final MediaMetadata mediaMetadata;
  
  /** The expand select tree. */
  private final ExpandSelectTreeNode expandSelectTree;
  
  /** The contains inline entry. */
  private boolean containsInlineEntry;

  /**
   * Instantiates a new o data entry impl.
   *
   * @param data the data
   * @param mediaMetadata the media metadata
   * @param entryMetadata the entry metadata
   * @param expandSelectTree the expand select tree
   */
  public ODataEntryImpl(final Map<String, Object> data, final MediaMetadata mediaMetadata,
      final EntryMetadata entryMetadata, final ExpandSelectTreeNodeImpl expandSelectTree) {
    this(data, mediaMetadata, entryMetadata, expandSelectTree, false);
  }

  /**
   * Instantiates a new o data entry impl.
   *
   * @param data the data
   * @param mediaMetadata the media metadata
   * @param entryMetadata the entry metadata
   * @param expandSelectTree the expand select tree
   * @param containsInlineEntry the contains inline entry
   */
  public ODataEntryImpl(final Map<String, Object> data, final MediaMetadata mediaMetadata,
      final EntryMetadata entryMetadata, final ExpandSelectTreeNode expandSelectTree,
      final boolean containsInlineEntry) {
    this.data = data;
    this.entryMetadata = entryMetadata;
    this.mediaMetadata = mediaMetadata;
    this.expandSelectTree = expandSelectTree;
    this.containsInlineEntry = containsInlineEntry;
  }

  /**
   * Gets the properties.
   *
   * @return the properties
   */
  @Override
  public Map<String, Object> getProperties() {
    return data;
  }

  /**
   * Gets the media metadata.
   *
   * @return the media metadata
   */
  @Override
  public MediaMetadata getMediaMetadata() {
    return mediaMetadata;
  }

  /**
   * Gets the metadata.
   *
   * @return the metadata
   */
  @Override
  public EntryMetadata getMetadata() {
    return entryMetadata;
  }

  /**
   * Contains inline entry.
   *
   * @return true, if successful
   */
  @Override
  public boolean containsInlineEntry() {
    return containsInlineEntry;
  }

  /**
   * Gets the expand select tree.
   *
   * @return the expand select tree
   */
  @Override
  public ExpandSelectTreeNode getExpandSelectTree() {
    return expandSelectTree;
  }

  /**
   * Sets the contains inline entry.
   *
   * @param containsInlineEntry the new contains inline entry
   */
  public void setContainsInlineEntry(final boolean containsInlineEntry) {
    this.containsInlineEntry = containsInlineEntry;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "ODataEntryImpl [data=" + data + ", "
        + "entryMetadata=" + entryMetadata + ", "
        + "mediaMetadata=" + mediaMetadata + ", "
        + "expandSelectTree=" + expandSelectTree + ", "
        + "containsInlineEntry=" + containsInlineEntry + "]";
  }
}
