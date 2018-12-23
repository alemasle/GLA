package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.acceis.forum.entity.Message;

public class ThreadServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(ThreadServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		int threadId = Integer.parseInt(req.getParameter("id"));

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();

			String threadName = dao.getThreadName(threadId);

			if (threadName == null) {
				logger.warn("\"" + (String) req.getSession().getAttribute("user")
						+ "\" tried to access unknown thread, redirected to home");
				resp.sendRedirect("/forum/home");
				return;
			}

			List<Message> messages = dao.getThreadMessages(threadId);
			dao.incrementeVues(threadId);

			req.setAttribute("messages", messages);
			req.setAttribute("threadName", threadName);
			session.setAttribute("idThread", threadId);
			session.setAttribute("sourceFrom", "thread?id=" + threadId);
			req.getRequestDispatcher("/WEB-INF/jsp/thread.jsp").forward(req, resp);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			logger.error("error while \"" + (String) req.getSession().getAttribute("user")
					+ "\" tried to access thread: " + threadId + ", error: " + e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
