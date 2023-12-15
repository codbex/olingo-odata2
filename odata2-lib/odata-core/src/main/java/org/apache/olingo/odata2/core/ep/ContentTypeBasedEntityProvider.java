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
package org.apache.olingo.odata2.core.ep;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.servicedocument.ServiceDocument;

// TODO: Auto-generated Javadoc
/**
 * Interface for all none basic (content type <b>dependent</b>) provider methods.
 * 
 * 
 */
public interface ContentTypeBasedEntityProvider {

  /**
   * Read feed.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data feed
   * @throws EntityProviderException the entity provider exception
   */
  ODataFeed readFeed(EdmEntitySet entitySet, InputStream content, EntityProviderReadProperties properties)
      throws EntityProviderException;

  /**
   * Read entry.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data entry
   * @throws EntityProviderException the entity provider exception
   */
  ODataEntry readEntry(EdmEntitySet entitySet, InputStream content, EntityProviderReadProperties properties)
      throws EntityProviderException;

  /**
   * Read property.
   *
   * @param edmProperty the edm property
   * @param content the content
   * @param properties the properties
   * @return the map
   * @throws EntityProviderException the entity provider exception
   */
  Map<String, Object>
      readProperty(EdmProperty edmProperty, InputStream content, EntityProviderReadProperties properties)
          throws EntityProviderException;

  /**
   * Read link.
   *
   * @param entitySet the entity set
   * @param content the content
   * @return the string
   * @throws EntityProviderException the entity provider exception
   */
  String readLink(EdmEntitySet entitySet, InputStream content) throws EntityProviderException;

  /**
   * Read links.
   *
   * @param entitySet the entity set
   * @param content the content
   * @return the list
   * @throws EntityProviderException the entity provider exception
   */
  List<String> readLinks(EdmEntitySet entitySet, InputStream content) throws EntityProviderException;

  /**
   * Write service document.
   *
   * @param edm the edm
   * @param serviceRoot the service root
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  ODataResponse writeServiceDocument(Edm edm, String serviceRoot) throws EntityProviderException;

  /**
   * Write feed.
   *
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  ODataResponse writeFeed(EdmEntitySet entitySet, List<Map<String, Object>> data,
      EntityProviderWriteProperties properties) throws EntityProviderException;

  /**
   * Write entry.
   *
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  ODataResponse writeEntry(EdmEntitySet entitySet, Map<String, Object> data, EntityProviderWriteProperties properties)
      throws EntityProviderException;

  /**
   * Write property.
   *
   * @param edmProperty the edm property
   * @param value the value
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  ODataResponse writeProperty(EdmProperty edmProperty, Object value) throws EntityProviderException;

  /**
   * Write link.
   *
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  ODataResponse writeLink(EdmEntitySet entitySet, Map<String, Object> data, EntityProviderWriteProperties properties)
      throws EntityProviderException;

  /**
   * Write links.
   *
   * @param entitySet the entity set
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  ODataResponse writeLinks(EdmEntitySet entitySet, List<Map<String, Object>> data,
      EntityProviderWriteProperties properties) throws EntityProviderException;

  /**
   * Write function import.
   *
   * @param functionImport the function import
   * @param data the data
   * @param properties the properties
   * @return the o data response
   * @throws EntityProviderException the entity provider exception
   */
  ODataResponse writeFunctionImport(EdmFunctionImport functionImport, Object data,
      EntityProviderWriteProperties properties) throws EntityProviderException;

  /**
   * Write error document.
   *
   * @param status the status
   * @param errorCode the error code
   * @param message the message
   * @param locale the locale
   * @param innerError the inner error
   * @return the o data response
   */
  ODataResponse writeErrorDocument(HttpStatusCodes status, String errorCode, String message, Locale locale,
      String innerError);

  /**
   * Write error document.
   *
   * @param context the context
   * @return the o data response
   */
  ODataResponse writeErrorDocument(ODataErrorContext context);

  /**
   * Read service document.
   *
   * @param serviceDocument the service document
   * @return the service document
   * @throws EntityProviderException the entity provider exception
   */
  ServiceDocument readServiceDocument(InputStream serviceDocument) throws EntityProviderException;

  /**
   * Read delta feed.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data delta feed
   * @throws EntityProviderException the entity provider exception
   */
  ODataDeltaFeed readDeltaFeed(EdmEntitySet entitySet, InputStream content, EntityProviderReadProperties properties)
      throws EntityProviderException;

  /**
   * Read error document.
   *
   * @param errorDocument the error document
   * @return the o data error context
   * @throws EntityProviderException the entity provider exception
   */
  ODataErrorContext readErrorDocument(InputStream errorDocument) throws EntityProviderException;

  /**
   * Read function import.
   *
   * @param functionImport the function import
   * @param content the content
   * @param properties the properties
   * @return the object
   * @throws EntityProviderException the entity provider exception
   */
  Object readFunctionImport(EdmFunctionImport functionImport, InputStream content,
      EntityProviderReadProperties properties) throws EntityProviderException;
}
