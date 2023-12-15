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
package org.apache.olingo.odata2.jpa.processor.ref.converter;

import java.util.UUID;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// TODO: Auto-generated Javadoc
/**
 * The Class UUIDConverter.
 */
@Converter(autoApply = true)
public class UUIDConverter implements AttributeConverter<UUID, String> {

  /**
   * Convert to database column.
   *
   * @param uuid the uuid
   * @return the string
   */
  @Override
  public String convertToDatabaseColumn(UUID uuid) {
    return uuid.toString();
  }

  /**
   * Convert to entity attribute.
   *
   * @param string the string
   * @return the uuid
   */
  @Override
  public UUID convertToEntityAttribute(String string) {
    return UUID.fromString(string);
  }

}
