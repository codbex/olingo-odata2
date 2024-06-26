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
package org.apache.olingo.odata2.ref.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataHttpException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;

// TODO: Auto-generated Javadoc
/**
 * Data access.
 */
public class BeanPropertyAccess {

  /**
   * Gets the property value.
   *
   * @param <T> the generic type
   * @param data the data
   * @param property the property
   * @return the property value
   * @throws ODataException the o data exception
   */
  public <T> Object getPropertyValue(final T data, final EdmProperty property) throws ODataException {
    return getValue(data, getGetterMethodName(property));
  }

  /**
   * Sets the property value.
   *
   * @param <T> the generic type
   * @param <V> the value type
   * @param data the data
   * @param property the property
   * @param value the value
   * @throws ODataException the o data exception
   */
  public <T, V> void setPropertyValue(final T data, final EdmProperty property, final V value) throws ODataException {
    final String methodName = getSetterMethodName(getGetterMethodName(property));
    if (methodName != null) {
      setValue(data, methodName, value);
    }
  }

  /**
   * Gets the property type.
   *
   * @param <T> the generic type
   * @param data the data
   * @param property the property
   * @return the property type
   * @throws ODataException the o data exception
   */
  public <T> Class<?> getPropertyType(final T data, final EdmProperty property) throws ODataException {
    return getType(data, getGetterMethodName(property));
  }

  /**
   * Gets the mapping value.
   *
   * @param <T> the generic type
   * @param data the data
   * @param mapping the mapping
   * @return the mapping value
   * @throws ODataException the o data exception
   */
  public <T> Object getMappingValue(final T data, final EdmMapping mapping) throws ODataException {
    if (mapping != null && mapping.getMediaResourceMimeTypeKey() != null) {
      return getValue(data, mapping.getMediaResourceMimeTypeKey());
    }
    return null;
  }

  /**
   * Sets the mapping value.
   *
   * @param <T> the generic type
   * @param <V> the value type
   * @param data the data
   * @param mapping the mapping
   * @param value the value
   * @throws ODataException the o data exception
   */
  public <T, V> void setMappingValue(final T data, final EdmMapping mapping, final V value) throws ODataException {
    if (mapping != null && mapping.getMediaResourceMimeTypeKey() != null) {
      setValue(data, getSetterMethodName(mapping.getMediaResourceMimeTypeKey()), value);
    }
  }

  /**
   * Gets the getter method name.
   *
   * @param property the property
   * @return the getter method name
   * @throws EdmException the edm exception
   */
  private String getGetterMethodName(final EdmProperty property) throws EdmException {
    final String prefix = isBooleanProperty(property) ? "is" : "get";
    final String defaultMethodName = prefix + property.getName();
    return property.getMapping() == null || property.getMapping().getInternalName() == null ?
        defaultMethodName : property.getMapping().getInternalName();
  }

  /**
   * Checks if is boolean property.
   *
   * @param property the property
   * @return true, if is boolean property
   * @throws EdmException the edm exception
   */
  private boolean isBooleanProperty(final EdmProperty property) throws EdmException {
    return property.isSimple()
        && property.getType() == EdmSimpleTypeKind.Boolean.getEdmSimpleTypeInstance();
  }

  /**
   * Gets the setter method name.
   *
   * @param getterMethodName the getter method name
   * @return the setter method name
   */
  private String getSetterMethodName(final String getterMethodName) {
    return getterMethodName.contains(".") ?
        null : getterMethodName.replaceFirst("^is", "set").replaceFirst("^get", "set");
  }

  /**
   * Gets the value.
   *
   * @param <T> the generic type
   * @param data the data
   * @param methodName the method name
   * @return the value
   * @throws ODataNotFoundException the o data not found exception
   */
  private <T> Object getValue(final T data, final String methodName) throws ODataNotFoundException {
    Object dataObject = data;

    for (final String method : methodName.split("\\.", -1)) {
      if (dataObject != null) {
        try {
          dataObject = dataObject.getClass().getMethod(method).invoke(dataObject);
        } catch (SecurityException e) {
          throw new ODataNotFoundException(ODataHttpException.COMMON, e);
        } catch (NoSuchMethodException e) {
          throw new ODataNotFoundException(ODataHttpException.COMMON, e);
        } catch (IllegalArgumentException e) {
          throw new ODataNotFoundException(ODataHttpException.COMMON, e);
        } catch (IllegalAccessException e) {
          throw new ODataNotFoundException(ODataHttpException.COMMON, e);
        } catch (InvocationTargetException e) {
          throw new ODataNotFoundException(ODataHttpException.COMMON, e);
        }
      }
    }

    return dataObject;
  }

  /**
   * Sets the value.
   *
   * @param <T> the generic type
   * @param <V> the value type
   * @param data the data
   * @param methodName the method name
   * @param value the value
   * @throws ODataNotFoundException the o data not found exception
   */
  private <T, V> void setValue(final T data, final String methodName, final V value)
      throws ODataNotFoundException {
    try {
      boolean found = false;
      for (final Method method : Arrays.asList(data.getClass().getMethods())) {
        if (method.getName().equals(methodName)) {
          found = true;
          final Class<?> type = method.getParameterTypes()[0];
          if (value == null) {
            if (type.equals(byte.class) || type.equals(short.class) || type.equals(int.class)
                || type.equals(long.class) || type.equals(char.class)) {
              method.invoke(data, 0);
            } else if (type.equals(float.class) || type.equals(double.class)) {
              method.invoke(data, 0.0);
            } else if (type.equals(boolean.class)) {
              method.invoke(data, false);
            } else {
              method.invoke(data, value);
            }
          } else {
            method.invoke(data, value);
          }
          break;
        }
      }
      if (!found) {
        throw new ODataNotFoundException(null);
      }
    } catch (SecurityException e) {
      throw new ODataNotFoundException(null, e);
    } catch (IllegalArgumentException e) {
      throw new ODataNotFoundException(null, e);
    } catch (IllegalAccessException e) {
      throw new ODataNotFoundException(null, e);
    } catch (InvocationTargetException e) {
      throw new ODataNotFoundException(null, e);
    }
  }

  /**
   * Gets the type.
   *
   * @param <T> the generic type
   * @param data the data
   * @param methodName the method name
   * @return the type
   * @throws ODataNotFoundException the o data not found exception
   */
  private <T> Class<?> getType(final T data, final String methodName) throws ODataNotFoundException {
    if (data == null) {
      throw new ODataNotFoundException(ODataHttpException.COMMON);
    }

    Class<?> type = data.getClass();
    for (final String method : methodName.split("\\.", -1)) {
      try {
        type = type.getMethod(method).getReturnType();
        if (type.isPrimitive()) {
          if (type == boolean.class) {
            type = Boolean.class;
          } else if (type == byte.class) {
            type = Byte.class;
          } else if (type == short.class) {
            type = Short.class;
          } else if (type == int.class) {
            type = Integer.class;
          } else if (type == long.class) {
            type = Long.class;
          } else if (type == float.class) {
            type = Float.class;
          } else if (type == double.class) {
            type = Double.class;
          }
        }
      } catch (final SecurityException e) {
        throw new ODataNotFoundException(ODataHttpException.COMMON, e);
      } catch (final NoSuchMethodException e) {
        throw new ODataNotFoundException(ODataHttpException.COMMON, e);
      }
    }
    return type;
  }
}
