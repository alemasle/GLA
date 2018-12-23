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

import fr.acceis.forum.entity.Utilisateur;

public class EditPostServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(EditPostServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		String user = (String) session.getAttribute("user");
		Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
		int idMsg = Integer.parseInt(req.getParameter("id"));

		if (session.getAttribute("idThread") == null) {
			logger.warn("\"" + user + "\" tried to edit a post from invalid page, redirected to home");
			resp.sendRedirect("/forum/home");
			return;
		}

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			int idThread = (int) session.getAttribute("idThread");
			String u = dao.userFromMessageId(idMsg);

			if (!user.equals(u) && !utilisateur.getRole().editAllMessages()) {
				if (!user.equals("invite"))
					logger.warn("\"" + user + "\" tried to edit a message without permissions");
				else
					logger.warn("visitor " + req.getRemoteAddr() + " tried to edit a message without permissions");

				resp.sendRedirect("/forum/thread?id=" + idThread);
				return;
			}
			dao.decrementeVues(idThread);
			String msg = dao.getTexte(idMsg);
			req.setAttribute("txt", msg);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			logger.error(
					"\"" + user + "\" error while trying to edit message id:" + idMsg + ", error: " + e.getMessage());
		}
		session.setAttribute("sourceFrom", "editpost?id=" + idMsg);
		req.getRequestDispatcher("/WEB-INF/jsp/editpost.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		String texte = req.getParameter("texte");
		int idThread = (int) req.getSession().getAttribute("idThread");
		int idMsg = Integer.parseInt(req.getParameter("id"));
		String user = (String) session.getAttribute("user");

		String sourceFrom = (String) session.getAttribute("sourceFrom");
		if (!sourceFrom.equals("editpost?id=" + idMsg)) {
			if (!user.equals("invite"))
				logger.warn("\"" + user
						+ "\" tried to edit a message without being in the good page, redirected to home");
			else
				logger.warn("visitor " + req.getRemoteAddr()
						+ " tried to edit a message without being in the good page, redirected to home");
			resp.sendRedirect("/forum/home");
			return;
		}

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			dao.updateTexte(idMsg, texte);
			dao.updateDate(idMsg);
			logger.info("\"" + (String) req.getSession().getAttribute("user") + "\" edited a post id: " + idMsg
					+ " from thread id: " + idThread);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			logger.error("error while \"" + (String) req.getSession().getAttribute("user")
					+ "\" tried to edit the post " + idMsg + " from thread " + idThread + ", error: " + e.getMessage());
		}
		resp.sendRedirect("/forum/thread?id=" + idThread);
	}

}
