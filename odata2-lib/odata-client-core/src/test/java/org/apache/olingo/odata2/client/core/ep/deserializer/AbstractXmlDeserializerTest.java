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
package org.apache.olingo.odata2.client.core.ep.deserializer;

import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractXmlDeserializerTest.
 */
@RunWith(Parameterized.class)
public abstract class AbstractXmlDeserializerTest extends AbstractDeserializerTest {

  /**
   * The Enum StreamWriterImplType.
   */
  public enum StreamWriterImplType {
    
    /** The woodstockimpl. */
    WOODSTOCKIMPL, 
 /** The suninternalimpl. */
 SUNINTERNALIMPL;
  }

  /**
   * Instantiates a new abstract xml deserializer test.
   *
   * @param type the type
   */
  // CHECKSTYLE:OFF
  public AbstractXmlDeserializerTest(final StreamWriterImplType type) {
    switch (type) {
    case WOODSTOCKIMPL:
      System.setProperty("javax.xml.stream.XMLInputFactory", "com.ctc.wstx.stax.WstxInputFactory"); // NOSONAR
      System.setProperty("javax.xml.stream.XMLOutputFactory", "com.ctc.wstx.stax.WstxOutputFactory"); // NOSONAR
      break;
    case SUNINTERNALIMPL:
      System.setProperty("javax.xml.stream.XMLInputFactory", "com.sun.xml.internal.stream.XMLInputFactoryImpl"); // NOSONAR
      System.setProperty("javax.xml.stream.XMLOutputFactory", "com.sun.xml.internal.stream.XMLOutputFactoryImpl"); // NOSONAR
      break;
    default:
      System.setProperty("javax.xml.stream.XMLOutputFactory", "com.sun.xml.internal.stream.XMLOutputFactoryImpl"); // NOSONAR
      System.setProperty("javax.xml.stream.XMLInputFactory", "com.sun.xml.internal.stream.XMLInputFactoryImpl"); // NOSONAR
      break;
    }
  }

  // CHECKSTYLE:On

  /**
   * Data.
   *
   * @return the list
   */
  @Parameterized.Parameters
  public static List<Object[]> data() {
    // If desired this can be made dependent on runtime variables
    Object[][] a = new Object[2][1];
    a[0][0] = StreamWriterImplType.WOODSTOCKIMPL;
    a[1][0] = StreamWriterImplType.SUNINTERNALIMPL;

    return Arrays.asList(a);
  }
}
