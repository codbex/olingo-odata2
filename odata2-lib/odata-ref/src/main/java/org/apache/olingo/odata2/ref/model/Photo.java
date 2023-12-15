/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.apache.olingo.odata2.ref.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class Photo.
 */
public class Photo {
    
    /** The Constant RESOURCE. */
    private static final String RESOURCE = "/Employee_1.png";
    
    /** The default image. */
    private static byte[] defaultImage;

    /** The id. */
    private final int id;
    
    /** The name. */
    private String name;
    
    /** The type. */
    private String type = "image/jpeg";
    
    /** The image url. */
    private String imageUrl = "http://localhost" + RESOURCE;
    
    /** The image. */
    private byte[] image = defaultImage;
    
    /** The image type. */
    private String imageType = type;
    
    /** The binary data. */
    private byte[] binaryData;
    
    /** The content. */
    private String content;

    /**
     * Instantiates a new photo.
     *
     * @param id the id
     * @param name the name
     * @param type the type
     */
    public Photo(final int id, final String name, final String type) {
        this.id = id;
        setName(name);
        setType(type);
    }

    static {
        try (InputStream instream = Photo.class.getResourceAsStream(RESOURCE)) {
            if (null == instream) {
                throw new IllegalStateException("Missing resource " + RESOURCE);
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            int b = 0;
            while ((b = instream.read()) != -1) {
                stream.write(b);
            }

            Photo.defaultImage = stream.toByteArray();
        } catch (IOException e) {
            throw new ModelException(e);
        }
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

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
        return imageType;
    }

    /**
     * Sets the image type.
     *
     * @param imageType the new image type
     */
    public void setImageType(final String imageType) {
        this.imageType = imageType;
    }

    /**
     * Gets the binary data.
     *
     * @return the binary data
     */
    public byte[] getBinaryData() {
        if (binaryData == null) {
            return null;
        }
        return binaryData.clone();
    }

    /**
     * Sets the binary data.
     *
     * @param binaryData the new binary data
     */
    public void setBinaryData(final byte[] binaryData) {
        this.binaryData = binaryData;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(final String content) {
        this.content = content;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(final Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && id == ((Photo) obj).id;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "{\"Id\":" + id + "," + "\"Name\":\"" + name + "\"," + "\"Type\":\"" + type + "\"," + "\"ImageUrl\":\"" + imageUrl + "\","
                + "\"Image\":\"" + Arrays.toString(image) + "\"," + "\"ImageType\":\"" + imageType + "\"," + "\"Content:\"" + content
                + "\"," + "\"BinaryData\":\"" + Arrays.toString(binaryData) + "\"}";
    }
}
