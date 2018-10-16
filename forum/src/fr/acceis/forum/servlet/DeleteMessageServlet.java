package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteMessageServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String user = (String) req.getSession().getAttribute("user");
		if (user == null) {
			resp.sendRedirect("/forum/home");
		} else {
			req.getRequestDispatcher("/WEB-INF/jsp/deletemessage.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String user = (String) session.getAttribute("user");
		String pass = req.getParameter("password");
		int id = Integer.parseInt(req.getParameter("id"));
		int idThread = (int) session.getAttribute("idThread");

		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			if (dao.checkUser(user, pass)) {
				dao.deleteMessage(id);
				System.out.println("\"" + user + "\" --> Suppression message " + id);
				resp.sendRedirect("/forum/thread?id=" + idThread);

			} else {
				req.setAttribute("error", "invalide");
				req.getRequestDispatcher("/WEB-INF/jsp/delete.jsp").forward(req, resp);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
