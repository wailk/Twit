/*Ajoute ou supprime un like d'un message*/
function addDelLike(id_message) {
    if (existeLike(id_message)) {
        var url = environnement.urlBaseServlet+"RemoveLikeMessage";
        deleteLike(id_message);
    } else {
    	var url = environnement.urlBaseServlet+"AddLikeMessage";
    	ajouterLike(id_message);
    }
    $.ajax({
        type : "GET",
        url : url,
        data : "key="+environnement.key+"&id_message="+id_message,
        dataType : "json",
        success : function(rep){ traiteRepAddDellLike(rep,id_message) },
        error : function (xhr,textstatus,errorthrow){
            func_erreur("#main","msg_main",xhr+" "+textstatus+" "+errorthrow);
        }
    });
}
function traiteRepAddDellLike(rep,id) {
    if (rep.error_code!=undefined) {
        func_erreur("#main","msg_main",rep.message);
    }
    var messages = environnement.messages;
    for (var i in messages) {
        if (messages[i].id == id){
            $("#message_"+messages[i].id).replaceWith(messages[i].getHTML());
        }
    }
}
/*Ajoute un like d'un message à la variable environnement*/
function ajouterLike(id_message){
	id_message = getIDKeyFromIDMessage(id_message);
    var likes = environnement.messages[id_message].likes;
    likes.push({"id_user": environnement.actif.id});
}
/*Supprime un like d'un message à la variable environnement*/
function deleteLike(id_message){
	id_message = getIDKeyFromIDMessage(id_message);
	var tab = new Array();
    var likes = environnement.messages[id_message].likes;
    for (var i in likes) {
    	if(likes[i].id_user!=environnement.actif.id){
    		tab.push(likes[i]);
    	}
    }
    environnement.messages[id_message].likes = tab;
}
/*Vérifie sur un like existe déjà pour un message*/
function existeLike(id_message){
	id_message = getIDKeyFromIDMessage(id_message);
    var likes = environnement.messages[id_message].likes;
    for (var i in likes) {
    	if(likes[i].id_user==environnement.actif.id){
    		return true;
    	}
    }
    return false;
}