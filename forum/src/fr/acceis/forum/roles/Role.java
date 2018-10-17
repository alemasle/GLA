package fr.acceis.forum.roles;

public interface Role {

	boolean readThread();
	
	boolean writeThread();

	boolean writeMessage();

	boolean readProfil();

	boolean deleteMessage();

	boolean deleteAllMessages();

	boolean editMessage();

	boolean editAllMessages();

	String getRole();

}
