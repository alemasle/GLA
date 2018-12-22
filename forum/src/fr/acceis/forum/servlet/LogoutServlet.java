package fr.acceis.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.acceis.forum.entity.Utilisateur;

public class LogoutServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(LogoutServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String user = (String) session.getAttribute("user");
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

		session.invalidate();
		logger.info("The " + utilisateur.getRole().getRole() + " \"" + user + "\" has disconnected. ip: "
				+ req.getRemoteAddr());
		resp.sendRedirect("/forum/home");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
