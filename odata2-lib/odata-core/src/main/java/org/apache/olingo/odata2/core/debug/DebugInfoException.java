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
package org.apache.olingo.odata2.core.debug;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;
import org.apache.olingo.odata2.core.exception.MessageService;

// TODO: Auto-generated Javadoc
/**
 * Exception debug information.
 */
public class DebugInfoException implements DebugInfo {

  /** The exception. */
  private final Exception exception;
  
  /** The locale. */
  private final Locale locale;

  /**
   * Instantiates a new debug info exception.
   *
   * @param exception the exception
   * @param locale the locale
   */
  public DebugInfoException(final Exception exception, final Locale locale) {
    this.exception = exception;
    this.locale = locale;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return "Stacktrace";
  }

  /**
   * Append json.
   *
   * @param jsonStreamWriter the json stream writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void appendJson(final JsonStreamWriter jsonStreamWriter) throws IOException {
    jsonStreamWriter.beginObject()
        .name("exceptions")
        .beginArray();
    Throwable throwable = exception;
    while (throwable != null) {
      jsonStreamWriter.beginObject()
          .namedStringValueRaw("class", throwable.getClass().getCanonicalName()).separator()
          .namedStringValue("message", getMessageText(throwable))
          .separator();

      jsonStreamWriter.name("invocation");
      appendJsonStackTraceElement(jsonStreamWriter, throwable.getStackTrace()[0]);

      jsonStreamWriter.endObject();
      throwable = throwable.getCause();
      if (throwable != null) {
        jsonStreamWriter.separator();
      }
    }
    jsonStreamWriter.endArray();
    jsonStreamWriter.separator();

    jsonStreamWriter.name("stacktrace")
        .beginArray();
    boolean first = true;
    for (final StackTraceElement stackTraceElement : exception != null ? exception.getStackTrace() : null) {
      if (!first) {
        jsonStreamWriter.separator();
      }
      first = false;
      appendJsonStackTraceElement(jsonStreamWriter, stackTraceElement);
    }
    jsonStreamWriter.endArray();
    jsonStreamWriter.endObject();
  }

  /**
   * Append json stack trace element.
   *
   * @param jsonStreamWriter the json stream writer
   * @param stackTraceElement the stack trace element
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void appendJsonStackTraceElement(final JsonStreamWriter jsonStreamWriter,
      final StackTraceElement stackTraceElement) throws IOException {
    jsonStreamWriter.beginObject()
        .namedStringValueRaw("class", stackTraceElement.getClassName()).separator()
        .namedStringValueRaw("method", stackTraceElement.getMethodName()).separator()
        .name("line").unquotedValue(Integer.toString(stackTraceElement.getLineNumber()))
        .endObject();
  }

  /**
   * Append html.
   *
   * @param writer the writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void appendHtml(final Writer writer) throws IOException {
    appendException(exception, writer);
    writer.append("<h2>Stacktrace</h2>\n");
    int count = 0;
    for (final StackTraceElement stackTraceElement : exception.getStackTrace()) {
      appendStackTraceElement(stackTraceElement, ++count == 1, count == exception.getStackTrace().length, writer);
    }
  }

  /**
   * Append exception.
   *
   * @param throwable the throwable
   * @param writer the writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void appendException(final Throwable throwable, final Writer writer) throws IOException {
    if (throwable.getCause() != null) {
      appendException(throwable.getCause(), writer);
    }
    final StackTraceElement details = throwable.getStackTrace()[0];
    writer.append("<h2>").append(throwable.getClass().getCanonicalName()).append("</h2>\n")
        .append("<p>")
        .append(ODataDebugResponseWrapper.escapeHtml(getMessageText(throwable)))
        .append("</p>\n");
    appendStackTraceElement(details, true, true, writer);
  }

  /**
   * Append stack trace element.
   *
   * @param stackTraceElement the stack trace element
   * @param isFirst the is first
   * @param isLast the is last
   * @param writer the writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void appendStackTraceElement(final StackTraceElement stackTraceElement,
      final boolean isFirst, final boolean isLast, final Writer writer) throws IOException {
    if (isFirst) {
      writer.append("<table>\n<thead>\n")
          .append("<tr>\n<th class=\"name\">Class</th>\n")
          .append("<th class=\"name\">Method</th>\n")
          .append("<th class=\"value\">Line number in class</th>\n</tr>\n")
          .append("</thead>\n<tbody>\n");
    }
    writer.append("<tr>\n<td class=\"name\">").append(stackTraceElement.getClassName()).append("</td>\n")
        .append("<td class=\"name\">").append(stackTraceElement.getMethodName()).append("</td>\n")
        .append("<td class=\"value\">").append(Integer.toString(stackTraceElement.getLineNumber()))
        .append("</td>\n</tr>\n");
    if (isLast) {
      writer.append("</tbody>\n</table>\n");
    }
  }

  /**
   * Gets the message text.
   *
   * @param throwable the throwable
   * @return the message text
   */
  private String getMessageText(final Throwable throwable) {
    return throwable instanceof ODataMessageException ?
        MessageService.getMessage(locale, ((ODataMessageException) throwable).getMessageReference()).getText() :
        throwable.getLocalizedMessage() == null ? "Exception text missing" : throwable.getLocalizedMessage();
  }
}
