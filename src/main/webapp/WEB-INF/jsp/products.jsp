<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">
	<h1>Liste des produits</h1>

	<c:url var="productAction" value="/admin" />
	
	<a class="btn btn-success btn-sm" href="${productAction}/add">Ajouter</a>


	<table class="table">
		<tr>
			<th>Nom</th>
			<th>Quantité</th>
			<th>Prix</th>
			<th>Description</th>
			<th>Actions</th>
		</tr>
		<c:forEach var="product" items="${products}">
			<tr>
				<td>${product.name}</td>
				<td>${product.quantity}</td>
				<td>${product.price}</td>
				<td>${product.description}</td>
				<td><a class="btn btn-success btn-sm"
					href="${productAction}/edit/${product.id}">Modifier</a>
					<a class="btn btn-danger btn-sm"
					href="${productAction}/delete/${product.id}" onclick="return confirm('Voulez-vous supprimer ce produit')">Supprimer</a></td>
			</tr>
		</c:forEach>
	</table>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>