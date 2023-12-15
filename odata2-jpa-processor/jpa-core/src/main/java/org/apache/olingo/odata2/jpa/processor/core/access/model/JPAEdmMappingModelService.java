/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package org.apache.olingo.odata2.jpa.processor.core.access.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext;
import org.apache.olingo.odata2.jpa.processor.api.access.JPAEdmMappingModelAccess;
import org.apache.olingo.odata2.jpa.processor.api.exception.ODataJPAModelException;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.mapping.JPAAttributeMapType.JPAAttribute;
import org.apache.olingo.odata2.jpa.processor.api.model.mapping.JPAEdmMappingModel;
import org.apache.olingo.odata2.jpa.processor.api.model.mapping.JPAEmbeddableTypeMapType;
import org.apache.olingo.odata2.jpa.processor.api.model.mapping.JPAEntityTypeMapType;
import org.apache.olingo.odata2.jpa.processor.api.model.mapping.JPAPersistenceUnitMapType;
import org.apache.olingo.odata2.jpa.processor.api.model.mapping.JPARelationshipMapType.JPARelationship;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

// TODO: Auto-generated Javadoc
/**
 * The Class JPAEdmMappingModelService.
 */
public class JPAEdmMappingModelService implements JPAEdmMappingModelAccess {

    /** The mapping model exists. */
    boolean mappingModelExists = true;
    
    /** The mapping model. */
    private JPAEdmMappingModel mappingModel;
    
    /** The mapping model stream. */
    private InputStream mappingModelStream = null;
    
    /** The mapping model name. */
    private final String mappingModelName;

    /**
     * Instantiates a new JPA edm mapping model service.
     *
     * @param ctx the ctx
     */
    public JPAEdmMappingModelService(final ODataJPAContext ctx) {
        JPAEdmExtension ext = null;
        mappingModelName = ctx.getJPAEdmMappingModel();
        if (mappingModelName == null) {
            ext = ctx.getJPAEdmExtension();
            if (ext != null) {
                mappingModelStream = ext.getJPAEdmMappingModelStream();
            }
        }

        mappingModelExists = mappingModelName != null || mappingModelStream != null;
    }

    /**
     * Load mapping model.
     */
    @Override
    public void loadMappingModel() {
        InputStream is = null;
        if (mappingModelExists) {
            JAXBContext context;
            try {
                context = JAXBContext.newInstance(JPAEdmMappingModel.class);

                Unmarshaller unmarshaller = context.createUnmarshaller();
                is = loadMappingModelInputStream();
                if (is == null) {
                    mappingModelExists = false;
                    return;
                }

                mappingModel = (JPAEdmMappingModel) unmarshaller.unmarshal(is);

                if (mappingModel != null) {
                    mappingModelExists = true;
                }

            } catch (JAXBException e) {
                mappingModelExists = false;
                ODataJPAModelException ex = ODataJPAModelException.throwException(ODataJPAModelException.GENERAL, e);
                throw new IllegalStateException("Failed to load model", ex);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    /**
     * Checks if is mapping model exists.
     *
     * @return true, if is mapping model exists
     */
    @Override
    public boolean isMappingModelExists() {
        return mappingModelExists;
    }

    /**
     * Gets the JPA edm mapping model.
     *
     * @return the JPA edm mapping model
     */
    @Override
    public JPAEdmMappingModel getJPAEdmMappingModel() {
        return mappingModel;
    }

    /**
     * Map JPA persistence unit.
     *
     * @param persistenceUnitName the persistence unit name
     * @return the string
     */
    @Override
    public String mapJPAPersistenceUnit(final String persistenceUnitName) {

        JPAPersistenceUnitMapType persistenceUnit = mappingModel.getPersistenceUnit();
        if (persistenceUnit.getName()
                           .equals(persistenceUnitName)) {
            return persistenceUnit.getEDMSchemaNamespace();
        }

        return null;
    }

    /**
     * Map JPA entity type.
     *
     * @param jpaEntityTypeName the jpa entity type name
     * @return the string
     */
    @Override
    public String mapJPAEntityType(final String jpaEntityTypeName) {

        JPAEntityTypeMapType jpaEntityTypeMap = searchJPAEntityTypeMapType(jpaEntityTypeName);
        if (jpaEntityTypeMap != null) {
            return jpaEntityTypeMap.getEDMEntityType();
        }
        return null;
    }

    /**
     * Map JPA entity set.
     *
     * @param jpaEntityTypeName the jpa entity type name
     * @return the string
     */
    @Override
    public String mapJPAEntitySet(final String jpaEntityTypeName) {
        JPAEntityTypeMapType jpaEntityTypeMap = searchJPAEntityTypeMapType(jpaEntityTypeName);
        if (jpaEntityTypeMap != null) {
            return jpaEntityTypeMap.getEDMEntitySet();
        }
        return null;
    }

    /**
     * Map JPA attribute.
     *
     * @param jpaEntityTypeName the jpa entity type name
     * @param jpaAttributeName the jpa attribute name
     * @return the string
     */
    @Override
    public String mapJPAAttribute(final String jpaEntityTypeName, final String jpaAttributeName) {
        JPAEntityTypeMapType jpaEntityTypeMap = searchJPAEntityTypeMapType(jpaEntityTypeName);
        if (jpaEntityTypeMap != null && jpaEntityTypeMap.getJPAAttributes() != null) {
            // fixing attributes
            // removal issue
            // from mapping
            for (JPAAttribute jpaAttribute : jpaEntityTypeMap.getJPAAttributes()
                                                             .getJPAAttribute()) {
                if (jpaAttribute.getName()
                                .equals(jpaAttributeName)) {
                    return jpaAttribute.getValue();
                }
            }
        }

        return null;
    }

    /**
     * Map JPA relationship.
     *
     * @param jpaEntityTypeName the jpa entity type name
     * @param jpaRelationshipName the jpa relationship name
     * @return the string
     */
    @Override
    public String mapJPARelationship(final String jpaEntityTypeName, final String jpaRelationshipName) {
        JPAEntityTypeMapType jpaEntityTypeMap = searchJPAEntityTypeMapType(jpaEntityTypeName);
        if (jpaEntityTypeMap != null && jpaEntityTypeMap.getJPARelationships() != null) {
            for (JPARelationship jpaRealtionship : jpaEntityTypeMap.getJPARelationships()
                                                                   .getJPARelationship()) {
                if (jpaRealtionship.getName()
                                   .equals(jpaRelationshipName)) {
                    return jpaRealtionship.getValue();
                }
            }
        }

        return null;
    }

    /**
     * Map JPA embeddable type.
     *
     * @param jpaEmbeddableTypeName the jpa embeddable type name
     * @return the string
     */
    @Override
    public String mapJPAEmbeddableType(final String jpaEmbeddableTypeName) {
        JPAEmbeddableTypeMapType jpaEmbeddableType = searchJPAEmbeddableTypeMapType(jpaEmbeddableTypeName);
        if (jpaEmbeddableType != null) {
            return jpaEmbeddableType.getEDMComplexType();
        }
        return null;
    }

    /**
     * Map JPA embeddable type attribute.
     *
     * @param jpaEmbeddableTypeName the jpa embeddable type name
     * @param jpaAttributeName the jpa attribute name
     * @return the string
     */
    @Override
    public String mapJPAEmbeddableTypeAttribute(final String jpaEmbeddableTypeName, final String jpaAttributeName) {
        JPAEmbeddableTypeMapType jpaEmbeddableType = searchJPAEmbeddableTypeMapType(jpaEmbeddableTypeName);
        if (jpaEmbeddableType != null && jpaEmbeddableType.getJPAAttributes() != null) {
            for (JPAAttribute jpaAttribute : jpaEmbeddableType.getJPAAttributes()
                                                              .getJPAAttribute()) {
                if (jpaAttribute.getName()
                                .equals(jpaAttributeName)) {
                    return jpaAttribute.getValue();
                }
            }
        }
        return null;
    }

    /**
     * Search JPA entity type map type.
     *
     * @param jpaEntityTypeName the jpa entity type name
     * @return the JPA entity type map type
     */
    private JPAEntityTypeMapType searchJPAEntityTypeMapType(final String jpaEntityTypeName) {
        if (mappingModel != null) {
            List<JPAEntityTypeMapType> types = mappingModel.getPersistenceUnit()
                                                           .getJPAEntityTypes()
                                                           .getJPAEntityType();
            for (JPAEntityTypeMapType jpaEntityType : types) {
                if (jpaEntityType.getName()
                                 .equals(jpaEntityTypeName)) {
                    return jpaEntityType;
                }
            }
        }
        return null;
    }

    /**
     * Search JPA embeddable type map type.
     *
     * @param jpaEmbeddableTypeName the jpa embeddable type name
     * @return the JPA embeddable type map type
     */
    private JPAEmbeddableTypeMapType searchJPAEmbeddableTypeMapType(final String jpaEmbeddableTypeName) {
        if (null != mappingModel.getPersistenceUnit() && null != mappingModel.getPersistenceUnit()
                                                                             .getJPAEmbeddableTypes()) {
            for (JPAEmbeddableTypeMapType jpaEmbeddableType : mappingModel.getPersistenceUnit()
                                                                          .getJPAEmbeddableTypes()
                                                                          .getJPAEmbeddableType()) {
                if (jpaEmbeddableType.getName()
                                     .equals(jpaEmbeddableTypeName)) {
                    return jpaEmbeddableType;
                }
            }
        }
        return null;
    }

    /**
     * Load mapping model input stream.
     *
     * @return the input stream
     */
    protected InputStream loadMappingModelInputStream() {
        if (mappingModelStream != null) {
            return mappingModelStream;
        }
        ClassLoader classLoader = JPAEdmMappingModelService.class.getClassLoader();
        InputStream modelStream = classLoader.getResourceAsStream("../../" + mappingModelName);
        return (modelStream != null) ? modelStream : classLoader.getResourceAsStream(mappingModelName);
    }

    /**
     * Check exclusion of JPA entity type.
     *
     * @param jpaEntityTypeName the jpa entity type name
     * @return true, if successful
     */
    @Override
    public boolean checkExclusionOfJPAEntityType(final String jpaEntityTypeName) {
        JPAEntityTypeMapType type = searchJPAEntityTypeMapType(jpaEntityTypeName);
        if (type != null) {
            return type.isExclude();
        }
        return false;
    }

    /**
     * Check exclusion of JPA attribute type.
     *
     * @param jpaEntityTypeName the jpa entity type name
     * @param jpaAttributeName the jpa attribute name
     * @return true, if successful
     */
    @Override
    public boolean checkExclusionOfJPAAttributeType(final String jpaEntityTypeName, final String jpaAttributeName) {
        JPAEntityTypeMapType type = searchJPAEntityTypeMapType(jpaEntityTypeName);
        if (type != null && type.getJPAAttributes() != null) {
            for (JPAAttribute jpaAttribute : type.getJPAAttributes()
                                                 .getJPAAttribute()) {
                if (jpaAttribute.getName()
                                .equals(jpaAttributeName)) {
                    return jpaAttribute.isExclude();
                }
            }
        }
        return false;
    }

    /**
     * Check exclusion of JPA embeddable type.
     *
     * @param jpaEmbeddableTypeName the jpa embeddable type name
     * @return true, if successful
     */
    @Override
    public boolean checkExclusionOfJPAEmbeddableType(final String jpaEmbeddableTypeName) {
        JPAEmbeddableTypeMapType type = searchJPAEmbeddableTypeMapType(jpaEmbeddableTypeName);
        if (type != null) {
            return type.isExclude();
        }
        return false;
    }

    /**
     * Check exclusion of JPA embeddable attribute type.
     *
     * @param jpaEmbeddableTypeName the jpa embeddable type name
     * @param jpaAttributeName the jpa attribute name
     * @return true, if successful
     */
    @Override
    public boolean checkExclusionOfJPAEmbeddableAttributeType(final String jpaEmbeddableTypeName, final String jpaAttributeName) {
        JPAEmbeddableTypeMapType type = searchJPAEmbeddableTypeMapType(jpaEmbeddableTypeName);
        if (type != null && type.getJPAAttributes() != null) {
            for (JPAAttribute jpaAttribute : type.getJPAAttributes()
                                                 .getJPAAttribute()) {
                if (jpaAttribute.getName()
                                .equals(jpaAttributeName)) {
                    return jpaAttribute.isExclude();
                }
            }
        }
        return false;
    }
}
