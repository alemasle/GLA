package fr.acceis.forum.servlet;

public class Thread {

	private int id;

	private int name;

	public Thread(int id, int name) {
		this.id = id;
		this.name = name;
	}

	public int getName() {
		return name;
	}

	public int getId() {
		return id;
	}

}
