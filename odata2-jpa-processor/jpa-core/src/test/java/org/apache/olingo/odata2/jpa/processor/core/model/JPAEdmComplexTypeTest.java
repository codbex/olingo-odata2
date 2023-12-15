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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.Metamodel;

import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEmbeddableMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAMetaModelMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPASingularAttributeMock;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmComplexTypeTest.
 */
public class JPAEdmComplexTypeTest extends JPAEdmTestModelView {

  /** The obj complex type. */
  private static JPAEdmComplexType objComplexType = null;
  
  /** The local view. */
  private static JPAEdmComplexTypeTest localView = null;

  /**
   * Setup.
   */
  @BeforeClass
  public static void setup() {
    localView = new JPAEdmComplexTypeTest();
    objComplexType = new JPAEdmComplexType(localView);
    try {
      objComplexType.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Gets the JPA embeddable type.
   *
   * @return the JPA embeddable type
   */
  @SuppressWarnings("rawtypes")
  @Override
  public EmbeddableType<?> getJPAEmbeddableType() {
    @SuppressWarnings("hiding")
    class JPAComplexAttribute<Long> extends JPAEmbeddableMock<Long> {

      @SuppressWarnings("unchecked")
      @Override
      public Class<Long> getJavaType() {

        return (Class<Long>) java.lang.Long.class;
      }

    }
    return new JPAComplexAttribute();
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
   * Gets the JPA meta model.
   *
   * @return the JPA meta model
   */
  @Override
  public Metamodel getJPAMetaModel() {
    return new JPAEdmMetaModel();
  }

  /**
   * Test get builder.
   */
  @Test
  public void testGetBuilder() {

    assertNotNull(objComplexType.getBuilder());
  }

  /**
   * Test get edm complex type.
   */
  @Test
  public void testGetEdmComplexType() {
    assertEquals(objComplexType.getEdmComplexType().getName(), "String");
  }

  /**
   * Test search complex type string.
   */
  @Test
  public void testSearchComplexTypeString() {
    assertNotNull(objComplexType.searchEdmComplexType("java.lang.String"));

  }

  /**
   * Test get JPA embeddable type.
   */
  @Test
  public void testGetJPAEmbeddableType() {
    assertTrue(objComplexType.getJPAEmbeddableType().getAttributes().size() > 0);

  }

  /**
   * Test get consistent edm complex types.
   */
  @Test
  public void testGetConsistentEdmComplexTypes() {
    assertTrue(objComplexType.getConsistentEdmComplexTypes().size() > 0);
  }

  /**
   * Test search complex type full qualified name.
   */
  @Test
  public void testSearchComplexTypeFullQualifiedName() {
    assertNotNull(objComplexType.searchEdmComplexType(new FullQualifiedName("salesorderprocessing", "String")));

  }

  /**
   * Test search complex type full qualified name negative.
   */
  @Test
  public void testSearchComplexTypeFullQualifiedNameNegative() {
    assertNull(objComplexType.searchEdmComplexType(new FullQualifiedName("salesorderprocessing", "lang.String")));
  }

  /**
   * Test get builder idempotent.
   */
  @Test
  public void testGetBuilderIdempotent() {
    JPAEdmBuilder builder1 = objComplexType.getBuilder();
    JPAEdmBuilder builder2 = objComplexType.getBuilder();

    assertEquals(builder1.hashCode(), builder2.hashCode());
  }

  /**
   * Test add comple type view.
   */
  @Test
  public void testAddCompleTypeView() {
    localView = new JPAEdmComplexTypeTest();
    objComplexType = new JPAEdmComplexType(localView);
    try {
      objComplexType.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }

    objComplexType.addJPAEdmCompleTypeView(localView);
    assertTrue(objComplexType.getConsistentEdmComplexTypes().size() > 1);
  }

  /**
   * Test expand edm complex type.
   */
  @Test
  public void testExpandEdmComplexType() {
    ComplexType complexType = new ComplexType();
    List<Property> properties = new ArrayList<Property>();
    JPAEdmMapping mapping1 = new JPAEdmMappingImpl();
    mapping1.setJPAColumnName("LINEITEMID");
    ((Mapping) mapping1).setInternalName("LineItemKey.LiId");
    JPAEdmMapping mapping2 = new JPAEdmMappingImpl();
    mapping2.setJPAColumnName("LINEITEMNAME");
    ((Mapping) mapping2).setInternalName("LineItemKey.LiName");
    properties.add(new SimpleProperty().setName("LIID").setMapping((Mapping) mapping1));
    properties.add(new SimpleProperty().setName("LINAME").setMapping((Mapping) mapping2));
    complexType.setProperties(properties);
    List<Property> expandedList = null;
    try {
      objComplexType.expandEdmComplexType(complexType, expandedList, "SalesOrderItemKey");
    } catch (ClassCastException e) {
      assertTrue(false);
    }
    assertTrue(true);

  }

  /**
   * Test complex type creation.
   */
  @Test
  public void testComplexTypeCreation() {
    try {
      objComplexType.getBuilder().build();
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
    assertEquals(objComplexType.pUnitName, "salesorderprocessing");
  }

  /**
   * The Class JPAEdmMetaModel.
   */
  private class JPAEdmMetaModel extends JPAMetaModelMock {
    
    /** The embeddable set. */
    Set<EmbeddableType<?>> embeddableSet;

    /**
     * Instantiates a new JPA edm meta model.
     */
    public JPAEdmMetaModel() {
      embeddableSet = new HashSet<EmbeddableType<?>>();
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

  }

  /**
   * The Class JPAEdmEmbeddable.
   *
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private class JPAEdmEmbeddable<String> extends JPAEmbeddableMock<String> {

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
      return (Class<String>) java.lang.String.class;
    }

  }

  /**
   * The Class JPAEdmAttribute.
   *
   * @param <Object> the generic type
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private class JPAEdmAttribute<Object, String> extends JPASingularAttributeMock<Object, String> {

    /**
     * Gets the persistent attribute type.
     *
     * @return the persistent attribute type
     */
    @Override
    public PersistentAttributeType getPersistentAttributeType() {
      return PersistentAttributeType.BASIC;
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
      return false;
    }

  }
}
