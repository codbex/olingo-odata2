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

import java.io.InputStream;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.ep.AbstractProviderTest;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlLinkEntityProducerTest.
 */
public class XmlLinkEntityProducerTest extends AbstractProviderTest {

  /**
   * Instantiates a new xml link entity producer test.
   *
   * @param type the type
   */
  public XmlLinkEntityProducerTest(final StreamWriterImplType type) {
    super(type);
  }

  /**
   * Serialize employee link.
   *
   * @throws Exception the exception
   */
  @Test
  public void serializeEmployeeLink() throws Exception {
    final EdmEntitySet entitySet = MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees");

    final ODataResponse content = createAtomEntityProvider().writeLink(entitySet, employeeData, DEFAULT_PROPERTIES);
    assertNotNull(content);
    assertNotNull(content.getEntity());
    assertNull("EntitypProvider should not set content header", content.getContentHeader());

    final String xml = StringHelper.inputStreamToString((InputStream) content.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:uri", xml);
    assertXpathEvaluatesTo(BASE_URI.toString() + "Employees('1')", "/d:uri/text()", xml);
  }

  /**
   * Serialize photo link.
   *
   * @throws Exception the exception
   */
  @Test
  public void serializePhotoLink() throws Exception {
    final EdmEntitySet entitySet = MockFacade.getMockEdm().getEntityContainer("Container2").getEntitySet("Photos");

    final ODataResponse response = createAtomEntityProvider().writeLink(entitySet, photoData, DEFAULT_PROPERTIES);
    assertNotNull(response);
    assertNotNull(response.getEntity());

    final String xml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertNotNull(xml);

    assertXpathExists("/d:uri", xml);
    assertXpathEvaluatesTo(BASE_URI.toString() + "Container2.Photos(Id=1,Type='image%2Fpng')", "/d:uri/text()", xml);
  }
}
