<%@ page language="java" import="pfe.migration.client.network.ClientEjb, pfe.migration.server.monitor.ClientMonitor, pfe.migration.server.monitor.ClientMonitorListener" %>
<html><head>

<title>Wandux monitor</title>
</head>
<body>
<center>Welcome to the wandux monitoring control</center>
<%
ClientEjb ce = (ClientEjb)application.getAttribute("ClientEjb");
if (ce == null)
{
	ce = new ClientEjb("127.0.0.1");
	application.setAttribute("ClientEjb", ce);
}

out.println("ClientEjb instanciate<br>");

ce.EjbConnect();

out.println("connection established ... <br>");

//ClientMonitor cm = (ClientMonitor)ce.ClientMonitor();
ClientMonitor cm = ce.makeTheClientMonitoring();
//out.println(cm.pouet() + "<br>");

ce.EjbClose();

out.println("connection closed ... <br>");

%>
</body>
</html>
