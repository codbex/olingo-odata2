/*
 * Copyright 2013 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.olingo.odata2.annotation.processor.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.annotation.edm.EdmConcurrencyControl;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class Room.
 */
@EdmEntityType(name = "Room", namespace = ModelSharedConstants.NAMESPACE_1)
@EdmEntitySet(name = "Rooms")
public class Room extends RefBase {

  /** The seats. */
  @EdmProperty
  private Integer seats;
  
  /** The version. */
  @EdmProperty(facets = @EdmFacets(scale = 0, precision = 0))
  @EdmConcurrencyControl
  private Integer version;
  
  /** The building. */
  @EdmNavigationProperty(name = "nr_Building", association = "BuildingRooms")
  private Building building;
  
  /** The employees. */
  @EdmNavigationProperty(name = "nr_Employees")
  private List<Employee> employees = new ArrayList<Employee>();

  /**
   * Instantiates a new room.
   */
  public Room() {
    this(0, null);
  }

  /**
   * Instantiates a new room.
   *
   * @param id the id
   * @param name the name
   */
  public Room(final int id, final String name) {
    super(id, name);
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
