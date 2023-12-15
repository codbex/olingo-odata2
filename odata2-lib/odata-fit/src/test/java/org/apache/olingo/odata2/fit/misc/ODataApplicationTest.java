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
package org.apache.olingo.odata2.fit.misc;

import static org.junit.Assert.assertNotNull;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataApplicationTest.
 */
public class ODataApplicationTest {

  /** The Constant endpoint. */
  private static final URI endpoint = URI.create("http://localhost:19080/osgi");
  
  /** The server. */
  private Server server;

  /**
   * Before.
   *
   * @throws Exception the exception
   */
  @Before
  public void before() throws Exception {
    final ServletContextHandler contextHandler = createContextHandler();
    final InetSocketAddress isa = new InetSocketAddress(endpoint.getHost(), endpoint.getPort());
    server = new Server(isa);

    server.setHandler(contextHandler);
    server.start();
  }

  /**
   * After.
   *
   * @throws Exception the exception
   */
  @After
  public void after() throws Exception {
    if (server != null) {
      server.stop();
    }
  }

  /**
   * Run.
   *
   * @throws Exception the exception
   */
  @Test
  public void run() throws Exception {
    URL url = new URL(endpoint + "/$metadata");
    URLConnection con = url.openConnection();
    con.addRequestProperty("accept", "*/*");
    Object content = con.getContent();
    assertNotNull(content);

  }

  /**
   * Creates the context handler.
   *
   * @return the servlet context handler
   */
  private ServletContextHandler createContextHandler() {
    final CXFNonSpringJaxrsServlet odataServlet = new CXFNonSpringJaxrsServlet();
    final ServletHolder odataServletHolder = new ServletHolder(odataServlet);
    odataServletHolder.setInitParameter("jakarta.ws.rs.Application",
        ODataApplication_.class.getName());

    final ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
    contextHandler.addServlet(odataServletHolder, endpoint.getPath() + "/*");
    return contextHandler;
  }

}
