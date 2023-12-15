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
import static org.junit.Assert.assertNull;
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
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmReferentialConstraintRoleView.RoleType;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAAttributeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAJavaMemberMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAManagedTypeMock;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmReferentialConstraintRoleTest.
 */
public class JPAEdmReferentialConstraintRoleTest extends JPAEdmTestModelView {

  /** The obj JPA edm referential constraint role. */
  private static JPAEdmReferentialConstraintRole objJPAEdmReferentialConstraintRole = null;
  
  /** The obj JPA edm referential constraint role test. */
  private static JPAEdmReferentialConstraintRoleTest objJPAEdmReferentialConstraintRoleTest = null;
  
  /** The join column names. */
  private List<String[]> joinColumnNames = null;

  /**
   * Sets the up.
   */
  @Before
  public void setUp() {
    objJPAEdmReferentialConstraintRoleTest = new JPAEdmReferentialConstraintRoleTest();

    objJPAEdmReferentialConstraintRole =
        new JPAEdmReferentialConstraintRole(RoleType.PRINCIPAL, objJPAEdmReferentialConstraintRoleTest,
            objJPAEdmReferentialConstraintRoleTest, objJPAEdmReferentialConstraintRoleTest);

    try {
      objJPAEdmReferentialConstraintRole.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Test is exists.
   */
  @Test
  public void testIsExists() {
    assertTrue(objJPAEdmReferentialConstraintRole.isExists());// Default
  }

  /**
   * Test get builder.
   */
  @Test
  public void testGetBuilder() {
    assertNotNull(objJPAEdmReferentialConstraintRole.getBuilder());
  }

  /**
   * Test get builder idempotent.
   */
  @Test
  public void testGetBuilderIdempotent() {
    JPAEdmBuilder builder1 = objJPAEdmReferentialConstraintRole.getBuilder();
    JPAEdmBuilder builder2 = objJPAEdmReferentialConstraintRole.getBuilder();

    assertEquals(builder1.hashCode(), builder2.hashCode());
  }

  /**
   * Test get role type principal.
   */
  @Test
  public void testGetRoleTypePrincipal() {
    assertEquals(objJPAEdmReferentialConstraintRole.getRoleType(), RoleType.PRINCIPAL);
  }

  /**
   * Test get role type dependent.
   */
  @Test
  public void testGetRoleTypeDependent() {
    objJPAEdmReferentialConstraintRoleTest = new JPAEdmReferentialConstraintRoleTest();
    objJPAEdmReferentialConstraintRole =
        new JPAEdmReferentialConstraintRole(RoleType.DEPENDENT, objJPAEdmReferentialConstraintRoleTest,
            objJPAEdmReferentialConstraintRoleTest, objJPAEdmReferentialConstraintRoleTest);

    try {
      objJPAEdmReferentialConstraintRole.getBuilder().build();
      objJPAEdmReferentialConstraintRole.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    assertEquals(objJPAEdmReferentialConstraintRole.getRoleType(), RoleType.DEPENDENT);
  }

  /**
   * Test get edm referential constraint role.
   */
  @Test
  public void testGetEdmReferentialConstraintRole() {
    try {
      objJPAEdmReferentialConstraintRole.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    assertNotNull(objJPAEdmReferentialConstraintRole.getEdmReferentialConstraintRole());
  }

  /**
   * Test get JPA column name.
   */
  @Test
  public void testGetJPAColumnName() {
    assertNull(objJPAEdmReferentialConstraintRole.getJPAColumnName());
  }

  /**
   * Test get edm entity type name.
   */
  @Test
  public void testGetEdmEntityTypeName() {
    assertNull(objJPAEdmReferentialConstraintRole.getEdmEntityTypeName());
  }

  /**
   * Test get edm association name.
   */
  @Test
  public void testGetEdmAssociationName() {
    assertNull(objJPAEdmReferentialConstraintRole.getEdmAssociationName());
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
   * Search edm entity type.
   *
   * @param arg0 the arg 0
   * @return the entity type
   */
  @Override
  public EntityType searchEdmEntityType(final String arg0) {

    EntityType entityType = new EntityType();

    JPAEdmMappingImpl mapping = new JPAEdmMappingImpl();
    mapping.setJPAColumnName("SOID");

    List<Property> propList = new ArrayList<Property>();

    Property property = new Property() {};
    property.setMapping((Mapping) mapping);
    property.setName("SOID");
    propList.add(property);

    entityType.setProperties(propList);

    return entityType;
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
