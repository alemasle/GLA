package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.acceis.forum.entity.Message;

public class ThreadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			int threadId = Integer.parseInt(req.getParameter("id"));
			dao.incrementeVues(threadId);
			List<Message> messages = dao.getThreadMessages(threadId);
			String threadName = dao.getThreadName(threadId);

			req.setAttribute("messages", messages);
			req.setAttribute("threadName", threadName);
			session.setAttribute("idThread", threadId);
			req.getRequestDispatcher("/WEB-INF/jsp/thread.jsp").forward(req, resp);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
