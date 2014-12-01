/**
 * Created by Wail on 09/04/2014.
 */

function show_register(){

	$("#login").hide();
	$("#register").show();

}

function show_login(){

	$("#login").show();
	$("#register").hide();

}

function displayMessages(messages, user){
    var out = '';    
    for(var i = 0; i < messages.length; i++){
        out += '<span class="message">';
        out += '<span class="username">' + user + '</span>';
        out += '<span class="date">' + moment(messages[i].date).fromNow() + '</span>';
        out += '<span class="text">' + messages[i].message + '</span>';
        //out += '<span class="like"><a onclick="like(' + messages[i].message._id +', window.user_id);">' + Like + '</a></span>';
        out += "</span>\n";
    }
    return out;
}

function loadMessages(user_id,user){

    $.ajax({

        url:"ListMessages",
        type:'GET',
        data:"uid="+user_id,
        dataType:'json',
        success:function(result){

            $("#messages").html(displayMessages(result.messages, user));

        },
        error:function(){alert("Error: cannot load messages! ");}
    });
}


function addMessage(user,user_key,message){
	$.ajax({
        url:"AddComment",
        type:'GET',
        data:{ key: user_key, text: message+'' },
        success:function(){
            loadMessages(window.user_id, user);
        },
        error:function(){alert("Error: cannot add message! ");}
    });
	
	
}



function isValidLogin(form){
	var username = form.username.value;
	var pass = form.password.value;;
	$.ajax({
        url:"Login",
        type:'GET',
        dataType:'json',
        data:{ login: ''+username, password: ''+pass },
        success:function(result){
			if(result.output == "OK") {
				window.user = username;
				$("#login").hide();
				$("#wrapper").show();
				$("#current_user").html(username);
				loadMessages(username);
			
			} else {
				alert("Mauvais login/motdepasse, Reessayer?");
			}
        },
        error:function(){ alert("error request");}
    });

}

function isValidLogin(form){
	var username = form.username.value;
	var pass = form.password.value;;
	$.ajax({
        url:"Login",
        type:'GET',
        dataType:'json',
        data:{ login: ''+username, password: ''+pass },
        success:function(result){
			if(result.output == "OK") {
				
				window.user = username;
				window.key = result.key;
				window.user_id = result.id;
				
				$("#login").hide();
				$("#wrapper").show();
				$("#current_user").html(username);
				loadMessages(window.user_id, username);
			
			} else {
				alert("Mauvais login/motdepasse, Reessayer?");
			}
        },
        error:function(){ alert("error request");}
    });

}





