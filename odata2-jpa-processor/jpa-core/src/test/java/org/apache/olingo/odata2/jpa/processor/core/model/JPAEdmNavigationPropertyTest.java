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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Type;

import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityTypeView;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEntityTypeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAPluralAttributeMock;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmNavigationPropertyTest.
 */
public class JPAEdmNavigationPropertyTest extends JPAEdmTestModelView {

  /** The obj navigation property. */
  private static JPAEdmNavigationProperty objNavigationProperty;
  
  /** The nav prop view. */
  private static JPAEdmNavigationPropertyTest navPropView;

  /**
   * Setup.
   */
  @BeforeClass
  public static void setup() {
    JPAEdmNavigationPropertyTest localView = new JPAEdmNavigationPropertyTest();
    navPropView = new JPAEdmNavigationPropertyTest();
    objNavigationProperty = new JPAEdmNavigationProperty(localView,
        localView, 1);
    try {
      objNavigationProperty.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Gets the p unit name.
   *
   * @return the p unit name
   */
  @Override
  public String getpUnitName() {
    return "salesorderprocessing";
  }

  /**
   * Gets the JPA edm entity type view.
   *
   * @return the JPA edm entity type view
   */
  @Override
  public JPAEdmEntityTypeView getJPAEdmEntityTypeView() {
    return this;
  }

  /**
   * Gets the JPA entity type.
   *
   * @return the JPA entity type
   */
  @Override
  public EntityType<?> getJPAEntityType() {
    return new JPAEdmEntityType();
  }

  /**
   * Gets the JPA attribute local.
   *
   * @return the JPA attribute local
   */
  private Attribute<?, ?> getJPAAttributeLocal() {
    AttributeMock<Object, String> attr = new AttributeMock<Object, String>();
    return attr;
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
   * Gets the edm association.
   *
   * @return the edm association
   */
  @Override
  public Association getEdmAssociation() {

    Association association = new Association();
    association.setName("Assoc_SalesOrderHeader_SalesOrderItem");
    association.setEnd1(new AssociationEnd().setType(
        new FullQualifiedName("salesorderprocessing", "String"))
        .setRole("SalesOrderHeader"));
    association.setEnd2(new AssociationEnd()
        .setType(
            new FullQualifiedName("salesorderprocessing",
                "SalesOrderItem")).setRole("SalesOrderItem"));
    return association;
  }

  /**
   * Test get builder.
   */
  @Test
  public void testGetBuilder() {
    assertNotNull(objNavigationProperty.getBuilder());

  }

  /**
   * Test get builder idempotent.
   */
  @Test
  public void testGetBuilderIdempotent() {
    JPAEdmBuilder builder1 = objNavigationProperty.getBuilder();
    JPAEdmBuilder builder2 = objNavigationProperty.getBuilder();

    assertEquals(builder1.hashCode(), builder2.hashCode());
  }

  /**
   * Test get edm navigation property.
   */
  @Test
  public void testGetEdmNavigationProperty() {
    if (objNavigationProperty == null || objNavigationProperty.getEdmNavigationProperty() == null) {
      JPAEdmNavigationPropertyTest localView = new JPAEdmNavigationPropertyTest();
      navPropView = new JPAEdmNavigationPropertyTest();
      objNavigationProperty = new JPAEdmNavigationProperty(localView,
          localView, 1);
      try {
        objNavigationProperty.getBuilder().build();
      } catch (ODataJPAModelException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      } catch (ODataJPARuntimeException e) {
        fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
      }
    }
    assertEquals(
        objNavigationProperty.getEdmNavigationProperty().getName(),
        "StringDetails");
  }

  /**
   * Test get consistent edm navigation properties.
   */
  @Test
  public void testGetConsistentEdmNavigationProperties() {
    assertTrue(objNavigationProperty.getConsistentEdmNavigationProperties()
        .size() > 0);
  }

  /**
   * Test add JPA edm navigation property view.
   */
  @Test
  public void testAddJPAEdmNavigationPropertyView() {
    JPAEdmNavigationPropertyTest localView = new JPAEdmNavigationPropertyTest();
    navPropView = new JPAEdmNavigationPropertyTest();
    objNavigationProperty = new JPAEdmNavigationProperty(localView,
        localView, 1);
    try {
      objNavigationProperty.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    objNavigationProperty.addJPAEdmNavigationPropertyView(navPropView);
    assertTrue(objNavigationProperty.getConsistentEdmNavigationProperties()
        .size() > 1);
  }

  /**
   * Checks if is consistent.
   *
   * @return true, if is consistent
   */
  @Override
  public boolean isConsistent() {
    return true;
  }

  /**
   * Test build navigation property.
   */
  @Test
  public void testBuildNavigationProperty() {

    try {
      objNavigationProperty.getBuilder().build();
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    assertEquals(objNavigationProperty.getEdmNavigationProperty()
        .getFromRole(), "SalesOrderItem");
    assertEquals(objNavigationProperty.getEdmNavigationProperty()
        .getToRole(), "SalesOrderHeader");

  }

  /**
   * The Class AttributeMock.
   *
   * @param <Object> the generic type
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private class AttributeMock<Object, String> extends
      JPAPluralAttributeMock {

    /**
     * Checks if is collection.
     *
     * @return true, if is collection
     */
    @Override
    public boolean isCollection() {
      return true;
    }

    /**
     * Gets the element type.
     *
     * @return the element type
     */
    @Override
    public Type<java.lang.String> getElementType() {
      return new ElementType();
    }
  }

  /**
   * The Class JPAEdmEntityType.
   */
  private class JPAEdmEntityType extends JPAEntityTypeMock<String> {
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public String getName() {
      return "SalesOrderHeader";
    }
  }

  /**
   * The Class ElementType.
   */
  private class ElementType implements Type<String> {

    /**
     * Gets the persistence type.
     *
     * @return the persistence type
     */
    @Override
    public jakarta.persistence.metamodel.Type.PersistenceType getPersistenceType() {
      return PersistenceType.BASIC;
    }

    /**
     * Gets the java type.
     *
     * @return the java type
     */
    @Override
    public Class<String> getJavaType() {
      return String.class;
    }

  }
}