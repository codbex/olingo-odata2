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
package org.apache.olingo.odata2.annotation.processor.core.model;

import java.util.Arrays;

import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceContent;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceMimeType;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceSource;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmType;

// TODO: Auto-generated Javadoc
/**
 * The Class Photo.
 */
@EdmEntityType(name = "Photo", namespace = ModelSharedConstants.NAMESPACE_1)
@EdmEntitySet(name = "Photos")
public class Photo {
  
  /** The name. */
  @EdmProperty
  @EdmKey
  private String name;
  
  /** The type. */
  @EdmProperty(name = "ImageFormat")
  @EdmKey
  private String type;
  
  /** The mime type. */
  @EdmProperty
  @EdmMediaResourceMimeType
  private String mimeType;
  
  /** The image url. */
  @EdmProperty
  @EdmMediaResourceSource
  private String imageUrl = "http://localhost/someResource.png";
  
  /** The image. */
  @EdmProperty(type = EdmType.BINARY)
  @EdmMediaResourceContent
  private byte[] image = ResourceHelper.generateImage();

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the type.
   *
   * @param type the new type
   */
  public void setType(final String type) {
    this.type = type;
  }

  /**
   * Gets the image uri.
   *
   * @return the image uri
   */
  public String getImageUri() {
    return imageUrl;
  }

  /**
   * Sets the image uri.
   *
   * @param uri the new image uri
   */
  public void setImageUri(final String uri) {
    imageUrl = uri;
  }

  /**
   * Gets the image.
   *
   * @return the image
   */
  public byte[] getImage() {
    return image.clone();
  }

  /**
   * Sets the image.
   *
   * @param image the new image
   */
  public void setImage(final byte[] image) {
    this.image = image;
  }

  /**
   * Gets the image type.
   *
   * @return the image type
   */
  public String getImageType() {
    return mimeType;
  }

  /**
   * Sets the image type.
   *
   * @param imageType the new image type
   */
  public void setImageType(final String imageType) {
    mimeType = imageType;
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 83 * hash + (name != null ? name.hashCode() : 0);
    hash = 83 * hash + (type != null ? type.hashCode() : 0);
    return hash;
  }

  /**
   * Equals.
   *
   * @param obj the obj
   * @return true, if successful
   */
  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Photo other = (Photo) obj;
    if ((name == null) ? (other.name != null) : !name.equals(other.name)) {
      return false;
    }
    if ((type == null) ? (other.type != null) : !type.equals(other.type)) {
      return false;
    }
    return true;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "{\"Name\":\"" + name + "\","
        + "\"Type\":\"" + type + "\","
        + "\"ImageUrl\":\"" + imageUrl + "\","
        + "\"Image\":\"" + Arrays.toString(image) + "\","
        + "\"MimeType\":\"" + mimeType + "\"";
  }
}
