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

import java.text.DateFormat;
import java.util.Calendar;

import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceContent;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceMimeType;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmType;

// TODO: Auto-generated Javadoc
/**
 * The Class Employee.
 */
@EdmEntityType(name = "Employee", namespace = ModelSharedConstants.NAMESPACE_1)
@EdmEntitySet(name = "Employees")
public class Employee {
  
  /** The employee id. */
  @EdmKey
  @EdmProperty(name = "EmployeeId", type = EdmType.STRING, facets = @EdmFacets(nullable = false))
  private String employeeId;
  
  /** The employee name. */
  @EdmProperty(name = "EmployeeName", facets = @EdmFacets(maxLength = 20))
  private String employeeName;
  
  /** The age. */
  @EdmProperty
  private Integer age;
  
  /** The manager. */
  @EdmNavigationProperty(name = "ne_Manager", association = "ManagerEmployees")
  private Manager manager;
  
  /** The team. */
  @EdmNavigationProperty(name = "ne_Team", association = "TeamEmployees")
  private Team team;
  
  /** The room. */
  @EdmNavigationProperty(name = "ne_Room")
  private Room room;
  
  /** The image type. */
  @EdmMediaResourceMimeType
  private String imageType;
  
  /** The image. */
  @EdmMediaResourceContent
  private byte[] image;
  
  /** The image url. */
  @EdmProperty(name = "ImageUrl")
  private String imageUrl;
  
  /** The entry date. */
  @EdmProperty(name = "EntryDate", type = EdmType.DATE_TIME,
      facets = @EdmFacets(nullable = true))
  private Calendar entryDate;
  
  /** The location. */
  @EdmProperty(name = "Location", facets = @EdmFacets(nullable = false))
  private Location location;

  /**
   * Gets the id.
   *
   * @return the id
   */
  public String getId() {
    return employeeId;
  }

  /**
   * Sets the employee name.
   *
   * @param employeeName the new employee name
   */
  public void setEmployeeName(final String employeeName) {
    this.employeeName = employeeName;
  }

  /**
   * Gets the employee name.
   *
   * @return the employee name
   */
  public String getEmployeeName() {
    return employeeName;
  }

  /**
   * Sets the age.
   *
   * @param age the new age
   */
  public void setAge(final int age) {
    this.age = age;
  }

  /**
   * Gets the age.
   *
   * @return the age
   */
  public int getAge() {
    return age;
  }

  /**
   * Sets the manager.
   *
   * @param manager the new manager
   */
  public void setManager(final Manager manager) {
    this.manager = manager;
  }

  /**
   * Gets the manager.
   *
   * @return the manager
   */
  public Manager getManager() {
    return manager;
  }

  /**
   * Sets the team.
   *
   * @param team the new team
   */
  public void setTeam(final Team team) {
    this.team = team;
  }

  /**
   * Gets the team.
   *
   * @return the team
   */
  public Team getTeam() {
    return team;
  }

  /**
   * Sets the room.
   *
   * @param room the new room
   */
  public void setRoom(final Room room) {
    this.room = room;
  }

  /**
   * Gets the room.
   *
   * @return the room
   */
  public Room getRoom() {
    return room;
  }

  /**
   * Sets the image uri.
   *
   * @param imageUri the new image uri
   */
  public void setImageUri(final String imageUri) {
    imageUrl = imageUri;
  }

  /**
   * Gets the image uri.
   *
   * @return the image uri
   */
  public String getImageUri() {
    return imageUrl;
  }

  /**
   * Sets the location.
   *
   * @param location the new location
   */
  public void setLocation(final Location location) {
    this.location = location;
  }

  /**
   * Gets the location.
   *
   * @return the location
   */
  public Location getLocation() {
    return location;
  }

  /**
   * Sets the entry date.
   *
   * @param date the new entry date
   */
  public void setEntryDate(final Calendar date) {
    entryDate = date;
  }

  /**
   * Gets the entry date.
   *
   * @return the entry date
   */
  public Calendar getEntryDate() {
    return entryDate;
  }

  /**
   * Sets the image type.
   *
   * @param imageType the new image type
   */
  public void setImageType(final String imageType) {
    this.imageType = imageType;
  }

  /**
   * Gets the image type.
   *
   * @return the image type
   */
  public String getImageType() {
    return imageType;
  }

  /**
   * Sets the image.
   *
   * @param image the new image
   */
  public void setImage(final byte[] image) {
    this.image = image;
  }

  /**
   * Sets the image.
   *
   * @param imageUrl the new image
   */
  public void setImage(final String imageUrl) {
    image = loadImage(imageUrl);
  }

  /**
   * Load image.
   *
   * @param imageUrl the image url
   * @return the byte[]
   */
  private static byte[] loadImage(final String imageUrl) {
    return ResourceHelper.loadAsByte(imageUrl);
  }

  /**
   * Gets the image.
   *
   * @return the image
   */
  public byte[] getImage() {
    if (image == null) {
      return null;
    }
    return image.clone();
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    if (employeeId == null) {
      return 0;
    }
    return employeeId.hashCode();
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
        || obj != null && getClass() == obj.getClass() && employeeId == ((Employee) obj).employeeId;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "{\"EmployeeId\":\"" + employeeId + "\","
        + "\"EmployeeName\":\"" + employeeName + "\","
        + "\"ManagerId\":" + (manager == null ? "null" : "\"" + manager.getId() + "\"") + ","
        + "\"RoomId\":" + (room == null ? "null" : "\"" + room.getId() + "\"") + ","
        + "\"TeamId\":" + (team == null ? "null" : "\"" + team.getId() + "\"") + ","
        + "\"Location\":"
        + (location == null ? "null" :
            "{\"City\":" + (location.getCity() == null ? "null" :
                "{\"PostalCode\":\"" + location.getCity().getPostalCode() + "\","
                    + "\"CityName\":\"" + location.getCity().getCityName() + "\"}") + ","
                + "\"Country\":\"" + location.getCountry() + "\"}") + ","
        + "\"Age\":" + age + ","
        + "\"EntryDate\":"
        + (entryDate == null ? "null" : "\"" + DateFormat.getInstance().format(entryDate.getTime()) + "\"") + ","
        + "\"ImageUrl\":\"" + imageUrl + "\"}";
  }
}
