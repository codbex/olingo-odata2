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

import org.apache.http.HttpResponse;
import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * Tests employing the reference scenario reading properties in JSON format.
 * 
 */
public class PropertyJsonReadOnlyTest extends AbstractRefTest {
  
  /**
   * Instantiates a new property json read only test.
   *
   * @param servletType the servlet type
   */
  public PropertyJsonReadOnlyTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * Simple property.
   *
   * @throws Exception the exception
   */
  @Test
  public void simpleProperty() throws Exception {
    HttpResponse response = callUri("Employees('2')/Age?$format=json");
    checkMediaType(response, HttpContentType.APPLICATION_JSON);
    assertEquals("{\"d\":{\"Age\":" + EMPLOYEE_2_AGE + "}}", getBody(response));

    response = callUri("Employees('2')/EntryDate?$format=json");
    assertEquals("{\"d\":{\"EntryDate\":\"\\/Date(1057017600000)\\/\"}}", getBody(response));

    response = callUri("Container2.Photos(Id=3,Type='image%2Fjpeg')/BinaryData?$format=json");
    assertEquals("{\"d\":{\"BinaryData\":null}}", getBody(response));

    response = callUri("Container2.Photos(Id=3,Type='image%2Fjpeg')/Image?$format=json");
    assertEquals("{\"d\":{\"Image\":\"" + PHOTO_DEFAULT_IMAGE + "\"}}", getBody(response));
  }

  /**
   * Complex property.
   *
   * @throws Exception the exception
   */
  @Test
  public void complexProperty() throws Exception {
    HttpResponse response = callUri("Employees('2')/Location/City?$format=json");
    checkMediaType(response, HttpContentType.APPLICATION_JSON);
    assertEquals("{\"d\":{\"City\":{\"__metadata\":{\"type\":\"RefScenario.c_City\"},"
        + "\"PostalCode\":\"69190\",\"CityName\":\"" + CITY_2_NAME + "\"}}}",
        getBody(response));
  }
}
