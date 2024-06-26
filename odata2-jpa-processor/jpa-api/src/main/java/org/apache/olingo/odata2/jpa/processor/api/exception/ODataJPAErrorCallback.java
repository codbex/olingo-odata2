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
package org.apache.olingo.odata2.jpa.processor.api.exception;

import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataJPAErrorCallback.
 */
public class ODataJPAErrorCallback implements ODataErrorCallback {

  /**
   * Handle error.
   *
   * @param context the context
   * @return the o data response
   * @throws ODataApplicationException the o data application exception
   */
  @Override
  public ODataResponse handleError(final ODataErrorContext context) throws ODataApplicationException {

    final String SEPARATOR = " : ";

    Throwable t = context.getException();
    if (t instanceof ODataJPAException && t.getCause() != null) {
      StringBuilder errorBuilder = new StringBuilder();
      errorBuilder.append(t.getCause().getClass().toString());
      errorBuilder.append(SEPARATOR);
      errorBuilder.append(t.getCause().getMessage());
      context.setInnerError(errorBuilder.toString());
    }
    return EntityProvider.writeErrorDocument(context);
  }

}
