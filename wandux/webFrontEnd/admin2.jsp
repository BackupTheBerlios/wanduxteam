<%@ page language="java" import="pfe.migration.client.network.ClientEjb" %>
	<HTML>
    <HEAD>
        <TITLE>Wandux</TITLE>
        <link type="text/css" rel="stylesheet" href="style.css">
    </HEAD>
    <script language="JavaScript" src="js/tree/dhtmlXCommon.js"></script>
    <script language="JavaScript" src="js/tree/dhtmlXTree.js"></script>
	<script>
	function doOnLoad()
	{
		tree=new dhtmlXTreeObject(document.getElementById('treeBox'),"100%","100%",0);
		tree.setImagePath("img/tree/");
		tree.loadXML("xml/tree.xml");
		tree.enableCheckBoxes(true);
		tree.enableThreeStateCheckboxes(true);
	}
	
	function selectMachine()
	{
		if (document.myform.machine.value != 'none')
		{
			alert("loading : xml/" + document.myform.machine.value + ".xml");
			delete tree;

		}
	}
	</script>
    <BODY onLoad="doOnLoad()">
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
			<FORM name="myform" action="listFile.jsp" method="post">
			<div id="select_ip">
				<SELECT name="machine" onChange="selectMachine();">
				<OPTION value="none">Choose a machine</OPTION>
				<OPTION value="tree">tree</OPTION>
				<OPTION value="tree42">tree42</OPTION>
				</SELECT>
			</div>
			</FORM>
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
	</TABLE>
    </BODY>
	</HTML>