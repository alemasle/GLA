package fr.acceis.forum.roles;

public interface Role {

	boolean readThread();

	boolean writeMessage();

	boolean readProfil();

	boolean deleteMessage();

	boolean deleteAllMessages();

	boolean editMessage();

	boolean editAllMessages();

	String getRole();

}
