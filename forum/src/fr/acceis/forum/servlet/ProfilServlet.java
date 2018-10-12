package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.acceis.forum.entity.Thread;

public class ProfilServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			String login = req.getParameter("login");

			if (!dao.existUser(login)) {
				resp.sendRedirect("/forum/home");
			} else {

				List<Thread> lthread = dao.getThreadUser(login);

				req.setAttribute("thread_answered", lthread);
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
