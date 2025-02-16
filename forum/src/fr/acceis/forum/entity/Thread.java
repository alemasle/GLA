package fr.acceis.forum.entity;

public class Thread {

	private int id;

	private String auteur;

	private String name;

	private int nbMsg;

	private int nbVues;

	public Thread(int id, String auteur, String name, int nbMsg, int nbVues) {
		this.id = id;
		this.auteur = auteur;
		this.name = name;
		this.nbMsg = nbMsg;
		this.nbVues = nbVues;
	}

	public String toString() {
		return "id:" + id + " auteur:" + auteur + " name: \"" + name + "\"" + " nbMsg:" + nbMsg + " vues:" + nbVues;
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

	public int getNbVues() {
		return nbVues;
	}

}
