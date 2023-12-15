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
package org.apache.olingo.odata2.core.edm.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmAssociation;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmNavigationPropertyImplProvTest.
 */
public class EdmNavigationPropertyImplProvTest extends BaseTest {

  /** The edm provider. */
  private static EdmProvider edmProvider;
  
  /** The nav property provider. */
  private static EdmNavigationPropertyImplProv navPropertyProvider;

  /**
   * Setup.
   *
   * @throws Exception the exception
   */
  @BeforeClass
  public static void setup() throws Exception {

    edmProvider = mock(EdmProvider.class);
    EdmImplProv edmImplProv = new EdmImplProv(edmProvider);

    FullQualifiedName relationship = new FullQualifiedName("namespace", "associationName");
    Association association = new Association().setName("associationName");
    when(edmProvider.getAssociation(relationship)).thenReturn(association);

    AssociationEnd end1 = new AssociationEnd().setRole("fromRole");
    FullQualifiedName entityName = new FullQualifiedName("namespace", "entityName");
    AssociationEnd end2 =
        new AssociationEnd().setRole("toRole").setMultiplicity(EdmMultiplicity.ONE).setType(entityName);
    association.setEnd1(end1).setEnd2(end2);

    EntityType entityType = new EntityType().setName("entityName");
    when(edmProvider.getEntityType(entityName)).thenReturn(entityType);

    NavigationProperty navProperty =
        new NavigationProperty().setName("navProperty").setFromRole("fromRole").setToRole("toRole").setRelationship(
            relationship);
    navPropertyProvider = new EdmNavigationPropertyImplProv(edmImplProv, navProperty);
  }

  /**
   * Test navigation property.
   *
   * @throws Exception the exception
   */
  @Test
  public void testNavigationProperty() throws Exception {
    assertNotNull(navPropertyProvider);
    assertEquals("navProperty", navPropertyProvider.getName());
    assertEquals("fromRole", navPropertyProvider.getFromRole());
    assertEquals("toRole", navPropertyProvider.getToRole());
    assertEquals(EdmMultiplicity.ONE, navPropertyProvider.getMultiplicity());
    assertEquals("entityName", navPropertyProvider.getType().getName());

    EdmAssociation association = navPropertyProvider.getRelationship();
    assertNotNull(association);
    assertEquals("associationName", association.getName());
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws Exception the exception
   */
  @Test
  public void getAnnotations() throws Exception {
    EdmAnnotatable annotatable = navPropertyProvider;
    EdmAnnotations annotations = annotatable.getAnnotations();
    assertNull(annotations.getAnnotationAttributes());
    assertNull(annotations.getAnnotationElements());
  }
}
