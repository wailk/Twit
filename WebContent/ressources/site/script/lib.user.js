/*User object*/
function user(id,login,password){
	this.id=id;
	this.login=login;
	this.contact= false;
	if(contact != undefined){ 
		this.contact=contact;
	}
	if(environnement==undefined){
		environnement = new Object();
	}
	if(environnement.users==undefined){
		environnement.users=[];
	}
	environnement.users[id]=this;
}
user.prototype.modifistatus=function(){
	this.contact=!this.contact;
}


