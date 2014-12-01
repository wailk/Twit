/*Ajoute ou supprime une amitiée*/
function addDelFriend(id) {
    var user = environnement.users[id];
    var url = environnement.urlBaseServlet+"AddFriend";
    if (user.contact) {
        url = environnement.urlBaseServlet+"RemoveFriend";
    }
    $.ajax({
        type : "GET",
        url : url,
        data : "key="+environnement.key+"&id_friend="+id,
        dataType : "json",
        success : function(rep){ traiteRepAddDellFriend(rep,id) },
        error : function (xhr,textstatus,errorthrow){
            func_erreur("#main","msg_main",xhr+" "+textstatus+" "+errorthrow);
        }
    });
}
function traiteRepAddDellFriend(rep,id) {
    if (rep.error_code!=undefined) {
        func_erreur("#main","msg_main",rep.message);
    }
    var user = environnement.users[id];
    user.modifistatus();
    var messages = environnement.messages;
    for (var i in messages) {
        if (messages[i].user.id == id){
            $("#message_"+messages[i].id).replaceWith(messages[i].getHTML());
        }
    }
}