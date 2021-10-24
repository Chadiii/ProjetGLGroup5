<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url var="bootstrap_css"
	value="/webjars/bootstrap/4.6.0-1/css/bootstrap.min.css" />
<c:url var="bootstrap_js"
	value="/webjars/bootstrap/4.6.0-1/js/bootstrap.min.js" />
<c:url var="jquery_js" value="/webjars/jquery/3.5.1/jquery.min.js" />
<c:url var="css" value="/style.css" />

<html>
<head>
<meta charset="UTF-8">
<title>OnlineShop</title>
<link rel="stylesheet" href="${css}">
<link rel="stylesheet" href="${bootstrap_css}">
<script src="${jquery_js}"></script>
<script src="${bootstrap_js}"></script>
</head>
<body>


	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<%
			if (session.getAttribute("active") != null) {
		%>
		<a class="navbar-brand" href="/admin">Liste des Produits</a> <a
			class="navbar-brand" href="/admin/order">Liste des Commandes</a> <a
			class="navbar-brand" href="/logout">Se déconnecter</a>
		<%
			} else {
		%>

		<a class="navbar-brand" href="/products">Liste des Produits</a> <a
			class="navbar-brand" href="/order">Mes Commandes</a>
		<%
				if (session.getAttribute("cart") != null) {
		%>
					<a class="navbar-brand" href="/cart">Panier
						<span class="position-absolute top-0 start-100 translate-middle p-2 bg-danger border border-light rounded-circle">
						</span>
					</a>
		<%		}
}

%>

	</nav>