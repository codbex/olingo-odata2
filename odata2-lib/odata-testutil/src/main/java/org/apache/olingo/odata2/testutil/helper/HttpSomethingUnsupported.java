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
package org.apache.olingo.odata2.testutil.helper;

import java.net.URI;

import org.apache.http.client.methods.HttpRequestBase;

// TODO: Auto-generated Javadoc
/**
 * The Class HttpSomethingUnsupported.
 */
public class HttpSomethingUnsupported extends HttpRequestBase {

  /** The Constant METHOD_NAME. */
  public final static String METHOD_NAME = "SOMETHING_UNSUPPORTED";

  /**
   * Instantiates a new http something unsupported.
   */
  public HttpSomethingUnsupported() {
    super();
  }

  /**
   * Instantiates a new http something unsupported.
   *
   * @param uri the uri
   */
  public HttpSomethingUnsupported(final URI uri) {
    super();
    setURI(uri);
  }

  /**
   * Instantiates a new http something unsupported.
   *
   * @param uri the uri
   * @throws IllegalArgumentException if the uri is invalid.
   */
  public HttpSomethingUnsupported(final String uri) {
    super();
    setURI(URI.create(uri));
  }

  /**
   * Gets the method.
   *
   * @return the method
   */
  @Override
  public String getMethod() {
    return METHOD_NAME;
  }

}
