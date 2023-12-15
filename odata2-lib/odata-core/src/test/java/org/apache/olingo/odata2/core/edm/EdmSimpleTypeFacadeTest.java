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
package org.apache.olingo.odata2.core.edm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmLiteral;
import org.apache.olingo.odata2.api.edm.EdmLiteralException;
import org.apache.olingo.odata2.api.edm.EdmSimpleType;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.uri.UriSyntaxException;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * Tests for the parsing of URI literals.
 */
public class EdmSimpleTypeFacadeTest extends BaseTest {

  /**
   * Parses the.
   *
   * @param literal the literal
   * @param expectedType the expected type
   * @throws EdmLiteralException the edm literal exception
   */
  public static void parse(final String literal, final EdmSimpleType expectedType) throws EdmLiteralException {
    final EdmLiteral uriLiteral = EdmSimpleTypeKind.parseUriLiteral(literal);
    assertNotNull(uriLiteral);
    if (!expectedType.equals(EdmNull.getInstance())) {
      assertNotNull(uriLiteral.getLiteral());
      assertTrue(uriLiteral.getLiteral().length() > 0);
    }
    assertNotNull(uriLiteral.getType());
    assertEquals(expectedType, uriLiteral.getType());
  }

  /**
   * Compare.
   *
   * @param simpleType the simple type
   */
  public static void compare(final EdmSimpleTypeKind simpleType) {
    final EdmSimpleType bin1 = simpleType.getEdmSimpleTypeInstance();
    assertNotNull(bin1);

    final EdmSimpleType bin2 = simpleType.getEdmSimpleTypeInstance();
    assertNotNull(bin2);

    assertEquals(bin1, bin2);
  }

  /**
   * Parses the uri literal binary.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralBinary() throws EdmLiteralException {
    parse("X'Fa12aAA1'", EdmBinary.getInstance());
    parse("binary'FA12AAA1'", EdmBinary.getInstance());
  }

  /**
   * Parses the uri literal boolean.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralBoolean() throws EdmLiteralException {
    parse("true", EdmBoolean.getInstance());
    parse("false", EdmBoolean.getInstance());
  }

  /**
   * Parses the uri literal bit.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralBit() throws EdmLiteralException {
    parse("1", Bit.getInstance());
    parse("0", Bit.getInstance());
  }

  /**
   * Parses the uri literal byte.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralByte() throws EdmLiteralException {
    parse("255", EdmByte.getInstance());
  }

  /**
   * Parses the uri literal uint 7.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralUint7() throws EdmLiteralException {
    parse("123", Uint7.getInstance());
    parse("111", Uint7.getInstance());
    parse("42", Uint7.getInstance());
  }

  /**
   * Parses the uri literal date time.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralDateTime() throws EdmLiteralException {
    parse("datetime'2009-12-26T21:23:38'", EdmDateTime.getInstance());
  }

  /**
   * Parses the uri literal date time offset.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralDateTimeOffset() throws EdmLiteralException {
    parse("datetimeoffset'2009-12-26T21:23:38Z'", EdmDateTimeOffset.getInstance());
    parse("datetimeoffset'2002-10-10T12:00:00-05:00'", EdmDateTimeOffset.getInstance());
    parse("datetimeoffset'2009-12-26T21:23:38'", EdmDateTimeOffset.getInstance());
  }

  /**
   * Parses the uri literal decimal.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralDecimal() throws EdmLiteralException {
    parse("4.5m", EdmDecimal.getInstance());
    parse("4.5M", EdmDecimal.getInstance());
  }

  /**
   * Parses the uri literal double.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralDouble() throws EdmLiteralException {
    parse("4.5d", EdmDouble.getInstance());
    parse("4.5D", EdmDouble.getInstance());
  }

  /**
   * Parses the uri literal guid.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralGuid() throws EdmLiteralException {
    parse("guid'1225c695-cfb8-4ebb-aaaa-80da344efa6a'", EdmGuid.getInstance());
  }

  /**
   * Parses the uri literal int 16.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralInt16() throws EdmLiteralException {
    parse("-32768", EdmInt16.getInstance());
    parse("3276", EdmInt16.getInstance());
  }

  /**
   * Parses the uri literal int 32.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralInt32() throws EdmLiteralException {
    parse("-327687", EdmInt32.getInstance());
    parse("32768", EdmInt32.getInstance());
  }

  /**
   * Parses the uri literal int 64.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralInt64() throws EdmLiteralException {
    parse("64l", EdmInt64.getInstance());
    parse("64L", EdmInt64.getInstance());
  }

  /**
   * Parses the uri literal null.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralNull() throws EdmLiteralException {
    parse(null, EdmNull.getInstance());
    parse("null", EdmNull.getInstance());
  }

  /**
   * Parses the uri literal S byte.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralSByte() throws EdmLiteralException {
    parse("-123", EdmSByte.getInstance());
  }

  /**
   * Parses the uri literal single.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralSingle() throws EdmLiteralException {
    parse("4.5f", EdmSingle.getInstance());
    parse("4.5F", EdmSingle.getInstance());
    parse("-INF", EdmSingle.getInstance());
    parse("INF", EdmSingle.getInstance());
    parse("NaN", EdmSingle.getInstance());
  }

  /**
   * Parses the uri literal string.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralString() throws EdmLiteralException {
    parse("'abc'", EdmString.getInstance());
  }

  /**
   * Parses the uri literal time.
   *
   * @throws EdmLiteralException the edm literal exception
   */
  @Test
  public void parseUriLiteralTime() throws EdmLiteralException {
    parse("time'PT120S'", EdmTime.getInstance());
  }

  /**
   * Compare types.
   */
  @Test
  public void compareTypes() {
    compare(EdmSimpleTypeKind.Binary);
    compare(EdmSimpleTypeKind.Boolean);
    compare(EdmSimpleTypeKind.Byte);
    compare(EdmSimpleTypeKind.SByte);
    compare(EdmSimpleTypeKind.DateTime);
    compare(EdmSimpleTypeKind.DateTimeOffset);
    compare(EdmSimpleTypeKind.Decimal);
    compare(EdmSimpleTypeKind.Double);
    compare(EdmSimpleTypeKind.Guid);
    compare(EdmSimpleTypeKind.Int16);
    compare(EdmSimpleTypeKind.Int32);
    compare(EdmSimpleTypeKind.Int64);
    compare(EdmSimpleTypeKind.Single);
    compare(EdmSimpleTypeKind.Time);
  }

  /**
   * Parse a URI literal value string and assert that it is compatible
   * to the given EDM simple type and has the correct parsed value.
   *
   * @param literal the URI literal value to be parsed as string
   * @param typeKind the {@link EdmSimpleTypeKind} the URI literal should be compatible to
   * @param expectedLiteral the expected literal value
   * @throws UriSyntaxException the uri syntax exception
   * @throws EdmException the edm exception
   */
  private void parseLiteral(final String literal, final EdmSimpleTypeKind typeKind, final String expectedLiteral)
      throws UriSyntaxException, EdmException {
    final EdmLiteral uriLiteral = EdmSimpleTypeKind.parseUriLiteral(literal);

    assertTrue(typeKind.getEdmSimpleTypeInstance().isCompatible(uriLiteral.getType()));
    assertEquals(expectedLiteral, uriLiteral.getLiteral());
  }

  /**
   * Parses the decimal.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseDecimal() throws Exception {
    parseLiteral("4.5m", EdmSimpleTypeKind.Decimal, "4.5");
    parseLiteral("4.5M", EdmSimpleTypeKind.Decimal, "4.5");
    parseLiteral("1", EdmSimpleTypeKind.Decimal, "1");
    parseLiteral("255", EdmSimpleTypeKind.Decimal, "255");
    parseLiteral("-32768", EdmSimpleTypeKind.Decimal, "-32768");
    parseLiteral("32768", EdmSimpleTypeKind.Decimal, "32768");
    parseLiteral("3000000", EdmSimpleTypeKind.Decimal, "3000000");
    parseLiteral("4.5d", EdmSimpleTypeKind.Decimal, "4.5");
    parseLiteral("4.2E9F", EdmSimpleTypeKind.Decimal, "4.2E9");
    parseLiteral("1234567890", EdmSimpleTypeKind.Decimal, "1234567890");
  }

  /**
   * Parses the int 16.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseInt16() throws Exception {
    parseLiteral("16", EdmSimpleTypeKind.Int16, "16");
    parseLiteral("-16", EdmSimpleTypeKind.Int16, "-16");
    parseLiteral("255", EdmSimpleTypeKind.Int16, "255");
    parseLiteral("-32768", EdmSimpleTypeKind.Int16, "-32768");

  }

  /**
   * Parses the int 32.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseInt32() throws Exception {
    parseLiteral("32", EdmSimpleTypeKind.Int32, "32");
    parseLiteral("-127", EdmSimpleTypeKind.Int32, "-127");
    parseLiteral("255", EdmSimpleTypeKind.Int32, "255");
    parseLiteral("32767", EdmSimpleTypeKind.Int32, "32767");
    parseLiteral("-32769", EdmSimpleTypeKind.Int32, "-32769");
  }

  /**
   * Parses the int 64.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseInt64() throws Exception {
    parseLiteral("64", EdmSimpleTypeKind.Int64, "64");
    parseLiteral("255", EdmSimpleTypeKind.Int64, "255");
    parseLiteral("1000", EdmSimpleTypeKind.Int64, "1000");
    parseLiteral("100000", EdmSimpleTypeKind.Int64, "100000");
    parseLiteral("-64L", EdmSimpleTypeKind.Int64, "-64");
    parseLiteral("" + Long.MAX_VALUE + "L", EdmSimpleTypeKind.Int64, "" + Long.MAX_VALUE);
    parseLiteral("" + Long.MIN_VALUE + "l", EdmSimpleTypeKind.Int64, "" + Long.MIN_VALUE);
  }

  /**
   * Parses the string.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseString() throws Exception {
    parseLiteral("'abc'", EdmSimpleTypeKind.String, "abc");
    parseLiteral("'true'", EdmSimpleTypeKind.String, "true");
    parseLiteral("''", EdmSimpleTypeKind.String, "");
  }

  /**
   * Parses the single.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSingle() throws Exception {
    parseLiteral("45", EdmSimpleTypeKind.Single, "45");
    parseLiteral("255", EdmSimpleTypeKind.Single, "255");
    parseLiteral("-32768", EdmSimpleTypeKind.Single, "-32768");
    parseLiteral("32768", EdmSimpleTypeKind.Single, "32768");
    parseLiteral("1L", EdmSimpleTypeKind.Single, "1");
    parseLiteral("4.5f", EdmSimpleTypeKind.Single, "4.5");
    parseLiteral("4.5F", EdmSimpleTypeKind.Single, "4.5");
    parseLiteral("4.5e9f", EdmSimpleTypeKind.Single, "4.5e9");
    parseLiteral("-INF", EdmSimpleTypeKind.Single, "-INF");
    parseLiteral("INF", EdmSimpleTypeKind.Single, "INF");
    parseLiteral("NaN", EdmSimpleTypeKind.Single, "NaN");
  }

  /**
   * Parses the double.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseDouble() throws Exception {
    parseLiteral("45", EdmSimpleTypeKind.Double, "45");
    parseLiteral("255", EdmSimpleTypeKind.Double, "255");
    parseLiteral("-32768", EdmSimpleTypeKind.Double, "-32768");
    parseLiteral("32768", EdmSimpleTypeKind.Double, "32768");
    parseLiteral("1l", EdmSimpleTypeKind.Double, "1");
    parseLiteral("4.5d", EdmSimpleTypeKind.Double, "4.5");
    parseLiteral("4.5D", EdmSimpleTypeKind.Double, "4.5");
    parseLiteral("4.5e21f", EdmSimpleTypeKind.Double, "4.5e21");
  }

  /**
   * Parses the byte.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseByte() throws Exception {
    parseLiteral("255", EdmSimpleTypeKind.Byte, "255");
    parseLiteral("123", EdmSimpleTypeKind.Byte, "123");
  }

  /**
   * Parses the guid.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseGuid() throws Exception {
    parseLiteral("guid'1225c695-cfb8-4ebb-aaaa-80da344efa6a'", EdmSimpleTypeKind.Guid,
        "1225c695-cfb8-4ebb-aaaa-80da344efa6a");
  }

  /**
   * Parses the time.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseTime() throws Exception {
    parseLiteral("time'PT120S'", EdmSimpleTypeKind.Time, "PT120S");
  }

  /**
   * Parses the datetime.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseDatetime() throws Exception {
    parseLiteral("datetime'2009-12-26T21:23:38'", EdmSimpleTypeKind.DateTime, "2009-12-26T21:23:38");
  }

  /**
   * Parses the datetime offset.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseDatetimeOffset() throws Exception {
    parseLiteral("datetimeoffset'2009-12-26T21:23:38Z'", EdmSimpleTypeKind.DateTimeOffset, "2009-12-26T21:23:38Z");
    parseLiteral("datetimeoffset'2002-10-10T12:00:00-05:00'", EdmSimpleTypeKind.DateTimeOffset,
        "2002-10-10T12:00:00-05:00");
    parseLiteral("datetimeoffset'2009-12-26T21:23:38'", EdmSimpleTypeKind.DateTimeOffset, "2009-12-26T21:23:38");
  }

  /**
   * Parses the boolean.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseBoolean() throws Exception {
    parseLiteral("true", EdmSimpleTypeKind.Boolean, "true");
    parseLiteral("false", EdmSimpleTypeKind.Boolean, "false");
    parseLiteral("1", EdmSimpleTypeKind.Boolean, "1");
    parseLiteral("0", EdmSimpleTypeKind.Boolean, "0");
  }

  /**
   * Parses the S byte.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseSByte() throws Exception {
    parseLiteral("-123", EdmSimpleTypeKind.SByte, "-123");
    parseLiteral("12", EdmSimpleTypeKind.SByte, "12");
  }

  /**
   * Parses the binary.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseBinary() throws Exception {
    parseLiteral("X'Fa12aAA1'", EdmSimpleTypeKind.Binary, "+hKqoQ==");
    parseLiteral("binary'FA12AAA1'", EdmSimpleTypeKind.Binary, "+hKqoQ==");
  }

  /**
   * Parses the wrong literal content.
   *
   * @param literal the literal
   * @param messageReference the message reference
   */
  private void parseWrongLiteralContent(final String literal, final MessageReference messageReference) {
    try {
      EdmSimpleTypeKind.parseUriLiteral(literal);
      fail("Expected EdmLiteralException not thrown");
    } catch (EdmLiteralException e) {
      assertNotNull(e);
      assertEquals(messageReference.getKey(), e.getMessageReference().getKey());
    }
  }

  /**
   * Parses the literal with wrong content.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseLiteralWithWrongContent() throws Exception {
    parseWrongLiteralContent("binary'abcde'", EdmLiteralException.LITERALFORMAT);
    parseWrongLiteralContent("'", EdmLiteralException.UNKNOWNLITERAL);
    parseWrongLiteralContent("'a", EdmLiteralException.UNKNOWNLITERAL);
    parseWrongLiteralContent("wrongprefix'PT1H2M3S'", EdmLiteralException.UNKNOWNLITERAL);
    parseWrongLiteralContent("32i", EdmLiteralException.UNKNOWNLITERAL);
    parseWrongLiteralContent("9876543210", EdmLiteralException.LITERALFORMAT);
    parseWrongLiteralContent("-9876543210", EdmLiteralException.LITERALFORMAT);
    parseWrongLiteralContent("12345678901234567890L", EdmLiteralException.LITERALFORMAT);
    parseWrongLiteralContent("guid'a'", EdmLiteralException.LITERALFORMAT);
    parseWrongLiteralContent("datetime'1'", EdmLiteralException.LITERALFORMAT);
    parseWrongLiteralContent("datetimeoffset'2'", EdmLiteralException.LITERALFORMAT);
    parseWrongLiteralContent("time'3'", EdmLiteralException.LITERALFORMAT);
  }

  /**
   * Parses the incompatible literal content.
   *
   * @param literal the literal
   * @param typeKind the type kind
   * @throws EdmLiteralException the edm literal exception
   */
  private void parseIncompatibleLiteralContent(final String literal, final EdmSimpleTypeKind typeKind)
      throws EdmLiteralException {
    final EdmLiteral uriLiteral = EdmSimpleTypeKind.parseUriLiteral(literal);
    assertFalse(typeKind.getEdmSimpleTypeInstance().isCompatible(uriLiteral.getType()));
  }

  /**
   * Parses the incompatible literal.
   *
   * @throws Exception the exception
   */
  @Test
  public void parseIncompatibleLiteral() throws Exception {
    parseIncompatibleLiteralContent("1D", EdmSimpleTypeKind.Binary);
    parseIncompatibleLiteralContent("'0'", EdmSimpleTypeKind.Boolean);
    parseIncompatibleLiteralContent("'1'", EdmSimpleTypeKind.Boolean);
    parseIncompatibleLiteralContent("2", EdmSimpleTypeKind.Boolean);
    parseIncompatibleLiteralContent("-1", EdmSimpleTypeKind.Byte);
    parseIncompatibleLiteralContent("-129", EdmSimpleTypeKind.Byte);
    parseIncompatibleLiteralContent("time'PT11H12M13S'", EdmSimpleTypeKind.DateTime);
    parseIncompatibleLiteralContent("time'PT11H12M13S'", EdmSimpleTypeKind.DateTimeOffset);
    parseIncompatibleLiteralContent("'1'", EdmSimpleTypeKind.Decimal);
    parseIncompatibleLiteralContent("1M", EdmSimpleTypeKind.Double);
    parseIncompatibleLiteralContent("1", EdmSimpleTypeKind.Guid);
    parseIncompatibleLiteralContent("32768", EdmSimpleTypeKind.Int16);
    parseIncompatibleLiteralContent("1L", EdmSimpleTypeKind.Int32);
    parseIncompatibleLiteralContent("1M", EdmSimpleTypeKind.Int64);
    parseIncompatibleLiteralContent("128", EdmSimpleTypeKind.SByte);
    parseIncompatibleLiteralContent("1D", EdmSimpleTypeKind.Single);
    parseIncompatibleLiteralContent("1", EdmSimpleTypeKind.String);
    parseIncompatibleLiteralContent("datetime'2012-10-10T11:12:13'", EdmSimpleTypeKind.Time);
  }
}
