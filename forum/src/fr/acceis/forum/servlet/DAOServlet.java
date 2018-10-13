package fr.acceis.forum.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;

import fr.acceis.forum.entity.Message;
import fr.acceis.forum.entity.PairNbPostMessage;
import fr.acceis.forum.entity.Thread;
import fr.acceis.forum.entity.Utilisateur;

public final class DAOServlet extends HttpServlet {

	private Connection connexion;

	private static DAOServlet dao = null;

	private DAOServlet(Connection connexion) {
		this.connexion = connexion;
	}

	/**
	 * Create or return the dao (Singleton pattern)
	 * 
	 * @return the dao
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static DAOServlet getDAO()
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (dao == null) {
			Class.forName("org.sqlite.JDBC").newInstance();
			Connection connexion = DriverManager
					.getConnection("jdbc:sqlite:/home/alemasle/Documents/GLA/forum/data/bdd.db", "sa", "");
			dao = new DAOServlet(connexion);
		}
		return dao;
	}

	public void close() throws SQLException {
		connexion.close();
		dao = null;
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
		stat.setString(1, user);
		ResultSet res1 = stat.executeQuery();

		if (res1.next()) {
			System.out.println("\"" + user + "\" already exists");
			res1.close();
			stat.close();
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		java.util.Date uDate = new java.util.Date();
		String date = dateFormat.format(uDate);

		PreparedStatement stat3 = connexion
				.prepareStatement("INSERT INTO UTILISATEURS(login, password, signup) VALUES(?,?,?)");
		stat3.setString(1, user);
		stat3.setString(2, pass);
		stat3.setString(3, date);
		stat3.executeUpdate();

		res1.close();
		stat.close();
		stat3.close();
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

	/**
	 * Get the list of thread and there informations
	 * 
	 * @return The list of thread
	 * @throws SQLException
	 */
	public List<Thread> getThreads() throws SQLException {
		List<Thread> threads = new ArrayList<Thread>();

		String sql = "SELECT * FROM Threads ORDER BY id desc";
		PreparedStatement stat = connexion.prepareStatement(sql);
		ResultSet res = stat.executeQuery();

		while (res.next()) {
			int id = res.getInt("id");
			int auteurId = res.getInt("auteur");
			String name = res.getString("name");
			int nbMsg = 0;
			int nbVues = res.getInt("vues");

			String sqlAut = "SELECT login FROM Utilisateurs WHERE id=?";
			PreparedStatement statAut = connexion.prepareStatement(sqlAut);
			statAut.setInt(1, auteurId);
			ResultSet aut = statAut.executeQuery();

			String auteur = "???";
			if (aut.next()) {
				auteur = aut.getString("login");
			}

			String sqlNbMsg = "SELECT count(id) FROM Messages WHERE idThread=?";
			PreparedStatement statNbMsg = connexion.prepareStatement(sqlNbMsg);
			statNbMsg.setInt(1, id);
			ResultSet msgs = statNbMsg.executeQuery();

			if (msgs.next()) {
				nbMsg = msgs.getInt(1);
			}

			Thread th = new Thread(id, auteur, name, nbMsg, nbVues);
			threads.add(th);

			statNbMsg.close();
			msgs.close();
			statAut.close();
			aut.close();
		}
		stat.close();
		res.close();
		return threads;
	}

	/**
	 * 
	 * @param idThread The thread's id from which the messages belong to
	 * @return The list of message linked to the same Thread (idThread)
	 * @throws SQLException
	 */
	public List<PairNbPostMessage> getThreadMessages(int idThread) throws SQLException {
		List<PairNbPostMessage> msg = new ArrayList<PairNbPostMessage>();

		String sql = "SELECT * FROM Messages WHERE idThread=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setInt(1, idThread);
		ResultSet res = stat.executeQuery();

		while (res.next()) {
			int id = res.getInt("id");
			int auteur = res.getInt("auteur");
			int idThr = res.getInt("idThread");
			String texte = res.getString("texte");
			String date = res.getString("date");
			boolean edited = res.getBoolean("edited");

			String sqlAut = "SELECT login FROM Utilisateurs WHERE id=?";
			PreparedStatement aut = connexion.prepareStatement(sqlAut);
			aut.setInt(1, auteur);
			ResultSet resAut = aut.executeQuery();
			while (resAut.next()) {
				String auteurMsg = resAut.getString("login");

				String sqlName = "SELECT name FROM Threads WHERE id=?";
				PreparedStatement nameReq = connexion.prepareStatement(sqlName);
				nameReq.setInt(1, idThread);
				ResultSet resName = nameReq.executeQuery();

				while (resName.next()) {

					String name = resName.getString("name");
					Message m = new Message(id, auteurMsg, idThr, texte, name, date, edited);
					int posts = getPostsUser(auteurMsg);// countMessagesUser(auteur);

					PairNbPostMessage p = new PairNbPostMessage(m, posts);
//					System.out.println(m.toString());
					msg.add(p);
				}
				nameReq.close();
				resName.close();
			}
			aut.close();
			resAut.close();
		}
		stat.close();
		res.close();
		return msg;
	}

	public int getPostsUser(String auteur) throws SQLException {
		String sqlAuteurId = "SELECT posts FROM Utilisateurs WHERE login=?";
		PreparedStatement statAut = connexion.prepareStatement(sqlAuteurId);
		statAut.setString(1, auteur);
		ResultSet res = statAut.executeQuery();
		int posts = 0;
		if (res.next()) {
			posts = res.getInt("posts");
		}
		res.close();
		statAut.close();
		return posts;
	}

	/**
	 * Count the number of post the thread has
	 * 
	 * @param idThread
	 * @return the number of posts
	 * @throws SQLException
	 */
	private int countMessagesThread(int idThread) throws SQLException {
		int result = 0;
		String sql = "SELECT count(id) FROM Messages WHERE idThread=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setInt(1, idThread);
		ResultSet res = stat.executeQuery();

		if (res.next()) {
			result = res.getInt("count(id)");
		}

		stat.close();
		res.close();
		return result;
	}

	/**
	 * Count the number of post the author has made
	 * 
	 * @param idAuteur
	 * @return the number of posts
	 * @throws SQLException
	 */
	private int countMessagesAuteur(int idAuteur) throws SQLException {
		int result = 0;
		String sql = "SELECT count(id) FROM Messages WHERE auteur=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setInt(1, idAuteur);
		ResultSet res = stat.executeQuery();

		if (res.next()) {
			result = res.getInt("count(id)");
		}

		stat.close();
		res.close();
		return result;
	}

	/**
	 * Increment the number of views for the thread threadId
	 * 
	 * @param threadId The thread's id
	 * @throws SQLException
	 */
	public void incrementeVues(int threadId) throws SQLException {
		String sql = "UPDATE Threads SET vues=vues+1 WHERE id=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setInt(1, threadId);
		stat.executeUpdate();
		stat.close();
	}

	/**
	 * Decrement the number of views for the thread threadId
	 * 
	 * @param threadId The thread's id
	 * @throws SQLException
	 */
	public void decrementeVues(int threadId) throws SQLException {
		String sql = "UPDATE Threads SET vues=vues-1 WHERE id=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setInt(1, threadId);
		stat.executeUpdate();
		stat.close();
	}

	public int getIdAuteur(String auteur) throws SQLException {
		String sqlAuteurId = "SELECT id FROM Utilisateurs WHERE login=?";
		PreparedStatement statAut = connexion.prepareStatement(sqlAuteurId);
		statAut.setString(1, auteur);
		ResultSet res = statAut.executeQuery();
		if (res.next()) {
			int aut = res.getInt("id");
			res.close();
			statAut.close();
			return aut;
		}
		res.close();
		statAut.close();
		return -1;
	}

	public void newThread(String auteur, String titre) throws SQLException {
		int auteurId = getIdAuteur(auteur);
		String sql = "INSERT INTO Threads(auteur, name, vues) VALUES(?,?,0)";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setInt(1, auteurId);
		stat.setString(2, titre);
		stat.executeUpdate();
		stat.close();

	}

	public void newPost(int idThread, String auteur, String texte) throws SQLException, ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		java.util.Date uDate = new java.util.Date();
		String date = dateFormat.format(uDate);

		String sql = "INSERT INTO Messages(auteur, idThread, texte, date) VALUES(?,?,?,?)";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setInt(1, getIdAuteur(auteur));
		stat.setInt(2, idThread);
		stat.setString(3, texte);
		stat.setString(4, date);
		stat.executeUpdate();
	}

	public void updateNbPosts(String auteur) throws SQLException {
		int id = getIdAuteur(auteur);
		int nbPosts = countMessagesAuteur(id);

		String sql = "UPDATE Utilisateurs SET posts=? WHERE id=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setInt(1, nbPosts);
		stat.setInt(2, id);
		stat.executeUpdate();

		stat.close();
	}

	public String getTexte(int idMsg) throws SQLException {
		String sqlAuteurId = "SELECT texte FROM Messages WHERE id=?";
		PreparedStatement statAut = connexion.prepareStatement(sqlAuteurId);
		statAut.setInt(1, idMsg);
		ResultSet res = statAut.executeQuery();

		if (res.next()) {
			String txt = res.getString("texte");
			statAut.close();
			res.close();
			return txt;
		}
		statAut.close();
		res.close();
		return "";
	}

	public void updateDate(int idMsg) throws SQLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		java.util.Date uDate = new java.util.Date();
		String date = dateFormat.format(uDate);

		String sql = "UPDATE Messages SET date=?, edited=? WHERE id=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setString(1, date);
		stat.setBoolean(2, true);
		stat.setInt(3, idMsg);
		stat.executeUpdate();
		stat.close();
	}

	public void updateTexte(int idMsg, String txt) throws SQLException {
		String sql = "UPDATE Messages SET texte=? WHERE id=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setString(1, txt);
		stat.setInt(2, idMsg);
		stat.executeUpdate();
		stat.close();
	}

	public List<Thread> getThreadUser(String user) throws SQLException {
		List<Thread> lth = new ArrayList<Thread>();
		int auteur = getIdAuteur(user);

		String sqlThreadsId = "SELECT * FROM Threads WHERE auteur=? ORDER BY id desc";
		PreparedStatement statAut = connexion.prepareStatement(sqlThreadsId);
		statAut.setInt(1, auteur);
		ResultSet res = statAut.executeQuery();

		while (res.next()) {
			int id = res.getInt("id");
			int nbMsg = countMessagesThread(id);
			lth.add(new Thread(id, user, res.getString("name"), nbMsg, res.getInt("vues")));

		}

		return lth;
	}

	public String existUser(int idAut) throws SQLException {
		String sqlThreadsId = "SELECT login FROM Utilisateurs WHERE id=?";
		PreparedStatement statAut = connexion.prepareStatement(sqlThreadsId);
		statAut.setInt(1, idAut);
		ResultSet res = statAut.executeQuery();
		String log = null;
		if (res.next()) {
			log = res.getString("login");
		}

		res.close();
		statAut.close();
		return log;
	}

	public String existUser(String login) throws SQLException {
		int idAut = getIdAuteur(login);
		return existUser(idAut);
	}

	public Utilisateur getUser(int idUser) throws SQLException {
		String sqlThreadsId = "SELECT * FROM Utilisateurs WHERE id=?";
		PreparedStatement statAut = connexion.prepareStatement(sqlThreadsId);
		statAut.setInt(1, idUser);
		ResultSet res = statAut.executeQuery();

		Utilisateur user = null;
		if (res.next()) {
			String login = res.getString("login");
			String password = "hidden";
			int id = res.getInt("id");
			int nbPosts = res.getInt("posts");
			String signup = res.getString("signup");
			String avatar = res.getString("avatar");

			user = new Utilisateur(login, password, id, nbPosts, signup, avatar);
		}

		return user;
	}

	public Utilisateur getUser(String idUser) throws SQLException {
		int user = getIdAuteur(idUser);
		return getUser(user);
	}
	
	public void updateAvatar(String user, String avatar) throws SQLException {
		String sql = "UPDATE Utilisateurs SET avatar=? WHERE login=?";
		PreparedStatement stat = connexion.prepareStatement(sql);
		stat.setString(1, avatar);
		stat.setString(2, user);
		stat.executeUpdate();
		
		stat.close();
	}

}
