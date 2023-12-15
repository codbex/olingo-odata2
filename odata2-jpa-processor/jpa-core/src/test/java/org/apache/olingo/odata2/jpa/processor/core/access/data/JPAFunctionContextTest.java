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
package org.apache.olingo.odata2.jpa.processor.core.access.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmParameter;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAFunction;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAMethodContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLContextType;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.model.JPAEdmMappingImpl;
import org.easymock.EasyMock;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAFunctionContextTest.
 */
public class JPAFunctionContextTest {

  /** The Constant FUNCTION_NAME. */
  private static final String FUNCTION_NAME = "testMethod";
  
  /** The Constant PARAMVALUE. */
  private static final String PARAMVALUE = "VALUE";
  
  /** The Constant PARAMNAME. */
  private static final String PARAMNAME = "PARAM1";
  
  /** The variant. */
  private int VARIANT = 0;

  /**
   * Builds the.
   *
   * @return the JPA function context
   */
  public JPAFunctionContext build() {
    JPAFunctionContext functionContext = null;
    try {
      if (VARIANT == 0) {
        functionContext =
            (JPAFunctionContext) JPAMethodContext.createBuilder(JPQLContextType.FUNCTION, getView()).build();
      }

    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    return functionContext;
  }

  /**
   * Test get enclosing object.
   */
  @Test
  public void testGetEnclosingObject() {

    VARIANT = 0;
    JPAFunctionContext context = build();
    assertNotNull(context);
    assertEquals(FunctionImportTestClass.class,context.getEnclosingObject().getClass());
    JPAFunction function = context.getJPAFunctionList().get(0);
    assertEquals(FUNCTION_NAME,function.getFunction().getName());
    assertEquals(String.class,function.getFunction().getReturnType());
    assertEquals(String.class,function.getFunction().getParameterTypes()[0]);
  }

  /**
   * Gets the view.
   *
   * @return the view
   */
  private GetFunctionImportUriInfo getView() {
    GetFunctionImportUriInfo functiontView = EasyMock.createMock(GetFunctionImportUriInfo.class);
    EasyMock.expect(functiontView.getFunctionImport()).andStubReturn(getEdmFunctionImport());
    EasyMock.expect(functiontView.getFunctionImportParameters()).andStubReturn(getFunctionImportParameters());

    EasyMock.replay(functiontView);
    return functiontView;
  }

  /**
   * Gets the function import parameters.
   *
   * @return the function import parameters
   */
  private Map<String, EdmLiteral> getFunctionImportParameters() {
    Map<String, EdmLiteral> paramMap = new HashMap<String, EdmLiteral>();
    paramMap.put(PARAMNAME, new EdmLiteral(EdmSimpleTypeKind.String.getEdmSimpleTypeInstance(), PARAMVALUE));
    return paramMap;
  }

  /**
   * Gets the edm function import.
   *
   * @return the edm function import
   */
  private EdmFunctionImport getEdmFunctionImport() {
    EdmFunctionImport edmFunctionImport = EasyMock.createMock(EdmFunctionImport.class);
    try {
      EasyMock.expect(edmFunctionImport.getMapping()).andStubReturn(getMapping());
      EasyMock.expect(edmFunctionImport.getParameterNames()).andStubReturn(getParameterNames());
      EasyMock.expect(edmFunctionImport.getParameter(PARAMNAME)).andStubReturn(getParameter(PARAMNAME));
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    EasyMock.replay(edmFunctionImport);
    return edmFunctionImport;
  }

  /**
   * Gets the parameter.
   *
   * @param string the string
   * @return the parameter
   */
  private EdmParameter getParameter(final String string) {
    EdmParameter edmParameter = EasyMock.createMock(EdmParameter.class);
    try {
      EasyMock.expect(edmParameter.getMapping()).andStubReturn(getEdmMapping());
      EasyMock.expect(edmParameter.getFacets()).andStubReturn(getFacets());
    } catch (EdmException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    EasyMock.replay(edmParameter);
    return edmParameter;
  }

  /**
   * Gets the facets.
   *
   * @return the facets
   */
  private EdmFacets getFacets(){
    EdmFacets facets = EasyMock.createMock(EdmFacets.class);
    EasyMock.expect(facets.isUnicode()).andReturn(true).anyTimes();
    EasyMock.expect(facets.getMaxLength()).andReturn(10).anyTimes();
    EasyMock.replay(facets);
    return facets;
  }
  
  /**
   * Gets the edm mapping.
   *
   * @return the edm mapping
   */
  private EdmMapping getEdmMapping() {
    JPAEdmMappingImpl mapping = new JPAEdmMappingImpl();
    mapping.setJPAType(String.class);
    ((Mapping) mapping).setInternalName(PARAMNAME);
    return mapping;
  }

  /**
   * Gets the mapping.
   *
   * @return the mapping
   */
  private JPAEdmMappingImpl getMapping() {
    JPAEdmMappingImpl mapping = new JPAEdmMappingImpl();
    mapping.setJPAType(FunctionImportTestClass.class);
    ((Mapping) mapping).setInternalName(FUNCTION_NAME);
    return mapping;
  }

  /**
   * Gets the parameter names.
   *
   * @return the parameter names
   */
  private Collection<String> getParameterNames() {
    Collection<String> parametersList = new ArrayList<String>();
    parametersList.add(PARAMNAME);
    return parametersList;
  }

  /**
   * The Class FunctionImportTestClass.
   */
  public static class FunctionImportTestClass {

    /**
     * Instantiates a new function import test class.
     */
    public FunctionImportTestClass() {

    }

    /**
     * Test method.
     *
     * @param message the message
     * @return the string
     */
    public String testMethod(final String message) {
      return message;
    }
  }
}
