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
package org.apache.olingo.odata2.core.ep.consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.olingo.odata2.api.ODataServiceVersion;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmAction;
import org.apache.olingo.odata2.api.edm.EdmConcurrencyMode;
import org.apache.olingo.odata2.api.edm.EdmContentKind;
import org.apache.olingo.odata2.api.edm.EdmFacets;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.AssociationSetEnd;
import org.apache.olingo.odata2.api.edm.provider.ComplexProperty;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.DataServices;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.FunctionImport;
import org.apache.olingo.odata2.api.edm.provider.FunctionImportParameter;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.testutil.helper.StringHelper;
import org.apache.olingo.odata2.testutil.mock.EdmTestProvider;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlMetadataConsumerTest.
 */
public class XmlMetadataConsumerTest extends AbstractXmlConsumerTest {

  /**
   * Instantiates a new xml metadata consumer test.
   *
   * @param type the type
   */
  public XmlMetadataConsumerTest(final StreamWriterImplType type) {
    super(type);
  }

  /** The Constant DEFAULT_VALUE. */
  private static final String DEFAULT_VALUE = "Photo";
  
  /** The Constant FC_TARGET_PATH. */
  private static final String FC_TARGET_PATH = "Содержание";
  
  /** The Constant FC_NS_URI. */
  private static final String FC_NS_URI = "http://localhost";
  
  /** The Constant FC_NS_PREFIX. */
  private static final String FC_NS_PREFIX = "ру";
  
  /** The Constant FC_KEEP_IN_CONTENT. */
  private static final Boolean FC_KEEP_IN_CONTENT = Boolean.FALSE;
  
  /** The Constant NAMESPACE. */
  private static final String NAMESPACE = "RefScenario";
  
  /** The Constant NAMESPACE2. */
  private static final String NAMESPACE2 = "RefScenario2";
  
  /** The Constant MIME_TYPE. */
  private static final String MIME_TYPE = "image/jpeg";
  
  /** The Constant ASSOCIATION. */
  private static final String ASSOCIATION = "ManagerEmployees";
  
  /** The Constant MAX_LENGTH. */
  private static final int MAX_LENGTH = 4;

  /** The property names. */
  private final String[] propertyNames = { "EmployeeId", "EmployeeName", "Location" };

  /** The xml. */
  private final String xml = "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
      + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
      + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
      + "<EntityType Name= \"Employee\" m:HasStream=\"true\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
      + "<Property Name=\"" + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>" + "<Property Name=\""
      + propertyNames[1] + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>" + "<Property Name=\""
      + propertyNames[2] + "\" Type=\"RefScenario.c_Location\" Nullable=\"false\"/>" + "</EntityType>"
      + "<ComplexType Name=\"c_Location\">" + "<Property Name=\"Country\" Type=\"Edm.String\"/>" + "</ComplexType>"
      + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";

  /** The xml 2. */
  private final String xml2 = "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06
      + "\" xmlns:prefix=\"namespace\">" + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
      + Edm.NAMESPACE_M_2007_08 + "\">" + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\""
      + Edm.NAMESPACE_EDM_2008_01 + "\">" + "<prefix:schemaElement>text3</prefix:schemaElement>"
      + "<EntityType Name= \"Employee\" m:HasStream=\"true\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
      + "<Property Name=\"" + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>" + "<Property Name=\""
      + propertyNames[1] + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>" + "<Property Name=\""
      + propertyNames[2] + "\" Type=\"RefScenario.c_Location\" Nullable=\"false\"/>" + "</EntityType>"
      + "<ComplexType Name=\"c_Location\">" + "<Property Name=\"Country\" Type=\"Edm.String\"/>" + "</ComplexType>"
      + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";

  /** The xml with base type. */
  private final String xmlWithBaseType = "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06
      + "\">" + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
      + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
      + "<EntityType Name= \"Employee\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>" + "<Property Name=\""
      + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>" + "<Property Name=\"" + propertyNames[1]
      + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>" + "<Property Name=\"" + propertyNames[2]
      + "\" Type=\"RefScenario.c_Location\" Nullable=\"false\"/>" + "</EntityType>"
      + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">" + "</EntityType>"
      + "<ComplexType Name=\"c_Location\">" + "<Property Name=\"Country\" Type=\"Edm.String\"/>" + "</ComplexType>"
      + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";

  /** The xml with association. */
  private final String xmlWithAssociation =
      "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
          + Edm.NAMESPACE_EDMX_2007_06
          + "\">"
          + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
          + Edm.NAMESPACE_M_2007_08
          + "\">"
          + "<Schema Namespace=\""
          + NAMESPACE
          + "\" xmlns=\""
          + Edm.NAMESPACE_EDM_2008_09
          + "\">"
          + "<EntityType Name= \"Employee\">"
          + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
          + "<Property Name=\""
          + propertyNames[0]
          + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
          + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployees\" " +
          "FromRole=\"r_Employees\" ToRole=\"r_Manager\" />"
          + "</EntityType>"
          + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
          + "<NavigationProperty Name=\"nm_Employees\" Relationship=\"RefScenario.ManagerEmployees\" " +
          "FromRole=\"r_Manager\" ToRole=\"r_Employees\" />"
          + "</EntityType>" + "<Association Name=\"" + ASSOCIATION + "\">"
          + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"r_Employees\">"
          + "<OnDelete Action=\"Cascade\"/>" + "</End>"
          + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"r_Manager\"/>" + "</Association>"
          + "</Schema>" + "<Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
          + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
          + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
          + "<EntitySet Name=\"Managers\" EntityType=\"RefScenario.Manager\"/>" + "<AssociationSet Name=\""
          + ASSOCIATION + "\" Association=\"RefScenario." + ASSOCIATION + "\">"
          + "<End EntitySet=\"Managers\" Role=\"r_Manager\"/>" + "<End EntitySet=\"Employees\" Role=\"r_Employees\"/>"
          + "</AssociationSet>" + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";

  /** The xml with two schemas. */
  private final String xmlWithTwoSchemas = "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06
      + "\">" + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
      + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
      + "<EntityType Name= \"Employee\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>" + "<Property Name=\""
      + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>" + "<Property Name=\"" + propertyNames[1]
      + "\" Type=\"Edm.String\"/>" + "</EntityType>" + "</Schema>" + "<Schema Namespace=\"" + NAMESPACE2
      + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">" + "<EntityType Name= \"Photo\">"
      + "<Key><PropertyRef Name=\"Id\"/></Key>"
      + "<Property Name=\"Id\" Type=\"Edm.Int32\" Nullable=\"false\" ConcurrencyMode=\"Fixed\" MaxLength=\""
      + MAX_LENGTH + "\"/>" + "<Property Name=\"Name\" Type=\"Edm.String\" Unicode=\"true\" DefaultValue=\""
      + DEFAULT_VALUE
      + "\" FixedLength=\"false\"/>" + "<Property Name=\"BinaryData\" Type=\"Edm.Binary\" m:MimeType=\"" + MIME_TYPE
      + "\"/>" + "<Property Name=\"Содержание\" Type=\"Edm.String\" m:FC_TargetPath=\"" + FC_TARGET_PATH
      + "\" m:FC_NsUri=\"" + FC_NS_URI + "\"" + " m:FC_NsPrefix=\"" + FC_NS_PREFIX + "\" m:FC_KeepInContent=\""
      + FC_KEEP_IN_CONTENT + "\" m:FC_ContentKind=\"text\" >" + "</Property>" + "</EntityType>" + "</Schema>"
      + "</edmx:DataServices>" + "</edmx:Edmx>";

  /** The xml with string value for max length facet. */
  private final String xmlWithStringValueForMaxLengthFacet = "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
      + Edm.NAMESPACE_EDMX_2007_06
      + "\">" + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
      + "<Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
      + "<EntityType Name= \"Photo\"><Key><PropertyRef Name=\"Id\"/></Key><Property Name=\"Id\" Type=\"Edm.Int32\" " +
      "Nullable=\"false\" MaxLength=\"Max\"/><Property Name=\"Name\" Type=\"Edm.Int32\" MaxLength=\"max\"/>"
      + "</EntityType></Schema></edmx:DataServices></edmx:Edmx>";

  /** The edmx ref for 1680364709. */
  private final String edmxRefFor1680364709 = 
      "<edmx:Edmx xmlns:edmx=\"http://schemas.microsoft.com/ado/2007/06/edmx\" " +
      "xmlns:m=\"http://schemas.microsoft.com/ado/2007/08/dataservices/metadata\" xmlns:sap=\"" +
      "http://www.sap.com/Protocols/SAPData\" Version=\"1.0\">" +
      "<edmx:Reference xmlns:edmx=\"http://docs.oasis-open.org/odata/ns/edmx\" " +
      "Uri=\"https://host:port/sap/opu/odata/IWFND/CATALOGSERVICE;v=2/Vocabularies"+
      "(TechnicalName='%2FIWBEP%2FVOC_COMMON'"+
      ",Version='0001',SAP__Origin='')/$value\">" +
      "<edmx:Include Alias=\"Common\" Namespace=\"com.sap.vocabularies.Common.v1\"/>" +
      "</edmx:Reference>" + 
      "</edmx:Edmx>";
  
  /**
   * Two edmx with validation.
   *
   * @throws Exception the exception
   */
  @Test
  public void twoEdmxWithValidation() throws Exception {
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(edmxRefFor1680364709);
    DataServices result = parser.readMetadata(reader, true);   
    assertNotNull(result);

  }
  
  /**
   * Two edmx without validation.
   *
   * @throws Exception the exception
   */
  @Test
  public void twoEdmxWithoutValidation() throws Exception {
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(edmxRefFor1680364709);
    DataServices result = parser.readMetadata(reader, false);   
    assertNotNull(result);

  }
  
  /**
   * Test metadata dokument with whitepaces.
   *
   * @throws Exception the exception
   */
  @Test
  public void testMetadataDokumentWithWhitepaces() throws Exception {
    final String metadata = ""
        + "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
        + "   <edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
        + "       <Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
        + "           <EntityType Name= \"Photo\">"
        + "               <Key> "
        + "                 <PropertyRef Name=\"Id\" />"
        + "               </Key>"
        + "               <Property Name=\"Id\" Type=\"Edm.Int16\" Nullable=\"false\" />"
        + "               <MyAnnotation xmlns=\"http://company.com/odata\">   "
        + "                 <child> value1</child>"
        + "                 <child>value2</child>"
        + "               </MyAnnotation>"
        + "           </EntityType>"
        + "       </Schema>"
        + "  </edmx:DataServices>"
        + "</edmx:Edmx>";

    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(metadata);
    DataServices result = parser.readMetadata(reader, true);

    assertEquals(1, result.getSchemas().size());
    List<EntityType> entityTypes = result.getSchemas().get(0).getEntityTypes();
    assertEquals(1, entityTypes.size());
    EntityType entityType = entityTypes.get(0);
    List<AnnotationElement> annotationElements = entityType.getAnnotationElements();
    assertEquals(1, annotationElements.size());
    AnnotationElement annotationElement = annotationElements.get(0);
    List<AnnotationElement> childElements = annotationElement.getChildElements();
    assertEquals(2, childElements.size());

    assertEquals(" value1", childElements.get(0).getText());
    assertEquals("value2", childElements.get(1).getText());
  }

  /**
   * Test metadata dokument with whitepaces multiline.
   *
   * @throws Exception the exception
   */
  @Test
  public void testMetadataDokumentWithWhitepacesMultiline() throws Exception {
    final String metadata = ""
        + "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
        + "   <edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
        + "       <Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
        + "           <EntityType Name= \"Photo\">"
        + "               <Key> "
        + "                 <PropertyRef Name=\"Id\" />"
        + "               </Key>"
        + "               <Property Name=\"Id\" Type=\"Edm.Int16\" Nullable=\"false\" />"
        + "               <MyAnnotation xmlns=\"http://company.com/odata\">   "
        + "                 <child> value1\n"
        + "                 long long long multiline attribute</child>"
        + "                 <child>value2</child>"
        + "               </MyAnnotation>"
        + "           </EntityType>"
        + "       </Schema>"
        + "  </edmx:DataServices>"
        + "</edmx:Edmx>";

    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(metadata);
    DataServices result = parser.readMetadata(reader, true);

    assertEquals(1, result.getSchemas().size());
    List<EntityType> entityTypes = result.getSchemas().get(0).getEntityTypes();
    assertEquals(1, entityTypes.size());
    EntityType entityType = entityTypes.get(0);
    List<AnnotationElement> annotationElements = entityType.getAnnotationElements();
    assertEquals(1, annotationElements.size());
    AnnotationElement annotationElement = annotationElements.get(0);
    List<AnnotationElement> childElements = annotationElement.getChildElements();
    assertEquals(2, childElements.size());

    assertEquals(" value1\n" +
        "                 long long long multiline attribute", childElements.get(0).getText());
    assertEquals("value2", childElements.get(1).getText());
  }

  /**
   * Test metadata dokument with whitepaces 2.
   *
   * @throws Exception the exception
   */
  @Test
  public void testMetadataDokumentWithWhitepaces2() throws Exception {
    final String metadata = ""
        + "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
        + "   <edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
        + "       <Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
        + "           <EntityType Name= \"Photo\">"
        + "               <Key> "
        + "                 <PropertyRef Name=\"Id\" />"
        + "               </Key>"
        + "               <Property Name=\"Id\" Type=\"Edm.Int16\" Nullable=\"false\" />"
        + "               <MyAnnotation xmlns=\"http://company.com/odata\">   "
        + "                 <child> value1"
        + "</child></MyAnnotation>"
        + "           </EntityType>"
        + "       </Schema>"
        + "  </edmx:DataServices>"
        + "</edmx:Edmx>";

    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(metadata);
    DataServices result = parser.readMetadata(reader, true);

    assertEquals(1, result.getSchemas().size());
    List<EntityType> entityTypes = result.getSchemas().get(0).getEntityTypes();
    assertEquals(1, entityTypes.size());
    EntityType entityType = entityTypes.get(0);
    List<AnnotationElement> annotationElements = entityType.getAnnotationElements();
    assertEquals(1, annotationElements.size());
    AnnotationElement annotationElement = annotationElements.get(0);
    List<AnnotationElement> childElements = annotationElement.getChildElements();
    assertEquals(1, childElements.size());

    assertEquals(" value1", childElements.get(0).getText());
  }
  

  /**
   * ODATAJAV A 77 test metadata dokument with multi level entity type.
   *
   * @throws Exception the exception
   */
  @Test
  public void ODATAJAVA_77_testMetadataDokumentWithMultiLevelEntityType() throws Exception {
    final String metadata = ""
        + "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
        + "   <edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
        + "       <Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
        + "         <EntityType Name= \"Parameter\">"
        + "               <Key> "
        + "                 <PropertyRef Name=\"Id\" />"
        + "               </Key>"
        + "               <Property Name=\"Id\" Type=\"Edm.Int16\" Nullable=\"false\" />"
        + "           </EntityType>"
        + "           <EntityType Name= \"ConfigParameter\" BaseType= \"RefScenario2.Parameter\" />"
        + "           <EntityType Name= \"DataConfigParameter\" BaseType= \"RefScenario2.ConfigParameter\" />"
        + "           <EntityType Name= \"StringDataConfigParameter\" BaseType= \"RefScenario2.DataConfigParameter\" />"
        + "       </Schema>"
        + "  </edmx:DataServices>"
        + "</edmx:Edmx>";

    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(metadata);
    DataServices result = parser.readMetadata(reader, true);

    assertEquals(1, result.getSchemas().size());
    List<EntityType> entityTypes = result.getSchemas().get(0).getEntityTypes();
    assertEquals(4, entityTypes.size());

  }
  
  /**
   * ODATAJAV A 77 test base type key.
   *
   * @throws Exception the exception
   */
  @Test
  public void ODATAJAVA_77_testBaseTypeKey() throws Exception {
    final String metadata = ""
        + "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
        + "   <edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
        + "       <Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
        + "         <EntityType Name= \"Parameter\">"
        + "               <Key> "
        + "                 <PropertyRef Name=\"Id\" />"
        + "               </Key>"
        + "               <Property Name=\"Id\" Type=\"Edm.Int16\" Nullable=\"false\" />"
        + "           </EntityType>"
        + "           <EntityType Name= \"ConfigParameter\" BaseType= \"RefScenario2.Parameter\" />"
        + "           <EntityType Name= \"DataConfigParameter\" BaseType= \"RefScenario2.ConfigParameter\" >"
        + "               <Key> "
        + "                 <PropertyRef Name=\"Name\" />"
        + "               </Key>"
        + "               <Property Name=\"Name\" Type=\"Edm.String\" Nullable=\"false\" />"
        + "           </EntityType>"
        + "           <EntityType Name= \"StringDataConfigParameter\" BaseType= \"RefScenario2.DataConfigParameter\" />"
        + "       </Schema>"
        + "  </edmx:DataServices>"
        + "</edmx:Edmx>";

    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(metadata);
    DataServices result = parser.readMetadata(reader, true);

    assertEquals(1, result.getSchemas().size());
    List<EntityType> entityTypes = result.getSchemas().get(0).getEntityTypes();
    assertEquals(4, entityTypes.size());

  }
  
  /**
   * ODATAJAV A 77 test entity type key.
   *
   * @throws Exception the exception
   */
  @Test
  public void ODATAJAVA_77_testEntityTypeKey() throws Exception {
    final String metadata = ""
        + "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
        + "   <edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
        + "       <Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
        + "         <EntityType Name= \"Parameter\">"
        + "               <Key> "
        + "                 <PropertyRef Name=\"Id\" />"
        + "               </Key>"
        + "               <Property Name=\"Id\" Type=\"Edm.Int16\" Nullable=\"false\" />"
        + "           </EntityType>"
        + "           <EntityType Name= \"ConfigParameter\" BaseType= \"RefScenario2.Parameter\" />"
        + "           <EntityType Name= \"DataConfigParameter\" BaseType= \"RefScenario2.ConfigParameter\" />"
        + "           <EntityType Name= \"StringDataConfigParameter\" BaseType= \"RefScenario2.DataConfigParameter\" >"
        + "               <Key> "
        + "                 <PropertyRef Name=\"Name\" />"
        + "               </Key>"
        + "               <Property Name=\"Name\" Type=\"Edm.String\" Nullable=\"false\" />"
        + "           </EntityType>"
        + "       </Schema>"
        + "  </edmx:DataServices>"
        + "</edmx:Edmx>";

    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(metadata);
    DataServices result = parser.readMetadata(reader, true);

    assertEquals(1, result.getSchemas().size());
    List<EntityType> entityTypes = result.getSchemas().get(0).getEntityTypes();
    assertEquals(4, entityTypes.size());

  }
  
  /**
   * ODATAJAV A 77 exception scenario.
   *
   * @throws Exception the exception
   */
  @Test(expected=EntityProviderException.class)
  public void ODATAJAVA_77_ExceptionScenario() throws Exception {
    final String metadata = ""
        + "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
        + "   <edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
        + "       <Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
        + "           <EntityType Name= \"ConfigParameter\" BaseType= \"RefScenario2.Parameter\" />"
        + "           <EntityType Name= \"DataConfigParameter\" BaseType= \"RefScenario2.ConfigParameter\" />"
        + "           <EntityType Name= \"StringDataConfigParameter\" BaseType= \"RefScenario2.DataConfigParameter\" />"
        + "         <EntityType Name= \"Parameter\">"
        + "            <Property Name=\"Id\" Type=\"Edm.Int16\" Nullable=\"false\" />"
        + "           </EntityType>"
        + "       </Schema>"
        + "  </edmx:DataServices>"
        + "</edmx:Edmx>";

    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(metadata);
    parser.readMetadata(reader, true);
  }



  /**
   * String value for max legth facet.
   *
   * @throws Exception the exception
   */
  @Test
  public void stringValueForMaxLegthFacet() throws Exception {
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithStringValueForMaxLengthFacet);
    DataServices result = parser.readMetadata(reader, true);

    List<Property> properties = result.getSchemas().get(0).getEntityTypes().get(0).getProperties();
    assertEquals(2, properties.size());

    Property property = getForName(properties, "Id");
    EdmFacets facets = property.getFacets();
    assertEquals(new Integer(Integer.MAX_VALUE), facets.getMaxLength());

    property = getForName(properties, "Name");
    facets = property.getFacets();
    assertEquals(new Integer(Integer.MAX_VALUE), facets.getMaxLength());
  }

  /**
   * Gets the for name.
   *
   * @param properties the properties
   * @param propertyName the property name
   * @return the for name
   */
  private Property getForName(final List<Property> properties, final String propertyName) {
    for (Property property : properties) {
      if (property.getName().equals(propertyName)) {
        return property;
      }
    }
    fail("Should have found property:" + propertyName);
    return null;
  }

  /**
   * Test.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void test() throws XMLStreamException, EntityProviderException {
    int i = 0;
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xml);
    DataServices result = parser.readMetadata(reader, true);
    assertEquals("2.0", result.getDataServiceVersion());
    for (Schema schema : result.getSchemas()) {
      assertEquals(NAMESPACE, schema.getNamespace());
      assertEquals(1, schema.getEntityTypes().size());
      assertEquals("Employee", schema.getEntityTypes().get(0).getName());
      assertEquals(Boolean.TRUE, schema.getEntityTypes().get(0).isHasStream());
      for (PropertyRef propertyRef : schema.getEntityTypes().get(0).getKey().getKeys()) {
        assertEquals("EmployeeId", propertyRef.getName());
      }
      for (Property property : schema.getEntityTypes().get(0).getProperties()) {
        assertEquals(propertyNames[i], property.getName());
        if ("Location".equals(property.getName())) {
          ComplexProperty cProperty = (ComplexProperty) property;
          assertEquals(NAMESPACE, cProperty.getType().getNamespace());
          assertEquals("c_Location", cProperty.getType().getName());
        } else if ("EmployeeName".equals(property.getName())) {
          assertNotNull(property.getCustomizableFeedMappings());
          assertEquals("SyndicationTitle", property.getCustomizableFeedMappings().getFcTargetPath());
          assertNull(property.getCustomizableFeedMappings().getFcContentKind());
        }
        i++;
      }
      assertEquals(1, schema.getComplexTypes().size());
      assertEquals("c_Location", schema.getComplexTypes().get(0).getName());
    }
  }

  /**
   * Test other edm namespace.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testOtherEdmNamespace() throws XMLStreamException, EntityProviderException {
    int i = 0;
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xml2);
    DataServices result = parser.readMetadata(reader, true);
    assertEquals("2.0", result.getDataServiceVersion());
    for (Schema schema : result.getSchemas()) {
      assertEquals(NAMESPACE, schema.getNamespace());
      assertEquals(1, schema.getEntityTypes().size());
      assertEquals("Employee", schema.getEntityTypes().get(0).getName());
      for (PropertyRef propertyRef : schema.getEntityTypes().get(0).getKey().getKeys()) {
        assertEquals("EmployeeId", propertyRef.getName());
      }
      for (Property property : schema.getEntityTypes().get(0).getProperties()) {
        assertEquals(propertyNames[i], property.getName());
        if ("Location".equals(property.getName())) {
          ComplexProperty cProperty = (ComplexProperty) property;
          assertEquals("c_Location", cProperty.getType().getName());
        } else if ("EmployeeName".equals(property.getName())) {
          assertNotNull(property.getCustomizableFeedMappings());
        }
        i++;
      }
      for (AnnotationElement annoElement : schema.getAnnotationElements()) {
        assertEquals("prefix", annoElement.getPrefix());
        assertEquals("namespace", annoElement.getNamespace());
        assertEquals("schemaElement", annoElement.getName());
        assertEquals("text3", annoElement.getText());
      }
    }
  }

  /**
   * Test base type.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testBaseType() throws XMLStreamException, EntityProviderException {
    int i = 0;
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithBaseType);
    DataServices result = parser.readMetadata(reader, true);
    assertEquals("2.0", result.getDataServiceVersion());
    for (Schema schema : result.getSchemas()) {
      assertEquals(NAMESPACE, schema.getNamespace());
      assertEquals(2, schema.getEntityTypes().size());
      assertEquals("Employee", schema.getEntityTypes().get(0).getName());
      for (PropertyRef propertyRef : schema.getEntityTypes().get(0).getKey().getKeys()) {
        assertEquals("EmployeeId", propertyRef.getName());
      }
      for (Property property : schema.getEntityTypes().get(0).getProperties()) {
        assertEquals(propertyNames[i], property.getName());
        i++;
      }

    }
  }

  /**
   * Test complex type with base type.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testComplexTypeWithBaseType() throws XMLStreamException, EntityProviderException {
    final String xml =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" Alias=\"RS\"  xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\"" + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\"" + propertyNames[2] + "\" Type=\"RefScenario.c_Location\" Nullable=\"false\"/>"
            + "</EntityType>" + "<ComplexType Name=\"c_BaseType_for_Location\" Abstract=\"true\">"
            + "<Property Name=\"Country\" Type=\"Edm.String\"/>" + "</ComplexType>"
            + "<ComplexType Name=\"c_Location\" BaseType=\"RefScenario.c_BaseType_for_Location\">" + "</ComplexType>"
            + "<ComplexType Name=\"c_Other_Location\" BaseType=\"RS.c_BaseType_for_Location\">" + "</ComplexType>"
            + "</Schema>"
            + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xml);
    DataServices result = parser.readMetadata(reader, true);
    assertEquals("2.0", result.getDataServiceVersion());
    for (Schema schema : result.getSchemas()) {
      for (ComplexType complexType : schema.getComplexTypes()) {
        if ("c_Location".equals(complexType.getName())) {
          assertNotNull(complexType.getBaseType());
          assertTrue(!complexType.isAbstract());
          assertEquals("c_BaseType_for_Location", complexType.getBaseType().getName());
          assertEquals("RefScenario", complexType.getBaseType().getNamespace());
        } else if ("c_Other_Location".equals(complexType.getName())) {
          assertNotNull(complexType.getBaseType());
          assertTrue(!complexType.isAbstract());
          assertEquals("c_BaseType_for_Location", complexType.getBaseType().getName());
          assertEquals("RS", complexType.getBaseType().getNamespace());
        } else if ("c_BaseType_for_Location".equals(complexType.getName())) {
          assertNotNull(complexType.isAbstract());
          assertTrue(complexType.isAbstract());
        } else {
          assertTrue(false);
        }
      }

    }
  }

  /**
   * Test complex type with invalid base type.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testComplexTypeWithInvalidBaseType() throws XMLStreamException, EntityProviderException {
    final String xml =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\"" + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\"" + propertyNames[2] + "\" Type=\"RefScenario.c_Location\" Nullable=\"false\"/>"
            + "</EntityType>" + "<ComplexType Name=\"c_BaseType_for_Location\" Abstract=\"true\">"
            + "<Property Name=\"Country\" Type=\"Edm.String\"/>" + "</ComplexType>"
            + "<ComplexType Name=\"c_Location\" BaseType=\"RefScenario.Employee\">" + "</ComplexType>" + "</Schema>"
            + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xml);
    parser.readMetadata(reader, true);
  }

  /**
   * Test complex type with invalid base type 2.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testComplexTypeWithInvalidBaseType2() throws XMLStreamException, EntityProviderException {
    final String xml =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\"" + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\"" + propertyNames[2] + "\" Type=\"RefScenario.c_Location\" Nullable=\"false\"/>"
            + "</EntityType>" + "<ComplexType Name=\"c_BaseType_for_Location\" Abstract=\"true\">"
            + "<Property Name=\"Country\" Type=\"Edm.String\"/>" + "</ComplexType>"
            + "<ComplexType Name=\"c_Location\" BaseType=\"c_BaseType_for_Location\">" + "</ComplexType>" + "</Schema>"
            + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xml);
    parser.readMetadata(reader, true);
  }

  /**
   * Test missing edmx close tag.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testMissingEdmxCloseTag() throws XMLStreamException, EntityProviderException {
    final String xml = "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
        + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
        + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
        + "<EntityType Name= \"Employee\" m:HasStream=\"true\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
        + "<Property Name=\"" + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>" + "<Property Name=\""
        + propertyNames[1] + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>" + "<Property Name=\""
        + propertyNames[2] + "\" Type=\"RefScenario.c_Location\" Nullable=\"false\"/>" + "</EntityType>"
        + "<ComplexType Name=\"c_Location\">" + "<Property Name=\"Country\" Type=\"Edm.String\"/>" + "</ComplexType>"
        + "</Schema>" + "</edmx:DataServices>";

    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xml);
    parser.readMetadata(reader, true);
  }

  /**
   * Test association.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testAssociation() throws XMLStreamException, EntityProviderException {
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithAssociation);
    DataServices result = parser.readMetadata(reader, true);
    assertEquals("2.0", result.getDataServiceVersion());
    for (Schema schema : result.getSchemas()) {
      for (EntityType entityType : schema.getEntityTypes()) {
        if ("Manager".equals(entityType.getName())) {
          assertEquals("RefScenario", entityType.getBaseType().getNamespace());
          assertEquals("Employee", entityType.getBaseType().getName());
          for (NavigationProperty navProperty : entityType.getNavigationProperties()) {
            assertEquals("r_Manager", navProperty.getFromRole());
            assertEquals("r_Employees", navProperty.getToRole());
            assertEquals("RefScenario", navProperty.getRelationship().getNamespace());
            assertEquals(ASSOCIATION, navProperty.getRelationship().getName());
          }
        }
        if ("Employee".equals(entityType.getName())) {
          for (NavigationProperty navProperty : entityType.getNavigationProperties()) {
            assertEquals("r_Employees", navProperty.getFromRole());
            assertEquals("RefScenario", navProperty.getRelationship().getNamespace());
            assertEquals(ASSOCIATION, navProperty.getRelationship().getName());
          }
        }
      }
      for (Association association : schema.getAssociations()) {
        AssociationEnd end;
        assertEquals(ASSOCIATION, association.getName());
        if ("Employee".equals(association.getEnd1().getType().getName())) {
          end = association.getEnd1();
        } else {
          end = association.getEnd2();
        }
        assertEquals(EdmMultiplicity.MANY, end.getMultiplicity());
        assertEquals("r_Employees", end.getRole());
        assertEquals(EdmAction.Cascade, end.getOnDelete().getAction());
      }
    }
  }

  /**
   * Test two schemas.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testTwoSchemas() throws XMLStreamException, EntityProviderException {
    int i = 0;
    String schemasNs[] = { NAMESPACE, NAMESPACE2 };
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithTwoSchemas);
    DataServices result = parser.readMetadata(reader, true);
    assertEquals("2.0", result.getDataServiceVersion());
    assertEquals(2, result.getSchemas().size());
    for (Schema schema : result.getSchemas()) {
      assertEquals(schemasNs[i], schema.getNamespace());
      assertEquals(1, schema.getEntityTypes().size());
      i++;

    }
  }

  /**
   * Test properties.
   *
   * @throws EntityProviderException the entity provider exception
   * @throws XMLStreamException the XML stream exception
   */
  @Test
  public void testProperties() throws EntityProviderException, XMLStreamException {
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithTwoSchemas);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      for (EntityType entityType : schema.getEntityTypes()) {
        if ("Employee".equals(entityType.getName())) {
          for (Property property : entityType.getProperties()) {
            if (propertyNames[0].equals(property.getName())) {
              assertNotNull(property.getFacets());
              assertEquals(Boolean.FALSE, property.getFacets().isNullable());
            } else if (propertyNames[1].equals(property.getName())) {
              assertNull(property.getFacets());
            }
          }
        } else if ("Photo".equals(entityType.getName())) {
          for (Property property : entityType.getProperties()) {
            SimpleProperty sProperty = (SimpleProperty) property;
            if ("Id".equals(property.getName())) {
              assertEquals(Boolean.FALSE, property.getFacets().isNullable());
              assertEquals(EdmConcurrencyMode.Fixed, property.getFacets().getConcurrencyMode());
              assertEquals(new Integer(MAX_LENGTH), property.getFacets().getMaxLength());

              assertEquals(EdmSimpleTypeKind.Int32, sProperty.getType());
              assertNull(property.getCustomizableFeedMappings());
            }
            if ("Name".equals(property.getName())) {
              assertEquals(Boolean.TRUE, property.getFacets().isUnicode());
              assertEquals(DEFAULT_VALUE, property.getFacets().getDefaultValue());
              assertEquals(Boolean.FALSE, property.getFacets().isFixedLength());
              assertEquals(EdmSimpleTypeKind.String, sProperty.getType());
              assertNull(property.getCustomizableFeedMappings());
            }
            if ("Содержание".equals(property.getName())) {
              assertEquals(FC_TARGET_PATH, property.getCustomizableFeedMappings().getFcTargetPath());
              assertEquals(FC_NS_URI, property.getCustomizableFeedMappings().getFcNsUri());
              assertEquals(FC_NS_PREFIX, property.getCustomizableFeedMappings().getFcNsPrefix());
              assertEquals(FC_KEEP_IN_CONTENT, property.getCustomizableFeedMappings().isFcKeepInContent());
              assertEquals(EdmContentKind.text, property.getCustomizableFeedMappings().getFcContentKind());
            }
            if ("BinaryData".equals(property.getName())) {
              assertEquals(MIME_TYPE, property.getMimeType());
            }
          }
        }
      }
    }
  }

  /**
   * Test entity set.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testEntitySet() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\"" + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\"" + propertyNames[1] + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>" + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>" + "</EntityContainer>"
            + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        assertEquals("Container1", container.getName());
        assertEquals(Boolean.TRUE, container.isDefaultEntityContainer());
        for (EntitySet entitySet : container.getEntitySets()) {
          assertEquals("Employees", entitySet.getName());
          assertEquals("Employee", entitySet.getEntityType().getName());
          assertEquals(NAMESPACE, entitySet.getEntityType().getNamespace());
        }
      }
    }
  }

  /**
   * Test association set.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testAssociationSet() throws XMLStreamException, EntityProviderException {
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithAssociation);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        assertEquals(NAMESPACE2, schema.getNamespace());
        assertEquals("Container1", container.getName());
        assertEquals(Boolean.TRUE, container.isDefaultEntityContainer());
        for (AssociationSet assocSet : container.getAssociationSets()) {
          assertEquals(ASSOCIATION, assocSet.getName());
          assertEquals(ASSOCIATION, assocSet.getAssociation().getName());
          assertEquals(NAMESPACE, assocSet.getAssociation().getNamespace());
          AssociationSetEnd end;
          if ("Employees".equals(assocSet.getEnd1().getEntitySet())) {
            end = assocSet.getEnd1();
          } else {
            end = assocSet.getEnd2();
          }
          assertEquals("r_Employees", end.getRole());
        }
      }
    }
  }

  /**
   * Test function import.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testFunctionImport() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[1]
            + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>"
            + "<EntityType Name= \"Room\" >"
            + "<Key><PropertyRef Name=\"RoomId\"/></Key>"
            + "<Property Name=\""
            + "RoomId"
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[2]
            + "\" Type=\"Edm.String\" />"
            + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<FunctionImport Name=\"EmployeeSearch\" ReturnType=\"Collection(RefScenario.Employee)\" " +
            "EntitySet=\"Employees\" m:HttpMethod=\"GET\">"
            + "<Parameter Name=\"q1\" Type=\"Edm.String\" Nullable=\"true\" />"
            + "<Parameter Name=\"q2\" Type=\"Edm.Int32\" Nullable=\"false\" />"
            + "</FunctionImport>"
            + "<FunctionImport Name=\"RoomSearch\" ReturnType=\"Collection(RefScenario.Room)\" " +
            "EntitySet=\"Rooms\" m:HttpMethod=\"GET\">"
            + "<Parameter Name=\"q1\" Type=\"Edm.String\" Nullable=\"true\" Mode=\"In\"/>"
            + "<Parameter Name=\"q2\" Type=\"Edm.Int32\" Nullable=\"false\" />"
            + "</FunctionImport>"
            + "<FunctionImport Name=\"NoParamters\" ReturnType=\"Collection(RefScenario.Room)\" " +
            "EntitySet=\"Rooms\" m:HttpMethod=\"GET\">"
            + "</FunctionImport>"
            + "<FunctionImport Name=\"NoReturn\" " +
            "EntitySet=\"Rooms\" m:HttpMethod=\"GET\"/>"
            + "<FunctionImport Name=\"SingleRoomReturnType\" ReturnType=\"RefScenario.Room\" " +
            "EntitySet=\"Rooms\" m:HttpMethod=\"GET\">"
            + "</FunctionImport>"
            + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        assertEquals("Container1", container.getName());
        assertEquals(Boolean.TRUE, container.isDefaultEntityContainer());

        assertEquals(5, container.getFunctionImports().size());
        FunctionImport functionImport1 = container.getFunctionImports().get(0);

        assertEquals("EmployeeSearch", functionImport1.getName());
        assertEquals("Employees", functionImport1.getEntitySet());
        assertEquals(NAMESPACE, functionImport1.getReturnType().getTypeName().getNamespace());
        assertEquals("Employee", functionImport1.getReturnType().getTypeName().getName());
        assertEquals(EdmMultiplicity.MANY, functionImport1.getReturnType().getMultiplicity());
        assertEquals("GET", functionImport1.getHttpMethod());
        assertEquals(2, functionImport1.getParameters().size());

        assertEquals("q1", functionImport1.getParameters().get(0).getName());
        assertEquals(EdmSimpleTypeKind.String, functionImport1.getParameters().get(0).getType());
        assertEquals(Boolean.TRUE, functionImport1.getParameters().get(0).getFacets().isNullable());

        assertEquals("q2", functionImport1.getParameters().get(1).getName());
        assertEquals(EdmSimpleTypeKind.Int32, functionImport1.getParameters().get(1).getType());
        assertEquals(Boolean.FALSE, functionImport1.getParameters().get(1).getFacets().isNullable());

        FunctionImport functionImport2 = container.getFunctionImports().get(1);
        assertEquals("RoomSearch", functionImport2.getName());
        assertEquals("Rooms", functionImport2.getEntitySet());
        assertEquals(NAMESPACE, functionImport2.getReturnType().getTypeName().getNamespace());
        assertEquals("Room", functionImport2.getReturnType().getTypeName().getName());
        assertEquals(EdmMultiplicity.MANY, functionImport2.getReturnType().getMultiplicity());
        assertEquals("GET", functionImport2.getHttpMethod());
        assertEquals(2, functionImport2.getParameters().size());
        assertEquals(new FullQualifiedName("RefScenario", "Room"),
            functionImport2.getReturnType().getTypeName());
        assertEquals(EdmMultiplicity.MANY, functionImport2.getReturnType().getMultiplicity());

        FunctionImportParameter functionImportParameter = functionImport2.getParameters().get(0);
        assertEquals("q1", functionImportParameter.getName());
        assertEquals(EdmSimpleTypeKind.String, functionImport2.getParameters().get(0).getType());
        assertEquals(Boolean.TRUE, functionImport2.getParameters().get(0).getFacets().isNullable());
        assertEquals("In", functionImportParameter.getMode());

        assertEquals("q2", functionImport2.getParameters().get(1).getName());
        assertEquals(EdmSimpleTypeKind.Int32, functionImport2.getParameters().get(1).getType());
        assertEquals(Boolean.FALSE, functionImport2.getParameters().get(1).getFacets().isNullable());
        assertEquals(null, functionImport2.getParameters().get(1).getMode());

        FunctionImport functionImport3 = container.getFunctionImports().get(2);
        assertEquals("NoParamters", functionImport3.getName());
        List<FunctionImportParameter> parameters3 = functionImport3.getParameters();
        assertNotNull(parameters3);
        assertEquals(0, parameters3.size());

        FunctionImport functionImport4 = container.getFunctionImports().get(3);
        assertEquals("NoReturn", functionImport4.getName());
        List<FunctionImportParameter> parameters4 = functionImport4.getParameters();
        assertNotNull(parameters4);
        assertEquals(0, parameters4.size());
        assertNull(functionImport4.getReturnType());

        FunctionImport functionImport5 = container.getFunctionImports().get(4);
        assertEquals("SingleRoomReturnType", functionImport5.getName());
        List<FunctionImportParameter> parameters5 = functionImport5.getParameters();
        assertNotNull(parameters5);
        assertEquals(0, parameters5.size());
        assertEquals(new FullQualifiedName("RefScenario", "Room"),
            functionImport5.getReturnType().getTypeName());
        assertEquals(EdmMultiplicity.ONE, functionImport5.getReturnType().getMultiplicity());

      }
    }
  }

/**
 * Test function import entity.
 *
 * @throws XMLStreamException the XML stream exception
 * @throws EntityProviderException the entity provider exception
 */
//Function Import with return type as entity type and entityset attribute set
  @Test
  public void testFunctionImportEntity() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[1]
            + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<FunctionImport Name=\"EmployeeSearch\" ReturnType=\"RefScenario.Employee\" " +
            "EntitySet=\"Employees\" m:HttpMethod=\"GET\">"
            + "<Parameter Name=\"q1\" Type=\"Edm.String\" Nullable=\"true\" />"
            + "<Parameter Name=\"q2\" Type=\"Edm.Int32\" Nullable=\"false\" />"
            + "</FunctionImport>"
            + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        assertEquals("Container1", container.getName());
        assertEquals(Boolean.TRUE, container.isDefaultEntityContainer());

        FunctionImport functionImport1 = container.getFunctionImports().get(0);

        assertEquals("EmployeeSearch", functionImport1.getName());
        assertEquals("Employees", functionImport1.getEntitySet());
        assertEquals(NAMESPACE, functionImport1.getReturnType().getTypeName().getNamespace());
        assertEquals("Employee", functionImport1.getReturnType().getTypeName().getName());
        assertEquals(EdmMultiplicity.ONE, functionImport1.getReturnType().getMultiplicity());
        assertEquals("GET", functionImport1.getHttpMethod());
        assertEquals(2, functionImport1.getParameters().size());
        assertEquals("q1", functionImport1.getParameters().get(0).getName());
        assertEquals(EdmSimpleTypeKind.String, functionImport1.getParameters().get(0).getType());
        assertEquals(Boolean.TRUE, functionImport1.getParameters().get(0).getFacets().isNullable());
        assertEquals("q2", functionImport1.getParameters().get(1).getName());
        assertEquals(EdmSimpleTypeKind.Int32, functionImport1.getParameters().get(1).getType());
        assertEquals(Boolean.FALSE, functionImport1.getParameters().get(1).getFacets().isNullable());
      }
    }
  }
  
  /**
   * Test function import entity order change.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testFunctionImportEntityOrderChange() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<FunctionImport Name=\"EmployeeSearch\" ReturnType=\"RefScenario.Employee\" " +
            "EntitySet=\"Employees\" m:HttpMethod=\"GET\">"
            + "<Parameter Name=\"q1\" Type=\"Edm.String\" Nullable=\"true\" />"
            + "<Parameter Name=\"q2\" Type=\"Edm.Int32\" Nullable=\"false\" />"
            + "</FunctionImport>"
            + "</EntityContainer>" 
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[1]
            + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>"
            + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        assertEquals("Container1", container.getName());
        assertEquals(Boolean.TRUE, container.isDefaultEntityContainer());

        FunctionImport functionImport1 = container.getFunctionImports().get(0);

        assertEquals("EmployeeSearch", functionImport1.getName());
        assertEquals("Employees", functionImport1.getEntitySet());
        assertEquals(NAMESPACE, functionImport1.getReturnType().getTypeName().getNamespace());
        assertEquals("Employee", functionImport1.getReturnType().getTypeName().getName());
        assertEquals(EdmMultiplicity.ONE, functionImport1.getReturnType().getMultiplicity());
        assertEquals("GET", functionImport1.getHttpMethod());
        assertEquals(2, functionImport1.getParameters().size());
        assertEquals("q1", functionImport1.getParameters().get(0).getName());
        assertEquals(EdmSimpleTypeKind.String, functionImport1.getParameters().get(0).getType());
        assertEquals(Boolean.TRUE, functionImport1.getParameters().get(0).getFacets().isNullable());
        assertEquals("q2", functionImport1.getParameters().get(1).getName());
        assertEquals(EdmSimpleTypeKind.Int32, functionImport1.getParameters().get(1).getType());
        assertEquals(Boolean.FALSE, functionImport1.getParameters().get(1).getFacets().isNullable());
      }
    }
  }
  
/**
 * Test function import entity without entity set.
 *
 * @throws XMLStreamException the XML stream exception
 * @throws EntityProviderException the entity provider exception
 */
//Function Import with return type as entity type and entityset attribute not set
  @Test
  public void testFunctionImportEntityWithoutEntitySet() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[1]
            + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<FunctionImport Name=\"EmployeeSearch\" ReturnType=\"RefScenario.Employee\" " +
            " m:HttpMethod=\"GET\">"
            + "<Parameter Name=\"q1\" Type=\"Edm.String\" Nullable=\"true\" />"
            + "<Parameter Name=\"q2\" Type=\"Edm.Int32\" Nullable=\"false\" />"
            + "</FunctionImport>"
            + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        assertEquals("Container1", container.getName());
        assertEquals(Boolean.TRUE, container.isDefaultEntityContainer());

        FunctionImport functionImport1 = container.getFunctionImports().get(0);

        assertEquals("EmployeeSearch", functionImport1.getName());
        assertEquals(null, functionImport1.getEntitySet());
        assertEquals(NAMESPACE, functionImport1.getReturnType().getTypeName().getNamespace());
        assertEquals("Employee", functionImport1.getReturnType().getTypeName().getName());
        assertEquals(EdmMultiplicity.ONE, functionImport1.getReturnType().getMultiplicity());
        assertEquals("GET", functionImport1.getHttpMethod());
        assertEquals(2, functionImport1.getParameters().size());

        assertEquals("q1", functionImport1.getParameters().get(0).getName());
        assertEquals(EdmSimpleTypeKind.String, functionImport1.getParameters().get(0).getType());
        assertEquals(Boolean.TRUE, functionImport1.getParameters().get(0).getFacets().isNullable());
        assertEquals("q2", functionImport1.getParameters().get(1).getName());
        assertEquals(EdmSimpleTypeKind.Int32, functionImport1.getParameters().get(1).getType());
        assertEquals(Boolean.FALSE, functionImport1.getParameters().get(1).getFacets().isNullable());
      }
    }
  }
  
  /**
   * Test function import collection.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  //Function Import with return type collection of complex type and entityset attribute not set
  @Test
  public void testFunctionImportCollection() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Room\" >"
            + "<Key><PropertyRef Name=\"RoomId\"/></Key>"
            + "<Property Name=\""
            + "RoomId"
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[2]
            + "\" Type=\"Edm.String\" />"
            + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Rooms\" EntityType=\"RefScenario.Room\"/>"
            + "<FunctionImport Name=\"LocationSearch\" ReturnType=\"Collection(RefScenario.Location)\" " +
            " m:HttpMethod=\"GET\">"
            + "<Parameter Name=\"q1\" Type=\"Edm.String\" Nullable=\"true\" Mode=\"In\"/>"
            + "<Parameter Name=\"q2\" Type=\"Edm.Int32\" Nullable=\"false\" />"
            + "</FunctionImport>"
            + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        assertEquals("Container1", container.getName());
        assertEquals(Boolean.TRUE, container.isDefaultEntityContainer());

        FunctionImport functionImport2 = container.getFunctionImports().get(0);
        assertEquals("LocationSearch", functionImport2.getName());
        assertEquals(null, functionImport2.getEntitySet());
        assertEquals(NAMESPACE, functionImport2.getReturnType().getTypeName().getNamespace());
        assertEquals("Location", functionImport2.getReturnType().getTypeName().getName());
        assertEquals(EdmMultiplicity.MANY, functionImport2.getReturnType().getMultiplicity());
        assertEquals("GET", functionImport2.getHttpMethod());
        assertEquals(2, functionImport2.getParameters().size());
        assertEquals(new FullQualifiedName("RefScenario", "Location"),
            functionImport2.getReturnType().getTypeName());
        assertEquals(EdmMultiplicity.MANY, functionImport2.getReturnType().getMultiplicity());

        FunctionImportParameter functionImportParameter = functionImport2.getParameters().get(0);
        assertEquals("q1", functionImportParameter.getName());
        assertEquals(EdmSimpleTypeKind.String, functionImport2.getParameters().get(0).getType());
        assertEquals(Boolean.TRUE, functionImport2.getParameters().get(0).getFacets().isNullable());
        assertEquals("In", functionImportParameter.getMode());

        assertEquals("q2", functionImport2.getParameters().get(1).getName());
        assertEquals(EdmSimpleTypeKind.Int32, functionImport2.getParameters().get(1).getType());
        assertEquals(Boolean.FALSE, functionImport2.getParameters().get(1).getFacets().isNullable());
        assertEquals(null, functionImport2.getParameters().get(1).getMode());


      }
    }
  }
  
  /**
   * Test function import coll error.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  //Function import returning collection of entitysets must have entityset attribute defined
  @Test(expected = EntityProviderException.class)
  public void testFunctionImportCollError() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[1]
            + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<FunctionImport Name=\"EmployeeSearch\" ReturnType=\"Collection(RefScenario.Employee)\" " +
            " m:HttpMethod=\"GET\">"
            + "<Parameter Name=\"q1\" Type=\"Edm.String\" Nullable=\"true\" />"
            + "<Parameter Name=\"q2\" Type=\"Edm.Int32\" Nullable=\"false\" />"
            + "</FunctionImport>"
            + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    parser.readMetadata(reader, true);
  }
  
  /**
   * Test function import error.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  //Function import returning complextype or edmsimpletype must not have entityset attribute
  @Test(expected = EntityProviderException.class)
  public void testFunctionImportError() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[1]
            + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<FunctionImport Name=\"NoReturn\" " +
            "EntitySet=\"Rooms\" m:HttpMethod=\"GET\"/>"
            + "<FunctionImport Name=\"SingleRoomReturnType\" ReturnType=\"RefScenario.Room\" " +
            "EntitySet=\"Rooms\" m:HttpMethod=\"GET\">"
            + "</FunctionImport>"
            + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    parser.readMetadata(reader, true);
  }

/**
 * Test function import property.
 *
 * @throws XMLStreamException the XML stream exception
 * @throws EntityProviderException the entity provider exception
 */
//Function import returning complextype or edmsimpletype must not have entityset attribute positive flow
  @Test
  public void testFunctionImportProperty() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[1]
            + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<FunctionImport Name=\"GetId\" ReturnType=\"Edm.String\""
            + " m:HttpMethod=\"GET\"/>"
            + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
    FunctionImport functionImport4 = container.getFunctionImports().get(0);
    assertEquals("GetId", functionImport4.getName());
    List<FunctionImportParameter> parameters4 = functionImport4.getParameters();
    assertNotNull(parameters4);
    assertEquals(0, parameters4.size());
    assertEquals("String", functionImport4.getReturnType().getTypeName().getName());
      }
    }
  }
  
  /**
   * Test alias.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test()
  public void testAlias() throws XMLStreamException, EntityProviderException {
    final String xml =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" Alias=\"RS\"  xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>" + "<Property Name=\""
            + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>" + "</EntityType>"
            + "<EntityType Name=\"Manager\" BaseType=\"RS.Employee\" m:HasStream=\"true\">" + "</EntityType>"
            + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xml);
    DataServices result = parser.readMetadata(reader, true);
    for (Schema schema : result.getSchemas()) {
      assertEquals("RS", schema.getAlias());
      for (EntityType entityType : schema.getEntityTypes()) {
        if ("Manager".equals(entityType.getName())) {
          assertEquals("Employee", entityType.getBaseType().getName());
          assertEquals("RS", entityType.getBaseType().getNamespace());
        }
      }

    }
  }

  /**
   * Test entity type without keys.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testEntityTypeWithoutKeys() throws XMLStreamException, EntityProviderException {
    final String xmlWithoutKeys =
        "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\">" + "<Property Name=\"" + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>" + "</EntityType>" + "</Schema>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithoutKeys);
    parser.readMetadata(reader, true);
  }

  /**
   * Test invalid base type.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testInvalidBaseType() throws XMLStreamException, EntityProviderException {
    final String xmlWithInvalidBaseType =
        "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Manager\" BaseType=\"Employee\" m:HasStream=\"true\">" + "</EntityType>"
            + "</Schema>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidBaseType);
    parser.readMetadata(reader, true);
  }

  /**
   * Test invalid role.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testInvalidRole() throws XMLStreamException, EntityProviderException {
    final String xmlWithInvalidAssociation =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "</EntityType>"
            + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
            + "<NavigationProperty Name=\"nm_Employees\" Relationship=\"RefScenario.ManagerEmployees\" " +
            "FromRole=\"Manager\" ToRole=\"Employees\" />"
            + "</EntityType>" + "<Association Name=\"ManagerEmployees\">"
            + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"r_Employees\"/>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"r_Manager\"/>" + "</Association>"
            + "</Schema>"
            + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidAssociation);
    parser.readMetadata(reader, true);
  }

  /**
   * Test invalid relationship.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testInvalidRelationship() throws XMLStreamException, EntityProviderException {
    final String xmlWithInvalidAssociation =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployee\" " +
            "FromRole=\"r_Employees\" ToRole=\"r_Manager\" />"
            + "</EntityType>" + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
            + "</EntityType>" + "<Association Name=\"ManagerEmployees\">"
            + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"r_Employees\"/>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"r_Manager\"/>" + "</Association>"
            + "</Schema>"
            + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidAssociation);
    parser.readMetadata(reader, true);
  }

  /**
   * Test missing relationship.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void testMissingRelationship() throws Exception {
    final String xmlWithInvalidAssociation =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>" + "<Property Name=\""
            + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" />" + "</EntityType>"
            + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">" + "</EntityType>"
            + "<Association Name=\"ManagerEmployees\">"
            + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"r_Employees\"/>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"r_Manager\"/>"
            + "</Association></Schema></edmx:DataServices></edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidAssociation);
    try {
      parser.readMetadata(reader, true);
    } catch (EntityProviderException e) {
      assertEquals(EntityProviderException.MISSING_ATTRIBUTE.getKey(), e.getMessageReference().getKey());
      assertEquals(2, e.getMessageReference().getContent().size());
      assertEquals("Relationship", e.getMessageReference().getContent().get(0));
      assertEquals("NavigationProperty", e.getMessageReference().getContent().get(1));
      throw e;
    }
  }

  /**
   * Test missing entity type.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void testMissingEntityType() throws Exception {
    final String xmlWithInvalidAssociation =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployees\" " +
            "FromRole=\"r_Employees\" ToRole=\"r_Manager\" />"
            + "</EntityType>" + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
            + "</EntityType>" + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" />" + "</EntityContainer>" + "<Association Name=\"ManagerEmployees\">"
            + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"r_Employees\"/>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"r_Manager\"/>"
            + "</Association></Schema></edmx:DataServices></edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidAssociation);
    try {
      parser.readMetadata(reader, true);
    } catch (EntityProviderException e) {
      assertEquals(EntityProviderException.MISSING_ATTRIBUTE.getKey(), e.getMessageReference().getKey());
      assertEquals(2, e.getMessageReference().getContent().size());
      assertEquals("EntityType", e.getMessageReference().getContent().get(0));
      assertEquals("EntitySet", e.getMessageReference().getContent().get(1));
      throw e;
    }
  }

  /**
   * Test missing type.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void testMissingType() throws Exception {
    final String xmlWithInvalidAssociation =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployees\" " +
            "FromRole=\"r_Employees\" ToRole=\"r_Manager\" />"
            + "</EntityType>" + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
            + "</EntityType>" + "<Association Name=\"ManagerEmployees\">"
            + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"r_Employees\"/>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"r_Manager\"/>"
            + "</Association></Schema></edmx:DataServices></edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidAssociation);
    try {
      parser.readMetadata(reader, true);
    } catch (EntityProviderException e) {
      assertEquals(EntityProviderException.MISSING_ATTRIBUTE.getKey(), e.getMessageReference().getKey());
      assertEquals(2, e.getMessageReference().getContent().size());
      assertEquals("Type", e.getMessageReference().getContent().get(0));
      assertEquals("Property", e.getMessageReference().getContent().get(1));
      throw e;
    }
  }

  /**
   * Test missing type at association.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void testMissingTypeAtAssociation() throws Exception {
    final String xmlWithInvalidAssociation =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployees\" " +
            "FromRole=\"r_Employees\" ToRole=\"r_Manager\" />"
            + "</EntityType>" + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
            + "</EntityType>" + "<Association Name=\"ManagerEmployees\">"
            + "<End Multiplicity=\"*\" Role=\"r_Employees\"/>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"r_Manager\"/>"
            + "</Association></Schema></edmx:DataServices></edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidAssociation);
    try {
      parser.readMetadata(reader, true);
    } catch (EntityProviderException e) {
      assertEquals(EntityProviderException.MISSING_ATTRIBUTE.getKey(), e.getMessageReference().getKey());
      assertEquals(2, e.getMessageReference().getContent().size());
      assertEquals("Type", e.getMessageReference().getContent().get(0));
      assertEquals("End", e.getMessageReference().getContent().get(1));
      throw e;
    }
  }

  /**
   * Test missing type at function import.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void testMissingTypeAtFunctionImport() throws Exception {
    final String xml =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[1]
            + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<FunctionImport Name=\"EmployeeSearch\" ReturnType=\"Collection(RefScenario.Employee)\" " +
            "EntitySet=\"Employees\" m:HttpMethod=\"GET\">"
            + "<Parameter Name=\"q\" Nullable=\"true\" />" + "</FunctionImport>"
            + "</EntityContainer></Schema></edmx:DataServices></edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xml);
    try {
      parser.readMetadata(reader, true);
    } catch (EntityProviderException e) {
      assertEquals(EntityProviderException.MISSING_ATTRIBUTE.getKey(), e.getMessageReference().getKey());
      assertEquals(2, e.getMessageReference().getContent().size());
      assertEquals("Type", e.getMessageReference().getContent().get(0));
      assertEquals("Parameter", e.getMessageReference().getContent().get(1));
      throw e;
    }
  }

  /**
   * Test missing association.
   *
   * @throws Exception the exception
   */
  @Test(expected = EntityProviderException.class)
  public void testMissingAssociation() throws Exception {
    final String xmlWithAssociation =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployees\" " +
            "FromRole=\"r_Employees\" ToRole=\"r_Manager\" />"
            + "</EntityType>" + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>" + "<AssociationSet Name=\""
            + ASSOCIATION
            // + "\" Association=\"RefScenario." + ASSOCIATION
            + "\">" + "<End EntitySet=\"Employees\" Role=\"r_Employees\"/>" + "</AssociationSet>"
            + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices></edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithAssociation);
    try {
      parser.readMetadata(reader, true);
    } catch (EntityProviderException e) {
      assertEquals(EntityProviderException.MISSING_ATTRIBUTE.getKey(), e.getMessageReference().getKey());
      assertEquals(2, e.getMessageReference().getContent().size());
      assertEquals("Association", e.getMessageReference().getContent().get(0));
      assertEquals("AssociationSet", e.getMessageReference().getContent().get(1));
      throw e;
    }
  }

  /**
   * Test invalid association.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testInvalidAssociation() throws XMLStreamException, EntityProviderException {
    final String xmlWithInvalidAssociationSet =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployees\" " +
            "FromRole=\"r_Employees\" ToRole=\"r_Manager\" />"
            + "</EntityType>"
            + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
            + "<NavigationProperty Name=\"nm_Employees\" Relationship=\"RefScenario.ManagerEmployees\" " +
            "FromRole=\"r_Manager\" ToRole=\"r_Employees\" />"
            + "</EntityType>" + "<Association Name=\"" + ASSOCIATION + "\">"
            + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"r_Employees\">"
            + "<OnDelete Action=\"Cascade\"/>" + "</End>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"r_Manager\"/>" + "</Association>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<EntitySet Name=\"Managers\" EntityType=\"RefScenario.Manager\"/>" + "<AssociationSet Name=\""
            + ASSOCIATION + "\" Association=\"RefScenario2." + ASSOCIATION + "\">"
            + "<End EntitySet=\"Managers\" Role=\"r_Manager\"/>"
            + "<End EntitySet=\"Employees\" Role=\"r_Employees\"/>" + "</AssociationSet>" + "</EntityContainer>"
            + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidAssociationSet);
    parser.readMetadata(reader, true);

  }

  /**
   * Test invalid association end.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testInvalidAssociationEnd() throws XMLStreamException, EntityProviderException {
    final String employees = "r_Employees";
    final String manager = "r_Manager";
    final String xmlWithInvalidAssociationSetEnd =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>" + "<Property Name=\""
            + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployees\" FromRole=\""
            + employees + "\" ToRole=\"" + manager + "\" />" + "</EntityType>"
            + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
            + "<NavigationProperty Name=\"nm_Employees\" Relationship=\"RefScenario.ManagerEmployees\" FromRole=\""
            + manager + "\" ToRole=\"" + employees + "\" />" + "</EntityType>" + "<Association Name=\"" + ASSOCIATION
            + "\">"
            + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"" + employees + "1" + "\"/>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"" + manager + "\"/>" + "</Association>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<EntitySet Name=\"Managers\" EntityType=\"RefScenario.Manager\"/>" + "<AssociationSet Name=\""
            + ASSOCIATION + "\" Association=\"RefScenario2." + ASSOCIATION + "\">"
            + "<End EntitySet=\"Managers\" Role=\"" + manager + "\"/>" + "<End EntitySet=\"Employees\" Role=\""
            + employees + "\"/>" + "</AssociationSet>" + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>"
            + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidAssociationSetEnd);
    parser.readMetadata(reader, true);

  }

  /**
   * Test invalid association end 2.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testInvalidAssociationEnd2() throws XMLStreamException, EntityProviderException {
    final String employees = "r_Employees";
    final String manager = "r_Manager";
    final String xmlWithInvalidAssociationSetEnd =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>" + "<Property Name=\""
            + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployees\" FromRole=\""
            + employees + "\" ToRole=\"" + manager + "\" />" + "</EntityType>"
            + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
            + "<NavigationProperty Name=\"nm_Employees\" Relationship=\"RefScenario.ManagerEmployees\" FromRole=\""
            + manager + "\" ToRole=\"" + employees + "\" />" + "</EntityType>" + "<Association Name=\"" + ASSOCIATION
            + "\">"
            + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"" + employees + "\"/>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"" + manager + "\"/>" + "</Association>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Employee\"/>"
            + "<EntitySet Name=\"Managers\" EntityType=\"RefScenario.Manager\"/>" + "<AssociationSet Name=\""
            + ASSOCIATION + "\" Association=\"RefScenario2." + ASSOCIATION + "\">"
            + "<End EntitySet=\"Managers\" Role=\"" + manager + "\"/>" + "<End EntitySet=\"Managers\" Role=\""
            + manager + "\"/>" + "</AssociationSet>" + "</EntityContainer>" + "</Schema>" + "</edmx:DataServices>"
            + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithInvalidAssociationSetEnd);
    parser.readMetadata(reader, true);

  }

  /**
   * Test invalid entity set.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test(expected = EntityProviderException.class)
  public void testInvalidEntitySet() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\"" + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\"" + propertyNames[1] + "\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\"/>"
            + "</EntityType>" + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RefScenario.Mitarbeiter\"/>" + "</EntityContainer>"
            + "</Schema>" + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    parser.readMetadata(reader, true);

  }

  /**
   * Test entity type in other schema.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testEntityTypeInOtherSchema() throws XMLStreamException, EntityProviderException {
    final String xmWithEntityContainer =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\"" + Edm.NAMESPACE_EDMX_2007_06 + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\"" + Edm.NAMESPACE_M_2007_08 + "\">"
            + "<Schema Namespace=\"" + NAMESPACE + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Employee\" m:HasStream=\"true\">" + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\"" + propertyNames[0] + "\" Type=\"Edm.String\" Nullable=\"false\"/>" + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Photos\" EntityType=\"" + NAMESPACE2 + ".Photo\"/>" + "</EntityContainer>"
            + "</Schema>" + "<Schema Namespace=\"" + NAMESPACE2 + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">"
            + "<EntityType Name= \"Photo\">" + "<Key><PropertyRef Name=\"Id\"/></Key>"
            + "<Property Name=\"Id\" Type=\"Edm.Int32\" Nullable=\"false\"/>" + "</EntityType>" + "</Schema>"
            + "</edmx:DataServices>"
            + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmWithEntityContainer);
    DataServices result = parser.readMetadata(reader, true);
    assertEquals("2.0", result.getDataServiceVersion());
    for (Schema schema : result.getSchemas()) {
      for (EntityContainer container : schema.getEntityContainers()) {
        assertEquals("Container1", container.getName());
        for (EntitySet entitySet : container.getEntitySets()) {
          assertEquals(NAMESPACE2, entitySet.getEntityType().getNamespace());
          assertEquals("Photo", entitySet.getEntityType().getName());
        }
      }
    }
  }

  /**
   * Scenario test.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void scenarioTest() throws XMLStreamException, EntityProviderException {
    final String ASSOCIATION2 = "TeamEmployees";
    final String xml =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" Alias=\"RS\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\""
            + propertyNames[0]
            + "\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\""
            + propertyNames[2]
            + "\" Type=\"RefScenario.c_Location\" Nullable=\"false\"/>"
            + "<NavigationProperty Name=\"ne_Manager\" Relationship=\"RefScenario.ManagerEmployees\" " +
            "FromRole=\"r_Employees\" ToRole=\"r_Manager\" />"
            + "<NavigationProperty Name=\"ne_Team\" Relationship=\"RefScenario.TeamEmployees\" " +
            "FromRole=\"r_Employees\" ToRole=\"r_Team\" />"
            + "</EntityType>"
            + "<EntityType Name=\"Manager\" BaseType=\"RefScenario.Employee\" m:HasStream=\"true\">"
            + "<NavigationProperty Name=\"nm_Employees\" Relationship=\"RefScenario.ManagerEmployees\" " +
            "FromRole=\"r_Manager\" ToRole=\"r_Employees\" />"
            + "</EntityType>"
            + "<EntityType Name=\"Team\">"
            + "<Key>"
            + "<PropertyRef Name=\"Id\"/>"
            + "</Key>"
            + "<NavigationProperty Name=\"nt_Employees\" Relationship=\"RefScenario.TeamEmployees\"" +
            " FromRole=\"r_Team\" ToRole=\"r_Employees\" />"
            + "</EntityType>" + "<ComplexType Name=\"c_Location\">"
            + "<Property Name=\"Country\" Type=\"Edm.String\"/>" + "</ComplexType>" + "<Association Name=\""
            + ASSOCIATION + "\">" + "<End Type=\"RS.Employee\" Multiplicity=\"*\" Role=\"r_Employees\">"
            + "<OnDelete Action=\"Cascade\"/>" + "</End>"
            + "<End Type=\"RefScenario.Manager\" Multiplicity=\"1\" Role=\"r_Manager\"/>" + "</Association>"
            + "<Association Name=\"" + ASSOCIATION2 + "\">"
            + "<End Type=\"RefScenario.Employee\" Multiplicity=\"*\" Role=\"r_Employees\"/>"
            + "<End Type=\"RefScenario.Team\" Multiplicity=\"1\" Role=\"r_Team\"/>" + "</Association>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Employees\" EntityType=\"RS.Employee\"/>"
            + "<EntitySet Name=\"Managers\" EntityType=\"RefScenario.Manager\"/>"
            + "<EntitySet Name=\"Teams\" EntityType=\"RefScenario.Team\"/>" + "<AssociationSet Name=\"" + ASSOCIATION
            + "\" Association=\"RefScenario." + ASSOCIATION + "\">"
            + "<End EntitySet=\"Managers\" Role=\"r_Manager\"/>"
            + "<End EntitySet=\"Employees\" Role=\"r_Employees\"/>" + "</AssociationSet>" + "<AssociationSet Name=\""
            + ASSOCIATION2 + "\" Association=\"RefScenario." + ASSOCIATION2 + "\">"
            + "<End EntitySet=\"Teams\" Role=\"r_Team\"/>" + "<End EntitySet=\"Employees\" Role=\"r_Employees\"/>"
            + "</AssociationSet>" + "</EntityContainer>" + "</Schema>" + "<Schema Namespace=\"" + NAMESPACE2
            + "\" xmlns=\"" + Edm.NAMESPACE_EDM_2008_09 + "\">" + "<EntityType Name= \"Photo\">" + "<Key>"
            + "<PropertyRef Name=\"Id\"/>" + "<PropertyRef Name=\"Name\"/>" + "</Key>"
            + "<Property Name=\"Id\" Type=\"Edm.Int32\" Nullable=\"false\" ConcurrencyMode=\"Fixed\" MaxLength=\""
            + MAX_LENGTH + "\"/>" + "<Property Name=\"Name\" Type=\"Edm.String\" Unicode=\"true\" DefaultValue=\""
            + DEFAULT_VALUE + "\" FixedLength=\"false\"/>"
            + "<Property Name=\"BinaryData\" Type=\"Edm.Binary\" m:MimeType=\"" + MIME_TYPE + "\"/>"
            + "<Property Name=\"Содержание\" Type=\"Edm.String\" m:FC_TargetPath=\"" + FC_TARGET_PATH
            + "\" m:FC_NsUri=\"" + FC_NS_URI + "\"" + " m:FC_NsPrefix=\"" + FC_NS_PREFIX + "\" m:FC_KeepInContent=\""
            + FC_KEEP_IN_CONTENT + "\" m:FC_ContentKind=\"text\" >" + "</Property>" + "</EntityType>"
            + "<EntityContainer Name=\"Container1\" m:IsDefaultEntityContainer=\"true\">"
            + "<EntitySet Name=\"Photos\" EntityType=\"RefScenario2.Photo\"/>" + "</EntityContainer>" + "</Schema>"
            + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xml);
    DataServices result = parser.readMetadata(reader, true);
    assertEquals(2, result.getSchemas().size());
    for (Schema schema : result.getSchemas()) {
      if (NAMESPACE.equals(schema.getNamespace())) {
        assertEquals(3, schema.getEntityTypes().size());
        assertEquals(1, schema.getComplexTypes().size());
        assertEquals(2, schema.getAssociations().size());
        assertEquals(1, schema.getEntityContainers().size());
      } else if (NAMESPACE2.equals(schema.getNamespace())) {
        assertEquals(1, schema.getEntityTypes().size());
        assertEquals(0, schema.getComplexTypes().size());
        assertEquals(0, schema.getAssociations().size());
        assertEquals(1, schema.getEntityContainers().size());
        for (EntityType entityType : schema.getEntityTypes()) {
          assertEquals(2, entityType.getKey().getKeys().size());
        }
      }
    }
  }

  /**
   * Test ref scenario.
   *
   * @throws Exception the exception
   */
  @Test
  public void testRefScenario() throws Exception {
    EdmProvider testProvider = new EdmTestProvider();
    ODataResponse response = EntityProvider.writeMetadata(testProvider.getSchemas(), null);

    String stream = StringHelper.inputStreamToString((InputStream) response.getEntity());
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    DataServices result = parser.readMetadata(createStreamReader(stream), true);

    ODataResponse response2 = EntityProvider.writeMetadata(result.getSchemas(), null);
    String streamAfterParse = StringHelper.inputStreamToString((InputStream) response2.getEntity());
    assertEquals(stream, streamAfterParse);
  }

  /**
   * Test annotations.
   *
   * @throws XMLStreamException the XML stream exception
   * @throws EntityProviderException the entity provider exception
   */
  @Test
  public void testAnnotations() throws XMLStreamException, EntityProviderException {
    final String xmlWithAnnotations =
        "<edmx:Edmx Version=\"1.0\" xmlns:edmx=\""
            + Edm.NAMESPACE_EDMX_2007_06
            + "\" xmlns:annoPrefix=\"http://annoNamespace\">"
            + "<edmx:DataServices m:DataServiceVersion=\"2.0\" xmlns:m=\""
            + Edm.NAMESPACE_M_2007_08
            + "\">"
            + "<Schema Namespace=\""
            + NAMESPACE
            + "\" xmlns=\""
            + Edm.NAMESPACE_EDM_2008_09
            + "\">"
            + "<EntityType Name= \"Employee\" prefix1:href=\"http://foo\" xmlns:prefix1=\"namespaceForAnno\">"
            + "<Key><PropertyRef Name=\"EmployeeId\"/></Key>"
            + "<Property Name=\"EmployeeId\" Type=\"Edm.String\" Nullable=\"false\"/>"
            + "<Property Name=\"EmployeeName\" Type=\"Edm.String\" m:FC_TargetPath=\"SyndicationTitle\" " +
            "annoPrefix:annoName=\"annoText\">"
            + "<annoPrefix:propertyAnnoElement>text</annoPrefix:propertyAnnoElement>"
            + "<annoPrefix:propertyAnnoElement2 />"
            + "</Property>"
            + "</EntityType>"
            + "<annoPrefix:schemaElementTest1>"
            + "<prefix:schemaElementTest2 xmlns:prefix=\"namespace\">text3"
            + "</prefix:schemaElementTest2>"
            + "<annoPrefix:schemaElementTest3 rel=\"self\" pre:href=\"http://foo\" " +
            "xmlns:pre=\"namespaceForAnno\">text4</annoPrefix:schemaElementTest3>"
            + " </annoPrefix:schemaElementTest1>" + "</Schema>"
            + "</edmx:DataServices>" + "</edmx:Edmx>";
    XmlMetadataConsumer parser = new XmlMetadataConsumer();
    XMLStreamReader reader = createStreamReader(xmlWithAnnotations);
    DataServices result = parser.readMetadata(reader, false);
    for (Schema schema : result.getSchemas()) {
      assertEquals(1, schema.getAnnotationElements().size());
      for (AnnotationElement annoElement : schema.getAnnotationElements()) {
        for (AnnotationElement childAnnoElement : annoElement.getChildElements()) {
          if ("schemaElementTest2".equals(childAnnoElement.getName())) {
            assertEquals("prefix", childAnnoElement.getPrefix());
            assertEquals("namespace", childAnnoElement.getNamespace());
            assertEquals("text3", childAnnoElement.getText());
          } else if ("schemaElementTest3".equals(childAnnoElement.getName())) {
            assertEquals("text4", childAnnoElement.getText());
            assertEquals("rel", childAnnoElement.getAttributes().get(0).getName());
            assertEquals("self", childAnnoElement.getAttributes().get(0).getText());
            assertEquals("", childAnnoElement.getAttributes().get(0).getPrefix());
            assertNull(childAnnoElement.getAttributes().get(0).getNamespace());
            assertEquals("href", childAnnoElement.getAttributes().get(1).getName());
            assertEquals("pre", childAnnoElement.getAttributes().get(1).getPrefix());
            assertEquals("namespaceForAnno", childAnnoElement.getAttributes().get(1).getNamespace());
            assertEquals("http://foo", childAnnoElement.getAttributes().get(1).getText());
          } else {
            throw new EntityProviderException(null, "xmlWithAnnotations");
          }
        }
      }
      for (EntityType entityType : schema.getEntityTypes()) {
        assertEquals(1, entityType.getAnnotationAttributes().size());
        AnnotationAttribute attr = entityType.getAnnotationAttributes().get(0);
        assertEquals("href", attr.getName());
        assertEquals("prefix1", attr.getPrefix());
        assertEquals("namespaceForAnno", attr.getNamespace());
        assertEquals("http://foo", attr.getText());
        for (Property property : entityType.getProperties()) {
          if ("EmployeeName".equals(property.getName())) {
            assertEquals(2, property.getAnnotationElements().size());
            for (AnnotationElement anElement : property.getAnnotationElements()) {
              if ("propertyAnnoElement".equals(anElement.getName())) {
                assertEquals("text", anElement.getText());
              }
            }
            for (AnnotationAttribute anAttribute : property.getAnnotationAttributes()) {
              assertEquals("annoName", anAttribute.getName());
              assertEquals("annoPrefix", anAttribute.getPrefix());
              assertEquals("annoText", anAttribute.getText());
              assertEquals("http://annoNamespace", anAttribute.getNamespace());
            }
          }
        }
      }
    }
  }

  /**
   * Edmx references.
   *
   * @throws Exception the exception
   */
  @Test
  public void edmxReferences() throws Exception {
    DataServices serviceMetadata = new DataServices();
    List<AnnotationElement> annoElements = new ArrayList<AnnotationElement>();
    annoElements.add(createElementWithoutInclude());
    annoElements.add(createElementWithInclude());
    serviceMetadata.setAnnotationElements(annoElements);
    serviceMetadata.setDataServiceVersion(ODataServiceVersion.V20);
    ODataResponse response = EntityProvider.writeMetadata(serviceMetadata, null);

    EntityProvider.readMetadata(response.getEntityAsStream(), false);
  }

  /**
   * Creates the element with include.
   *
   * @return the annotation element
   */
  private AnnotationElement createElementWithInclude() {
    List<AnnotationAttribute> childAttributes = new ArrayList<AnnotationAttribute>();
    childAttributes.add(new AnnotationAttribute().setName("Namespace").setText("Org.OData.Core.V1"));
    childAttributes.add(new AnnotationAttribute().setName("Alias").setText("UI"));
    List<AnnotationElement> childElements = new ArrayList<AnnotationElement>();
    childElements.add(new AnnotationElement().setName("Include").setNamespace(
        "http://docs.oasis-open.org/odata/ns/edmx").setPrefix("edmx").setAttributes(childAttributes));
    List<AnnotationAttribute> referenceAttributes = new ArrayList<AnnotationAttribute>();
    referenceAttributes.add(new AnnotationAttribute().setName("Uri").setText("http://someurl2.com"));
    return new AnnotationElement().setName("Reference").setPrefix("edmx").setNamespace(
        "http://docs.oasis-open.org/odata/ns/edmx").setAttributes(referenceAttributes).setChildElements(childElements);
  }

  /**
   * Creates the element without include.
   *
   * @return the annotation element
   */
  private AnnotationElement createElementWithoutInclude() {
    List<AnnotationAttribute> referenceAttributes = new ArrayList<AnnotationAttribute>();
    referenceAttributes.add(new AnnotationAttribute().setName("Uri").setText("http://someurl.com"));
    return new AnnotationElement().setName("Reference").setPrefix("edmx").setNamespace(
        "http://docs.oasis-open.org/odata/ns/edmx").setAttributes(referenceAttributes);
  }

  /**
   * Creates the stream reader.
   *
   * @param xml the xml
   * @return the XML stream reader
   * @throws XMLStreamException the XML stream exception
   */
  private XMLStreamReader createStreamReader(final String xml) throws XMLStreamException {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    factory.setProperty(XMLInputFactory.IS_VALIDATING, false);
    factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
    XMLStreamReader streamReader = factory.createXMLStreamReader(new StringReader(xml));

    return streamReader;
  }

}
