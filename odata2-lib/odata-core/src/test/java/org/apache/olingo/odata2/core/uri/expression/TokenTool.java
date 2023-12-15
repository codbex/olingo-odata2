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
package org.apache.olingo.odata2.core.uri.expression;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.api.uri.expression.ExpressionParserException;
import org.apache.olingo.odata2.core.exception.MessageService;
import org.apache.olingo.odata2.core.exception.MessageService.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class TokenTool.
 */
public class TokenTool {
  
  /** The token. */
  protected Token token;
  
  /** The tokens. */
  protected TokenList tokens = null;
  
  /** The cur exception. */
  private Exception curException;
  
  /** The exception. */
  private Exception exception;
  
  /** The expression. */
  private String expression;
  
  /** The debug. */
  private static boolean debug = false;

  /** The Constant log. */
  private static final Logger log = LoggerFactory.getLogger(ParserTool.class);
  
  /** The Constant DEFAULT_LANGUAGE. */
  private static final Locale DEFAULT_LANGUAGE = new Locale("test", "foo");

  /**
   * Instantiates a new token tool.
   *
   * @param expression the expression
   * @param wsp the wsp
   */
  public TokenTool(final String expression, final boolean wsp) {
    dout("TokenTool - Testing: " + expression);
    this.expression = expression;

    Tokenizer tokenizer = new Tokenizer(expression).setFlagWhiteSpace(wsp);
    try {
      tokens = tokenizer.tokenize();
      at(0);
    } catch (TokenizerException e) {
      exception = e;
    } catch (ExpressionParserException e) {
      exception = e;
    }

    curException = exception;
  }

  /**
   * Set the token to be check to the token at position <code>index</code>.
   *
   * @param index Index of the token to be checked
   * @return Returns <code>this</code>
   */
  public TokenTool at(final int index) {
    token = tokens.elementAt(index);
    return this;
  }

  /**
   * Checks that the Type of the token matches the <code>kind</code>.
   *
   * @param kind Kind to be compared with the token type
   * @return Returns <code>this</code>
   */
  public TokenTool aKind(final TokenKind kind) {
    assertEquals(kind, token.getKind());
    return this;
  }

  /**
   * Checks that the EDM Type of the token matches the <code>edmType</code>.
   *
   * @param edmType EDM Type to be compared with the token type
   * @return Returns <code>this</code>
   */
  public TokenTool aEdmType(final int edmType) {
    assertEquals(edmType, token.getEdmType());
    return this;
  }

  /**
   * Checks that the Value of the token matches the <code>stringValue</code>.
   *
   * @param stringValue Value to be compared with the token value
   * @return Returns <code>this</code>
   */
  public TokenTool aUriLiteral(final String stringValue) {
    assertEquals(stringValue, token.getUriLiteral());
    return this;
  }

  /**
   * A position.
   *
   * @param position the position
   * @return the token tool
   */
  public TokenTool aPosition(final int position) {
    assertEquals(position, token.getPosition());
    return this;
  }

  /**
   * Dout.
   *
   * @param out the out
   */
  public static void dout(final String out) {
    if (debug) {
      log.debug(out);
    }
  }

  /**
   * Out.
   *
   * @param out the out
   */
  public static void out(final String out) {
    log.debug(out);
  }

  /**
   * Verifies that all place holders in the message text definition of the thrown exception are provided with content.
   *
   * @return TokenTool
   */
  public TokenTool aExMsgContentAllSet() {
    String info = "aExMessageTextNoEmptyTag(" + expression + ")-->";
    if (curException == null) {
      fail("Error in aExMessageText: Expected exception.");
    }

    ODataMessageException messageException;

    try {
      messageException = (ODataMessageException) curException;
    } catch (ClassCastException ex) {
      fail("Error in aExNext: curException not an ODataMessageException");
      return this;
    }

    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, messageException.getMessageReference());
    info = "  " + info + "Messagetext: '" + ms.getText() + "contains [%";

    dout(info);

    if (ms.getText().contains("[%")) {
      fail(info);
    }
    return this;
  }

  /**
   * Verifies that the message text of the thrown exception is not empty.
   *
   * @return TokenTool
   */
  public TokenTool aExMsgNotEmpty() {
    String info = "aExTextNotEmpty(" + expression + ")-->";
    if (curException == null) {
      fail("Error in aExMessageText: Expected exception.");
    }

    ODataMessageException messageException;

    try {
      messageException = (ODataMessageException) curException;
    } catch (ClassCastException ex) {
      fail("Error in aExNext: curException not an ODataMessageException");
      return this;
    }

    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, messageException.getMessageReference());
    info = "  " + info + "check if Messagetext is empty";
    dout(info);

    if (ms.getText().length() == 0) {
      fail(info);
    }
    return this;
  }

  /**
   * A ex key.
   *
   * @param expressionExpectedAtPos the expression expected at pos
   * @return the token tool
   */
  public TokenTool aExKey(final MessageReference expressionExpectedAtPos) {
    String expectedKey = expressionExpectedAtPos.getKey();
    ODataMessageException messageException;

    String info = "GetExceptionType(" + expression + ")-->";

    if (curException == null) {
      fail("Error in aExType: Expected exception");
    }

    try {
      messageException = (ODataMessageException) curException;
    } catch (ClassCastException ex) {
      fail("Error in aExNext: curException not an ODataMessageException");
      return this;
    }

    String actualKey = messageException.getMessageReference().getKey();
    dout("  " + info + "Expected key: " + expectedKey + " Actual: " + actualKey);

    if (expectedKey != actualKey) {
      fail("  " + info + "Expected: " + expectedKey + " Actual: " + actualKey);
    }
    return this;
  }

  /**
   * Prints the ex message.
   *
   * @return the token tool
   */
  public TokenTool printExMessage() {
    ODataMessageException messageException;

    if (curException == null) {
      fail("Error in aExMsgPrint: Expected exception");
    }

    try {
      messageException = (ODataMessageException) curException;
    } catch (ClassCastException ex) {
      fail("Error in aExNext: curException not an ODataMessageException");
      return this;
    }

    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, messageException.getMessageReference());
    out("Messge --> ");
    out("  " + ms.getText());
    out("Messge <-- ");

    return this;
  }

  /**
   * Verifies that the message text of the thrown exception serialized is {@paramref messageText}.
   *
   * @param messageText Expected message text
   * @return this
   */
  public TokenTool aExMsgText(final String messageText) {
    String info = "aExMessageText(" + expression + ")-->";
    if (curException == null) {
      fail("Error in aExMessageText: Expected exception.");
    }

    ODataMessageException messageException;

    try {
      messageException = (ODataMessageException) curException;
    } catch (ClassCastException ex) {
      fail("Error in aExNext: curException not an ODataMessageException");
      return this;
    }

    Message ms = MessageService.getMessage(DEFAULT_LANGUAGE, messageException.getMessageReference());
    info = "  " + info + "Expected: '" + messageText + "' Actual: '" + ms.getText() + "'";

    dout(info);
    assertEquals(info, messageText, ms.getText());

    return this;
  }
}
