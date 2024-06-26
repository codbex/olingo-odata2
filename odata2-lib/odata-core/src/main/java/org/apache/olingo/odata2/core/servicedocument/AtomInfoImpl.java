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
package org.apache.olingo.odata2.core.servicedocument;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySetInfo;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.servicedocument.AtomInfo;
import org.apache.olingo.odata2.api.servicedocument.Collection;
import org.apache.olingo.odata2.api.servicedocument.CommonAttributes;
import org.apache.olingo.odata2.api.servicedocument.ExtensionElement;
import org.apache.olingo.odata2.api.servicedocument.Workspace;
import org.apache.olingo.odata2.core.commons.Decoder;
import org.apache.olingo.odata2.core.edm.provider.EdmEntitySetInfoImplProv;

// TODO: Auto-generated Javadoc
/**
 * The Class AtomInfoImpl.
 */
public class AtomInfoImpl implements AtomInfo {
  
  /** The workspaces. */
  private List<Workspace> workspaces;
  
  /** The attributes. */
  private CommonAttributes attributes;
  
  /** The extension elements. */
  private List<ExtensionElement> extensionElements;

  /**
   * Gets the common attributes.
   *
   * @return the common attributes
   */
  @Override
  public CommonAttributes getCommonAttributes() {
    return attributes;
  }

  /**
   * Gets the workspaces.
   *
   * @return the workspaces
   */
  @Override
  public List<Workspace> getWorkspaces() {
    return workspaces;
  }

  /**
   * Gets the extesion elements.
   *
   * @return the extesion elements
   */
  @Override
  public List<ExtensionElement> getExtesionElements() {
    return extensionElements;
  }

  /**
   * Sets the workspaces.
   *
   * @param workspaces the workspaces
   * @return the atom info impl
   */
  public AtomInfoImpl setWorkspaces(final List<Workspace> workspaces) {
    this.workspaces = workspaces;
    return this;
  }

  /**
   * Sets the common attributes.
   *
   * @param attributes the attributes
   * @return the atom info impl
   */
  public AtomInfoImpl setCommonAttributes(final CommonAttributes attributes) {
    this.attributes = attributes;
    return this;
  }

  /**
   * Sets the extesion elements.
   *
   * @param elements the elements
   * @return the atom info impl
   */
  public AtomInfoImpl setExtesionElements(final List<ExtensionElement> elements) {
    extensionElements = elements;
    return this;
  }

  /**
   * Gets the entity sets info.
   *
   * @return the entity sets info
   * @throws EntityProviderException the entity provider exception
   */
  public List<EdmEntitySetInfo> getEntitySetsInfo() throws EntityProviderException {
    List<EdmEntitySetInfo> entitySets = new ArrayList<EdmEntitySetInfo>();
    for (Workspace workspace : workspaces) {
      for (Collection collection : workspace.getCollections()) {
        String[] names = collection.getHref().split("\\" + Edm.DELIMITER + "(?=[^" + Edm.DELIMITER + "]+$)");
        try {
          if (names.length == 1) {
            EntitySet entitySet = new EntitySet().setName(Decoder.decode(names[0]));
            EntityContainerInfo container = new EntityContainerInfo().setDefaultEntityContainer(true);
            EdmEntitySetInfo entitySetInfo = new EdmEntitySetInfoImplProv(entitySet, container);
            entitySets.add(entitySetInfo);
          } else if (names.length == 2) {
            EntitySet entitySet = new EntitySet().setName(Decoder.decode(names[1]));
            EntityContainerInfo container =
                new EntityContainerInfo().setName(Decoder.decode(names[0])).setDefaultEntityContainer(false);
            EdmEntitySetInfo entitySetInfo = new EdmEntitySetInfoImplProv(entitySet, container);
            entitySets.add(entitySetInfo);
          }
        } catch (EdmException e) {
          throw new EntityProviderException(EntityProviderException.COMMON, e);
        }
      }
    }
    return entitySets;
  }
}
