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
package org.apache.olingo.odata2.client.core.uri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.UriNotMatchingException;
import org.apache.olingo.odata2.api.uri.UriSyntaxException;
import org.apache.olingo.odata2.client.api.ODataClient;
import org.apache.olingo.odata2.core.ODataPathSegmentImpl;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class UriInfoTest.
 */
public class UriInfoTest {

  /** The edm. */
  private static Edm edm;
  
  /**
   * Gets the edm.
   *
   * @return the edm
   * @throws ODataException the o data exception
   * @throws EdmException the edm exception
   */
  @BeforeClass
  public static void getEdm() throws ODataException, EdmException {
    edm = MockFacade.getMockEdm();
  }
  
  /**
   * Everything initial.
   *
   * @throws Exception the exception
   */
  @Test
  public void everythingInitial() throws Exception {
    UriInfoImpl result = parse("/");

    assertEquals(Collections.emptyList(), result.getKeyPredicates());
    assertEquals(Collections.emptyList(), result.getTargetKeyPredicates());
    assertEquals(Collections.emptyList(), result.getNavigationSegments());
    assertEquals(Collections.emptyList(), result.getPropertyPath());
    assertEquals(Collections.emptyList(), result.getExpand());
    assertEquals(Collections.emptyList(), result.getSelect());
    assertEquals(Collections.emptyMap(), result.getFunctionImportParameters());
    assertEquals(Collections.emptyMap(), result.getCustomQueryOptions());
    assertNull(result.getEntityContainer());
    assertNull(result.getStartEntitySet());
    assertNull(result.getTargetEntitySet());
    assertNull(result.getFunctionImport());
    assertNull(result.getTargetType());
    assertNull(result.getFormat());
    assertNull(result.getFilter());
    assertNull(result.getInlineCount());
    assertNull(result.getOrderBy());
    assertNull(result.getSkipToken());
    assertNull(result.getSkip());
    assertNull(result.getTop());
  }

  /**
   * All initial.
   *
   * @throws Exception the exception
   */
  @Test
  public void allInitial() throws Exception {
    UriInfoImpl result = parse("/Employees");

    assertEquals(Collections.emptyList(), result.getKeyPredicates());
    assertEquals(Collections.emptyList(), result.getTargetKeyPredicates());
    assertEquals(Collections.emptyList(), result.getNavigationSegments());
    assertEquals(Collections.emptyList(), result.getPropertyPath());
    assertEquals(Collections.emptyList(), result.getExpand());
    assertEquals(Collections.emptyList(), result.getSelect());
    assertEquals(Collections.emptyMap(), result.getFunctionImportParameters());
    assertEquals(Collections.emptyMap(), result.getCustomQueryOptions());
  }

  /**
   * Some initial.
   *
   * @throws Exception the exception
   */
  @Test
  public void someInitial() throws Exception {
    UriInfoImpl result = parse("/Employees('1')");

    assertNotSame(Collections.emptyList(), result.getKeyPredicates());
    assertNotSame(Collections.emptyList(), result.getTargetKeyPredicates());

    assertEquals(Collections.emptyList(), result.getNavigationSegments());
    assertEquals(Collections.emptyList(), result.getPropertyPath());
    assertEquals(Collections.emptyList(), result.getExpand());
    assertEquals(Collections.emptyList(), result.getSelect());
    assertEquals(Collections.emptyMap(), result.getFunctionImportParameters());
    assertEquals(Collections.emptyMap(), result.getCustomQueryOptions());
  }

  /**
   * Some initial 2.
   *
   * @throws Exception the exception
   */
  @Test
  public void someInitial2() throws Exception {
    UriInfoImpl result = parse("/Employees('1')/ne_Manager");

    assertNotSame(Collections.emptyList(), result.getKeyPredicates());
    assertNotSame(Collections.emptyList(), result.getNavigationSegments());

    assertEquals(Collections.emptyList(), result.getTargetKeyPredicates());
    assertEquals(Collections.emptyList(), result.getPropertyPath());
    assertEquals(Collections.emptyList(), result.getExpand());
    assertEquals(Collections.emptyList(), result.getSelect());
    assertEquals(Collections.emptyMap(), result.getFunctionImportParameters());
    assertEquals(Collections.emptyMap(), result.getCustomQueryOptions());
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

    final List<PathSegment> pathSegments = getPathSegments(path[0]);
    Map<String, List<String>> queryParameters;
    if (path.length == 2) {
      queryParameters = getQueryParameters(unescape(path[1]));
    } else {
      queryParameters = new HashMap<String, List<String>>();
    }

    UriInfo result = ODataClient.newInstance().parseUri(edm, pathSegments, queryParameters);

    return (UriInfoImpl) result;
  }
  
  /**
   * Gets the path segments.
   *
   * @param uri the uri
   * @return the path segments
   * @throws UriSyntaxException the uri syntax exception
   */
  private List<PathSegment> getPathSegments(final String uri) throws UriSyntaxException {
    List<PathSegment> pathSegments = new ArrayList<PathSegment>();
    for (final String segment : uri.split("/", -1)) {
      final String unescapedSegment = unescape(segment);
      PathSegment oDataSegment = new ODataPathSegmentImpl(unescapedSegment, null);
      pathSegments.add(oDataSegment);
    }
    return pathSegments;
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

}
