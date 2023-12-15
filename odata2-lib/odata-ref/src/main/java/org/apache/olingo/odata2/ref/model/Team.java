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
 * The Class Team.
 */
public class Team {
  
  /** The id. */
  private final int id;
  
  /** The name. */
  private String name;
  
  /** The is scrum team. */
  private Boolean isScrumTeam;
  
  /** The employees. */
  private List<Employee> employees = new ArrayList<Employee>();

  /**
   * Instantiates a new team.
   *
   * @param id the id
   * @param name the name
   */
  public Team(final int id, final String name) {
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
   * Checks if is scrum team.
   *
   * @return true, if is scrum team
   */
  public boolean isScrumTeam() {
    return isScrumTeam;
  }

  /**
   * Sets the scrum team.
   *
   * @param isScrumTeam the new scrum team
   */
  public void setScrumTeam(final boolean isScrumTeam) {
    this.isScrumTeam = isScrumTeam;
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
        || obj != null && getClass() == obj.getClass() && id == ((Team) obj).id;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "{\"Id\":\"" + id + "\",\"Name\":\"" + name + "\",\"isScrumTeam\":" + isScrumTeam + "}";
  }
}
