package fr.acceis.forum.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.acceis.forum.entity.Utilisateur;

public class NewPostServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		String user = (String) session.getAttribute("user");

		if (user == null || ("invite".compareTo(user) == 0)) {
			resp.sendRedirect("/forum/home");
		} else {
			Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
			if (!utilisateur.getRole().writeMessage()) {
				resp.sendRedirect("/forum/home");
			} else {
				DAOServlet dao;
				try {
					dao = DAOServlet.getDAO();
					int idThread = (int) req.getSession().getAttribute("idThread");
					dao.decrementeVues(idThread);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}

				req.getRequestDispatcher("/WEB-INF/jsp/newpost.jsp").forward(req, resp);
			}
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String auteur = (String) req.getSession().getAttribute("user");
		String texte = req.getParameter("texte");
		int idThread = (int) req.getSession().getAttribute("idThread");
		DAOServlet dao;
		try {
			dao = DAOServlet.getDAO();
			dao.newPost(idThread, auteur, texte);
			dao.updateNbPosts(auteur);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException
				| ParseException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("/forum/thread?id=" + idThread);
	}

}
