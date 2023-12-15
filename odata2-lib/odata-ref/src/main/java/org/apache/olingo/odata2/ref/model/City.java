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
package org.apache.olingo.odata2.ref.model;

// TODO: Auto-generated Javadoc
/**
 * The Class City.
 */
public class City {

  /** The postal code. */
  private String postalCode;
  
  /** The city name. */
  private String cityName;

  /**
   * Instantiates a new city.
   *
   * @param postalCode the postal code
   * @param name the name
   */
  public City(final String postalCode, final String name) {
    this.postalCode = postalCode;
    cityName = name;
  }

  /**
   * Sets the postal code.
   *
   * @param postalCode the new postal code
   */
  public void setPostalCode(final String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * Gets the postal code.
   *
   * @return the postal code
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * Sets the city name.
   *
   * @param cityName the new city name
   */
  public void setCityName(final String cityName) {
    this.cityName = cityName;
  }

  /**
   * Gets the city name.
   *
   * @return the city name
   */
  public String getCityName() {
    return cityName;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return String.format("%s, %s", cityName, postalCode);
  }

}
