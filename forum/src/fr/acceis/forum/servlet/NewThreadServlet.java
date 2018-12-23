package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewThreadServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(NewThreadServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		session.setAttribute("sourceFrom", "newthread");
		req.getRequestDispatcher("/WEB-INF/jsp/newthread.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		String auteur = (String) session.getAttribute("user");
		String titre = req.getParameter("titre");

		String sourceFrom = (String) session.getAttribute("sourceFrom");
		if (!sourceFrom.equals("newthread")) {
			if (!auteur.equals("invite"))
				logger.warn("\"" + auteur
						+ "\" tried to post a new thread without being in the good page, redirected to home");
			else
				logger.warn("visitor " + req.getRemoteAddr()
						+ " tried to post a new thread without being in the good page, redirected to home");
			resp.sendRedirect("/forum/home");
			return;
		}

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			dao.newThread(auteur, titre);
			logger.info("\"" + auteur + "\" posted a new thread: \"" + titre + "\"");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			logger.error("error while \"" + auteur + "\" tried to post a new thread, error: " + e.getMessage());
		}
		resp.sendRedirect("/forum/home");
	}

}
