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
package org.apache.olingo.odata2.testutil.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.apache.olingo.odata2.api.commons.ODataHttpMethod;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmComplexType;
import org.apache.olingo.odata2.api.edm.EdmConcurrencyMode;
import org.apache.olingo.odata2.api.edm.EdmCustomizableFeedMappings;
import org.apache.olingo.odata2.api.edm.EdmEntityContainer;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.edm.EdmEntityType;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmFunctionImport;
import org.apache.olingo.odata2.api.edm.EdmMapping;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.edm.EdmParameter;
import org.apache.olingo.odata2.api.edm.EdmProperty;
import org.apache.olingo.odata2.api.edm.EdmServiceMetadata;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.EdmStructuralType;
import org.apache.olingo.odata2.api.edm.EdmTargetPath;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmTypeKind;
import org.apache.olingo.odata2.api.edm.EdmTyped;
import org.apache.olingo.odata2.api.edm.provider.CustomizableFeedMappings;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.mockito.Mockito;

// TODO: Auto-generated Javadoc
/**
 * Mocked Entity Data Model, more or less aligned to the Reference Scenario.
 * 
 */
class EdmMock {

  /**
   * Creates the mock edm.
   *
   * @return the edm
   * @throws ODataException the o data exception
   */
  public static Edm createMockEdm() throws ODataException {
    EdmEntityContainer defaultContainer = mock(EdmEntityContainer.class);
    when(defaultContainer.isDefaultEntityContainer()).thenReturn(true);

    final EdmEntitySet employeeEntitySet =
        createEntitySetMock(defaultContainer, "Employees", EdmSimpleTypeKind.String, "EmployeeId");
    final EdmEntitySet teamEntitySet = createEntitySetMock(defaultContainer, "Teams", EdmSimpleTypeKind.String, "Id");
    final EdmEntitySet roomEntitySet = createEntitySetMock(defaultContainer, "Rooms", EdmSimpleTypeKind.String, "Id");
    final EdmEntitySet managerEntitySet =
        createEntitySetMock(defaultContainer, "Managers", EdmSimpleTypeKind.String, "EmployeeId");
    final EdmEntitySet buildingEntitySet =
        createEntitySetMock(defaultContainer, "Buildings", EdmSimpleTypeKind.String, "Id");
    final EdmEntitySet companiesEntitySet =
        createEntitySetMock(defaultContainer, "Companys", EdmSimpleTypeKind.String, "Id");
    final EdmEntitySet organizationsEntitySet =
        createEntitySetMock(defaultContainer, "Organizations", EdmSimpleTypeKind.String, "Id");

    EdmEntityType employeeType = employeeEntitySet.getEntityType();
    when(employeeType.hasStream()).thenReturn(true);
    EdmMapping employeeTypeMapping = Mockito.mock(EdmMapping.class);
    when(employeeTypeMapping.getMediaResourceMimeTypeKey()).thenReturn("getImageType");
    when(employeeType.getMapping()).thenReturn(employeeTypeMapping);
    when(employeeType.getPropertyNames()).thenReturn(Arrays.asList(
        "EmployeeId", "EmployeeName", "ManagerId", "RoomId", "TeamId",
        "Location", "Age", "EntryDate", "ImageUrl"));
    when(employeeType.getNavigationPropertyNames()).thenReturn(Arrays.asList("ne_Manager", "ne_Team", "ne_Room"));

    EdmProperty employeeNameProperty = createProperty("EmployeeName", EdmSimpleTypeKind.String, employeeType);
    EdmCustomizableFeedMappings employeeTitleMappings = mock(EdmCustomizableFeedMappings.class);
    when(employeeTitleMappings.getFcTargetPath()).thenReturn(EdmTargetPath.SYNDICATION_TITLE);
    when(employeeTitleMappings.isFcKeepInContent()).thenReturn(true);
    when(employeeNameProperty.getCustomizableFeedMappings()).thenReturn(employeeTitleMappings);

    createProperty("ManagerId", EdmSimpleTypeKind.String, employeeType);
    createProperty("RoomId", EdmSimpleTypeKind.String, employeeType);
    createProperty("TeamId", EdmSimpleTypeKind.String, employeeType);
    createProperty("Age", EdmSimpleTypeKind.Int32, employeeType);

    EdmProperty employeeEntryDateProperty = createProperty("EntryDate", EdmSimpleTypeKind.DateTime, employeeType);
    EdmCustomizableFeedMappings employeeUpdatedMappings = mock(EdmCustomizableFeedMappings.class);
    when(employeeUpdatedMappings.getFcTargetPath()).thenReturn(EdmTargetPath.SYNDICATION_UPDATED);
    when(employeeUpdatedMappings.isFcKeepInContent()).thenReturn(true);
    when(employeeEntryDateProperty.getCustomizableFeedMappings()).thenReturn(employeeUpdatedMappings);
    EdmFacets employeeEntryDateFacets = mock(EdmFacets.class);
    when(employeeEntryDateFacets.getMaxLength()).thenReturn(null);
    when(employeeEntryDateFacets.isNullable()).thenReturn(Boolean.TRUE);
    when(employeeEntryDateProperty.getFacets()).thenReturn(employeeEntryDateFacets);

    createProperty("ImageUrl", EdmSimpleTypeKind.String, employeeType);

    final EdmComplexType locationComplexType = mock(EdmComplexType.class);
    when(locationComplexType.getKind()).thenReturn(EdmTypeKind.COMPLEX);
    when(locationComplexType.getName()).thenReturn("c_Location");
    when(locationComplexType.getNamespace()).thenReturn("RefScenario");
    when(locationComplexType.getPropertyNames()).thenReturn(Arrays.asList("City", "Country"));

    final EdmProperty locationComplexProperty = mock(EdmProperty.class);
    when(locationComplexProperty.getType()).thenReturn(locationComplexType);
    when(locationComplexProperty.getName()).thenReturn("Location");
    when(employeeType.getProperty("Location")).thenReturn(locationComplexProperty);
    createProperty("Country", EdmSimpleTypeKind.String, locationComplexType);

    final EdmComplexType cityComplexType = mock(EdmComplexType.class);
    when(cityComplexType.getKind()).thenReturn(EdmTypeKind.COMPLEX);
    when(cityComplexType.getName()).thenReturn("c_City");
    when(cityComplexType.getNamespace()).thenReturn("RefScenario");
    when(cityComplexType.getPropertyNames()).thenReturn(Arrays.asList("PostalCode", "CityName"));

    EdmProperty cityProperty = mock(EdmProperty.class);
    when(cityProperty.getType()).thenReturn(cityComplexType);
    when(cityProperty.getName()).thenReturn("City");
    when(locationComplexType.getProperty("City")).thenReturn(cityProperty);

    createProperty("PostalCode", EdmSimpleTypeKind.String, cityComplexType);
    createProperty("CityName", EdmSimpleTypeKind.String, cityComplexType);

    createNavigationProperty("ne_Manager", EdmMultiplicity.ONE, employeeEntitySet, managerEntitySet);
    createNavigationProperty("ne_Team", EdmMultiplicity.ONE, employeeEntitySet, teamEntitySet);
    createNavigationProperty("ne_Room", EdmMultiplicity.ONE, employeeEntitySet, roomEntitySet);

    EdmEntityType teamType = teamEntitySet.getEntityType();
    when(teamType.getPropertyNames()).thenReturn(Arrays.asList("Id", "Name", "isScrumTeam"));
    createProperty("Name", EdmSimpleTypeKind.String, teamType);
    createProperty("isScrumTeam", EdmSimpleTypeKind.Boolean, teamType);
    when(teamType.getNavigationPropertyNames()).thenReturn(Arrays.asList("nt_Employees"));
    createNavigationProperty("nt_Employees", EdmMultiplicity.MANY, teamEntitySet, employeeEntitySet);

    EdmEntityType roomType = roomEntitySet.getEntityType();
    when(roomType.getPropertyNames()).thenReturn(Arrays.asList("Id", "Name", "Seats", "Version"));
    EdmProperty roomId = roomEntitySet.getEntityType().getKeyProperties().get(0);
    EdmFacets roomIdFacets = mock(EdmFacets.class);
    when(roomIdFacets.getMaxLength()).thenReturn(100);
    when(roomId.getFacets()).thenReturn(roomIdFacets);
    createProperty("Name", EdmSimpleTypeKind.String, roomType);
    createProperty("Seats", EdmSimpleTypeKind.Int16, roomType);
    EdmProperty roomVersion = createProperty("Version", EdmSimpleTypeKind.Int16, roomType);
    EdmFacets roomVersionFacets = mock(EdmFacets.class);
    when(roomVersionFacets.getConcurrencyMode()).thenReturn(EdmConcurrencyMode.Fixed);
    when(roomVersionFacets.isNullable()).thenReturn(null);
    when(roomVersion.getFacets()).thenReturn(roomVersionFacets);
    when(roomType.getNavigationPropertyNames()).thenReturn(Arrays.asList("nr_Employees", "nr_Building"));
    createNavigationProperty("nr_Employees", EdmMultiplicity.MANY, roomEntitySet, employeeEntitySet);
    createNavigationProperty("nr_Building", EdmMultiplicity.ONE, roomEntitySet, buildingEntitySet);

    createNavigationProperty("ne_Manager", EdmMultiplicity.ONE, managerEntitySet, managerEntitySet);
    createNavigationProperty("ne_Team", EdmMultiplicity.ONE, managerEntitySet, teamEntitySet);
    createNavigationProperty("ne_Room", EdmMultiplicity.ONE, managerEntitySet, roomEntitySet);
    createNavigationProperty("nm_Employees", EdmMultiplicity.MANY, managerEntitySet, employeeEntitySet);

    EdmEntityType buildingType = buildingEntitySet.getEntityType();
    when(buildingType.getPropertyNames()).thenReturn(Arrays.asList("Id", "Name", "Image"));
    createProperty("Name", EdmSimpleTypeKind.String, buildingType);
    createProperty("Image", EdmSimpleTypeKind.Binary, buildingType);
    when(buildingType.getNavigationPropertyNames()).thenReturn(Arrays.asList("nb_Rooms"));
    createNavigationProperty("nb_Rooms", EdmMultiplicity.MANY, buildingEntitySet, roomEntitySet);

    EdmEntityType companyType = companiesEntitySet.getEntityType();
    when(companyType.getPropertyNames()).thenReturn(Arrays.asList("Id", "Name", "Kind", "NGO", "Location"));
    when(companyType.getProperty("Location")).thenReturn(locationComplexProperty);
    createProperty("Name", EdmSimpleTypeKind.String, companyType);
    createProperty("Kind", EdmSimpleTypeKind.String, companyType);
    createProperty("NGO", EdmSimpleTypeKind.Boolean, companyType);

    EdmEntityType organizationType = organizationsEntitySet.getEntityType();
    when(organizationType.getPropertyNames()).thenReturn(Arrays.asList("Id", "Name", "Kind", "Location", "NoOfTeam"
    		, "Revenue"));
    when(organizationType.getProperty("Location")).thenReturn(locationComplexProperty);
    EdmProperty orgName = createProperty("Name", EdmSimpleTypeKind.String, organizationType);
    EdmProperty orgKind = createProperty("Kind", EdmSimpleTypeKind.String, organizationType);
    createProperty("NoOfTeam", EdmSimpleTypeKind.Int16, organizationType);
    createProperty("Revenue", EdmSimpleTypeKind.Decimal, organizationType);
    
    EdmFacets orgNameFacets = mock(EdmFacets.class);
    when(orgNameFacets.isNullable()).thenReturn(null);
    when(orgName.getFacets()).thenReturn(orgNameFacets);
    EdmCustomizableFeedMappings orgTitleMappings = mock(EdmCustomizableFeedMappings.class);
    when(orgTitleMappings.getFcTargetPath()).thenReturn(EdmTargetPath.SYNDICATION_TITLE);
    when(orgTitleMappings.isFcKeepInContent()).thenReturn(false);
    when(orgName.getCustomizableFeedMappings()).thenReturn(orgTitleMappings);
    
    EdmFacets orgKindFacets = mock(EdmFacets.class);
    when(orgKindFacets.isNullable()).thenReturn(null);
    when(orgKind.getFacets()).thenReturn(orgKindFacets);
    EdmCustomizableFeedMappings orgKindMappings = mock(EdmCustomizableFeedMappings.class);
    when(orgKindMappings.getFcTargetPath()).thenReturn(EdmTargetPath.SYNDICATION_SUMMARY);
    when(orgKindMappings.isFcKeepInContent()).thenReturn(true);
    when(orgKind.getCustomizableFeedMappings()).thenReturn(orgKindMappings);
    
    EdmFunctionImport employeeSearchFunctionImport =
        createFunctionImportMock(defaultContainer, "EmployeeSearch", employeeType, EdmMultiplicity.MANY);
    when(employeeSearchFunctionImport.getEntitySet()).thenReturn(employeeEntitySet);
    EdmParameter employeeSearchParameter = mock(EdmParameter.class);
    when(employeeSearchParameter.getType()).thenReturn(EdmSimpleTypeKind.String.getEdmSimpleTypeInstance());
    when(employeeSearchFunctionImport.getParameterNames()).thenReturn(Arrays.asList("q"));
    when(employeeSearchFunctionImport.getParameter("q")).thenReturn(employeeSearchParameter);
    when(employeeSearchParameter.getName()).thenReturn("q");    
    EdmFunctionImport buildingSearchFunctionImport =
        createFunctionImportMock(defaultContainer, "BuildingSearch", employeeType, EdmMultiplicity.MANY);
    when(buildingSearchFunctionImport.getEntitySet()).thenReturn(buildingEntitySet);
    EdmParameter buildingSearchParameter1 = mock(EdmParameter.class);
    when(buildingSearchParameter1.getType()).thenReturn(EdmSimpleTypeKind.String.getEdmSimpleTypeInstance());
    when(buildingSearchFunctionImport.getParameterNames()).thenReturn(Arrays.asList("q", "r"));
    when(buildingSearchFunctionImport.getParameter("q")).thenReturn(buildingSearchParameter1);
    when(buildingSearchParameter1.getName()).thenReturn("q");
    EdmParameter buildingSearchParameter2 = mock(EdmParameter.class);
    when(buildingSearchParameter2.getType()).thenReturn(EdmSimpleTypeKind.Int16.getEdmSimpleTypeInstance());
    when(buildingSearchFunctionImport.getParameter("r")).thenReturn(buildingSearchParameter2);
    when(buildingSearchParameter2.getName()).thenReturn("r");
    createFunctionImportMock(defaultContainer, "AllLocations", locationComplexType, EdmMultiplicity.MANY);
    createFunctionImportMock(defaultContainer, "AllUsedRoomIds", EdmSimpleTypeKind.String.getEdmSimpleTypeInstance(),
        EdmMultiplicity.MANY);
    createFunctionImportMock(defaultContainer, "MaximalAge", EdmSimpleTypeKind.Int16.getEdmSimpleTypeInstance(),
        EdmMultiplicity.ONE);
    createFunctionImportMock(defaultContainer, "MostCommonLocation", locationComplexType, EdmMultiplicity.ONE);
    EdmFunctionImport managerPhotoFunctionImport =
        createFunctionImportMock(defaultContainer, "ManagerPhoto", EdmSimpleTypeKind.Binary.getEdmSimpleTypeInstance(),
            EdmMultiplicity.ONE);
    EdmParameter managerPhotoParameter = mock(EdmParameter.class);
    when(managerPhotoParameter.getType()).thenReturn(EdmSimpleTypeKind.String.getEdmSimpleTypeInstance());
    EdmFacets managerPhotoParameterFacets = mock(EdmFacets.class);
    when(managerPhotoParameterFacets.isNullable()).thenReturn(false);
    when(managerPhotoParameter.getFacets()).thenReturn(managerPhotoParameterFacets);
    when(managerPhotoParameter.getName()).thenReturn("Id");
    when(managerPhotoFunctionImport.getParameterNames()).thenReturn(Arrays.asList("Id"));
    when(managerPhotoFunctionImport.getParameter("Id")).thenReturn(managerPhotoParameter);
    EdmFunctionImport oldestEmployeeFunctionImport =
        createFunctionImportMock(defaultContainer, "OldestEmployee", employeeType, EdmMultiplicity.ONE);
    when(oldestEmployeeFunctionImport.getEntitySet()).thenReturn(employeeEntitySet);
    EdmFunctionImport employeeActionImport =
        createFunctionImportMock(defaultContainer, "SetEmployee", null, EdmMultiplicity.ONE);
    when(employeeActionImport.getEntitySet()).thenReturn(employeeEntitySet);
    when(employeeActionImport.getHttpMethod()).thenReturn(ODataHttpMethod.POST.name());

    //Issue no return type
    EdmFunctionImport addEmployeeActionImport =
        createActionImportMock(defaultContainer, "AddEmployee", null, EdmMultiplicity.ONE);
    when(addEmployeeActionImport.getEntitySet()).thenReturn(employeeEntitySet);
    when(addEmployeeActionImport.getHttpMethod()).thenReturn(ODataHttpMethod.POST.name());
    
    
    // Issue with not explicitly nullable parameters and facets
    EdmFunctionImport functionImportNullableParameter =
        createFunctionImportMock(defaultContainer, "FINullableParameter", EdmSimpleTypeKind.Boolean
            .getEdmSimpleTypeInstance(), EdmMultiplicity.ONE);
    EdmParameter nullableFIParameter = mock(EdmParameter.class);
    when(nullableFIParameter.getType()).thenReturn(EdmSimpleTypeKind.String.getEdmSimpleTypeInstance());
    when(nullableFIParameter.getName()).thenReturn("Id");
    EdmFacets nullableFIParameterFacets = mock(EdmFacets.class);
    when(nullableFIParameterFacets.isNullable()).thenReturn(null);
    when(nullableFIParameterFacets.getMaxLength()).thenReturn(new Integer(1));
    when(nullableFIParameter.getFacets()).thenReturn(nullableFIParameterFacets);
    when(functionImportNullableParameter.getParameterNames()).thenReturn(Arrays.asList("Id"));
    when(functionImportNullableParameter.getParameter("Id")).thenReturn(nullableFIParameter);

    EdmEntityContainer specificContainer = mock(EdmEntityContainer.class);
    when(specificContainer.getEntitySet("Employees")).thenReturn(employeeEntitySet);
    when(specificContainer.getName()).thenReturn("Container1");

    EdmEntityType photoEntityType = mock(EdmEntityType.class);
    when(photoEntityType.getName()).thenReturn("Photo");
    when(photoEntityType.getNamespace()).thenReturn("RefScenario2");
    when(photoEntityType.getPropertyNames()).thenReturn(Arrays.asList(
        "Id", "Name", "Type", "Image", "BinaryData", "Содержание", "CustomProperty"));
    when(photoEntityType.getKeyPropertyNames()).thenReturn(Arrays.asList("Id", "Type"));
    when(photoEntityType.hasStream()).thenReturn(true);
    EdmMapping photoEntityTypeMapping = Mockito.mock(EdmMapping.class);
    when(photoEntityTypeMapping.getMediaResourceMimeTypeKey()).thenReturn("getType");
    when(photoEntityType.getMapping()).thenReturn(photoEntityTypeMapping);
    EdmProperty photoIdProperty = createProperty("Id", EdmSimpleTypeKind.Int32, photoEntityType);
    EdmFacets photoIdFacet = mock(EdmFacets.class);
    when(photoIdFacet.getConcurrencyMode()).thenReturn(EdmConcurrencyMode.Fixed);
    when(photoIdProperty.getFacets()).thenReturn(photoIdFacet);

    createProperty("Name", EdmSimpleTypeKind.String, photoEntityType);
    final EdmProperty photoTypeProperty = createProperty("Type", EdmSimpleTypeKind.String, photoEntityType);
    when(photoEntityType.getKeyProperties()).thenReturn(Arrays.asList(photoIdProperty, photoTypeProperty));

    EdmProperty photoImageProperty = createProperty("Image", EdmSimpleTypeKind.Binary, photoEntityType);
    EdmMapping imageMapping = mock(EdmMapping.class);
    when(imageMapping.getMediaResourceMimeTypeKey()).thenReturn("getImageType");
    when(photoImageProperty.getMapping()).thenReturn(imageMapping);

    EdmProperty binaryDataProperty = createProperty("BinaryData", EdmSimpleTypeKind.Binary, photoEntityType);
    when(binaryDataProperty.getMimeType()).thenReturn("image/jpeg");

    EdmProperty photoRussianProperty = createProperty("Содержание", EdmSimpleTypeKind.String, photoEntityType);
    EdmFacets photoRussianFacets = mock(EdmFacets.class);
    when(photoRussianFacets.isNullable()).thenReturn(true);
    when(photoRussianFacets.isUnicode()).thenReturn(true);
    when(photoRussianFacets.getMaxLength()).thenReturn(Integer.MAX_VALUE);
    when(photoRussianProperty.getFacets()).thenReturn(photoRussianFacets);
    CustomizableFeedMappings photoRussianMapping = mock(CustomizableFeedMappings.class);
    when(photoRussianMapping.getFcKeepInContent()).thenReturn(false);
    when(photoRussianMapping.getFcNsPrefix()).thenReturn("ру");
    when(photoRussianMapping.getFcNsUri()).thenReturn("http://localhost");
    when(photoRussianMapping.getFcTargetPath()).thenReturn("Содержание");
    when(photoRussianProperty.getCustomizableFeedMappings()).thenReturn(photoRussianMapping);

    EdmProperty customProperty = createProperty("CustomProperty", EdmSimpleTypeKind.String, photoEntityType);
    CustomizableFeedMappings customFeedMapping = mock(CustomizableFeedMappings.class);
    when(customFeedMapping.getFcKeepInContent()).thenReturn(false);
    when(customFeedMapping.getFcNsPrefix()).thenReturn("custom");
    when(customFeedMapping.getFcNsUri()).thenReturn("http://localhost");
    when(customFeedMapping.getFcTargetPath()).thenReturn("TarPath");
    when(customProperty.getCustomizableFeedMappings()).thenReturn(customFeedMapping);

    EdmEntitySet photoEntitySet = mock(EdmEntitySet.class);
    when(photoEntitySet.getName()).thenReturn("Photos");
    when(photoEntitySet.getEntityType()).thenReturn(photoEntityType);
    EdmEntityContainer photoContainer = mock(EdmEntityContainer.class);
    when(photoContainer.isDefaultEntityContainer()).thenReturn(false);
    when(photoContainer.getEntitySet("Photos")).thenReturn(photoEntitySet);
    when(photoContainer.getName()).thenReturn("Container2");

    when(photoEntitySet.getEntityContainer()).thenReturn(photoContainer);

    Edm edm = mock(Edm.class);
    EdmServiceMetadata serviceMetadata = mock(EdmServiceMetadata.class);
    when(serviceMetadata.getDataServiceVersion()).thenReturn("MockEdm");
    when(edm.getServiceMetadata()).thenReturn(serviceMetadata);
    when(edm.getDefaultEntityContainer()).thenReturn(defaultContainer);
    when(edm.getEntityContainer("Container1")).thenReturn(specificContainer);
    when(edm.getEntityContainer("Container2")).thenReturn(photoContainer);
    when(edm.getEntityType("RefScenario", "Employee")).thenReturn(employeeType);
    when(edm.getEntityType("RefScenario", "Team")).thenReturn(teamType);
    when(edm.getEntityType("RefScenario", "Room")).thenReturn(roomType);
    when(edm.getEntityType("RefScenario", "Building")).thenReturn(buildingType);
    when(edm.getComplexType("RefScenario", "c_Location")).thenReturn(locationComplexType);
    when(edm.getEntityType("RefScenario2", "Photo")).thenReturn(photoEntityType);
    when(edm.getEntityType("RefScenario", "Company")).thenReturn(companyType);
    when(edm.getEntityType("RefScenario", "Organization")).thenReturn(organizationType);
    EdmFunctionImport photoSearchFunctionImport =
    createFunctionImportMock(photoContainer, "PhotoSearch", photoEntityType,
        EdmMultiplicity.ONE);
    when(photoSearchFunctionImport.getEntitySet()).thenReturn(photoEntitySet);
    EdmParameter photoParameter1 = mock(EdmParameter.class);
    when(photoParameter1.getType()).thenReturn(EdmSimpleTypeKind.Int16.getEdmSimpleTypeInstance());
    EdmFacets photoParameterFacets = mock(EdmFacets.class);
    when(photoParameterFacets.isNullable()).thenReturn(true);
    when(photoParameter1.getFacets()).thenReturn(photoParameterFacets);
    when(photoParameter1.getName()).thenReturn("Id");
    EdmParameter photoParameter2 = mock(EdmParameter.class);
    when(photoParameter2.getType()).thenReturn(EdmSimpleTypeKind.String.getEdmSimpleTypeInstance());
    when(photoParameter2.getName()).thenReturn("Type");
    when(photoSearchFunctionImport.getParameterNames()).thenReturn(Arrays.asList("Id","Type"));
    when(photoSearchFunctionImport.getParameter("Id")).thenReturn(photoParameter1);
    when(photoSearchFunctionImport.getParameter("Type")).thenReturn(photoParameter2);
    return edm;
  }

  /**
   * Creates the navigation property.
   *
   * @param name the name
   * @param multiplicity the multiplicity
   * @param entitySet the entity set
   * @param targetEntitySet the target entity set
   * @return the edm navigation property
   * @throws EdmException the edm exception
   */
  private static EdmNavigationProperty createNavigationProperty(final String name, final EdmMultiplicity multiplicity,
      final EdmEntitySet entitySet, final EdmEntitySet targetEntitySet) throws EdmException {
    EdmType navigationType = mock(EdmType.class);
    when(navigationType.getKind()).thenReturn(EdmTypeKind.ENTITY);

    EdmNavigationProperty navigationProperty = mock(EdmNavigationProperty.class);
    when(navigationProperty.getName()).thenReturn(name);
    EdmType type = targetEntitySet.getEntityType();
    when(navigationProperty.getType()).thenReturn(type);
    when(navigationProperty.getMultiplicity()).thenReturn(multiplicity);

    when(entitySet.getEntityType().getProperty(name)).thenReturn(navigationProperty);
    when(entitySet.getRelatedEntitySet(navigationProperty)).thenReturn(targetEntitySet);

    return navigationProperty;
  }

  /**
   * Creates the property.
   *
   * @param name the name
   * @param kind the kind
   * @param entityType the entity type
   * @return the edm property
   * @throws EdmException the edm exception
   */
  private static EdmProperty createProperty(final String name, final EdmSimpleTypeKind kind,
      final EdmStructuralType entityType) throws EdmException {
    EdmProperty property = mock(EdmProperty.class);
    when(property.getType()).thenReturn(kind.getEdmSimpleTypeInstance());
    when(property.getName()).thenReturn(name);
    when(entityType.getProperty(name)).thenReturn(property);
    return property;
  }

  /**
   * Creates the entity set mock.
   *
   * @param container the container
   * @param name the name
   * @param kind the kind
   * @param keyPropertyId the key property id
   * @return the edm entity set
   * @throws EdmException the edm exception
   */
  private static EdmEntitySet createEntitySetMock(final EdmEntityContainer container, final String name,
      final EdmSimpleTypeKind kind, final String keyPropertyId) throws EdmException {
    final EdmEntityType entityType = createEntityTypeMock(name.substring(0, name.length() - 1), kind, keyPropertyId);

    EdmEntitySet entitySet = mock(EdmEntitySet.class);
    when(entitySet.getName()).thenReturn(name);
    when(entitySet.getEntityType()).thenReturn(entityType);

    when(entitySet.getEntityContainer()).thenReturn(container);

    when(container.getEntitySet(name)).thenReturn(entitySet);

    return entitySet;
  }

  /**
   * Creates the entity type mock.
   *
   * @param name the name
   * @param kind the kind
   * @param keyPropertyId the key property id
   * @return the edm entity type
   * @throws EdmException the edm exception
   */
  private static EdmEntityType createEntityTypeMock(final String name, final EdmSimpleTypeKind kind,
      final String keyPropertyId) throws EdmException {
    EdmEntityType entityType = mock(EdmEntityType.class);
    when(entityType.getName()).thenReturn(name);
    when(entityType.getNamespace()).thenReturn("RefScenario");

    final EdmProperty keyProperty = createProperty(keyPropertyId, kind, entityType);
    EdmFacets facets = mock(EdmFacets.class);
    when(facets.getMaxLength()).thenReturn(null);
    when(facets.isNullable()).thenReturn(false);
    when(keyProperty.getFacets()).thenReturn(facets);

    when(entityType.getKind()).thenReturn(EdmTypeKind.ENTITY);
    when(entityType.getPropertyNames()).thenReturn(Arrays.asList(keyPropertyId));
    when(entityType.getKeyPropertyNames()).thenReturn(Arrays.asList(keyPropertyId));
    when(entityType.getKeyProperties()).thenReturn(Arrays.asList(keyProperty));

    return entityType;
  }

  /**
   * Creates the function import mock.
   *
   * @param container the container
   * @param name the name
   * @param type the type
   * @param multiplicity the multiplicity
   * @return the edm function import
   * @throws EdmException the edm exception
   */
  private static EdmFunctionImport createFunctionImportMock(final EdmEntityContainer container, final String name,
      final EdmType type, final EdmMultiplicity multiplicity) throws EdmException {
    EdmTyped returnType = mock(EdmTyped.class);
    when(returnType.getType()).thenReturn(type);
    when(returnType.getMultiplicity()).thenReturn(multiplicity);

    EdmFunctionImport functionImport = mock(EdmFunctionImport.class);
    when(functionImport.getName()).thenReturn(name);
    when(functionImport.getReturnType()).thenReturn(returnType);
    when(functionImport.getHttpMethod()).thenReturn(ODataHttpMethod.GET.name());

    when(container.getFunctionImport(name)).thenReturn(functionImport);

    return functionImport;
  }
  
  
  
  /**
   * Creates the action import mock.
   *
   * @param container the container
   * @param name the name
   * @param type the type
   * @param multiplicity the multiplicity
   * @return the edm function import
   * @throws EdmException the edm exception
   */
  private static EdmFunctionImport createActionImportMock(final EdmEntityContainer container, final String name,
      final EdmType type, final EdmMultiplicity multiplicity) throws EdmException {

    EdmFunctionImport functionImport = mock(EdmFunctionImport.class);
    when(functionImport.getName()).thenReturn(name);
    when(functionImport.getReturnType()).thenReturn(null);
    when(functionImport.getHttpMethod()).thenReturn(ODataHttpMethod.GET.name());

    when(container.getFunctionImport(name)).thenReturn(functionImport);

    return functionImport;
  }
}
