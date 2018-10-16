package roles;

public class Administrateur implements Role {

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
		return "Administrateur";
	}

	@Override
	public boolean deleteAllMessage() {
		return true;
	}

	@Override
	public boolean editAllMessage() {
		return true;
	}

}
