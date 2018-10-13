package fr.acceis.forum.servlet;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.entity.Thread;
import fr.acceis.forum.entity.Utilisateur;

public class ProfilServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			String login = req.getParameter("login");

			if (dao.existUser(login) == null) {
				resp.sendRedirect("/forum/home");
			} else {

				Utilisateur user = dao.getUser(login);				

				List<Thread> lthread = dao.getThreadUser(login);
				req.setAttribute("threads_answered", lthread);
				req.setAttribute("login", login);
				req.setAttribute("utilisateur", user);
				
				req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req, resp);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
