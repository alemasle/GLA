package fr.acceis.forum.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JeuDeTestJdBc {

	public final static String[] QUERIES = {
			"drop table if exists Messages",
			"drop table if exists Threads",
			"drop table if exists Utilisateurs",
			
			"create table Utilisateurs (id bigint not null, login varchar(255), password varchar(255), primary key (id))",
			"create table Threads (id bigint not null, auteur bigint not null, name varchar(255), vues bigint, primary key (id), constraint fk_thread_auteur foreign key (auteur) references Utilisateurs(id))",
			"create table Messages (id bigint not null, auteur bigint not null, idThread bigint not null, texte varchar(5000), primary key (id), constraint fk_msg_thread foreign key (idThread) references Threads(id), constraint fk_message_auteur foreign key (auteur) references Utilisateurs(id))",

			"INSERT INTO UTILISATEURS VALUES(1,'admin', 'admin')",
			"INSERT INTO UTILISATEURS VALUES(2,'pierre', 'pierre')",
			"INSERT INTO UTILISATEURS VALUES(3,'paul', 'paul')",
			"INSERT INTO UTILISATEURS VALUES(4,'jacques', 'jacques')",

			"INSERT INTO THREADS VALUES(1, 1, 'Mon premier thread est nul et sans interet', 0)",
			"INSERT INTO THREADS VALUES(2, 3, 'Mon second suis le premier', 0)",
			"INSERT INTO THREADS VALUES(3, 2, 'Mon tout est une mauvaise charade', 0)",
			"INSERT INTO MESSAGES VALUES(1, 1, 1, 'First!')",
			"INSERT INTO MESSAGES VALUES(2, 3, 1, 'Bon ben deuxieme')",
			};

	public static void main(String[] args) throws Exception {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connexion = DriverManager
				.getConnection("jdbc:hsqldb:/home/alemasle/Documents/GLA/forum/data/basejpa", "sa", "");
		Statement stmt = connexion.createStatement();

		for (String query : QUERIES) {
//			System.out.println(query);
			stmt.executeUpdate(query);
		}

		stmt.close();
		connexion.close();

		System.out.println("Tables Utilisateurs, Threads et Messages (re)cree!");
		
		DAOServlet dao = DAOServlet.getDAO();
		dao.getThreads();
		dao.getThreadMessages(1);
		dao.close();

	}

}
