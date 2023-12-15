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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.olingo.odata2.api.processor.ODataContext.RuntimeMeasurement;
import org.apache.olingo.odata2.core.ep.util.JsonStreamWriter;

// TODO: Auto-generated Javadoc
/**
 * Runtime debug information.
 */
public class DebugInfoRuntime implements DebugInfo {

  /**
   * The Class RuntimeNode.
   */
  private class RuntimeNode {
    
    /** The class name. */
    protected String className;
    
    /** The method name. */
    protected String methodName;
    
    /** The time started. */
    protected long timeStarted;
    
    /** The time stopped. */
    protected long timeStopped;
    
    /** The children. */
    protected List<RuntimeNode> children = new ArrayList<RuntimeNode>();
    
    /** The memory started. */
    protected long memoryStarted;
    
    /** The memory stopped. */
    protected long memoryStopped;

    /**
     * Instantiates a new runtime node.
     */
    protected RuntimeNode() {
      timeStarted = 0;
      timeStopped = Long.MAX_VALUE;
      memoryStarted = 0;
      memoryStopped = 0;
    }

    /**
     * Instantiates a new runtime node.
     *
     * @param runtimeMeasurement the runtime measurement
     */
    private RuntimeNode(final RuntimeMeasurement runtimeMeasurement) {
      className = runtimeMeasurement.getClassName();
      methodName = runtimeMeasurement.getMethodName();
      timeStarted = runtimeMeasurement.getTimeStarted();
      timeStopped = runtimeMeasurement.getTimeStopped();
      memoryStarted = runtimeMeasurement.getMemoryStarted();
      memoryStopped = runtimeMeasurement.getMemoryStopped();
    }

    /**
     * Adds the.
     *
     * @param runtimeMeasurement the runtime measurement
     * @return true, if successful
     */
    protected boolean add(final RuntimeMeasurement runtimeMeasurement) {
      if (timeStarted <= runtimeMeasurement.getTimeStarted()
          && timeStopped != 0 && timeStopped >= runtimeMeasurement.getTimeStopped()) {
        for (RuntimeNode candidate : children) {
          if (candidate.add(runtimeMeasurement)) {
            return true;
          }
        }
        children.add(new RuntimeNode(runtimeMeasurement));
        return true;
      } else {
        return false;
      }
    }

    /**
     * Combines runtime measurements with identical class names and method
     * names into one measurement, assuming that they originate from a loop
     * or a similar construct where a summary measurement has been intended.
     */
    protected void combineRuntimeMeasurements() {
      RuntimeNode preceding = null;
      for (Iterator<RuntimeNode> iterator = children.iterator(); iterator.hasNext();) {
        final RuntimeNode child = iterator.next();
        if (preceding != null
            && preceding.timeStopped != 0 && child.timeStopped != 0
            && preceding.timeStopped <= child.timeStarted
            && preceding.children.isEmpty() && child.children.isEmpty()
            && preceding.methodName.equals(child.methodName)
            && preceding.className.equals(child.className)) {
          preceding.timeStarted = child.timeStarted - (preceding.timeStopped - preceding.timeStarted);
          preceding.timeStopped = child.timeStopped;

          preceding.memoryStarted = child.memoryStarted - (preceding.memoryStopped - preceding.memoryStarted);
          preceding.memoryStopped = child.memoryStopped;

          iterator.remove();
        } else {
          preceding = child;
          child.combineRuntimeMeasurements();
        }
      }
    }
  }

  /** The root node. */
  private final RuntimeNode rootNode;

  /**
   * Instantiates a new debug info runtime.
   *
   * @param runtimeMeasurements the runtime measurements
   */
  public DebugInfoRuntime(final List<RuntimeMeasurement> runtimeMeasurements) {
    rootNode = new RuntimeNode();
    for (final RuntimeMeasurement runtimeMeasurement : runtimeMeasurements) {
      rootNode.add(runtimeMeasurement);
    }
    rootNode.combineRuntimeMeasurements();
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return "Runtime";
  }

  /**
   * Append json.
   *
   * @param jsonStreamWriter the json stream writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void appendJson(final JsonStreamWriter jsonStreamWriter) throws IOException {
    appendJsonChildren(jsonStreamWriter, rootNode);
  }

  /**
   * Append json node.
   *
   * @param jsonStreamWriter the json stream writer
   * @param node the node
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void appendJsonNode(final JsonStreamWriter jsonStreamWriter, final RuntimeNode node)
      throws IOException {
    jsonStreamWriter.beginObject()
        .namedStringValueRaw("class", node.className).separator()
        .namedStringValueRaw("method", node.methodName).separator()
        .name("duration");
    if (node.timeStopped == 0) {
      jsonStreamWriter.unquotedValue(null);
    } else {
      jsonStreamWriter.beginObject()
          .name("value").unquotedValue(Long.toString((node.timeStopped - node.timeStarted) / 1000)).separator()
          .namedStringValueRaw("unit", "µs")
          .endObject();
    }
    if (!node.children.isEmpty()) {
      jsonStreamWriter.separator()
          .name("children");
      appendJsonChildren(jsonStreamWriter, node);
    }
    jsonStreamWriter.endObject();
  }

  /**
   * Append json children.
   *
   * @param jsonStreamWriter the json stream writer
   * @param node the node
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private static void appendJsonChildren(final JsonStreamWriter jsonStreamWriter, final RuntimeNode node)
      throws IOException {
    jsonStreamWriter.beginArray();
    boolean first = true;
    for (final RuntimeNode childNode : node.children) {
      if (!first) {
        jsonStreamWriter.separator();
      }
      first = false;
      appendJsonNode(jsonStreamWriter, childNode);
    }
    jsonStreamWriter.endArray();
  }

  /**
   * Append html.
   *
   * @param writer the writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void appendHtml(final Writer writer) throws IOException {
    appendRuntimeNode(rootNode, "", true, writer);
  }

  /**
   * Append runtime node.
   *
   * @param node the node
   * @param draw the draw
   * @param isLast the is last
   * @param writer the writer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void appendRuntimeNode(final RuntimeNode node, final String draw, final boolean isLast, final Writer writer)
      throws IOException {
    if (node.className != null) {
      writer.append("<li>")
          .append("<span class=\"code\">")
          .append("<span class=\"draw\">").append(draw)
          .append(isLast ? "&#x2514;" : "&#x251C;").append("&#x2500;&nbsp;</span>")
          .append("<span class=\"class\">").append(node.className).append("</span>.")
          .append("<span class=\"method\">").append(node.methodName).append("(&hellip;)")
          .append("</span></span>");
      long time = node.timeStopped == 0 ? 0 : (node.timeStopped - node.timeStarted) / 1000;
      writer.append("<span class=\"").append(time == 0 ? "null" : "numeric")
          .append("\" title=\"").append(time == 0 ? "Stop time missing" : "Gross duration")
          .append("\">").append(time == 0 ? "unfinished" : Long.toString(time) + "&nbsp;&micro;s")
          .append("</span>\n");
    }
    if (!node.children.isEmpty()) {
      writer.append("<ol class=\"tree\">\n");
      for (final RuntimeNode childNode : node.children) {
        appendRuntimeNode(childNode,
            node.className == null ? draw : draw + (isLast ? "&nbsp;" : "&#x2502;") + "&nbsp;&nbsp;",
            node.children.indexOf(childNode) == node.children.size() - 1,
            writer);
      }
      writer.append("</ol>\n");
    }
    if (node.className != null) {
      writer.append("</li>\n");
    }
  }
}
