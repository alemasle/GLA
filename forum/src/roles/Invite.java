package roles;

public class Invite implements Role {

	@Override
	public boolean readThread() {
		return true;
	}

	@Override
	public boolean writeMessage() {
		return false;
	}

	@Override
	public boolean readProfil() {
		return false;
	}

	@Override
	public boolean deleteMessage() {
		return false;
	}

	@Override
	public boolean editMessage() {
		return false;
	}

	@Override
	public String getRole() {
		return "Invite";
	}

	@Override
	public boolean deleteAllMessage() {
		return false;
	}

	@Override
	public boolean editAllMessage() {
		return false;
	}
}
