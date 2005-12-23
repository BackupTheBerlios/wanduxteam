<%@ page language="Java" import="java.util.*" %>
<%@ page import='pfe.migration.client.network.ClientEjb' %>
<%@ page import='pfe.migration.client.network.ComputerInformation' %>
<%@ page import='pfe.migration.server.ejb.WanduxEjb' %>


<%				//TODO !!! PASSER EN PARAMETRE L'ID DU PC + les infos de progs et de fichiers
				//ex : prog1='OpenOffice' computer=2 etc...
				
				
				ClientEjb ce = (ClientEjb)application.getAttribute("ClientEjb");
				WanduxEjb bean = ce.getBean();
				List cl = bean.getIps();
				
				int i;
				String computer;
				String[] temp;
				
				temp = (String[])request.getParameterValues("computer");
				computer = temp[0];
				ComputerInformation ci = bean.getCi(computer);

				//ICI INSERER LES PROGRAMMES ET FICHIERS CHOISIS DANS LE CI
				List winprogs = ci.windowsProgram;
				ArrayList proglist = new ArrayList();
				for (i=0; i < winprogs.size(); i++)
				{
					if (winprogs.get(i) != null)
					{
						temp = (String[])request.getParameterValues("prog" + i);
						if (temp != null && temp[0] != null)
						{
							proglist.add(temp[0]);
						}
					}
				}
				///////////////////////////////////////////////////////////
				
				ci.migrable = 1;
				bean.putCi(ci);
%>

	<HTML>
    <HEAD>
        <TITLE>Wandux</TITLE>
        <link type="text/css" rel="stylesheet" href="style.css">
    </HEAD>
    <BODY background="img/sidebar.bmp">
        <TABLE>
	<tr>
		<td align=left colspan=2>
			<img src="img/barrewandux.bmp">
		</td>
	</tr>
	<tr>
		<td colspan=2 valign=top>
			<center>
	<br><br><br>
	<DIV class="succes"><b>You have successfully completed the pre-migration steps :</b></DIV><br><br>
	<b>The computer ' <% out.print(computer); %> ' is now Migrating to Linux</b><br><br>
	<a href="admin.jsp">click here to go back to the WANDUX Administration pages</a><br>

	</center>
		</td>
	</tr>
	</TABLE>
	
    </BODY>
	</HTML>
