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

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class Room.
 */
public class Room {
  
  /** The id. */
  private final int id;
  
  /** The name. */
  private String name;
  
  /** The seats. */
  private Integer seats;
  
  /** The version. */
  private Integer version;
  
  /** The building. */
  private Building building;
  
  /** The employees. */
  private List<Employee> employees = new ArrayList<Employee>();

  /**
   * Instantiates a new room.
   *
   * @param id the id
   * @param name the name
   */
  public Room(final int id, final String name) {
    this.id = id;
    setName(name);
  }

  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return Integer.toString(id);
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Sets the seats.
   *
   * @param seats the new seats
   */
  public void setSeats(final int seats) {
    this.seats = seats;
  }

  /**
   * Gets the seats.
   *
   * @return the seats
   */
  public int getSeats() {
    return seats;
  }

  /**
   * Sets the version.
   *
   * @param version the new version
   */
  public void setVersion(final int version) {
    this.version = version;
  }

  /**
   * Gets the version.
   *
   * @return the version
   */
  public int getVersion() {
    return version;
  }

  /**
   * Sets the building.
   *
   * @param building the new building
   */
  public void setBuilding(final Building building) {
    this.building = building;
  }

  /**
   * Gets the building.
   *
   * @return the building
   */
  public Building getBuilding() {
    return building;
  }

  /**
   * Gets the employees.
   *
   * @return the employees
   */
  public List<Employee> getEmployees() {
    return employees;
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return id;
  }

  /**
   * Equals.
   *
   * @param obj the obj
   * @return true, if successful
   */
  @Override
  public boolean equals(final Object obj) {
    return this == obj
        || obj != null && getClass() == obj.getClass() && id == ((Room) obj).id;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "{\"Id\":\"" + id + "\",\"Name\":\"" + name + "\",\"Seats\":" + seats + ",\"Version\":" + version + "}";
  }
}
