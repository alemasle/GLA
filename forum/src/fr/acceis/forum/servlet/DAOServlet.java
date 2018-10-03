package fr.acceis.forum.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public boolean checkUser(String user, String pass) throws SQLException {
		Statement stat = connexion.createStatement();
		ResultSet res = stat.executeQuery("SELECT login,password FROM Utilisateurs");

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
