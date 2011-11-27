<%@page import="java.util.HashMap"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.critize.dax.Iconnect"%>
<%@page import="com.critize.dax.DaoFactory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String cntx = request.getContextPath();
	String cssPath = cntx + "/stylesheets/Underground.css";
	String jQueryPath = cntx + "/js/jquery-1.6.2.min.js";
	String dataTablePath = cntx + "/js/jquery.dataTables.min.js";
	String jQueryWidget = cntx + "/js/jquery-ui-1.8.16.custom.min.js";
	String jQuerycssPath = cntx + "/stylesheets/jquery-ui-1.8.16.custom.css";
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<%=cssPath%>" type="text/css" />
<script src="<%=jQueryPath %>" type="text/javascript"></script>
<script src="<%=dataTablePath %>" type="text/javascript"></script>
<script src="<%=jQueryWidget %>" type="text/javascript"></script>


<title>Insert title here</title>

<script type="text/javascript">
$(document).ready(function() {

	$('#myStockTable').dataTable();

} );

</script>

</head>
<body>

<script>
	$(function() {
		var availableTags = [
			"Audio Plus",
			"Ahuja",
			"CAMCO",
			"Studiomaster",
			"Korg",
			"JBL",
			"Allen & Heath",
			"db Audio",
			"Schenizer",
			"EV",
			"Sure",
			"Groovy"
		];
		$( "#Company" ).autocomplete({
			source: availableTags
		});
	});
	</script>
<jsp:include page="header.jsp"></jsp:include>
<%
	DaoFactory factory = DaoFactory.getInstance();
	Iconnect iCon = factory.getDao("MySql");
	Connection conn = (Connection)iCon.getConnection();
	
	String sql = "SELECT * FROM currentstock" ;
	HashMap result = new HashMap<Integer, HashMap>();
	HashMap hashMap = new HashMap();
	
	result = (HashMap<Integer, HashMap>) iCon.executeQuery(conn, sql);
	
	
	String editProd = cntx + "/controller?requestType=ACTION&module=EditProduct";
	
	String Company,Products,ModelNo,QTY,Date,Id;
	
%>


	<table id = "myStockTable" class="display" >
		<thead>
			<th>Company</th>
			<th>Product</th>
			<th>Model No. </th>
			<th>Qty</th>
			<th>Date</th>
			<th>Action</th>
		</thead>
		<tbody>
			
			<%
			for(int rows = 1; rows <= result.size() ; rows++){
				 hashMap = (HashMap) result.get(rows);
				
				 Company = (String)hashMap.get("Company");
				 Products = (String)hashMap.get("Products");
				 ModelNo = (String)hashMap.get("ModelNo");
				 QTY = (String)hashMap.get("QTY");
				 Date = (String)hashMap.get("Date");
				 Id = (String)hashMap.get("id");
				
			%>
			
			<%if( rows%2 == 0){%>
			<tr>
			<%}else{ %>
			<tr>
			<%} %>
				<td><%=Company %></td>
				<td><%=Products %></td>
				<td><%=ModelNo %></td>
				<td><%=QTY %></td>
				<td><%=Date %></td>
				<td>
					<form name = "editProduct" method="post" action = "<%=editProd %>">
						<input type = "hidden" name = "Id" value = "<%=Id %>">
						<input type = "submit" name = "edit" value = "Edit" />
					</form>
				</td>
				</tr>
			<%} %>
		</tbody>
	</table>
	
	<br />
	
	<h1>Add New Product Details</h1>
	
	<div class="mainPost">
	<%
	String path = request.getContextPath();
	path += "/controller?requestType=ACTION&module=AddProduct";
	%>

		<form action=<%= path %> method="post" name = "myForm">

			
			<table>
				<tr>
					<td>Company:</td><td><input type="text" name="Company" id="Company" /></td>
				</tr>
				<tr>
					<td>Product:</td><td><input type="text" name="Product" id="Product" /></td>
				</tr>
				<tr>
					<td>Model No:</td><td><input type="text" name="ModelNo" id="ModelNo" /></td>
				</tr>
				<tr>
					<td>Description:</td><td><input type="text" name="Description" id="Description" /></td>
				</tr>
				<tr>
					<td>QTY:</td><td><input type="text" name="QTY" id="QTY" /></td>
				</tr>
				<tr>
					<td><input type="submit" name="submit" value="Add Product"></td>
				<tr>
			</table>
			
			
			
			
			<!--  <input type="hidden" id = "module" name = "module" value="AddProduct">-->
		</form>
	</div>
	
	
</div>
</div>
</body>
</html>