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
package org.apache.olingo.odata2.fit.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.api.processor.part.MetadataProcessor;
import org.apache.olingo.odata2.api.processor.part.ServiceDocumentProcessor;
import org.apache.olingo.odata2.api.uri.info.GetMetadataUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetServiceDocumentUriInfo;
import org.apache.olingo.odata2.testutil.helper.HttpMerge;
import org.apache.olingo.odata2.testutil.helper.HttpSomethingUnsupported;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Test;
import jakarta.ws.rs.HttpMethod;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicHttpTest.
 */
public class BasicHttpTest extends AbstractBasicTest {

    /**
     * Instantiates a new basic http test.
     *
     * @param servletType the servlet type
     */
    public BasicHttpTest(final ServletType servletType) {
        super(servletType);
    }

    /**
     * Creates the processor.
     *
     * @return the o data single processor
     * @throws ODataException the o data exception
     */
    @Override
    protected ODataSingleProcessor createProcessor() throws ODataException {
        final ODataSingleProcessor processor = mock(ODataSingleProcessor.class);
        when(((MetadataProcessor) processor).readMetadata(any(GetMetadataUriInfo.class), any(String.class))).thenReturn(
                ODataResponse.entity("metadata")
                             .status(HttpStatusCodes.OK)
                             .build());
        when(((ServiceDocumentProcessor) processor).readServiceDocument(any(GetServiceDocumentUriInfo.class),
                any(String.class))).thenReturn(ODataResponse.entity("service document")
                                                            .status(HttpStatusCodes.OK)
                                                            .build());
        return processor;
    }

    /**
     * Gets the service document.
     *
     * @return the service document
     * @throws Exception the exception
     */
    @Test
    public void getServiceDocument() throws Exception {
        final HttpResponse response = executeGetRequest("/");
        assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine()
                                                                 .getStatusCode());
        assertEquals("service document", StringHelper.inputStreamToString(response.getEntity()
                                                                                  .getContent()));
    }

    /**
     * Gets the service document with redirect.
     *
     * @return the service document with redirect
     * @throws Exception the exception
     */
    @Test
    public void getServiceDocumentWithRedirect() throws Exception {
        final HttpResponse response = executeGetRequest("");
        assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine()
                                                                 .getStatusCode());
        assertEquals("service document", StringHelper.inputStreamToString(response.getEntity()
                                                                                  .getContent()));
    }

    /**
     * Gets the.
     *
     * @throws Exception the exception
     */
    @Test
    public void get() throws Exception {
        HttpResponse response = executeGetRequest("$metadata");

        assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine()
                                                                 .getStatusCode());
        assertEquals("metadata", StringHelper.inputStreamToString(response.getEntity()
                                                                          .getContent()));

        response = executeGetRequest("//////$metadata");
        assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine()
                                                                 .getStatusCode());
        StringHelper.inputStreamToString(response.getEntity()
                                                 .getContent());
        response = executeGetRequest("/./$metadata");
        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), response.getStatusLine()
                                                                        .getStatusCode());
        StringHelper.inputStreamToString(response.getEntity()
                                                 .getContent());
        response = executeGetRequest("$metadata/./");
        assertEquals(HttpStatusCodes.BAD_REQUEST.getStatusCode(), response.getStatusLine()
                                                                          .getStatusCode());
    }

    /**
     * Head.
     *
     * @throws Exception the exception
     */
    @Test
    public void head() throws Exception {
        HttpResponse response = executeHeadRequest("/");
        assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine()
                                                                 .getStatusCode());
        assertNull(response.getEntity());

        response = executeHeadRequest("$metadata");
        assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine()
                                                                 .getStatusCode());
        assertNull(response.getEntity());

        response = executeHeadRequest("//////$metadata");
        assertEquals(HttpStatusCodes.OK.getStatusCode(), response.getStatusLine()
                                                                 .getStatusCode());
        assertNull(response.getEntity());
        response = executeHeadRequest("/./$metadata");
        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), response.getStatusLine()
                                                                        .getStatusCode());
        assertNull(response.getEntity());
        response = executeHeadRequest("$metadata/./");
        assertEquals(HttpStatusCodes.BAD_REQUEST.getStatusCode(), response.getStatusLine()
                                                                          .getStatusCode());
    }


    /**
     * Put.
     *
     * @throws Exception the exception
     */
    @Test
    public void put() throws Exception {
        final HttpPut put = new HttpPut(URI.create(getEndpoint().toString() + "aaa/bbb/ccc"));
        final HttpResponse response = getHttpClient().execute(put);

        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), response.getStatusLine()
                                                                        .getStatusCode());
        final String payload = StringHelper.inputStreamToString(response.getEntity()
                                                                        .getContent());
        assertTrue(payload.contains("error"));
    }

    /**
     * Put with content.
     *
     * @throws Exception the exception
     */
    @Test
    public void putWithContent() throws Exception {
        final HttpPut put = new HttpPut(URI.create(getEndpoint().toString()));
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<entry xmlns=\"" + Edm.NAMESPACE_ATOM_2005 + "\"" + " xmlns:m=\""
                + Edm.NAMESPACE_M_2007_08 + "\"" + " xmlns:d=\"" + Edm.NAMESPACE_D_2007_08 + "\""
                + " xml:base=\"https://server.at.some.domain.com/path.to.some.service/ReferenceScenario.svc/\">" + "</entry>";
        final HttpEntity entity = new StringEntity(xml);
        put.setEntity(entity);
        final HttpResponse response = getHttpClient().execute(put);

        assertEquals(HttpStatusCodes.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatusLine()
                                                                                 .getStatusCode());
        final String payload = StringHelper.inputStreamToString(response.getEntity()
                                                                        .getContent());
        assertTrue(payload.contains("error"));
    }

    /**
     * Post method not allowed with content.
     *
     * @throws Exception the exception
     */
    @Test
    public void postMethodNotAllowedWithContent() throws Exception {
        final HttpPost post = new HttpPost(URI.create(getEndpoint().toString()));
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<entry xmlns=\"" + Edm.NAMESPACE_ATOM_2005 + "\"" + " xmlns:m=\""
                + Edm.NAMESPACE_M_2007_08 + "\"" + " xmlns:d=\"" + Edm.NAMESPACE_D_2007_08 + "\""
                + " xml:base=\"https://server.at.some.domain.com/path.to.some.service/ReferenceScenario.svc/\">" + "</entry>";
        final HttpEntity entity = new StringEntity(xml);
        post.setEntity(entity);
        final HttpResponse response = getHttpClient().execute(post);

        assertEquals(HttpStatusCodes.METHOD_NOT_ALLOWED.getStatusCode(), response.getStatusLine()
                                                                                 .getStatusCode());
        final String payload = StringHelper.inputStreamToString(response.getEntity()
                                                                        .getContent());
        assertTrue(payload.contains("error"));
    }

    /**
     * Post not found.
     *
     * @throws Exception the exception
     */
    @Test
    public void postNotFound() throws Exception {
        final HttpPost post = new HttpPost(URI.create(getEndpoint().toString() + "aaa/bbb/ccc"));
        final HttpResponse response = getHttpClient().execute(post);

        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), response.getStatusLine()
                                                                        .getStatusCode());
        final String payload = StringHelper.inputStreamToString(response.getEntity()
                                                                        .getContent());
        assertTrue(payload.contains("error"));
    }

    /**
     * Delete.
     *
     * @throws Exception the exception
     */
    @Test
    public void delete() throws Exception {
        final HttpDelete delete = new HttpDelete(URI.create(getEndpoint().toString() + "aaa/bbb/ccc"));
        final HttpResponse response = getHttpClient().execute(delete);

        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), response.getStatusLine()
                                                                        .getStatusCode());
        final String payload = StringHelper.inputStreamToString(response.getEntity()
                                                                        .getContent());
        assertTrue(payload.contains("error"));
    }

    /**
     * Merge.
     *
     * @throws Exception the exception
     */
    @Test
    public void merge() throws Exception {
        final HttpMerge merge = new HttpMerge(URI.create(getEndpoint().toString() + "aaa/bbb/ccc"));
        final HttpResponse response = getHttpClient().execute(merge);

        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), response.getStatusLine()
                                                                        .getStatusCode());
        final String payload = StringHelper.inputStreamToString(response.getEntity()
                                                                        .getContent());
        assertTrue(payload.contains("error"));
    }

    /**
     * Patch.
     *
     * @throws Exception the exception
     */
    @Test
    public void patch() throws Exception {
        HttpPatch get = new HttpPatch(URI.create(getEndpoint().toString() + "aaa/bbb/ccc"));
        final HttpResponse response = getHttpClient().execute(get);

        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), response.getStatusLine()
                                                                        .getStatusCode());
        final String payload = StringHelper.inputStreamToString(response.getEntity()
                                                                        .getContent());
        assertTrue(payload.contains("error"));
    }

    /**
     * Unsupported method.
     *
     * @throws Exception the exception
     */
    @Test
    public void unsupportedMethod() throws Exception {
        URI endpoint = getEndpoint();
        HttpOptions options = new HttpOptions(endpoint);
        HttpClient httpClient = getHttpClient();
        HttpResponse response = httpClient.execute(options);
        assertEquals(HttpStatusCodes.NOT_FOUND.getStatusCode(), response.getStatusLine()
                                                                        .getStatusCode());
    }

    /**
     * Unknown method.
     *
     * @throws Exception the exception
     */
    @Test
    public void unknownMethod() throws Exception {
        HttpSomethingUnsupported request = new HttpSomethingUnsupported(getEndpoint() + "aaa/bbb/ccc");
        final HttpResponse response = getHttpClient().execute(request);
        assertEquals(HttpStatusCodes.NOT_IMPLEMENTED.getStatusCode(), response.getStatusLine()
                                                                              .getStatusCode());
    }

    /**
     * Tunneled by post.
     *
     * @throws Exception the exception
     */
    @Test
    public void tunneledByPost() throws Exception {
        tunnelPost("X-HTTP-Method", ODataHttpMethod.MERGE);
        tunnelPost("X-HTTP-Method", ODataHttpMethod.PATCH);
        tunnelPost("X-HTTP-Method", ODataHttpMethod.DELETE);
        tunnelPost("X-HTTP-Method", ODataHttpMethod.PUT);
        tunnelPost("X-HTTP-Method", ODataHttpMethod.GET);
        tunnelPost("X-HTTP-Method", ODataHttpMethod.POST);
        tunnelPost("X-HTTP-Method", "HEAD", HttpStatusCodes.NOT_FOUND);

        tunnelPost("X-HTTP-Method-Override", ODataHttpMethod.MERGE);
        tunnelPost("X-HTTP-Method-Override", ODataHttpMethod.PATCH);
        tunnelPost("X-HTTP-Method-Override", ODataHttpMethod.DELETE);
        tunnelPost("X-HTTP-Method-Override", ODataHttpMethod.PUT);
        tunnelPost("X-HTTP-Method-Override", ODataHttpMethod.GET);
        tunnelPost("X-HTTP-Method-Override", ODataHttpMethod.POST);
        tunnelPost("X-HTTP-Method-Override", "HEAD", HttpStatusCodes.NOT_FOUND);
    }

    /**
     * Tunnel post.
     *
     * @param header the header
     * @param method the method
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void tunnelPost(final String header, final ODataHttpMethod method) throws IOException {
        tunnelPost(header, method.toString(), HttpStatusCodes.NOT_FOUND);
    }

    /**
     * Tunnel post.
     *
     * @param header the header
     * @param method the method
     * @param expectedStatus the expected status
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void tunnelPost(final String header, final String method, final HttpStatusCodes expectedStatus) throws IOException {
        HttpPost post = new HttpPost(URI.create(getEndpoint().toString() + "aaa/bbb/ccc"));
        post.setHeader(header, method);
        final HttpResponse response = getHttpClient().execute(post);
        assertEquals(expectedStatus.getStatusCode(), response.getStatusLine()
                                                             .getStatusCode());

        final String payload = StringHelper.inputStreamToString(response.getEntity()
                                                                        .getContent());
        assertTrue(payload.contains("error"));
    }

    /**
     * Tunneled bad request.
     *
     * @throws Exception the exception
     */
    @Test
    public void tunneledBadRequest() throws Exception {
        final HttpPost post = new HttpPost(URI.create(getEndpoint().toString() + "aaa/bbb/ccc"));
        post.setHeader("X-HTTP-Method", "MERGE");
        post.setHeader("X-HTTP-Method-Override", "PATCH");
        final HttpResponse response = getHttpClient().execute(post);
        assertEquals(HttpStatusCodes.BAD_REQUEST.getStatusCode(), response.getStatusLine()
                                                                          .getStatusCode());

        final String payload = StringHelper.inputStreamToString(response.getEntity()
                                                                        .getContent());
        assertTrue(payload.contains("error"));
    }

    /**
     * Tunneled unsupported method.
     *
     * @throws Exception the exception
     */
    @Test
    public void tunneledUnsupportedMethod() throws Exception {
        tunnelPost("X-HTTP-Method", HttpMethod.OPTIONS, HttpStatusCodes.NOT_IMPLEMENTED);
        tunnelPost("X-HTTP-Method-Override", HttpMethod.OPTIONS, HttpStatusCodes.NOT_IMPLEMENTED);
    }

    /**
     * Tunneled unknown method.
     *
     * @throws Exception the exception
     */
    @Test
    public void tunneledUnknownMethod() throws Exception {
        tunnelPost("X-HTTP-Method", "xxx", HttpStatusCodes.NOT_IMPLEMENTED);
    }

    /**
     * Tunneled unknown method override.
     *
     * @throws Exception the exception
     */
    @Test
    public void tunneledUnknownMethodOverride() throws Exception {
        tunnelPost("X-HTTP-Method-Override", "xxx", HttpStatusCodes.NOT_IMPLEMENTED);
    }

    /**
     * Execute head request.
     *
     * @param request the request
     * @return the http response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected HttpResponse executeHeadRequest(final String request) throws IOException {
        final HttpHead head = new HttpHead(URI.create(getEndpoint().toString() + request));
        return getHttpClient().execute(head);
    }
}
