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
package org.apache.olingo.odata2.client.core.ep.deserializer;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.client.api.ep.DeserializerProperties;
import org.apache.olingo.odata2.client.api.ep.EntityStream;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.apache.olingo.odata2.testutil.mock.MockFacade;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDeserializerTest.
 */
public abstract class AbstractDeserializerTest extends BaseTest {

  /** The Constant DEFAULT_PROPERTIES. */
  protected static final DeserializerProperties DEFAULT_PROPERTIES = DeserializerProperties.init()
      .build();

  /**
   * Creates the reader for test.
   *
   * @param input the input
   * @return the XML stream reader
   * @throws XMLStreamException the XML stream exception
   */
  protected XMLStreamReader createReaderForTest(final String input) throws XMLStreamException {
    return createReaderForTest(input, false);
  }

  /**
   * Creates the reader for test.
   *
   * @param input the input
   * @param namespaceAware the namespace aware
   * @return the XML stream reader
   * @throws XMLStreamException the XML stream exception
   */
  protected XMLStreamReader createReaderForTest(final String input, final boolean namespaceAware)
      throws XMLStreamException {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
    factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, namespaceAware);

    XMLStreamReader streamReader = factory.createXMLStreamReader(new StringReader(input));

    return streamReader;
  }

  /**
   * Creates the type mappings.
   *
   * @param key the key
   * @param value the value
   * @return the map
   */
  protected Map<String, Object> createTypeMappings(final String key, final Object value) {
    Map<String, Object> typeMappings = new HashMap<String, Object>();
    typeMappings.put(key, value);
    return typeMappings;
  }

  /**
   * Gets the file as stream.
   *
   * @param filename the filename
   * @return the file as stream
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected InputStream getFileAsStream(final String filename) throws IOException {
    InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
    if (in == null) {
      throw new IOException("Requested file '" + filename + "' was not found.");
    }
    return in;
  }

  /**
   * Read file.
   *
   * @param filename the filename
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected String readFile(final String filename) throws IOException {
    InputStream in = getFileAsStream(filename);

    byte[] tmp = new byte[8192];
    int count = in.read(tmp);
    StringBuilder b = new StringBuilder();
    while (count >= 0) {
      b.append(new String(tmp, 0, count));
      count = in.read(tmp);
    }

    return b.toString();
  }

  /**
   * Create a map with a 'String' to 'Class<?>' mapping based on given parameters.
   * Therefore parameters MUST be a set of such pairs.
   * As example an correct method call would be:
   * <p>
   * <code>
   * createTypeMappings("someKey", Integer.class, "anotherKey", Long.class);
   * </code>
   * </p>
   *
   * @param firstKeyThenMappingClass the first key then mapping class
   * @return the map
   */
  protected Map<String, Object> createTypeMappings(final Object... firstKeyThenMappingClass) {
    Map<String, Object> typeMappings = new HashMap<String, Object>();
    if (firstKeyThenMappingClass.length % 2 != 0) {
      throw new IllegalArgumentException("Got odd number of parameters. Please read javadoc.");
    }
    for (int i = 0; i < firstKeyThenMappingClass.length; i += 2) {
      String key = (String) firstKeyThenMappingClass[i];
      Class<?> mappingClass = (Class<?>) firstKeyThenMappingClass[i + 1];
      typeMappings.put(key, mappingClass);
    }
    return typeMappings;
  }

  /**
   * Creates the content as stream.
   *
   * @param content the content
   * @return the input stream
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  protected InputStream createContentAsStream(final String content) throws UnsupportedEncodingException {
    return new ByteArrayInputStream(content.getBytes("UTF-8"));
  }

  /**
   * Creates the content as stream.
   *
   * @param content the content
   * @param replaceWhitespaces if <code>true</code> all XML not necessary whitespaces between tags are
   * @return the input stream
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  protected InputStream createContentAsStream(final String content, final boolean replaceWhitespaces)
      throws UnsupportedEncodingException {
    String contentForStream = content;
    if (replaceWhitespaces) {
      contentForStream = content.replaceAll(">\\s.<", "><");
    }

    return new ByteArrayInputStream(contentForStream.getBytes("UTF-8"));
  }

  /**
   * Prepare and execute entry.
   *
   * @param fileName the file name
   * @param entitySetName the entity set name
   * @param readProperties the read properties
   * @return the o data entry
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EdmException the edm exception
   * @throws ODataException the o data exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   * @throws EntityProviderException the entity provider exception
   */
  protected ODataEntry prepareAndExecuteEntry(final String fileName, final String entitySetName,
      final DeserializerProperties readProperties) throws IOException, EdmException, ODataException,
      UnsupportedEncodingException, EntityProviderException {
    // prepare
    EdmEntitySet entitySet = MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet(entitySetName);
    String content = readFile(fileName);
    assertNotNull(content);
    InputStream contentBody = createContentAsStream(content);
    EntityStream entityStream = new EntityStream();
    entityStream.setContent(contentBody);
    entityStream.setReadProperties(readProperties);

    // execute
    JsonEntityDeserializer xec = new JsonEntityDeserializer();
    ODataEntry result = xec.readEntry(entitySet, entityStream);
    assertNotNull(result);
    return result;
  }

  /**
   * Prepare and execute feed.
   *
   * @param fileName the file name
   * @param entitySetName the entity set name
   * @param readProperties the read properties
   * @return the o data feed
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws EdmException the edm exception
   * @throws ODataException the o data exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   * @throws EntityProviderException the entity provider exception
   */
  protected ODataFeed prepareAndExecuteFeed(final String fileName, final String entitySetName,
      final DeserializerProperties readProperties) throws IOException, EdmException, ODataException,
      UnsupportedEncodingException, EntityProviderException {
    // prepare
    EdmEntitySet entitySet = MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet(entitySetName);
    String content = readFile(fileName);
    assertNotNull(content);
    InputStream contentBody = createContentAsStream(content);
    EntityStream entityStream = new EntityStream();
    entityStream.setContent(contentBody);
    entityStream.setReadProperties(readProperties);

    // execute
    JsonEntityDeserializer xec = new JsonEntityDeserializer();
    ODataFeed result = xec.readFeed(entitySet, entityStream);
    assertNotNull(result);
    return result;
  }

}
