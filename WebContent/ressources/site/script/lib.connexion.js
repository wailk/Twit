/*Connexion � partir du formulaire*/
function connexion(formulaire){
	var login = formulaire.username.value;
	var password=formulaire.password.value;
	if(verifierFormulaireConnexion(formulaire,login,password)){
		connecte(formulaire,login,pass);
	}
}

function message_erreur(msg){
	var er=$("#erreur");
	if (er.length==0){
		$("#formulaire").prepend("<div id='erreur' color='green'>"+msg+"</div>");
	}
	else{
		$("#erreur").replaceWith("<div id='erreur' color='green'>"+msg+"</div>");
	}
}

/*V�rification du formulaire de connexion*/
function verifierFormulaireConnexion(formulaire,login,password){
	if(login.length == 0 && password.length == 0){
		func_erreur(formulaire,"msg_connexion","Login et mot de passe incorrects.");
		return false;
	}
	if(login.length == 0){
		func_erreur(formulaire,"msg_connexion","Login obligatoire.");
		return false;
	}
	if(password.length == 0){
		func_erreur(formulaire,"msg_connexion","Password obligatoire.");
		return false;
	
	if (login.length >20){
		alert("Login trop long (max 20 charact�res");
		return (false);
	}
	
	if (password.length > 20){
		alert("Mot de passe trop long (max 20 charact�res)");
		return (false);
	}
	}
	return true;
}

/*Requ�te Ajax pour appeler la servlet Login*/
function connecte(formulaire,login,pass){
	alert ("connexion: "+login+","+password);
	$.ajax({
		type : "GET", //methode d'envoi au serveur
		url : "http://132.227.201.129:33306/khecha_djamah/Login", //addresse du serveur et "Login" est le nom de la servlet
		data : "login="+login+"&pass="+pass, //comment �a apparait apr�s l'url
		dataType : "json", //le p sert � �viter les cas de cross domain car la servlet n'est pas � la meme adresse
		success : traiteRepConnexion, //quand on aura une r�ponse du serveur, on appellera cette fonction avec en 
						//param�tre la r�ponse du serveur
		error : function (xhr,textstatus,errorthrow){
			func_erreur(formulaire,"msg_connexion",xhr+" "+textstatus+" "+errorthrow);
			disconnect();//deconnexion de l'utilisateur si erreur
		}
	});
}

function traiteRepConnexion(rep){
	if(rep.error_code!=undefined){
		func_erreur(".contenu","msg_connexion",rep.message);
	} else {
		func_succes(".contenu","msg_connexion","Vous &ecirc;tes connect&eacute;.");
		window.location.href = "index.html?id="+rep.id+"&key="+rep.key+"&login="+rep.login;
	}
}
/*Deconnecte l'utilisateur*/
function disconnect(){
	$.ajax({
		type : "GET",
		url : "http://132.227.201.129:33306/khecha_djamah/Logout", 
		data : "key="+environnement.key,
		dataType : "json",
		success : function (rep){
			window.location.href="index.html";
		},
		error : function (xhr,textstatus,errorthrow){
			func_erreur("#main","msg_main",xhr+" "+textstatus+" "+errorthrow);
		}
	});
}
