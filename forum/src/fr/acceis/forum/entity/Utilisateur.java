package fr.acceis.forum.entity;

public class Utilisateur {

	private String login;

	private String password;

	private int id;

	private int nbPosts;

	private String signup;

	private String avatar;

	public Utilisateur(String login, String password, int id, int nbPosts, String signup, String avatar) {
		this.login = login;
		this.password = password;
		this.id = id;
		this.nbPosts = nbPosts;
		this.signup = signup;
		this.avatar = avatar;
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

	public int getNbPosts() {
		return nbPosts;
	}

	public String getSignUp() {
		return signup;
	}

	public String getAvatar() {
		return avatar;
	}
}
