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
package org.apache.olingo.odata2.annotation.processor.core.datasource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;

// TODO: Auto-generated Javadoc
/**
 * <p>This interface is intended to make it easier to implement an OData
 * service in cases where all data for each entity set can be provided as a {@link List} of objects from which all
 * properties described in the
 * Entity Data Model can be retrieved and set.</p>
 * <p>By obeying these restrictions, data-source implementations get the
 * following advantages:
 * <ul>
 * <li>All system query options can be handled centrally.</li>
 * <li>Following navigation paths must only be done step by step.</li>
 * </ul>
 * </p>
 * 
 */
public interface DataSource {

  /**
   * Retrieves the whole data list for the specified entity set.
   *
   * @param entitySet the requested {@link EdmEntitySet}
   * @return the requested data list
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  List<?> readData(EdmEntitySet entitySet) throws ODataNotImplementedException, ODataNotFoundException, EdmException,
      ODataApplicationException;

  /**
   * Retrieves a single data object for the specified entity set and key.
   *
   * @param entitySet the requested {@link EdmEntitySet}
   * @param keys the entity key as map of key names to key values
   * @return the requested data object
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  Object readData(EdmEntitySet entitySet, Map<String, Object> keys) throws ODataNotImplementedException,
      ODataNotFoundException, EdmException, ODataApplicationException;

  /**
   * <p>Retrieves data for the specified function import and key.</p>
   * <p>This method is called also for function imports that have defined in
   * their metadata an other HTTP method than <code>GET</code>.</p>
   *
   * @param function the requested {@link EdmFunctionImport}
   * @param parameters the parameters of the function import
   * as map of parameter names to parameter values
   * @param keys the key of the returned entity set, as map of key names to key values,
   * if the return type of the function import is a collection of entities
   * (optional)
   * @return the requested data object, either a list or a single object;
   * if the function import's return type is of type <code>Binary</code>,
   * the returned object(s) must be of type {@link BinaryData}
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  Object readData(EdmFunctionImport function, Map<String, Object> parameters, Map<String, Object> keys)
      throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException;

  /**
   * <p>Retrieves related data for the specified source data, entity set, and key.</p>
   * <p>If the underlying association of the EDM is specified to have target
   * multiplicity '*' and no target key is given, this method returns a list of
   * related data, otherwise it returns a single data object.</p>
   *
   * @param sourceEntitySet the {@link EdmEntitySet} of the source entity
   * @param sourceData the data object of the source entity
   * @param targetEntitySet the requested target {@link EdmEntitySet}
   * @param targetKeys the key of the target entity as map of key names to key values
   * (optional)
   * @return the requested releated data object, either a list or a single object
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  Object readRelatedData(EdmEntitySet sourceEntitySet, Object sourceData, EdmEntitySet targetEntitySet,
      Map<String, Object> targetKeys) throws ODataNotImplementedException, ODataNotFoundException, EdmException,
      ODataApplicationException;

  /**
   * Retrieves the binary data and the MIME type for the media resource
   * associated to the specified media-link entry.
   *
   * @param entitySet the {@link EdmEntitySet} of the media-link entry
   * @param mediaLinkEntryData the data object of the media-link entry
   * @return the binary data and the MIME type of the media resource
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  BinaryData readBinaryData(EdmEntitySet entitySet, Object mediaLinkEntryData) throws ODataNotImplementedException,
      ODataNotFoundException, EdmException, ODataApplicationException;

  /**
   * <p>Creates and returns a new instance of the requested data-object type.</p>
   * <p>This instance must not be part of the corresponding list and should
   * have empty content, apart from the key and other mandatory properties.
   * However, intermediate objects to access complex properties must not be
   * <code>null</code>.</p>
   *
   * @param entitySet the {@link EdmEntitySet} the object must correspond to
   * @return the new data object
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  Object newDataObject(EdmEntitySet entitySet) throws ODataNotImplementedException, EdmException,
      ODataApplicationException;

  /**
   * Writes the binary data for the media resource associated to the
   * specified media-link entry.
   *
   * @param entitySet the {@link EdmEntitySet} of the media-link entry
   * @param mediaLinkEntryData the data object of the media-link entry
   * @param binaryData the binary data of the media resource along with
   * the MIME type of the binary data
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  void writeBinaryData(EdmEntitySet entitySet, Object mediaLinkEntryData, BinaryData binaryData)
      throws ODataNotImplementedException, ODataNotFoundException, EdmException, ODataApplicationException;

  /**
   * Deletes a single data object identified by the specified entity set and key.
   *
   * @param entitySet the {@link EdmEntitySet} of the entity to be deleted
   * @param keys the entity key as map of key names to key values
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  void deleteData(EdmEntitySet entitySet, Map<String, Object> keys) throws ODataNotImplementedException,
      ODataNotFoundException, EdmException, ODataApplicationException;

  /**
   * <p>Inserts an instance into the entity list of the specified entity set.</p>
   * <p>If {@link #newDataObject} has not set the key and other mandatory
   * properties already, this method must set them before inserting the
   * instance into the list.</p>
   *
   * @param entitySet the {@link EdmEntitySet} the object must correspond to
   * @param data the data object of the new entity
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  void createData(EdmEntitySet entitySet, Object data) throws ODataNotImplementedException, EdmException,
      ODataApplicationException;

  /**
   * Deletes the relation from the specified source data to a target entity
   * specified by entity set and key.
   *
   * @param sourceEntitySet the {@link EdmEntitySet} of the source entity
   * @param sourceData the data object of the source entity
   * @param targetEntitySet the {@link EdmEntitySet} of the target entity
   * @param targetKeys the key of the target entity as map of key names to key values
   * (optional)
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  void deleteRelation(EdmEntitySet sourceEntitySet, Object sourceData, EdmEntitySet targetEntitySet,
      Map<String, Object> targetKeys) throws ODataNotImplementedException, ODataNotFoundException, EdmException,
      ODataApplicationException;

  /**
   * Writes a relation from the specified source data to a target entity
   * specified by entity set and key.
   *
   * @param sourceEntitySet the {@link EdmEntitySet} of the source entity
   * @param sourceData the data object of the source entity
   * @param targetEntitySet the {@link EdmEntitySet} of the relation target
   * @param targetKeys the key of the target entity as map of key names to key values
   * @throws ODataNotImplementedException the o data not implemented exception
   * @throws ODataNotFoundException the o data not found exception
   * @throws EdmException the edm exception
   * @throws ODataApplicationException the o data application exception
   */
  void writeRelation(EdmEntitySet sourceEntitySet, Object sourceData, EdmEntitySet targetEntitySet,
      Map<String, Object> targetKeys) throws ODataNotImplementedException, ODataNotFoundException, EdmException,
      ODataApplicationException;

  /**
   * Container to store binary data (as byte array) and the associated MIME type.
   */
  public class BinaryData {
    
    /** The data. */
    private final byte[] data;
    
    /** The mime type. */
    private final String mimeType;

    /**
     * Instantiates a new binary data.
     *
     * @param data the data
     * @param mimeType the mime type
     */
    public BinaryData(final byte[] data, final String mimeType) {
      this.data = data;
      this.mimeType = mimeType;
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public byte[] getData() {
      return data;
    }

    /**
     * Gets the mime type.
     *
     * @return the mime type
     */
    public String getMimeType() {
      return mimeType;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
      return "data=" + Arrays.toString(data) + ", mimeType=" + mimeType;
    }
  }
}
