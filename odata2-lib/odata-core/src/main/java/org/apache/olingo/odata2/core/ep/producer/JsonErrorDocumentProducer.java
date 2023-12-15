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
package org.apache.olingo.odata2.core.ep.producer;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.core.ep.util.FormatJson;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonErrorDocumentProducer.
 */
public class JsonErrorDocumentProducer {

  /**
   * Write error document.
   *
   * @param writer the writer
   * @param errorCode the error code
   * @param message the message
   * @param locale the locale
   * @param innerError the inner error
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void writeErrorDocument(final Writer writer, final String errorCode, final String message,
      final Locale locale, final String innerError) throws IOException {
      ODataErrorContext context = new ODataErrorContext();
      context.setErrorCode(errorCode);
      context.setMessage(message);
      context.setLocale(locale);
      context.setInnerError(innerError);

      writeErrorDocument(writer, context);
  }

  /**
   * Write error document.
   *
   * @param writer the writer
   * @param context the context
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void writeErrorDocument(final Writer writer, ODataErrorContext context) throws IOException {
    Locale locale = context.getLocale();
    String innerError = context.getInnerError();

    JsonStreamWriter jsonStreamWriter = new JsonStreamWriter(writer);
    jsonStreamWriter
        .beginObject()
        .name(FormatJson.ERROR)
        .beginObject()
        .namedStringValue(FormatJson.CODE, context.getErrorCode())
        .separator()
        .name(FormatJson.MESSAGE)
        .beginObject()
        .namedStringValueRaw(
            FormatJson.LANG,
            locale == null || locale.getLanguage() == null ? null :
                locale.getLanguage()
                    + (locale.getCountry() == null || locale.getCountry().isEmpty() ? "" : ("-" + locale.getCountry())))
        .separator()
        .namedStringValue(FormatJson.VALUE, context.getMessage())
        .endObject();
        if (!context.getErrorDetails().isEmpty()) {
            int cntr = 0;
            jsonStreamWriter.separator().name(FormatJson.INNER_ERROR).beginObject().name(FormatJson.ERROR_DETAILS)
                    .beginArray();
            for (ODataErrorContext detail : context.getErrorDetails()) {
                if (cntr > 0) {
                    jsonStreamWriter.separator();
                }
                cntr++;
                jsonStreamWriter.beginObject()
                .namedStringValue(FormatJson.CODE, detail.getErrorCode()).separator()
                .namedStringValue(FormatJson.MESSAGE, detail.getMessage()).separator()
                .namedStringValue(FormatJson.TARGET, detail.getTarget()).separator()
                .namedStringValue(FormatJson.SEVERITY, detail.getSeverity())
                .endObject();
            }
            jsonStreamWriter.endArray().endObject();
        } else if (innerError != null) {
            jsonStreamWriter.separator().namedStringValue(FormatJson.INNER_ERROR, innerError);
        }
    jsonStreamWriter.endObject()
        .endObject();
  }
}
