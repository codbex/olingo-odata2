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
package org.apache.olingo.odata2.api.uri.expression;

import org.apache.olingo.odata2.api.exception.MessageReference;
import org.apache.olingo.odata2.api.exception.ODataMessageException;

// TODO: Auto-generated Javadoc
/**
 * Exception thrown while traversing/visiting a filter expression tree.
 */
public class ExceptionVisitExpression extends ODataMessageException {
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 7701L;

  /** The Constant COMMON. */
  public static final MessageReference COMMON = createMessageReference(ExceptionVisitExpression.class, "COMMON");

  /** The filter tree. */
  private CommonExpression filterTree;

  /**
   * Instantiates a new exception visit expression.
   */
  public ExceptionVisitExpression() {
    super(COMMON);
  }

  /**
   * Create {@link ExceptionVisitExpression} with given {@link MessageReference}.
   * 
   * @param messageReference
   * references the message text (and additional values) of this {@link ExceptionVisitExpression}
   */
  public ExceptionVisitExpression(final MessageReference messageReference) {
    super(messageReference);
  }

  /**
   * Create {@link ExceptionVisitExpression} with given {@link MessageReference} and cause {@link Throwable} which
   * caused
   * this {@link ExceptionVisitExpression}.
   * 
   * @param message
   * references the message text (and additional values) of this {@link ExceptionVisitExpression}
   * @param cause
   * exception which caused this {@link ExceptionVisitExpression}
   */
  public ExceptionVisitExpression(final MessageReference message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Get erroneous filter for debug information.
   *
   * @return Erroneous filter tree
   */
  public CommonExpression getFilterTree() {
    return filterTree;
  }

  /**
   * Sets erroneous filter tree for debug information.
   * @param filterTree Erroneous filter tree
   */
  public void setFilterTree(final CommonExpression filterTree) {
    this.filterTree = filterTree;
  }
}
