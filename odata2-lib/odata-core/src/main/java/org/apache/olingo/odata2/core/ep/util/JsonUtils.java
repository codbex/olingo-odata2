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
package org.apache.olingo.odata2.core.ep.util;

import java.io.IOException;

import org.apache.olingo.odata2.api.ep.EntityProviderException;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtils.
 */
public class JsonUtils {

  /**
   * Creates the O data context value for tombstone.
   *
   * @param entitySetName the entity set name
   * @return the string
   */
  public static String createODataContextValueForTombstone(final String entitySetName) {
    return FormatJson.DELTA_CONTEXT_PREFIX + entitySetName + FormatJson.DELTA_CONTEXT_POSTFIX;
  }

  /**
   * Start json.
   *
   * @param reader the reader
   * @return the int
   * @throws EntityProviderException the entity provider exception
   */
  public static int startJson(final JsonReader reader) throws EntityProviderException {
    // The enclosing "d" and "results" are optional - so we cannot check for the presence
    // but we have to read over them in case they are present.
    JsonToken token;
    try {
      token = reader.peek();
      int openJsonObjects = 0;
      if (JsonToken.BEGIN_OBJECT == token) {
        reader.beginObject();
        openJsonObjects++;
        token = reader.peek();
        if (JsonToken.NAME == token) {
          String name = reader.nextName();
          if (!("d".equals(name) ^ "results".equals(name))) {
            // TODO I18N
            throw new EntityProviderException(EntityProviderException.COMMON, name +
                " not expected, only d or results");
          }
        }

        token = reader.peek();
        if (JsonToken.BEGIN_OBJECT == token) {
          reader.beginObject();
          openJsonObjects++;
        } else if (JsonToken.BEGIN_ARRAY == token) {
          // TODO I18N
          throw new EntityProviderException(EntityProviderException.COMMON, "Array not expected");
        }
      }

      return openJsonObjects;
    } catch (IOException e) {
      // TODO I18N
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * End json.
   *
   * @param reader the reader
   * @param openJsonObjects the open json objects
   * @return true, if successful
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EntityProviderException the entity provider exception
   */
  public static boolean endJson(final JsonReader reader, final int openJsonObjects) throws IOException,
      EntityProviderException {

    for (int closedJsonObjects = 0; closedJsonObjects < openJsonObjects; closedJsonObjects++) {
      reader.endObject();
    }
    return reader.peek() == JsonToken.END_DOCUMENT;
  }
}
