/*addMessage : ajoute un message à partir d'un formulaire*/
function addMessage(formulaire) {
	var message = formulaire.message.value;
	if(verifierFormulaireMessage(formulaire,message)){
		ajoutemessage(formulaire,message);
	}
}
/*verifierFormulaireMessage : vérifie le formulaire message*/
function verifierFormulaireMessage(formulaire,message) {
	if(!environnement.actif){
		func_erreur(formulaire,"msg_message","Vous devez etre connecte.");
		return false;
	} else if(message.length == 0){
		func_erreur(formulaire,"msg_message","Message incorrect.");
		return false;
	}
	return true;
}
/*ajoutemessage : ajoute un message*/
function ajoutemessage(formulaire,message){
	$.ajax({
		type : "GET",
		url : environnement.urlBaseServlet+"CreateMessage",
		data : "key="+environnement.key+"&message="+message,
		dataType : "json",
		success : function (rep){ traiteRepAjouteMessage(formulaire,rep) },
		error : function (xhr,textstatus,errorthrow){
			func_erreur(formulaire,"msg_message",xhr+" "+textstatus+" "+errorthrow);
		}
	});
}
function traiteRepAjouteMessage(formulaire,rep){
	if(rep.error_code!=undefined){
		func_erreur(formulaire,"msg_message",rep.message);
	} else {
		func_succes(formulaire,"msg_message","Votre message a &eacute;t&eacute; envoy&eacute; avec succ&egrave;s.");
		formulaire.message.value = "";
		loadMessages();
	}
}

/*Message object*/
function message(id,user,texte,date,likes,comments,score){
	this.id = id;
	this.user = user;
	this.texte = texte;
	this.date = date;
	this.likes = likes;
	this.comments = comments;
	this.score = score;
}
/*Message method getHTML*/
message.prototype.getHTML = function(){
	var rt = '<div id="message_'+this.id+'" class="message"><div class="article"><div>Poster par '+this.user.login+' le '+this.date+'</div><p>'+this.texte+'</p>';
	//listes links
	rt += '<ul><li class="likeCount">'+this.likes.length+' personnes aiment &ccedil;a</li>';
	if(environnement.key!=undefined){
		if(!existeLike(this.id)){
			rt += '<li class="like"><a href="javascript:addDelLike(\''+this.id+'\');">J&apos;aime</a></li>';
		} else {
			rt += '<li class="like"><a href="javascript:addDelLike(\''+this.id+'\');">Je n&apos;aime plus</a></li>';
		}
	}
	if (environnement.key==undefined||environnement.actif.id==this.user.id) {
	} else {
		if (this.user.contact==true) {
			rt += '<li class="addFriend"><a href="javascript:addDelFriend(\''+this.user.id+'\');">Supprimer de mes amies</a></li>';
		} else {
			rt += '<li class="addFriend"><a href="javascript:addDelFriend(\''+this.user.id+'\');">Ajouter &agrave; mes amies</a></li>';
		}
	}
	rt += '<li class="commentsCount">'+this.comments.length+' commentaires</li>';
	rt += '</ul></div>';
	//formaddcomment
	if (environnement.key!=undefined) {
		rt += '<div class="formAddComment"><form  action="javascript:(function(){return;})();" onSubmit="javascript:addComment(this,\''+this.id+'\');">';
		rt += '<textarea class="textAreaComments" name="comment"></textarea><br /><input type="submit" value="Poster un commentaire"/><input type="hidden" name="id_message" value="'+this.id+'" />';
		rt += '</form></div>';
	}
	//comments
	rt += '<div class="comments">';
	//alert(this.comments.length);
	for(i=0;i<this.comments.length;i++){
		//alert(this.comments[i].getHTML());
		rt+=this.comments[i].getHTML();
	}
	rt +='</div></div>';
	return rt;
}

function loadMessages(key) {
	var urlReq = environnement.urlBaseServlet+"GetMessages";
	var dataReq = "";
	if(key!=""){
		dataReq = "key="+key;
	}
	$.ajax({
		type : "GET",
		url : urlReq,
		dataType : "text",
		data:dataReq,
		success : function(rep){
			func_load("#container","msg_load","Chargement des messages...");
			var objm = new RechercheMessages();
			objm.traiteReponseJson(rep);
			$("#messages").html(objm.getHTML());
			$("#messages").fadeIn("slow");
		},
		error : function (xhr,textstatus,errorthrow){
		   func_erreur(".contenu","msg_main",xhr+" "+textstatus+" "+errorthrow);
		}
	});
}

function RechercheMessages(){
}
RechercheMessages.prototype.traiteReponseJson = function(jsontext){
	var messages = JSON.parse(jsontext);
	messages = messages.result;
	var tab = new Array()
	if (messages!=null) {
		for(i=0;i<messages.length;i++){
			if(!environnement.users[messages[i].id_user]){
				environnement.users[messages[i].id_user] = new user(messages[i].id_user,messages[i].name_user,messages[i].is_friend);
			} else {
				if(environnement.users[messages[i].id_user].contact!= messages[i].is_friend){
					environnement.users[messages[i].id_user].modifistatus();
				}
			}
			environnement.messages[i] = new message(
				messages[i]._id,
				environnement.users[messages[i].id_user],
				messages[i].message,
				messages[i].date,
				(messages[i].likes?messages[i].likes:new Array()),
				CommentstraiteReponseJson(messages[i].comments),
				0
			);
		}
	}
}
RechercheMessages.prototype.getHTML = function (){
	var rt = ''; 
	for (var i in environnement.messages)
		rt = environnement.messages[i].getHTML() + rt;
	return rt;
}


function getIDKeyFromIDMessage(id_message){
    var messages = environnement.messages;
    for (var i in messages) {
    	if(messages[i].id==id_message){
    		return i;
    	}
    }
    return 0;
}
