package fr.acceis.forum.roles;

public class User implements Role {

	@Override
	public boolean readThread() {
		return true;
	}

	@Override
	public boolean writeMessage() {
		return true;
	}

	@Override
	public boolean readProfil() {
		return true;
	}

	@Override
	public boolean deleteMessage() {
		return true;
	}

	@Override
	public boolean deleteAllMessages() {
		return false;
	}

	@Override
	public boolean editMessage() {
		return true;
	}

	@Override
	public boolean editAllMessages() {
		return false;
	}

	@Override
	public String getRole() {
		return "User";
	}

	@Override
	public boolean writeThread() {
		return true;
	}

}
