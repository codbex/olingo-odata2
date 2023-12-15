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
package org.apache.olingo.odata2.jpa.processor.ref.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// TODO: Auto-generated Javadoc
/**
 * The Class Address.
 */
@Embeddable
public class Address {

  /**
   * Instantiates a new address.
   */
  public Address() {
    super();
  }

  /**
   * Instantiates a new address.
   *
   * @param houseNumber the house number
   * @param streetName the street name
   * @param city the city
   * @param country the country
   */
  public Address(final short houseNumber, final String streetName, final String city,
      final String country) {
    this();
    this.houseNumber = houseNumber;
    this.streetName = streetName;
    this.city = city;
    this.country = country;
  }

  /** The house number. */
  @Column(name = "HOUSE_NUMBER")
  private short houseNumber;

  /** The street name. */
  @Column(name = "STREET_NAME")
  private String streetName;

  /** The city. */
  @Column(name = "CITY")
  private String city;

  /** The country. */
  @Column(name = "COUNTRY")
  private String country;

  /** The pincode. */
  @Column(name = "PINCODE")
  private String pincode;

  /**
   * Gets the pincode.
   *
   * @return the pincode
   */
  public String getPincode() {
    return pincode;
  }

  /**
   * Sets the pincode.
   *
   * @param pincode the new pincode
   */
  public void setPincode(final String pincode) {
    this.pincode = pincode;
  }

  /**
   * Gets the house number.
   *
   * @return the house number
   */
  public short getHouseNumber() {
    return houseNumber;
  }

  /**
   * Sets the house number.
   *
   * @param houseNumber the new house number
   */
  public void setHouseNumber(final short houseNumber) {
    this.houseNumber = houseNumber;
  }

  /**
   * Gets the street name.
   *
   * @return the street name
   */
  public String getStreetName() {
    return streetName;
  }

  /**
   * Sets the street name.
   *
   * @param streetName the new street name
   */
  public void setStreetName(final String streetName) {
    this.streetName = streetName;
  }

  /**
   * Gets the city.
   *
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * Sets the city.
   *
   * @param city the new city
   */
  public void setCity(final String city) {
    this.city = city;
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
   * Sets the country.
   *
   * @param country the new country
   */
  public void setCountry(final String country) {
    this.country = country;
  }
}
