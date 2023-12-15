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

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.EdmEntitySet;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.core.ep.util.FormatXml;

// TODO: Auto-generated Javadoc
/**
 * The Class XmlLinkConsumer.
 */
public class XmlLinkConsumer {

  /**
   * Reads single link with format {@code <uri>http://somelink</uri>}.
   *
   * @param reader the reader
   * @param entitySet the entity set
   * @return link as string object
   * @throws EntityProviderException the entity provider exception
   */
  public String readLink(final XMLStreamReader reader, final EdmEntitySet entitySet) throws EntityProviderException {
    try {
      reader.next();
      return readLink(reader);
    } catch (final XMLStreamException e) {
      throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  }

  /**
   * Read link.
   *
   * @param reader the reader
   * @return the string
   * @throws XMLStreamException the XML stream exception
   */
  private String readLink(final XMLStreamReader reader) throws XMLStreamException {
    return readTag(reader, Edm.NAMESPACE_D_2007_08, FormatXml.D_URI);
  }

  /**
   * Read tag.
   *
   * @param reader the reader
   * @param namespaceURI the namespace URI
   * @param localName the local name
   * @return the string
   * @throws XMLStreamException the XML stream exception
   */
  private String readTag(final XMLStreamReader reader, final String namespaceURI, final String localName)
      throws XMLStreamException {
    reader.require(XMLStreamConstants.START_ELEMENT, namespaceURI, localName);
    final String result = reader.getElementText();
    reader.require(XMLStreamConstants.END_ELEMENT, namespaceURI, localName);

    return result;
  }

  /**
   * Reads multiple links with format
   * <pre> {@code
   * <links>
   *  <uri>http://somelink</uri>
   *  <uri>http://anotherLink</uri>
   *  <uri>http://somelink/yetAnotherLink</uri>
   * </links>
   * } </pre>.
   *
   * @param reader the reader
   * @param entitySet the entity set
   * @return list of string based links
   * @throws EntityProviderException the entity provider exception
   */
  public List<String> readLinks(final XMLStreamReader reader, final EdmEntitySet entitySet)
      throws EntityProviderException {
    try {
      List<String> links = new ArrayList<String>();
      reader.nextTag();
      reader.require(XMLStreamConstants.START_ELEMENT, Edm.NAMESPACE_D_2007_08, FormatXml.D_LINKS);
      reader.nextTag();
      while (!reader.isEndElement()) {
        if (reader.getLocalName().equals(FormatXml.M_COUNT)) {
          readTag(reader, Edm.NAMESPACE_M_2007_08, FormatXml.M_COUNT);
        } else {
          final String link = readLink(reader);
          links.add(link);
        }
        reader.nextTag();
      }

      reader.require(XMLStreamConstants.END_ELEMENT, Edm.NAMESPACE_D_2007_08, FormatXml.D_LINKS);
      return links;
    } catch (final XMLStreamException e) {
      throw new EntityProviderException(EntityProviderException.EXCEPTION_OCCURRED.addContent(e.getClass()
          .getSimpleName()), e);
    }
  }
}
