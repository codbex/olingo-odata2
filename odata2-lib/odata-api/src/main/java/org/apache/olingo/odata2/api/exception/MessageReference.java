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
package org.apache.olingo.odata2.api.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * APPLICATION DEVELOPERS: Please use {@link ODataApplicationException} to throw custom exceptions. This class is used
 * inside the library only.
 * <p>A {@link MessageReference} references to the used message for an {@link ODataMessageException} and its sub
 * classes. It supports
 * internationalization and translation of exception messages.
 * <br>Theses classes contain a {@link MessageReference} object which
 * can be mapped to a related key and message text in the resource bundles.
 * 
 */
public abstract class MessageReference {

  /** The key. */
  protected final String key;
  
  /** The content. */
  protected List<Object> content = null;

  /**
   * Instantiates a new message reference.
   *
   * @param key the key
   */
  private MessageReference(final String key) {
    this(key, null);
  }

  /**
   * Instantiates a new message reference.
   *
   * @param key the key
   * @param content the content
   */
  private MessageReference(final String key, final List<Object> content) {
    this.key = key;
    this.content = content;
  }

  /**
   * Creates a {@link MessageReference} for given <code>class</code> and <code>key</code>.
   * This combination of <code>class</code> and <code>key</code> has to be provided
   * by a resource bundle.
   * @param clazz {@link ODataMessageException} for which this {@link MessageReference} should be used
   * @param key unique key (in context of {@link ODataMessageException}) for reference
   * to message text in resource bundle
   * @return created {@link MessageReference}
   */
  public static MessageReference create(final Class<? extends ODataException> clazz, final String key) {
    return new SimpleMessageReference(clazz.getName() + "." + key);
  }

  /**
   * Creates the.
   *
   * @return the message reference
   */
  public MessageReference create() {
    return new SingleMessageReference(key);
  }

  /**
   * Returns message key.
   *
   * @return the key
   */
  public String getKey() {
    return key;
  }

  /**
   * Adds given content to message reference.
   *
   * @param content the content
   * @return the message reference
   */
  public MessageReference addContent(final Object... content) {
    if (this.content == null) {
      return new SimpleMessageReference(key, content);
    } else {
      final List<Object> mergedContent = new ArrayList<Object>(this.content.size() + content.length);
      mergedContent.addAll(this.content);
      mergedContent.addAll(Arrays.asList(content));
      return new SimpleMessageReference(key, mergedContent);
    }
  }

  /**
   * Receives content for this {@link MessageReference}.
   * Beware that returned list is immutable.
   *
   * @return the content
   */
  public List<?> getContent() {
    if (content == null) {
      return Collections.emptyList();
    } else {
      return Collections.unmodifiableList(content);
    }
  }

  /**
   * Simple inner class for realization of {@link MessageReference} interface.
   */
  private static class SimpleMessageReference extends MessageReference {
    
    /**
     * Instantiates a new simple message reference.
     *
     * @param implKey the impl key
     */
    public SimpleMessageReference(final String implKey) {
      super(implKey);
    }

    /**
     * Instantiates a new simple message reference.
     *
     * @param implKey the impl key
     * @param content the content
     */
    public SimpleMessageReference(final String implKey, final List<Object> content) {
      super(implKey, content);
    }

    /**
     * Instantiates a new simple message reference.
     *
     * @param implKey the impl key
     * @param content the content
     */
    public SimpleMessageReference(final String implKey, final Object... content) {
      super(implKey, Arrays.asList(content));
    }
  }

  /**
   * The Class SingleMessageReference.
   */
  private static class SingleMessageReference extends MessageReference {
    
    /**
     * Instantiates a new single message reference.
     *
     * @param implKey the impl key
     */
    public SingleMessageReference(final String implKey) {
      super(implKey);
    }

    /**
     * Instantiates a new single message reference.
     *
     * @param implKey the impl key
     * @param content the content
     */
    public SingleMessageReference(final String implKey, final List<Object> content) {
      super(implKey, content);
    }

    /**
     * Instantiates a new single message reference.
     *
     * @param implKey the impl key
     * @param content the content
     */
    public SingleMessageReference(final String implKey, final Object... content) {
      super(implKey, Arrays.asList(content));
    }

    /**
     * Adds the content.
     *
     * @param content the content
     * @return the message reference
     */
    @Override
    public MessageReference addContent(final Object... content) {

      if (this.content == null) {
        this.content = new ArrayList<Object>();
      }

      this.content.addAll(Arrays.asList(content));
      return this;
    }
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((key == null) ? 0 : key.hashCode());
    return result;
  }

  /**
   * {@link MessageReference}s are equal if their message keys have the same value.
   *
   * @param obj the obj
   * @return <code>true</code> if both instances are equal, otherwise <code>false</code>.
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    MessageReference other = (MessageReference) obj;
    if (key == null) {
      if (other.key != null) {
        return false;
      }
    } else if (!key.equals(other.key)) {
      return false;
    }
    return true;
  }
  
  /**
   * Update content.
   *
   * @param oldContent the old content
   * @param newContent the new content
   * @return the message reference
   */
  public MessageReference updateContent(List<?> oldContent, Object... newContent) {
    
    final List<Object> mergedContent = new ArrayList<Object>();
    if (oldContent != null && !oldContent.isEmpty()) {
      mergedContent.addAll(oldContent); 
    }
    mergedContent.addAll(Arrays.asList(newContent));
    return new SimpleMessageReference(key, mergedContent);
  }
}
