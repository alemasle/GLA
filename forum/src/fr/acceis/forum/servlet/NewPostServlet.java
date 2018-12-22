package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NewPostServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(NewPostServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			int idThread = (int) req.getSession().getAttribute("idThread");
			dao.decrementeVues(idThread);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			logger.error("error while \"" + (String) req.getSession().getAttribute("user")
					+ "\" tried to access newpost page, error: " + e.getMessage());
		}

		req.getRequestDispatcher("/WEB-INF/jsp/newpost.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String auteur = (String) req.getSession().getAttribute("user");
		String texte = req.getParameter("texte");
		int idThread = (int) req.getSession().getAttribute("idThread");
		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			dao.newPost(idThread, auteur, texte);
			dao.updateNbPosts(auteur);
			logger.info("\"" + auteur + "\" posted a new message on thread id: " + idThread);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException
				| ParseException e) {
			logger.error("error while \"" + (String) req.getSession().getAttribute("user")
					+ "\" tried to post a new message, error: " + e.getMessage());
		}
		resp.sendRedirect("/forum/thread?id=" + idThread);
	}

}
