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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmLiteralKind;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmParameter;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeException;
import org.apache.olingo.odata2.api.uri.info.GetFunctionImportUriInfo;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAFunction;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAMethodContext;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAFunctionContext.
 */
public class JPAFunctionContext extends JPAMethodContext {

  /**
   * The Class JPAFunctionContextBuilder.
   */
  public class JPAFunctionContextBuilder extends JPAMethodContextBuilder {

    /** The function view. */
    protected GetFunctionImportUriInfo functionView;
    
    /** The function import. */
    private EdmFunctionImport functionImport;
    
    /** The mapping. */
    private EdmMapping mapping;

    /**
     * Builds the.
     *
     * @return the JPA method context
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    @Override
    public JPAMethodContext build() throws ODataJPAModelException, ODataJPARuntimeException {
      if (functionView != null) {

        functionImport = functionView.getFunctionImport();
        try {
          mapping = functionImport.getMapping();

          List<JPAFunction> jpaFunctionList = new ArrayList<JPAFunction>();
          jpaFunctionList.add(generateJPAFunction());
          setJpaFunction(jpaFunctionList);
          setEnclosingObject(generateEnclosingObject());
        } catch (EdmException e) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
        } catch (InstantiationException e) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
        } catch (IllegalAccessException e) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
        } catch (IllegalArgumentException e) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
        } catch (InvocationTargetException e) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
        } catch (NoSuchMethodException e) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
        } catch (SecurityException e) {
          throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.GENERAL.addContent(e.getMessage()), e);
        }
      }

      return JPAFunctionContext.this;
    }

    /**
     * Sets the results view.
     *
     * @param resultsView the new results view
     */
    @Override
    protected void setResultsView(final Object resultsView) {
      if (resultsView instanceof GetFunctionImportUriInfo) {
        functionView = (GetFunctionImportUriInfo) resultsView;
      }
    
    }

    /**
     * Generate JPA function.
     *
     * @return the JPA function
     * @throws EdmException the edm exception
     * @throws NoSuchMethodException the no such method exception
     * @throws SecurityException the security exception
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     */
    private JPAFunction generateJPAFunction() throws EdmException, NoSuchMethodException, SecurityException,
        ODataJPAModelException, ODataJPARuntimeException {

      Class<?>[] parameterTypes = getParameterTypes();
      Method method = getMethod(parameterTypes);
      Type returnType = getReturnType();
      Object[] args = getArguments();

      return new JPAFunction(method, parameterTypes, returnType, args);
    }

    /**
     * Gets the arguments.
     *
     * @return the arguments
     * @throws EdmException the edm exception
     */
    private Object[] getArguments() throws EdmException {
      Map<String, EdmLiteral> edmArguments = functionView.getFunctionImportParameters();

      if (edmArguments == null) {
        return null;
      } else {
        Collection<String> paramNames = functionImport.getParameterNames();
        Object[] args = new Object[paramNames.size()];
        int i = 0;
        for (String paramName : functionImport.getParameterNames()) {
          EdmLiteral literal = edmArguments.get(paramName);
          EdmParameter parameter = functionImport.getParameter(paramName);
          JPAEdmMapping mappingValue = (JPAEdmMapping) parameter.getMapping();
          args[i++] = convertArgument(literal, parameter.getFacets(), mappingValue.getJPAType());
        }
        return args;
      }

    }

    /**
     * Convert argument.
     *
     * @param edmLiteral the edm literal
     * @param facets the facets
     * @param targetType the target type
     * @return the object
     * @throws EdmSimpleTypeException the edm simple type exception
     */
    private Object convertArgument(final EdmLiteral edmLiteral, final EdmFacets facets, final Class<?> targetType)
        throws EdmSimpleTypeException {
      Object value = null;
      if (edmLiteral != null) {
        EdmSimpleType edmType = edmLiteral.getType();
        value = edmType.valueOfString(edmLiteral.getLiteral(), EdmLiteralKind.DEFAULT, facets, targetType);
      }
      return value;
    }

    /**
     * Gets the parameter types.
     *
     * @return the parameter types
     * @throws EdmException the edm exception
     */
    private Class<?>[] getParameterTypes() throws EdmException {

      Class<?>[] parameterTypes = new Class<?>[functionImport.getParameterNames().size()];
      int i = 0;
      for (String parameterName : functionImport.getParameterNames()) {
        EdmParameter parameter = functionImport.getParameter(parameterName);
        parameterTypes[i++] = ((JPAEdmMapping) parameter.getMapping()).getJPAType();
      }

      return parameterTypes;
    }

    /**
     * Gets the method.
     *
     * @param parameterTypes the parameter types
     * @return the method
     * @throws NoSuchMethodException the no such method exception
     * @throws SecurityException the security exception
     */
    private Method getMethod(final Class<?>[] parameterTypes) throws NoSuchMethodException, SecurityException {

      Class<?> type = ((JPAEdmMapping) mapping).getJPAType();
      Method method;
      method = type.getMethod(mapping.getInternalName(), parameterTypes);

      return method;
    }

    /**
     * Gets the return type.
     *
     * @return the return type
     * @throws ODataJPAModelException the o data JPA model exception
     * @throws ODataJPARuntimeException the o data JPA runtime exception
     * @throws EdmException the edm exception
     */
    private Type getReturnType() throws ODataJPAModelException, ODataJPARuntimeException, EdmException {
      return null;
    }

    /**
     * Generate enclosing object.
     *
     * @return the object
     * @throws InstantiationException the instantiation exception
     * @throws IllegalAccessException the illegal access exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws InvocationTargetException the invocation target exception
     * @throws NoSuchMethodException the no such method exception
     * @throws SecurityException the security exception
     */
    private Object generateEnclosingObject() throws InstantiationException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

      Class<?> type = ((JPAEdmMapping) mapping).getJPAType();
      Object[] params = null;

      return type.getConstructor((Class<?>[]) params).newInstance(params);
    }
  }
}
