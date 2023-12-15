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
 * The Class RoomTest.
 */
public class RoomTest extends BaseTest {

  /** The Constant VALUE_VERSION_NR. */
  private static final int VALUE_VERSION_NR = 1;
  
  /** The Constant VALUE_4. */
  private static final int VALUE_4 = 4;
  
  /** The Constant NAME. */
  private static final String NAME = "Room 100";

  /**
   * Test id.
   */
  @Test
  public void testId() {
    Room room1 = new Room(1, NAME);
    assertNotNull(room1.getId());
  }

  /**
   * Test seats.
   */
  @Test
  public void testSeats() {
    Room room1 = new Room(1, null);
    room1.setSeats(VALUE_4);
    assertEquals(VALUE_4, room1.getSeats());
  }

  /**
   * Test version.
   */
  @Test
  public void testVersion() {
    Room room1 = new Room(1, null);
    room1.setVersion(VALUE_VERSION_NR);
    assertEquals(VALUE_VERSION_NR, room1.getVersion());
  }

  /**
   * Test building.
   */
  @Test
  public void testBuilding() {
    Room room1 = new Room(1, null);
    Building build1 = new Building(1, null);
    build1.getRooms().add(room1);
    room1.setBuilding(build1);
    assertEquals(build1, room1.getBuilding());
    assertEquals(room1, build1.getRooms().get(0));
  }

  /**
   * Test employees.
   */
  @Test
  public void testEmployees() {
    Employee employee1 = new Employee(1, null);
    Employee employee2 = new Employee(2, null);
    List<Employee> employeesList = Arrays.asList(employee1, employee2);
    Room room1 = new Room(1, null);
    room1.getEmployees().addAll(employeesList);
    employee1.setRoom(room1);
    employee2.setRoom(room1);
    assertEquals(employeesList, room1.getEmployees());
    assertEquals(room1, employee1.getRoom());
  }

}
