package services.authentification;


import java.sql.SQLException;
import org.json.JSONObject;
import org.json.JSONException;
import db.tools.AuthentificationTools;

import services.ServicesTools;


public class Users {
	/**
	 * Creation d'un nouvel utilisateur:
	 * 1) Verification des parametres; doivent etre non nuls
	 * 2) Verification que l'utilisateur n'esxiste pas
	 * 3) Ajout de l'utilisateur dans la base de donnees
	 * Renvoie du message de confirmation
	 * @param login 
	 * @param password
	 * @param nom
	 * @param prenom
	 * @return
	 * @throws JSONException
	 * @throws SQLException
	 * @throws org.json.JSONException
	 */
	public static JSONObject createUser( String nom, String prenom,String login, String password) throws SQLException{
		boolean is_user = false;
		try {
			is_user = AuthentificationTools.userExist(login);
		} catch (JSONException e) {
			ServicesTools.error(10);
		}

		if((login == null) || (password == null) || (nom == null) || (prenom == null))
			ServicesTools.error(-1);
		if (is_user)
			ServicesTools.error(0);
		else{
			AuthentificationTools.insertUser(login, password, nom, prenom);
			JSONObject json = new JSONObject();
			try {
				json.put("output", 1);
			} catch (JSONException e) {
				ServicesTools.error(10);
			}
			return json;
		}
		return null;	
	}

	/**
	 * Suppression d'un utilisateur:
	 * 1) Verification des parametres doivent etre non nuls
	 * 2) Verification que l'utilisateur esxiste
	 * 3) Suppression de l'utilisateur de la base de donnees
	 * Renvoie du message de confirmation
	 * @param login 
	 * @param password
	 * @return
	 * @throws JSONException
	 * @throws SQLException
	 * @throws org.json.JSONException
	 */
	public static JSONObject deleteUser(String login, String password) throws SQLException{
		boolean is_user = true;
		try {
			is_user = AuthentificationTools.userExist(login);
		} catch (JSONException e) {
			ServicesTools.error(10);
		}

		if((login == null) || (password == null))
			ServicesTools.error(-1);
		if (!is_user)
			ServicesTools.error(0);
		else{
			int id = AuthentificationTools.getIdUser(login);
			AuthentificationTools.removeUser(id, password);
			JSONObject json = new JSONObject();
			try {
				json.put("output", "OK, supprimé");
			} catch (JSONException e) {
				ServicesTools.error(10);
			}
			return json;
		}
		return null;	
	}
		
	
	/**
	 * Connection d'un utilisateur:
	 * 1) Verification des parametres; doivent etre non muls
	 * 2) Verification que l'utilisateur existe
	 * 3) Verification du password
	 * 4) Insertion d'une nouvelle session via la clé
	 * Renvoie la clé
	 * @param login
	 * @param password
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject login(String login, String password) throws SQLException {
		// 1)
		if((login == null) || (password == null) )
			return(ServicesTools.error(-1));

		JSONObject json = new JSONObject();

		try{
			// 2)
			boolean is_user = AuthentificationTools.userExist(login);
			if (!is_user)
				return(ServicesTools.error(0));


			// 3)
			boolean password_ok = AuthentificationTools.checkPassword(login,password); 
			if (!password_ok)
				return(ServicesTools.error(2));


			// Recuperation de l'id de l'utilisateur pour pouvoir inserer une nouvelle session
			int id_user = AuthentificationTools.getIdUser(login);
			if(id_user==-1)
				return ServicesTools.error(100000);
			
			
			// 4) on insere la session
			//String key = ServicesTools.generatorKey();
			String key = AuthentificationTools.insertSession(id_user,false);
			
			
			json.put("output", "OK");
			json.put("login", login);
			json.put("id", id_user);
			json.put("key",key);
			return(json);
		}
		catch (Exception e) {
			return(ServicesTools.error(10000));
		}
	}

	/**
	 * Deconnecte un utilisateur
	 * @param key
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject logout(String key_user){
		JSONObject j = new JSONObject();
		if(key_user == null || key_user == ""){
			try {
				j.put("output", "erreur");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			ServicesTools.error(-1);
			return j;
		}
		else{
			JSONObject json = new JSONObject();
			try {
				int sup = AuthentificationTools.supprSession(key_user);
				
				json.put("suppression", sup);
				json.put ("output" ,"OK" );
			} catch ( JSONException e ) {
				ServicesTools.error(10);
			}
			return json;
		}
	}

	/**
	 * Ajoute un ami
	 * @param key
	 * @param id_friend
	 */
	public static JSONObject addFriend(String key_user, String id_friend){
		if(key_user == null || id_friend == null)
			ServicesTools.error(-1);
		else{
			JSONObject json = new JSONObject();
			try{
				AuthentificationTools.addFriend(key_user, id_friend);
				json.put("Output", "Friend added");
			}catch(JSONException e){
				ServicesTools.error(10);
			}
			return json;
		}
		return null;
	}
	
	public static JSONObject removeFriend(String key_user, String id_friend){
		if(key_user == null || id_friend == null)
			ServicesTools.error(-1);
		else{
			JSONObject json = new JSONObject();
			try{
				AuthentificationTools.removeFriend(key_user, id_friend);
				json.put("Output", "Friend deleted");
			}catch(JSONException e){
				ServicesTools.error(10);
			}
			return json;
		}
		return null;
	}
}
