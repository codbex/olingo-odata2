<!--
  Licensed to the Apache Software Foundation (ASF) under one
  or more contributor license agreements.  See the NOTICE file
  distributed with this work for additional information
  regarding copyright ownership.  The ASF licenses this file
  to you under the Apache License, Version 2.0 (the
  "License"); you may not use this file except in compliance
  with the License.  You may obtain a copy of the License at
  
     http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
-->
<%@page
	import="org.apache.olingo.odata2.jpa.processor.ref.factory.JPAEntityManagerFactory"%>
<%@page import="java.util.List"%>
<%@page import="jakarta.persistence.EntityManager"%>
<%@page import="jakarta.persistence.EntityManagerFactory"%>
<%@page import="jakarta.persistence.Persistence"%>
<%@page import="jakarta.persistence.Query"%>
<%@page
	import="org.apache.olingo.odata2.jpa.processor.ref.web.JPAReferenceServiceFactory"%>
<%@page
	import="org.apache.olingo.odata2.jpa.processor.ref.util.DataGenerator"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Apache Olingo - OData2 JPA Processor Library</title>
<style type="text/css">
body {
	font-family: Arial, sans-serif;
	font-size: 13px;
	line-height: 18px;
	color: blue;
	background-color: #ffffff;
}

a {
	color: blue;
	text-decoration: none;
}

a:focus {
	outline: thin dotted #4076cb;
	outline-offset: -1px;
}

a:hover,a:active {
	outline: 0;
}

a:hover {
	color: #404a7e;
	text-decoration: underline;
}

h1,h2,h3,h4,h5,h6 {
	margin: 9px 0;
	font-family: inherit;
	font-weight: bold;
	line-height: 1;
	color: blue;
}

h1 {
	font-size: 36px;
	line-height: 40px;
}

h2 {
	font-size: 30px;
	line-height: 40px;
}

h3 {
	font-size: 24px;
	line-height: 40px;
}

h4 {
	font-size: 18px;
	line-height: 20px;
}

h5 {
	font-size: 14px;
	line-height: 20px;
}

h6 {
	font-size: 12px;
	line-height: 20px;
}

.logo {
	float: right;
}

ul {
	padding: 0;
	margin: 0 0 9px 25px;
}

ul ul {
	margin-bottom: 0;
}

li {
	line-height: 18px;
}

hr {
	margin: 18px 0;
	border: 0;
	border-top: 1px solid #cccccc;
	border-bottom: 1px solid #ffffff;
}

table {
	border-collapse: collapse;
	border-spacing: 10px;
	border: 0px;
}

th,td {
	border: 0px solid;
	padding: 20px;
}

.code {
	font-family: "Courier New", monospace;
	font-size: 13px;
	line-height: 18px;
}
</style>
</head>
<body>
	<h1>Apache Olingo - OData2 JPA Processor Library</h1>
	<hr />
	<table width=100% cellspacing="1" cellpadding="1">
		<tr>
			<td width=50%><h2>Reference Scenario</h2></td>
			<td width="50%">
				<table cellspacing="1" cellpadding="1">
					<tr align="center">
						<td align="right" width="80%"><font color="green"><small>
									<%
									  EntityManagerFactory entityManagerFactory =
									      JPAEntityManagerFactory.getEntityManagerFactory("salesorderprocessing");
									  EntityManager entityManager = entityManagerFactory
									      .createEntityManager();
									  System.out.println("Data Gen " + entityManager.hashCode());
									  DataGenerator dataGenerator = new DataGenerator(entityManager);

									  Number result1 = null;
									  Number existingCount = null;

									  String msg = null;
									  if (request.getParameter("button") != null) {
									    if (request.getParameter("button").equalsIgnoreCase("Generate")) {
									      Query q = entityManager
									          .createQuery("SELECT COUNT(x) FROM SalesOrderHeader x");
									      existingCount = (Number) q.getSingleResult();
									      if (existingCount.intValue() < 10) { // Generate only if no data!
									        dataGenerator.generate();
									        result1 = (Number) q.getSingleResult();
									        System.out
									            .println("Data not existing earlier.... Generated number of Items - "
									                + result1);
									        msg = result1 + " items generated. ";

									      } else {
									        System.err
									            .println("Data already existing.... No Item generated by Data Generator !!");
									        msg = "Data exists. No Item generated !!";
									      }
									    } else { //Clean

									      // Check if data already exists
									      Query q = entityManager
									          .createQuery("SELECT COUNT(x) FROM SalesOrderHeader x");
									      Number result = (Number) q.getSingleResult();
									      if (result.intValue() > 0) { // Generate only if no data!
									        dataGenerator.clean();
									        msg = "Data Cleaned. " + result + " items cleaned.";
									      } else {
									        msg = " Nothing to clean!!";
									      }
									    }
									%> <%=(msg)%>
							</small> </font></td>
						<%
						  }
						%>
						<td width="10%">
							<form name="form1" method="get">
								<input type="hidden" name="button" value="Generate"> <input
									type="submit" value="Generate Data" width="100%">
							</form>
						</td>
						<td width="10%">

							<form name="form2" method="get">
								<input type="hidden" name="button" value="Clean"> <input
									type="submit" value="   Clean Data  " width="100%">
							</form>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width=100%>
		<tr>
			<td valign="top">
				<h3>Service Document and Metadata</h3>
				<ul>
					<li><a href="SalesOrderProcessing.svc?_wadl" target="_blank">wadl</a></li>
					<li><a href="SalesOrderProcessing.svc/" target="_blank">service
							document</a></li>
					<li><a href="SalesOrderProcessing.svc/$metadata"
						target="_blank">metadata</a></li>
				</ul>
				<h3>EntitySets</h3>
				<ul>
					<li><a href="SalesOrderProcessing.svc/SalesOrders"
						target="_blank">SalesOrders</a></li>
					<li><a href="SalesOrderProcessing.svc/SalesOrderLineItems"
						target="_blank">SalesOrderLineItems</a></li>
					<li><a href="SalesOrderProcessing.svc/Materials"
						target="_blank">Materials</a></li>
					<li><a href="SalesOrderProcessing.svc/Stores" target="_blank">Stores</a></li>
				</ul>
				<h3>Entities</h3>
				<ul>
					<li><a href="SalesOrderProcessing.svc/SalesOrders(2L)"
						target="_blank">SalesOrders(2L)</a></li>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrderLineItems(LiId=10L,SoId=2L)"
						target="_blank">SalesOrderLineItems(LiId=10L,SoId=2L)</a></li>
					<li><a href="SalesOrderProcessing.svc/Materials(111L)"
						target="_blank">Materials(111L)</a></li>
					<li><a href="SalesOrderProcessing.svc/Stores(131L)"
						target="_blank">Stores(131L)</a></li>

				</ul>
				<h3>Navigation</h3>
				<ul>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrders(2L)/SalesOrderLineItemDetails"
						target="_blank">SalesOrders(2L)/SalesOrderLineItemDetails</a></li>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrders(2L)/SalesOrderLineItemDetails"
						target="_blank">SalesOrders(2L)/SalesOrderLineItemDetails</a></li>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrderLineItems(LiId=10L,SoId=2L)/MaterialDetails"
						target="_blank">SalesOrderLineItems(LiId=10L,SoId=2L)/MaterialDetails</a></li>
					<li><a
						href="SalesOrderProcessing.svc/Materials(112L)/StoreDetails"
						target="_blank">Materials(112L)/StoreDetails</a></li>

				</ul>
				<h3>$expand</h3>
				<ul>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrders?$expand=SalesOrderLineItemDetails"
						target="_blank">SalesOrders?$expand=SalesOrderLineItemDetails</a></li>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrders(2L)?$expand=SalesOrderLineItemDetails"
						target="_blank">SalesOrders(2L)?$expand=SalesOrderLineItemDetails</a></li>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrders?$expand=SalesOrderLineItemDetails/MaterialDetails,SalesOrderLineItemDetails/MaterialDetails/StoreDetails,NotesDetails"
						target="_blank">SalesOrders?$expand=SalesOrderLineItemDetails/MaterialDetails,SalesOrderLineItemDetails/MaterialDetails/StoreDetails,NotesDetails</a></li>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrders(2L)?$expand=SalesOrderLineItemDetails/MaterialDetails,SalesOrderLineItemDetails/MaterialDetails/StoreDetails,NotesDetails"
						target="_blank">SalesOrders(2L)?$expand=SalesOrderLineItemDetails/MaterialDetails,SalesOrderLineItemDetails/MaterialDetails/StoreDetails,NotesDetails</a></li>

				</ul>
				<h3>Function Imports</h3>
				<ul>
					<li><a
						href="SalesOrderProcessing.svc/FindAllSalesOrders?DeliveryStatusCode='01'"
						target="_blank">SalesOrderProcessing.svc/FindAllSalesOrders?DeliveryStatusCode='01'</a></li>
					<li><a href="SalesOrderProcessing.svc/orderValue?SoId=2L"
						target="_blank">SalesOrderProcessing.svc/orderValue?SoId=2L</a></li>
				</ul>
				<h3>Paging</h3>
				<ul>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrders?$top=1&$inlinecount=allpages"
						target="_blank">SalesOrderProcessing.svc/SalesOrders?$top=1&amp;$inlinecount=allpages"</a></li>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrders?$top=1&$inlinecount=allpages&$skiptoken=5"
						target="_blank">SalesOrderProcessing.svc/SalesOrders?$top=1&amp;$inlinecount=allpages&amp;$skiptoken=5</a></li>
					<li><a
						href="SalesOrderProcessing.svc/SalesOrders?$top=1&$skip=4&$inlinecount=allpages&$skiptoken=5"
						target="_blank">SalesOrderProcessing.svc/SalesOrders?$top=1&amp;$skip=4&amp;$inlinecount=allpages&amp;$skiptoken=5</a></li>
				</ul>
			</td>
			<td valign="top">&nbsp;</td>
			<td valign="bottom">
				<div class="code">
					<%
					  String version = "gen/version.html";
					%>
					<%
					  try {
					%>
					<jsp:include page='<%=version%>' />
					<%
					  } catch (Exception e) {
					%>
					<p>IDE Build</p>
					<%
					  }
					%>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>

