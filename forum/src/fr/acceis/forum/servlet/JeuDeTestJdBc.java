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

			"INSERT INTO UTILISATEURS(login,password, avatar, role) VALUES('admin', '279dae236bcdc6f2a92369b9c7589ad2113b391b33ae04ba58756a08e5f00a19', 'admin.gif', 'Administrateur')",
			// admin:poiuytreza
	};

	/**
	 * Used to make tests and reset the database
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC").newInstance();
		Connection connexion = DriverManager.getConnection("jdbc:sqlite:/home/alemasle/Documents/GLA/forum/data/bdd.db",
				"sa", "");
		Statement stmt = connexion.createStatement();

		for (String query : QUERIES) {
			stmt.executeUpdate(query);
		}

		stmt.close();
		connexion.close();

		System.out.println("Tables Utilisateurs, Threads et Messages (re)cree!");

	}

}
