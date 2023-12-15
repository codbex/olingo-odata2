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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ManagerTest.
 */
public class ManagerTest extends BaseTest {

  /** The Constant VALUE_NAME. */
  private static final String VALUE_NAME = "Peter Schulz";
  
  /** The Constant TEAM_NAME. */
  private static final String TEAM_NAME = "Team 1";

  /**
   * Test id.
   */
  @Test
  public void testId() {
    Manager manager = new Manager(1, null);
    assertNotNull(manager.getId());
  }

  /**
   * Test manager name.
   */
  @Test
  public void testManagerName() {
    Manager manager = new Manager(1, VALUE_NAME);
    assertEquals(VALUE_NAME, manager.getEmployeeName());
  }

  /**
   * Test manager.
   */
  @Test
  public void testManager() {
    Manager manager = new Manager(1, "Walter Winter");
    Employee employee = new Employee(2, "Peter Burke");
    Manager manager2 = new Manager(3, "Jonathan Smith");
    List<Employee> list = Arrays.asList(manager2, employee, manager);
    manager.getEmployees().addAll(list);
    for (Employee emp : list) {
      emp.setManager(manager);
    }
    assertEquals(list, manager.getEmployees());
    assertEquals(manager, employee.getManager());
  }

  /**
   * Test room.
   */
  @Test
  public void testRoom() {
    Employee manager = new Manager(1, null);
    Room room = new Room(1, null);
    room.getEmployees().add(manager);
    manager.setRoom(room);
    assertEquals(room, manager.getRoom());
    assertEquals(manager, room.getEmployees().get(0));
  }

  /**
   * Test team.
   */
  @Test
  public void testTeam() {
    Employee manager = new Manager(1, null);
    List<Employee> list = Arrays.asList(manager);
    Team team = new Team(1, TEAM_NAME);
    team.getEmployees().add(manager);
    manager.setTeam(team);
    assertEquals(team, manager.getTeam());
    assertEquals(list, team.getEmployees());
  }

  /**
   * Test employees.
   */
  @Test
  public void testEmployees() {
    Manager manager = new Manager(1, null);
    Employee employee1 = new Employee(2, null);
    Employee employee2 = new Employee(3, null);
    List<Employee> employeesList = Arrays.asList(employee1, employee2);
    manager.getEmployees().addAll(employeesList);
    for (Employee emp : employeesList) {
      emp.setManager(manager);
    }
    List<Employee> testList = manager.getEmployees();
    assertEquals(testList, employeesList);
    assertEquals(manager, employee1.getManager());
  }

}
