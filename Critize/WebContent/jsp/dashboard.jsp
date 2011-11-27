<%@ page language="java" contentType= "text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String cntx = request.getContextPath();
	String cssPath = cntx + "/stylesheets/Underground.css";
	String addNew = cntx + "/controller?requestType=VIEW&module=AddNew";
	String CurrentStock = cntx + "/controller?requestType=VIEW&module=CurrentStock";
%>
<link rel="stylesheet" href="<%=cssPath%>" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>EMS</title>
</head>
<body>



<jsp:include page="header.jsp"></jsp:include>



<h1 align="center" style="position: absolute; top: 100px; left: 490px">
Welcome Employee No:</h1>

<table width="75%" cellpadding="8" align="center"
	style="position: absolute; top: 200px; left: 175px">
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr height="20%">
		<td>&nbsp;</td>
		<td><a href="<%=addNew%>">
		<h2>Add New Product</h2>
		</a></td>
		<td>&nbsp;</td>
		<td><a href="http://www.google.co.in"><b>
		<h2>Enter Daily Sales</h2>
		</b></a></td>
		<td>&nbsp;</td>
	</tr>
	<tr height="20%">
		<td><a href="<%=CurrentStock %>"><b>
		<h2>Current Invetory</h2>
		</b></a></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	
		<td><a href="#">
				<h2>Sales Summary</h2>
			</a>
		</td>
	</tr>
	<tr height="20%">
		<td>&nbsp;</td>
		<td><a href="#"><b>
		<h2>
		</b>
		</h2>
		</a></td>
		<td>&nbsp;</td>
		<td><a href=""><b>
		 
		<h2> </h2> 
		</b>
		
		</a></td>
		<td>&nbsp;</td>
	</tr>
	<tr height="20%">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td><a href="#"><b>
		<h2></h2>
		</b></a></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		
	</tr>

</table>


</body>

</html>
