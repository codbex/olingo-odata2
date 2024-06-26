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
package org.apache.olingo.odata2.jpa.processor.core.mock.data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.olingo.odata2.api.ep.entry.EntryMetadata;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.ep.feed.ODataFeed;
import org.apache.olingo.odata2.jpa.processor.core.mock.data.JPATypeMock.JPARelatedTypeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.data.JPATypeMock.JPATypeEmbeddableMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.data.JPATypeMock.JPATypeEmbeddableMock2;
import org.easymock.EasyMock;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataEntryMockUtil.
 */
public class ODataEntryMockUtil {

  /** The Constant VALUE_MINT. */
  public static final int VALUE_MINT = 20;
  
  /** The value date time. */
  public static Calendar VALUE_DATE_TIME = null;
  
  /** The value blob. */
  public static byte[] VALUE_BLOB = null;
  
  /** The Constant VALUE_CLOB. */
  public static final String VALUE_CLOB = "ABC";
  
  /** The Constant VALUE_C. */
  public static final String VALUE_C = "D";
  
  /** The Constant VALUE_CARRAY. */
  public static final String VALUE_CARRAY = "EFG";
  
  /** The Constant VALUE_CHAR. */
  public static final String VALUE_CHAR = "I";
  
  /** The Constant VALUE_CHARARRAY. */
  public static final String VALUE_CHARARRAY = "LMN";
  
  /** The Constant VALUE_MSTRING. */
  public static final String VALUE_MSTRING = "Mock";
  
  /** The Constant VALUE_XMLADAPTER. */
  public static final String VALUE_XMLADAPTER = "DOES-NOT-MATTER";
  
  /** The Constant VALUE_MLONG. */
  public static final long VALUE_MLONG = 1234567890L;
  
  /** The Constant VALUE_MDOUBLE. */
  public static final double VALUE_MDOUBLE = 20.12;
  
  /** The Constant VALUE_MBYTE. */
  public static final byte VALUE_MBYTE = 0XA;
  
  /** The Constant VALUE_MBYTEARRAY. */
  public static final byte[] VALUE_MBYTEARRAY = new byte[] { 0XA, 0XB };
  
  /** The Constant VALUE_MFLOAT. */
  public static final float VALUE_MFLOAT = 2.00F;
  
  /** The Constant VALUE_UUID. */
  public static final UUID VALUE_UUID = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
  
  /** The Constant VALUE_SHORT. */
  public static final Short VALUE_SHORT = 2;
  
  /** The Constant VALUE_ENUM. */
  public static final JPATypeMock.JPATypeMockEnum VALUE_ENUM = JPATypeMock.JPATypeMockEnum.VALUE;
  
  /** The value date. */
  public static java.util.Date VALUE_DATE = null;
  
  /** The value date1. */
  public static java.sql.Date VALUE_DATE1 = null;
  
  /** The value time. */
  public static Time VALUE_TIME = null;
  
  /** The value timestamp. */
  public static Timestamp VALUE_TIMESTAMP = null;

  /**
   * Mock O data entry.
   *
   * @param entityName the entity name
   * @return the o data entry
   */
  public static ODataEntry mockODataEntry(final String entityName) {
    ODataEntry oDataEntry = EasyMock.createMock(ODataEntry.class);
    EasyMock.expect(oDataEntry.getProperties()).andReturn(mockODataEntryProperties(entityName)).anyTimes();

    enhanceMockODataEntry(oDataEntry, false, new ArrayList<String>());
    EasyMock.replay(oDataEntry);
    return oDataEntry;
  }
  
  /**
   * Mock O data entry with null value.
   *
   * @param entityName the entity name
   * @return the o data entry
   */
  public static ODataEntry mockODataEntryWithNullValue(final String entityName) {
    ODataEntry oDataEntry = EasyMock.createMock(ODataEntry.class);
    Map<String, Object> propertiesMap = mockODataEntryProperties(entityName);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_MINT, null);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_MCHAR, null);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_CLOB, null);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_ENUM, null);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_MBLOB, null);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_MCARRAY, null);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_MC, null);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_MCHARARRAY, null);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_MDATETIME, null);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_MSTRING, null);
    EasyMock.expect(oDataEntry.getProperties()).andReturn(propertiesMap).anyTimes();

    enhanceMockODataEntry(oDataEntry, false, new ArrayList<String>());
    EasyMock.replay(oDataEntry);
    return oDataEntry;
  }

  /**
   * Mock O data entry with complex type.
   *
   * @param entityName the entity name
   * @return the o data entry
   */
  public static ODataEntry mockODataEntryWithComplexType(final String entityName) {
    ODataEntry oDataEntry = EasyMock.createMock(ODataEntry.class);
    EasyMock.expect(oDataEntry.getProperties()).andReturn(mockODataEntryPropertiesWithComplexType(entityName))
        .anyTimes();

    enhanceMockODataEntry(oDataEntry, false, new ArrayList<String>());
    EasyMock.replay(oDataEntry);
    return oDataEntry;
  }
  
  /**
   * Mock O data entry with complex type with null value.
   *
   * @param entityName the entity name
   * @return the o data entry
   */
  @SuppressWarnings("unchecked")
  public static ODataEntry mockODataEntryWithComplexTypeWithNullValue(final String entityName) {
    ODataEntry oDataEntry = EasyMock.createMock(ODataEntry.class);
    Map<String, Object> propertiesMap = mockODataEntryPropertiesWithComplexType(entityName);
    propertiesMap.put(JPATypeMock.PROPERTY_NAME_MCARRAY, null);
    Map<String, Object> complexPropertiesMap = (Map<String, Object>) propertiesMap.get
        (JPATypeMock.PROPERTY_NAME_MCOMPLEXTYPE);
    complexPropertiesMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MSHORT, null);
    complexPropertiesMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MDATE, null);
    complexPropertiesMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MDATE1, null);
    complexPropertiesMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MTIME, null);
    complexPropertiesMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MTIMESTAMP, null);
    Map<String, Object> embeddableMap = (Map<String, Object>) complexPropertiesMap.
        get(JPATypeEmbeddableMock.PROPERTY_NAME_MEMBEDDABLE);
    embeddableMap.put(JPATypeEmbeddableMock2.PROPERTY_NAME_MUUID, null);
    embeddableMap.put(JPATypeEmbeddableMock2.PROPERTY_NAME_MFLOAT, null);
    EasyMock.expect(oDataEntry.getProperties()).andReturn(propertiesMap)
        .anyTimes();

    enhanceMockODataEntry(oDataEntry, false, new ArrayList<String>());
    EasyMock.replay(oDataEntry);
    return oDataEntry;
  }

  /**
   * Mock O data entry properties.
   *
   * @param entityName the entity name
   * @return the map
   */
  public static Map<String, Object> mockODataEntryProperties(final String entityName) {
    Map<String, Object> propertyMap = new HashMap<String, Object>();

    if (entityName.equals(JPATypeMock.ENTITY_NAME)) {
      propertyMap.put(JPATypeMock.PROPERTY_NAME_MINT, VALUE_MINT);
      propertyMap.put(JPATypeMock.PROPERTY_NAME_ENUM, "VALUE");

      VALUE_DATE_TIME = Calendar.getInstance(TimeZone.getDefault());
      VALUE_DATE_TIME.set(2013, 1, 1, 1, 1, 1);
      propertyMap.put(JPATypeMock.PROPERTY_NAME_MDATETIME, VALUE_DATE_TIME);

      VALUE_BLOB = VALUE_MBYTEARRAY;
      propertyMap.put(JPATypeMock.PROPERTY_NAME_MBLOB, VALUE_BLOB);

      propertyMap.put(JPATypeMock.PROPERTY_NAME_CLOB, VALUE_CLOB);
      propertyMap.put(JPATypeMock.PROPERTY_NAME_MC, VALUE_C);
      propertyMap.put(JPATypeMock.PROPERTY_NAME_MCARRAY, VALUE_CARRAY);
      propertyMap.put(JPATypeMock.PROPERTY_NAME_MCHAR, VALUE_CHAR);
      propertyMap.put(JPATypeMock.PROPERTY_NAME_MCHARARRAY, VALUE_CHARARRAY);
      propertyMap.put(JPATypeMock.PROPERTY_NAME_MSTRING, VALUE_MSTRING);
      propertyMap.put(JPATypeMock.PROPERTY_NAME_XMLADAPTER, VALUE_XMLADAPTER);
    } else if (entityName.equals(JPARelatedTypeMock.ENTITY_NAME)) {
      propertyMap.put(JPARelatedTypeMock.PROPERTY_NAME_MLONG, VALUE_MLONG);
      propertyMap.put(JPARelatedTypeMock.PROPERTY_NAME_MDOUBLE, VALUE_MDOUBLE);
      propertyMap.put(JPARelatedTypeMock.PROPERTY_NAME_MBYTE, VALUE_MBYTE);
      propertyMap.put(JPARelatedTypeMock.PROPERTY_NAME_MBYTEARRAY, VALUE_MBYTEARRAY);
    } else if (entityName.equals(JPATypeEmbeddableMock.ENTITY_NAME)) {
      propertyMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MSHORT, VALUE_SHORT);
      VALUE_DATE = Calendar.getInstance(TimeZone.getDefault()).getTime();
      VALUE_DATE1 = new java.sql.Date(Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis());
      VALUE_TIME = new java.sql.Time(Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis());
      VALUE_TIMESTAMP = new Timestamp(Calendar.getInstance(TimeZone.getDefault()).getTimeInMillis());
      propertyMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MDATE, Calendar.getInstance(TimeZone.getDefault()));
      propertyMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MDATE1, Calendar.getInstance(TimeZone.getDefault()));
      propertyMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MTIME, Calendar.getInstance(TimeZone.getDefault()));
      propertyMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MTIMESTAMP, Calendar.getInstance(TimeZone.getDefault()));
      propertyMap.put(JPATypeEmbeddableMock.PROPERTY_NAME_MEMBEDDABLE,
          mockODataEntryProperties(JPATypeEmbeddableMock2.ENTITY_NAME));
    } else if (entityName.equals(JPATypeEmbeddableMock2.ENTITY_NAME)) {
      propertyMap.put(JPATypeEmbeddableMock2.PROPERTY_NAME_MFLOAT, VALUE_MFLOAT);
      propertyMap.put(JPATypeEmbeddableMock2.PROPERTY_NAME_MUUID, VALUE_UUID);
    }

    return propertyMap;
  }

  /**
   * Mock O data entry properties with complex type.
   *
   * @param entityName the entity name
   * @return the map
   */
  public static Map<String, Object> mockODataEntryPropertiesWithComplexType(final String entityName) {
    Map<String, Object> propertyMap = mockODataEntryProperties(entityName);
    propertyMap
        .put(JPATypeMock.PROPERTY_NAME_MCOMPLEXTYPE, mockODataEntryProperties(JPATypeEmbeddableMock.ENTITY_NAME));
    return propertyMap;
  }

  /**
   * Mock O data entry properties with inline.
   *
   * @param entityName the entity name
   * @return the map
   */
  public static Map<String, Object> mockODataEntryPropertiesWithInline(final String entityName) {
    Map<String, Object> propertyMap = mockODataEntryProperties(entityName);
    List<ODataEntry> relatedEntries = new ArrayList<ODataEntry>();
    relatedEntries.add(mockODataEntry(JPARelatedTypeMock.ENTITY_NAME));
    ODataFeed feed = EasyMock.createMock(ODataFeed.class);
    EasyMock.expect(feed.getEntries()).andReturn(relatedEntries);
    EasyMock.replay(feed);
    propertyMap.put(JPATypeMock.NAVIGATION_PROPERTY_X, feed);

    return propertyMap;

  }

  /**
   * Mock O data entry with inline.
   *
   * @param entityName the entity name
   * @return the o data entry
   */
  public static ODataEntry mockODataEntryWithInline(final String entityName) {
    ODataEntry oDataEntry = EasyMock.createMock(ODataEntry.class);
    EasyMock.expect(oDataEntry.getProperties()).andReturn(mockODataEntryPropertiesWithInline(entityName)).anyTimes();
    if (entityName.equals(JPATypeMock.ENTITY_NAME)) {
      List<String> links = new ArrayList<String>();
      links.add(JPATypeMock.ENTITY_NAME + "(" + ODataEntryMockUtil.VALUE_MINT + ")/"
          + JPATypeMock.NAVIGATION_PROPERTY_X);
      enhanceMockODataEntry(oDataEntry, true, links);
    } else {
      enhanceMockODataEntry(oDataEntry, false, new ArrayList<String>());
    }
    EasyMock.replay(oDataEntry);
    return oDataEntry;
  }

  /**
   * Enhance mock O data entry.
   *
   * @param oDataEntry the o data entry
   * @param hasInline the has inline
   * @param associationURIs the association UR is
   */
  private static void
      enhanceMockODataEntry(final ODataEntry oDataEntry, final boolean hasInline, final List<String> associationURIs) {
    EasyMock.expect(oDataEntry.containsInlineEntry()).andReturn(hasInline).anyTimes();
    EntryMetadata entryMetadata = EasyMock.createMock(EntryMetadata.class);
    if (hasInline) {
      EasyMock.expect(entryMetadata.getAssociationUris(JPATypeMock.NAVIGATION_PROPERTY_X)).andReturn(associationURIs)
          .anyTimes();
      EasyMock.expect(entryMetadata.getAssociationUris(JPATypeMock.NAVIGATION_PROPERTY_XS)).andReturn(
          new ArrayList<String>())
          .anyTimes();
    } else {
      EasyMock.expect(entryMetadata.getAssociationUris(EasyMock.isA(String.class))).andReturn(associationURIs)
          .anyTimes();
    }

    EasyMock.replay(entryMetadata);
    EasyMock.expect(oDataEntry.getMetadata()).andReturn(entryMetadata).anyTimes();
  }
}
