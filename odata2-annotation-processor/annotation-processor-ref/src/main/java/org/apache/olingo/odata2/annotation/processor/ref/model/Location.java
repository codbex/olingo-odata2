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
package org.apache.olingo.odata2.annotation.processor.ref.model;

import org.apache.olingo.odata2.api.annotation.edm.EdmComplexType;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class Location.
 */
@EdmComplexType(name = "c_Location", namespace = ModelSharedConstants.NAMESPACE_1)
public class Location {
  
  /** The country. */
  @EdmProperty
  private String country;
  
  /** The city. */
  @EdmProperty
  private City city;

  /**
   * Instantiates a new location.
   */
  public Location() {}

  /**
   * Instantiates a new location.
   *
   * @param country the country
   * @param postalCode the postal code
   * @param cityName the city name
   */
  public Location(final String country, final String postalCode, final String cityName) {
    this.country = country;
    city = new City(postalCode, cityName);
  }

  /**
   * Sets the country.
   *
   * @param country the new country
   */
  public void setCountry(final String country) {
    this.country = country;
  }

  /**
   * Gets the country.
   *
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * Sets the city.
   *
   * @param city the new city
   */
  public void setCity(final City city) {
    this.city = city;
  }

  /**
   * Gets the city.
   *
   * @return the city
   */
  public City getCity() {
    return city;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return String.format("%s, %s", country, city.toString());
  }

}
