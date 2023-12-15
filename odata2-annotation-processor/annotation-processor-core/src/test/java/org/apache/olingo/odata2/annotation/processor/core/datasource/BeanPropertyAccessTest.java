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

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.junit.Test;
import org.mockito.Mockito;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanPropertyAccessTest.
 */
public class BeanPropertyAccessTest {

  /** The ava. */
  private final BeanPropertyAccess ava;

  /**
   * Instantiates a new bean property access test.
   */
  public BeanPropertyAccessTest() {
    ava = new BeanPropertyAccess();
  }

  /**
   * Gets the property type.
   *
   * @return the property type
   * @throws ODataException the o data exception
   */
  @Test
  public void getPropertyType() throws ODataException {
    SimpleEntity data = new SimpleEntity();
    data.name = "A Name";
    EdmProperty property = mockProperty("Name");

    Class<?> type = ava.getPropertyType(data, property);

    Assert.assertEquals(String.class, type);
  }

  /**
   * Gets the property type invalid property name.
   *
   * @return the property type invalid property name
   * @throws ODataException the o data exception
   */
  @Test(expected = ODataNotFoundException.class)
  public void getPropertyTypeInvalidPropertyName() throws ODataException {
    SimpleEntity data = new SimpleEntity();
    data.name = "A Name";
    EdmProperty property = mockProperty("invalid.Name");

    Object value = ava.getPropertyValue(data, property);
    Assert.assertNull(value);
  }

  /**
   * Gets the property value.
   *
   * @return the property value
   * @throws ODataException the o data exception
   */
  @Test
  public void getPropertyValue() throws ODataException {

    SimpleEntity data = new SimpleEntity();
    data.name = "A Name";
    EdmProperty property = mockProperty("Name");

    Object value = ava.getPropertyValue(data, property);

    Assert.assertEquals(String.class, value.getClass());
    Assert.assertEquals("A Name", value);
  }

  /**
   * Gets the property value boolean.
   *
   * @return the property value boolean
   * @throws ODataException the o data exception
   */
  @Test
  public void getPropertyValueBoolean() throws ODataException {

    SimpleEntity data = new SimpleEntity();
    EdmProperty property = mockProperty("BooleanProperty", true);

    Object value = ava.getPropertyValue(data, property);

    Assert.assertEquals(Boolean.class, value.getClass());
    Assert.assertEquals(true, value);
  }

  /**
   * Gets the property value null.
   *
   * @return the property value null
   * @throws ODataException the o data exception
   */
  @Test
  public void getPropertyValueNull() throws ODataException {

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

    SimpleEntity data = null;
    EdmProperty property = mockProperty("Name");

    Object value = ava.getPropertyValue(data, property);

    Assert.assertNull(value);
  }

  /**
   * Sets the property value.
   *
   * @throws ODataException the o data exception
   */
  @Test
  public void setPropertyValue() throws ODataException {

    SimpleEntity data = new SimpleEntity();
    data.name = "A Name";
    EdmProperty property = mockProperty("Name");

    Object value = "Another Name";
    ava.setPropertyValue(data, property, value);

    Assert.assertEquals("Another Name", data.name);
  }

  /**
   * Sets the property value null.
   *
   * @throws ODataException the o data exception
   */
  @Test
  public void setPropertyValueNull() throws ODataException {

    SimpleEntity data = new SimpleEntity();
    data.name = "A Name";
    EdmProperty property = mockProperty("Name");

    ava.setPropertyValue(data, property, null);

    Assert.assertNull(null, data.name);
  }

  /**
   * Gets the mapping value.
   *
   * @return the mapping value
   * @throws Exception the exception
   */
  @Test
  public void getMappingValue() throws Exception {

    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = "mapped property value";
    EdmMapping mapping = mockMapping("getMyMappedProperty");

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

    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = null;
    EdmMapping mapping = mockMapping("getMyMappedProperty");

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

    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = "mapped property value";
    EdmMapping mapping = mockMapping("getMyMappedProperty");

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

    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = "mapped property value";
    EdmMapping mapping = mockMapping("getMyMappedProperty");

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

    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = "mapped property value";
    EdmMapping mapping = null;

    Object value = null;
    ava.setMappingValue(data, mapping, value);

    Assert.assertEquals("mapped property value", data.myMappedProperty);
  }

  /**
   * Invalid method name.
   *
   * @throws Exception the exception
   */
  @Test(expected = ODataNotFoundException.class)
  public void invalidMethodName() throws Exception {

    SimpleEntity data = new SimpleEntity();
    data.myMappedProperty = null;
    EdmMapping mapping = mockMapping("MyMappedProperty");

    Object value = ava.getMappingValue(data, mapping);

    Assert.assertNull(value);
  }

  /**
   * Mock property.
   *
   * @param name the name
   * @return the edm property
   * @throws EdmException the edm exception
   */
  private EdmProperty mockProperty(final String name) throws EdmException {
    return mockProperty(name, false);
  }

  /**
   * Mock property.
   *
   * @param name the name
   * @param isBoolean the is boolean
   * @return the edm property
   * @throws EdmException the edm exception
   */
  private EdmProperty mockProperty(final String name, final boolean isBoolean) throws EdmException {
    EdmProperty property = Mockito.mock(EdmProperty.class);
    Mockito.when(property.getName()).thenReturn(name);
    Mockito.when(property.isSimple()).thenReturn(Boolean.TRUE);
    if (isBoolean) {
      Mockito.when(property.getType()).thenReturn(EdmSimpleTypeKind.Boolean.getEdmSimpleTypeInstance());
    }
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
  protected class SimpleEntity {
    
    /** The name. */
    private String name;
    
    /** The my mapped property. */
    private String myMappedProperty;

    /**
     * Gets the name.
     *
     * @return the name
     */
    public final String getName() {
      return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public final void setName(final String name) {
      this.name = name;
    }

    /**
     * Gets the my mapped property.
     *
     * @return the my mapped property
     */
    public final String getMyMappedProperty() {
      return myMappedProperty;
    }

    /**
     * Sets the my mapped property.
     *
     * @param myMappedProperty the new my mapped property
     */
    public final void setMyMappedProperty(final String myMappedProperty) {
      this.myMappedProperty = myMappedProperty;
    }

    /**
     * Checks if is boolean property.
     *
     * @return true, if is boolean property
     */
    public final boolean isBooleanProperty() {
      return true;
    }
  }
}
