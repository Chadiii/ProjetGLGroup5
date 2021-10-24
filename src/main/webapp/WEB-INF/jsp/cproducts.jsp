<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">

	
	<form method="get" action="/products">
		<div class="input-group">
			<input type="text" class="form-control" name="name" placeholder="Entrer le Nom du Produit" required/>
			<button type="submit" class="btn btn-primary">Filtrer</button>
		</div>
	</form>
	
	<h1>Liste des produits</h1>

	<table class="table">
		<tr>
			<th>Nom</th>
			<th>Quantité</th>
			<th>Prix</th>
			<th>Description</th>
			<th>Quantité a commander</th>
			<th>Action</th>
		</tr>
		<c:forEach var="product" items="${products}">
			<tr>
				<td>${product.name}</td>
				<td>${product.quantity}</td>
				<td>${product.price}</td>
				<td>${product.description}</td>
				<td>
					<button class="btn btn-light rounded" onclick="decrementCounter('quantityIncDec${product.id}')">-</button>
					<label id="quantityIncDec${product.id}">1</label>
					<button class="btn btn-light rounded" onclick="incrementCounter('quantityIncDec${product.id}',${product.quantity})">+</button>
				</td>
				<td>
					<form method="post" action="/cart/add">
						<div class="form-group">
							<input type="hidden" name="quantityInput"  id="InputquantityIncDec${product.id}" value="1" />
							<input type="hidden" name="idInput" value="${product.id}" />
							<button type="submit" class="btn btn-light rounded">Ajouter au Panier</button>
						</div>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<script type="text/javascript">
	function decrementCounter(id){
		var counter = document.getElementById(id).textContent;
		if(counter > 1){
			counter = parseInt(counter) - 1;
			document.getElementById(id).textContent = counter ;
			document.getElementById('Input'+id).value = counter ;
		}
	}
	function incrementCounter(id, max){
		var counter = document.getElementById(id).textContent;
		if(counter < max){
			counter = parseInt(counter) + 1;
			document.getElementById(id).textContent = counter ;
			document.getElementById('Input'+id).value = counter ;
		}
	}
</script>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>