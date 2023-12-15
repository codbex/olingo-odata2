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
package org.apache.olingo.odata2.jpa.processor.core.mock.model;

import org.apache.olingo.odata2.jpa.processor.core.mock.model.JPAEdmMockData.EntityType.EntityTypeA;

// TODO: Auto-generated Javadoc
/**
 * The Interface JPAEdmMockData.
 */
public interface JPAEdmMockData {
  
  /**
   * The Interface ComplexType.
   */
  /*
   * Edm Complex Type Mock Data
   */
  public interface ComplexType {

    /**
     * The Interface ComplexTypeA.
     */
    public interface ComplexTypeA {
      
      /** The Constant name. */
      public static final String name = "ComplexTypeA";
      
      /** The Constant clazz. */
      public static final Class<ComplexTypeA> clazz = ComplexTypeA.class;

      /**
       * The Interface Property.
       */
      public interface Property {
        
        /** The Constant PROPERTY_A. */
        public static final String PROPERTY_A = "A";
        
        /** The Constant PROPERTY_B. */
        public static final String PROPERTY_B = "B";
        
        /** The Constant PROPERTY_C. */
        public static final String PROPERTY_C = "C";
      }

    }

    /**
     * The Interface ComplexTypeB.
     */
    public interface ComplexTypeB {
      
      /** The Constant name. */
      public static final String name = "ComplexTypeB";

      /**
       * The Interface Property.
       */
      public interface Property {
        
        /** The Constant PROPERTY_D. */
        public static final String PROPERTY_D = "D";
        
        /** The Constant PROPERTY_E. */
        public static final String PROPERTY_E = "E";
      }

    }
  }

  /**
   * The Interface EntityType.
   */
  public interface EntityType {
    
    /**
     * The Interface EntityTypeA.
     */
    public interface EntityTypeA {
      
      /** The Constant name. */
      public static final String name = "SalesOrderHeader";
      
      /** The Constant entityClazz. */
      public static final Class<EntityTypeA> entityClazz = EntityTypeA.class;

      /**
       * The Interface Property.
       */
      public interface Property {
        
        /** The Constant PROPERTY_A. */
        public static final String PROPERTY_A = SimpleType.SimpleTypeA.NAME;

      }

    }
  }

  /**
   * The Interface SimpleType.
   */
  public interface SimpleType {
    
    /**
     * The Interface SimpleTypeA.
     */
    public interface SimpleTypeA {
      
      /** The Constant NAME. */
      public static final String NAME = "SOID";
      
      /** The Constant clazz. */
      public static final Class<String> clazz = String.class;
      
      /** The Constant declaringClazz. */
      public static final Class<EntityTypeA> declaringClazz = EntityType.EntityTypeA.class;
    }
  }
}
