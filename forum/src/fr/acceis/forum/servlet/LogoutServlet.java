package fr.acceis.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String user = (String) session.getAttribute("user");
		if (session != null) {
			session.invalidate();
		}
		resp.sendRedirect("/forum/home");
//		req.getRequestDispatcher("/WEB-INF/jsp/threads.jsp").forward(req, resp);
		System.out.println("--> " + user + " has disconnected");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
