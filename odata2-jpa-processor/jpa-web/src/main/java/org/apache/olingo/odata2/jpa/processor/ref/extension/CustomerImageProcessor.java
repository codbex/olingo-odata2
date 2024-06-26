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
package org.apache.olingo.odata2.jpa.processor.ref.extension;

import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImport.ReturnType.Type;
import org.apache.olingo.odata2.api.annotation.edm.EdmFunctionImportParameter;
import org.apache.olingo.odata2.jpa.processor.ref.util.CustomerImageLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class CustomerImageProcessor.
 */
public class CustomerImageProcessor {

  /**
   * Gets the image.
   *
   * @param customerId the customer id
   * @return the image
   */
  @EdmFunctionImport(returnType = @ReturnType(type = Type.SIMPLE))
  public byte[] getImage(
      @EdmFunctionImportParameter(name = "CustomerId", facets = @EdmFacets(nullable = false)) Long customerId) {
    return CustomerImageLoader.loadImage(customerId);
  }
}
