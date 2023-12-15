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

import static org.junit.Assert.assertEquals;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.junit.Test;

import com.google.gson.stream.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonLinkConsumerTest.
 */
public class JsonLinkConsumerTest extends AbstractConsumerTest {

  /**
   * Link with D.
   *
   * @throws Exception the exception
   */
  @Test
  public void linkWithD() throws Exception {
    final String link = "{\"d\":{\"uri\":\"http://somelink\"}}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(link)));
    assertEquals("http://somelink", new JsonLinkConsumer().readLink(reader, null));
  }

  /**
   * Link without D.
   *
   * @throws Exception the exception
   */
  @Test
  public void linkWithoutD() throws Exception {
    final String link = "{\"uri\":\"http://somelink\"}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(link)));
    assertEquals("http://somelink", new JsonLinkConsumer().readLink(reader, null));
  }

  /**
   * Invalid double closing brackets.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void invalidDoubleClosingBrackets() throws Exception {
    final String link = "{\"uri\":\"http://somelink\"}}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(link)));
    new JsonLinkConsumer().readLink(reader, null);
  }

  /**
   * Trailing garbage.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void trailingGarbage() throws Exception {
    final String link = "{\"uri\":\"http://somelink\"},{\"a\":null}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(link)));
    new JsonLinkConsumer().readLink(reader, null);
  }

  /**
   * Wrong tag name.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void wrongTagName() throws Exception {
    final String link = "{\"URI\":\"http://somelink\"}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(link)));
    new JsonLinkConsumer().readLink(reader, null);
  }

  /**
   * Wrong value type.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void wrongValueType() throws Exception {
    final String link = "{\"uri\":false}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(link)));
    new JsonLinkConsumer().readLink(reader, null);
  }

  /**
   * Links with D.
   *
   * @throws Exception the exception
   */
  @Test
  public void linksWithD() throws Exception {
    final String links = "{\"d\":[{\"uri\":\"http://somelink\"}]}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    assertEquals(Arrays.asList("http://somelink"), new JsonLinkConsumer().readLinks(reader, null));
  }

  /**
   * Links without D.
   *
   * @throws Exception the exception
   */
  @Test
  public void linksWithoutD() throws Exception {
    final String links = "[{\"uri\":\"http://somelink\"},{\"uri\":\"\"}]";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    assertEquals(Arrays.asList("http://somelink", ""), new JsonLinkConsumer().readLinks(reader, null));
  }

  /**
   * Links empty.
   *
   * @throws Exception the exception
   */
  @Test
  public void linksEmpty() throws Exception {
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream("[]")));
    assertEquals(Collections.emptyList(), new JsonLinkConsumer().readLinks(reader, null));
  }

  /**
   * Links with count.
   *
   * @throws Exception the exception
   */
  @Test
  public void linksWithCount() throws Exception {
    final String links = "{\"__count\":\"5\",\"results\":[{\"uri\":\"http://somelink\"},{\"uri\":\"\"}]}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    assertEquals(Arrays.asList("http://somelink", ""), new JsonLinkConsumer().readLinks(reader, null));
  }

  /**
   * Links with count at end.
   *
   * @throws Exception the exception
   */
  @Test
  public void linksWithCountAtEnd() throws Exception {
    final String links = "{\"results\":[{\"uri\":\"http://somelink\"},{\"uri\":\"\"}],\"__count\":\"5\"}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    assertEquals(Arrays.asList("http://somelink", ""), new JsonLinkConsumer().readLinks(reader, null));
  }

  /**
   * Links with D and results.
   *
   * @throws Exception the exception
   */
  @Test
  public void linksWithDAndResults() throws Exception {
    final String links = "{\"d\":{\"results\":[{\"uri\":\"http://somelink\"}]}}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    assertEquals(Arrays.asList("http://somelink"), new JsonLinkConsumer().readLinks(reader, null));
  }

  /**
   * Links wrong results name.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void linksWrongResultsName() throws Exception {
    final String links = "{\"__results\":[{\"uri\":\"http://somelink\"}]}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    new JsonLinkConsumer().readLinks(reader, null);
  }

  /**
   * Links wrong count name.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void linksWrongCountName() throws Exception {
    final String links = "{\"count\":\"5\",\"results\":[]}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    new JsonLinkConsumer().readLinks(reader, null);
  }

  /**
   * Links wrong count name at end.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void linksWrongCountNameAtEnd() throws Exception {
    final String links = "{\"results\":[],\"count\":\"5\"}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    new JsonLinkConsumer().readLinks(reader, null);
  }

  /**
   * Links without results.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void linksWithoutResults() throws Exception {
    final String links = "{\"count\":\"42\"}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    new JsonLinkConsumer().readLinks(reader, null);
  }

  /**
   * Links double count.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void linksDoubleCount() throws Exception {
    final String links = "{\"__count\":\"5\",\"results\":[],\"__count\":\"42\"}";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    new JsonLinkConsumer().readLinks(reader, null);
  }

  /**
   * Links wrong uri name.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void linksWrongUriName() throws Exception {
    final String links = "[{\"uri\":\"http://somelink\"},{\"URI\":\"\"}]";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    new JsonLinkConsumer().readLinks(reader, null);
  }

  /**
   * Links wrong uri type.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void linksWrongUriType() throws Exception {
    final String links = "[{\"uri\":false}]";
    JsonReader reader = new JsonReader(new InputStreamReader(createContentAsStream(links)));
    new JsonLinkConsumer().readLinks(reader, null);
  }
}
