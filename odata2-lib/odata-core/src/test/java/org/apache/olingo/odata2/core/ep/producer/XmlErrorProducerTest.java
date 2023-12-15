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
package org.apache.olingo.odata2.core.ep.producer;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathNotExists;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.odata2.api.ODataServiceVersion;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.ODataHttpHeaders;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.ep.AbstractXmlProducerTestHelper;
import org.apache.olingo.odata2.core.ep.AtomEntityProvider;
import org.apache.olingo.odata2.core.ep.ProviderFacadeImpl;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.custommonkey.xmlunit.SimpleNamespaceContext;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlErrorProducerTest.
 */
public class XmlErrorProducerTest extends AbstractXmlProducerTestHelper {

  /** The Constant contentType. */
  private static final String contentType = ContentType.APPLICATION_XML.toContentTypeString();
  
  /** The Constant expectedStatus. */
  private static final HttpStatusCodes expectedStatus = HttpStatusCodes.INTERNAL_SERVER_ERROR;

  /**
   * Instantiates a new xml error producer test.
   *
   * @param type the type
   */
  public XmlErrorProducerTest(final StreamWriterImplType type) {
    super(type);
  }

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
   * Via runtime delegate.
   *
   * @throws Exception the exception
   */
  @Test
  public void viaRuntimeDelegate() throws Exception {
    ODataErrorContext context = new ODataErrorContext();
    context.setContentType(contentType);
    context.setHttpStatus(expectedStatus);
    context.setErrorCode(null);
    context.setMessage(null);
    context.setLocale(null);
    context.setInnerError(null);
    ODataResponse response = EntityProvider.writeErrorDocument(context);
    String errorXml = verifyResponse(response);
    verifyXml(null, null, null, null, errorXml);

    context.setErrorCode("a");
    context.setMessage("a");
    context.setLocale(Locale.GERMAN);
    context.setInnerError("a");
    response = EntityProvider.writeErrorDocument(context);
    errorXml = verifyResponse(response);
    verifyXml("a", "a", Locale.GERMAN, "a", errorXml);

    context.setErrorCode(null);
    context.setInnerError(null);
    response = EntityProvider.writeErrorDocument(context);
    errorXml = verifyResponse(response);
    verifyXml(null, "a", Locale.GERMAN, null, errorXml);
  }

  /**
   * Via provider facade impl.
   *
   * @throws Exception the exception
   */
  @Test
  public void viaProviderFacadeImpl() throws Exception {
    String errorCode = null;
    String message = null;
    Locale locale = null;
    String innerError = null;

    ODataErrorContext ctx = new ODataErrorContext();
    ctx.setContentType(contentType);
    ctx.setErrorCode(errorCode);
    ctx.setHttpStatus(expectedStatus);
    ctx.setLocale(locale);
    ctx.setMessage(message);

    ODataResponse response = new ProviderFacadeImpl().writeErrorDocument(ctx);
    String errorXml = verifyResponse(response);
    verifyXml(errorCode, message, locale, innerError, errorXml);

    errorCode = "a";
    message = "a";
    locale = Locale.GERMAN;
    innerError = "a";

    ctx = new ODataErrorContext();
    ctx.setContentType(contentType);
    ctx.setErrorCode(errorCode);
    ctx.setHttpStatus(expectedStatus);
    ctx.setLocale(locale);
    ctx.setMessage(message);
    ctx.setInnerError(innerError);

    response = new ProviderFacadeImpl().writeErrorDocument(ctx);
    errorXml = verifyResponse(response);
    verifyXml(errorCode, message, locale, innerError, errorXml);

    errorCode = null;
    message = "a";
    locale = Locale.GERMAN;
    innerError = null;

    ctx = new ODataErrorContext();
    ctx.setContentType(contentType);
    ctx.setErrorCode(errorCode);
    ctx.setHttpStatus(expectedStatus);
    ctx.setLocale(locale);
    ctx.setMessage(message);
    ctx.setInnerError(innerError);

    response = new ProviderFacadeImpl().writeErrorDocument(ctx);
    errorXml = verifyResponse(response);
    verifyXml(errorCode, message, locale, innerError, errorXml);
  }

  /**
   * With error details.
   *
   * @throws Exception the exception
   */
  @Test
  public void withErrorDetails() throws Exception {
    String errorCode = "500";
    String message = "Main message";
    String detailedMessage = "Detailed message";
    String severity = "error";
    String target = "element1";
    Locale locale = Locale.GERMAN;
    String innerError = "Inner Error";

    ODataErrorContext detailed = new ODataErrorContext();
    detailed.setErrorCode(errorCode);
    detailed.setMessage(detailedMessage);
    detailed.setSeverity(severity);
    detailed.setTarget(target);

    ODataErrorContext ctx = new ODataErrorContext();
    ctx.setContentType(contentType);
    ctx.setErrorCode(errorCode);
    ctx.setHttpStatus(expectedStatus);
    ctx.setLocale(locale);
    ctx.setMessage(message);
    ctx.setInnerError(innerError);
    ctx.setErrorDetails(Collections.singletonList(detailed));

    ODataResponse response = new ProviderFacadeImpl().writeErrorDocument(ctx);
    String errorXml = verifyResponse(response);
    System.out.println(errorXml);
    verifyXml(errorCode, message, locale, innerError, errorXml);
    verifyDetailsXml(errorCode, detailedMessage, severity, target, errorXml);
  }

  /**
   * Normal.
   *
   * @throws Exception the exception
   */
  @Test
  public void normal() throws Exception {
    serializeError(null, "Message", null, Locale.GERMAN);
    serializeError(null, "Message", null, Locale.ENGLISH);
    serializeError(null, "Message", null, Locale.CANADA);
    serializeError(null, "Message", null, Locale.FRANCE);
    serializeError(null, "Message", null, Locale.CHINA);
  }

  /**
   * None.
   *
   * @throws Exception the exception
   */
  @Test
  public void none() throws Exception {
    serializeError(null, null, null, null);
  }

  /**
   * Only error code.
   *
   * @throws Exception the exception
   */
  @Test
  public void onlyErrorCode() throws Exception {
    serializeError("ErrorCode", null, null, null);
  }

  /**
   * Only message.
   *
   * @throws Exception the exception
   */
  @Test
  public void onlyMessage() throws Exception {
    serializeError(null, "message", null, null);
  }

  /**
   * Only inner error.
   *
   * @throws Exception the exception
   */
  @Test
  public void onlyInnerError() throws Exception {
    serializeError(null, null, "InnerError", null);
  }

  /**
   * Only locale.
   *
   * @throws Exception the exception
   */
  @Test
  public void onlyLocale() throws Exception {
    serializeError(null, null, null, Locale.GERMANY);
  }

  /**
   * Without message.
   *
   * @throws Exception the exception
   */
  @Test
  public void withoutMessage() throws Exception {
    serializeError("ErrorCode", null, null, Locale.GERMAN);
  }

  /**
   * Normal with error code variation.
   *
   * @throws Exception the exception
   */
  @Test
  public void normalWithErrorCodeVariation() throws Exception {
    serializeError("", "Message", null, Locale.GERMAN);
    serializeError("  ", "Message", null, Locale.GERMAN);
  }

  /**
   * Normal with inner error variation.
   *
   * @throws Exception the exception
   */
  @Test
  public void normalWithInnerErrorVariation() throws Exception {
    serializeError(null, "Message", "", Locale.GERMAN);
    serializeError(null, "Message", "  ", Locale.GERMAN);
  }

  /**
   * All.
   *
   * @throws Exception the exception
   */
  @Test
  public void all() throws Exception {
    serializeError("ErrorCode", "Message", "InnerError", Locale.GERMAN);
    serializeError("ErrorCode", "Message", "InnerError", Locale.ENGLISH);
    serializeError("ErrorCode", "Message", "InnerError", Locale.CANADA);
    serializeError("ErrorCode", "Message", "InnerError", Locale.FRANCE);
    serializeError("ErrorCode", "Message", "InnerError", Locale.CHINA);
  }

  /**
   * Gets the lang.
   *
   * @param locale the locale
   * @return the lang
   */
  private String getLang(final Locale locale) {
    if (locale == null) {
      return "";
    }
    if (locale.getCountry().isEmpty()) {
      return locale.getLanguage();
    } else {
      return locale.getLanguage() + "-" + locale.getCountry();
    }
  }

  /**
   * Serialize error.
   *
   * @param errorCode the error code
   * @param message the message
   * @param innerError the inner error
   * @param locale the locale
   * @throws Exception the exception
   */
  private void
      serializeError(final String errorCode, final String message, final String innerError, final Locale locale)
          throws Exception {
    ODataErrorContext context = new ODataErrorContext();
    context.setHttpStatus(expectedStatus);
    context.setErrorCode(errorCode);
    context.setMessage(message);
    context.setLocale(locale);
    context.setInnerError(innerError);
    ODataResponse response =
        new AtomEntityProvider().writeErrorDocument(context);
    String errorXml = verifyResponse(response);
    verifyXml(errorCode, message, locale, innerError, errorXml);
  }

  /**
   * Verify response.
   *
   * @param response the response
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String verifyResponse(final ODataResponse response) throws IOException {
    assertNotNull(response);
    assertNotNull(response.getEntity());
    assertNull("EntityProvider should not set content header", response.getContentHeader());
    assertEquals(expectedStatus, response.getStatus());
    assertNotNull(response.getHeader(ODataHttpHeaders.DATASERVICEVERSION));
    assertEquals(ODataServiceVersion.V10, response.getHeader(ODataHttpHeaders.DATASERVICEVERSION));

    String xmlString = StringHelper.inputStreamToString((InputStream) response.getEntity());
    return xmlString;
  }

  /**
   * Verify xml.
   *
   * @param errorCode the error code
   * @param message the message
   * @param locale the locale
   * @param innerError the inner error
   * @param errorXml the error xml
   * @throws Exception the exception
   */
  private void verifyXml(final String errorCode, final String message, final Locale locale, final String innerError,
      final String errorXml) throws Exception {

    assertXpathExists("/a:error", errorXml);

    if (errorCode != null) {
      assertXpathEvaluatesTo(errorCode, "/a:error/a:code", errorXml);
    } else {
      assertXpathExists("/a:error/a:code", errorXml);
    }
    if (message != null) {
      assertXpathEvaluatesTo(message, "/a:error/a:message", errorXml);
      assertXpathExists("/a:error/a:message[@xml:lang=\"" + getLang(locale) + "\"]", errorXml);
    } else {
      if (locale == null) {
        assertXpathExists("/a:error/a:message[@xml:lang='']", errorXml);
      } else {
        assertXpathExists("/a:error/a:message[@xml:lang=\"" + getLang(locale) + "\"]", errorXml);
      }
    }

    if (innerError == null) {
      assertXpathNotExists("/a:error/a:innererror", errorXml);
    } else {
      assertXpathExists("/a:error/a:innererror", errorXml);
    }
  }

  /**
   * Verify details xml.
   *
   * @param errorCode the error code
   * @param message the message
   * @param severity the severity
   * @param target the target
   * @param errorXml the error xml
   * @throws Exception the exception
   */
  private void verifyDetailsXml(final String errorCode, final String message, 
          String severity, String target, final String errorXml) throws Exception {
    assertXpathExists("/a:error/a:innererror/a:errordetails/a:errordetail", errorXml);

    if (errorCode != null) {
        assertXpathEvaluatesTo(errorCode, "/a:error/a:innererror/a:errordetails/a:errordetail/a:code", errorXml);
    }
    if (message != null) {
        assertXpathEvaluatesTo(message, "/a:error/a:innererror/a:errordetails/a:errordetail/a:message", errorXml);
    }
    if (severity != null) {
        assertXpathEvaluatesTo(severity, "/a:error/a:innererror/a:errordetails/a:errordetail/a:severity", errorXml);
    }
    if (target != null) {
        assertXpathEvaluatesTo(target, "/a:error/a:innererror/a:errordetails/a:errordetail/a:target", errorXml);
    }
  }

}
