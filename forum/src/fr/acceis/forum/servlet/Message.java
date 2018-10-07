package fr.acceis.forum.servlet;

public class Message {

	private int id;

	private int author;

	private String texte;

	private int idThread;

	public Message(int id, int author, int idThread, String texte) {
		this.id = id;
		this.author = author;
		this.texte = texte;
		this.idThread = idThread;
	}

	public String toString() {
		return "id:" + id + " auteur:" + author + " thread:" + idThread + " Message: \"" + texte + "\"";
	}

	public int getId() {
		return id;
	}

	public int getAuthor() {
		return author;
	}

	public String getTexte() {
		return texte;
	}

	public int getIdThread() {
		return idThread;
	}

}
