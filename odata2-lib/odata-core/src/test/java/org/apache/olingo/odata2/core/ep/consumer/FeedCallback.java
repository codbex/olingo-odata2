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
package org.apache.olingo.odata2.core.ep.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.callback.OnReadInlineContent;
import org.apache.olingo.odata2.api.ep.callback.ReadEntryResult;
import org.apache.olingo.odata2.api.ep.callback.ReadFeedResult;
import org.apache.olingo.odata2.api.ep.callback.ReadResult;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;

// TODO: Auto-generated Javadoc
/**
 * The Class FeedCallback.
 */
public class FeedCallback implements OnReadInlineContent{

  /** The navigation prop. */
  private static Map<String, Object> navigationProp = new HashMap<String, Object>();
  
  /** The id. */
  private String id = "";
  
  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Gets the navigation properties.
   *
   * @return the navigationPropFeed
   */
  public Map<String, Object> getNavigationProperties() {
    return navigationProp;
  }

  /**
   * Receive read properties.
   *
   * @param readProperties the read properties
   * @param navigationProperty the navigation property
   * @return the entity provider read properties
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public EntityProviderReadProperties receiveReadProperties(EntityProviderReadProperties readProperties,
      EdmNavigationProperty navigationProperty) throws ODataApplicationException {
    Map<String, Object> typeMappings = new HashMap<String, Object>();
    return EntityProviderReadProperties.init().addTypeMappings(typeMappings).callback(new FeedCallback()).build();
  }

  /**
   * Handle read entry.
   *
   * @param context the context
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public void handleReadEntry(ReadEntryResult context) throws ODataApplicationException {
    this.id = context.getParentEntryId();
    handleEntry(context);
    
  }

  /**
   * Handle read feed.
   *
   * @param context the context
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public void handleReadFeed(ReadFeedResult context) throws ODataApplicationException {
    this.id = context.getParentEntryId();
    handleFeed(context);
    
  }
  
  /**
   * Handle entry.
   *
   * @param context the context
   */
  private void handleEntry(final ReadResult context) {
    try {
      String navigationPropertyName = context.getNavigationProperty().getName();
      if (navigationPropertyName != null) {
        navigationProp.put(navigationPropertyName + id, (ODataEntry) context.getResult());
      } else {
        throw new RuntimeException("Invalid title");
      }
    } catch (EdmException e) {
      throw new RuntimeException("Invalid title");
    }
  }

  /**
   * Handle feed.
   *
   * @param context the context
   */
  private void handleFeed(final ReadFeedResult context) {
    try {
      String navigationPropertyName = context.getNavigationProperty().getName();
      if (navigationPropertyName != null) {
        navigationProp.put(navigationPropertyName + id, (ODataFeed) context.getResult());
      } else {
        throw new RuntimeException("Invalid title");
      }
    } catch (EdmException e) {
      throw new RuntimeException("Invalid title");
    }
  }
}
