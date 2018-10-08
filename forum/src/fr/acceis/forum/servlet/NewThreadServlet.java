package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewThreadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session.getAttribute("user") == null) {
			resp.sendRedirect("/forum/home");
		} else {
			req.getRequestDispatcher("/WEB-INF/jsp/newthread.jsp").forward(req, resp);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String auteur = (String) req.getSession().getAttribute("user");
		String titre = req.getParameter("titre");
		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			dao.newThread(auteur, titre);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("/forum/home");
	}

}
