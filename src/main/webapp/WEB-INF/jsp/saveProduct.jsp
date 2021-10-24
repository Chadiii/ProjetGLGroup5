<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">
    <% if(request.getAttribute("product") == null) { %>
    <h1>Ajouter un Produit</h1>
    <% }else{%>
    <h1>Modifier un Produit</h1>
    <% 	}%>

    <form method="post" action="/saveProduct">

        <% if(request.getAttribute("product") != null) {%>
        <input type="hidden" name="id" value="${product.id}" required/>
        <% } %>

        <div class="form-group">
            <label class="form-label">Nom :</label>
            <input type="text" name="name" value="${product.name}" class="form-control" required/>
        </div>
        <div class="form-group">
            <label class="form-label">Quantité :</label>
            <input type="text" name="quantity" value="${product.quantity}" class="form-control" pattern="[0-9]+" title="Ce champs est composé de chiffres sans , exp:11" required/>
        </div>
        <div class="form-group">
            <label class="form-label">Prix :</label>
            <input type="text" name="price" value="${product.price}" class="form-control" pattern="[0-9]+(\.[0-9]{1,2})?" title="Ce champs est composé de chiffres exp : 11 ou 11.02" required/>
        </div>
        <div class="form-group">
            <label class="form-label">Description :</label>
            <textarea rows="3" class="form-control" name="description">${product.description}</textarea>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Enregistrer</button>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
