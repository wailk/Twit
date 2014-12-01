function afficherUser(formulaire) {
	var nom = formulaire.nom.value;
	if(verifierFormulaireAfficherUser(formulaire,nom)){
		loadStatut(nom);
	}
}

function verifierFormulaireAfficherUser(formulaire,nom) {
	if(nom.length == 0){
		func_erreur(formulaire,"msg_message","Nom incorrect.");
		return false;
	}
	return true;
}

function loadStatut(login) {
	var urlReq = environnement.urlBaseServlet+"GetStatutUtilisateur";
	$.ajax({
		type : "GET",
		url : urlReq,
		dataType : "json",
		data:"login="+login,
		success : function(rep){
			func_load("#main","msg_load","Chargement du statut..");
			$(".bloc_msg_erreur").slideUp("slow");
			$("#StatutUser").html("<h1>Statut</h1>"+rep.statut);
		},
		error : function (xhr,textstatus,errorthrow){
		   func_erreur(".contenu","msg_main",xhr+" "+textstatus+" "+errorthrow);
		}
	});
}


function modifierStatutUser(formulaire) {
	var nom = formulaire.nom.value;
	var message = formulaire.message.value;
	if(verifierFormulaireModifierStatutUser(formulaire,nom,message)){
		modifierStatut(formulaire,nom,message);
	}
}

function verifierFormulaireModifierStatutUser(formulaire,nom,message) {
	if(nom.length == 0){
		func_erreur(formulaire,"msg_message","Nom incorrect.");
		return false;
	}
	if(message.length == 0){
		func_erreur(formulaire,"msg_message","Message incorrect.");
		return false;
	}
	return true;
}

function modifierStatut(formulaire,nom,message){
	var urlReq = environnement.urlBaseServlet+"StatutUtilisateur";
	$.ajax({
		type : "GET",
		url : urlReq,
		dataType : "json",
		data:"login="+nom+"&statut="+message,
		success : function(rep){
			$(".bloc_msg_erreur").slideUp("slow");
			func_succes(formulaire,"msg_modifier","Message modifier avec succes.");
			 loadMessage(nom);
		},
		error : function (xhr,textstatus,errorthrow){
		   func_erreur(formulaire,"msg_modifier",xhr+" "+textstatus+" "+errorthrow);
		}
	});
}

