package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.entity.Thread;
import fr.acceis.forum.entity.Utilisateur;

public class ProfilServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			String login = req.getParameter("login");

			Utilisateur profil = dao.getUser(login);

			List<Thread> lthread = dao.getThreadUser(login);
			req.setAttribute("threads_answered", lthread);
			req.setAttribute("login", login);
			req.setAttribute("userProfil", profil);
			req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req, resp);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
