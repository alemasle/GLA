package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditPostServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("idThread") == null) {
			resp.sendRedirect("/forum/home");
		} else {

			DAOServlet dao;
			try {
				dao = DAOServlet.getDAO();
				int idMsg = Integer.parseInt(req.getParameter("id"));
				int idThread = (int) req.getSession().getAttribute("idThread");

				String u = dao.userFromMessageId(idMsg);
				String user = (String) req.getSession().getAttribute("user");

				if (!user.equals(u)) {
					resp.sendRedirect("/forum/thread?id=" + idThread);
					return;
				} else {
					dao.decrementeVues(idThread);
					String msg = dao.getTexte(idMsg);
					req.setAttribute("txt", msg);
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			req.getRequestDispatcher("/WEB-INF/jsp/editpost.jsp").forward(req, resp);

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
