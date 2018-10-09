package fr.acceis.forum.entity;

public class Utilisateurs {

	private String login;

	private String password;

	private int id;

	private int nbPosts;

	public Utilisateurs(String login, String password, int id, int nbPosts) {
		this.login = login;
		this.password = password;
		this.id = id;
		this.nbPosts = nbPosts;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}

	public int nbPosts() {
		return nbPosts;
	}
}
