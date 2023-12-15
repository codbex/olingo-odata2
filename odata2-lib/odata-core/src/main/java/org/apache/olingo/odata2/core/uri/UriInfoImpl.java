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
package org.apache.olingo.odata2.core.uri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.uri.KeyPredicate;
import org.apache.olingo.odata2.api.uri.NavigationPropertySegment;
import org.apache.olingo.odata2.api.uri.NavigationSegment;
import org.apache.olingo.odata2.api.uri.SelectItem;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.api.uri.expression.OrderByExpression;

// TODO: Auto-generated Javadoc
/**
 * The Class UriInfoImpl.
 */
public class UriInfoImpl implements UriInfo {

  /** The uri type. */
  private UriType uriType;

  /** The entity container. */
  private EdmEntityContainer entityContainer;
  
  /** The start entity set. */
  private EdmEntitySet startEntitySet;
  
  /** The target entity set. */
  private EdmEntitySet targetEntitySet;
  
  /** The function import. */
  private EdmFunctionImport functionImport;
  
  /** The target type. */
  private EdmType targetType;
  
  /** The key predicates. */
  private List<KeyPredicate> keyPredicates = Collections.emptyList();
  
  /** The navigation segments. */
  private List<NavigationSegment> navigationSegments = Collections.emptyList();
  
  /** The property path. */
  private List<EdmProperty> propertyPath = Collections.emptyList();
  
  /** The count. */
  private boolean count;
  
  /** The value. */
  private boolean value;
  
  /** The links. */
  private boolean links;

  /** The format. */
  private String format;
  
  /** The filter. */
  private FilterExpression filter;
  
  /** The inline count. */
  private InlineCount inlineCount;
  
  /** The order by. */
  private OrderByExpression orderBy;
  
  /** The skip token. */
  private String skipToken;
  
  /** The skip. */
  private Integer skip;
  
  /** The top. */
  private Integer top;
  
  /** The expand. */
  private List<ArrayList<NavigationPropertySegment>> expand = Collections.emptyList();
  
  /** The select. */
  private List<SelectItem> select = Collections.emptyList();
  
  /** The function import parameters. */
  private Map<String, EdmLiteral> functionImportParameters = Collections.emptyMap();
  
  /** The custom query options. */
  private Map<String, String> customQueryOptions = Collections.emptyMap();

  /**
   * Gets the uri type.
   *
   * @return the uri type
   */
  public UriType getUriType() {
    return uriType;
  }

  /**
   * Sets the uri type.
   *
   * @param uriType the new uri type
   */
  public void setUriType(final UriType uriType) {
    this.uriType = uriType;
  }

  /**
   * Sets the entity container.
   *
   * @param entityContainer the new entity container
   */
  public void setEntityContainer(final EdmEntityContainer entityContainer) {
    this.entityContainer = entityContainer;
  }

  /**
   * Gets the entity container.
   *
   * @return the entity container
   */
  @Override
  public EdmEntityContainer getEntityContainer() {
    return entityContainer;
  }

  /**
   * Sets the start entity set.
   *
   * @param edmEntitySet the new start entity set
   */
  public void setStartEntitySet(final EdmEntitySet edmEntitySet) {
    startEntitySet = edmEntitySet;
  }

  /**
   * Gets the start entity set.
   *
   * @return the start entity set
   */
  @Override
  public EdmEntitySet getStartEntitySet() {
    return startEntitySet;
  }

  /**
   * Sets the target entity set.
   *
   * @param targetEntitySet the new target entity set
   */
  public void setTargetEntitySet(final EdmEntitySet targetEntitySet) {
    this.targetEntitySet = targetEntitySet;
  }

  /**
   * Gets the target entity set.
   *
   * @return the target entity set
   */
  @Override
  public EdmEntitySet getTargetEntitySet() {
    return targetEntitySet;
  }

  /**
   * Sets the function import.
   *
   * @param functionImport the new function import
   */
  public void setFunctionImport(final EdmFunctionImport functionImport) {
    this.functionImport = functionImport;
  }

  /**
   * Gets the function import.
   *
   * @return the function import
   */
  @Override
  public EdmFunctionImport getFunctionImport() {
    return functionImport;
  }

  /**
   * Sets the target type.
   *
   * @param targetType the new target type
   */
  public void setTargetType(final EdmType targetType) {
    this.targetType = targetType;
  }

  /**
   * Gets the target type.
   *
   * @return the target type
   */
  @Override
  public EdmType getTargetType() {
    return targetType;
  }

  /**
   * Sets the key predicates.
   *
   * @param keyPredicates the new key predicates
   */
  public void setKeyPredicates(final List<KeyPredicate> keyPredicates) {
    this.keyPredicates = keyPredicates;
  }

  /**
   * Gets the key predicates.
   *
   * @return the key predicates
   */
  @Override
  public List<KeyPredicate> getKeyPredicates() {
    return keyPredicates;
  }

  /**
   * Gets the target key predicates.
   *
   * @return the target key predicates
   */
  @Override
  public List<KeyPredicate> getTargetKeyPredicates() {
    return navigationSegments.isEmpty() ?
        keyPredicates :
        navigationSegments.get(navigationSegments.size() - 1).getKeyPredicates();
  }

  /**
   * Adds the navigation segment.
   *
   * @param navigationSegment the navigation segment
   */
  public void addNavigationSegment(final NavigationSegment navigationSegment) {
    if (navigationSegments.equals(Collections.EMPTY_LIST)) {
      navigationSegments = new ArrayList<NavigationSegment>();
    }

    navigationSegments.add(navigationSegment);
  }

  /**
   * Gets the navigation segments.
   *
   * @return the navigation segments
   */
  @Override
  public List<NavigationSegment> getNavigationSegments() {
    return navigationSegments;
  }

  /**
   * Adds the property.
   *
   * @param property the property
   */
  public void addProperty(final EdmProperty property) {
    if (propertyPath.equals(Collections.EMPTY_LIST)) {
      propertyPath = new ArrayList<EdmProperty>();
    }

    propertyPath.add(property);
  }

  /**
   * Gets the property path.
   *
   * @return the property path
   */
  @Override
  public List<EdmProperty> getPropertyPath() {
    return propertyPath;
  }

  /**
   * Sets the count.
   *
   * @param count the new count
   */
  public void setCount(final boolean count) {
    this.count = count;
  }

  /**
   * Checks if is count.
   *
   * @return true, if is count
   */
  @Override
  public boolean isCount() {
    return count;
  }

  /**
   * Sets the value.
   *
   * @param value the new value
   */
  public void setValue(final boolean value) {
    this.value = value;
  }

  /**
   * Checks if is value.
   *
   * @return true, if is value
   */
  @Override
  public boolean isValue() {
    return value;
  }

  /**
   * Sets the links.
   *
   * @param links the new links
   */
  public void setLinks(final boolean links) {
    this.links = links;
  }

  /**
   * Checks if is links.
   *
   * @return true, if is links
   */
  @Override
  public boolean isLinks() {
    return links;
  }

  /**
   * Sets the format.
   *
   * @param contentType the new format
   */
  public void setFormat(final String contentType) {
    format = contentType;
  }

  /**
   * Gets the format.
   *
   * @return the format
   */
  @Override
  public String getFormat() {
    return format;
  }

  /**
   * Sets the filter.
   *
   * @param filter the new filter
   */
  public void setFilter(final FilterExpression filter) {
    this.filter = filter;
  }

  /**
   * Gets the filter.
   *
   * @return the filter
   */
  @Override
  public FilterExpression getFilter() {
    return filter;
  }

  /**
   * Sets the inline count.
   *
   * @param inlineCount the new inline count
   */
  public void setInlineCount(final InlineCount inlineCount) {
    this.inlineCount = inlineCount;
  }

  /**
   * Gets the inline count.
   *
   * @return the inline count
   */
  @Override
  public InlineCount getInlineCount() {
    return inlineCount;
  }

  /**
   * Sets the order by.
   *
   * @param orderBy the new order by
   */
  public void setOrderBy(final OrderByExpression orderBy) {
    this.orderBy = orderBy;
  }

  /**
   * Gets the order by.
   *
   * @return the order by
   */
  @Override
  public OrderByExpression getOrderBy() {
    return orderBy;
  }

  /**
   * Sets the skip token.
   *
   * @param skipToken the new skip token
   */
  public void setSkipToken(final String skipToken) {
    this.skipToken = skipToken;
  }

  /**
   * Gets the skip token.
   *
   * @return the skip token
   */
  @Override
  public String getSkipToken() {
    return skipToken;
  }

  /**
   * Sets the skip.
   *
   * @param skip the new skip
   */
  public void setSkip(final Integer skip) {
    this.skip = skip;
  }

  /**
   * Gets the skip.
   *
   * @return the skip
   */
  @Override
  public Integer getSkip() {
    return skip;
  }

  /**
   * Sets the top.
   *
   * @param top the new top
   */
  public void setTop(final Integer top) {
    this.top = top;
  }

  /**
   * Gets the top.
   *
   * @return the top
   */
  @Override
  public Integer getTop() {
    return top;
  }

  /**
   * Sets the expand.
   *
   * @param expand the new expand
   */
  public void setExpand(final List<ArrayList<NavigationPropertySegment>> expand) {
    this.expand = expand;
  }

  /**
   * Gets the expand.
   *
   * @return the expand
   */
  @Override
  public List<ArrayList<NavigationPropertySegment>> getExpand() {
    return expand;
  }

  /**
   * Sets the select.
   *
   * @param select the new select
   */
  public void setSelect(final List<SelectItem> select) {
    this.select = select;
  }

  /**
   * Gets the select.
   *
   * @return the select
   */
  @Override
  public List<SelectItem> getSelect() {
    return select;
  }

  /**
   * Adds the function import parameter.
   *
   * @param name the name
   * @param value the value
   */
  public void addFunctionImportParameter(final String name, final EdmLiteral value) {
    if (functionImportParameters.equals(Collections.EMPTY_MAP)) {
      functionImportParameters = new HashMap<String, EdmLiteral>();
    }

    functionImportParameters.put(name, value);
  }

  /**
   * Gets the function import parameters.
   *
   * @return the function import parameters
   */
  @Override
  public Map<String, EdmLiteral> getFunctionImportParameters() {
    return functionImportParameters;
  }

  /**
   * Gets the custom query options.
   *
   * @return the custom query options
   */
  @Override
  public Map<String, String> getCustomQueryOptions() {
    return customQueryOptions;
  }

  /**
   * Sets the custom query options.
   *
   * @param customQueryOptions the custom query options
   */
  public void setCustomQueryOptions(final Map<String, String> customQueryOptions) {
    this.customQueryOptions = customQueryOptions;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "UriParserResult: uriType=" + uriType + ", "
        + "entityContainer=" + entityContainer + ", "
        + "entitySet=" + startEntitySet + ", "
        + "targetEntitySet=" + targetEntitySet + ", "
        + "functionImport=" + functionImport + ", "
        + "targetType=" + targetType + ", "
        + "keyPredicates=" + keyPredicates + ", "
        + "navigationSegments=" + navigationSegments + ", "
        + "propertyPath=" + propertyPath + ", "
        + "isCount=" + count + ", "
        + "isValue=" + value + ", "
        + "isLinks=" + links + ", "
        + "contentType=" + format + ", "
        + "filter=" + filter + ", "
        + "inlineCount=" + inlineCount + ", "
        + "orderBy=" + orderBy + ", "
        + "skipToken=" + skipToken + ", "
        + "skip=" + skip + ", "
        + "top=" + top + ", "
        + "expand=" + expand + ", "
        + "select=" + select + ", "
        + "functionImportParameters=" + functionImportParameters + ", "
        + "customQueryOptions=" + customQueryOptions;
  }
}
