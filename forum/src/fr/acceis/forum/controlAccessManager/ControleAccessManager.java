package fr.acceis.forum.controlAccessManager;

import fr.acceis.forum.entity.Utilisateur;
import fr.acceis.forum.roles.Role;

public class ControleAccessManager {

	public static boolean autorize(Utilisateur user, String uri) {

		Role role = user.getRole();

		switch (uri) {

		case "home":
			return true;

		case "thread":
			return role.readThread();

		case "signup":
			return role.getRole().compareTo("Invite") == 0;

		case "newthread":
			return role.writeThread();

		case "logout":
			return role.getRole().compareTo("Invite") != 0;

		case "newpost":
			return role.writeMessage();

		case "editpost":
			return role.editMessage();

		case "profil":
			return role.readProfil();

		case "uploadavatar":
			return role.readProfil();

		case "deletemessage":
			return role.writeMessage();

		case "login":
			return role.getRole().compareTo("Invite") == 0;

		default:
			return false;
		}
	}

}
