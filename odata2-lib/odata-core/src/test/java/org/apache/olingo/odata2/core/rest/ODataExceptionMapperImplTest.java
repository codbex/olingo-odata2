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
package org.apache.olingo.odata2.core.rest;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.uri.UriSyntaxException;
import org.apache.olingo.odata2.core.exception.MessageService;
import org.apache.olingo.odata2.core.exception.ODataRuntimeException;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataExceptionMapperImplTest.
 */
public class ODataExceptionMapperImplTest extends BaseTest {

  /** The exception mapper. */
  ODataExceptionMapperImpl exceptionMapper;
  
  /** The uri. */
  URI uri;

  /**
   * Setup.
   *
   * @throws Exception the exception
   */
  @BeforeClass
  public static void setup() throws Exception {
    Map<String, String> prefixMap = new HashMap<String, String>();
    prefixMap.put("a", Edm.NAMESPACE_M_2007_08);
    XMLUnit.setXpathNamespaceContext(new SimpleNamespaceContext(prefixMap));
  }

  /**
   * Before.
   *
   * @throws URISyntaxException the URI syntax exception
   */
  @Before
  public void before() throws URISyntaxException {
    exceptionMapper = new ODataExceptionMapperImpl();
    exceptionMapper.httpHeaders = mock(HttpHeaders.class);
    exceptionMapper.uriInfo = mock(UriInfo.class);
    exceptionMapper.servletConfig = mock(ServletConfig.class);
    exceptionMapper.servletRequest = mock(HttpServletRequest.class);
    MultivaluedHashMap<String, String> map = new MultivaluedHashMap<String, String>();
    when(exceptionMapper.uriInfo.getQueryParameters()).thenReturn(map);
    uri = new URI("http://localhost:8080/ODataService.svc/Entity");
    when(exceptionMapper.uriInfo.getRequestUri()).thenReturn(uri);

    when(exceptionMapper.servletConfig.getInitParameter(ODataServiceFactory.FACTORY_LABEL)).thenReturn(
        ODataServiceFactoryImpl.class.getName());
    when(exceptionMapper.servletRequest.getAttribute(ODataServiceFactory.FACTORY_CLASSLOADER_LABEL)).thenReturn(
        ODataServiceFactoryImpl.class.getClassLoader());

    MultivaluedHashMap<String, String> httpHeaders = new MultivaluedHashMap<String, String>();
    when(exceptionMapper.httpHeaders.getRequestHeaders()).thenReturn(httpHeaders);

    disableLogging();

  }

  /**
   * Test extended O data error context.
   *
   * @throws Exception the exception
   */
  @Test
  public void testExtendedODataErrorContext() throws Exception {
    MultivaluedMap<String, String> value = new MultivaluedHashMap<String, String>();
    value.putSingle("Accept", "AcceptValue");
    value.put("AcceptMulti", Arrays.asList("AcceptValue_1", "AcceptValue_2"));
    when(exceptionMapper.httpHeaders.getRequestHeaders()).thenReturn(value);
    when(exceptionMapper.servletConfig.getInitParameter(ODataServiceFactory.FACTORY_LABEL)).thenReturn(
        ODataServiceFactoryWithCallbackImpl.class.getName());
    when(exceptionMapper.servletRequest.getAttribute(ODataServiceFactory.FACTORY_CLASSLOADER_LABEL)).thenReturn(null);
    Response response = exceptionMapper.toResponse(new Exception());

    // verify
    assertNotNull(response);
    assertEquals(HttpStatusCodes.BAD_REQUEST.getStatusCode(), response.getStatus());
    String errorMessage = (String) response.getEntity();
    assertEquals("bla", errorMessage);
    String contentTypeHeader = response.getHeaderString(org.apache.olingo.odata2.api.commons.HttpHeaders.CONTENT_TYPE);
    assertEquals("text/html", contentTypeHeader);
    //
    assertEquals(uri.toASCIIString(), response.getHeaderString("RequestUri"));
    assertEquals("[AcceptValue]", response.getHeaderString("Accept"));
    assertEquals("[AcceptValue_1, AcceptValue_2]", response.getHeaderString("AcceptMulti"));
  }

  /**
   * Servlet request with classloader.
   *
   * @throws Exception the exception
   */
  @Test
  public void servletRequestWithClassloader() throws Exception {
    MultivaluedMap<String, String> value = new MultivaluedHashMap<String, String>();
    value.putSingle("Accept", "AcceptValue");
    value.put("AcceptMulti", Arrays.asList("AcceptValue_1", "AcceptValue_2"));
    when(exceptionMapper.httpHeaders.getRequestHeaders()).thenReturn(value);
    when(exceptionMapper.servletConfig.getInitParameter(ODataServiceFactory.FACTORY_LABEL)).thenReturn(
        ODataServiceFactoryWithCallbackImpl.class.getName());
    when(exceptionMapper.servletRequest.getAttribute(ODataServiceFactory.FACTORY_CLASSLOADER_LABEL)).thenReturn(
        ODataServiceFactoryWithCallbackImpl.class.getClassLoader());
    Response response = exceptionMapper.toResponse(new Exception());

    // verify
    assertNotNull(response);
    assertEquals(HttpStatusCodes.BAD_REQUEST.getStatusCode(), response.getStatus());
    String errorMessage = (String) response.getEntity();
    assertEquals("bla", errorMessage);
  }

  /**
   * Dollar format json.
   *
   * @throws Exception the exception
   */
  @Test
  public void dollarFormatJson() throws Exception {
    MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();
    queryParameters.putSingle("$format", "json");
    when(exceptionMapper.uriInfo.getQueryParameters()).thenReturn(queryParameters);

    Response response = exceptionMapper.toResponse(new Exception("text"));
    assertNotNull(response);
    String contentTypeHeader = response.getHeaderString(org.apache.olingo.odata2.api.commons.HttpHeaders.CONTENT_TYPE);
    assertEquals("application/json", contentTypeHeader);
    String errorMessage = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertEquals("{\"error\":{\"code\":null,\"message\":{\"lang\":\"en\",\"value\":\"text\"}}}", errorMessage);
  }

  /**
   * Dollar format xml.
   *
   * @throws Exception the exception
   */
  @Test
  public void dollarFormatXml() throws Exception {
    MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();
    queryParameters.putSingle("$format", "xml");
    when(exceptionMapper.uriInfo.getQueryParameters()).thenReturn(queryParameters);

    Response response = exceptionMapper.toResponse(new Exception("text"));
    assertNotNull(response);
    String contentTypeHeader = response.getHeaderString(org.apache.olingo.odata2.api.commons.HttpHeaders.CONTENT_TYPE);
    assertEquals("application/xml", contentTypeHeader);
    String errorMessage = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertXpathExists("/a:error/a:code", errorMessage);
    assertXpathEvaluatesTo("text", "/a:error/a:message", errorMessage);
  }

  /**
   * Dollar format atom.
   *
   * @throws Exception the exception
   */
  @Test
  public void dollarFormatAtom() throws Exception {
    MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();
    queryParameters.putSingle("$format", "xml");
    when(exceptionMapper.uriInfo.getQueryParameters()).thenReturn(queryParameters);

    Response response = exceptionMapper.toResponse(new Exception("text"));
    assertNotNull(response);
    String contentTypeHeader = response.getHeaderString(org.apache.olingo.odata2.api.commons.HttpHeaders.CONTENT_TYPE);
    assertEquals("application/xml", contentTypeHeader);
    String errorMessage = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertXpathExists("/a:error/a:code", errorMessage);
    assertXpathEvaluatesTo("text", "/a:error/a:message", errorMessage);
  }

  /**
   * Dollar format unknown.
   *
   * @throws Exception the exception
   */
  @Test
  public void dollarFormatUnknown() throws Exception {
    MultivaluedMap<String, String> queryParameters = new MultivaluedHashMap<String, String>();
    queryParameters.putSingle("$format", "someFormat");
    when(exceptionMapper.uriInfo.getQueryParameters()).thenReturn(queryParameters);

    Response response = exceptionMapper.toResponse(new Exception("text"));
    assertNotNull(response);
    String contentTypeHeader = response.getHeaderString(org.apache.olingo.odata2.api.commons.HttpHeaders.CONTENT_TYPE);
    assertEquals("application/xml", contentTypeHeader);
    String errorMessage = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertXpathExists("/a:error/a:code", errorMessage);
    assertXpathEvaluatesTo("text", "/a:error/a:message", errorMessage);
  }

  /**
   * Test O data not found exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataNotFoundException() throws Exception {
    // prepare
    Exception exception = new ODataNotFoundException(ODataNotFoundException.ENTITY);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, MessageService.getMessage(Locale.ENGLISH, ODataNotFoundException.ENTITY).getText(),
        HttpStatusCodes.NOT_FOUND);
  }

  /**
   * Test entity provider exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testEntityProviderException() throws Exception {
    // prepare
    Exception exception = new EntityProviderException(EntityProviderException.INVALID_PROPERTY.addContent("unknown"));

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, MessageService.getMessage(Locale.ENGLISH,
        EntityProviderException.INVALID_PROPERTY.addContent("unknown")).getText(), HttpStatusCodes.BAD_REQUEST);
  }

  /**
   * Test O data not found exception de.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataNotFoundExceptionDe() throws Exception {
    // prepare
    Exception exception = new ODataNotFoundException(ODataNotFoundException.ENTITY);
    when(exceptionMapper.httpHeaders.getAcceptableLanguages())
        .thenReturn(Arrays.asList(Locale.GERMAN));

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, MessageService.getMessage(Locale.GERMAN, ODataNotFoundException.ENTITY).getText(),
        HttpStatusCodes.NOT_FOUND);
  }

  /**
   * Test wrapped O data not found exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testWrappedODataNotFoundException() throws Exception {
    // prepare
    Exception causeException = new ODataNotFoundException(ODataNotFoundException.ENTITY);
    String exceptionMessage = "Some odd exception";
    Exception exception = new ODataException(exceptionMessage, causeException);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, MessageService.getMessage(Locale.ENGLISH, ODataNotFoundException.ENTITY).getText(),
        HttpStatusCodes.NOT_FOUND);
  }

  /**
   * Test O data application exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataApplicationException() throws Exception {
    // prepare
    String message = "expected exception message";
    Exception exception = new ODataApplicationException(message, Locale.ENGLISH);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, message, HttpStatusCodes.INTERNAL_SERVER_ERROR);
  }

  /**
   * Test O data application exception wrapped.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataApplicationExceptionWrapped() throws Exception {
    // prepare
    String message = "expected exception message";
    Exception exception = new ODataException(new ODataApplicationException(message, Locale.ENGLISH));

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, message, HttpStatusCodes.INTERNAL_SERVER_ERROR);
  }

  /**
   * Test O data application exception with status.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataApplicationExceptionWithStatus() throws Exception {
    // prepare
    String message = "expected exception message";
    HttpStatusCodes status = HttpStatusCodes.OK;
    Exception exception = new ODataApplicationException(message, Locale.ENGLISH, status);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, message, status);
  }

  /**
   * Test O data application exception with status wrapped.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataApplicationExceptionWithStatusWrapped() throws Exception {
    // prepare
    String message = "expected exception message";
    HttpStatusCodes status = HttpStatusCodes.OK;
    Exception exception = new ODataException(new ODataApplicationException(message, Locale.ENGLISH, status));

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, message, status);
  }

  /**
   * Test uri parser exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testUriParserException() throws Exception {
    // prepare
    Exception exception = new UriSyntaxException(UriSyntaxException.EMPTYSEGMENT);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, MessageService.getMessage(Locale.ENGLISH, UriSyntaxException.EMPTYSEGMENT).getText(),
        HttpStatusCodes.BAD_REQUEST);
  }

  /**
   * Test uri parser exception wrapped.
   *
   * @throws Exception the exception
   */
  @Test
  public void testUriParserExceptionWrapped() throws Exception {
    // prepare
    Exception exception =
        new ODataException("outer exception", new UriSyntaxException(UriSyntaxException.EMPTYSEGMENT));

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, MessageService.getMessage(Locale.ENGLISH, UriSyntaxException.EMPTYSEGMENT).getText(),
        HttpStatusCodes.BAD_REQUEST);
  }

  /**
   * Test io exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testIoException() throws Exception {
    // prepare
    String message = "expected exception message";
    Exception exception = new IOException(message);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, message, HttpStatusCodes.INTERNAL_SERVER_ERROR);
  }

  /**
   * Test O data exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataException() throws Exception {
    // prepare
    String exceptionMessage = "Some odd exception";
    Exception exception = new ODataException(exceptionMessage);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, exceptionMessage, HttpStatusCodes.INTERNAL_SERVER_ERROR);
  }

  /**
   * Test not allowed jax rs exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testNotAllowedJaxRsException() throws Exception {
    // prepare
    String message = "The request dispatcher does not allow the HTTP method used for the request.";
    Exception exception =
        new ClientErrorException(Response.status(Response.Status.METHOD_NOT_ALLOWED).header(HttpHeaders.ALLOW, "GET")
            .build());

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, message, HttpStatusCodes.NOT_IMPLEMENTED);
  }

  /**
   * Test O data exception without text.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataExceptionWithoutText() throws Exception {
    final String text = null;
    final Exception exception = new ODataException(text);
    final Response response = exceptionMapper.toResponse(exception);

    assertNotNull(response);
    final String errorMessage = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertXpathExists("/a:error/a:message", errorMessage);
    assertXpathEvaluatesTo("en", "/a:error/a:message/@xml:lang", errorMessage);
  }

  /**
   * Test O data runtime exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataRuntimeException() throws Exception {
    // prepare
    String exceptionMessage = "Some odd runtime exception";
    Exception exception = new ODataRuntimeException(exceptionMessage);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    verifyResponse(response, exceptionMessage, HttpStatusCodes.INTERNAL_SERVER_ERROR);
  }

  /**
   * Test error code for application exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testErrorCodeForApplicationException() throws Exception {
    // prepare
    String errorCode = "ErrorCode";
    String message = "expected exception message";
    Exception exception =
        new ODataApplicationException(message, Locale.ENGLISH, HttpStatusCodes.INTERNAL_SERVER_ERROR, errorCode);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    String errorMessage = verifyResponse(response, message, HttpStatusCodes.INTERNAL_SERVER_ERROR);
    assertXpathEvaluatesTo(errorCode, "/a:error/a:code", errorMessage);
  }

  /**
   * Test O data not found exception with error code.
   *
   * @throws Exception the exception
   */
  @Test
  public void testODataNotFoundExceptionWithErrorCode() throws Exception {
    // prepare
    String errorCode = "ErrorCode";
    Exception exception = new ODataNotFoundException(ODataNotFoundException.ENTITY, errorCode);

    // execute
    Response response = exceptionMapper.toResponse(exception);

    // verify
    String errorMessage =
        verifyResponse(response, MessageService.getMessage(Locale.ENGLISH, ODataNotFoundException.ENTITY).getText(),
            HttpStatusCodes.NOT_FOUND);
    assertXpathEvaluatesTo(errorCode, "/a:error/a:code", errorMessage);
  }

  /**
   * Test callback.
   *
   * @throws Exception the exception
   */
  @Test
  public void testCallback() throws Exception {
    when(exceptionMapper.servletConfig.getInitParameter(ODataServiceFactory.FACTORY_LABEL)).thenReturn(
        ODataServiceFactoryWithCallbackImpl.class.getName());
    Response response = exceptionMapper.toResponse(new Exception());

    // verify
    assertNotNull(response);
    assertEquals(HttpStatusCodes.BAD_REQUEST.getStatusCode(), response.getStatus());
    String errorMessage = (String) response.getEntity();
    assertEquals("bla", errorMessage);
    String contentTypeHeader = response.getHeaderString(org.apache.olingo.odata2.api.commons.HttpHeaders.CONTENT_TYPE);
    assertEquals("text/html", contentTypeHeader);
  }

  /**
   * Verify response.
   *
   * @param response the response
   * @param message the message
   * @param statusCode the status code
   * @return the string
   * @throws Exception the exception
   */
  private String verifyResponse(final Response response, final String message, final HttpStatusCodes statusCode)
      throws Exception {
    assertNotNull(response);
    assertEquals(statusCode.getStatusCode(), response.getStatus());
    String errorXml = StringHelper.inputStreamToString((InputStream) response.getEntity());
    assertXpathExists("/a:error/a:code", errorXml);
    assertXpathEvaluatesTo(message, "/a:error/a:message", errorXml);
    return errorXml;
  }

}
