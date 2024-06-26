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
package org.apache.olingo.odata2.api.uri;

import java.net.URI;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Object to keep OData URI information.
 * @org.apache.olingo.odata2.DoNotImplement
 * 
 */
public interface PathInfo {

  /**
   * Gets preceding path segments.
   * @return List of path segments
   */
  List<PathSegment> getPrecedingSegments();

  /**
   * Gets OData path segments as immutable list.
   * @return List of path segments
   */
  List<PathSegment> getODataSegments();

  /**
   * Gets the root URI of this service. This includes any segments which can be found in the preceding segments list.
   * @return absolute base URI of the request
   */
  URI getServiceRoot();

  /**
   * Get the absolute request URI including any query parameters.
   * @return the absolute request URI
   */
  URI getRequestUri();

}
