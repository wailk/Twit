var environnement;
environnement = new Object();
environnement.users = new Array();
environnement.messages = new Array();
environnement.comments = new Array();
environnement.urlBaseServlet = "http://132.227.201.129:33306/khecha_djamah/"   //http://li328.lip6.fr:8280/khecha_djamah

$(function(){
	$("#disconnect a").click(disconnect);
	$("#disconnect").css({display: "none"});
});

function main(){
	gererDirConnexion();
	loadMessages("");
}

function mainbis(id,login,key){
	if(id!=undefined && login!=undefined && key!=undefined){
		environnement.actif = new user(id,login,false);
		environnement.key = key;
	}
	gererDirConnexion();
	loadMessages(key);
}

function gererDirConnexion(){
	if(environnement.actif==undefined){
		$("#connect").css({display: "block"});
		$("#disconnect").css({display: "none"});
		$("#friend_filtre input").prop('disabled', true);
	} else {
		$("#connect").css({display: "none"});
		$("#disconnect").css({display: "block"});
	}
}

function func_erreur(formulaire,id_bloc_msg,msg){
	var msg_box = '<div id="'+id_bloc_msg+'" class="bloc_msg_erreur" >'+msg+"</div>";
	var tab = $("#"+id_bloc_msg);
	if(tab.length==0){
		$(formulaire).prepend(msg_box);
		$("#"+id_bloc_msg).slideDown("slow");
	} else {
		$("#"+id_bloc_msg).slideUp("slow", function() {
			tab.replaceWith(msg_box);
			$("#"+id_bloc_msg).slideDown("slow");
		});
	}
}

function func_succes(formulaire,id_bloc_msg,msg){
	var msg_box = '<div id="'+id_bloc_msg+'" class="bloc_msg_succes" >'+msg+"</div>";
	var tab = $("#"+id_bloc_msg);
	if(tab.length==0){
		$(formulaire).prepend(msg_box);
		$("#"+id_bloc_msg).slideDown("slow").delay(3000).slideUp("slow");
	} else {
		$("#"+id_bloc_msg).slideUp("slow", function() {
			tab.replaceWith(msg_box);
			$("#"+id_bloc_msg).slideDown("slow").delay(3000).slideUp("slow");
		});
	}
}

function func_load(formulaire,id_bloc_msg,msg){
	var msg_box = '<div id="'+id_bloc_msg+'" class="bloc_msg_load" >'+msg+"</div>";
	var tab = $("#"+id_bloc_msg);
	if(tab.length==0){
		$(formulaire).prepend(msg_box);
		$("#"+id_bloc_msg).slideDown("slow").slideUp("slow");
	} else {
		$("#"+id_bloc_msg).slideUp("slow", function() {
			tab.replaceWith(msg_box);
			$("#"+id_bloc_msg).slideDown("slow").slideUp("slow");
		});
	}
}
