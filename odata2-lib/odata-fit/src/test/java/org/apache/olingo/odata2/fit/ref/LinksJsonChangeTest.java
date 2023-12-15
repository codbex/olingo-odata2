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
package org.apache.olingo.odata2.fit.ref;

import static org.junit.Assert.assertEquals;

import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * Tests employing the reference scenario changing links in JSON format.
 * 
 */
public final class LinksJsonChangeTest extends AbstractRefTest {

  /**
   * Instantiates a new links json change test.
   *
   * @param servletType the servlet type
   */
  public LinksJsonChangeTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * Creates the link.
   *
   * @throws Exception the exception
   */
  @Test
  public void createLink() throws Exception {
    final String uriString = "Rooms('3')/$links/nr_Employees";
    final String requestBody = "{\"uri\":\"" + getEndpoint() + "Employees('6')\"}";
    postUri(uriString, requestBody, HttpContentType.APPLICATION_JSON, HttpStatusCodes.NO_CONTENT);
    assertEquals("{\"d\":" + requestBody + "}", getBody(callUri(uriString + "('6')?$format=json")));
  }

  /**
   * Update link.
   *
   * @throws Exception the exception
   */
  @Test
  public void updateLink() throws Exception {
    final String uriString = "Employees('2')/$links/ne_Room";
    final String requestBody = "{\"uri\":\"" + getEndpoint() + "Rooms('3')\"}";
    putUri(uriString, requestBody, HttpContentType.APPLICATION_JSON, HttpStatusCodes.NO_CONTENT);
    assertEquals("{\"d\":" + requestBody + "}", getBody(callUri(uriString + "?$format=json")));
  }
}
