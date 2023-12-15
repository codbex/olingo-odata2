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
package org.apache.olingo.odata2.jpa.processor.core.mock;

import java.util.List;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmAssociation;
import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmServiceMetadata;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.jpa.processor.core.mock.data.EdmMockUtilV2;
import org.easymock.EasyMock;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataServiceMock.
 */
public class ODataServiceMock {

  /** The edm mock. */
  private Edm edmMock = null;
  
  /** The Constant SERVICE_ROOT. */
  public static final String SERVICE_ROOT = "http://apache.odata.org/OData.svc/";

  /**
   * Mock.
   *
   * @return the o data service
   * @throws ODataException the o data exception
   */
  public ODataService mock() throws ODataException {
    ODataService odataService = EasyMock.createMock(ODataService.class);
    EasyMock.expect(odataService.getEntityDataModel()).andReturn(mockEdm());
    EasyMock.replay(odataService);
    return odataService;

  }

  /**
   * Mock edm.
   *
   * @return the edm
   */
  private Edm mockEdm() {
    if (edmMock == null) {
      edmMock = new EdmMock();
    }
    return edmMock;
  }

  /**
   * The Class EdmMock.
   */
  public static class EdmMock implements Edm {

    /**
     * Gets the entity container.
     *
     * @param name the name
     * @return the entity container
     * @throws EdmException the edm exception
     */
    @Override
    public EdmEntityContainer getEntityContainer(final String name) throws EdmException {
      return EdmMockUtilV2.mockEdmEntityContainer(name);
    }

    /**
     * Gets the entity type.
     *
     * @param namespace the namespace
     * @param name the name
     * @return the entity type
     * @throws EdmException the edm exception
     */
    @Override
    public EdmEntityType getEntityType(final String namespace, final String name) throws EdmException {
      // TODO Auto-generated method stub
      return null;
    }

    /**
     * Gets the complex type.
     *
     * @param namespace the namespace
     * @param name the name
     * @return the complex type
     * @throws EdmException the edm exception
     */
    @Override
    public EdmComplexType getComplexType(final String namespace, final String name) throws EdmException {
      // TODO Auto-generated method stub
      return null;
    }

    /**
     * Gets the association.
     *
     * @param namespace the namespace
     * @param name the name
     * @return the association
     * @throws EdmException the edm exception
     */
    @Override
    public EdmAssociation getAssociation(final String namespace, final String name) throws EdmException {
      // TODO Auto-generated method stub
      return null;
    }

    /**
     * Gets the service metadata.
     *
     * @return the service metadata
     */
    @Override
    public EdmServiceMetadata getServiceMetadata() {
      // TODO Auto-generated method stub
      return null;
    }

    /**
     * Gets the default entity container.
     *
     * @return the default entity container
     * @throws EdmException the edm exception
     */
    @Override
    public EdmEntityContainer getDefaultEntityContainer() throws EdmException {
      return EdmMockUtilV2.mockEdmEntityContainer(null);
    }

    /**
     * Gets the entity sets.
     *
     * @return the entity sets
     * @throws EdmException the edm exception
     */
    @Override
    public List<EdmEntitySet> getEntitySets() throws EdmException {
      // TODO Auto-generated method stub
      return null;
    }

    /**
     * Gets the function imports.
     *
     * @return the function imports
     * @throws EdmException the edm exception
     */
    @Override
    public List<EdmFunctionImport> getFunctionImports() throws EdmException {
      // TODO Auto-generated method stub
      return null;
    }

  }
}
