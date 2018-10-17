package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.acceis.forum.entity.Thread;
import fr.acceis.forum.entity.Utilisateur;
import fr.acceis.forum.roles.Invite;
import fr.acceis.forum.roles.Role;

public class ProfilServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			String login = req.getParameter("login");
			HttpSession session = req.getSession();

			System.out.println("Sess user test filter: " + session.getAttribute("user"));
//			if (session.getAttribute("user") == null) {
//				session.setAttribute("user", "invite");
//				Role role = new Invite();
//				Utilisateur invite = new Utilisateur("invite", "", 0, 0, "", "", role);
//				session.setAttribute("utilisateur", invite);
//				resp.sendRedirect("/forum/profil?login=" + login);
//			} else {

			if (dao.getLogin(login) == null) {
				resp.sendRedirect("/forum/home");
			} else {

				Utilisateur profil = dao.getUser(login);
				Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

				if (!utilisateur.getRole().readProfil()) {
					resp.sendRedirect("/forum/home");
				} else {

					List<Thread> lthread = dao.getThreadUser(login);
					req.setAttribute("threads_answered", lthread);
					req.setAttribute("login", login);
					req.setAttribute("userProfil", profil);
					req.getRequestDispatcher("/WEB-INF/jsp/profil.jsp").forward(req, resp);
				}
			}
//			}

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
