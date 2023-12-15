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
package org.apache.olingo.odata2.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.UriInfo;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.core.rest.ODataErrorHandlerCallbackImpl;
import org.apache.olingo.odata2.core.rest.ODataExceptionWrapper;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Test;
import org.mockito.Mockito;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataExceptionWrapperTest.
 */
public class ODataExceptionWrapperTest extends BaseTest {

  /**
   * Wrap an exception and verify that {@link PathInfo} is available and filled with correct values.
   *
   * @throws Exception the exception
   */
  @Test
  public void testCallbackPathInfoAvailable() throws Exception {
    ODataContextImpl context = getMockedContext("http://localhost:80/test", "ODataServiceRoot");
    ODataErrorCallback errorCallback = new ODataErrorCallback() {
      @Override
      public ODataResponse handleError(final ODataErrorContext context) throws ODataApplicationException {
        PathInfo pathInfo = context.getPathInfo();
        assertEquals("ODataServiceRoot", pathInfo.getServiceRoot().toString());
        assertEquals("http://localhost:80/test", pathInfo.getRequestUri().toString());
        return ODataResponse.entity("bla").status(HttpStatusCodes.BAD_REQUEST).contentHeader("text/html").build();
      }
    };
    when(context.getServiceFactory()).thenReturn(new MapperServiceFactory(errorCallback));

    //
    Map<String, String> queryParameters = Collections.emptyMap();
    List<String> acceptContentTypes = Arrays.asList("text/html");
    ODataExceptionWrapper exceptionWrapper = createWrapper(context, queryParameters, acceptContentTypes);
    ODataResponse response = exceptionWrapper.wrapInExceptionResponse(new Exception());

    // verify
    assertNotNull(response);
    assertEquals(HttpStatusCodes.BAD_REQUEST.getStatusCode(), response.getStatus().getStatusCode());
    String errorMessage = (String) response.getEntity();
    assertEquals("bla", errorMessage);
    String contentTypeHeader = response.getContentHeader();
    assertEquals("text/html", contentTypeHeader);
  }
  
  /**
   * Test callback with locales.
   *
   * @throws Exception the exception
   */
  @Test
  public void testCallbackWithLocales() throws Exception {
    ODataContextImpl context = getMockedContextWithLocale("http://localhost:80/test", "ODataServiceRoot");
    ODataErrorCallback errorCallback = new ODataErrorCallback() {
      @Override
      public ODataResponse handleError(final ODataErrorContext context) throws ODataApplicationException {
        PathInfo pathInfo = context.getPathInfo();
        assertEquals("ODataServiceRoot", pathInfo.getServiceRoot().toString());
        assertEquals("http://localhost:80/test", pathInfo.getRequestUri().toString());
        assertEquals("de", context.getLocale().getLanguage());
        assertEquals("DE", context.getLocale().getCountry());
        return ODataResponse.entity("bla").status(HttpStatusCodes.BAD_REQUEST).contentHeader("text/html").build();
      }
    };
    when(context.getServiceFactory()).thenReturn(new MapperServiceFactory(errorCallback));

    //
    Map<String, String> queryParameters = Collections.emptyMap();
    List<String> acceptContentTypes = Arrays.asList("text/html");
    ODataExceptionWrapper exceptionWrapper = createWrapper(context, queryParameters, acceptContentTypes);
    ODataResponse response = exceptionWrapper.wrapInExceptionResponse(
        new ODataApplicationException("Error",Locale.GERMANY));

    // verify
    assertNotNull(response);
    assertEquals(HttpStatusCodes.BAD_REQUEST.getStatusCode(), response.getStatus().getStatusCode());
    String errorMessage = (String) response.getEntity();
    assertEquals("bla", errorMessage);
    String contentTypeHeader = response.getContentHeader();
    assertEquals("text/html", contentTypeHeader);
  }
  
  /**
   * Test callback with locales 1.
   *
   * @throws Exception the exception
   */
  @Test
  public void testCallbackWithLocales1() throws Exception {
    UriInfo uriInfo = getMockedUriInfo();
    HttpHeaders httpHeaders = Mockito.mock(HttpHeaders.class);
    List<Locale> locales = new ArrayList<Locale>();
    locales.add(Locale.GERMANY);
    locales.add(Locale.FRANCE);
    when(httpHeaders.getAcceptableLanguages()).thenReturn(locales);
    
    ODataErrorCallback errorCallback = new ODataErrorCallback() {
      @Override
      public ODataResponse handleError(final ODataErrorContext context) throws ODataApplicationException {
        assertEquals("de", context.getLocale().getLanguage());
        assertEquals("DE", context.getLocale().getCountry());
        return ODataResponse.entity("bla").status(HttpStatusCodes.BAD_REQUEST).contentHeader("text/html").build();
      }
    };

    ODataExceptionWrapper exceptionWrapper = createWrapper1(uriInfo, httpHeaders, errorCallback);
    ODataResponse response = exceptionWrapper.wrapInExceptionResponse(
        new ODataApplicationException("Error",Locale.GERMANY));

    // verify
    assertNotNull(response);
    assertEquals(HttpStatusCodes.BAD_REQUEST.getStatusCode(), response.getStatus().getStatusCode());
    String errorMessage = (String) response.getEntity();
    assertEquals("bla", errorMessage);
    String contentTypeHeader = response.getContentHeader();
    assertEquals("text/html", contentTypeHeader);
  }

  /**
   * Gets the mocked uri info.
   *
   * @return the mocked uri info
   */
  private UriInfo getMockedUriInfo() {
    UriInfo uriInfo = Mockito.mock(UriInfo.class);
    when(uriInfo.getRequestUri()).thenReturn(URI.create("http://localhost:80/test"));
    return uriInfo;
  }

  /**
   * Creates the wrapper 1.
   *
   * @param uriInfo the uri info
   * @param httpHeaders the http headers
   * @param errorCallback the error callback
   * @return the o data exception wrapper
   * @throws URISyntaxException the URI syntax exception
   */
  private ODataExceptionWrapper createWrapper1(final UriInfo uriInfo,
      final HttpHeaders httpHeaders, ODataErrorCallback errorCallback) throws URISyntaxException {
    ODataExceptionWrapper exceptionWrapper = new ODataExceptionWrapper(uriInfo, httpHeaders, errorCallback);

    return exceptionWrapper;
  }
  
  /**
   * Creates the wrapper.
   *
   * @param context the context
   * @param queryParameters the query parameters
   * @param acceptContentTypes the accept content types
   * @return the o data exception wrapper
   * @throws URISyntaxException the URI syntax exception
   */
  private ODataExceptionWrapper createWrapper(final ODataContextImpl context,
      final Map<String, String> queryParameters, final List<String> acceptContentTypes) throws URISyntaxException {
    ODataExceptionWrapper exceptionWrapper = new ODataExceptionWrapper(context, queryParameters, acceptContentTypes);

    return exceptionWrapper;
  }

  /**
   * Gets the mocked context.
   *
   * @param requestUri the request uri
   * @param serviceRoot the service root
   * @return the mocked context
   * @throws ODataException the o data exception
   * @throws URISyntaxException the URI syntax exception
   */
  private ODataContextImpl getMockedContext(final String requestUri, final String serviceRoot) throws ODataException,
      URISyntaxException {
    ODataContextImpl context = Mockito.mock(ODataContextImpl.class);
    PathInfoImpl pathInfo = new PathInfoImpl();
    pathInfo.setRequestUri(new URI(requestUri));
    pathInfo.setServiceRoot(new URI(serviceRoot));
    when(context.getPathInfo()).thenReturn(pathInfo);
    when(context.getRequestHeaders()).thenReturn(new MultivaluedHashMap<String, String>());
    return context;
  }
  
  /**
   * Gets the mocked context with locale.
   *
   * @param requestUri the request uri
   * @param serviceRoot the service root
   * @return the mocked context with locale
   * @throws ODataException the o data exception
   * @throws URISyntaxException the URI syntax exception
   */
  private ODataContextImpl getMockedContextWithLocale(final String requestUri, 
      final String serviceRoot) throws ODataException,
  URISyntaxException {
    ODataContextImpl context = Mockito.mock(ODataContextImpl.class);
    PathInfoImpl pathInfo = new PathInfoImpl();
    pathInfo.setRequestUri(new URI(requestUri));
    pathInfo.setServiceRoot(new URI(serviceRoot));
    when(context.getPathInfo()).thenReturn(pathInfo);
    MultivaluedHashMap<String,String> headers = new MultivaluedHashMap<String, String>();
    headers.add("Accept-Language","de-DE, de;q=0.7");
    when(context.getRequestHeaders()).thenReturn(headers);
    List<Locale> locales = new ArrayList<Locale>();
    locales.add(Locale.GERMANY);
    when(context.getAcceptableLanguages()).thenReturn(locales);
    return context;
    }

  /**
   * A factory for creating MapperService objects.
   */
  public static final class MapperServiceFactory extends ODataServiceFactory {
    
    /** The error callback. */
    private ODataErrorCallback errorCallback;

    /**
     * Instantiates a new mapper service factory.
     *
     * @param callback the callback
     */
    public MapperServiceFactory(final ODataErrorCallback callback) {
      errorCallback = callback;
    }

    /**
     * Creates a new MapperService object.
     *
     * @param ctx the ctx
     * @return the o data service
     * @throws ODataException the o data exception
     */
    @Override
    public ODataService createService(final ODataContext ctx) throws ODataException {
      return null;
    }

    /**
     * Gets the callback.
     *
     * @param <T> the generic type
     * @param callbackInterface the callback interface
     * @return the callback
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ODataCallback> T getCallback(final Class<T> callbackInterface) {
      if (callbackInterface == ODataErrorCallback.class) {
        if (errorCallback == null) {
          return (T) new ODataErrorHandlerCallbackImpl();
        }
        return (T) errorCallback;
      }
      // only error callbacks are handled here
      return null;
    }
  }
}
