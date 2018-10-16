package roles;

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
	public boolean deleteAllMessage() {
		return false;
	}

	@Override
	public boolean editMessage() {
		return true;
	}

	@Override
	public boolean editAllMessage() {
		return false;
	}

	@Override
	public String getRole() {
		return "User";
	}

}
