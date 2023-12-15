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
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.aggregator.EntityPropertyInfo;

import com.google.gson.stream.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonEntityConsumer.
 */
public class JsonEntityConsumer {

  /**  Default used charset for reader. */
  private static final String DEFAULT_CHARSET = "UTF-8";

  /**
   * Read entry.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data entry
   * @throws EntityProviderException the entity provider exception
   */
  public ODataEntry readEntry(final EdmEntitySet entitySet, final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    JsonReader reader = null;
    EntityProviderException cachedException = null;

    try {
      EntityInfoAggregator eia = EntityInfoAggregator.create(entitySet);
      reader = createJsonReader(content);

      return new JsonEntryConsumer(reader, eia, properties).readSingleEntry();
    } catch (UnsupportedEncodingException e) {
      cachedException =
          new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
              .getSimpleName()), e);
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          if (cachedException != null) {
            throw cachedException;
          } else {
            throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
                .getSimpleName()), e);
          }
        }
      }
    }
  }

  /**
   * Read feed.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param readProperties the read properties
   * @return the o data feed
   * @throws EntityProviderException the entity provider exception
   */
  public ODataFeed readFeed(final EdmEntitySet entitySet, final InputStream content,
      final EntityProviderReadProperties readProperties) throws EntityProviderException {
    return readDeltaFeed(entitySet, content, readProperties);
  }

  /**
   * Read delta feed.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param readProperties the read properties
   * @return the o data delta feed
   * @throws EntityProviderException the entity provider exception
   */
  public ODataDeltaFeed readDeltaFeed(final EdmEntitySet entitySet, final InputStream content,
      final EntityProviderReadProperties readProperties) throws EntityProviderException {

    JsonReader reader = null;
    EntityProviderException cachedException = null;

    try {
      EntityInfoAggregator eia = EntityInfoAggregator.create(entitySet);
      reader = createJsonReader(content);

      JsonFeedConsumer jfc = new JsonFeedConsumer(reader, eia, readProperties);
      ODataDeltaFeed result = jfc.readFeedStandalone();

      return result;
    } catch (UnsupportedEncodingException e) {
      cachedException =
          new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
              .getSimpleName()), e);
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          if (cachedException != null) {
            throw cachedException;
          } else {
            throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
                .getSimpleName()), e);
          }
        }
      }
    }
  }


  /**
   * Read property.
   *
   * @param edmProperty the edm property
   * @param content the content
   * @param properties the properties
   * @return the map
   * @throws EntityProviderException the entity provider exception
   */
  public Map<String, Object> readProperty(final EdmProperty edmProperty, InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    return readProperty(EntityInfoAggregator.create(edmProperty), content, properties);
  }

  /**
   * Read property.
   *
   * @param propertyInfo the property info
   * @param content the content
   * @param readProperties the read properties
   * @return the map
   * @throws EntityProviderException the entity provider exception
   */
  public Map<String, Object> readProperty(final EntityPropertyInfo propertyInfo, final InputStream content,
      final EntityProviderReadProperties readProperties) throws EntityProviderException {
    JsonReader reader = null;
    EntityProviderException cachedException = null;

    try {
      reader = createJsonReader(content);
      return new JsonPropertyConsumer().readPropertyStandalone(reader, propertyInfo, readProperties);
    } catch (final UnsupportedEncodingException e) {
      cachedException =
          new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
              .getSimpleName()), e);
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (final IOException e) {
          if (cachedException != null) {
            throw cachedException;
          } else {
            throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
                .getSimpleName()), e);
          }
        }
      }
    }
  }

  /**
   * Read collection.
   *
   * @param info the info
   * @param content the content
   * @param properties the properties
   * @return the list
   * @throws EntityProviderException the entity provider exception
   */
  public List<?> readCollection(final EntityPropertyInfo info, InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    JsonReader reader = null;
    EntityProviderException cachedException = null;

    try {
      reader = createJsonReader(content);
      return new JsonPropertyConsumer().readCollection(reader, info, properties);
    } catch (final UnsupportedEncodingException e) {
      cachedException = new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED
          .addContent(e.getClass().getSimpleName()), e);
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (final IOException e) {
          if (cachedException != null) {
            throw cachedException;
          } else {
            throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED
                .addContent(e.getClass().getSimpleName()), e);
          }
        }
      }
    }
  }

  /**
   * Read link.
   *
   * @param entitySet the entity set
   * @param content the content
   * @return the string
   * @throws EntityProviderException the entity provider exception
   */
  public String readLink(final EdmEntitySet entitySet, final Object content) throws EntityProviderException {
    JsonReader reader = null;
    EntityProviderException cachedException = null;

    try {
      reader = createJsonReader(content);
      return new JsonLinkConsumer().readLink(reader, entitySet);
    } catch (final UnsupportedEncodingException e) {
      cachedException =
          new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
              .getSimpleName()), e);
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (final IOException e) {
          if (cachedException != null) {
            throw cachedException;
          } else {
            throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
                .getSimpleName()), e);
          }
        }
      }
    }
  }

  /**
   * Read links.
   *
   * @param entitySet the entity set
   * @param content the content
   * @return the list
   * @throws EntityProviderException the entity provider exception
   */
  public List<String> readLinks(final EdmEntitySet entitySet, final Object content) throws EntityProviderException {
    JsonReader reader = null;
    EntityProviderException cachedException = null;

    try {
      reader = createJsonReader(content);
      return new JsonLinkConsumer().readLinks(reader, entitySet);
    } catch (final UnsupportedEncodingException e) {
      cachedException =
          new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
              .getSimpleName()), e);
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (final IOException e) {
          if (cachedException != null) {
            throw cachedException;
          } else {
            throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
                .getSimpleName()), e);
          }
        }
      }
    }
  }

  /**
   * Creates the json reader.
   *
   * @param content the content
   * @return the json reader
   * @throws EntityProviderException the entity provider exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  private JsonReader createJsonReader(final Object content) throws EntityProviderException,
      UnsupportedEncodingException {

    if (content == null) {
      throw new EntityProviderException(EntityProviderException.ILLEGAL_ARGUMENT
          .addContent("Got not supported NULL object as content to de-serialize."));
    }

    if (content instanceof InputStream) {
      return new JsonReader(new InputStreamReader((InputStream) content, DEFAULT_CHARSET));
    }
    throw new EntityProviderException(EntityProviderException.ILLEGAL_ARGUMENT
        .addContent("Found not supported content of class '" + content.getClass() + "' to de-serialize."));
  }
}
