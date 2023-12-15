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

import java.util.Arrays;
import java.util.Collections;

import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class UriParserFacadeTest.
 */
public class UriParserFacadeTest extends BaseTest {

  /**
   * Parses the with facade.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseWithFacade() throws Exception {
    UriParser.parse(MockFacade.getMockEdm(),
        MockFacade.getPathSegmentsAsODataPathSegmentMock(Arrays.asList("$metadata")),
        Collections.<String, String> emptyMap());
  }

}
