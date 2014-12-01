package db.tools;

//import java.net.UnknownHostException;
import java.sql.*;

import javax.sql.*;
import javax.naming.*;

public class Database {
	private static DataSource dataSource;
	private static Database database = null;


	public Database(String jndiname) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/db" +
					jndiname);
		} catch (NamingException e) {
			// Handle error that it’s not configured in JNDI.
			throw new SQLException(jndiname + " is missing in JNDI! : "+e.getMessage());
		}
	}

/*	public Connection getConnection() throws SQLException
	{
		return dataSource.getConnection();
	}
	
	public static Connection getMySQLConnection() throws SQLException, NamingException
	{
	      if(DBStatic.mysql_pooling==false)
	      {
	            return(DriverManager.getConnection("jdbc:mysql://"+DBStatic.mysql_host+"/"+DBStatic.mysql_db, DBStatic.mysql_username,DBStatic.mysql_password));
	      }else
	      {
	            if (database==null)
	            {
	                  database=new DataBase("jdbc/db");
	            }
	            return(database.getConnection());
	*/
	
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}


	public static Connection getMySQLConnection() throws SQLException, NamingException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
	      if(DBStatic.mysql_pooling== false)
	      {
	            return(DriverManager.getConnection("jdbc:mysql://"+DBStatic.mysql_host+"/"+DBStatic.mysql_db, DBStatic.mysql_username,DBStatic.mysql_password));
	      }else
	      {
	            if (database==null)
	            {
	                  database=new Database("jdbc/db");
	            }
	            return(database.getConnection());
	
	      }
	}
}

