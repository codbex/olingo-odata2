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
package org.apache.olingo.odata2.jpa.processor.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.api.uri.expression.ExpressionParserException;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.jpa.processor.api.jpql.JPQLStatement;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class ODataFilterExpressionParserTest.
 */
public class ODataFilterExpressionParserTest {
  
  /** The Constant INPUT. */
  private static final short INPUT = 0;
  
  /** The Constant OUTPUT. */
  private static final short OUTPUT = 1;
  
  /** The Constant TABLE_ALIAS. */
  private static final String TABLE_ALIAS = "E1";
  
  /** The Constant NAMESPACE. */
  private static final String NAMESPACE = "SalesOrderProcessing";
  
  /** The Constant ENTITY_NOTE. */
  private static final String ENTITY_NOTE = "Note";
  
  /** The Constant EXPRESSION_EQ. */
  // Index 0 - Is test input and Index 1 - Is expected output
  private static final String[] EXPRESSION_EQ = { "id eq '123'", "(E1.id LIKE '123' ESCAPE '\\')" };
  
  /** The Constant EXPRESSION_NE. */
  private static final String[] EXPRESSION_NE = { "id ne '123'", "(E1.id NOT LIKE '123' ESCAPE '\\')" };
  
  /** The Constant EXPRESSION_NE_SPECIAL. */
  private static final String[] EXPRESSION_NE_SPECIAL = { "id ne '1_3'", "(E1.id NOT LIKE '1_3' ESCAPE '\\')" };
  
  /** The Constant EXPRESSION_ESCAPE. */
  private static final String[] EXPRESSION_ESCAPE = { "id ne '123''22'", "(E1.id NOT LIKE '123'22' ESCAPE '\\')" };
   
   /** The Constant EXPRESSION_BINARY_AND. */
   private static final String[] EXPRESSION_BINARY_AND =
  {
      "id le '123' and soId eq 123L and not (substringof(id,'123') eq false) eq true",
      "(((E1.id <= '123') AND (E1.soId = 123)) AND (NOT(((CASE WHEN ('123' LIKE CONCAT('%',CONCAT(E1.id,'%')"
      + ") ESCAPE '\\') "
          + "THEN TRUE ELSE FALSE END) = false)) = true))" };
  
  /** The Constant EXPRESSION_BINARY_OR. */
  private static final String[] EXPRESSION_BINARY_OR = { "id ge '123' or soId gt 123L",
      "((E1.id >= '123') OR (E1.soId > 123))" };
  
  /** The Constant EXPRESSION_MEMBER_OR. */
  private static final String[] EXPRESSION_MEMBER_OR = { "id lt '123' or oValue/Currency eq 'INR'",
      "((E1.id < '123') OR (E1.oValue.Currency LIKE 'INR' ESCAPE '\\'))" };
  
  /** The Constant EXPRESSION_STARTS_WITH. */
  private static final String[] EXPRESSION_STARTS_WITH = { "startswith(oValue/Currency,'INR')",
      "E1.oValue.Currency LIKE CONCAT('INR','%') ESCAPE '\\'" };
  
  /** The Constant EXPRESSION_STARTS_WITH_EQUAL. */
  private static final String[] EXPRESSION_STARTS_WITH_EQUAL = { "startswith(oValue/Currency,'INR') eq true",
      "(E1.oValue.Currency LIKE CONCAT('INR','%') ESCAPE '\\' )" };
  
  /** The Constant EXPRESSION_NOT_STARTS_WITH. */
  private static final String[] EXPRESSION_NOT_STARTS_WITH = { "startswith(oValue/Currency,'INR') eq false",
      "(E1.oValue.Currency NOT LIKE CONCAT('INR','%') ESCAPE '\\' )" };
  
  /** The Constant EXPRESSION_NOT_ENDS_WITH. */
  private static final String[] EXPRESSION_NOT_ENDS_WITH = { "endswith(oValue/Currency,tolower('INR')) eq false",
      "(E1.oValue.Currency NOT LIKE CONCAT('%',LOWER('INR')) ESCAPE '\\' )" };
  
  /** The Constant EXPRESSION_NESTED_METHOD. */
  private static final String[] EXPRESSION_NESTED_METHOD = {
      "endswith(substring(oValue/Currency,2),'INR') eq false",
      "(SUBSTRING(E1.oValue.Currency, 2 + 1 ) NOT LIKE CONCAT('%','INR') ESCAPE '\\' )" };
  
  /** The Constant EXPRESSION_SUBSTRING_OF. */
  private static final String[] EXPRESSION_SUBSTRING_OF = {
      "substringof(id,'123') ne true",
      "((CASE WHEN ('123' LIKE CONCAT('%',CONCAT(E1.id,'%')) ESCAPE '\\') THEN TRUE ELSE FALSE END) <> true)" };
  
  /** The Constant EXPRESSION_STARTS_WITH_WRONG_OP. */
  private static final String[] EXPRESSION_STARTS_WITH_WRONG_OP = { "startswith(oValue/Currency,'INR') lt true", "" };
  
  /** The Constant EXPRESSION_SUBSTRING_ALL_OP. */
  private static final String[] EXPRESSION_SUBSTRING_ALL_OP = { "substring(oValue/Currency,1,3) eq 'INR'",
      "(SUBSTRING(E1.oValue.Currency, 1 + 1 , 3) LIKE 'INR' ESCAPE '\\')" };
  
  /** The Constant EXPRESSION_SUBSTRINGOF_INJECTION1. */
  private static final String[] EXPRESSION_SUBSTRINGOF_INJECTION1 = {
      "substringof('a'' OR 1=1 OR E1.id LIKE ''b',id) eq true",
      "((CASE WHEN (E1.id LIKE CONCAT('%',CONCAT('a' OR 1=1 OR E1.id LIKE 'b','%')) ESCAPE '\\') "
          + "THEN TRUE ELSE FALSE END) = true)" };
  
  /** The Constant EXPRESSION_SUBSTRINGOF_INJECTION2. */
  private static final String[] EXPRESSION_SUBSTRINGOF_INJECTION2 =
  {
      "substringof('substringof(''a'' OR 1=1 OR E1.id LIKE ''b'',id)',id) eq true",
      "((CASE WHEN (E1.id LIKE CONCAT('%',CONCAT('substringof('a' OR 1=1 OR E1.id LIKE 'b',id)','%')) ESCAPE '\\') "
          + "THEN TRUE ELSE FALSE END) = true)" };
  
  /** The Constant EXPRESSION_SUBSTRINGOF_INJECTION3. */
  private static final String[] EXPRESSION_SUBSTRINGOF_INJECTION3 =
  {
      "substringof( substring(' ) OR execute_my_sql OR '' LIKE ',3),'de''') eq true",
      "((CASE WHEN ('de'' LIKE CONCAT('%',CONCAT(SUBSTRING(' ) OR execute_my_sql OR ' LIKE ', 3 + 1 ),'%')"
      + ") ESCAPE '\\') "
          + "THEN TRUE ELSE FALSE END) = true)" };
  
  /** The Constant EXPRESSION_ENDSWITH_INJECTION1. */
  private static final String[] EXPRESSION_ENDSWITH_INJECTION1 = { "endswith(id,'Str''eet') eq true",
      "(E1.id LIKE CONCAT('%','Str'eet') ESCAPE '\\' )" };
  
  /** The Constant EXPRESSION_PRECEDENCE. */
  private static final String[] EXPRESSION_PRECEDENCE = {
      "id eq '123' and id ne '123' or (id eq '123' and id ne '123')",
      "(((E1.id LIKE '123' ESCAPE '\\') AND (E1.id NOT LIKE '123' ESCAPE '\\')) OR ((E1.id LIKE '123' ESCAPE '\\') "
      + "AND (E1.id NOT LIKE '123' ESCAPE '\\')))" };
  
  /** The Constant EXPRESSION_DATETIME. */
  private static final String[] EXPRESSION_DATETIME = { "date eq datetime'2000-01-01T00:00:00'",
      "(E1.date = 2000-01-01 00:00:00.000)" };
  
  /** The Constant EXPRESSION_NULL. */
  private static final String[] EXPRESSION_NULL = { "date eq null", "(E1.date IS null)" };

  /** The Constant EXPRESSION_NOT_NULL. */
  private static final String[] EXPRESSION_NOT_NULL = { "date ne null", "(E1.date IS NOT null)" };
  
  /** The Constant EXPRESSION_STARTSWITH_EQBINARY. */
  private static final String[] EXPRESSION_STARTSWITH_EQBINARY = { "startswith(id,'123') and text eq 'abc'", 
      "(E1.id LIKE CONCAT('123','%') ESCAPE '\\' AND (E1.text LIKE 'abc' ESCAPE '\\'))" };
  
  /** The Constant EXPRESSION_STARTSWITHEQ_EQBINARY. */
  private static final String[] EXPRESSION_STARTSWITHEQ_EQBINARY = { "startswith(id,'123') eq true and text eq 'abc'", 
  "((E1.id LIKE CONCAT('123','%') ESCAPE '\\' ) AND (E1.text LIKE 'abc' ESCAPE '\\'))" };
  
  /** The Constant EXPRESSION_EQBINARY_STARTSWITH. */
  private static final String[] EXPRESSION_EQBINARY_STARTSWITH = { "text eq 'abc' and startswith(id,'123')", 
      "((E1.text LIKE 'abc' ESCAPE '\\') AND E1.id LIKE CONCAT('123','%') ESCAPE '\\')" };
  
  /** The Constant EXPRESSION_EQBINARY_STARTSWITHEQ. */
  private static final String[] EXPRESSION_EQBINARY_STARTSWITHEQ = { "text eq 'abc' and startswith(id,'123') eq true", 
  "((E1.text LIKE 'abc' ESCAPE '\\') AND (E1.id LIKE CONCAT('123','%') ESCAPE '\\' ))" };

  /** The Constant EXPRESSION_STARTSWITH_STARTSWITH. */
  private static final String[] EXPRESSION_STARTSWITH_STARTSWITH = { "startswith(text,'abc') and startswith(id,'123')", 
  "(E1.text LIKE CONCAT('abc','%') ESCAPE '\\' AND E1.id LIKE CONCAT('123','%') ESCAPE '\\')" };
  
  /** The Constant EXPRESSION_STARTSWITHEQ_STARTSWITHEQ. */
  private static final String[] EXPRESSION_STARTSWITHEQ_STARTSWITHEQ = { 
      "startswith(text,'abc') eq true and startswith(id,'123') eq true", 
  "((E1.text LIKE CONCAT('abc','%') ESCAPE '\\' ) AND (E1.id LIKE CONCAT('123','%') ESCAPE '\\' ))" };
  
  /** The Constant EXPRESSION_STARTSWITH_ANDTRUE. */
  private static final String[] EXPRESSION_STARTSWITH_ANDTRUE = {"startswith(text,'abc') and true", 
      "(E1.text LIKE CONCAT('abc','%') ESCAPE '\\' AND true)"};
  
  /** The Constant EXPRESSION_STARTSWITHEQTRUE_ANDTRUE. */
  private static final String[] EXPRESSION_STARTSWITHEQTRUE_ANDTRUE = {"startswith(text,'abc') eq true and true", 
      "((E1.text LIKE CONCAT('abc','%') ESCAPE '\\' ) AND true)"};

  /** The Constant EXPRESSION_NULL_EQ. */
  private static final String[] EXPRESSION_NULL_EQ = { "id eq null", "(E1.id IS null)" };
  
  /** The Constant EXPRESSION_GUID_EQ. */
  private static final String[] EXPRESSION_GUID_EQ = { 
      "ExternalRecommendationUUID eq guid'56fe79b1-1c88-465b-b309-33bf8b8f6800'", 
      "(E1.ExternalRecommendationUUID = 56fe79b1-1c88-465b-b309-33bf8b8f6800)" };
	  
  /** The edm. */
  private static Edm edm = null;

  /**
   * Setup.
   */
  @BeforeClass
  public static void setup() {
    InputStream metadataStream =
        ODataFilterExpressionParserTest.class.getClassLoader().getResourceAsStream("metadata.xml");
    try {
      edm = EntityProvider.readMetadata(metadataStream, true);
    } catch (EntityProviderException e) {
      fail("Not expected");
    }
  }

  /**
   * Test UUID.
   */
  @Test
  public void testUUID() {
    String whereExpression = parseWhereExpression(EXPRESSION_GUID_EQ[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_GUID_EQ[OUTPUT], whereExpression);
  }
  
  /**
   * Test date time.
   */
  @Test
  public void testDateTime() {
    String whereExpression = parseWhereExpression(EXPRESSION_DATETIME[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_DATETIME[OUTPUT], whereExpression);
  }

  /**
   * Test precedence.
   */
  @Test
  public void testPrecedence() {
    String whereExpression = parseWhereExpression(EXPRESSION_PRECEDENCE[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_PRECEDENCE[OUTPUT], whereExpression);
  }

  /**
   * Test sub string of SQL injection.
   */
  @Test
  public void testSubStringOfSQLInjection() {
    String whereExpression = parseWhereExpression(EXPRESSION_SUBSTRINGOF_INJECTION1[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_SUBSTRINGOF_INJECTION1[OUTPUT], whereExpression);

    whereExpression = parseWhereExpression(EXPRESSION_SUBSTRINGOF_INJECTION2[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_SUBSTRINGOF_INJECTION2[OUTPUT], whereExpression);

    whereExpression = parseWhereExpression(EXPRESSION_SUBSTRINGOF_INJECTION3[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_SUBSTRINGOF_INJECTION3[OUTPUT], whereExpression);
  }

  /**
   * Test ends with SQL injection.
   */
  @Test
  public void testEndsWithSQLInjection() {
    String whereExpression = parseWhereExpression(EXPRESSION_ENDSWITH_INJECTION1[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_ENDSWITH_INJECTION1[OUTPUT], whereExpression);
  }

  /**
   * Test sub string with all operator.
   */
  @Test
  public void testSubStringWithAllOperator() {
    String whereExpression = parseWhereExpression(EXPRESSION_SUBSTRING_ALL_OP[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_SUBSTRING_ALL_OP[OUTPUT], whereExpression);
  }

  /**
   * Test starts with wrong operator.
   */
  @Test
  public void testStartsWithWrongOperator() {
    parseWhereExpression(EXPRESSION_STARTS_WITH_WRONG_OP[INPUT], true);
  }

  /**
   * Test sub string of.
   */
  @Test
  public void testSubStringOf() {
    String whereExpression = parseWhereExpression(EXPRESSION_SUBSTRING_OF[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_SUBSTRING_OF[OUTPUT], whereExpression);
  }

  /**
   * Test starts with equal.
   */
  @Test
  public void testStartsWithEqual() {
    String whereExpression = parseWhereExpression(EXPRESSION_STARTS_WITH_EQUAL[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_STARTS_WITH_EQUAL[OUTPUT], whereExpression);
  }

  /**
   * Test escape characters.
   */
  @Test
  public void testEscapeCharacters() {
    String whereExpression = parseWhereExpression(EXPRESSION_ESCAPE[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_ESCAPE[OUTPUT], whereExpression);
  }

  /**
   * Test not ends with to lower method.
   */
  @Test
  public void testNotEndsWithToLowerMethod() {
    String whereExpression = parseWhereExpression(EXPRESSION_NOT_ENDS_WITH[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_NOT_ENDS_WITH[OUTPUT], whereExpression);
  }

  /**
   * Test nested method.
   */
  @Test
  public void testNestedMethod() {
    String whereExpression = parseWhereExpression(EXPRESSION_NESTED_METHOD[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_NESTED_METHOD[OUTPUT], whereExpression);
  }

  /**
   * Test not starts with.
   */
  @Test
  public void testNotStartsWith() {
    String whereExpression = parseWhereExpression(EXPRESSION_NOT_STARTS_WITH[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_NOT_STARTS_WITH[OUTPUT], whereExpression);
  }

  /**
   * Test starts with.
   */
  @Test
  public void testStartsWith() {
    String whereExpression = parseWhereExpression(EXPRESSION_STARTS_WITH[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_STARTS_WITH[OUTPUT], whereExpression);
  }

  /**
   * Test simple eq relation.
   */
  @Test
  public void testSimpleEqRelation() {
    String whereExpression = parseWhereExpression(EXPRESSION_EQ[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_EQ[OUTPUT], whereExpression);
  
  }
  
  /**
   * Test null eq relation.
   */
  @Test
  public void testNullEqRelation() {
    String whereExpression = parseWhereExpression(EXPRESSION_NULL_EQ[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_NULL_EQ[OUTPUT], whereExpression);
  
  }

  /**
   * Test simple ne relation.
   */
  @Test
  public void testSimpleNeRelation() {
    String whereExpression = parseWhereExpression(EXPRESSION_NE[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_NE[OUTPUT], whereExpression);
  }
  
  /**
   * Test ne special relation.
   */
  @Test
  public void testNeSpecialRelation() {
    String whereExpression = parseWhereExpression(EXPRESSION_NE_SPECIAL[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_NE_SPECIAL[OUTPUT], whereExpression);
  }

  /**
   * Test binary and.
   */
  @Test
  public void testBinaryAnd() {
    String whereExpression = parseWhereExpression(EXPRESSION_BINARY_AND[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_BINARY_AND[OUTPUT], whereExpression);
  }

 /**
  * Test binary or.
  */
 @Test
  public void testBinaryOr() {
    String whereExpression = parseWhereExpression(EXPRESSION_BINARY_OR[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_BINARY_OR[OUTPUT], whereExpression);
  }

  /**
   * Test member or.
   */
  @Test
  public void testMemberOr() {
    String whereExpression = parseWhereExpression(EXPRESSION_MEMBER_OR[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_MEMBER_OR[OUTPUT], whereExpression);
  }

  /**
   * Test null.
   */
  @Test
  public void testNull() {
    String whereExpression = parseWhereExpression(EXPRESSION_NULL[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_NULL[OUTPUT], whereExpression);
  }

  /**
   * Test not null.
   */
  @Test
  public void testNotNull() {
    String whereExpression = parseWhereExpression(EXPRESSION_NOT_NULL[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_NOT_NULL[OUTPUT], whereExpression);
  }
  
  /**
   * Parses the where expression.
   *
   * @param input the input
   * @param isExceptionExpected the is exception expected
   * @return the string
   */
  private String parseWhereExpression(final String input, final boolean isExceptionExpected) {
    FilterExpression expression;
    try {
      expression = UriParser.parseFilter(edm, edm.getEntityType(NAMESPACE, ENTITY_NOTE), input);
      String expressionString = ODataExpressionParser.parseToJPAWhereExpression(expression, TABLE_ALIAS);
      return expressionString;
    } catch (ExpressionParserException e) {
      fail("Not expected");
    } catch (EdmException e) {
      fail("Not expected");
    } catch (ODataMessageException e) {
      fail("Not expected");
    } catch (ODataException e) {
      if (isExceptionExpected) {
        assertTrue(true);
      } else {
        fail("Not expected");
      }
    }
    return "";
  }
  
  /**
   * Test starts with binary eq.
   */
  @Test
  public void testStartsWith_BinaryEq() {
    String whereExpression = parseWhereExpression(
        EXPRESSION_STARTSWITH_EQBINARY[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_STARTSWITH_EQBINARY[OUTPUT], whereExpression);
  }
  
  /**
   * Test binary eq starts with.
   */
  @Test
  public void testBinaryEq_StartsWith() {
    String whereExpression = parseWhereExpression(
        EXPRESSION_EQBINARY_STARTSWITH[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_EQBINARY_STARTSWITH[OUTPUT], whereExpression);
  }
  
  /**
   * Test starts with eq binary eq.
   */
  public void testStartsWithEq_BinaryEq() {
    String whereExpression = parseWhereExpression(
        EXPRESSION_STARTSWITHEQ_EQBINARY[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_STARTSWITHEQ_EQBINARY[OUTPUT], whereExpression);
  }
  
  /**
   * Test binary eq starts with eq.
   */
  @Test
  public void testBinaryEq_StartsWithEq() {
    String whereExpression = parseWhereExpression(
        EXPRESSION_EQBINARY_STARTSWITHEQ[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_EQBINARY_STARTSWITHEQ[OUTPUT], whereExpression);
  }
  
  /**
   * Test starts with starts with.
   */
  @Test
  public void testStartsWith_StartsWith() {
    String whereExpression = parseWhereExpression(
        EXPRESSION_STARTSWITH_STARTSWITH[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_STARTSWITH_STARTSWITH[OUTPUT], whereExpression);
  }
  
  /**
   * Test starts with eq starts with eq.
   */
  @Test
  public void testStartsWithEq_StartsWithEq() {
    String whereExpression = parseWhereExpression(
        EXPRESSION_STARTSWITHEQ_STARTSWITHEQ[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_STARTSWITHEQ_STARTSWITHEQ[OUTPUT], whereExpression);
  }
  
  /**
   * Test starts with eq and true.
   */
  @Test
  public void testStartsWithEq_AndTrue() {
    String whereExpression = parseWhereExpression(
        EXPRESSION_STARTSWITHEQTRUE_ANDTRUE[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_STARTSWITHEQTRUE_ANDTRUE[OUTPUT], whereExpression);
  }
  
  /**
   * Test starts and true.
   */
  @Test
  public void testStarts_AndTrue() {
    String whereExpression = parseWhereExpression(
        EXPRESSION_STARTSWITH_ANDTRUE[INPUT], false);
    whereExpression = replacePositionalParameters(whereExpression);
    assertEquals(EXPRESSION_STARTSWITH_ANDTRUE[OUTPUT], whereExpression);
  }
  
  /**
   * Replace positional parameters.
   *
   * @param whereExpression the where expression
   * @return the string
   */
  private String replacePositionalParameters(String whereExpression) {
    Map<Integer, Object> positionalParameters = ODataExpressionParser.getPositionalParametersThreadLocal();
    for (Entry<Integer, Object> param : positionalParameters.entrySet()) {
      Integer key = param.getKey();
      if (param.getValue() instanceof String) {
        whereExpression = whereExpression.replaceAll("\\?" + String.valueOf(key), "\'" + param.getValue() + "\'");
      } else if (param.getValue() instanceof Timestamp || param.getValue() instanceof Calendar){
        Calendar datetime = (Calendar) param.getValue();
        String year = String.format("%04d", datetime.get(Calendar.YEAR));
        String month = String.format("%02d", datetime.get(Calendar.MONTH) + 1);
        String day = String.format("%02d", datetime.get(Calendar.DAY_OF_MONTH));
        String hour = String.format("%02d", datetime.get(Calendar.HOUR_OF_DAY));
        String min = String.format("%02d", datetime.get(Calendar.MINUTE));
        String sec = String.format("%02d", datetime.get(Calendar.SECOND));
        String value =
            year + JPQLStatement.DELIMITER.HYPHEN + month + JPQLStatement.DELIMITER.HYPHEN + day
                + JPQLStatement.DELIMITER.SPACE + hour + JPQLStatement.DELIMITER.COLON + min
                + JPQLStatement.DELIMITER.COLON + sec + JPQLStatement.KEYWORD.OFFSET;
        whereExpression = whereExpression.replaceAll("\\?" + String.valueOf(key), value);
      } else if(param.getValue() instanceof Byte[]){
        byte[] byteValue = convertToByte((Byte[])param.getValue());
        whereExpression = whereExpression.replaceAll("\\?" + String.valueOf(key), new String(byteValue));
      }else {
        whereExpression = whereExpression.replaceAll("\\?" + String.valueOf(key), param.getValue().toString());
      }
    }
    return whereExpression;
  }
  
  /**
   * Convert to byte.
   *
   * @param value the value
   * @return the byte[]
   */
  private byte[] convertToByte(Byte[] value) {
    int length =  value.length;
    if (length == 0) {
        return new byte[0];
    }
    final byte[] result = new byte[length];
    for (int i = 0; i < length; i++) {
        result[i] = value[i];
    }
    return result;
 }
}
