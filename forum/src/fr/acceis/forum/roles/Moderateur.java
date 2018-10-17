package fr.acceis.forum.roles;

public class Moderateur implements Role {

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
	public boolean editMessage() {
		return true;
	}

	@Override
	public String getRole() {
		return "Moderateur";
	}

	@Override
	public boolean deleteAllMessages() {
		return true;
	}

	@Override
	public boolean editAllMessages() {
		return true;
	}

	@Override
	public boolean writeThread() {
		return true;
	}

}
