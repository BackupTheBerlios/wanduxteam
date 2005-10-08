<%@ page language="Java" import="java.util.*" %>
<br>Paths of the selected files printed from the JSP :<br><br><br>

	<%
		String[] paths;
		paths = (String[])request.getParameterValues("paths");
		
		if (paths != null)
		{
			out.print(paths[0]);
			out.print("<br>");
		}
	%>