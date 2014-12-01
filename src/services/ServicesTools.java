package services;

import java.math.BigInteger;
import java.util.Random;
//import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;


public class ServicesTools {
	
	private static final Random random = new Random();
	
	/**
	 * Pour un http_code donne en parametre, un message specifique est retourne
	 * @param http_code
	 * @return
	 */
	public static JSONObject ok(int http_code){
		JSONObject json = new JSONObject();
		String msg = "";
		try{
			if(http_code == 200){
				msg = "OK";
				json.put("Requete traitee avec succes: ", msg);
			}
			else if(http_code == 201){
				msg = "Created";
				json.put("Requete traitee avec succes avec creation d'un document: ", msg);
			}
			else if(http_code == 202){
				msg = "Accepted";
				json.put("Requete traitee mais sans garantie de resultat: ", msg);
			}
			else if(http_code == 203){
				msg = "Non-Authoritative Information";
				json.put("Information retournee mais generee par une source non certifiee: ", msg);
			}
			else if(http_code == 204){
				msg = "No Content";
				json.put("Requete traitee avec succes mais pas d'information a renvoyer: ", msg);
			}
			else if(http_code == 205){
				msg = "Reset Content";
				json.put("Requete traitee avec succes, la page courante peut etre effacee: ", msg);
			}
			else if(http_code == 206){
				msg = "Partial Content";
				json.put("Une partie seulement de la requete a ete transmise: ", msg);
			}
			else if(http_code == 207){
				msg = "Multi-Status";
				json.put("WebDAV : Reponse multiple: ", msg);
			}
			else if(http_code == 210){
				msg = "Content Different";
				json.put("WebDAV : La copie de la ressource cote client differe de celle du serveur (contenu ou proprietes): ", msg);
			}
			else if(http_code == 226){
				msg = "IM Used	RFC 3229";
				json.put("?: ", msg);
			}
		}catch(JSONException e){ 
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Pour un code_error donne en parametre, un message specifique est retourne
	 * @param code_error
	 * @return
	 */
	public static JSONObject error(int code_error){

		JSONObject json = new JSONObject();
		String msg = "";
		try{
			if(code_error == -1){
				msg = "Erreur arguments URL";
				json.put("Error: ", msg);
			}
			else if(code_error == 0){
				msg = "Utilisateur inconnu";
				json.put("Error: ", msg);
			}
			else if(code_error == 2){
				msg = "Mot de passe inconnu";
				json.put("Error: ", msg);
			}
			else if(code_error == 3){
				msg = "Key inconnue";
				json.put("Error: ", msg);
			}
			else if(code_error == 4){
				msg = "Utilisateur deja existant";
				json.put("Error: ", msg);
			}
			else if(code_error == 10){
				msg = "Erreur JSON";
				json.put("Error: ", msg);
			}
			else if(code_error == 100){
				msg = "Erreur SQL";
				json.put("Error: ", msg);
			}
			else if(code_error == 1000){
				msg = "Erreur Java";
				json.put("Error: ", msg);
			}
			else if(code_error == 500){
				msg = "Erreur Mongo";
				json.put("Error: ", msg);
			}
			else if(code_error == 10000){
				msg = "Erreur";
				json.put("Error: ", msg);
			}
			else if(code_error == 100000){
				msg = "Session introuvable";
				json.put("Error: ", msg);
			}
		}catch(JSONException e){ 
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * @param p poids de l'entier (en octets)
	 * @param n nombre de caract�res � utiliser (entre 2 et 36)
	 * @return une cha�ne de caract�res repr�sentant en base 'n' un entier al�atoire de 'p' octets 
	 */
	public static String randomString(int p, int n)
	{
		byte[] bytes = new byte[p];
		random.nextBytes(bytes);
		BigInteger big = new BigInteger(bytes).abs();
		return big.toString(n);
	}

	/**
	 * @param length taille de la chaine de caract�res
	 * @return une cha�ne de caract�res de taille 'length' repr�sent�es avec tous les chiffres et lettres.
	 */
	public static String randomString(int length)
	{
		String str = randomString(length,36);
		return str.substring(str.length()-length);
	}
	/**
	 * Genere une cle 
	 * !!!!!!!!!!!!!!!!a revoir cf main
	 * @return
	 */
	public static String generatorKey(){
		String s = randomString(32);
		return s;
//		UUID u =  new UUID(0,31);
//		u = u.randomUUID();
//		return u.toString();
	}
}
