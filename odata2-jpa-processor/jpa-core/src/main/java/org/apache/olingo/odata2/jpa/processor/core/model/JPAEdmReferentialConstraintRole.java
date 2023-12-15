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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.metamodel.Attribute;

import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.ReferentialConstraintRole;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmAssociationView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmEntityTypeView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmMapping;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmPropertyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmReferentialConstraintRoleView;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmReferentialConstraintRole.
 */
public class JPAEdmReferentialConstraintRole extends JPAEdmBaseViewImpl implements JPAEdmReferentialConstraintRoleView {

  /** The first build. */
  private boolean firstBuild = true;

  /** The entity type view. */
  private JPAEdmEntityTypeView entityTypeView;
  
  /** The role type. */
  private JPAEdmReferentialConstraintRoleView.RoleType roleType;

  /** The jpa attribute. */
  private Attribute<?, ?> jpaAttribute;
  
  /** The jpa column names. */
  private List<String[]> jpaColumnNames;
  
  /** The association. */
  private Association association;

  /** The role exists. */
  private boolean roleExists = false;

  /** The builder. */
  private JPAEdmRefConstraintRoleBuilder builder;
  
  /** The current role. */
  private ReferentialConstraintRole currentRole;

  /**
   * Instantiates a new JPA edm referential constraint role.
   *
   * @param roleType the role type
   * @param entityTypeView the entity type view
   * @param propertyView the property view
   * @param associationView the association view
   */
  public JPAEdmReferentialConstraintRole(final JPAEdmReferentialConstraintRoleView.RoleType roleType,
      final JPAEdmEntityTypeView entityTypeView, final JPAEdmPropertyView propertyView,
      final JPAEdmAssociationView associationView) {

    super(entityTypeView);
    this.entityTypeView = entityTypeView;
    this.roleType = roleType;

    jpaAttribute = propertyView.getJPAAttribute();
    jpaColumnNames = propertyView.getJPAJoinColumns();
    association = associationView.getEdmAssociation();

  }

  /**
   * Checks if is exists.
   *
   * @return true, if is exists
   */
  @Override
  public boolean isExists() {
    return roleExists;

  }

  /**
   * Gets the builder.
   *
   * @return the builder
   */
  @Override
  public JPAEdmBuilder getBuilder() {
    if (builder == null) {
      builder = new JPAEdmRefConstraintRoleBuilder();
    }

    return builder;
  }

  /**
   * Gets the role type.
   *
   * @return the role type
   */
  @Override
  public RoleType getRoleType() {
    return roleType;
  }

  /**
   * Gets the edm referential constraint role.
   *
   * @return the edm referential constraint role
   */
  @Override
  public ReferentialConstraintRole getEdmReferentialConstraintRole() {
    return currentRole;
  }

  /**
   * Gets the JPA column name.
   *
   * @return the JPA column name
   */
  @Override
  public String getJPAColumnName() {
    return null;
  }

  /**
   * Gets the edm entity type name.
   *
   * @return the edm entity type name
   */
  @Override
  public String getEdmEntityTypeName() {
    return null;
  }

  /**
   * Gets the edm association name.
   *
   * @return the edm association name
   */
  @Override
  public String getEdmAssociationName() {
    return null;
  }

  /**
   * The Class JPAEdmRefConstraintRoleBuilder.
   */
  private class JPAEdmRefConstraintRoleBuilder implements JPAEdmBuilder {

    /**
     * Builds the.
     *
     * @throws ODataJPAModelException the o data JPA model exception
     */
    @Override
    public void build() throws ODataJPAModelException {
      if (firstBuild) {
        firstBuild();
      } else if (roleExists) {
        try {
          buildRole();
        } catch (SecurityException e) {
          throw ODataJPAModelException.throwException(ODataJPAModelException.GENERAL.addContent(e.getMessage()), e);
        } catch (NoSuchFieldException e) {
          throw ODataJPAModelException.throwException(ODataJPAModelException.GENERAL.addContent(e.getMessage()), e);
        }
      }

    }

    /**
     * First build.
     */
    private void firstBuild() {
      firstBuild = false;
      isConsistent = false;

      if (jpaColumnNames == null || jpaColumnNames.isEmpty()) {
        roleExists = false;
        return;
      } else {
        roleExists = true;
      }
    }

    /**
     * Builds the role.
     *
     * @throws SecurityException the security exception
     * @throws NoSuchFieldException the no such field exception
     */
    private void buildRole() throws SecurityException, NoSuchFieldException {

      int index = 0;
      if (currentRole == null) {
        currentRole = new ReferentialConstraintRole();
        String jpaAttributeType = null;
        String jpaColumnName = null;
        EntityType edmEntityType = null;

        if (roleType == RoleType.PRINCIPAL) {
          jpaAttributeType = jpaAttribute.getJavaType().getSimpleName();
          if (jpaAttribute.isCollection()) {
            Type type =
                ((ParameterizedType) jpaAttribute.getJavaMember().getDeclaringClass().getDeclaredField(
                    jpaAttribute.getName()).getGenericType()).getActualTypeArguments()[0];
            int lastIndexOfDot = type.toString().lastIndexOf(".");
            jpaAttributeType = type.toString().substring(lastIndexOfDot + 1);
          }
          edmEntityType = entityTypeView.searchEdmEntityType(jpaAttributeType);
          index = 1;
        } else if (roleType == RoleType.DEPENDENT) {
          edmEntityType =
              entityTypeView.searchEdmEntityType(jpaAttribute.getDeclaringType().getJavaType().getSimpleName());
        }

        List<PropertyRef> propertyRefs = new ArrayList<PropertyRef>();
        if (edmEntityType != null) {
          for (String[] columnName : jpaColumnNames) {
            for (Property property : edmEntityType.getProperties()) {
              jpaColumnName = ((JPAEdmMapping) property.getMapping()).getJPAColumnName();
              if (columnName[index].equals(jpaColumnName) ||
                  columnName[index].equals(property.getName())) {
                PropertyRef propertyRef = new PropertyRef();
                propertyRef.setName(property.getName());
                propertyRefs.add(propertyRef);
                break;
              }
            }
          }
          currentRole.setPropertyRefs(propertyRefs);
          if (propertyRefs.isEmpty()) {
            isConsistent = false;
            return;
          }
          // First condition is required for Self Joins where the entity type on both ends are same
          AssociationEnd end1 = association.getEnd1();
          AssociationEnd end2 = association.getEnd2();
          if (end1.getType().getName().equals(end2.getType().getName())) {
            if (roleType == RoleType.PRINCIPAL) {
              currentRole.setRole(end1.getRole());
            } else {
              currentRole.setRole(end2.getRole());
            }
            isConsistent = true;
          } else {
            if (end1.getType().getName().equals(edmEntityType.getName())) {
              currentRole.setRole(end1.getRole());
              isConsistent = true;
            } else if (end2.getType().getName().equals(edmEntityType.getName())) {
              currentRole.setRole(end2.getRole());
              isConsistent = true;
            }
          }
        }

      }
    }
  }
}
