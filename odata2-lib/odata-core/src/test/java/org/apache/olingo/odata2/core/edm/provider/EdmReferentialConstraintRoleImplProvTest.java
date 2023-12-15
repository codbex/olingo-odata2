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

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.ReferentialConstraintRole;
import org.junit.BeforeClass;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmReferentialConstraintRoleImplProvTest.
 */
public class EdmReferentialConstraintRoleImplProvTest {
  
  /** The referential constraint role prov. */
  private static EdmReferentialConstraintRoleImplProv referentialConstraintRoleProv;

  /**
   * Gets the edm entity container impl.
   *
   * @return the edm entity container impl
   * @throws Exception the exception
   */
  @BeforeClass
  public static void getEdmEntityContainerImpl() throws Exception {
    List<PropertyRef> propertyRefs = new ArrayList<PropertyRef>();
    PropertyRef propertyRef = new PropertyRef().setName("ID");
    propertyRefs.add(propertyRef);

    ReferentialConstraintRole dependent = new ReferentialConstraintRole()
        .setRole("end1Role")
        .setPropertyRefs(propertyRefs);

    referentialConstraintRoleProv = new EdmReferentialConstraintRoleImplProv(dependent);
  }

  /**
   * Test association.
   *
   * @throws Exception the exception
   */
  @Test
  public void testAssociation() throws Exception {
    EdmReferentialConstraintRoleImplProv referentialConstraintRole = referentialConstraintRoleProv;

    assertEquals("end1Role", referentialConstraintRole.getRole());
    assertEquals(1, referentialConstraintRole.getPropertyRefNames().size());
    assertEquals("ID", referentialConstraintRole.getPropertyRefNames().get(0));
  }
}
