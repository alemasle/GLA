package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.acceis.forum.entity.Thread;
import fr.acceis.forum.entity.Utilisateur;

public class ProfilServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(ProfilServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String login = req.getParameter("login");
		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();

			Utilisateur profil = dao.getUser(login);

			List<Thread> lthread = dao.getThreadUser(login);
			req.setAttribute("threads_answered", lthread);
			req.setAttribute("login", login);
			req.setAttribute("userProfil", profil);
			req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req, resp);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			logger.error("error while \"" + (String) req.getSession().getAttribute("user") + "\" tried to access \""
					+ login + "\" profil page");
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
