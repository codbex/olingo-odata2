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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.olingo.odata2.api.ep.callback.OnReadInlineContent;

// TODO: Auto-generated Javadoc
/**
 * <p>The {@link EntityProviderReadProperties} contains all necessary settings
 * to read an entity with the {@link EntityProvider}.</p>
 * <p>The main settings are
 * <ul>
 * <li>the <code>mergeSemantic</code></li>
 * <li>the <code>callback for inlined navigation properties</code></li>
 * <li>the <code>type mappings</code></li>
 * <li>and <code>validatingFacets</code></li>
 * </ul>
 * </p>
 */
public class EntityProviderReadProperties {
  /** Callback which is necessary if entity contains inlined navigation properties. */
  private OnReadInlineContent callback;
  /**
   * if merge is <code>true</code> the input content is in context of a <b>merge</b> (e.g. MERGE, PATCH) read request,
   * otherwise if <code>false</code> it is a <b>non-merge</b> (e.g. CREATE) read request
   */
  private boolean merge;
  /**
   * typeMappings contains mappings from <code>edm property name</code> to <code>java class</code> which should be used
   * for a type mapping during reading of content. If according <code>edm property</code> can not be read
   * into given <code>java class</code> an {@link EntityProviderException} is thrown.
   * Supported mappings are documented in {@link org.apache.olingo.odata2.api.edm.EdmSimpleType}.
   */
  final private Map<String, Object> typeMappings;
  
  /**  whether the constraints expressed in properties' facets are validated. */
  private boolean validatingFacets = true;

  /** The validated prefix 2 namespace uri. */
  final private Map<String, String> validatedPrefix2NamespaceUri;

  /**
   * Instantiates a new entity provider read properties.
   */
  private EntityProviderReadProperties() {
    typeMappings = new HashMap<String, Object>();
    validatedPrefix2NamespaceUri = new HashMap<String, String>();
  }

  /**
   * Inits the.
   *
   * @return the entity provider read properties builder
   */
  public static EntityProviderReadPropertiesBuilder init() {
    return new EntityProviderReadPropertiesBuilder();
  }

  /**
   * Inits the from.
   *
   * @param properties the properties
   * @return the entity provider read properties builder
   */
  public static EntityProviderReadPropertiesBuilder initFrom(final EntityProviderReadProperties properties) {
    return new EntityProviderReadPropertiesBuilder(properties);
  }

  /**
   * Gets the validated prefix namespace uris.
   *
   * @return the validated prefix namespace uris
   */
  public Map<String, String> getValidatedPrefixNamespaceUris() {
    return Collections.unmodifiableMap(validatedPrefix2NamespaceUri);
  }

  /**
   * Gets the type mappings.
   *
   * @return the type mappings
   */
  public Map<String, Object> getTypeMappings() {
    return Collections.unmodifiableMap(typeMappings);
  }

  /**
   * Gets the callback.
   *
   * @return the callback
   */
  public OnReadInlineContent getCallback() {
    return callback;
  }

  /**
   * <p>Gets the merge semantics.</p>
   * <p>Merge semantics is set if the input content has to be treated in the context
   * of a request to merge incoming data with existing data (e.g., indicated by the
   * HTTP verbs <code>MERGE</code> or <code>PATCH</code> in a server application).
   * Otherwise the request, even if not all data are supplied, is supposed to
   * overwrite the existing entity completely.</p>
   *
   * @return the merge semantic
   */
  public boolean getMergeSemantic() {
    return merge;
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
   * Builder for {@link EntityProviderReadProperties}.
   */
  public static class EntityProviderReadPropertiesBuilder {
    
    /** The properties. */
    private final EntityProviderReadProperties properties = new EntityProviderReadProperties();

    /**
     * Instantiates a new entity provider read properties builder.
     */
    public EntityProviderReadPropertiesBuilder() {}

    /**
     * Instantiates a new entity provider read properties builder.
     *
     * @param propertiesFrom the properties from
     */
    public EntityProviderReadPropertiesBuilder(final EntityProviderReadProperties propertiesFrom) {
      properties.merge = propertiesFrom.merge;
      properties.callback = propertiesFrom.callback;
      addValidatedPrefixes(propertiesFrom.validatedPrefix2NamespaceUri);
      addTypeMappings(propertiesFrom.typeMappings);
      properties.validatingFacets = propertiesFrom.validatingFacets;
    }

    /**
     * Sets the merge semantics.
     *
     * @param mergeSemantic whether merge semantics is requested
     * @return the entity provider read properties builder
     * @see EntityProviderReadProperties#getMergeSemantic()
     */
    public EntityProviderReadPropertiesBuilder mergeSemantic(final boolean mergeSemantic) {
      properties.merge = mergeSemantic;
      return this;
    }

    /**
     * Callback.
     *
     * @param callback the callback
     * @return the entity provider read properties builder
     */
    public EntityProviderReadPropertiesBuilder callback(final OnReadInlineContent callback) {
      properties.callback = callback;
      return this;
    }

    /**
     * Adds the validated prefixes.
     *
     * @param prefix2NamespaceUri the prefix 2 namespace uri
     * @return the entity provider read properties builder
     */
    public EntityProviderReadPropertiesBuilder addValidatedPrefixes(final Map<String, String> prefix2NamespaceUri) {
      if (prefix2NamespaceUri != null) {
        properties.validatedPrefix2NamespaceUri.putAll(prefix2NamespaceUri);
      }
      return this;
    }

    /**
     * Adds the type mappings.
     *
     * @param typeMappings the type mappings
     * @return the entity provider read properties builder
     */
    public EntityProviderReadPropertiesBuilder addTypeMappings(final Map<String, Object> typeMappings) {
      if (typeMappings != null) {
        properties.typeMappings.putAll(typeMappings);
      }
      return this;
    }

    /**
     * Checks if is validating facets.
     *
     * @param validatingFacets the validating facets
     * @return the entity provider read properties builder
     */
    public EntityProviderReadPropertiesBuilder isValidatingFacets(final boolean validatingFacets) {
      properties.validatingFacets = validatingFacets;
      return this;
    }

    /**
     * Builds the.
     *
     * @return the entity provider read properties
     */
    public EntityProviderReadProperties build() {
      return properties;
    }
  }
}
