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
package org.apache.olingo.odata2.core.rest;

import java.net.URI;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.Response;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataRedirectLocator.
 */
public class ODataRedirectLocator {

  /**
   * Redirect get.
   *
   * @return the response
   */
  @GET
  public Response redirectGet() {
    return redirect();
  }

  /**
   * Redirect put.
   *
   * @return the response
   */
  @PUT
  public Response redirectPut() {
    return redirect();
  }

  /**
   * Redirect post.
   *
   * @return the response
   */
  @POST
  public Response redirectPost() {
    return redirect();
  }

  /**
   * Redirect delete.
   *
   * @return the response
   */
  @DELETE
  public Response redirectDelete() {
    return redirect();
  }

  /**
   * Redirect options.
   *
   * @return the response
   */
  @OPTIONS
  public Response redirectOptions() {
    return redirect();
  }

  /**
   * Redirect head.
   *
   * @return the response
   */
  @HEAD
  public Response redirectHead() {
    return redirect();
  }

  /**
   * Redirect patch.
   *
   * @return the response
   */
  @PATCH
  public Response redirectPatch() {
    return redirect();
  }

  /**
   * Redirect merge.
   *
   * @return the response
   */
  @MERGE
  public Response redirectMerge() {
    return redirect();
  }

  /**
   * Redirect.
   *
   * @return the response
   */
  private Response redirect() {
    return Response.temporaryRedirect(URI.create("/")).build();
  }

}
