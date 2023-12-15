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
package org.apache.olingo.odata2.jpa.processor.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import jakarta.persistence.metamodel.Attribute;

import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAAttributeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEdmMockData.SimpleType;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEdmMockData.SimpleType.SimpleTypeA;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmAssociationEndTest.
 */
public class JPAEdmAssociationEndTest extends JPAEdmTestModelView {

  /** The Constant VARIANT1. */
  private final static int VARIANT1 = 1;
  
  /** The Constant VARIANT2. */
  private final static int VARIANT2 = 2;
  
  /** The Constant VARIANT3. */
  private final static int VARIANT3 = 3;

  /** The Constant PUNIT_NAME. */
  private static final String PUNIT_NAME = "salesorderprocessing";
  
  /** The obj JPA edm association end. */
  private static JPAEdmAssociationEnd objJPAEdmAssociationEnd = null;

  /**
   * Setup.
   */
  @BeforeClass
  public static void setup() {
    InnerMock objJPAEdmAssociationEndTest = new InnerMock(Attribute.PersistentAttributeType.MANY_TO_MANY);
    objJPAEdmAssociationEnd = new JPAEdmAssociationEnd(objJPAEdmAssociationEndTest, objJPAEdmAssociationEndTest);
    try {
      objJPAEdmAssociationEnd.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Test get builder.
   */
  @Test
  public void testGetBuilder() {
    JPAEdmBuilder builder = objJPAEdmAssociationEnd.getBuilder();
    assertNotNull(builder);

  }

  /**
   * Test get builder idempotent.
   */
  @Test
  public void testGetBuilderIdempotent() {
    JPAEdmBuilder builder1 = objJPAEdmAssociationEnd.getBuilder();
    JPAEdmBuilder builder2 = objJPAEdmAssociationEnd.getBuilder();

    assertEquals(builder1.hashCode(), builder2.hashCode());
  }

  /**
   * Test get association end 1.
   */
  @Test
  public void testGetAssociationEnd1() {
    AssociationEnd associationEnd = objJPAEdmAssociationEnd.getEdmAssociationEnd1();
    assertEquals(associationEnd.getType().getName(), "SOID");
  }

  /**
   * Test get association end 2.
   */
  @Test
  public void testGetAssociationEnd2() {
    AssociationEnd associationEnd = objJPAEdmAssociationEnd.getEdmAssociationEnd2();
    assertEquals(associationEnd.getType().getName(), "String");
  }

  /**
   * Test compare.
   */
  @Test
  public void testCompare() {
    assertTrue(objJPAEdmAssociationEnd.compare(getAssociationEnd("SOID", 1), getAssociationEnd("String", 1)));
    assertFalse(objJPAEdmAssociationEnd.compare(getAssociationEnd("String", 2), getAssociationEnd("SOID", 1)));
  }

  /**
   * Test build association end.
   */
  @Test
  public void testBuildAssociationEnd() {
    assertEquals("SOID", objJPAEdmAssociationEnd.getEdmAssociationEnd1().getType().getName());
    assertEquals(new FullQualifiedName("salesorderprocessing", "SOID"), objJPAEdmAssociationEnd.getEdmAssociationEnd1()
        .getType());
    assertTrue(objJPAEdmAssociationEnd.isConsistent());
  }

  /**
   * Test build association end many to one.
   *
   * @throws Exception the exception
   */
  @Test
  public void testBuildAssociationEndManyToOne() throws Exception {
    InnerMock mockFirst = new InnerMock(Attribute.PersistentAttributeType.ONE_TO_MANY);
    InnerMock mockSecond = new InnerMock(Attribute.PersistentAttributeType.MANY_TO_ONE);
    JPAEdmAssociationEnd associationEnd = new JPAEdmAssociationEnd(mockFirst, mockSecond);
    associationEnd.getBuilder().build();
    assertEquals(EdmMultiplicity.MANY, associationEnd.getEdmAssociationEnd1().getMultiplicity());
    assertEquals(EdmMultiplicity.ONE, associationEnd.getEdmAssociationEnd2().getMultiplicity());
    assertEquals("SOID", associationEnd.getEdmAssociationEnd1().getType().getName());
    assertEquals(new FullQualifiedName("salesorderprocessing", "SOID"), associationEnd.getEdmAssociationEnd1()
        .getType());
    assertTrue(associationEnd.isConsistent());
  }

  /**
   * Test build association end one to many.
   *
   * @throws Exception the exception
   */
  @Test
  public void testBuildAssociationEndOneToMany() throws Exception {
    InnerMock mockFirst = new InnerMock(Attribute.PersistentAttributeType.MANY_TO_ONE);
    InnerMock mockSecond = new InnerMock(Attribute.PersistentAttributeType.ONE_TO_MANY);
    JPAEdmAssociationEnd associationEnd = new JPAEdmAssociationEnd(mockFirst, mockSecond);
    associationEnd.getBuilder().build();
    assertEquals(EdmMultiplicity.ONE, associationEnd.getEdmAssociationEnd1().getMultiplicity());
    assertEquals(EdmMultiplicity.MANY, associationEnd.getEdmAssociationEnd2().getMultiplicity());
    assertEquals("SOID", associationEnd.getEdmAssociationEnd1().getType().getName());
    assertEquals(new FullQualifiedName("salesorderprocessing", "SOID"), associationEnd.getEdmAssociationEnd1()
        .getType());
    assertTrue(associationEnd.isConsistent());
  }

  /**
   * Test build association end one to one.
   *
   * @throws Exception the exception
   */
  @Test
  public void testBuildAssociationEndOneToOne() throws Exception {
    InnerMock mockFirst = new InnerMock(Attribute.PersistentAttributeType.ONE_TO_ONE);
    InnerMock mockSecond = new InnerMock(Attribute.PersistentAttributeType.ONE_TO_ONE);
    JPAEdmAssociationEnd associationEnd = new JPAEdmAssociationEnd(mockFirst, mockSecond);
    associationEnd.getBuilder().build();
    assertEquals(EdmMultiplicity.ONE, associationEnd.getEdmAssociationEnd1().getMultiplicity());
    assertEquals(EdmMultiplicity.ONE, associationEnd.getEdmAssociationEnd2().getMultiplicity());
    assertEquals("SOID", associationEnd.getEdmAssociationEnd1().getType().getName());
    assertEquals(new FullQualifiedName("salesorderprocessing", "SOID"), associationEnd.getEdmAssociationEnd1()
        .getType());
    assertTrue(associationEnd.isConsistent());
  }

  /**
   * Gets the association end.
   *
   * @param typeName the type name
   * @param variant the variant
   * @return the association end
   */
  private AssociationEnd getAssociationEnd(final String typeName, final int variant) {
    AssociationEnd associationEnd = new AssociationEnd();
    associationEnd.setType(getFullQualifiedName(typeName));
    if (variant == VARIANT1) {
      associationEnd.setMultiplicity(EdmMultiplicity.MANY);
    } else if (variant == VARIANT2) {
      associationEnd.setMultiplicity(EdmMultiplicity.ONE);
    } else if (variant == VARIANT3) {
      associationEnd.setMultiplicity(EdmMultiplicity.ZERO_TO_ONE);
    } else {
      associationEnd.setMultiplicity(EdmMultiplicity.MANY);//
    }
    return associationEnd;
  }

  /**
   * Gets the full qualified name.
   *
   * @param typeName the type name
   * @return the full qualified name
   */
  private FullQualifiedName getFullQualifiedName(final String typeName) {
    FullQualifiedName fullQualifiedName = new FullQualifiedName(PUNIT_NAME, typeName);
    return fullQualifiedName;
  }


  /**
   * The Class InnerMock.
   */
  private static class InnerMock extends JPAEdmTestModelView {

    /** The mock. */
    private final AttributeMock<Object, String> mock;

    /**
     * Instantiates a new inner mock.
     *
     * @param variant the variant
     */
    public InnerMock(Attribute.PersistentAttributeType variant) {
      this.mock = new AttributeMock<Object, String>(variant);
    }

    /**
     * Gets the JPA attribute.
     *
     * @return the JPA attribute
     */
    @Override
    public Attribute<?, ?> getJPAAttribute() {
      return getJPAAttributeLocal();
    }

    /**
     * Gets the p unit name.
     *
     * @return the p unit name
     */
    @Override
    public String getpUnitName() {
      return PUNIT_NAME;
    }

    /**
     * Gets the edm entity type.
     *
     * @return the edm entity type
     */
    @Override
    public EntityType getEdmEntityType() {
      EntityType entityType = new EntityType();
      entityType.setName(SimpleTypeA.NAME);
      return entityType;
    }
    
    /**
     * Gets the JPA attribute local.
     *
     * @return the JPA attribute local
     */
    private Attribute<?, ?> getJPAAttributeLocal() {
      return mock;
    }

  }

    /**
     * The Class AttributeMock.
     *
     * @param <Object> the generic type
     * @param <String> the generic type
     */
    // The inner class which gives us an replica of the jpa attribute
    @SuppressWarnings("hiding")
    private static class AttributeMock<Object, String> extends JPAAttributeMock<Object, String> {

      /** The variant. */
      final private PersistentAttributeType variant;

      /**
       * Instantiates a new attribute mock.
       *
       * @param variant the variant
       */
      public AttributeMock(PersistentAttributeType variant) {
        this.variant = variant;
      }

      /**
       * Gets the java type.
       *
       * @return the java type
       */
      @SuppressWarnings("unchecked")
      @Override
      public Class<String> getJavaType() {
        return (Class<String>) SimpleType.SimpleTypeA.clazz;
      }

      /**
       * Gets the persistent attribute type.
       *
       * @return the persistent attribute type
       */
      @Override
      public PersistentAttributeType getPersistentAttributeType() {
        return variant;
      }
    }
}
