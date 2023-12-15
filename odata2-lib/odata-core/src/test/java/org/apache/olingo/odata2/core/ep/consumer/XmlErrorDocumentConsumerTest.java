/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.apache.olingo.odata2.core.ep.consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.InputStream;
import java.util.Locale;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.testutil.helper.LocaleAsserter;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlErrorDocumentConsumerTest.
 */
public class XmlErrorDocumentConsumerTest extends AbstractConsumerTest {

    /** The Constant XML_ERROR_DOCUMENT_SIMPLE. */
    private static final String XML_ERROR_DOCUMENT_SIMPLE = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" + "\t<code>ErrorCode</code>\n"
            + "\t<message xml:lang=\"en-US\">Message</message>\n" + "</error>";
    
    /** The Constant XML_ERROR_DOCUMENT_NULL_LOCALE. */
    private static final String XML_ERROR_DOCUMENT_NULL_LOCALE = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" + "\t<code>ErrorCode</code>\n"
            + "\t<message xml:lang=\"\">Message</message>\n" + "</error>";
    
    /** The Constant XML_ERROR_DOCUMENT_INNER_ERROR. */
    private static final String XML_ERROR_DOCUMENT_INNER_ERROR = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" + "\t<code>ErrorCode</code>\n"
            + "\t<message xml:lang=\"en-US\">Message</message>\n" + "<innererror>Some InnerError</innererror>\n" + "</error>";
    
    /** The Constant XML_ERROR_DOCUMENT_INNER_ERROR_COMPLEX. */
    private static final String XML_ERROR_DOCUMENT_INNER_ERROR_COMPLEX = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" + "\t<code>ErrorCode</code>\n"
            + "\t<message xml:lang=\"en-US\">Message</message>\n" + "<innererror>" + "<moreInner>More Inner Error</moreInner>"
            + "</innererror>\n" + "</error>";
    
    /** The Constant XML_ERROR_DOCUMENT_INVALID_XML. */
    private static final String XML_ERROR_DOCUMENT_INVALID_XML = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" + "\t<code>ErrorCode</CODE>\n"
            + "\t<message xml:lang=\"en-US\">Message</message>\n" + "</error>";
    
    /** The Constant XML_ERROR_DOCUMENT_UNKNOWN_CONTENT. */
    /* error document with name 'locale' instead of 'lang' for message object */
    private static final String XML_ERROR_DOCUMENT_UNKNOWN_CONTENT = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" + "\t<code>ErrorCode</code>\n"
            + "\t<message xml:locale=\"en-US\">Message</message>\n" + "\t<privateMessage>Secret</privateMessage>\n" + "</error>";
    
    /** The Constant XML_ERROR_DOCUMENT_EMPTY_MESSAGE. */
    /* error document without value for message object */
    private static final String XML_ERROR_DOCUMENT_EMPTY_MESSAGE = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" + "\t<code>ErrorCode</code>\n"
            + "\t<message xml:lang=\"en-US\" />\n" + "</error>";
    
    /** The Constant XML_ERROR_DOCUMENT_MISSING_MESSAGE. */
    private static final String XML_ERROR_DOCUMENT_MISSING_MESSAGE = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" + "\t<code>ErrorCode</code>\n"
            + "</error>";
    
    /** The Constant XML_ERROR_DOCUMENT_MISSING_CODE. */
    private static final String XML_ERROR_DOCUMENT_MISSING_CODE = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<error xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n"
            + "\t<message xml:lang=\"en-US\">Message</message>\n" + "</error>";
    
    /** The Constant XML_ERROR_DOCUMENT_MISSING_ERROR. */
    private static final String XML_ERROR_DOCUMENT_MISSING_ERROR = "<?xml version='1.0' encoding='UTF-8'?>\n"
            + "<errorForMe xmlns=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\">\n" + "\t<code>ErrorCode</code>\n"
            + "\t<message xml:lang=\"en-US\">Message</message>\n" + "</errorForMe>";
    
    /** The xedc. */
    private final XmlErrorDocumentConsumer xedc = new XmlErrorDocumentConsumer();

    /**
     * Simple error document.
     *
     * @throws Exception the exception
     */
    @Test
    public void simpleErrorDocument() throws Exception {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_SIMPLE);
        ODataErrorContext error = xedc.readError(in);

        assertEquals("Wrong content type", "application/xml", error.getContentType());
        assertEquals("Wrong message", "Message", error.getMessage());
        assertEquals("Wrong error code", "ErrorCode", error.getErrorCode());
        LocaleAsserter.assertLocale("Wrong locale for lang", error.getLocale(), Locale.US);
    }

    /**
     * Empty message.
     *
     * @throws EntityProviderException the entity provider exception
     */
    @Test
    public void emptyMessage() throws EntityProviderException {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_EMPTY_MESSAGE);

        ODataErrorContext error = xedc.readError(in);

        assertEquals("Wrong content type", "application/xml", error.getContentType());
        assertEquals("Wrong message", "", error.getMessage());
        assertEquals("Wrong error code", "ErrorCode", error.getErrorCode());
        LocaleAsserter.assertLocale("Wrong locale for lang", error.getLocale(), Locale.US);
    }

    /**
     * Locale null.
     *
     * @throws Exception the exception
     */
    @Test
    public void localeNull() throws Exception {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_NULL_LOCALE);
        ODataErrorContext error = xedc.readError(in);

        assertEquals("Wrong content type", "application/xml", error.getContentType());
        assertEquals("Wrong message", "Message", error.getMessage());
        assertEquals("Wrong error code", "ErrorCode", error.getErrorCode());
        assertNull("Expected NULL for locale", error.getLocale());
    }

    /**
     * Inner error.
     *
     * @throws Exception the exception
     */
    @Test
    public void innerError() throws Exception {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_INNER_ERROR);
        ODataErrorContext error = xedc.readError(in);

        assertEquals("Wrong content type", "application/xml", error.getContentType());
        assertEquals("Wrong message", "Message", error.getMessage());
        assertEquals("Wrong error code", "ErrorCode", error.getErrorCode());
        assertEquals("Wrong inner error", "Some InnerError", error.getInnerError());
    }

    /**
     * Inner error complex.
     *
     * @throws Exception the exception
     */
    @Test
    public void innerErrorComplex() throws Exception {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_INNER_ERROR_COMPLEX);
        ODataErrorContext error = xedc.readError(in);

        assertEquals("Wrong content type", "application/xml", error.getContentType());
        assertEquals("Wrong message", "Message", error.getMessage());
        assertEquals("Wrong error code", "ErrorCode", error.getErrorCode());
        assertEquals("Wrong inner error", "<moreInner>More Inner Error</moreInner>", error.getInnerError());
    }

    /**
     * Inner error complex two.
     *
     * @throws Exception the exception
     */
    @Test
    public void innerErrorComplexTwo() throws Exception {
        String innerErrorText = "<firstTag>tagText</firstTag><secondTag>secondText</secondTag>";
        String innerError = "<innererror>" + innerErrorText + "</innererror>";
        String errorDocument = XML_ERROR_DOCUMENT_INNER_ERROR_COMPLEX.replaceAll("<innererror.*error>", innerError);
        InputStream in = StringHelper.encapsulate(errorDocument);
        ODataErrorContext error = xedc.readError(in);

        assertEquals("Wrong content type", "application/xml", error.getContentType());
        assertEquals("Wrong message", "Message", error.getMessage());
        assertEquals("Wrong error code", "ErrorCode", error.getErrorCode());
        assertEquals("Wrong inner error", innerErrorText, error.getInnerError());
    }

    /**
     * Inner error complex more characters.
     *
     * @throws Exception the exception
     */
    @Test
    public void innerErrorComplexMoreCharacters() throws Exception {
        String innerErrorText = "\n\t<firstTag>tagText</firstTag>\n<secondTag>secondText</secondTag>\n";
        String innerError = "<innererror>" + innerErrorText + "</innererror>";
        String errorDocument = XML_ERROR_DOCUMENT_INNER_ERROR_COMPLEX.replaceAll("<innererror.*error>", innerError);
        InputStream in = StringHelper.encapsulate(errorDocument);
        ODataErrorContext error = xedc.readError(in);

        assertEquals("Wrong content type", "application/xml", error.getContentType());
        assertEquals("Wrong message", "Message", error.getMessage());
        assertEquals("Wrong error code", "ErrorCode", error.getErrorCode());
        assertEquals("Wrong inner error", innerErrorText, error.getInnerError());
    }

    /**
     * Invalid json.
     *
     * @throws EntityProviderException the entity provider exception
     */
    @Test(expected = EntityProviderException.class)
    public void invalidJson() throws EntityProviderException {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_INVALID_XML);
        try {
            xedc.readError(in);
            fail("Expected exception was not thrown");
        } catch (EntityProviderException e) {
            assertEquals(EntityProviderException.INVALID_STATE, e.getMessageReference());
            throw e;
        }
    }

    /**
     * Invalid empty document.
     *
     * @throws EntityProviderException the entity provider exception
     */
    @Test(expected = EntityProviderException.class)
    public void invalidEmptyDocument() throws EntityProviderException {
        InputStream in = StringHelper.encapsulate("");
        try {
            xedc.readError(in);
            fail("Expected exception was not thrown");
        } catch (EntityProviderException e) {
            assertEquals("Got wrong exception: " + e.getMessageReference()
                                                    .getKey(),
                    EntityProviderException.INVALID_STATE, e.getMessageReference());
            throw e;
        }
    }

    /**
     * Null parameter.
     *
     * @throws EntityProviderException the entity provider exception
     */
    @Test(expected = EntityProviderException.class)
    public void nullParameter() throws EntityProviderException {
        try {
            xedc.readError(null);
            fail("Expected exception was not thrown");
        } catch (EntityProviderException e) {
            assertEquals(EntityProviderException.ILLEGAL_ARGUMENT, e.getMessageReference());
            throw e;
        }
    }

    /**
     * Invalid error document unknown.
     *
     * @throws EntityProviderException the entity provider exception
     */
    @Test(expected = EntityProviderException.class)
    public void invalidErrorDocumentUnknown() throws EntityProviderException {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_UNKNOWN_CONTENT);
        try {
            xedc.readError(in);
            fail("Expected exception was not thrown");
        } catch (EntityProviderException e) {
            assertEquals(EntityProviderException.INVALID_CONTENT, e.getMessageReference());
            throw e;
        }
    }

    /**
     * Invalid error document missing error.
     *
     * @throws EntityProviderException the entity provider exception
     */
    @Test(expected = EntityProviderException.class)
    public void invalidErrorDocumentMissingError() throws EntityProviderException {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_MISSING_ERROR);
        try {
            xedc.readError(in);
            fail("Expected exception was not thrown");
        } catch (EntityProviderException e) {
            assertEquals("Got wrong exception: " + e.getMessageReference()
                                                    .getKey(),
                    EntityProviderException.INVALID_STATE, e.getMessageReference());
            throw e;
        }
    }

    /**
     * Invalid error document missing code.
     *
     * @throws EntityProviderException the entity provider exception
     */
    @Test(expected = EntityProviderException.class)
    public void invalidErrorDocumentMissingCode() throws EntityProviderException {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_MISSING_CODE);
        try {
            xedc.readError(in);
            fail("Expected exception was not thrown");
        } catch (EntityProviderException e) {
            assertEquals("Got wrong exception: " + e.getMessageReference()
                                                    .getKey(),
                    EntityProviderException.MISSING_PROPERTY, e.getMessageReference());
            assertTrue(e.getMessage()
                        .contains("code"));
            throw e;
        }
    }

    /**
     * Invalid error document missing message.
     *
     * @throws EntityProviderException the entity provider exception
     */
    @Test(expected = EntityProviderException.class)
    public void invalidErrorDocumentMissingMessage() throws EntityProviderException {
        InputStream in = StringHelper.encapsulate(XML_ERROR_DOCUMENT_MISSING_MESSAGE);
        try {
            xedc.readError(in);
            fail("Expected exception was not thrown");
        } catch (EntityProviderException e) {
            assertEquals("Got wrong exception: " + e.getMessageReference()
                                                    .getKey(),
                    EntityProviderException.MISSING_PROPERTY, e.getMessageReference());
            assertTrue(e.getMessage()
                        .contains("message"));
            throw e;
        }
    }
}
