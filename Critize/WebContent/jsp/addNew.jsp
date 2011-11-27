<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css" rel="stylesheet" href="../stylesheets/main.css" />
<title>Insert title here</title>
</head>
<body>


<h1 class="siteHead">Add Products</h1>
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
path += "/controller?requestType=ACTION&module=AddProduct";
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

					<td><input type="text" name="Company" id="Company" /></td>
					<td><input type="text" name="Product" id="Product" /></td>
					<td><input type="text" name="ModelNo" id="ModelNo" /></td>
					<td><input type="text" name="Description" id="Description" />
					</td>
					<td><input type="text" name="QTY" id="QTY" /></td>
				</tr>
				
				<tr>
					<td><input type="submit" name="submit" value="Save"></td>
					<td><input type="button" name="addNew" value="Add"></td>
				<tr>
				
				
			</table>
			
			
			
			<!--  <input type="hidden" id = "module" name = "module" value="AddProduct">-->
		</form>
	</div>
	
	<script>
	$(function() {
		var availableTags = [
			"ActionScript",
			"AppleScript",
			"Asp",
			"BASIC",
			"C",
			"C++",
			"Clojure",
			"COBOL",
			"ColdFusion",
			"Erlang",
			"Fortran",
			"Groovy",
			"Haskell",
			"Java",
			"JavaScript",
			"Lisp",
			"Perl",
			"PHP",
			"Python",
			"Ruby",
			"Scala",
			"Scheme"
		];
		$( "#tags" ).autocomplete({
			source: availableTags
		});
	});
	</script>


	
<div class="demo">

<div class="ui-widget">
	<label for="tags">Tags: </label>
	<input id="tags" />
</div>

</div><!-- End demo -->



<div class="demo-description">
<p>The Autocomplete widgets provides suggestions while you type into the field. Here the suggestions are tags for programming languages, give "ja" (for Java or JavaScript) a try.</p>
<p>The datasource is a simple JavaScript array, provided to the widget using the source-option.</p>
</div><!-- End demo-description -->
	
</body>
</html>