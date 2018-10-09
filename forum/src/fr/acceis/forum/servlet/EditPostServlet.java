package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditPostServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if (session == null) {
			resp.sendRedirect("/forum/home");
		} else {

			if (req.getSession().getAttribute("idThread") == null) {
				resp.sendRedirect("/forum/home");
			} else {
				DAOServlet dao;
				try {
					dao = DAOServlet.getDAO();
					int idMsg = Integer.parseInt(req.getParameter("id"));
					String msg = dao.getTexte(idMsg);
					req.setAttribute("txt", msg);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				req.getRequestDispatcher("/WEB-INF/jsp/editpost.jsp").forward(req, resp);
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String texte = req.getParameter("texte");
		int idThread = (int) req.getSession().getAttribute("idThread");
		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			int idMsg = Integer.parseInt(req.getParameter("id"));
			dao.updateTexte(idMsg, texte);
			dao.updateDate(idMsg);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("/forum/thread?id=" + idThread);
	}

}
