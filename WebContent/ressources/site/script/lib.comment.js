/*addComment : ajoute un comment � un message, � partir d'un formulaire*/
function addComment(formulaire,id_message_parent) {
	var comment = formulaire.comment.value;
	if(verifierFormulaireComment(formulaire,comment,id_message_parent)){
		ajoutecomment(formulaire,comment,id_message_parent);
	}
}
/*verifierFormulaireComment : v�rifie le formulaire commentaire*/
function verifierFormulaireComment(formulaire,comment,id_message_parent) {
	if(!environnement.actif){
		func_erreur(formulaire,"msg_"+id_message_parent+"_comment","Vous devez etre connecte.");
		return false;
	} else if(comment.length == 0){
		func_erreur(formulaire,"msg_"+id_message_parent+"_comment","Commentaire incorrect.");
		return false;
	}
	return true;
}


/*ajoutecomment : ajoute un message*/
function ajoutecomment(formulaire,message,id_message_parent){
	$.ajax({
		type : "GET",
		url : environnement.urlBaseServlet+"CreateComment",
		data : "key="+environnement.key+"&id_message="+id_message_parent+"&message="+message,
		dataType : "json",
		success : function (rep){ traiteRepAjouteComment(formulaire,rep,id_message_parent) },
		error : function (xhr,textstatus,errorthrow){
			func_erreur(formulaire,"msg_"+id_message_parent+"_comment",xhr+" "+textstatus+" "+errorthrow);
		}
	});
}
function traiteRepAjouteComment(formulaire,rep,id_message_parent){
	if(rep.error_code!=undefined){
		func_erreur(formulaire,"msg_"+id_message_parent+"_comment",rep.message);
	} else {
		func_succes(formulaire,"msg_"+id_message_parent+"_comment","Votre commentaire a &eacute;t&eacute; envoy&eacute; avec succ&egrave;s.");
		loadMessages();
	}
}

/*Comment object*/
function comment(id,user,texte,date){
	this.id=id;
	this.user = user;
	this.texte = texte;
	this.date = date;
}

/*Comment method getHTML*/
comment.prototype.getHTML = function(){
	return '<div class="comment"><div>Commentaire de '+this.user.login+' le '+this.date+'</div><p>'+this.texte+'</p></div>'
}

/*Traitement des commentaires depuis json*/
function CommentstraiteReponseJson(comments){
	var tab = new Array();
	if (comments!=null) {
		for (var i in comments){
			if(!environnement.users[comments[i].id_user]){
				environnement.users[comments[i].id_user] = new user(comments[i].id_user,comments[i].name_user,false);
			}
			tab[i] = new comment(
				comments[i]._id.$oid,
				environnement.users[comments[i].id_user],
				comments[i].message,
				comments[i].date
			);
		}
	}
	return tab;
}
