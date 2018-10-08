package fr.acceis.forum.servlet;

public class Message {

	private int id;

	private String auteur;

	private String texte;

	private int idThread;

	private String threadName;

	public Message(int id, String auteur, int idThread, String texte, String threadName) {
		this.id = id;
		this.auteur = auteur;
		this.texte = texte;
		this.idThread = idThread;
		this.threadName = threadName;
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

}
