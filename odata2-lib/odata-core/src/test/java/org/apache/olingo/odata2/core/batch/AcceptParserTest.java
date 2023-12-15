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
package org.apache.olingo.odata2.core.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.olingo.odata2.api.batch.BatchException;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class AcceptParserTest.
 */
public class AcceptParserTest {
  
  /** The Constant TAB. */
  private static final String TAB = "\t";

  /**
   * Test accept header.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptHeader() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
    List<String> acceptHeaders = parser.parseAcceptHeaders();
    
    assertNotNull(acceptHeaders);
    assertEquals(4, acceptHeaders.size());
    assertEquals("text/html", acceptHeaders.get(0));
    assertEquals("application/xhtml+xml", acceptHeaders.get(1));
    assertEquals("application/xml", acceptHeaders.get(2));
    assertEquals("*/*", acceptHeaders.get(3));
  }

  /**
   * Test accept header with parameter.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptHeaderWithParameter() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("application/json;odata=verbose;q=1.0, */*;q=0.1");
    List<String> acceptHeaders = parser.parseAcceptHeaders();
    
    assertNotNull(acceptHeaders);
    assertEquals(2, acceptHeaders.size());
    assertEquals("application/json;odata=verbose", acceptHeaders.get(0));
    assertEquals("*/*", acceptHeaders.get(1));
  }

  /**
   * Test accept header with parameter and lws.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptHeaderWithParameterAndLws() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("application/json;  odata=verbose;q=1.0, */*;q=0.1");
    List<String> acceptHeaders = parser.parseAcceptHeaders();

    assertNotNull(acceptHeaders);
    assertEquals(2, acceptHeaders.size());
    assertEquals("application/json;  odata=verbose", acceptHeaders.get(0));
    assertEquals("*/*", acceptHeaders.get(1));
  }

  /**
   * Test accept header with tabulator.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptHeaderWithTabulator() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("application/json;\todata=verbose;q=1.0, */*;q=0.1");
    List<String> acceptHeaders = parser.parseAcceptHeaders();
    
    assertNotNull(acceptHeaders);
    assertEquals(2, acceptHeaders.size());
    assertEquals("application/json;" + TAB + "odata=verbose", acceptHeaders.get(0));
    assertEquals("*/*", acceptHeaders.get(1));
  }
  
  /**
   * Test special accept language.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testSpecialAcceptLanguage() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("en-US-x-XXXXXX");
    List<String> acceptLanguageHeaders = parser.parseAcceptableLanguages();
    assertNotNull(acceptLanguageHeaders);
    assertEquals(1, acceptLanguageHeaders.size());
    assertEquals("en-US-x-XXXXXX", acceptLanguageHeaders.get(0)); 
  }
  
  /**
   * Test invalid accept language 1.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testInvalidAcceptLanguage1() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("en-US-x-xxxx-");
    parser.parseAcceptHeaders();
  }
  
  /**
   * Test invalid accept language 2.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testInvalidAcceptLanguage2() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("en-US-");
    parser.parseAcceptableLanguages();
  }
  
  /**
   * Test all accept language invalid.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testAllAcceptLanguageInvalid() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("*-DE");
    parser.parseAcceptableLanguages();
  }
  
  /**
   * Test accept language 16 char.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testAcceptLanguage16Char() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("abcdefghijklmnop");
    parser.parseAcceptableLanguages();
  }
  
  /**
   * Test accept language upper.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptLanguageUpper() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("EN-DE");
    List<String> acceptLanguageHeaders = parser.parseAcceptableLanguages();
    
    assertNotNull(acceptLanguageHeaders);
    assertEquals(1, acceptLanguageHeaders.size());
  }
  
  /**
   * Test accept language many parts.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptLanguageManyParts() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("en-DE-FR-IN-AD");
    List<String> acceptLanguageHeaders = parser.parseAcceptableLanguages();
    
    assertNotNull(acceptLanguageHeaders);
    assertEquals(1, acceptLanguageHeaders.size());
  }
  
  /**
   * Test accept language ciphers.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptLanguageCiphers() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("en-150");
    List<String> acceptLanguageHeaders = parser.parseAcceptableLanguages();
    
    assertNotNull(acceptLanguageHeaders);
    assertEquals(1, acceptLanguageHeaders.size());
  }
  
  /**
   * Test invalid accept language 4.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testInvalidAcceptLanguage4() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("en-US-x-$%");
    parser.parseAcceptableLanguages();
  }
  
  /**
   * Test invalid accept language 5.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testInvalidAcceptLanguage5() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("en-");
    parser.parseAcceptableLanguages();
  }
  
  /**
   * Test accept header with two parameters.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptHeaderWithTwoParameters() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("application/xml;another=test ; param=alskdf, */*;q=0.1");
    List<String> acceptHeaders = parser.parseAcceptHeaders();

    assertNotNull(acceptHeaders);
    assertEquals(2, acceptHeaders.size());
    assertEquals("application/xml;another=test ; param=alskdf", acceptHeaders.get(0));
    assertEquals("*/*", acceptHeaders.get(1));
  }

  /**
   * Test accept header 2.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptHeader2() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("text/html;level=1, application/*, */*;q=0.1");
    List<String> acceptHeaders = parser.parseAcceptHeaders();

    assertNotNull(acceptHeaders);
    assertEquals(3, acceptHeaders.size());
    assertEquals("text/html;level=1", acceptHeaders.get(0));
    assertEquals("application/*", acceptHeaders.get(1));
    assertEquals("*/*", acceptHeaders.get(2));
  }

  /**
   * Test more specific media type.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testMoreSpecificMediaType() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("application/*, application/xml");
    List<String> acceptHeaders = parser.parseAcceptHeaders();
    
    assertNotNull(acceptHeaders);
    assertEquals(2, acceptHeaders.size());
    assertEquals("application/xml", acceptHeaders.get(0));
    assertEquals("application/*", acceptHeaders.get(1));
  }

  /**
   * Test quality parameter.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testQualityParameter() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("application/*, */*; q=0.012");
    List<String> acceptHeaders = parser.parseAcceptHeaders();
    
    assertNotNull(acceptHeaders);
  }

  /**
   * Test invalid accept header.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testInvalidAcceptHeader() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("appi cation/*, */*;q=0.1");
    parser.parseAcceptHeaders();
  }

  /**
   * Test invalid quality parameter.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testInvalidQualityParameter() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("appication/*, */*;q=0,9");
    parser.parseAcceptHeaders();
  }

  /**
   * Test invalid quality parameter 2.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testInvalidQualityParameter2() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptHeaderValue("appication/*, */*;q=1.0001");
    parser.parseAcceptHeaders();
  }

  /**
   * Test accept languages.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptLanguages() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("en-US,en;q=0.7,en-UK;q=0.9");
    List<String> acceptLanguageHeaders = parser.parseAcceptableLanguages();

    assertNotNull(acceptLanguageHeaders);
    assertEquals(3, acceptLanguageHeaders.size());
    assertEquals("en-US", acceptLanguageHeaders.get(0));
    assertEquals("en-UK", acceptLanguageHeaders.get(1));
    assertEquals("en", acceptLanguageHeaders.get(2));
  }

  /**
   * Test all accept languages.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAllAcceptLanguages() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("*");
    List<String> acceptLanguageHeaders = parser.parseAcceptableLanguages();
    
    assertNotNull(acceptLanguageHeaders);
    assertEquals(1, acceptLanguageHeaders.size());
  }

  /**
   * Test long accept language value.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testLongAcceptLanguageValue() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("english");
    List<String> acceptLanguageHeaders = parser.parseAcceptableLanguages();
    
    assertNotNull(acceptLanguageHeaders);
    assertEquals("english", acceptLanguageHeaders.get(0));
  }

  /**
   * Test invalid accept language value.
   *
   * @throws BatchException the batch exception
   */
  @Test(expected = BatchException.class)
  public void testInvalidAcceptLanguageValue() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("en_US");
    parser.parseAcceptableLanguages();
  }
  
  /**
   * Test accept languages with alpha numeric values.
   *
   * @throws BatchException the batch exception
   */
  @Test
  public void testAcceptLanguagesWithAlphaNumericValues() throws BatchException {
    AcceptParser parser = new AcceptParser();
    parser.addAcceptLanguageHeaderValue("es-419,en-US");
    List<String> acceptLanguageHeaders = parser.parseAcceptableLanguages();

    assertNotNull(acceptLanguageHeaders);
    assertEquals(2, acceptLanguageHeaders.size());
    assertEquals("es-419", acceptLanguageHeaders.get(0));
    assertEquals("en-US", acceptLanguageHeaders.get(1));
  }
}
