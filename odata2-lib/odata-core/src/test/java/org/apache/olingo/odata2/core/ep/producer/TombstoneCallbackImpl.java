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
package org.apache.olingo.odata2.core.ep.producer;

import java.util.ArrayList;
import java.util.Map;

import org.apache.olingo.odata2.api.ep.callback.TombstoneCallback;
import org.apache.olingo.odata2.api.ep.callback.TombstoneCallbackResult;

// TODO: Auto-generated Javadoc
/**
 * The Class TombstoneCallbackImpl.
 */
public class TombstoneCallbackImpl implements TombstoneCallback {

  /** The deleted entries data. */
  private ArrayList<Map<String, Object>> deletedEntriesData;
  
  /** The delta link. */
  private String deltaLink = null;

  /**
   * Instantiates a new tombstone callback impl.
   *
   * @param deletedEntriesData the deleted entries data
   * @param deltaLink the delta link
   */
  public TombstoneCallbackImpl(final ArrayList<Map<String, Object>> deletedEntriesData, final String deltaLink) {
    this.deletedEntriesData = deletedEntriesData;
    this.deltaLink = deltaLink;
  }

  /**
   * Gets the tombstone callback result.
   *
   * @return the tombstone callback result
   */
  @Override
  public TombstoneCallbackResult getTombstoneCallbackResult() {
    TombstoneCallbackResult result = new TombstoneCallbackResult();
    result.setDeletedEntriesData(deletedEntriesData);
    result.setDeltaLink(deltaLink);
    return result;
  }

}
