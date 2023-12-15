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

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.olingo.odata2.api.edm.EdmEntitySetInfo;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.core.commons.Encoder;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmEntitySetInfoImplProv.
 */
public class EdmEntitySetInfoImplProv implements EdmEntitySetInfo {

  /** The entity set name. */
  private final String entitySetName;
  
  /** The entity set uri. */
  private final URI entitySetUri;
  
  /** The entity container name. */
  private final String entityContainerName;
  
  /** The is default entity container. */
  private final boolean isDefaultEntityContainer;

  /**
   * Instantiates a new edm entity set info impl prov.
   *
   * @param entitySet the entity set
   * @param entityContainerInfo the entity container info
   * @throws EdmException the edm exception
   */
  public EdmEntitySetInfoImplProv(final EntitySet entitySet, final EntityContainerInfo entityContainerInfo)
      throws EdmException {
    entityContainerName = entityContainerInfo.getName();
    isDefaultEntityContainer = entityContainerInfo.isDefaultEntityContainer();

    entitySetName = entitySet.getName();

    try {
      if (isDefaultEntityContainer) {
        entitySetUri = new URI(Encoder.encode(entitySetName));
      } else {
        entitySetUri = new URI(Encoder.encode(entityContainerName + "." + entitySetName));
      }
    } catch (URISyntaxException e) {
      throw new EdmException(EdmException.COMMON, e);
    }

  }

  /**
   * Gets the entity container name.
   *
   * @return the entity container name
   */
  @Override
  public String getEntityContainerName() {
    return entityContainerName;

  }

  /**
   * Gets the entity set name.
   *
   * @return the entity set name
   */
  @Override
  public String getEntitySetName() {
    return entitySetName;

  }

  /**
   * Checks if is default entity container.
   *
   * @return true, if is default entity container
   */
  @Override
  public boolean isDefaultEntityContainer() {
    return isDefaultEntityContainer;

  }

  /**
   * Gets the entity set uri.
   *
   * @return the entity set uri
   */
  @Override
  public URI getEntitySetUri() {
    return entitySetUri;

  }

}
