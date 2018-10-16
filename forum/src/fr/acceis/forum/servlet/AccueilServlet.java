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

public class AccueilServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//		HttpSession session = req.getSession();
//
//		if (session.getAttribute("user") == null) {
//			session.setAttribute("user", "invite");
//			Role role = new Invite();
//			Utilisateur invite = new Utilisateur("invite", "", 0, 0, "", "", role);
//			session.setAttribute("utilisateur", invite);
//
//		} else if ("invite".compareTo((String) session.getAttribute("user")) != 0) {
//			session.removeAttribute("idThread");
//		}

		try {
			DAOServlet dao = DAOServlet.getDAO();
			List<Thread> threads = dao.getThreads();
			req.setAttribute("threads", threads);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("/WEB-INF/jsp/threads.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
