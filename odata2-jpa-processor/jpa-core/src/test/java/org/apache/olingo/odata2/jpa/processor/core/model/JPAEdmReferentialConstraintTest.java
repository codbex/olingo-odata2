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

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.ManagedType;

import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAAttributeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAJavaMemberMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAManagedTypeMock;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmReferentialConstraintTest.
 */
public class JPAEdmReferentialConstraintTest extends JPAEdmTestModelView {

  /** The obj JPA edm referential constraint. */
  private static JPAEdmReferentialConstraint objJPAEdmReferentialConstraint = null;
  
  /** The obj JPA edm referential constraint test. */
  private static JPAEdmReferentialConstraintTest objJPAEdmReferentialConstraintTest = null;
  
  /** The join column names. */
  private List<String[]> joinColumnNames = null;

  /**
   * Sets the up.
   */
  @Before
  public void setUp() {
    objJPAEdmReferentialConstraintTest = new JPAEdmReferentialConstraintTest();
    objJPAEdmReferentialConstraint =
        new JPAEdmReferentialConstraint(objJPAEdmReferentialConstraintTest, objJPAEdmReferentialConstraintTest,
            objJPAEdmReferentialConstraintTest);
    try {
      objJPAEdmReferentialConstraint.getBuilder().build();
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
    assertNotNull(objJPAEdmReferentialConstraint.getBuilder());
  }

  /**
   * Test get builder idempotent.
   */
  @Test
  public void testGetBuilderIdempotent() {
    JPAEdmBuilder builder1 = objJPAEdmReferentialConstraint.getBuilder();
    JPAEdmBuilder builder2 = objJPAEdmReferentialConstraint.getBuilder();

    assertEquals(builder1.hashCode(), builder2.hashCode());
  }

  /**
   * Test get edm referential constraint.
   */
  @Test
  public void testGetEdmReferentialConstraint() {
    assertNotNull(objJPAEdmReferentialConstraint.getEdmReferentialConstraint());
  }

  /**
   * Test is exists true.
   */
  @Test
  public void testIsExistsTrue() {
    objJPAEdmReferentialConstraintTest = new JPAEdmReferentialConstraintTest();
    objJPAEdmReferentialConstraint =
        new JPAEdmReferentialConstraint(objJPAEdmReferentialConstraintTest, objJPAEdmReferentialConstraintTest,
            objJPAEdmReferentialConstraintTest);
    try {
      objJPAEdmReferentialConstraint.getBuilder().build();
      objJPAEdmReferentialConstraint.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    assertTrue(objJPAEdmReferentialConstraint.isExists());
  }

  /**
   * Test get relation ship name.
   */
  @Test
  public void testGetRelationShipName() {
    assertEquals("Assoc_SalesOrderHeader_SalesOrderItem", objJPAEdmReferentialConstraint.getEdmRelationShipName());
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
    association.setEnd1(new AssociationEnd().setType(new FullQualifiedName("salesorderprocessing", "String")).setRole(
        "SalesOrderHeader"));
    association.setEnd2(new AssociationEnd().setType(new FullQualifiedName("salesorderprocessing", "SalesOrderItem"))
        .setRole("SalesOrderItem"));
    return association;
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
   * Gets the JPA join columns.
   *
   * @return the JPA join columns
   */
  @Override
  public List<String[]> getJPAJoinColumns() {
    if (joinColumnNames == null) {

      joinColumnNames = new ArrayList<String[]>();
      String[] names = { "SOID", "SOID" };
      joinColumnNames.add(names);
    }
    return joinColumnNames;
  }

  /**
   * The Class AttributeMock.
   *
   * @param <Object> the generic type
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private class AttributeMock<Object, String> extends JPAAttributeMock<Object, String> {

    /**
     * Gets the java member.
     *
     * @return the java member
     */
    @Override
    public Member getJavaMember() {
      return new JavaMemberMock();
    }

    /**
     * Gets the java type.
     *
     * @return the java type
     */
    @SuppressWarnings("unchecked")
    @Override
    public Class<String> getJavaType() {
      return (Class<String>) java.lang.String.class;
    }

    /**
     * Gets the declaring type.
     *
     * @return the declaring type
     */
    @SuppressWarnings("unchecked")
    @Override
    public ManagedType<Object> getDeclaringType() {
      return (ManagedType<Object>) getManagedTypeLocal();
    }

    /**
     * Gets the managed type local.
     *
     * @return the managed type local
     */
    private ManagedType<?> getManagedTypeLocal() {
      ManagedTypeMock<String> managedTypeMock = new ManagedTypeMock<String>();
      return managedTypeMock;
    }
  }

  /**
   * The Class ManagedTypeMock.
   *
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private class ManagedTypeMock<String> extends JPAManagedTypeMock<String> {

    /**
     * Gets the java type.
     *
     * @return the java type
     */
    @SuppressWarnings("unchecked")
    @Override
    public Class<String> getJavaType() {
      return (Class<String>) java.lang.String.class;
    }
  }

  /**
   * The Class JavaMemberMock.
   */
  private class JavaMemberMock extends JPAJavaMemberMock {
    
    /**
     * Gets the annotation.
     *
     * @param <T> the generic type
     * @param annotationClass the annotation class
     * @return the annotation
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
      JoinColumn joinColumn = EasyMock.createMock(JoinColumn.class);
      EasyMock.expect(joinColumn.referencedColumnName()).andReturn("SOID");
      EasyMock.expect(joinColumn.name()).andReturn("SOID");

      EasyMock.replay(joinColumn);
      return (T) joinColumn;
    }
  }

}
