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
package org.apache.olingo.odata2.client.core;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.exception.ODataNotAcceptableException;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.UriNotMatchingException;
import org.apache.olingo.odata2.api.uri.UriSyntaxException;
import org.apache.olingo.odata2.client.api.ODataClient;
import org.apache.olingo.odata2.client.api.edm.EdmDataServices;
import org.apache.olingo.odata2.client.api.ep.ContentTypeBasedDeserializer;
import org.apache.olingo.odata2.client.api.ep.ContentTypeBasedSerializer;
import org.apache.olingo.odata2.client.api.ep.DeserializerMetadataProviderInterface;
import org.apache.olingo.odata2.client.api.uri.EdmURIBuilder;
import org.apache.olingo.odata2.client.api.uri.URIBuilder;
import org.apache.olingo.odata2.client.core.ep.AtomSerializerDeserializer;
import org.apache.olingo.odata2.client.core.ep.JsonSerializerDeserializer;
import org.apache.olingo.odata2.client.core.ep.deserializer.XmlMetadataDeserializer;
import org.apache.olingo.odata2.client.core.uri.EdmURIBuilderImpl;
import org.apache.olingo.odata2.client.core.uri.URIBuilderImpl;
import org.apache.olingo.odata2.core.ODataPathSegmentImpl;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.apache.olingo.odata2.core.uri.UriParserImpl;

// TODO: Auto-generated Javadoc
/**
 * Implementation class to obtain serializers and deserializers based on content type.
 */
public class ODataClientImpl extends ODataClient implements DeserializerMetadataProviderInterface{

  /** The Constant AMP. */
  private static final String AMP = "&";
  
  /** The Constant EQUAL. */
  private static final String EQUAL = "=";
  
  /** The Constant QUESTIONMARK. */
  private static final String QUESTIONMARK = "\\?";
  
  /** The Constant SLASH. */
  private static final String SLASH = "/";
  
  /**
   * Creates the serializer.
   *
   * @param contentType the content type
   * @return the content type based serializer
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ContentTypeBasedSerializer createSerializer(final String contentType) 
      throws EntityProviderException {
    return createSerializer(ContentType.createAsCustom(contentType));

  }

  /**
   * Creates the serializer.
   *
   * @param contentType the content type
   * @return the content type based serializer
   * @throws EntityProviderException the entity provider exception
   */
  private ContentTypeBasedSerializer createSerializer(ContentType contentType)
      throws EntityProviderException {
    try {
      switch (contentType.getODataFormat()) {
      case ATOM:
      case XML:
        return new AtomSerializerDeserializer(contentType.getODataFormat());
      case JSON:
        return new JsonSerializerDeserializer();
      default:
        throw new ODataNotAcceptableException(ODataNotAcceptableException.NOT_SUPPORTED_CONTENT_TYPE
            .addContent(contentType));
      }
    } catch (final ODataNotAcceptableException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }
  
  /**
   * Creates the deserializer.
   *
   * @param contentType the content type
   * @return the content type based deserializer
   * @throws EntityProviderException the entity provider exception
   */
  @Override
  public ContentTypeBasedDeserializer createDeserializer(final String contentType) 
      throws EntityProviderException {
    return createDeserializer(ContentType.createAsCustom(contentType));

  }

  /**
   * Creates the deserializer.
   *
   * @param contentType the content type
   * @return the content type based deserializer
   * @throws EntityProviderException the entity provider exception
   */
  private ContentTypeBasedDeserializer createDeserializer(ContentType contentType)
      throws EntityProviderException {
    try {
      switch (contentType.getODataFormat()) {
      case ATOM:
      case XML:
        return new AtomSerializerDeserializer(contentType.getODataFormat());
      case JSON:
        return new JsonSerializerDeserializer();
      default:
        throw new ODataNotAcceptableException(ODataNotAcceptableException.NOT_SUPPORTED_CONTENT_TYPE
            .addContent(contentType));
      }
    } catch (final ODataNotAcceptableException e) {
      throw new EntityProviderException(EntityProviderException.COMMON, e);
    }
  }

  /**
   * Read metadata.
   *
   * @param content the content
   * @param validate the validate
   * @return the edm data services
   * @throws EntityProviderException the entity provider exception
   * @throws EdmException the edm exception
   */
  @Override
  public EdmDataServices readMetadata(InputStream content, boolean validate) 
      throws EntityProviderException, EdmException {
    return new XmlMetadataDeserializer().readMetadata(content, validate);
  }

  /**
   * Parses the uri.
   *
   * @param edm the edm
   * @param pathSegments the path segments
   * @param queryParameters the query parameters
   * @return the uri info
   * @throws UriSyntaxException the uri syntax exception
   * @throws UriNotMatchingException the uri not matching exception
   * @throws EdmException the edm exception
   */
  @Override
  public UriInfo parseUri(Edm edm, List<PathSegment> pathSegments, Map<String, List<String>> queryParameters)
      throws UriSyntaxException, UriNotMatchingException, EdmException {
    return new UriParserImpl(edm).parseAll(pathSegments, queryParameters);
  }

  /**
   * Parses the uri.
   *
   * @param edm the edm
   * @param pathSegments the path segments
   * @param queryParameters the query parameters
   * @param strictFilter the strict filter
   * @return the uri info
   * @throws UriSyntaxException the uri syntax exception
   * @throws UriNotMatchingException the uri not matching exception
   * @throws EdmException the edm exception
   */
  @Override
  public UriInfo parseUri(Edm edm, List<PathSegment> pathSegments,
                          Map<String, String> queryParameters, boolean strictFilter)
      throws UriSyntaxException, UriNotMatchingException, EdmException {
    return new UriParserImpl(edm).parse(pathSegments, queryParameters, strictFilter);
  }

  /**
   * Parses the uri.
   *
   * @param edm the edm
   * @param uri the uri
   * @return the uri info
   * @throws UriSyntaxException the uri syntax exception
   * @throws UriNotMatchingException the uri not matching exception
   * @throws EdmException the edm exception
   */
  @Override
  public UriInfo parseUri(Edm edm, String uri) throws UriSyntaxException, UriNotMatchingException, EdmException {
    final String[] path = uri.split(QUESTIONMARK, -1);
    if (path.length > 2) {
      throw new UriSyntaxException(UriSyntaxException.URISYNTAX);
    }

    final List<PathSegment> pathSegments = getPathSegments(path[0]);
    
    Map<String, List<String>> queryParameters;
    if (path.length == 2) {
      queryParameters = getQueryParameters(unescape(path[1]));
    } else {
      queryParameters = new HashMap<String, List<String>>();
    }
    
    return new UriParserImpl(edm).parseAll(pathSegments, queryParameters);
  }

  /**
   * Parses the uri.
   *
   * @param edm the edm
   * @param uri the uri
   * @param strictFilter the strict filter
   * @return the uri info
   * @throws UriSyntaxException the uri syntax exception
   * @throws UriNotMatchingException the uri not matching exception
   * @throws EdmException the edm exception
   */
  @Override
  public UriInfo parseUri(Edm edm, String uri, boolean strictFilter)
      throws UriSyntaxException, UriNotMatchingException, EdmException {

    final String[] path = uri.split(QUESTIONMARK, -1);
    if (path.length > 2) {
      throw new UriSyntaxException(UriSyntaxException.URISYNTAX);
    }

    final List<PathSegment> pathSegments = getPathSegments(path[0]);

    Map<String, String> queryParameters;
    if (path.length == 2) {
      queryParameters = getQueryParametersWithStrictFilter(unescape(path[1]));
    } else {
      queryParameters = new HashMap<String, String>();
    }

    return new UriParserImpl(edm).parse(pathSegments, queryParameters, strictFilter);
  }

  /**
   * Fetch query parameters.
   *
   * @param uri the uri
   * @return the query parameters
   */
  private Map<String, List<String>> getQueryParameters(final String uri) {
    Map<String, List<String>> allQueryParameters = new HashMap<String, List<String>>();

    for (final String option : uri.split(AMP)) {
      final String[] keyAndValue = option.split(EQUAL);
      List<String> list = allQueryParameters.containsKey(keyAndValue[0]) ?
          allQueryParameters.get(keyAndValue[0]) : new LinkedList<String>();

      list.add(keyAndValue.length == 2 ? keyAndValue[1] : "");

      allQueryParameters.put(keyAndValue[0], list);
    }

    return allQueryParameters;
  }

  /**
   * Fetch Query parameters.
   *
   * @param uri the uri
   * @return the query parameters with strict filter
   */
  private Map<String, String> getQueryParametersWithStrictFilter(String uri) {
    Map<String, String> queryParameters = new HashMap<String, String>();
    for (final String option : uri.split(AMP)) {
      final String[] keyAndValue = option.split(EQUAL);
      if (keyAndValue.length == 2) {
        queryParameters.put(keyAndValue[0], keyAndValue[1]);
      } else {
        queryParameters.put(keyAndValue[0], "");
      }
    }
    return queryParameters;
  }

  /**
   * Fetch path segments.
   *
   * @param uri the uri
   * @return the path segments
   * @throws UriSyntaxException the uri syntax exception
   */
  private List<PathSegment> getPathSegments(final String uri) throws UriSyntaxException {
    List<PathSegment> pathSegments = new ArrayList<PathSegment>();
    for (final String segment : uri.split(SLASH, -1)) {
      final String unescapedSegment = unescape(segment);
      PathSegment oDataSegment = new ODataPathSegmentImpl(unescapedSegment, null);
      pathSegments.add(oDataSegment);
    }
    return pathSegments;
  }
  
  /**
   * Unescape.
   *
   * @param s the s
   * @return the string
   * @throws UriSyntaxException the uri syntax exception
   */
  private String unescape(final String s) throws UriSyntaxException {
    try {
      return new URI(s).getPath();
    } catch (URISyntaxException e) {
      throw new UriSyntaxException(UriSyntaxException.NOTEXT, e);
    }
  }


/**
 * Edm uri builder.
 *
 * @param serviceRoot the service root
 * @return the edm URI builder
 */
@Override
  public EdmURIBuilder edmUriBuilder(String serviceRoot) {
    return new EdmURIBuilderImpl(serviceRoot);
  }

/**
 * Uri builder.
 *
 * @param serviceRoot the service root
 * @return the URI builder
 */
@Override
  public URIBuilder uriBuilder(String serviceRoot) {
    return new URIBuilderImpl(serviceRoot);
  }
}
