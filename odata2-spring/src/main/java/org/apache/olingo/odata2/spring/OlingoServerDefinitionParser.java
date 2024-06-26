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
package org.apache.olingo.odata2.spring;

import org.apache.cxf.jaxrs.spring.JAXRSServerFactoryBeanDefinitionParser;
import org.apache.olingo.odata2.core.rest.ODataExceptionMapperImpl;
import org.apache.olingo.odata2.core.rest.app.ODataApplication;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class OlingoServerDefinitionParser.
 */
public class OlingoServerDefinitionParser extends JAXRSServerFactoryBeanDefinitionParser {

  /** The Constant OLINGO_ROOT_LOCATOR. */
  protected static final String OLINGO_ROOT_LOCATOR = "OlingoRootLocator";
  
  /** The Constant OLINGO_ODATA_PROVIDER. */
  protected static final String OLINGO_ODATA_PROVIDER = "OlingoODataProvider";
  
  /** The Constant OLINGO_ODATA_EXCEPTION_HANDLER. */
  protected static final String OLINGO_ODATA_EXCEPTION_HANDLER = "OlingoODataExceptionHandler";
  
  /** The Constant SERVICE_FACTORY. */
  protected static final String SERVICE_FACTORY = "serviceFactory";
  
  /** The Constant SERVICE_BEANS. */
  protected static final String SERVICE_BEANS = "serviceBeans";
  
  /** The Constant ID. */
  protected static final String ID = "id";
  
  /** The Constant FACTORY. */
  protected static final String FACTORY = "factory";
  
  /** The Constant PATH_SPLIT. */
  protected static final String PATH_SPLIT = "pathSplit";

  /**
   * Instantiates a new olingo server definition parser.
   */
  public OlingoServerDefinitionParser() {
    super();
    setBeanClass(SpringJAXRSServerFactoryBean.class);
  }

  /**
   * Map attribute.
   *
   * @param bean the bean
   * @param e the e
   * @param name the name
   * @param val the val
   */
  @Override
  protected void mapAttribute(BeanDefinitionBuilder bean, Element e, String name, String val) {
    if (ID.equals(name) || "address".equals(name)) {
      mapToProperty(bean, name, val);
    }
  }

  /**
   * Do parse.
   *
   * @param element the element
   * @param parserContext the parser context
   * @param bean the bean
   */
  @Override
  protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder bean) {
    super.doParse(element, parserContext, bean);

    if (!parserContext.getRegistry().containsBeanDefinition(OLINGO_ODATA_EXCEPTION_HANDLER)) {
      AbstractBeanDefinition definition =
          BeanDefinitionBuilder.genericBeanDefinition(ODataExceptionMapperImpl.class).getBeanDefinition();
      definition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
      BeanDefinitionHolder holder = new BeanDefinitionHolder(definition, OLINGO_ODATA_EXCEPTION_HANDLER, new String[0]);
      registerBeanDefinition(holder, parserContext.getRegistry());
    }

    if (!parserContext.getRegistry().containsBeanDefinition(OLINGO_ODATA_PROVIDER)) {
      AbstractBeanDefinition definition =
          BeanDefinitionBuilder.genericBeanDefinition(ODataApplication.MyProvider.class).getBeanDefinition();
      definition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
      BeanDefinitionHolder holder = new BeanDefinitionHolder(definition, OLINGO_ODATA_PROVIDER, new String[0]);
      registerBeanDefinition(holder, parserContext.getRegistry());
    }

    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(OlingoRootLocator.class);
    builder.setScope(BeanDefinition.SCOPE_PROTOTYPE);
    builder.addPropertyReference(SERVICE_FACTORY, element.getAttribute(FACTORY));
    if (element.hasAttribute(PATH_SPLIT)) {
      builder.addPropertyValue(PATH_SPLIT, element.getAttribute(PATH_SPLIT));
    }
    AbstractBeanDefinition definition = builder.getBeanDefinition();
    BeanDefinitionHolder holder = new BeanDefinitionHolder(definition,
        OLINGO_ROOT_LOCATOR + "-" + element.getAttribute(ID) + "-" + element.getAttribute(FACTORY));
    registerBeanDefinition(holder, parserContext.getRegistry());

    ManagedList<BeanDefinition> services = new ManagedList<BeanDefinition>(3);
    services.add(definition);
    services.add(parserContext.getRegistry().getBeanDefinition(OLINGO_ODATA_EXCEPTION_HANDLER));
    services.add(parserContext.getRegistry().getBeanDefinition(OLINGO_ODATA_PROVIDER));
    bean.addPropertyValue(SERVICE_BEANS, services);
  }

}
