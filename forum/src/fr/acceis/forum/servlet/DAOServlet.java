package fr.acceis.forum.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;

public final class DAOServlet extends HttpServlet {

	private Connection connexion;

	private static DAOServlet dao = null;

	private DAOServlet(Connection connexion) {
		this.connexion = connexion;
	}

	public static DAOServlet getDAO()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (dao == null) {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			Connection connexion = DriverManager
					.getConnection("jdbc:hsqldb:/home/alemasle/Documents/GLA/forum/data/basejpa", "sa", "");
			dao = new DAOServlet(connexion);
		}
		return dao;
	}

	/**
	 * Insert a new user in the Utilisateurs table if it does not already exists
	 * 
	 * @param user
	 * @param pass
	 * @return true if the user was added, false otherwise
	 * @throws SQLException
	 */
	public boolean addUser(String user, String pass) throws SQLException {
		String sql = "SELECT login FROM Utilisateurs WHERE login=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		System.out.println(sql);
		stat.setString(1, user);
		ResultSet res1 = stat.executeQuery();

		if (res1.next()) {
			System.out.println(user + " already exists");
			res1.close();
			stat.close();
			return false;
		}

		Statement stat1 = connexion.createStatement();
		ResultSet res = stat1.executeQuery("SELECT id FROM Utilisateurs WHERE id = (SELECT MAX(id) FROM Utilisateurs)");
		res.next();
		int id = res.getInt(1);
		System.out.println("New id = " + (id + 1));

		PreparedStatement stat3 = connexion.prepareStatement("INSERT INTO UTILISATEURS VALUES(?,?,?)");
		stat3.setInt(1, (id + 1));
		stat3.setString(2, user);
		stat3.setString(3, pass);
		stat3.executeUpdate();

		res.close();
		res1.close();
		stat.close();
		stat1.close();
		return true;
	}

	/**
	 * Check the existence of the couple (user,pass) in the Utilisateurs database
	 * 
	 * @param user The username
	 * @param pass The password
	 * @return True if the couple (user,pass) exist in the database, false otherwise
	 * @throws SQLException
	 */
	public boolean checkUser(String user, String pass) throws SQLException {
		String sql = "SELECT login,password FROM Utilisateurs WHERE login=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setString(1, user);
		ResultSet res = stat.executeQuery();

		while (res.next()) {
			if ((res.getString(1).compareTo(user) == 0) && (res.getString(2).compareTo(pass) == 0)) {
				res.close();
				stat.close();
				return true;
			}
		}
		res.close();
		stat.close();
		return false;
	}

}
