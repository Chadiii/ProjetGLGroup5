<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">

	<form method="get" action="/order">
		<div class="input-group">
			<input type="text" class="form-control" name="mail" placeholder="Enter Votre Email" required/>
			<button type="submit" class="btn btn-primary">Rechercher</button>
		</div>
	</form>

	<h1>Liste des commande</h1>

	<table class="table">
		<tr>
			<th>La date la création de commande</th>
			<th>La date de livraison de la commande</th>
			<th>List des Produits</th>
			<th>Action</th>
		</tr>
		<c:forEach var="order" items="${orders}">
			<tr>
				<td>${order.orderDate}</td>
				<td>${order.shippingDate}</td>
				<td>
					<c:forEach var="product" items="${order.products}">${product.name} <br/> </c:forEach>
				</td>
				<td>
				
					<a class="btn btn-danger btn-sm"
					href="order/cancel/${order.id}" onclick="return confirm('Voulez-vous annuler cette commande')">Annuler</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>