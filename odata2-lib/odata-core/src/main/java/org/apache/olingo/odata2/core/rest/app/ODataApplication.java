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
package org.apache.olingo.odata2.core.rest.app;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

import org.apache.olingo.odata2.core.rest.ODataExceptionMapperImpl;
import org.apache.olingo.odata2.core.rest.ODataRootLocator;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataApplication.
 */
public class ODataApplication extends Application {

  /**
   * Gets the classes.
   *
   * @return the classes
   */
  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> classes = new HashSet<Class<?>>();
    classes.add(ODataRootLocator.class);
    classes.add(ODataExceptionMapperImpl.class);
    classes.add(MyProvider.class);
    return classes;
  }

  /**
   * Singletons are not recommended because they break the state less REST principle.
   *
   * @return the singletons
   */
  @Override
  public Set<Object> getSingletons() {
    return Collections.emptySet();
  }

  /**
   * The Class MyProvider.
   */
  @Provider
  @Produces({ "generic/value", "multipart/mixed" })
  public static class MyProvider implements MessageBodyWriter<String> {

    /**
     * Checks if is writeable.
     *
     * @param type the type
     * @param genericType the generic type
     * @param annotations the annotations
     * @param mediaType the media type
     * @return true, if is writeable
     */
    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations,
        final MediaType mediaType) {
      return (type == String.class);
    }

    /**
     * Gets the size.
     *
     * @param t the t
     * @param type the type
     * @param genericType the generic type
     * @param annotations the annotations
     * @param mediaType the media type
     * @return the size
     */
    @Override
    public long getSize(final String t, final Class<?> type, final Type genericType, final Annotation[] annotations,
        final MediaType mediaType) {
      return t.length();
    }

    /**
     * Write to.
     *
     * @param t the t
     * @param type the type
     * @param genericType the generic type
     * @param annotations the annotations
     * @param mediaType the media type
     * @param httpHeaders the http headers
     * @param entityStream the entity stream
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws WebApplicationException the web application exception
     */
    @Override
    public void writeTo(final String t, final Class<?> type, final Type genericType, final Annotation[] annotations,
        final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream)
        throws IOException, WebApplicationException {
      StringBuilder b = new StringBuilder();
      b.append(t);
      entityStream.write(b.toString().getBytes("UTF-8"));
      entityStream.flush();
    }
  }
}
