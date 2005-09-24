<%@ page language="java" import="pfe.migration.client.network.ClientEjb" %>
	<HTML>
    <HEAD>
        <TITLE>Wandux</TITLE>
        <link type="text/css" rel="stylesheet" href="style.css">
    </HEAD>
    <BODY>
        <TABLE>
	<tr>
		<td align=left>
			<img src="img/wandux.gif">
		</td>
		<td align=left>
			&nbsp;&nbsp;<b>Wandux Administration Page</b>
		</td>
	</tr>
	<tr>
		<td colspan=2 valign=top>
			<center>
	<br><br><br>
	Trying to connect to Wandux Server on :<br><br>
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
		<DIV class="alert">Cannot etablish connection.<br><br></DIV>
		Retry connectint with the same adresse or change it below :<br>
		<FORM action="connect.jsp" method="post">
		<INPUT type="text" name="ipadress" size=16>
		<INPUT type="image" src="img/go.gif">
		</FORM>
		<%
		} else {
		%>
		<DIV class="succes">Successfully Connected to <%out.print(ipServer[0]);%>.<br><br></DIV><br><br>
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