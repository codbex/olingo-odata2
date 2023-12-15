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
package org.apache.olingo.odata2.testutil.mock;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.mockito.Mockito;

// TODO: Auto-generated Javadoc
/**
 * Mocked Entity Data Model, more or less aligned to the Reference Scenario.
 * 
 */
public class MockFacade {

  /**
   * Gets the mock edm.
   *
   * @return the mock edm
   * @throws ODataException the o data exception
   */
  public static Edm getMockEdm() throws ODataException {
    return EdmMock.createMockEdm();
  }

  /**
   * Gets the path segments as O data path segment mock.
   *
   * @param segments the segments
   * @return the path segments as O data path segment mock
   */
  public static List<PathSegment> getPathSegmentsAsODataPathSegmentMock(final List<String> segments) {
    final List<PathSegment> pathSegmentsMock = new ArrayList<PathSegment>();
    for (final String segment : segments) {
      PathSegment pathSegment = Mockito.mock(PathSegment.class);
      Mockito.when(pathSegment.getPath()).thenReturn(segment);
      pathSegmentsMock.add(pathSegment);
    }
    return pathSegmentsMock;
  }
}
