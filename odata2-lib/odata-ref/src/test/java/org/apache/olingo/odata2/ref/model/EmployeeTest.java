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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeeTest.
 */
public class EmployeeTest extends BaseTest {

  /** The Constant VALUE_AGE. */
  private static final int VALUE_AGE = 36;
  
  /** The Constant VALUE_NAME. */
  private static final String VALUE_NAME = "Alex Kaiser";
  
  /** The Constant VALUE_URI. */
  private static final String VALUE_URI = "http://localhost/employee1.jpg";
  
  /** The Constant IMAGE_URL. */
  private static final String IMAGE_URL = "/Employee_1.png";
  
  /** The Constant IMAGE. */
  private static final String IMAGE = "String for convert";
  
  /** The Constant TYPE. */
  private static final String TYPE = "image/jpeg";

  /**
   * Test id.
   */
  @Test
  public void testId() {
    Employee employee1 = new Employee(1, null);
    assertNotNull(employee1.getId());
  }

  /**
   * Test name.
   */
  @Test
  public void testName() {
    Employee employee1 = new Employee(1, VALUE_NAME);
    assertEquals(VALUE_NAME, employee1.getEmployeeName());
  }

  /**
   * Test age.
   */
  @Test
  public void testAge() {
    Employee employee1 = new Employee(1, null);
    employee1.setAge(VALUE_AGE);
    assertEquals(VALUE_AGE, employee1.getAge());
  }

  /**
   * Test image uri.
   */
  @Test
  public void testImageUri() {
    Employee employee1 = new Employee(1, null);
    employee1.setImageUri(VALUE_URI);
    assertEquals(VALUE_URI, employee1.getImageUri());
  }

  /**
   * Test manager.
   */
  @Test
  public void testManager() {
    Employee employee1 = new Employee(1, null);
    Manager manager1 = new Manager(2, null);
    manager1.getEmployees().add(employee1);
    employee1.setManager(manager1);
    assertEquals(manager1, employee1.getManager());
    assertEquals(employee1, manager1.getEmployees().get(0));
  }

  /**
   * Test team.
   */
  @Test
  public void testTeam() {
    Employee employee1 = new Employee(1, null);
    final Team team1 = new Team(1, null);
    team1.getEmployees().add(employee1);
    employee1.setTeam(team1);
    assertEquals(team1, employee1.getTeam());
    assertEquals(employee1, team1.getEmployees().get(0));
  }

  /**
   * Test room.
   */
  @Test
  public void testRoom() {
    Employee employee1 = new Employee(1, null);
    Room room1 = new Room(1, null);
    room1.getEmployees().add(employee1);
    employee1.setRoom(room1);
    assertEquals(room1, employee1.getRoom());
    assertEquals(employee1, room1.getEmployees().get(0));
  }

  /**
   * Test location.
   */
  @Test
  public void testLocation() {
    Location location = new Location(null, null, null);
    Employee emp1 = new Employee(1, null);
    emp1.setLocation(location);
    assertEquals(location, emp1.getLocation());
  }

  /**
   * Test entry date.
   */
  @Test
  public void testEntryDate() {
    Employee employee1 = new Employee(1, null);
    final Calendar date1 = Calendar.getInstance();
    employee1.setEntryDate(date1);
    assertEquals(date1, employee1.getEntryDate());
  }

  /**
   * Test image.
   */
  @Test
  public void testImage() {
    byte[] byteArray = null;
    Employee employee1 = new Employee(1, VALUE_NAME);
    employee1.setImage(IMAGE_URL);
    try {
      InputStream in = Employee.class.getResourceAsStream(IMAGE_URL);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      int c = 0;
      while ((c = in.read()) != -1) {
        bos.write((char) c);
      }

      byteArray = bos.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    assertArrayEquals(byteArray, employee1.getImage());
  }

  /**
   * Test image 2.
   */
  @Test
  public void testImage2() {
    byte[] byteArray = IMAGE.getBytes();
    Employee employee1 = new Employee(1, null);
    employee1.setImage(byteArray);
    byte[] byteArrayOfImage = employee1.getImage();
    assertEquals(byteArray.length, byteArrayOfImage.length);
    assertArrayEquals(byteArray, byteArrayOfImage);
  }

  /**
   * Test image type.
   */
  @Test
  public void testImageType() {
    Employee employee1 = new Employee(1, null);
    employee1.setImageType(TYPE);
    assertEquals(TYPE, employee1.getImageType());
  }

}
