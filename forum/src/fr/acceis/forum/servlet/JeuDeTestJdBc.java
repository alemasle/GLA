package fr.acceis.forum.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JeuDeTestJdBc {

	public final static String[] QUERIES = {
			"drop table if exists Messages",
			"drop table if exists Threads",
			"drop table if exists Utilisateurs",
			
			"create table Utilisateurs (id bigint not null identity, login varchar(255), password varchar(255), posts bigint default 0,  primary key (id))",
			"create table Threads (id bigint not null identity, auteur bigint not null, name varchar(255), vues bigint default 0, primary key (id), constraint fk_thread_auteur foreign key (auteur) references Utilisateurs(id))",
			"create table Messages (id bigint not null identity, auteur bigint not null, idThread bigint not null, texte varchar(5000), date varchar(100), edited boolean default false, primary key (id), constraint fk_msg_thread foreign key (idThread) references Threads(id), constraint fk_message_auteur foreign key (auteur) references Utilisateurs(id))",

			"INSERT INTO UTILISATEURS(login,password) VALUES('admin', 'admin')",
			"INSERT INTO UTILISATEURS(login,password) VALUES('pierre', 'pierre')",
			"INSERT INTO UTILISATEURS(login,password) VALUES('paul', 'paul')",
			"INSERT INTO UTILISATEURS(login,password) VALUES('jacques', 'jacques')",

//			"INSERT INTO THREADS(auteur, name) VALUES(0, 'Mon premier thread est nul et sans interet')",
//			"INSERT INTO THREADS(auteur, name) VALUES(2, 'Mon second suis le premier')",
//			"INSERT INTO THREADS(auteur, name) VALUES(1, 'Mon tout est une mauvaise charade')",
//			
//			"INSERT INTO MESSAGES(auteur, idThread, texte, date) VALUES(0, 0, 'First!', '00:11:03 10/10/2018')",
//			"INSERT INTO MESSAGES(auteur, idThread, texte) VALUES(2, 0, 'Bon ben deuxieme')",
//			"INSERT INTO MESSAGES(auteur, idThread, texte) VALUES(1, 1, 'C est mon deuxieme message!')",
			};

	public static void main(String[] args) throws Exception {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connexion = DriverManager.getConnection("jdbc:hsqldb:/home/alemasle/Documents/GLA/forum/data/basejpa", "sa", "");
		Statement stmt = connexion.createStatement();

		for (String query : QUERIES) {
//			System.out.println(query);
			stmt.executeUpdate(query);
		}

		stmt.close();
		connexion.close();

		System.out.println("Tables Utilisateurs, Threads et Messages (re)cree!");
		
//		DAOServlet dao = DAOServlet.getDAO();
//		dao.updateDate(0);
//		dao.getThreads();
//		dao.getThreadMessages(0);
//		dao.close();

	}

}
