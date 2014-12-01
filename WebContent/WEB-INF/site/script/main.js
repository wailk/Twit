function main(){
	environnement= new Object();
	environnement.user=[];

}

function User(id, login, password){
	this.id=id;
	this.login=login;
	this.contact=contact;
	if(contact != undefined){ 
		this.contact=contact;
	}
	if(environnement == undefined){
		environnement= new Object();
	}
	if (environnement == undefined){
		environnement.user=[];
	}
	environnement.user[this.id]=this;

}

User.prototype.modifStatus=function(){
	this.contact=!this.contact;
}

function Commentaire(id, auteur, texte, date, score){
	this.id=id;
	this.auteur=auteur;
	this.texte=texte;
	this.date=date;
	this.score=score;
}

Commentaire.prototype.gethtml=function(){
	var s="\t < div id\"Commentaire_"+this.id+ "\"class=\"Comment\">\n";
	s+="\t\t <div class=\"text_comment\">"+this.text+"</div>\n";

	if(this.auteur.contact){
		s+="\t <img class=\image_" + this.auteur.id+"\" src=\"Image/image.jpg\"title=\AjoutContact"
		onClick="\"javascript:ajout_contact("+this.auteur.id+")\"/>\n";
		return s;
	}
}


function RechercheCommantaire(resultats, recherche, contact_only, auteur, date){
	this.resultat=resultat;
	this.auteur=auteur;
	this.recherche=recherche;
	if (recherche== undefined){
		this.recherche="";
	}
	this.date=date;
	if(date == undefined){
		this.date= new Date();
	}
	environement.recherche=this;
}


RechercheCommentaire.prototype.gethtml=function(){
	var s="<div id=\"comments\">\n";
	for(var i=0; i<this.Resultats.length; i++){
		s+= this.resultats[i].gethtml()+"\n";
	}
	s+="</div>";
	return s;
}

RechercheCommentaire.revival=function revival(key,value){
	if (key.length == 0){
		var r;
		if((value.erreur== undefined)||(value.erreur== 0)){
			r=new RechercheCommentaire(value.resulatats, value.recherche, value.date, value.auteur);
		}
		else{
			r=new Object();
			r.erreur= value.erreur;
		}
		return r;
	}
	elseif(value.auteur instanceof User){
		var c = new Commentaire(value.date, value.score);
		return c;
		elseif (key== 'auteur'){
			var u;
			if((environnement !=undefined)&&(environnement.users[value.id]!= undefined)){
				u=environnement.users[value.id];
			}
			else{
				u=new User(value.id, value.login, value.contact);
			}
			return u;

		}

	}
}


RechercheCommentaire.traiteJSON= function(JSONtext){
	var obj=JSON.parse(JSONtext,RechercheCommentaire.revival);
	if (obj.erreur == undefined){
		alert(obj.gethtml());	
	}
	else{
		alert(obj.erreur);
	}
}


//fonction rajoutées le 21 mars


function gererDivconnex(){

	var user=environnement.actif;
	if((user != undefined)&&(user.login != "")){
		$("#connrlog").html("<span id =\"log\>"+user.login+"</span>");
		$("#log").css("margin_right", "10 px");
		$("#disconnect").css("visibility","visible");
	}
	else
	{
		$("#connrlog").html("<a href = \"connexion.html\" id= \"connexion_link \"> Connexion<\a> / <a href = \"enregistrement.html \" id = \"enregistrement \">Enregistrement <\a>");

				$("#disconnect").css("visibility","hidden");
	}


	RechercheCommentaires.traiteReponseJson=(json.text){
		var old_users = environnement.users;
		environnement.users = [];

		var obj = JSON.parse(json.text,RechercheCommentaire.Revival);

		if(obj.erreur==undefined){
			$("#main").html(obj.gethtml());
		}

		else{
			environnement.users =old.users;

			if(obj.erreur==1){alert("Probleme serveur");}

			else{
				alert("Probleme DB");
			}
		}
	}


	function disconnect(){

		environnement_actif=undefined;
		gererDivConnexion();
		var json_text=envoieCommentaires();
		RechercheCommentaires.traiteReponseJSON(json_text);
	}


	function func_filtre() {
		alert("Filtre :" +$("#box-friends").get(0).checked());

		var json_text=envoieCommentaires();
		RechercheCommentaires.traiteReponseJSON(json_text);
	}

	function func_search(){
		var rech= $("#requete").val();
		var json_text = envoieCommentaires(rech);
		RechercheCommentaires.traiteReponseJSON(json_text);
	}


	function func_new_comment() {

		alert("Nouveau Commentaire :"+$("textarea").val());
		var json_text=envoieCommentaires();
		RechercheCommentaires.traiteReponseJson(json_text);

	}

	function traiteReponseAjoutSupContext(reponse,id){

		if((reponse.erreur==undefined)||(reponse.erreur==0) && (environnement.user[id] != undefined)){

			var user = environnement.user[id];
			user.modifStatus();

			var Comments = environnement.Recherche.resultats;

			for (var i in Comments){

				var comment=Comments[i];
				var id_comment= Comment_id;

				$("#Commentaire"+id_comment).ReplaceWith(Comment.gethtml());
			}
		}
	}

}
/*function connection(formulaire)
{
   var login=formulaire.login.vales*/