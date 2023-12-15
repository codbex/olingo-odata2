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
package org.apache.olingo.odata2.ref.read;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.ref.model.DataContainer;
import org.apache.olingo.odata2.ref.processor.BeanPropertyAccess;
import org.apache.olingo.odata2.ref.processor.ListsProcessor;
import org.apache.olingo.odata2.ref.processor.ScenarioDataSource;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EntityTest.
 */
public class EntityTest extends BaseTest {

  /** The data container. */
  private static DataContainer dataContainer;
  
  /** The data source. */
  private static ScenarioDataSource dataSource;
  
  /** The processor. */
  private static ListsProcessor processor;

  /** The mocked context. */
  private ODataContext mockedContext;

  /**
   * Inits the.
   */
  @BeforeClass
  public static void init() {
    dataContainer = new DataContainer();
    dataContainer.reset();
    dataSource = new ScenarioDataSource(dataContainer);
    processor = new ListsProcessor(dataSource, new BeanPropertyAccess());
  }

  /**
   * Sets the up.
   *
   * @throws Exception the exception
   */
  @Before
  public void setUp() throws Exception {
    mockedContext = mock(ODataContext.class);
    PathInfo uriInfo = mock(PathInfo.class);
    when(uriInfo.getServiceRoot()).thenReturn(new URI("http://localhost/"));
    when(mockedContext.getPathInfo()).thenReturn(uriInfo);

    processor.setContext(mockedContext);
  }

  /**
   * Mock uri result.
   *
   * @param entitySetName the entity set name
   * @param keyName the key name
   * @param keyValue the key value
   * @return the uri info
   * @throws EdmException the edm exception
   */
  private UriInfo mockUriResult(final String entitySetName, final String keyName, final String keyValue)
      throws EdmException {
    EdmProperty keyProperty = mock(EdmProperty.class);
    when(keyProperty.getName()).thenReturn(keyName);
    when(keyProperty.getType()).thenReturn(EdmSimpleTypeKind.String.getEdmSimpleTypeInstance());
    when(keyProperty.isSimple()).thenReturn(true);
    EdmMapping mapping = mock(EdmMapping.class);
    when(mapping.getInternalName()).thenReturn("getId");
    when(keyProperty.getMapping()).thenReturn(mapping);

    KeyPredicate key = mock(KeyPredicate.class);
    when(key.getProperty()).thenReturn(keyProperty);
    when(key.getLiteral()).thenReturn(keyValue);

    ArrayList<KeyPredicate> keys = new ArrayList<KeyPredicate>();
    keys.add(key);

    EdmEntityType entityType = mock(EdmEntityType.class);
    when(entityType.getName()).thenReturn(entitySetName);
    when(entityType.getProperty(keyProperty.getName())).thenReturn(keyProperty);
    when(entityType.getPropertyNames()).thenReturn(Arrays.asList(keyName));

    EdmEntitySet entitySet = mock(EdmEntitySet.class);
    when(entitySet.getName()).thenReturn(entitySetName);
    when(entitySet.getEntityType()).thenReturn(entityType);

    EdmEntityContainer entityContainer = mock(EdmEntityContainer.class);
    when(entityContainer.isDefaultEntityContainer()).thenReturn(true);
    when(entitySet.getEntityContainer()).thenReturn(entityContainer);

    UriInfo uriResult = mock(UriInfo.class);
    when(uriResult.getStartEntitySet()).thenReturn(entitySet);
    when(uriResult.getTargetEntitySet()).thenReturn(entitySet);
    when(uriResult.getKeyPredicates()).thenReturn(keys);
    return uriResult;
  }

  /**
   * Read content.
   *
   * @param response the response
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String readContent(final ODataResponse response) throws IOException {
    CharBuffer content = CharBuffer.allocate(1000);
    new InputStreamReader((InputStream) response.getEntity()).read(content);
    content.rewind();
    return content.toString();
  }

  /**
   * Read employees.
   *
   * @throws Exception the exception
   */
  @Test
  public void readEmployees() throws Exception {
    final UriInfo uriResult = mockUriResult("Employees", "EmployeeId", "5");

    ODataResponse response =
        processor.readEntity(uriResult, ContentType.APPLICATION_ATOM_XML_ENTRY.toContentTypeString());
    assertNotNull(response);
    assertTrue(readContent(response).contains("Employee"));
  }

  /**
   * Read teams.
   *
   * @throws Exception the exception
   */
  @Test
  public void readTeams() throws Exception {
    final UriInfo uriResult = mockUriResult("Teams", "Id", "1");

    ODataResponse response =
        processor.readEntity(uriResult, ContentType.APPLICATION_ATOM_XML_ENTRY.toContentTypeString());
    assertNotNull(response);
    assertTrue(readContent(response).contains("Team"));
  }

  /**
   * Read rooms.
   *
   * @throws Exception the exception
   */
  @Test
  public void readRooms() throws Exception {
    final UriInfo uriResult = mockUriResult("Rooms", "Id", "1");

    ODataResponse response =
        processor.readEntity(uriResult, ContentType.APPLICATION_ATOM_XML_FEED.toContentTypeString());
    assertNotNull(response);
    assertTrue(readContent(response).contains("Room"));
  }

  /**
   * Read managers.
   *
   * @throws Exception the exception
   */
  @Test
  public void readManagers() throws Exception {
    final UriInfo uriResult = mockUriResult("Managers", "EmployeeId", "1");

    ODataResponse response =
        processor.readEntity(uriResult, ContentType.APPLICATION_ATOM_XML_ENTRY.toContentTypeString());
    assertNotNull(response);
    assertTrue(readContent(response).contains("Manager"));
  }

  /**
   * Read buildings.
   *
   * @throws Exception the exception
   */
  @Test
  public void readBuildings() throws Exception {
    final UriInfo uriResult = mockUriResult("Buildings", "Id", "1");

    ODataResponse response =
        processor.readEntity(uriResult, ContentType.APPLICATION_ATOM_XML_ENTRY.toContentTypeString());
    assertNotNull(response);
    assertTrue(readContent(response).contains("Building"));
  }

}
