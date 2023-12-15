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
package org.apache.olingo.odata2.jpa.processor.core;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderReadProperties;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataBadRequestException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataEntityParser.
 */
public final class ODataEntityParser {

  /** The context. */
  private ODataJPAContext context;
  
  /** The edm. */
  private Edm edm;
  
  /** The service root. */
  private String serviceRoot = null;

  /**
   * Instantiates a new o data entity parser.
   *
   * @param context the context
   */
  public ODataEntityParser(final ODataJPAContext context) {
    this.context = context;
    try {
      serviceRoot = context.getODataContext().getPathInfo().getServiceRoot().toString();
    } catch (ODataException e) {
      serviceRoot = "";
    }
  }

  /**
   * Parses the entry.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param requestContentType the request content type
   * @param merge the merge
   * @return the o data entry
   * @throws ODataBadRequestException the o data bad request exception
   */
  public final ODataEntry parseEntry(final EdmEntitySet entitySet,
      final InputStream content, final String requestContentType, final boolean merge)
      throws ODataBadRequestException {
    ODataEntry entryValues;
    try {
      EntityProviderReadProperties entityProviderProperties =
          EntityProviderReadProperties.init().mergeSemantic(merge).build();
      entryValues = EntityProvider.readEntry(requestContentType, entitySet, content, entityProviderProperties);
    } catch (EntityProviderException e) {
      throw new ODataBadRequestException(ODataBadRequestException.BODY, e);
    }
    return entryValues;

  }

  /**
   * Parses the URI segment with custom options.
   *
   * @param segmentFromIndex the segment from index
   * @param segmentToIndex the segment to index
   * @param options the options
   * @return the uri info
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public final UriInfo parseURISegmentWithCustomOptions(final int segmentFromIndex, final int segmentToIndex,
      final Map<String, String> options) throws ODataJPARuntimeException {
    UriInfo uriInfo = null;
    if (segmentFromIndex == segmentToIndex || segmentFromIndex > segmentToIndex || segmentFromIndex < 0) {
      return uriInfo;
    }
    try {
      edm = getEdm();
      List<PathSegment> pathSegments = context.getODataContext().getPathInfo().getODataSegments();
      List<PathSegment> subPathSegments = pathSegments.subList(segmentFromIndex, segmentToIndex);
      uriInfo = UriParser.parse(edm, subPathSegments, options);
    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }
    return uriInfo;
  }

  /**
   * Parses the URI segment.
   *
   * @param segmentFromIndex the segment from index
   * @param segmentToIndex the segment to index
   * @return the uri info
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public final UriInfo parseURISegment(final int segmentFromIndex, final int segmentToIndex)
      throws ODataJPARuntimeException {
    UriInfo uriInfo = null;
    if (segmentFromIndex == segmentToIndex || segmentFromIndex > segmentToIndex || segmentFromIndex < 0) {
      return uriInfo;
    }
    try {
      edm = getEdm();
      List<PathSegment> pathSegments = context.getODataContext().getPathInfo().getODataSegments();
      List<PathSegment> subPathSegments = pathSegments.subList(segmentFromIndex, segmentToIndex);
      uriInfo = UriParser.parse(edm, subPathSegments, Collections.<String, String> emptyMap());
    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }
    return uriInfo;
  }

  /**
   * Parses the link.
   *
   * @param entitySet the entity set
   * @param content the content
   * @param contentType the content type
   * @return the uri info
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public final UriInfo parseLink(final EdmEntitySet entitySet, final InputStream content, final String contentType)
      throws ODataJPARuntimeException {

    String uriString = null;
    UriInfo uri = null;
    try {
      uriString = EntityProvider.readLink(contentType, entitySet, content);
      ODataContext odataContext = context.getODataContext();
      final String svcRoot = odataContext.getPathInfo().getServiceRoot().toString();
      final String path =
          uriString.startsWith(svcRoot.toString()) ? uriString.substring(svcRoot.length()) : uriString;
      final List<PathSegment> pathSegment = getPathSegment(path);
      edm = getEdm();
      uri = UriParser.parse(edm, pathSegment, Collections.<String, String> emptyMap());
    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }
    return uri;
  }

  /**
   * Parses the link segments.
   *
   * @param linkSegments the link segments
   * @param options the options
   * @return the uri info
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public UriInfo parseLinkSegments(final List<String> linkSegments, final Map<String, String> options)
      throws ODataJPARuntimeException {
    List<PathSegment> pathSegments = new ArrayList<PathSegment>();
    for (String link : linkSegments) {
      List<PathSegment> pathSegment = getPathSegment(link);
      pathSegments.addAll(pathSegment);
    }
    UriInfo uriInfo = null;
    try {
      edm = getEdm();
      uriInfo = UriParser.parse(edm, pathSegments, options);
    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }
    return uriInfo;
  }

  /**
   * Parses the binding link.
   *
   * @param link the link
   * @param options the options
   * @return the uri info
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  public UriInfo parseBindingLink(final String link, final Map<String, String> options)
      throws ODataJPARuntimeException {
    final List<PathSegment> pathSegment = getPathSegment(link);
    UriInfo uriInfo = null;
    try {
      edm = getEdm();
      uriInfo = UriParser.parse(edm, pathSegment, options);
    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }
    return uriInfo;
  }

  /**
   * Gets the edm.
   *
   * @return the edm
   * @throws ODataException the o data exception
   */
  private Edm getEdm() throws ODataException {
    if (edm == null) {
      edm = context.getODataContext().getService().getEntityDataModel();
    }
    return edm;
  }

  /**
   * Gets the path segment.
   *
   * @param path the path
   * @return the path segment
   */
  private List<PathSegment> getPathSegment(final String path) {
    String trimmedPath = path.replace(serviceRoot, "");

    final String[] splittedPath = trimmedPath.split("/");
    final List<PathSegment> pathSegments = new ArrayList<PathSegment>();

    for (final String pathSegmentString : splittedPath) {
      final PathSegment pathSegment = new PathSegment() {
        @Override
        public String getPath() {
          return pathSegmentString;
        }
        @Override
        public Map<String, List<String>> getMatrixParameters() {
          return null;
        }
      };
      pathSegments.add(pathSegment);
    }

    return pathSegments;
  }
}
