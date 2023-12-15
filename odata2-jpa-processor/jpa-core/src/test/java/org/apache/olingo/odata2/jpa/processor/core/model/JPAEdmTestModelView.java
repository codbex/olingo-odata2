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

import java.util.HashMap;
import java.util.List;

import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.Metamodel;

import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.ComplexProperty;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Key;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.ReferentialConstraint;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmMappingModelAccess;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationEndView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationSetView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmBaseView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmComplexPropertyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmComplexTypeView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityContainerView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntitySetView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityTypeView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmKeyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmModelView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmNavigationPropertyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmPropertyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmReferentialConstraintView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmTestModelView.
 */
public class JPAEdmTestModelView implements JPAEdmAssociationEndView, JPAEdmAssociationSetView, JPAEdmAssociationView,
    JPAEdmBaseView, JPAEdmComplexPropertyView, JPAEdmComplexTypeView, JPAEdmEntityContainerView, JPAEdmEntitySetView,
    JPAEdmEntityTypeView, JPAEdmKeyView, JPAEdmModelView, JPAEdmNavigationPropertyView, JPAEdmPropertyView,
    JPAEdmReferentialConstraintView, JPAEdmSchemaView {

  /** The mapping model access. */
  protected JPAEdmMappingModelAccess mappingModelAccess;

  /**
   * Gets the edm schema.
   *
   * @return the edm schema
   */
  @Override
  public Schema getEdmSchema() {
    return null;
  }

  /**
   * Gets the JPA edm association view.
   *
   * @return the JPA edm association view
   */
  @Override
  public JPAEdmAssociationView getJPAEdmAssociationView() {
    return null;
  }

  /**
   * Gets the JPA edm complex type view.
   *
   * @return the JPA edm complex type view
   */
  @Override
  public JPAEdmComplexTypeView getJPAEdmComplexTypeView() {
    return null;
  }

  /**
   * Gets the JPA edm entity container view.
   *
   * @return the JPA edm entity container view
   */
  @Override
  public JPAEdmEntityContainerView getJPAEdmEntityContainerView() {
    return null;
  }

  /**
   * Gets the JPA attribute.
   *
   * @return the JPA attribute
   */
  @Override
  public Attribute<?, ?> getJPAAttribute() {
    return null;
  }

  /**
   * Gets the JPA edm key view.
   *
   * @return the JPA edm key view
   */
  @Override
  public JPAEdmKeyView getJPAEdmKeyView() {
    return null;
  }

  /**
   * Gets the edm property list.
   *
   * @return the edm property list
   */
  @Override
  public List<Property> getEdmPropertyList() {
    return null;
  }

  /**
   * Gets the edm simple property.
   *
   * @return the edm simple property
   */
  @Override
  public SimpleProperty getEdmSimpleProperty() {
    return null;
  }

  /**
   * Gets the edm schema view.
   *
   * @return the edm schema view
   */
  @Override
  public JPAEdmSchemaView getEdmSchemaView() {
    return null;
  }

  /**
   * Gets the edm key.
   *
   * @return the edm key
   */
  @Override
  public Key getEdmKey() {
    return null;
  }

  /**
   * Gets the consistent edm entity types.
   *
   * @return the consistent edm entity types
   */
  @Override
  public List<EntityType> getConsistentEdmEntityTypes() {
    return null;
  }

  /**
   * Gets the edm entity type.
   *
   * @return the edm entity type
   */
  @Override
  public EntityType getEdmEntityType() {
    return null;
  }

  /**
   * Gets the JPA entity type.
   *
   * @return the JPA entity type
   */
  @Override
  public jakarta.persistence.metamodel.EntityType<?> getJPAEntityType() {
    return null;
  }

  /**
   * Gets the consistent edm entity set list.
   *
   * @return the consistent edm entity set list
   */
  @Override
  public List<EntitySet> getConsistentEdmEntitySetList() {
    return null;
  }

  /**
   * Gets the edm entity set.
   *
   * @return the edm entity set
   */
  @Override
  public EntitySet getEdmEntitySet() {
    return null;
  }

  /**
   * Gets the JPA edm entity type view.
   *
   * @return the JPA edm entity type view
   */
  @Override
  public JPAEdmEntityTypeView getJPAEdmEntityTypeView() {
    return null;
  }

  /**
   * Gets the consistent edm entity container list.
   *
   * @return the consistent edm entity container list
   */
  @Override
  public List<EntityContainer> getConsistentEdmEntityContainerList() {
    return null;
  }

  /**
   * Gets the edm association set view.
   *
   * @return the edm association set view
   */
  @Override
  public JPAEdmAssociationSetView getEdmAssociationSetView() {
    return null;
  }

  /**
   * Gets the edm entity container.
   *
   * @return the edm entity container
   */
  @Override
  public EntityContainer getEdmEntityContainer() {
    return null;
  }

  /**
   * Gets the JPA edm entity set view.
   *
   * @return the JPA edm entity set view
   */
  @Override
  public JPAEdmEntitySetView getJPAEdmEntitySetView() {
    return null;
  }

  /**
   * Adds the JPA edm comple type view.
   *
   * @param arg0 the arg 0
   */
  @Override
  public void addJPAEdmCompleTypeView(final JPAEdmComplexTypeView arg0) {

  }

  /**
   * Gets the consistent edm complex types.
   *
   * @return the consistent edm complex types
   */
  @Override
  public List<ComplexType> getConsistentEdmComplexTypes() {
    return null;
  }

  /**
   * Gets the edm complex type.
   *
   * @return the edm complex type
   */
  @Override
  public ComplexType getEdmComplexType() {
    return null;
  }

  /**
   * Gets the JPA embeddable type.
   *
   * @return the JPA embeddable type
   */
  @Override
  public EmbeddableType<?> getJPAEmbeddableType() {
    return null;
  }

  /**
   * Search edm complex type.
   *
   * @param arg0 the arg 0
   * @return the complex type
   */
  @Override
  public ComplexType searchEdmComplexType(final String arg0) {
    return null;
  }

  /**
   * Search edm complex type.
   *
   * @param arg0 the arg 0
   * @return the complex type
   */
  @Override
  public ComplexType searchEdmComplexType(final FullQualifiedName arg0) {
    return null;
  }

  /**
   * Gets the edm complex property.
   *
   * @return the edm complex property
   */
  @Override
  public ComplexProperty getEdmComplexProperty() {
    return null;
  }

  /**
   * Clean.
   */
  @Override
  public void clean() {

  }

  /**
   * Gets the builder.
   *
   * @return the builder
   */
  @Override
  public JPAEdmBuilder getBuilder() {
    return null;
  }

  /**
   * Gets the JPA meta model.
   *
   * @return the JPA meta model
   */
  @Override
  public Metamodel getJPAMetaModel() {
    return null;
  }

  /**
   * Gets the p unit name.
   *
   * @return the p unit name
   */
  @Override
  public String getpUnitName() {
    return null;
  }

  /**
   * Checks if is consistent.
   *
   * @return true, if is consistent
   */
  @Override
  public boolean isConsistent() {
    return false;
  }

  /**
   * Adds the JPA edm ref constraint view.
   *
   * @param arg0 the arg 0
   */
  @Override
  public void addJPAEdmRefConstraintView(final JPAEdmReferentialConstraintView arg0) {

  }

  /**
   * Gets the edm referential constraint.
   *
   * @return the edm referential constraint
   */
  @Override
  public ReferentialConstraint getEdmReferentialConstraint() {
    return null;
  }

  /**
   * Gets the edm relation ship name.
   *
   * @return the edm relation ship name
   */
  @Override
  public String getEdmRelationShipName() {
    return null;
  }

  /**
   * Checks if is exists.
   *
   * @return true, if is exists
   */
  @Override
  public boolean isExists() {
    return false;
  }

  /**
   * Search edm entity type.
   *
   * @param arg0 the arg 0
   * @return the entity type
   */
  @Override
  public EntityType searchEdmEntityType(final String arg0) {
    return null;
  }

  /**
   * Gets the JPA edm referential constraint view.
   *
   * @return the JPA edm referential constraint view
   */
  @Override
  public JPAEdmReferentialConstraintView getJPAEdmReferentialConstraintView() {
    return null;
  }

  /**
   * Gets the consistent edm association list.
   *
   * @return the consistent edm association list
   */
  @Override
  public List<Association> getConsistentEdmAssociationList() {
    return null;
  }

  /**
   * Search association.
   *
   * @param arg0 the arg 0
   * @return the association
   */
  @Override
  public Association searchAssociation(final JPAEdmAssociationEndView arg0) {
    return null;
  }

  /**
   * Gets the consistent edm association set list.
   *
   * @return the consistent edm association set list
   */
  @Override
  public List<AssociationSet> getConsistentEdmAssociationSetList() {
    return null;
  }

  /**
   * Gets the edm association.
   *
   * @return the edm association
   */
  @Override
  public Association getEdmAssociation() {
    return null;
  }

  /**
   * Gets the edm association set.
   *
   * @return the edm association set
   */
  @Override
  public AssociationSet getEdmAssociationSet() {
    return null;
  }

  /**
   * Compare.
   *
   * @param arg0 the arg 0
   * @param arg1 the arg 1
   * @return true, if successful
   */
  @Override
  public boolean compare(final AssociationEnd arg0, final AssociationEnd arg1) {
    return false;
  }

  /**
   * Gets the edm association end 1.
   *
   * @return the edm association end 1
   */
  @Override
  public AssociationEnd getEdmAssociationEnd1() {
    return null;
  }

  /**
   * Gets the edm association end 2.
   *
   * @return the edm association end 2
   */
  @Override
  public AssociationEnd getEdmAssociationEnd2() {
    return null;
  }

  /**
   * Gets the JPA edm navigation property view.
   *
   * @return the JPA edm navigation property view
   */
  @Override
  public JPAEdmNavigationPropertyView getJPAEdmNavigationPropertyView() {
    return null;
  }

  /**
   * Adds the JPA edm navigation property view.
   *
   * @param view the view
   */
  @Override
  public void addJPAEdmNavigationPropertyView(final JPAEdmNavigationPropertyView view) {

  }

  /**
   * Gets the consistent edm navigation properties.
   *
   * @return the consistent edm navigation properties
   */
  @Override
  public List<NavigationProperty> getConsistentEdmNavigationProperties() {
    return null;
  }

  /**
   * Gets the edm navigation property.
   *
   * @return the edm navigation property
   */
  @Override
  public NavigationProperty getEdmNavigationProperty() {
    return null;
  }

  /**
   * Expand edm complex type.
   *
   * @param complexType the complex type
   * @param expandedPropertyList the expanded property list
   * @param embeddablePropertyName the embeddable property name
   */
  @Override
  public void expandEdmComplexType(final ComplexType complexType, final List<Property> expandedPropertyList,
      final String embeddablePropertyName) {

  }

  /**
   * Gets the JPA edm mapping model access.
   *
   * @return the JPA edm mapping model access
   */
  @Override
  public JPAEdmMappingModelAccess getJPAEdmMappingModelAccess() {
    return null;
  }

  /**
   * Register operations.
   *
   * @param customClass the custom class
   * @param methodNames the method names
   */
  @Override
  public void registerOperations(final Class<?> customClass, final String[] methodNames) {
    // Do nothing
  }

  /**
   * Gets the registered operations.
   *
   * @return the registered operations
   */
  @Override
  public HashMap<Class<?>, String[]> getRegisteredOperations() {
    return null;
  }

  /**
   * Gets the JPA edm extension.
   *
   * @return the JPA edm extension
   */
  @Override
  public JPAEdmExtension getJPAEdmExtension() {
    return null;
  }

  /**
   * Adds the JPA edm association view.
   *
   * @param associationView the association view
   * @param associationEndView the association end view
   */
  @Override
  public void addJPAEdmAssociationView(final JPAEdmAssociationView associationView,
      final JPAEdmAssociationEndView associationEndView) {
    // TODO Auto-generated method stub

  }

  /**
   * Gets the number of associations with similar end points.
   *
   * @param view the view
   * @return the number of associations with similar end points
   */
  @Override
  public int getNumberOfAssociationsWithSimilarEndPoints(final JPAEdmAssociationEndView view) {
    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * Gets the join column names.
   *
   * @return the join column names
   */
  @Override
  public String[] getJoinColumnNames() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the join column reference column names.
   *
   * @return the join column reference column names
   */
  @Override
  public String[] getJoinColumnReferenceColumnNames() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the mapped by name.
   *
   * @return the mapped by name
   */
  @Override
  public String getMappedByName() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the owning property name.
   *
   * @return the owning property name
   */
  @Override
  public String getOwningPropertyName() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Checks if is default naming skipped.
   *
   * @return true, if is default naming skipped
   */
  @Override
  public boolean isDefaultNamingSkipped() {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Checks if is referenced in key.
   *
   * @param complexTypeName the complex type name
   * @return true, if is referenced in key
   */
  @Override
  public boolean isReferencedInKey(final String complexTypeName) {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * Sets the referenced in key.
   *
   * @param complexTypeName the new referenced in key
   */
  @Override
  public void setReferencedInKey(final String complexTypeName) {
    // TODO Auto-generated method stub

  }

  /**
   * Gets the JPA referenced attribute.
   *
   * @return the JPA referenced attribute
   */
  @Override
  public Attribute<?, ?> getJPAReferencedAttribute() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Gets the JPA join columns.
   *
   * @return the JPA join columns
   */
  @Override
  public List<String[]> getJPAJoinColumns() {
    // TODO Auto-generated method stub
    return null;
  }

}
