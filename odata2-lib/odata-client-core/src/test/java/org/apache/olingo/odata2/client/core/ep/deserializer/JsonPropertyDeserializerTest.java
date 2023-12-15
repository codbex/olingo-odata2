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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.client.api.ep.DeserializerProperties;
import org.apache.olingo.odata2.core.ep.aggregator.EntityComplexPropertyInfo;
import org.apache.olingo.odata2.core.ep.aggregator.EntityInfoAggregator;
import org.apache.olingo.odata2.core.ep.aggregator.EntityPropertyInfo;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.apache.olingo.odata2.testutil.mock.MockFacade;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.stream.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonPropertyDeserializerTest.
 */
public class JsonPropertyDeserializerTest extends BaseTest {
  
  /** The expected ex. */
  @Rule
  public ExpectedException expectedEx = ExpectedException.none();
  
  /**
   * Boolean simple property.
   *
   * @throws Exception the exception
   */
  @Test
  public void booleanSimpleProperty() throws Exception {
    EdmProperty property = mock(EdmProperty.class);
    when(property.getName()).thenReturn("Boolean");
    when(property.isSimple()).thenReturn(true);
    when(property.getType()).thenReturn(EdmSimpleTypeKind.Boolean.getEdmSimpleTypeInstance());

    JsonReader reader = prepareReader("{\"Boolean\":true}");
    final Map<String, Object> resultMap = execute(property, reader);
    assertEquals(Boolean.TRUE, resultMap.get("Boolean"));
  }

  /**
   * All number simple property kinds.
   *
   * @throws Exception the exception
   */
  @Test
  public void allNumberSimplePropertyKinds() throws Exception {
    String simplePropertyJson = "{\"d\":{\"Age\":67}}";
    EdmProperty edmProperty = mock(EdmProperty.class);
    when(edmProperty.getName()).thenReturn("Age");
    when(edmProperty.isSimple()).thenReturn(true);

    // Byte
    JsonReader reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Byte.getEdmSimpleTypeInstance());
    Map<String, Object> resultMap = execute(edmProperty, reader);
    assertEquals(Short.valueOf("67"), resultMap.get("Age"));

    // SByte
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.SByte.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(Byte.valueOf("67"), resultMap.get("Age"));
    // Int16
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Int16.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(Short.valueOf("67"), resultMap.get("Age"));
    // Int32
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Int32.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(Integer.valueOf("67"), resultMap.get("Age"));
  }

  /**
   * All string simple property kinds.
   *
   * @throws Exception the exception
   */
  @Test
  public void allStringSimplePropertyKinds() throws Exception {
    EdmProperty edmProperty = mock(EdmProperty.class);
    when(edmProperty.getName()).thenReturn("Name");
    when(edmProperty.isSimple()).thenReturn(true);
    String simplePropertyJson;

    // DateTime
    simplePropertyJson = "{\"d\":{\"Name\":\"\\/Date(915148800000)\\/\"}}";
    JsonReader reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.DateTime.getEdmSimpleTypeInstance());
    Map<String, Object> resultMap = execute(edmProperty, reader);
    Calendar entryDate = (Calendar) resultMap.get("Name");
    assertEquals(Long.valueOf(915148800000l), Long.valueOf(entryDate.getTimeInMillis()));
    // DateTimeOffset
    simplePropertyJson = "{\"d\":{\"Name\":\"\\/Date(915148800000)\\/\"}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.DateTime.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    entryDate = (Calendar) resultMap.get("Name");
    assertEquals(Long.valueOf(915148800000l), Long.valueOf(entryDate.getTimeInMillis()));
    // Decimal
    simplePropertyJson = "{\"d\":{\"Name\":\"123456789\"}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Decimal.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(BigDecimal.valueOf(Long.valueOf("123456789")), resultMap.get("Name"));
    // Double
    simplePropertyJson = "{\"d\":{\"Name\":\"123456789\"}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Double.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(Double.valueOf("123456789"), resultMap.get("Name"));
    // Double without "
    simplePropertyJson = "{\"d\":{\"Name\":123456789}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Double.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(Double.valueOf("123456789"), resultMap.get("Name"));
    // Int64
    simplePropertyJson = "{\"d\":{\"Name\":\"123456789\"}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Int64.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(Long.valueOf("123456789"), resultMap.get("Name"));
    // Single
    simplePropertyJson = "{\"d\":{\"Name\":\"123456\"}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Single.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(Float.valueOf("123456"), resultMap.get("Name"));
    // Single without "
    simplePropertyJson = "{\"d\":{\"Name\":123456}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Single.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(Float.valueOf("123456"), resultMap.get("Name"));
    // String
    simplePropertyJson = "{\"d\":{\"Name\":\"123456789\"}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.String.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals("123456789", resultMap.get("Name"));
    // Guid
    simplePropertyJson = "{\"d\":{\"Name\":\"AABBCCDD-AABB-CCDD-EEFF-AABBCCDDEEFF\"}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Guid.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertEquals(UUID.fromString("aabbccdd-aabb-ccdd-eeff-aabbccddeeff"), resultMap.get("Name"));
    // Binary
    simplePropertyJson = "{\"d\":{\"Name\":\"qrvM\"}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Binary.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    assertTrue(Arrays.equals(new byte[] { (byte) 0xAA, (byte) 0xBB, (byte) 0xCC },
        (byte[]) resultMap.get("Name")));
 // Time
    simplePropertyJson = "{\"d\":{\"Name\":\"PT23H32M3S\"}}";
    reader = prepareReader(simplePropertyJson);
    when(edmProperty.getType()).thenReturn(EdmSimpleTypeKind.Time.getEdmSimpleTypeInstance());
    resultMap = execute(edmProperty, reader);
    Calendar dateTime = Calendar.getInstance();
    dateTime.clear();
    dateTime.set(Calendar.HOUR_OF_DAY, 23);
    dateTime.set(Calendar.MINUTE, 32);
    dateTime.set(Calendar.SECOND, 3);
    assertEquals(dateTime, resultMap.get("Name"));
  }

  /**
   * Simple property on open reader.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyOnOpenReader() throws Exception {
    String simplePropertyJson = "{\"Name\":\"Team 1\"}";
    JsonReader reader = prepareReader(simplePropertyJson);
    EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Teams").getEntityType()
            .getProperty("Name");
    EntityPropertyInfo entityPropertyInfo = EntityInfoAggregator.create(edmProperty);
    reader.beginObject();
    reader.nextName();

    JsonPropertyDeserializer jpc = new JsonPropertyDeserializer();
    Object value = jpc.readPropertyValue(reader, entityPropertyInfo, null, null);
    assertEquals("Team 1", value);
  }

  /**
   * Very long string standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void veryLongStringStandalone() throws Exception {
    char[] chars = new char[32768];
    Arrays.fill(chars, 0, 32768, 'a');
    String propertyValue = new String(chars);
    String simplePropertyJson = "{\"d\":{\"Name\":\"" + propertyValue + "\"}}";
    JsonReader reader = prepareReader(simplePropertyJson);
    final EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Room").getProperty("Name");

    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    when(readProperties.getTypeMappings()).thenReturn(null);
    Map<String, Object> resultMap =
        new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, readProperties);

    assertEquals(propertyValue, resultMap.get("Name"));
  }

  /**
   * Simple property violating validation.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void simplePropertyViolatingValidation() throws Exception {
    EdmProperty property = (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Room")
        .getProperty("Name");
    EdmFacets facets = mock(EdmFacets.class);
    when(facets.getMaxLength()).thenReturn(10);
    when(property.getFacets()).thenReturn(facets);
    new JsonPropertyDeserializer().readPropertyStandalone(prepareReader("{\"Name\":\"TooLongName\"}"), property, null);
  }

  /**
   * Simple property ignoring validation.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyIgnoringValidation() throws Exception {
    EdmProperty property = (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Room")
        .getProperty("Name");
    EdmFacets facets = mock(EdmFacets.class);
    when(facets.getMaxLength()).thenReturn(10);
    when(property.getFacets()).thenReturn(facets);
    final DeserializerProperties readProperties = mock(DeserializerProperties.class);
    final Map<String, Object> resultMap = new JsonPropertyDeserializer()
        .readPropertyStandalone(prepareReader("{\"Name\":\"TooLongName\"}"), property, readProperties);
    assertTrue(resultMap.containsKey("Name"));
    assertEquals("TooLongName", resultMap.get("Name"));
  }

  /**
   * Simple property null.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyNull() throws Exception {
    JsonReader reader = prepareReader("{\"Name\":null}");
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Room").getProperty("Name");
    final Map<String, Object> resultMap = new JsonPropertyDeserializer().readPropertyStandalone(reader, property, null);
    assertTrue(resultMap.containsKey("Name"));
    assertNull(resultMap.get("Name"));
  }

  /**
   * Simple property null value not allowed.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void simplePropertyNullValueNotAllowed() throws Exception {
    JsonReader reader = prepareReader("{\"Age\":null}");
    EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");
    EdmFacets facets = mock(EdmFacets.class);
    when(facets.isNullable()).thenReturn(false);
    when(property.getFacets()).thenReturn(facets);

    new JsonPropertyDeserializer().readPropertyStandalone(reader, property, null);
  }

  /**
   * Simple property invalid name.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void simplePropertyInvalidName() throws Exception {
    EdmProperty property = mock(EdmProperty.class);
    when(property.getName()).thenReturn("property-name");
    when(property.isSimple()).thenReturn(true);
    when(property.getType()).thenReturn(EdmSimpleTypeKind.Boolean.getEdmSimpleTypeInstance());

    JsonReader reader = prepareReader("{\"Boolean\":true}");
    execute(property, reader);
    expectedEx.expect(RuntimeException.class);
    expectedEx.expectMessage("'property-name' name pattern not valid.");
  }
  
  /**
   * Simple property with null mapping standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyWithNullMappingStandalone() throws Exception {
    String simplePropertyJson = "{\"d\":{\"Age\":67}}";
    JsonReader reader = prepareReader(simplePropertyJson);
    final EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");

    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    when(readProperties.getTypeMappings()).thenReturn(null);
    Map<String, Object> resultMap =
        new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, readProperties);

    assertEquals(Integer.valueOf(67), resultMap.get("Age"));
  }

  /**
   * Simple property with null mapping standalone without D.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyWithNullMappingStandaloneWithoutD() throws Exception {
    String simplePropertyJson = "{\"Age\":67}";
    JsonReader reader = prepareReader(simplePropertyJson);
    final EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");

    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    when(readProperties.getTypeMappings()).thenReturn(null);
    Map<String, Object> resultMap =
        new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, readProperties);

    assertEquals(Integer.valueOf(67), resultMap.get("Age"));
  }

  /**
   * Simple property with empty mapping standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyWithEmptyMappingStandalone() throws Exception {
    String simplePropertyJson = "{\"d\":{\"Age\":67}}";
    JsonReader reader = prepareReader(simplePropertyJson);
    final EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");

    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    when(readProperties.getTypeMappings()).thenReturn(new HashMap<String, Object>());
    Map<String, Object> resultMap =
        new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, readProperties);

    assertEquals(Integer.valueOf(67), resultMap.get("Age"));
  }

  /**
   * Simple property with string to long mapping standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyWithStringToLongMappingStandalone() throws Exception {
    String simplePropertyJson = "{\"d\":{\"Age\":67}}";
    JsonReader reader = prepareReader(simplePropertyJson);
    final EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");

    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    Map<String, Object> typeMappings = new HashMap<String, Object>();
    typeMappings.put("Age", Integer.class);
    when(readProperties.getTypeMappings()).thenReturn(typeMappings);
    Map<String, Object> resultMap =
        new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, readProperties);

    assertEquals(Integer.valueOf(67), resultMap.get("Age"));
  }

  /**
   * Simple property with string to null mapping standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyWithStringToNullMappingStandalone() throws Exception {
    String simplePropertyJson = "{\"d\":{\"Age\":67}}";
    JsonReader reader = prepareReader(simplePropertyJson);
    final EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");

    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    Map<String, Object> typeMappings = new HashMap<String, Object>();
    typeMappings.put("Age", null);
    when(readProperties.getTypeMappings()).thenReturn(typeMappings);
    Map<String, Object> resultMap =
        new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, readProperties);

    assertEquals(Integer.valueOf(67), resultMap.get("Age"));
  }

  /**
   * No content.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void noContent() throws Exception {
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");
    JsonReader reader = prepareReader("{}");
    new JsonPropertyDeserializer().readPropertyStandalone(reader, property, null);
  }

  /**
   * Simple property unfinished.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void simplePropertyUnfinished() throws Exception {
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");
    JsonReader reader = prepareReader("{\"Age\":67");
    new JsonPropertyDeserializer().readPropertyStandalone(reader, property, null);
  }

  /**
   * Simple propert invalid name.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void simplePropertInvalidName() throws Exception {
    String simplePropertyJson = "{\"d\":{\"Invalid\":67}}";
    JsonReader reader = prepareReader(simplePropertyJson);
    final EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getEntityType("RefScenario", "Employee").getProperty("Age");

    new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, null);
  }

  /**
   * Complex property with string to string mapping standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void complexPropertyWithStringToStringMappingStandalone() throws Exception {
    final String complexPropertyJson =
        "{\"d\":{\"City\":{\"__metadata\":{\"type\":\"RefScenario.c_City\"}," +
            "\"PostalCode\":\"69124\",\"CityName\":\"Heidelberg\"}}}";
    JsonReader reader = prepareReader(complexPropertyJson);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getComplexType("RefScenario", "c_Location").getProperty("City");

    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    Map<String, Object> innerMappings = new HashMap<String, Object>();
    innerMappings.put("PostalCode", String.class);
    Map<String, Object> typeMappings = new HashMap<String, Object>();
    typeMappings.put("City", innerMappings);
    when(readProperties.getTypeMappings()).thenReturn(typeMappings);
    Map<String, Object> result = new JsonPropertyDeserializer().
        readPropertyStandalone(reader, property, readProperties);

    assertEquals(1, result.size());
    @SuppressWarnings("unchecked")
    Map<String, Object> innerResult = (Map<String, Object>) result.get("City");
    assertEquals("Heidelberg", innerResult.get("CityName"));
    assertEquals("69124", innerResult.get("PostalCode"));
  }

  /**
   * Deep complex property with string to string mapping standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void deepComplexPropertyWithStringToStringMappingStandalone() throws Exception {
    final String complexPropertyJson =
        "{\"d\":{\"Location\":{\"__metadata\":{\"type\":\"RefScenario.c_Location\"}," +
            "\"City\":{\"__metadata\":{\"type\":\"RefScenario.c_City\"},\"PostalCode\":\"69124\"," +
            "\"CityName\":\"Heidelberg\"},\"Country\":\"Germany\"}}}";
    JsonReader reader = prepareReader(complexPropertyJson);
    EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees").getEntityType()
            .getProperty("Location");

    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    Map<String, Object> cityMappings = new HashMap<String, Object>();
    cityMappings.put("PostalCode", String.class);
    Map<String, Object> locationMappings = new HashMap<String, Object>();
    locationMappings.put("City", cityMappings);
    Map<String, Object> mappings = new HashMap<String, Object>();
    mappings.put("Location", locationMappings);
    when(readProperties.getTypeMappings()).thenReturn(mappings);

    final Map<String, Object> result =
        new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, readProperties);

    assertEquals(1, result.size());
    @SuppressWarnings("unchecked")
    Map<String, Object> locationResult = (Map<String, Object>) result.get("Location");
    assertEquals(2, locationResult.size());
    assertEquals("Germany", locationResult.get("Country"));
    @SuppressWarnings("unchecked")
    Map<String, Object> innerResult = (Map<String, Object>) locationResult.get("City");
    assertEquals(2, innerResult.size());
    assertEquals("Heidelberg", innerResult.get("CityName"));
    assertEquals("69124", innerResult.get("PostalCode"));
  }

  /**
   * Complex property on open reader.
   *
   * @throws Exception the exception
   */
  @Test
  public void complexPropertyOnOpenReader() throws Exception {
    final String complexPropertyJson =
        "{\"__metadata\":{\"type\":\"RefScenario.c_City\"},\"PostalCode\":\"69124\",\"CityName\":\"Heidelberg\"}";
    JsonReader reader = prepareReader(complexPropertyJson);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getComplexType("RefScenario", "c_Location").getProperty("City");
    EntityComplexPropertyInfo entityPropertyInfo = (EntityComplexPropertyInfo) EntityInfoAggregator.create(property);

    JsonPropertyDeserializer jpc = new JsonPropertyDeserializer();
    @SuppressWarnings("unchecked")
    Map<String, Object> result = (Map<String, Object>) jpc.readPropertyValue(reader, entityPropertyInfo, null, null);

    assertEquals(2, result.size());
    assertEquals("Heidelberg", result.get("CityName"));
    assertEquals("69124", result.get("PostalCode"));
  }

  /**
   * Complex property on open reader with no metadata.
   *
   * @throws Exception the exception
   */
  @Test
  public void complexPropertyOnOpenReaderWithNoMetadata() throws Exception {
    final String complexPropertyJson = "{\"PostalCode\":\"69124\",\"CityName\":\"Heidelberg\"}";
    JsonReader reader = prepareReader(complexPropertyJson);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getComplexType("RefScenario", "c_Location").getProperty("City");
    EntityComplexPropertyInfo entityPropertyInfo = (EntityComplexPropertyInfo) EntityInfoAggregator.create(property);

    JsonPropertyDeserializer jpc = new JsonPropertyDeserializer();
    @SuppressWarnings("unchecked")
    Map<String, Object> result = (Map<String, Object>) jpc.readPropertyValue(reader, entityPropertyInfo, null, null);

    assertEquals(2, result.size());
    assertEquals("Heidelberg", result.get("CityName"));
    assertEquals("69124", result.get("PostalCode"));
  }

  /**
   * Deep complex property on open reader.
   *
   * @throws Exception the exception
   */
  @Test
  public void deepComplexPropertyOnOpenReader() throws Exception {
    final String complexPropertyJson =
        "{\"__metadata\":{\"type\":\"RefScenario.c_Location\"}," +
            "\"City\":{\"__metadata\":{\"type\":\"RefScenario.c_City\"},\"PostalCode\":\"69124\"," +
            "\"CityName\":\"Heidelberg\"},\"Country\":\"Germany\"}";
    JsonReader reader = prepareReader(complexPropertyJson);
    EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees").getEntityType()
            .getProperty("Location");
    EntityComplexPropertyInfo entityPropertyInfo = (EntityComplexPropertyInfo) EntityInfoAggregator.create(edmProperty);

    JsonPropertyDeserializer jpc = new JsonPropertyDeserializer();
    @SuppressWarnings("unchecked")
    Map<String, Object> result = (Map<String, Object>) jpc.readPropertyValue(reader, entityPropertyInfo, null, null);

    assertEquals(2, result.size());
    assertEquals("Germany", result.get("Country"));
    @SuppressWarnings("unchecked")
    Map<String, Object> innerResult = (Map<String, Object>) result.get("City");
    assertEquals(2, innerResult.size());
    assertEquals("Heidelberg", innerResult.get("CityName"));
    assertEquals("69124", innerResult.get("PostalCode"));
  }

  /**
   * Simple property standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void simplePropertyStandalone() throws Exception {
    String simplePropertyJson = "{\"d\":{\"Name\":\"Team 1\"}}";
    EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Teams").getEntityType()
            .getProperty("Name");
    JsonReader reader = prepareReader(simplePropertyJson);

    Map<String, Object> result = new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, null);
    assertEquals("Team 1", result.get("Name"));
  }

  /**
   * Complex property standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void complexPropertyStandalone() throws Exception {
    final String complexPropertyJson =
        "{\"d\":{\"City\":{\"__metadata\":{\"type\":\"RefScenario.c_City\"}," +
            "\"PostalCode\":\"69124\",\"CityName\":\"Heidelberg\"}}}";
    JsonReader reader = prepareReader(complexPropertyJson);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getComplexType("RefScenario", "c_Location").getProperty("City");

    final Map<String, Object> result = new JsonPropertyDeserializer().readPropertyStandalone(reader, property, null);

    assertEquals(1, result.size());
    @SuppressWarnings("unchecked")
    Map<String, Object> innerResult = (Map<String, Object>) result.get("City");
    assertEquals("Heidelberg", innerResult.get("CityName"));
    assertEquals("69124", innerResult.get("PostalCode"));
  }

  /**
   * Deep complex property standalone.
   *
   * @throws Exception the exception
   */
  @Test
  public void deepComplexPropertyStandalone() throws Exception {
    final String complexPropertyJson =
        "{\"d\":{\"Location\":{\"__metadata\":{\"type\":\"RefScenario.c_Location\"}," +
            "\"City\":{\"__metadata\":{\"type\":\"RefScenario.c_City\"},\"PostalCode\":\"69124\"," +
            "\"CityName\":\"Heidelberg\"},\"Country\":\"Germany\"}}}";
    JsonReader reader = prepareReader(complexPropertyJson);
    EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees").getEntityType()
            .getProperty("Location");

    JsonPropertyDeserializer jpc = new JsonPropertyDeserializer();
    Map<String, Object> result = jpc.readPropertyStandalone(reader, edmProperty, null);

    assertEquals(1, result.size());
    @SuppressWarnings("unchecked")
    Map<String, Object> locationResult = (Map<String, Object>) result.get("Location");
    assertEquals(2, locationResult.size());
    assertEquals("Germany", locationResult.get("Country"));
    @SuppressWarnings("unchecked")
    Map<String, Object> innerResult = (Map<String, Object>) locationResult.get("City");
    assertEquals(2, innerResult.size());
    assertEquals("Heidelberg", innerResult.get("CityName"));
    assertEquals("69124", innerResult.get("PostalCode"));
  }

  /**
   * Complex property with invalid child.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void complexPropertyWithInvalidChild() throws Exception {
    String cityProperty = "{\"d\":{\"City\":{\"Invalid\":\"69124\",\"CityName\":\"Heidelberg\"}}}";
    JsonReader reader = prepareReader(cityProperty);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getComplexType("RefScenario", "c_Location").getProperty("City");

    new JsonPropertyDeserializer().readPropertyStandalone(reader, property, null);
  }

  /**
   * Complex property with invalid name.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void complexPropertyWithInvalidName() throws Exception {
    String cityProperty = "{\"d\":{\"Invalid\":{\"PostalCode\":\"69124\",\"CityName\":\"Heidelberg\"}}}";
    JsonReader reader = prepareReader(cityProperty);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getComplexType("RefScenario", "c_Location").getProperty("City");

    new JsonPropertyDeserializer().readPropertyStandalone(reader, property, null);
  }

  /**
   * Complex property null.
   *
   * @throws Exception the exception
   */
  @Test
  public void complexPropertyNull() throws Exception {
    final String locationProperty = "{\"Location\":null}";
    JsonReader reader = prepareReader(locationProperty);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer().
        getEntitySet("Employees").getEntityType()
            .getProperty("Location");

    final Map<String, Object> propertyData = new JsonPropertyDeserializer().
        readPropertyStandalone(reader, property, null);
    assertNotNull(propertyData);
    assertEquals(1, propertyData.size());
    assertTrue(propertyData.containsKey("Location"));
    assertNull(propertyData.get("Location"));
  }

  /**
   * Complex property null value not allowed.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void complexPropertyNullValueNotAllowed() throws Exception {
    final String locationProperty = "{\"Location\":null}";
    JsonReader reader = prepareReader(locationProperty);
    EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Employees").getEntityType()
            .getProperty("Location");
    EdmFacets facets = mock(EdmFacets.class);
    when(facets.isNullable()).thenReturn(false);
    when(property.getFacets()).thenReturn(facets);

    new JsonPropertyDeserializer().readPropertyStandalone(reader, property, null);
  }

  /**
   * Complex property null value not allowed but not validated.
   *
   * @throws Exception the exception
   */
  @Test
  public void complexPropertyNullValueNotAllowedButNotValidated() throws Exception {
    final EdmProperty property = (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer()
        .getEntitySet("Employees").getEntityType().getProperty("Location");
    EdmFacets facets = mock(EdmFacets.class);
    when(facets.isNullable()).thenReturn(false);
    when(property.getFacets()).thenReturn(facets);
    final DeserializerProperties readProperties = mock(DeserializerProperties.class);

    final Map<String, Object> propertyData = new JsonPropertyDeserializer()
        .readPropertyStandalone(prepareReader("{\"Location\":null}"), property, readProperties);
    assertNotNull(propertyData);
    assertEquals(1, propertyData.size());
    assertTrue(propertyData.containsKey("Location"));
    assertNull(propertyData.get("Location"));
  }

  /**
   * Complex property empty.
   *
   * @throws Exception the exception
   */
  @Test
  public void complexPropertyEmpty() throws Exception {
    final String cityProperty = "{\"d\":{\"City\":{}}}";
    JsonReader reader = prepareReader(cityProperty);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().
        getComplexType("RefScenario", "c_Location").getProperty("City");

    final Map<String, Object> propertyData = new JsonPropertyDeserializer().
        readPropertyStandalone(reader, property, null);
    assertNotNull(propertyData);
    assertEquals(1, propertyData.size());
    assertNotNull(propertyData.get("City"));
    @SuppressWarnings("unchecked")
    final Map<String, Object> innerMap = (Map<String, Object>) propertyData.get("City");
    assertTrue(innerMap.isEmpty());
  }

  /**
   * Complex property metadata invalid tag.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void complexPropertyMetadataInvalidTag() throws Exception {
    String complexPropertyJson =
        "{\"__metadata\":{\"invalid\":\"RefScenario.c_City\"},"
        + "\"PostalCode\":\"69124\",\"CityName\":\"Heidelberg\"}";
    JsonReader reader = prepareReader(complexPropertyJson);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getComplexType
        ("RefScenario", "c_Location").getProperty("City");
    EntityComplexPropertyInfo entityPropertyInfo = (EntityComplexPropertyInfo) 
        EntityInfoAggregator.create(property);

    new JsonPropertyDeserializer().readPropertyValue(reader, entityPropertyInfo, null, null);
  }

  /**
   * Complex property metadata invalid type content.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void complexPropertyMetadataInvalidTypeContent() throws Exception {
    String complexPropertyJson =
        "{\"__metadata\":{\"type\":\"Invalid\"},\"PostalCode\":\"69124\",\"CityName\":\"Heidelberg\"}";
    JsonReader reader = prepareReader(complexPropertyJson);
    final EdmProperty property =
        (EdmProperty) MockFacade.getMockEdm().getComplexType
        ("RefScenario", "c_Location").getProperty("City");
    EntityComplexPropertyInfo entityPropertyInfo = (EntityComplexPropertyInfo) 
        EntityInfoAggregator.create(property);

    new JsonPropertyDeserializer().readPropertyValue(reader, entityPropertyInfo, null, null);
  }

  /**
   * Prepare reader.
   *
   * @param json the json
   * @return the json reader
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  private JsonReader prepareReader(final String json) throws UnsupportedEncodingException {
    InputStream jsonStream = createContentAsStream(json);
    JsonReader reader = new JsonReader(new InputStreamReader(jsonStream));
    return reader;
  }

  /**
   * Execute.
   *
   * @param edmProperty the edm property
   * @param reader the reader
   * @return the map
   * @throws EntityProviderException the entity provider exception
   */
  private Map<String, Object> execute(final EdmProperty edmProperty, final JsonReader reader)
      throws EntityProviderException {
    JsonPropertyDeserializer jpc = new JsonPropertyDeserializer();
    Map<String, Object> resultMap = jpc.readPropertyStandalone(reader, edmProperty, null);
    return resultMap;
  }

  /**
   * Invalid double closing brackets.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void invalidDoubleClosingBrackets() throws Exception {
    String simplePropertyJson = "{\"d\":{\"Name\":\"Team 1\"}}}";
    EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Teams").getEntityType()
            .getProperty("Name");
    JsonReader reader = prepareReader(simplePropertyJson);

    new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, null);
  }

  /**
   * Invalid double closing brackets without D.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void invalidDoubleClosingBracketsWithoutD() throws Exception {
    String simplePropertyJson = "{\"Name\":\"Team 1\"}}";
    EdmProperty edmProperty =
        (EdmProperty) MockFacade.getMockEdm().getDefaultEntityContainer().getEntitySet("Teams").getEntityType()
            .getProperty("Name");
    JsonReader reader = prepareReader(simplePropertyJson);

    new JsonPropertyDeserializer().readPropertyStandalone(reader, edmProperty, null);
  }

  /**
   * Collection empty.
   *
   * @throws Exception the exception
   */
  @Test
  public void collectionEmpty() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds"));
    List<?> collection = new JsonPropertyDeserializer().readCollection(prepareReader("[]"), info, null);
    assertNotNull(collection);
    assertTrue(collection.isEmpty());

    collection = new JsonPropertyDeserializer().readCollection(prepareReader("{\"d\":[]}"), info, null);
    assertNotNull(collection);
    assertTrue(collection.isEmpty());

    collection = new JsonPropertyDeserializer().readCollection(prepareReader("{\"results\":[]}"), info, null);
    assertNotNull(collection);
    assertTrue(collection.isEmpty());

    collection = new JsonPropertyDeserializer().readCollection(prepareReader("{\"d\":{\"results\":[]}}"), info, null);
    assertNotNull(collection);
    assertTrue(collection.isEmpty());
  }

  /**
   * Collection simple.
   *
   * @throws Exception the exception
   */
  @Test
  public void collectionSimple() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds"));
    List<?> collection = new JsonPropertyDeserializer().readCollection(prepareReader("[\"1\",\"42\"]"), info, null);
    assertNotNull(collection);
    assertEquals(Arrays.asList("1", "42"), collection);
  }

  /**
   * Collection simple with metadata.
   *
   * @throws Exception the exception
   */
  @Test
  public void collectionSimpleWithMetadata() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds"));
    List<?> collection = new JsonPropertyDeserializer().readCollection(prepareReader(
        "{\"__metadata\":{\"type\":\"Collection(Edm.String)\"},\"results\":[\"1\",\"42\"]}"),
        info, null);
    assertNotNull(collection);
    assertEquals(Arrays.asList("1", "42"), collection);
  }

  /**
   * Collection simple with mapping.
   *
   * @throws Exception the exception
   */
  @Test
  public void collectionSimpleWithMapping() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds"));
    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    when(readProperties.getTypeMappings()).thenReturn(
        Collections.<String, Object> singletonMap("AllUsedRoomIds", String.class));
    List<?> collection = new JsonPropertyDeserializer().readCollection(prepareReader("[\"1\",\"42\"]"), info,
        readProperties);
    assertNotNull(collection);
    assertEquals(Arrays.asList("1", "42"), collection);
  }

  /**
   * Collection complex.
   *
   * @throws Exception the exception
   */
  @Test
  public void collectionComplex() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllLocations"));
    DeserializerProperties readProperties = mock(DeserializerProperties.class);
    final Map<String, Object> mappings = Collections.<String, Object> singletonMap("Location",
        Collections.<String, Object> singletonMap("City",
            Collections.<String, Object> singletonMap("PostalCode", String.class)));
    when(readProperties.getTypeMappings()).thenReturn(mappings);
    List<?> collection = new JsonPropertyDeserializer().readCollection(prepareReader(
        "{\"__metadata\":{\"type\":\"Collection(RefScenario.c_Location)\"},"
            + "\"results\":["
            + "{\"City\":{\"PostalCode\":\"69124\",\"CityName\":\"Heidelberg\"},\"Country\":\"Germany\"},"
            + "{\"City\":{\"PostalCode\":\"69190\",\"CityName\":\"Walldorf\"},\"Country\":\"Germany\"}]}"),
        info, readProperties);
    assertNotNull(collection);
    assertEquals(2, collection.size());
    @SuppressWarnings("unchecked")
    final Map<String, Object> secondLocation = (Map<String, Object>) collection.get(1);
    assertEquals("Germany", secondLocation.get("Country"));
  }

  /**
   * Collection unfinished.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void collectionUnfinished() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds"));
    new JsonPropertyDeserializer().readCollection(prepareReader("[\"1\""), info, null);
  }

  /**
   * Collection without closing.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void collectionWithoutClosing() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds"));
    new JsonPropertyDeserializer().readCollection(prepareReader("{\"results\":[]"), info, null);
  }

  /**
   * Collection with wrong tag.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void collectionWithWrongTag() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds"));
    new JsonPropertyDeserializer().readCollection(prepareReader("{\"something\":[]}"), info, null);
  }

  /**
   * Collection with wrong inner tag.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void collectionWithWrongInnerTag() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds"));
    new JsonPropertyDeserializer().readCollection(prepareReader("{\"d\":{\"something\":[]}}"), info, null);
  }

  /**
   * Collection with trailing.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void collectionWithTrailing() throws Exception {
    final EntityPropertyInfo info = EntityInfoAggregator.create(
        MockFacade.getMockEdm().getDefaultEntityContainer().getFunctionImport("AllUsedRoomIds"));
    new JsonPropertyDeserializer().readCollection(prepareReader("{\"results\":[],\"a\":0}"), info, null);
  }

  /**
   * Creates the content as stream.
   *
   * @param json the json
   * @return the input stream
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  private InputStream createContentAsStream(final String json) throws UnsupportedEncodingException {
    return new ByteArrayInputStream(json.getBytes("UTF-8"));
  }
}
