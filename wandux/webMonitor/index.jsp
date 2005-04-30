<%@ page language="java" import="pfe.migration.client.network.ClientEjb" %>
<html><head>

<title>Wandux monitor</title>
</head>
<body>
<center>Welcome to the wandux monitoring control</center>
<%

ClientEjb ce = new ClientEjb("127.0.0.1");

out.println("ClientEjb instanciate<br>");

ce.EjbConnect();

out.println("connection established ... <br>");

ce.EjbClose();

out.println("connection closed ... <br>");

%>
</body>
</html>
