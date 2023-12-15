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
package org.apache.olingo.odata2.testutil.fit;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.testutil.server.TestServer;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating FitStaticService objects.
 */
public class FitStaticServiceFactory extends ODataServiceFactory {

  /**
   * Gets the callback.
   *
   * @param <T> the generic type
   * @param callbackInterface the callback interface
   * @return the callback
   */
  @SuppressWarnings("unchecked")
  @Override
  public <T extends ODataCallback> T getCallback(final Class<T> callbackInterface) {
    if (callbackInterface.isAssignableFrom(FitErrorCallback.class)) {
      return (T) new FitErrorCallback();
    }

    return super.getCallback(callbackInterface);
  }

  /** The port 2 service. */
  private static Map<String, ODataService> PORT_2_SERVICE = Collections
      .synchronizedMap(new HashMap<String, ODataService>());

  /**
   * Bind service.
   *
   * @param key the key
   * @param service the service
   */
  public static void bindService(final String key, final ODataService service) {
    PORT_2_SERVICE.put(key, service);
  }

  /**
   * Unbind service.
   *
   * @param key the key
   */
  public static void unbindService(final String key) {
    PORT_2_SERVICE.remove(key);
  }

  /**
   * Bind service.
   *
   * @param server the server
   * @param service the service
   */
  public static void bindService(final TestServer server, final ODataService service) {
    PORT_2_SERVICE.put(createId(server), service);
  }

  /**
   * Unbind service.
   *
   * @param server the server
   */
  public static void unbindService(final TestServer server) {
    PORT_2_SERVICE.remove(createId(server));
  }

  /**
   * Creates a new FitStaticService object.
   *
   * @param ctx the ctx
   * @return the o data service
   * @throws ODataException the o data exception
   */
  @Override
  public ODataService createService(final ODataContext ctx) throws ODataException {

    assertNotNull(ctx);
    assertNotNull(ctx.getAcceptableLanguages());
    assertNotNull(ctx.getParameter(ODataContext.HTTP_SERVLET_REQUEST_OBJECT));

    final Map<String, List<String>> requestHeaders = ctx.getRequestHeaders();
    final String host = requestHeaders.get("Host").get(0);

    String tmp[] = host.split(":", 2);
    String port = (tmp.length == 2 && tmp[1] != null) ? tmp[1] : "80";

    // access and validation in synchronized block
    synchronized (PORT_2_SERVICE) {
      final ODataService service = PORT_2_SERVICE.get(port);
//      if (service == null) {
//        throw new IllegalArgumentException("no static service set for JUnit test");
//      }
      return service;
    }
  }

  /**
   * Creates a new FitStaticService object.
   *
   * @param server the server
   * @return the string
   */
  private static String createId(final TestServer server) {
    final URI endpoint = server.getEndpoint();
    if (endpoint == null) {
      throw new IllegalArgumentException("Got TestServer without endpoint.");
    }
    return "" + endpoint.getPort();
  }
}
