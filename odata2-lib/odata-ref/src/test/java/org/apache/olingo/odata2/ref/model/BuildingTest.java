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

import java.util.Arrays;
import java.util.List;

import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class BuildingTest.
 */
public class BuildingTest extends BaseTest {

  /** The Constant VALUE_NAME. */
  private static final String VALUE_NAME = "bd01";
  
  /** The Constant VALUE_IMAGE. */
  private static final String VALUE_IMAGE = "String for convert";

  /**
   * Test id.
   */
  @Test
  public void testId() {
    Building build1 = new Building(1, null);
    assertNotNull(build1.getId());
  }

  /**
   * Test name.
   */
  @Test
  public void testName() {
    Building build1 = new Building(1, VALUE_NAME);
    assertEquals(VALUE_NAME, build1.getName());
  }

  /**
   * Test image.
   */
  @Test
  public void testImage() {
    Building build1 = new Building(1, null);
    final byte[] byteArray = VALUE_IMAGE.getBytes();
    build1.setImage(byteArray);
    byte[] testArray = build1.getImage();
    assertEquals(byteArray.length, testArray.length);
    assertArrayEquals(byteArray, testArray);
  }

  /**
   * Test rooms.
   */
  @Test
  public void testRooms() {
    List<Room> list = Arrays.asList(new Room(1, null), new Room(2, null), new Room(3, null));
    Building building1 = new Building(1, null);
    building1.getRooms().add(list.get(0));
    building1.getRooms().add(list.get(1));
    building1.getRooms().add(list.get(2));
    assertEquals(list, building1.getRooms());
  }

}
