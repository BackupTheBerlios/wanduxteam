<%@ page language="java" import="pfe.migration.client.network.ClientEjb, pfe.migration.server.ejb.WanduxEjb, java.util.*" %>
	<HTML>
    <HEAD>
        <TITLE>Wandux</TITLE>
        <link type="text/css" rel="stylesheet" href="style.css">
    </HEAD>
    <script language="JavaScript" src="js/tree/dhtmlXCommon.js"></script>
    <script language="JavaScript" src="js/tree/dhtmlXTree.js"></script>
	function selectMachine()
	{
		if (document.myform.machine.value != 'none')
		{
			alert("loading : xml/" + document.myform.machine.value + ".xml");
			delete tree;

		}
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
			<div id="treeBox" style="width:200;height:200">
			</div>
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