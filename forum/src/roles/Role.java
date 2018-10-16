package roles;

public interface Role {

	boolean readThread();

	boolean writeMessage();

	boolean readProfil();

	boolean deleteMessage();
	
	boolean deleteAllMessage();
	
	boolean editMessage();
	
	boolean editAllMessage();
	
	String getRole();

}
