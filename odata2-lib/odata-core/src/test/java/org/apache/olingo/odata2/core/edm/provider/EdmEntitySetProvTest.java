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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.AssociationSetEnd;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 *  
 *
 */
public class EdmEntitySetProvTest extends BaseTest {

  /** The edm entity set foo. */
  private static EdmEntitySetImplProv edmEntitySetFoo;
  
  /** The edm enitiy set bar. */
  private static EdmEntitySetImplProv edmEnitiySetBar;
  
  /** The edm provider. */
  private static EdmProvider edmProvider;

  /**
   * Gets the edm entity container impl.
   *
   * @return the edm entity container impl
   * @throws Exception the exception
   */
  @BeforeClass
  public static void getEdmEntityContainerImpl() throws Exception {

    edmProvider = mock(EdmProvider.class);
    EdmImplProv edmImplProv = new EdmImplProv(edmProvider);

    EntityContainerInfo entityContainer = new EntityContainerInfo().setName("Container");
    when(edmProvider.getEntityContainerInfo("Container")).thenReturn(entityContainer);
    EdmEntityContainerImplProv edmEntityContainer = new EdmEntityContainerImplProv(edmImplProv, entityContainer);

    EntitySet entitySetFoo = new EntitySet().setName("foo");
    when(edmProvider.getEntitySet("Container", "foo")).thenReturn(entitySetFoo);

    List<NavigationProperty> navigationProperties = new ArrayList<NavigationProperty>();
    FullQualifiedName fooBarAssocName = new FullQualifiedName("namespace", "fooBarAssoc");
    navigationProperties.add(new NavigationProperty().setName("fooBarNav").setFromRole("fromFoo").setRelationship(
        fooBarAssocName).setToRole("toBar"));

    EntityType fooEntityType = new EntityType().setName("fooEntityType").setNavigationProperties(navigationProperties);
    FullQualifiedName fooEntityTypeFullName = new FullQualifiedName("namespace", "fooEntityType");
    entitySetFoo.setEntityType(fooEntityTypeFullName);
    when(edmProvider.getEntityType(fooEntityTypeFullName)).thenReturn(fooEntityType);

    EntitySet entitySetBar = new EntitySet().setName("bar");
    when(edmProvider.getEntitySet("Container", "bar")).thenReturn(entitySetBar);

    EntityType barEntityType = new EntityType().setName("barEntityType");
    FullQualifiedName barEntityTypeFullName = new FullQualifiedName("namespace", "barEntityType");
    entitySetBar.setEntityType(barEntityTypeFullName);
    when(edmProvider.getEntityType(barEntityTypeFullName)).thenReturn(barEntityType);

    AssociationEnd fooEnd = new AssociationEnd().setRole("fromFoo");
    AssociationEnd barEnd = new AssociationEnd().setRole("toBar");

    Association fooBarAssoc = new Association().setName("fooBarAssoc").setEnd1(fooEnd).setEnd2(barEnd);
    when(edmProvider.getAssociation(fooBarAssocName)).thenReturn(fooBarAssoc);

    AssociationSet associationSet =
        new AssociationSet().setName("fooBarRelation").setEnd1(
            new AssociationSetEnd().setRole("fromFoo").setEntitySet("foo")).setEnd2(
            new AssociationSetEnd().setRole("toBar").setEntitySet("bar"));
    FullQualifiedName assocFQName = new FullQualifiedName("namespace", "fooBarAssoc");
    when(edmProvider.getAssociationSet("Container", assocFQName, "foo", "fromFoo")).thenReturn(associationSet);

    edmEntitySetFoo = new EdmEntitySetImplProv(edmImplProv, entitySetFoo, edmEntityContainer);
    edmEnitiySetBar = new EdmEntitySetImplProv(edmImplProv, entitySetBar, edmEntityContainer);
  }

  /**
   * Test entity set 1.
   *
   * @throws Exception the exception
   */
  @Test
  public void testEntitySet1() throws Exception {
    assertEquals("foo", edmEntitySetFoo.getName());
    assertEquals("Container", edmEntitySetFoo.getEntityContainer().getName());
  }

  /**
   * Test entity set 2.
   *
   * @throws Exception the exception
   */
  @Test
  public void testEntitySet2() throws Exception {
    assertEquals("bar", edmEnitiySetBar.getName());
    assertEquals("Container", edmEnitiySetBar.getEntityContainer().getName());
  }

  /**
   * Test entity set navigation.
   *
   * @throws Exception the exception
   */
  @Test
  public void testEntitySetNavigation() throws Exception {
    List<String> navPropertyyNames = edmEntitySetFoo.getEntityType().getNavigationPropertyNames();
    assertTrue(navPropertyyNames.contains("fooBarNav"));
    EdmTyped navProperty = edmEntitySetFoo.getEntityType().getProperty("fooBarNav");
    assertNotNull(navProperty);

    EdmEntitySet relatedEntitySet = edmEntitySetFoo.getRelatedEntitySet((EdmNavigationProperty) navProperty);

    assertEquals(edmEnitiySetBar.getName(), relatedEntitySet.getName());
  }

  /**
   * Test entity set type.
   *
   * @throws Exception the exception
   */
  @Test
  public void testEntitySetType() throws Exception {
    assertEquals("fooEntityType", edmEntitySetFoo.getEntityType().getName());
    assertEquals(edmEntitySetFoo.getEntityType().getName(), edmProvider.getEntityType(
        new FullQualifiedName("namespace", "fooEntityType")).getName());
  }

  /**
   * Check invalid start colon name.
   *
   * @throws Exception the exception
   */
  @Test(expected=EdmException.class)
  public void checkInvalidStartColonName() throws Exception {
    EntitySet provES = new EntitySet().setName(":Name");
    new EdmEntitySetImplProv(null, provES, null);
  }

  /**
   * Check invalid colon name.
   *
   * @throws Exception the exception
   */
  @Test(expected=EdmException.class)
  public void checkInvalidColonName() throws Exception {
    EntitySet provES = new EntitySet().setName("B:Name");
    new EdmEntitySetImplProv(null, provES , null);
  }

  /**
   * Check invalid minus name.
   *
   * @throws Exception the exception
   */
  @Test(expected=EdmException.class)
  public void checkInvalidMinusName() throws Exception {
    EntitySet provES = new EntitySet().setName("My-Name");
    new EdmEntitySetImplProv(null, provES, null);
  }

  /**
   * Check invalid start minus name.
   *
   * @throws Exception the exception
   */
  @Test(expected=EdmException.class)
  public void checkInvalidStartMinusName() throws Exception {
    EntitySet provES = new EntitySet().setName("-Name");
    new EdmEntitySetImplProv(null, provES , null);
  }

  /**
   * Check valid name.
   *
   * @throws Exception the exception
   */
  @Test
  public void checkValidName() throws Exception {
    EntitySet provES = new EntitySet().setName("Содержание");
    EdmEntitySet entitySet = new EdmEntitySetImplProv(null, provES , null);
    assertEquals("Содержание", entitySet.getName());
  }

  /**
   * Check valid name with underscore.
   *
   * @throws Exception the exception
   */
  @Test
  public void checkValidNameWithUnderscore() throws Exception {
    EntitySet provES = new EntitySet().setName("_Name");
    EdmEntitySet entitySet = new EdmEntitySetImplProv(null, provES , null);
    assertEquals("_Name", entitySet.getName());

    provES = new EntitySet().setName("N_ame_");
    entitySet = new EdmEntitySetImplProv(null, provES , null);
    assertEquals("N_ame_", entitySet.getName());
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws Exception the exception
   */
  @Test
  public void getAnnotations() throws Exception {
    EdmAnnotatable annotatable = edmEntitySetFoo;
    EdmAnnotations annotations = annotatable.getAnnotations();
    assertNull(annotations.getAnnotationAttributes());
    assertNull(annotations.getAnnotationElements());
  }

}
