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

import java.util.Date;

import org.apache.olingo.odata2.api.ep.entry.DeletedEntryMetadata;

// TODO: Auto-generated Javadoc
/**
 * The Class DeletedEntryMetadataImpl.
 */
public class DeletedEntryMetadataImpl implements DeletedEntryMetadata {

  /** The uri. */
  private String uri;
  
  /** The when. */
  private Date when;

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
   * Gets the when.
   *
   * @return the when
   */
  @Override
  public Date getWhen() {
    return when;
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
   * Sets the when.
   *
   * @param when the new when
   */
  public void setWhen(final Date when) {
    this.when = when;
  }

}
