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
package org.apache.olingo.odata2.api.processor;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.olingo.odata2.api.ODataService;
import org.apache.olingo.odata2.api.ODataServiceFactory;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.uri.PathInfo;

// TODO: Auto-generated Javadoc
/**
 * Compilation of generic context objects.
 * 
 * @org.apache.olingo.odata2.DoNotImplement
 */
public interface ODataContext {

  /** The http servlet request object. */
  String HTTP_SERVLET_REQUEST_OBJECT = "~httpRequestObject";

  /**
   * Gets the OData service.
   *
   * @return ODataService related for this context
   * @throws ODataException the o data exception
   */
  ODataService getService() throws ODataException;

  /**
   * Gets the service factory.
   *
   * @return the service factory instance
   */
  ODataServiceFactory getServiceFactory();

  /**
   * Gets information about the request path.
   *
   * @return an OData path info object
   * @throws ODataException the o data exception
   */
  PathInfo getPathInfo() throws ODataException;

  /**
   * If a request execution is part of batch processing then this method returns the context of the
   * outer batch request.
   * @return a batch parent context or null
   */
  ODataContext getBatchParentContext();

  /**
   * Checks if is in batch mode.
   *
   * @return true in case of this request is part of a batch processing queue
   */
  boolean isInBatchMode();

  /**
   * Starts runtime measurement.
   * @param className class name where the runtime measurement starts
   * @param methodName method name where the runtime measurement starts
   * @return handle for the started runtime measurement which can be used for stopping
   */
  int startRuntimeMeasurement(String className, String methodName);

  /**
   * Stops runtime measurement.
   * @param handle of runtime measurement to be stopped
   */
  void stopRuntimeMeasurement(int handle);

  /**
   * Gets the list of all runtime measurements.
   * @return list of all runtime measurements of type {@link RuntimeMeasurement}
   */
  List<RuntimeMeasurement> getRuntimeMeasurements();

  /**
   * Gets the HTTP method of the request.
   * @return HTTP method as {@link String}
   */
  String getHttpMethod();

  /**
   * Sets a parameter.
   * @param name of parameter (name is used as key, existing values are overwritten)
   * @param value of parameter as object
   */
  void setParameter(String name, Object value);

  /**
   * Removes parameter.
   * @param name of parameter to be removed
   */
  void removeParameter(String name);

  /**
   * Gets a named parameter value.
   * @param name of parameter
   * @return parameter value as {@link Object} for the given name
   */
  Object getParameter(String name);

  /**
   * Returns the first found header value of the HTTP request.
   * @param name name of the first found request header element (e.g. "Content-Type")
   * @return null or a request header value if found
   */
  String getRequestHeader(String name);

  /**
   * Returns all header values of the HTTP request but never null.
   * @return immutable map of request header values
   */
  Map<String, List<String>> getRequestHeaders();

  /**
   * Gets information about enabled debug mode.
   * @return debugMode as boolean
   */
  boolean isInDebugMode();

  /**
   * Enables debug mode.
   * @param debugMode as boolean
   */
  void setDebugMode(boolean debugMode);

  /**
   * Gets a list of languages that are acceptable for the response.
   * If no acceptable languages are specified, a read-only list containing
   * a single wildcard java.util.Locale instance (with language field set to "*") is returned.
   * @return a read-only list of acceptable languages sorted according to their q-value,
   * with highest preference first.
   */
  List<Locale> getAcceptableLanguages();

  /**
   * <p>Runtime measurements.</p>
   * <p>All times are in nanoseconds since some fixed but arbitrary time
   * (perhaps in the future, so values may be negative).</p>
   * @see System#nanoTime()
   */
  public interface RuntimeMeasurement {
    /**
     * Sets the class name.
     * @param className the name of the class that is measured
     */
    void setClassName(String className);

    /**
     * Gets the class name.
     * @return the name of the class that is measured
     */
    String getClassName();

    /**
     * Sets the method name.
     * @param methodName the name of the method that is measured
     */
    void setMethodName(String methodName);

    /**
     * Gets the method name.
     * @return the name of the method that is measured
     */
    String getMethodName();

    /**
     * Sets the start time.
     * @param timeStarted the start time in nanoseconds
     * @see System#nanoTime()
     */
    void setTimeStarted(long timeStarted);

    /**
     * Gets the start time.
     * @return the start time in nanoseconds or 0 if not set yet
     * @see System#nanoTime()
     */
    long getTimeStarted();

    /**
     * Sets the stop time.
     * @param timeStopped the stop time in nanoseconds
     * @see System#nanoTime()
     */
    void setTimeStopped(long timeStopped);

    /**
     * Gets the stop time.
     * @return the stop time in nanoseconds or 0 if not set yet
     * @see System#nanoTime()
     */
    long getTimeStopped();

    /**
     * Sets the start heap memory used.
     * @param usedHeap the start heap memory used in bytes
     * @see java.lang.ManagementFactory#getMemoryMXBean()
     */
    void setMemoryStarted(long usedHeap);

    /**
     * Sets the stop heap memory used.
     * @param usedHeap the stop heap memory used in bytes
     * @see java.lang.ManagementFactory#getMemoryMXBean()
     */
    void setMemoryStopped(long usedHeap);

    /**
     * Gets the start heap memory used.
     * @return start heap memory used or 0 if not set
     * @see java.lang.ManagementFactory#getMemoryMXBean()
     */
    long getMemoryStarted();

    /**
     * Gets the stop heap memory used.
     * @return stop heap memory used or 0 if not set
     * @see java.lang.ManagementFactory#getMemoryMXBean()
     */
    long getMemoryStopped();
  }
}
