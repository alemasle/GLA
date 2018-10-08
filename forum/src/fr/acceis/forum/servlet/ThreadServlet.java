package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		dao.incrementeVues();
		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();

			int threadId = Integer.parseInt(req.getParameter("id"));
			List<Message> messages = dao.getThreadMessages(threadId);
			// Paire message/auteur TODO

			req.setAttribute("messages", messages);
			req.getRequestDispatcher("/WEB-INF/jsp/thread.jsp").forward(req, resp);

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
