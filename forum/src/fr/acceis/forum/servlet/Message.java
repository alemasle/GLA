package fr.acceis.forum.servlet;

public class Message {

	private int id;

	private String author;

	private String texte;

	private int idThread;

	public Message(int id, String author, String texte, int idThread) {
		this.id = id;
		this.author = author;
		this.texte = texte;
		this.idThread = idThread;
	}

	public int getId() {
		return id;
	}

	public String getAuthor() {
		return author;
	}

	public String getTexte() {
		return texte;
	}

	public int getIdThread() {
		return idThread;
	}

}
