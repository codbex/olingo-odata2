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
package org.apache.olingo.odata2.core.processor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.util.Arrays;
import java.util.List;

import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.processor.feature.CustomContentType;
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
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataSingleProcessorServiceTest.
 */
public class ODataSingleProcessorServiceTest extends BaseTest {

  /** The Constant APPLICATION_XML. */
  private static final String APPLICATION_XML = createConstant(ContentType.APPLICATION_XML);
  
  /** The Constant APPLICATION_ATOM_SVC. */
  private static final String APPLICATION_ATOM_SVC = createConstant(ContentType.APPLICATION_ATOM_SVC);
  
  /** The Constant APPLICATION_JSON. */
  private static final String APPLICATION_JSON = createConstant(ContentType.APPLICATION_JSON);
  
  /** The service. */
  private ODataSingleProcessorService service;
  
  /** The processor. */
  private ODataSingleProcessor processor;
  
  /** The provider. */
  EdmProvider provider;

  /**
   * Before.
   *
   * @throws Exception the exception
   */
  @Before
  public void before() throws Exception {
    provider = mock(EdmProvider.class);
    processor = mock(ODataSingleProcessor.class, withSettings().extraInterfaces(CustomContentType.class));
    service = new ODataSingleProcessorService(provider, processor);
  }

  /**
   * Creates the constant.
   *
   * @param contentType the content type
   * @return the string
   */
  private static String createConstant(final ContentType contentType) {
    return appendCharset(contentType).toContentTypeString();
  }

  /**
   * Append charset.
   *
   * @param contentType the content type
   * @return the content type
   */
  private static ContentType appendCharset(final ContentType contentType) {
    return ContentType.create(contentType, ContentType.PARAMETER_CHARSET, ContentType.CHARSET_UTF_8);
  }

  /**
   * Default supported content types for entity set.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForEntitySet() throws Exception {
    List<String> types = service.getSupportedContentTypes(EntitySetProcessor.class);
    List<ContentType> convertedTypes = ContentType.convert(types);
    assertTrue(convertedTypes.contains(appendCharset(ContentType.APPLICATION_ATOM_XML)));
    assertTrue(convertedTypes.contains(appendCharset(ContentType.APPLICATION_ATOM_XML_FEED)));
    assertTrue(convertedTypes.contains(appendCharset(ContentType.APPLICATION_JSON)));
  }

  /**
   * Default supported content types for entity.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForEntity() throws Exception {
    List<String> types = service.getSupportedContentTypes(EntityProcessor.class);
    List<ContentType> convertedTypes = ContentType.convert(types);
    assertTrue(convertedTypes.contains(appendCharset(ContentType.APPLICATION_ATOM_XML)));
    assertTrue(convertedTypes.contains(appendCharset(ContentType.APPLICATION_ATOM_XML_ENTRY)));
    assertTrue(convertedTypes.contains(appendCharset(ContentType.APPLICATION_JSON)));
  }

  /**
   * Default supported content types for service document.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForServiceDocument() throws Exception {
    List<String> types = service.getSupportedContentTypes(ServiceDocumentProcessor.class);
    assertTrue(types.contains(APPLICATION_ATOM_SVC));
    assertTrue(types.contains(APPLICATION_JSON));
  }

  /**
   * Default supported content types for metadata.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForMetadata() throws Exception {
    List<String> types = service.getSupportedContentTypes(MetadataProcessor.class);
    assertTrue(types.contains(APPLICATION_XML));
  }

  /**
   * Default supported content types for function import.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForFunctionImport() throws Exception {
    List<String> types = service.getSupportedContentTypes(FunctionImportProcessor.class);
    assertTrue(types.contains(APPLICATION_XML));
    assertTrue(types.contains(APPLICATION_JSON));
  }

  /**
   * Default supported content types for entity complex property.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForEntityComplexProperty() throws Exception {
    List<String> types = service.getSupportedContentTypes(EntityComplexPropertyProcessor.class);
    assertTrue(types.contains(APPLICATION_XML));
    assertTrue(types.contains(APPLICATION_JSON));
  }

  /**
   * Default supported content types for entity simple property.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForEntitySimpleProperty() throws Exception {
    List<String> types = service.getSupportedContentTypes(EntitySimplePropertyProcessor.class);
    assertTrue(types.contains(APPLICATION_XML));
    assertTrue(types.contains(APPLICATION_JSON));
  }

  /**
   * Default supported content types for entity links.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForEntityLinks() throws Exception {
    List<String> types = service.getSupportedContentTypes(EntityLinksProcessor.class);
    assertTrue(types.contains(APPLICATION_XML));
    assertTrue(types.contains(APPLICATION_JSON));
  }

  /**
   * Default supported content types for entity link.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForEntityLink() throws Exception {
    ContentType ctGif = ContentType.create("image", "gif");

    List<String> types = service.getSupportedContentTypes(EntityLinkProcessor.class);
    assertTrue(types.contains(APPLICATION_XML));
    assertTrue(types.contains(APPLICATION_JSON));
    assertFalse(types.contains(ctGif.toContentTypeString()));
  }

  /**
   * Default supported content types and gif for entity link.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesAndGifForEntityLink() throws Exception {
    String ctGif = ContentType.create("image", "gif").toContentTypeString();
    when(((CustomContentType) processor).getCustomContentTypes(EntityLinkProcessor.class)).thenReturn(
        Arrays.asList(ctGif));

    List<String> types = service.getSupportedContentTypes(EntityLinkProcessor.class);
    assertTrue(types.contains(ctGif));
  }

  /**
   * Default supported content types for entity media.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForEntityMedia() throws Exception {
    List<String> types = service.getSupportedContentTypes(EntityMediaProcessor.class);
    assertTrue(types.contains(ContentType.WILDCARD.toContentTypeString()));
  }

  /**
   * Default supported content types for simple property value.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForSimplePropertyValue() throws Exception {
    List<String> types = service.getSupportedContentTypes(EntitySimplePropertyValueProcessor.class);
    assertTrue(types.contains(ContentType.WILDCARD.toContentTypeString()));
  }

  /**
   * Default supported content types for function import value.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForFunctionImportValue() throws Exception {
    List<String> types = service.getSupportedContentTypes(FunctionImportValueProcessor.class);
    assertTrue(types.contains(ContentType.WILDCARD.toContentTypeString()));
  }

  /**
   * Default supported content types for batch.
   *
   * @throws Exception the exception
   */
  @Test
  public void defaultSupportedContentTypesForBatch() throws Exception {
    List<String> types = service.getSupportedContentTypes(BatchProcessor.class);
    assertTrue(types.contains(ContentType.WILDCARD.toContentTypeString()));
  }
}
