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

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty.Multiplicity;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmType;

// TODO: Auto-generated Javadoc
/**
 * The Class Team.
 */
@EdmEntityType(name = "Team", namespace = ModelSharedConstants.NAMESPACE_1)
@EdmEntitySet(name = "Teams")
public class Team extends RefBase {
  
  /** The is scrum team. */
  @EdmProperty(type = EdmType.BOOLEAN)
  private Boolean isScrumTeam;
  
  /** The employees. */
  @EdmNavigationProperty(name = "nt_Employees", association = "TeamEmployees", toMultiplicity = Multiplicity.MANY)
  private List<Employee> employees = new ArrayList<Employee>();
  
  /** The sub team. */
  @EdmNavigationProperty
  private Team subTeam;

  /**
   * Checks if is scrum team.
   *
   * @return the boolean
   */
  public Boolean isScrumTeam() {
    return isScrumTeam;
  }

  /**
   * Sets the scrum team.
   *
   * @param isScrumTeam the new scrum team
   */
  public void setScrumTeam(final Boolean isScrumTeam) {
    this.isScrumTeam = isScrumTeam;
  }

  /**
   * Adds the employee.
   *
   * @param e the e
   */
  public void addEmployee(final Employee e) {
    employees.add(e);
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
   * Sets the sub team.
   *
   * @param subTeam the new sub team
   */
  public void setSubTeam(Team subTeam) {
    this.subTeam = subTeam;
  }

  /**
   * Gets the sub team.
   *
   * @return the sub team
   */
  public Team getSubTeam() {
    return subTeam;
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return super.hashCode();
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
    return "{\"Id\":\"" + id + "\",\"Name\":\"" + name + "\",\"IsScrumTeam\":" + isScrumTeam + "}";
  }
}