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

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmServiceMetadata;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.edm.provider.EdmServiceMetadataImplProv;
import org.apache.olingo.odata2.core.ep.AbstractXmlProducerTestHelper;
import org.apache.olingo.odata2.core.ep.AtomEntityProvider;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class AtomServiceDocumentProducerTest.
 */
public class AtomServiceDocumentProducerTest extends AbstractXmlProducerTestHelper {

  /** The edm. */
  private Edm edm;
  
  /** The schemas. */
  private List<Schema> schemas;

  /**
   * Instantiates a new atom service document producer test.
   *
   * @param type the type
   */
  public AtomServiceDocumentProducerTest(final StreamWriterImplType type) {
    super(type);
  }

  /**
   * Before.
   *
   * @throws ODataException the o data exception
   */
  @Before
  public void before() throws ODataException {
    Map<String, String> prefixMap = new HashMap<String, String>();
    prefixMap.put("atom", Edm.NAMESPACE_ATOM_2005);
    prefixMap.put("a", Edm.NAMESPACE_APP_2007);
    prefixMap.put("xml", Edm.NAMESPACE_XML_1998);
    prefixMap.put("custom", "http://localhost");
    XMLUnit.setXpathNamespaceContext(new SimpleNamespaceContext(prefixMap));

    schemas = new ArrayList<Schema>();

    EdmProvider edmProvider = mock(EdmProvider.class);
    when(edmProvider.getSchemas()).thenReturn(schemas);

    EdmServiceMetadata edmServiceMetadata = new EdmServiceMetadataImplProv(edmProvider);

    edm = mock(Edm.class);
    when(edm.getServiceMetadata()).thenReturn(edmServiceMetadata);
  }

  /**
   * Write empty service document over runtime delegate.
   *
   * @throws Exception the exception
   */
  @Test
  public void writeEmptyServiceDocumentOverRuntimeDelegate() throws Exception {
    ODataResponse response =
        EntityProvider.writeServiceDocument(HttpContentType.APPLICATION_ATOM_XML, edm, "http://localhost");
    String xmlString = verifyResponse(response);

    assertXpathExists("/a:service", xmlString);
    assertXpathExists("/a:service/a:workspace", xmlString);
    assertXpathExists("/a:service/a:workspace/atom:title", xmlString);
    assertXpathEvaluatesTo("Default", "/a:service/a:workspace/atom:title", xmlString);
  }

  /**
   * Write empty service document.
   *
   * @throws Exception the exception
   */
  @Test
  public void writeEmptyServiceDocument() throws Exception {
    ODataResponse response = new AtomEntityProvider().writeServiceDocument(edm, "http://localhost");
    String xmlString = verifyResponse(response);

    assertXpathExists("/a:service", xmlString);
    assertXpathExists("/a:service/a:workspace", xmlString);
    assertXpathExists("/a:service/a:workspace/atom:title", xmlString);
    assertXpathEvaluatesTo("Default", "/a:service/a:workspace/atom:title", xmlString);
  }

  /**
   * Write service document with one enity set one container one schema.
   *
   * @throws Exception the exception
   */
  @Test
  public void writeServiceDocumentWithOneEnitySetOneContainerOneSchema() throws Exception {
    List<EntitySet> entitySets = new ArrayList<EntitySet>();
    entitySets.add(new EntitySet().setName("Employees"));

    List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
    entityContainers.add(new EntityContainer().setDefaultEntityContainer(true).setName("Container").setEntitySets(
        entitySets));

    schemas.add(new Schema().setEntityContainers(entityContainers));

    ODataResponse response = new AtomEntityProvider().writeServiceDocument(edm, "http://localhost");
    String xmlString = verifyResponse(response);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']", xmlString);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);
    assertXpathEvaluatesTo("Employees", "/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);
  }

  /**
   * Write service document with one enity set two containers one schema.
   *
   * @throws Exception the exception
   */
  @Test
  public void writeServiceDocumentWithOneEnitySetTwoContainersOneSchema() throws Exception {
    List<EntitySet> entitySets = new ArrayList<EntitySet>();
    entitySets.add(new EntitySet().setName("Employees"));

    List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
    entityContainers.add(new EntityContainer().setDefaultEntityContainer(true).setName("Container").setEntitySets(
        entitySets));
    entityContainers.add(new EntityContainer().setDefaultEntityContainer(false).setName("Container2").setEntitySets(
        entitySets));

    schemas.add(new Schema().setEntityContainers(entityContainers));

    ODataResponse response = new AtomEntityProvider().writeServiceDocument(edm, "http://localhost");
    String xmlString = verifyResponse(response);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']", xmlString);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);
    assertXpathEvaluatesTo("Employees", "/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);

    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']", xmlString);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);
    assertXpathEvaluatesTo("Employees", "/a:service/a:workspace/a:collection[@href='Container2.Employees']/atom:title",
        xmlString);
  }

  /**
   * Write service document with one enity set two containers two schemas.
   *
   * @throws Exception the exception
   */
  @Test
  public void writeServiceDocumentWithOneEnitySetTwoContainersTwoSchemas() throws Exception {
    List<EntitySet> entitySets = new ArrayList<EntitySet>();
    entitySets.add(new EntitySet().setName("Employees"));

    List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
    entityContainers.add(new EntityContainer().setDefaultEntityContainer(true).setName("Container").setEntitySets(
        entitySets));
    entityContainers.add(new EntityContainer().setDefaultEntityContainer(false).setName("Container2").setEntitySets(
        entitySets));

    List<EntityContainer> entityContainers2 = new ArrayList<EntityContainer>();
    entityContainers2.add(new EntityContainer().setDefaultEntityContainer(false).setName("Container3").setEntitySets(
        entitySets));
    entityContainers2.add(new EntityContainer().setDefaultEntityContainer(false).setName("Container4").setEntitySets(
        entitySets));

    schemas.add(new Schema().setEntityContainers(entityContainers));
    schemas.add(new Schema().setEntityContainers(entityContainers2));

    ODataResponse response = new AtomEntityProvider().writeServiceDocument(edm, "http://localhost");
    String xmlString = verifyResponse(response);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']", xmlString);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);
    assertXpathEvaluatesTo("Employees", "/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);

    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']", xmlString);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);
    assertXpathEvaluatesTo("Employees", "/a:service/a:workspace/a:collection[@href='Container2.Employees']/atom:title",
        xmlString);

    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']", xmlString);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);
    assertXpathEvaluatesTo("Employees", "/a:service/a:workspace/a:collection[@href='Container3.Employees']/atom:title",
        xmlString);

    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']", xmlString);
    assertXpathExists("/a:service/a:workspace/a:collection[@href='Employees']/atom:title", xmlString);
    assertXpathEvaluatesTo("Employees", "/a:service/a:workspace/a:collection[@href='Container4.Employees']/atom:title",
        xmlString);
  }

  /**
   * Verify response.
   *
   * @param response the response
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String verifyResponse(final ODataResponse response) throws IOException {
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntityProvider should not set content header", response.getContentHeader());
    String xmlString = StringHelper.inputStreamToString((InputStream) response.getEntity());
    return xmlString;
  }

}
