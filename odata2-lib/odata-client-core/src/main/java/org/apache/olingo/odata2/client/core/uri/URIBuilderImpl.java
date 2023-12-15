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
package org.apache.olingo.odata2.client.core.uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.client.api.uri.QueryOption;
import org.apache.olingo.odata2.client.api.uri.SegmentType;
import org.apache.olingo.odata2.client.api.uri.URIBuilder;
import org.apache.olingo.odata2.client.core.uri.util.UriUtil;
import org.apache.olingo.odata2.core.commons.Encoder;

// TODO: Auto-generated Javadoc
/**
 * This is a builder class that constructs URI without edm validations.
 */
public class URIBuilderImpl implements URIBuilder {
  
  /** The segments. */
  protected final List<Segment> segments = new ArrayList<Segment>();
  
  /**
   * Insertion-order map of query options.
   */
  protected final Map<String, String> queryOptions = new LinkedHashMap<String, String>();
  
  /**
   * Insertion-order map of custom query options.
   */
  protected final Map<String, String> customQueryOptions = new LinkedHashMap<String, String>();
  
  /**
   * Insertion-order map of function import parameters.
   */
  protected final Map<String, Object> functionImportParameters = new LinkedHashMap<String, Object>();
  
  /**
   * Constructor.
   *
   * @param serviceRoot absolute URL (schema, host and port included) representing the location of the root of the data
   * service.
   */
  public URIBuilderImpl(final String serviceRoot) {
    segments.add(new Segment(SegmentType.INITIAL, serviceRoot));
  }
  
  /**
   * Append count segment.
   *
   * @return the URI builder
   */
  @Override
  public URIBuilder appendCountSegment() {
    segments.add(new Segment(SegmentType.COUNT, SegmentType.COUNT.getValue()));
    return this;
  }

  /**
   * Append metadata segment.
   *
   * @return the URI builder
   */
  @Override
  public URIBuilder appendMetadataSegment() {
    segments.add(new Segment(SegmentType.METADATA, SegmentType.METADATA.getValue()));
    return this;
  }

  /**
   * Format.
   *
   * @param format the format
   * @return the URI builder
   */
  @Override
  public URIBuilder format(String format) {
    UriUtil.appendQueryOption(QueryOption.FORMAT.toString(), format, queryOptions, true);
    return this;
  }
  
  /**
   * Append value segment.
   *
   * @return the URI builder
   */
  @Override
  public URIBuilder appendValueSegment() {
    segments.add(new Segment(SegmentType.VALUE, SegmentType.VALUE.getValue()));
    return this;
  }

  /**
   * Adds the query option.
   *
   * @param option the option
   * @param value the value
   * @return the URI builder
   */
  @Override
  public URIBuilder addQueryOption(QueryOption option, String value) {
    UriUtil.appendQueryOption(option.toString(), value, queryOptions, false);
    return this;
  }

  /**
   * Filter.
   *
   * @param filter the filter
   * @return the URI builder
   */
  @Override
  public URIBuilder filter(String filter) {
    return replaceQueryOption(QueryOption.FILTER, filter);
  }

  /**
   * Top.
   *
   * @param top the top
   * @return the URI builder
   */
  @Override
  public URIBuilder top(int top) {
    return replaceQueryOption(QueryOption.TOP, String.valueOf(top));
  }

  /**
   * Skip.
   *
   * @param skip the skip
   * @return the URI builder
   */
  @Override
  public URIBuilder skip(int skip) {
    return replaceQueryOption(QueryOption.SKIP, String.valueOf(skip));
  }

  /**
   * Adds the custom query option.
   *
   * @param paramName the param name
   * @param paramValue the param value
   * @return the URI builder
   */
  @Override
  public URIBuilder addCustomQueryOption(String paramName, Object paramValue) {
    UriUtil.appendQueryOption(paramName, paramValue.toString(),
        customQueryOptions, true);
    return this;
  }
  
  /**
   * Builds the.
   *
   * @return the uri
   */
  @Override
  public URI build() {
    return UriUtil.getUri(segments, queryOptions, customQueryOptions, functionImportParameters);
  }
  
  /**
   * Append entity set segment.
   *
   * @param entitySet the entity set
   * @return the URI builder
   */
  @Override
  public URIBuilder appendEntitySetSegment(String entitySet) {
    segments.add(new Segment(SegmentType.ENTITYSET, entitySet));
    return this;
  }

  /**
   * Append navigation segment.
   *
   * @param navigationProperty the navigation property
   * @return the URI builder
   */
  @Override
  public URIBuilder appendNavigationSegment(String navigationProperty) {
    segments.add(new Segment(SegmentType.NAVIGATION, navigationProperty));
    return this;
  }

  /**
   * Append key segment.
   *
   * @param value the value
   * @return the URI builder
   */
  @Override
  public URIBuilder appendKeySegment(Object value) {
    String key = getKey(value, false);
    segments.add(new Segment(SegmentType.KEY, key));
    return this;
  }

  /**
   * Gets the key.
   *
   * @param value the value
   * @param isSegment the is segment
   * @return the key
   */
  private String getKey(Object value, boolean isSegment) {
    String key = "";
    if (!isSegment) {
      if (value instanceof String) {
        value = Encoder.encode(value.toString()); //NOSONAR
        key += "('" + value + "')";
      } else {
        key += "(" + value + ")";
      }
    } else {
      if (value instanceof String) {
        value = Encoder.encode(value.toString()); //NOSONAR
        key += "'" + value + "'";
      } else {
        key += value;
      }
    }
    return key;
  }

  /**
   * Append key segment.
   *
   * @param segmentValues the segment values
   * @return the URI builder
   */
  @Override
  public URIBuilder appendKeySegment(Map<String, Object> segmentValues) {
    String key = buildMultiKeySegment(segmentValues, ',');
    segments.add(new Segment(SegmentType.KEY, key));
    return this;
  }

  /**
   * Builds the multi key segment.
   *
   * @param segmentValues the segment values
   * @param separator the separator
   * @return the string
   */
  private String buildMultiKeySegment(Map<String, Object> segmentValues, char separator) {
    if (segmentValues == null || segmentValues.isEmpty()) {
      return "";
    } else {
      final StringBuilder keyBuilder = new StringBuilder().append('(');
      for (Map.Entry<String, Object> entry : segmentValues.entrySet()) {
        keyBuilder.append(entry.getKey()).append('=').append(
            getKey(entry.getValue(), true));
        keyBuilder.append(separator);
      }
      keyBuilder.deleteCharAt(keyBuilder.length() - 1).append(')');

      return keyBuilder.toString();
    }
  }

  /**
   * Append property segment.
   *
   * @param segmentValue the segment value
   * @return the URI builder
   */
  @Override
  public URIBuilder appendPropertySegment(String segmentValue) {
    segments.add(new Segment(SegmentType.PROPERTY, segmentValue));
    return this;
  }

  /**
   * Expand.
   *
   * @param expandItems the expand items
   * @return the URI builder
   */
  @Override
  public URIBuilder expand(String... expandItems) {
    return addQueryOption(QueryOption.EXPAND, UriUtil.join(expandItems, ","));
  }
  
  /**
   * Select.
   *
   * @param selectItems the select items
   * @return the URI builder
   */
  @Override
  public URIBuilder select(String... selectItems) {
    return addQueryOption(QueryOption.SELECT, UriUtil.join(selectItems, ","));
  }

  /**
   * Order by.
   *
   * @param order the order
   * @return the URI builder
   */
  @Override
  public URIBuilder orderBy(String order) {
    return addQueryOption(QueryOption.ORDERBY, order);
  }

  /**
   * Replace query option.
   *
   * @param option the option
   * @param value the value
   * @return URIBuilder
   */
  public URIBuilder replaceQueryOption(QueryOption option, String value) {
    UriUtil.appendQueryOption(option.toString(), value, queryOptions, true);
    return this;
  }

  /**
   * Append function import segment.
   *
   * @param functionImport the function import
   * @return the URI builder
   */
  @Override
  public URIBuilder appendFunctionImportSegment(String functionImport) {
    segments.add(new Segment(SegmentType.FUNCTIONIMPORT, functionImport));
    return this;
  }

  /**
   * Append function import parameters.
   *
   * @param functionImportParams the function import params
   * @return the URI builder
   */
  @Override
  public URIBuilder appendFunctionImportParameters(Map<String, Object> functionImportParams) {
    if (functionImportParams != null && !functionImportParams.isEmpty()) {
      for (Map.Entry<String, Object> param : functionImportParams.entrySet()) {
        Object value = param.getValue();
        if (value instanceof String) {
          value = "'" + value + "'";
        }
        functionImportParameters.put(param.getKey(), value);
      }
    }
    return this;
  }
}
