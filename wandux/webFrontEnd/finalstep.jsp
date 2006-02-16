<%@ page language="Java" import="java.util.*" %>
<%@ page import='pfe.migration.client.network.ClientEjb' %>
<%@ page import='pfe.migration.client.network.ComputerInformation' %>
<%@ page import='pfe.migration.server.ejb.WanduxEjb' %>
<%@ page import='pfe.migration.server.ejb.bdd.NetworkConfig' %>


<%				ClientEjb ce = (ClientEjb)application.getAttribute("ClientEjb");
				WanduxEjb bean = ce.getBean();
				List cl = bean.getIps();
				
				int i;
				String computer;
				String[] temp;
				
				temp = (String[])request.getParameterValues("computer");
				int main_interface;
				computer = temp[0];
				ComputerInformation ci = bean.getCi(computer);
				NetworkConfig netconf;

				//ICI INSERTION DE LA LISTE DES PROGRAMMES CHOISIS DANS LE CI
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
				
				//ON MET EN PREMIER L'INTERFACE PRINCIPALE EN PREMIER DANS LA LISTE
				temp = (String[])request.getParameterValues("main_interface");
				main_interface = Integer.parseInt(temp[0]);
				
				if (main_interface != 0)
				{
					netconf = ci.netconf[0];
					ci.netconf[0] = ci.netconf[main_interface];
					ci.netconf[main_interface] = netconf;
				}
				
				
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
