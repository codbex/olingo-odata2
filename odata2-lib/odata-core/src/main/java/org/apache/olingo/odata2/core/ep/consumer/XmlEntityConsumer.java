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

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties.EntityProviderReadPropertiesBuilder;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataDeltaFeed;
import org.apache.olingo.odata2.core.commons.XmlHelper;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.aggregator.EntityPropertyInfo;

// TODO: Auto-generated Javadoc
/**
 * Xml entity (content type dependent) consumer for reading input (from <code>content</code>).
 * 
 * 
 */
public class XmlEntityConsumer {

  /**
   * Instantiates a new xml entity consumer.
   *
   * @throws EntityProviderException the entity provider exception
   */
  public XmlEntityConsumer() throws EntityProviderException {
    super();
  }

  /**
   * Read feed.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param properties the properties
   * @return the o data delta feed
   * @throws EntityProviderException the entity provider exception
   */
  public ODataDeltaFeed readFeed(final EdmEntitySet entitySet, final InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    XMLStreamReader reader = null;
    EntityProviderException cachedException = null;

    try {
      reader = XmlHelper.createStreamReader(content);

      EntityInfoAggregator eia = EntityInfoAggregator.create(entitySet);
      XmlFeedConsumer xfc = new XmlFeedConsumer();
      return xfc.readFeed(reader, eia, properties);
    } catch (EntityProviderException e) {
      cachedException = e;
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (XMLStreamException e) {
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
    XMLStreamReader reader = null;
    EntityProviderException cachedException = null;

    try {
      reader = XmlHelper.createStreamReader(content);
      EntityInfoAggregator eia = EntityInfoAggregator.create(entitySet);

      return new XmlEntryConsumer().readEntry(reader, eia, properties, false);
    } catch (EntityProviderException e) {
      cachedException = e;
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (XMLStreamException e) {
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
  public Map<String, Object> readProperty(final EdmProperty edmProperty, final InputStream content,
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
    XMLStreamReader reader = null;
    EntityProviderException cachedException = null;
    try {
      reader = XmlHelper.createStreamReader(content);
      return new XmlPropertyConsumer().readProperty(reader, propertyInfo, readProperties);
    } catch (EntityProviderException e) {
      cachedException = e;
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (XMLStreamException e) {
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
   * Read property value.
   *
   * @param edmProperty the edm property
   * @param content the content
   * @return the object
   * @throws EntityProviderException the entity provider exception
   */
  public Object readPropertyValue(final EdmProperty edmProperty, final InputStream content)
      throws EntityProviderException {
    return readPropertyValue(edmProperty, content, null);
  }

  /**
   * Read property value.
   *
   * @param edmProperty the edm property
   * @param content the content
   * @param typeMapping the type mapping
   * @return the object
   * @throws EntityProviderException the entity provider exception
   */
  public Object readPropertyValue(final EdmProperty edmProperty, final InputStream content, final Class<?> typeMapping)
      throws EntityProviderException {
    try {
      final Map<String, Object> result;
      EntityProviderReadPropertiesBuilder propertiesBuilder = EntityProviderReadProperties.init().mergeSemantic(false);
      if (typeMapping == null) {
        result = readProperty(edmProperty, content, propertiesBuilder.build());
      } else {
        Map<String, Object> typeMappings = new HashMap<String, Object>();
        typeMappings.put(edmProperty.getName(), typeMapping);
        result = readProperty(edmProperty, content, propertiesBuilder.addTypeMappings(typeMappings).build());
      }
      return result.get(edmProperty.getName());
    } catch (EdmException e) {
      throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  }


  /**
   * Read collection.
   *
   * @param info the info
   * @param content the content
   * @param properties the properties
   * @return the object
   * @throws EntityProviderException the entity provider exception
   */
  public Object readCollection(final EntityPropertyInfo info, InputStream content,
      final EntityProviderReadProperties properties) throws EntityProviderException {
    XMLStreamReader reader = null;
    EntityProviderException cachedException = null;
    try {
      reader = XmlHelper.createStreamReader(content);
      return new XmlPropertyConsumer().readCollection(reader, info, properties);
    } catch (final EntityProviderException e) {
      cachedException = e;
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (final XMLStreamException e) {
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
    XMLStreamReader reader = null;
    EntityProviderException cachedException = null;
    XmlLinkConsumer xlc = new XmlLinkConsumer();

    try {
      reader = XmlHelper.createStreamReader(content);
      return xlc.readLink(reader, entitySet);
    } catch (EntityProviderException e) {
      cachedException = e;
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (XMLStreamException e) {
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
    XMLStreamReader reader = null;
    EntityProviderException cachedException = null;
    XmlLinkConsumer xlc = new XmlLinkConsumer();

    try {
      reader = XmlHelper.createStreamReader(content);
      return xlc.readLinks(reader, entitySet);
    } catch (EntityProviderException e) {
      cachedException = e;
      throw cachedException;
    } finally {// NOPMD (suppress DoNotThrowExceptionInFinally)
      if (reader != null) {
        try {
          reader.close();
        } catch (XMLStreamException e) {
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
}
