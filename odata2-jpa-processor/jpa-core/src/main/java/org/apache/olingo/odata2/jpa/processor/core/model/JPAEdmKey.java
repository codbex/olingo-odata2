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

import java.util.ArrayList;
import java.util.List;

import org.apache.olingo.odata2.api.edm.provider.ComplexProperty;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.Facets;
import org.apache.olingo.odata2.api.edm.provider.Key;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmBuilder;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmComplexTypeView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmKeyView;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmPropertyView;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmKey.
 */
public class JPAEdmKey extends JPAEdmBaseViewImpl implements JPAEdmKeyView {

  /** The property view. */
  private JPAEdmPropertyView propertyView;
  
  /** The complex type view. */
  private JPAEdmComplexTypeView complexTypeView = null;
  
  /** The is build mode complex type. */
  private boolean isBuildModeComplexType = false;
  
  /** The key. */
  private Key key;

  /**
   * Instantiates a new JPA edm key.
   *
   * @param view the view
   */
  public JPAEdmKey(final JPAEdmProperty view) {
    super(view);
    propertyView = view;
  }

  /**
   * Instantiates a new JPA edm key.
   *
   * @param complexTypeView the complex type view
   * @param propertyView the property view
   */
  public JPAEdmKey(final JPAEdmComplexTypeView complexTypeView, final JPAEdmPropertyView propertyView) {
    super(complexTypeView);
    this.propertyView = propertyView;
    this.complexTypeView = complexTypeView;
    isBuildModeComplexType = true;
  }

  /**
   * Gets the builder.
   *
   * @return the builder
   */
  @Override
  public JPAEdmBuilder getBuilder() {
    if (builder == null) {
      builder = new JPAEdmKeyBuider();
    }

    return builder;
  }

  /**
   * Gets the edm key.
   *
   * @return the edm key
   */
  @Override
  public Key getEdmKey() {
    return key;
  }

  /**
   * The Class JPAEdmKeyBuider.
   */
  private class JPAEdmKeyBuider implements JPAEdmBuilder {

    /**
     * Builds the.
     *
     * @throws ODataJPAModelException the o data JPA model exception
     */
    @Override
    public void build() throws ODataJPAModelException {

      List<PropertyRef> propertyRefList = null;
      if (key == null) {
        key = new Key();
      }

      if (key.getKeys() == null) {
        propertyRefList = new ArrayList<PropertyRef>();
        key.setKeys(propertyRefList);
      } else {
        propertyRefList = key.getKeys();
      }

      if (isBuildModeComplexType) {
        ComplexType complexType =
            complexTypeView.searchEdmComplexType(propertyView.getJPAAttribute().getJavaType().getName());
        normalizeComplexKey(complexType, propertyRefList);
      } else {
        PropertyRef propertyRef = new PropertyRef();
        propertyRef.setName(propertyView.getEdmSimpleProperty().getName());
        Facets facets = (Facets) propertyView.getEdmSimpleProperty().getFacets();
        if (facets == null) {
          propertyView.getEdmSimpleProperty().setFacets(new Facets().setNullable(false));
        } else {
          facets.setNullable(false);
        }
        propertyRefList.add(propertyRef);
      }

    }

    /**
     * Normalize complex key.
     *
     * @param complexType the complex type
     * @param propertyRefList the property ref list
     */
    // TODO think how to stop the recursion if A includes B and B includes A!!!!!!
    public void normalizeComplexKey(final ComplexType complexType, final List<PropertyRef> propertyRefList) {
      for (Property property : complexType.getProperties()) {
        try {

          SimpleProperty simpleProperty = (SimpleProperty) property;
          Facets facets = (Facets) simpleProperty.getFacets();
          if (facets == null) {
            simpleProperty.setFacets(new Facets().setNullable(false));
          } else {
            facets.setNullable(false);
          }
          PropertyRef propertyRef = new PropertyRef();
          propertyRef.setName(simpleProperty.getName());
          propertyRefList.add(propertyRef);

        } catch (ClassCastException e) {
          ComplexProperty complexProperty = (ComplexProperty) property;
          normalizeComplexKey(complexTypeView.searchEdmComplexType(complexProperty.getType()), propertyRefList);
        }

      }
    }
  }
}
