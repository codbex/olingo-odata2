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
package org.apache.olingo.odata2.fit.ref.contentnegotiation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.commons.HttpContentType;
import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataSingleProcessor;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.processor.ODataSingleProcessorService;
import org.apache.olingo.odata2.core.uri.UriType;
import org.apache.olingo.odata2.ref.edm.ScenarioEdmProvider;
import org.apache.olingo.odata2.ref.model.DataContainer;
import org.apache.olingo.odata2.ref.processor.ListsProcessor;
import org.apache.olingo.odata2.ref.processor.ScenarioDataSource;
import org.apache.olingo.odata2.testutil.fit.AbstractFitTest;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.server.ServletType;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractContentNegotiationTest.
 */
public abstract class AbstractContentNegotiationTest extends AbstractFitTest {

  /**
   * Instantiates a new abstract content negotiation test.
   *
   * @param servletType the servlet type
   */
  public AbstractContentNegotiationTest(final ServletType servletType) {
    super(servletType);
  }

  /** The Constant LOG. */
  private static final Logger LOG = LoggerFactory.getLogger(AbstractContentNegotiationTest.class);

  /** The Constant ACCEPT_HEADER_VALUES. */
  protected final static List<String> ACCEPT_HEADER_VALUES = Arrays.asList(
      "", // for requests with no 'Accept' header set
      HttpContentType.TEXT_PLAIN,
      HttpContentType.TEXT_PLAIN_UTF8,
      HttpContentType.APPLICATION_JSON,
      HttpContentType.APPLICATION_JSON_UTF8,
      HttpContentType.APPLICATION_XML,
      HttpContentType.APPLICATION_XML_UTF8,
      HttpContentType.APPLICATION_ATOM_XML,
      HttpContentType.APPLICATION_ATOM_XML_UTF8,
      HttpContentType.APPLICATION_ATOM_SVC,
      HttpContentType.APPLICATION_ATOM_SVC_UTF8
      );
  
  /** The Constant QUERY_OPTIONS. */
  protected final static List<String> QUERY_OPTIONS = Arrays.asList(
      "",
      "?$format=xml",
      "?$format=atom",
      "?$format=json"
      );

  /** The Constant CONTENT_TYPE_VALUES. */
  public static final List<String> CONTENT_TYPE_VALUES = Arrays.asList(
      HttpContentType.TEXT_PLAIN,
      HttpContentType.TEXT_PLAIN_UTF8,
      HttpContentType.APPLICATION_JSON,
      HttpContentType.APPLICATION_JSON_UTF8,
      HttpContentType.APPLICATION_XML,
      HttpContentType.APPLICATION_XML_UTF8,
      HttpContentType.APPLICATION_ATOM_XML,
      HttpContentType.APPLICATION_ATOM_XML_UTF8
      );

  /**
   * Creates the service.
   *
   * @return the o data service
   * @throws ODataException the o data exception
   */
  @Override
  protected ODataService createService() throws ODataException {
    DataContainer dataContainer = new DataContainer();
    dataContainer.init();
    final ODataSingleProcessor processor = new ListsProcessor(new ScenarioDataSource(dataContainer));
    final EdmProvider provider = new ScenarioEdmProvider();
    return new ODataSingleProcessorService(provider, processor) {};
  }

  /**
   * The Class FitTestSetBuilder.
   */
  protected static class FitTestSetBuilder {
    
    /** The test set. */
    private final FitTestSet testSet;

    /**
     * Instantiates a new fit test set builder.
     *
     * @param testSet the test set
     */
    public FitTestSetBuilder(final FitTestSet testSet) {
      this.testSet = testSet;
    }

    /**
     * Query options.
     *
     * @param queryOptions the query options
     * @return the fit test set builder
     */
    public FitTestSetBuilder queryOptions(final List<String> queryOptions) {
      testSet.queryOptions = queryOptions;
      return this;
    }

    /**
     * Accept header.
     *
     * @param acceptHeader the accept header
     * @return the fit test set builder
     */
    public FitTestSetBuilder acceptHeader(final List<String> acceptHeader) {
      testSet.acceptHeader = acceptHeader;
      return this;
    }

    /**
     * Expected status code.
     *
     * @param expectedStatusCode the expected status code
     * @return the fit test set builder
     */
    public FitTestSetBuilder expectedStatusCode(final HttpStatusCodes expectedStatusCode) {
      testSet.expectedStatusCode = expectedStatusCode;
      return this;
    }

    /**
     * Expected content type.
     *
     * @param expectedContentType the expected content type
     * @return the fit test set builder
     */
    public FitTestSetBuilder expectedContentType(final String expectedContentType) {
      testSet.expectedContentType = expectedContentType;
      return this;
    }

    /**
     * Inits the.
     *
     * @return the fit test set
     */
    public FitTestSet init() {
      return init(true);
    }

    /**
     * Inits the.
     *
     * @param populate the populate
     * @return the fit test set
     */
    public FitTestSet init(final boolean populate) {
      if (populate) {
        testSet.populate();
      }
      return testSet;
    }

    /**
     * Content.
     *
     * @param content the content
     * @return the fit test set builder
     */
    public FitTestSetBuilder content(final String content) {
      testSet.content = content;
      return this;
    }

    /**
     * Http method.
     *
     * @param httpMethod the http method
     * @return the fit test set builder
     */
    public FitTestSetBuilder httpMethod(final String httpMethod) {
      testSet.httpMethod = httpMethod;
      return this;
    }

    /**
     * Request content types.
     *
     * @param contentTypes the content types
     * @return the fit test set builder
     */
    public FitTestSetBuilder requestContentTypes(final List<String> contentTypes) {
      testSet.requestContentTypes = contentTypes;
      return this;
    }
  }

  /**
   * A set of {@link FitTest} which can be executed against a service endpoint.
   * 
   */
  protected static class FitTestSet {

    /** The Constant DEFAULT_WAIT_BETWEEN_TESTCALLS_IN_MS. */
    private static final int DEFAULT_WAIT_BETWEEN_TESTCALLS_IN_MS = 25;

    /** The test parameters. */
    private final Set<FitTest> testParameters = new HashSet<AbstractContentNegotiationTest.FitTest>();

    /** The uri type. */
    private final UriType uriType;
    
    /** The path. */
    private final String path;

    /** The query options. */
    private List<String> queryOptions = Arrays.asList("");
    
    /** The accept header. */
    private List<String> acceptHeader = Arrays.asList("");
    
    /** The request content types. */
    private List<String> requestContentTypes = Arrays.asList("");

    /** The expected status code. */
    private HttpStatusCodes expectedStatusCode = HttpStatusCodes.OK;
    
    /** The expected content type. */
    private String expectedContentType = null;
    
    /** The http method. */
    private String httpMethod = "GET";
    
    /** The content. */
    private String content = null;

    /**
     * Instantiates a new fit test set.
     *
     * @param uriType the uri type
     * @param path the path
     */
    private FitTestSet(final UriType uriType, final String path) {
      super();
      this.uriType = uriType;
      this.path = path;
    }

    /**
     * Creates the.
     *
     * @param uriType the uri type
     * @param path the path
     * @return the fit test set builder
     */
    public static FitTestSetBuilder create(final UriType uriType, final String path) {
      return create(uriType, path, true, true, false);
    }

    /**
     * Creates the.
     *
     * @param uriType the uri type
     * @param path the path
     * @param defaultQueryOptions the default query options
     * @param defaultAcceptHeaders the default accept headers
     * @param defaultRequestContentTypeHeaders the default request content type headers
     * @return the fit test set builder
     */
    public static FitTestSetBuilder create(final UriType uriType, final String path,
        final boolean defaultQueryOptions, final boolean defaultAcceptHeaders,
        final boolean defaultRequestContentTypeHeaders) {

      FitTestSetBuilder builder = new FitTestSetBuilder(new FitTestSet(uriType, path));
      if (defaultQueryOptions) {
        builder.queryOptions(QUERY_OPTIONS);
      }
      if (defaultAcceptHeaders) {
        builder.acceptHeader(ACCEPT_HEADER_VALUES);
      }
      if (defaultRequestContentTypeHeaders) {
        builder.requestContentTypes(CONTENT_TYPE_VALUES);
      }
      return builder;
    }

    /**
     * Populate.
     */
    public void populate() {
      testParameters.addAll(FitTest.create(this));
    }

    /**
     * Sets the test param.
     *
     * @param fitTest the new test param
     */
    public void setTestParam(final FitTest fitTest) {
      testParameters.remove(fitTest);
      testParameters.add(fitTest);
    }

    /**
     * Modify request content types.
     *
     * @param requestContentTypes the request content types
     * @param expectedStatusCode the expected status code
     * @param expectedContentType the expected content type
     */
    public void modifyRequestContentTypes(final List<String> requestContentTypes,
        final HttpStatusCodes expectedStatusCode, final String expectedContentType) {
      FitTestSet fts = new FitTestSetBuilder(this)
          .requestContentTypes(requestContentTypes)
          .expectedStatusCode(expectedStatusCode)
          .expectedContentType(expectedContentType).init(false);
      replaceTestParameters(FitTest.create(fts));
    }

    /**
     * Sets the test param.
     *
     * @param acceptHeader the accept header
     * @param expectedStatusCode the expected status code
     * @param expectedContentType the expected content type
     */
    public void setTestParam(final List<String> acceptHeader, final HttpStatusCodes expectedStatusCode,
        final String expectedContentType) {
      setTestParam(queryOptions, acceptHeader, expectedStatusCode, expectedContentType);
    }

    /**
     * Sets the test param.
     *
     * @param queryOptions the query options
     * @param acceptHeader the accept header
     * @param expectedStatusCode the expected status code
     * @param expectedContentType the expected content type
     */
    public void setTestParam(final List<String> queryOptions, final List<String> acceptHeader,
        final HttpStatusCodes expectedStatusCode, final String expectedContentType) {
      List<FitTest> tp = FitTest.create(this, queryOptions, acceptHeader, expectedStatusCode, expectedContentType);
      replaceTestParameters(tp);
    }

    /**
     * Replace test parameters.
     *
     * @param tp the tp
     */
    private void replaceTestParameters(final List<FitTest> tp) {
      testParameters.removeAll(tp);
      testParameters.addAll(tp);
    }

    /**
     * Execute all {@link FitTest}s with a default wait time between the calls (of
     * {@value #DEFAULT_WAIT_BETWEEN_TESTCALLS_IN_MS} ms).
     * 
     * For more information see @see #execute(URI, long)
     *
     * @param serviceEndpoint the service endpoint
     * @throws Exception the exception
     */
    public void execute(final URI serviceEndpoint) throws Exception {
      execute(serviceEndpoint, DEFAULT_WAIT_BETWEEN_TESTCALLS_IN_MS);
    }

    /**
     * Execute.
     *
     * @param serviceEndpoint the service endpoint
     * @param sleepTimeInMs the sleep time in ms
     * @throws Exception the exception
     */
    public void execute(final URI serviceEndpoint, final long sleepTimeInMs) throws Exception {
      Map<FitTest, AssertionError> test2Failure = new HashMap<AbstractContentNegotiationTest.FitTest, AssertionError>();
      List<FitTest> successTests = new ArrayList<AbstractContentNegotiationTest.FitTest>();

      for (FitTest testParam : testParameters) {
        try {
          testParam.execute(serviceEndpoint);
          successTests.add(testParam);
        } catch (AssertionError e) {
          test2Failure.put(testParam, e);
        } finally {
          if (sleepTimeInMs > 0) {
            TimeUnit.MILLISECONDS.sleep(sleepTimeInMs);
          }
        }
      }

      // System.out.println("#########################################");
      // System.out.println("# Success: '" + successTests.size() + "', failed '" + test2Failure.size() +
      // "', total '" + testParameters.size() + "'.");
      // System.out.println("#########################################");

      if (!test2Failure.isEmpty()) {
        Set<Entry<FitTest, AssertionError>> failedTests = test2Failure.entrySet();
        List<AssertionError> errors = new ArrayList<AssertionError>();
        for (Entry<FitTest, AssertionError> entry : failedTests) {
          errors.add(entry.getValue());
        }
        Assert.fail("Found '" + test2Failure.size() + "' test failures. See [\n" + errors + "]");
      }
    }
  }

  /**
   * The Class FitTestBuilder.
   */
  static class FitTestBuilder {
    
    /** The test. */
    private FitTest test;

    /**
     * Instantiates a new fit test builder.
     *
     * @param testSet the test set
     */
    public FitTestBuilder(final FitTestSet testSet) {
      test = new FitTest(testSet);
    }

    /**
     * Instantiates a new fit test builder.
     *
     * @param uriType the uri type
     * @param httpMethod the http method
     * @param path the path
     * @param expectedStatusCode the expected status code
     * @param expectedContentType the expected content type
     */
    public FitTestBuilder(final UriType uriType, final String httpMethod, final String path,
        final HttpStatusCodes expectedStatusCode, final String expectedContentType) {
      test = new FitTest(uriType, httpMethod, path, expectedStatusCode, expectedContentType);
    }

    /**
     * Query options.
     *
     * @param queryOptions the query options
     * @return the fit test builder
     */
    public FitTestBuilder queryOptions(final String queryOptions) {
      test.queryOptions = queryOptions;
      return this;
    }

    /**
     * Content.
     *
     * @param content the content
     * @return the fit test builder
     */
    public FitTestBuilder content(final String content) {
      test.request.content = content;
      return this;
    }

    /**
     * Set header with name to given value without any sort of checking or validation of value.
     *
     * @param name the name
     * @param value the value
     * @return FitTestBuilder
     */
    public FitTestBuilder header(final String name, final String value) {
      test.request.headers.put(name, value);
      return this;
    }

    /**
     * Set the accept header if value is not <code>NULL</code> and has a <code>length > 0</code>.
     *
     * @param value the value
     * @return FitTestBuilder
     */
    public FitTestBuilder acceptHeader(final String value) {
      if (value != null && value.length() > 0) {
        return header(HttpHeaders.ACCEPT, value);
      }
      return this;
    }

    /**
     * Set the accept header if value is not <code>NULL</code> and has a <code>length > 0</code>.
     *
     * @param value the value
     * @return FitTestBuilder
     */
    public FitTestBuilder contentTypeHeader(final String value) {
      if (value != null && value.length() > 0) {
        return header(HttpHeaders.CONTENT_TYPE, value);
      }
      return this;
    }

    /**
     * Checks if is response content expected.
     *
     * @param isExpected the is expected
     * @return the fit test builder
     */
    public FitTestBuilder isResponseContentExpected(final boolean isExpected) {
      test.isContentExpected = isExpected;
      return this;
    }

    /**
     * Builds the.
     *
     * @return the fit test
     */
    public FitTest build() {
      return test;
    }
  }

  /**
   * Combination of test parameters and expected test result which can be tested/executed against a service endpoint.
   */
  protected static class FitTest {
    
    /** The uri type. */
    private final UriType uriType;
    
    /** The path. */
    private final String path;
    
    /** The request. */
    private final FitTestRequest request;

    /** The query options. */
    private String queryOptions;

    /** The request line. */
    private String requestLine;

    /** The expected status code. */
    private HttpStatusCodes expectedStatusCode;
    
    /** The expected content type. */
    private String expectedContentType;
    
    /** The is content expected. */
    private boolean isContentExpected;

    /**
     * Instantiates a new fit test.
     *
     * @param testSet the test set
     */
    public FitTest(final FitTestSet testSet) {
      this(testSet.uriType, testSet.httpMethod, testSet.path, testSet.expectedStatusCode, testSet.expectedContentType);
    }

    /**
     * Instantiates a new fit test.
     *
     * @param uriType the uri type
     * @param httpMethod the http method
     * @param path the path
     * @param expectedStatusCode the expected status code
     * @param expectedContentType the expected content type
     */
    public FitTest(final UriType uriType, final String httpMethod, final String path,
        final HttpStatusCodes expectedStatusCode, final String expectedContentType) {
      super();
      this.uriType = uriType;
      this.path = path;
      this.expectedStatusCode = expectedStatusCode;
      this.expectedContentType = expectedContentType;
      isContentExpected = false;
      //
      request = new FitTestRequest();
      request.type = httpMethod;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
      return "TestParam [testUrl=" + requestLine + ", \n\turiType=" + uriType + ", path=" + path +
          ", queryOption=" + queryOptions + ", request=" + request +
          ", expectedStatusCode=" + expectedStatusCode + ", expectedContentType="
          + expectedContentType + ", isContentExpected=" + isContentExpected + "]";
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      String acceptHeader = request.headers.get(HttpHeaders.ACCEPT);
      result = prime * result + ((acceptHeader == null) ? 0 : acceptHeader.hashCode());
      String contentType = request.headers.get(HttpHeaders.CONTENT_TYPE);
      result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
      result = prime * result + ((path == null) ? 0 : path.hashCode());
      result = prime * result + ((queryOptions == null) ? 0 : queryOptions.hashCode());
      return result;
    }

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      FitTest other = (FitTest) obj;
      if (request == null) {
        if (other.request != null) {
          return false;
        }
      } else if (!request.equals(other.request)) {
        return false;
      }
      if (path == null) {
        if (other.path != null) {
          return false;
        }
      } else if (!path.equals(other.path)) {
        return false;
      }
      if (queryOptions == null) {
        if (other.queryOptions != null) {
          return false;
        }
      } else if (!queryOptions.equals(other.queryOptions)) {
        return false;
      }
      return true;
    }

    /**
     * Execute.
     *
     * @param serviceEndpoint the service endpoint
     * @throws Exception the exception
     */
    public void execute(final URI serviceEndpoint) throws Exception {
      HttpRequestBase request = null;

      try {
        String endpoint = serviceEndpoint.toASCIIString();
        String requestUrl = endpoint.substring(0, endpoint.length() - 1) + path;
        if (queryOptions != null) {
          requestUrl += queryOptions;
        }
        request = this.request.createRequest(requestUrl);

        requestLine = request.getRequestLine().toString();
        HttpClient httpClient = new DefaultHttpClient();

        LOG.debug("Execute test for [" + requestLine + "]");
        final HttpResponse response = httpClient.execute(request);
        LOG.debug("Got response for request [" + requestLine + "]");

        int resultStatusCode = response.getStatusLine().getStatusCode();
        assertEquals("Unexpected status code for " + toString(), expectedStatusCode.getStatusCode(), resultStatusCode);

        final String contentType = response.getFirstHeader(HttpHeaders.CONTENT_TYPE).getValue();
        assertEquals("Unexpected content type for " + toString(), ContentType.create(expectedContentType), ContentType
            .create(contentType));

        if (isContentExpected) {
          assertNotNull("Unexpected content for " + toString(), StringHelper.inputStreamToString(response.getEntity()
              .getContent()));
        }
        LOG.trace("Test passed [" + toString() + "]");
      } finally {
        if (request != null) {
          request.releaseConnection();
          LOG.debug("Released connection [" + requestLine + "]");
        }
      }
    }

    /**
     * Inits the.
     *
     * @param uriType the uri type
     * @param httpMethod the http method
     * @param path the path
     * @param expectedStatusCode the expected status code
     * @param expectedContentType the expected content type
     * @return the fit test builder
     */
    public static FitTestBuilder init(final UriType uriType, final String httpMethod, final String path,
        final HttpStatusCodes expectedStatusCode, final String expectedContentType) {
      return new FitTestBuilder(uriType, httpMethod, path, expectedStatusCode, expectedContentType);
    }

    /**
     * Inits the.
     *
     * @param fitTestSet the fit test set
     * @return the fit test builder
     */
    public static FitTestBuilder init(final FitTestSet fitTestSet) {
      return new FitTestBuilder(fitTestSet);
    }

    /**
     * Creates the.
     *
     * @param uriType the uri type
     * @param httpMethod the http method
     * @param path the path
     * @param queryOption the query option
     * @param acceptHeader the accept header
     * @param content the content
     * @param requestContentType the request content type
     * @param expectedStatusCode the expected status code
     * @param expectedContentType the expected content type
     * @return the fit test
     */
    public static FitTest create(final UriType uriType, final String httpMethod, final String path,
        final String queryOption, final String acceptHeader,
        final String content, final String requestContentType,
        final HttpStatusCodes expectedStatusCode, final String expectedContentType) {

      return init(uriType, httpMethod, path, expectedStatusCode, expectedContentType)
          .queryOptions(queryOption)
          .content(content).contentTypeHeader(requestContentType)
          .acceptHeader(acceptHeader)
          .build();
    }

    /**
     * Creates the.
     *
     * @param fitTestSet the fit test set
     * @param queryOptions the query options
     * @param acceptHeaders the accept headers
     * @param expectedStatusCode the expected status code
     * @param expectedContentType the expected content type
     * @return the list
     */
    private static List<FitTest> create(final FitTestSet fitTestSet,
        final List<String> queryOptions, final List<String> acceptHeaders,
        final HttpStatusCodes expectedStatusCode, final String expectedContentType) {

      Map<String, ContentType> acceptHeader2ContentType = new HashMap<String, ContentType>();
      for (String acceptHeader : acceptHeaders) {
        acceptHeader2ContentType.put(acceptHeader, ContentType.create(expectedContentType));
      }

      String content = fitTestSet.content;
      List<String> reqContentTypes = fitTestSet.requestContentTypes;
      return create(fitTestSet.uriType, fitTestSet.httpMethod, fitTestSet.path,
          queryOptions, acceptHeaders, acceptHeader2ContentType,
          content, reqContentTypes, expectedStatusCode);
    }

    /**
     * Creates the.
     *
     * @param fitTestSet the fit test set
     * @return the list
     */
    public static List<FitTest> create(final FitTestSet fitTestSet) {
      Map<String, ContentType> acceptHeader2ContentType = new HashMap<String, ContentType>();
      if (fitTestSet.expectedContentType != null) {
        for (String acceptHeader : fitTestSet.acceptHeader) {
          acceptHeader2ContentType.put(acceptHeader, ContentType.create(fitTestSet.expectedContentType));
        }
      }

      return create(fitTestSet, acceptHeader2ContentType);
    }

    /**
     * Creates the.
     *
     * @param fitTestSet the fit test set
     * @param acceptHeader2ContentType the accept header 2 content type
     * @return the list
     */
    public static List<FitTest> create(final FitTestSet fitTestSet,
        final Map<String, ContentType> acceptHeader2ContentType) {
      UriType uriType = fitTestSet.uriType;
      String httpMethod = fitTestSet.httpMethod;
      String path = fitTestSet.path;
      List<String> queryOptions = fitTestSet.queryOptions;
      List<String> acceptHeaders = fitTestSet.acceptHeader;
      String content = fitTestSet.content;
      List<String> requestContentTypeHeaders = fitTestSet.requestContentTypes;
      HttpStatusCodes expectedStatusCode = fitTestSet.expectedStatusCode;

      return create(uriType, httpMethod, path, queryOptions, acceptHeaders, acceptHeader2ContentType,
          content, requestContentTypeHeaders, expectedStatusCode);
    }

    /**
     * Creates the.
     *
     * @param uriType the uri type
     * @param httpMethod the http method
     * @param path the path
     * @param queryOptions the query options
     * @param acceptHeaders the accept headers
     * @param acceptHeader2ContentType the accept header 2 content type
     * @param content the content
     * @param requestContentTypeHeaders the request content type headers
     * @param expectedStatusCode the expected status code
     * @return the list
     */
    private static List<FitTest> create(final UriType uriType, final String httpMethod, final String path,
        final List<String> queryOptions,
        final List<String> acceptHeaders, final Map<String, ContentType> acceptHeader2ContentType,
        final String content, final List<String> requestContentTypeHeaders, final HttpStatusCodes expectedStatusCode) {

      List<FitTest> testParameters = new ArrayList<AbstractContentNegotiationTest.FitTest>();

      for (String queryOption : queryOptions) {
        for (String acceptHeader : acceptHeaders) {
          for (String requestContentType : requestContentTypeHeaders) {
            String expectedContentType = getExpectedResponseContentType(acceptHeader2ContentType, acceptHeader);
            FitTest tp = init(uriType, httpMethod, path, expectedStatusCode, expectedContentType)
                .queryOptions(queryOption)
                .acceptHeader(acceptHeader)
                .content(content).contentTypeHeader(requestContentType)
                .build();
            testParameters.add(tp);
          }
        }
      }

      return testParameters;
    }

    /**
     * Gets the expected response content type.
     *
     * @param acceptHeader2ContentType the accept header 2 content type
     * @param acceptHeader the accept header
     * @return the expected response content type
     */
    private static String getExpectedResponseContentType(final Map<String, ContentType> acceptHeader2ContentType,
        final String acceptHeader) {
      String expectedContentType = null;
      if (acceptHeader != null) {
        ContentType tmpContentType = acceptHeader2ContentType.get(acceptHeader);
        if (tmpContentType == null) {
          tmpContentType = ContentType.create(ContentType.create(acceptHeader),
              ContentType.PARAMETER_CHARSET, ContentType.CHARSET_UTF_8);
        }
        expectedContentType = tmpContentType.toContentTypeString();
      }
      return expectedContentType;
    }
  }

  /**
   * The Class FitTestRequest.
   */
  static class FitTestRequest {
    
    /** The type. */
    String type;
    
    /** The headers. */
    Map<String, String> headers = new HashMap<String, String>();
    
    /** The content. */
    String content;

    /** The request url. */
    String requestUrl;

    /**
     * Creates the request.
     *
     * @param requestUrl the request url
     * @return the http request base
     */
    HttpRequestBase createRequest(final String requestUrl) {
      this.requestUrl = requestUrl;
      URI uri = URI.create(requestUrl);
      HttpRequestBase request;
      // first try read (GET)
      if ("GET".equals(type)) {
        request = new HttpGet(uri);
      } else { // then try write
        HttpEntityEnclosingRequestBase writeRequest;
        if ("POST".equals(type)) {
          writeRequest = new HttpPost(uri);
        } else if ("PUT".equals(type)) {
          writeRequest = new HttpPut(uri);
        } else {
          throw new IllegalArgumentException("Unsupported HttpMethod of type '" + type + "'.");
        }
        // common write parts
        HttpEntity entity = createEntity();
        writeRequest.setEntity(entity);
        request = writeRequest;
      }

      // common request parts
      Set<Entry<String, String>> entries = headers.entrySet();

      for (Entry<String, String> entry : entries) {
        request.addHeader(entry.getKey(), entry.getValue());
      }

      return request;
    }

    /**
     * Creates the entity.
     *
     * @return the http entity
     */
    private HttpEntity createEntity() {
      if (content == null) {
        throw new IllegalArgumentException("Found NULL content for '" + toFullString() + "' request.");
      }
      String contentType = headers.get(HttpHeaders.CONTENT_TYPE);
      if (contentType == null) {
        throw new IllegalArgumentException("Found NONE Content-Type header for '" + toFullString() + "' request.");
      }
      org.apache.http.entity.ContentType ct = org.apache.http.entity.ContentType.create(contentType);
      return new StringEntity(content, ct);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((content == null) ? 0 : content.hashCode());
      result = prime * result + ((headers == null) ? 0 : headers.hashCode());
      result = prime * result + ((type == null) ? 0 : type.hashCode());
      return result;
    }

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(final Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      FitTestRequest other = (FitTestRequest) obj;
      if (content == null) {
        if (other.content != null) {
          return false;
        }
      } else if (!content.equals(other.content)) {
        return false;
      }
      if (headers == null) {
        if (other.headers != null) {
          return false;
        }
      } else if (!headers.equals(other.headers)) {
        return false;
      }
      if (type == null) {
        if (other.type != null) {
          return false;
        }
      } else if (!type.equals(other.type)) {
        return false;
      }
      return true;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
      return "FTR [type=" + type + ", headers=" + headers + "]";
    }

    /**
     * To full string.
     *
     * @return the string
     */
    public String toFullString() {
      return "FitTestRequest [type=" + type + ", requestUrl=" + requestUrl + ", headers=" + headers + ", content=\n{"
          + content + "\n}]";
    }
  }
}