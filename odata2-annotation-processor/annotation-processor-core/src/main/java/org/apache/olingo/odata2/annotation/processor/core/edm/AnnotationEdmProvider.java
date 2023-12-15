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
package org.apache.olingo.odata2.annotation.processor.core.edm;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.olingo.odata2.annotation.processor.core.util.AnnotationHelper;
import org.apache.olingo.odata2.annotation.processor.core.util.ClassHelper;
import org.apache.olingo.odata2.api.annotation.edm.EdmComplexType;
import org.apache.olingo.odata2.api.annotation.edm.EdmConcurrencyControl;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.annotation.edm.EdmEntityType;
import org.apache.olingo.odata2.api.annotation.edm.EdmFacets;
import org.apache.olingo.odata2.api.annotation.edm.EdmKey;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceContent;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceMimeType;
import org.apache.olingo.odata2.api.annotation.edm.EdmMediaResourceSource;
import org.apache.olingo.odata2.api.annotation.edm.EdmNavigationProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmProperty;
import org.apache.olingo.odata2.api.annotation.edm.EdmType;
import org.apache.olingo.odata2.api.edm.EdmConcurrencyMode;
import org.apache.olingo.odata2.api.edm.EdmMultiplicity;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.AnnotationAttribute;
import org.apache.olingo.odata2.api.edm.provider.AnnotationElement;
import org.apache.olingo.odata2.api.edm.provider.Association;
import org.apache.olingo.odata2.api.edm.provider.AssociationEnd;
import org.apache.olingo.odata2.api.edm.provider.AssociationSet;
import org.apache.olingo.odata2.api.edm.provider.AssociationSetEnd;
import org.apache.olingo.odata2.api.edm.provider.ComplexProperty;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Facets;
import org.apache.olingo.odata2.api.edm.provider.FunctionImport;
import org.apache.olingo.odata2.api.edm.provider.Key;
import org.apache.olingo.odata2.api.edm.provider.Mapping;
import org.apache.olingo.odata2.api.edm.provider.NavigationProperty;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.PropertyRef;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.api.edm.provider.Using;
import org.apache.olingo.odata2.api.exception.ODataException;

// TODO: Auto-generated Javadoc
/**
 * Provider for the entity data model used in the reference scenario.
 */
public class AnnotationEdmProvider extends EdmProvider {

  /** The Constant ANNOTATION_HELPER. */
  private static final AnnotationHelper ANNOTATION_HELPER = new AnnotationHelper();

  /** The annotated classes. */
  private final List<Class<?>> annotatedClasses;
  
  /** The name 2 container. */
  private final Map<String, EntityContainer> name2Container = new HashMap<String, EntityContainer>();
  
  /** The container name 2 container builder. */
  private final Map<String, ContainerBuilder> containerName2ContainerBuilder = new HashMap<String, ContainerBuilder>();
  
  /** The namespace 2 schema. */
  private final Map<String, Schema> namespace2Schema = new HashMap<String, Schema>();
  
  /** The default container. */
  private EntityContainer defaultContainer;

  /**
   * Instantiates a new annotation edm provider.
   *
   * @param annotatedClasses the annotated classes
   * @throws ODataException the o data exception
   */
  public AnnotationEdmProvider(final Collection<Class<?>> annotatedClasses) throws ODataException {

    this.annotatedClasses = new ArrayList<Class<?>>(annotatedClasses.size());
    for (Class<?> aClass : annotatedClasses) {
      if (ANNOTATION_HELPER.isEdmAnnotated(aClass)) {
        this.annotatedClasses.add(aClass);
      }
    }

    init();
  }

  /**
   * Instantiates a new annotation edm provider.
   *
   * @param packageToScan the package to scan
   * @throws ODataException the o data exception
   */
  public AnnotationEdmProvider(final String packageToScan) throws ODataException {
    annotatedClasses = ClassHelper.loadClasses(packageToScan, new ClassHelper.ClassValidator() {
      @Override
      public boolean isClassValid(final Class<?> c) {
        return ANNOTATION_HELPER.isEdmAnnotated(c);
      }
    });

    init();
  }

  /**
   * Inits the.
   *
   * @throws ODataException the o data exception
   */
  private void init() throws ODataException {
    for (Class<?> aClass : annotatedClasses) {
      updateSchema(aClass);
      handleEntityContainer(aClass);
    }

    finish();
  }

  /**
   * Gets the association.
   *
   * @param edmFQName the edm FQ name
   * @return the association
   * @throws ODataException the o data exception
   */
  @Override
  public Association getAssociation(final FullQualifiedName edmFQName) throws ODataException {
    Schema schema = namespace2Schema.get(edmFQName.getNamespace());
    if (schema != null) {
      List<Association> associations = schema.getAssociations();
      for (Association association : associations) {
        if (association.getName().equals(edmFQName.getName())) {
          return association;
        }
      }
    }
    return null;
  }

  /**
   * Gets the association set.
   *
   * @param entityContainer the entity container
   * @param association the association
   * @param sourceEntitySetName the source entity set name
   * @param sourceEntitySetRole the source entity set role
   * @return the association set
   * @throws ODataException the o data exception
   */
  @Override
  public AssociationSet getAssociationSet(final String entityContainer, final FullQualifiedName association,
      final String sourceEntitySetName, final String sourceEntitySetRole) throws ODataException {
    EntityContainer container = name2Container.get(entityContainer);
    if (container != null) {
      List<AssociationSet> associations = container.getAssociationSets();
      for (AssociationSet associationSet : associations) {
        if (associationSet.getAssociation().equals(association)) {
          final AssociationSetEnd endOne = associationSet.getEnd1();
          if (endOne.getRole().equals(sourceEntitySetRole)
              && endOne.getEntitySet().equals(sourceEntitySetName)) {
            return associationSet;
          }
          final AssociationSetEnd endTwo = associationSet.getEnd2();
          if (endTwo.getRole().equals(sourceEntitySetRole)
              && endTwo.getEntitySet().equals(sourceEntitySetName)) {
            return associationSet;
          }
        }
      }
    }
    return null;
  }

  /**
   * Gets the complex type.
   *
   * @param edmFQName the edm FQ name
   * @return the complex type
   * @throws ODataException the o data exception
   */
  @Override
  public ComplexType getComplexType(final FullQualifiedName edmFQName) throws ODataException {
    Schema schema = namespace2Schema.get(edmFQName.getNamespace());
    if (schema != null) {
      List<ComplexType> complexTypes = schema.getComplexTypes();
      for (ComplexType complexType : complexTypes) {
        if (complexType.getName().equals(edmFQName.getName())) {
          return complexType;
        }
      }
    }
    return null;
  }

  /**
   * Gets the entity container info.
   *
   * @param name the name
   * @return the entity container info
   * @throws ODataException the o data exception
   */
  @Override
  public EntityContainerInfo getEntityContainerInfo(final String name) throws ODataException {
    EntityContainer container = name2Container.get(name);
    if (container == null) {
      // use default container (if set)
      container = defaultContainer;
    }
    if (container != null) {
      EntityContainerInfo info = new EntityContainerInfo();
      info.setName(container.getName());
      info.setDefaultEntityContainer(container.isDefaultEntityContainer());
      info.setExtendz(container.getExtendz());
      info.setAnnotationAttributes(container.getAnnotationAttributes());
      info.setAnnotationElements(container.getAnnotationElements());

      return info;
    }

    return null;
  }

  /**
   * Gets the entity set.
   *
   * @param entityContainer the entity container
   * @param name the name
   * @return the entity set
   * @throws ODataException the o data exception
   */
  @Override
  public EntitySet getEntitySet(final String entityContainer, final String name) throws ODataException {
    EntityContainer container = name2Container.get(entityContainer);
    if (container != null) {
      List<EntitySet> entitySets = container.getEntitySets();
      for (EntitySet entitySet : entitySets) {
        if (entitySet.getName().equals(name)) {
          return entitySet;
        }
      }
    }

    return null;
  }

  /**
   * Gets the entity type.
   *
   * @param edmFQName the edm FQ name
   * @return the entity type
   * @throws ODataException the o data exception
   */
  @Override
  public EntityType getEntityType(final FullQualifiedName edmFQName) throws ODataException {
    Schema schema = namespace2Schema.get(edmFQName.getNamespace());
    if (schema != null) {
      List<EntityType> complexTypes = schema.getEntityTypes();
      for (EntityType complexType : complexTypes) {
        if (complexType.getName().equals(edmFQName.getName())) {
          return complexType;
        }
      }
    }
    return null;
  }

  /**
   * Gets the function import.
   *
   * @param entityContainer the entity container
   * @param name the name
   * @return the function import
   * @throws ODataException the o data exception
   */
  @Override
  public FunctionImport getFunctionImport(final String entityContainer, final String name) throws ODataException {
    EntityContainer container = name2Container.get(entityContainer);
    if (container != null) {
      List<FunctionImport> functionImports = container.getFunctionImports();
      for (FunctionImport functionImport : functionImports) {
        if (functionImport.getName().equals(name)) {
          return functionImport;
        }
      }
    }
    return null;
  }

  /**
   * Gets the schemas.
   *
   * @return the schemas
   * @throws ODataException the o data exception
   */
  @Override
  public List<Schema> getSchemas() throws ODataException {
    return new ArrayList<Schema>(namespace2Schema.values());
  }

  //
  //
  /** The namespace 2 schema builder. */
  //
  private Map<String, SchemaBuilder> namespace2SchemaBuilder = new HashMap<String, SchemaBuilder>();

  /**
   * Update schema.
   *
   * @param aClass the a class
   */
  private void updateSchema(final Class<?> aClass) {
    EdmEntityType et = aClass.getAnnotation(EdmEntityType.class);
    if (et != null) {
      updateSchema(aClass, et);
    }
    EdmComplexType ect = aClass.getAnnotation(EdmComplexType.class);
    if (ect != null) {
      updateSchema(aClass, ect);
    }
  }

  /**
   * Update schema.
   *
   * @param aClass the a class
   * @param et the et
   */
  private void updateSchema(final Class<?> aClass, final EdmEntityType et) {
    SchemaBuilder b = getSchemaBuilder(et.namespace(), aClass);
    TypeBuilder typeBuilder = TypeBuilder.init(et, aClass);
    b.addEntityType(typeBuilder.buildEntityType());
    b.addAssociations(typeBuilder.buildAssociations());
  }

  /**
   * Gets the schema builder.
   *
   * @param namespace the namespace
   * @param aClass the a class
   * @return the schema builder
   */
  private SchemaBuilder getSchemaBuilder(final String namespace, final Class<?> aClass) {
    String usedNamespace = namespace;
    if (usedNamespace.isEmpty()) {
      usedNamespace = ANNOTATION_HELPER.getCanonicalNamespace(aClass);
    }
    SchemaBuilder builder = namespace2SchemaBuilder.get(usedNamespace);
    if (builder == null) {
      builder = SchemaBuilder.init(usedNamespace);
      namespace2SchemaBuilder.put(usedNamespace, builder);
    }
    return builder;
  }

  /**
   * Update schema.
   *
   * @param aClass the a class
   * @param et the et
   */
  private void updateSchema(final Class<?> aClass, final EdmComplexType et) {
    SchemaBuilder b = getSchemaBuilder(et.namespace(), aClass);
    TypeBuilder typeBuilder = TypeBuilder.init(et, aClass);
    b.addComplexType(typeBuilder.buildComplexType());
  }

  /**
   * Handle entity container.
   *
   * @param aClass the a class
   */
  private void handleEntityContainer(final Class<?> aClass) {
    EdmEntityType entityType = aClass.getAnnotation(EdmEntityType.class);
    if (entityType != null) {
      FullQualifiedName typeName = createFqnForEntityType(aClass);
      String containerName = ANNOTATION_HELPER.extractContainerName(aClass);
      ContainerBuilder builder = containerName2ContainerBuilder.get(containerName);
      if (builder == null) {
        builder = ContainerBuilder.init(typeName.getNamespace(), containerName);
        containerName2ContainerBuilder.put(containerName, builder);
      }
      EdmEntitySet entitySet = aClass.getAnnotation(EdmEntitySet.class);
      if (entitySet != null) {
        builder.addEntitySet(createEntitySet(typeName, aClass));
      }
    }
  }

  /**
   * Creates the entity set.
   *
   * @param typeName the type name
   * @param entitySetClass the entity set class
   * @return the entity set
   */
  private EntitySet createEntitySet(final FullQualifiedName typeName, final Class<?> entitySetClass) {
    String entitySetName = ANNOTATION_HELPER.extractEntitySetName(entitySetClass);
    return new EntitySet().setName(entitySetName).setEntityType(typeName);
  }

  /**
   * Creates the fqn for entity type.
   *
   * @param annotatedClass the annotated class
   * @return the full qualified name
   */
  private FullQualifiedName createFqnForEntityType(final Class<?> annotatedClass) {
    return ANNOTATION_HELPER.extractEntityTypeFqn(annotatedClass);
  }

  /**
   * Finish.
   *
   * @throws ODataException the o data exception
   */
  private void finish() throws ODataException {
    //
    Collection<ContainerBuilder> containers = containerName2ContainerBuilder.values();
    for (ContainerBuilder containerBuilder : containers) {
      SchemaBuilder schemaBuilder = namespace2SchemaBuilder.get(containerBuilder.getNamespace());
      containerBuilder.addAssociationSets(schemaBuilder.name2Associations.values());
      final EntityContainer container = containerBuilder.build();
      schemaBuilder.addEntityContainer(container);
      name2Container.put(container.getName(), container);
      if (container.isDefaultEntityContainer()) {
        defaultContainer = container;
      }
    }
    //
    Collection<SchemaBuilder> schemaBuilders = namespace2SchemaBuilder.values();
    for (SchemaBuilder schemaBuilder : schemaBuilders) {
      final Schema schema = schemaBuilder.build();
      namespace2Schema.put(schema.getNamespace(), schema);
    }
  }

  //
  //
  /**
   * The Class TypeBuilder.
   */
  //
  static class TypeBuilder {

    /** The namespace. */
    final private String namespace;
    
    /** The name. */
    final private String name;
    
    /** The is abstract. */
    private boolean isAbstract = false;
    
    /** The is media resource. */
    private boolean isMediaResource = false;
    
    /** The media resource mime type key. */
    private String mediaResourceMimeTypeKey;
    
    /** The media resource source key. */
    private String mediaResourceSourceKey;
    
    /** The base entity type. */
    private FullQualifiedName baseEntityType = null;
    
    /** The key properties. */
    private final List<PropertyRef> keyProperties = new ArrayList<PropertyRef>();
    
    /** The properties. */
    private final List<Property> properties = new ArrayList<Property>();
    
    /** The nav properties. */
    private final List<NavigationProperty> navProperties = new ArrayList<NavigationProperty>();
    
    /** The associations. */
    private final List<Association> associations = new ArrayList<Association>();

    /**
     * Instantiates a new type builder.
     *
     * @param fqn the fqn
     */
    public TypeBuilder(final FullQualifiedName fqn) {
      namespace = fqn.getNamespace();
      name = fqn.getName();
    }

    /**
     * Inits the.
     *
     * @param entity the entity
     * @param aClass the a class
     * @return the type builder
     */
    public static TypeBuilder init(final EdmEntityType entity, final Class<?> aClass) {
      return new TypeBuilder(ANNOTATION_HELPER.extractEntityTypeFqn(entity, aClass)).withClass(aClass);
    }

    /**
     * Inits the.
     *
     * @param entity the entity
     * @param aClass the a class
     * @return the type builder
     */
    public static TypeBuilder init(final EdmComplexType entity, final Class<?> aClass) {
      return new TypeBuilder(ANNOTATION_HELPER.extractComplexTypeFqn(entity, aClass)).withClass(aClass);
    }

    /**
     * With class.
     *
     * @param aClass the a class
     * @return the type builder
     */
    private TypeBuilder withClass(final Class<?> aClass) {
      baseEntityType = createBaseEntityFqn(aClass);

      if (Modifier.isAbstract(aClass.getModifiers())) {
        isAbstract = true;
      }

      Field[] fields = aClass.getDeclaredFields();
      for (Field field : fields) {
        EdmProperty ep = field.getAnnotation(EdmProperty.class);
        if (ep != null) {
          Property property = createProperty(ep, field);
          properties.add(property);
          EdmKey eti = field.getAnnotation(EdmKey.class);
          if (eti != null) {
            keyProperties.add(createKeyProperty(ep, field));
          }
          EdmMediaResourceMimeType emrmt = field.getAnnotation(EdmMediaResourceMimeType.class);
          if (emrmt !=null) {
            mediaResourceMimeTypeKey = property.getName();
          }
          EdmMediaResourceSource emrs = field.getAnnotation(EdmMediaResourceSource.class);
          if (emrs !=null) {
            mediaResourceSourceKey =  property.getName();
          }
        }
        EdmNavigationProperty enp = field.getAnnotation(EdmNavigationProperty.class);
        if (enp != null) {
          Class<?> fromClass = field.getDeclaringClass();
          Class<?> toClass = ClassHelper.getFieldType(field);
          AnnotationHelper.AnnotatedNavInfo info = ANNOTATION_HELPER.getCommonNavigationInfo(fromClass, toClass);

          final NavigationProperty navProperty = createNavigationProperty(namespace, field, info);
          navProperties.add(navProperty);
          Association association = createAssociation(info);
          associations.add(association);
        }
        EdmMediaResourceContent emrc = field.getAnnotation(EdmMediaResourceContent.class);
        if (emrc != null) {
          isMediaResource = true;
        }
      }

      return this;
    }

    /**
     * Adds the property.
     *
     * @param property the property
     * @return the type builder
     */
    public TypeBuilder addProperty(final PropertyRef property) {
      keyProperties.add(property);
      return this;
    }

    /**
     * Adds the property.
     *
     * @param property the property
     * @return the type builder
     */
    public TypeBuilder addProperty(final Property property) {
      properties.add(property);
      return this;
    }

    /**
     * Adds the navigation property.
     *
     * @param property the property
     * @return the type builder
     */
    public TypeBuilder addNavigationProperty(final NavigationProperty property) {
      navProperties.add(property);
      return this;
    }

    /**
     * Sets the abstract.
     *
     * @param isAbstract the is abstract
     * @return the type builder
     */
    public TypeBuilder setAbstract(final boolean isAbstract) {
      this.isAbstract = isAbstract;
      return this;
    }

    /**
     * Builds the complex type.
     *
     * @return the complex type
     */
    public ComplexType buildComplexType() {
      ComplexType complexType = new ComplexType();
      if (baseEntityType != null) {
        complexType.setBaseType(baseEntityType);
      }
      return complexType.setName(name).setProperties(properties);
    }

    /**
     * Builds the entity type.
     *
     * @return the entity type
     */
    public EntityType buildEntityType() {
      EntityType entityType = new EntityType();
      if (baseEntityType != null) {
        entityType.setBaseType(baseEntityType);
      }
      if (!keyProperties.isEmpty()) {
        entityType.setKey(new Key().setKeys(keyProperties));
      }
      if (!navProperties.isEmpty()) {
        entityType.setNavigationProperties(navProperties);
      }
      return entityType.setName(name)
          .setAbstract(isAbstract)
          .setHasStream(isMediaResource)
          .setProperties(properties)
          .setMapping(new Mapping().setMediaResourceMimeTypeKey(mediaResourceMimeTypeKey)
                                    .setMediaResourceSourceKey(mediaResourceSourceKey));
    }

    /**
     * Builds the associations.
     *
     * @return the collection
     */
    public Collection<Association> buildAssociations() {
      return Collections.unmodifiableCollection(associations);
    }

    /**
     * Creates the key property.
     *
     * @param et the et
     * @param field the field
     * @return the property ref
     */
    private PropertyRef createKeyProperty(final EdmProperty et, final Field field) {
      PropertyRef keyProperty = new PropertyRef();
      String entityName = et.name();
      if (entityName.isEmpty()) {
        entityName = getCanonicalName(field);
      }
      return keyProperty.setName(entityName);
    }

    /**
     * Creates the property.
     *
     * @param ep the ep
     * @param field the field
     * @return the property
     */
    private Property createProperty(final EdmProperty ep, final Field field) {
      if (isAnnotatedEntity(field.getType())) {
        return createComplexProperty(ep, field);
      } else {
        return createSimpleProperty(ep, field);
      }
    }

    /**
     * Creates the simple property.
     *
     * @param ep the ep
     * @param field the field
     * @return the property
     */
    private Property createSimpleProperty(final EdmProperty ep, final Field field) {
      SimpleProperty sp = new SimpleProperty();
      String entityName = ANNOTATION_HELPER.getPropertyName(field);
      sp.setName(entityName);
      //
      EdmType type = ep.type();
      if (type == EdmType.NULL) {
        type = getEdmType(field.getType());
      }
      sp.setType(ANNOTATION_HELPER.mapTypeKind(type));
      sp.setFacets(createFacets(ep.facets(), field.getAnnotation(EdmConcurrencyControl.class)));
      return sp;
    }

    /**
     * Creates the facets.
     *
     * @param facets the facets
     * @param concurrencyControl the concurrency control
     * @return the facets
     */
    private Facets createFacets(final EdmFacets facets, final EdmConcurrencyControl concurrencyControl) {
      Facets resultFacets = new Facets().setNullable(facets.nullable());
      if(facets.maxLength() > -1) {
        resultFacets.setMaxLength(facets.maxLength());
      }
      if(facets.precision() > -1) {
        resultFacets.setPrecision(facets.precision());
      }
      if(facets.scale() > -1) {
        resultFacets.setScale(facets.scale());
      }
      if (concurrencyControl != null) {
        resultFacets.setConcurrencyMode(EdmConcurrencyMode.Fixed);
      }
      return resultFacets;
    }

    /**
     * Creates the complex property.
     *
     * @param ep the ep
     * @param field the field
     * @return the property
     */
    private Property createComplexProperty(EdmProperty ep, final Field field) {
      ComplexProperty cp = new ComplexProperty();
      // settings from property
      String entityName = ANNOTATION_HELPER.getPropertyName(field);
      cp.setName(entityName);

      // settings from related complex entity
      FullQualifiedName fqn = ANNOTATION_HELPER.extractComplexTypeFqn(field.getType());
      cp.setType(fqn);

      cp.setFacets(createFacets(ep.facets(), field.getAnnotation(EdmConcurrencyControl.class)));
      
      return cp;
    }

    /**
     * Creates the navigation property.
     *
     * @param namespace the namespace
     * @param field the field
     * @param navInfo the nav info
     * @return the navigation property
     */
    private NavigationProperty createNavigationProperty(final String namespace, Field field,
                                                        AnnotationHelper.AnnotatedNavInfo navInfo) {
      NavigationProperty navProp = new NavigationProperty();
      navProp.setName(ANNOTATION_HELPER.getPropertyName(field));
      String fromRole = navInfo.getFromRoleName();
      navProp.setFromRole(fromRole);
      navProp.setToRole(navInfo.getToRoleName());

      String relationshipName = navInfo.getRelationshipName();
      navProp.setRelationship(new FullQualifiedName(namespace, relationshipName));

      return navProp;
    }

    /**
     * Gets the edm type.
     *
     * @param type the type
     * @return the edm type
     */
    private EdmType getEdmType(final Class<?> type) {
      if (type == String.class) {
        return EdmType.STRING;
      } else if (type == boolean.class || type == Boolean.class) {
        return EdmType.BOOLEAN;
      } else if (type == byte.class || type == Byte.class) {
        return EdmType.SBYTE;
      } else if (type == short.class || type == Short.class) {
        return EdmType.INT16;
      } else if (type == int.class || type == Integer.class) {
        return EdmType.INT32;
      } else if (type == long.class || type == Long.class) {
        return EdmType.INT64;
      } else if (type == double.class || type == Double.class) {
        return EdmType.DOUBLE;
      } else if (type == float.class || type == Float.class) {
        return EdmType.SINGLE;
      } else if (type == BigInteger.class || type == BigDecimal.class) {
        return EdmType.DECIMAL;
      } else if (type == Byte[].class || type == byte[].class) {
        return EdmType.BINARY;
      } else if (type == Date.class) {
        return EdmType.DATE_TIME;
      } else if (type == Calendar.class) {
        return EdmType.DATE_TIME_OFFSET;
      } else if (type == UUID.class) {
        return EdmType.GUID;
      } else {
        throw new UnsupportedOperationException("Not yet supported type '" + type + "'.");
      }
    }

    /**
     * Check for base entity class.
     *
     * @param aClass the a class
     * @return the class
     */
    private Class<?> checkForBaseEntityClass(final Class<?> aClass) {
      Class<?> superClass = aClass.getSuperclass();
      if (superClass == Object.class) {
        return null;
      } else {
        EdmEntityType edmEntity = superClass.getAnnotation(EdmEntityType.class);
        if (edmEntity == null) {
          return checkForBaseEntityClass(superClass);
        } else {
          return superClass;
        }
      }
    }

    /**
     * Creates the base entity fqn.
     *
     * @param aClass the a class
     * @return the full qualified name
     */
    private FullQualifiedName createBaseEntityFqn(final Class<?> aClass) {
      Class<?> baseEntityClass = checkForBaseEntityClass(aClass);
      if (baseEntityClass == null) {
        return null;
      }
      return ANNOTATION_HELPER.extractEntityTypeFqn(baseEntityClass);
    }

    /**
     * Creates the association.
     *
     * @param info the info
     * @return the association
     */
    private Association createAssociation(final AnnotationHelper.AnnotatedNavInfo info) {
      Association association = new Association();

      AssociationEnd fromEnd = new AssociationEnd();
      fromEnd.setRole(info.getFromRoleName());
      fromEnd.setType(new FullQualifiedName(namespace, info.getFromTypeName()));
      fromEnd.setMultiplicity(info.getFromMultiplicity());
      association.setEnd1(fromEnd);

      AssociationEnd toEnd = new AssociationEnd();
      toEnd.setRole(info.getToRoleName());
      toEnd.setType(new FullQualifiedName(namespace, info.getToTypeName()));
      toEnd.setMultiplicity(info.getToMultiplicity());
      association.setEnd2(toEnd);

      String associationName = info.getRelationshipName();
      association.setName(associationName);
      return association;
    }

    /**
     * Gets the canonical name.
     *
     * @param field the field
     * @return the canonical name
     */
    private String getCanonicalName(final Field field) {
      return ANNOTATION_HELPER.getCanonicalName(field);
    }

    /**
     * Checks if is annotated entity.
     *
     * @param clazz the clazz
     * @return true, if is annotated entity
     */
    private boolean isAnnotatedEntity(final Class<?> clazz) {
      return ANNOTATION_HELPER.isEdmTypeAnnotated(clazz);
    }
  }

  /**
   * The Class SchemaBuilder.
   */
  static class SchemaBuilder {

    /** The namespace. */
    final private String namespace;
    
    /** The usings. */
    private final List<Using> usings = new ArrayList<Using>();
    
    /** The entity types. */
    private final List<EntityType> entityTypes = new ArrayList<EntityType>();
    
    /** The complex types. */
    private final List<ComplexType> complexTypes = new ArrayList<ComplexType>();
    
    /** The name 2 associations. */
    private final Map<String, Association> name2Associations = new HashMap<String, Association>();
    
    /** The entity containers. */
    private final List<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
    
    /** The annotation attributes. */
    private final List<AnnotationAttribute> annotationAttributes = new ArrayList<AnnotationAttribute>();
    
    /** The annotation elements. */
    private final List<AnnotationElement> annotationElements = new ArrayList<AnnotationElement>();

    /**
     * Instantiates a new schema builder.
     *
     * @param namespace the namespace
     */
    private SchemaBuilder(final String namespace) {
      this.namespace = namespace;
    }

    /**
     * Inits the.
     *
     * @param namespace the namespace
     * @return the schema builder
     */
    public static SchemaBuilder init(final String namespace) {
      return new SchemaBuilder(namespace);
    }

    /**
     * Adds the entity type.
     *
     * @param type the type
     * @return the schema builder
     */
    public SchemaBuilder addEntityType(final EntityType type) {
      entityTypes.add(type);
      return this;
    }

    /**
     * Adds the entity container.
     *
     * @param container the container
     * @return the schema builder
     */
    public SchemaBuilder addEntityContainer(final EntityContainer container) {
      entityContainers.add(container);
      return this;
    }

    /**
     * Adds the complex type.
     *
     * @param createEntityType the create entity type
     * @return the schema builder
     */
    public SchemaBuilder addComplexType(final ComplexType createEntityType) {
      complexTypes.add(createEntityType);
      return this;
    }

    /**
     * Adds the associations.
     *
     * @param associations the associations
     */
    public void addAssociations(final Collection<Association> associations) {
      for (Association association : associations) {
        final String relationshipName = association.getName();
        if (name2Associations.containsKey(relationshipName)) {
          association = mergeAssociations(name2Associations.get(relationshipName), association);
        }
        name2Associations.put(relationshipName, association);
      }
    }

    /**
     * Merge associations.
     *
     * @param associationOne the association one
     * @param associationTwo the association two
     * @return the association
     */
    private Association mergeAssociations(final Association associationOne, final Association associationTwo) {
      AssociationEnd oneEnd1 = associationOne.getEnd1();
      AssociationEnd oneEnd2 = associationOne.getEnd2();
      AssociationEnd twoEnd1 = associationTwo.getEnd1();
      AssociationEnd twoEnd2 = associationTwo.getEnd2();
      AssociationEnd[] oneEnds = new AssociationEnd[] { oneEnd1, oneEnd2 };

      for (AssociationEnd associationEnd : oneEnds) {
        if (associationEnd.getRole().equals(twoEnd1.getRole())) {
          if (twoEnd1.getMultiplicity() == EdmMultiplicity.MANY) {
            associationEnd.setMultiplicity(EdmMultiplicity.MANY);
          }
        } else if (associationEnd.getRole().equals(twoEnd2.getRole())) {
          if (twoEnd2.getMultiplicity() == EdmMultiplicity.MANY) {
            associationEnd.setMultiplicity(EdmMultiplicity.MANY);
          }
        }
      }

      return associationOne;
    }

    /**
     * Builds the.
     *
     * @return the schema
     */
    public Schema build() {
      Schema s = new Schema();
      s.setUsings(usings);
      s.setEntityTypes(entityTypes);
      s.setComplexTypes(complexTypes);
      s.setAssociations(new ArrayList<Association>(name2Associations.values()));
      s.setEntityContainers(entityContainers);
      s.setAnnotationAttributes(annotationAttributes);
      s.setAnnotationElements(annotationElements);
      s.setNamespace(namespace);
      return s;
    }
  }

  /**
   * The Class ContainerBuilder.
   */
  private static class ContainerBuilder {

    /** The name. */
    final private String name;
    
    /** The namespace. */
    final private String namespace;
    
    /** The default container. */
    private boolean defaultContainer = true;
    
    /** The entity sets. */
    private final List<EntitySet> entitySets = new ArrayList<EntitySet>();
    
    /** The association sets. */
    private final List<AssociationSet> associationSets = new ArrayList<AssociationSet>();
    
    /** The function imports. */
    private final List<FunctionImport> functionImports = new ArrayList<FunctionImport>();

    /**
     * Instantiates a new container builder.
     *
     * @param namespace the namespace
     * @param containerName the container name
     */
    private ContainerBuilder(final String namespace, final String containerName) {
      this.namespace = namespace;
      name = containerName;
    }

    /**
     * Gets the namespace.
     *
     * @return the namespace
     */
    public String getNamespace() {
      return namespace;
    }

    /**
     * Inits the.
     *
     * @param namespace the namespace
     * @param containerName the container name
     * @return the container builder
     */
    public static ContainerBuilder init(final String namespace, final String containerName) {
      return new ContainerBuilder(namespace, containerName);
    }

    /**
     * Adds the entity set.
     *
     * @param entitySet the entity set
     * @return the container builder
     */
    public ContainerBuilder addEntitySet(final EntitySet entitySet) {
      entitySets.add(entitySet);
      return this;
    }

    /**
     * Adds the association sets.
     *
     * @param associations the associations
     * @throws ODataException the o data exception
     */
    public void addAssociationSets(final Collection<Association> associations) throws ODataException {
      for (Association association : associations) {
        AssociationSet as = new AssociationSet();
        as.setName(association.getName());
        FullQualifiedName asAssociationFqn = new FullQualifiedName(namespace, association.getName());
        as.setAssociation(asAssociationFqn);

        AssociationSetEnd asEnd1 = new AssociationSetEnd();
        asEnd1.setEntitySet(getEntitySetName(association.getEnd1()));
        asEnd1.setRole(association.getEnd1().getRole());
        as.setEnd1(asEnd1);

        AssociationSetEnd asEnd2 = new AssociationSetEnd();
        asEnd2.setEntitySet(getEntitySetName(association.getEnd2()));
        asEnd2.setRole(association.getEnd2().getRole());
        as.setEnd2(asEnd2);

        associationSets.add(as);
      }
    }

    /**
     * Builds the.
     *
     * @return the entity container
     */
    public EntityContainer build() {
      EntityContainer ec = new EntityContainer();
      ec.setName(name);
      ec.setDefaultEntityContainer(defaultContainer);
      ec.setEntitySets(entitySets);
      ec.setAssociationSets(associationSets);
      ec.setFunctionImports(functionImports);
      return ec;
    }

    /**
     * Gets the entity set name.
     *
     * @param end the end
     * @return the entity set name
     * @throws ODataException the o data exception
     */
    private String getEntitySetName(final AssociationEnd end) throws ODataException {
      for (EntitySet entitySet : entitySets) {
        if (entitySet.getEntityType().equals(end.getType())) {
          return entitySet.getName();
        }
      }
      throw new ODataException("No entity set found for " + end.getType());
    }
  }
}
