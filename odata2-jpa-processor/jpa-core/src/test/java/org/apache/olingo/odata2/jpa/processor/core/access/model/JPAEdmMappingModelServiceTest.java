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
package org.apache.olingo.odata2.jpa.processor.core.access.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.io.InputStream;
import org.apache.olingo.odata2.jpa.processor.core.mock.ODataJPAContextMock;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmMappingModelServiceTest.
 */
public class JPAEdmMappingModelServiceTest extends JPAEdmMappingModelService {

    /** The obj JPA edm mapping model service test. */
    private static JPAEdmMappingModelServiceTest objJPAEdmMappingModelServiceTest;

    /** The Constant MAPPING_FILE_CORRECT. */
    private static final String MAPPING_FILE_CORRECT = "SalesOrderProcessingMappingModels.xml";
    
    /** The Constant MAPPING_FILE_INCORRECT. */
    private static final String MAPPING_FILE_INCORRECT = "TEST.xml";

    /** The variant mapping file. */
    private static int VARIANT_MAPPING_FILE; // 0 FOR INCORRECT, 1 FOR CORRECT

    /** The persistence unit name jpa. */
    private static String PERSISTENCE_UNIT_NAME_JPA = "salesorderprocessing";
    
    /** The persistence unit name edm. */
    private static String PERSISTENCE_UNIT_NAME_EDM = "SalesOrderProcessing";

    /** The entity type name jpa. */
    private static String ENTITY_TYPE_NAME_JPA = "SalesOrderHeader";
    
    /** The entity type name edm. */
    private static String ENTITY_TYPE_NAME_EDM = "SalesOrder";
    
    /** The entity set name edm. */
    private static String ENTITY_SET_NAME_EDM = "SalesOrders";
    
    /** The relationship name jpa. */
    private static String RELATIONSHIP_NAME_JPA = "salesOrderItems";
    
    /** The relationship name edm. */
    private static String RELATIONSHIP_NAME_EDM = "SalesOrderItemDetails";
    
    /** The attribute name jpa. */
    private static String ATTRIBUTE_NAME_JPA = "netAmount";
    
    /** The attribute name edm. */
    private static String ATTRIBUTE_NAME_EDM = "NetAmount";
    
    /** The embeddable type name jpa. */
    private static String EMBEDDABLE_TYPE_NAME_JPA = "SalesOrderItemKey";
    
    /** The embeddable attribute name jpa. */
    private static String EMBEDDABLE_ATTRIBUTE_NAME_JPA = "liId";
    
    /** The embeddable attribute name edm. */
    private static String EMBEDDABLE_ATTRIBUTE_NAME_EDM = "ID";
    
    /** The embeddable type 2 name jpa. */
    private static String EMBEDDABLE_TYPE_2_NAME_JPA = "SalesOrderItemKey";

    /** The entity type name jpa wrong. */
    private static String ENTITY_TYPE_NAME_JPA_WRONG = "SalesOrderHeaders";
    
    /** The relationship name jpa wrong. */
    private static String RELATIONSHIP_NAME_JPA_WRONG = "value";
    
    /** The embeddable type name jpa wrong. */
    private static String EMBEDDABLE_TYPE_NAME_JPA_WRONG = "SalesOrderItemKeys";

    /**
     * Instantiates a new JPA edm mapping model service test.
     */
    public JPAEdmMappingModelServiceTest() {
        super(ODataJPAContextMock.mockODataJPAContext());
    }

    /**
     * Setup.
     */
    @BeforeClass
    public static void setup() {
        objJPAEdmMappingModelServiceTest = new JPAEdmMappingModelServiceTest();
        VARIANT_MAPPING_FILE = 1;
        objJPAEdmMappingModelServiceTest.loadMappingModel();
    }

    /**
     * Test load mapping model.
     */
    @Test
    public void testLoadMappingModel() {
        VARIANT_MAPPING_FILE = 1;
        loadMappingModel();
        assertTrue(isMappingModelExists());
    }

    /**
     * Test load mapping model negative.
     */
    @Test
    public void testLoadMappingModelNegative() {
        VARIANT_MAPPING_FILE = 0;
        loadMappingModel();
        assertFalse(isMappingModelExists());
        // reset it for other JUnits
        VARIANT_MAPPING_FILE = 1;
        loadMappingModel();
    }

    /**
     * Test is mapping model exists.
     */
    @Test
    public void testIsMappingModelExists() {
        assertTrue(objJPAEdmMappingModelServiceTest.isMappingModelExists());
    }

    /**
     * Test get JPA edm mapping model.
     */
    @Test
    public void testGetJPAEdmMappingModel() {
        assertNotNull(objJPAEdmMappingModelServiceTest.getJPAEdmMappingModel());
    }

    /**
     * Test map JPA persistence unit.
     */
    @Test
    public void testMapJPAPersistenceUnit() {
        assertEquals(PERSISTENCE_UNIT_NAME_EDM, objJPAEdmMappingModelServiceTest.mapJPAPersistenceUnit(PERSISTENCE_UNIT_NAME_JPA));
    }

    /**
     * Test map JPA persistence unit negative.
     */
    @Test
    public void testMapJPAPersistenceUnitNegative() {
        assertNull(objJPAEdmMappingModelServiceTest.mapJPAPersistenceUnit(PERSISTENCE_UNIT_NAME_EDM));// Wrong value to
                                                                                                      // bring null
    }

    /**
     * Test map JPA entity type.
     */
    @Test
    public void testMapJPAEntityType() {
        assertEquals(ENTITY_TYPE_NAME_EDM, objJPAEdmMappingModelServiceTest.mapJPAEntityType(ENTITY_TYPE_NAME_JPA));
    }

    /**
     * Test map JPA entity type negative.
     */
    @Test
    public void testMapJPAEntityTypeNegative() {
        assertNull(objJPAEdmMappingModelServiceTest.mapJPAEntityType(ENTITY_TYPE_NAME_JPA_WRONG));// Wrong value to bring
                                                                                                  // null
    }

    /**
     * Test map JPA entity set.
     */
    @Test
    public void testMapJPAEntitySet() {
        assertEquals(ENTITY_SET_NAME_EDM, objJPAEdmMappingModelServiceTest.mapJPAEntitySet(ENTITY_TYPE_NAME_JPA));
    }

    /**
     * Test map JPA entity set negative.
     */
    @Test
    public void testMapJPAEntitySetNegative() {
        assertNull(objJPAEdmMappingModelServiceTest.mapJPAEntitySet(ENTITY_TYPE_NAME_JPA_WRONG));// Wrong value to bring
                                                                                                 // null
    }

    /**
     * Test map JPA attribute.
     */
    @Test
    public void testMapJPAAttribute() {
        assertEquals(ATTRIBUTE_NAME_EDM, objJPAEdmMappingModelServiceTest.mapJPAAttribute(ENTITY_TYPE_NAME_JPA, ATTRIBUTE_NAME_JPA));
    }

    /**
     * Test map JPA attribute negative.
     */
    @Test
    public void testMapJPAAttributeNegative() {
        // Wrong value to bring null
        assertNull(objJPAEdmMappingModelServiceTest.mapJPAAttribute(ENTITY_TYPE_NAME_JPA, ATTRIBUTE_NAME_JPA + "AA"));
    }

    /**
     * Test map JPA relationship.
     */
    @Test
    public void testMapJPARelationship() {
        assertEquals(RELATIONSHIP_NAME_EDM,
                objJPAEdmMappingModelServiceTest.mapJPARelationship(ENTITY_TYPE_NAME_JPA, RELATIONSHIP_NAME_JPA));
    }

    /**
     * Test map JPA relationship negative.
     */
    @Test
    public void testMapJPARelationshipNegative() {
        // Wrong value to bring null
        assertNull(objJPAEdmMappingModelServiceTest.mapJPARelationship(ENTITY_TYPE_NAME_JPA, RELATIONSHIP_NAME_JPA_WRONG));
    }

    /**
     * Test map JPA embeddable type.
     */
    @Test
    public void testMapJPAEmbeddableType() {
        assertEquals("SalesOrderLineItemKey", objJPAEdmMappingModelServiceTest.mapJPAEmbeddableType("SalesOrderItemKey"));
    }

    /**
     * Test map JPA embeddable type negative.
     */
    @Test
    public void testMapJPAEmbeddableTypeNegative() {
        assertNull(objJPAEdmMappingModelServiceTest.mapJPAEmbeddableType(EMBEDDABLE_TYPE_NAME_JPA_WRONG));// Wrong value to
                                                                                                          // bring null
    }

    /**
     * Test map JPA embeddable type attribute.
     */
    @Test
    public void testMapJPAEmbeddableTypeAttribute() {
        assertEquals(EMBEDDABLE_ATTRIBUTE_NAME_EDM,
                objJPAEdmMappingModelServiceTest.mapJPAEmbeddableTypeAttribute(EMBEDDABLE_TYPE_NAME_JPA, EMBEDDABLE_ATTRIBUTE_NAME_JPA));
    }

    /**
     * Test map JPA embeddable type attribute negative.
     */
    @Test
    public void testMapJPAEmbeddableTypeAttributeNegative() {
        assertNull(objJPAEdmMappingModelServiceTest.mapJPAEmbeddableTypeAttribute(EMBEDDABLE_TYPE_NAME_JPA_WRONG,
                EMBEDDABLE_ATTRIBUTE_NAME_JPA));
    }

    /**
     * Test check exclusion of JPA entity type.
     */
    @Test
    public void testCheckExclusionOfJPAEntityType() {
        assertTrue(!objJPAEdmMappingModelServiceTest.checkExclusionOfJPAEntityType(ENTITY_TYPE_NAME_JPA));
    }

    /**
     * Test check exclusion of JPA attribute type.
     */
    @Test
    public void testCheckExclusionOfJPAAttributeType() {
        assertTrue(!objJPAEdmMappingModelServiceTest.checkExclusionOfJPAAttributeType(ENTITY_TYPE_NAME_JPA, ATTRIBUTE_NAME_JPA));
    }

    /**
     * Test check exclusion of JPA embeddable type.
     */
    @Test
    public void testCheckExclusionOfJPAEmbeddableType() {
        assertTrue(!objJPAEdmMappingModelServiceTest.checkExclusionOfJPAEmbeddableType(EMBEDDABLE_TYPE_2_NAME_JPA));
    }

    /**
     * Test check exclusion of JPA embeddable attribute type.
     */
    @Test
    public void testCheckExclusionOfJPAEmbeddableAttributeType() {
        assertTrue(!objJPAEdmMappingModelServiceTest.checkExclusionOfJPAEmbeddableAttributeType(EMBEDDABLE_TYPE_NAME_JPA,
                EMBEDDABLE_ATTRIBUTE_NAME_JPA));
    }

    /**
     * This method is for loading the xml file for testing.
     *
     * @return the input stream
     */
    @Override
    protected InputStream loadMappingModelInputStream() {
        if (VARIANT_MAPPING_FILE == 1) {
            return ClassLoader.getSystemResourceAsStream(MAPPING_FILE_CORRECT);
        }
        return ClassLoader.getSystemResourceAsStream(MAPPING_FILE_INCORRECT);
    }
}
