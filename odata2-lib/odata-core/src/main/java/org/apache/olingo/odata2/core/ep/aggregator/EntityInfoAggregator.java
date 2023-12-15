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
package org.apache.olingo.odata2.core.ep.aggregator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.EdmConcurrencyMode;
import org.apache.olingo.odata2.api.edm.EdmCustomizableFeedMappings;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmStructuralType;
import org.apache.olingo.odata2.api.edm.EdmTargetPath;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;

// TODO: Auto-generated Javadoc
/**
 * Aggregator to get easy and fast access to all for serialization and de-serialization necessary {@link EdmEntitySet}
 * informations.
 * 
 * 
 */
public class EntityInfoAggregator {

  /** The Constant SYN_TARGET_PATHS. */
  private static final Set<String> SYN_TARGET_PATHS = new HashSet<String>(Arrays.asList(
      EdmTargetPath.SYNDICATION_AUTHORNAME,
      EdmTargetPath.SYNDICATION_AUTHOREMAIL,
      EdmTargetPath.SYNDICATION_AUTHORURI,
      EdmTargetPath.SYNDICATION_PUBLISHED,
      EdmTargetPath.SYNDICATION_RIGHTS,
      EdmTargetPath.SYNDICATION_TITLE,
      EdmTargetPath.SYNDICATION_UPDATED,
      EdmTargetPath.SYNDICATION_CONTRIBUTORNAME,
      EdmTargetPath.SYNDICATION_CONTRIBUTOREMAIL,
      EdmTargetPath.SYNDICATION_CONTRIBUTORURI,
      EdmTargetPath.SYNDICATION_SOURCE,
      EdmTargetPath.SYNDICATION_SUMMARY));

  /** The property info. */
  private Map<String, EntityPropertyInfo> propertyInfo = new HashMap<String, EntityPropertyInfo>();
  
  /** The navigation property infos. */
  private Map<String, NavigationPropertyInfo> navigationPropertyInfos = new HashMap<String, NavigationPropertyInfo>();
  
  /** The key property infos. */
  private List<EntityPropertyInfo> keyPropertyInfos;

  /** The etag property names. */
  /*
   * list with all property names in the order based on order in {@link EdmProperty} (normally [key, entity,
   * navigation])
   */
  private List<String> etagPropertyNames = new ArrayList<String>();
  
  /** The property names. */
  private List<String> propertyNames;
  
  /** The navigation property names. */
  private List<String> navigationPropertyNames;
  
  /** The selected property names. */
  private List<String> selectedPropertyNames;
  
  /** The selected navigation property names. */
  private List<String> selectedNavigationPropertyNames;
  
  /** The expanded navigation property names. */
  private List<String> expandedNavigationPropertyNames;

  /** The target path 2 entity property info. */
  private Map<String, EntityPropertyInfo> targetPath2EntityPropertyInfo = new HashMap<String, EntityPropertyInfo>();
  
  /** The none syndication target paths. */
  private List<String> noneSyndicationTargetPaths = new ArrayList<String>();

  /** The is default entity container. */
  private boolean isDefaultEntityContainer;
  
  /** The entity set name. */
  private String entitySetName;
  
  /** The entity container name. */
  private String entityContainerName;

  /** The entity type. */
  private EdmEntityType entityType;
  
  /** The entity set. */
  private EdmEntitySet entitySet;

  /**
   * Constructor is private to force creation over {@link #create(EdmEntitySet)} method.
   */
  private EntityInfoAggregator() {}

  /**
   * Create an {@link EntityInfoAggregator} based on given {@link EdmEntitySet}.
   *
   * @param entitySet with which the {@link EntityInfoAggregator} is initialized.
   * @param expandSelectTree the expand select tree
   * @return created and initialized {@link EntityInfoAggregator}
   * @throws EntityProviderException if during initialization of {@link EntityInfoAggregator} something goes wrong (e.g. exceptions during
   * access
   * of {@link EdmEntitySet}).
   */
  public static EntityInfoAggregator create(final EdmEntitySet entitySet, final ExpandSelectTreeNode expandSelectTree)
      throws EntityProviderException {
    EntityInfoAggregator eia = new EntityInfoAggregator();
    eia.initialize(entitySet, expandSelectTree);
    return eia;
  }

  /**
   * Create an {@link EntityInfoAggregator} based on given {@link EdmEntitySet}.
   *
   * @param entitySet with which the {@link EntityInfoAggregator} is initialized.
   * @return created and initialized {@link EntityInfoAggregator}
   * @throws EntityProviderException if during initialization of {@link EntityInfoAggregator} something goes wrong (e.g. exceptions during
   * access
   * of {@link EdmEntitySet}).
   */
  public static EntityInfoAggregator create(final EdmEntitySet entitySet) throws EntityProviderException {
    EntityInfoAggregator eia = new EntityInfoAggregator();
    eia.initialize(entitySet, null);
    return eia;
  }

  /**
   * Create an {@link EntityPropertyInfo} based on given {@link EdmProperty}.
   *
   * @param property for which the {@link EntityPropertyInfo} is created.
   * @return created {@link EntityPropertyInfo}
   * @throws EntityProviderException if create of {@link EntityPropertyInfo} something goes wrong (e.g. exceptions during
   * access of {@link EdmProperty}).
   */
  public static EntityPropertyInfo create(final EdmProperty property) throws EntityProviderException {
    try {
      EntityInfoAggregator eia = new EntityInfoAggregator();
      return eia.createEntityPropertyInfo(property);
    } catch (EdmException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Create an map of <code>complex type property name</code> to {@link EntityPropertyInfo} based on given
   * {@link EdmComplexType}.
   *
   * @param complexType for which the {@link EntityPropertyInfo} is created.
   * @return created map of <code>complex type property name</code> to {@link EntityPropertyInfo}
   * @throws EntityProviderException if create of {@link EntityPropertyInfo} something goes wrong (e.g. exceptions during
   * access of {@link EntityPropertyInfo}).
   */
  public static Map<String, EntityPropertyInfo> create(final EdmComplexType complexType)
      throws EntityProviderException {
    try {
      EntityInfoAggregator entityInfo = new EntityInfoAggregator();
      return entityInfo.createPropertyInfoObjects(complexType, complexType.getPropertyNames());
    } catch (EdmException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Create an {@link EntityPropertyInfo} based on given {@link EdmFunctionImport}.
   *
   * @param functionImport for which the {@link EntityPropertyInfo} is created.
   * @return created {@link EntityPropertyInfo}
   * @throws EntityProviderException if create of {@link EntityPropertyInfo} something goes wrong (e.g. exceptions during
   * access of {@link EdmFunctionImport}).
   */
  public static EntityPropertyInfo create(final EdmFunctionImport functionImport) throws EntityProviderException {
    try {
      EntityInfoAggregator eia = new EntityInfoAggregator();
      return eia.createEntityPropertyInfo(functionImport, functionImport.getReturnType().getType());
    } catch (EdmException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Gets the entity set.
   *
   * @return the edm entity set which was used to build this entity info aggregator object
   */
  public EdmEntitySet getEntitySet() {
    return entitySet;
  }

  /**
   * Gets the entity set name.
   *
   * @return entity set name.
   */
  public String getEntitySetName() {
    return entitySetName;
  }

  /**
   * Checks if is default entity container.
   *
   * @return <code>true</code> if the entity container of {@link EdmEntitySet} is the default container,
   * otherwise <code>false</code>.
   */
  public boolean isDefaultEntityContainer() {
    return isDefaultEntityContainer;
  }

  /**
   * Gets the target path info.
   *
   * @param targetPath the target path
   * @return the target path info
   */
  public EntityPropertyInfo getTargetPathInfo(final String targetPath) {
    return targetPath2EntityPropertyInfo.get(targetPath);
  }

  /**
   * Gets the entity type.
   *
   * @return the entity type
   */
  public EdmEntityType getEntityType() {
    return entityType;
  }

  /**
   * Gets the entity container name.
   *
   * @return the entity container name
   */
  public String getEntityContainerName() {
    return entityContainerName;
  }

  /**
   * Gets the target path names.
   *
   * @return unmodifiable set of all found target path names.
   */
  public Collection<String> getTargetPathNames() {
    return Collections.unmodifiableCollection(targetPath2EntityPropertyInfo.keySet());
  }

  /**
   * Gets the none syndication target path names.
   *
   * @return unmodifiable set of found <code>none syndication target path names</code> (all target path names which are
   * not pre-defined).
   */
  public List<String> getNoneSyndicationTargetPathNames() {
    return Collections.unmodifiableList(noneSyndicationTargetPaths);
  }

  /**
   * Gets the navigation property names.
   *
   * @return unmodifiable set of all found navigation property names.
   * @throws EntityProviderException the entity provider exception
   */
  public List<String> getNavigationPropertyNames() throws EntityProviderException {
    return Collections.unmodifiableList(navigationPropertyNames);
  }

  /**
   * Gets the property names.
   *
   * @return unmodifiable set of all property names.
   * @throws EntityProviderException the entity provider exception
   */
  public List<String> getPropertyNames() throws EntityProviderException {
    return Collections.unmodifiableList(propertyNames);
  }

  /**
   * Gets the selected property names.
   *
   * @return unmodifiable set of selected property names.
   * @throws EntityProviderException the entity provider exception
   */
  public List<String> getSelectedPropertyNames() throws EntityProviderException {
    return Collections.unmodifiableList(selectedPropertyNames);
  }

  /**
   * Gets the selected navigation property names.
   *
   * @return unmodifiable set of selected property names.
   * @throws EntityProviderException the entity provider exception
   */
  public List<String> getSelectedNavigationPropertyNames() throws EntityProviderException {
    return Collections.unmodifiableList(selectedNavigationPropertyNames);
  }

  /**
   * Gets the property infos.
   *
   * @return the property infos
   */
  public Collection<EntityPropertyInfo> getPropertyInfos() {
    return Collections.unmodifiableCollection(propertyInfo.values());
  }

  /**
   * Gets the property info.
   *
   * @param name the name
   * @return the property info
   */
  public EntityPropertyInfo getPropertyInfo(final String name) {
    return propertyInfo.get(name);
  }

  /**
   * Gets the e tag property infos.
   *
   * @return the e tag property infos
   */
  public Collection<EntityPropertyInfo> getETagPropertyInfos() {
    List<EntityPropertyInfo> keyProperties = new ArrayList<EntityPropertyInfo>();
    for (String etagPropertyName : etagPropertyNames) {
      EntityPropertyInfo e = propertyInfo.get(etagPropertyName);
      keyProperties.add(e);
    }
    return keyProperties;
  }

  /**
   * Gets the key property infos.
   *
   * @return list of all key property infos
   * @throws EntityProviderException the entity provider exception
   */
  public List<EntityPropertyInfo> getKeyPropertyInfos() throws EntityProviderException {

    if (keyPropertyInfos == null) {
      try {
        keyPropertyInfos = new ArrayList<EntityPropertyInfo>();
        for (String keyPropertyName : entityType.getKeyPropertyNames()) {
          keyPropertyInfos.add(propertyInfo.get(keyPropertyName));
        }
      } catch (EdmException e) {
        throw new EntityProviderException(EntityProviderException.COMMON, e);
      }
    }
    return keyPropertyInfos;
  }

  /**
   * Gets the navigation property info.
   *
   * @param name the name
   * @return the navigation property info
   */
  public NavigationPropertyInfo getNavigationPropertyInfo(final String name) {
    return navigationPropertyInfos.get(name);
  }

  /**
   * Initialize.
   *
   * @param entitySet the entity set
   * @param expandSelectTree the expand select tree
   * @throws EntityProviderException the entity provider exception
   */
  private void initialize(final EdmEntitySet entitySet, final ExpandSelectTreeNode expandSelectTree)
      throws EntityProviderException {
    try {
      this.entitySet = entitySet;
      entityType = entitySet.getEntityType();
      entitySetName = entitySet.getName();
      isDefaultEntityContainer = entitySet.getEntityContainer().isDefaultEntityContainer();
      entityContainerName = entitySet.getEntityContainer().getName();

      propertyNames = entityType.getPropertyNames();
      navigationPropertyNames = entityType.getNavigationPropertyNames();

      propertyInfo = createPropertyInfoObjects(entityType, propertyNames);
      navigationPropertyInfos = createNavigationInfoObjects(entityType, navigationPropertyNames);

      selectedPropertyNames = propertyNames;
      selectedNavigationPropertyNames = navigationPropertyNames;
      expandedNavigationPropertyNames = new ArrayList<String>();

      if (expandSelectTree != null && !expandSelectTree.isAll()) {
        selectedPropertyNames = new ArrayList<String>();
        selectedNavigationPropertyNames = new ArrayList<String>();
        for (EdmProperty property : expandSelectTree.getProperties()) {
          selectedPropertyNames.add(property.getName());
        }
        for (String property : expandSelectTree.getLinks().keySet()) {
          selectedNavigationPropertyNames.add(property);
          if (expandSelectTree.getLinks().get(property) != null) {
            expandedNavigationPropertyNames.add(property);
          }
        }
      } else if (expandSelectTree != null) {
        for (String property : expandSelectTree.getLinks().keySet()) {
          if (expandSelectTree.getLinks().get(property) != null) {
            expandedNavigationPropertyNames.add(property);
          }
        }
      }

    } catch (EdmException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Creates the property info objects.
   *
   * @param type the type
   * @param propertyNames the property names
   * @return the map
   * @throws EntityProviderException the entity provider exception
   */
  private Map<String, EntityPropertyInfo> createPropertyInfoObjects(final EdmStructuralType type,
      final List<String> propertyNames) throws EntityProviderException {
    try {
      Map<String, EntityPropertyInfo> infos = new HashMap<String, EntityPropertyInfo>();

      for (String propertyName : propertyNames) {
        EdmProperty property = (EdmProperty) type.getProperty(propertyName);

        checkETagRelevant(property);

        EntityPropertyInfo info = createEntityPropertyInfo(property);
        infos.put(info.getName(), info);
        checkTargetPathInfo(property, info);
      }

      return infos;
    } catch (EdmException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Creates the navigation info objects.
   *
   * @param type the type
   * @param propertyNames the property names
   * @return the map
   * @throws EntityProviderException the entity provider exception
   */
  private Map<String, NavigationPropertyInfo> createNavigationInfoObjects(final EdmStructuralType type,
      final List<String> propertyNames) throws EntityProviderException {
    try {
      Map<String, NavigationPropertyInfo> infos = new HashMap<String, NavigationPropertyInfo>();

      for (String propertyName : propertyNames) {
        EdmNavigationProperty navProperty = (EdmNavigationProperty) type.getProperty(propertyName);
        NavigationPropertyInfo info = NavigationPropertyInfo.create(navProperty);
        infos.put(propertyName, info);
      }

      return infos;
    } catch (EdmException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Creates the entity property info.
   *
   * @param property the property
   * @return the entity property info
   * @throws EdmException the edm exception
   * @throws EntityProviderException the entity provider exception
   */
  private EntityPropertyInfo createEntityPropertyInfo(final EdmProperty property) throws EdmException,
      EntityProviderException {
    EdmType type = property.getType();
    if (type instanceof EdmSimpleType) {
      return EntityPropertyInfo.create(property);
    } else if (type instanceof EdmComplexType) {
      EdmComplexType complex = (EdmComplexType) type;
      Map<String, EntityPropertyInfo> recursiveInfos = createPropertyInfoObjects(complex, complex.getPropertyNames());
      return EntityComplexPropertyInfo.create(property, complex.getPropertyNames(), recursiveInfos);
    } else {
      throw new EntityProviderException(EntityProviderException.UNSUPPORTED_PROPERTY_TYPE);
    }
  }

  /**
   * Creates the entity property info.
   *
   * @param functionImport the function import
   * @param type the type
   * @return the entity property info
   * @throws EdmException the edm exception
   * @throws EntityProviderException the entity provider exception
   */
  private EntityPropertyInfo createEntityPropertyInfo(final EdmFunctionImport functionImport, final EdmType type)
      throws EdmException, EntityProviderException {
    EntityPropertyInfo epi;

    if (type.getKind() == EdmTypeKind.COMPLEX) {
      EdmComplexType complex = (EdmComplexType) type;
      Map<String, EntityPropertyInfo> eia = EntityInfoAggregator.create(complex);

      List<EntityPropertyInfo> childEntityInfoList = new ArrayList<EntityPropertyInfo>();
      for (String propertyName : complex.getPropertyNames()) {
        childEntityInfoList.add(eia.get(propertyName));
      }
      epi = new EntityComplexPropertyInfo(functionImport.getName(), type, null, null, childEntityInfoList);

    } else if (type.getKind() == EdmTypeKind.SIMPLE) {

      epi = new EntityPropertyInfo(functionImport.getName(), type, null, null, null, null);
    } else {
      throw new EntityProviderException(EntityProviderException.UNSUPPORTED_PROPERTY_TYPE.addContent(type.getKind()));
    }

    return epi;
  }

  /**
   * Check E tag relevant.
   *
   * @param edmProperty the edm property
   * @throws EntityProviderException the entity provider exception
   */
  private void checkETagRelevant(final EdmProperty edmProperty) throws EntityProviderException {
    try {
      if (edmProperty.getFacets() != null && edmProperty.getFacets().getConcurrencyMode() == EdmConcurrencyMode.Fixed) {
        etagPropertyNames.add(edmProperty.getName());
      }
    } catch (EdmException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Check target path info.
   *
   * @param property the property
   * @param propertyInfo the property info
   * @throws EntityProviderException the entity provider exception
   */
  private void checkTargetPathInfo(final EdmProperty property, final EntityPropertyInfo propertyInfo)
      throws EntityProviderException {
    try {
      EdmCustomizableFeedMappings customizableFeedMappings = property.getCustomizableFeedMappings();
      if (customizableFeedMappings != null) {
        String targetPath = customizableFeedMappings.getFcTargetPath();
        targetPath2EntityPropertyInfo.put(targetPath, propertyInfo);
        if (!SYN_TARGET_PATHS.contains(targetPath)) {
          noneSyndicationTargetPaths.add(targetPath);
        }
      }
    } catch (EdmException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Gets the expanded navigation property names.
   *
   * @return the expanded navigation property names
   */
  public List<String> getExpandedNavigationPropertyNames() {
    return expandedNavigationPropertyNames;
  }
}
