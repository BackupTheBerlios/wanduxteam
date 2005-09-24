<%@ page language="Java" import="java.util.*" %>
<HTML>
    <HEAD>
        <TITLE>Welcome to Wandux Aministration page</TITLE>
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
			Please, enter here the IP Adress of the application server:<br><br>
			<center><FORM action="connect.jsp" method="post">
			<INPUT type="text" name="ipadress" size=16>
			<INPUT type="image" src="img/go.gif">
			</FORM></center>
		</td>
	</tr>
	</TABLE>
    </BODY>
</HTML>
