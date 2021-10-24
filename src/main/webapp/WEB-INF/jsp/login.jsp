<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">
	<h1>Authentification</h1>

	<form method="post" action="/login">

		<div class="form-group">
			<label class="form-label">Login :</label>
			<input type="text" class="form-control" name="login" required/>
		</div>
		<div class="form-group">
			<label class="form-label">Password</label>
			<input type="password" class="form-control" name="password" required/>
		</div>
		<div class="form-group">
			<button type="submit" class="btn btn-primary">Se connecter</button>
		</div>
	</form>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
