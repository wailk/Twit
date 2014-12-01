package services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONObject;

import db.tools.Database;

public class test {

	public static JSONObject insertUser(String login,String password,String nom,String prenom){
		try{
			Connection c = Database.getMySQLConnection();
			Statement st = c.createStatement();
			st.executeUpdate("INSERT into user values("+"'" + login + "',"+"'" + password + "',"+"'" + prenom + "',"+"'" + nom + "')");
		
			if(st != null)
				st.close();

			if(c != null)
				c.close();
		} catch (SQLException e) {
			ServicesTools.error(100);
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
