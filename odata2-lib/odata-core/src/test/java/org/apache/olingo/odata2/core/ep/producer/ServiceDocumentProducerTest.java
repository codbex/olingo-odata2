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
package org.apache.olingo.odata2.core.ep.producer;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.edm.provider.EdmImplProv;
import org.apache.olingo.odata2.core.ep.AbstractXmlProducerTestHelper;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceDocumentProducerTest.
 */
public class ServiceDocumentProducerTest extends AbstractXmlProducerTestHelper {

  /** The edm. */
  private EdmImplProv edm;

  /**
   * Instantiates a new service document producer test.
   *
   * @param type the type
   */
  public ServiceDocumentProducerTest(final StreamWriterImplType type) {
    super(type);
  }

  /**
   * Before.
   *
   * @throws ODataException the o data exception
   */
  @Before
  public void before() throws ODataException {
    EdmProvider edmProvider = mock(EdmProvider.class);

    EntityType entityType = new EntityType().setName("EntityType1");
    when(edmProvider.getEntityType(new FullQualifiedName("EntityType1Ns", "EntityType1"))).thenReturn(entityType);

    ComplexType complexType = new ComplexType().setName("ComplexType1");
    when(edmProvider.getComplexType(new FullQualifiedName("ComplexType1Ns", "ComplexType1"))).thenReturn(complexType);

    Association association = new Association().setName("Association1");
    when(edmProvider.getAssociation(new FullQualifiedName("Association1Ns", "Association1"))).thenReturn(association);

    EntityContainerInfo defaultEntityContainer = new EntityContainerInfo().setName("Container1");
    when(edmProvider.getEntityContainerInfo(null)).thenReturn(defaultEntityContainer);
    when(edmProvider.getEntityContainerInfo("Container1")).thenReturn(defaultEntityContainer);

    edm = new EdmImplProv(edmProvider);

  }

  /**
   * Test service document xml.
   *
   * @throws EntityProviderException the entity provider exception
   * @throws ODataException the o data exception
   */
  @Test
  public void testServiceDocumentXml() throws EntityProviderException, ODataException {
    ODataResponse response =
        EntityProvider.writeServiceDocument(HttpContentType.APPLICATION_ATOM_XML, edm, "http://localhost/");

    assertNull("EntityProvider should not set content header", response.getContentHeader());
  }
}
