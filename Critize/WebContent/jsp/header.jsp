	
<%
      String cntx1 = request.getContextPath();
      String homePath = cntx1 + "/controller?link=dashboard";  
      String logoutPath=cntx1 + "/controller?link=logout";
%>
<div id="wrap">
<div id="header">									
				<span id="slogan"> We deliver excellence..</span><!-- tabs -->		
				<ul>
                 <li><a href="<%=homePath %>" ><span>Home</span></a></li>
				 <li><a href="index.html"><span>About Us</span></a></li>
				 <li><a href="<%=logoutPath %>"><span>Logout</span></a></li>
				 
                 </ul> 									
			</div>
	
    <div id="header-logo">			
			<div id="logo">Employee <span class="red">management</span><span class="black"> system</span></div>		
	</div>
