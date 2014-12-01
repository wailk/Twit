//***********************************************************************************************************************//
//********************************************Fonctions exemplaire de retour*********************************************//
//***********************************************************************************************************************//

function RepDataJSONUsers() {
	return '[{ "id" : 1 , "login" : "Mathieusub" , "contact" : false}, { "id" : 2 , "login" : "Stevenmad" , "contact" : true}, { "id" : 3 , "login" : "tintin" , "contact" : false}]';
}

function RepDataJSONMessages() {
	return '[{ "_id" : { "$oid" : "513078403c3ec0a3b1fe5c65"} , "id_user" : 1 , "name_user" : "Mathieusub" , "message" : "youpi" , "date" : "Fri Mar 01 10:43:28 CET 2013" , "likes" : [ { "id_user" : "1"},{ "id_user" : "2"} ]},{ "_id" : { "$oid" : "513078203c3e732e71396fd5"} , "date" : "Fri Mar 01 10:42:56 CET 2013" , "id_user" : 2 , "likes" : [ ] , "message" : "Bienvenue a toi aussi" , "name_user" : "Stevenmad"},{ "_id" : { "$oid" : "51475ab73c3ece8d5fbe846a"} , "id_user" : 1 , "name_user" : "Mathieusub" , "message" : "tt" , "date" : "Mon Mar 18 19:19:34 CET 2013"},{ "_id" : { "$oid" : "51475abf3c3e1016471661f7"} , "id_user" : 1 , "name_user" : "Mathieusub" , "message" : "tt" , "date" : "Mon Mar 18 19:19:42 CET 2013"}]';
	//return '[{ "_id" : { "$oid" : "513078403c3ec0a3b1fe5c65"} , "id_user" : 1 , "name_user" : "Mathieusub" , "message" : "youpi" , "date" : "Fri Mar 01 10:43:28 CET 2013" , "likes" : [ { "id" : "1"},{ "id" : "2"} ] }]';
}

function RepDataJSONCommentaires(id_message) {
	if (id_message=="513078403c3ec0a3b1fe5c65") {
		return '[{ "_id" : { "$oid" : "512765f03c3ebd9e92293bcc"} , "id_message" : "513078403c3ec0a3b1fe5c65" , "id_user" : 1 , "name_user" : "Mathieusub" , "message" : "rep2" , "date" : { "$date" : "2013-02-22T12:34:56.251Z"}}]';
	} else if (id_message=="51475abf3c3e1016471661f7") {
		return '[{ "_id" : { "$oid" : "512765f03dfgdfgdfdqsdgcc"} , "id_message" : "51475abf3c3e1016471661f7" , "id_user" : 1 , "name_user" : "Mathieusub" , "message" : "rettep2" , "date" : { "$date" : "2013-02-22T12:34:56.251Z"}}]';
	} else {
		return '[]';
	}
}