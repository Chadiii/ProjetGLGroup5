<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">
    <h1>Entrer les informations pour paiment</h1>

    <form method="post" action="/order/save">

        <div class="form-group">
            <label class="form-label">Nom Complet:</label>
            <input type="text" class="form-control" name="name" required/>
        </div>
        <div class="form-group">
            <label class="form-label">Email :</label>
            <input type="email" class="form-control" name="mail" required/>
        </div>
        <div class="form-group">
            <label class="form-label">Nom du proprietaire du carte :</label>
            <input type="text" class="form-control" name="ccName" required/>
        </div>
        <div class="form-group">
            <label class="form-label">Numero de la carte :</label>
            <input type="text" class="form-control" name="ccNumber" pattern="[0-9]{12}" title="Ce champs est compos� de 12 chiffres" required/>
        </div>
        <div class="form-group">
            <label class="form-label">Date d'Expiration (mm/aa) :</label>
            <input type="text" class="form-control" name="ccExpiration"  pattern="((0[1-9])|(1[0-2]))/[0-9]{2}"  required/>
        </div>
        <div class="form-group">
            <label class="form-label">CCV :</label>
            <input type="text" class="form-control" name="ccCCV" pattern="[0-9]{3}" required/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Enregistrer</button>
        </div>
    </form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
