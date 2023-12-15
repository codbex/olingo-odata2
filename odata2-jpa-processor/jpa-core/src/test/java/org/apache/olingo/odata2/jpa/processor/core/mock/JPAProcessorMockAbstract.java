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
import org.apache.olingo.odata2.jpa.processor.api.access.JPAProcessor;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAProcessorMockAbstract.
 */
public abstract class JPAProcessorMockAbstract implements JPAProcessor {

  /**
   * Process.
   *
   * @param <T> the generic type
   * @param requestView the request view
   * @return the list
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public <T> List<T> process(GetEntitySetUriInfo requestView) throws ODataJPAModelException, ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param <T> the generic type
   * @param requestView the request view
   * @return the object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public <T> Object process(GetEntityUriInfo requestView) throws ODataJPAModelException, ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param requestView the request view
   * @return the long
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public long process(GetEntitySetCountUriInfo requestView) throws ODataJPAModelException, ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Process.
   *
   * @param resultsView the results view
   * @return the long
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public long process(GetEntityCountUriInfo resultsView) throws ODataJPAModelException, ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Process.
   *
   * @param requestView the request view
   * @return the list
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public List<Object> process(GetFunctionImportUriInfo requestView) throws ODataJPAModelException,
      ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param uriParserResultView the uri parser result view
   * @return the object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public Object process(GetEntityLinkUriInfo uriParserResultView) throws ODataJPAModelException,
      ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param <T> the generic type
   * @param uriParserResultView the uri parser result view
   * @return the list
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public <T> List<T> process(GetEntitySetLinksUriInfo uriParserResultView) throws ODataJPAModelException,
      ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param createView the create view
   * @param content the content
   * @param requestContentType the request content type
   * @return the object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public Object process(PostUriInfo createView, InputStream content, String requestContentType)
      throws ODataJPAModelException, ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param createView the create view
   * @param content the content
   * @return the object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public Object process(PostUriInfo createView, Map<String, Object> content) throws ODataJPAModelException,
      ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param updateView the update view
   * @param content the content
   * @param requestContentType the request content type
   * @return the object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public Object process(PutMergePatchUriInfo updateView, InputStream content, String requestContentType)
      throws ODataJPAModelException, ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param updateView the update view
   * @param content the content
   * @return the object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public Object process(PutMergePatchUriInfo updateView, Map<String, Object> content) throws ODataJPAModelException,
      ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param deleteuriInfo the deleteuri info
   * @param contentType the content type
   * @return the object
   * @throws ODataJPAModelException the o data JPA model exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  @Override
  public Object process(DeleteUriInfo deleteuriInfo, String contentType) throws ODataJPAModelException,
      ODataJPARuntimeException {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Process.
   *
   * @param uriParserResultView the uri parser result view
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws ODataJPAModelException the o data JPA model exception
   */
  @Override
  public void process(PostUriInfo uriParserResultView, InputStream content, String requestContentType,
      String contentType) throws ODataJPARuntimeException, ODataJPAModelException {
    // TODO Auto-generated method stub

  }

  /**
   * Process.
   *
   * @param uriParserResultView the uri parser result view
   * @param content the content
   * @param requestContentType the request content type
   * @param contentType the content type
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws ODataJPAModelException the o data JPA model exception
   */
  @Override
  public void process(PutMergePatchUriInfo uriParserResultView, InputStream content, String requestContentType,
      String contentType) throws ODataJPARuntimeException, ODataJPAModelException {
    // TODO Auto-generated method stub

  }

}
