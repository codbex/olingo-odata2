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
package org.apache.olingo.odata2.jpa.processor.ref.util;

import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPADefaultProcessor;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomODataJPAProcessor.
 */
public class CustomODataJPAProcessor extends ODataJPADefaultProcessor {

  /** The Constant LOG. */
  private static final Logger LOG = Logger.getLogger(CustomODataJPAProcessor.class.getName());
  
  /** The Constant READ_COUNT. */
  private static final AtomicInteger READ_COUNT = new AtomicInteger(0);

  /**
   * Instantiates a new custom O data JPA processor.
   *
   * @param oDataJPAContext the o data JPA context
   */
  public CustomODataJPAProcessor(ODataJPAContext oDataJPAContext) {
    super(oDataJPAContext);
  }

  /**
   * Read entity set.
   *
   * @param uriParserResultView the uri parser result view
   * @param contentType the content type
   * @return the o data response
   * @throws ODataException the o data exception
   */
  @Override
  public ODataResponse readEntitySet(final GetEntitySetUriInfo uriParserResultView, final String contentType)
      throws ODataException {

    int readCount = READ_COUNT.incrementAndGet();
    LOG.log(Level.INFO, "Start read access number '" + readCount + "' for '" +
        uriParserResultView.getTargetEntitySet().getName() + "'.");
    long start = System.currentTimeMillis();
    List<Object> jpaEntities = jpaProcessor.process(uriParserResultView);
    ODataResponse oDataResponse = responseBuilder.build(uriParserResultView, jpaEntities, contentType);
    long duration = System.currentTimeMillis() - start;
    LOG.log(Level.INFO, "Finished read access number '" + readCount + "' after '" + duration + "'ms.");

    return oDataResponse;
  }

}