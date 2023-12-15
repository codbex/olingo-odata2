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
package org.apache.olingo.odata2.core;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.odata2.api.ODataDebugCallback;
import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.api.uri.PathInfo;

// TODO: Auto-generated Javadoc
/**
 * Context.
 */
public class ODataContextImpl implements ODataContext {

  /** The Constant ODATA_BATCH_PARENT_CONTEXT. */
  private static final String ODATA_BATCH_PARENT_CONTEXT = "~odataBatchParentContext";
  
  /** The Constant ODATA_REQUEST. */
  private static final String ODATA_REQUEST = "~odataRequest";
  
  /** The Constant DEBUG_MODE. */
  private static final String DEBUG_MODE = "~debugMode";
  
  /** The Constant SERVICE. */
  private static final String SERVICE = "~service";
  
  /** The Constant SERVICE_FACTORY. */
  private static final String SERVICE_FACTORY = "~serviceFactory";
  
  /** The Constant PATH_INFO. */
  private static final String PATH_INFO = "~pathInfo";
  
  /** The Constant RUNTIME_MEASUREMENTS. */
  private static final String RUNTIME_MEASUREMENTS = "~runtimeMeasurements";
  
  /** The Constant HTTP_METHOD. */
  private static final String HTTP_METHOD = "~httpMethod";

  /** The parameter table. */
  private Map<String, Object> parameterTable = new HashMap<String, Object>();

  /** The acceptable languages. */
  private List<Locale> acceptableLanguages;

  /**
   * Instantiates a new o data context impl.
   *
   * @param request the request
   * @param factory the factory
   */
  public ODataContextImpl(final ODataRequest request, final ODataServiceFactory factory) {
    setServiceFactory(factory);
    setRequest(request);
    setPathInfo(request.getPathInfo());
    setHttpMethod(request.getHttpMethod());
    setAcceptableLanguages(request.getAcceptableLanguages());
    setDebugMode(checkDebugMode(request.getQueryParameters()));
  }

  /**
   * Sets the parameter.
   *
   * @param name the name
   * @param value the value
   */
  @Override
  public void setParameter(final String name, final Object value) {
    parameterTable.put(name, value);
  }

  /**
   * Removes the parameter.
   *
   * @param name the name
   */
  @Override
  public void removeParameter(final String name) {
    parameterTable.remove(name);
  }

  /**
   * Gets the parameter.
   *
   * @param name the name
   * @return the parameter
   */
  @Override
  public Object getParameter(final String name) {
    return parameterTable.get(name);
  }

  /**
   * Checks if is in debug mode.
   *
   * @return true, if is in debug mode
   */
  @Override
  public boolean isInDebugMode() {
    return getParameter(DEBUG_MODE) != null && (Boolean) getParameter(DEBUG_MODE);
  }

  /**
   * Sets the debug mode.
   *
   * @param debugMode the new debug mode
   */
  @Override
  public void setDebugMode(final boolean debugMode) {
    setParameter(DEBUG_MODE, debugMode);
  }

  /**
   * Sets the service.
   *
   * @param service the new service
   */
  public void setService(final ODataService service) {
    setParameter(SERVICE, service);
  }

  /**
   * Gets the service.
   *
   * @return the service
   * @throws ODataException the o data exception
   */
  @Override
  public ODataService getService() throws ODataException {
    return (ODataService) getParameter(SERVICE);
  }

  /**
   * Sets the path info.
   *
   * @param uriInfo the new path info
   */
  public void setPathInfo(final PathInfo uriInfo) {
    setParameter(PATH_INFO, uriInfo);
  }

  /**
   * Gets the path info.
   *
   * @return the path info
   * @throws ODataException the o data exception
   */
  @Override
  public PathInfo getPathInfo() throws ODataException {
    return (PathInfo) getParameter(PATH_INFO);
  }

  /**
   * Sets the service factory.
   *
   * @param serviceFactory the new service factory
   */
  public void setServiceFactory(final ODataServiceFactory serviceFactory) {
    setParameter(SERVICE_FACTORY, serviceFactory);
  }

  /**
   * Gets the service factory.
   *
   * @return the service factory
   */
  @Override
  public ODataServiceFactory getServiceFactory() {
    return (ODataServiceFactory) getParameter(SERVICE_FACTORY);
  }

  /**
   * Start runtime measurement.
   *
   * @param className the class name
   * @param methodName the method name
   * @return the int
   */
  @Override
  public int startRuntimeMeasurement(final String className, final String methodName) {
    if (isInDebugMode()) {
      List<RuntimeMeasurement> runtimeMeasurements = getRuntimeMeasurements();
      int handleId = runtimeMeasurements.size();

      final RuntimeMeasurement measurement = new RuntimeMeasurementImpl();
      measurement.setTimeStarted(System.nanoTime());
      measurement.setClassName(className);
      measurement.setMethodName(methodName);
      measurement.setMemoryStarted(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed());

      runtimeMeasurements.add(measurement);

      return handleId;
    } else {
      return 0;
    }
  }

  /**
   * Stop runtime measurement.
   *
   * @param handle the handle
   */
  @Override
  public void stopRuntimeMeasurement(final int handle) {
    if (isInDebugMode()) {
      long stopTime = System.nanoTime();
      long stopMemory = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();

      RuntimeMeasurement runtimeMeasurement = getRuntimeMeasurement(handle);
      if (runtimeMeasurement != null) {
        runtimeMeasurement.setTimeStopped(stopTime);
        runtimeMeasurement.setMemoryStopped(stopMemory);
      }
    }
  }

  /**
   * Gets the runtime measurement.
   *
   * @param handle the handle
   * @return the runtime measurement
   */
  private RuntimeMeasurement getRuntimeMeasurement(final int handle) {
    List<RuntimeMeasurement> runtimeMeasurements = getRuntimeMeasurements();
    if (handle >= 0 && handle < runtimeMeasurements.size()) {
      return runtimeMeasurements.get(handle);
    }
    return null;
  }

  /**
   * Gets the runtime measurements.
   *
   * @return the runtime measurements
   */
  @Override
  public List<RuntimeMeasurement> getRuntimeMeasurements() {
    @SuppressWarnings("unchecked")
    List<RuntimeMeasurement> runtimeMeasurements = (List<RuntimeMeasurement>) getParameter(RUNTIME_MEASUREMENTS);
    if (runtimeMeasurements == null) {
      runtimeMeasurements = new ArrayList<RuntimeMeasurement>();
      setParameter(RUNTIME_MEASUREMENTS, runtimeMeasurements);
    }
    return runtimeMeasurements;
  }

  /**
   * The Class RuntimeMeasurementImpl.
   */
  protected class RuntimeMeasurementImpl implements RuntimeMeasurement {
    
    /** The class name. */
    private String className;
    
    /** The method name. */
    private String methodName;
    
    /** The time started. */
    private long timeStarted;
    
    /** The time stopped. */
    private long timeStopped;
    
    /** The memory started. */
    private long memoryStarted;
    
    /** The memory stopped. */
    private long memoryStopped;

    /**
     * Sets the class name.
     *
     * @param className the new class name
     */
    @Override
    public void setClassName(final String className) {
      this.className = className;
    }

    /**
     * Gets the class name.
     *
     * @return the class name
     */
    @Override
    public String getClassName() {
      return className;
    }

    /**
     * Sets the method name.
     *
     * @param methodName the new method name
     */
    @Override
    public void setMethodName(final String methodName) {
      this.methodName = methodName;
    }

    /**
     * Gets the method name.
     *
     * @return the method name
     */
    @Override
    public String getMethodName() {
      return methodName;
    }

    /**
     * Sets the time started.
     *
     * @param start the new time started
     */
    @Override
    public void setTimeStarted(final long start) {
      timeStarted = start;
    }

    /**
     * Gets the time started.
     *
     * @return the time started
     */
    @Override
    public long getTimeStarted() {
      return timeStarted;
    }

    /**
     * Sets the time stopped.
     *
     * @param stop the new time stopped
     */
    @Override
    public void setTimeStopped(final long stop) {
      timeStopped = stop;
    }

    /**
     * Gets the time stopped.
     *
     * @return the time stopped
     */
    @Override
    public long getTimeStopped() {
      return timeStopped;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
      return className + "." + methodName + ": duration: " + (timeStopped - timeStarted)
          + ", memory: " + (memoryStopped - memoryStarted);
    }

    /**
     * Sets the memory started.
     *
     * @param used the new memory started
     */
    @Override
    public void setMemoryStarted(final long used) {
      memoryStarted = used;
    }

    /**
     * Sets the memory stopped.
     *
     * @param used the new memory stopped
     */
    @Override
    public void setMemoryStopped(final long used) {
      memoryStopped = used;
    }

    /**
     * Gets the memory started.
     *
     * @return the memory started
     */
    @Override
    public long getMemoryStarted() {
      return memoryStarted;
    }

    /**
     * Gets the memory stopped.
     *
     * @return the memory stopped
     */
    @Override
    public long getMemoryStopped() {
      return memoryStopped;
    }
  }

  /**
   * Gets the request header.
   *
   * @param name the name
   * @return the request header
   */
  @Override
  public String getRequestHeader(final String name) {
    ODataRequest request = (ODataRequest) parameterTable.get(ODATA_REQUEST);
    return request.getRequestHeaderValue(name);
  }

  /**
   * Gets the request headers.
   *
   * @return the request headers
   */
  @Override
  public Map<String, List<String>> getRequestHeaders() {
    ODataRequest request = (ODataRequest) parameterTable.get(ODATA_REQUEST);
    return request.getRequestHeaders();
  }

  /**
   * Gets the acceptable languages.
   *
   * @return the acceptable languages
   */
  @Override
  public List<Locale> getAcceptableLanguages() {
    return Collections.unmodifiableList(acceptableLanguages);
  }

  /**
   * Sets the acceptable languages.
   *
   * @param acceptableLanguages the new acceptable languages
   */
  public void setAcceptableLanguages(final List<Locale> acceptableLanguages) {
    this.acceptableLanguages = acceptableLanguages;

    if (this.acceptableLanguages.isEmpty()) {
      final Locale wildcard = new Locale("*");
      this.acceptableLanguages.add(wildcard);
    }
  }

  /**
   * Sets the http method.
   *
   * @param httpMethod the new http method
   */
  public void setHttpMethod(final String httpMethod) {
    setParameter(HTTP_METHOD, httpMethod);
  }

  /**
   * Gets the http method.
   *
   * @return the http method
   */
  @Override
  public String getHttpMethod() {
    return (String) getParameter(HTTP_METHOD);
  }

  /**
   * Sets the request.
   *
   * @param request the new request
   */
  public void setRequest(final ODataRequest request) {
    setParameter(ODATA_REQUEST, request);
  }

  /**
   * Check debug mode.
   *
   * @param queryParameters the query parameters
   * @return true, if successful
   */
  private boolean checkDebugMode(final Map<String, String> queryParameters) {
    final ODataDebugCallback callback = getServiceFactory().getCallback(ODataDebugCallback.class);
    if(callback != null){
      return callback.isDebugEnabled();
    }
    return false;
  }

  /**
   * Sets the batch parent context.
   *
   * @param ctx the new batch parent context
   */
  public void setBatchParentContext(final ODataContext ctx) {
    setParameter(ODATA_BATCH_PARENT_CONTEXT, ctx);
  }

  /**
   * Gets the batch parent context.
   *
   * @return the batch parent context
   */
  @Override
  public ODataContext getBatchParentContext() {
    return (ODataContext) parameterTable.get(ODATA_BATCH_PARENT_CONTEXT);
  }

  /**
   * Checks if is in batch mode.
   *
   * @return true, if is in batch mode
   */
  @Override
  public boolean isInBatchMode() {
    return parameterTable.containsKey(ODATA_BATCH_PARENT_CONTEXT);
  }

}
