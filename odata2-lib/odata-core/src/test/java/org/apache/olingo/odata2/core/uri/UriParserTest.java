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
package org.apache.olingo.odata2.core.uri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.UriNotMatchingException;
import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.api.uri.UriSyntaxException;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * Tests for OData URI parsing.
 * 
 */
public class UriParserTest extends BaseTest {

  /** The edm. */
  private Edm edm;
  
  /** The Constant ACCEPT_FORM_ENCODING. */
  private static final String ACCEPT_FORM_ENCODING = "odata-accept-forms-encoding";

  /**
   * Gets the edm.
   *
   * @return the edm
   * @throws ODataException the o data exception
   */
  @Before
  public void getEdm() throws ODataException {
    edm = MockFacade.getMockEdm();
  }

  /**
   * Parse the URI part after an OData service root, given as string.
   * Query parameters can be included.
   *
   * @param uri the URI part
   * @return a {@link UriInfoImpl} instance containing the parsed information
   * @throws UriSyntaxException the uri syntax exception
   * @throws UriNotMatchingException the uri not matching exception
   * @throws EdmException the edm exception
   */
  private UriInfoImpl parse(final String uri) throws UriSyntaxException, UriNotMatchingException, EdmException {
    final String[] path = uri.split("\\?", -1);
    if (path.length > 2) {
      throw new UriSyntaxException(UriSyntaxException.URISYNTAX);
    }

    final List<PathSegment> pathSegments =
        MockFacade.getPathSegmentsAsODataPathSegmentMock(Arrays.asList(path[0].split("/", -1)));
    final Map<String, List<String>> queryParameters = getQueryParameters(path.length == 2 ? unescape(path[1]) : "");

    return (UriInfoImpl) new UriParserImpl(edm).parseAll(pathSegments, queryParameters);
  }

  /**
   * Gets the query parameters.
   *
   * @param uri the uri
   * @return the query parameters
   */
  private Map<String, List<String>> getQueryParameters(final String uri) {
    Map<String, List<String>> allQueryParameters = new HashMap<String, List<String>>();

    for (final String option : uri.split("&")) {
      final String[] keyAndValue = option.split("=");
      List<String> list = allQueryParameters.containsKey(keyAndValue[0]) ?
          allQueryParameters.get(keyAndValue[0]) : new LinkedList<String>();

      list.add(keyAndValue.length == 2 ? keyAndValue[1] : "");

      allQueryParameters.put(keyAndValue[0], list);
    }

    return allQueryParameters;
  }

  /**
   * Unescape.
   *
   * @param s the s
   * @return the string
   * @throws UriSyntaxException the uri syntax exception
   */
  private String unescape(final String s) throws UriSyntaxException {
    try {
      return new URI(s).getPath();
    } catch (URISyntaxException e) {
      throw new UriSyntaxException(UriSyntaxException.NOTEXT);
    }
  }

  /**
   * Parses the wrong uri.
   *
   * @param uri the uri
   * @param exceptionContext the exception context
   */
  private void parseWrongUri(final String uri, final MessageReference exceptionContext) {
    try {
      parse(uri);
      fail("Expected UriParserException not thrown");
    } catch (ODataMessageException e) {
      assertNotNull(e);
      assertEquals(exceptionContext.getKey(), e.getMessageReference().getKey());
    }
  }

  /**
   * Copy path segments test.
   *
   * @throws Exception the exception
   */
  @Test
  public void copyPathSegmentsTest() throws Exception {
    List<PathSegment> pathSegments = new ArrayList<PathSegment>();
    pathSegments.add(UriParser.createPathSegment("$metadata", null));
    UriInfo result = new UriParserImpl(edm).parse(pathSegments, Collections.<String, String> emptyMap());
    assertNotNull(result);
    assertEquals(1, pathSegments.size());
    assertEquals("$metadata", pathSegments.get(0).getPath());
  }
  
  /**
   * Copy path segments test encoded.
   *
   * @throws Exception the exception
   */
  @Test
  public void copyPathSegmentsTestEncoded() throws Exception {
    List<PathSegment> pathSegments = new ArrayList<PathSegment>();
    pathSegments.add(UriParser.createPathSegment("%24metadata", null));
    UriInfoImpl result = (UriInfoImpl) new UriParserImpl(edm).parse(pathSegments,
        Collections.<String, String> emptyMap());
    assertNotNull(result);
    assertEquals(UriType.URI8, result.getUriType());
  }
  
  /**
   * Parses the nonsense.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNonsense() throws Exception {
    parseWrongUri("/bla", UriNotMatchingException.NOTFOUND);
  }

  /**
   * Parses the service document.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseServiceDocument() throws Exception {
    UriInfoImpl result = parse("/");
    assertEquals(UriType.URI0, result.getUriType());

    result = parse("");
    assertEquals(UriType.URI0, result.getUriType());

    result =
        (UriInfoImpl) new UriParserImpl(edm).parse(Collections.<PathSegment> emptyList(), Collections
            .<String, String> emptyMap());
    assertEquals(UriType.URI0, result.getUriType());
  }

  /**
   * Parses the metadata.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseMetadata() throws Exception {
    UriInfoImpl result = parse("/$metadata");
    assertEquals(UriType.URI8, result.getUriType());
  }

  /**
   * Parses the metadata error.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseMetadataError() throws Exception {
    parseWrongUri("/$metadata/somethingwrong", UriSyntaxException.MUSTBELASTSEGMENT);
  }

  /**
   * Parses the batch.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseBatch() throws Exception {
    UriInfoImpl result = parse("/$batch");
    assertEquals(UriType.URI9, result.getUriType());
  }

  /**
   * Parses the batch error.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseBatchError() throws Exception {
    parseWrongUri("/$batch/somethingwrong", UriSyntaxException.MUSTBELASTSEGMENT);
  }

  /**
   * Parses the something entity set.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSomethingEntitySet() throws Exception {
    parseWrongUri("/somethingwrong", UriNotMatchingException.NOTFOUND);
  }

  /**
   * Parses the container without entity set.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseContainerWithoutEntitySet() throws Exception {
    parseWrongUri("Container1.", UriNotMatchingException.MATCHPROBLEM);
  }

  /**
   * Parses the employees entity set.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntitySet() throws Exception {
    UriInfoImpl result = parse("/Employees");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
  }

  /**
   * Parses the employees entity set parentheses count.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntitySetParenthesesCount() throws Exception {
    UriInfoImpl result = parse("/Employees()/$count");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI15, result.getUriType());
    assertTrue(result.isCount());
  }

  /**
   * Parses the employees entity set parentheses count not last.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntitySetParenthesesCountNotLast() throws Exception {
    parseWrongUri("/Employees()/$count/somethingwrong", UriSyntaxException.MUSTBELASTSEGMENT);
  }

  /**
   * Parses the employees entity set parentheses.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntitySetParentheses() throws Exception {
    UriInfoImpl result = parse("/Employees()");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
  }

  /**
   * Final empty segment.
   *
   * @throws Exception the exception
   */
  @Test
  public void finalEmptySegment() throws Exception {
    UriInfoImpl result = parse("Employees()/");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
  }

  /**
   * Parses the wrong entities.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWrongEntities() throws Exception {
    parseWrongUri("//", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("/Employees%20()", UriNotMatchingException.NOTFOUND);
    parseWrongUri("Employees%28%29", UriNotMatchingException.NOTFOUND);
    parseWrongUri("/Employees()%2F", UriNotMatchingException.MATCHPROBLEM);
    parseWrongUri("/Employees()/somethingwrong", UriSyntaxException.ENTITYSETINSTEADOFENTITY);
    parseWrongUri("/Employees/somethingwrong", UriSyntaxException.ENTITYSETINSTEADOFENTITY);
    parseWrongUri("//Employees", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Employees//", UriSyntaxException.EMPTYSEGMENT);
  }

  /**
   * Parses the employees entity with key.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithKey() throws Exception {
    UriInfoImpl result = parse("/Employees('1')");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }
  
  /**
   * Parses the employees entity with null key.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithNullKey() throws Exception {
    parseWrongUri("/Employees(null)", UriSyntaxException.INCOMPATIBLELITERAL);
  }


  /**
   * Parses the employees entity with key with comma.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithKeyWithComma() throws Exception {
    UriInfoImpl result = parse("/Employees('1,2')");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1,2", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }

  /**
   * Parses the employees entity with key with squote in string.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithKeyWithSquoteInString() throws Exception {
    UriInfoImpl result = parse("/Employees('1''2')");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1'2", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }


  /**
   * Parses the employees entity with key encoded.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithKeyEncoded() throws Exception {
    UriInfoImpl result = parse("/%45mployees('1')");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }
  
  /**
   * Parses the employees entity.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntity() throws Exception {
    UriInfoImpl result = parse("/Employees('1')");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }

  /**
   * Parses the employees entity with explicit key.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithExplicitKey() throws Exception {
    UriInfoImpl result = parse("/Employees(EmployeeId='1')");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }

  /**
   * Parses the employees entity with explicit key and comma.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithExplicitKeyAndComma() throws Exception {
    UriInfoImpl result = parse("/Employees(EmployeeId='1,2')");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1,2", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }

  /**
   * Parses the employees entity with explicit key and squote in string.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithExplicitKeyAndSquoteInString() throws Exception {
    UriInfoImpl result = parse("/Employees(EmployeeId='1''2')");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1'2", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }

  /**
   * Parses the employees entity with key value.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithKeyValue() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/$value");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertTrue(result.getStartEntitySet().getEntityType().hasStream());
    assertEquals(UriType.URI17, result.getUriType());
    assertTrue(result.isValue());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }

  /**
   * Parses the employees entity with key count.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithKeyCount() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/$count");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI16, result.getUriType());
    assertTrue(result.isCount());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }
  
  /**
   * Parses the employees entity with key count encoded.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesEntityWithKeyCountEncoded() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/%24count");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI16, result.getUriType());
    assertTrue(result.isCount());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }
  
  /**
   * Parses the employees simple property.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesSimpleProperty() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/EmployeeName");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI5, result.getUriType());
    assertEquals("EmployeeName", result.getPropertyPath().get(0).getName());
  }

  /**
   * Parses the employees simple property value.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesSimplePropertyValue() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/EmployeeName/$value");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI5, result.getUriType());
    assertEquals("EmployeeName", result.getPropertyPath().get(0).getName());
    assertTrue(result.isValue());
  }
  
  /**
   * Parses the employees simple property value encoded.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesSimplePropertyValueEncoded() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/EmployeeName/%24value");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI5, result.getUriType());
    assertEquals("EmployeeName", result.getPropertyPath().get(0).getName());
    assertTrue(result.isValue());
  }
  
  /**
   * Parses the employees complex property.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesComplexProperty() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/Location");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI3, result.getUriType());
    assertEquals("Location", result.getPropertyPath().get(0).getName());
  }

  /**
   * Parses the employees complex property with entity.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesComplexPropertyWithEntity() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/Location/Country");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI4, result.getUriType());
    assertEquals("Location", result.getPropertyPath().get(0).getName());
    assertEquals("Country", result.getPropertyPath().get(1).getName());
  }

  /**
   * Parses the employees complex property with entity value.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseEmployeesComplexPropertyWithEntityValue() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/Location/Country/$value");
    assertNull(result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI4, result.getUriType());
    assertEquals("Location", result.getPropertyPath().get(0).getName());
    assertEquals("Country", result.getPropertyPath().get(1).getName());
    assertTrue(result.isValue());
  }

  /**
   * Simple property wrong.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyWrong() throws Exception {
    parseWrongUri("/Employees('1')/EmployeeName(1)", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("/Employees('1')/EmployeeName()", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("/Employees('1')/EmployeeName/something", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("/Employees('1')/EmployeeName/$value/something", UriSyntaxException.MUSTBELASTSEGMENT);
  }

  /**
   * Complex property wrong.
   *
   * @throws Exception the exception
   */
  @Test
  public void complexPropertyWrong() throws Exception {
    parseWrongUri("/Employees('1')/Location(1)", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("/Employees('1')/Location/somethingwrong", UriNotMatchingException.PROPERTYNOTFOUND);
  }

  /**
   * Employees no property.
   *
   * @throws Exception the exception
   */
  @Test
  public void EmployeesNoProperty() throws Exception {
    parseWrongUri("/Employees('1')/somethingwrong", UriNotMatchingException.PROPERTYNOTFOUND);
  }

  /**
   * Parses the navigation property with entity result.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithEntityResult() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/ne_Manager");
    assertEquals("Managers", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI6A, result.getUriType());
  }

  /**
   * Parses the navigation property with entity set result.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithEntitySetResult() throws Exception {
    UriInfoImpl result = parse("/Managers('1')/nm_Employees");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI6B, result.getUriType());
  }

  /**
   * Parses the navigation property with entity set result parenthesis.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithEntitySetResultParenthesis() throws Exception {
    UriInfoImpl result = parse("/Managers('1')/nm_Employees()");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI6B, result.getUriType());
  }

  /**
   * Parses the navigation property with entity result with key.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithEntityResultWithKey() throws Exception {
    UriInfoImpl result = parse("/Managers('1')/nm_Employees('1')");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI6A, result.getUriType());
  }

  /**
   * Parses the navigation property with links one.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithLinksOne() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/$links/ne_Manager");
    assertEquals("Managers", result.getTargetEntitySet().getName());
    assertTrue(result.isLinks());
    assertEquals(UriType.URI7A, result.getUriType());
  }

  /**
   * Parses the navigation property with links many.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithLinksMany() throws Exception {
    UriInfoImpl result = parse("/Managers('1')/$links/nm_Employees");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertTrue(result.isLinks());
    assertEquals(UriType.URI7B, result.getUriType());
  }
  
  /**
   * Parses the navigation property with links many encoded.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithLinksManyEncoded() throws Exception {
    UriInfoImpl result = parse("/Managers('1')/%24links/nm_Employees");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertTrue(result.isLinks());
    assertEquals(UriType.URI7B, result.getUriType());
  }
  
  /**
   * Parses the navigation property with managers count.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithManagersCount() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/ne_Manager/$count");
    assertEquals("Managers", result.getTargetEntitySet().getName());
    assertTrue(result.isCount());
    assertEquals(UriType.URI16, result.getUriType());
  }

  /**
   * Parses the navigation property with employees count.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithEmployeesCount() throws Exception {
    UriInfoImpl result = parse("/Managers('1')/nm_Employees/$count");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertTrue(result.isCount());
    assertEquals(UriType.URI15, result.getUriType());
  }

  /**
   * Parses the navigation property with employee count.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithEmployeeCount() throws Exception {
    UriInfoImpl result = parse("Managers('1')/nm_Employees('1')/$count");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertTrue(result.isCount());
    assertEquals(UriType.URI16, result.getUriType());
  }

  /**
   * Parses the navigation property with links count many.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithLinksCountMany() throws Exception {
    UriInfoImpl result = parse("/Managers('1')/$links/nm_Employees/$count");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertTrue(result.isLinks());
    assertTrue(result.isCount());
    assertEquals(UriType.URI50B, result.getUriType());
  }

  /**
   * Parses the navigation property with links count one.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNavigationPropertyWithLinksCountOne() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/$links/ne_Manager/$count");
    assertEquals("Managers", result.getTargetEntitySet().getName());
    assertTrue(result.isLinks());
    assertTrue(result.isCount());
    assertEquals(UriType.URI50A, result.getUriType());

    result = parse("/Managers('1')/$links/nm_Employees('1')/$count");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertTrue(result.isLinks());
    assertTrue(result.isCount());
    assertEquals(UriType.URI50A, result.getUriType());
  }

  /**
   * Navigation property wrong.
   *
   * @throws Exception the exception
   */
  @Test
  public void navigationPropertyWrong() throws Exception {
    parseWrongUri("Employees('1')/somethingwrong", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Employees('1')//ne_Manager", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Employees('1')/ne_Manager()", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Employees('1')/ne_Manager('1')", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Employees('1')/$links", UriSyntaxException.MUSTNOTBELASTSEGMENT);
    parseWrongUri("Employees('1')/$links/ne_Manager('1')", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Employees('1')/$links/ne_Manager()", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Employees('1')/$links/ne_Manager/somethingwrong", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Employees('1')/ne_Manager/$count/somethingwrong", UriSyntaxException.MUSTBELASTSEGMENT);
    parseWrongUri("Employees('1')/$links/ne_Manager/$count/somethingwrong", UriSyntaxException.MUSTBELASTSEGMENT);
    parseWrongUri("Employees('1')/ne_Manager/$value", UriSyntaxException.NOMEDIARESOURCE);
    parseWrongUri("Managers('1')/nm_Employees('1')/$value/somethingwrong", UriSyntaxException.MUSTBELASTSEGMENT);
    parseWrongUri("Managers('1')/nm_Employees/$links", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Managers('1')/nm_Employees/$links/Manager", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Managers('1')/nm_Employees/somethingwrong", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Employees('1')/$links/somethingwrong", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Employees('1')/$links/EmployeeName", UriSyntaxException.NONAVIGATIONPROPERTY);
    parseWrongUri("Employees('1')/$links/$links/ne_Manager", UriNotMatchingException.PROPERTYNOTFOUND);
  }

  /**
   * Navigation path wrong match.
   *
   * @throws Exception the exception
   */
  @Test
  public void navigationPathWrongMatch() throws Exception {
    parseWrongUri("/Employees('1')/(somethingwrong(", UriNotMatchingException.MATCHPROBLEM);
  }

  /**
   * Navigation segment wrong match.
   *
   * @throws Exception the exception
   */
  @Test
  public void navigationSegmentWrongMatch() throws Exception {
    parseWrongUri("/Employees('1')/$links/(somethingwrong(", UriNotMatchingException.MATCHPROBLEM);
  }

  /**
   * Parses the teams entity with int key value.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseTeamsEntityWithIntKeyValue() throws Exception {
    parseWrongUri("/Teams(1)/$value", UriSyntaxException.INCOMPATIBLELITERAL);
  }

  /**
   * Parses the wrong key.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWrongKey() throws Exception {
    parseWrongUri("Employees(EmployeeId=)", UriSyntaxException.INVALIDKEYPREDICATE);
    parseWrongUri("Employees(,)", UriSyntaxException.INVALIDKEYPREDICATE);
    parseWrongUri("Employees(')", UriSyntaxException.UNKNOWNLITERAL);
    parseWrongUri("Employees(0)", UriSyntaxException.INCOMPATIBLELITERAL);
    parseWrongUri("Employees(,'1')", UriSyntaxException.INVALIDKEYPREDICATE);
    parseWrongUri("Employees('1',)", UriSyntaxException.INVALIDKEYPREDICATE);
    parseWrongUri("Employees('1','2')", UriSyntaxException.DUPLICATEKEYNAMES);
    parseWrongUri("Employees(EmployeeName='1')", UriSyntaxException.INVALIDKEYPREDICATE);
    parseWrongUri("Employees(EmployeeId='1',EmployeeId='1')", UriSyntaxException.DUPLICATEKEYNAMES);
    parseWrongUri("/Employees(EmployeeId='1',somethingwrong=abc)", UriSyntaxException.INVALIDKEYPREDICATE);
    parseWrongUri("/Employees(somethingwrong=1)", UriSyntaxException.INVALIDKEYPREDICATE);
    parseWrongUri("/Container2.Photos(Id=1,,Type='abc')", UriSyntaxException.INVALIDKEYPREDICATE);
    parseWrongUri("/Container2.Photos(Id=1;Type='abc')", UriSyntaxException.INVALIDKEYPREDICATE);
    parseWrongUri("Container2.Photos(Id=1,'abc')", UriSyntaxException.MISSINGKEYPREDICATENAME);
    parseWrongUri("Container2.Photos(Id=1)", UriSyntaxException.INVALIDKEYPREDICATE);
  }

  /**
   * Parses the photo entity with explicit key set.
   *
   * @throws Exception the exception
   */
  @Test
  public void parsePhotoEntityWithExplicitKeySet() throws Exception {
    UriInfoImpl result = parse("/Container2.Photos(Id=1,Type='abc')");
    assertEquals("Container2", result.getEntityContainer().getName());
    assertEquals("Photos", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());
    assertEquals(2, result.getKeyPredicates().size());
    assertEquals("1", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("Id", result.getKeyPredicates().get(0).getProperty().getName());
    assertEquals("abc", result.getKeyPredicates().get(1).getLiteral());
    assertEquals("Type", result.getKeyPredicates().get(1).getProperty().getName());

    result = parse("/Container2.Photos(Id=1,Type='abc%20xyz')");
    assertEquals("abc xyz", result.getKeyPredicates().get(1).getLiteral());

    result = parse("/Container2.Photos(Id=1,Type='image%2Fpng')");
    assertEquals("image/png", result.getKeyPredicates().get(1).getLiteral());

    result = parse("/Container2.Photos(Id=5,Type='test%2Ccomma')");
    assertEquals("test,comma", result.getKeyPredicates().get(1).getLiteral());
  }

  /**
   * Parses the container employees entity set.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseContainerEmployeesEntitySet() throws Exception {
    UriInfoImpl result = parse("/Container1.Employees");
    assertEquals("Container1", result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
  }

  /**
   * Parses the container employees entity set parentheses.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseContainerEmployeesEntitySetParentheses() throws Exception {
    UriInfoImpl result = parse("/Container1.Employees()");
    assertEquals("Container1", result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
  }

  /**
   * Parses the container employees entity with key.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseContainerEmployeesEntityWithKey() throws Exception {
    UriInfoImpl result = parse("/Container1.Employees('1')");
    assertEquals("Container1", result.getEntityContainer().getName());
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());

    assertEquals(1, result.getKeyPredicates().size());
    assertEquals("1", result.getKeyPredicates().get(0).getLiteral());
    assertEquals("EmployeeId", result.getKeyPredicates().get(0).getProperty().getName());
  }

  /**
   * Parses the nonexistent container.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseNonexistentContainer() throws Exception {
    parseWrongUri("/somethingwrong.Employees()", UriNotMatchingException.CONTAINERNOTFOUND);
  }

  /**
   * Parses the invalid segment.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseInvalidSegment() throws Exception {
    parseWrongUri("/.somethingwrong", UriNotMatchingException.MATCHPROBLEM);
  }

  /**
   * Parses the function imports.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseFunctionImports() throws Exception {
    UriInfoImpl result = parse("EmployeeSearch");
    assertEquals("EmployeeSearch", result.getFunctionImport().getName());
    assertEquals(EdmTypeKind.ENTITY, result.getTargetType().getKind());
    assertEquals(UriType.URI10a, result.getUriType());

    result = parse("AllLocations");
    assertEquals("AllLocations", result.getFunctionImport().getName());
    assertEquals(UriType.URI11, result.getUriType());

    result = parse("AllUsedRoomIds");
    assertEquals("AllUsedRoomIds", result.getFunctionImport().getName());
    assertEquals(UriType.URI13, result.getUriType());

    result = parse("MaximalAge");
    assertEquals("MaximalAge", result.getFunctionImport().getName());
    assertEquals(UriType.URI14, result.getUriType());

    result = parse("MaximalAge/$value");
    assertEquals("MaximalAge", result.getFunctionImport().getName());
    assertTrue(result.isValue());
    assertEquals(UriType.URI14, result.getUriType());
    
    result = parse("MaximalAge/%24value");
    assertEquals("MaximalAge", result.getFunctionImport().getName());
    assertTrue(result.isValue());
    assertEquals(UriType.URI14, result.getUriType());
    
    result = parse("MostCommonLocation");
    assertEquals("MostCommonLocation", result.getFunctionImport().getName());
    assertEquals(UriType.URI12, result.getUriType());

    result = parse("ManagerPhoto?Id='1'");
    assertEquals("ManagerPhoto", result.getFunctionImport().getName());
    assertEquals(UriType.URI14, result.getUriType());

    result = parse("OldestEmployee");
    assertEquals("OldestEmployee", result.getFunctionImport().getName());
    assertEquals(UriType.URI10, result.getUriType());
    
    result = parse("SetEmployee");
    assertEquals("SetEmployee", result.getFunctionImport().getName());
    assertEquals(UriType.URI14, result.getUriType());
  }

  /**
   * Parses the function import parameters.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseFunctionImportParameters() throws Exception {
    UriInfoImpl result = parse("EmployeeSearch?q='Hugo'&notaparameter=2");
    assertEquals("EmployeeSearch", result.getFunctionImport().getName());
    assertEquals(1, result.getFunctionImportParameters().size());
    assertEquals(EdmSimpleTypeKind.String.getEdmSimpleTypeInstance(), result.getFunctionImportParameters().get("q")
        .getType());
    assertEquals("Hugo", result.getFunctionImportParameters().get("q").getLiteral());
  }
  
  /**
   * Parses the function import parameters with facets.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseFunctionImportParametersWithFacets() throws Exception {
    UriInfoImpl result = parse("FINullableParameter");
    assertEquals("FINullableParameter", result.getFunctionImport().getName());
    assertTrue(result.getFunctionImportParameters().isEmpty());
    
    result = parse("FINullableParameter?Id='A'");
    assertEquals("FINullableParameter", result.getFunctionImport().getName());
    assertFalse(result.getFunctionImportParameters().isEmpty());
    assertEquals("A", result.getFunctionImportParameters().get("Id").getLiteral());
  }

  /**
   * Parses the wrong function imports.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWrongFunctionImports() throws Exception {
    parseWrongUri("EmployeeSearch?q=42", UriSyntaxException.INCOMPATIBLELITERAL);
    parseWrongUri("AllLocations/$value", UriSyntaxException.MUSTBELASTSEGMENT);
    parseWrongUri("MaximalAge()", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("MaximalAge/somethingwrong", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("ManagerPhoto", UriSyntaxException.MISSINGPARAMETER);
    parseWrongUri("ManagerPhoto?Id='", UriSyntaxException.UNKNOWNLITERAL);
  }

  /**
   * Parses the wrong function import parameters.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWrongFunctionImportParameters() throws Exception {
    // override parameter type for testing literal parsing errors
    when(edm.getDefaultEntityContainer().getFunctionImport("ManagerPhoto").getParameter("Id").getType())
        .thenReturn(EdmSimpleTypeKind.Binary.getEdmSimpleTypeInstance());
    parseWrongUri("ManagerPhoto?Id=X'Z'", UriSyntaxException.LITERALFORMAT);
    when(edm.getDefaultEntityContainer().getFunctionImport("ManagerPhoto").getParameter("Id").getType())
        .thenReturn(EdmSimpleTypeKind.Int32.getEdmSimpleTypeInstance());
    parseWrongUri("ManagerPhoto?Id=12345678901234567890", UriSyntaxException.LITERALFORMAT);
  }

  /**
   * Parses the system query options.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptions() throws Exception {
    UriInfoImpl result = parse("Employees?$format=json&$inlinecount=allpages&$skiptoken=abc&$skip=2&$top=1");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("json", result.getFormat());
    assertEquals(InlineCount.ALLPAGES, result.getInlineCount());
    assertEquals("abc", result.getSkipToken());
    assertEquals(2, result.getSkip().intValue());
    assertEquals(1, result.getTop().intValue());
    
    result = parse("Employees?$format=json&%24inlinecount=allpages&%24skiptoken=abc&%24skip=2&$top=1");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("json", result.getFormat());
    assertEquals(InlineCount.ALLPAGES, result.getInlineCount());
    assertEquals("abc", result.getSkipToken());
    assertEquals(2, result.getSkip().intValue());
    assertEquals(1, result.getTop().intValue());
    
    result = parse("Employees?$format=atom&$inlinecount=none&$skip=0&$top=0");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("atom", result.getFormat());
    assertEquals(InlineCount.NONE, result.getInlineCount());
    assertEquals(0, result.getSkip().intValue());
    assertEquals(0, result.getTop().intValue());

    result = parse("Employees?$format=json&$inlinecount=none");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("json", result.getFormat());
    assertEquals(InlineCount.NONE, result.getInlineCount());
    assertNull(result.getSkip());
    assertNull(result.getTop());

    result = parse("Employees?$format=atom");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("atom", result.getFormat());

    result = parse("Employees?$format=xml");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("xml", result.getFormat());
    assertNull(result.getTop());

    result = parse("Employees?$format=custom/*");
    assertNotNull(result.getFormat());
    assertEquals("custom/*", result.getFormat().toString());

    result = parse("/Employees('1')/Location/Country?$format=json");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI4, result.getUriType());
    assertEquals("json", result.getFormat());

    result = parse("/Employees('1')/EmployeeName?$format=json");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI5, result.getUriType());
    assertEquals("json", result.getFormat());

    result = parse("Employees?$filter=Age%20gt%2020&$orderby=EmployeeName%20desc");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertNotNull(result.getFilter());
    assertNotNull(result.getOrderBy());
    assertEquals("EmployeeName desc", result.getOrderBy().getUriLiteral());
  }

  /**
   * Parses the wrong system query options.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWrongSystemQueryOptions() throws Exception {
    parseWrongUri("Employees??", UriSyntaxException.URISYNTAX);
    parseWrongUri("Employees?$inlinecount=no", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?&$skiptoken==", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$somethingwrong", UriSyntaxException.INVALIDSYSTEMQUERYOPTION);
    parseWrongUri("Employees?$somethingwrong=", UriSyntaxException.INVALIDSYSTEMQUERYOPTION);
    parseWrongUri("Employees?$somethingwrong=adjaodjai", UriSyntaxException.INVALIDSYSTEMQUERYOPTION);
    parseWrongUri("Employees?$formatformat=xml", UriSyntaxException.INVALIDSYSTEMQUERYOPTION);
    parseWrongUri("Employees?$Format=atom", UriSyntaxException.INVALIDSYSTEMQUERYOPTION);
    parseWrongUri("Employees?$filter=Age", UriSyntaxException.INVALIDFILTEREXPRESSION);
    parseWrongUri("Employees?$filter=(Age", UriSyntaxException.INVALIDFILTEREXPRESSION);
    parseWrongUri("Employees?$orderby=desc", UriSyntaxException.INVALIDORDERBYEXPRESSION);
  }

  /**
   * Parses the wrong redundant system query options.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWrongRedundantSystemQueryOptions() throws Exception {
    parseWrongUri("Employees?$top=1&$top=2", UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
    parseWrongUri("Employees?$top=1&$skip=1&$top=2", UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
    parseWrongUri("Employees?$top=1&$top=1", UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
    parseWrongUri("Employees?$skip=1&$skip=2", UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
    parseWrongUri("Employees?$expand=ne_Manager&$expand=ne_Manager", UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
    parseWrongUri("Employees?$orderby=Name%20desc&$orderby=Birthday%20desc",
        UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
    parseWrongUri("Employees?$select=EmployeeName&$select=EmployeeName",
        UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
    parseWrongUri("Employees?$filter=EmployeeName%20eq%20'Foo'&$filter=EmployeeName%20ne%20'Bar'",
        UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
    parseWrongUri("Employees?$inlinecount=allpages&$inlinecount=none",
        UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
    parseWrongUri("Employees?$format=xml&$format=json", UriSyntaxException.DUPLICATESYSTEMQUERYPARAMETES);
  }

  /**
   * Parses the wrong system query option skip.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWrongSystemQueryOptionSkip() throws Exception {
    parseWrongUri("Employees?$skip=-1", UriSyntaxException.INVALIDNEGATIVEVALUE);
    parseWrongUri("Employees?$skip=-0", UriSyntaxException.INVALIDNEGATIVEVALUE);
    parseWrongUri("Employees?$skip=+1", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip=+0", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip=a", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip=-a", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip=+a", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip='a'", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip=-'a'", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip=+'a'", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip=-'a'", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip='+a'", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip='-a'", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$skip=12345678901234567890", UriSyntaxException.INVALIDVALUE);
  }

  /**
   * Parses the wrong system query option top.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWrongSystemQueryOptionTop() throws Exception {
    parseWrongUri("Employees?$top=-1", UriSyntaxException.INVALIDNEGATIVEVALUE);
    parseWrongUri("Employees?$top=-0", UriSyntaxException.INVALIDNEGATIVEVALUE);
    parseWrongUri("Employees?$top=+1", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$top=+0", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$top=a", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$top=-a", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$top=+a", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$top='a'", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$top=-'a'", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$top=+'a'", UriSyntaxException.INVALIDVALUE);
    parseWrongUri("Employees?$top=12345678901234567890", UriSyntaxException.INVALIDVALUE);
  }

  /**
   * Parses the wrong system query option initial values.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWrongSystemQueryOptionInitialValues() throws Exception {
    parseWrongUri("Employees?$expand=", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$filter=", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$orderby=", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$format=", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$skip=", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$top=", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$skiptoken=", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$inlinecount=", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$select=", UriSyntaxException.INVALIDNULLVALUE);

    parseWrongUri("Employees?$expand", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$filter", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$orderby", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$format", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$skip", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$top", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$skiptoken", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$inlinecount", UriSyntaxException.INVALIDNULLVALUE);
    parseWrongUri("Employees?$select", UriSyntaxException.INVALIDNULLVALUE);
  }

  /**
   * Parses the compatible system query options.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseCompatibleSystemQueryOptions() throws Exception {
    UriInfoImpl result = parse("Employees?$format=json&$inlinecount=allpages&$skiptoken=abc&$skip=2&$top=1");
    assertEquals("Employees", result.getStartEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("json", result.getFormat());
    assertEquals(InlineCount.ALLPAGES, result.getInlineCount());
    assertEquals("abc", result.getSkipToken());
    assertEquals(2, result.getSkip().intValue());
    assertEquals(1, result.getTop().intValue());
  }

  /**
   * Parses the in compatible system query options.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseInCompatibleSystemQueryOptions() throws Exception {
    parseWrongUri("$metadata?$top=1", UriSyntaxException.INCOMPATIBLESYSTEMQUERYOPTION);
    parseWrongUri("Employees('1')?$format=json&$inlinecount=allpages&$skiptoken=abc&$skip=2&$top=1",
        UriSyntaxException.INCOMPATIBLESYSTEMQUERYOPTION);
    parseWrongUri("/Employees('1')/Location/Country/$value?$format=json",
        UriSyntaxException.INCOMPATIBLESYSTEMQUERYOPTION);
    parseWrongUri("/Employees('1')/Location/Country/$value?$skip=2", UriSyntaxException.INCOMPATIBLESYSTEMQUERYOPTION);
    parseWrongUri("/Employees('1')/EmployeeName/$value?$format=json", UriSyntaxException.INCOMPATIBLESYSTEMQUERYOPTION);
    parseWrongUri("/Employees('1')/EmployeeName/$value?$skip=2", UriSyntaxException.INCOMPATIBLESYSTEMQUERYOPTION);
  }

  /**
   * Parses the possible query options.
   *
   * @throws Exception the exception
   */
  @Test
  public void parsePossibleQueryOptions() throws Exception {
    UriInfoImpl result = parse("EmployeeSearch?q='a'&client=100&odata-debug=true");
    assertEquals(2, result.getCustomQueryOptions().size());
    assertEquals("100", result.getCustomQueryOptions().get("client"));
    assertEquals("true", result.getCustomQueryOptions().get("odata-debug"));
  }

  /**
   * Parses the system query option select single.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionSelectSingle() throws Exception {
    UriInfoImpl result = parse("Employees?$select=EmployeeName");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(1, result.getSelect().size());
    assertEquals("EmployeeName", result.getSelect().get(0).getProperty().getName());
    
    result = parse("Employees?%24select=EmployeeName");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(1, result.getSelect().size());
    assertEquals("EmployeeName", result.getSelect().get(0).getProperty().getName());
    
    result = parse("Employees?$select=*");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(1, result.getSelect().size());
    assertTrue(result.getSelect().get(0).isStar());

    result = parse("Employees?$select=Location");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(1, result.getSelect().size());
    assertEquals("Location", result.getSelect().get(0).getProperty().getName());

    result = parse("Employees?$select=ne_Manager");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(1, result.getSelect().size());
    assertEquals(1, result.getSelect().get(0).getNavigationPropertySegments().size());
    assertEquals("Managers", result.getSelect().get(0).getNavigationPropertySegments().get(0).getTargetEntitySet()
        .getName());
    assertNull(result.getSelect().get(0).getProperty());

    result = parse("Teams?$select=nt_Employees/ne_Manager/*");
    assertEquals(1, result.getSelect().size());
    assertEquals(2, result.getSelect().get(0).getNavigationPropertySegments().size());
    assertNull(result.getSelect().get(0).getProperty());
    assertTrue(result.getSelect().get(0).isStar());
  }

  /**
   * Parses the system query option select multiple.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionSelectMultiple() throws Exception {
    UriInfoImpl result = parse("Employees?$select=EmployeeName,Location");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(2, result.getSelect().size());
    assertEquals("EmployeeName", result.getSelect().get(0).getProperty().getName());
    assertEquals("Location", result.getSelect().get(1).getProperty().getName());

    result = parse("Employees?$select=%20ne_Manager,%20EmployeeName,%20Location");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(3, result.getSelect().size());
    assertEquals("EmployeeName", result.getSelect().get(1).getProperty().getName());
    assertEquals("Location", result.getSelect().get(2).getProperty().getName());
    assertEquals(1, result.getSelect().get(0).getNavigationPropertySegments().size());
    assertEquals("Managers", result.getSelect().get(0).getNavigationPropertySegments().get(0).getTargetEntitySet()
        .getName());

    result = parse("Managers('1')?$select=nm_Employees/EmployeeName,nm_Employees/Location");
    assertEquals("Managers", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());
    assertEquals(2, result.getSelect().size());
    assertEquals("EmployeeName", result.getSelect().get(0).getProperty().getName());
    assertEquals("Location", result.getSelect().get(1).getProperty().getName());
    assertEquals(1, result.getSelect().get(0).getNavigationPropertySegments().size());
    assertEquals("Employees",
        result.getSelect().get(0).getNavigationPropertySegments().get(0).getTargetEntitySet().getName());
  }

  /**
   * Parses the system query option select negative.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionSelectNegative() throws Exception {
    parseWrongUri("Employees?$select=somethingwrong", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Employees?$select=*/Somethingwrong", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Employees?$select=EmployeeName/*", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Employees?$select=,EmployeeName", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Employees?$select=EmployeeName,", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Employees?$select=EmployeeName,,Location", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Employees?$select=*EmployeeName", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Employees?$select=EmployeeName*", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Employees?$select=/EmployeeName", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Employees?$select=EmployeeName/", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Employees?$select=Location/City", UriSyntaxException.INVALIDSEGMENT);
    parseWrongUri("Teams('1')?$select=nt_Employees/Id", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Teams('1')?$select=nt_Employees//*", UriSyntaxException.EMPTYSEGMENT);
  }

  /**
   * Parses the filter with space form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseFilterWithSpaceFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=EmployeeId%20eq%20%271%27&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("EmployeeId eq '1'", result.getFilter().getUriLiteral());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }
  
  /**
   * Parses the filter with space no form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseFilterWithSpaceNoFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=EmployeeId%20eq%20%271%27");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("EmployeeId eq '1'", result.getFilter().getUriLiteral());
  }
  
  /**
   * Parses the system query option filter form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionFilterFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=EmployeeId+eq+%271%27&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("EmployeeId eq '1'", result.getFilter().getUriLiteral());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }
  
  /**
   * Parses the filter form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseFilterFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?odata-accept-forms-encoding=true&$filter=EmployeeId+eq+%271%27");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("EmployeeId eq '1'", result.getFilter().getUriLiteral());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }
  
  /**
   * Parses the system query option filter false form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionFilterFalseFormEncoding() throws Exception {
    parseWrongUri("Employees?$filter=EmployeeId+eq+%271%27&odata-accept-forms-encoding=false", 
        UriSyntaxException.INVALIDFILTEREXPRESSION);
  }
  
  /**
   * Parses the system query option filter no form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionFilterNoFormEncoding() throws Exception {
    parseWrongUri("Employees?$filter=EmployeeId+eq+%271%27", 
        UriSyntaxException.INVALIDFILTEREXPRESSION);
  }
  
  /**
   * Parses the system query option filter invalid form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionFilterInvalidFormEncoding() throws Exception {
    parseWrongUri("Employees?$filter=EmployeeId+eq+%271%27&odata-accept-forms-encoding=asdf", 
        UriSyntaxException.INVALIDFILTEREXPRESSION);
  }

  
  /**
   * Parses the system query option having plus as value filter form encoding 1.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValueFilterFormEncoding1() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=EmployeeId+eq+%27A+B%27&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("EmployeeId eq 'A+B'", result.getFilter().getUriLiteral());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }
  
  /**
   * Parses the system query option having plus as value filter form encoding 2.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValueFilterFormEncoding2() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=EmployeeId+eq+%27A+B+C%27&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("EmployeeId eq 'A+B+C'", result.getFilter().getExpressionString());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }
  
  /**
   * Parses the system query option having plus as value AND filter form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValueANDFilterFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=EmployeeId+eq+%27A+B%27+and+"
    		+ "EmployeeName+eq+%27Sam%27&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("EmployeeId eq 'A+B' and EmployeeName eq 'Sam'", result.getFilter().getExpressionString());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }
  
  /**
   * Parses the system query option having plus as valuestartswith filter form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValuestartswithFilterFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=substring(EmployeeId,1)+eq+%27A+B%27"
    		+ "&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("substring(EmployeeId,1) eq 'A+B'", result.getFilter().getExpressionString());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }
  
  /**
   * Parses the system query option having plus as valuesubstringofilter form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValuesubstringofilterFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=startswith(EmployeeId,%27A+B%27)&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("startswith(EmployeeId,'A+B')", result.getFilter().getExpressionString());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  } 
  
  /**
   * Parses the system query option having plus as valueendswith filter form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValueendswithFilterFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=endswith(EmployeeId,%27A+B%27)&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("endswith(EmployeeId,'A+B')", result.getFilter().getExpressionString());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  } 

  /**
   * Parses the system query option having plus as value OR filter form encoding 1.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValueORFilterFormEncoding1() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=EmployeeId+eq+%27A+B%27+or+"
    		+ "EmployeeName+eq+%27A%20B%27&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("EmployeeId eq 'A+B' or EmployeeName eq 'A B'", result.getFilter().getExpressionString());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }
  
  /**
   * Parses the system query option having plus as valueorderby andsubstringofilter form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValueorderbyAndsubstringofilterFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?$orderby=EmployeeId%20asc&$filter=substringof(%27GW%27,EmployeeId)"
    		+ "%20and%20substringof(%27IWBEP%27,EmployeeName)%20and%20substringof(%27%20%27,EmployeeId)"
    		+ "&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("substringof('GW',EmployeeId) and substringof('IWBEP',EmployeeName) and substringof(' ',EmployeeId)",
    		result.getFilter().getExpressionString());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }  
   
  
  /**
   * Parses the system query option having plus as value OR filter form encoding 2.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValueORFilterFormEncoding2() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=EmployeeId+eq+%27A%20B%27+or+"
    		+ "EmployeeName+eq+%27A+B%27&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("EmployeeId eq 'A B' or EmployeeName eq 'A+B'", result.getFilter().getExpressionString());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }
  
  /**
   * Parses the system query option having plus as value AN dsubsctringof filter form encoding.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionHavingPlusAsValueANDsubsctringofFilterFormEncoding() throws Exception {
    UriInfoImpl result = parse("Employees?$filter=substring(EmployeeId,1)+eq+"
    		+ "%27A+B%27+and+EmployeeId+ne+%27C+D%27&odata-accept-forms-encoding=true");
    assertEquals("Employees", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals(UriType.URI1, result.getUriType());
    assertEquals("substring(EmployeeId,1) eq 'A+B' and EmployeeId ne 'C+D'", result.getFilter().getExpressionString());
    assertNull(result.getCustomQueryOptions().get(ACCEPT_FORM_ENCODING));
  }   
    
  /**
   * Parses the system query option expand.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionExpand() throws Exception {
    UriInfoImpl result = parse("Managers('1')?$expand=nm_Employees");
    assertEquals("Managers", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());
    assertEquals(1, result.getExpand().size());
    assertEquals(1, result.getExpand().get(0).size());
    assertEquals("Employees", result.getExpand().get(0).get(0).getTargetEntitySet().getName());
    assertEquals(result.getTargetEntitySet().getEntityType().getProperty("nm_Employees"),
        result.getExpand().get(0).get(0).getNavigationProperty());
    
    result = parse("Managers('1')?%24expand=nm_Employees");
    assertEquals("Managers", result.getTargetEntitySet().getName());
    assertEquals(UriType.URI2, result.getUriType());
    assertEquals(1, result.getExpand().size());
    assertEquals(1, result.getExpand().get(0).size());
    assertEquals("Employees", result.getExpand().get(0).get(0).getTargetEntitySet().getName());
    assertEquals(result.getTargetEntitySet().getEntityType().getProperty("nm_Employees"),
        result.getExpand().get(0).get(0).getNavigationProperty());
  }

  /**
   * Parses the system query option expand wrong.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSystemQueryOptionExpandWrong() throws Exception {
    parseWrongUri("Managers('1')?$expand=,nm_Employees", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Managers('1')?$expand=nm_Employees,", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Managers('1')?$expand=nm_Employees,,", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Managers('1')?$expand=nm_Employees,,nm_Employees", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Managers('1')?$expand=nm_Employees, somethingwrong", UriSyntaxException.NOTEXT);
    parseWrongUri("Managers('1')?$expand=/nm_Employees", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Managers('1')?$expand=nm_Employees/", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Managers('1')?$expand=nm_Employees//", UriSyntaxException.EMPTYSEGMENT);
    parseWrongUri("Managers('1')?$expand=somethingwrong", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Managers('1')?$expand=nm_Employees/EmployeeName", UriSyntaxException.NONAVIGATIONPROPERTY);
    parseWrongUri("Managers('1')?$expand=nm_Employees/Location", UriSyntaxException.NONAVIGATIONPROPERTY);
    parseWrongUri("Managers('1')?$expand=nm_Employees/somethingwrong", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Managers('1')?$expand=nm_Employees/*", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Managers('1')?$expand=nm_Employees/*,somethingwrong", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Managers('1')?$expand=nm_Employees/*,some()", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Managers('1')?$expand=nm_Employees/(...)", UriNotMatchingException.PROPERTYNOTFOUND);
    parseWrongUri("Teams('1')?$expand=nt_Employees//ne_Manager", UriSyntaxException.EMPTYSEGMENT);
  }

  /**
   * Wrong get key.
   *
   * @param entitySet the entity set
   * @param link the link
   * @param serviceRoot the service root
   * @param exceptionContext the exception context
   * @throws ODataException the o data exception
   */
  private void wrongGetKey(final EdmEntitySet entitySet, final String link, final String serviceRoot,
      final MessageReference exceptionContext) throws ODataException {
    try {
      new UriParserImpl(null).getKeyFromEntityLink(entitySet, link,
          serviceRoot == null ? null : URI.create(serviceRoot));
      fail("Expected UriParserException not thrown");
    } catch (ODataMessageException e) {
      assertNotNull(e);
      assertEquals(exceptionContext.getKey(), e.getMessageReference().getKey());
    }
  }

  /**
   * Gets the key from link.
   *
   * @return the key from link
   * @throws Exception the exception
   */
  @Test
  public void getKeyFromLink() throws Exception {
    final EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Teams");
    final EdmEntitySet entitySet2 = edm.getEntityContainer("Container2").getEntitySet("Photos");
    final String serviceRoot = "http://service.com/example.svc/";
    final UriParserImpl parser = new UriParserImpl(null);

    List<KeyPredicate> key = parser.getKeyFromEntityLink(entitySet, "Teams('1')", null);
    assertEquals(1, key.size());
    assertEquals(entitySet.getEntityType().getKeyProperties().get(0), key.get(0).getProperty());
    assertEquals("1", key.get(0).getLiteral());

    key = parser.getKeyFromEntityLink(entitySet, "Teams(Id='2')", URI.create(serviceRoot));
    assertEquals("2", key.get(0).getLiteral());

    key = parser.getKeyFromEntityLink(entitySet, serviceRoot + "Teams('3')", URI.create(serviceRoot));
    assertEquals("3", key.get(0).getLiteral());

    key = parser.getKeyFromEntityLink(entitySet2, "Container2.Photos(Id=4,Type='test')", null);
    assertEquals(2, key.size());

    wrongGetKey(entitySet, "someContainer.Teams('5')", null, UriNotMatchingException.CONTAINERNOTFOUND);
    wrongGetKey(entitySet, "Employees('6')/ne_Team", null, UriNotMatchingException.MATCHPROBLEM);
    wrongGetKey(entitySet, "Teams()", null, UriSyntaxException.ENTITYSETINSTEADOFENTITY);
    wrongGetKey(entitySet, "Teams('8')/Id", null, UriNotMatchingException.MATCHPROBLEM);
    wrongGetKey(entitySet, "Rooms('9')", null, UriNotMatchingException.NOTFOUND);
    wrongGetKey(entitySet, "anotherServiceRoot/Teams('10')", serviceRoot, UriNotMatchingException.NOTFOUND);
    wrongGetKey(entitySet2, "Photos(Id=11,Type='test')", null, UriNotMatchingException.CONTAINERNOTFOUND);
    wrongGetKey(entitySet2, "anotherContainer.Photos(Id=12,Type='test')", null,
        UriNotMatchingException.CONTAINERNOTFOUND);
  }

  /**
   * Creates the path segment.
   */
  @Test
  public void createPathSegment() {
    PathSegment segment = UriParser.createPathSegment("simple", null);
    assertEquals("simple", segment.getPath());
    assertTrue(segment.getMatrixParameters().isEmpty());

    Map<String, List<String>> matrixParameter = new HashMap<String, List<String>>();
    matrixParameter.put("parameter1", Arrays.asList("one", "two"));
    PathSegment segmentWithMatrix = UriParser.createPathSegment("matrix", matrixParameter);
    assertEquals("matrix", segmentWithMatrix.getPath());
    assertEquals(1, segmentWithMatrix.getMatrixParameters().size());
    assertEquals(2, segmentWithMatrix.getMatrixParameters().get("parameter1").size());
    assertTrue(segmentWithMatrix.getMatrixParameters().get("parameter1").contains("one"));
    assertTrue(segmentWithMatrix.getMatrixParameters().get("parameter1").contains("two"));
  }
}
