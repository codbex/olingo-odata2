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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.Metamodel;

import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPARuntimeException;
import org.apache.olingo.odata2.jpa.processor.core.common.ODataJPATestConstants;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEmbeddableMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAMetaModelMock;
import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPASingularAttributeMock;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmModelTest.
 */
public class JPAEdmModelTest extends JPAEdmTestModelView {

  /** The obj JPA edm model. */
  private JPAEdmModel objJPAEdmModel;

  /**
   * Sets the up.
   */
  @Before
  public void setUp() {
    objJPAEdmModel = new JPAEdmModel(getJPAMetaModel(), "salesorderprocessing");
    try {
      objJPAEdmModel.getBuilder().build();
    } catch (ODataJPAModelException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    } catch (ODataJPARuntimeException e) {
      fail(ODataJPATestConstants.EXCEPTION_MSG_PART_1 + e.getMessage() + ODataJPATestConstants.EXCEPTION_MSG_PART_2);
    }
  }

  /**
   * Test get edm schema view.
   */
  @Test
  public void testGetEdmSchemaView() {
    assertNotNull(objJPAEdmModel.getEdmSchemaView());
  }

  /**
   * Test get builder.
   */
  @Test
  public void testGetBuilder() {
    assertNotNull(objJPAEdmModel.getBuilder());
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
