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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmCustomizableFeedMappings;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmType;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityComplexPropertyInfo.
 */
public class EntityComplexPropertyInfo extends EntityPropertyInfo {

  /** The entity property info. */
  protected List<EntityPropertyInfo> entityPropertyInfo;

  /**
   * Instantiates a new entity complex property info.
   *
   * @param name the name
   * @param type the type
   * @param facets the facets
   * @param customizableFeedMapping the customizable feed mapping
   * @param childEntityInfos the child entity infos
   */
  EntityComplexPropertyInfo(final String name, final EdmType type, final EdmFacets facets,
      final EdmCustomizableFeedMappings customizableFeedMapping, final List<EntityPropertyInfo> childEntityInfos) {
    super(name, type, facets, customizableFeedMapping, null, null);
    entityPropertyInfo = childEntityInfos;
  }

  /**
   * Creates the.
   *
   * @param property the property
   * @param propertyNames the property names
   * @param childEntityInfos the child entity infos
   * @return the entity complex property info
   * @throws EdmException the edm exception
   */
  static EntityComplexPropertyInfo create(final EdmProperty property, final List<String> propertyNames,
      final Map<String, EntityPropertyInfo> childEntityInfos) throws EdmException {
    List<EntityPropertyInfo> childEntityInfoList = new ArrayList<EntityPropertyInfo>(childEntityInfos.size());
    for (String name : propertyNames) {
      childEntityInfoList.add(childEntityInfos.get(name));
    }

    EntityComplexPropertyInfo info = new EntityComplexPropertyInfo(
        property.getName(),
        property.getType(),
        property.getFacets(),
        property.getCustomizableFeedMappings(),
        childEntityInfoList);
    return info;
  }

  /**
   * Checks if is complex.
   *
   * @return true, if is complex
   */
  @Override
  public boolean isComplex() {
    return true;
  }

  /**
   * Gets the property infos.
   *
   * @return the property infos
   */
  public List<EntityPropertyInfo> getPropertyInfos() {
    return Collections.unmodifiableList(entityPropertyInfo);
  }

  /**
   * Gets the property info.
   *
   * @param name the name
   * @return the property info
   */
  public EntityPropertyInfo getPropertyInfo(final String name) {
    for (EntityPropertyInfo info : entityPropertyInfo) {
      if (info.getName().equals(name)) {
        return info;
      }
    }
    return null;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (EntityPropertyInfo info : entityPropertyInfo) {
      if (sb.length() == 0) {
        sb.append(super.toString()).append("=>[").append(info.toString());
      } else {
        sb.append(", ").append(info.toString());
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
