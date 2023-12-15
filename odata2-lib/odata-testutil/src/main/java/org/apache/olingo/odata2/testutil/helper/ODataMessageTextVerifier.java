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
package org.apache.olingo.odata2.testutil.helper;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.olingo.odata2.api.exception.MessageReference;

// TODO: Auto-generated Javadoc
/**
 * This class is a helper for writing proper error messages.
 * Please use the static method {@link #TestClass(Class)} to
 * test whether all fields of type {@link MessageReference} of
 * the tested (Exception) class are provided in the <b>i18n.properties</b> file.
 * 
 * 
 */
public class ODataMessageTextVerifier {

  /** Same as define in {@link MessageService}. */
  private static final String BUNDLE_NAME = "i18n"; //$NON-NLS-1$
  
  /** The Constant locale. */
  private static final Locale locale = Locale.ROOT;

  /** The resource bundle. */
  private ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
  
  /** The error collector. */
  private final List<Throwable> errorCollector;

  /**
   * Instantiates a new o data message text verifier.
   */
  public ODataMessageTextVerifier() {
    errorCollector = new ArrayList<Throwable>();
  }

  /**
   * Fail collector.
   *
   * @param text the text
   */
  private void failCollector(final String text) {
    try {
      fail(text);
    } catch (final AssertionError ae) {
      errorCollector.add(ae);
    }
  }

  /**
   * Gets the message.
   *
   * @param msgRef the msg ref
   * @return the message
   */
  private String getMessage(final MessageReference msgRef) {
    try {
      final String key = msgRef.getKey();
      final String value = resourceBundle.getString(key);
      return value;
    } catch (final MissingResourceException e) {
      failCollector("Error-->Messagetext for key:\"" + msgRef.getKey() + "\" missing");
    }
    return null;
  }

  /**
   * Assert exist message.
   *
   * @param msgRef the msg ref
   */
  private void assertExistMessage(final MessageReference msgRef) {
    final String text = getMessage(msgRef);
    if (text == null) {
      return; // checked in getMessage
    }

    if (text.length() == 0) {
      failCollector("Error-->Messagetext for key:\"" + msgRef.getKey() + "\" empty");
    }
  }

  /**
   * Check messages of class.
   *
   * @param exceptionClassToBeTested the exception class to be tested
   */
  public void CheckMessagesOfClass(final Class<? extends Exception> exceptionClassToBeTested) {
    final Class<? extends Exception> testClass = exceptionClassToBeTested;

    for (final Field field : testClass.getDeclaredFields()) {
      // if field from type MessageReference
      if (field.getType().isAssignableFrom(MessageReference.class)) {
        final int modifiers = field.getModifiers();
        if (!Modifier.isStatic(modifiers)) {
          continue;
        }
        // field should be public
        assertTrue("MsgRef Error--> Error: field should be public.", Modifier.isPublic(modifiers));
        // field should be final
        assertTrue("MsgRef Error--> Error: field should be final.", Modifier.isFinal(modifiers));

        MessageReference msgRef = null;
        try {
          msgRef = (MessageReference) field.get(null);
        } catch (final IllegalArgumentException e) {
          failCollector("MsgRef Error--> Error: MsgRef " + field.getName() + " of class \"" + testClass.getSimpleName()
              + "\"");
          break;
        } catch (final IllegalAccessException e) {
          failCollector("MsgRef Error--> Not public: MsgRef " + field.getName() + " of class \""
              + testClass.getSimpleName() + "\"");
          break;
        }

        if (msgRef == null) {
          failCollector("MsgRef Error--> Not assigned: MsgRef " + field.getName() + " of class \""
              + testClass.getSimpleName() + "\"");
          break;
        }

        assertExistMessage(msgRef);
      }
    }
  }

  /**
   * Gets the error collector.
   *
   * @return the error collector
   */
  public List<Throwable> getErrorCollector() {
    return errorCollector;
  }

  /**
   * Test class.
   *
   * @param exceptionClassToBeTested the exception class to be tested
   */
  static public void TestClass(final Class<? extends Exception> exceptionClassToBeTested) {
    final ODataMessageTextVerifier tool = new ODataMessageTextVerifier();
    tool.CheckMessagesOfClass(exceptionClassToBeTested);
    for (final Throwable throwable : tool.getErrorCollector()) {
      fail(throwable.getMessage());
    }
  }
}
