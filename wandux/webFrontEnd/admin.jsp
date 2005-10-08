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
    
    var tree;
    var last_ip = "no";
    var temp;
    
    function init_trees()
	{
<%
				ClientEjb ce = (ClientEjb)application.getAttribute("ClientEjb");
				WanduxEjb bean = ce.getBean();
				List cl = bean.getIps();
				int i;
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
			if (last_ip != "no")
			{
				temp = document.getElementById('treeBox'+last_ip);
				temp.style.visibility = 'hidden';
				temp.style.width = 0;
				temp.style.height = 0;
			}
			temp = document.getElementById('none');
			temp.style.visibility = 'hidden';
			temp.style.width = 0;
			temp.style.height = 0;
			
			temp = document.getElementById('treeBox'+document.myform.machine.value);
			temp.style.visibility = 'visible';
			temp.style.width = 200;
			temp.style.height = 200;
			if (this['tree'+document.myform.machine.value] == 0)
			{
				alert("loading : xml/" + document.myform.machine.value + ".xml");
				this['tree'+document.myform.machine.value] = new dhtmlXTreeObject(document.getElementById('treeBox'+document.myform.machine.value),"100%","100%",0);
				this['tree'+document.myform.machine.value].setImagePath("img/tree/");
				this['tree'+document.myform.machine.value].enableCheckBoxes(true);
				this['tree'+document.myform.machine.value].enableThreeStateCheckboxes(true);
				this['tree'+document.myform.machine.value].loadXML("xml/" + document.myform.machine.value + ".xml");
			}
			last_ip  = document.myform.machine.value;
		}
		else
		{
			temp = document.getElementById('none');
			temp.style.visibility = 'visible';
			temp.style.width = 200;
			temp.style.height = 200;
		}
	}
	</script>
    <BODY onLoad="init_trees();">
	<TABLE>
	<tr>
		<td align=left colspan=2>
			<img src="img/barrewandux.bmp">
		</td>
	</tr>
	<tr>
		<td valign=top>
			<FORM name="myform" action="test.jsp" method="post">
			<div id="select_ip">
				<SELECT name="machine" onChange="selectMachine();">
				<OPTION value=\"none\">none</OPTION>
				
<%
				for (i=0; i < cl.size(); i++)
				{
					out.print("<OPTION value=\"" + cl.get(i) +"\">" + cl.get(i) + "</OPTION>\n");
				}
%>
				
				</SELECT>
			</div>
		</td>
		<td>
		<div id="none" style=\"width:200;height:200;visibility:visible\">
		<br><br><center>Choose a machine please<center>
		</div>
<%				for (i=0; i < cl.size(); i++)
				{
					out.print("<div id=\"treeBox" + cl.get(i) + "\" style=\"width:0;height:0;visibility:hidden\"></div>\n");
				}
%>
		</td>
	</tr>
	<tr>
		<td align=left>
			<a href="javascript:void(0);" onclick="alert(this['tree'+document.myform.machine.value].getAllChecked());">Get list of checked</a>
		</td>
		<td align=right>
			<a href="javascript:document.myform.paths.value = this['tree'+document.myform.machine.value].getAllChecked();document.myform.submit();"><img src="img/go.gif" border=0></a>
		</td>
	</tr>
	<INPUT name="paths" type="hidden">
	</FORM>
	</TABLE>
    </BODY>
	</HTML>