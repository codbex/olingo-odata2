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
package org.apache.olingo.odata2.core.batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.odata2.api.batch.BatchRequestPart;
import org.apache.olingo.odata2.api.processor.ODataRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class BatchRequestPartImpl.
 */
public class BatchRequestPartImpl implements BatchRequestPart {

  /** The requests. */
  private List<ODataRequest> requests = new ArrayList<ODataRequest>();
  
  /** The is change set. */
  private boolean isChangeSet;

  /**
   * Instantiates a new batch request part impl.
   */
  public BatchRequestPartImpl() {}

  /**
   * Instantiates a new batch request part impl.
   *
   * @param isChangeSet the is change set
   * @param requests the requests
   */
  public BatchRequestPartImpl(final boolean isChangeSet, final List<ODataRequest> requests) {
    this.isChangeSet = isChangeSet;
    this.requests = requests;
  }

  /**
   * Checks if is change set.
   *
   * @return true, if is change set
   */
  @Override
  public boolean isChangeSet() {
    return isChangeSet;
  }

  /**
   * Sets the change set.
   *
   * @param isChangeSet the new change set
   */
  public void setChangeSet(final boolean isChangeSet) {
    this.isChangeSet = isChangeSet;
  }

  /**
   * Gets the requests.
   *
   * @return the requests
   */
  @Override
  public List<ODataRequest> getRequests() {
    return Collections.unmodifiableList(requests);
  }

  /**
   * Sets the requests.
   *
   * @param requests the new requests
   */
  public void setRequests(final List<ODataRequest> requests) {
    this.requests = requests;
  }

}
