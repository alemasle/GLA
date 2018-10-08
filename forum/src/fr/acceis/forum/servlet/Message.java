package fr.acceis.forum.servlet;

public class Message {

	private int id;

	private String auteur;

	private String texte;

	private int idThread;

	private String threadName;

	private String date;

	public Message(int id, String auteur, int idThread, String texte, String threadName, String date) {
		this.id = id;
		this.auteur = auteur;
		this.texte = texte;
		this.idThread = idThread;
		this.threadName = threadName;
		this.date = date;
	}

	public String toString() {
		return "id:" + id + " auteur:" + auteur + " thread:" + idThread + " Message: \"" + texte + "\""
				+ " nom thread: " + threadName;
	}

	public int getId() {
		return id;
	}

	public String getAuteur() {
		return auteur;
	}

	public String getTexte() {
		return texte;
	}

	public int getIdThread() {
		return idThread;
	}

	public String getThreadName() {
		return threadName;
	}

	public String getDate() {
		return date;
	}

}
