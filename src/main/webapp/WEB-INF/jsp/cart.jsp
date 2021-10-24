<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">

    <%if(request.getAttribute("productWithLessQuantity") != null){ %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        la quantité demandée pour ce produit "${productWithLessQuantity.name}" est supérieur a sa quantité en stock !
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <% } %>

    <h1>Liste des produits dans Panier</h1>

    <table class="table">
        <tr>
            <th>Nom</th>
            <th>Quantité</th>
            <th>Prix</th>
            <th>Description</th>
            <th>Quantité commandée</th>
            <th>Action</th>
        </tr>
        <c:forEach var="entry" items="${cart}">
            <tr>
                <td>${entry.key.name}</td>
                <td>${entry.key.quantity}</td>
                <td>${entry.key.price}</td>
                <td>${entry.key.description}</td>
                <td>${entry.value}</td>
                <td>
                    <a class="btn btn-danger btn-sm"
                       href="cart/delete/${entry.key.id}" onclick="return confirm('Voulez-vous supprimer ce produit du panier')">Supprimer du Panier</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a class="btn btn-danger btn-sm" href="cart/cancel" onclick="return confirm('Voulez-vous annuler votre commande')">Annuler Commande</a>
    <a class="btn btn-primary btn-sm" href="cart/confirm" onclick="return confirm('Voulez-vous confirmer votre commande')">Confirmer Commande</a>

</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>