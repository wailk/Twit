package db.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import java.util.UUID;
import javax.naming.NamingException;

import org.json.JSONException;
//import org.json.JSONObject;


import db.tools.Database;
import services.ServicesTools;



public class AuthentificationTools {

	public static ResultSet executeQuery(String query) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NamingException
	{
		
		
		Class.forName("com.mysql.jdbc.Driver");
		
		
		java.sql.Connection connection = Database.getMySQLConnection();
		
		Statement st = connection.createStatement();

		ResultSet rs = st.executeQuery(query);
		

		return rs;
	}
	
	
	public static void executeUpdate(String query) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NamingException
	{
		
		
		Class.forName("com.mysql.jdbc.Driver");
		
		new Database("jdbc/db");
		java.sql.Connection connection = Database.getMySQLConnection();
		
		Statement st = connection.createStatement();
		st.executeUpdate(query);

	}
	
	/**
	 * Verifie si l'utilisateur dont le login est passe en parametre existe
	 * Recupere une connexion sql
	 * Execute une requete SQL pour verifier si l'usitisateur existe
	 * @param login
	 * @return
	 * @throws JSONException
	 */
	public static boolean userExist(String login) throws JSONException {
		boolean retour = false;
		Connection c = null;
		String q = "";
		Statement st = null;
		ResultSet rs = null;

		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			c = Database.getMySQLConnection();
			q = ("SELECT id FROM user WHERE login= '" + login + "'");
			st = c.createStatement();
			st.executeQuery(q);
			rs = st.getResultSet();
			if(!rs.next()){
				if(st != null && c != null){
					st.close();
					c.close();
				}
				ServicesTools.error(3);
			}
			else{
				retour = true;
				if(st != null && c != null){
					st.close();
					c.close();
				}
				return retour;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}  catch (Exception e) {

			e.printStackTrace();
		} 
		return retour;	
	}


	/**
	 * Recupere l'identifiant de l'utilisateur dont le login est passe en parametre
	 * Recupere une connexion sql
	 * Execute une requete SQL qui recupere l'id de l'utilisateur
	 * @param login
	 * @return
	 * @throws JSONException
	 */
	public static int getIdUser(String login) {
		Connection c = null;
		String q = "";
		Statement st = null;
		ResultSet rs = null;

		try{
			c = Database.getMySQLConnection();
			q = ("SELECT id FROM user WHERE login = '" + login + "'");
			st = c.createStatement();
			st.executeQuery(q);
			rs= st.getResultSet();
			if(!rs.next()){
				if(st != null && c != null){
					st.close();
					c.close();
				}
			}
			else{

				long countLong = rs.getLong(1);

				if(st != null && c != null){
					st.close();
					c.close();
				}

				return(int)countLong; 


			}
		}catch(SQLException e){
			ServicesTools.error(100);
			return -68;
		} catch (Exception e) {
			e.printStackTrace();
			return -20;
		}
		return -1;
	}



	/**
	 * Recupere l'id de l'utilisateur via la cle
	 * @param key
	 * @return
	 */
	public static int getIdUserKey(String key_user) {
		try{/*String query = "select user.id from users user where login='"+login+"'";
		
		ResultSet rs = executeQuery(query);
		
		if(rs.next())
		{
			return rs.getInt("id");
		}
		else
		{
			return -1;
		}*/
			//int id=-1;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			//Statement st = c.createStatement();
			//st.executeQuery("SELECT * FROM session WHERE key_user='" + key_user + "'");
			String query = "SELECT s.id_user FROM session s WHERE s.key_user='"+key_user+"'";
			ResultSet rs = executeQuery(query);
			if(!rs.next()){
				if(/*st != null &&*/ c != null){
					//st.close();
					c.close();
				}
			}
			else{
				if(/*st != null && */c != null){
					//st.close();
					c.close();
				}
				return rs.getInt("id_user");
				/*id = rs.getInt(2);
				if(rs.getInt(3) == 1){
					rs.close();
					st.close();
					c.close();			
				}
				return id;*/
		}}catch(SQLException e){
			ServicesTools.error(100);
			return -666;
		} catch (Exception e) {
			e.printStackTrace();
			return -18;
		}
		return -1;
	}
 
	/**
	 * Verifie qu'un mot de passe est correct
	 * Recupere une connexion sql
	 * Execute une requete sql ou l'on recupere le mot de passe passe en param correspondant au login 
	 * @param login
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String login, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NamingException{
			String q = "SELECT password FROM user WHERE login = '" + login + "'";
			ResultSet rs = executeQuery(q);
			
			if(rs.next())
			{
				if(rs.getString("password").equals(password))
				{
					return true;
				}
				return false;
			}
			return false;
	}
	
	/**
	 * Insere un utilisateur dans la base de donnees
	 * @param login
	 * @param password
	 * @param nom
	 * @param prenom
	 */
	public static void insertUser(String login,String password,String nom,String prenom) {
		try {
			Connection c = Database.getMySQLConnection();
			Statement st = c.createStatement();
			st.executeUpdate("INSERT into user(login, password, prenom, nom) values('"+login +"','"+ password+ "','"+prenom + "','" + nom +"')");
				st.close();
				c.close();
				
		} catch (SQLException e) {
			ServicesTools.error(100);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Supprime un utilisateur de la base de donnees
	 * @param login
	 * @param password
	 */
	public static void removeUser(int id_user,String password){
		try {
			Connection c = Database.getMySQLConnection();
			Statement st = c.createStatement();
			st.executeUpdate("DELETE FROM user WHERE id='"+id_user+"' AND password='"+password+"'");
			st.close();
			c.close();
			
			if(st != null)
				st.close();

			if(c != null)
				c.close();
		} catch (SQLException e) {
			ServicesTools.error(100);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	
	/**
	 * Insere une session dans la base de donnees
	 * @param id_user
	 * @param key
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NamingException 
	 */
	public static String insertSession(int id_user, boolean b) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NamingException {
		String key_user = ServicesTools.generatorKey();
		try{
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn= Database.getMySQLConnection();
			Statement inst = conn.createStatement();
			inst.executeUpdate("INSERT into session(id_user,key_user) values('"+id_user+"','"+key_user+"' );");
			inst.close();
			conn.close();

		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return key_user;
	}
		

	/**
	 * Supprime une session de la base de donnees
	 * @param key
	 */
	public static int supprSession(String key_user) {
		Connection c = null;
		Statement st = null;
		//ResultSet rs = null;
		String query = "";

		try{
			c = Database.getMySQLConnection();
			st = c.createStatement();
			query = ("DELETE FROM session WHERE key_user= '" + key_user + "'") ;
			//rs = st.executeQuery(query);
			st.executeUpdate(query);
			//			if(!rs.next())
			//				ServicesTools.error(100000);

			if(st != null)
				st.close();

			if(c != null)
				c.close();
			return 5;
		}catch(SQLException e){
			ServicesTools.error(100);
			return -65;
		}catch(Exception e){
			e.printStackTrace();
			return -20;
		}
	}

	/**
	 * Rajoute l'ami d'un utilisateur dans la base de donnee
	 * @param key
	 * @param id_friend
	 */
	public static void addFriend(String key_user, String id_friend) {
		Connection c = null;
		Statement st = null;
		
		String query = "";
		int id_user=0;

		try{
			id_user = getIdUserKey(key_user);
			c = Database.getMySQLConnection();
			st = c.createStatement();
			//query ="INSERT INTO `khecha_djamah`.`friends`(`id_user`, `id_friend`) values('"+id_user+"', '"+id_friend+"')";
			query ="INSERT INTO friends(id_user, id_friend) values('"+id_user+"', '"+id_friend+"')";
			st.executeUpdate(query);
			//			if(!rs.next())
			//				ServicesTools.error(100000);

			if(st != null)
				st.close();

			if(c != null)
				c.close();
		}catch(SQLException e){
			ServicesTools.error(100);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void removeFriend(String key_user, String id_friend) {
		Connection c = null;
		Statement st = null;
		String query = "";
		int id_user=-1;

		try{
			id_user = getIdUserKey(key_user);
			c = Database.getMySQLConnection();
			st = c.createStatement();
			query = "DELETE FROM friends WHERE id_user = "+id_user+" AND id_friend = "+id_friend+";";
			st.executeUpdate(query);
			
			if(st != null)
				st.close();

			if(c != null)
				c.close();
		}catch(SQLException e){
			ServicesTools.error(100);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
