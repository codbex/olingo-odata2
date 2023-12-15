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

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.Attribute.PersistentAttributeType;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.Type;

import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.*;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmComplexTypeView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityContainerView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntitySetView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityTypeView;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEdmMockData.ComplexType;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEdmMockData.SimpleType;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEmbeddableTypeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEntityTypeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAJavaMemberMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAMetaModelMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAPluralAttributeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPASingularAttributeMock;
import org.easymock.EasyMock;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmPropertyTest.
 */
public class JPAEdmPropertyTest extends JPAEdmTestModelView {

  /** The obj JPA edm property test. */
  private JPAEdmPropertyTest objJPAEdmPropertyTest;
  
  /** The obj JPA edm property. */
  private JPAEdmProperty objJPAEdmProperty;
  
  /** The test case. */
  private static java.lang.String testCase = "Default";

  /** The attribute type. */
  private static PersistentAttributeType ATTRIBUTE_TYPE = PersistentAttributeType.BASIC;

  /**
   * Sets the up.
   */
  public void setUp() {
    ATTRIBUTE_TYPE = PersistentAttributeType.BASIC;
    objJPAEdmPropertyTest = new JPAEdmPropertyTest();
    objJPAEdmProperty = new JPAEdmProperty(objJPAEdmPropertyTest);
    try {
      objJPAEdmProperty.getBuilder().build();
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
    setUp();
    assertNotNull(objJPAEdmProperty.getBuilder());
  }

  /**
   * Test get builder idempotent.
   */
  @Test
  public void testGetBuilderIdempotent() {
    setUp();
    JPAEdmBuilder builder1 = objJPAEdmProperty.getBuilder();
    JPAEdmBuilder builder2 = objJPAEdmProperty.getBuilder();

    assertEquals(builder1.hashCode(), builder2.hashCode());
  }

  /**
   * Test get property list.
   */
  @Test
  public void testGetPropertyList() {
    setUp();
    assertNotNull(objJPAEdmProperty.getEdmPropertyList());
    assertTrue(objJPAEdmProperty.getEdmPropertyList().size() > 0);
  }

  /**
   * Test get JPA edm key view.
   */
  @Test
  public void testGetJPAEdmKeyView() {
    setUp();
    assertNotNull(objJPAEdmProperty.getJPAEdmKeyView());
  }

  /**
   * Test get simple property.
   */
  @Test
  public void testGetSimpleProperty() {
    setUp();
    assertNotNull(objJPAEdmProperty.getEdmSimpleProperty());
  }

  /**
   * Test get JPA attribute.
   */
  @Test
  public void testGetJPAAttribute() {
    setUp();
    assertNotNull(objJPAEdmProperty.getJPAAttribute());
  }

  /**
   * Test get edm complex property.
   */
  @Test
  public void testGetEdmComplexProperty() {

    // builder for complex type
    ATTRIBUTE_TYPE = PersistentAttributeType.EMBEDDED;
    objJPAEdmPropertyTest = new JPAEdmPropertyTest();
    objJPAEdmProperty = new JPAEdmProperty(objJPAEdmPropertyTest, objJPAEdmPropertyTest);
    try {
      objJPAEdmProperty.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    assertNotNull(objJPAEdmProperty.getEdmComplexProperty());
  }

  /**
   * Test get JPA edm navigation property view.
   */
  @Test
  public void testGetJPAEdmNavigationPropertyView() {
    setUp();
    assertNotNull(objJPAEdmProperty.getJPAEdmNavigationPropertyView());
  }

  /**
   * Test is consistent.
   */
  @Test
  public void testIsConsistent() {
    setUp();
    assertNotNull(objJPAEdmProperty.isConsistent());
  }

  /**
   * Test clean.
   */
  @Test
  public void testClean() {
    setUp();
    objJPAEdmProperty.clean();
    assertFalse(objJPAEdmProperty.isConsistent());
  }

  /**
   * Test build many to one.
   */
  @Test
  public void testBuildManyToOne() {
    ATTRIBUTE_TYPE = PersistentAttributeType.MANY_TO_ONE;
    testCase = "Default";
    objJPAEdmPropertyTest = new JPAEdmPropertyTest();
    objJPAEdmProperty = new JPAEdmProperty(objJPAEdmPropertyTest);

    try {
      objJPAEdmProperty.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    NavigationProperty navigationProperty =
        objJPAEdmProperty.getJPAEdmNavigationPropertyView().getEdmNavigationProperty();
    assertNotNull(navigationProperty);
  }

  /**
   * Test build many to one no join column names.
   */
  @Test
  public void testBuildManyToOneNoJoinColumnNames() {
    ATTRIBUTE_TYPE = PersistentAttributeType.MANY_TO_ONE;
    testCase = "NoJoinColumnNames";
    objJPAEdmPropertyTest = new JPAEdmPropertyTest();
    objJPAEdmProperty = new JPAEdmProperty(objJPAEdmPropertyTest);

    try {
      objJPAEdmProperty.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    Property property = objJPAEdmProperty.getEdmPropertyList().get(0);
    EdmFacets facets = property.getFacets();
    assertTrue(facets.isNullable());

    NavigationProperty navigationProperty =
        objJPAEdmProperty.getJPAEdmNavigationPropertyView().getEdmNavigationProperty();
    assertNotNull(navigationProperty);
    assertEquals("String", navigationProperty.getFromRole());
    assertEquals("salesorderprocessing", navigationProperty.getToRole());
    assertEquals("StringDetails", navigationProperty.getName());
  }

  /**
   * Test build many to one join column with facets.
   */
  @Test
  public void testBuildManyToOneJoinColumnWithFacets() {
    ATTRIBUTE_TYPE = PersistentAttributeType.MANY_TO_ONE;
    testCase = "JoinColumnWithFacets";
    objJPAEdmPropertyTest = new JPAEdmPropertyTest();
    objJPAEdmProperty = new JPAEdmProperty(objJPAEdmPropertyTest);

    try {
      objJPAEdmProperty.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    Property property = objJPAEdmProperty.getEdmPropertyList().get(0);
    EdmFacets facets = property.getFacets();
    assertFalse(facets.isNullable());
  }

  /**
   * Gets the JPA meta model.
   *
   * @return the JPA meta model
   */
  @Override
  public Metamodel getJPAMetaModel() {
    return new JPAEdmMetaModel();
  }

  /**
   * Checks if is referenced in key.
   *
   * @param complexType the complex type
   * @return true, if is referenced in key
   */
  @Override
  public boolean isReferencedInKey(final String complexType) {
    return false;
  }

  /**
   * Gets the edm schema.
   *
   * @return the edm schema
   */
  @Override
  public Schema getEdmSchema() {
    Schema schema = new Schema();
    schema.setNamespace(getpUnitName());
    return schema;
  }

  /**
   * Search edm complex type.
   *
   * @param arg0 the arg 0
   * @return the org.apache.olingo.odata 2 .api.edm.provider. complex type
   */
  @Override
  public org.apache.olingo.odata2.api.edm.provider.ComplexType searchEdmComplexType(final String arg0) {
    org.apache.olingo.odata2.api.edm.provider.ComplexType complexType =
        new org.apache.olingo.odata2.api.edm.provider.ComplexType();
    complexType.setName("ComplexTypeA");
    return complexType;
  }

  /**
   * Gets the JPA edm entity set view.
   *
   * @return the JPA edm entity set view
   */
  @Override
  public JPAEdmEntitySetView getJPAEdmEntitySetView() {
    return this;
  }

  /**
   * Gets the JPA edm entity container view.
   *
   * @return the JPA edm entity container view
   */
  @Override
  public JPAEdmEntityContainerView getJPAEdmEntityContainerView() {
    return this;
  }

  /**
   * Gets the JPA entity type.
   *
   * @return the JPA entity type
   */
  @Override
  public EntityType<?> getJPAEntityType() {
    return new JPAEdmEntityType<String>();
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
   * Gets the edm entity type.
   *
   * @return the edm entity type
   */
  @Override
  public org.apache.olingo.odata2.api.edm.provider.EntityType getEdmEntityType() {
    org.apache.olingo.odata2.api.edm.provider.EntityType entityType =
        new org.apache.olingo.odata2.api.edm.provider.EntityType();
    entityType.setName("SalesOrderHeader");

    return entityType;
  }

  /**
   * Gets the edm association.
   *
   * @return the edm association
   */
  @Override
  public Association getEdmAssociation() {
    Association association = new Association();
    AssociationEnd end1 = new AssociationEnd();
    end1.setType(new FullQualifiedName("salesorderprocessing", "SalesOrderHeader"));
    end1.setMultiplicity(EdmMultiplicity.MANY);
    end1.setRole("salesorderprocessing");

    AssociationEnd end2 = new AssociationEnd();
    end2.setType(new FullQualifiedName("String", "SalesOrderHeader"));
    end2.setMultiplicity(EdmMultiplicity.ONE);
    end2.setRole("String");

    association
        .setEnd1(end1);
    association.setEnd2(end2);
    association.setName("salesorderprocessing_String");

    return association;
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
   * Gets the JPA edm association view.
   *
   * @return the JPA edm association view
   */
  @Override
  public JPAEdmAssociationView getJPAEdmAssociationView() {
    return this;
  }

  /**
   * Gets the JPA embeddable type.
   *
   * @return the JPA embeddable type
   */
  @Override
  public EmbeddableType<?> getJPAEmbeddableType() {
    return new JPAEdmEmbeddable<java.lang.String>();
  }

  /**
   * Gets the JPA edm complex type view.
   *
   * @return the JPA edm complex type view
   */
  @Override
  public JPAEdmComplexTypeView getJPAEdmComplexTypeView() {
    return this;
  }

  /**
   * Gets the JPA referenced attribute.
   *
   * @return the JPA referenced attribute
   */
  @Override
  public Attribute<?, ?> getJPAReferencedAttribute() {
    JPAEdmAttribute<Object, String> refAttribute =
        new JPAEdmAttribute<Object, String>(java.lang.String.class, "SOLITID");

    return refAttribute;
  }

  /**
   * The Class JPAEdmAttribute.
   *
   * @param <Object> the generic type
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private static class JPAEdmAttribute<Object, String> extends JPASingularAttributeMock<Object, String> {

    /**
     * Gets the persistent attribute type.
     *
     * @return the persistent attribute type
     */
    @Override
    public PersistentAttributeType getPersistentAttributeType() {
      if (attributeName.equals("SOLITID")) {
        return PersistentAttributeType.BASIC;
      }
      return ATTRIBUTE_TYPE;
    }

    /** The clazz. */
    Class<String> clazz;
    
    /** The attribute name. */
    java.lang.String attributeName;

    /**
     * Instantiates a new JPA edm attribute.
     *
     * @param javaType the java type
     * @param name the name
     */
    public JPAEdmAttribute(final Class<String> javaType, final java.lang.String name) {
      this.clazz = javaType;
      this.attributeName = name;

    }

    /**
     * Gets the java type.
     *
     * @return the java type
     */
    @Override
    public Class<String> getJavaType() {
      return clazz;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public java.lang.String getName() {
      return this.attributeName;
    }

    /**
     * Checks if is id.
     *
     * @return true, if is id
     */
    @Override
    public boolean isId() {
      return true;
    }

    /**
     * Gets the java member.
     *
     * @return the java member
     */
    @Override
    public Member getJavaMember() {
      if (this.attributeName.equals("SOLITID")) {
        return new JPAJavaMember();
      }
      return null;
    }
  }

  /**
   * The Class JPAEdmMetaModel.
   */
  private class JPAEdmMetaModel extends JPAMetaModelMock {
    
    /** The entities. */
    Set<EntityType<?>> entities;
    
    /** The embeddable set. */
    Set<EmbeddableType<?>> embeddableSet;

    /**
     * Instantiates a new JPA edm meta model.
     */
    public JPAEdmMetaModel() {
      entities = new HashSet<EntityType<?>>();
      embeddableSet = new HashSet<EmbeddableType<?>>();
    }

    /**
     * Gets the entities.
     *
     * @return the entities
     */
    @Override
    public Set<EntityType<?>> getEntities() {
      entities.add(new JPAEdmEntityType<String>());
      return entities;
    }

    /**
     * Gets the embeddables.
     *
     * @return the embeddables
     */
    @Override
    public Set<EmbeddableType<?>> getEmbeddables() {
      embeddableSet.add(new JPAEdmEmbeddable<String>());
      return embeddableSet;
    }

    /**
     * Entity.
     *
     * @param <X> the generic type
     * @param arg0 the arg 0
     * @return the entity type
     */
    @SuppressWarnings("unchecked")
    @Override
    public <X> EntityType<X> entity(final Class<X> arg0) {
      JPAEdmRefEntityType<String> refEntityType = new JPAEdmRefEntityType<String>();
      return (EntityType<X>) refEntityType;

    }
  }

  /**
   * The Class JPAEdmRefEntityType.
   *
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private static class JPAEdmRefEntityType<String> extends JPAEntityTypeMock<String> {
    
    /** The attribute set. */
    Set<Attribute<? super String, ?>> attributeSet = new HashSet<Attribute<? super String, ?>>();

    /**
     * Sets the values to set.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setValuesToSet() {
      attributeSet.add((Attribute<? super String, String>) new JPAEdmAttribute(java.lang.String.class, "SOLITID"));
      attributeSet.add((Attribute<? super String, String>) new JPAEdmAttribute(java.lang.String.class, "SONAME"));
    }

    /**
     * Gets the attributes.
     *
     * @return the attributes
     */
    @Override
    public Set<Attribute<? super String, ?>> getAttributes() {
      setValuesToSet();
      return attributeSet;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public java.lang.String getName() {
      return "salesorderitemdetails";
    }
  }

  /**
   * The Class JPAEdmEntityType.
   *
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private static class JPAEdmEntityType<String> extends JPAEntityTypeMock<String> {
    
    /** The attribute set. */
    Set<Attribute<? super String, ?>> attributeSet = new HashSet<Attribute<? super String, ?>>();

    /**
     * Sets the values to set.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setValuesToSet() {
      if (JPAEdmPropertyTest.ATTRIBUTE_TYPE.equals(PersistentAttributeType.BASIC)) {
        attributeSet.add((Attribute<? super String, String>) new JPAEdmAttribute(java.lang.String.class, "SOID"));
        attributeSet.add((Attribute<? super String, String>) new JPAEdmAttribute(java.lang.String.class, "SONAME"));
      } else if (JPAEdmPropertyTest.ATTRIBUTE_TYPE.equals(PersistentAttributeType.EMBEDDED)) {
        attributeSet.add(new JPAEdmAttribute(JPAEdmEmbeddable.class, ComplexType.ComplexTypeA.clazz.getName()));
      } else if (JPAEdmPropertyTest.ATTRIBUTE_TYPE.equals(PersistentAttributeType.MANY_TO_ONE)) {
        attributeSet.add(new JPAEdmPluralAttribute());
      }
    }

    /**
     * Gets the attributes.
     *
     * @return the attributes
     */
    @Override
    public Set<Attribute<? super String, ?>> getAttributes() {
      setValuesToSet();
      return attributeSet;
    }

    /**
     * The Class JPAEdmPluralAttribute.
     */
    private class JPAEdmPluralAttribute extends JPAPluralAttributeMock {

      /**
       * Gets the java member.
       *
       * @return the java member
       */
      @Override
      public Member getJavaMember() {
        if (ATTRIBUTE_TYPE.equals(PersistentAttributeType.MANY_TO_ONE)) {
          return new JPAJavaMember();
        }
        return null;

      }

      /**
       * Gets the name.
       *
       * @return the name
       */
      @Override
      public java.lang.String getName() {
        return "salesorderheaderdetails";
      }

      /**
       * Gets the persistent attribute type.
       *
       * @return the persistent attribute type
       */
      @Override
      public jakarta.persistence.metamodel.Attribute.PersistentAttributeType getPersistentAttributeType() {
        return ATTRIBUTE_TYPE;
      }

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
        return new Type<java.lang.String>() {

          @Override
          public Class<java.lang.String> getJavaType() {
            return java.lang.String.class;
          }

          @Override
          public jakarta.persistence.metamodel.Type.PersistenceType getPersistenceType() {
            return null;
          }

        };
      }
    }
  }

  /**
   * The Class JPAEdmEmbeddable.
   *
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private class JPAEdmEmbeddable<String> extends JPAEmbeddableTypeMock<String> {

    /** The attribute set. */
    Set<Attribute<? super String, ?>> attributeSet = new HashSet<Attribute<? super String, ?>>();

    /**
     * Sets the values to set.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void setValuesToSet() {
      attributeSet.add((Attribute<? super String, String>) new JPAEdmAttribute(java.lang.String.class, "SOID"));
      attributeSet.add((Attribute<? super String, String>) new JPAEdmAttribute(java.lang.String.class, "SONAME"));
    }

    /**
     * Gets the attributes.
     *
     * @return the attributes
     */
    @Override
    public Set<Attribute<? super String, ?>> getAttributes() {
      setValuesToSet();
      return attributeSet;
    }

    /**
     * Gets the java type.
     *
     * @return the java type
     */
    @SuppressWarnings("unchecked")
    @Override
    public Class<String> getJavaType() {
      Class<?> clazz = null;
      if (ATTRIBUTE_TYPE.equals(PersistentAttributeType.BASIC)) {
        clazz = (Class<java.lang.String>) SimpleType.SimpleTypeA.clazz;
      } else {
        clazz = (Class<?>) ComplexType.ComplexTypeA.clazz;
      }
      return (Class<String>) clazz;
    }

    /**
     * The Class JPAEdmAttribute.
     *
     * @param <Object> the generic type
     * @param <String> the generic type
     */
    private class JPAEdmAttribute<Object, String> extends JPASingularAttributeMock<Object, String> {

      /**
       * Gets the persistent attribute type.
       *
       * @return the persistent attribute type
       */
      @Override
      public PersistentAttributeType getPersistentAttributeType() {
        return ATTRIBUTE_TYPE;
      }

      /** The clazz. */
      Class<String> clazz;
      
      /** The attribute name. */
      java.lang.String attributeName;

      /**
       * Instantiates a new JPA edm attribute.
       *
       * @param javaType the java type
       * @param name the name
       */
      public JPAEdmAttribute(final Class<String> javaType, final java.lang.String name) {
        this.clazz = javaType;
        this.attributeName = name;

      }

      /**
       * Gets the java type.
       *
       * @return the java type
       */
      @Override
      public Class<String> getJavaType() {
        return clazz;
      }

      /**
       * Gets the name.
       *
       * @return the name
       */
      @Override
      public java.lang.String getName() {
        return this.attributeName;
      }

      /**
       * Checks if is id.
       *
       * @return true, if is id
       */
      @Override
      public boolean isId() {
        return true;
      }
    }

  }

  /**
   * The Class JPAJavaMember.
   */
  private static class JPAJavaMember extends JPAJavaMemberMock {

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

      if(annotationClass.equals(ManyToOne.class)) {
        ManyToOne manyToOne = EasyMock.createMock(ManyToOne.class);
        EasyMock.expect(manyToOne.optional()).andReturn(true).anyTimes();
        return (T) manyToOne;

      } else if (annotationClass.equals(JoinColumn.class)) {

        JoinColumn joinColumn = EasyMock.createMock(JoinColumn.class);
        EasyMock.expect(joinColumn.insertable()).andReturn(true).anyTimes();
        EasyMock.expect(joinColumn.updatable()).andReturn(true).anyTimes();
        if (testCase.equals("Default")) {
          EasyMock.expect(joinColumn.name()).andReturn("FSOID").anyTimes();
          EasyMock.expect(joinColumn.referencedColumnName()).andReturn("SOLITID").anyTimes();
          EasyMock.expect(joinColumn.nullable()).andReturn(false).anyTimes();
          EasyMock.replay(joinColumn);
        } else if (testCase.equals("NoJoinColumnNames")) {
          EasyMock.expect(joinColumn.name()).andReturn("").anyTimes();
          EasyMock.expect(joinColumn.referencedColumnName()).andReturn("").anyTimes();
          EasyMock.expect(joinColumn.nullable()).andReturn(true).anyTimes();
          EasyMock.replay(joinColumn);
        } else if (testCase.equals("JoinColumnWithFacets")) {
          EasyMock.expect(joinColumn.name()).andReturn("ColumnWithFacets").anyTimes();
          EasyMock.expect(joinColumn.referencedColumnName()).andReturn("").anyTimes();
          EasyMock.expect(joinColumn.nullable()).andReturn(false).anyTimes();
          EasyMock.replay(joinColumn);
        }
        return (T) joinColumn;

      } else {

        if (testCase.equals("Default")) {
          Column column = EasyMock.createMock(Column.class);
          EasyMock.expect(column.name()).andReturn("SOLITID").anyTimes();
          EasyMock.expect(column.nullable()).andReturn(true).anyTimes();
          EasyMock.expect(column.length()).andReturn(30).anyTimes();
          EasyMock.replay(column);
          return (T) column;
        }

      }
      return null;
    }
  }

}
