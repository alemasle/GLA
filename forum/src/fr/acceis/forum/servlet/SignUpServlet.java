package fr.acceis.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignUpServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = req.getParameter("username");
		String pass = req.getParameter("password");

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			if (dao.addUser(user, pass)) {
				System.out.println("Sign up -- User = " + user + " -- Pass = " + pass);
				HttpSession session = req.getSession();
				session.setAttribute("sess", true);
				session.setAttribute("user", user);
				resp.sendRedirect("/forum/home");
			} else {
				System.out.println("Fail to create user!");
				req.setAttribute("user", "Non connect&eacute;");
				req.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(req, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
