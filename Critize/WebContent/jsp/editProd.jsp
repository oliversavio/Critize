<%@page import="com.critize.model.Stock"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String cntx = request.getContextPath();
	String cssPath = cntx + "/stylesheets/Underground.css";
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="../stylesheets/main.css" />
<link rel="stylesheet" href="<%=cssPath%>" type="text/css" />
<title>Insert title here</title>
</head>
<body>

<jsp:include page="header.jsp"></jsp:include>
<h1 class="siteHead">Add Products</h1>
<%
	
	Stock myStockRec =  (Stock) session.getAttribute("myStockRec");
	String sCompany = myStockRec.getsCompany();
	String sModelNo = myStockRec.getsModelNo();
	String sProducts = myStockRec.getsPoducts();
	String sDesc = myStockRec.getsDescription();
	String sQTY = myStockRec.getsQTY();
	String Id = myStockRec.getId();
%>

<%
	
	String reply = (String) request.getAttribute("reply");
	if(reply!= null){
	%>
	<%="Record added successfully" %>	
<%
	}
%>
<div class="mainPost">
<%
String path = request.getContextPath();
path += "/controller?requestType=ACTION&module=UpdateProduct";
%>

		<form action=<%= path %> method="post" name = "myForm">

			<table>
				<thead>
					<tr>
						<td>Company:</td>
						<td>Product:</td>
						<td>Model No:</td>
						<td>Description:</td>
						<td>QTY:</td>
					</tr>
				</thead>
				<tr>

					<td><input type="text" name="Company" id="Company" value = "<%=sCompany %>"/></td>
					<td><input type="text" name="Product" id="Product" value = "<%=sProducts %>"/></td>
					<td><input type="text" name="ModelNo" id="ModelNo" value = "<%=sModelNo %>"/></td>
					<td><input type="text" name="Description" id="Description" value = "<%=sDesc %>"/>
					</td>
					<td>
						<input type="text" name="QTY" id="QTY" value = "<%=sQTY %>"/>
						<input type = "hidden" name = "Id" id = "Id" value = "<%=Id %>">
					</td>
				</tr>
				
				<tr>
					<td><input type="submit" name="submit" value="Save"></td>
					<td><input type="button" name="addNew" value="Add"></td>
				<tr>
				
				
			</table>
			
			
			
			<!--  <input type="hidden" id = "module" name = "module" value="AddProduct">-->
		</form>
	</div>
</div>
</body>
</html>