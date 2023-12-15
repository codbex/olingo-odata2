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
package org.apache.olingo.odata2.core.edm.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmCustomizableFeedMappings;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmEntityTypeImplProv.
 */
public class EdmEntityTypeImplProv extends EdmStructuralTypeImplProv implements EdmEntityType {

  /** The entity type. */
  private EntityType entityType;

  /** The key properties. */
  private Map<String, EdmProperty> keyProperties;
  
  /** The edm key properties. */
  private List<EdmProperty> edmKeyProperties;
  
  /** The edm key property names. */
  private List<String> edmKeyPropertyNames;

  /** The navigation properties. */
  private Map<String, NavigationProperty> navigationProperties;
  
  /** The edm navigation property names. */
  private List<String> edmNavigationPropertyNames;

  /**
   * Instantiates a new edm entity type impl prov.
   *
   * @param edm the edm
   * @param entityType the entity type
   * @param namespace the namespace
   * @throws EdmException the edm exception
   */
  public EdmEntityTypeImplProv(final EdmImplProv edm, final EntityType entityType, final String namespace)
      throws EdmException {
    super(edm, entityType, EdmTypeKind.ENTITY, namespace);
    this.entityType = entityType;

    buildNavigationPropertiesInternal();
  }

  /**
   * Builds the navigation properties internal.
   *
   * @throws EdmException the edm exception
   */
  private void buildNavigationPropertiesInternal() throws EdmException {
    navigationProperties = new HashMap<String, NavigationProperty>();

    if (entityType.getNavigationProperties() != null) {
      for (final NavigationProperty navigationProperty : entityType.getNavigationProperties()) {
        navigationProperties.put(navigationProperty.getName(), navigationProperty);
      }
    }
  }

  /**
   * Gets the key property names.
   *
   * @return the key property names
   * @throws EdmException the edm exception
   */
  @Override
  public List<String> getKeyPropertyNames() throws EdmException {
    if (edmKeyPropertyNames == null) {
      if (edmBaseType != null) {
        return ((EdmEntityType) edmBaseType).getKeyPropertyNames();
      }

      edmKeyPropertyNames = new ArrayList<String>();

      if (entityType.getKey() != null) {
        for (final PropertyRef keyProperty : entityType.getKey().getKeys()) {
          edmKeyPropertyNames.add(keyProperty.getName());
        }
      } else {
        // Entity Type does not define a key
        throw new EdmException(EdmException.COMMON);
      }
    }

    return edmKeyPropertyNames;
  }

  /**
   * Gets the key properties.
   *
   * @return the key properties
   * @throws EdmException the edm exception
   */
  @Override
  public List<EdmProperty> getKeyProperties() throws EdmException {
    if (edmKeyProperties == null) {
      if (edmBaseType != null) {
        return ((EdmEntityType) edmBaseType).getKeyProperties();
      }

      if (keyProperties == null) {
        keyProperties = new HashMap<String, EdmProperty>();
        edmKeyProperties = new ArrayList<EdmProperty>();

        for (String keyPropertyName : getKeyPropertyNames()) {
          final EdmTyped edmProperty = getProperty(keyPropertyName);
          if (edmProperty != null && edmProperty instanceof EdmProperty) {
            keyProperties.put(keyPropertyName, (EdmProperty) edmProperty);
            edmKeyProperties.add((EdmProperty) edmProperty);
          } else {
            throw new EdmException(EdmException.COMMON);
          }
        }
      }
    }

    return edmKeyProperties;
  }

  /**
   * Checks for stream.
   *
   * @return true, if successful
   * @throws EdmException the edm exception
   */
  @Override
  public boolean hasStream() throws EdmException {
    return entityType.isHasStream();
  }

  /**
   * Gets the customizable feed mappings.
   *
   * @return the customizable feed mappings
   * @throws EdmException the edm exception
   */
  @Override
  public EdmCustomizableFeedMappings getCustomizableFeedMappings() throws EdmException {
    return entityType.getCustomizableFeedMappings();
  }

  /**
   * Gets the navigation property names.
   *
   * @return the navigation property names
   * @throws EdmException the edm exception
   */
  @Override
  public List<String> getNavigationPropertyNames() throws EdmException {
    if (edmNavigationPropertyNames == null) {
      edmNavigationPropertyNames = new ArrayList<String>();
      if (edmBaseType != null) {
        edmNavigationPropertyNames.addAll(((EdmEntityType) edmBaseType).getNavigationPropertyNames());
      }
      if (entityType.getNavigationProperties() != null) {
        for (final NavigationProperty navigationProperty : entityType.getNavigationProperties()) {
          edmNavigationPropertyNames.add(navigationProperty.getName());
        }
      }
    }
    return edmNavigationPropertyNames;
  }

  /**
   * Gets the base type.
   *
   * @return the base type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityType getBaseType() throws EdmException {
    return (EdmEntityType) edmBaseType;
  }

  /**
   * Gets the property internal.
   *
   * @param name the name
   * @return the property internal
   * @throws EdmException the edm exception
   */
  @Override
  protected EdmTyped getPropertyInternal(final String name) throws EdmException {
    EdmTyped edmProperty = super.getPropertyInternal(name);

    if (edmProperty != null) {
      return edmProperty;
    }

    if (navigationProperties.containsKey(name)) {
      edmProperty = createNavigationProperty(navigationProperties.get(name));
      edmProperties.put(name, edmProperty);
    } else if (edmBaseType != null) {
      edmProperty = edmBaseType.getProperty(name);
      if (edmProperty != null) {
        edmProperties.put(name, edmProperty);
      }
    }

    return edmProperty;
  }

  /**
   * Creates the navigation property.
   *
   * @param property the property
   * @return the edm typed
   * @throws EdmException the edm exception
   */
  protected EdmTyped createNavigationProperty(final NavigationProperty property) throws EdmException {
    return new EdmNavigationPropertyImplProv(edm, property);
  }
}
