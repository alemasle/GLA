package fr.acceis.forum.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JeuDeTestJdBc {

	public final static String[] QUERIES = { "drop table Utilisateurs if exists",
			"drop table Threads if exists",
			"create table Utilisateurs (id bigint not null, login varchar(255), password varchar(255), primary key (id))",
			"create table Threads (id bigint not null, auteur bigint not null, name varchar(255))",
			"create table Message (id bigint not null, auteur bigint not null,",
			"INSERT INTO UTILISATEURS VALUES(1,'admin', 'admin')",
			"INSERT INTO UTILISATEURS VALUES(2,'pierre', 'pierre')",
			"INSERT INTO UTILISATEURS VALUES(3,'paul', 'paul')",
			"INSERT INTO UTILISATEURS VALUES(4,'jacques', 'jacques')",
			"INSERT INTO THREADS VALUES(1, 'Mon premier thread est nul et sans interet')"};

	public static void main(String[] args) throws Exception {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connexion = DriverManager
				.getConnection("jdbc:hsqldb:/home/alemasle/Documents/GLA/forum/data/basejpa", "sa", "");
		Statement stmt = connexion.createStatement();

		for (String query : QUERIES) {
			stmt.executeUpdate(query);
		}

		stmt.close();
		connexion.close();
		
		System.out.println("Table Utilisateurs et Threads (re)cree!");

	}

}
