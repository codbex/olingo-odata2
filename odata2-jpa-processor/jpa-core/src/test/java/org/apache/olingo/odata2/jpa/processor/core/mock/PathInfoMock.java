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
package org.apache.olingo.odata2.jpa.processor.core.mock;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.easymock.EasyMock;

// TODO: Auto-generated Javadoc
/**
 * The Class PathInfoMock.
 */
public class PathInfoMock {

  /** The path segments. */
  private List<PathSegment> pathSegments;
  
  /** The uri. */
  private URI uri;

  /**
   * Sets the path segments.
   *
   * @param pathSegments the new path segments
   */
  public void setPathSegments(final List<PathSegment> pathSegments) {
    this.pathSegments = pathSegments;
  }

  /**
   * Sets the service root URI.
   *
   * @param uriString the new service root URI
   * @throws URISyntaxException the URI syntax exception
   */
  public void setServiceRootURI(final String uriString) throws URISyntaxException {
    uri = new URI(uriString);
  }

  /**
   * Mock.
   *
   * @return the path info
   */
  public PathInfo mock() {
    PathInfo pathInfo = EasyMock.createMock(PathInfo.class);
    EasyMock.expect(pathInfo.getODataSegments()).andReturn(pathSegments).anyTimes();
    EasyMock.expect(pathInfo.getServiceRoot()).andReturn(uri).anyTimes();

    EasyMock.replay(pathInfo);
    return pathInfo;

  }
}
