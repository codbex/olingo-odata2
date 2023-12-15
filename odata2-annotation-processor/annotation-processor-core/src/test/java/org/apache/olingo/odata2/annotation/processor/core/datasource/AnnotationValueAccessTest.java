/*
 * Copyright 2013 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.olingo.odata2.annotation.processor.core.datasource;

import junit.framework.Assert;

import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotImplementedException;
import org.junit.Test;
import org.mockito.Mockito;

// TODO: Auto-generated Javadoc
/**
 * The Class AnnotationValueAccessTest.
 */
public class AnnotationValueAccessTest {

  /**
   * Gets the property type.
   *
   * @return the property type
   * @throws ODataException the o data exception
   */
  @Test
  public void getPropertyType() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.name = "A Name";
    EdmProperty property = mockProperty("Name");

    Class<?> type = ava.getPropertyType(data, property);

    Assert.assertEquals(String.class, type);
  }

  /**
   * Gets the property type null data.
   *
   * @return the property type null data
   * @throws ODataException the o data exception
   */
  @Test
  public void getPropertyTypeNullData() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    EdmProperty property = mockProperty("Name");

    Class<?> type = ava.getPropertyType(null, property);

    Assert.assertNull(type);
  }

  /**
   * Gets the property type not annotated.
   *
   * @return the property type not annotated
   * @throws ODataException the o data exception
   */
  @Test(expected = ODataNotImplementedException.class)
  public void getPropertyTypeNotAnnotated() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    NotAnnotatedBean data = new NotAnnotatedBean();
    EdmProperty property = mockProperty("Name");

    Class<?> type = ava.getPropertyType(data, property);

    Assert.assertEquals(String.class, type);
  }

  /**
   * Gets the property value.
   *
   * @return the property value
   * @throws ODataException the o data exception
   */
  @Test
  public void getPropertyValue() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.name = "A Name";
    EdmProperty property = mockProperty("Name");

    Object value = ava.getPropertyValue(data, property);

    Assert.assertEquals(String.class, value.getClass());
    Assert.assertEquals("A Name", value);
  }

  /**
   * Gets the property value null.
   *
   * @return the property value null
   * @throws ODataException the o data exception
   */
  @Test
  public void getPropertyValueNull() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    EdmProperty property = mockProperty("Name");

    Object value = ava.getPropertyValue(data, property);

    Assert.assertNull(value);
  }

  /**
   * Gets the property value null data.
   *
   * @return the property value null data
   * @throws ODataException the o data exception
   */
  @Test
  public void getPropertyValueNullData() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = null;
    EdmProperty property = mockProperty("Name");

    Object value = ava.getPropertyValue(data, property);

    Assert.assertNull(value);
  }

  /**
   * Gets the property value not annotated.
   *
   * @return the property value not annotated
   * @throws ODataException the o data exception
   */
  @Test(expected = ODataNotImplementedException.class)
  public void getPropertyValueNotAnnotated() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    NotAnnotatedBean data = new NotAnnotatedBean();
    EdmProperty property = mockProperty("Name");

    Object value = ava.getPropertyValue(data, property);
    Assert.assertEquals("A Name", value);
  }

  /**
   * Sets the property value.
   *
   * @throws ODataException the o data exception
   */
  @Test
  public void setPropertyValue() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.name = "A Name";
    EdmProperty property = mockProperty("Name");

    Object value = "Another Name";
    ava.setPropertyValue(data, property, value);

    Assert.assertEquals("Another Name", data.name);
  }

  /**
   * Sets the property value not annotated.
   *
   * @throws ODataException the o data exception
   */
  @Test(expected = ODataNotImplementedException.class)
  public void setPropertyValueNotAnnotated() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    NotAnnotatedBean data = new NotAnnotatedBean();
    EdmProperty property = mockProperty("Name");

    Object value = "Another Name";
    ava.setPropertyValue(data, property, value);
  }

  /**
   * Sets the property value null.
   *
   * @throws ODataException the o data exception
   */
  @Test
  public void setPropertyValueNull() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.name = "A Name";
    EdmProperty property = mockProperty("Name");

    ava.setPropertyValue(data, property, null);

    Assert.assertNull(null, data.name);
  }

  /**
   * Sets the property data null.
   *
   * @throws ODataException the o data exception
   */
  @Test
  public void setPropertyDataNull() throws ODataException {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = null;
    EdmProperty property = mockProperty("Name");

    ava.setPropertyValue(data, property, null);

    // no exception is thrown, all fine
  }

  /**
   * Gets the mapping value.
   *
   * @return the mapping value
   * @throws Exception the exception
   */
  @Test
  public void getMappingValue() throws Exception {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = "mapped property value";
    EdmMapping mapping = mockMapping("MyMappedProperty");

    Object value = ava.getMappingValue(data, mapping);

    Assert.assertEquals(String.class, value.getClass());
    Assert.assertEquals("mapped property value", value);
  }

  /**
   * Gets the mapping value null mapping.
   *
   * @return the mapping value null mapping
   * @throws Exception the exception
   */
  @Test
  public void getMappingValueNullMapping() throws Exception {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = "property";
    EdmMapping mapping = null;

    Object value = ava.getMappingValue(data, mapping);

    Assert.assertNull(value);
  }

  /**
   * Gets the mapping value null value.
   *
   * @return the mapping value null value
   * @throws Exception the exception
   */
  @Test
  public void getMappingValueNullValue() throws Exception {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = null;
    EdmMapping mapping = mockMapping("MyMappedProperty");

    Object value = ava.getMappingValue(data, mapping);

    Assert.assertNull(value);
  }

  /**
   * Sets the mapping value.
   *
   * @throws Exception the exception
   */
  @Test
  public void setMappingValue() throws Exception {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = "mapped property value";
    EdmMapping mapping = mockMapping("MyMappedProperty");

    Object value = "Changed mapped property value";
    ava.setMappingValue(data, mapping, value);

    Assert.assertEquals("Changed mapped property value", data.myMappedProperty);
  }

  /**
   * Sets the mapping value null value.
   *
   * @throws Exception the exception
   */
  @Test
  public void setMappingValueNullValue() throws Exception {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = "mapped property value";
    EdmMapping mapping = mockMapping("MyMappedProperty");

    Object value = null;
    ava.setMappingValue(data, mapping, value);

    Assert.assertNull(data.myMappedProperty);
  }

  /**
   * Sets the mapping value null mapping.
   *
   * @throws Exception the exception
   */
  @Test
  public void setMappingValueNullMapping() throws Exception {
    AnnotationValueAccess ava = new AnnotationValueAccess();
    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = "mapped property value";
    EdmMapping mapping = null;

    Object value = null;
    ava.setMappingValue(data, mapping, value);

    Assert.assertEquals("mapped property value", data.myMappedProperty);
  }

  /**
   * Mock property.
   *
   * @param name the name
   * @return the edm property
   * @throws EdmException the edm exception
   */
  private EdmProperty mockProperty(final String name) throws EdmException {
    EdmProperty property = Mockito.mock(EdmProperty.class);
    Mockito.when(property.getName()).thenReturn(name);
    return property;
  }

  /**
   * Mock mapping.
   *
   * @param mimeTypeKey the mime type key
   * @return the edm mapping
   * @throws EdmException the edm exception
   */
  private EdmMapping mockMapping(final String mimeTypeKey) throws EdmException {
    EdmMapping mapping = Mockito.mock(EdmMapping.class);
    Mockito.when(mapping.getMediaResourceMimeTypeKey()).thenReturn(mimeTypeKey);
    return mapping;
  }

  /**
   * The Class SimpleEntity.
   */
  @EdmEntityType
  private class SimpleEntity {
    
    /** The name. */
    @org.apache.olingo.odata2.api.annotation.edm.EdmProperty
    String name;
    
    /** The my mapped property. */
    @org.apache.olingo.odata2.api.annotation.edm.EdmProperty
    String myMappedProperty;
  }

  /**
   * The Class NotAnnotatedBean.
   */
  private class NotAnnotatedBean {}
}
