package services.authentification;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoException;

import db.tools.AuthentificationTools;
import db.tools.MongoDB;
import services.ServicesTools;

public class Comments {
	public static JSONObject addComment(String key, String text) throws SQLException, UnknownHostException, MongoException, JSONException{
		if(key == null || text == null)
			return ServicesTools.error(-1);
		else{
			try {
				MongoDB.addComment(key,text);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (MongoException e1) {
				ServicesTools.error(500);
			} catch (JSONException e1) {
				ServicesTools.error(10);
			}
			JSONObject json = new JSONObject();
			try{
				json.put("output", 1);
			}catch(JSONException e){
				return ServicesTools.error(10);
			}
			//MongoDB.affComment(login);
			return json;
		}
	}

	public static JSONObject search(String key, String query, String date) throws JSONException, UnknownHostException, MongoException{
		int id =AuthentificationTools.getIdUserKey(key);
		JSONObject commentaires = null;
		JSONObject json = new JSONObject();
		try{
			if ((query=="")&&(date=="")){
				commentaires = MongoDB.searchTweetByLogin(id);
			}
			else if ((date=="")&&(key=="")){
				commentaires = MongoDB.searchTweetByWord(query);
			}
			else if (query==""){
				commentaires = MongoDB.searchTweetByDate(id, date);
			}
			
			json.put("Commentaire: ", commentaires);
		}catch(JSONException e){
			return ServicesTools.error(10);
		}
		return json;
	}
	
	public static JSONObject listMessages(String id,String username, String nbMessage, String offset, String last) throws InstantiationException, IllegalAccessException, ClassNotFoundException, UnknownHostException, MongoException, JSONException {
		//default
		int off = 0;
		int nb = 100;
		int user = -1;
			
		if(offset != null && offset != "")
			off = Integer.parseInt(offset);
		
		if(nbMessage != null && nbMessage!="")
			nb = Integer.parseInt(nbMessage);

		
		if(id != null && id != ""){
			user = Integer.parseInt(id);
		}
			
		//if(username != null && username != "" && user == -1)
			//user = UserTools.userExists(username);
			
		return MongoDB.listMessages(user,nb,off,last);
		//return null;
		
	}
	
	public static JSONObject like(String comment_id, String liker_id) throws SQLException, UnknownHostException, MongoException, JSONException {
		if(comment_id == null || liker_id == null)
			return ServicesTools.error(-1);
		else{
			try {
				MongoDB.likeComment(comment_id,liker_id);
			} catch (MongoException e1) {
				ServicesTools.error(500);
			} catch (JSONException e1) {
				ServicesTools.error(10);
			}
			JSONObject json = new JSONObject();
			try{
				json.put("output", 1);
			}catch(JSONException e){
				return ServicesTools.error(10);
			}
			
			return json;
		}
	}
	
	
	
	
	
}
