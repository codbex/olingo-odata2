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
package org.apache.olingo.odata2.core.ep.feed;

import org.apache.olingo.odata2.api.ep.entry.DeletedEntryMetadata;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;

// TODO: Auto-generated Javadoc
/**
 * Simple wrapper for {@link ODataEntry} or {@link DeletedEntryMetadata} object.
 * Currently used in Json consumer (Entry and Feed).
 */
public class JsonFeedEntry {
  
  /** The o data entry. */
  private final ODataEntry oDataEntry;
  
  /** The deleted entry metadata. */
  private final DeletedEntryMetadata deletedEntryMetadata;

  /**
   * Instantiates a new json feed entry.
   *
   * @param entry the entry
   */
  public JsonFeedEntry(final ODataEntry entry) {
    oDataEntry = entry;
    deletedEntryMetadata = null;
  }

  /**
   * Instantiates a new json feed entry.
   *
   * @param entry the entry
   */
  public JsonFeedEntry(final DeletedEntryMetadata entry) {
    deletedEntryMetadata = entry;
    oDataEntry = null;
  }

  /**
   * Checks if is o data entry.
   *
   * @return true, if is o data entry
   */
  public boolean isODataEntry() {
    return oDataEntry != null;
  }

  /**
   * Gets the o data entry.
   *
   * @return the o data entry
   */
  public ODataEntry getODataEntry() {
    return oDataEntry;
  }

  /**
   * Checks if is deleted entry.
   *
   * @return true, if is deleted entry
   */
  public boolean isDeletedEntry() {
    return deletedEntryMetadata != null;
  }

  /**
   * Gets the deleted entry metadata.
   *
   * @return the deleted entry metadata
   */
  public DeletedEntryMetadata getDeletedEntryMetadata() {
    return deletedEntryMetadata;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "JsonFeedEntry [oDataEntry=" + oDataEntry + ", deletedEntryMetadata=" + deletedEntryMetadata + "]";
  }
}
