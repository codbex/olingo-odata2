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

import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;

// TODO: Auto-generated Javadoc
/**
 * Debug information.
 */
public interface DebugInfo {

  /**
   * Gets the name of this debug information part, useful as title.
   * @return the name
   */
  public String getName();

  /**
   * Appends the content of this debug information part
   * to the given JSON stream writer.
   *
   * @param jsonStreamWriter a JSON stream writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void appendJson(JsonStreamWriter jsonStreamWriter) throws IOException;

  /**
   * Appends the content of this debug information part to the given writer.
   *
   * @param writer a {@link Writer}
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void appendHtml(Writer writer) throws IOException;
}
