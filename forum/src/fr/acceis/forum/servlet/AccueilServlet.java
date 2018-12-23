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

import fr.acceis.forum.entity.Thread;

public class AccueilServlet extends HttpServlet {

	private final static Logger logger = LogManager.getLogger(AccueilServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		if ("invite".equals((String) session.getAttribute("user"))) {
			session.removeAttribute("idThread");
		}

		try {
			DAOServlet dao = DAOServlet.getDAO();
			List<Thread> threads = dao.getThreads();
			req.setAttribute("threads", threads);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			logger.error("\"" + (String) session.getAttribute("user") + "\" error while trying to access home, error: "
					+ e.getMessage());
		}
		session.setAttribute("sourceFrom", "home");
		req.getRequestDispatcher("/WEB-INF/jsp/threads.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
