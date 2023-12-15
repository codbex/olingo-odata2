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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.olingo.odata2.api.edm.EdmAnnotationAttribute;
import org.apache.olingo.odata2.api.edm.EdmAnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;
import org.apache.olingo.odata2.testutil.fit.BaseTest;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class EdmAnnotationsImplProvTest.
 */
public class EdmAnnotationsImplProvTest extends BaseTest {

  /** The annotations provider. */
  private EdmAnnotationsImplProv annotationsProvider;
  
  /** The annotations provider with null eement and attributes. */
  private EdmAnnotationsImplProv annotationsProviderWithNullEementAndAttributes;

  /**
   * Gets the edm entity container impl.
   *
   * @return the edm entity container impl
   * @throws Exception the exception
   */
  @Before
  public void getEdmEntityContainerImpl() throws Exception {

    List<AnnotationAttribute> annotationAttributes = new ArrayList<AnnotationAttribute>();
    AnnotationAttribute attribute =
        new AnnotationAttribute().setName("attributeName").setNamespace("namespace").setPrefix("prefix")
            .setText("Text");
    annotationAttributes.add(attribute);

    List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();
    AnnotationElement element =
        new AnnotationElement().setName("elementName").setNamespace("namespace").setPrefix("prefix").setText("xmlData");
    annotationElements.add(element);

    annotationsProvider = new EdmAnnotationsImplProv(annotationAttributes, annotationElements);

    annotationsProviderWithNullEementAndAttributes = new EdmAnnotationsImplProv(null, null);
  }

  /**
   * Null elments and attributes.
   */
  @Test
  public void nullElmentsAndAttributes() {
    assertNull(annotationsProviderWithNullEementAndAttributes.getAnnotationAttributes());
    assertNull(annotationsProviderWithNullEementAndAttributes.getAnnotationElements());
    assertNull(annotationsProviderWithNullEementAndAttributes.getAnnotationAttribute("name", "namespace"));
    assertNull(annotationsProviderWithNullEementAndAttributes.getAnnotationElement("name", "namespace"));
  }

  /**
   * Test attributes.
   */
  @Test
  public void testAttributes() {
    List<? extends EdmAnnotationAttribute> annotations = annotationsProvider.getAnnotationAttributes();
    assertEquals(1, annotations.size());

    Iterator<? extends EdmAnnotationAttribute> iterator = annotations.iterator();
    while (iterator.hasNext()) {
      EdmAnnotationAttribute attribute = iterator.next();
      assertEquals("attributeName", attribute.getName());
      assertEquals("namespace", attribute.getNamespace());
      assertEquals("prefix", attribute.getPrefix());
      assertEquals("Text", attribute.getText());
    }
  }

  /**
   * Test attribute.
   */
  @Test
  public void testAttribute() {
    EdmAnnotationAttribute attribute = annotationsProvider.getAnnotationAttribute("attributeName", "namespace");
    assertEquals("attributeName", attribute.getName());
    assertEquals("namespace", attribute.getNamespace());
    assertEquals("prefix", attribute.getPrefix());
    assertEquals("Text", attribute.getText());
  }

  /**
   * Test attribute null.
   */
  @Test
  public void testAttributeNull() {
    EdmAnnotationAttribute attribute =
        annotationsProvider.getAnnotationAttribute("attributeNameWrong", "namespaceWrong");
    assertNull(attribute);
  }

  /**
   * Test elements.
   */
  @Test
  public void testElements() {
    List<? extends EdmAnnotationElement> annotations = annotationsProvider.getAnnotationElements();
    assertEquals(1, annotations.size());

    Iterator<? extends EdmAnnotationElement> iterator = annotations.iterator();
    while (iterator.hasNext()) {
      EdmAnnotationElement element = iterator.next();
      assertEquals("elementName", element.getName());
      assertEquals("namespace", element.getNamespace());
      assertEquals("prefix", element.getPrefix());
      assertEquals("xmlData", element.getText());
    }
  }

  /**
   * Test element.
   */
  @Test
  public void testElement() {
    EdmAnnotationElement element = annotationsProvider.getAnnotationElement("elementName", "namespace");
    assertEquals("elementName", element.getName());
    assertEquals("namespace", element.getNamespace());
    assertEquals("prefix", element.getPrefix());
    assertEquals("xmlData", element.getText());

    assertNull(element.getChildElements());
  }

  /**
   * Test element null.
   */
  @Test
  public void testElementNull() {
    EdmAnnotationElement element = annotationsProvider.getAnnotationElement("elementNameWrong", "namespaceWrong");
    assertNull(element);
  }

}
