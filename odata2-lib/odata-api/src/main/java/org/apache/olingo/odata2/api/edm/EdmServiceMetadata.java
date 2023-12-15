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
package org.apache.olingo.odata2.api.edm;

import java.io.InputStream;
import java.util.List;

import org.apache.olingo.odata2.api.exception.ODataException;

// TODO: Auto-generated Javadoc
/**
 * The Interface EdmServiceMetadata.
 *
 * @org.apache.olingo.odata2.DoNotImplement This interface gives access to the metadata of a service, the calculated Data Service Version and an info list of all
 * entity sets inside this EntityDataModel.
 */
public interface EdmServiceMetadata {

  /**
   * Gets the metadata.
   *
   * @return {@link InputStream} containing the metadata document
   * @throws ODataException the o data exception
   */
  InputStream getMetadata() throws ODataException;

  /**
   * Gets the data service version.
   *
   * @return <b>String</b> data service version of this service
   * @throws ODataException the o data exception
   */
  String getDataServiceVersion() throws ODataException;

  /**
   * Gets the entity set infos.
   *
   * @return a list of {@link EdmEntitySetInfo} objects of all entity sets in this data model
   * @throws ODataException the o data exception
   */
  List<EdmEntitySetInfo> getEntitySetInfos() throws ODataException;;
}
