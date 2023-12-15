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

import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Ignore;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractRefJsonTest.
 */
@Ignore("no test methods")
public class AbstractRefJsonTest extends AbstractRefTest {

  /**
   * Instantiates a new abstract ref json test.
   *
   * @param servletType the servlet type
   */
  public AbstractRefJsonTest(final ServletType servletType) {
    super(servletType);
  }

  /**
   * Gets the linked tree map.
   *
   * @param body the body
   * @return the linked tree map
   */
  public LinkedTreeMap<?,?> getLinkedTreeMap(final String body) {
    Gson gson = new Gson();
    final LinkedTreeMap<?,?> map = gson.fromJson(body, new TypeToken<LinkedTreeMap<?,?>>() {}.getType());
    if (map.get("d") instanceof LinkedTreeMap<?,?>) {
      return (LinkedTreeMap<?,?>) map.get("d");
    } else {
      return map;
    }
  }
}
