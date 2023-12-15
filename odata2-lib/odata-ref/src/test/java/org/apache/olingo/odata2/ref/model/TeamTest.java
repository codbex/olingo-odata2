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
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class TeamTest.
 */
public class TeamTest extends BaseTest {

  /** The Constant VALUE_NAME. */
  private static final String VALUE_NAME = "Team 1";

  /**
   * Test id.
   */
  @Test
  public void testId() {
    Team team1 = new Team(1, null);
    assertNotNull(team1.getId());
  }

  /**
   * Test name.
   */
  @Test
  public void testName() {
    Team team1 = new Team(1, VALUE_NAME);
    assertEquals(team1.getName(), VALUE_NAME);
  }

  /**
   * Test is scrum team.
   */
  @Test
  public void testIsScrumTeam() {
    Team team1 = new Team(1, null);
    team1.setScrumTeam(true);
    assertTrue(team1.isScrumTeam());
  }

  /**
   * Test employees.
   */
  @Test
  public void testEmployees() {
    Team team1 = new Team(1, VALUE_NAME);
    Employee employee1 = new Employee(1, null);
    Employee employee2 = new Employee(2, null);
    List<Employee> testList = Arrays.asList(employee1, employee2);
    team1.getEmployees().addAll(testList);
    for (Employee emp : testList) {
      emp.setTeam(team1);
    }
    assertEquals(testList, team1.getEmployees());
    assertEquals(team1, employee1.getTeam());
  }
}
