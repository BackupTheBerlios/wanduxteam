<%@ page language="Java" import="java.util.*" %>
<%@ page import='pfe.migration.client.network.ClientEjb' %>
<%@ page import='pfe.migration.client.network.ComputerInformation' %>
<%@ page import='pfe.migration.server.ejb.WanduxEjb' %>


<%				//TODO !!! PASSER EN PARAMETRE L'ID DU PC + les infos de progs et de fichiers

				ClientEjb ce = (ClientEjb)application.getAttribute("ClientEjb");
				WanduxEjb bean = ce.getBean();
				List cl = bean.getIps();
				int computer;
				
				computer = (String[])request.getParameterValues("computer");
				ComputerInformation ci = bean.getCi((String)cl.get(computer));
				
				//ICI INSERER LES PROGRAMMES ET FICHIERS CHOISIS DANS LE CI
				
				
				
				///////////////////////////////////////////////////////////
				
				
				
				ci.migrable = 1;
				bean.putCi(ci);
				
				//ICI REDIRIGER VERS ADMIN.JSP
%>