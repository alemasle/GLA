package fr.acceis.forum.servlet;

public class PairNbPostMessage {

	private Message msg;

	private int nbPosts;

	public PairNbPostMessage(Message msg, int nbPosts) {
		this.msg = msg;
		this.nbPosts = nbPosts;
	}

	public Message getMessage() {
		return msg;
	}

	public int getNbPosts() {
		return nbPosts;
	}

}
