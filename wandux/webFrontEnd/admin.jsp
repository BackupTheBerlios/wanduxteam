<%@ page language="java" %>
<%@ page import='java.util.*' %>
<%@ page import='pfe.migration.client.network.ClientEjb' %>
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
				int i;
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
	   	if (document.myform.machine.value == 'none')
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
	   	if (document.myform.machine.value == 'none')
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
				for (i=0; i < cl.size(); i++)
				{
					out.print("this['tree'+'" + cl.get(i) +"'] = 0;\n");
				}
%>
    }
    
    function isdefined(variable)
	{
    	return (typeof(window[variable]) == "undefined")?  false: true;
	}
	
	function selectMachine()
	{
		if (document.myform.machine.value != 'none')
		{
			if (last_div != "no")
			{
				temp = document.getElementById(last_div);
				temp.style.visibility = 'hidden';
				temp.style.width = 0;
				temp.style.height = 0;
			}
			temp = document.getElementById('none');
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
				temp = document.getElementById('treeBox'+document.myform.machine.value);
				if (this['tree'+document.myform.machine.value] == 0)
				{
					this['tree'+document.myform.machine.value] = new dhtmlXTreeObject(document.getElementById('treeBox'+document.myform.machine.value),"100%","100%",0);
					this['tree'+document.myform.machine.value].setImagePath("img/tree/");
					this['tree'+document.myform.machine.value].enableCheckBoxes(true);
					this['tree'+document.myform.machine.value].enableThreeStateCheckboxes(true);
					this['tree'+document.myform.machine.value].loadXML("xml/" + document.myform.machine.value + ".xml");
				}
				last_div = 'treeBox'+document.myform.machine.value;
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
			temp = document.getElementById('none');
			temp.style.visibility = 'visible';
			temp.style.width = 650;
			temp.style.height = 400;
		}
	}
		
	</script>
    <BODY onLoad="init_trees();">
	<TABLE border=0>
	<tr>
		<td align="left" valign="down" colspan="2" width="800" height="82">
			<img src="img/barrewandux.bmp" border=0>
		</td>
	</tr>
	<tr>
		<td valign="top" width="150" height="600" style="background-image: url(img/barre_grise.jpg);">
			<FORM name="myform" action="test.jsp" method="post">
				<SELECT name="machine" onChange="selectMachine();">
				<OPTION value="none">none</OPTION>				
<%				for (i=0; i < cl.size(); i++)
				{
					out.print("<OPTION value=\"" + cl.get(i) +"\">" + cl.get(i) + "</OPTION>\n");
				}%>
				</SELECT>
		</td>
		<td width="650" height="400">


		
	

		<div id="none" style=\"width:650;height:400;visibility:visible;position:absolute;top:120;left:160;\">
		<br><br><center><b>Please, choose a computer to migrate in the list on the left of your screen.</b><center>
		</div>
<%				for (i=0; i < cl.size(); i++)
				{
					out.print("<div id=\"CI_" + cl.get(i) + "\" style=\"width:0;height:0;visibility:hidden;position:absolute;top:120;left:160;\"><b>Informations extracted from " + cl.get(i) + ".<br><br> Please, check these settings and go on next Step</b><br><br>");
					out.print("ICI ON PRINT LES INFOS<br><br>EXTRAITES DE COMPUTER INFORMATION<br>");
					out.print("</div>\n");
				}

				for (i=0; i < cl.size(); i++)
				{
					out.print("<div id=\"treeBox" + cl.get(i) + "\" style=\"width:0;height:0;visibility:hidden;position:absolute;top:120;left:160;\"><b>This tree represent the whole Windows file system of " + cl.get(i) + ".<br><br> Please, select the files and folders that you want to migrate on the new Linux system using this tree :</b><br><br></div>\n");
				}
				
				for (i=0; i < cl.size(); i++)
				{
					out.print("<div id=\"ChoosePrograms" + cl.get(i) + "\" style=\"width:0;height:0;visibility:hidden;position:absolute;top:120;left:160;\"><b> There is a list of Linux programs that are equivalent to the Windows Softwares installed on " + cl.get(i) + "<br><br>Select the programs that will be installed on the new Linux System</b><br><br> ICI : SECTION D'EQUIVQLENCE LOGICIELS WINDOWS / LINUX ET CHOIX DES SOFTS  A MIGRER.<br></div>\n");
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
			<a href="javascript:next_step();"><img src="img/fleche_suivant.gif" border=0></a>
		</td>
		
		<a href="javascript:document.myform.paths.value = this['tree'+document.myform.machine.value].getAllChecked();document.myform.submit();"></a>

	</tr>
	<INPUT name="paths" type="hidden">
	</FORM>
	</TABLE>
    </BODY>
	</HTML>