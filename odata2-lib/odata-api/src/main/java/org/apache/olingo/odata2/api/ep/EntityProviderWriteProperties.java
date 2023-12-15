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
package org.apache.olingo.odata2.api.ep;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;

// TODO: Auto-generated Javadoc
/**
 * {@link EntityProviderWriteProperties} contains all additional properties which are necessary to <b>write
 * (serialize)</b> an {@link org.apache.olingo.odata2.api.ep.entry.ODataEntry} into an specific format (e.g.
 * <code>XML</code> or <code>JSON</code> or ...).
 */
public class EntityProviderWriteProperties {

  /** The service root. */
  private URI serviceRoot;
  
  /** The inline count type. */
  private InlineCount inlineCountType;
  
  /** The inline count. */
  private Integer inlineCount;
  
  /** The next link. */
  private String nextLink;
  
  /** The expand select tree. */
  private ExpandSelectTreeNode expandSelectTree;
  
  /** The callbacks. */
  private Map<String, ODataCallback> callbacks = Collections.emptyMap();
  
  /** The self link. */
  private URI selfLink;
  
  /** The include simple property type. */
  private boolean includeSimplePropertyType;
  
  /** The additional links. */
  private Map<String, Map<String, Object>> additionalLinks;
  
  /** The omit json wrapper. */
  private boolean omitJsonWrapper;
  
  /** The content only. */
  private boolean contentOnly;
  
  /** The omit E tag. */
  private boolean omitETag;
  
  /** The validating facets. */
  private boolean validatingFacets = true;

  /** The is response payload. */
  private boolean isResponsePayload = true;
  
  /** The include metadata in content only. */
  private boolean includeMetadataInContentOnly = false;
  
  /** The is data based property serialization. */
  private boolean isDataBasedPropertySerialization = false;
  
  /** The omit inline for null data. */
  private boolean omitInlineForNullData = false;

  /**
   * Instantiates a new entity provider write properties.
   */
  private EntityProviderWriteProperties() {}
  
  /**
   * Checks if is omit inline for null data.
   *
   * @return true, if is omit inline for null data
   */
  public final boolean isOmitInlineForNullData() {
    return omitInlineForNullData;
  }

  /**
   * Returns true if the payload has dynamic properties i.e. every entry has different property list
   *
   * @return true, if is data based property serialization
   */
  public final boolean isDataBasedPropertySerialization() {
    return isDataBasedPropertySerialization;
  }
  
  /**
   * Checks if is omit E tag.
   *
   * @return true, if is omit E tag
   */
  public final boolean isOmitETag() {
    return omitETag;
  }

  /**
   * Checks if is content only.
   *
   * @return true, if is content only
   */
  public final boolean isContentOnly() {
    return contentOnly;
  }

  /**
   * Checks if is omit json wrapper.
   *
   * @return true, if is omit json wrapper
   */
  public final boolean isOmitJsonWrapper() {
    return omitJsonWrapper;
  }

  /**
   * Returns if type information of simple properties should be in the payload.
   * @return true if information should be in the payload.
   */
  public final boolean isIncludeSimplePropertyType() {
    return includeSimplePropertyType;
  }

  /**
   * Gets the self link from an application. May be null.
   * @return the self link
   */
  public final URI getSelfLink() {
    return selfLink;
  }

  /**
   * Gets the service root.
   * @return the service root
   */
  public final URI getServiceRoot() {
    return serviceRoot;
  }

  /**
   * Gets the type of the inlinecount request from the system query option.
   * @return the type of the inlinecount request from the system query option
   */
  public final InlineCount getInlineCountType() {
    return inlineCountType;
  }

  /**
   * Gets the callbacks.
   *
   * @return the callbacks
   */
  public final Map<String, ODataCallback> getCallbacks() {
    return callbacks;
  }

  /**
   * Gets the expand select tree data structure resulting from $expand and $select query options.
   * @return a parsed tree structure representing the $expand and $select
   */
  public final ExpandSelectTreeNode getExpandSelectTree() {
    return expandSelectTree;
  }

  /**
   * Gets the inlinecount.
   * @return the inlinecount as Integer
   * @see #getInlineCountType
   */
  public final Integer getInlineCount() {
    return inlineCount;
  }

  /**
   * Gets the next link used for server-side paging of feeds.
   * @return the next link
   */
  public final String getNextLink() {
    return nextLink;
  }

  /**
   * Gets the additional links that should be in the payload.
   * @return the additional links as Map where the navigation-property name is the key and
   * a key predicate is the value -
   * a key predicate is a Map from key-property names to their values
   */
  public final Map<String, Map<String, Object>> getAdditionalLinks() {
    return additionalLinks;
  }

  /**
   * Service root.
   *
   * @param serviceRoot the service root
   * @return the o data entity provider properties builder
   */
  public static ODataEntityProviderPropertiesBuilder serviceRoot(final URI serviceRoot) {
    return new ODataEntityProviderPropertiesBuilder().serviceRoot(serviceRoot);
  }

  /**
   * Checks if is validating facets.
   *
   * @return true, if is validating facets
   */
  public boolean isValidatingFacets() {
    return validatingFacets;
  }

  /**
   * Checks if is response payload.
   *
   * @return true, if is response payload
   */
  public boolean isResponsePayload() {
    return isResponsePayload;
  }

  /**
   * Checks if is include metadata in content only.
   *
   * @return true, if is include metadata in content only
   */
  public boolean isIncludeMetadataInContentOnly() {
    return includeMetadataInContentOnly;
  }

  /**
   * The Class ODataEntityProviderPropertiesBuilder.
   */
  public static class ODataEntityProviderPropertiesBuilder {
    
    /** The properties. */
    private final EntityProviderWriteProperties properties = new EntityProviderWriteProperties();

    /**
     * Omit inline for null data.
     *
     * @param omitInlineForNullData the omit inline for null data
     * @return the o data entity provider properties builder
     */
    public final ODataEntityProviderPropertiesBuilder omitInlineForNullData(boolean omitInlineForNullData) {
      properties.omitInlineForNullData = omitInlineForNullData;
      return this;
    }
    
    /**
     * Checks if is data based property serialization.
     *
     * @param isDataBasedPropertySerialization the is data based property serialization
     * @return the o data entity provider properties builder
     */
    public final ODataEntityProviderPropertiesBuilder isDataBasedPropertySerialization
    (boolean isDataBasedPropertySerialization) {
      properties.isDataBasedPropertySerialization = isDataBasedPropertySerialization;
      return this;
    }
    
    /**
     * Include simple property type.
     *
     * @param includeSimplePropertyType true to include simple property type information in the payload
     * @return the o data entity provider properties builder
     */
    public final ODataEntityProviderPropertiesBuilder includeSimplePropertyType(
        final boolean includeSimplePropertyType) {
      properties.includeSimplePropertyType = includeSimplePropertyType;
      return this;
    }

    /**
     * Inline count type.
     *
     * @param inlineCountType the inlineCountType to set
     * @return the o data entity provider properties builder
     */
    public final ODataEntityProviderPropertiesBuilder inlineCountType(final InlineCount inlineCountType) {
      properties.inlineCountType = inlineCountType;
      return this;
    }

    /**
     * Inline count.
     *
     * @param inlineCount the inlineCount to set
     * @return the o data entity provider properties builder
     */
    public final ODataEntityProviderPropertiesBuilder inlineCount(final Integer inlineCount) {
      properties.inlineCount = inlineCount;
      return this;
    }

    /**
     * Service root.
     *
     * @param serviceRoot the service root
     * @return the o data entity provider properties builder
     */
    public final ODataEntityProviderPropertiesBuilder serviceRoot(final URI serviceRoot) {
      properties.serviceRoot = serviceRoot;
      return this;
    }

    /**
     * Next link.
     *
     * @param nextLink Next link to render feeds with server side paging. Should usually contain a skiptoken.
     * @return the o data entity provider properties builder
     */
    public ODataEntityProviderPropertiesBuilder nextLink(final String nextLink) {
      properties.nextLink = nextLink;
      return this;
    }

    /**
     * Build properties object.
     * @return assembled properties object
     */
    public final EntityProviderWriteProperties build() {
      return properties;
    }

    /**
     * Set a expand select tree which results from $expand and $select query parameter. Usually the data structure is
     * constructed by the URI parser.
     * @param expandSelectTree data structure
     * @return properties builder
     */
    public ODataEntityProviderPropertiesBuilder expandSelectTree(final ExpandSelectTreeNode expandSelectTree) {
      properties.expandSelectTree = expandSelectTree;
      return this;
    }

    /**
     * Callbacks.
     *
     * @param callbacks the callbacks
     * @return the o data entity provider properties builder
     */
    public ODataEntityProviderPropertiesBuilder callbacks(final Map<String, ODataCallback> callbacks) {
      properties.callbacks = callbacks;
      return this;
    }

    /**
     * Self link.
     *
     * @param selfLink the self link
     * @return the o data entity provider properties builder
     */
    public ODataEntityProviderPropertiesBuilder selfLink(final URI selfLink) {
      properties.selfLink = selfLink;
      return this;
    }

    /**
     * Sets additional links from this entity to other entities.
     * @param links a Map where the navigation-property name is the key and
     * a key predicate is the value -
     * a key predicate is a Map from key-property names to their values
     * @return properties builder
     */
    public ODataEntityProviderPropertiesBuilder additionalLinks(final Map<String, Map<String, Object>> links) {
      properties.additionalLinks = links;
      return this;
    }

    /**
     * Omit json wrapper.
     *
     * @param omitJsonWrapper the omit json wrapper
     * @return the o data entity provider properties builder
     */
    public ODataEntityProviderPropertiesBuilder omitJsonWrapper(final boolean omitJsonWrapper) {
      properties.omitJsonWrapper = omitJsonWrapper;
      return this;
    }

    /**
     * Content only.
     *
     * @param contentOnly the content only
     * @return the o data entity provider properties builder
     */
    public ODataEntityProviderPropertiesBuilder contentOnly(final boolean contentOnly) {
      properties.contentOnly = contentOnly;
      return this;
    }

    /**
     * Omit E tag.
     *
     * @param omitETag the omit E tag
     * @return the o data entity provider properties builder
     */
    public ODataEntityProviderPropertiesBuilder omitETag(final boolean omitETag) {
      properties.omitETag = omitETag;
      return this;
    }

    /**
     * Validating facets.
     *
     * @param validatingFacets the validating facets
     * @return the o data entity provider properties builder
     */
    public ODataEntityProviderPropertiesBuilder validatingFacets(final boolean validatingFacets) {
      properties.validatingFacets = validatingFacets;
      return this;
    }

    /**
     * If set to true an entity set (or collection) is rendered as response payload in OData V2 format.
     * Otherwise an entity set (or collection) is rendered as request payload in OData V2 format.
     *
     * See 2.2.6.3.2 Entity Set (as a JSON Array)
     * The grammar rule "entitySetInJson2" defines the version 2.0 JSON
     * representation of a collection of entities for response payloads only.
     *
     * @param responsePayload true for response payload handling, false for request payload handling
     * @return the builder
     */
    public ODataEntityProviderPropertiesBuilder responsePayload(final boolean responsePayload) {
      properties.isResponsePayload = responsePayload;
      return this;
    }

    /**
     * Include metadata in content only.
     *
     * @param includeMetadataInContentOnly the include metadata in content only
     * @return the o data entity provider properties builder
     */
    public ODataEntityProviderPropertiesBuilder
        includeMetadataInContentOnly(final boolean includeMetadataInContentOnly) {
      properties.includeMetadataInContentOnly = includeMetadataInContentOnly;
      return this;
    }

    /**
     * From properties.
     *
     * @param properties the properties
     * @return the o data entity provider properties builder
     */
    public ODataEntityProviderPropertiesBuilder fromProperties(final EntityProviderWriteProperties properties) {
      this.properties.inlineCountType = properties.getInlineCountType();
      this.properties.inlineCount = properties.getInlineCount();
      this.properties.nextLink = properties.getNextLink();
      this.properties.expandSelectTree = properties.getExpandSelectTree();
      this.properties.callbacks = properties.getCallbacks();
      this.properties.selfLink = properties.getSelfLink();
      this.properties.includeSimplePropertyType = properties.includeSimplePropertyType;
      this.properties.additionalLinks = properties.additionalLinks;
      this.properties.omitJsonWrapper = properties.omitJsonWrapper;
      this.properties.contentOnly = properties.contentOnly;
      this.properties.omitETag = properties.omitETag;
      this.properties.validatingFacets = properties.validatingFacets;
      this.properties.isResponsePayload = properties.isResponsePayload;
      this.properties.includeMetadataInContentOnly = properties.includeMetadataInContentOnly;
      this.properties.isDataBasedPropertySerialization = properties.isDataBasedPropertySerialization;
      this.properties.omitInlineForNullData = properties.omitInlineForNullData;
      return this;
    }

  }

  /**
   * From properties.
   *
   * @param properties the properties
   * @return the o data entity provider properties builder
   */
  public static ODataEntityProviderPropertiesBuilder fromProperties(final EntityProviderWriteProperties properties) {
    final ODataEntityProviderPropertiesBuilder builder =
        EntityProviderWriteProperties.serviceRoot(properties.getServiceRoot());
    return builder.fromProperties(properties);
  }
}
