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

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;

// TODO: Auto-generated Javadoc
/**
 * Helper for XML unit tests.
 * 
 */
public class XMLUnitHelper {

  /**
   * Verifies order of <code>tags</code> in given <code>xmlString</code>.
   *
   * @param xmlString the xml string
   * @param toCheckTags the to check tags
   */
  public static void verifyTagOrdering(final String xmlString, final String... toCheckTags) {
    int lastTagPos = -1;

    for (final String tagName : toCheckTags) {
      final Pattern p = Pattern.compile(tagName);
      final Matcher m = p.matcher(xmlString);

      if (m.find()) {
        final int currentTagPos = m.start();
        Assert.assertTrue("Tag with name '" + tagName + "' is not in correct order. Expected order is '"
            + Arrays.toString(toCheckTags) + "'.",
            lastTagPos < currentTagPos);
        lastTagPos = currentTagPos;
      } else {
        Assert.fail("Expected tag '" + tagName + "' was not found in input [\n\n" + xmlString + "\n\n].");
      }

    }
  }
}
