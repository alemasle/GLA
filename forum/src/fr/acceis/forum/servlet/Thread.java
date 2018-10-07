package fr.acceis.forum.servlet;

public class Thread {

	private int id;

	private String auteur;

	private String name;

	private int nbMsg;

	public Thread(int id, String auteur, String name, int nbMsg) {
		this.id = id;
		this.auteur = auteur;
		this.name = name;
		this.nbMsg = nbMsg;
	}

	public String toString() {
		return "id:" + id + " auteur:" + auteur + " name: \"" + name + "\"" + " nbMsg:" + nbMsg;
	}

	public String getName() {
		return name;
	}

	public String getAuteur() {
		return auteur;
	}

	public int getId() {
		return id;
	}

	public int getNbMsg() {
		return nbMsg;
	}

}
