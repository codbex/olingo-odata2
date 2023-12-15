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
package org.apache.olingo.odata2.core.ep.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySetInfo;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.core.commons.Decoder;
import org.apache.olingo.odata2.core.edm.provider.EdmEntitySetInfoImplProv;
import org.apache.olingo.odata2.core.ep.util.FormatJson;
import org.apache.olingo.odata2.core.servicedocument.ServiceDocumentImpl;

import com.google.gson.stream.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * Reads the OData service document (JSON).
 * 
 */
public class JsonServiceDocumentConsumer {
  
  /** The Constant DEFAULT_CHARSET. */
  private static final String DEFAULT_CHARSET = "UTF-8";
  
  /** The entity sets. */
  List<EdmEntitySetInfo> entitySets = new ArrayList<EdmEntitySetInfo>();
  
  /** The current handled object name. */
  private String currentHandledObjectName;

  /**
   * Parses the json.
   *
   * @param in the in
   * @return the service document impl
   * @throws EntityProviderException the entity provider exception
   */
  public ServiceDocumentImpl parseJson(final InputStream in) throws EntityProviderException {
    return readServiceDocument(createJsonReader(in));
  }

  /**
   * Read service document.
   *
   * @param reader the reader
   * @return the service document impl
   * @throws EntityProviderException the entity provider exception
   */
  private ServiceDocumentImpl readServiceDocument(final JsonReader reader) throws EntityProviderException {
    try {
      reader.beginObject();
      currentHandledObjectName = reader.nextName();
      if (FormatJson.D.equals(currentHandledObjectName)) {
        reader.beginObject();
        readContent(reader);
        reader.endObject();
      }
      reader.endObject();
      reader.peek();
      reader.close();
    } catch (final IOException e) {
      throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    } catch (final IllegalStateException e) {
      throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    } catch (final EdmException e) {
      throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
    return new ServiceDocumentImpl().setEntitySetsInfo(entitySets);
  }

  /**
   * Read content.
   *
   * @param reader the reader
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EdmException the edm exception
   * @throws EntityProviderException the entity provider exception
   */
  private void readContent(final JsonReader reader) throws IOException, EdmException, EntityProviderException {
    currentHandledObjectName = reader.nextName();
    if (FormatJson.ENTITY_SETS.equals(currentHandledObjectName)) {
      reader.beginArray();
      readEntitySets(reader);
      reader.endArray();
    }
  }

  /**
   * Read entity sets.
   *
   * @param reader the reader
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EntityProviderException the entity provider exception
   * @throws EdmException the edm exception
   */
  private void readEntitySets(final JsonReader reader) throws IOException, EntityProviderException, EdmException {
    while (reader.hasNext()) {
      currentHandledObjectName = reader.nextString();
      if (currentHandledObjectName != null) {
        // Looking for the last dot: "\\.(?=[^.]+$)"
        String[] names = currentHandledObjectName.split("\\" + Edm.DELIMITER + "(?=[^" + Edm.DELIMITER + "]+$)");
        if (names.length == 1) {
          EntitySet entitySet = new EntitySet().setName(Decoder.decode(names[0]));
          EntityContainerInfo container = new EntityContainerInfo().setDefaultEntityContainer(true);
          EdmEntitySetInfo entitySetInfo = new EdmEntitySetInfoImplProv(entitySet, container);
          entitySets.add(entitySetInfo);
        } else {
          EntitySet entitySet = new EntitySet().setName(Decoder.decode(names[1]));
          EntityContainerInfo container =
              new EntityContainerInfo().setName(Decoder.decode(names[0])).setDefaultEntityContainer(false);
          EdmEntitySetInfo entitySetInfo = new EdmEntitySetInfoImplProv(entitySet, container);
          entitySets.add(entitySetInfo);
        }
      }
    }
  }

  /**
   * Creates the json reader.
   *
   * @param in the in
   * @return the json reader
   * @throws EntityProviderException the entity provider exception
   */
  private JsonReader createJsonReader(final InputStream in) throws EntityProviderException {
    if (in == null) {
      throw new EntityProviderException(EntityProviderException.INVALID_STATE
          .addContent(("Got not supported NULL object as content to de-serialize.")));
    }
    InputStreamReader isReader;
    try {
      isReader = new InputStreamReader(in, DEFAULT_CHARSET);
    } catch (final UnsupportedEncodingException e) {
      throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
    return new JsonReader(isReader);
  }
}
