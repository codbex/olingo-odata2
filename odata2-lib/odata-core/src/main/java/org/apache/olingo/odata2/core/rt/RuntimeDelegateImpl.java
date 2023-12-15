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
package org.apache.olingo.odata2.core.rt;

import java.io.InputStream;
import java.util.Locale;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.batch.BatchResponsePart.BatchResponsePartBuilder;
import org.apache.olingo.odata2.api.client.batch.BatchChangeSet.BatchChangeSetBuilder;
import org.apache.olingo.odata2.api.client.batch.BatchChangeSetPart.BatchChangeSetPartBuilder;
import org.apache.olingo.odata2.api.client.batch.BatchQueryPart.BatchQueryPartBuilder;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeFacade;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.ep.EntityProvider.EntityProviderInterface;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.api.processor.ODataRequest.ODataRequestBuilder;
import org.apache.olingo.odata2.api.processor.ODataResponse.ODataResponseBuilder;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.rt.RuntimeDelegate.RuntimeDelegateInstance;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode.ExpandSelectTreeNodeBuilder;
import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.core.ODataRequestImpl;
import org.apache.olingo.odata2.core.ODataResponseImpl;
import org.apache.olingo.odata2.core.batch.BatchChangeSetImpl;
import org.apache.olingo.odata2.core.batch.BatchChangeSetPartImpl;
import org.apache.olingo.odata2.core.batch.BatchQueryPartImpl;
import org.apache.olingo.odata2.core.batch.BatchResponsePartImpl;
import org.apache.olingo.odata2.core.edm.EdmSimpleTypeFacadeImpl;
import org.apache.olingo.odata2.core.edm.provider.EdmImplProv;
import org.apache.olingo.odata2.core.edm.provider.EdmxProvider;
import org.apache.olingo.odata2.core.ep.ProviderFacadeImpl;
import org.apache.olingo.odata2.core.exception.MessageService;
import org.apache.olingo.odata2.core.exception.MessageService.Message;
import org.apache.olingo.odata2.core.processor.ODataSingleProcessorService;
import org.apache.olingo.odata2.core.uri.ExpandSelectTreeNodeImpl;
import org.apache.olingo.odata2.core.uri.UriParserImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class RuntimeDelegateImpl.
 */
public class RuntimeDelegateImpl extends RuntimeDelegateInstance {

  /**
   * Creates the O data response builder.
   *
   * @return the o data response builder
   */
  @Override
  protected ODataResponseBuilder createODataResponseBuilder() {
    ODataResponseImpl r = new ODataResponseImpl();
    return r.new ODataResponseBuilderImpl();
  }

  /**
   * Gets the edm simple type.
   *
   * @param edmSimpleType the edm simple type
   * @return the edm simple type
   */
  @Override
  protected EdmSimpleType getEdmSimpleType(final EdmSimpleTypeKind edmSimpleType) {
    return EdmSimpleTypeFacadeImpl.getEdmSimpleType(edmSimpleType);
  }

  /**
   * Gets the uri parser.
   *
   * @param edm the edm
   * @return the uri parser
   */
  @Override
  protected UriParser getUriParser(final Edm edm) {
    return new UriParserImpl(edm);
  }

  /**
   * Gets the simple type facade.
   *
   * @return the simple type facade
   */
  @Override
  protected EdmSimpleTypeFacade getSimpleTypeFacade() {
    return new EdmSimpleTypeFacadeImpl();
  }

  /**
   * Creates the edm.
   *
   * @param provider the provider
   * @return the edm
   */
  @Override
  protected Edm createEdm(final EdmProvider provider) {
    return new EdmImplProv(provider);
  }

  /**
   * Creates the entity provider.
   *
   * @return the entity provider interface
   */
  @Override
  protected EntityProviderInterface createEntityProvider() {
    return new ProviderFacadeImpl();
  }

  /**
   * Creates the O data single processor service.
   *
   * @param provider the provider
   * @param processor the processor
   * @return the o data service
   */
  @Override
  protected ODataService createODataSingleProcessorService(final EdmProvider provider,
      final ODataSingleProcessor processor) {
    return new ODataSingleProcessorService(provider, processor);
  }

  /**
   * Creates the edm provider.
   *
   * @param metadataXml the metadata xml
   * @param validate the validate
   * @return the edm provider
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  protected EdmProvider createEdmProvider(final InputStream metadataXml, final boolean validate)
      throws EntityProviderException {
    return new EdmxProvider().parse(metadataXml, validate);
  }

  /**
   * Creates the batch response part builder.
   *
   * @return the batch response part builder
   */
  @Override
  protected BatchResponsePartBuilder createBatchResponsePartBuilder() {
    BatchResponsePartImpl part = new BatchResponsePartImpl();
    return part.new BatchResponsePartBuilderImpl();
  }

  /**
   * Creates the O data request builder.
   *
   * @return the o data request builder
   */
  @Override
  protected ODataRequestBuilder createODataRequestBuilder() {
    ODataRequestImpl request = new ODataRequestImpl();
    return request.new ODataRequestBuilderImpl();
  }

  /**
   * Creates the batch change set builder.
   *
   * @return the batch change set builder
   */
  @Override
  protected BatchChangeSetBuilder createBatchChangeSetBuilder() {
    BatchChangeSetImpl changeSet = new BatchChangeSetImpl();
    return changeSet.new BatchChangeSetBuilderImpl();
  }

  /**
   * Creates the batch query request builder.
   *
   * @return the batch query part builder
   */
  @Override
  protected BatchQueryPartBuilder createBatchQueryRequestBuilder() {
    BatchQueryPartImpl batchQueryRequest = new BatchQueryPartImpl();
    return batchQueryRequest.new BatchQueryRequestBuilderImpl();
  }

  /**
   * Creates the batch change set request.
   *
   * @return the batch change set part builder
   */
  @Override
  protected BatchChangeSetPartBuilder createBatchChangeSetRequest() {
    BatchChangeSetPartImpl batchChangeSetRequest = new BatchChangeSetPartImpl();
    ;
    return batchChangeSetRequest.new BatchChangeSetRequestBuilderImpl();
  }

  /**
   * Creates the expand select tree node builder.
   *
   * @return the expand select tree node builder
   */
  @Override
  public ExpandSelectTreeNodeBuilder createExpandSelectTreeNodeBuilder() {
    ExpandSelectTreeNodeImpl expandSelectTreeNode = new ExpandSelectTreeNodeImpl();
    return expandSelectTreeNode.new ExpandSelectTreeNodeBuilderImpl();
  }

  /**
   * Extract exception message.
   *
   * @param exception the exception
   * @return the string
   */
  @Override
  public String extractExceptionMessage(final ODataMessageException exception) {
    Message msg = MessageService.getMessage(Locale.ENGLISH, exception.getMessageReference());
    return msg.getText();
  }
}
