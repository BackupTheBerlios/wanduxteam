<%@ page language="java" %>
<%@ page import='java.util.*' %>
<%@ page import='pfe.migration.client.network.ClientEjb' %>
<%@ page import='pfe.migration.client.network.ComputerInformation' %>
<%@ page import='pfe.migration.server.ejb.WanduxEjb' %>

	<HTML>
    <HEAD>
        <TITLE>Wandux</TITLE>
        <link type="text/css" rel="stylesheet" href="style.css">
    </HEAD>
    <script language="JavaScript" src="js/tree/dhtmlXCommon.js"></script>
    <script language="JavaScript" src="js/tree/dhtmlXTree.js"></script>
    <script language="JavaScript">
 
<%				ClientEjb ce = (ClientEjb)application.getAttribute("ClientEjb");
				WanduxEjb bean = ce.getBean();
				List cl = bean.getIps();
				int i, x;
%>

    var tree;
    var last_div = "no";
    var temp;
    var MAX_STEP = 3;
    
    var steps = new Array();
<%
				for (i=0; i < cl.size(); i++)
				{
					out.print("steps[\"" + cl.get(i) +"\"] = 1;\n");
				}
%>
    
    
    function prev_step()
    {
	   	if (document.myform.machine.value == ' - Choose a Computer - ')
    	{
    		alert("Please, Choose a machine in the list");
    	}
    	else if (steps[document.myform.machine.value] == 1)
    	{
    		alert("There is no previous steps.");
    	}
    	else
    	{
    		steps[document.myform.machine.value] = steps[document.myform.machine.value] - 1;
    		selectMachine();
    	}
    }
    
    function next_step()
    {
	   	if (document.myform.machine.value == ' - Choose a Computer - ')
    	{
    		alert("Please, Choose a machine in the list");
    	}
    	else if (steps[document.myform.machine.value] == MAX_STEP)
    	{
    		alert("This is the last Step.");
    	}
    	else
    	{
    		steps[document.myform.machine.value] = steps[document.myform.machine.value] + 1;
    		selectMachine();
    	}
    }
    
    function init_trees()
	{
<%
				//for (i=0; i < cl.size(); i++)
				//{
				//	out.print("this['tree'+'" + cl.get(i) +"'] = 0;\n");
				//}
%>
    }
    
    function isdefined(variable)
	{
    	return (typeof(window[variable]) == "undefined")?  false: true;
	}
	
	function selectMachine()
	{
		if (last_div != "no")
			{
				temp = document.getElementById(last_div);
				temp.style.visibility = 'hidden';
				temp.style.width = 0;
				temp.style.height = 0;
			}
		if (document.myform.machine.value != ' - Choose a Computer - ')
		{
			temp = document.getElementById(' - Choose a Computer - ');
			temp.style.visibility = 'hidden';
			temp.style.width = 0;
			temp.style.height = 0;
			
			if (steps[document.myform.machine.value] == 1)
			{
				temp = document.getElementById('CI_'+document.myform.machine.value);
				last_div = 'CI_'+document.myform.machine.value;
			}
			else if (steps[document.myform.machine.value] == 2)
			{
				temp = document.getElementById('treeApplet'+document.myform.machine.value);
				last_div = 'treeApplet'+document.myform.machine.value;
			}
			if (steps[document.myform.machine.value] == 3)
			{
				temp = document.getElementById('ChoosePrograms'+document.myform.machine.value);
				last_div = 'ChoosePrograms'+document.myform.machine.value;
			}
			
			
			
			
			temp.style.visibility = 'visible';
			temp.style.width = 650;
			temp.style.height = 400;
		}
		else
		{
			temp = document.getElementById(' - Choose a Computer - ');
			temp.style.visibility = 'visible';
			temp.style.width = 650;
			temp.style.height = 400;
			last_div = ' - Choose a Computer - ';
		}
	}
		
	</script>
<%    //<BODY onLoad="init_trees();"> %>
	<BODY background="img/sidebar.bmp">
	<br>
	<TABLE border=0>
	<tr>
		<td align="left" valign="down" colspan="2" width="800" height="82">
			<img src="img/barrewandux.bmp" border=0>
		</td>
	</tr>
	<tr>
		<td valign="top" width="150" height="400">
			<FORM name="myform" action="test.jsp" method="post">
				<SELECT name="machine" onChange="selectMachine();">
				<OPTION value=" - Choose a Computer - "> - Choose a Computer - </OPTION>				
<%				for (i=0; i < cl.size(); i++)
				{
					out.print("<OPTION value=\"" + cl.get(i) +"\">" + cl.get(i) + "</OPTION>\n");
				}%>
				</SELECT>
		</td>
		<td width="650" height="400">


		
	

		<div id=" - Choose a Computer - " style=\"width:650;height:400;visibility:visible;position:absolute;top:120;left:160;\">
		<br><br><center><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Please, choose a computer to migrate in the list on the left of your screen.</b></center>
		</div>
<%
for (i=0; i < cl.size(); i++)
{
	// SECTION RECAPITULATIVE DU COMPUTERINFORMATION
	ComputerInformation ci = bean.getCi((String)cl.get(i));
	out.print("<div id=\"CI_" + cl.get(i) + "\" style=\"width:0;height:0;visibility:hidden;position:absolute;top:120;left:170;overflow:auto;\"><b><br><br><center>Informations extracted from " + cl.get(i) + ".<br></b>&nbsp;&nbsp;&nbsp;<font color=\"#1122FF\"> >> Please, check these settings and go on next Step</font></center><br><br><br>");
	out.print("Hostname : <b>" + cl.get(i) + "</b><br><br>");
	out.print("<TABLE width=300 border=0 cellspacing=2><TR><TD colspan=2><b>Network Interfaces :</b></TD></TR>");
	out.print("<TR><TD colspan=2><br><br></TD></TR>");
	for (x=0; x < (ci.netconf.length - 1); x++)
	{
		out.print("<TR><TD>Interface :</TD><TD><b>" + ci.netconf[x].getNetworkInterface() + "</b></TD></TR>");
		out.print("<TR><TD>Ip Adress :</TD><TD><b>" + ci.netconf[x].getNetworkIpAddress() + "</b></TD></TR>");
		out.print("<TR><TD>SubNetMask :</TD><TD><b>" + ci.netconf[x].getNetworkSubnetmask() + "</b></TD></TR>");
		out.print("<TR><TD>Mac Adress :</TD><TD><b>" + ci.netconf[x].getNetworkMacAdress() + "</b></TD></TR>");
		out.print("<TR><TD>Primary DNS :</TD><TD><b>" + ci.netconf[x].getNetworkDnsServer() + "</b></TD></TR>");	
		out.print("<TR><TD>Secondary DNS :</TD><TD><b>" + ci.netconf[x].getNetworkDnsServer2() + "</b></TD></TR>");
		out.print("<TR><TD>Gateway : </TD><TD><b>" + ci.netconf[x].getNetworkGateway() + "</b></TD></TR>");
		out.print("<TR><TD>DHCP Enabled :</TD><TD><b>");
		 if (ci.netconf[x].getNetworkDhcpEnabled().intValue() == 1)
		{
			out.print("yes</b></TD></TR>");
		}
		else
		{
			out.print("no</b></TD></TR>");
		}
		 
		out.print("<TR><TD>Status : </TD><TD><b>");
		if (ci.netconf[x].getNetworkStatus().intValue() == 1)
		{
			out.print("activated</b></TD></TR>");
		}
		else
		{
			out.print("disabled</b></TD></TR>");
		}
		out.print("<TR><TD colspan=2><br><br></TD></TR>");
	}
	out.print("</TABLE>\n");
	out.print("</div>\n");
	////////////////////////////////////////////////
	
	
	//deprecated
	//out.print("<div id=\"treeBox" + cl.get(i) + "\" style=\"width:0;height:0;visibility:hidden;position:absolute;top:120;left:160;overflow:auto;\"><b><br><br><center>This tree represent the whole Windows file system of " + cl.get(i) + ".</b></center><br><br>&nbsp;&nbsp;&nbsp;<font color=\"#1122FF\"> >>  Please, select the files and folders that you want to migrate on the new Linux system using this tree :</font><br><br></div>\n");

	// SECTION TREE - AFFICHAGTE DU FILESYSTEM
	out.print("<div id=\"treeApplet" + cl.get(i) + "\" style=\"width:0;height:0;visibility:hidden;position:absolute;top:120;left:160;overflow:auto;\"><b><br><br><center>This tree represent the whole Windows file system of " + cl.get(i) + ".</b></center><br><br>&nbsp;&nbsp;&nbsp;<font color=\"#1122FF\"> >>  Please, select the files and folders that you want to migrate on the new Linux system<br>&nbsp;&nbsp;&nbsp;&nbsp;using this tree :</font><br><br>\n");
%>
 <applet
   code="Horloge.class"
   width=400
   height=300
   hspace=0
   vspace=0
   align=top>
  <param name="background-color" value="#ffffff" />
 </applet>
<%
	out.print("</div>\n");
	///////////////////////////////////////////////
	
	
	//SECTION EQUIVALENCE DES PROGRAMMES
	out.print("<div id=\"ChoosePrograms" + cl.get(i) + "\" style=\"width:0;height:0;visibility:hidden;position:absolute;top:120;left:160;overflow:auto;\"><br><br><b><center> There is a list of Linux programs that are equivalent to the Windows Softwares installed on " + cl.get(i) + "</b></center><br><br>&nbsp;&nbsp;&nbsp;<font color=\"#1122FF\"> >>  Select the programs that will be installed on the new Linux System</font><br><br>");
	out.print("<TABLE width=500 border=1>");
	out.print("<TR><TD><b><center>Windows programs</b></center></TD><TD><b><center>Linux Equivalents</b></center></TD></TR>");
	List winprogs = ci.windowsProgram;
	for (i=0; i < winprogs.size(); i++)
	{
		out.print("<TR>");
		out.print("<TD>");
		out.print(winprogs.get(i) + "<br>");
		out.print("</TD>");
		out.print("<TD>");
		
		out.print("<INPUT type=radio name=prog" + i + " value=\"equivalent 1\">equivalent 1<br>");
		out.print("<INPUT type=radio name=prog" + i + " value=\"equivalent 2\">equivalent 2<br>");
		out.print("<INPUT type=radio name=prog" + i + " value=\"equivalent 3\">equivalent 3");
		out.print("</TD>");
		out.print("</TR>");
	}
	out.print("</TABLE>");
	//////////////////////////////////////
}
%>





		</td>
	</tr>
	<tr>
		<td align=center colspan=2 width="800" height="40">
	
			<br><a href="javascript:prev_step();"><img src="img/fleche_precedent.gif" border=0></a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:next_step();"><img src="img/fleche_suivant.gif" border=0></a>
		</td>
		
		<a href="javascript:document.myform.paths.value = this['tree'+document.myform.machine.value].getAllChecked();document.myform.submit();"></a>

	</tr>
	<INPUT name="paths" type="hidden">
	</FORM>
	</TABLE>
    </BODY>
	</HTML>