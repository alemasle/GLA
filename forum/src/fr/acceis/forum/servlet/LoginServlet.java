package fr.acceis.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.acceis.forum.entity.Utilisateur;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = req.getParameter("username");
		String pass = req.getParameter("password");

		if ("".compareTo(user) == 0 || "".compareTo(pass) == 0) {
			System.out.println("Fields empty");
			req.setAttribute("error", "emptyfields");
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
		} else {

			DAOServlet dao;
			try {
				dao = DAOServlet.getDAO();
				if (dao.checkUser(user, pass)) {

					Utilisateur u = dao.getUser(user);
					HttpSession session = req.getSession();
					session.setAttribute("sess", true);
					session.setAttribute("user", user);
					session.setAttribute("utilisateur", u);
					resp.sendRedirect("/forum/home");
					System.out.println("--> " + user + " : " + u.getRole().getRole() + " -- connection success");
				} else {
					req.setAttribute("error", "invite");
					req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
					System.out.println("--> " + user + " connection failed");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
