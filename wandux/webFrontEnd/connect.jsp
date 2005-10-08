<%@ page language="java" import="pfe.migration.client.network.ClientEjb" %>
	<HTML>
    <HEAD>
        <TITLE>Wandux</TITLE>
        <link type="text/css" rel="stylesheet" href="style.css">
    </HEAD>
    <BODY>
        <TABLE>
	<tr>
		<td align=left colspan=2>
			<img src="img/barrewandux.bmp">
		</td>
	</tr>
	<tr>
		<td colspan=2 valign=top>
			<center>
	<br><br>
	<b>Trying to connect to Wandux Server on :</b><br><br>
	<%
		String[] ipServer;
		ipServer = (String[])request.getParameterValues("ipadress");
		
		if (ipServer != null)
		{
			out.print(ipServer[0]);
		}
	%>
	<BR><BR><BR>

	<%
		ClientEjb ce = (ClientEjb)application.getAttribute("ClientEjb");
		if (ce == null)
		{
			ce = new ClientEjb(ipServer[0]);
			application.setAttribute("ClientEjb", ce);
			ce.EjbConnect();
		}
		if (ce.IsConnected() == false) {
		%>
		<DIV class="alert"><b>Cannot etablish connection.<b><br><br></DIV>
		Retry connectint with the same adresse or change it below :<br>
		<FORM action="connect.jsp" method="post">
		<INPUT type="text" name="ipadress" size=16>
		<INPUT type="image" src="img/go.gif">
		</FORM>
		<%
		} else {
		%>
		<DIV class="succes"><b>Successfully Connected to <%out.print(ipServer[0]);%></b><br><br></DIV><br><br>
		<a href="admin.jsp"><img src="img/go.gif" border=0></a>
		<%
		}
		%>


	</center>
		</td>
	</tr>
	</TABLE>
	
    </BODY>
	</HTML>