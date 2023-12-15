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
package org.apache.olingo.odata2.api.ep.callback;

import java.net.URI;

// TODO: Auto-generated Javadoc
/**
 * Context given if the target of an expand is a feed. It contains the source entity set, the navigation property
 * pointing to the entry which has to be expanded, the current expand select tree node and the data of the source entry.
 * 
 * 
 */
public class WriteFeedCallbackContext extends WriteCallbackContext {

  /** The self link. */
  private URI selfLink;

  /**
   * Sets the self Link for this feed.
   *
   * @param selfLink the new self link
   */
  public void setSelfLink(final URI selfLink) {
    this.selfLink = selfLink;
  }

  /**
   * This self link is the same as the link displayed for the navigation property e.g. Rooms(1)/nr_Buildings.
   * @return the self link calculated by the library
   */
  public URI getSelfLink() {
    return selfLink;
  }

}
