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

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;

import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityContainerView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntitySetView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityTypeView;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEntityTypeMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAMetaModelMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPASingularAttributeMock;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmEntityContainerTest.
 */
public class JPAEdmEntityContainerTest extends JPAEdmTestModelView {

  /** The obj JPA edm entity container. */
  private JPAEdmEntityContainer objJPAEdmEntityContainer;
  
  /** The obj JPA edm entity container test. */
  private JPAEdmEntityContainerTest objJPAEdmEntityContainerTest;

  /**
   * Sets the up.
   */
  @Before
  public void setUp() {
    objJPAEdmEntityContainerTest = new JPAEdmEntityContainerTest();
    objJPAEdmEntityContainer = new JPAEdmEntityContainer(objJPAEdmEntityContainerTest);
    try {
      objJPAEdmEntityContainer.getBuilder().build();
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
    assertNotNull(objJPAEdmEntityContainer.getBuilder());
  }

  /**
   * Test get edm entity container.
   */
  @Test
  public void testGetEdmEntityContainer() {
    assertNotNull(objJPAEdmEntityContainer.getEdmEntityContainer());
    assertTrue(objJPAEdmEntityContainer.getEdmEntityContainer().getEntitySets().size() > 0);
  }

  /**
   * Test get consistent edm entity container list.
   */
  @Test
  public void testGetConsistentEdmEntityContainerList() {
    assertNotNull(objJPAEdmEntityContainer.getConsistentEdmEntityContainerList());
    assertTrue(objJPAEdmEntityContainer.getConsistentEdmEntityContainerList().size() > 0);

  }

  /**
   * Test get JPA edm entity set view.
   */
  @Test
  public void testGetJPAEdmEntitySetView() {
    assertNotNull(objJPAEdmEntityContainer.getJPAEdmEntitySetView());
    assertEquals("salesorderprocessing", objJPAEdmEntityContainer.getJPAEdmEntitySetView().getpUnitName());
  }

  /**
   * Test is consistent.
   */
  @Test
  public void testIsConsistent() {
    assertTrue(objJPAEdmEntityContainer.isConsistent());
    objJPAEdmEntityContainer.clean();
    assertFalse(objJPAEdmEntityContainer.isConsistent());
  }

  /**
   * Test get edm association set view.
   */
  @Test
  public void testGetEdmAssociationSetView() {
    assertNotNull(objJPAEdmEntityContainer.getEdmAssociationSetView());
  }

  /**
   * Test get builder idempotent.
   */
  @Test
  public void testGetBuilderIdempotent() {
    JPAEdmBuilder builder1 = objJPAEdmEntityContainer.getBuilder();
    JPAEdmBuilder builder2 = objJPAEdmEntityContainer.getBuilder();

    assertEquals(builder1.hashCode(), builder2.hashCode());
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
   * Gets the edm schema.
   *
   * @return the edm schema
   */
  @Override
  public Schema getEdmSchema() {
    Schema schema = new Schema();
    schema.setNamespace("salesordereprocessing");
    return schema;
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
   * Gets the builder.
   *
   * @return the builder
   */
  @Override
  public JPAEdmBuilder getBuilder() {
    return new JPAEdmBuilder() {

      @Override
      public void build() {
        // Nothing to do?
      }
    };
  }

  /**
   * The Class JPAEdmMetaModel.
   */
  private class JPAEdmMetaModel extends JPAMetaModelMock {
    
    /** The entities. */
    Set<EntityType<?>> entities;

    /**
     * Instantiates a new JPA edm meta model.
     */
    public JPAEdmMetaModel() {
      entities = new HashSet<EntityType<?>>();
    }

    /**
     * Gets the entities.
     *
     * @return the entities
     */
    @Override
    public Set<EntityType<?>> getEntities() {
      entities.add(new JPAEdmEntityType());
      return entities;
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

      /**
       * Gets the java type.
       *
       * @return the java type
       */
      @Override
      public Class<String> getJavaType() {
        return (Class<String>) java.lang.String.class;
      }
    }
  }

  /**
   * The Class JPAEdmEntityType.
   *
   * @param <String> the generic type
   */
  @SuppressWarnings("hiding")
  private class JPAEdmEntityType<String> extends JPAEntityTypeMock<String> {
    
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
        return true;
      }
    }
  }

}
