package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		req.getRequestDispatcher("/WEB-INF/jsp/newthread.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String auteur = (String) req.getSession().getAttribute("user");
		String titre = req.getParameter("titre");
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
