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
package org.apache.olingo.odata2.jpa.processor.api.access;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

// TODO: Auto-generated Javadoc
/**
 * The interface provides methods for processing OData Requests for Create, Read, Update, Delete operations.
 * Pass the OData request or parsed OData request (Map of properties) as request.
 * A JPA entity is returned as a response.
 * 
 */
public interface JPAProcessor {
  
  /**
   * Processes OData request for querying an Entity Set. The method returns
   * list of Objects of type representing JPA Entity Types.
   *
   * @param <T> Template parameter representing Java Persistence Entity Type.
   * <p>
   * <b>Note:-</b> Default parameter is Object.
   * </p>
   * @param requestView is an OData request for querying an entity set
   * <p>
   * @return list of objects representing JPA entity types
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public <T> List<T> process(GetEntitySetUriInfo requestView)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Processes OData request for reading an Entity. The method returns an
   * Object of type representing JPA Entity Type.
   *
   * @param <T> Template parameter representing Java Persistence Entity Type.
   * <p>
   * <b>Note:-</b> Default parameter is Object.
   * </p>
   * @param requestView OData request for reading an entity
   * 
   * <p>
   * @return object representing JPA entity type
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public <T> Object process(GetEntityUriInfo requestView)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Processes OData request for fetching Entity count. The method returns JPA Entity count
   *
   * @param requestView OData request for counting an entity set
   * @return long value representing count of JPA entity set
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */

  public long process(GetEntitySetCountUriInfo requestView)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Processes OData request for fetching Entity count. The method returns count of target entity.
   * This is specific to situation where cardinality is 1:1
   *
   * @param resultsView OData request for counting target entity.
   * @return long value representing count of JPA entity
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public long process(GetEntityCountUriInfo resultsView)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Processes OData request for executing custom operations. The method
   * returns a List of Object. The list contains one entry if the the custom
   * operations return type has multiplicity of ONE.
   *
   * @param requestView OData request for executing function import
   * @return result of executing function import
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public List<Object> process(GetFunctionImportUriInfo requestView)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Processes OData request for executing $links OData command for N:1 relation.
   * The method returns an Object of type representing OData entity.
   *
   * @param uriParserResultView OData request for Entity Link URI
   * @return an object representing JPA entity
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public Object process(GetEntityLinkUriInfo uriParserResultView)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Processes OData request for executing $links OData command for N:1 relation.
   * The method returns an Object of type representing OData entity.
   *
   * @param <T> the generic type
   * @param uriParserResultView OData request for Entity Set Link URI
   * @return a list of object representing JPA entities
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public <T> List<T> process(GetEntitySetLinksUriInfo uriParserResultView)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Processes OData request for creating Entity. The method returns an Object
   * which is created. A Null reference implies object was not created.
   *
   * @param createView the create view
   * @param content the content
   * @param requestContentType the request content type
   * @return Created Object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */

  public Object process(PostUriInfo createView, InputStream content,
      String requestContentType) throws ODataJPAModelException,
      ODataJPARuntimeException;

  /**
   * Processes OData request for creating Entity. The method expects a parsed OData request which is a Map of
   * properties.
   * The method returns an Object that is created. A Null reference implies object was not created.
   *
   * @param createView the create view
   * @param content the content
   * @return Created Object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */

  public Object process(PostUriInfo createView, Map<String, Object> content) throws ODataJPAModelException,
      ODataJPARuntimeException;

  /**
   * Processes OData request for updating Entity. The method returns an Object
   * which is updated. A Null reference implies object was not created.
   *
   * @param updateView the update view
   * @param content the content
   * @param requestContentType the request content type
   * @return Deleted Object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public Object process(PutMergePatchUriInfo updateView,
      InputStream content, String requestContentType)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Processes OData request for updating Entity. The method returns an Object
   * which is updated. A Null reference implies object was not created.
   *
   * @param updateView the update view
   * @param content the content
   * @return Deleted Object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public Object process(PutMergePatchUriInfo updateView, Map<String, Object> content)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Processes OData request for deleting Entity. The method returns an Object
   * which is deleted. A Null reference implies object was not created.
   *
   * @param deleteuriInfo the deleteuri info
   * @param contentType the content type
   * @return Deleted Object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public Object process(DeleteUriInfo deleteuriInfo, String contentType)
      throws ODataJPAModelException, ODataJPARuntimeException;

  /**
   * Process OData request for creating Links. The OData request should contain
   * $links OData command.
   *
   * @param uriParserResultView OData request for creating Links
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws ODataJPAModelException the o data JPA model exception
   */
  public void process(PostUriInfo uriParserResultView,
      InputStream content, String requestContentType, String contentType)
      throws ODataJPARuntimeException, ODataJPAModelException;

  /**
   * Process OData request for updating Links. The OData request should contain
   * $links OData command.
   *
   * @param uriParserResultView OData request for updating Links
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws ODataJPAModelException the o data JPA model exception
   */
  public void process(PutMergePatchUriInfo uriParserResultView,
      InputStream content, String requestContentType, String contentType)
      throws ODataJPARuntimeException, ODataJPAModelException;
}
