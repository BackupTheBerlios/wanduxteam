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
    
    function isdefined(variable)
	{
    	return (typeof(window[variable]) == "undefined")?  false: true;
	}
	
	function selectMachine()
	{
		if (document.myform.machine.value != 'none')
		{
			alert("loading : xml/" + document.myform.machine.value + ".xml");
			if (last_ip != "no")
			{
				temp = document.getElementById('treeBox'+last_ip);
				temp.style.visibility = 'hidden';
				temp.style.width = 0;
				temp.style.height = 0;
			}
			
			temp = document.getElementById('treeBox'+document.myform.machine.value);
			temp.style.visibility = 'visible';
			temp.style.width = 200;
			temp.style.height = 200;
			tree = new dhtmlXTreeObject(document.getElementById('treeBox'+document.myform.machine.value),"100%","100%",0);
			tree.setImagePath("img/tree/");
			tree.loadXML("xml/" + document.myform.machine.value + ".xml");
			tree.enableCheckBoxes(true);
			tree.enableThreeStateCheckboxes(true);
		}
		last_ip  = document.myform.machine.value;
	}
	</script>
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
		<td valign=top>
			<FORM name="myform" action="finish.jsp" method="post">
			<div id="select_ip">
				<SELECT name="machine" onChange="selectMachine();">
				
<%
				ClientEjb ce = (ClientEjb)application.getAttribute("ClientEjb");
				WanduxEjb bean = ce.getBean();
				List cl = bean.getIps();
				int i;
				for (i=0; i < cl.size(); i++)
				{
					out.print("<OPTION value=\"" + cl.get(i) +"\">" + cl.get(i) + "</OPTION>");
				}
%>
				
				</SELECT>
			</div>
		</td>
		<td>
<%				for (i=0; i < cl.size(); i++)
				{
					out.print("<div id=\"treeBox" + cl.get(i) + "\" style=\"width:0;height:0;visibility:hidden\"></div");
				}
%>
		</td>
	</tr>
	<tr>
		<td align=left>
			<a href="javascript:void(0);" onclick="alert(tree.getAllChecked());">Get list of checked</a>
		</td>
		<td align=right>
			<a href="test.jsp"><img src="img/go.gif" border=0></a>
		</td>
	</tr>
	</FORM>
	</TABLE>
    </BODY>
	</HTML>