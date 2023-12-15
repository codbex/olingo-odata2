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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.ep.entry.EntryMetadata;

// TODO: Auto-generated Javadoc
/**
 * The Class EntryMetadataImpl.
 */
public class EntryMetadataImpl implements EntryMetadata {
  
  /** The id. */
  private String id;
  
  /** The etag. */
  private String etag;
  
  /** The uri. */
  private String uri;
  
  /** The association uris. */
  private Map<String, List<String>> associationUris = new HashMap<String, List<String>>();

  /**
   * Gets the id.
   *
   * @return the id
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Sets the id.
   *
   * @param id the new id
   */
  public void setId(final String id) {
    this.id = id;
  }

  /**
   * Gets the etag.
   *
   * @return the etag
   */
  @Override
  public String getEtag() {
    return etag;
  }

  /**
   * Sets the etag.
   *
   * @param etag the new etag
   */
  public void setEtag(final String etag) {
    this.etag = etag;
  }

  /**
   * Gets the uri.
   *
   * @return the uri
   */
  @Override
  public String getUri() {
    return uri;
  }

  /**
   * Sets the uri.
   *
   * @param uri the new uri
   */
  public void setUri(final String uri) {
    this.uri = uri;
  }

  /**
   * Gets the association uris.
   *
   * @param navigationPropertyName the navigation property name
   * @return the association uris
   */
  @Override
  public List<String> getAssociationUris(final String navigationPropertyName) {
    final List<String> uris = associationUris.get(navigationPropertyName);
    if (uris == null) {
      return Collections.emptyList();
    } else {
      return Collections.unmodifiableList(uris);
    }
  }

  /**
   * Put association uri.
   *
   * @param navigationPropertyName the navigation property name
   * @param uri the uri
   */
  public void putAssociationUri(final String navigationPropertyName, final String uri) {
    List<String> uris = associationUris.get(navigationPropertyName);
    if (uris == null) {
      uris = new ArrayList<String>();
    }
    uris.add(uri);
    associationUris.put(navigationPropertyName, uris);
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "EntryMetadataImpl [id=" + id + ", etag=" + etag + ", uri=" + uri + ", associationUris=" + associationUris
        + "]";
  }
}
