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
package org.apache.olingo.odata2.api.edm.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmConcurrencyMode;
import org.apache.olingo.odata2.api.edm.EdmFacets;

// TODO: Auto-generated Javadoc
/**
 * Objects of this class represent the facets an entity type, property or function import can have.
 */
public class Facets implements EdmFacets {

  /**
   * Specification default is TRUE but we won't set it here because
   * we want to know if it's set explicitly by an application.
   */
  Boolean nullable;
  
  /** The default value. */
  String defaultValue;
  
  /** The max length. */
  Integer maxLength;
  
  /** The fixed length. */
  Boolean fixedLength;
  
  /** The precision. */
  Integer precision;
  
  /** The scale. */
  Integer scale;
  
  /** The unicode. */
  Boolean unicode;
  
  /** The collation. */
  String collation;
  
  /** The concurrency mode. */
  EdmConcurrencyMode concurrencyMode;

  /**
   * Checks if is nullable.
   *
   * @return the boolean
   */
  @Override
  public Boolean isNullable() {
    return nullable;
  }

  /**
   * Gets the default value.
   *
   * @return the default value
   */
  @Override
  public String getDefaultValue() {
    return defaultValue;
  }

  /**
   * Gets the max length.
   *
   * @return the max length
   */
  @Override
  public Integer getMaxLength() {
    return maxLength;
  }

  /**
   * Checks if is fixed length.
   *
   * @return the boolean
   */
  @Override
  public Boolean isFixedLength() {
    return fixedLength;
  }

  /**
   * Gets the precision.
   *
   * @return the precision
   */
  @Override
  public Integer getPrecision() {
    return precision;
  }

  /**
   * Gets the scale.
   *
   * @return the scale
   */
  @Override
  public Integer getScale() {
    return scale;
  }

  /**
   * Checks if is unicode.
   *
   * @return the boolean
   */
  @Override
  public Boolean isUnicode() {
    return unicode;
  }

  /**
   * Gets the collation.
   *
   * @return the collation
   */
  @Override
  public String getCollation() {
    return collation;
  }

  /**
   * Gets the concurrency mode.
   *
   * @return the concurrency mode
   */
  @Override
  public EdmConcurrencyMode getConcurrencyMode() {
    return concurrencyMode;
  }

  /**
   * Sets if this {@link Facets} is nullable.
   *
   * @param nullable the nullable
   * @return {@link Facets} for method chaining
   */
  public Facets setNullable(final Boolean nullable) {
    this.nullable = nullable;
    return this;
  }

  /**
   * Sets the fixed length of this {@link Facets}.
   *
   * @param fixedLength the fixed length
   * @return {@link Facets} for method chaining
   */
  public Facets setFixedLength(final Boolean fixedLength) {
    this.fixedLength = fixedLength;
    return this;
  }

  /**
   * Sets if this {@link Facets} is in Unicode.
   *
   * @param unicode the unicode
   * @return {@link Facets} for method chaining
   */
  public Facets setUnicode(final Boolean unicode) {
    this.unicode = unicode;
    return this;
  }

  /**
   * Sets the default value of this {@link Facets}.
   *
   * @param defaultValue the default value
   * @return {@link Facets} for method chaining
   */
  public Facets setDefaultValue(final String defaultValue) {
    this.defaultValue = defaultValue;
    return this;
  }

  /**
   * Sets the maximum length of this {@link Facets}.
   *
   * @param maxLength the max length
   * @return {@link Facets} for method chaining
   */
  public Facets setMaxLength(final Integer maxLength) {
    this.maxLength = maxLength;
    return this;
  }

  /**
   * Sets the precision of this {@link Facets}.
   *
   * @param precision the precision
   * @return {@link Facets} for method chaining
   */
  public Facets setPrecision(final Integer precision) {
    this.precision = precision;
    return this;
  }

  /**
   * Sets the scale of this {@link Facets}.
   *
   * @param scale the scale
   * @return {@link Facets} for method chaining
   */
  public Facets setScale(final Integer scale) {
    this.scale = scale;
    return this;
  }

  /**
   * Sets the collation of this {@link Facets}.
   *
   * @param collation the collation
   * @return {@link Facets} for method chaining
   */
  public Facets setCollation(final String collation) {
    this.collation = collation;
    return this;
  }

  /**
   * Sets the {@link EdmConcurrencyMode} of this {@link Facets}.
   *
   * @param concurrencyMode the concurrency mode
   * @return {@link Facets} for method chaining
   */
  public Facets setConcurrencyMode(final EdmConcurrencyMode concurrencyMode) {
    this.concurrencyMode = concurrencyMode;
    return this;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    final List<String> values = new ArrayList<String>();
    if (nullable != null) {
      values.add("Nullable=" + nullable);
    }
    if (defaultValue != null) {
      values.add("DefaultValue=" + defaultValue);
    }
    if (maxLength != null) {
      values.add("MaxLength=" + maxLength);
    }
    if (fixedLength != null) {
      values.add("FixedLength=" + fixedLength);
    }
    if (precision != null) {
      values.add("Precision=" + precision);
    }
    if (scale != null) {
      values.add("Scale=" + scale);
    }
    if (unicode != null) {
      values.add("Unicode=" + unicode);
    }
    if (collation != null) {
      values.add("Collation=" + collation);
    }
    if (concurrencyMode != null) {
      values.add("ConcurrencyMode=" + concurrencyMode);
    }
    return values.toString();
  }
}
