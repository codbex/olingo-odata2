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
package org.apache.olingo.odata2.api.edm;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface Edm.
 *
 * @org.apache.olingo.odata2.DoNotImplement Entity Data Model (EDM)
 * <p>Interface representing a Entity Data Model as described in the Conceptual Schema Definition.
 */
public interface Edm {

  /** The Constant NAMESPACE_EDM_2006_04. */
  public static final String NAMESPACE_EDM_2006_04 = "http://schemas.microsoft.com/ado/2006/04/edm";
  
  /** The Constant NAMESPACE_EDM_2007_05. */
  public static final String NAMESPACE_EDM_2007_05 = "http://schemas.microsoft.com/ado/2007/05/edm";
  
  /** The Constant NAMESPACE_EDM_2008_01. */
  public static final String NAMESPACE_EDM_2008_01 = "http://schemas.microsoft.com/ado/2008/01/edm";
  
  /** The Constant NAMESPACE_EDM_2008_09. */
  public static final String NAMESPACE_EDM_2008_09 = "http://schemas.microsoft.com/ado/2008/09/edm";
  
  /** The Constant NAMESPACE_APP_2007. */
  public static final String NAMESPACE_APP_2007 = "http://www.w3.org/2007/app";
  
  /** The Constant NAMESPACE_ATOM_2005. */
  public static final String NAMESPACE_ATOM_2005 = "http://www.w3.org/2005/Atom";
  
  /** The Constant NAMESPACE_D_2007_08. */
  public static final String NAMESPACE_D_2007_08 = "http://schemas.microsoft.com/ado/2007/08/dataservices";
  
  /** The Constant NAMESPACE_M_2007_08. */
  public static final String NAMESPACE_M_2007_08 = "http://schemas.microsoft.com/ado/2007/08/dataservices/metadata";
  
  /** The Constant NAMESPACE_EDMX_2007_06. */
  public static final String NAMESPACE_EDMX_2007_06 = "http://schemas.microsoft.com/ado/2007/06/edmx";
  
  /** The Constant NAMESPACE_REL_2007_08. */
  public static final String NAMESPACE_REL_2007_08 = "http://schemas.microsoft.com/ado/2007/08/dataservices/related/";
  
  /** The Constant NAMESPACE_REL_ASSOC_2007_08. */
  public static final String NAMESPACE_REL_ASSOC_2007_08 =
      "http://schemas.microsoft.com/ado/2007/08/dataservices/relatedlinks/";
  
  /** The Constant NAMESPACE_SCHEME_2007_08. */
  public static final String NAMESPACE_SCHEME_2007_08 = "http://schemas.microsoft.com/ado/2007/08/dataservices/scheme";
  
  /** The Constant NAMESPACE_XML_1998. */
  public static final String NAMESPACE_XML_1998 = "http://www.w3.org/XML/1998/namespace";
  
  /** The Constant PREFIX_EDM. */
  public static final String PREFIX_EDM = "edm";
  
  /** The Constant PREFIX_APP. */
  public static final String PREFIX_APP = "app";
  
  /** The Constant PREFIX_ATOM. */
  public static final String PREFIX_ATOM = "atom";
  
  /** The Constant PREFIX_D. */
  public static final String PREFIX_D = "d";
  
  /** The Constant PREFIX_M. */
  public static final String PREFIX_M = "m";
  
  /** The Constant PREFIX_XML. */
  public static final String PREFIX_XML = "xml";
  
  /** The Constant PREFIX_EDMX. */
  public static final String PREFIX_EDMX = "edmx";
  
  /** The Constant LINK_REL_SELF. */
  public static final String LINK_REL_SELF = "self";
  
  /** The Constant LINK_REL_EDIT_MEDIA. */
  public static final String LINK_REL_EDIT_MEDIA = "edit-media";
  
  /** The Constant LINK_REL_EDIT. */
  public static final String LINK_REL_EDIT = "edit";
  
  /** The Constant LINK_REL_NEXT. */
  public static final String LINK_REL_NEXT = "next";
  
  /** The Constant DELIMITER. */
  public static final String DELIMITER = ".";

  /**
   * Get entity container by name
   * <p>See {@link EdmEntityContainer} for more information.
   *
   * @param name the name
   * @return {@link EdmEntityContainer}
   * @throws EdmException the edm exception
   */
  EdmEntityContainer getEntityContainer(String name) throws EdmException;

  /**
   * Get entity type by full qualified name
   * <p>See {@link EdmEntityType} for more information.
   *
   * @param namespace the namespace
   * @param name the name
   * @return {@link EdmEntityType}
   * @throws EdmException the edm exception
   */
  EdmEntityType getEntityType(String namespace, String name) throws EdmException;

  /**
   * Get complex type by full qualified name
   * <p>See {@link EdmComplexType} for more information.
   *
   * @param namespace the namespace
   * @param name the name
   * @return {@link EdmComplexType}
   * @throws EdmException the edm exception
   */
  EdmComplexType getComplexType(String namespace, String name) throws EdmException;

  /**
   * Get association by full qualified name
   * <p>See {@link EdmAssociation} for more information.
   *
   * @param namespace the namespace
   * @param name the name
   * @return {@link EdmAssociation}
   * @throws EdmException the edm exception
   */
  EdmAssociation getAssociation(String namespace, String name) throws EdmException;

  /**
   * Get service metadata
   * <p>See {@link EdmServiceMetadata} for more information.
   * @return {@link EdmServiceMetadata}
   */
  EdmServiceMetadata getServiceMetadata();

  /**
   * Get default entity container
   * <p>See {@link EdmEntityContainer} for more information.
   *
   * @return {@link EdmEntityContainer}
   * @throws EdmException the edm exception
   */
  EdmEntityContainer getDefaultEntityContainer() throws EdmException;

  /**
   * Get all contained EntitySets.
   *
   * @return a list of {@link EdmEntitySet}
   * @throws EdmException the edm exception
   */
  List<EdmEntitySet> getEntitySets() throws EdmException;

  /**
   * Get all contained FunctionImports.
   *
   * @return a list of {@link EdmFunctionImport}
   * @throws EdmException the edm exception
   */
  List<EdmFunctionImport> getFunctionImports() throws EdmException;
}
