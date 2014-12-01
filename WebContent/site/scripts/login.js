


function login(form){
	var username=form.username.value;
	var password=form.password.value;
	if (validation(username,password)){
		connexion(username,password);
		}
}

function validation(username, password){

	if (username.length == 0){
		alert("Login obligatoire");
		return false;
	}
	if (password.length == 0){
		alert("Mot de passe obligatoire");
		return false;
	}
	
	if (username.length >20){
		alert("Login trop long (max 20 charactères");
		return false;
	}
	
	if (password.length > 20){
		alert("Mot de passe trop long (max 20 charactères)");
		return false;
	}
	return true;
}


function connexion(login, password){
	$("#erreur").replaceWith();
	$.ajax({
		url: "http://localhost:8080/khecha_djamah/Login?login=sam&password=abcD", 
		success:function(result){
		    alert(result);
		  }, 
		error: function (jhi, status, exception){ 
			alert("Probleme de communication "+status+" "+exception);
			//deconnexion();				//deconnexion de l'utilisateur si erreur 
			}
	});
}

function traiteReponseConnexion(json){
	if(json.erreur!=undefined){
		alert(json.erreur);
	}
	else{
		alert(json.login + "");
	}
}