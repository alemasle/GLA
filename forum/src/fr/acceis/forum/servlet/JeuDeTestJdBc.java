package fr.acceis.forum.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JeuDeTestJdBc {

	public final static String[] QUERIES = { "drop table if exists Messages", "drop table if exists Threads",
			"drop table if exists Utilisateurs",

			"create table Utilisateurs (id integer primary key, login varchar(255) not null, password varchar(255), posts integer default 0, signup varchar(100) default 'Was born here', avatar varchar(255) default 'default.jpg', role varchar(255) default 'User')",
			"create table Threads (id integer, auteur integer not null, name varchar(255), vues integer default 0, primary key (id), foreign key (auteur) references Utilisateurs(id))",
			"create table Messages (id integer, auteur integer not null, idThread integer not null, texte varchar(5000), date varchar(100), edited boolean default false, primary key (id), foreign key (idThread) references Threads(id), foreign key (auteur) references Utilisateurs(id))",

			"INSERT INTO UTILISATEURS(login,password, avatar, role) VALUES('admin', 'admin', 'admin.jpg', 'Administrateur')",
			"INSERT INTO UTILISATEURS(login,password, avatar) VALUES('pierre', 'pierre', 'pierre.jpg')",
			"INSERT INTO UTILISATEURS(login,password, avatar) VALUES('paul', 'paul', 'paul.jpg')",
			"INSERT INTO UTILISATEURS(login,password) VALUES('jacques', 'jacques')",

			"INSERT INTO THREADS(auteur, name) VALUES(1, 'Mon premier thread est nul et sans interet')",
			"INSERT INTO THREADS(auteur, name) VALUES(1, 'Mon second suis le premier')",
			"INSERT INTO THREADS(auteur, name) VALUES(2, 'PLAGIAAAAT')",

			"INSERT INTO MESSAGES(auteur, idThread, texte, date) VALUES(1, 1, 'First!', '00:11:03 10/10/2018')",
			"INSERT INTO MESSAGES(auteur, idThread, texte, date) VALUES(1, 1, 'Deuxio!', '00:12:03 11/10/2018')",
			"INSERT INTO MESSAGES(auteur, idThread, texte, date) VALUES(2, 1, 'Third!', '00:13:03 12/10/2018')", };

	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC").newInstance();
		Connection connexion = DriverManager.getConnection("jdbc:sqlite:/home/alemasle/Documents/GLA/forum/data/bdd.db",
				"sa", "");
		Statement stmt = connexion.createStatement();

		for (String query : QUERIES) {
//			System.out.println(query);
			stmt.executeUpdate(query);
		}

		stmt.close();
		connexion.close();

		System.out.println("Tables Utilisateurs, Threads et Messages (re)cree!");

		DAOServlet dao = DAOServlet.getDAO();

		int[] a = { 1, 2, 3 };

		for (int i : a) {
			String aut = dao.existUser(i);
			dao.updateNbPosts(aut);
		}

//		dao.updateDate(0);
//		dao.getThreadMessages(0);
//		dao.close();

	}

}
