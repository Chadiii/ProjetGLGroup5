<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">
    <h1>Liste des commande</h1>

    <c:url var="orderAction" value="/admin/order" />

    <table class="table">
        <tr>
            <th>La date la création de commande</th>
            <th>La date de livraison de la commande</th>
            <th>List des Produits</th>
            <th>Client</th>
            <th>Action</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.orderDate}</td>
                <td>${order.shippingDate}</td>
                <td>
                    <c:forEach var="product" items="${order.products}">${product.name} <br/> </c:forEach>
                </td>
                <td>${order.client.name}</td>
                <td><a class="btn btn-danger btn-sm"
                       href="${orderAction}/delete/${order.id}" onclick="return confirm('Voulez-vous supprimer cette commande')">Supprimer</a></td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>