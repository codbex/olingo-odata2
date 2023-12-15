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

import java.io.InputStream;
import java.util.List;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;
import org.apache.olingo.odata2.api.edm.EdmAnnotationElement;
import org.apache.olingo.odata2.api.edm.EdmAnnotations;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.testutil.mock.EdmTestProvider;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmxProviderTest.
 */
public class EdmxProviderTest {

  /**
   * Gets the elements with alias.
   *
   * @return the elements with alias
   * @throws Exception the exception
   */
  @Test
  public void getElementsWithAlias() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);

    assertNotNull(edm.getEntityType("Self", "Employee"));
    assertNotNull(edm.getEntityType("Self", "Room"));
    assertNotNull(edm.getEntityType("Self", "Manager"));
    assertNotNull(edm.getEntityType("Self", "Team"));
    assertNotNull(edm.getEntityType("Self", "Building"));

    assertNotNull(edm.getAssociation("Self", "BuildingRooms"));
    assertNotNull(edm.getAssociation("Self", "ManagerEmployees"));
    assertNotNull(edm.getAssociation("Self", "TeamEmployees"));
    assertNotNull(edm.getAssociation("Self", "RoomEmployees"));

    assertNotNull(edm.getComplexType("Self", "c_Location"));
    assertNotNull(edm.getComplexType("Self", "c_City"));
  }

  /**
   * Annotations at simple property.
   *
   * @throws Exception the exception
   */
  @Test
  public void annotationsAtSimpleProperty() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmProperty property = (EdmProperty) edm.getEntityType("Self", "Employee").getProperty("EmployeeName");
    assertNotNull(property);

    EdmAnnotations annotations = property.getAnnotations();
    assertNotNull(annotations);
    List<EdmAnnotationAttribute> annotationAttributes = annotations.getAnnotationAttributes();
    assertNotNull(annotationAttributes);

    List<EdmAnnotationElement> annotationElements = annotations.getAnnotationElements();
    assertNotNull(annotationElements);
    assertEquals(2, annotationElements.size());

    assertNull(annotationElements.get(0).getChildElements());
  }

  /**
   * Null annotations at entity type.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtEntityType() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmAnnotations annotations = edm.getEntityType("Self", "Employee").getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Null annotations at complex type.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtComplexType() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmAnnotations annotations = edm.getComplexType("Self", "c_Location").getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Null annotations at simple property.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtSimpleProperty() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmProperty property = (EdmProperty) edm.getEntityType("Self", "Employee").getProperty("RoomId");
    EdmAnnotations annotations = property.getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Null annotations at complex property.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtComplexProperty() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmProperty property = (EdmProperty) edm.getEntityType("Self", "Employee").getProperty("Location");
    EdmAnnotations annotations = property.getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Null annotations at association.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtAssociation() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmAnnotations annotations = edm.getAssociation("Self", "BuildingRooms").getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Null annotations at association set.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtAssociationSet() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmEntitySet entitySet = edm.getDefaultEntityContainer().getEntitySet("Employees");
    EdmAnnotations annotations =
        edm.getDefaultEntityContainer().getAssociationSet(entitySet,
            (EdmNavigationProperty) entitySet.getEntityType().getProperty("ne_Manager")).getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Null annotations at edm container.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtEdmContainer() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmAnnotations annotations = edm.getDefaultEntityContainer().getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Null annotations at function import.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtFunctionImport() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmAnnotations annotations = edm.getDefaultEntityContainer().getFunctionImport("EmployeeSearch").getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Null annotations at function import parameter.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtFunctionImportParameter() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmAnnotations annotations =
        edm.getDefaultEntityContainer().getFunctionImport("EmployeeSearch").getParameter("q").getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Null annotations at navigation property.
   *
   * @throws Exception the exception
   */
  @Test
  public void nullAnnotationsAtNavigationProperty() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);
    EdmNavigationProperty property =
        (EdmNavigationProperty) edm.getEntityType("Self", "Employee").getProperty("ne_Manager");
    EdmAnnotations annotations = property.getAnnotations();
    checkNullAnnotations(annotations);
  }

  /**
   * Check null annotations.
   *
   * @param annotations the annotations
   */
  private void checkNullAnnotations(final EdmAnnotations annotations) {
    assertNotNull(annotations);

    List<EdmAnnotationAttribute> annotationAttributes = annotations.getAnnotationAttributes();
    assertNull(annotationAttributes);

    List<EdmAnnotationElement> annotationElements = annotations.getAnnotationElements();
    assertNull(annotationElements);
  }

  /**
   * Test entity type.
   *
   * @throws Exception the exception
   */
  @Test
  public void testEntityType() throws Exception {
    Edm edm = createEdm();

    assertNotNull(edm);
    FullQualifiedName fqNameEmployee = new FullQualifiedName("RefScenario", "Employee");
    EdmProvider testProvider = new EdmTestProvider();
    EdmImplProv edmImpl = (EdmImplProv) edm;
    EntityType employee = edmImpl.getEdmProvider().getEntityType(fqNameEmployee);
    EntityType testEmployee = testProvider.getEntityType(fqNameEmployee);
    assertEquals(testEmployee.getName(), employee.getName());
    assertEquals(testEmployee.isHasStream(), employee.isHasStream());
    assertEquals(testEmployee.getProperties().size(), employee.getProperties().size());
    assertEquals(testEmployee.getNavigationProperties().size(), employee.getNavigationProperties().size());

  }

  /**
   * Test association.
   *
   * @throws Exception the exception
   */
  @Test
  public void testAssociation() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);

    FullQualifiedName fqNameAssociation = new FullQualifiedName("RefScenario", "BuildingRooms");
    EdmProvider testProvider = new EdmTestProvider();
    EdmImplProv edmImpl = (EdmImplProv) edm;
    Association association = edmImpl.getEdmProvider().getAssociation(fqNameAssociation);
    Association testAssociation = testProvider.getAssociation(fqNameAssociation);
    assertEquals(testAssociation.getName(), association.getName());
    assertEquals(testAssociation.getEnd1().getMultiplicity(), association.getEnd1().getMultiplicity());
    assertEquals(testAssociation.getEnd2().getRole(), association.getEnd2().getRole());
    assertEquals(testAssociation.getEnd1().getType(), association.getEnd1().getType());

  }

  /**
   * Test association set.
   *
   * @throws Exception the exception
   */
  @Test
  public void testAssociationSet() throws Exception {
    EdmProvider testProvider = new EdmTestProvider();
    Edm edm = createEdm();
    assertNotNull(edm);

    FullQualifiedName fqNameAssociation = new FullQualifiedName("RefScenario", "ManagerEmployees");
    EdmImplProv edmImpl = (EdmImplProv) edm;
    AssociationSet associationSet =
        edmImpl.getEdmProvider().getAssociationSet("Container1", fqNameAssociation, "Managers", "r_Manager");
    AssociationSet testAssociationSet =
        testProvider.getAssociationSet("Container1", fqNameAssociation, "Managers", "r_Manager");
    assertEquals(testAssociationSet.getName(), associationSet.getName());
    assertEquals(testAssociationSet.getEnd1().getEntitySet(), associationSet.getEnd1().getEntitySet());
    assertEquals(testAssociationSet.getEnd2().getEntitySet(), associationSet.getEnd2().getEntitySet());
    assertEquals(testAssociationSet.getEnd2().getRole(), associationSet.getEnd2().getRole());

  }

  /**
   * Test schema.
   *
   * @throws Exception the exception
   */
  @Test
  public void testSchema() throws Exception {
    EdmProvider testProvider = new EdmTestProvider();
    Edm edm = createEdm();
    assertNotNull(edm);

    EdmImplProv edmImpl = (EdmImplProv) edm;
    List<Schema> schemas = edmImpl.getEdmProvider().getSchemas();
    List<Schema> testSchemas = testProvider.getSchemas();
    assertEquals(testSchemas.size(), schemas.size());

    if (!schemas.isEmpty() && !testSchemas.isEmpty()) {
      Schema schema = schemas.get(0);
      Schema testSchema = testSchemas.get(0);
      assertEquals(testSchema.getEntityContainers().size(), schema.getEntityContainers().size());
      assertEquals(testSchema.getEntityTypes().size(), schema.getEntityTypes().size());
      assertEquals(testSchema.getComplexTypes().size(), schema.getComplexTypes().size());
    }
  }

  /**
   * Test container.
   *
   * @throws Exception the exception
   */
  @Test
  public void testContainer() throws Exception {
    EdmProvider testProvider = new EdmTestProvider();
    Edm edm = createEdm();
    assertNotNull(edm);

    EdmImplProv edmImpl = (EdmImplProv) edm;
    EntityContainerInfo container = edmImpl.getEdmProvider().getEntityContainerInfo("Container2");
    EntityContainerInfo testContainer = testProvider.getEntityContainerInfo("Container2");
    assertEquals(testContainer.getName(), container.getName());
    assertEquals(testContainer.isDefaultEntityContainer(), container.isDefaultEntityContainer());

    container = edmImpl.getEdmProvider().getEntityContainerInfo(null);
    testContainer = testProvider.getEntityContainerInfo(null);
    assertNotNull(container);
    assertEquals(testContainer.getName(), container.getName());
    assertEquals(testContainer.isDefaultEntityContainer(), container.isDefaultEntityContainer());
  }

  /**
   * Test entity sets.
   *
   * @throws Exception the exception
   */
  @Test
  public void testEntitySets() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);

    List<EdmEntitySet> entitySets = edm.getEntitySets();
    assertEquals(6, entitySets.size());
  }

  /**
   * Test function imports.
   *
   * @throws Exception the exception
   */
  @Test
  public void testFunctionImports() throws Exception {
    Edm edm = createEdm();
    assertNotNull(edm);

    List<EdmFunctionImport> functionImports = edm.getFunctionImports();
    assertEquals(7, functionImports.size());
  }

  /**
   * Creates the edm.
   *
   * @return the edm
   * @throws Exception the exception
   */
  private Edm createEdm() throws Exception {
    EdmProvider testProvider = new EdmTestProvider();
    ODataResponse response = EntityProvider.writeMetadata(testProvider.getSchemas(), null);
    InputStream in = (InputStream) response.getEntity();
    return EntityProvider.readMetadata(in, true);

  }

}
