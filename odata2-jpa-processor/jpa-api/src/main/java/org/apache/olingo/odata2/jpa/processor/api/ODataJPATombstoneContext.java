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
package org.apache.olingo.odata2.jpa.processor.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * This class provides a thread safe container for accessing Tombstone objects.
 */
public final class ODataJPATombstoneContext {

  /** The Constant deltaToken. */
  private static final ThreadLocal<String> deltaToken = new ThreadLocal<String>();
  
  /** The Constant deltaTokenUTCTimeStamp. */
  private static final ThreadLocal<Long> deltaTokenUTCTimeStamp = new ThreadLocal<Long>();
  
  /** The Constant deltas. */
  private static final ThreadLocal<HashMap<String, List<Object>>> deltas =
      new ThreadLocal<HashMap<String, List<Object>>>();

  /**
   * Gets the delta token.
   *
   * @return the delta token
   */
  public static String getDeltaToken() {
    return deltaToken.get();
  }

  /**
   * Sets the delta token.
   *
   * @param token the new delta token
   */
  public static void setDeltaToken(final String token) {
    deltaToken.set(token);
  }

  /**
   * Adds the to delta result.
   *
   * @param entity the entity
   * @param entityName the entity name
   */
  public static void addToDeltaResult(final Object entity, final String entityName) {
    HashMap<String, List<Object>> entityDeltaMap = deltas.get();
    if (entityDeltaMap == null) {
      deltas.set(new HashMap<String, List<Object>>());
    }
    entityDeltaMap = deltas.get();
    if (entityDeltaMap.get(entityName) == null) {
      entityDeltaMap.put(entityName, new ArrayList<Object>());
    }
    entityDeltaMap.get(entityName).add(entity);
  }

  /**
   * Gets the delta result.
   *
   * @param entityName the entity name
   * @return the delta result
   */
  public static List<Object> getDeltaResult(final String entityName) {
    HashMap<String, List<Object>> entityDeltaMap = deltas.get();
    if (entityDeltaMap != null) {
      return entityDeltaMap.get(entityName);
    } else {
      return null;
    }
  }

  /**
   * Cleanup.
   */
  public static void cleanup() {
    deltas.remove();
    deltaToken.remove();
    deltaTokenUTCTimeStamp.remove();
  }

  /**
   * Gets the delta token UTC time stamp.
   *
   * @return the delta token UTC time stamp
   */
  public static Long getDeltaTokenUTCTimeStamp() {
    Long timestamp = deltaTokenUTCTimeStamp.get();

    if (timestamp != null) {
      return timestamp;
    } else if (deltaToken.get() != null) {
      try {
        timestamp = new Long(Long.parseLong(deltaToken.get()));
      } catch (NumberFormatException e) {
        return null;
      }
      deltaTokenUTCTimeStamp.set(timestamp);
    } else {
      deltaTokenUTCTimeStamp.set(new Long(0L));
    }

    return deltaTokenUTCTimeStamp.get();
  }

}
