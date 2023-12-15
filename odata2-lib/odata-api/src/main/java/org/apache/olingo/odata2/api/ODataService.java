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
package org.apache.olingo.odata2.api;

import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataProcessor;
import org.apache.olingo.odata2.api.processor.part.BatchProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityComplexPropertyProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityLinkProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityLinksProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityMediaProcessor;
import org.apache.olingo.odata2.api.processor.part.EntityProcessor;
import org.apache.olingo.odata2.api.processor.part.EntitySetProcessor;
import org.apache.olingo.odata2.api.processor.part.EntitySimplePropertyProcessor;
import org.apache.olingo.odata2.api.processor.part.EntitySimplePropertyValueProcessor;
import org.apache.olingo.odata2.api.processor.part.FunctionImportProcessor;
import org.apache.olingo.odata2.api.processor.part.FunctionImportValueProcessor;
import org.apache.olingo.odata2.api.processor.part.MetadataProcessor;
import org.apache.olingo.odata2.api.processor.part.ServiceDocumentProcessor;

// TODO: Auto-generated Javadoc
/**
 * Root interface for a custom OData service.
 * 
 * 
 * 
 */
public interface ODataService {

  /**
   * Gets the version.
   *
   * @return implemented OData version of this service
   * @throws ODataException the o data exception
   * @see ODataServiceVersion
   */
  String getVersion() throws ODataException;

  /**
   * Gets the entity data model.
   *
   * @return entity data model of this service
   * @throws ODataException the o data exception
   * @see Edm
   */
  Edm getEntityDataModel() throws ODataException;

  /**
   * Gets the metadata processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see MetadataProcessor
   */
  MetadataProcessor getMetadataProcessor() throws ODataException;

  /**
   * Gets the service document processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see ServiceDocumentProcessor
   */
  ServiceDocumentProcessor getServiceDocumentProcessor() throws ODataException;

  /**
   * Gets the entity processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see EntityProcessor
   */
  EntityProcessor getEntityProcessor() throws ODataException;

  /**
   * Gets the entity set processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see EntitySetProcessor
   */
  EntitySetProcessor getEntitySetProcessor() throws ODataException;

  /**
   * Gets the entity complex property processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see EntityComplexPropertyProcessor
   */
  EntityComplexPropertyProcessor getEntityComplexPropertyProcessor() throws ODataException;

  /**
   * Gets the entity link processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see EntityLinkProcessor
   */
  EntityLinkProcessor getEntityLinkProcessor() throws ODataException;

  /**
   * Gets the entity links processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see EntityLinksProcessor
   */
  EntityLinksProcessor getEntityLinksProcessor() throws ODataException;

  /**
   * Gets the entity media processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see EntityMediaProcessor
   */
  EntityMediaProcessor getEntityMediaProcessor() throws ODataException;

  /**
   * Gets the entity simple property processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see EntitySimplePropertyProcessor
   */
  EntitySimplePropertyProcessor getEntitySimplePropertyProcessor() throws ODataException;

  /**
   * Gets the entity simple property value processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see EntitySimplePropertyValueProcessor
   */
  EntitySimplePropertyValueProcessor getEntitySimplePropertyValueProcessor() throws ODataException;

  /**
   * Gets the function import processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see FunctionImportProcessor
   */
  FunctionImportProcessor getFunctionImportProcessor() throws ODataException;

  /**
   * Gets the function import value processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see FunctionImportValueProcessor
   */
  FunctionImportValueProcessor getFunctionImportValueProcessor() throws ODataException;

  /**
   * Gets the batch processor.
   *
   * @return a processor which handles this request
   * @throws ODataException the o data exception
   * @see BatchProcessor
   */
  BatchProcessor getBatchProcessor() throws ODataException;

  /**
   * Gets the processor.
   *
   * @return root processor interface
   * @throws ODataException the o data exception
   * @see ODataProcessor
   */
  ODataProcessor getProcessor() throws ODataException;

  /**
   * Gets the supported content types.
   *
   * @param processorFeature the processor feature
   * @return ordered list of all <code>content types</code> this service supports
   * @throws ODataException the o data exception
   */
  List<String> getSupportedContentTypes(Class<? extends ODataProcessor> processorFeature) throws ODataException;
}
