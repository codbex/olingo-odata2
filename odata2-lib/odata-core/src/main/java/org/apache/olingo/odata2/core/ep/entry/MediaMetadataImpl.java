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

import org.apache.olingo.odata2.api.ep.entry.MediaMetadata;

// TODO: Auto-generated Javadoc
/**
 * The Class MediaMetadataImpl.
 */
public class MediaMetadataImpl implements MediaMetadata {

  /** The source link. */
  private String sourceLink;
  
  /** The etag. */
  private String etag;
  
  /** The content type. */
  private String contentType;
  
  /** The edit link. */
  private String editLink;

  /**
   * Gets the source link.
   *
   * @return the source link
   */
  @Override
  public String getSourceLink() {
    return sourceLink;
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
   * Gets the content type.
   *
   * @return the content type
   */
  @Override
  public String getContentType() {
    return contentType;
  }

  /**
   * Gets the edits the link.
   *
   * @return the edits the link
   */
  @Override
  public String getEditLink() {
    return editLink;
  }

  /**
   * Sets the source link.
   *
   * @param sourceLink the new source link
   */
  public void setSourceLink(final String sourceLink) {
    this.sourceLink = sourceLink;
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
   * Sets the content type.
   *
   * @param contentType the new content type
   */
  public void setContentType(final String contentType) {
    this.contentType = contentType;
  }

  /**
   * Sets the edits the link.
   *
   * @param editLink the new edits the link
   */
  public void setEditLink(final String editLink) {
    this.editLink = editLink;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "MediaMetadataImpl [sourceLink=" + sourceLink + ", etag=" + etag + ", contentType=" + contentType
        + ", editLink=" + editLink + "]";
  }
}
