/*Inscription � partir du formulaire*/
function inscription(formulaire){
	var login = formulaire.login.value;
	var pass = formulaire.pass.value;
	var nom = formulaire.nom.value;
	var prenom = formulaire.prenom.value;
	var mdp2= formulaire.pass2.value;
	if(verifierFormulaireInscription(formulaire,login,pass,nom,prenom,mdp2)){
		inscrit(formulaire,login,pass,nom,prenom);
	}
}
/*V�rification du formulaire d'inscription*/
function verifierFormulaireInscription(formulaire,login,pass,nom,prenom,mdp2){
	if(login.length == 0){
		func_erreur(formulaire,"msg_inscription","Login incorrect.");
		return false;
	}
	if(pass.length == 0){
		func_erreur(formulaire,"msg_inscription","Password incorrect.");
		return false;
	}
	if(mdp2.length == 0){
		func_erreur(formulaire,"msg_inscription","Password incorrect.");
		return false;
	}
	if(nom.length == 0){
		func_erreur(formulaire,"msg_inscription","Nom incorrect.");
		return false;
	}
	if(prenom.length == 0){
		func_erreur(formulaire,"msg_inscription","Pr&eacute;nom incorrect.");
		return false;
	}
	return true;
}

/*Requ�te Ajax pour appeler la servlet CreateUser*/
function inscrit(formulaire,login,pass,nom,prenom){
	$.ajax({
		type : "GET",
		url : environnement.urlBaseServlet+"/user/new",
		data : "login="+login+"&pass="+pass+"&lname="+nom+"&fname="+prenom,
		dataType : "json",
		success : traiteRepInscription,
		error : function (xhr,textstatus,errorthrow){
			func_erreur(formulaire,"msg_inscription",xhr+" "+textstatus+" "+errorthrow);
		}
	});
}
function traiteRepInscription(rep){
	if(rep.error_code!=undefined){
		func_erreur(".contenu","msg_inscription",rep.message);
	} else {
		func_succes(".contenu","msg_inscription","Vous &ecirc;tes d&eacute;sormais inscrit.");
		window.location.href = "index.html";
	}
}

