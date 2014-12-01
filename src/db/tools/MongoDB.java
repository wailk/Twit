package db.tools;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import db.tools.DBStatic;
import exceptions.EndOfSessionException;
import exceptions.Finish;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class MongoDB {
	public static JSONObject addComment(String key, String text) throws UnknownHostException, MongoException, JSONException, SQLException{
		//int id_user= AuthentificationTools.getIdUserKey(key);
		JSONObject json = new JSONObject();
			Mongo m = new Mongo(DBStatic.mongoDb_host,DBStatic.mongoDb_port);
			DB db = m.getDB("khecha_djamah_bis");
			DBCollection collection = db.getCollection("comments");
			BasicDBObject obj = new BasicDBObject();
			
			double[] likers_ids = {};
			
			obj.put("id",Integer.parseInt(key));
			obj.put("message",text);
			obj.put("date",new Date().getTime());
			obj.put("likers",likers_ids);
			
			collection.insert(obj);
			
			json.put("message", text);
			json.put("message_id",obj.get("_id").toString());
			m.close();
			return json;
	}
	
	public static JSONObject deleteMessage(String tweet_id,int user_id) throws JSONException {
		try{
			JSONObject res = new JSONObject();
			Mongo m = new Mongo("132.227.201.129",27130);
			DB db = m.getDB("khecha_djamah_bis");
			DBCollection collection = db.getCollection("comments");
			BasicDBObject query = new BasicDBObject();
			
			query.put("_id", new ObjectId(tweet_id));
			query.put("id",user_id);	
			WriteResult wr = collection.remove(query);
			
			if(wr.getN() == 1){
				m.close();
				res.put("message","ok");
				return res;
			}else{
				m.close();
				
			}
			
		}catch (UnknownHostException e) {
			
		}
		return null;
		
	}
	
	
	public static JSONObject likeComment(String comment_id, String liker_id) throws JSONException {
		try{
			JSONObject res = new JSONObject();
			Mongo m = new Mongo("132.227.201.129",27130);
			DB db = m.getDB("khecha_djamah_bis");
			DBCollection collection = db.getCollection("comments");
			BasicDBObject query = new BasicDBObject();
			
			
			// Check if already liked
			
			//DBCursor cursor = collection.find(new BasicDBObject("_id",new ObjectId(comment_id)));
			
			//cursor.
			
			
			
			query.put("$push", new BasicDBObject().put("likers", Long.parseLong(liker_id)));
			
			WriteResult wr = collection.update(new BasicDBObject("_id",new ObjectId(comment_id)), query);
			
			if(wr.getN() == 1){
				m.close();
				res.put("message","ok");
				return res;
			}else{
				m.close();
				
			}
			
		}catch (UnknownHostException e) {
			
		}
		return null;
		
	}
	
	
	public static void affComment(String key) throws UnknownHostException, MongoException, JSONException, SQLException{
		Mongo m = new Mongo("132.227.201.129",27130);
		DB db = m.getDB("khecha_djamah_bis");
		DBCollection c = db.getCollection("comments");
		BasicDBObject query = new BasicDBObject();
		query.put("author-id",AuthentificationTools.getIdUserKey(key) );
		DBCursor curs = c.find(query);
		while(curs.hasNext()){
			DBObject obj = curs.next();
			System.out.println(obj);
		}
	}
	
	public void affCommentDec(String login, String key) throws UnknownHostException, MongoException, JSONException, SQLException{
		Mongo m = new Mongo("132.227.201.129",27130);
		DB db = m.getDB("khecha_djamah_bis");
		DBCollection c = db.getCollection("comments");
		BasicDBObject query = new BasicDBObject();
		GregorianCalendar cal = new java.util.GregorianCalendar();
		cal.add(Calendar.HOUR,-1);
		Date d = cal.getTime();
		query.put("date", d);
		query.put("author-id", AuthentificationTools.getIdUser(login));
		DBCursor curs = c.find(query);
		while(curs.hasNext()){
			DBObject obj = curs.next();
			System.out.println(obj);
		}
	}
	
	public void affCommentUsers(String login1,String login2) throws UnknownHostException, MongoException, JSONException, SQLException{
		Mongo m = new Mongo("132.227.201.129",27130);
		DB db = m.getDB("khecha_djamah_bis");
		DBCollection c = db.getCollection("comments");
		BasicDBObject query1 = new BasicDBObject();
		BasicDBObject query2 = new BasicDBObject();
		query1.put("author_id",AuthentificationTools.getIdUser(login1));
		query2.put("author_id",AuthentificationTools.getIdUser(login2));
		List <BasicDBObject> query12 = new ArrayList <BasicDBObject>();
		query12.add(query1);
		query12.add(query2);
		BasicDBObject query = new BasicDBObject("$or",query12);
	}
	
	
	//public static JSONObject search(String key) {
		public static JSONObject search(String key, String query, String friend) {
			//friends = 1 si cochée, sinon 0
			JSONObject json = new JSONObject();
			Statement statement=null;
			Connection connexion=null;
			String result="";
			int id = -1;
			int ami = -1;
			/*if (friend=="true")	
				ami=1;
			else ami = 0;*/
			////
			try {
				//connection Ë SQL si la case amis est cochÅ½e 
				if(ami==1){
					Class.forName("com.mysql.jdbc.driver");
					connexion = (Connection) Database.getMySQLConnection();
					statement = (Statement) connexion.createStatement();	
				}
				
				Mongo mongo = new Mongo("132.227.201.129",27130);
				DB db = mongo.getDB("khecha_djamah_bis");
				DBCollection collection = db.getCollection("comments");
				BasicDBObject doc = new BasicDBObject();
				DBObject o = null;
				//si on n'est pas connecté on affiche les derniers commentaires
				/*	 search sans paramÅ½tres: affiche les 10 derniers commentaires,
				 *  utilisÅ½ lors du chargement de la page 
				 *  et donc lorsque personne n'est connectÅ½
				 */
				if ((key=="")&&(query=="")){
					DBCursor curseur =collection.find(doc);
//					DBCursor curseur =collection.find(new BasicDBObject("id",id));
					//DBCursor curseur =collection.find(doc);
					int nb_com=10;
					while ((curseur.hasNext())&&(nb_com>0)){
						o=curseur.next();
						//json.put("id ", curseur.toString());}
						//result+=curseur.next();
						o.get("comment");
						result+=o.toString()+o.get("auteur").toString()+o.get("date").toString();
						nb_com--;
						json.put("", result);
					}
					curseur.close();
					return json; //return result;
				}
				
				//rÅ½cupÅ½ration de l'id du connectÅ½
				id =AuthentificationTools.getIdUserKey(key);
				if (id<0){
					throw new EndOfSessionException("Session terminée");//return ("user inconnu");
				}
				if((query=="")||(query==null)){		// recherche sans mots clÅ½s 

					if (ami!=1){			//recherche sans tri par ami et sans mots clÅ½s 

						doc = new BasicDBObject();
						doc.put("auteur", id);		//tous les commentaires de id
						DBCursor curseur = collection.find(doc);
						while (curseur.hasNext()){
							o = curseur.next();
							o.get("comment");	//les commentaires de id_user
							result+=o.toString()+"ecrit par "+o.get("id").toString()+"Ë la date "+o.get("date").toString();
							json.put("", result);
						}
						return json;//throw new Finish("1");//return result;

					}else{				//recherche par tri d'amis et sans mots clÅ½s

						//connection Ë la base SQL et rÅ½cupÅ½ration de la liste des amis de l'utilisateur
						ResultSet listAmi = statement.executeQuery("SELECT * FROM Friends WHERE id_from="+id);
						if (!listAmi.next()){
							throw new Finish("0");//return ("pas d'ami");
						}
						else{
							statement.close();
							connexion.close();
						}	

						//recherche des comentaires avec la liste d'amis 
						doc = new BasicDBObject();
						ArrayList<Integer> list = new ArrayList<Integer>();		
						while (listAmi.next())
							list.add(new Integer(listAmi.toString()).intValue());
						doc.put("id", new BasicDBObject("$in",list));	//trie les commentaires par amis 

						DBCursor curseur = collection.find(doc);
						while (curseur.hasNext()){
							o = curseur.next();
							o.get("comment");		//selection des commentaires des amis  
							result+=o.toString()+o.get("id").toString()+o.get("date").toString();
							json.put("", result);
						}
						return json;
					}
				}/*else{		// recherche avec mots clÅ½s

					if (ami!=1){	//recherche par mots clÅ½s et sans tri d'amis 
						//String requete1 = "SELECT doc_id, word, tf FROM tf WHERE ";
						String requete2 = "SELECT a.word, a.doc_id, a.tf, b.df FROM tf a, df b WHERE (";

						//String where_req1 ="";

						String where_req2 ="";
						String where_or_req2 ="";
						String where_and_req2 =") AND (a.word = b.word)";

						String t;
						for (int i = 0; i<mots_recherche.size();i++){
							t = mots_recherche.get(i);
							if (i == mots_recherche.size()-1){//Si on analyse le dernier mot de la recherche
								//where_req1 += "word="+t;
								where_or_req2 += "a.word="+t;
							}
							else{
								//where_req1 += "word="+t+" OR ";
								where_or_req2 += "a.word="+t+" OR ";
							}*/				
			}catch (Exception e){
				e.printStackTrace();
			}
			return json;
			
		}
				
				/*while(current.hasNext()){
				current.next();
				json.put(i+"", current.toString());////
				
				id = AuthentificationTools.getIdUserKey(key);
				if (id<0){		//on crée une erreur pour sortir directement
					 return null;
				}
					
				doc.put("auteur", id);		//tous les commentaires de id
				
				if((com=="")||(com==null)){		// recherche sans mots clés
					
					if (ami==0){			//recherche sans tri par ami et sans mots clés 
						DBCursor curseur1 = collection.find(doc);
						while (curseur.hasNext())
							result+=curseur.next();
						int i=0;
						while (curseur1.hasNext()){
							curseur1.next();
							json.put(i+"lsjfb", curseur1.toString());}
							//result+=curseur.next();
						curseur1.close();
						return json;	
						
					}else{				//recherche par tri d'amis et sans mots clés
						
					//connection à la base SQL et récupération de la liste des amis de l'utilisateur
						Class.forName("com.mysql.jdbc.driver");
						Connection connexion = (Connection) Database.getMySQLConnection();
						Statement statement = (Statement) connexion.createStatement();	
						ResultSet listAmi = statement.executeQuery("SELECT * FROM friends WHERE id_user='"+id+"'");
						if (!listAmi.next()){
							return null;//("Liste d'amis introuvable");
						}
						else{
							statement.close();
							connexion.close();
						}	//liste d'amis obtenue 
						
					//recherche des comentaires avec la liste d'amis 
						doc = new BasicDBObject();
						ArrayList<Integer> list = new ArrayList<Integer>();		
						while (listAmi.next())
							list.add(new Integer(listAmi.toString()).intValue());
						doc.put("auteur", new BasicDBObject("$in",list));	//trie les commentaires par amis 
					
						DBCursor curseur = collection.find(doc);
						while (curseur.hasNext())
							result+=curseur.next();
						int i=0;
						while (curseur.hasNext()){
							curseur.next();
							json.put(i+"", curseur.toString());}
							//result+=curseur.next();
						curseur.close();
						return json;	
					}}////
					
					
				
							
			}catch (Exception e){
				e.printStackTrace();
			}					
			return json;		// la BD NOSQL retourne le commentaire au type JSON.
			}*/
	
	/*public static JSONObject search(String id) throws JSONException
	{
		try
		{
			JSONObject json = new JSONObject();
			Mongo m = new Mongo("132.227.201.129",27130);
			DB db = m.getDB("khecha_djamah_bis");
			DBCollection c = db.getCollection("comments");
			BasicDBObject query = new BasicDBObject();
			query.put("id", id);
			DBCursor current = c.find(query);
			

			String result="";
			int i = 0;
			while(current.hasNext())
			{
				current.next();
				json.put(i+"", current.toString());
				//
			}
			current.close();
			return json;
		}
		catch(Exception e)
		{
			//ServicesTools.error("Unkown "+e.toString(), 0);
		}
		
		
		return null;
		
	}*/
	
	public static JSONObject listMessages(int id, int nb, int off,String last) throws JSONException, UnknownHostException, MongoException{
					
			JSONObject json = new JSONObject();
			Mongo m = new Mongo("132.227.201.129",27130);
			DB db = m.getDB("khecha_djamah_bis");
			DBCollection collection = db.getCollection("comments");	
			
			DBCursor cursor = collection.find(new BasicDBObject("id",id)).sort(new BasicDBObject("date",-1)).limit(nb).skip(off); 
			
			while(cursor.hasNext()){
				DBObject next = cursor.next();
				
				if(last != null && last != "" && next.get("_id").toString().equals(last))
				{
					cursor.close();
					m.close();
					return json;
				}
				
				json.accumulate("messages", next);
			}
			
			cursor.close();
			m.close();
			return json;
			
	}
	
	/*public static JSONObject search(String key) throws JSONException
	{
		try
		{
			JSONObject json = new JSONObject();
			Mongo m = new Mongo("132.227.201.129",27130);
			DB db = m.getDB("khecha_djamah_bis");
			DBCollection collection = db.getCollection("comments");
			int id = AuthentificationTools.getIdUserKey(key);
			BasicDBObject query = new BasicDBObject();
			query.put("id", id);
			DBCursor current = collection.find(query);
			

			String result="";
			int i = 0;
			while(current.hasNext())
			{
				DBObject obj=current.next();
				System.out.print(obj);
				//
			}
			current.close();
			return json;
		}
		catch(Exception e)
		{
			
		}
		
		
		return null;
		
	}	*/
	public static JSONObject searchTweetByDate(int login, String date) throws UnknownHostException, MongoException, JSONException{
		Mongo m = new Mongo("132.227.201.129",27130);
		DB db = m.getDB("khecha_djamah_bis");
		DBCollection collection = db.getCollection("comments");
        BasicDBObject query = new BasicDBObject("date", new BasicDBObject("$gt", date));
        query.put("id", login);
        DBCursor cursor = collection.find(query);
        
        boolean op = cursor.hasNext();
        if(op){
        	int i=0;
        	JSONObject ob = new JSONObject();
        	ArrayList <String> list = new ArrayList<String>();
        	 while(cursor.hasNext()){
        		 list.add(cursor.next().toString());
              	ob.accumulate("Resultats", list.get(i).toString().replace("\"", ""));
              	System.out.println(list.get(i));
              	i++;
             }
            return ob;
        }
        else{
        	return null;
        }
        
    }
    
    public static JSONObject searchTweetByLogin(int login) throws UnknownHostException, MongoException, JSONException{
    	Mongo m = new Mongo("132.227.201.129",27130);
		DB db = m.getDB("khecha_djamah_bis");
		DBCollection collection = db.getCollection("comments");
        BasicDBObject query = new BasicDBObject("id", login);
        DBCursor cursor = collection.find(query);
        boolean op = cursor.hasNext();
        if(op){
        	int i=0;
        	JSONObject ob = new JSONObject();
        	ArrayList <String> list = new ArrayList<String>();
        	 while(cursor.hasNext()){
        		 list.add(cursor.next().toString());
              	ob.accumulate("Resultats", list.get(i).toString().replace("\"", ""));
              	System.out.println(list.get(i));
              	i++;
             }
            return ob;
        }
        else{
        	return null;
        	}
    }
    
    public static JSONObject searchTweetByWord(String keyWord) throws UnknownHostException, MongoException, JSONException{
    	Mongo m = new Mongo("132.227.201.129",27130);
		DB db = m.getDB("khecha_djamah_bis");
		DBCollection collection = db.getCollection("comments");
        BasicDBObject query = new BasicDBObject("text", new BasicDBObject("$regex", keyWord));
        DBCursor cursor = collection.find(query);
        boolean op = cursor.hasNext();
        if(op){
        	int i=0;
        	JSONObject ob = new JSONObject();
        	ArrayList <String> list = new ArrayList<String>();
        	 while(cursor.hasNext()){
        		 list.add(cursor.next().toString());
              	ob.accumulate("Resultats", list.get(i).toString().replace("\"", ""));
              	System.out.println(list.get(i));
              	i++;
             }
            return ob;
        }
        else{
        	return null;
        }

    }



}