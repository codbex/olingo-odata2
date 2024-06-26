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

import org.apache.olingo.odata2.api.ODataCallback;

// TODO: Auto-generated Javadoc
/**
 * <p>Interface that must be implemented in order to provide tombstone support.</p>
 * <p>The callback implementing this interface is registered at the
 * {@link org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties EntityProviderWriteProperties} using the
 * callback key of this class.</p>
 * 
 */
public interface TombstoneCallback extends ODataCallback {

  /**
   * The key to be used when registering the callback at the
   * {@link org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties EntityProviderWriteProperties}
   */
  public static final String CALLBACK_KEY_TOMBSTONE = "~tombstoneCallback";
  
  /** The Constant PREFIX_TOMBSTONE. */
  public static final String PREFIX_TOMBSTONE = "at";
  
  /** The Constant NAMESPACE_TOMBSTONE. */
  public static final String NAMESPACE_TOMBSTONE = "http://purl.org/atompub/tombstones/1.0";

  /** The Constant REL_DELTA. */
  @Deprecated
  public static final String REL_DELTA = "delta";

  /**
   * <p>This method is called after all entries have been serialized.</p>
   * <p>The returned {@link TombstoneCallbackResult} must contain all deleted entries,
   * in the form of List{@literal <}Map{@literal <}property name, property value{@literal >}{@literal >},
   * which should be serialized.</p>
   * <p>A map representing a deleted entry
   * <ul><li><b>MUST</b> contain all properties which are part of the key for this entry.</li>
   * <li><b>MAY</b> contain the property which is mapped on SyndicationUpdated.
   * The provided value here will result in the value of the "when" attribute
   * of the deleted entry.</li></ul></p>
   * <p>The provided delta link will be serialized at the end of the feed document.</p>
   *
   * @return the tombstone callback result
   */
  TombstoneCallbackResult getTombstoneCallbackResult();
}
