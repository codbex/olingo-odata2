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

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.InlineCount;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmStructuralType;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties;
import org.apache.olingo.odata2.api.ep.EntityProviderWriteProperties.ODataEntityProviderPropertiesBuilder;
import org.apache.olingo.odata2.api.ep.callback.TombstoneCallback;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataHttpException;
import org.apache.olingo.odata2.api.exception.ODataNotFoundException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.ExpandSelectTreeNode;
import org.apache.olingo.odata2.api.uri.NavigationPropertySegment;
import org.apache.olingo.odata2.api.uri.PathInfo;
import org.apache.olingo.odata2.api.uri.SelectItem;
import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityLinkUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetLinksUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.apache.olingo.odata2.core.uri.UriInfoImpl;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAResponseBuilder;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPATombstoneContext;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAPaging;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.core.access.data.JPAEntityParser;
import org.apache.olingo.odata2.jpa.processor.core.callback.JPAExpandCallBack;
import org.apache.olingo.odata2.jpa.processor.core.callback.JPATombstoneCallBack;
import org.apache.olingo.odata2.jpa.processor.core.callback.JPATombstoneCallBackFI;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataJPAResponseBuilderDefault.
 */
public final class ODataJPAResponseBuilderDefault implements ODataJPAResponseBuilder {

  /** The Constant COUNT. */
  private static final String COUNT = "count";
  
  /** The o data JPA context. */
  private final ODataJPAContext oDataJPAContext;

  /**
   * Instantiates a new o data JPA response builder default.
   *
   * @param context the context
   */
  public ODataJPAResponseBuilderDefault(final ODataJPAContext context) {
    oDataJPAContext = context;
  }

  /**
   * Builds the.
   *
   * @param resultsView the results view
   * @param jpaEntities the jpa entities
   * @param contentType the content type
   * @return the o data response
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  /* Response for Read Entity Set */
  @Override
  public ODataResponse build(final GetEntitySetUriInfo resultsView, final List<Object> jpaEntities,
      final String contentType) throws ODataJPARuntimeException {

    EdmEntityType edmEntityType = null;
    ODataResponse odataResponse = null;
    List<ArrayList<NavigationPropertySegment>> expandList = null;

    try {
      edmEntityType = resultsView.getTargetEntitySet().getEntityType();
      List<Map<String, Object>> edmEntityList = null;
      JPAEntityParser jpaResultParser = new JPAEntityParser();
      final List<SelectItem> selectedItems = resultsView.getSelect();
      if (selectedItems != null && !selectedItems.isEmpty()) {
        edmEntityList =
            jpaResultParser.parse2EdmEntityList(jpaEntities, buildSelectItemList(selectedItems, edmEntityType));
      } else {
        edmEntityList = jpaResultParser.parse2EdmEntityList(jpaEntities, edmEntityType);
      }
      expandList = resultsView.getExpand();
      if (expandList != null && !expandList.isEmpty()) {
        int count = 0;
        List<EdmNavigationProperty> edmNavPropertyList = constructListofNavProperty(expandList);
        for (Object jpaEntity : jpaEntities) {
          Map<String, Object> relationShipMap = edmEntityList.get(count);
          HashMap<String, Object> navigationMap =
              jpaResultParser.parse2EdmNavigationValueMap(jpaEntity, edmNavPropertyList);
          relationShipMap.putAll(navigationMap);
          count++;
        }
      }

      EntityProviderWriteProperties feedProperties = null;

      feedProperties = getEntityProviderProperties(oDataJPAContext, resultsView, edmEntityList);
      odataResponse =
          EntityProvider.writeFeed(contentType, resultsView.getTargetEntitySet(), edmEntityList, feedProperties);
      odataResponse = ODataResponse.fromResponse(odataResponse).status(HttpStatusCodes.OK).build();

    } catch (EntityProviderException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    } catch (EdmException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }

    return odataResponse;
  }

  /**
   * Builds the.
   *
   * @param resultsView the results view
   * @param jpaEntity the jpa entity
   * @param contentType the content type
   * @return the o data response
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws ODataNotFoundException the o data not found exception
   */
  /* Response for Read Entity */
  @Override
  public ODataResponse build(final GetEntityUriInfo resultsView, final Object jpaEntity,
      final String contentType) throws ODataJPARuntimeException,
      ODataNotFoundException {

    List<ArrayList<NavigationPropertySegment>> expandList = null;
    if (jpaEntity == null) {
      throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
    }
    EdmEntityType edmEntityType = null;
    ODataResponse odataResponse = null;

    try {

      edmEntityType = resultsView.getTargetEntitySet().getEntityType();
      Map<String, Object> edmPropertyValueMap = null;

      JPAEntityParser jpaResultParser = new JPAEntityParser();
      final List<SelectItem> selectedItems = resultsView.getSelect();
      if (selectedItems != null && !selectedItems.isEmpty()) {
        edmPropertyValueMap =
            jpaResultParser.parse2EdmPropertyValueMap(jpaEntity, buildSelectItemList(selectedItems, resultsView
                .getTargetEntitySet().getEntityType()));
      } else {
        edmPropertyValueMap = jpaResultParser.parse2EdmPropertyValueMap(jpaEntity, edmEntityType);
      }

      expandList = resultsView.getExpand();
      if (expandList != null && !expandList.isEmpty()) {
        HashMap<String, Object> navigationMap =
            jpaResultParser.parse2EdmNavigationValueMap(jpaEntity, constructListofNavProperty(expandList));
        edmPropertyValueMap.putAll(navigationMap);
      }
      EntityProviderWriteProperties feedProperties = null;
      feedProperties = getEntityProviderProperties(oDataJPAContext, resultsView);
      odataResponse =
          EntityProvider.writeEntry(contentType, resultsView.getTargetEntitySet(), edmPropertyValueMap, feedProperties);

      odataResponse = ODataResponse.fromResponse(odataResponse).status(HttpStatusCodes.OK).build();

    } catch (EntityProviderException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    } catch (EdmException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }

    return odataResponse;
  }

  /**
   * Builds the.
   *
   * @param jpaEntityCount the jpa entity count
   * @return the o data response
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  /* Response for $count */
  @Override
  public ODataResponse build(final long jpaEntityCount)
      throws ODataJPARuntimeException {

    ODataResponse odataResponse = null;
    try {
      odataResponse = EntityProvider.writeText(String.valueOf(jpaEntityCount));
      odataResponse = ODataResponse.fromResponse(odataResponse).build();
    } catch (EntityProviderException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }
    return odataResponse;
  }

  /**
   * Builds the.
   *
   * @param uriInfo the uri info
   * @param createdObject the created object
   * @param contentType the content type
   * @return the o data response
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws ODataNotFoundException the o data not found exception
   */
  /* Response for Create Entity */
  @Override
  public ODataResponse build(final PostUriInfo uriInfo, final Object createdObject,
      final String contentType) throws ODataJPARuntimeException,
      ODataNotFoundException {

    if (createdObject == null) {
      throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
    }

    EdmEntityType edmEntityType = null;
    ODataResponse odataResponse = null;

    try {

      edmEntityType = uriInfo.getTargetEntitySet().getEntityType();
      Map<String, Object> edmPropertyValueMap = null;

      JPAEntityParser jpaResultParser = new JPAEntityParser();
      edmPropertyValueMap = jpaResultParser.parse2EdmPropertyValueMap(createdObject, edmEntityType);

      EntityProviderWriteProperties feedProperties = null;
      try {
        feedProperties = getEntityProviderPropertiesforPost(oDataJPAContext);
      } catch (ODataException e) {
        throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
      }

      odataResponse =
          EntityProvider.writeEntry(contentType, uriInfo.getTargetEntitySet(), edmPropertyValueMap, feedProperties);

      odataResponse = ODataResponse.fromResponse(odataResponse).status(HttpStatusCodes.CREATED).build();

    } catch (EntityProviderException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    } catch (EdmException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }

    return odataResponse;
  }

  /**
   * Builds the.
   *
   * @param putUriInfo the put uri info
   * @param updatedObject the updated object
   * @return the o data response
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws ODataNotFoundException the o data not found exception
   */
  /* Response for Update Entity */
  @Override
  public ODataResponse build(final PutMergePatchUriInfo putUriInfo, final Object updatedObject)
      throws ODataJPARuntimeException, ODataNotFoundException {
    if (updatedObject == null) {
      throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
    }
    return ODataResponse.status(HttpStatusCodes.NO_CONTENT).build();
  }

  /**
   * Builds the.
   *
   * @param deleteUriInfo the delete uri info
   * @param deletedObject the deleted object
   * @return the o data response
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws ODataNotFoundException the o data not found exception
   */
  /* Response for Delete Entity */
  @Override
  public ODataResponse build(final DeleteUriInfo deleteUriInfo, final Object deletedObject)
      throws ODataJPARuntimeException, ODataNotFoundException {

    if (deletedObject == null) {
      throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
    }
    return ODataResponse.status(HttpStatusCodes.NO_CONTENT).build();
  }

  /**
   * Builds the.
   *
   * @param resultsView the results view
   * @param result the result
   * @return the o data response
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  /* Response for Function Import Single Result */
  @Override
  public ODataResponse build(final GetFunctionImportUriInfo resultsView, final Object result)
      throws ODataJPARuntimeException {

    try {
      final EdmFunctionImport functionImport = resultsView.getFunctionImport();
      final EdmSimpleType type = (EdmSimpleType) functionImport.getReturnType().getType();

      if (result != null) {
        ODataResponse response = null;
        if (type.getDefaultType().equals(byte[].class)) {
          response = EntityProvider.writeBinary("application/octet-stream", (byte[]) result);
        } else {
          final String value = type.valueToString(result, EdmLiteralKind.DEFAULT, null);
          response = EntityProvider.writeText(value);
        }

        return ODataResponse.fromResponse(response).build();
      } else {
        throw new ODataNotFoundException(ODataHttpException.COMMON);
      }
    } catch (EdmException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    } catch (EntityProviderException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
    }
  }

  /**
   * Builds the.
   *
   * @param resultsView the results view
   * @param resultList the result list
   * @param contentType the content type
   * @return the o data response
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   * @throws ODataNotFoundException the o data not found exception
   */
  /* Response for Function Import Multiple Result */
  @Override
  public ODataResponse build(final GetFunctionImportUriInfo resultsView, final List<Object> resultList,
      final String contentType) throws ODataJPARuntimeException,
      ODataNotFoundException {

    ODataResponse odataResponse = null;

    if (resultList != null) {
      JPAEntityParser jpaResultParser = new JPAEntityParser();
      EdmType edmType = null;
      EdmFunctionImport functionImport = null;
      List<Map<String, Object>> edmEntityList = null;
      Object result = null;
      try {
      
        functionImport = resultsView.getFunctionImport();
        edmType = functionImport.getReturnType().getType();
        
        final List<SelectItem> selectedItems = resultsView.getSelect();
        List<ArrayList<NavigationPropertySegment>> expandList = null;
        EntityProviderWriteProperties feedProperties = null;

        if (edmType.getKind().equals(EdmTypeKind.ENTITY) || edmType.getKind().equals(EdmTypeKind.COMPLEX)) {
	    	if (selectedItems != null && !selectedItems.isEmpty()) {
	            edmEntityList =
	                jpaResultParser.parse2EdmEntityList(resultList, 
	                		buildSelectItemList(selectedItems, (EdmStructuralType) edmType));
	          } else {
	              edmEntityList = jpaResultParser.parse2EdmEntityList(resultList, (EdmStructuralType) edmType);
	          }
	    	expandList = resultsView.getExpand();
	        if (expandList != null && !expandList.isEmpty()) {
	          int count = 0;
	          List<EdmNavigationProperty> edmNavPropertyList = constructListofNavProperty(expandList);
	          for (Object jpaEntity : resultList) {
	            Map<String, Object> relationShipMap = edmEntityList.get(count);
	            HashMap<String, Object> navigationMap =
	                jpaResultParser.parse2EdmNavigationValueMap(jpaEntity, edmNavPropertyList);
	            relationShipMap.putAll(navigationMap);
	            count++;
	          }
	        }
	        if (functionImport.getReturnType().getMultiplicity().equals(EdmMultiplicity.MANY)) {
	        	result = edmEntityList;
	        } else {
	        	result = edmEntityList.get(0);
	        }
	    	feedProperties = 
	        		getEntityProviderProperties(oDataJPAContext, resultsView, edmEntityList);


        } else if (edmType.getKind().equals(EdmTypeKind.SIMPLE)) {
          result = resultList.get(0);
          feedProperties =
                  EntityProviderWriteProperties.serviceRoot(
                		  oDataJPAContext.getODataContext().getPathInfo().getServiceRoot())
                      .build();
        }
        
        odataResponse =
            EntityProvider.writeFunctionImport(contentType, resultsView.getFunctionImport(), result, feedProperties);
        odataResponse = ODataResponse.fromResponse(odataResponse).status(HttpStatusCodes.OK).build();

      } catch (EdmException e) {
        throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
      } catch (EntityProviderException e) {
        throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
      } catch (ODataException e) {
        throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
      }

    } else {
      throw new ODataNotFoundException(ODataHttpException.COMMON);
    }

    return odataResponse;
  }

  /**
   * Builds the.
   *
   * @param resultsView the results view
   * @param jpaEntity the jpa entity
   * @param contentType the content type
   * @return the o data response
   * @throws ODataNotFoundException the o data not found exception
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  /* Response for Read Entity Link */
  @Override
  public ODataResponse build(final GetEntityLinkUriInfo resultsView, final Object jpaEntity,
      final String contentType) throws ODataNotFoundException,
      ODataJPARuntimeException {

    if (jpaEntity == null) {
      throw new ODataNotFoundException(ODataNotFoundException.ENTITY);
    }
    EdmEntityType edmEntityType = null;
    ODataResponse odataResponse = null;

    try {

      EdmEntitySet entitySet = resultsView.getTargetEntitySet();
      edmEntityType = entitySet.getEntityType();
      Map<String, Object> edmPropertyValueMap = null;

      JPAEntityParser jpaResultParser = new JPAEntityParser();
      edmPropertyValueMap = jpaResultParser.parse2EdmPropertyValueMap(jpaEntity, edmEntityType.getKeyProperties());

      EntityProviderWriteProperties entryProperties =
          EntityProviderWriteProperties.serviceRoot(oDataJPAContext.getODataContext().getPathInfo().getServiceRoot())
              .build();

      ODataResponse response = EntityProvider.writeLink(contentType, entitySet, edmPropertyValueMap, entryProperties);

      odataResponse = ODataResponse.fromResponse(response).build();

    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);

    }

    return odataResponse;
  }

  /**
   * Builds the.
   *
   * @param resultsView the results view
   * @param jpaEntities the jpa entities
   * @param contentType the content type
   * @return the o data response
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  /* Response for Read Entity Links */
  @Override
  public ODataResponse build(final GetEntitySetLinksUriInfo resultsView, final List<Object> jpaEntities,
      final String contentType) throws ODataJPARuntimeException {
    EdmEntityType edmEntityType = null;
    ODataResponse odataResponse = null;

    try {

      EdmEntitySet entitySet = resultsView.getTargetEntitySet();
      edmEntityType = entitySet.getEntityType();
      List<EdmProperty> keyProperties = edmEntityType.getKeyProperties();

      List<Map<String, Object>> edmEntityList = new ArrayList<Map<String, Object>>();
      Map<String, Object> edmPropertyValueMap = null;
      JPAEntityParser jpaResultParser = new JPAEntityParser();

      for (Object jpaEntity : jpaEntities) {
        edmPropertyValueMap = jpaResultParser.parse2EdmPropertyValueMap(jpaEntity, keyProperties);
        edmEntityList.add(edmPropertyValueMap);
      }

      Integer count = null;
      if (resultsView.getInlineCount() != null) {
        if ((resultsView.getSkip() != null || resultsView.getTop() != null)) {
          // when $skip and/or $top is present with $inlinecount
          count = getInlineCountForNonFilterQueryLinks(edmEntityList, resultsView);
        } else {
          // In all other cases
          count = resultsView.getInlineCount() == InlineCount.ALLPAGES ? edmEntityList.size() : null;
        }
      }

      ODataContext context = oDataJPAContext.getODataContext();
      EntityProviderWriteProperties entryProperties =
          EntityProviderWriteProperties.serviceRoot(context.getPathInfo().getServiceRoot()).inlineCountType(
              resultsView.getInlineCount()).inlineCount(count).build();

      odataResponse = EntityProvider.writeLinks(contentType, entitySet, edmEntityList, entryProperties);

      odataResponse = ODataResponse.fromResponse(odataResponse).build();

    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }

    return odataResponse;

  }

  /**
   * Gets the inline count for non filter query links.
   *
   * @param edmEntityList the edm entity list
   * @param resultsView the results view
   * @return the inline count for non filter query links
   */
  /*
   * This method handles $inlinecount request. It also modifies the list of results in case of
   * $inlinecount and $top/$skip combinations. Specific to LinksUriInfo.
   * 
   * @param edmEntityList
   * 
   * @param resultsView
   * 
   * @return
   */
  private static Integer getInlineCountForNonFilterQueryLinks(final List<Map<String, Object>> edmEntityList,
      final GetEntitySetLinksUriInfo resultsView) {
    // when $skip and/or $top is present with $inlinecount, first get the total count
    Integer count = null;
    if (resultsView.getInlineCount() == InlineCount.ALLPAGES) {
      if (resultsView.getSkip() != null || resultsView.getTop() != null) {
        count = edmEntityList.size();
        // Now update the list
        if (resultsView.getSkip() != null) {
          // Index checks to avoid IndexOutOfBoundsException
          if (resultsView.getSkip() > edmEntityList.size()) {
            edmEntityList.clear();
            return count;
          }
          edmEntityList.subList(0, resultsView.getSkip()).clear();
        }
        if (resultsView.getTop() != null && resultsView.getTop() >= 0 && resultsView.getTop() < edmEntityList.size()) {
          edmEntityList.subList(0, resultsView.getTop());
        }
      }
    }// Inlinecount of None is handled by default - null
    return count;
  }
  
  /**
   * Gets the entity provider properties.
   *
   * @param odataJPAContext the odata JPA context
   * @param resultsView the results view
   * @param edmEntityList the edm entity list
   * @return the entity provider properties
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  /*
   * Method to build the entity provider Property.Callbacks for $expand would
   * be registered here
   */
  private static EntityProviderWriteProperties getEntityProviderProperties(final ODataJPAContext odataJPAContext,
      final GetFunctionImportUriInfo resultsView, final List<Map<String, Object>> edmEntityList)
      throws ODataJPARuntimeException {
    ODataEntityProviderPropertiesBuilder entityFeedPropertiesBuilder = null;
    ODataContext context = odataJPAContext.getODataContext();

    Integer count = null;
    if (resultsView.getInlineCount() != null) {
       count = getInlineCountForNonFilterQueryEntitySet(edmEntityList, resultsView);
    }

    try {
      PathInfo pathInfo = context.getPathInfo();
      URI serviceRoot = pathInfo.getServiceRoot();

      entityFeedPropertiesBuilder =
          EntityProviderWriteProperties.serviceRoot(pathInfo.getServiceRoot());
      JPAPaging paging = odataJPAContext.getPaging();
      if (odataJPAContext.getPageSize() > 0 && paging != null && paging.getNextPage() > 0) {
        String nextLink =
            serviceRoot.relativize(pathInfo.getRequestUri()).toString();
        nextLink = percentEncodeNextLink(nextLink);
        nextLink += (nextLink != null ? nextLink.contains("?") ? "&" : "?" : "?")
            + "$skiptoken=" + odataJPAContext.getPaging().getNextPage();
        entityFeedPropertiesBuilder.nextLink(nextLink);
      }
      entityFeedPropertiesBuilder.inlineCount(count);
      entityFeedPropertiesBuilder.inlineCountType(resultsView.getInlineCount());
      ExpandSelectTreeNode expandSelectTree =
          UriParser.createExpandSelectTree(resultsView.getSelect(), resultsView.getExpand());

      Map<String, ODataCallback> expandCallBack =
          JPAExpandCallBack.getCallbacks(serviceRoot, expandSelectTree, resultsView.getExpand());

      Map<String, ODataCallback> callBackMap = new HashMap<String, ODataCallback>();
      callBackMap.putAll(expandCallBack);
      
      String deltaToken = ODataJPATombstoneContext.getDeltaToken();
      if (deltaToken != null) {
        callBackMap.put(TombstoneCallback.CALLBACK_KEY_TOMBSTONE, new JPATombstoneCallBackFI(serviceRoot.toString(),
            resultsView, deltaToken));
      }

      entityFeedPropertiesBuilder.callbacks(callBackMap);
      entityFeedPropertiesBuilder.expandSelectTree(expandSelectTree);
      
    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
    }

    return entityFeedPropertiesBuilder.build();
  }

  /**
   * Gets the entity provider properties.
   *
   * @param odataJPAContext the odata JPA context
   * @param resultsView the results view
   * @param edmEntityList the edm entity list
   * @return the entity provider properties
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  /*
   * Method to build the entity provider Property.Callbacks for $expand would
   * be registered here
   */
  private static EntityProviderWriteProperties getEntityProviderProperties(final ODataJPAContext odataJPAContext,
      final GetEntitySetUriInfo resultsView, final List<Map<String, Object>> edmEntityList)
      throws ODataJPARuntimeException {
    ODataEntityProviderPropertiesBuilder entityFeedPropertiesBuilder = null;
    ODataContext context = odataJPAContext.getODataContext();

    Integer count = null;
    if (resultsView.getInlineCount() != null) {
       count = getInlineCountForNonFilterQueryEntitySet(edmEntityList, resultsView);
    }

    try {
      PathInfo pathInfo = context.getPathInfo();
      URI serviceRoot = pathInfo.getServiceRoot();

      entityFeedPropertiesBuilder =
          EntityProviderWriteProperties.serviceRoot(pathInfo.getServiceRoot());
      JPAPaging paging = odataJPAContext.getPaging();
      if (odataJPAContext.getPageSize() > 0 && paging != null && paging.getNextPage() > 0) {
        String nextLink =
            serviceRoot.relativize(pathInfo.getRequestUri()).toString();
        nextLink = percentEncodeNextLink(nextLink);
        nextLink += (nextLink != null ? nextLink.contains("?") ? "&" : "?" : "?")
            + "$skiptoken=" + odataJPAContext.getPaging().getNextPage();
        entityFeedPropertiesBuilder.nextLink(nextLink);
      }
      entityFeedPropertiesBuilder.inlineCount(count);
      entityFeedPropertiesBuilder.inlineCountType(resultsView.getInlineCount());
      ExpandSelectTreeNode expandSelectTree =
          UriParser.createExpandSelectTree(resultsView.getSelect(), resultsView.getExpand());

      Map<String, ODataCallback> expandCallBack =
          JPAExpandCallBack.getCallbacks(serviceRoot, expandSelectTree, resultsView.getExpand());

      Map<String, ODataCallback> callBackMap = new HashMap<String, ODataCallback>();
      callBackMap.putAll(expandCallBack);

      String deltaToken = ODataJPATombstoneContext.getDeltaToken();
      if (deltaToken != null) {
        callBackMap.put(TombstoneCallback.CALLBACK_KEY_TOMBSTONE, new JPATombstoneCallBack(serviceRoot.toString(),
            resultsView, deltaToken));
      }

      entityFeedPropertiesBuilder.callbacks(callBackMap);
      entityFeedPropertiesBuilder.expandSelectTree(expandSelectTree);

    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
    }

    return entityFeedPropertiesBuilder.build();
  }

  /**
   * Percent encode next link.
   *
   * @param link the link
   * @return the string
   */
  private static String percentEncodeNextLink(final String link) {
    if (link == null) {
      return null;
    }

    return link.replaceAll("\\$skiptoken=.+?(?:&|$)", "")
        .replaceAll("\\$skip=.+?(?:&|$)", "")
        .replaceFirst("(?:\\?|&)$", ""); // Remove potentially trailing "?" or "&" left over from remove actions
  }
  
  /**
   * Checks if is numeric.
   *
   * @param strNum the str num
   * @return true, if is numeric
   */
  public static boolean isNumeric(String strNum) {
    return strNum.matches("-?\\d+(\\.\\d+)?");
}

  /**
   * Gets the inline count for non filter query entity set.
   *
   * @param edmEntityList the edm entity list
   * @param resultsView the results view
   * @return the inline count for non filter query entity set
   */
  /*
   * This method handles $inlinecount request. It also modifies the list of results in case of
   * $inlinecount and $top/$skip combinations. Specific to Entity Set.
   */
  private static Integer getInlineCountForNonFilterQueryEntitySet(final List<Map<String, Object>> edmEntityList,
      final GetEntitySetUriInfo resultsView) {
    // when $skip and/or $top is present with $inlinecount, first get the total count
    Integer count = null;
    if (resultsView.getInlineCount() == InlineCount.ALLPAGES) {
      count = edmEntityList.size();
      if (resultsView.getCustomQueryOptions() != null) {
        String countValue = resultsView.getCustomQueryOptions().get(COUNT);
        if (countValue != null && isNumeric(countValue)) {
          count = Integer.parseInt(countValue);
          resultsView.getCustomQueryOptions().remove(COUNT);
          if (resultsView.getCustomQueryOptions().size() == 0) {
            ((UriInfoImpl) resultsView).setCustomQueryOptions(null);
          }
        }
      }
    }// Inlinecount of None is handled by default - null
    return count;
  }
  
  /**
   * Gets the inline count for non filter query entity set.
   *
   * @param edmEntityList the edm entity list
   * @param resultsView the results view
   * @return the inline count for non filter query entity set
   */
  /*
   * This method handles $inlinecount request. It also modifies the list of results in case of
   * $inlinecount and $top/$skip combinations. Specific to Entity Set.
   */
  private static Integer getInlineCountForNonFilterQueryEntitySet(final List<Map<String, Object>> edmEntityList,
      final GetFunctionImportUriInfo resultsView) {
    // when $skip and/or $top is present with $inlinecount, first get the total count
    Integer count = null;
    if (resultsView.getInlineCount() == InlineCount.ALLPAGES) {
      count = edmEntityList.size();
      if (resultsView.getCustomQueryOptions() != null) {
        String countValue = resultsView.getCustomQueryOptions().get(COUNT);
        if (countValue != null && isNumeric(countValue)) {
          count = Integer.parseInt(countValue);
          resultsView.getCustomQueryOptions().remove(COUNT);
          if (resultsView.getCustomQueryOptions().size() == 0) {
            ((UriInfoImpl) resultsView).setCustomQueryOptions(null);
          }
        }
      }
    }// Inlinecount of None is handled by default - null
    return count;
  }

  /**
   * Gets the entity provider properties.
   *
   * @param odataJPAContext the odata JPA context
   * @param resultsView the results view
   * @return the entity provider properties
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  private static EntityProviderWriteProperties getEntityProviderProperties(final ODataJPAContext odataJPAContext,
      final GetEntityUriInfo resultsView) throws ODataJPARuntimeException {
    ODataEntityProviderPropertiesBuilder entityFeedPropertiesBuilder = null;
    ExpandSelectTreeNode expandSelectTree = null;
    try {
      entityFeedPropertiesBuilder =
          EntityProviderWriteProperties.serviceRoot(odataJPAContext.getODataContext().getPathInfo().getServiceRoot());
      expandSelectTree = UriParser.createExpandSelectTree(resultsView.getSelect(), resultsView.getExpand());
      entityFeedPropertiesBuilder.expandSelectTree(expandSelectTree);
      entityFeedPropertiesBuilder.callbacks(JPAExpandCallBack.getCallbacks(odataJPAContext.getODataContext()
          .getPathInfo().getServiceRoot(), expandSelectTree, resultsView.getExpand()));
    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
    }

    return entityFeedPropertiesBuilder.build();
  }

  /**
   * Gets the entity provider propertiesfor post.
   *
   * @param odataJPAContext the odata JPA context
   * @return the entity provider propertiesfor post
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  private static EntityProviderWriteProperties getEntityProviderPropertiesforPost(
      final ODataJPAContext odataJPAContext) throws ODataJPARuntimeException {
    ODataEntityProviderPropertiesBuilder entityFeedPropertiesBuilder = null;
    try {
      entityFeedPropertiesBuilder =
          EntityProviderWriteProperties.serviceRoot(odataJPAContext.getODataContext().getPathInfo().getServiceRoot());
    } catch (ODataException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
    }

    return entityFeedPropertiesBuilder.build();
  }

  /**
   * Builds the select item list.
   *
   * @param selectItems the select items
   * @param entity the entity
   * @return the list
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  private static List<EdmProperty> buildSelectItemList(final List<SelectItem> selectItems, 
		  final EdmStructuralType entity) throws ODataJPARuntimeException {
    boolean flag = false;
    List<EdmProperty> selectPropertyList = new ArrayList<EdmProperty>();
    try {
      for (SelectItem selectItem : selectItems) {
        if (selectItem.getNavigationPropertySegments().size() <= 0) {
          if (selectItem.isStar()) {
            selectPropertyList.addAll(getEdmProperties(entity));
            return selectPropertyList;
          } else {
            selectPropertyList.add(selectItem.getProperty());
          }
        }
      }
      if (entity instanceof EdmEntityType) {
    	  EdmEntityType edmEntity = (EdmEntityType)entity;
	      for (EdmProperty keyProperty : edmEntity.getKeyProperties()) {
	        flag = true;
	        for (SelectItem selectedItem : selectItems) {
	          if (!selectedItem.isStar() && keyProperty.equals(selectedItem.getProperty())) {
	            flag = false;
	            break;
	          }
	        }
	        if (flag) {
	          selectPropertyList.add(keyProperty);
	        }
	      }
      }
    } catch (EdmException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
    }
    return selectPropertyList;
  }

  /**
   * Construct listof nav property.
   *
   * @param expandList the expand list
   * @return the list
   */
  private static List<EdmNavigationProperty> constructListofNavProperty(
      final List<ArrayList<NavigationPropertySegment>> expandList) {
    List<EdmNavigationProperty> navigationPropertyList = new ArrayList<EdmNavigationProperty>();
    for (ArrayList<NavigationPropertySegment> navpropSegment : expandList) {
      navigationPropertyList.add(navpropSegment.get(0).getNavigationProperty());
    }
    return navigationPropertyList;
  }

  /**
   * Gets the edm properties.
   *
   * @param structuralType the structural type
   * @return the edm properties
   * @throws ODataJPARuntimeException the o data JPA runtime exception
   */
  private static List<EdmProperty> getEdmProperties(final EdmStructuralType structuralType)
      throws ODataJPARuntimeException {
    List<EdmProperty> edmProperties = new ArrayList<EdmProperty>();
    try {
      for (String propertyName : structuralType.getPropertyNames()) {
        edmProperties.add((EdmProperty) structuralType.getProperty(propertyName));
      }
    } catch (EdmException e) {
      throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.INNER_EXCEPTION, e);
    }
    return edmProperties;
  }

}
