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
package org.apache.olingo.odata2.core.edm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmAssociation;
import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmImplTest.
 */
public class EdmImplTest extends BaseTest {

  /** The edm. */
  private ForEdmImplTest edm;

  /**
   * Gets the edm impl.
   *
   * @return the edm impl
   * @throws EdmException the edm exception
   */
  @Before
  public void getEdmImpl() throws EdmException {
    edm = new ForEdmImplTest();
  }

  /**
   * Test entity container cache.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testEntityContainerCache() throws EdmException {
    assertEquals(edm.getEntityContainer("foo"), edm.getEntityContainer("foo"));
    assertNotSame(edm.getEntityContainer("foo"), edm.getEntityContainer("bar"));
    assertEquals(edm.getDefaultEntityContainer(), edm.getEntityContainer(null));
    assertNotSame(edm.getDefaultEntityContainer(), edm.getEntityContainer(""));
  }

  /**
   * Test entity type cache.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testEntityTypeCache() throws EdmException {
    assertEquals(edm.getEntityType("foo", "bar"), edm.getEntityType("foo", "bar"));
    assertNotSame(edm.getEntityType("foo", "bar"), edm.getEntityType("bar", "foo"));
  }

  /**
   * Test complex type cache.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testComplexTypeCache() throws EdmException {
    assertEquals(edm.getComplexType("foo", "bar"), edm.getComplexType("foo", "bar"));
    assertNotSame(edm.getComplexType("foo", "bar"), edm.getComplexType("bar", "foo"));
  }

  /**
   * Test association cache.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testAssociationCache() throws EdmException {
    assertEquals(edm.getAssociation("foo", "bar"), edm.getAssociation("foo", "bar"));
    assertNotSame(edm.getAssociation("foo", "bar"), edm.getAssociation("bar", "foo"));
  }

  /**
   * Test entity sets cache.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testEntitySetsCache() throws EdmException {
    assertEquals(edm.getEntitySets(), edm.getEntitySets());
  }

  /**
   * Test function import cache.
   *
   * @throws EdmException the edm exception
   */
  @Test
  public void testFunctionImportCache() throws EdmException {
    assertEquals(edm.getFunctionImports(), edm.getFunctionImports());
  }

  /**
   * The Class ForEdmImplTest.
   */
  private class ForEdmImplTest extends EdmImpl {

    /**
     * Instantiates a new for edm impl test.
     */
    public ForEdmImplTest() {
      super(null);
    }

    /**
     * Creates the entity container.
     *
     * @param name the name
     * @return the edm entity container
     * @throws ODataException the o data exception
     */
    @Override
    protected EdmEntityContainer createEntityContainer(final String name) throws ODataException {
      EdmEntityContainer edmEntityContainer = mock(EdmEntityContainer.class);
      when(edmEntityContainer.getName()).thenReturn(name);
      return edmEntityContainer;
    }

    /**
     * Creates the entity type.
     *
     * @param fqName the fq name
     * @return the edm entity type
     * @throws ODataException the o data exception
     */
    @Override
    protected EdmEntityType createEntityType(final FullQualifiedName fqName) throws ODataException {
      EdmEntityType edmEntityType = mock(EdmEntityType.class);
      when(edmEntityType.getNamespace()).thenReturn(fqName.getNamespace());
      when(edmEntityType.getName()).thenReturn(fqName.getName());
      return edmEntityType;
    }

    /**
     * Creates the complex type.
     *
     * @param fqName the fq name
     * @return the edm complex type
     * @throws ODataException the o data exception
     */
    @Override
    protected EdmComplexType createComplexType(final FullQualifiedName fqName) throws ODataException {
      EdmComplexType edmComplexType = mock(EdmComplexType.class);
      when(edmComplexType.getNamespace()).thenReturn(fqName.getNamespace());
      when(edmComplexType.getName()).thenReturn(fqName.getName());
      return edmComplexType;
    }

    /**
     * Creates the association.
     *
     * @param fqName the fq name
     * @return the edm association
     * @throws ODataException the o data exception
     */
    @Override
    protected EdmAssociation createAssociation(final FullQualifiedName fqName) throws ODataException {
      EdmAssociation edmAssociation = mock(EdmAssociation.class);
      when(edmAssociation.getNamespace()).thenReturn(fqName.getNamespace());
      when(edmAssociation.getName()).thenReturn(fqName.getName());
      return edmAssociation;
    }

    /**
     * Creates the entity sets.
     *
     * @return the list
     * @throws ODataException the o data exception
     */
    @Override
    protected List<EdmEntitySet> createEntitySets() throws ODataException {
      List<EdmEntitySet> edmEntitySets = new ArrayList<EdmEntitySet>();
      return edmEntitySets;
    }

    /**
     * Creates the function imports.
     *
     * @return the list
     * @throws ODataException the o data exception
     */
    @Override
    protected List<EdmFunctionImport> createFunctionImports() throws ODataException {
      List<EdmFunctionImport> edmFunctionImports = new ArrayList<EdmFunctionImport>();
      return edmFunctionImports;
    }

    /**
     * Creates the alias to namespace info.
     *
     * @return the map
     * @throws ODataException the o data exception
     */
    @Override
    protected Map<String, String> createAliasToNamespaceInfo() throws ODataException {
      return new HashMap<String, String>();
    }
  }
}
