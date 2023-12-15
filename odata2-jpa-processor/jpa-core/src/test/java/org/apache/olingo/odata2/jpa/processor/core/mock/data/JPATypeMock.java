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
package org.apache.olingo.odata2.jpa.processor.core.mock.data;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class JPATypeMock.
 */
/* ========================================================================= */
public class JPATypeMock {

  /**
   * The Enum JPATypeMockEnum.
   */
  enum JPATypeMockEnum {/** The value. */
VALUE, /** The more value. */
 MORE_VALUE}

  /** The Constant ENTITY_NAME. */
  public static final String ENTITY_NAME = "JPATypeMock";
  
  /** The Constant PROPERTY_NAME_MINT. */
  public static final String PROPERTY_NAME_MINT = "mInt";
  
  /** The Constant PROPERTY_NAME_MSTRING. */
  public static final String PROPERTY_NAME_MSTRING = "mString";
  
  /** The Constant PROPERTY_NAME_MDATETIME. */
  public static final String PROPERTY_NAME_MDATETIME = "mDateTime";
  
  /** The Constant PROPERTY_NAME_MBLOB. */
  public static final String PROPERTY_NAME_MBLOB = "mBlob";
  
  /** The Constant PROPERTY_NAME_CLOB. */
  public static final String PROPERTY_NAME_CLOB = "mClob";
  
  /** The Constant PROPERTY_NAME_MCHAR. */
  public static final String PROPERTY_NAME_MCHAR = "mChar";
  
  /** The Constant PROPERTY_NAME_MCHARARRAY. */
  public static final String PROPERTY_NAME_MCHARARRAY = "mCharArray";
  
  /** The Constant PROPERTY_NAME_MC. */
  public static final String PROPERTY_NAME_MC = "mC";
  
  /** The Constant PROPERTY_NAME_MCARRAY. */
  public static final String PROPERTY_NAME_MCARRAY = "mCArray";
  
  /** The Constant PROPERTY_NAME_MKEY. */
  public static final String PROPERTY_NAME_MKEY = "key";
  
  /** The Constant PROPERTY_NAME_MCOMPLEXTYPE. */
  public static final String PROPERTY_NAME_MCOMPLEXTYPE = "complexType";
  
  /** The Constant PROPERTY_NAME_ENUM. */
  public static final String PROPERTY_NAME_ENUM = "mSomeEnum";
  
  /** The Constant PROPERTY_NAME_XMLADAPTER. */
  public static final String PROPERTY_NAME_XMLADAPTER = "propertyWithXmlAdapter";

  /** The Constant NAVIGATION_PROPERTY_X. */
  public static final String NAVIGATION_PROPERTY_X = "mRelatedEntity";
  
  /** The Constant NAVIGATION_PROPERTY_XS. */
  public static final String NAVIGATION_PROPERTY_XS = "mRelatedEntities";

  /** The key. */
  private JPATypeEmbeddableMock key;
  
  /** The complex type. */
  private JPATypeEmbeddableMock complexType;
  
  /** The m int. */
  private int mInt;
  
  /** The m string. */
  private String mString;
  
  /** The m date time. */
  private Calendar mDateTime;
  
  /** The m blob. */
  private Blob mBlob;
  
  /** The m clob. */
  private Clob mClob;
  
  /** The m C. */
  private char mC;
  
  /** The m C array. */
  private char[] mCArray;
  
  /** The m char. */
  private Character mChar;
  
  /** The m char array. */
  private Character[] mCharArray;
  
  /** The m some enum. */
  private JPATypeMockEnum mSomeEnum;
  
  /** The property with xml adapter. */
  private EntityWithXmlAdapterOnProperty propertyWithXmlAdapter;

  /**
   * Gets the m clob.
   *
   * @return the m clob
   */
  public Clob getMClob() {
    return mClob;
  }

  /**
   * Sets the m clob.
   *
   * @param mClob the new m clob
   */
  public void setMClob(final Clob mClob) {
    this.mClob = mClob;
  }

  /**
   * Gets the mc.
   *
   * @return the mc
   */
  public char getMC() {
    return mC;
  }

  /**
   * Sets the mc.
   *
   * @param mC the new mc
   */
  public void setMC(final char mC) {
    this.mC = mC;
  }

  /**
   * Gets the MC array.
   *
   * @return the MC array
   */
  public char[] getMCArray() {
    return mCArray;
  }

  /**
   * Sets the MC array.
   *
   * @param mCArray the new MC array
   */
  public void setMCArray(final char[] mCArray) {
    this.mCArray = mCArray;
  }

  /**
   * Gets the m char.
   *
   * @return the m char
   */
  public Character getMChar() {
    return mChar;
  }

  /**
   * Sets the m char.
   *
   * @param mChar the new m char
   */
  public void setMChar(final Character mChar) {
    this.mChar = mChar;
  }

  /**
   * Gets the m char array.
   *
   * @return the m char array
   */
  public Character[] getMCharArray() {
    return mCharArray;
  }

  /**
   * Sets the m char array.
   *
   * @param mCharArray the new m char array
   */
  public void setMCharArray(final Character[] mCharArray) {
    this.mCharArray = mCharArray;
  }
  
  /** The m related entity. */
  private JPARelatedTypeMock mRelatedEntity;
  
  /** The m related entities. */
  private List<JPARelatedTypeMock> mRelatedEntities = new ArrayList<JPATypeMock.JPARelatedTypeMock>();

  /**
   * Gets the m string.
   *
   * @return the m string
   */
  public String getMString() {
    return mString;
  }

  /**
   * Sets the m string.
   *
   * @param mString the new m string
   */
  public void setMString(final String mString) {
    this.mString = mString;
  }

  /**
   * Gets the key.
   *
   * @return the key
   */
  public JPATypeEmbeddableMock getKey() {
    return key;
  }

  /**
   * Sets the key.
   *
   * @param key the new key
   */
  public void setKey(final JPATypeEmbeddableMock key) {
    this.key = key;
  }

  /**
   * Gets the m int.
   *
   * @return the m int
   */
  public int getMInt() {
    return mInt;
  }

  /**
   * Sets the m int.
   *
   * @param mInt the new m int
   */
  public void setMInt(final int mInt) {
    this.mInt = mInt;
  }

  /**
   * Gets the m date time.
   *
   * @return the m date time
   */
  public Calendar getMDateTime() {
    return mDateTime;
  }

  /**
   * Sets the m date time.
   *
   * @param mDateTime the new m date time
   */
  public void setMDateTime(final Calendar mDateTime) {
    this.mDateTime = mDateTime;
  }

  /**
   * Gets the m related entity.
   *
   * @return the m related entity
   */
  public JPARelatedTypeMock getMRelatedEntity() {
    return mRelatedEntity;
  }

  /**
   * Sets the m related entity.
   *
   * @param mRelatedEntity the new m related entity
   */
  public void setMRelatedEntity(final JPARelatedTypeMock mRelatedEntity) {
    this.mRelatedEntity = mRelatedEntity;
  }

  /**
   * Gets the m related entities.
   *
   * @return the m related entities
   */
  public List<JPARelatedTypeMock> getMRelatedEntities() {
    return mRelatedEntities;
  }

  /**
   * Sets the m related entities.
   *
   * @param mRelatedEntities the new m related entities
   */
  public void setMRelatedEntities(final List<JPARelatedTypeMock> mRelatedEntities) {
    this.mRelatedEntities = mRelatedEntities;
  }

  /**
   * Gets the complex type.
   *
   * @return the complex type
   */
  public JPATypeEmbeddableMock getComplexType() {
    return complexType;
  }

  /**
   * Sets the complex type.
   *
   * @param complexType the new complex type
   */
  public void setComplexType(final JPATypeEmbeddableMock complexType) {
    this.complexType = complexType;
  }

  /**
   * Gets the m blob.
   *
   * @return the m blob
   */
  public Blob getMBlob() {
    return mBlob;
  }

  /**
   * Sets the m blob.
   *
   * @param mBlob the new m blob
   */
  public void setMBlob(final Blob mBlob) {
    this.mBlob = mBlob;
  }

  /**
   * Gets the m some enum.
   *
   * @return the m some enum
   */
  public JPATypeMockEnum getMSomeEnum() {
    return mSomeEnum;
  }

  /**
   * Sets the m some enum.
   *
   * @param mSomeEnum the new m some enum
   */
  public void setMSomeEnum(JPATypeMockEnum mSomeEnum) {
    this.mSomeEnum = mSomeEnum;
  }
  
  /**
   * Gets the property with xml adapter.
   *
   * @return the property with xml adapter
   */
  @XmlJavaTypeAdapter(XmlAdapter.class)
  public EntityWithXmlAdapterOnProperty getPropertyWithXmlAdapter() {
    return propertyWithXmlAdapter;
  }

  /**
   * Sets the property with xml adapter.
   *
   * @param propertyWithXmlAdapter the new property with xml adapter
   */
  public void setPropertyWithXmlAdapter(EntityWithXmlAdapterOnProperty propertyWithXmlAdapter) {
    this.propertyWithXmlAdapter = propertyWithXmlAdapter;
  }

  /**
   * The Class JPATypeEmbeddableMock.
   */
  /* ========================================================================= */
  public static class JPATypeEmbeddableMock {

    /** The Constant ENTITY_NAME. */
    public static final String ENTITY_NAME = "JPATypeEmbeddableMock";
    
    /** The Constant PROPERTY_NAME_MSHORT. */
    public static final String PROPERTY_NAME_MSHORT = "mShort";
    
    /** The Constant PROPERTY_NAME_MEMBEDDABLE. */
    public static final String PROPERTY_NAME_MEMBEDDABLE = "mEmbeddable";
    
    /** The Constant PROPERTY_NAME_MDATE. */
    public static final String PROPERTY_NAME_MDATE = "mDate";
    
    /** The Constant PROPERTY_NAME_MDATE1. */
    public static final String PROPERTY_NAME_MDATE1 = "mDate1";
    
    /** The Constant PROPERTY_NAME_MTIMESTAMP. */
    public static final String PROPERTY_NAME_MTIMESTAMP = "mTimestamp";
    
    /** The Constant PROPERTY_NAME_MTIME. */
    public static final String PROPERTY_NAME_MTIME = "mTime";

    /** The m short. */
    private Short mShort;
    
    /** The m embeddable. */
    private JPATypeEmbeddableMock2 mEmbeddable;
    
    /** The m date. */
    private Date mDate;
    
    /** The m date 1. */
    private java.sql.Date mDate1;
    
    /** The m timestamp. */
    private Timestamp mTimestamp;
    
    /** The m time. */
    private Time mTime;

    /**
     * Gets the m short.
     *
     * @return the m short
     */
    public Short getMShort() {
      return mShort;
    }

    /**
     * Sets the m short.
     *
     * @param mShort the new m short
     */
    public void setMShort(final Short mShort) {
      this.mShort = mShort;
    }

    /**
     * Gets the m embeddable.
     *
     * @return the m embeddable
     */
    public JPATypeEmbeddableMock2 getMEmbeddable() {
      return mEmbeddable;
    }

    /**
     * Sets the m embeddable.
     *
     * @param mEmbeddable the new m embeddable
     */
    public void setMEmbeddable(final JPATypeEmbeddableMock2 mEmbeddable) {
      this.mEmbeddable = mEmbeddable;
    }
    
    /**
     * Gets the m timestamp.
     *
     * @return the m timestamp
     */
    public Timestamp getMTimestamp() {
      return mTimestamp;
    }

    /**
     * Sets the m timestamp.
     *
     * @param mTimestamp the new m timestamp
     */
    public void setMTimestamp(final Timestamp mTimestamp) {
      this.mTimestamp = mTimestamp;
    }

    /**
     * Gets the m time.
     *
     * @return the m time
     */
    public Time getMTime() {
      return mTime;
    }
    
    /**
     * Sets the m time.
     *
     * @param mTime the new m time
     */
    public void setMTime(final Time mTime) {
      this.mTime = mTime;
    }

    /**
     * Sets the m date.
     *
     * @param mDate the new m date
     */
    public void setMDate(final Date mDate) {
      this.mDate = mDate;
    }
    
    /**
     * Gets the m date.
     *
     * @return the m date
     */
    public Date getMDate() {
      return mDate;
    }

    /**
     * Sets the m date 1.
     *
     * @param mDate1 the new m date 1
     */
    public void setMDate1(final java.sql.Date mDate1) {
      this.mDate1 = mDate1;
    }
    
    /**
     * Gets the m date 1.
     *
     * @return the m date 1
     */
    public java.sql.Date getMDate1() {
      return mDate1;
    }

  }

  /**
   * The Class JPATypeEmbeddableMock2.
   */
  /* ========================================================================= */
  public static class JPATypeEmbeddableMock2 {

    /** The Constant ENTITY_NAME. */
    public static final String ENTITY_NAME = "JPATypeEmbeddableMock2";
    
    /** The Constant PROPERTY_NAME_MUUID. */
    public static final String PROPERTY_NAME_MUUID = "mUUID";
    
    /** The Constant PROPERTY_NAME_MFLOAT. */
    public static final String PROPERTY_NAME_MFLOAT = "mFloat";

    /** The m UUID. */
    private UUID mUUID;
    
    /** The m float. */
    private Float mFloat;

    /**
     * Gets the muuid.
     *
     * @return the muuid
     */
    public UUID getMUUID() {
      return mUUID;
    }

    /**
     * Sets the muuid.
     *
     * @param mUUID the new muuid
     */
    public void setMUUID(final UUID mUUID) {
      this.mUUID = mUUID;
    }

    /**
     * Gets the m float.
     *
     * @return the m float
     */
    public Float getMFloat() {
      return mFloat;
    }

    /**
     * Sets the m float.
     *
     * @param mFloat the new m float
     */
    public void setMFloat(final Float mFloat) {
      this.mFloat = mFloat;
    }

  }

  /**
   * The Class JPARelatedTypeMock.
   */
  /* ========================================================================= */
  public static final class JPARelatedTypeMock {
    
    /** The Constant ENTITY_NAME. */
    public static final String ENTITY_NAME = "JPARelatedTypeMock";
    
    /** The Constant PROPERTY_NAME_MLONG. */
    public static final String PROPERTY_NAME_MLONG = "mLong";
    
    /** The Constant PROPERTY_NAME_MDOUBLE. */
    public static final String PROPERTY_NAME_MDOUBLE = "mDouble";
    
    /** The Constant PROPERTY_NAME_MBYTE. */
    public static final String PROPERTY_NAME_MBYTE = "mByte";
    
    /** The Constant PROPERTY_NAME_MBYTEARRAY. */
    public static final String PROPERTY_NAME_MBYTEARRAY = "mByteArray";

    /** The m long. */
    private long mLong;
    
    /** The m double. */
    private double mDouble;
    
    /** The m byte. */
    private byte mByte;
    
    /** The m byte array. */
    private byte mByteArray[];

    /**
     * Gets the m long.
     *
     * @return the m long
     */
    public long getMLong() {
      return mLong;
    }

    /**
     * Sets the m long.
     *
     * @param key the new m long
     */
    public void setMLong(final long key) {
      mLong = key;
    }

    /**
     * Gets the m double.
     *
     * @return the m double
     */
    public double getMDouble() {
      return mDouble;
    }

    /**
     * Sets the m double.
     *
     * @param mDouble the new m double
     */
    public void setMDouble(final double mDouble) {
      this.mDouble = mDouble;
    }

    /**
     * Gets the m byte.
     *
     * @return the m byte
     */
    public byte getMByte() {
      return mByte;
    }

    /**
     * Sets the m byte.
     *
     * @param mByte the new m byte
     */
    public void setMByte(final byte mByte) {
      this.mByte = mByte;
    }

    /**
     * Gets the m byte array.
     *
     * @return the m byte array
     */
    public byte[] getMByteArray() {
      return mByteArray;
    }

    /**
     * Sets the m byte array.
     *
     * @param mByteArray the new m byte array
     */
    public void setMByteArray(final byte mByteArray[]) {
      this.mByteArray = mByteArray;
    }

  }
}
