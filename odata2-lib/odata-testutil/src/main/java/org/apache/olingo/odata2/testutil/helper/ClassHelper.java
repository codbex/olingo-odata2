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
package org.apache.olingo.odata2.testutil.helper;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassHelper.
 */
public class ClassHelper {
  
  /** The Constant JAVA_FILE_FILTER. */
  public static final FileFilter JAVA_FILE_FILTER = new FileFilter() {
    @Override
    public boolean accept(final File path) {
      return path.isFile() && path.getName().toLowerCase(Locale.ROOT).endsWith("class");
    }
  };
  
  /** The Constant CLASS_FILE_ENDING. */
  public static final String CLASS_FILE_ENDING = ".class";
  
  /** The Constant EMPTY_CLASS_ARRAY. */
  private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
  
  /** The Constant EMPTY_OBJECT_ARRAY. */
  private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

  /**
   * Gets the class instances.
   *
   * @param <T> the generic type
   * @param exClasses the ex classes
   * @return the class instances
   */
  public static <T> List<T> getClassInstances(final List<Class<T>> exClasses) {
    return getClassInstances(exClasses, EMPTY_CLASS_ARRAY, EMPTY_OBJECT_ARRAY);
  }

  /**
   * Gets the class instances.
   *
   * @param <T> the generic type
   * @param exClasses the ex classes
   * @param ctorParameters the ctor parameters
   * @return the class instances
   */
  public static <T> List<T> getClassInstances(final List<Class<T>> exClasses, final Object... ctorParameters) {
    final List<Class<?>> ctorParameterClasses = new ArrayList<Class<?>>();
    for (final Object object : ctorParameters) {
      ctorParameterClasses.add(object.getClass());
    }

    return getClassInstances(exClasses, ctorParameterClasses.toArray(new Class<?>[0]), ctorParameters);
  }

  /**
   * Gets the class instances.
   *
   * @param <T> the generic type
   * @param exClasses the ex classes
   * @param ctorParameterClasses the ctor parameter classes
   * @param ctorParameters the ctor parameters
   * @return the class instances
   */
  public static <T> List<T> getClassInstances(final List<Class<T>> exClasses, final Class<?>[] ctorParameterClasses,
      final Object[] ctorParameters) {

    final List<T> toTestExceptions = new ArrayList<T>();
    for (final Class<T> clazz : exClasses) {
      if (isNotAbstractOrInterface(clazz)) {
        Constructor<T> ctor;
        try {
          ctor = clazz.getConstructor(ctorParameterClasses);
          final T ex = ctor.newInstance(ctorParameters);
          toTestExceptions.add(ex);
        } catch (final SecurityException e) {
          continue;
        } catch (final NoSuchMethodException e) {
          continue;
        } catch (final IllegalArgumentException e) {
          continue;
        } catch (final InstantiationException e) {
          continue;
        } catch (final IllegalAccessException e) {
          continue;
        } catch (final InvocationTargetException e) {
          continue;
        }
      }
    }
    return toTestExceptions;
  }

  /**
   * Checks if is not abstract or interface.
   *
   * @param clazz the clazz
   * @return true, if is not abstract or interface
   */
  public static boolean isNotAbstractOrInterface(final Class<?> clazz) {
    return !Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers());
  }

  /**
   * Gets the assignable classes.
   *
   * @param <T> the generic type
   * @param packageName the package name
   * @param assignableToClass the assignable to class
   * @return the assignable classes
   */
  public static <T> List<Class<T>> getAssignableClasses(final String packageName, final Class<T> assignableToClass) {
    final List<Class<T>> foundClasses = new ArrayList<Class<T>>();
    final URL url = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));

    final File path = new File(url.getPath());
    if (path.exists()) {
      final File[] javaFiles = path.listFiles(JAVA_FILE_FILTER);
      for (final File file : javaFiles) {
        final Class<T> clazz = getClass(file, packageName, assignableToClass);
        if (clazz != null) {
          foundClasses.add(clazz);
        }
      }
    }

    return foundClasses;
  }

  /**
   * Gets the class.
   *
   * @param <T> the generic type
   * @param file the file
   * @param packageName the package name
   * @param clazz the clazz
   * @return the class
   */
  public static <T> Class<T> getClass(final File file, final String packageName, final Class<T> clazz) {
    String className = file.getName();
    if (className.endsWith(CLASS_FILE_ENDING)) {
      className = className.substring(0, className.length() - CLASS_FILE_ENDING.length());
    }

    return getClass(packageName + "." + className, clazz);
  }

  /**
   * Gets the class.
   *
   * @param <T> the generic type
   * @param className the class name
   * @param clazz the clazz
   * @return the class
   */
  @SuppressWarnings("unchecked")
  public static <T> Class<T> getClass(final String className, final Class<T> clazz) {
    try {
      final Class<?> clazzForName = Class.forName(className);
      if (clazz.isAssignableFrom(clazzForName)) {
        return (Class<T>) clazzForName;
      }
    } catch (final ClassNotFoundException e) {
      return null;
    }
    return null;
  }
}
