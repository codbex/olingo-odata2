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
package org.apache.olingo.odata2.core.ep.util;

// TODO: Auto-generated Javadoc
/**
 * String constants for deserialization and serialization of metadata document.
 * 
 */
public class XmlMetadataConstants {
  
  /** The Constant EDMX_TAG. */
  public static final String EDMX_TAG = "Edmx";
  
  /** The Constant EDM_DATA_SERVICES. */
  public static final String EDM_DATA_SERVICES = "DataServices";
  
  /** The Constant EDM_DATA_SERVICE_VERSION. */
  public static final String EDM_DATA_SERVICE_VERSION = "DataServiceVersion";
  
  /** The Constant EDM_SCHEMA. */
  public static final String EDM_SCHEMA = "Schema";
  
  /** The Constant EDM_SCHEMA_NAMESPACE. */
  public static final String EDM_SCHEMA_NAMESPACE = "Namespace";
  
  /** The Constant EDM_SCHEMA_ALIAS. */
  public static final String EDM_SCHEMA_ALIAS = "Alias";
  
  /** The Constant EDM_ENTITY_TYPE. */
  public static final String EDM_ENTITY_TYPE = "EntityType";
  
  /** The Constant EDM_COMPLEX_TYPE. */
  public static final String EDM_COMPLEX_TYPE = "ComplexType";
  
  /** The Constant EDM_ASSOCIATION. */
  public static final String EDM_ASSOCIATION = "Association";
  
  /** The Constant EDM_USING. */
  public static final String EDM_USING = "Using";

  /** The Constant EDM_NAME. */
  public static final String EDM_NAME = "Name";
  
  /** The Constant EDM_TYPE. */
  public static final String EDM_TYPE = "Type";
  
  /** The Constant EDM_ENTITY_TYPE_KEY. */
  public static final String EDM_ENTITY_TYPE_KEY = "Key";
  
  /** The Constant EDM_TYPE_ABSTRACT. */
  public static final String EDM_TYPE_ABSTRACT = "Abstract";
  
  /** The Constant EDM_BASE_TYPE. */
  public static final String EDM_BASE_TYPE = "BaseType";
  
  /** The Constant EDM_PROPERTY_REF. */
  public static final String EDM_PROPERTY_REF = "PropertyRef";

  /** The Constant EDM_PROPERTY. */
  public static final String EDM_PROPERTY = "Property";
  
  /** The Constant EDM_PROPERTY_NULLABLE. */
  public static final String EDM_PROPERTY_NULLABLE = "Nullable";
  
  /** The Constant EDM_PROPERTY_MAX_LENGTH. */
  public static final String EDM_PROPERTY_MAX_LENGTH = "MaxLength";
  
  /** The Constant EDM_PROPERTY_MAX_LENGTH_MAX_VALUE_FIRST_UPPERCASE. */
  public static final String EDM_PROPERTY_MAX_LENGTH_MAX_VALUE_FIRST_UPPERCASE = "Max";
  
  /** The Constant EDM_PROPERTY_MAX_LENGTH_MAX_VALUE_LOWERCASE. */
  public static final String EDM_PROPERTY_MAX_LENGTH_MAX_VALUE_LOWERCASE = "max";
  
  /** The Constant EDM_PROPERTY_DEFAULT_VALUE. */
  public static final String EDM_PROPERTY_DEFAULT_VALUE = "DefaultValue";
  
  /** The Constant EDM_PROPERTY_FIXED_LENGTH. */
  public static final String EDM_PROPERTY_FIXED_LENGTH = "FixedLength";
  
  /** The Constant EDM_PROPERTY_UNICODE. */
  public static final String EDM_PROPERTY_UNICODE = "Unicode";
  
  /** The Constant EDM_PROPERTY_COLLATION. */
  public static final String EDM_PROPERTY_COLLATION = "Collation";
  
  /** The Constant EDM_PROPERTY_PRECISION. */
  public static final String EDM_PROPERTY_PRECISION = "Precision";
  
  /** The Constant EDM_PROPERTY_SCALE. */
  public static final String EDM_PROPERTY_SCALE = "Scale";
  
  /** The Constant EDM_PROPERTY_CONCURRENCY_MODE. */
  public static final String EDM_PROPERTY_CONCURRENCY_MODE = "ConcurrencyMode";

  /** The Constant EDM_NAVIGATION_PROPERTY. */
  public static final String EDM_NAVIGATION_PROPERTY = "NavigationProperty";
  
  /** The Constant EDM_NAVIGATION_FROM_ROLE. */
  public static final String EDM_NAVIGATION_FROM_ROLE = "FromRole";
  
  /** The Constant EDM_NAVIGATION_TO_ROLE. */
  public static final String EDM_NAVIGATION_TO_ROLE = "ToRole";
  
  /** The Constant EDM_NAVIGATION_RELATIONSHIP. */
  public static final String EDM_NAVIGATION_RELATIONSHIP = "Relationship";

  /** The Constant EDM_ASSOCIATION_CONSTRAINT. */
  public static final String EDM_ASSOCIATION_CONSTRAINT = "ReferentialConstraint";
  
  /** The Constant EDM_ASSOCIATION_END. */
  public static final String EDM_ASSOCIATION_END = "End";

  /** The Constant EDM_ASSOCIATION_MULTIPLICITY. */
  public static final String EDM_ASSOCIATION_MULTIPLICITY = "Multiplicity";
  
  /** The Constant EDM_ASSOCIATION_ONDELETE. */
  public static final String EDM_ASSOCIATION_ONDELETE = "OnDelete";
  
  /** The Constant EDM_ONDELETE_ACTION. */
  public static final String EDM_ONDELETE_ACTION = "Action";

  /** The Constant EDM_ENTITY_CONTAINER. */
  public static final String EDM_ENTITY_CONTAINER = "EntityContainer";
  
  /** The Constant EDM_CONTAINER_IS_DEFAULT. */
  public static final String EDM_CONTAINER_IS_DEFAULT = "IsDefaultEntityContainer";
  
  /** The Constant EDM_CONTAINER_EXTENDZ. */
  public static final String EDM_CONTAINER_EXTENDZ = "Extendz";
  
  /** The Constant EDM_ENTITY_SET. */
  public static final String EDM_ENTITY_SET = "EntitySet";
  
  /** The Constant EDM_ASSOCIATION_SET. */
  public static final String EDM_ASSOCIATION_SET = "AssociationSet";
  
  /** The Constant EDM_FUNCTION_IMPORT. */
  public static final String EDM_FUNCTION_IMPORT = "FunctionImport";

  /** The Constant EDM_FUNCTION_IMPORT_HTTP_METHOD. */
  public static final String EDM_FUNCTION_IMPORT_HTTP_METHOD = "HttpMethod";
  
  /** The Constant EDM_FUNCTION_IMPORT_RETURN. */
  public static final String EDM_FUNCTION_IMPORT_RETURN = "ReturnType";
  
  /** The Constant EDM_FUNCTION_PARAMETER. */
  public static final String EDM_FUNCTION_PARAMETER = "Parameter";
  
  /** The Constant EDM_FUNCTION_PARAMETER_MODE. */
  public static final String EDM_FUNCTION_PARAMETER_MODE = "Mode";

  /** The Constant M_ENTITY_TYPE_HAS_STREAM. */
  public static final String M_ENTITY_TYPE_HAS_STREAM = "HasStream";
  
  /** The Constant M_MIMETYPE. */
  public static final String M_MIMETYPE = "MimeType";
  
  /** The Constant M_FC_TARGET_PATH. */
  public static final String M_FC_TARGET_PATH = "FC_TargetPath";
  
  /** The Constant M_FC_SOURCE_PATH. */
  public static final String M_FC_SOURCE_PATH = "FC_SourcePath";
  
  /** The Constant M_FC_NS_URI. */
  public static final String M_FC_NS_URI = "FC_NsUri";
  
  /** The Constant M_FC_PREFIX. */
  public static final String M_FC_PREFIX = "FC_NsPrefix";
  
  /** The Constant M_FC_KEEP_IN_CONTENT. */
  public static final String M_FC_KEEP_IN_CONTENT = "FC_KeepInContent";
  
  /** The Constant M_FC_CONTENT_KIND. */
  public static final String M_FC_CONTENT_KIND = "FC_ContentKind";
  
  /** The Constant EDM_ASSOCIATION_PRINCIPAL. */
  public static final String EDM_ASSOCIATION_PRINCIPAL = "Principal";
  
  /** The Constant EDM_ASSOCIATION_DEPENDENT. */
  public static final String EDM_ASSOCIATION_DEPENDENT = "Dependent";

  /** The Constant EDM_ROLE. */
  public static final String EDM_ROLE = "Role";
  
  /** The Constant DOCUMENTATION. */
  public static final String DOCUMENTATION = "Documentation";
  
  /** The Constant SUMMARY. */
  public static final String SUMMARY = "Summary";
  
  /** The Constant LONG_DESCRIPTION. */
  public static final String LONG_DESCRIPTION = "LongDescription";

}
