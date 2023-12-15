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
package org.apache.olingo.odata2.core.exception;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.core.exception.MessageService.Message;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageServiceTest.
 */
public class MessageServiceTest extends BaseTest {

  /** The Constant DEFAULT_LANGUAGE. */
  private static final Locale DEFAULT_LANGUAGE = new Locale("test", "foo");

  /**
   * Test resource bundle exception.
   *
   * @throws Exception the exception
   */
  @Test
  public void testResourceBundleException() throws Exception {
    MessageReference context = MessageReference.create(ODataMessageException.class, "COMMON");
    Message ms = MessageService.getMessage(null, context);

    assertEquals(
        "MessageService could not be created because of exception 'IllegalArgumentException with message " +
            "'Parameter locale MUST NOT be NULL.'.",
        ms.getText());
  }

  /**
   * Test.
   *
   * @throws Exception the exception
   */
  @Test
  public void test() throws Exception {
    MessageReference context = MessageReference.create(ODataMessageException.class, "COMMON");
    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, context);

    assertEquals("Common exception", ms.getText());
  }

  /**
   * Test parameter.
   *
   * @throws Exception the exception
   */
  @Test
  public void testParameter() throws Exception {
    MessageReference context =
        MessageReference.create(ODataMessageException.class, "ONE_REPLACEMENTS").addContent("first");
    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, context);

    assertEquals("Only replacement is [first]!", ms.getText());
  }

  /**
   * Test one parameter for two.
   *
   * @throws Exception the exception
   */
  @Test
  public void testOneParameterForTwo() throws Exception {
    MessageReference context =
        MessageReference.create(ODataMessageException.class, "TWO_REPLACEMENTS").addContent("first");
    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, context);

    assertEquals(
        "Missing replacement for place holder in message 'First was [%1$s] and second was [%2$s]!' " +
            "for following arguments '[first]'!",
        ms.getText());
  }

  /**
   * Test two parameters.
   *
   * @throws Exception the exception
   */
  @Test
  public void testTwoParameters() throws Exception {
    MessageReference context =
        MessageReference.create(ODataMessageException.class, "TWO_REPLACEMENTS").addContent("first", "second");
    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, context);

    assertEquals("First was [first] and second was [second]!", ms.getText());
  }

  /**
   * Test two parameters with two add content.
   *
   * @throws Exception the exception
   */
  @Test
  public void testTwoParametersWithTwoAddContent() throws Exception {
    MessageReference context =
        MessageReference.create(ODataMessageException.class, "TWO_REPLACEMENTS").addContent("first").addContent(
            "second");
    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, context);

    assertEquals("First was [first] and second was [second]!", ms.getText());
  }

  /**
   * Test three parameters for two.
   *
   * @throws Exception the exception
   */
  @Test
  public void testThreeParametersForTwo() throws Exception {
    MessageReference context =
        MessageReference.create(ODataMessageException.class, "TWO_REPLACEMENTS").addContent("first", "second", "third");
    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, context);

    assertEquals("First was [first] and second was [second]!", ms.getText());
  }

  /**
   * Test three parameters per add content for two.
   *
   * @throws Exception the exception
   */
  @Test
  public void testThreeParametersPerAddContentForTwo() throws Exception {
    MessageReference context = MessageReference.create(ODataMessageException.class, "TWO_REPLACEMENTS")
        .addContent("first").addContent("second").addContent("third");
    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, context);

    assertEquals("First was [first] and second was [second]!", ms.getText());
  }

  /**
   * Test three parameters per mixed for two.
   *
   * @throws Exception the exception
   */
  @Test
  public void testThreeParametersPerMixedForTwo() throws Exception {
    MessageReference context = MessageReference.create(ODataMessageException.class, "TWO_REPLACEMENTS")
        .addContent("first").addContent("second", "third");
    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, context);

    assertEquals("First was [first] and second was [second]!", ms.getText());
  }

}
