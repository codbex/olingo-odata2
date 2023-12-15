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
package org.apache.olingo.odata2.client.core.edm.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmParameter;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.client.api.edm.EdmDocumentation;

// TODO: Auto-generated Javadoc
/**
 *  Objects of this class represent EdmFunctionImport.
 */
public class EdmFunctionImportImpl extends EdmNamedImpl implements EdmFunctionImport, EdmAnnotatable {

  /** The edm entity container. */
  private EdmEntityContainer edmEntityContainer;
  
  /** The edm parameters. */
  private Map<String, EdmParameter> edmParameters;
  
  /** The parameters. */
  private Map<String, ArrayList<EdmFunctionImportParameter>> parameters;
  
  /** The parameters list. */
  private List<String> parametersList;
  
  /** The annotations. */
  private EdmAnnotations annotations;
  
  /** The edm return type. */
  private EdmTyped edmReturnType;
  
  /** The entity set. */
  private String entitySet;
  
  /** The http method. */
  private String httpMethod;
  
  /** The mapping. */
  private EdmMapping mapping;
  
  /** The documentation. */
  private EdmDocumentation documentation;

  /**
   * Gets the edm return type.
   *
   * @return the edm return type
   */
  public EdmTyped getEdmReturnType() {
    return edmReturnType;
  }

  /**
   * Sets the edm return type.
   *
   * @param edmReturnType the new edm return type
   */
  public void setEdmReturnType(EdmTyped edmReturnType) {
    this.edmReturnType = edmReturnType;
  }

  /**
   * Gets the documentation.
   *
   * @return the documentation
   */
  public EdmDocumentation getDocumentation() {
    return documentation;
  }

  /**
   * Sets the documentation.
   *
   * @param documentation the new documentation
   */
  public void setDocumentation(EdmDocumentation documentation) {
    this.documentation = documentation;
  }

  /**
   * Sets the mapping.
   *
   * @param mapping the new mapping
   */
  public void setMapping(EdmMapping mapping) {
    this.mapping = mapping;
  }

  /**
   * Sets the return type.
   *
   * @param returnType the returnType to set
   */
  public void setReturnType(EdmTyped returnType) {
    this.edmReturnType = returnType;
  }

  /**
   * Sets the entity set.
   *
   * @param edmEntitySet the entitySet to set
   */
  public void setEntitySet(String edmEntitySet) {
    this.entitySet = edmEntitySet;
  }

  /**
   * Sets the http method.
   *
   * @param httpMethod the httpMethod to set
   */
  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  /**
   * Gets the edm entity container.
   *
   * @return the edm entity container
   */
  public EdmEntityContainer getEdmEntityContainer() {
    return edmEntityContainer;
  }

  /**
   * Sets the edm entity container.
   *
   * @param edmEntityContainer the new edm entity container
   */
  public void setEdmEntityContainer(EdmEntityContainer edmEntityContainer) {
    this.edmEntityContainer = edmEntityContainer;
  }

  /**
   * Gets the entity set name.
   *
   * @return the entity set name
   */
  public String getEntitySetName() {
    return entitySet;
  } 
  
  /**
   * Gets the edm parameters.
   *
   * @return the edm parameters
   */
  public Map<String, EdmParameter> getEdmParameters() {
    return edmParameters;
  }

  /**
   * Sets the edm parameters.
   *
   * @param edmParameters the edm parameters
   */
  public void setEdmParameters(Map<String, EdmParameter> edmParameters) {
    this.edmParameters = edmParameters;
  }

  /**
   * Gets the parameters.
   *
   * @return the parameters
   */
  public Map<String, ArrayList<EdmFunctionImportParameter>> getParameters() {
    return parameters;
  }

  /**
   * Sets the parameters.
   *
   * @param parameters the parameters
   */
  public void setParameters(Map<String, ArrayList<EdmFunctionImportParameter>> parameters) {
    this.parameters = parameters;
  }

  /**
   * Gets the parameters list.
   *
   * @return the parameters list
   */
  public List<String> getParametersList() {
    return parametersList;
  }

  /**
   * Sets the parameters list.
   *
   * @param parametersList the new parameters list
   */
  public void setParametersList(List<String> parametersList) {
    this.parametersList = parametersList;
  }

  /**
   * Sets the annotations.
   *
   * @param annotations the new annotations
   */
  public void setAnnotations(EdmAnnotations annotations) {
    this.annotations = annotations;
  }

  /**
   * Gets the parameter.
   *
   * @param name the name
   * @return the parameter
   * @throws EdmException the edm exception
   */
  @Override
  public EdmParameter getParameter(final String name) throws EdmException {
    for (Entry<String, EdmParameter> param : edmParameters.entrySet()) {
      if (param.getKey().equalsIgnoreCase(name)) {
        return param.getValue();
      }
    }
    return null;
  }

  /**
   * Gets the parameter names.
   *
   * @return the parameter names
   * @throws EdmException the edm exception
   */
  @Override
  public List<String> getParameterNames() throws EdmException {
    return parametersList;
  }

  /**
   * Gets the entity set.
   *
   * @return the entity set
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntitySet getEntitySet() throws EdmException {
    if(edmEntityContainer != null){
      return edmEntityContainer.getEntitySet(entitySet);
    }else{
      return null;
    }
  }

  /**
   * Gets the http method.
   *
   * @return the http method
   * @throws EdmException the edm exception
   */
  @Override
  public String getHttpMethod() throws EdmException {
    return this.httpMethod;
  }

  /**
   * Gets the return type.
   *
   * @return the return type
   * @throws EdmException the edm exception
   */
  @Override
  public EdmTyped getReturnType() throws EdmException {
    return edmReturnType;
  }

  /**
   * Gets the entity container.
   *
   * @return the entity container
   * @throws EdmException the edm exception
   */
  @Override
  public EdmEntityContainer getEntityContainer() throws EdmException {
    return edmEntityContainer;
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws EdmException the edm exception
   */
  @Override
  public EdmAnnotations getAnnotations() throws EdmException {
    return annotations;
  }

  /**
   * Gets the mapping.
   *
   * @return the mapping
   * @throws EdmException the edm exception
   */
  @Override
  public EdmMapping getMapping() throws EdmException {
    return mapping;
  }  
  
  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return String.format(name);
  }

}
