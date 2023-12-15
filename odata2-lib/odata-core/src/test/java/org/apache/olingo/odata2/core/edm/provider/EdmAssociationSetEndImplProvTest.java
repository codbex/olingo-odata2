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
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.olingo.odata2.api.edm.EdmAnnotatable;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmAssociationSetEnd;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.provider.AssociationSetEnd;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmAssociationSetEndImplProvTest.
 */
public class EdmAssociationSetEndImplProvTest extends BaseTest {
  
  /** The edm association set end. */
  private static EdmAssociationSetEnd edmAssociationSetEnd;
  
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
    EdmEntityContainer edmEntityContainer = new EdmEntityContainerImplProv(edmImplProv, entityContainer);

    AssociationSetEnd associationSetEnd = new AssociationSetEnd().setRole("end1Role").setEntitySet("entitySetRole1");
    EntitySet entitySet = new EntitySet().setName("entitySetRole1");
    when(edmProvider.getEntitySet("Container", "entitySetRole1")).thenReturn(entitySet);

    edmAssociationSetEnd =
        new EdmAssociationSetEndImplProv(associationSetEnd, edmEntityContainer.getEntitySet("entitySetRole1"));
  }

  /**
   * Test association set end.
   *
   * @throws Exception the exception
   */
  @Test
  public void testAssociationSetEnd() throws Exception {
    EdmAssociationSetEnd setEnd = edmAssociationSetEnd;

    assertEquals("end1Role", setEnd.getRole());
    assertEquals("entitySetRole1", setEnd.getEntitySet().getName());
  }

  /**
   * Gets the annotations.
   *
   * @return the annotations
   * @throws Exception the exception
   */
  @Test
  public void getAnnotations() throws Exception {
    EdmAnnotatable annotatable = (EdmAnnotatable) edmAssociationSetEnd;
    EdmAnnotations annotations = annotatable.getAnnotations();
    assertNull(annotations.getAnnotationAttributes());
    assertNull(annotations.getAnnotationElements());
  }
}
